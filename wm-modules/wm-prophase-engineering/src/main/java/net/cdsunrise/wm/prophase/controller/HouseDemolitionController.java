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
import net.cdsunrise.wm.prophase.entity.HDStatisticalData;
import net.cdsunrise.wm.prophase.entity.HouseDemolition;
import net.cdsunrise.wm.prophase.service.HouseDemolitionService;
import net.cdsunrise.wm.prophase.vo.HouseDemolitionSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api("房屋拆迁")
@RestController
@RequestMapping("/houseDemo")
public class HouseDemolitionController {

    @Autowired
    private HouseDemolitionService houseDemolitionService;

    /**
     * 台账Excel导出模板绝对路径
     */
    @Value("${FWCQExcelModelPath}")
    private String ExcelModelPath;

    @ApiOperation("增加/更新")
    @SavePath
    public void save(HouseDemolitionSaveVo saveVo) {
        houseDemolitionService.save(saveVo);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        houseDemolitionService.delete(id);
    }

    @ApiOperation("查询单个对象")
    @GetPath
    public HouseDemolition findOne(Long id) {
        HouseDemolition houseDemolition = houseDemolitionService.findOne(id);
        return houseDemolition;
    }

    @ApiOperation("分页")
    @PagePath
    public Pager<HouseDemolition> getPager(PageCondition condition) {
        Pager pager = houseDemolitionService.getPager(condition);
        return pager;
    }

    @ApiOperation(value = "房屋拆迁Excel导出")
    @RequestMapping(value = "/HDExport", method = RequestMethod.GET)
    public void ledgerExport(HouseDemolition queryVo, HttpServletResponse response) throws IOException {
        List<HouseDemolition> houseDemolitions = houseDemolitionService.selectList(queryVo);
        HouseDemolition houseDemolition;
        if (houseDemolitions.size() > 0) {
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < houseDemolitions.size(); i++) {
                houseDemolition = houseDemolitions.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                entityMap.put(0, i + 1);
                entityMap.put(1, houseDemolition.getRelationship());
                entityMap.put(2, houseDemolition.getDemolitionReason());
                entityMap.put(3, houseDemolition.getNature());
                entityMap.put(4, houseDemolition.getLayerNumber());
                entityMap.put(5, houseDemolition.getDemolitionArea());
                entityMap.put(6, houseDemolition.getDemolitionStatus());
                entityMap.put(7, houseDemolition.getIsConBuild());
                entityMap.put(8, houseDemolition.getOccupyTime());
                entityMap.put(9, houseDemolition.getDemolitionDifficulty());
                entityMap.put(10, houseDemolition.getPropertyRight());
                entityMap.put(11, houseDemolition.getUrgency());
                list.add(entityMap);
            }
            String fileName = "房屋拆迁数据-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            ExcelExport.exportByModel(response, fileName, list, ExcelModelPath, 2);
        }
    }

    @ApiOperation(value = "列表底部数据统计")
    @RequestMapping(value = "/dataStat",method = RequestMethod.GET)
    public HDStatisticalData dataStat() {
        return houseDemolitionService.dataStat();
    }
}
