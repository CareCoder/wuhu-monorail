package net.cdsunrise.wm.hiddentrouble.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.PagePath;
import net.cdsunrise.wm.hiddentrouble.bo.HiddenTroubleBo;
import net.cdsunrise.wm.hiddentrouble.bo.LevelSummaryBo;
import net.cdsunrise.wm.hiddentrouble.bo.StatusStatisticsBo;
import net.cdsunrise.wm.hiddentrouble.service.HiddenTroubleService;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleAcceptanceVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleCreateVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleQueryVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@Validated
@RestController
@RequestMapping("/hidden-trouble")
public class HiddenTroubleController {
    @Autowired
    private HiddenTroubleService hiddenTroubleService;

    /**
     * 创建隐患
     */
    @ApiOperation("创建隐患信息")
    @PostMapping("/create")
    public void create(HiddenTroubleCreateVo createVo) {
        hiddenTroubleService.create(createVo);
    }

    /**
     * 处理后上报
     *
     * @param reportVo
     */
    @ApiOperation("处理后上报")
    @PostMapping("/report")
    public void report(HiddenTroubleReportVo reportVo) {
        hiddenTroubleService.report(reportVo);
    }

    /**
     * 处理后验收
     *
     * @param acceptanceVo
     */
    @ApiOperation("处理后验收")
    @PostMapping("/acceptance")
    public void acceptance(HiddenTroubleAcceptanceVo acceptanceVo) {
        hiddenTroubleService.acceptance(acceptanceVo);
    }

    /**
     * 分页查询
     *
     * @param queryVo
     * @param condition
     * @return
     */
    @ApiOperation("分页查询")
    @PagePath
    public Pager<HiddenTroubleBo> getPager(HiddenTroubleQueryVo queryVo, PageCondition condition) {
        return hiddenTroubleService.getPager(queryVo, condition);
    }

    @ApiOperation("根据ID获取信息")
    @GetMapping("/get/{id}")
    public HiddenTroubleBo get(@PathVariable("id") Long id) {
        return hiddenTroubleService.get(id);
    }

    @ApiOperation("隐患排查处理情况/状态处理情况")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "workPointId", value = "工点名称", dataType = "Long", required = true),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "Date")
    })
    @GetMapping("status-statistics")
    public StatusStatisticsBo getStatusStatistics(Long workPointId,
                                                  Date startDate,
                                                  Date endDate) {
        return hiddenTroubleService.getStatusStatistics(workPointId, startDate, endDate);
    }

    /**
     * 隐患排查分级分类
     *
     * @param workPointId
     * @param startDate
     * @param endDate
     * @return
     */
    @ApiOperation("隐患排查分级统计")
    @GetMapping("level-summary")
    public List<LevelSummaryBo> getLevelSummary(@ApiParam("工点名称") Long workPointId,
                                                @ApiParam("开始时间") @NotNull(message = "开始时间不能为空") Date startDate,
                                                @ApiParam("结束时间") @NotNull(message = "结束时间不能为空") Date endDate) {
        return hiddenTroubleService.getLevelSummary(workPointId, startDate, endDate);
    }

    /**
     * 按等级统计
     *
     * @param workPointId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("level-statistics")
    public Map<String, Integer> getLevelStatistics(@ApiParam("工点名称") Long workPointId,
                                                   @ApiParam("开始时间") Date startDate,
                                                   @ApiParam("结束时间") Date endDate) {
        return hiddenTroubleService.getLevelStatistics(workPointId, startDate, endDate);
    }
}
