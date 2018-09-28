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
import net.cdsunrise.wm.prophase.entity.PipelineTransform;
import net.cdsunrise.wm.prophase.service.PipelineTransformService;
import net.cdsunrise.wm.prophase.vo.PipelineTranSaveVo;
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
@Api("管线改移")
@RestController
@RequestMapping("/pipTran")
public class PipelineTranController {

    @Autowired
    private PipelineTransformService pipelineTransformService;

    /**
     * 管线改移Excel导出模板绝对路径
     */
    @Value("${GXGYExcelModelPath}")
    private String ExcelModelPath;

    @ApiOperation("增加/更新")
    @SavePath
    public void save(PipelineTranSaveVo pipelineTranSaveVo) {
        pipelineTransformService.save(pipelineTranSaveVo);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        pipelineTransformService.delete(id);
    }

    @ApiOperation("查询单个对象")
    @GetPath
    public PipelineTransform findOne(Long id) {
        PipelineTransform pipelineTransform = pipelineTransformService.findOne(id);
        return pipelineTransform;
    }

    @ApiOperation("分页")
    @PagePath
    public Pager<PipelineTransform> getPager(PageCondition condition) {
        Pager pager = pipelineTransformService.getPager(condition);
        return pager;
    }

    @ApiOperation(value = "管线改移Excel导出")
    @RequestMapping(value = "/PLTExport", method = RequestMethod.GET)
    public void ledgerExport(PipelineTransform queryVo, HttpServletResponse response) throws IOException {
        List<PipelineTransform> pipelineTransforms = pipelineTransformService.selectList(queryVo);
        PipelineTransform pipelineTransform;
        if (pipelineTransforms.size() > 0) {
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < pipelineTransforms.size(); i++) {
                pipelineTransform = pipelineTransforms.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                entityMap.put(0, i + 1);
                entityMap.put(1, pipelineTransform.getPipelineType());
                entityMap.put(2, pipelineTransform.getRelationship());
                entityMap.put(3, pipelineTransform.getPipe());
                entityMap.put(4, pipelineTransform.getSpecifications());
                entityMap.put(5, pipelineTransform.getPlanePosition());
                entityMap.put(6, pipelineTransform.getHandleReason());
                entityMap.put(7, pipelineTransform.getBuriedDepth());
                entityMap.put(8, pipelineTransform.getInfluenceLength());
                entityMap.put(9, pipelineTransform.getMeasuresProtect());
                entityMap.put(10, pipelineTransform.getSuspensionProtect());
                entityMap.put(11, pipelineTransform.getDismantle());
                entityMap.put(12, pipelineTransform.getShiftLength());
                entityMap.put(13, pipelineTransform.getPertShiftLength());
                entityMap.put(14, pipelineTransform.getPropertyCompany());
                entityMap.put(15, pipelineTransform.getBeginTime());
                entityMap.put(16,pipelineTransform.getEndTime());
                entityMap.put(17,pipelineTransform.getResponsibleUnit());
                entityMap.put(18,pipelineTransform.getPositionInformation());
                entityMap.put(19, pipelineTransform.getNote());
                list.add(entityMap);
            }
            String fileName = "管线改移数据-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            ExcelExport.exportByModel(response, fileName, list, ExcelModelPath, 2);
        }
    }

}
