package net.cdsunrise.wm.riskmanagement.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.exception.DataBaseRollBackException;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.riskmanagement.bo.ImageResourceBo;
import net.cdsunrise.wm.riskmanagement.bo.LevelClassificationSummaryBo;
import net.cdsunrise.wm.riskmanagement.bo.RiskStatisticsBo;
import net.cdsunrise.wm.riskmanagement.bo.TaskReportBo;
import net.cdsunrise.wm.riskmanagement.client.UserClient;
import net.cdsunrise.wm.riskmanagement.entity.ImageResource;
import net.cdsunrise.wm.riskmanagement.entity.InspectionTask;
import net.cdsunrise.wm.riskmanagement.entity.TaskReport;
import net.cdsunrise.wm.riskmanagement.enums.InspectionTaskStatus;
import net.cdsunrise.wm.riskmanagement.repository.InspectionTaskRepository;
import net.cdsunrise.wm.riskmanagement.service.ImageResourceService;
import net.cdsunrise.wm.riskmanagement.service.InspectionTaskService;
import net.cdsunrise.wm.riskmanagement.service.TaskReportService;
import net.cdsunrise.wm.riskmanagement.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
@Transactional(rollbackFor = {DataBaseRollBackException.class})
@Service
public class InspectionTaskServiceImpl implements InspectionTaskService {

    @Value("${imageUrlPrefix}")
    private String imageUrlPrefix;
    @Value("${spring.application.name}")
    private String serverName;
    @Autowired
    private InspectionTaskRepository inspectionTaskRepository;
    @Autowired
    private ImageResourceService imageResourceService;
    @Autowired
    private TaskReportService taskReportService;
    @Autowired
    private CommonDAO commonDAO;
    @Autowired
    private UserClient userClient;

    @Override
    public void create(TaskCreateSubmitVo createSubmitVo) {
        UserVo creator = userClient.get(UserUtils.getUserId());
        UserVo handler = userClient.get(createSubmitVo.getHandlerId());
        UserVo acceptancePerson = userClient.get(createSubmitVo.getAcceptancePersonId());
        InspectionTask task = new InspectionTask();
        BeanUtils.copyProperties(createSubmitVo, task);
        task.setCreateTime(new Date());
        task.setModifyTime(new Date());
        task.setCreatorId(creator.getId());
//        task.setWorkPointName(workPointName);
        task.setCreatorName(creator.getRealName());
        task.setCreateDeptId(creator.getDeptId());
        task.setHandlerName(handler.getRealName());
        task.setAcceptancePersonName(acceptancePerson.getRealName());
        task.setStatus(InspectionTaskStatus.UNDONE);
        List<ImageResource> imageResources = imageResourceService.upload(createSubmitVo.getImage());
        task.setScreenImages(imageResources);
        inspectionTaskRepository.save(task);
    }

    @Override
    public void report(TaskReportSubmitVo reportSubmitVo) {
        InspectionTask task = inspectionTaskRepository.findOne(reportSubmitVo.getTaskId());
        TaskReport report = new TaskReport();
        report.setTask(task);
        report.setExistsRisk(reportSubmitVo.isExistsRisk());
        report.setRemark(reportSubmitVo.getRemark());
        List<ImageResource> imageResources = imageResourceService.upload(reportSubmitVo.getImages());
        report.setReportImages(imageResources);
        report.setCreatorId(UserUtils.getUserId());
        report.setCreateTime(new Date());
        report.setModifyTime(new Date());
        taskReportService.save(report);
    }

    @Override
    public void acceptance(TaskAcceptanceSubmitVo acceptanceSubmitVo) {
        TaskReport report = taskReportService.findOne(acceptanceSubmitVo.getReportId());
        report.setAcceptanceMeasure(acceptanceSubmitVo.getMeasure());
        report.setAcceptanceTime(new Date());
        taskReportService.save(report);
    }

    @Override
    public InspectionTaskVo get(Long id) {
        InspectionTask task = inspectionTaskRepository.findOne(id);
        InspectionTaskVo vo = convertToVo(task);
        List<TaskReportBo> reportBos = taskReportService.findBoByTaskId(vo.getId());
        vo.setReports(reportBos);
        return vo;
    }

