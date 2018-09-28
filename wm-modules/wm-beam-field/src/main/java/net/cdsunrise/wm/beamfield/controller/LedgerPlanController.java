package net.cdsunrise.wm.beamfield.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.util.ExcelExport;
import net.cdsunrise.wm.base.web.annotation.DeletePath;
import net.cdsunrise.wm.base.web.annotation.GetPath;
import net.cdsunrise.wm.base.web.annotation.ListPath;
import net.cdsunrise.wm.base.web.annotation.SavePath;
import net.cdsunrise.wm.beamfield.entity.LedgerPlan;
import net.cdsunrise.wm.beamfield.entity.LedgerPlanExaminePlan;
import net.cdsunrise.wm.beamfield.entity.RelatedDrawings;
import net.cdsunrise.wm.beamfield.vo.LedgePlanExamineVo;
import net.cdsunrise.wm.beamfield.vo.LedgerDemandStatistics;
import net.cdsunrise.wm.beamfield.vo.LedgerPlanVo;
import net.cdsunrise.wm.beamfield.service.LedgerPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Author : WangRui
 * Date : 2018/5/3
 * Describe :
 */
@Api("制梁计划")
@RestController
@RequestMapping("/ledgerPlan")
public class LedgerPlanController {

    @Autowired
    private LedgerPlanService ledgerPlanService;

