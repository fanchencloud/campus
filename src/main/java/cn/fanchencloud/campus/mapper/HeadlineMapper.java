package cn.fanchencloud.campus.mapper;

import cn.fanchencloud.campus.entity.Headline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/10
 * Time: 19:04
 * Description: 头条信息数据持久层
 *
 * @author chen
 */
@Mapper
public interface HeadlineMapper {

    /**
     * 查询头条数据
     *
     * @param number 查询数量
     * @param status 头条状态
     * @return 头条信息列表
     */
    List<Headline> queryHeadlineList(@Param("number") Integer number, @Param("status") Integer status);

    /**
     * 根据头条信息UUID获取该头条的图片
     *
     * @param uuid 唯一标志
     * @return 查询结果
     */
    Headline queryHeadlineImageByUUID(String uuid);

}
