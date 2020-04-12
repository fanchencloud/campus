package cn.fanchencloud.campus.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/13
 * Time: 3:36
 * Description: 订单信息模型，用于提交订单
 *
 * @author chen
 */
@Data
public class OrderInformation implements Serializable {

    private static final long serialVersionUID = 5731399463767739531L;

    /**
     * 订单用户名
     */
    private String username;
    /**
     * 订单手机号码
     */
    private String phone;
}
