package cn.fanchencloud.campus.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/12
 * Time: 17:15
 * Description: 路径工具类
 *
 * @author chen
 */
public class PathUtils {

    /**
     * 获取图片根路径
     *
     * @return 图片文件夹路径
     */
    public static String getImageBasePath() {
        String os = System.getProperty("os.name");
        String basePath;
        String osStart = "win";
        if (os.toLowerCase().startsWith(osStart)) {
            basePath = "E:/campusShop/image/";
        } else {
            basePath = "/home/chen/image/";
        }
        basePath = basePath.replace("/", File.separator);
        return basePath;
    }

    /**
     * 获取商店缩略图的存储路径
     *
     * @param shopId 商店id
     * @return 该商店的根路径
     */
    public static String getShopImagePath(int shopId) {
        String imagePath = "upload/thumbnail/shop/" + shopId + "/";
        return imagePath.replace("/", File.separator);
    }

    /**
     * 获取商品缩略图的存储路径
     *
     * @param productId 商品id
     * @return 该商品的根路径
     */
    public static String getProductThumbnailImagePath(int productId) {
        String imagePath = "upload/thumbnail/product/" + productId + "/";
        return imagePath.replace("/", File.separator);
    }

    /**
     * 获取商品详情图的存储路径
     *
     * @param productId 商品id
     * @return 该商品详情图片存储的根路径
     */
    public static String getProductImagePath(int productId) {
        String imagePath = "upload/picture/product/" + productId + "/";
        return imagePath.replace("/", File.separator);
    }

    /**
     * 获取用户头像的保存路径
     *
     * @param id 用户id
     * @return 保存路径
     */
    public static String getUserImagePath(Integer id) {
        String imagePath = "upload/user/" + id + "/";
        return imagePath.replace("/", File.separator);
    }

    /**
     * 将文件分隔符替换为对应系统的
     *
     * @param filePath 文件路径
     * @return 替换分隔符之后的路径
     */
    public static String replaceFileSeparator(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        String s = filePath.replace("\\", File.separator);
        s = s.replace("/", File.separator);
        return s;
    }
}
