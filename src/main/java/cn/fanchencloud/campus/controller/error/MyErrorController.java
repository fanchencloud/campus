package cn.fanchencloud.campus.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/2
 * Time: 3:19
 * Description: 异常控制器
 *
 * @author chen
 */
@Controller
@Log4j2
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 404) {
            return "error/404";
        } else {
            return "error/500";
        }

    }

    @Override
    public String getErrorPath() {
        log.info("进入自定义错误页面");
        return "error/error";
    }

    /**
     * 404 error
     *
     * @return 404异常界面
     */
    @RequestMapping("/404")
    public String error404() {
        return "error/error";
    }

    /**
     * 500 error
     *
     * @return 500 异常界面
     */
    @RequestMapping("/500")
    public String error500() {
        return "error/500";
    }
}
