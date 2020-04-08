package cn.fanchencloud.campus.controller;

import cn.fanchencloud.campus.util.CommonStrings;
import com.alibaba.fastjson.JSON;
import cn.fanchencloud.campus.entity.*;
import cn.fanchencloud.campus.model.FileContainer;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.AreaService;
import cn.fanchencloud.campus.service.ProductCategoryService;
import cn.fanchencloud.campus.service.ShopCategoryService;
import cn.fanchencloud.campus.service.ShopService;
import cn.fanchencloud.campus.util.CommonUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/13
 * Time: 21:54
 * Description: 店铺管理控制器
 *
 * @author chen
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    /**
     * 日志记录
     */
    private static Logger logger = LoggerFactory.getLogger(ShopController.class);

    /**
     * 错误记录
     */
    private static final String ERROR = "error";

    /**
     * 修改店铺的id
     */
    private static final String MODIFY_SHOP_ID = "modifyShopId";

    /**
     * 验证码
     */
    private static final String VALIDATE_CODE = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

    /**
     * 注入店铺信息服务层
     */
    private ShopService shopService;

    /**
     * 注入区域信息服务类
     */
    private AreaService areaService;

    /**
     * 注入店铺类别服务层
     */
    private ShopCategoryService shopCategoryService;

    /**
     * 注入店铺商品类别服务层
     */
    private ProductCategoryService productCategoryService;


    /**
     * 请求跳转到注册店铺的界面
     *
     * @return 界面
     */
    @GetMapping(value = "/registerShop")
    public String registerShop() {
        return "shop/registerShop";
    }

    /**
     * 请求跳转到用户店铺列表
     *
     * @return 用户店铺列表界面
     */
    @GetMapping(value = "/listShop")
    public String listShop() {
        return "shop/listShop";
    }

    /**
     * 请求跳转到店铺商品类别列表
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/listShopProductCategory")
    public String listShopProductCategory() {
        return "shop/shopProductList";
    }

    /**
     * 请求跳转到商品管理界面
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/managerProduct")
    public String managerProduct() {
        return "product/managerProduct";
    }


    /**
     * 请求获取店铺编号为id的店铺的商品类别列表
     *
     * @param shopId 店铺编号
     * @return 商品类别列表
     */
    @GetMapping(value = "/getShopProductCategoryList")
    @ResponseBody
    public JsonResponse getShopProductCategoryList(int shopId, HttpServletRequest request) {
        List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryListByShopId(shopId);
        request.getSession().setAttribute("shopId", shopId);
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("productCategoryList", productCategoryList);
        return JsonResponse.success("success", map);
    }

    /**
     * 请求跳转到修改店铺信息界面
     *
     * @param shopId 店铺id
     * @return 界面
     */
    @GetMapping(value = "/modifyShop")
    public String modifyShopMessage(int shopId, HttpServletRequest request) {
        request.getSession().setAttribute(MODIFY_SHOP_ID, shopId);
        return "shop/modifyShop";
    }

    /**
     * 获取用户店铺信息列表
     *
     * @return 用户店铺信息列表
     */
    @GetMapping(value = "/getShopList")
    @ResponseBody
    public JsonResponse getShopList(HttpServletRequest request) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        if (user == null) {
            return JsonResponse.errorMsg("后台数据错误！不存在用户！");
        }
        int userId = user.getUserId();

        List<Shop> shopList = shopService.getShopList(userId);
        Map<String, Object> map = new HashMap<>(2);
        map.put("shopList", shopList);
        map.put("user", "admin");
        return JsonResponse.success("success", map);
    }

    /**
     * 获取注册店铺所需要的信息，区域列表、店铺类型列表
     *
     * @return 区域、店铺类型列表信息
     */
    @GetMapping("/getRegisterShopInfo")
    @ResponseBody
    public JsonResponse getRegisterShopInfo() {
        // 获取区域列表
        List<Area> registerAreaList = areaService.getRegisterAreaList();
        // 获取店铺类别列表
        List<ShopCategory> categoryList = shopCategoryService.getRegisterShopCategoryList();
        Map<String, Object> map = new HashMap<>(2);
        map.put("areaList", registerAreaList);
        map.put("shopCategoryList", categoryList);
        return JsonResponse.success("success", map);
    }

    /**
     * 获取修改店铺信息所需要的信息，区域列表
     *
     * @param shopId 店铺id
     * @return 查询结果
     */
    @GetMapping(value = "/getModifyShopInfo")
    @ResponseBody
    public JsonResponse getModifyShopInfo(int shopId) {
        // 获取区域列表
        List<Area> registerAreaList = areaService.getRegisterAreaList();
        // 获取店铺信息
        Shop shop = shopService.queryShopByShopId(shopId);
        // 获取店铺类别信息
        ShopCategory shopCategory = shopCategoryService.getRegisterShopCategoryById(shop.getShopCategoryId());
        Map<String, Object> map = new HashMap<>(3);
        map.put("areaList", registerAreaList);
        map.put("shop", shop);
        map.put("shopCategory", shopCategory.getShopCategoryName());
        return JsonResponse.success("success", map);
    }

    /**
     * 请求获取店铺门面照
     *
     * @param shopId   店铺编号
     * @param response 响应
     */
    @GetMapping("/getStorePhoto")
    public void getStorePhoto(int shopId, HttpServletResponse response) throws IOException {
        //设置返回的文件类型
        response.setContentType("image/jpeg");
        // 获取该店铺的门面照的文件流
        File shopImage = shopService.getShopImage(shopId);
        CommonUtils.exportPictureData(response, shopImage);
    }

    @GetMapping("/getValidateCode")
    @ResponseBody
    public String getValidateCode(HttpServletRequest request) {
        String validateCode = (String) request.getSession().getAttribute(VALIDATE_CODE);
        logger.info("本次验证码为：" + validateCode);
        return validateCode;
    }

    /**
     * 注册一家店铺，接收并转化相应的参数，包括店铺信息以及图片信息
     *
     * @param request          http 请求
     * @param shopImg          商铺的图片
     * @param shopMessage      商铺的注册信息
     * @param verificationCode 验证码
     * @return 注册结果
     */
    @PostMapping("/registerShop")
    @ResponseBody
    public JsonResponse registerShop(HttpServletRequest request,
                                     @RequestParam("shopMessage") String shopMessage,
                                     @RequestParam("verificationCode") String verificationCode,
                                     @RequestParam(value = "shopImg", required = false) MultipartFile shopImg) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(shopMessage)) {
            return JsonResponse.errorMsg("注册的店铺信息不能为空");
        }
        if (StringUtils.isBlank(verificationCode)) {
            return JsonResponse.errorMsg("验证码不能为空");
        } else {
            String cacheCode = (String) request.getSession().getAttribute(CommonStrings.VALIDATE_CODE);
            // 验证码错误
            if (!(cacheCode.toLowerCase().equals(verificationCode.toLowerCase()))) {
                return JsonResponse.errorMsg("验证码错误！");
            }
        }
        if (shopImg == null) {
            return JsonResponse.errorMsg("注册的店铺的图片不能为空");
        }
        // 店铺信息
        Shop shop = JSON.parseObject(shopMessage, Shop.class);
        // 封装上传的商铺图片
        FileContainer fileContainer = new FileContainer();
        // 上传的文件流
        try {
            fileContainer.setFileInputStream(shopImg.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 上传文件的文件名
        fileContainer.setFileName(shopImg.getOriginalFilename());
        // 设置店主
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        shop.setOwnerId(user.getUserId());
        if (shopService.addShop(shop, fileContainer)) {
            return JsonResponse.ok("组成成功，请等待管理员审核！");
        } else {
            return JsonResponse.errorMsg("注册失败！");
        }
    }

    /**
     * 修改一家店铺
     *
     * @param request http 请求
     * @return 修改结果
     */
    @PostMapping("/modifyShop")
    @ResponseBody
    public JsonResponse modifyShop(HttpServletRequest request) throws UnsupportedEncodingException {
        // 获取本次修改的店铺id
        Integer shopId = (Integer) request.getSession().getAttribute(MODIFY_SHOP_ID);
        if (shopId == null) {
            return JsonResponse.errorMsg("会话过期或请求出错，请重试！");
        }
        // 接收并转化相应的参数，包括店铺信息以及图片信息
        AtomicReference<Map<String, Object>> modifyMap = new AtomicReference<>(doUploadMessage(request, false));
        if (modifyMap.get().get(ERROR) != null) {
            return (JsonResponse) modifyMap.get().get(ERROR);
        }
        // 需要修改的店铺信息
        Shop modifyShop = (Shop) modifyMap.get().get("shop");
        // 设置店铺id
        modifyShop.setShopId(shopId);
        // 上传的文件流
        InputStream uploadFile = (InputStream) modifyMap.get().get("uploadFile");
        // 上传文件的文件名
        String filename = (String) modifyMap.get().get("filename");
        FileContainer fileContainer = new FileContainer();
        fileContainer.setFileInputStream(uploadFile);
        fileContainer.setFileName(filename);
        if (shopService.modifyShop(modifyShop, fileContainer)) {
            return JsonResponse.ok("修改成功！");
        } else {
            return JsonResponse.errorMsg("修改失败！");
        }
    }

    /**
     * 处理表单请求
     *
     * @param request 请求数据
     * @param flag    是否需要上传图片
     * @return 数据集合
     * @throws UnsupportedEncodingException 抛出异常
     */
    private Map<String, Object> doUploadMessage(HttpServletRequest request, boolean flag) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        // 店铺信息
        String shopMessage = null;
        // 上传的文件流
        InputStream uploadFile = null;
        // 上传文件的文件名
        String filename = null;
        String verifyCode = null;
        Map<String, Object> map = new HashMap<>(4);
        //这种方法主要通过if (item.isFormField())这个条件判别文件还是非文件
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        } // 解析request请求
        // 遍历表单中提交过来的内容
        assert items != null;
        for (Object item1 : items) {
            FileItem item = (FileItem) item1;
            //  如果是表单域 ，就是非文件上传元素
            if (item.isFormField()) {
                // 获取value属性的值，这里需要指明UTF-8格式，否则出现中文乱码问题
                String value = item.getString("UTF-8");
                // 对应form中属性的名字
                if ("shop".equals(item.getFieldName())) {
                    shopMessage = value;
                } else if ("verifyCode".equals(item.getFieldName())) {
                    verifyCode = value;
                }
            } else {
                // 文件的名字
                filename = item.getName();
                // 获取上传文件的文件流
                try {
                    uploadFile = item.getInputStream();
                } catch (IOException e) {
                    logger.error(e.getMessage() + "获取文件上传流失败！");
                }
            }
        }
        // 检查图片验证码
        if (verifyCode == null || "".equals(verifyCode)) {
            map.put(ERROR, JsonResponse.errorMsg("图片验证码不能为空"));
        }
        // 获取kaptcha生成存放在session中的验证码
        String kaptchaValue = (String) request.getSession().getAttribute(VALIDATE_CODE);
        // 比较两个验证码
        assert verifyCode != null;
        if (!verifyCode.toLowerCase().equals(kaptchaValue.toLowerCase())) {
            map.put(ERROR, JsonResponse.errorMsg("验证码错误！"));
        }
        // 判断上传信息是否失败
        if (shopMessage == null) {
            map.put(ERROR, JsonResponse.errorMsg("店铺信息不能为空"));
        }
        if (flag) {
            if (uploadFile == null || filename == null) {
                map.put(ERROR, JsonResponse.errorMsg("店铺的图片信息不能为空,上传的图片为空，请重新上传！"));
            }
        }
        Shop shop = JSON.parseObject(shopMessage, Shop.class);
        map.put("shop", shop);
        map.put("uploadFile", uploadFile);
        map.put("filename", filename);
        return map;
    }

    /**
     * 请求添加一个商品列表信息
     *
     * @param productCategory 店铺商品类别
     * @return 添加结果
     */
    @PostMapping(value = "/addProductCategory")
    @ResponseBody
    public JsonResponse addProductCategory(ProductCategory productCategory, HttpServletRequest request) {
        if (StringUtils.isEmpty(productCategory.getProductCategoryName())) {
            return JsonResponse.errorMsg("分类名称不能为空");
        }
        Integer shopId = (Integer) request.getSession().getAttribute("shopId");
        if (shopId == null && productCategory.getShopId() == null) {
            return JsonResponse.errorMsg("店铺编号不能为空");
        }
        if (productCategory.getShopId() == null) {
            productCategory.setShopId(shopId);
        }
        boolean flag = productCategoryService.addProductCategory(productCategory);
        if (flag) {
            return JsonResponse.ok("添加成功！");
        }
        return JsonResponse.errorMsg("添加失败！");
    }

    /**
     * 删除店铺中的商品类别
     *
     * @param productCategoryId 商品类别id
     */
    @GetMapping(value = "/deleteProductCategory")
    @ResponseBody
    public JsonResponse deleteProductCategory(int productCategoryId) {
        productCategoryService.deleteProductCategory(productCategoryId);
        return JsonResponse.ok();
    }

    /**
     * 修改店铺中的商品类别
     *
     * @param productCategory 商品类别
     */
    @PostMapping(value = "/modifyProductCategory")
    @ResponseBody
    public JsonResponse modifyProductCategory(ProductCategory productCategory) {
        if (productCategory.getProductCategoryId() == null) {
            return JsonResponse.errorMsg("参数错误，修改失败！");
        }
        if (productCategoryService.modifyProductCategory(productCategory)) {
            return JsonResponse.ok();
        }
        return JsonResponse.errorMsg("修改失败");
    }

    /**
     * 通过商铺id 查询商铺的门面照
     *
     * @param shopId 商铺id
     */
    @GetMapping("/getShopImage")
    public void getShopImage(@RequestParam("shopId") int shopId, HttpServletResponse response) {
        File productThumbnail = shopService.getShopImage(shopId);
        try {
            CommonUtils.exportPictureData(response, productThumbnail);
        } catch (IOException e) {
            logger.error("输出图片文件失败！失败原因：" + e.getMessage());
        }
    }


    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @Autowired
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }

    @Autowired
    public void setShopCategoryService(ShopCategoryService shopCategoryService) {
        this.shopCategoryService = shopCategoryService;
    }

    @Autowired
    public void setProductCategoryService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
}
