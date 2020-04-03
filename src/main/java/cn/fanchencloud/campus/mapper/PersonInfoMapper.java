package cn.fanchencloud.campus.mapper;


import cn.fanchencloud.campus.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;

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
}
