package cn.fanchencloud.campus.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020年4月13日
 * Time: 03点20分
 * Description: 订单实体类
 *
 * @author chen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderForm extends Base implements Serializable {

    private static final long serialVersionUID = 404738854827942624L;

    /**
     * 订单唯一id，使用uuid生成
     */
    private String orderId;

    /**
     * 下单用户id
     */
    private Integer userId;
    /**
     * 下单商品id
     */
    private Integer productId;
    /**
     * 下单的店铺id
     */
    private Integer shopId;
    /**
     * 下单用户名称
     */
    private String orderUsername;
    /**
     * 下单用户联系方式
     */
    private String orderPhone;
    /**
     * 下单用户地址
     */
    private String orderAddress;
    /**
     * 下单用户备注
     */
    private String orderUserRemark;
    /**
     * 下单店家备注
     */
    private String orderShopRemark;

    /**
     * 订单状态，0-提交订单未处理 ， 1-商家已接单， 2-商家拒接订单,
     */
    private Integer orderStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    private Date lastEditTime;
}
