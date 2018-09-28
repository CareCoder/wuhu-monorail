package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.ScheduleReportRefDelayReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/***
 * @author gechaoqing
 * 进度上报关联延滞因素数据仓库
 */
public interface ScheduleReportRefDelayReasonRepository extends JpaRepository<ScheduleReportRefDelayReason,Long> {

    /***
     * 根据上报ID获取关联延滞因素名称
     * @param reportIds 上报ID列表
     * @return 延滞因素名称列表
     */
    @Query(value = "SELECT dr.name FROM wm_schedule_report_ref_delay_reason rdr LEFT JOIN wm_delay_reason dr ON rdr.delay_id=dr.id WHERE rdr.report_id IN ?1 AND rdr.delay_id IS NOT NULL",nativeQuery = true)
    List<String> findReasonNamesByReportIds(List<Long> reportIds);
}
