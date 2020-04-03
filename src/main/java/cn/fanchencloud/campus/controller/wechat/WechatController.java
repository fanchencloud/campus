package cn.fanchencloud.campus.controller.wechat;

import cn.fanchencloud.campus.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 1:24
 * Description: 微信控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/wechat")
public class WechatController {

    /**
     * 日志注入
     */
    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    /**
     * 接收微信服务器发送的4个参数进行校验，并返回echostr
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return 返回数据
     */
    @GetMapping(value = "/authenticate")
    @ResponseBody
    public String test(String signature, String timestamp, String nonce, String echostr) {
        logger.info("接收到微信服务器认证请求！");
        if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
            // 校验通过，原样返回echostr参数内容
            return echostr;
        } else {
            return null;
        }
    }
}
