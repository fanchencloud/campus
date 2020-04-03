package cn.fanchencloud.campus.service;

import cn.fanchencloud.campus.entity.Product;
import cn.fanchencloud.campus.model.FileContainer;
import cn.fanchencloud.campus.model.JsonResponse;

import java.io.File;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/30
 * Time: 1:56
 * Description: 商品服务类
 *
 * @author chen
 */
public interface ProductService {
    /**
     * 添加一个商品信息
     *
     * @param product          商品信息
     * @param thumbnail        商品缩略图
     * @param productImageList 商品详情图片列表
     * @return 记录
     */
    JsonResponse addProduct(Product product, FileContainer thumbnail, List<FileContainer> productImageList);

    /**
     * 修改一个商品信息
     *
     * @param product          商品信息
     * @param thumbnail        商品缩略图
     * @param productImageList 商品详情图片列表
     * @return 记录
     */
    JsonResponse modifyProduct(Product product, FileContainer thumbnail, List<FileContainer> productImageList);

    /**
     * 根据商品id 商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     */
    Product getProductByProductId(int productId);

    /**
     * 根据商品id 获取商品的缩略图文件
     *
     * @param productId 商品id
     * @return 商品缩略图文件流
     */
    File getProductThumbnail(int productId);

    /**
     * 根据商品id删除商品缩略图
     *
     * @param productId 商品id
     * @return 删除结果
     */
    JsonResponse deleteProductThumbnail(int productId);

    /**
     * 根据商品名称、商品类别、商铺id等复合查询商品数据
     *
     * @param productName       商品名称
     * @param productCategoryId 商品类别id
     * @param shopId            商铺id
     * @param enableStatus      商品状态
     * @param rowIndex          记录行数
     * @param pageSize          分页数据大小
     * @return 数据集合
     */
    List<Product> compoundSearch(String productName, Integer productCategoryId, Integer shopId, Integer enableStatus, Integer rowIndex, Integer pageSize);

    /**
     * 通过商铺id 获取该商铺所有的商品列表
     *
     * @param shopId 商铺id
     * @return 商品列表
     */
    List<Product> getProductByShopId(int shopId);

    /**
     * 改变商品上下架情况
     *
     * @param productId 商品id
     */
    void modifyProductStatus(int productId);
}
