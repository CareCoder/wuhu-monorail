package net.cdsunrise.wm.riskmanagement.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.riskmanagement.bo.LevelClassificationSummaryBo;
import net.cdsunrise.wm.riskmanagement.bo.RiskStatisticsBo;
import net.cdsunrise.wm.riskmanagement.entity.InspectionTask;
import net.cdsunrise.wm.riskmanagement.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
public interface InspectionTaskService {
    /**
     * 创建任务
     *
     * @param createSubmitVo
     */
    void create(TaskCreateSubmitVo createSubmitVo);

    /**
     * 任务上报
     *
     * @param reportSubmitVo
     */
    void report(TaskReportSubmitVo reportSubmitVo);

    /**
     * 任务验收
     *
     * @param acceptanceSubmitVo
     */
    void acceptance(TaskAcceptanceSubmitVo acceptanceSubmitVo);

    /**
     * 查询任务详细信息
     *
     * @param id
     */
    InspectionTaskVo get(Long id);

    /**
     * 查询列表
     *
     * @param queryVo
     * @return
     */
    List<InspectionTaskVo> findList(InspectionTaskQueryVo queryVo);

    /**
     * 分页查询
     *
     * @param queryVo
     * @param condition
     * @return
     */
    Pager<InspectionTaskVo> getPager(InspectionTaskQueryVo queryVo, PageCondition condition);

    /**
     * 按风险等级统计汇总
     *
     * @param startDate
     * @param endDate
     * @param workPointId
     * @return
     */
    List<LevelClassificationSummaryBo> getLevelSummary(Date startDate, Date endDate, Long workPointId);

    /**
     * 根据工点查询 风险类型数量
     *
     * @param startDate
     * @param endDate
     * @param workPointId
     * @return
     */
    List<RiskStatisticsBo> getRiskStatistics(Date startDate, Date endDate, Long workPointId);

    /**
     * 三类预警统计
     *
     * @param startDate
     * @param endDate
     * @param workPointId
     * @return
     */
    Map<String, Integer> getLevelStatistics(Date startDate, Date endDate, Long workPointId);

    void delete(Long id);
}