    /**
     * 制梁计划Excel导出模板绝对路径
     */
    @Value("${PlanExcelModelPath}")
    private String ledgerPlanExcelModelPath;

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        ledgerPlanService.delete(id);
    }

    @ApiOperation("新增/修改制梁计划基础数据")
    @SavePath
    public void save(LedgerPlan ledgerPlan) {
        ledgerPlanService.save(ledgerPlan);
    }

    @ApiOperation("新增制梁计划2.0")
    @PostMapping("/addLedgerPlan")
    public void addLedgerPlan(List<LedgerPlan> ledgerPlanList) {
        ledgerPlanService.addLedgerPlan(ledgerPlanList);
    }

    @ApiOperation("条件查询")
    @GetMapping("/selectList")
    public Pager<LedgerPlan> selectList(LedgerPlanVo ledgerPlanVo, PageCondition condition) {
        return ledgerPlanService.selectList(ledgerPlanVo, condition);
    }

    @ApiOperation("分页列表")
    @ListPath
    public Pager<LedgerPlan> list(PageCondition condition) {
        return ledgerPlanService.getPager(condition);
    }

    @ApiOperation(value = "查询单个信息")
    @GetPath
    public LedgerPlan get(Long id) {
        return ledgerPlanService.findOne(id);
    }

    @ApiOperation(value = "Excel导出")
    @GetMapping(value = "/ledgerPlanExport")
    public void ledgerPlanExport(HttpServletResponse response) throws IOException {
        List<LedgerPlan> LedgerPlans = ledgerPlanService.findAll();
        LedgerPlan ledgerPlan;
        if (LedgerPlans.size() > 0) {
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < LedgerPlans.size(); i++) {
                ledgerPlan = LedgerPlans.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                entityMap.put(0, i + 1);
                entityMap.put(1, ledgerPlan.getBeamNumber());
                entityMap.put(2, ledgerPlan.getLine());
                entityMap.put(3, ledgerPlan.getLineInterval());
                entityMap.put(4, ledgerPlan.getLeftOrRightLine());
                entityMap.put(5, ledgerPlan.getBeamType());
                entityMap.put(6, ledgerPlan.getBeamSpan());
                entityMap.put(7, ledgerPlan.getStraightCurve());
                entityMap.put(8, ledgerPlan.getRadiusOfCurve());
                entityMap.put(9, ledgerPlan.getSteelBarType());
                entityMap.put(10, ledgerPlan.getSteelBarNumber());
                entityMap.put(11, ledgerPlan.getPvc());
                entityMap.put(12, ledgerPlan.getSupport());
                entityMap.put(13, ledgerPlan.getHangPointSteelPipe());
                entityMap.put(14, ledgerPlan.getBellowsSpecification());
                entityMap.put(15, ledgerPlan.getEndocardiumArea());
                entityMap.put(16, ledgerPlan.getReinEngBeamPedestal());
                entityMap.put(17, ledgerPlan.getSpecificationOfAnchoragePlate());
                entityMap.put(18, ledgerPlan.getAnchorPlatesNumber());
                entityMap.put(19, ledgerPlan.getApplicationOfBeamLength());
                entityMap.put(20, ledgerPlan.getContactRailChannelNumber());
                entityMap.put(21, ledgerPlan.getIsNeedFingerPlateSeat());
                entityMap.put(22, ledgerPlan.getAnchorageSpecification());
                entityMap.put(23, ledgerPlan.getAnchorageNumber());
                entityMap.put(24, ledgerPlan.getWireNumber());
                entityMap.put(25, ledgerPlan.getQuantityOfPressure());
                entityMap.put(26, ledgerPlan.getBeamStorageNumber());
                entityMap.put(27, ledgerPlan.getPedestalNumber());
                entityMap.put(28, ledgerPlan.getReinEngCplBindTime());
                entityMap.put(29, ledgerPlan.getFinalCompletionTime());
                entityMap.put(30, ledgerPlan.getPouringTime());
                entityMap.put(31, ledgerPlan.getPresTensGroutPulping());
                entityMap.put(32, ledgerPlan.getUseTime());
                entityMap.put(33, ledgerPlan.getStatus() == 1 ? "未开始制梁" : "已开始制梁");
                list.add(entityMap);
            }
            String fileName = "制梁计划-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            ExcelExport.exportByModel(response, fileName, list, ledgerPlanExcelModelPath, 3);
        }
    }

    @ApiOperation("每个月的用梁需求量")
    @GetMapping(value = "/demand")
    public List<LedgerDemandStatistics> demand() {
        return ledgerPlanService.demandStatistics(1);
    }

    @ApiOperation("用梁计划完成量与实际完成量")
    @GetMapping(value = "/ledgerPlanActual")
    public Map<String, List<LedgerDemandStatistics>> ledgerPlanActual() {
        Map<String, List<LedgerDemandStatistics>> map = new HashMap<>();
        map.put("plan", ledgerPlanService.demandStatistics(2));
        map.put("actual", ledgerPlanService.demandStatistics(3));
        return map;
    }

    @ApiOperation("开始制梁")
    @PostMapping("/beginMake")
    public void beginMake(Long id) {
        ledgerPlanService.beginMake(id);
    }

    @ApiOperation("审核通过1.0")
    @PostMapping("/examinePass")
    public Map<String, Integer> examinePass(Long id) {
        ledgerPlanService.examinePass(id);
        Map<String, Integer> map = new HashMap<>();
        map.put("status", 1);
        return map;
    }

    @ApiOperation("审核列表1.0")
    @PostMapping("/examineList")
    public List<LedgerPlanExaminePlan> examineList(Long ledgerPlanId) {
        return ledgerPlanService.examineList(ledgerPlanId);
    }

    @ApiOperation("新增参数2.0")
    @PostMapping("/ledgerPlanDrawing")
    public void ledgerPlanDrawing(LedgerPlanVo ledgerPlanVo) {
        ledgerPlanService.ledgerPlanDrawing(ledgerPlanVo);
    }

    @ApiOperation("查询是否已存在同样规格梁号图纸，value为0时表示没有图纸")
    @PostMapping("/isHaveSameDrawing")
    public Map<Integer, Integer> isHaveSameDrawing(LedgerPlanVo ledgerPlanVo) {
        return ledgerPlanService.isHaveSameDrawing(ledgerPlanVo);
    }

    @ApiOperation("查询图纸-PC端")
    @GetMapping("/queryDrawing")
    public String queryDrawing(RelatedDrawings relatedDrawings) {
        return ledgerPlanService.queryDrawing(relatedDrawings);
    }

    @ApiOperation("查询图纸-APP端")
    @GetMapping("/queryDrawingAPP")
    public String queryDrawingAPP(Long id, Integer processId) {
        return ledgerPlanService.queryDrawingAPP(id, processId);
    }

    @ApiOperation("工序审核2.0")
    @PostMapping("/processAudit")
    public void processAudit(Long id, Integer processId) {
        ledgerPlanService.processAudit(id, processId);
    }

    @ApiOperation("制梁计划审核2.0")
    @PostMapping("/ledgerExamine")
    public void ledgerExamine(LedgePlanExamineVo ledgePlanExamineVo) {
        ledgerPlanService.ledgerExamine(ledgePlanExamineVo);
    }
}
