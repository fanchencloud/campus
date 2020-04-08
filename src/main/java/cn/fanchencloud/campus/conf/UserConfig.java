package cn.fanchencloud.campus.conf;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/1
 * Time: 7:46
 * Description: 用户配置类
 *
 * @author chen
 */
public class UserConfig {

    /**
     * 普通用户
     */
    public static final String GENERAL_USER = "普通用户";

    /**
     * 普通顾客的身份标识
     */
    public static final int GENERAL_USER_INDEX = 1;

    /**
     * 商家
     */
    public static final String BUSINESS = "商家";

    /**
     * 商家的身份标识
     */
    public static final int BUSINESS_INDEX = 2;

    /**
     * 管理员
     */
    public static final String ADMINISTRATOR = "管理员";

    /**
     * 管理员的身份标识
     */
    public static final int ADMINISTRATOR_INDEX = 3;

    /**
     * 登录成功
     */
    public static int LOGIN_SUCCESSFUL = 1;
    /**
     * 登录失败
     */
    public static int LOGIN_FAILED = 2;
    /**
     * 账号还在审核
     */
    public static int ACCOUNT_IS_STILL_UNDER_REVIEW = 3;

}
