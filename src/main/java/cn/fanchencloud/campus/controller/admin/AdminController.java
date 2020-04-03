package cn.fanchencloud.campus.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping(value = "/test")
    @ResponseBody
    public String test() {
        return "test Controller";
    }
}
