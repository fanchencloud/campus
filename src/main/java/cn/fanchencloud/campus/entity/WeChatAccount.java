package cn.fanchencloud.campus.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/9
 * Time: 1:13
 * Description: 微信账号
 *
 * @author chen
 */
public class WeChatAccount extends Base implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * 用户id 连接用户表
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后编辑时间
     */
    private Date lastEditTime;

    @Override
    public String toString() {
        return "WeChatAccount{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
