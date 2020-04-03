package cn.fanchencloud.campus.conf;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/2
 * Time: 16:36
 * Description:kaptcha验证码 配置类
 *
 * @author chen
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框
        properties.setProperty("kaptcha.border", "no");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        // 图片宽度
        properties.setProperty("kaptcha.image.width", "135");
        // 使用哪些字符生成验证码
        properties.setProperty("kaptcha.textproducer.char.string", "ACDEFGHIJKLMNOPQRSTUVWXYZ123456789");
        // 图片高度
        properties.setProperty("kaptcha.image.height", "50");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "43");
        // 干扰线的颜色
        properties.setProperty("kaptcha.noise.color", "black");
//        properties.setProperty("kaptcha.session.key", "code");
        // 字符个数
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 使用哪些字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑,Arial");
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;

    }
}