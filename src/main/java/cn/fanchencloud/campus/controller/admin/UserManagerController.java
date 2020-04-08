package cn.fanchencloud.campus.controller.admin;

import cn.fanchencloud.campus.entity.PersonInfo;
import cn.fanchencloud.campus.entity.Shop;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.model.User;
import cn.fanchencloud.campus.service.LocalAccountService;
import cn.fanchencloud.campus.service.PersonInfoService;
import cn.fanchencloud.campus.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/8
 * Time: 18:42
 * Description: 管理员用户管理控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/admin")
public class UserManagerController {

    /**
     * 注入本地账号服务
     */
    private LocalAccountService localAccountService;
    /**
     * 注入用户信息服务
     */
    private PersonInfoService personInfoService;

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    /**
     * 使编号为 UserId 的账号可用状态为可用
     *
     * @param userId 用户Id
     * @return 操作结果
     */
    @ResponseBody
    @PostMapping(value = "/examinationAccountPassed")
    public JsonResponse examinationAccountPassed(@RequestParam("userId") int userId) {
        logger.debug("使能用户Id = " + userId);
        if (personInfoService.modifyUserStatus(userId, true)) {
            return JsonResponse.ok("修改成功！");
        } else {
            return JsonResponse.errorMsg("修改失败！");
        }
    }

    /**
     * 使编号为 UserId 的账号可用状态为不可用
     *
     * @param userId 用户Id
     * @return 操作结果
     */
    @ResponseBody
    @PostMapping(value = "/verifyAccountDisable")
    public JsonResponse verifyAccountDisable(@RequestParam("userId") int userId) {
        logger.debug("使能用户Id = " + userId);
        if (personInfoService.modifyUserStatus(userId, false)) {
            return JsonResponse.ok("修改成功！");
        } else {
            return JsonResponse.errorMsg("修改失败！");
        }
    }

    @GetMapping(value = "/getUserEnableStatus")
    @ResponseBody
    public JsonResponse getUserEnableStatus(@RequestParam("userId") int userId) {
        PersonInfo personInfo = personInfoService.getPersonInfo(userId);
        if (personInfo == null) {
            return JsonResponse.errorMsg("查询出错！");
        }
        User user = new User();
        user.setId(personInfo.getId());
        user.setEnableStatus(personInfo.getEnableStatus());
        Map<String, User> map = new HashMap<>(2);
        map.put("user", user);
        return JsonResponse.ok(map);
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
