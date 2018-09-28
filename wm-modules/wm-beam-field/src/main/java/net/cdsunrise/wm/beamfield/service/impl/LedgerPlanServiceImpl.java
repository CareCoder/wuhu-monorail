package net.cdsunrise.wm.beamfield.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.util.FileUtils;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.beamfield.client.SystemClient;
import net.cdsunrise.wm.beamfield.entity.LedgerPlan;
import net.cdsunrise.wm.beamfield.entity.LedgerPlanExaminePlan;
import net.cdsunrise.wm.beamfield.entity.RelatedDrawings;
import net.cdsunrise.wm.beamfield.repository.LedgerPlanExaminePlanRepository;
import net.cdsunrise.wm.beamfield.repository.LedgerPlanRepository;
import net.cdsunrise.wm.beamfield.repository.RelatedDrawingsRepository;
import net.cdsunrise.wm.beamfield.service.LedgerPlanService;
import net.cdsunrise.wm.beamfield.vo.LedgePlanExamineVo;
import net.cdsunrise.wm.beamfield.vo.LedgerDemandStatistics;
import net.cdsunrise.wm.beamfield.vo.LedgerPlanVo;
import net.cdsunrise.wm.beamfield.vo.ResourceVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author : WangRui
 * Date : 2018/4/23
 * Describe :
 */
@Service
public class LedgerPlanServiceImpl implements LedgerPlanService {

    @Autowired
    private LedgerPlanRepository ledgerPlanRepository;

    @Autowired
    private LedgerPlanExaminePlanRepository ledgerPlanExaminePlanRepository;

    @Autowired
    private RelatedDrawingsRepository relatedDrawingsRepository;

    @Autowired
    private SystemClient systemClient;

    @Autowired
    private CommonDAO commonDAO;

    /**
     * 图片上传路径
     */
    @Value("${uploadPath}")
    private String uploadPath;

    @Value("${accessPath}")
    private String accessPath;

    @Override
    public void save(LedgerPlan ledgerPlan) {
        if (ledgerPlan.getId() != null) {//制梁计划基础数据修改
            LedgerPlan ledgerPlan1 = findOne(ledgerPlan.getId());
            BeanUtils.copyProperties(ledgerPlan, ledgerPlan1, "beamNumber", "line", "lineInterval", "leftOrRightLine", "structureType", "beamSpan", "straightCurve", "status", "useTime");
            ledgerPlanRepository.save(ledgerPlan1);
        } else {//新增制梁计划，判断数据库是否有此线路梁号的记录，有则更新，无则新增
            LedgerPlan exist = ledgerPlanRepository.findByBeamNumberAndLine(ledgerPlan.getBeamNumber(), ledgerPlan.getLine());
            if (exist != null) {//有此线路梁号记录，则更新
                exist.setBeamNumber(ledgerPlan.getBeamNumber());
                exist.setLine(ledgerPlan.getLine());
                exist.setLineInterval(ledgerPlan.getLineInterval());
                exist.setLeftOrRightLine(ledgerPlan.getLeftOrRightLine());
                exist.setBeamType(ledgerPlan.getBeamType());
                exist.setBeamSpan(ledgerPlan.getBeamSpan());
                exist.setStraightCurve(ledgerPlan.getStraightCurve());
                exist.setUseTime(ledgerPlan.getUseTime());
                ledgerPlanRepository.save(exist);
            } else {//无记录
                ledgerPlan.setStatus(1);
                ledgerPlanRepository.save(ledgerPlan);
            }
        }
    }

    @Override
    public LedgerPlan findOne(Long id) {
        return ledgerPlanRepository.findOne(id);
    }

    @Override
    public Pager<LedgerPlan> getPager(PageCondition condition) {
        QueryHelper helper;
        helper = new QueryHelper(LedgerPlan.class, "l")
                .addCondition("planExamineStatus in" + judgeUserType())
                .addOrderProperty("planExamineStatus", true)
                .setPageCondition(condition)
                .useNativeSql(false);
        Pager<LedgerPlan> pager = commonDAO.findPager(helper);
        return pager;
    }

    @Override
    public List<LedgerPlan> findAll() {
        return ledgerPlanRepository.findAll();
    }

