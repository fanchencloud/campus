package cn.fanchencloud.campus.util;

import cn.fanchencloud.campus.controller.ProductController;
import cn.fanchencloud.campus.model.FileContainer;
import cn.fanchencloud.campus.model.JsonResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/4
 * Time: 5:30
 * Description:
 *
 * @author chen
 */
public class ChenHttpUtils {
    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * 错误记录
     */
    private static final String ERROR = "error";

    /**
     * 处理表单请求
     *
     * @param request  请求数据
     * @param paramMap 请求参数列表
     * @return 数据集合
     * @throws UnsupportedEncodingException 抛出异常
     */
    public static Map<String, Object> doUploadMessage(HttpServletRequest request, Map<String, String> paramMap) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Map<String, Object> map = new HashMap<>(8);
        //这种方法主要通过if (item.isFormField())这个条件判别文件还是非文件
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        // 解析request请求
        // 遍历表单中提交过来的内容
        assert items != null;
        List<FileContainer> fileContainers = null;
        for (Object item1 : items) {
            FileItem item = (FileItem) item1;
            //  如果是表单域 ，就是非文件上传元素
            if (item.isFormField()) {
                // 获取value属性的值，这里需要指明UTF-8格式，否则出现中文乱码问题
                String value = item.getString("UTF-8");
                // 对应form中属性的名字
                if (paramMap.containsKey(item.getFieldName())) {
                    map.put(item.getFieldName(), value);
                    paramMap.remove(item.getFieldName());
                } else {
                    map.put(item.getFieldName(), value);
                }
            } else {
                if (fileContainers == null) {
                    fileContainers = new LinkedList<>();
                }
                // 文件流
                String paramName = item.getFieldName();
                System.out.println("文件流名称！" + paramName);

                // 文件的名字
                String filename = item.getName();
                InputStream uploadFile = null;
                // 获取上传文件的文件流
                try {
                    uploadFile = item.getInputStream();
                } catch (IOException e) {
                    logger.error(e.getMessage() + "获取文件上传流失败！");
                }
                FileContainer container = new FileContainer();
                container.setFileName(filename);
                container.setFileInputStream(uploadFile);
                if (paramMap.containsKey(paramName)) {
                    map.put(paramName, container);
                    paramMap.remove(paramName);
                } else {
                    fileContainers.add(container);
                }
            }
        }
        // 封装其他文件流
        map.put("fileList", fileContainers);
        if (paramMap.size() > 0) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                map.put(ERROR, JsonResponse.errorMsg("缺少" + entry.getValue() + "数据，请确保数据完整！"));
            }
        }
        return map;
    }
}
