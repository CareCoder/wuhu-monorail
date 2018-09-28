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
import net.cdsunrise.wm.prophase.entity.PeripheralRisk;
import net.cdsunrise.wm.prophase.service.PeripheralRiskService;
import net.cdsunrise.wm.prophase.vo.PeripheralRiskSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Api("周边风险")
@RestController
@RequestMapping("/pepRisk")
public class PeripheralRiskController {

    @Autowired
    private PeripheralRiskService peripheralRiskService;
    /**
     * 台账Excel导出模板绝对路径
     */
    @Value("${ZBFXExcelModelPath}")
    private String ExcelModelPath;

    @ApiOperation("增加/更新")
    @SavePath
    public void save(PeripheralRiskSaveVo peripheralRiskSaveVo) {
        peripheralRiskService.save(peripheralRiskSaveVo);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        peripheralRiskService.delete(id);
    }

    @ApiOperation("查询单个对象")
    @GetPath
    public PeripheralRisk findOne(Long id) {
        PeripheralRisk peripheralRisk = peripheralRiskService.findOne(id);
        return peripheralRisk;
    }

    @ApiOperation("分页")
    @PagePath
    public Pager<PeripheralRisk> getPager(PageCondition condition) {
        Pager pager = peripheralRiskService.getPager(condition);
        return pager;
    }

    @ApiOperation(value = "周边风险Excel导出")
    @RequestMapping(value = "/PPRExport", method = RequestMethod.GET)
    public void ledgerExport(PeripheralRisk queryVo, HttpServletResponse response) throws IOException {
        List<PeripheralRisk> peripheralRisks = peripheralRiskService.selectList(queryVo);
        PeripheralRisk peripheralRisk;
        if (peripheralRisks.size() > 0) {
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < peripheralRisks.size(); i++) {
                peripheralRisk = peripheralRisks.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                entityMap.put(0, i + 1);
                entityMap.put(1, peripheralRisk.getName());
                entityMap.put(2, peripheralRisk.getType());
                entityMap.put(3, peripheralRisk.getRiskLevel());
                entityMap.put(4, peripheralRisk.getPassStatus());
                entityMap.put(5, peripheralRisk.getPositionalRelation());
                entityMap.put(6, peripheralRisk.getSideLineDistance());
                entityMap.put(7, peripheralRisk.getVaultHorizonDis());
                entityMap.put(8, peripheralRisk.getDetailedDescription());
                entityMap.put(9, peripheralRisk.getBuildTime());
                entityMap.put(10, peripheralRisk.getPropertyCompany());
                entityMap.put(11, peripheralRisk.getStructuralStyle());
                entityMap.put(12, peripheralRisk.getLayerNumber());
                entityMap.put(13, peripheralRisk.getFloorElevation());
                entityMap.put(14, peripheralRisk.getFoundationType());
                entityMap.put(15, peripheralRisk.getSize());
                entityMap.put(16, peripheralRisk.getPileDiameter());
                entityMap.put(17, peripheralRisk.getPileNumber());
                entityMap.put(18, peripheralRisk.getStartMileage());
                entityMap.put(19, peripheralRisk.getEndMileage());
                entityMap.put(20, peripheralRisk.getBasicParameters());
                entityMap.put(21, peripheralRisk.getDLevel());
                entityMap.put(22, peripheralRisk.getPileNature());
                entityMap.put(23, peripheralRisk.getPileSize());
                entityMap.put(24, peripheralRisk.getPileWallThickness());
                entityMap.put(25, peripheralRisk.getPileMethod());
                entityMap.put(26, peripheralRisk.getAntiSeepageTret());
                entityMap.put(27, peripheralRisk.getVaultBuriedDepth());
                entityMap.put(28, peripheralRisk.getPassTime());
                entityMap.put(29, peripheralRisk.getSecurityState());
                entityMap.put(30, peripheralRisk.getEngineGeology());
                entityMap.put(31, peripheralRisk.getEngineMeasures());
                entityMap.put(32, peripheralRisk.getNote());
                list.add(entityMap);
            }
            String fileName = "周边风险数据-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            ExcelExport.exportByModel(response, fileName, list, ExcelModelPath, 2);
        }
    }
}
