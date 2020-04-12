package cn.fanchencloud.campus.mapper;

import cn.fanchencloud.campus.entity.OrderForm;
import org.apache.ibatis.annotations.Mapper;

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
}
