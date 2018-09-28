package net.cdsunrise.wm.beamfield.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.GetPath;
import net.cdsunrise.wm.base.web.annotation.SavePath;
import net.cdsunrise.wm.beamfield.entity.LedgerPlanV2;
import net.cdsunrise.wm.beamfield.entity.RelatedDrawings;
import net.cdsunrise.wm.beamfield.service.LedgerPlanV2Service;
import net.cdsunrise.wm.beamfield.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
@RestController
@RequestMapping("/ledgerPlanV2")
public class LedgerPlanV2Controller {

    @Autowired
    private LedgerPlanV2Service ledgerPlanV2Service;

    @ApiOperation("新增")
    @SavePath
    public void save(LedgerPlanV2Vo ledgerPlanV2Vo) {
        ledgerPlanV2Service.save(ledgerPlanV2Vo);
    }

    @ApiOperation("下载")
    @GetPath
    public LedgerPlanV2 get(Long id) {
        return ledgerPlanV2Service.findOne(id);
    }

    @ApiOperation("未通过审核的制梁计划页面-分页")
    @GetMapping("/listOne")
    public Pager<LedgerPlanV2> getPagerOne(PageCondition condition) {
        return ledgerPlanV2Service.getPager(condition, 1);
    }

    @ApiOperation("未通过审核的制梁计划页面-条件查询")
    @GetMapping("/selectListOne")
    public Pager<LedgerPlanV2> selectListOne(LedgerPlanV2Vo ledgerPlanV2Vo, PageCondition condition) {
        return ledgerPlanV2Service.selectList(ledgerPlanV2Vo, condition, 1);
    }

    @ApiOperation("已通过审核的制梁计划页面-分页")
    @GetMapping("/listTwo")
    public Pager<LedgerPlanV2> getPagerTwo(PageCondition condition) {
        return ledgerPlanV2Service.getPager(condition, 2);
    }

    @ApiOperation("已通过审核的制梁计划页面-条件查询")
    @GetMapping("/selectListTwo")
    public Pager<LedgerPlanV2> selectListTwo(LedgerPlanV2Vo ledgerPlanV2Vo, PageCondition condition) {
        return ledgerPlanV2Service.selectList(ledgerPlanV2Vo, condition, 2);
    }

    @ApiOperation("制梁计划审核")
    @PostMapping("/ledgerExamine")
    public void ledgerExamine(LedgePlanExamineVoV2 ledgePlanExamineVoV2) {
        ledgerPlanV2Service.ledgerExamine(ledgePlanExamineVoV2);
    }

    @ApiOperation("查询是否已存在同样规格梁型和墩高的图纸，value为0时表示没有图纸")
    @PostMapping("/isHaveSameDrawing")
    public Map<Integer, Integer> isHaveSameDrawing(DrawingQueryVo drawingQueryVo) {
        return ledgerPlanV2Service.isHaveSameDrawing(drawingQueryVo);
    }

    @ApiOperation("查询图纸-PC端")
    @PostMapping("/queryDrawing")
    public String queryDrawing(RelatedDrawings relatedDrawings) {
        return ledgerPlanV2Service.queryDrawing(relatedDrawings);
    }

    @ApiOperation("查询所有图纸-APP端")
    @GetMapping("/queryDrawingAPP")
    public LedgerPlanAppQueryResult queryDrawingAPP(Long id) {
        return ledgerPlanV2Service.queryDrawingAPP(id);
    }

    @ApiOperation("制梁计划批量上传图片")
    @PostMapping("/ledgerPlanDrawing")
    public void ledgerPlanDrawing(LedgerPlanV2DrawingUploadVo ledgerPlanV2DrawingUploadVo) {
        ledgerPlanV2Service.ledgerPlanDrawing(ledgerPlanV2DrawingUploadVo);
    }

    @ApiOperation("工序审核")
    @PostMapping("/processAudit")
    public void processAudit(Long id, Integer processId, String isPass) {
        ledgerPlanV2Service.processAudit(id, processId, isPass);
    }

    @ApiOperation("工序审核2.0")
    @PostMapping("/processAudit2")
    public void processAudit2(ProcessInputVo processInputVo) {
        ledgerPlanV2Service.processAudit2(processInputVo);
    }

    @ApiOperation("工序审核查询数据")
    @PostMapping("/processAuditQuery")
    public ProcessAuditQueryVo processAuditQuery(Long id) {
        return ledgerPlanV2Service.processAuditQuery(id);
    }

    @ApiOperation("每个月的用梁需求量")
    @GetMapping(value = "/demand")
    public List<LedgerDemandStatistics> demand() {
        return ledgerPlanV2Service.demandStatistics(1);
    }

    @ApiOperation("用梁计划完成量与实际完成量")
    @GetMapping(value = "/ledgerPlanActual")
    public Map<String, List<LedgerDemandStatistics>> ledgerPlanActual() {
        Map<String, List<LedgerDemandStatistics>> map = new HashMap<>();
        map.put("plan", ledgerPlanV2Service.demandStatistics(2));
        map.put("actual", ledgerPlanV2Service.demandStatistics(3));
        return map;
    }

    @ApiOperation("Excel导出")
    @GetMapping("/ledgerPlanExport")
    public void ledgerPlanExport(HttpServletResponse response) {
        ledgerPlanV2Service.ledgerPlanExport(response);
    }

    @ApiOperation("根据墩号数组生成制梁计划")
    @PostMapping("/createLedgerPlanByPier")
    public void createLedgerPlanByPier(LedgerPlanV2VoV2 ledgerPlanV2VoV2) {
        ledgerPlanV2Service.createLedgerPlanByPier(ledgerPlanV2VoV2);
    }

    @ApiOperation("根据梁号list生成制梁计划")
    @PostMapping("/createLedgerPlanByBNList")
    public void createLedgerPlanByPier(@RequestBody List<CreateLedgerPlanVo> createLedgerPlanVoList) {
        ledgerPlanV2Service.createLedgerPlanByPier2(createLedgerPlanVoList);
    }

    @ApiOperation("删除某个指导图片")
    @GetMapping("/deleteImg")
    public void deleteImg(String beamType, String pierHeight, int picType) {
        ledgerPlanV2Service.deleteImg(beamType, pierHeight, picType);
    }

    @ApiOperation("判断用户类型")
    @GetMapping("/ledgerUserType")
    public Map<String, String> ledgerUserType() {
        return ledgerPlanV2Service.ledgerUserType();
    }

    @ApiOperation("开始制梁")
    @GetMapping("/beginMaking")
    public void beginMaking(Long id) {
        ledgerPlanV2Service.beginMaking(id);
    }
}
