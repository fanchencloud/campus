package cn.fanchencloud.campus.controller;

import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.entity.OrderForm;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.OrderService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