    @Override
    public Pager<LedgerPlan> selectList(LedgerPlanVo ledgerPlanVo, PageCondition condition) {
        QueryHelper helper = new QueryHelper(LedgerPlan.class)
                .addCondition("status <> 0")
                .addCondition("planExamineStatus in" + judgeUserType())
                .addCondition(StringUtils.isNotBlank(ledgerPlanVo.getBeamNumber()), " beamNumber like ?", "%" + ledgerPlanVo.getBeamNumber() + "%")
                .addCondition(StringUtils.isNotBlank(ledgerPlanVo.getLine()), " line like ?", "%" + ledgerPlanVo.getLine() + "%")
                .addCondition(StringUtils.isNotBlank(ledgerPlanVo.getLineInterval()), "lineInterval like ?", "%" + ledgerPlanVo.getLineInterval() + "%")
                .addCondition(ledgerPlanVo.getUseTime() != null, "DATE_FORMAT(useTime,'%Y-%m') = DATE_FORMAT(?,'%Y-%m')", ledgerPlanVo.getUseTime())
                .addOrderProperty("planExamineStatus", true)
                .setPageCondition(condition)
                .useNativeSql(false);
        Pager<LedgerPlan> pager = commonDAO.findPager(helper);
        return pager;
    }

    /**
     * queryType:1 在制梁计划表根据每条数据的用梁时间分月查询每个月的用梁需求量，月度汇总
     * queryType:2 在制梁计划表根据每条数据的用梁时间分月查询每个月的累加用梁需求量，月度累加
     * queryType:3 在制梁计划审核表根据每条数据的最后一个工序审核完成的时间查询每个月的累加实际生产梁，月度累加
     *
     * @return
     */
    @Override
    public List<LedgerDemandStatistics> demandStatistics(int queryType) {
        List<LedgerDemandStatistics> ledgerDemandStatisticsList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (int i = 1; i <= 12; i++) {
            ledgerDemandStatisticsList.add(demandStatistics(queryType, i, year));
        }
        return ledgerDemandStatisticsList;
    }

    public LedgerDemandStatistics demandStatistics(int queryType, int month, int year) {
        QueryHelper helper;
        switch (queryType) {
            case 1:
                helper = new QueryHelper("l.month,l.number",
                        "(select CONCAT('" + year + "-','" + month + "') as month,count(*) as number from wm_leger_plan where MONTH(use_time) = " + month + " and YEAR(use_time)=" + year + ") as l")
                        .useNativeSql(true);
                break;
            case 2:
                helper = new QueryHelper("l.month,l.number",
                        "(select CONCAT('" + year + "-','" + month + "') as month,count(*) as number from wm_leger_plan where MONTH(use_time) <= " + month + " and YEAR(use_time)=" + year + ") as l")
                        .useNativeSql(true);
                break;
            default:
                helper = new QueryHelper("l.month,l.number",
                        "(select CONCAT('" + year + "-','" + month + "') as month,count(*) as number from wm_leger_plan_examine where process_number = 7 and examine_status=1 and MONTH(examine_time) <= " + month + " and YEAR(examine_time)=" + year + ") as l")
                        .useNativeSql(true);
                break;
        }
        List<LedgerDemandStatistics> list = commonDAO.findList(helper, LedgerDemandStatistics.class);
        return list.get(0);
    }

