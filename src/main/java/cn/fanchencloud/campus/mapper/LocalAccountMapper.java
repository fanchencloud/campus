package cn.fanchencloud.campus.mapper;

import cn.fanchencloud.campus.entity.LocalAccount;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 8:26
 * Description: 本地账号数据持久层
 *
 * @author chen
 */
@Mapper
public interface LocalAccountMapper {
    /**
     * 根据用户名和密码查询用户本地账号信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 查询记录
     */
    LocalAccount getRecordByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 添加一个本地账号
     *
     * @param localAccount 本地账号
     * @return 添加结果影响记录条数
     */
    int addLocalAccount(LocalAccount localAccount);

    /**
     * 查询用户名是否被使用
     *
     * @param username 用户名
     * @return 是否被使用
     */
    boolean isExist(String username);

    /**
     * 修改用户信息
     *
     * @param localAccount 用户信息
     * @return 修改影响记录条数
     */
    int updateLocalAccount(LocalAccount localAccount);

    /**
     * 通过用户名查询用户本地账户信息
     *
     * @param username 用户名
     * @return 用户本地信息
     */
    LocalAccount getRecordByUsername(String username);

    /**
     * 查询管理员页面所需要的所有账户列表（使用user_id 做map的key）
     *
     * @return 用户列表
     */
    @MapKey("userId")
    Map<Integer, LocalAccount> queryAdministratorLocalAccountList();

    /**
     * 根据用户userId 查询列表
     * @param userIdList 用户userId 集合
     * @return 用户本地账号列表
     */
    @MapKey("userId")
    Map<Integer, LocalAccount> getRecordsByUserIds(List<Integer> userIdList);
}
