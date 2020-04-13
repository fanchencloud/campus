package cn.fanchencloud.campus.mapper;

import cn.fanchencloud.campus.entity.OrderForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/13
 * Time: 4:41
 * Description: 订单持久层
 *
 * @author chen
 */
@Mapper
public interface OrderFormMapper {

    /**
     * 将一条订单记录持久化到数据库
     *
     * @param orderForm 订单信息
     * @return 订单记录
     */
    int addRecord(OrderForm orderForm);

    /**
     * 根据商铺shopId查询所有订单信息
     *
     * @param shopId 商铺的shopId
     * @return 订单列表
     */
    List<OrderForm> getOrderListByShopId(int shopId);

    /**
     * 根据商铺shopId查询所有已完成订单信息
     *
     * @param shopId 商铺的shopId
     * @return 订单列表
     */
    List<OrderForm> getOrderListByShopIdCompleted(int shopId);

    /**
     * 根据商铺shopId查询所有未完成订单信息
     *
     * @param shopId 商铺的shopId
     * @return 订单列表
     */
    List<OrderForm> getOrderListByShopIdUnfinished(int shopId);

    /**
     * 修改一条订单记录
     *
     * @param orderForm 订单记录
     * @return 修改影响结果
     */
    int modifyOrder(OrderForm orderForm);


    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    OrderForm getRecordByOrderId(String orderId);
}