    /**
     * 用户点击开始制梁,在制梁计划审核表插入对应梁号的7个工序的待审核数据
     *
     * @param id
     */
    @Override
    public void beginMake(Long id) {
        LedgerPlan ledgerPlan = ledgerPlanRepository.findOne(id);
        ledgerPlan.setStatus(2);
        ledgerPlanRepository.save(ledgerPlan);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        List<LedgerPlanExaminePlan> ledgerPlanExaminePlanList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            LedgerPlanExaminePlan ledgerPlanExaminePlan = new LedgerPlanExaminePlan();
            ledgerPlanExaminePlan.setLedgerPlanId(id);
            ledgerPlanExaminePlan.setProcessNumber(i);
            ledgerPlanExaminePlan.setExamineStatus(0);
            String processName;
            int hour = 0;
            switch (i) {
                case 1:
                    hour = 2;
                    processName = "模板打磨";
                    break;
                case 2:
                    hour = 3;
                    processName = "涂模板漆";
                    break;
                case 3:
                    hour = 5;
                    processName = "合模";
                    break;
                case 4:
                    hour = 8;
                    processName = "线型条、梁长及梁宽调整";
                    break;
                case 5:
                    hour = 11;
                    processName = "入模测量验收";
                    break;
                case 6:
                    hour = 35;
                    processName = "浇筑等强";
                    break;
                default:
                    hour = 36;
                    processName = "拆模";
                    break;
            }
            calendar.add(Calendar.HOUR, hour); //小时加减
            ledgerPlanExaminePlan.setExamineTime(sdf.format(calendar.getTime()));
            ledgerPlanExaminePlan.setProcessName(processName);
            ledgerPlanExaminePlanList.add(ledgerPlanExaminePlan);
        }
        ledgerPlanExaminePlanRepository.save(ledgerPlanExaminePlanList);
    }

    /**
     * 用户点击审核通过，根据制梁计划id更新其每个工序的完成时间，预计审核时间，审核状态，1模板打磨 2涂模板漆 3合模 4线型条、梁长及梁宽调整 5入模测量验收 6浇筑等强 7拆模
     *
     * @param id
     */
    @Override
    public void examinePass(Long id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String name = systemClient.fetchUserNameByUserId(UserUtils.getUserId()).getUsername();
        LedgerPlanExaminePlan result = ledgerPlanExaminePlanRepository.findOne(id);
        Integer currentProcessNumber = result.getProcessNumber();
        Long ledgerPlanId = result.getLedgerPlanId();
        LedgerPlanExaminePlan saveEntity;
        for (int i = 1; i <= 7; i++) {
            saveEntity = ledgerPlanExaminePlanRepository.findByLedgerPlanIdAndProcessNumber(ledgerPlanId, i);
            if (saveEntity.getExamineStatus() != 0) {
                continue;
            } else {
                //更新当前工序及之前工序的审核状态、审核时间、审核人
                if (i <= currentProcessNumber) {
                    saveEntity.setExamineStatus(1);
                    saveEntity.setExamineTime(sdf.format(calendar.getTime()));
                    saveEntity.setExamineEmp(name);
                } else {
                    //更新当前工序之后工序的预计审核时间
                    int hour = 0;
                    switch (saveEntity.getProcessNumber()) {
                        case 2:
                            hour += 1;
                            break;
                        case 3:
                            hour += 2;
                            break;
                        case 4:
                            hour += 3;
                            break;
                        case 5:
                            hour += 3;
                            break;
                        case 6:
                            hour += 24;
                            break;
                        default:
                            hour += 1;
                            break;
                    }
                    calendar.add(Calendar.HOUR, hour); //小时加减
                    saveEntity.setExamineTime(sdf.format(calendar.getTime()));
                }
                ledgerPlanExaminePlanRepository.save(saveEntity);
            }
        }
    }

    @Override
    public List<LedgerPlanExaminePlan> examineList(Long id) {
        return ledgerPlanExaminePlanRepository.findByLedgerPlanId(id);
    }

    /**
     * 删除制梁计划，没有开始制梁的计划才能删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        LedgerPlan ledgerPlan = ledgerPlanRepository.findOne(id);
        if (ledgerPlan.getStatus() == 1) {
            ledgerPlanRepository.delete(id);
        }
    }

    /**
     * 新增参数，上传工法指导书图片
     *
     * @param ledgerPlanVo
     */
    @Override
    public void ledgerPlanDrawing(LedgerPlanVo ledgerPlanVo) {
        LedgerPlan ledgerPlan = ledgerPlanRepository.findOne(ledgerPlanVo.getId());
        ledgerPlan.setBeamType(ledgerPlanVo.getBeamType());
        ledgerPlan.setBeamLength(ledgerPlanVo.getBeamLength());
        ledgerPlan.setPierHeight(ledgerPlanVo.getPierHeight());
        ledgerPlan.setRadiusOfCurve(ledgerPlanVo.getRadiusOfCurve());
        ledgerPlan.setNote(ledgerPlanVo.getNote());
        ledgerPlanRepository.save(ledgerPlan);
        //查询该梁长，梁型，墩高条件下是否已存在同样规格梁号图纸，将已存在的从map结果集中移除，方便后面调用
        List<RelatedDrawings> relatedDrawingsList = new ArrayList<>();
        List<MultipartFile> multipartFileList = new ArrayList<>();
        Map<Integer, Integer> map = isHaveSameDrawing(ledgerPlanVo);
        for (int i = 1; i <= 12; i++) {
            Integer value = map.get(i);
            if (value == 1) {
                map.remove(i);
            }
        }
        for (Integer key : map.keySet()) {
            MultipartFile file = null;
            switch (key) {
                case 1:
                    file = ledgerPlanVo.getBearingType();
                    break;
                case 2:
                    file = ledgerPlanVo.getEndInstallationParameters();
                    break;
                case 3:
                    file = ledgerPlanVo.getBeamTopBottomSize();
                    break;
                case 4:
                    file = ledgerPlanVo.getAntiArchAndSuperHigh();
                    break;
                case 5:
                    file = ledgerPlanVo.getBarSpacing();
                    break;
                case 6:
                    file = ledgerPlanVo.getSteelBeamShapeLength();
                    break;
                case 7:
                    file = ledgerPlanVo.getBellowsPositioningData();
                    break;
                case 8:
                    file = ledgerPlanVo.getBeamBodyStructureDiagram();
                    break;
                case 9:
                    file = ledgerPlanVo.getEndDieSizeShape();
                    break;
                case 10:
                    file = ledgerPlanVo.getSteelBarStructuralDrawing();
                    break;
                case 11:
                    file = ledgerPlanVo.getSteelBeamNumberSpecification();
                    break;
                case 12:
                    file = ledgerPlanVo.getEmbeddedPartsTypeData();
                    break;
                default:
                    break;
            }
            if (file != null) {
                multipartFileList.add(file);
                RelatedDrawings relatedDrawings = new RelatedDrawings();
                relatedDrawings.setBeamLength(ledgerPlanVo.getBeamLength());
                relatedDrawings.setBeamType(ledgerPlanVo.getBeamType());
                relatedDrawings.setPierHeight(ledgerPlanVo.getPierHeight());
                relatedDrawings.setDrawingsType(key);
                relatedDrawingsList.add(relatedDrawings);
            }
        }
        //上传路径
        String uploadUrl = uploadPath;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int month = calendar.get(Calendar.MONTH) + 1;
        uploadUrl = uploadUrl + "/" + String.valueOf(month) + "月";
        try {
            List<String> urlList = FileUtils.uploadList(uploadUrl, multipartFileList);
            for (int i = 0; i < urlList.size(); i++) {
                String url = urlList.get(i);
                RelatedDrawings relatedDrawings = relatedDrawingsList.get(i);
                relatedDrawings.setFileUrl(String.valueOf(month) + "月/" + url);
            }
            relatedDrawingsRepository.save(relatedDrawingsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询是否已存在同样规格梁号图纸
     *
     * @param ledgerPlanVo
     * @return
     */
    @Override
    public Map<Integer, Integer> isHaveSameDrawing(LedgerPlanVo ledgerPlanVo) {
        List<RelatedDrawings> list = relatedDrawingsRepository.findByBeamLengthAndBeamTypeAndPierHeight(ledgerPlanVo.getBeamLength(), ledgerPlanVo.getBeamType(), ledgerPlanVo.getPierHeight());
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            //value为0时表示没有图纸
            map.put(i, 0);
        }
        for (RelatedDrawings entity : list) {
            map.put(entity.getDrawingsType(), 1);
        }
        return map;
    }

    /**
     * 查询图纸
     *
     * @param relatedDrawings
     * @return
     */
    @Override
    public String queryDrawing(RelatedDrawings relatedDrawings) {
        String url = null;
        if (relatedDrawings.getDrawingsType() > 12 && relatedDrawings.getDrawingsType() <= 18) {
            //查询通用图纸
            url = accessPath + relatedDrawingsRepository.findFirstByDrawingsType(relatedDrawings.getDrawingsType()).getFileUrl();
        } else if (relatedDrawings.getDrawingsType() <= 12 && relatedDrawings.getDrawingsType() > 0) {
            url = accessPath + relatedDrawingsRepository.findFirstByBeamLengthAndBeamTypeAndPierHeightAndDrawingsType(relatedDrawings.getBeamLength(), relatedDrawings.getBeamType(), relatedDrawings.getPierHeight(), relatedDrawings.getDrawingsType()).getFileUrl();
        }
        return url;
    }

    /**
     * APP查看图纸
     *
     * @param id
     * @param processId
     * @return
     */
    @Override
    public String queryDrawingAPP(Long id, Integer processId) {
        LedgerPlan ledgerPlan = ledgerPlanRepository.findOne(id);
        String url = null;
        if (processId > 12 && processId <= 18) {
            //查询通用图纸
            url = accessPath + relatedDrawingsRepository.findFirstByDrawingsType(processId).getFileUrl();
        } else if (processId <= 12 && processId > 0) {
            url = accessPath + relatedDrawingsRepository.findFirstByBeamLengthAndBeamTypeAndPierHeightAndDrawingsType(ledgerPlan.getBeamLength(), ledgerPlan.getBeamType(), ledgerPlan.getPierHeight(), processId).getFileUrl();
        }
        return url;
    }

    /**
     * 工序审核通过
     *
     * @param id
     * @param processId
     */
    @Override
    public void processAudit(Long id, Integer processId) {
        LedgerPlan ledgerPlan = ledgerPlanRepository.findOne(id);
        Integer currentProcessId = ledgerPlan.getExamineProgress();
        if (processId > currentProcessId) {
            ledgerPlan.setExamineProgress(processId);
            ledgerPlan.setExamineTime(new Date());
            ledgerPlanRepository.save(ledgerPlan);
        }
    }

    /**
     * 制梁计划审核
     * 制梁计划状态：
     * 计划生成-待总包方审核状态-1
     * 总包方审核通过-待梁场审核状态-2
     * 总包方审核不通过或选择逻辑删除计划-更改为删除状态-5
     * 梁场审核通过-同意生产状态-4
     * 梁场审核不通过改为总包方待审核状态，梁场给出预计生产时间-3
     * 总包方查看梁场意见同意的话就变更用梁时间为梁场预计的梁出场时间，同意生产状态，-4
     *
     * @param ledgePlanExamineVo
     */
    @Override
    public void ledgerExamine(LedgePlanExamineVo ledgePlanExamineVo) {
        LedgerPlan ledgerPlan = ledgerPlanRepository.findOne(ledgePlanExamineVo.getId());
        Integer previousStatus = ledgerPlan.getPlanExamineStatus();
        Integer isPass = ledgePlanExamineVo.getIdPass();
        if (previousStatus == 1) {
            //最初的制梁计划，总包方审核
            if (isPass == 1) {
                ledgerPlan.setPlanExamineStatus(2);
            } else {
                ledgerPlan.setPlanExamineStatus(5);
            }
        } else if (previousStatus == 2) {
            //梁场审核
            if (isPass == 1) {
                //审核通过变为生产状态
                ledgerPlan.setPlanExamineStatus(4);
            } else {
                //审核不通过变为总包方状态，提交梁预计用梁时间
                ledgerPlan.setPlanExamineStatus(3);
                if (ledgePlanExamineVo.getBeamCompletionTime() != null) {
                    ledgerPlan.setBeamCompletionTime(ledgePlanExamineVo.getBeamCompletionTime());
                }
                if (ledgePlanExamineVo.getNote() != null) {
                    ledgerPlan.setNote(ledgePlanExamineVo.getNote());
                }
            }
        } else if (previousStatus == 3) {
            //梁场查看，建议过的制梁计划，总包方审核
            if (isPass == 1) {
                //总包审核通过直接变为生产状态
                ledgerPlan.setPlanExamineStatus(4);
                if (ledgePlanExamineVo.getNote() != null) {
                    ledgerPlan.setNote(ledgePlanExamineVo.getNote());
                }
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    ledgerPlan.setUseTime(df.parse(ledgerPlan.getBeamCompletionTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (isPass == 2) {
                //总包方审核不通过变为梁场审核状态
                ledgerPlan.setPlanExamineStatus(2);
                if (ledgePlanExamineVo.getNote() != null) {
                    ledgerPlan.setNote(ledgePlanExamineVo.getNote());
                }
            } else {
                //总包方删除这条计划
                ledgerPlan.setPlanExamineStatus(5);
            }
        }
        ledgerPlanRepository.save(ledgerPlan);
    }

    /**
     * 新增制梁计划2.0
     *
     * @param ledgerPlanList
     */
    @Override
    public void addLedgerPlan(List<LedgerPlan> ledgerPlanList) {

    }

    /**
     * 判断用户类型，分类：
     * 总包：只查看需要总包方审核的数据和审核通过的数据
     * 梁场：只查看需要梁场方审核的数据和审核通过的数据
     * type 'hql':总包  'bf':梁场
     *计划生成-待总包方审核状态-1
     *总包方审核通过-待梁场审核状态-2
     *总包方审核不通过或选择逻辑删除计划-更改为删除状态-5
     *梁场审核通过-同意生产状态-4
     *梁场审核不通过改为总包方待审核状态，梁场给出预计生产时间-3
     *总包方查看梁场意见同意的话就变更用梁时间为梁场预计的梁出场时间，同意生产状态，-4
     * @return 查询条件参数
     */
    public String judgeUserType() {
        //默认其他用户
        String type = "(4)";
        ResourceVo resourceVo = systemClient.fetchUserType("hql");
        ResourceVo resourceVo1 = systemClient.fetchUserType("bf");
        if (resourceVo != null) {
            //总包方
            type = "(1,3,4)";
        } else if (resourceVo1 != null) {
            //梁场用户
            type = "(2,4)";
        }
        return type;
    }
}
