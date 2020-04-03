package cn.fanchencloud.campus.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/9
 * Time: 16:05
 * Description: 店铺实体类
 *
 * @author chen
 */
public class Shop extends Base implements Serializable {
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 店铺所有者
     */
    private Integer ownerId;
    /**
     * 店铺区域id
     */
    private Integer areaId;
    /**
     * 店铺类别ID
     */
    private Integer shopCategoryId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 店铺描述
     */
    private String shopDescription;

    /**
     * 店铺地址
     */
    private String shopAddress;
    /**
     * 店铺联系方式
     */
    private String phone;
    /**
     * 店铺门面照片
     */
    private String shopImg;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    private Date lastEditTime;
    /**
     * 店铺状态
     * 0 审核中
     * 1 审核通过
     * -1 审核不通过
     */
    private Integer enableStatus;
    /**
     * 店铺建议
     */
    private String advice;

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", ownerId=" + ownerId +
                ", areaId=" + areaId +
                ", shopCategoryId=" + shopCategoryId +
                ", shopName='" + shopName + '\'' +
                ", shopDescription='" + shopDescription + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", shopImg='" + shopImg + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", enableStatus=" + enableStatus +
                ", advice='" + advice + '\'' +
                '}';
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Integer shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}