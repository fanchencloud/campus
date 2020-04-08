package cn.fanchencloud.campus.model;

import cn.fanchencloud.campus.entity.PersonInfo;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/9
 * Time: 1:40
 * Description: 封装用户信息
 *
 * @author chen
 */
public class User extends PersonInfo {
    /**
     * 用户名
     */
    private String username;

    public User() {
    }


    public User(PersonInfo p) {
        this.setId(p.getId());
        this.setName(p.getName());
        this.setGender(p.getGender());
        this.setPhone(p.getPhone());
        this.setEmail(p.getEmail());
        this.setHeadPortrait(p.getHeadPortrait());
        this.setCreateTime(p.getCreateTime());
        this.setLastEditTime(p.getLastEditTime());
        this.setEnableStatus(p.getEnableStatus());
        this.setUserType(p.getUserType());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
