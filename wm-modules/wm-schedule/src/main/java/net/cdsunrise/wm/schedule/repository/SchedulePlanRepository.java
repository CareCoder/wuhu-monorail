package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.SchedulePlan;
import net.cdsunrise.wm.schedule.vo.SchedulePlanStatusModelVo;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/****
 * @author gechaoqing
 * 进度计划数据仓库
 */
public interface SchedulePlanRepository extends JpaRepository<SchedulePlan,Long> {


    /***
     * 根据父级任务ID获取直接子集任务
     * @param parentId 父级ID
     * @param name 任务名称
     * @return 子集列表
     */
    @Query("SELECT p FROM SchedulePlan p WHERE p.parentId=?1 AND p.status <>'DELETE' AND p.name=?2")
    List<SchedulePlan> findDirectChildren(Long parentId,String name);
    /***
     * 删除进度计划任务
     * @param parentId 进度计划ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT wsp.* FROM wm_schedule_plan wsp WHERE wsp.code LIKE (SELECT sp.code FROM wm_schedule_plan sp WHERE sp.id=?1 AND sp.category<>'MASTER' AND sp.status='NOT_START') AND wsp.status<>'DELETE'",nativeQuery = true)
    List<SchedulePlan> findToDelete(Long parentId);
    /***
     * 删除任务关联模型信息
     * @param schedulePlanId 任务ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "DELETE FROM wm_schedule_plan_ref_model WHERE schedule_plan_id=?1",nativeQuery = true)
    void deleteRefModelByPlanId(Long schedulePlanId);

    /***
     * 添加任务关联模型信息
     * @param schedulePlanId 任务表
     * @param fid FID
     * @param guid  GUID
     * @param pierCode 墩号
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "INSERT INTO wm_schedule_plan_ref_model(schedule_plan_id,fid,guid,pier_code) VALUES(?1,?2,?3,?4)",nativeQuery = true)
    void insertRefModel(Long schedulePlanId,Long fid,String guid,String pierCode);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "INSERT INTO wm_schedule_plan_ref_model(schedule_plan_id,model_id,pier_code) VALUES(?1,?2,?3)",nativeQuery = true)
    void insertRefModel(Long schedulePlanId,Long modelId,String pierCode);

    /***
     * 根据计划编号查找计划
     * @param code 计划编号
     * @return 计划数据
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.code=?1 AND p.status<>'DELETE' AND p.confirmStatus='OK'")
    SchedulePlan findByCode(String code);


    /***
     * 获取父级ID为空的进度计划
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.parentId IS NULL AND p.status<>'DELETE'")
    List<SchedulePlan> findByParentIdIsNull();

    /***
     * 根据父级ID获取进度计划列表
     * @param parentId 父级ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.parentId=?1 AND p.status<>'DELETE'")
    List<SchedulePlan> findByParentId(Long parentId);

    /***
     * 根据工点ID查询进度计划
     * @param workPointId 工点ID
     * @param category 进度任务类别 指导性/实施性
     * @return
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.workPointId=?1 AND p.category=?2 AND p.status<>'DELETE'")
    List<SchedulePlan> findByWorkPointIdAndCategory(Long workPointId,String category);

    /***
     * 根据工点ID查询进度计划
     * @param workPointId 工点ID
     * @param category 进度任务类别 指导性/实施性
     * @param deptIds 部门ID
     * @return
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.workPointId=?1 AND p.category=?2 AND p.status<>'DELETE' AND p.deptId IN ?3")
    List<SchedulePlan> findByWorkPointIdAndCategoryAndDeptIdIn(Long workPointId,String category,List<Long> deptIds);

    /***
     * 根据工点ID查询进度计划
     * @param workPointIdList 工点ID
     * @param category 进度任务类别 指导性/实施性
     * @return
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.workPointId IN :workPointIdList AND p.category = :category AND p.status<>'DELETE'")
    List<SchedulePlan> findByWorkPointIdInAndCategory(@Param("workPointIdList") List<Long> workPointIdList,@Param("category") String category);

    /***
     * 根据工点ID查询进度计划
     * @param workPointIdList 工点ID
     * @param category 进度任务类别 指导性/实施性
     * @param deptIds 部门ID
     * @return
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.workPointId IN :workPointIdList AND p.category = :category AND p.status<>'DELETE' AND p.deptId IN :deptIds")
    List<SchedulePlan> findByWorkPointIdInAndCategoryAndDeptIdIn(@Param("workPointIdList") List<Long> workPointIdList,@Param("category") String category,@Param("deptIds")List<Long> deptIds);

    /***
     * 获取计划子节点
     * @param parentId
     * @return
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.parentId IN ?1")
    List<SchedulePlan> findByParentIdList(List<Long> parentId);
    /***
     * 根据ID获取进度计划列表
     * 进行中和未开工的进度计划
     * @param ids 进度计划ID列表
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE (p.status='PROCESSING' OR p.status='NOT_START') AND p.confirmStatus='OK' AND p.id IN ?1")
    List<SchedulePlan> findByIdInForReport(Long[] ids);

    /***
     * 根据ID获取需要审核完成的进度计划列表
     * @param ids 进度计划ID列表
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status='AUDITING' AND p.confirmStatus='OK' AND p.id IN ?1")
    List<SchedulePlan> findByIdInForAuditComplete(Long[] ids);

    /***
     * 根据ID获取进度计划列表进行确认审核
     * @param ids 进度计划ID列表
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND (p.confirmStatus='NONE' OR p.confirmStatus='NOT_OK' ) AND p.id IN ?1")
    List<SchedulePlan> findByIdInForConfirm(Long[] ids);

    /***
     * 根据ID获取进度计划列表
     * @param idList 进度计划ID列表
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE (p.status<> 'DELETE' OR p.status IS NULL) AND p.id IN ?1")
    List<SchedulePlan> findByIdIn(List<Long> idList);

    /***
     * 查询当前部门所分配
     * @param deptId 部门ID
     * @return 任务列表
     */
    @Query("SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.deptId=?1")
    List<SchedulePlan> findByDeptId(Long deptId);

