package cn.fanchencloud.campus.controller;

import cn.fanchencloud.campus.entity.ShopCategory;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.ShopCategoryService;
import cn.fanchencloud.campus.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/11
 * Time: 14:37
 * Description:
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/shopCategory")
public class ShopCategoryController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ShopCategoryController.class);

    /**
     * 注入商铺类别服务层
     */
    private ShopCategoryService shopCategoryService;

    /**
     * 根据商铺类别的id获取该类别的图片描述
     *
     * @param id       商铺类别id
     * @param response 图片描述文件流
     */
    @GetMapping(value = "/image")
    public void getImage(String id, HttpServletResponse response) {
        // 获取该商品的文件流
        File productImage = shopCategoryService.getShopCategoryImage(id);
        try {
            CommonUtils.exportPictureData(response, productImage);
        } catch (IOException e) {
            logger.error("输出图片文件失败！失败原因：" + e.getMessage());
        }
    }

    /**
     * 根据查询的商铺类别父类id 查询商铺类别列表
     *
     * @param shopCategoryId 商铺类别的父类id
     * @return 商铺类别列表
     */
    @GetMapping(value = "/getShopCategoryList")
    @ResponseBody
    public JsonResponse getShopCategoryList(@RequestParam(value = "id", required = false) Integer shopCategoryId) {
        List<ShopCategory> shopCategoryList = shopCategoryService.getRegisterShopCategoryByParentId(shopCategoryId);
        if (shopCategoryList != null) {
            return JsonResponse.ok(shopCategoryList);
        }
        return JsonResponse.errorMsg("查询参数错误！");
    }

    @Autowired
    public void setShopCategoryService(ShopCategoryService shopCategoryService) {
        this.shopCategoryService = shopCategoryService;
    }
}