    @Override
    public List<InspectionTaskVo> findList(InspectionTaskQueryVo queryVo) {
        List<InspectionTask> tasks;
        if (queryVo.getRowCount() != null && queryVo.getRowCount() > 0) {
            PageCondition condition = new PageCondition();
            condition.setPageNum(0);
            condition.setPageSize(queryVo.getRowCount());
            condition.setOrder("create_time");
            condition.setOrderBy("desc");
            QueryHelper helper = getQueryHelper(queryVo)
                    .setPageCondition(condition);
            Pager<InspectionTask> taskPager = commonDAO.findPager(helper);
            tasks = taskPager.getContent();
        } else {
            QueryHelper helper = getQueryHelper(queryVo);
            tasks = commonDAO.findList(helper);
        }
        return convertToVos(tasks);
    }

    @Override
    public Pager<InspectionTaskVo> getPager(InspectionTaskQueryVo queryVo, PageCondition condition) {
        QueryHelper helper = getQueryHelper(queryVo)
                .setPageCondition(condition);
        Pager<InspectionTask> taskPager = commonDAO.findPager(helper);
        return new Pager<>(taskPager.getNumber()
                , taskPager.getTotalElements()
                , taskPager.getTotalElements()
                , convertToVos(taskPager.getContent()));
    }

    @Override
    public List<LevelClassificationSummaryBo> getLevelSummary(Date startDate, Date endDate, Long workPointId) {
        QueryHelper helper = new QueryHelper("\tdate_format( t.report_time, '%Y-%m' ) yearMonthStr,\n" +
                "\tifnull(sum(case when t.report_level = 1 THEN 1 ELSE 0 END),0) lv1,\n" +
                "\tifnull(sum(case when t.report_level = 2 THEN 1 ELSE 0 END),0) lv2,\n" +
                "\tifnull(sum(case when t.report_level = 3 THEN 1 ELSE 0 END),0) lv3,\n" +
                "\tifnull(sum(case when t.report_level = 4 THEN 1 ELSE 0 END),0) lv4,\n" +
                "\tifnull(sum(case when t.report_level = 5 THEN 1 ELSE 0 END),0) lv5",
                "wm_inspection_task t")
                .addCondition("t.report_level is not null")
                .addCondition(null != startDate, "t.report_time>=?", startDate)
                .addCondition(null != endDate, "t.report_time<=?", endDate)
                .addCondition(null != workPointId, "t.work_point_id = ?", workPointId)
                .addGroupBy("yearMonthStr")
                .addOrderProperty("yearMonthStr", Boolean.FALSE);
        List<LevelClassificationSummaryBo> statisticsBos = commonDAO.findList(helper, LevelClassificationSummaryBo.class);
        List<LevelClassificationSummaryBo> bos = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        while (startTime.getYear() <= endTime.getYear() && startTime.getMonth().getValue() <= endTime.getMonth().getValue()) {
            //当前循环的年月
            boolean has = false;
            String nym = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            if (statisticsBos != null && !statisticsBos.isEmpty()) {
                for (LevelClassificationSummaryBo b : statisticsBos) {
                    if (b.getYearMonthStr().equals(nym)) {
                        bos.add(b);
                        has = true;
                        break;
                    }
                }
            }
            if (!has) {
                LevelClassificationSummaryBo b = new LevelClassificationSummaryBo();
                b.setYearMonthStr(nym);
                bos.add(b);
            }
            //加一个月
            startTime = startTime.plusMonths(1);
        }
        return bos;
    }

    @Override
    public List<RiskStatisticsBo> getRiskStatistics(Date startDate, Date endDate, Long workPointId) {
        QueryHelper helper = new QueryHelper("r.source name, count(1) quantity", "wm_inspection_task t ")
                .addJoin(" join wm_task_risk tr  on t.id = tr.task_id")
                .addJoin(" join wm_risk_source r on r.id = tr.risk_id")
                .addCondition(startDate != null, "t.report_time>=?", startDate)
                .addCondition(endDate != null, "t.report_time<=?", endDate)
                .addCondition(null != workPointId, "t.work_point_id=?", workPointId)
                .addGroupBy("r.source")
                .addOrderProperty("r.source", Boolean.FALSE);
        List<RiskStatisticsBo> list = commonDAO.findList(helper, RiskStatisticsBo.class);
        return list;
    }

