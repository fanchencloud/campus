package cn.fanchencloud.campus.mapper;



import cn.fanchencloud.campus.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/28
 * Time: 3:33
 * Description: 店铺商品类别持久化
 *
 * @author chen
 */
@Mapper
public interface ProductCategoryMapper {

    /**
     * 根据店铺id查询该店铺所有的商品类别列表
     *
     * @param shopId 店铺id
     * @return 商品类别列表
     */
    List<ProductCategory> queryProductCategoryListByShopId(int shopId);

    /**
     * 添加一个店铺商品类别信息
     *
     * @param productCategory 商品类别信息
     * @return 添加记录影响记录条数
     */
    int addProductCategory(ProductCategory productCategory);

    /**
     * 删除店铺中的商品类别
     *
     * @param productCategoryId 商品类别id
     */
    void deleteProductCategory(int productCategoryId);

    /**
     * 修改一条商品类别记录
     *
     * @param productCategory 商品类别记录
     * @return 修改影响记录条数
     */
    int modifyProductCategory(ProductCategory productCategory);

    /**
     * 根据商品类别记录id查询类别信息
     *
     * @param productCategoryId 商品类别记录id
     * @return 商品类别信息
     */
    ProductCategory queryProductCategoryByProductCategoryId(int productCategoryId);
}
