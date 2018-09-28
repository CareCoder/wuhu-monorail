package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.ScheduleAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * @author gechaoqing
 * 进度审核数据仓库
 */
public interface ScheduleAuditRepository extends JpaRepository<ScheduleAudit,Long> {

    /***
     * 根据进度ID获取审核历史记录
     * @param planId 进度ID
     * @return 历史记录列表
     */
    List<ScheduleAudit> findBySchedulePlanIdOrderByCreateTimeDesc(Long planId);

    /**
     * 根据进度ID集合获取审核历史记录
     * @param planIdList 进度ID集合
     * @param type 审核类型
     * @return 审核记录列表
     */
    List<ScheduleAudit> findBySchedulePlanIdInAndType(List<Long> planIdList,String type);
}