    @Override
    public Map<String, Integer> getLevelStatistics(Date startDate, Date endDate, Long workPointId) {
        QueryHelper helper = new QueryHelper("\tifnull(sum(case when t.report_level = 1 THEN 1 ELSE 0 END),0) lv1,\n" +
                "\tifnull(sum(case when t.report_level = 2 THEN 1 ELSE 0 END),0) lv2,\n" +
                "\tifnull(sum(case when t.report_level = 3 THEN 1 ELSE 0 END),0) lv3,\n" +
                "\tifnull(sum(case when t.report_level = 4 THEN 1 ELSE 0 END),0) lv4,\n" +
                "\tifnull(sum(case when t.report_level = 5 THEN 1 ELSE 0 END),0) lv5", "wm_inspection_task t ")
                .addCondition(startDate != null, "t.report_time>=?", startDate)
                .addCondition(endDate != null, "t.report_time<=?", endDate)
                .addCondition(null != workPointId, "t.work_point_id=?", workPointId);
        List<Map> list = commonDAO.findList(helper);
        Map<String, Integer> map = new HashMap<>(5);
        map.put("lv1", 0);
        map.put("lv2", 0);
        map.put("lv3", 0);
        map.put("lv4", 0);
        map.put("lv5", 0);
        if (list != null && list.size() > 0) {
            list.forEach(m -> {
                m.forEach((k, v) -> {
                    map.put(k.toString(), ((BigDecimal) v).intValue());
                });
            });
        }
        return map;
    }

    @Override
    public void delete(Long id) {
        imageResourceService.deleteByTaskId(id);
        taskReportService.deleteByTaskId(id);
        inspectionTaskRepository.delete(id);
    }

    private QueryHelper getQueryHelper(InspectionTaskQueryVo queryVo) {
        QueryHelper helper = new QueryHelper(InspectionTask.class, "t")
                .addCondition(queryVo.getCreatorId() != null, "t.creatorId=?", queryVo.getCreatorId())
                .addCondition(queryVo.getHandlerId() != null, "t.handlerId=?", queryVo.getHandlerId())
                .addCondition(queryVo.getStartDate() != null, "t.startDate>=?", queryVo.getStartDate())
                .addCondition(queryVo.getEndDate() != null, "t.endDate<=?", queryVo.getEndDate())
                .addOrderProperty("t.startDate", Boolean.FALSE)
                .useNativeSql(Boolean.FALSE);
        return helper;
    }

    /**
     * 转成列表
     *
     * @param tasks
     * @return
     */
    private List<InspectionTaskVo> convertToVos(List<InspectionTask> tasks) {
        List<InspectionTaskVo> vos = new ArrayList<>();
        if (!tasks.isEmpty()) {
            tasks.forEach(task ->
                    vos.add(convertToVo(task))
            );
        }
        return vos;
    }

    /**
     * 转换成VO 对象
     *
     * @param task
     * @return
     */
    private InspectionTaskVo convertToVo(InspectionTask task) {
        InspectionTaskVo vo = new InspectionTaskVo();
        BeanUtils.copyProperties(task, vo);
        List<TaskReportBo> reportBos = taskReportService.findBoByTaskId(task.getId());
        vo.setReports(reportBos);
        if (!task.getScreenImages().isEmpty()) {
            List<ImageResourceBo> imgBos = new ArrayList<>();
            for (ImageResource img : task.getScreenImages()) {
                ImageResourceBo imgBo = new ImageResourceBo();
                BeanUtils.copyProperties(img, imgBo);
                imgBo.setUrl("/" + serverName + imageUrlPrefix + imgBo.getNewName());
                imgBos.add(imgBo);
            }
            vo.setScreenImages(imgBos);
        }
        return vo;
    }
}
