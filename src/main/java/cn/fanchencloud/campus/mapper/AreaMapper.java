package cn.fanchencloud.campus.mapper;


import cn.fanchencloud.campus.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/12
 * Time: 0:27
 * Description: 区域数据持久化操作
 *
 * @author chen
 */
@Mapper
public interface AreaMapper {
    /**
     * 查询所有的区域信息
     *
     * @return 区域列表
     */
    List<Area> queryAllArea();

    /**
     * 获取注册的区域列表
     *
     * @return 区域列表
     */
    List<Area> getRegisterAreaList();
}
