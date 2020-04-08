package cn.fanchencloud.campus.controller.admin;

import cn.fanchencloud.campus.conf.UserConfig;
import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.entity.PersonInfo;
import cn.fanchencloud.campus.entity.Shop;
import cn.fanchencloud.campus.model.User;
import cn.fanchencloud.campus.service.LocalAccountService;
import cn.fanchencloud.campus.service.PersonInfoService;
import cn.fanchencloud.campus.service.ShopService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/1
 * Time: 8:47
 * Description: 超级管理员控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    /**
     * 注入用户服务类
     */
    private LocalAccountService localAccountService;

    /**
     * 注入用户信息服务类
     */
    private PersonInfoService personInfoService;

    /**
     * 注入商铺服务层
     */
    private ShopService shopService;

    /**
     * 跳转到管理员登录页面
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "admin/login";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        // 获取已登录的用户信息
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        if (user == null) {
            return "admin/login";
        } else {
            return "admin/index";
        }
    }

    @GetMapping("/index")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/index_v")
    public String indexV() {
        return "admin/index_v";
    }

    @GetMapping(value = "/shopManager")
    public ModelAndView shopManager() {
        ModelAndView modelAndView = new ModelAndView("admin/shopManager");
        List<Shop> shopList = shopService.getAdministratorShopList();
        modelAndView.addObject("shopList", shopList);
        return modelAndView;
    }

//    @GetMapping(value = "/headlinesManager")
//    public String headlinesManager() {
//        return "admin/headlinesManager";
//    }

    @GetMapping(value = "/userManager")
    public ModelAndView userManager() {
        ModelAndView modelAndView = new ModelAndView("admin/userManager");
        List<User> userList = personInfoService.getAdministratorUserList();
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }


    /**
     * 管理员登录请求
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 登录结果
     */
    @PostMapping(value = "/login")
    public ModelAndView administratorLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        ModelAndView result = new ModelAndView();
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
                if (personInfo.getUserType() != UserConfig.ADMINISTRATOR_INDEX) {
                    result.setViewName("admin/login");
                    result.addObject("PromptData", "非管理员账号，禁止登录！");
                    return result;
                }
                temp.setUserType(personInfo.getUserType());
                request.getSession().setAttribute("user", temp);
                result.setViewName("admin/index");
                return result;
            }
        }
        result.addObject("PromptData", "用户名或密码错误，请重新登录！");
        result.setViewName("admin/login");
        return result;
    }

    @GetMapping(value = "/testData")
    public ModelAndView testPromptData() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("PromptData", "这是测试数据");
        modelAndView.setViewName("admin/login");
        return modelAndView;
    }

    @Autowired
    public void setLocalAccountService(LocalAccountService localAccountService) {
        this.localAccountService = localAccountService;
    }

    @Autowired
    public void setPersonInfoService(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }
}