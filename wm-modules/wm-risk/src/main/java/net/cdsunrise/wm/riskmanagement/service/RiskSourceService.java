package net.cdsunrise.wm.riskmanagement.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.riskmanagement.bo.RiskSourceLevelSummaryBo;
import net.cdsunrise.wm.riskmanagement.entity.RiskSource;
import net.cdsunrise.wm.riskmanagement.vo.RiskSourceQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
public interface RiskSourceService {
    /**
     * 分页查询
     *
     * @param queryVo
     * @param condition
     * @return
     */
    Pager<RiskSource> getPager(RiskSourceQueryVo queryVo, PageCondition condition);

    /**
     * 列表查询
     *
     * @param queryVo
     * @return
     */
    List<RiskSource> getList(RiskSourceQueryVo queryVo);

    List<RiskSourceLevelSummaryBo> getLevelSummary(String workPointName);

    Map<String,Integer> getLevelStatistics(String workPointName);
}
