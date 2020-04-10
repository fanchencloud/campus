package cn.fanchencloud.campus.mapper;


import cn.fanchencloud.campus.entity.PersonInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 23:36
 * Description: 用户信息持久层
 *
 * @author chen
 */
@Mapper
public interface PersonInfoMapper {

    /**
     * 新增一位用户
     *
     * @param personInfo 用户信息
     * @return 添加用户的数据库中的主键
     */
    int addPersonInfo(PersonInfo personInfo);

    /**
     * 根据记录id查询用户信息
     *
     * @param id 用户记录id
     * @return 用户信息
     */
    PersonInfo queryById(int id);

    /**
     * 删除用户的头像
     *
     * @param id 记录id
     */
    void deletePersonHeadImage(int id);

    /**
     * 更新用户数据
     *
     * @param personInfo 用户数据
     * @return 更新结果
     */
    int updateUserMessage(PersonInfo personInfo);

    /**
     * 查询管理员页面所需要的用户列表
     *
     * @return 用户列表
     */
    List<PersonInfo> queryAdministratorUserList();

    /**
     * 根据用户 userId 查询用户信息列表
     *
     * @param userIdList 用户 userId 集合
     * @return 信息列表
     */
    @MapKey("id")
    Map<Integer, PersonInfo> getRecordsByUserIds(List<Integer> userIdList);
}
