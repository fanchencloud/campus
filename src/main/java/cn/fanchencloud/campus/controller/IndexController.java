package cn.fanchencloud.campus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/12
 * Time: 4:11
 * Description:
 *
 * @author chen
 */
@Controller
public class IndexController {
    /**
     * 请求跳转到首页
     *
     * @return 首页
     */
    @GetMapping(value = "/home")
    public String homePage() {
        return "homePage";
    }

    /**
     * 请求跳转到首页
     *
     * @return 首页
     */
    @GetMapping(value = "/")
    public String homePage1() {
        return "homePage";
    }

    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }
}
