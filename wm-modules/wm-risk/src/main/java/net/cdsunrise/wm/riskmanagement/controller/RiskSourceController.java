package net.cdsunrise.wm.riskmanagement.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.ListPath;
import net.cdsunrise.wm.base.web.annotation.PagePath;
import net.cdsunrise.wm.riskmanagement.bo.RiskSourceLevelSummaryBo;
import net.cdsunrise.wm.riskmanagement.entity.RiskSource;
import net.cdsunrise.wm.riskmanagement.service.RiskSourceService;
import net.cdsunrise.wm.riskmanagement.vo.RiskSourceQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
@Api("风险")
@RestController
@RequestMapping("/risk-source")
public class RiskSourceController {
    @Autowired
    private RiskSourceService riskSourceService;

    @ApiOperation("列表查询")
    @ListPath
    public List<RiskSource> getList(RiskSourceQueryVo queryVo) {
        return riskSourceService.getList(queryVo);
    }

    @ApiOperation("分页查询")
    @PagePath
    public Pager<RiskSource> getPager(RiskSourceQueryVo queryVo, PageCondition condition) {
        Pager<RiskSource> pager = riskSourceService.getPager(queryVo, condition);
        return pager;
    }

    /**
     * [
     * {
     * 风险名称: xxx
     * 等级1：3
     * 等级2：2
     * }
     * ]
     *
     * @param workPointName
     * @return
     */
    @ApiOperation("风险源等级统计")
    @GetMapping("/level-summary")
    public List<RiskSourceLevelSummaryBo> getLevelSummary(String workPointName) {
        return riskSourceService.getLevelSummary(workPointName);
    }

    @ApiOperation("风险源分类汇总")
    @GetMapping("/level-statistics")
    public Map<String, Integer> getLevelStatistics(String workPointName) {
        return riskSourceService.getLevelStatistics(workPointName);
    }
}
