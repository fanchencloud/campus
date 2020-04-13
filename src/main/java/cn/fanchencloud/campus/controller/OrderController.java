package cn.fanchencloud.campus.controller;

import cn.fanchencloud.campus.conf.OrderConfig;
import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.entity.OrderForm;
import cn.fanchencloud.campus.entity.Product;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.model.OrderFormModel;
import cn.fanchencloud.campus.service.OrderService;
import cn.fanchencloud.campus.service.ProductService;
import com.alibaba.fastjson.JSON;
import com.sun.jdi.IntegerType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/13
 * Time: 3:33
 * Description: 订单控制器
 *
 * @author chen
 */
@RequestMapping(value = "/order")
@Controller
public class OrderController {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 注入订单服务
     */
    private OrderService orderService;

    /**
     * 注入商品服务
     */
    private ProductService productService;

    /**
     * 请求到商家的订单页面
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/listShopOrder")
    public ModelAndView listShopOrder(@RequestParam("shopId") int shopId,
                                      @RequestParam(value = "flag", required = false) Integer flag) {
        ModelAndView view = new ModelAndView("order/listOrder");
        logger.debug("请求订单列表的店铺ID = " + shopId);
        // 查询该店铺所有的订单
        List<OrderForm> orderFormList = null;
        if (flag == null) {
            orderFormList = orderService.getOrderListByShopId(shopId, null);
        } else if (flag == 1) {
            // 已完成订单
            orderFormList = orderService.getOrderListByShopId(shopId, 1);
        } else {
            // 未完成订单
            orderFormList = orderService.getOrderListByShopId(shopId, 0);
        }
        if (orderFormList == null || orderFormList.size() == 0) {
            return view;
        }
        List<OrderFormModel> list = orderFormList.stream().map(order -> {
            Product p = productService.getProductByProductId(order.getProductId());
            OrderFormModel t = new OrderFormModel(order);
            t.setProductName(p.getProductName());
            if (p.getPromotionPrice() != null) {
                t.setPrice(p.getPromotionPrice().toString());
            } else {
                t.setPrice(p.getNormalPrice().toString());
            }
            return t;
        }).collect(Collectors.toList());
        view.addObject("orderFormList", list);
        return view;
    }

    /**
     * 提交订单到服务器
     *
     * @param request          请求会话
     * @param orderInformation 订单信息
     * @return 请求结果
     */
    @PostMapping(value = "/submitOrder")
    @ResponseBody
    public JsonResponse submitOrder(HttpServletRequest request,
                                    @RequestParam("orderInformation") String orderInformation) {
        logger.debug("订单信息：" + orderInformation);
        if (StringUtils.isBlank(orderInformation)) {
            return JsonResponse.errorMsg("提交信息不能为空！");
        }
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        OrderForm orderForm = JSON.parseObject(orderInformation, OrderForm.class);
        if (user != null) {
            orderForm.setUserId(user.getUserId());
        } else {
            return JsonResponse.errorMsg("未登录，获取用户信息出错！");
        }
        logger.debug("转换的json对对象！" + orderForm.toString());
        if (orderService.addRecord(orderForm)) {
            return JsonResponse.ok("提交成功！");
        } else {
            return JsonResponse.errorMsg("添加失败！");
        }
    }

    /**
     * 确认接收订单
     *
     * @param orderId 订单id
     * @param message 订单备注信息
     * @return 请求结果
     */
    @PostMapping(value = "/confirmOrder")
    @ResponseBody
    public JsonResponse confirmOrder(@RequestParam("orderId") String orderId,
                                     @RequestParam("message") String message) {
        if (orderService.confirmOrder(orderId, message)) {
            Map<String, Object> map = new HashMap<>(2);
            OrderForm orderForm = orderService.getRecordByOrderId(orderId);
            if (orderForm.getOrderStatus() == OrderConfig.ORDER_RECEIVED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_RECEIVED_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_UNPROCESSED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_UNPROCESSED_INDEX);
            } else {
                map.put("orderStatus", OrderConfig.ORDER_REFUSE_STRING);
            }
            map.put("shopRemark", orderForm.getOrderShopRemark());
            return JsonResponse.ok(map);
        }
        return JsonResponse.errorMsg("订单处理失败！");
    }

    /**
     * 确认拒绝订单
     *
     * @param orderId 订单id
     * @param message 订单备注
     * @return 请求结果
     */
    @PostMapping(value = "/rejectOrder")
    @ResponseBody
    public JsonResponse rejectOrder(@RequestParam("orderId") String orderId,
                                    @RequestParam("message") String message) {
        if (orderService.rejectOrder(orderId, message)) {
            Map<String, Object> map = new HashMap<>(2);
            OrderForm orderForm = orderService.getRecordByOrderId(orderId);
            if (orderForm.getOrderStatus() == OrderConfig.ORDER_RECEIVED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_RECEIVED_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_UNPROCESSED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_UNPROCESSED_INDEX);
            } else {
                map.put("orderStatus", OrderConfig.ORDER_REFUSE_STRING);
            }
            map.put("shopRemark", orderForm.getOrderShopRemark());
            return JsonResponse.ok(map);
        }
        return JsonResponse.errorMsg("订单处理失败！");
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
