package cn.fanchencloud.campus.service.impl;

import cn.fanchencloud.campus.entity.OrderForm;
import cn.fanchencloud.campus.mapper.OrderFormMapper;
import cn.fanchencloud.campus.service.OrderService;
import cn.fanchencloud.campus.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/13
 * Time: 4:33
 * Description:
 *
 * @author chen
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 注入订单服务持久层
     */
    private OrderFormMapper orderFormMapper;

    @Override
    public boolean addRecord(OrderForm orderForm) {
        orderForm.setOrderId(CommonUtils.getUniversallyUniqueIdentifier());
        orderForm.setCreateTime(new Date());
        orderForm.setLastEditTime(new Date());
        // 默认设置为提交订单未处理状态
        orderForm.setOrderStatus(0);
        return orderFormMapper.addRecord(orderForm) > 0;
    }

    @Override
    public List<OrderForm> getOrderListByShopId(int shopId, Integer flag) {
        if (flag == null) {
            return orderFormMapper.getOrderListByShopId(shopId);
        } else if (flag == 1) {
            return orderFormMapper.getOrderListByShopIdCompleted(shopId);
        } else {
            return orderFormMapper.getOrderListByShopIdUnfinished(shopId);
        }
    }

    @Override
    public boolean confirmOrder(String orderId, String message) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(orderId);
        //      * 订单状态，0-提交订单未处理 ， 1-商家已接单， 2-商家拒接订单,
        orderForm.setOrderStatus(1);
        orderForm.setOrderShopRemark(message);
        orderForm.setLastEditTime(new Date());
        return orderFormMapper.modifyOrder(orderForm) > 0;
    }

    @Override
    public boolean rejectOrder(String orderId, String message) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(orderId);
        //      * 订单状态，0-提交订单未处理 ， 1-商家已接单， 2-商家拒接订单,
        orderForm.setOrderStatus(2);
        orderForm.setOrderShopRemark(message);
        orderForm.setLastEditTime(new Date());
        return orderFormMapper.modifyOrder(orderForm) > 0;
    }

    @Override
    public OrderForm getRecordByOrderId(String orderId) {
        return orderFormMapper.getRecordByOrderId(orderId);
    }

    @Autowired
    public void setOrderFormMapper(OrderFormMapper orderFormMapper) {
        this.orderFormMapper = orderFormMapper;
    }
}
