package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.ScheduleReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * @author gechaoqing
 * 进度上报数据仓库
 */
public interface ScheduleReportRepository extends JpaRepository<ScheduleReport,Long> {
    /***
     * 获取最后一次上报记录
     * @param schedulePlanId 进度计划ID
     * @return 上报记录
     */
    ScheduleReport findFirstBySchedulePlanIdOrderByCreateTimeDesc(Long schedulePlanId);

    /***
     * 获取上报记录列表
     * @param schedulePlanId 进度计划ID
     * @return
     */
    List<ScheduleReport> findBySchedulePlanIdOrderByCreateTimeDesc(Long schedulePlanId);

    /***
     * 根据进度ID列表获取上报记录
     * @param schedulePlanIdList 进度ID列表
     * @return 上报记录列表
     */
    List<ScheduleReport> findBySchedulePlanIdIn(List<Long> schedulePlanIdList);
}
