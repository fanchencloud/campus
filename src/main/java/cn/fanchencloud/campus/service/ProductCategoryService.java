package cn.fanchencloud.campus.service;

import cn.fanchencloud.campus.entity.ProductCategory;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/28
 * Time: 14:47
 * Description: 店铺商品类别服务
 *
 * @author chen
 */
public interface ProductCategoryService {

    /**
     * 根据店铺id查询该店铺所有的商品类别列表
     *
     * @param shopId 店铺id
     * @return 商品类别列表
     */
    List<ProductCategory> queryProductCategoryListByShopId(int shopId);

    /**
     * 添加一个店铺商品分类
     *
     * @param productCategory 商品分类
     * @return 添加结果
     */
    boolean addProductCategory(ProductCategory productCategory);

    /**
     * 删除店铺中的商品类别
     *
     * @param productCategoryId 商品类别id
     */
    void deleteProductCategory(int productCategoryId);

    /**
     * 修改一条商品类别信息记录
     *
     * @param productCategory 商品类别信息记录
     * @return 修改结果
     */
    boolean modifyProductCategory(ProductCategory productCategory);

    /**
     * 根据商品类别记录id查询类别信息
     *
     * @param productCategoryId 商品类别记录id
     * @return 商品类别信息
     */
    ProductCategory queryProductCategoryByProductCategoryId(int productCategoryId);
}
