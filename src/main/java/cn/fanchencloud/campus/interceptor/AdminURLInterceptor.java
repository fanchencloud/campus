package cn.fanchencloud.campus.interceptor;

import cn.fanchencloud.campus.conf.UserConfig;
import cn.fanchencloud.campus.entity.LocalAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/4
 * Time: 23:44
 * Description: 管理员 URL拦截器，用户拦截管理员请求域下的URL请求
 *
 * @author chen
 */
public class AdminURLInterceptor implements HandlerInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(AdminURLInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("预处理执行！");
        //客户请求求的URL，不包括参数数据
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("requestURL = " + requestURL);
        logger.debug("客户端请求的URL = " + requestURL);
        // 获取已登录的用户信息
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        // 如果未登录直接返回
        if (user == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/login");
            request.setAttribute("PromptData", "未登录禁止访问！");
            logger.debug("用户未登录，禁止访问！");
            requestDispatcher.forward(request, response);
            return false;
        }
        // 已经登录，但不是管理员
        if (user.getUserType() != UserConfig.ADMINISTRATOR_INDEX) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/login");
            request.setAttribute("PromptData", "身份错误，请求异常！");
            logger.debug("用户身份错误，禁止访问！");
            requestDispatcher.forward(request, response);
            return false;
        }
//        String ip = request.getLocalAddr();
//        //将URL的域名和尾随的参数截取掉，剩下的那部分就是URI
//        String requestURI = request.getRequestURI();
//        logger.debug("requestURI = " + requestURI);
//        //返回URL上的参数部分的字符串，必须是GET的请求才有效，不然报错
//        String queryString = request.getQueryString();
//        logger.debug("queryString = " + queryString);
//        //返回请求的方案名，如http,ftp,https等
//        String scheme = request.getScheme();
//        logger.debug("scheme = " + scheme);
//        //HTTP请求的的方法名，默认是GET，也可以指定PUT或POST
//        String method = request.getMethod();
//        logger.debug("method = " + method);
//        //即斜杆加工程名
//        String contextPath = request.getContextPath();
//        logger.debug("contextPath = " + contextPath);
//        //服务器主机名
//        String serverName = request.getServerName();
//        logger.debug("serverName = " + serverName);
//        //服务器上web应用的访问端口
//        int serverPort = request.getServerPort();
//        int localPort = request.getLocalPort();
//        logger.debug("serverPort = " + serverPort + "   localPort = " + localPort);
//        //返回请求的协议名和版本，如HTTP/1.1等
//        String protocol = request.getProtocol();
//        logger.debug("protocol = " + protocol);
//        //工程之后到参数之前的这部分字符串
//        String servletPath = request.getServletPath();
//        logger.debug("servletPath = " + servletPath);
//        //字符串包含与客户端发送请求的URL相关的额外信息
//        String pathInfo = request.getPathInfo();
//        logger.debug("pathInfo = " + pathInfo);

        logger.debug("预处理器执行完毕！允许访问！访问身份：" + user.getUsername());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandler！");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("afterCompletion");
    }
}
