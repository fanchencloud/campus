package cn.fanchencloud.campus.mapper;

import cn.fanchencloud.campus.entity.ShopCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/14
 * Time: 0:49
 * Description: 店铺类别持久层
 *
 * @author chen
 */
@Mapper
public interface ShopCategoryMapper {

    /**
     * 获取用于注册店铺时使用的店铺类别列表
     *
     * @return 店铺类别列表
     */
    List<ShopCategory> getRegisterShopCategoryList();

    /**
     * 获取所有的店铺类别列表
     *
     * @return 店铺类别列表
     */
    List<ShopCategory> getAllShopCategoryList();

    /**
     * 根据店铺类别id获取类别所有信息
     *
     * @param shopCategoryId 类别id
     * @return 类别信息
     */
    ShopCategory getRegisterShopCategoryById(Integer shopCategoryId);

    /**
     * 根据传入的查询条件查询商铺类别列表
     *
     * @param shopCategory 查询条件
     * @return 商铺类别列表
     */
    List<ShopCategory> queryShopCategoryList(@Param("shopCategory") ShopCategory shopCategory);

    /**
     * 根据商铺类别UUID获取该类别的图片
     *
     * @param uuid 唯一标志
     * @return 查询信息
     */
    ShopCategory queryShopCategoryImageByUUID(String uuid);
}
