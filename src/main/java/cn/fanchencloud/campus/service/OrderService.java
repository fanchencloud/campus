package cn.fanchencloud.campus.service;

import cn.fanchencloud.campus.entity.OrderForm;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/13
 * Time: 4:31
 * Description: 订单服务类
 *
 * @author chen
 */
public interface OrderService {

    /**
     * 添加一条订单记录
     *
     * @param orderForm 订单信息
     * @return 添加结果
     */
    boolean addRecord(OrderForm orderForm);

}
