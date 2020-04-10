package cn.fanchencloud.campus.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/10
 * Time: 16:39
 * Description: 评论实体类
 *
 * @author chen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Comment extends Base implements Serializable {

    private static final long serialVersionUID = 4756741195656808137L;

    /**
     * 评论Id
     */
    private Integer id;
    /**
     * 评论者ID
     */
    private Integer userId;
    /**
     * 评论商铺ID
     */
    private Integer shopId;

    /**
     * 评论内容
     */
    private String detail;

    /**
     * 评论时间
     */
    private Date createTime;

}
