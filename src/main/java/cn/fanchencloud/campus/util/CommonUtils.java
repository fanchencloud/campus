package cn.fanchencloud.campus.util;

import cn.fanchencloud.campus.model.JsonResponse;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/5
 * Time: 3:14
 * Description: 公共工具类
 *
 * @author chen
 */
public class CommonUtils {
    /**
     * 获取UUID
     * @return 返回唯一UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取通用唯一识别码
     * @return UUID
     */
    public static String getUniversallyUniqueIdentifier() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 将图片文件输出到前端
     *
     * @param response 响应
     * @param file     文件
     */
    public static void exportPictureData(HttpServletResponse response, File file) throws IOException {

        //解决中文文件名下载后乱码的问题
        String fileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //设置返回的文件类型
        response.setContentType("image/" + fileSuffix);
        response.setHeader("Content- Disposition", "attachment;filename=" + fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        //将图片写入ImageIO流
        BufferedImage bufferedImage = ImageIO.read(file);
        //将图片写出到指定位置（复制图片）
        ImageIO.write(bufferedImage, fileSuffix, outputStream);
    }

    public static List<Object> validateCode(String verifyCode, HttpServletRequest request) {
        List<Object> list = new LinkedList<>();
        // 检查图片验证码
        if (verifyCode == null || "".equals(verifyCode)) {
            list.add(JsonResponse.errorMsg("图片验证码不能为空"));
        }
        // 获取 kaptcha 生成存放在session中的验证码
        String kaptchaValue = (String) request.getSession().getAttribute(CommonStrings.VALIDATE_CODE);
        // 比较两个验证码
        assert verifyCode != null;
        if (!verifyCode.toLowerCase().equals(kaptchaValue.toLowerCase())) {
            list.add(JsonResponse.errorMsg("验证码错误！"));
        }
        return list;
    }
}
