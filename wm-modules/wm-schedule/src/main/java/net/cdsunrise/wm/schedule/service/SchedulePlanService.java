package net.cdsunrise.wm.schedule.service;


import net.cdsunrise.wm.schedule.entity.SchedulePlan;
import net.cdsunrise.wm.schedule.entity.SchedulePlanFront;
import net.cdsunrise.wm.schedule.entity.SchedulePlanRefModel;
import net.cdsunrise.wm.schedule.enums.SchedulePlanStatus;
import net.cdsunrise.wm.schedule.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cdsunrise.net / LJ
 * @since 2018-02-07
 */
public interface SchedulePlanService{
    /***
     * 保存进度计划
     * @param saveVo 保存信息
     */
    void save(SchedulePlanSaveVo saveVo);

    /***
     * 保存前置条件
     * @param planId 进度计划ID
     * @param planFrontList 前置任务列表
     */
    void saveFront(Long planId,List<SchedulePlanFront> planFrontList);

    /***
     * 保存关联模型
     * @param planId 进度计划ID
     * @param refModelVoList 关联模型列表
     */
    void saveRefModel(Long planId,List<SchedulePlanRefModelVo> refModelVoList);
    /***
     * 提交任务进行计划审核
     * @param planIds 任务ID列表
     */
    void submitDraft(Long[] planIds);
    /***
     * 查询与自己有关的进度计划

     * @return 计划列表
     */
	List<SchedulePlan> queryMyTask();

    /***
     * 查询进度计划进行上报
     * @param workPointId 工点ID
     * @return 计划列表
     */
	List<SchedulePlan> queryMyTaskForReport(Long workPointId);

    /***
     * 查询全线我的需要上报的任务
     * @return 需要上报的任务列表
     */
	List<SchedulePlanForReportVo> queryMyTaskForReport();
    /***
     * 根据ID数组批量删除进度计划
     * @param ids ID数组
     */
    void deleteByIdArray(Long[] ids);

    /***
     * 查询重要时间节点，里程碑事件
     * @return 数据列表
     */
	List<AnalyzeCalendarVo> queryImportantPoint();

    /***
     * 根据工点/线路进度分析，查询任务完成进度百分比
     * @param id 工点/线路ID
     * @param type workPoint/line
     * @return 进度结果
     */
	AnalyzeCompletePercentVo queryCompletePercentAnalyze(Long id,String type);


    /***
     * 根据工点/线路进度分析，形象进度图
     * @param id 工点/线路ID
     * @param type workPoint/line
     * @return 形象进度数据列表
     */
	List<AnalyzeVisualizationScheduleVo> queryVisualizationAnalyze(Long id,String type);

    /***
     * 进度分析详细
     * @param id 工点/线路ID
     * @param type workPoint/line
     * @return 进度分析详细数据列表
     */
	List<AnalyzeScheduleDetailVo> queryScheduleDetailAnalyze(Long id,String type);

    /***
     * 进度分析详细
     * @param id 工点/线路ID
     * @param type workPoint/line
     * @return 进度分析详细数据列表
     */
    List<AnalyzeScheduleDetailVo> queryScheduleDetailAnalyzeV2(Long id,String type);

    /***
     * 进度延滞因素分析统计
     * @param id 工点/线路ID
     * @param type workPoint/line
     * @return 进度延滞原因分析数据列表
     */
	List<AnalyzeCommonCountVo> queryScheduleDelayReasonAnalyze(Long id,String type);

    /***
     * 进度预警分析统计
     * @param id 工点/线路ID
     * @param type workPoint/line
     * @return 进度预警分析数据列表
     */
    List<AnalyzeCommonCountVo> queryScheduleWarningAnalyze(Long id,String type);

    /***
     * 进度状态统计
     * @param id 工点/线路ID
     * @param type workPoint/line
     * @return 进度状态数据列表
     */
    List<AnalyzeCommonCountVo> queryScheduleStatusAnalyze(Long id,String type);

    /***
     * 查询工点所有任务相关模型进行状态分组高亮
     * @param workPintId 工点ID
     * @return 模型关联数据
     */
	Map<SchedulePlanStatus,List<SchedulePlanStatusModelVo>> queryStatusModels(Long workPintId);

    /***
     * 查询当前登录用户可以选择的前置任务
     * 同等级的任务
     * @param params 查询参数
     * @return 任务列表
     */
    List<SchedulePlan> queryFrontPlanList(QuerySchedulePlanFrontParams params);


    /****
     * 查询进度计划进行进度模拟
     * @return
     */
    List<SchedulePlanForMonitorVo> queryForMonitor();

    /***
     * 查询工点任务计划
     * @param workPointId 工点ID
     * @param category 类别
     * @return 任务计划列表
     */
    List<SchedulePlan> queryByWorkPoint(Long workPointId,String category);

    /***
     * 获取进度任务计划的前置任务列表
     * @param planId 进度计划ID
     * @return 前置任务列表
     */
    List<SchedulePlanFront> querySchedulePlanFront(Long planId);

    /***
     * 根据父级ID获取子集计划节点
     * @param parentId 父级ID
     * @return 进度计划子集列表
     */
    List<SchedulePlan> queryChildren(Long parentId);

    /***
     * 工程量快速计量
     * @param dateType 日期类型
     * @return 计量结果
     */
    List<QuantityMeasurementVo> quantityMeasurement(String dateType);

    /***
     * 进度计划导入
     * @param multipartFile 文件
     * @param category 计划类型 ['MASTER','YEAR','MONTH','WEEK']
     * @return 导入列表
     */
    List<SchedulePlanImportVo> importPlanData(MultipartFile multipartFile,String category);

    /***
     * 进度计划导入
     * @param workPointId 工点ID
     * @param multipartFile 文件
     * @param category 计划类型 ['MASTER','YEAR','MONTH','WEEK']
     * @return 导入列表
     */
    List<SchedulePlanImportVo> importWorkPointPlanData(MultipartFile multipartFile,Long workPointId,String category);

    /***
     * 进度计划导出
     * @param workPointId 工点ID
     * @param category 计划类型 ['MASTER','YEAR','MONTH','WEEK']
     * @param response 返回
     */
    void exportWorkPointPlanData(Long workPointId, String category,HttpServletResponse response);

    /***
     * 根据计划类别获取计划列表
     * @param category 类别
     * @param workPointId 工点ID
     * @param parentId 父级ID
     * @return 计划列表
     */
    List<SchedulePlan> queryMyTaskByCategoryAndWorkPoint(String category,Long workPointId,Long parentId);

    /***
     * 根据计划类别获取计划列表
     * @param category 类别
     * @param workPointId 工点ID
     * @param parentId 父级ID
     * @param forParent 是否是为查询父级任务
     * @return 计划列表
     */
    List<SchedulePlan> queryAllByCategoryAndWorkPoint(String category,Long workPointId,Long parentId,String forParent);

    /***
     * 获取所有已经关联的模型
     * @return 关联模型列表
     */
    List<SchedulePlanRefModelVo> getAllRefModels();

    /***
     * 根据ID获取进度计划详情
     * @param id ID
     * @return 进度计划详情
     */
    SchedulePlan getById(Long id);

    /***
     * 获取工点下的任务进行进度对比模拟
     * @param workPointId 工点ID
     * @return 进度列表
     */
    List<SchedulePlanCompareMonitorVo> queryPlansForCompareMonitor(Long workPointId);

    /***
     * 修改进度任务开始结束时间
     * @param id
     * @param start
     * @param end
     */
    void updatePlanDate(Long id, Date start,Date end);
}
