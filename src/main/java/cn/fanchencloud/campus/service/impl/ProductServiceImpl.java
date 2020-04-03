package cn.fanchencloud.campus.service.impl;

import cn.fanchencloud.campus.mapper.ProductMapper;
import cn.fanchencloud.campus.entity.Product;
import cn.fanchencloud.campus.entity.ProductImage;
import cn.fanchencloud.campus.model.FileContainer;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.ProductImageService;
import cn.fanchencloud.campus.service.ProductService;
import cn.fanchencloud.campus.util.CommonUtils;
import cn.fanchencloud.campus.util.ImageUtils;
import cn.fanchencloud.campus.util.PathUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/30
 * Time: 2:02
 * Description: 商品服务层
 *
 * @author chen
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);


    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 注入商品持久层
     */
    private ProductMapper productMapper;

    /**
     * 注入商品详情图片服务层
     */
    private ProductImageService productImageService;

    /**
     * 添加一个商品信息
     * 1、处理店铺的缩略图信息，将缩略图保存后返回缩略图信息给商品
     * 2、将店铺信息写入到数据库中获取productId
     * 3、结合productId 批量处理商品详情图
     * 4、将商品详情图的信息持久化到数据库中
     *
     * @param product          商品信息
     * @param thumbnail        商品缩略图
     * @param productImageList 商品详情图片列表
     * @return 记录
     */
    @Override
    @Transactional
    public JsonResponse addProduct(Product product, FileContainer thumbnail, List<FileContainer> productImageList) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("addProduct");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
        JsonResponse jsonResponse = null;
        boolean flag = true;
        try {
            // 判断店铺商品是否是空值
            if (product == null || product.getShopId() == null || product.getProductCategoryId() == null) {
                throw new RuntimeException("商品信息有误！");
            }
            // 给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(product.getCreateTime());
            // 默认为上架的状态
            product.setEnableStatus(1);
            // 创建商品信息
            int number = productMapper.insertProduct(product);
            if (number <= 0) {
                throw new RuntimeException("添加商品信息失败！");
            }
            // 缩略图不为空则添加缩略图
            if (thumbnail != null) {
                saveThumbnail(product, thumbnail);
                // 更新商品的缩略图信息
                Product temp = new Product();
                temp.setProductId(product.getProductId());
                temp.setImgPath(product.getImgPath());
                number = productMapper.updateProduct(temp);
                if (number <= 0) {
                    logger.error("处理商品缩略图信息出错！商品id：" + product.getProductId());
                    throw new RuntimeException("添加商品失败！");
                }
            }
            // 商品详情图片不为空则将商品详情图保存到磁盘
            if (productImageList != null && productImageList.size() > 0) {
                List<ProductImage> productImages = saveProductImage(productImageList, product);
                // 将详情图片数据持久化到数据库 99999
                int line = productImageService.addInBatchesProductImage(productImages);
                if (line <= 0) {
                    throw new RuntimeException("添加商品详情图片失败！");
                }
            }
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(status);
            flag = false;
            jsonResponse = JsonResponse.errorMsg(e.getMessage());
        }
        if (flag) {
            jsonResponse = JsonResponse.ok("添加商品成功！");
        }
        return jsonResponse;
    }


    /**
     * 添加一个商品信息
     * 1、处理店铺的缩略图信息，将缩略图保存后返回缩略图信息给商品
     * 2、将店铺信息写入到数据库中获取productId
     * 3、结合productId 批量处理商品详情图
     * 4、将商品详情图的信息持久化到数据库中
     *
     * @param product          商品信息
     * @param thumbnail        商品缩略图
     * @param productImageList 商品详情图片列表
     * @return 记录
     */
    @Override
    @Transactional
    public JsonResponse modifyProduct(Product product, FileContainer thumbnail, List<FileContainer> productImageList) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("modifyProduct");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
        JsonResponse jsonResponse = null;
        boolean flag = true;
        try {
            // 判断店铺商品是否是空值
            if (product == null || product.getProductId() == null || product.getProductCategoryId() == null) {
                logger.error("商品信息有误！");
                throw new RuntimeException("商品信息有误！");
            }
            // 给商品设置默认属性
            product.setLastEditTime(new Date());
            // 修改商品信息
            int number = productMapper.updateProduct(product);
            if (number <= 0) {
                throw new RuntimeException("修改商品信息失败！");
            }
            // 缩略图不为空则添加缩略图
            if (thumbnail != null) {
                saveThumbnail(product, thumbnail);
                // 更新商品的缩略图信息
                Product temp = new Product();
                temp.setProductId(product.getProductId());
                temp.setImgPath(product.getImgPath());
                number = productMapper.updateProduct(temp);
                if (number <= 0) {
                    logger.error("处理商品缩略图信息出错！商品id：" + product.getProductId());
                    throw new RuntimeException("修改商品失败！");
                }
            }
            // 商品详情图片不为空则将商品详情图保存到磁盘
            if (productImageList != null && productImageList.size() > 0) {
                List<ProductImage> productImages = saveProductImage(productImageList, product);
                // 将详情图片数据持久化到数据库 99999
                int line = productImageService.addInBatchesProductImage(productImages);
                if (line <= 0) {
                    throw new RuntimeException("修改商品详情图片失败！");
                }
            }
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(status);
            flag = false;
            jsonResponse = JsonResponse.errorMsg(e.getMessage());
        }
        if (flag) {
            jsonResponse = JsonResponse.ok("修改商品成功！");
        }
        return jsonResponse;
    }

    @Override
    public Product getProductByProductId(int productId) {
        Product product = productMapper.queryProductByProductId(productId);
        product.setCreateTime(null);
        product.setLastEditTime(null);
        if (product.getImgPath() != null) {
            product.setImgPath(product.getProductId().toString());
        }
        return product;
    }

    @Override
    public File getProductThumbnail(int productId) {
        Product product = productMapper.queryProductByProductId(productId);
        String productImagePath = PathUtils.getImageBasePath() + product.getImgPath();
        productImagePath = PathUtils.replaceFileSeparator(productImagePath);
        assert productImagePath != null;
        return new File(productImagePath);
    }

    @Override
    public JsonResponse deleteProductThumbnail(int productId) {
        //删除图片文件
        Product product = productMapper.queryProductByProductId(productId);
        boolean flag = ImageUtils.deleteProductThumbnail(product.getImgPath());
        int number = productMapper.deleteProductThumbnail(productId);
        if (flag && (number != 0)) {
            return JsonResponse.ok("删除成功");
        } else {
            return JsonResponse.errorMsg("删除失败");
        }
    }

    @Override
    public List<Product> compoundSearch(String productName, Integer productCategoryId, Integer shopId, Integer enableStatus, Integer rowIndex, Integer pageSize) {
        Product product = new Product();
        if (StringUtils.isNoneBlank(productName)) {
            product.setProductName(productName);
        }
        if (productCategoryId != null) {
            product.setProductCategoryId(productCategoryId);
        }
        if (enableStatus != null) {
            product.setEnableStatus(enableStatus);
        }
        product.setShopId(shopId);
        return productMapper.queryProductList(product, rowIndex, pageSize);
    }

    @Override
    public List<Product> getProductByShopId(int shopId) {
        return productMapper.getProductByShopId(shopId);
    }

    @Override
    public void modifyProductStatus(int productId) {
        Product product = productMapper.queryProductByProductId(productId);
        if (product == null) {
            return;
        }
        Product p = new Product();
        p.setProductId(productId);
        if (product.getEnableStatus() == 1) {
            p.setEnableStatus(0);
        } else {
            p.setEnableStatus(1);
        }
        productMapper.updateProduct(p);
    }

    /**
     * 将商品详情图片保存到本地
     *
     * @param productImageList 商品详情图片文件列表
     * @return 商品详情图片列表信息
     */
    private List<ProductImage> saveProductImage(List<FileContainer> productImageList, Product product) throws IOException {
        String targetPath = PathUtils.getProductImagePath(product.getProductId());
        List<ProductImage> productImages = new ArrayList<>(productImageList.size());
        for (FileContainer productImage : productImageList) {
            String realPath = ImageUtils.savePicture(productImage, targetPath);
            ProductImage temp = new ProductImage();
            temp.setImgPath(realPath);
            temp.setCreateTime(new Date());
            temp.setProductId(product.getProductId());
            temp.setUuid(CommonUtils.getUUID());
            productImages.add(temp);
        }
        return productImages;
    }

    /**
     * 存储商品的缩略图
     *
     * @param product   商品信息
     * @param thumbnail 商品缩略图信息
     */
    private void saveThumbnail(Product product, FileContainer thumbnail) throws IOException {
        // 获取基准路径
        String targetPath = PathUtils.getProductThumbnailImagePath(product.getProductId());
        String thumbnailPath = ImageUtils.generateThumbnail(thumbnail.getFileInputStream(), targetPath, thumbnail.getFileName());
        product.setImgPath(thumbnailPath);
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Autowired
    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }
}