package cn.fanchencloud.campus.controller.user;

import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.entity.PersonInfo;
import cn.fanchencloud.campus.model.FileContainer;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.PersonInfoService;
import cn.fanchencloud.campus.util.CommonStrings;
import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/18
 * Time: 11:43
 * Description: 用户信息控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/person")
public class PersonInfoController {
    
    private static final Logger logger = LoggerFactory.getLogger(PersonInfoController.class);

    /**
     * 注入个人信息服务层
     */
    private PersonInfoService personInfoService;

    /**
     * 请求修改用户信息
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/modifyUser")
    public String modifyUserInfo() {
        return "user/modifyUserInfo";
    }

//    /**
//     * 请求修改用户信息
//     *
//     * @return 页面跳转
//     */
//    @PostMapping(value = "/modifyUser")
//    @ResponseBody
//    public JsonResponse modifyUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
//        HashMap<String, String> paramMap = new HashMap<>(2);
//        paramMap.put("user", "用户信息");
//        paramMap.put("verifyCode", "验证码");
////        paramMap.put("thumbnail", "用户头像");
//        Map<String, Object> uploadMessage = ChenHttpUtils.doUploadMessage(request, paramMap);
//        String verifyCode = (String) uploadMessage.get("verifyCode");
//        if (uploadMessage.get(CommonStrings.ERROR) != null) {
//            return (JsonResponse) uploadMessage.get(CommonStrings.ERROR);
//        }
//        List<Object> list = CommonUtils.validateCode(verifyCode, request);
//        if (list.size() > 0) {
//            return (JsonResponse) list.get(0);
//        }
//        // 用户信息
//        String userMessage = (String) uploadMessage.get("user");
//        PersonInfo personInfo = JSON.parseObject(userMessage, PersonInfo.class);
//        // 缩略图
//        // 处理详情图片
//        List<FileContainer> fileContainerList = null;
//        FileContainer headImage = null;
//        if (uploadMessage.get(CommonStrings.FILE_LIST) instanceof List) {
//            fileContainerList = myCast(uploadMessage.get(CommonStrings.FILE_LIST));
//            headImage = fileContainerList.get(0);
//        }
//        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
//        personInfo.setId(user.getUserId());
//        return personInfoService.updateMessage(personInfo, headImage);
//    }

    /**
     * 请求修改用户信息
     *
     * @param user       用户信息
     * @param verifyCode 验证码
     * @param thumbnail  用户头像（可不选）
     * @return 修改结果
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    @PostMapping(value = "/modifyUser")
    @ResponseBody
    public JsonResponse modifyUserInfo(HttpServletRequest request,
                                       @RequestParam("user") String user,
                                       @RequestParam("verifyCode") String verifyCode,
                                       @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail) throws UnsupportedEncodingException {
        // 图片验证码为空
        if (StringUtils.isBlank(user)) {
            return JsonResponse.errorMsg("提交的用户信息为空!");
        }
        // 图片验证码为空
        if (StringUtils.isBlank(verifyCode)) {
            return JsonResponse.errorMsg("验证码不能为空!");
        } else {
            String cacheCode = (String) request.getSession().getAttribute(CommonStrings.VALIDATE_CODE);
            // 验证码错误
            if (!(cacheCode.toLowerCase().equals(verifyCode.toLowerCase()))) {
                return JsonResponse.errorMsg("验证码错误！");
            }
        }
        PersonInfo personInfo = JSON.parseObject(user, PersonInfo.class);
        // 从session中获取用户信息
        LocalAccount userSessionMessage = (LocalAccount) request.getSession().getAttribute("user");
        personInfo.setId(userSessionMessage.getUserId());
        FileContainer myHeadImage = null;
        if (thumbnail != null) {
            myHeadImage = new FileContainer();
            try {
                myHeadImage.setFileInputStream(thumbnail.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            myHeadImage.setFileName(thumbnail.getOriginalFilename());
        }
        // 将数据提交给用户信息修改服务层进行处理
        return personInfoService.updateMessage(personInfo, myHeadImage);
    }


    @SuppressWarnings("unchecked")
    private static <T> T myCast(Object obj) {
        return (T) obj;
    }

    @GetMapping(value = "/deleteHeadImage")
    @ResponseBody
    public JsonResponse deleteHeadImage(HttpServletRequest request) {
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        personInfoService.deleteHeadImage(user.getUserId());
        return JsonResponse.ok();
    }


    @Autowired
    public void setPersonInfoService(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }
}
