package cn.fanchencloud.campus.service.impl;

import cn.fanchencloud.campus.entity.OrderForm;
import cn.fanchencloud.campus.mapper.OrderFormMapper;
import cn.fanchencloud.campus.service.OrderService;
import cn.fanchencloud.campus.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Autowired
    public void setOrderFormMapper(OrderFormMapper orderFormMapper) {
        this.orderFormMapper = orderFormMapper;
    }
}
