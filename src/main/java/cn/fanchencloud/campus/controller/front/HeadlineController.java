package cn.fanchencloud.campus.controller.front;

import cn.fanchencloud.campus.service.HeadlineService;
import cn.fanchencloud.campus.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/11
 * Time: 14:37
 * Description: 头条信息控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/headline")
public class HeadlineController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(HeadlineController.class);

    /**
     * 注入头条信息服务层
     */
    private HeadlineService headlineService;

    /**
     * 根据头条信息UUID获取该头条的图片
     *
     * @param id       uuid
     * @param response 图片文件流
     */
    @GetMapping(value = "/image")
    public void getImage(String id, HttpServletResponse response) {
        // 获取该商品的文件流
        File productImage = headlineService.getHeadlineImage(id);
        try {
            CommonUtils.exportPictureData(response, productImage);
        } catch (IOException e) {
            logger.error("输出图片文件失败！失败原因：" + e.getMessage());
        }
    }

    @Autowired
    public void setHeadlineService(HeadlineService headlineService) {
        this.headlineService = headlineService;
    }
}
