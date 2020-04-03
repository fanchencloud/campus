package cn.fanchencloud.campus.service.impl;

import cn.fanchencloud.campus.mapper.ShopCategoryMapper;
import cn.fanchencloud.campus.entity.ShopCategory;
import cn.fanchencloud.campus.service.ShopCategoryService;
import cn.fanchencloud.campus.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/14
 * Time: 0:48
 * Description:
 *
 * @author chen
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    /**
     * 注入店铺类别Mapper数据持久层
     */
    private ShopCategoryMapper shopCategoryMapper;

    @Override
    public List<ShopCategory> getRegisterShopCategoryList() {
        return shopCategoryMapper.getRegisterShopCategoryList();
    }

    @Override
    public ShopCategory getRegisterShopCategoryById(Integer shopCategoryId) {
        ShopCategory category = getTotalRegisterShopCategoryById(shopCategoryId);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(category.getShopCategoryId());
        shopCategory.setShopCategoryName(category.getShopCategoryName());
        shopCategory.setShopCategoryDesc(category.getShopCategoryDesc());
        return shopCategory;
    }

    @Override
    public ShopCategory getTotalRegisterShopCategoryById(Integer shopCategoryId) {
        return shopCategoryMapper.getRegisterShopCategoryById(shopCategoryId);
    }

    @Override
    public List<ShopCategory> queryShopCategoryList(ShopCategory shopCategory) {
        return shopCategoryMapper.queryShopCategoryList(shopCategory);
    }

    @Override
    public File getShopCategoryImage(String uuid) {
        ShopCategory shopCategory = shopCategoryMapper.queryShopCategoryImageByUUID(uuid);
        if (shopCategory == null) {
            logger.error("获取商品类别图片文件出错！uuid = " + uuid);
            return null;
        }
        String productImagePath = PathUtils.getImageBasePath() + shopCategory.getShopCategoryImg();
        productImagePath = PathUtils.replaceFileSeparator(productImagePath);
        assert productImagePath != null;
        return new File(productImagePath);
    }

    @Override
    public List<ShopCategory> getRegisterShopCategoryByParentId(Integer parentId) {
        ShopCategory shopCategory = null;
        if (parentId != null) {
            shopCategory = new ShopCategory();
            shopCategory.setParentId(parentId);
        }
        return shopCategoryMapper.queryShopCategoryList(shopCategory);
    }

    @Autowired
    public void setShopCategoryMapper(ShopCategoryMapper shopCategoryMapper) {
        this.shopCategoryMapper = shopCategoryMapper;
    }
}
