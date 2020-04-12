package cn.fanchencloud.campus.controller.front;

import cn.fanchencloud.campus.entity.Product;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/15
 * Time: 2:31
 * Description: 商品前端控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/productFront")
public class ProductFrontController {

    /**
     *
     */
    private ProductService productService;

    /**
     * 请求查询商品数据
     *
     * @param productName       商品名（可以为空）
     * @param productCategoryId 商品类别id（可以为空）
     * @param rowIndex          记录行数
     * @param pageSize          分页数据大小
     * @return 数据集合
     */
    @GetMapping(value = "/getProductList")
    @ResponseBody
    public JsonResponse getProductList(@RequestParam(value = "productName", required = false) String productName,
                                       @RequestParam(value = "id", required = false) Integer productCategoryId,
                                       @RequestParam(value = "rowIndex") Integer rowIndex,
                                       @RequestParam(value = "shopId") Integer shopId,
                                       @RequestParam(value = "pageSize") Integer pageSize) {
        List<Product> productList = productService.compoundSearch(productName, productCategoryId, shopId, 1, rowIndex, pageSize);
        return JsonResponse.ok(productList);
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
