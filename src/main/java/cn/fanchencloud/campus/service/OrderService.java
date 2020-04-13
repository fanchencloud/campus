package cn.fanchencloud.campus.service;

import cn.fanchencloud.campus.entity.OrderForm;

import java.util.List;

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

    /**
     * 根据商铺shopId查询所有订单信息
     *
     * @param shopId 商铺的shopId
     * @param flag   请求参数
     * @return 订单列表
     */
    List<OrderForm> getOrderListByShopId(int shopId, Integer flag);

    /**
     * 确认接收订单
     *
     * @param orderId 订单id
     * @param message 订单备注
     * @return 请求结果
     */
    boolean confirmOrder(String orderId, String message);

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    OrderForm getRecordByOrderId(String orderId);

    /**
     * 拒接订单
     *
     * @param orderId 订单id
     * @param message 订单备注
     * @return 请求结果
     */

    boolean rejectOrder(String orderId, String message);
}
