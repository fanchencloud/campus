package cn.fanchencloud.campus.service.impl;

import cn.fanchencloud.campus.mapper.ProductCategoryMapper;
import cn.fanchencloud.campus.entity.ProductCategory;
import cn.fanchencloud.campus.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/28
 * Time: 14:48
 * Description:
 * h
 *
 * @author chen
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    /**
     * 注入店铺商品类别持久层
     */
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> queryProductCategoryListByShopId(int shopId) {
        List<ProductCategory> productCategoryList = productCategoryMapper.queryProductCategoryListByShopId(shopId);
        for (ProductCategory productCategory : productCategoryList) {
            productCategory.setShopId(null);
        }
        return productCategoryList;
    }

    @Override
    public boolean addProductCategory(ProductCategory productCategory) {
        return productCategoryMapper.addProductCategory(productCategory) != 0;
    }

    @Override
    public void deleteProductCategory(int productCategoryId) {
        productCategoryMapper.deleteProductCategory(productCategoryId);
    }

    @Override
    public boolean modifyProductCategory(ProductCategory productCategory) {
        productCategory.setLastEditTime(new Date());
        return productCategoryMapper.modifyProductCategory(productCategory) != 0;
    }

    @Override
    public ProductCategory queryProductCategoryByProductCategoryId(int productCategoryId) {
        return productCategoryMapper.queryProductCategoryByProductCategoryId(productCategoryId);
    }

    @Autowired
    public void setProductCategoryMapper(ProductCategoryMapper productCategoryMapper) {
        this.productCategoryMapper = productCategoryMapper;
    }
}
