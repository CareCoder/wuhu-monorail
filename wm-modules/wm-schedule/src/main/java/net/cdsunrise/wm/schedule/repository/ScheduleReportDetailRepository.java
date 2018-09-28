package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.ScheduleReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/****
 * @author gechaoqing
 * 进度上报明细数据仓库
 */
public interface ScheduleReportDetailRepository extends JpaRepository<ScheduleReportDetail,Long> {
    /***
     * 根据上报ID获取上报明细
     * @param reportId 上报ID
     * @return 上报明细列表
     */
    List<ScheduleReportDetail> findByReportId(Long reportId);

    /***
     * 根据上报ID集合获取上报明细列表
     * @param reportIdList 上报ID集合
     * @return 上报明细列表
     */
    List<ScheduleReportDetail> findByReportIdIn(List<Long> reportIdList);
}
