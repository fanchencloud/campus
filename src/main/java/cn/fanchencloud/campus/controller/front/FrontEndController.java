package cn.fanchencloud.campus.controller.front;

import cn.fanchencloud.campus.entity.Headline;
import cn.fanchencloud.campus.entity.Shop;
import cn.fanchencloud.campus.entity.ShopCategory;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.HeadlineService;
import cn.fanchencloud.campus.service.ShopCategoryService;
import cn.fanchencloud.campus.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/10
 * Time: 20:31
 * Description: 首页控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/front")
public class FrontEndController {

    /**
     * 注入头条信息服务层
     */
    private HeadlineService headlineService;

    /**
     * 注入商铺类别服务层
     */
    private ShopCategoryService shopCategoryService;

    /**
     * 注入商铺服务层
     */
    private ShopService shopService;

    /**
     * 获取首页信息
     * 包括一级店铺类别列表
     * 头条信息列表
     *
     * @return 信息
     */
    @GetMapping(value = "/homePageInfo")
    @ResponseBody
    public JsonResponse getHomePageInfo() {
        Map<String, Object> map = new HashMap<>(2);
        try {
            // 获取一级店铺类别列表
            List<ShopCategory> shopCategoryList = shopCategoryService.queryShopCategoryList(null);
            // 获取头条信息
            List<Headline> headlineList = headlineService.queryHeadlineList(4, 1);
            map.put("shopCategoryList", shopCategoryList);
            map.put("headlineList", headlineList);
        } catch (Exception e) {
            return JsonResponse.errorMsg("获取信息出错，请联系管理员！");
        }
        return JsonResponse.ok(map);
    }

    /**
     * 多条件查询商铺列表（商铺名称模糊查询、商铺类别查询、商铺区域查询）
     *
     * @param shopName       商铺名称 参数可以为空
     * @param shopCategoryId 商铺类别id 参数可以为空
     * @param areaId         商铺区域id 参数可以为空
     * @param parentId       商铺区域id 参数可以为空
     * @return 商铺列表
     */
    @GetMapping(value = "/getShopList")
    @ResponseBody
    public JsonResponse getShopList(@RequestParam(value = "shopName", required = false) String shopName,
                                    @RequestParam(value = "id", required = false) Integer shopCategoryId,
                                    @RequestParam(value = "areaId", required = false) Integer areaId,
                                    @RequestParam(value = "parentId", required = false) Integer parentId,
                                    @RequestParam(value = "rowIndex") Integer rowIndex,
                                    @RequestParam(value = "pageSize") Integer pageSize) {
        List<Shop> shopList = shopService.getShopList(shopName, shopCategoryId, areaId, rowIndex, pageSize, parentId);
        return JsonResponse.ok(shopList);
    }

    /**
     * 获取父类id为id的商铺类别的列表
     *
     * @param id 商铺类别父类id 参数可以为空
     * @return 商铺类别列表
     */
    @GetMapping(value = "/getShopCategoryList")
    @ResponseBody
    public JsonResponse getShopCategoryList(@RequestParam(value = "id", required = false) Integer id) {
        List<ShopCategory> shopCategoryList = shopCategoryService.getRegisterShopCategoryByParentId(id);
        if (shopCategoryList != null) {
            return JsonResponse.ok(shopCategoryList);
        }
        return JsonResponse.errorMsg("查询参数有误！");
    }

    @Autowired
    public void setHeadlineService(HeadlineService headlineService) {
        this.headlineService = headlineService;
    }

    @Autowired
    public void setShopCategoryService(ShopCategoryService shopCategoryService) {
        this.shopCategoryService = shopCategoryService;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }
}