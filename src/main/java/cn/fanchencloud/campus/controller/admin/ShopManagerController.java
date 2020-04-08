package cn.fanchencloud.campus.controller.admin;

import cn.fanchencloud.campus.entity.Shop;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/8
 * Time: 18:42
 * Description: 管理员商铺管理控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/admin")
public class ShopManagerController {

    /**
     * 注入商铺服务
     */
    private ShopService shopService;

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ShopManagerController.class);

    /**
     * 使编号为 shopId 的商铺可用状态为可用
     *
     * @param shopId 商铺ID
     * @return 操作结果
     */
    @ResponseBody
    @PostMapping(value = "/examinationPassed")
    public JsonResponse examinationPassed(@RequestParam("shopId") int shopId) {
        logger.debug("使能商铺Id = " + shopId);
        if (shopService.modifyShopStatus(shopId, true)) {
            return JsonResponse.ok("修改成功！");
        } else {
            return JsonResponse.errorMsg("修改失败！");
        }
    }

    /**
     * 使编号为 shopId 的商铺可用状态为不可用
     *
     * @param shopId 商铺ID
     * @return 操作结果
     */
    @ResponseBody
    @PostMapping(value = "/verifyDisable")
    public JsonResponse verifyDisable(@RequestParam("shopId") int shopId) {
        logger.debug("使能商铺Id = " + shopId);
        if (shopService.modifyShopStatus(shopId, false)) {
            return JsonResponse.ok("修改成功！");
        } else {
            return JsonResponse.errorMsg("修改失败！");
        }
    }

    @GetMapping(value = "/getShopEnableStatus")
    @ResponseBody
    public JsonResponse getShopEnableStatus(@RequestParam("shopId") int shopId) {
        Shop shop = shopService.queryTotalShopByShopId(shopId);
        if (shop == null) {
            return JsonResponse.errorMsg("查询出错！");
        }
        Shop temp = new Shop();
        temp.setShopId(shop.getShopId());
        temp.setEnableStatus(shop.getEnableStatus());
        Map<String, Shop> map = new HashMap<>(2);
        map.put("shop", temp);
        return JsonResponse.ok(map);
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }
}
