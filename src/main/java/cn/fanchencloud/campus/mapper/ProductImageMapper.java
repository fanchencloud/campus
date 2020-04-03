package cn.fanchencloud.campus.mapper;

import cn.fanchencloud.campus.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/30
 * Time: 1:02
 * Description:
 *
 * @author chen
 */
@Mapper
public interface ProductImageMapper {

    /**
     * 批量添加商品图片信息
     *
     * @param productImageList 商品图片信息列表
     * @return 操作影响记录条数
     */
    int bulkInsertProductImage(List<ProductImage> productImageList);

    /**
     * 根据商品id查询商品的图片列表
     *
     * @param productId 商品id
     * @return 商品图片列表
     */
    List<ProductImage> queryProductImageByProductId(int productId);

    /**
     * 根绝商品id 删除该商品的所有图片信息
     *
     * @param productId 商品id
     * @return 删除影响记录条数
     */
    int deleteProductImageByProductId(int productId);

    /**
     * 根据商品图片id 删除该图片
     *
     * @param productImgId 商品图片id
     * @return 删除结果影响条数
     */
    int deleteProductImageByProductImageId(int productImgId);

    /**
     * 通过UUID 查询商品图片详情
     *
     * @param uuid UUID
     * @return 商品图片详细数据
     */
    ProductImage queryProductImageByUUID(String uuid);

    /**
     * 查询商品id 为productId的所有商品图片的UUID
     *
     * @param productId 商品id
     * @return UUID 列表
     */
    List<String> queryProductImageList(int productId);

    /**
     * 根据商品详情图片的uuid 删除图片
     *
     * @param uuid uuid
     * @return 删除结果
     */
    int deleteProductImageByUUId(String uuid);
}
