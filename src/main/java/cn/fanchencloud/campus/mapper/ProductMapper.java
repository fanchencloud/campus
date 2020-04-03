package cn.fanchencloud.campus.mapper;


import cn.fanchencloud.campus.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/30
 * Time: 0:54
 * Description: 商品数据持久层
 *
 * @author chen
 */
@Mapper
public interface ProductMapper {

    /**
     * 将一条商品信息持久化到数据库
     *
     * @param product 商品信息
     * @return 持久化影响数据记录条数
     */
    int insertProduct(Product product);

    /**
     * 更新商品信息
     *
     * @param product 商品信息
     * @return 更新影响记录条数
     */
    int updateProduct(Product product);

    /**
     * 根据商品id查询商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     */
    Product queryProductByProductId(int productId);

    /**
     * 根据商品id 删除商品的缩略图
     *
     * @param productId 商品id
     * @return 删除影响记录条数
     */
    int deleteProductThumbnail(int productId);

    /**
     * 分页查询商品信息，可输入的条件：商品名称（模糊查询）、商品类别、所属商铺id
     *
     * @param product  商品查询信息
     * @param rowIndex 记录开始行数
     * @param pageSize 查询记录的行数
     * @return 查询列表
     */
    List<Product> queryProductList(@Param("product") Product product, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 获取商铺所有的商品信息
     *
     * @param shopId 商铺id
     * @return 商品列表
     */
    List<Product> getProductByShopId(int shopId);
}