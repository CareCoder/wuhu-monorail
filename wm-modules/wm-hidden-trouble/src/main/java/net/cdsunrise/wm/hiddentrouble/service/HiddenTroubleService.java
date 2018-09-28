package net.cdsunrise.wm.hiddentrouble.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.hiddentrouble.bo.HiddenTroubleBo;
import net.cdsunrise.wm.hiddentrouble.bo.LevelSummaryBo;
import net.cdsunrise.wm.hiddentrouble.bo.StatusStatisticsBo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleAcceptanceVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleCreateVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleQueryVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleReportVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
public interface HiddenTroubleService {
    /**
     * 创建隐患
     *
     * @param createVo
     */
    void create(HiddenTroubleCreateVo createVo);

    /**
     * 处理后上报
     *
     * @param reportVo
     */
    void report(HiddenTroubleReportVo reportVo);

    /**
     * 处理后验收
     *
     * @param acceptanceVo
     */
    void acceptance(HiddenTroubleAcceptanceVo acceptanceVo);

    /**
     * 隐患排查统计
     *
     * @param workPointId
     * @param startDate
     * @param endDate
     * @return
     */
    StatusStatisticsBo getStatusStatistics(Long workPointId, Date startDate, Date endDate);

    /**
     * 隐患排查分级统计
     *
     * @param workPointId
     * @param startDate
     * @param endDate
     * @return
     */
    List<LevelSummaryBo> getLevelSummary(Long workPointId, Date startDate, Date endDate);

    /**
     * 分页查询
     *
     * @param queryVo
     * @param condition
     * @return
     */
    Pager<HiddenTroubleBo> getPager(HiddenTroubleQueryVo queryVo, PageCondition condition);

    /**
     * 根据ID 查询
     *
     * @param id
     * @return
     */
    HiddenTroubleBo get(Long id);

    /**
     * 分级统计
     *
     * @param startDate
     * @param endDate
     * @param workPointId
     * @return
     */
    Map<String, Integer> getLevelStatistics(Long workPointId, Date startDate, Date endDate);
}
