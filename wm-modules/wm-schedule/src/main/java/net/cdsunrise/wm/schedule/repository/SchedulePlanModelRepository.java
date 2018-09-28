package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.SchedulePlanRefModel;
import net.cdsunrise.wm.schedule.vo.SchedulePlanRefModelVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/***
 * @author gechaoqing
 * 进度关联模型数据仓库
 */
public interface SchedulePlanModelRepository extends JpaRepository<SchedulePlanRefModel,Long> {
    /***
     * 根据进度任务ID获取关联模型数据
     * @param schedulePlanId 进度ID
     * @return 关联模型数据列表
     */
    @Query(value = "SELECT * FROM wm_schedule_plan_ref_model prf WHERE prf.schedule_plan_id=?1",nativeQuery = true)
    List<SchedulePlanRefModel> findBySchedulePlanId(Long schedulePlanId);

    /***
     * 根据进度任务ID获取关联模型数据
     * @param schedulePlanId 进度ID列表
     * @return 关联模型数据列表
     */
    @Query(value = "SELECT * FROM wm_schedule_plan_ref_model prf WHERE prf.schedule_plan_id IN ?1",nativeQuery = true)
    List<SchedulePlanRefModel> findBySchedulePlanIdIn(List<Long> schedulePlanId);


    /***
     * 根据进度任务ID获取还没有完成的关联模型数据
     * @param schedulePlanId 进度ID列表
     * @return 关联模型数据列表
     */
    @Query(value = "SELECT * FROM wm_schedule_plan_ref_model prf WHERE prf.status IS NULL AND prf.schedule_plan_id IN ?1",nativeQuery = true)
    List<SchedulePlanRefModel> findNotComplete(List<Long> schedulePlanId);

    /***
     * 查询已经上报了的模型
     * @return
     */
    @Query(value = "SELECT prf FROM SchedulePlanRefModel prf WHERE prf.status='COMPLETE'")
    List<SchedulePlanRefModel> findCompleted();

    /***
     * 进度上报模型完成
     * @param modelId 模型ID
     */
    @Modifying
    @Query("UPDATE SchedulePlanRefModel SET status='COMPLETE' WHERE modelId=?1")
    @Transactional(rollbackFor = Exception.class)
    void updateRefModelComplete(Long modelId);

    /***
     * 查询所有已经关联到进度计划的模型
     * @return 模型列表
     */
    /*@Query("SELECT new net.cdsunrise.wm.schedule.vo.SchedulePlanRefModelVo(rfm.fid,rfm.guid) FROM SchedulePlanRefModel rfm")
    List<SchedulePlanRefModelVo> getAllModelVo();*/

    /***
     * 查询所有已经关联到进度计划的模型
     * @return 模型列表
     */
    @Query("SELECT new net.cdsunrise.wm.schedule.vo.SchedulePlanRefModelVo(rfm.modelId) FROM SchedulePlanRefModel rfm")
    List<SchedulePlanRefModelVo> getAllModelVo();

    /***
     * 查询所有已经关联到进度计划的模型
     * @param planId  计划ID
     * @return 模型列表
     */
    @Query(value = "SELECT rfm.pier_code FROM wm_schedule_plan_ref_model rfm WHERE rfm.schedule_plan_id=?1 AND rfm.pier_code IS NOT NULL",nativeQuery = true)
    List<String> getRefModelPierCode(Long planId);

    /***
     * 根据查询关联模型
     * @param pierCodes
     * @return
     */
    @Query(value = "SELECT rfm FROM SchedulePlanRefModel rfm  WHERE rfm.pierCode IN ?1")
    List<SchedulePlanRefModel> getRefModelForLedgerPlan(Set<String> pierCodes);
}
