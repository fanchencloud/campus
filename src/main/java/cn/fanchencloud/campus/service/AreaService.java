package cn.fanchencloud.campus.service;

import cn.fanchencloud.campus.entity.Area;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/12
 * Time: 3:48
 * Description: 区域信息服务类
 *
 * @author chen
 */
public interface AreaService {
    /**
     * 获取区域信息
     *
     * @return 区域信息列表
     */
    List<Area> getAreaList();

    /**
     * 获取区域列表信息
     *
     * @return 用于注册店铺使用的区域信息
     */
    List<Area> getRegisterAreaList();

    boolean addRecord(Area a);
}