    /***
     * 查询我的部门的进度任务进行上报
     * @param workPointId 工点ID
     * @param deptId 部门ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE (p.status='PROCESSING' OR p.status='NOT_START') AND p.confirmStatus='OK' AND p.workPointId=?1 AND p.category = 'WEEK' AND p.deptId=?2")
    List<SchedulePlan> findForReport(Long workPointId,Long deptId);


    /***
     * 查询我的部门的进度任务进行上报
     * @param deptId 部门ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT p.* FROM wm_schedule_plan p WHERE (p.status='PROCESSING' OR p.status='NOT_START') AND p.confirm_status='OK' AND p.category = 'WEEK' AND p.dept_id=?1 ORDER BY p.complete_date limit 0,10",nativeQuery = true)
    List<SchedulePlan> findForReportTop10(Long deptId);

    /***
     * 根据类目获取进度计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory
     * @param category 类目 月/周计划
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.confirmStatus='OK' AND p.category=?1")
    List<SchedulePlan> findConfirmedByCategory(String category);

    /***
     * 获取分配给某个部门的任务列表
     * @param category 任务类别 年|月|周
     * @param deptId 部门ID
     * @return 计划任务列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.deptId=?2")
    List<SchedulePlan> findByCategoryAndDeptId(String category,Long deptId);


    /***
     * 根据类别和ID列表获取计划列表
     * @param category 类别 年|月|周
     * @param deptId 部门ID
     * @param parentId 任务父级ID
     * @return 计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.parentId=?2 AND p.deptId=?3")
    List<SchedulePlan> findByCategoryAndParentIdAndDeptId(String category,Long parentId,Long deptId);

    /***
     * 根据类别和ID列表获取计划列表
     * @param category 类别
     * @param deptId 部门ID
     * @param workPointId 工点ID
     * @return 计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.deptId=?2 AND p.workPointId=?3")
    List<SchedulePlan> findByCategoryAndDeptIdAndWorkPointId(String category,Long deptId,Long workPointId);

    /***
     * 根据类别和ID列表获取计划列表
     * @param category 类别
     * @param deptId 部门ID
     * @param workPointId 工点ID
     * @param parentId 父级ID
     * @return 计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.deptId=?2 AND p.workPointId=?3 AND p.parentId=?4")
    List<SchedulePlan> findByCategoryAndDeptIdAndWorkPointIdAndParentId(String category,Long deptId,Long workPointId,Long parentId);



    /***
     * 根据类目获取进度计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory
     * @param deptIds 部门ID
     * @param category 类目 月/周计划
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.deptId IN ?2")
    List<SchedulePlan> findAllByCategoryAndDeptIdIn(String category,List<Long> deptIds);


    /***
     * 根据类目获取进度计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory
     * @param deptIds 部门ID
     * @param category 类目 月/周计划
     * @param parentId 父级ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.parentId=?2 AND p.deptId IN ?3")
    List<SchedulePlan> findAllByCategoryAndParentIdAndDeptIdIn(String category,Long parentId,List<Long> deptIds);


    /***
     * 根据类目获取进度计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory
     * @param deptId 部门ID列表
     * @param category 类目 月/周计划
     * @param workPointId 工点ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.workPointId=?2 And p.deptId IN ?3")
    List<SchedulePlan> findAllByCategoryAndWorkPointIdAndDeptIdIn(String category,Long workPointId,List<Long> deptId);


    /***
     * 根据类目获取进度计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory
     * @param deptIds 部门ID
     * @param category 类目 月/周计划
     * @param workPointId 工点ID
     * @param parentId 父级ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.workPointId=?2 AND p.parentId=?3 AND p.deptId IN ?4")
    List<SchedulePlan> findAllByCategoryAndWorkPointIdAndParentIdAndDeptIdIn(String category,Long workPointId,Long parentId,List<Long> deptIds);

    /***
     * 获取前置任务列表
     * @param category 类别
     * @return 任务列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1")
    List<SchedulePlan> findByCategory(String category);

    /***
     * 根据类目查询除去指定ID的进度计划列表2
     * @param category 类目 月/周计划
     * @param planId 除去指定进度ID
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.category=?1 AND p.id<>?2")
    List<SchedulePlan> findForFront(String category,Long planId);

    /***
     * 获取需要审核的进度计划
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status='AUDITING' AND p.confirmStatus='OK' ")
    List<SchedulePlan> findForAudit();

    /***
     * 根据ID查询任务
     * @param id 任务ID
     * @return 任务数据
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE (p.status<>'DELETE' OR p.status IS NULL) AND p.id=?1")
    @Override
    SchedulePlan findOne(Long id);

    /***
     * 获取需要审核确认的进度计划
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<>'DELETE' AND p.confirmStatus='NONE'")
    List<SchedulePlan> findForConfirm();

    /***
     * 获取需要审核确认的进度计划
     * @param status 审核状态
     * @param category 类型
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<>'DELETE' AND p.category=?1 AND p.confirmStatus=?2")
    List<SchedulePlan> findForConfirm(String category,String status);

    /***
     * 获取需要审核确认的进度计划
     * @param category  计划类型
     * @param status 审核状态
     * @param createDate 计划创建时间
     * @return 进度计划列表
     */
    @Query(value = "SELECT p.* FROM wm_schedule_plan p WHERE p.status<>'DELETE' AND p.category=?1 AND p.confirm_status=?2 AND DATE_FORMAT(p.create_time,'%Y-%m')=?4",nativeQuery = true)
    List<SchedulePlan> findForConfirm(String category,String status,String createDate);


