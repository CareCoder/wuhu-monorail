package net.cdsunrise.wm.prophase.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.util.ExcelExport;
import net.cdsunrise.wm.base.web.AjaxResult;
import net.cdsunrise.wm.base.web.annotation.DeletePath;
import net.cdsunrise.wm.base.web.annotation.GetPath;
import net.cdsunrise.wm.base.web.annotation.PagePath;
import net.cdsunrise.wm.base.web.annotation.SavePath;
import net.cdsunrise.wm.prophase.entity.TrafficDiversion;
import net.cdsunrise.wm.prophase.service.TrafficDiversionService;
import net.cdsunrise.wm.prophase.vo.LocationVo;
import net.cdsunrise.wm.prophase.vo.TrafficDiversionSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
@Api("交通导改")
@RestController
@RequestMapping("/trafficDiv")
public class TrafficDiversionController {

    @Autowired
    private TrafficDiversionService trafficDiversionService;

    @Value("${JTDGExcelModelPath}")
    private String ExcelModelPath;

    @ApiOperation("增加/修改")
    @SavePath
    public void save(TrafficDiversionSaveVo trafficDiversionSaveVo) {
        trafficDiversionService.save(trafficDiversionSaveVo);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        trafficDiversionService.delete(id);
    }

    @ApiOperation("查询单个信息")
    @GetPath
    public TrafficDiversion get(Long id) {
        TrafficDiversion trafficDiversion = trafficDiversionService.findOne(id);
        return trafficDiversion;
    }

    @ApiOperation("分页")
    @PagePath
    public Pager<TrafficDiversion> getPager(PageCondition condition) {
        Pager pager = trafficDiversionService.getPager(condition);
        return pager;
    }

    @ApiOperation("初始化查询所有需要显示的模型位置信息")
    @GetMapping("/allModelLocation")
    public List<LocationVo> allModelLocation() {
        return trafficDiversionService.allModelLocation();
    }


    @ApiOperation("交通导改Excel导出")
    @RequestMapping(value = "/TDExport", method = RequestMethod.GET)
    public void trafficDivExport(TrafficDiversion queryVo, HttpServletResponse response) throws IOException {
        List<TrafficDiversion> trafficDiversions = trafficDiversionService.selectList(queryVo);
        TrafficDiversion trafficDiversion;
        if (trafficDiversions.size() > 0) {
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < trafficDiversions.size(); i++) {
                trafficDiversion = trafficDiversions.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                entityMap.put(0, i + 1);
                entityMap.put(1, trafficDiversion.getStationPosition());
                entityMap.put(2, trafficDiversion.getSchedule());
                entityMap.put(3, trafficDiversion.getDLevel());
                entityMap.put(4, trafficDiversion.getSpeed());
                entityMap.put(5, trafficDiversion.getInfluenceScope());
                entityMap.put(6, trafficDiversion.getRedLine());
                entityMap.put(7, trafficDiversion.getRoadway());
                entityMap.put(8, trafficDiversion.getDriveway());
                entityMap.put(9, trafficDiversion.getNotDriveway());
                entityMap.put(10, trafficDiversion.getGreenBeltWidth());
                entityMap.put(11, trafficDiversion.getRelation());
                entityMap.put(12, trafficDiversion.getDiversionReason());
                entityMap.put(13, trafficDiversion.getTransitDescribe());
                entityMap.put(14, trafficDiversion.getVehicleWidth());
                entityMap.put(15, trafficDiversion.getVehicleNumber());
                entityMap.put(16, trafficDiversion.getBlendWidth());
                entityMap.put(17, trafficDiversion.getBlendNumber());
                entityMap.put(18, trafficDiversion.getMinTurnRadius());
                entityMap.put(19, trafficDiversion.getPfArea());
                entityMap.put(20, trafficDiversion.getStoneLength());
                entityMap.put(21, trafficDiversion.getTreePoolNumber());
                entityMap.put(22, trafficDiversion.getBlindRoadArea());
                entityMap.put(23, trafficDiversion.getBuildArea());
                entityMap.put(24, trafficDiversion.getDiversionTime());
                entityMap.put(25, trafficDiversion.getDiversionDifficulty());
                entityMap.put(26, trafficDiversion.getBeginTime());
                entityMap.put(27,trafficDiversion.getEndTime());
                entityMap.put(28,trafficDiversion.getResponsibleUnit());
                entityMap.put(29,trafficDiversion.getPositionInformation());
                entityMap.put(30, trafficDiversion.getNote());
                list.add(entityMap);
            }
            String fileName = "交通导改数据-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            ExcelExport.exportByModel(response, fileName, list, ExcelModelPath, 2);
        }
    }
}
