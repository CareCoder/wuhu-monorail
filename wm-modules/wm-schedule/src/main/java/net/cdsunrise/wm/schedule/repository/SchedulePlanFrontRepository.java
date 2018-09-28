package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.SchedulePlanFront;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * @author gechaoqing
 * 前置任务数据仓库
 */
public interface SchedulePlanFrontRepository extends JpaRepository<SchedulePlanFront,Long> {
    /***
     * 删除进度任务关联的前置关系
     * @param planId 进度计划ID
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE FROM wm_schedule_plan_ref_front WHERE schedule_id=?1",nativeQuery = true)
    void deleteRefFrontByPlanId(Long planId);

    /***
     * 查询进度任务的所有前置条件
     * @param scheduleId 进度任务ID
     * @return 前置任务列表
     */
    List<SchedulePlanFront> findByScheduleId(Long scheduleId);
}