    /***
     * 获取需要审核确认的进度计划
     * @param category  计划类型
     * @param status 审核状态
     * @param departId 部门ID
     * @return 进度计划列表
     */
    @Query("SELECT p FROM SchedulePlan p WHERE p.status<>'DELETE' AND p.category=?1 AND p.confirmStatus=?2 AND p.deptId IN ?3")
    List<SchedulePlan> findForConfirm(String category,String status,List<Long> departId);

    /***
     * 获取需要审核确认的进度计划
     * @param category  计划类型
     * @param status 审核状态
     * @param departId 部门ID
     * @param createDate 计划创建时间
     * @return 进度计划列表
     */
    @Query(value = "SELECT p.* FROM wm_schedule_plan p WHERE p.status<>'DELETE' AND p.category=?1 AND p.confirm_status=?2 AND p.dept_id IN ?3 AND DATE_FORMAT(p.create_time,'%Y-%m')=?4",nativeQuery = true)
    List<SchedulePlan> findForConfirm(String category,String status,List<Long> departId,String createDate);

    /***
     * 提交草稿进行审核
     * @param ids 进度计划ID列表
     * @return 进度计划列表
     */
    @Query(value = "SELECT p FROM SchedulePlan p WHERE p.status<> 'DELETE' AND p.confirmStatus='DRAFT' AND p.id IN ?1")
    List<SchedulePlan> findByIdInForsubmitDraft(Long[] ids);

    /***
     * 根据计划完成日期获取进度计划列表
     * @param start 开始日期
     * @param end 结束日期
     * @return 进度计划列表
     */
    List<SchedulePlan> findByCompleteDateBetween(Date start,Date end);

    /***
     * 获取导入计划的父级计划
     * @param name 父级计划名称
     * @param workPointId 父级计划所在工点ID
     * @param category 类别
     * @return
     */
    @Query("SELECT p FROM SchedulePlan p WHERE p.status<>'DELETE' AND p.name=?1 AND p.workPointId=?2 AND p.category=?3")
    SchedulePlan findForImportParentId(String name,Long workPointId,String category);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("UPDATE SchedulePlan SET startDate=?1,completeDate=?2 WHERE id=?3")
    void updatePlanDate(Long id,Date start,Date end);
}
