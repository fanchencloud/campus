package cn.fanchencloud.campus.service;

import cn.fanchencloud.campus.entity.PersonInfo;
import cn.fanchencloud.campus.model.FileContainer;
import cn.fanchencloud.campus.model.JsonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 9:14
 * Description: 用户信息服务接口
 *
 * @author chen
 */
public interface PersonInfoService {

    /**
     * 根据 用户记录id 查询用户详细信息
     *
     * @param id 用户表记录id
     * @return 用户信息
     */
    PersonInfo getPersonInfo(int id);

    /**
     * 获取用户的头像文件
     *
     * @param userId 用户id
     * @return 用户头像文件
     */
    File getUserImage(Integer userId);

    /**
     * 删除用户头像
     *
     * @param userId 用户记录id
     */
    void deleteHeadImage(int userId);

    /**
     * 更新用户数据信息
     *
     * @param personInfo 用户信息
     * @param headImage  用户头像
     * @return 更新结果
     */
    JsonResponse updateMessage(PersonInfo personInfo, FileContainer headImage);
}
