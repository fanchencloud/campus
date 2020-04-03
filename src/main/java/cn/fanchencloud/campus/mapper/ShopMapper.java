package cn.fanchencloud.campus.mapper;

import cn.fanchencloud.campus.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/12
 * Time: 16:12
 * Description: 电铺数据持久化操作
 *
 * @author chen
 */
@Mapper
public interface ShopMapper {

    /**
     * 新增一家电铺
     *
     * @param shop 店铺信息
     * @return 添加结果
     */
    int addShop(Shop shop);

    /**
     * 通过店铺id查询店铺相关信息
     *
     * @param shopId 店铺id
     * @return 店铺信息
     */
    Shop queryShopByShopId(int shopId);


    /**
     * 通过店铺id查询店铺门面照信息
     *
     * @param shopId 店铺id
     * @return 店铺门面照信息
     */
    Shop queryShopImageByShopId(int shopId);

    /**
     * 更新店铺信息
     *
     * @param shop 店铺信息
     * @return 更新结果
     */
    int updateShop(Shop shop);

    /**
     * 分页查询店铺信息，可输入的条件：店铺名（模糊查询）、店铺状态、店铺类别、区域、所有者
     *
     * @param shop     店铺查询信息
     * @param rowIndex 记录开始行数
     * @param pageSize 查询记录的行数
     * @return 查询列表
     */
    List<Shop> queryShopList(@Param("shop") Shop shop, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 查询满足条件的记录条数
     *
     * @param shop 查询条件
     * @return 记录条数
     */
    int queryShopNumber(@Param("shop") Shop shop);
}
