package cn.fanchencloud.campus.controller;

import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.ProductImageService;
import cn.fanchencloud.campus.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/5
 * Time: 3:17
 * Description: 商品图片控制器
 *
 * @author chen
 */
@Controller
@RequestMapping("/productImage")
public class ProductImageController {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductImageController.class);

    /**
     * 注入商品图片服务层
     */
    private ProductImageService productImageService;

    /**
     * 请求获取商品详情图
     *
     * @param id       商品照片的唯一编号UUID
     * @param response 响应
     */
    @GetMapping("/getProductImage")
    public void getStorePhoto(String id, HttpServletResponse response) {
        // 获取该商品的文件流
        File productImage = productImageService.getProductImage(id);
        try {
            CommonUtils.exportPictureData(response, productImage);
        } catch (IOException e) {
            logger.error("输出图片文件失败！失败原因：" + e.getMessage());
        }
    }

    /**
     * 请求获取商品详情图UUID 列表
     *
     * @param productId 商品id
     */
    @GetMapping("/getProductImageList")
    @ResponseBody
    public JsonResponse getStorePhoto(int productId) {
        List<String> uuidList = productImageService.queryProductImageList(productId);
        return JsonResponse.ok(uuidList);
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public JsonResponse deletePicture(String id) {
        if (productImageService.deleteProductImageByUUID(id)) {
            return JsonResponse.ok("删除成功");
        } else {
            return JsonResponse.ok("删除失败");
        }
    }

    @Autowired
    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }
}
