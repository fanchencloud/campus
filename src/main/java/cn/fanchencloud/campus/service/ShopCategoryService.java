package cn.fanchencloud.campus.service;

import cn.fanchencloud.campus.entity.ShopCategory;

import java.io.File;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/14
 * Time: 0:46
 * Description: 店铺类别服务层
 *
 * @author chen
 */
public interface ShopCategoryService {

    /**
     * 获取用于注册店铺时使用的店铺类别列表
     *
     * @return 店铺类别列表
     */
    List<ShopCategory> getRegisterShopCategoryList();

    /**
     * 根据店铺类别id获取类别信息
     *
     * @param shopCategoryId 类别id
     * @return 类别信息
     */
    ShopCategory getRegisterShopCategoryById(Integer shopCategoryId);

    /**
     * 根据店铺类别id获取类别所有信息
     *
     * @param shopCategoryId 类别id
     * @return 类别信息
     */
    ShopCategory getTotalRegisterShopCategoryById(Integer shopCategoryId);

    /**
     * 根据传入的查询条件查询商铺类别列表
     *
     * @param shopCategory 查询条件
     * @return 商铺类别列表
     */
    List<ShopCategory> queryShopCategoryList(ShopCategory shopCategory);

    /**
     * 根据商铺类别UUID获取该类别的图片
     *
     * @param uuid 唯一标志
     * @return 文件流
     */
    File getShopCategoryImage(String uuid);

    /**
     * 查询 parentId 下的所有商铺类别列表
     *
     * @param parentId 父类标签id
     * @return 商店类别列表
     */
    List<ShopCategory> getRegisterShopCategoryByParentId(Integer parentId);
}
