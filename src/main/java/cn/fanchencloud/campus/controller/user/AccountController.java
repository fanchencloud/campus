package cn.fanchencloud.campus.controller.user;

import cn.fanchencloud.campus.conf.UserConfig;
import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.entity.PersonInfo;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.LocalAccountService;
import cn.fanchencloud.campus.service.PersonInfoService;
import cn.fanchencloud.campus.util.CommonStrings;
import cn.fanchencloud.campus.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 7:13
 * Description: 用户账号服务控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/user")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * 注入用户服务类
     */
    private LocalAccountService localAccountService;

    /**
     * 注入用户信息服务类
     */
    private PersonInfoService personInfoService;

    /**
     * 用户使用用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public JsonResponse login(String username, String password, HttpServletRequest request) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            LocalAccount user = new LocalAccount();
            user.setUsername(username);
            user.setPassword(password);
            int flag = localAccountService.login(user);
            if (flag == UserConfig.LOGIN_SUCCESSFUL) {
                // 获取用户本地账户
                LocalAccount temp = localAccountService.getLocalAccount(username);
                // 查询用户详细信息
                PersonInfo personInfo = personInfoService.getPersonInfo(temp.getUserId());
                temp.setUserType(personInfo.getUserType());
                request.getSession().setAttribute("user", temp);
                return JsonResponse.ok("登录成功！");
            } else if (flag == UserConfig.ACCOUNT_IS_STILL_UNDER_REVIEW) {
                return JsonResponse.errorMsg("登录失败，账号还在审核中！");
            } else {
                return JsonResponse.errorMsg("登录失败，用户名或密码错误！");
            }
        }
        return JsonResponse.errorMsg("登录失败，用户名或密码错误！");
    }

    /**
     * 获取用户的头像
     *
     * @param response 服务器响应
     */
    @GetMapping(value = "/getUserImage")
    public void getUserHeadImage(HttpServletRequest request, HttpServletResponse response) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        if (user == null) {
            return;
        }
        // 获取该用户头像的文件流
        File userImage = personInfoService.getUserImage(user.getUserId());
        try {
            CommonUtils.exportPictureData(response, userImage);
        } catch (IOException e) {
            logger.error("输出图片文件失败！失败原因：" + e.getMessage());
        }
    }

    /**
     * 获取用户的头像
     *
     * @param response 服务器响应
     */
    @GetMapping(value = "/getUserImageById")
    public void getUserImageById(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("userId") int userId) {
        // 获取该用户头像的文件流
        File userImage = personInfoService.getUserImage(userId);
        try {
            CommonUtils.exportPictureData(response, userImage);
        } catch (IOException e) {
            logger.error("输出图片文件失败！失败原因：" + e.getMessage());
        }
    }

    @GetMapping(value = "/isLogin")
    @ResponseBody
    public JsonResponse isLogin(HttpServletRequest request) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        if (user == null) {
            return JsonResponse.errorMsg("你还没登录，请登录！");
        } else {
            return JsonResponse.ok("已登录！");
        }
    }


    /**
     * 检查用户的评论权限
     *
     * @param request 请求
     * @param shopId  请求的商铺Id
     * @return 检验信息
     */
    @GetMapping(value = "/checkPermissions")
    @ResponseBody
    public JsonResponse checkPermissions(HttpServletRequest request,
                                         @RequestParam("shopId") int shopId) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<>(4);
        if (user == null) {
            // 未登录
            map.put("status", 400);
            map.put("message", "你还没登录，请登录！");
            return JsonResponse.error(null, map);
        } else if (user.getUserType() == UserConfig.GENERAL_USER_INDEX) {
            // 已经登录，是普通用户
            map.put("status", 200);
            return JsonResponse.error(null, map);
        } else {
            // 已经登录，但不是普通用户或者未在此商铺消费
            map.put("status", 401);
            map.put("message", "已登录，无操作权限！");
            return JsonResponse.error(null, map);
        }
    }

    /**
     * 校验是否显示预定按钮
     *
     * @param request 请求会话
     * @return 校验结果
     */
    @ResponseBody
    @GetMapping(value = "/checkReservation")
    public JsonResponse checkReservation(HttpServletRequest request) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        if (user != null && user.getUserType() == UserConfig.GENERAL_USER_INDEX) {
            return JsonResponse.ok();
        } else {
            return JsonResponse.errorMsg("无权限");
        }
    }

    /**
     * 请求获取用户信息
     *
     * @param request 数据请求
     * @return 用户信息
     */
    @GetMapping(value = "/getUserInfo")
    @ResponseBody
    public JsonResponse getPersonInfo(HttpServletRequest request) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        if (user == null) {
            return JsonResponse.errorMsg("你还没登录，请登录！");
        }
        PersonInfo personInfo = personInfoService.getPersonInfo(user.getUserId());
        personInfo.setEnableStatus(null);
        return JsonResponse.ok(personInfo);
    }

    /**
     * 注册用户
     *
     * @param username     用户名
     * @param password     密码
     * @param validateCode 验证码
     * @param userType     用户类别
     * @return 注册结果
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public JsonResponse register(String username, String password, String validateCode, String userType, HttpServletRequest request) {
        String cacheCode = (String) request.getSession().getAttribute(CommonStrings.VALIDATE_CODE);
        // 验证码为空
        if (StringUtils.isBlank(validateCode)) {
            return JsonResponse.errorMsg("验证码不能为空！");
        }
        // 验证码错误
        if (!(cacheCode.toLowerCase().equals(validateCode.toLowerCase()))) {
            return JsonResponse.errorMsg("验证码错误！");
        }
        int userFlag = 0;
        if (UserConfig.GENERAL_USER.equals(userType)) {
            userFlag = 1;
        } else if (UserConfig.BUSINESS.equals(userType)) {
            userFlag = 2;
        }
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || userFlag == 0) {
            return JsonResponse.errorMsg("注册信息有误！");
        }
        LocalAccount user = new LocalAccount();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserType(userFlag);
        return localAccountService.register(user);
    }

    /**
     * 请求修改用户登录密码
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/modifyPassword")
    public String modifyPassword() {
        return "/user/modifyPassword";
    }

    @PostMapping(value = "/modifyPassword")
    @ResponseBody
    public JsonResponse modifyPassword(String originalPassword, String newPassword, HttpServletRequest request) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        if (localAccountService.modifyPassword(originalPassword, newPassword, user)) {
            return JsonResponse.ok("修改成功！");
        }
        return JsonResponse.errorMsg("修改密码失败！");
    }

    /**
     * 请求退出登录
     *
     * @param request 请求
     * @return 页面跳转
     */
    @GetMapping(value = "/loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/home";
    }

    @Autowired
    public void setLocalAccountService(LocalAccountService localAccountService) {
        this.localAccountService = localAccountService;
    }

    @Autowired
    public void setPersonInfoService(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }
}