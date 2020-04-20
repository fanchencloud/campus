package cn.fanchencloud.campus.controller.admin;

import cn.fanchencloud.campus.entity.Area;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.AreaService;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/14
 * Time: 2:23
 * Description: 区域管理控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/area")
public class AreaManagerController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(AreaManagerController.class);

    /**
     * 区域服务
     */
    private AreaService areaService;

    /**
     * 请求跳转到区域管理页面
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/areaManagerPage")
    public ModelAndView areaManagerPage() {
        ModelAndView modelAndView = new ModelAndView("admin/areaManager");
        List<Area> areaList = areaService.getAreaList();
        modelAndView.addObject("areaList", areaList);
        return modelAndView;
    }

    @PostMapping(value = "/addAreaAdmin")
    public ModelAndView addAreaAdmin(@RequestParam("addAreaName") String addAreaName,
                                     @RequestParam("addAreaDescription") String addAreaDescription,
                                     @RequestParam("addRegionalWeight") int addRegionalWeight) {
        Area a = new Area();
        a.setAreaName(addAreaName);
        a.setAreaDescription(addAreaDescription);
        a.setPriority(addRegionalWeight);
        a.setCreateTime(new Date());
        a.setLastEditTime(new Date());
        ModelAndView modelAndView = new ModelAndView("admin/areaManager");
        if (areaService.addRecord(a)) {
            modelAndView.addObject("errormessage", "添加成功");
        } else {
            modelAndView.addObject("errormessage", "添加失败");
        }
        List<Area> areaList = areaService.getAreaList();
        modelAndView.addObject("areaList", areaList);
        return modelAndView;
    }

    @Autowired
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }
}
