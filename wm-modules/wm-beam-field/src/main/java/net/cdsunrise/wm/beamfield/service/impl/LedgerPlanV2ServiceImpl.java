package net.cdsunrise.wm.beamfield.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.util.ExcelExport;
import net.cdsunrise.wm.base.util.FileUtils;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.beamfield.client.SystemClient;
import net.cdsunrise.wm.beamfield.entity.*;
import net.cdsunrise.wm.beamfield.repository.*;
import net.cdsunrise.wm.beamfield.service.LedgerPlanV2Service;
import net.cdsunrise.wm.beamfield.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
@Service
public class LedgerPlanV2ServiceImpl implements LedgerPlanV2Service {

    @Autowired
    private LedgerPlanV2Repository ledgerPlanV2Repository;

    @Autowired
    private LedgerBasicInformationRepository ledgerBasicInformationRepository;

    @Autowired
    private RelatedDrawingsRepository relatedDrawingsRepository;

    @Autowired
    private LedgerV2Repository ledgerV2Repository;

    @Autowired
    private LedgerExamineLogRepository ledgerExamineLogRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private LedgerPlanServiceImpl util;

    @Autowired
    private SystemClient systemClient;

    /**
     * 制梁计划Excel导出模板绝对路径
     */
    @Value("${PlanExcelModelPath}")
    private String ledgerPlanExcelModelPath;

    /**
     * 图片上传路径
     */
    @Value("${uploadPath}")
    private String uploadPath;

    @Value("${accessPath}")
    private String accessPath;

    @Override
    public void
    save(LedgerPlanV2Vo ledgerPlanV2Vo) {
        LedgerPlanV2 exist = ledgerPlanV2Repository.findByBeamNumber(ledgerPlanV2Vo.getBeamNumber());
        if (exist != null) {
            //存在此梁号的制梁计划，更新最新的用梁时间
            if (exist.getExamineStatus() != 4) {
                //还未生产的计划更改为：1待审核状态
                exist.setExamineStatus(1);
            }
            exist.setUseTime(ledgerPlanV2Vo.getUseTime());
            exist.setNote("用梁时间有变化！");
        } else {
            //新增为待审核制梁计划
            //查看梁基本信息表是否有此梁号,没有就新增
            exist = new LedgerPlanV2();
            LedgerBasicInformation ledgerBasicInformation = ledgerBasicInformationRepository.findByBeamNumber(ledgerPlanV2Vo.getBeamNumber());
            if (ledgerBasicInformation == null) {
                ledgerBasicInformation = new LedgerBasicInformation();
            }
            ledgerBasicInformation.setBeamNumber(ledgerPlanV2Vo.getBeamNumber());
            ledgerBasicInformation.setLine(ledgerPlanV2Vo.getLine());
            ledgerBasicInformation.setWorkPoint(ledgerPlanV2Vo.getWorkPoint());
            ledgerBasicInformation = ledgerBasicInformationRepository.save(ledgerBasicInformation);
            exist.setLedgerBasicInformation(ledgerBasicInformation);
            exist.setProcessExamineId(-1);
            exist.setUseTime(ledgerPlanV2Vo.getUseTime());
            exist.setExamineStatus(1);
        }
        ledgerPlanV2Repository.save(exist);
    }

    @Override
    public LedgerPlanV2 findOne(Long id) {
        return ledgerPlanV2Repository.findOne(id);
    }

    /**
     * 制梁计划分页查询，未审核的靠前显示
     *
     * @param condition
     * @param type      未通过审核的制梁计划页面:1  ，已通过审核的制梁计划页面:2 只显示通过了审核的计划
     * @return
     */
    @Override
    public Pager<LedgerPlanV2> getPager(PageCondition condition, int type) {
        String param = util.judgeUserType();
        if (type == 2) {
            param = "(4)";
        }
        QueryHelper helper = new QueryHelper(LedgerPlanV2.class, "l")
                .addCondition(" examineStatus in " + param)
                .addOrderProperty(" examineStatus ", true)
                .setPageCondition(condition)
                .useNativeSql(false);
        Pager<LedgerPlanV2> pager = commonDAO.findPager(helper);
        return pager;
    }

    /**
     * 条件查询，未审核的靠前显示
     *
     * @param ledgerPlanV2Vo
     * @param condition
     * @param type           未通过审核的制梁计划页面:1  ，已通过审核的制梁计划页面:2 只显示通过了审核的计划
     * @return
     */
    @Override
    public Pager<LedgerPlanV2> selectList(LedgerPlanV2Vo ledgerPlanV2Vo, PageCondition condition, int type) {
        String param = util.judgeUserType();
        if (type == 2) {
            param = "(4)";
        }
        QueryHelper helper = new QueryHelper("id,beamNumber,beamLength,beamSpan,beamType,line,pierHeight,pierNumberBig,pierNumberSmall,radiusOfCurve," +
                "type,workPoint,useTime,note,examineStatus,processExamineId", "(select p.id,b.beam_number as beamNumber," +
                "b.beam_length as beamLength,b.beam_span as beamSpan,b.beam_type as beamType,b.line,b.pier_height as pierHeight," +
                "b.pier_number_big as pierNumberBig,b.pier_number_small as pierNumberSmall,b.radius_of_curve as radiusOfCurve," +
                "b.type,b.work_point as workPoint,p.use_time as useTime,p.note,p.examine_status as examineStatus,p.process_examine_id as processExamineId" +
                " from wm_ledger_plan2 p left JOIN wm_ledger_basic_info b on b.beam_number = p.beam_number) u")
                .addCondition("examineStatus in" + param)
                .addCondition(StringUtils.isNotBlank(ledgerPlanV2Vo.getBeamNumber()), " beamNumber like ?", "%" + ledgerPlanV2Vo.getBeamNumber() + "%")
                .addCondition(StringUtils.isNotBlank(ledgerPlanV2Vo.getWorkPoint()), " beamNumber in( select beam_number  from wm_ledger_basic_info where work_point like ?) ", "%" + ledgerPlanV2Vo.getWorkPoint() + "%")
                .addCondition(ledgerPlanV2Vo.getUseTime() != null, " DATE_FORMAT(useTime,'%Y-%m') = DATE_FORMAT(?,'%Y-%m') ", ledgerPlanV2Vo.getUseTime())
                .addCondition(StringUtils.isNoneBlank(ledgerPlanV2Vo.getUseTimeYM()), " DATE_FORMAT(useTime,'%Y-%m') = ? ", ledgerPlanV2Vo.getUseTimeYM())
                .addOrderProperty(" examineStatus ", true)
                .setPageCondition(condition)
                .useNativeSql(true);
        Pager<LedgerPlanAndBasicInfoQueryResultVo> pager = commonDAO.findPager(helper, LedgerPlanAndBasicInfoQueryResultVo.class);
        Pager<LedgerPlanV2> p = new Pager<>(pager.getNumber(), pager.getPageSize(), pager.getTotalElements(), listConvertVo(pager.getContent()));
        return p;
    }

    List<LedgerPlanV2> listConvertVo(List<LedgerPlanAndBasicInfoQueryResultVo> list) {
        if (list.size() > 0) {
            List<LedgerPlanV2> vos = new ArrayList<>();
            list.forEach(temp ->
                    vos.add(convertVo(temp))
            );
            return vos;
        } else {
            return null;
        }
    }

    LedgerPlanV2 convertVo(LedgerPlanAndBasicInfoQueryResultVo vo) {
        if (vo != null) {
            LedgerPlanV2 ledgerPlanV2 = new LedgerPlanV2();
            LedgerBasicInformation ledgerBasicInformation = new LedgerBasicInformation();
            BeanUtils.copyProperties(vo, ledgerBasicInformation);
            ledgerPlanV2.setId(vo.getId());
            ledgerPlanV2.setLedgerBasicInformation(ledgerBasicInformation);
            ledgerPlanV2.setNote(vo.getNote());
            ledgerPlanV2.setUseTime(vo.getUseTime());
            ledgerPlanV2.setExamineStatus(vo.getExamineStatus());
            ledgerPlanV2.setProcessExamineId(vo.getProcessExamineId());
            return ledgerPlanV2;
        } else {
            return null;
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
     * @param ledgePlanExamineVoV2
     */
    @Override
    public void ledgerExamine(LedgePlanExamineVoV2 ledgePlanExamineVoV2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date());
        LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findOne(ledgePlanExamineVoV2.getId());
        Integer previousStatus = ledgerPlanV2.getExamineStatus();
        Integer isPass = ledgePlanExamineVoV2.getResult();
        if (previousStatus == 1) {
            //最初的制梁计划，总包方审核
            if (isPass == 1) {
                ledgerPlanV2.setExamineStatus(2);
                ledgerPlanV2.setNote(StringUtils.isNoneBlank(ledgerPlanV2.getNote()) ? (ledgerPlanV2.getNote() + time + " : " + ledgePlanExamineVoV2.getNote() + ";") : (time + " : " + ledgePlanExamineVoV2.getNote() + ";"));
            } else if (isPass == 2) {
                //不通过时修改用梁时间
                ledgerPlanV2.setExamineStatus(2);
                ledgerPlanV2.setUseTime(ledgePlanExamineVoV2.getUseTime());
                ledgerPlanV2.setNote(StringUtils.isNoneBlank(ledgerPlanV2.getNote()) ? (ledgerPlanV2.getNote() + time + " : " + ledgePlanExamineVoV2.getNote() + ";") : (time + " : " + ledgePlanExamineVoV2.getNote() + ";"));
            } else {
                //逻辑删除此条计划
                ledgerPlanV2.setExamineStatus(5);
            }
        } else if (previousStatus == 2) {
            //计划状态为2时：梁场审核
            if (isPass == 1) {
                //审核通过变为生产状态
                ledgerPlanV2.setExamineStatus(4);
                ledgerPlanV2.setNote(StringUtils.isNoneBlank(ledgerPlanV2.getNote()) ? (ledgerPlanV2.getNote() + time + " : " + ledgePlanExamineVoV2.getNote() + ";") : (time + " : " + ledgePlanExamineVoV2.getNote() + ";"));
            } else {
                //审核不通过变为总包方状态，修改用梁时间
                ledgerPlanV2.setExamineStatus(3);
                ledgerPlanV2.setUseTime(ledgePlanExamineVoV2.getUseTime());
                if (ledgePlanExamineVoV2.getNote() != null) {
                    ledgerPlanV2.setNote(StringUtils.isNoneBlank(ledgerPlanV2.getNote()) ? (ledgerPlanV2.getNote() + time + " : " + ledgePlanExamineVoV2.getNote() + ";") : (time + " : " + ledgePlanExamineVoV2.getNote() + ";"));
                }
            }
        } else if (previousStatus == 3) {
            //计划状态为23时：总包方审核
            if (isPass == 1) {
                //总包审核通过直接变为生产状态
                ledgerPlanV2.setExamineStatus(4);
                ledgerPlanV2.setNote(StringUtils.isNoneBlank(ledgerPlanV2.getNote()) ? (ledgerPlanV2.getNote() + time + " : " + ledgePlanExamineVoV2.getNote() + ";") : (time + " : " + ledgePlanExamineVoV2.getNote() + ";"));
            } else if (isPass == 2) {
                //不通过时修改用梁时间
                ledgerPlanV2.setExamineStatus(2);
                ledgerPlanV2.setUseTime(ledgePlanExamineVoV2.getUseTime());
                if (ledgePlanExamineVoV2.getNote() != null) {
                    ledgerPlanV2.setNote(StringUtils.isNoneBlank(ledgerPlanV2.getNote()) ? (ledgerPlanV2.getNote() + time + " : " + ledgePlanExamineVoV2.getNote() + ";") : (time + " : " + ledgePlanExamineVoV2.getNote() + ";"));
                }
            } else {
                //逻辑删除此条计划
                ledgerPlanV2.setExamineStatus(5);
            }
        }
        ledgerPlanV2Repository.save(ledgerPlanV2);
    }

    /**
     * 查询是否已存在同样规格梁型和墩高的图纸
     *
     * @param drawingQueryVo
     * @return
     */
    @Override
    public Map<Integer, Integer> isHaveSameDrawing(DrawingQueryVo drawingQueryVo) {
        List<RelatedDrawings> list = relatedDrawingsRepository.findByBeamTypeAndPierHeight(drawingQueryVo.getBeamType(), drawingQueryVo.getPierHeight());
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
     * 查询图纸-PC端
     *
     * @param relatedDrawings
     * @return
     */
    @Override
    public String queryDrawing(RelatedDrawings relatedDrawings) {
        RelatedDrawings relatedDrawings1;
        String url = null;
        if (relatedDrawings.getDrawingsType() > 12 && relatedDrawings.getDrawingsType() <= 28) {
            relatedDrawings1 = relatedDrawingsRepository.findFirstByDrawingsType(relatedDrawings.getDrawingsType());
            //查询通用图纸
            if (relatedDrawings1 != null) {
                url = accessPath + relatedDrawings1.getFileUrl();
            } else {
                url = "";

            }
        } else if (relatedDrawings.getDrawingsType() <= 12 && relatedDrawings.getDrawingsType() > 0) {
            relatedDrawings1 = relatedDrawingsRepository.findFirstByBeamTypeAndPierHeightAndDrawingsType(relatedDrawings.getBeamType(), relatedDrawings.getPierHeight(), relatedDrawings.getDrawingsType());
            if (relatedDrawings1 != null) {
                url = accessPath + relatedDrawings1.getFileUrl();
            } else {
                url = "";
            }
        }
        return url;
    }

    /**
     * 查询图纸-APP端
     *
     * @param id
     * @return
     */
    @Override
    public LedgerPlanAppQueryResult queryDrawingAPP(Long id) {
        LedgerPlanAppQueryResult result = new LedgerPlanAppQueryResult();
        LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findOne(id);
        result.setBeamNumber(ledgerPlanV2.getLedgerBasicInformation().getBeamNumber());
        result.setUseTime(ledgerPlanV2.getUseTime());
        result.setLine(ledgerPlanV2.getLedgerBasicInformation().getLine());
        result.setWorkPoint(ledgerPlanV2.getLedgerBasicInformation().getWorkPoint());
        result.setBeamType(ledgerPlanV2.getLedgerBasicInformation().getBeamType());
        result.setPierNumberSmall(ledgerPlanV2.getLedgerBasicInformation().getPierNumberSmall());
        result.setPierNumberBig(ledgerPlanV2.getLedgerBasicInformation().getPierNumberBig());
        result.setType(ledgerPlanV2.getLedgerBasicInformation().getType());
        result.setBeamLength(ledgerPlanV2.getLedgerBasicInformation().getBeamLength());
        result.setBeamSpan(ledgerPlanV2.getLedgerBasicInformation().getBeamSpan());
        result.setRadiusOfCurve(ledgerPlanV2.getLedgerBasicInformation().getRadiusOfCurve());
        result.setPierHeight(ledgerPlanV2.getLedgerBasicInformation().getPierHeight());
        Map<String, String> map = new HashMap<>();
        RelatedDrawings relatedDrawings = new RelatedDrawings();
        relatedDrawings.setBeamType(ledgerPlanV2.getLedgerBasicInformation().getBeamType());
        relatedDrawings.setPierHeight(ledgerPlanV2.getLedgerBasicInformation().getPierHeight());
        for (int i = 1; i <= 27; i++) {
            relatedDrawings.setDrawingsType(i);
            map.put("url" + i, queryDrawing(relatedDrawings));
        }
        result.setUrl(map);
        return result;
    }

    /**
     * 上传工法指导书图片
     *
     * @param ledgerPlanV2DrawingUploadVo
     */
    @Override
    public void ledgerPlanDrawing(LedgerPlanV2DrawingUploadVo ledgerPlanV2DrawingUploadVo) {
        LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findOne(ledgerPlanV2DrawingUploadVo.getId());
        if (ledgerPlanV2DrawingUploadVo.getNote() != null) {
            ledgerPlanV2.setNote(ledgerPlanV2DrawingUploadVo.getNote());
            ledgerPlanV2Repository.save(ledgerPlanV2);
        }
        //查询该梁型，墩高条件下是否已存在同样规格梁号图纸，将已存在的从map结果集中移除，方便后面调用
        List<RelatedDrawings> relatedDrawingsList = new ArrayList<>();
        List<MultipartFile> multipartFileList = new ArrayList<>();
        DrawingQueryVo drawingQueryVo = new DrawingQueryVo();
        drawingQueryVo.setBeamType(ledgerPlanV2.getLedgerBasicInformation().getBeamType());
        drawingQueryVo.setPierHeight(ledgerPlanV2.getLedgerBasicInformation().getPierHeight());
        Map<Integer, Integer> map = isHaveSameDrawing(drawingQueryVo);
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
                    file = ledgerPlanV2DrawingUploadVo.getBearingType();
                    break;
                case 2:
                    file = ledgerPlanV2DrawingUploadVo.getEndInstallationParameters();
                    break;
                case 3:
                    file = ledgerPlanV2DrawingUploadVo.getBeamTopBottomSize();
                    break;
                case 4:
                    file = ledgerPlanV2DrawingUploadVo.getAntiArchAndSuperHigh();
                    break;
                case 5:
                    file = ledgerPlanV2DrawingUploadVo.getBarSpacing();
                    break;
                case 6:
                    file = ledgerPlanV2DrawingUploadVo.getSteelBeamShapeLength();
                    break;
                case 7:
                    file = ledgerPlanV2DrawingUploadVo.getBellowsPositioningData();
                    break;
                case 8:
                    file = ledgerPlanV2DrawingUploadVo.getBeamBodyStructureDiagram();
                    break;
                case 9:
                    file = ledgerPlanV2DrawingUploadVo.getEndDieSizeShape();
                    break;
                case 10:
                    file = ledgerPlanV2DrawingUploadVo.getSteelBarStructuralDrawing();
                    break;
                case 11:
                    file = ledgerPlanV2DrawingUploadVo.getSteelBeamNumberSpecification();
                    break;
                default:
                    file = ledgerPlanV2DrawingUploadVo.getEmbeddedPartsTypeData();
                    break;
            }
            if (file != null) {
                multipartFileList.add(file);
                RelatedDrawings relatedDrawings = new RelatedDrawings();
                relatedDrawings.setBeamType(ledgerPlanV2.getLedgerBasicInformation().getBeamType());
                relatedDrawings.setPierHeight(ledgerPlanV2.getLedgerBasicInformation().getPierHeight());
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
                RelatedDrawings relatedDrawings = relatedDrawingsList.get(i);
                relatedDrawings.setFileUrl(String.valueOf(month) + "月/" + urlList.get(i));
            }
            relatedDrawingsRepository.save(relatedDrawingsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 工序审核通过
     *
     * @param id
     * @param processId
     */
    @Override
    public void processAudit(Long id, Integer processId, String isPass) {
        LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findOne(id);
        if (processId == 8 && isPass.equals("noPass")) {
            ledgerPlanV2.setExamineStatus(5);
        } else {
            ledgerPlanV2.setProcessExamineId(processId);
        }
        ledgerPlanV2.setProcessExamineTime(new Date());
        ledgerPlanV2Repository.save(ledgerPlanV2);
    }

    /**
     * 工序审核通过2.0
     * 带填报功能
     *
     * @param processInputVo
     */
    @Override
    public void processAudit2(ProcessInputVo processInputVo) {
        LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findOne(processInputVo.getId());
        if (processInputVo.getAdvice() != null) {
            LedgerExamineLog ledgerExamineLog = new LedgerExamineLog();
            ledgerExamineLog.setBeamNumber(ledgerPlanV2.getLedgerBasicInformation().getBeamNumber());
            ledgerExamineLog.setAdvice(processInputVo.getAdvice());
            ledgerExamineLog.setProcessExamineId(processInputVo.getProcessId());
            ledgerExamineLog.setReportTime(new Date());
            ledgerExamineLog.setReportUser(UserUtils.getUsername());
            ledgerExamineLogRepository.save(ledgerExamineLog);
        }
        if (processInputVo.getProcessId() == 8 && processInputVo.getIsPass().equals("noPass")) {
            ledgerPlanV2.setProcessExamineId(5);
        } else {
            ledgerPlanV2.setProcessExamineId(processInputVo.getProcessId());
        }
        if (processInputVo.getProcessId() == 23) {
            //梁出场
            LedgerV2 ledgerV2 = ledgerV2Repository.findFirstByLedgerBasicInformation(ledgerPlanV2.getLedgerBasicInformation());
            ledgerV2.setIsGrant("是");
            ledgerV2Repository.save(ledgerV2);
        } else {
            //通过审核更新填报的台账数据
            LedgerV2 ledgerV2 = ledgerV2Repository.findFirstByLedgerBasicInformation(ledgerPlanV2.getLedgerBasicInformation());
            if (processInputVo.getBeamPedestal() != null) {
                ledgerV2.setBeamPedestal(processInputVo.getBeamPedestal());
            }
            if (processInputVo.getSteelBarBindingTime() != null) {
                ledgerV2.setSteelBarBindingTime(processInputVo.getSteelBarBindingTime());
            }
            if (processInputVo.getModeTime() != null) {
                ledgerV2.setModeTime(processInputVo.getModeTime());
            }
            if (processInputVo.getPouringTime() != null) {
                ledgerV2.setPouringTime(processInputVo.getPouringTime());
            }
            if (processInputVo.getSlump() != null) {
                ledgerV2.setSlump(processInputVo.getSlump());
            }
            if (processInputVo.getDieTemperature() != null) {
                ledgerV2.setDieTemperature(processInputVo.getDieTemperature());
            }
            if (processInputVo.getDieBreakingTime() != null) {
                ledgerV2.setDieBreakingTime(processInputVo.getDieBreakingTime());
            }
            if (processInputVo.getInitialTensioningTime() != null) {
                ledgerV2.setInitialTensioningTime(processInputVo.getInitialTensioningTime());
            }
            if (processInputVo.getBeamShiftingTime() != null) {
                ledgerV2.setBeamShiftingTime(processInputVo.getBeamShiftingTime());
            }
            if (processInputVo.getStorageBeamNumber() != null) {
                ledgerV2.setStorageBeamNumber(processInputVo.getStorageBeamNumber());
            }
            if (processInputVo.getEndTensioningTime() != null) {
                ledgerV2.setEndTensioningTime(processInputVo.getEndTensioningTime());
            }
            if (processInputVo.getPulpingTime() != null) {
                ledgerV2.setPulpingTime(processInputVo.getPulpingTime());
            }
            ledgerV2Repository.save(ledgerV2);
        }
        ledgerPlanV2.setProcessExamineTime(new Date());
        ledgerPlanV2Repository.save(ledgerPlanV2);
    }

    /**
     * queryType:1 在制梁计划表根据每条数据的用梁时间分月查询每个月的用梁需求量，月度汇总
     * queryType:2 在制梁计划表根据每条数据的用梁时间分月查询每个月的累加用梁需求量，月度累加
     * queryType:3 在梁基础信息表根据每条数据的最后一个工序审核是否完成，查询每个月的累加实际生产梁，月度累加 最好一个工序process_examineId=？
     *
     * @return
     */
    @Override
    public List<LedgerDemandStatistics> demandStatistics(int queryType) {
        List<LedgerDemandStatistics> ledgerDemandStatisticsList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (int i = 1; i <= 12; i++) {
            ledgerDemandStatisticsList.add(dataStatistics(i, year, queryType));
        }
        return ledgerDemandStatisticsList;
    }

    /**
     * 制梁计划Excel导出
     *
     * @param response
     */
    @Override
    public void ledgerPlanExport(HttpServletResponse response) {
        List<LedgerPlanV2> ledgerPlanV2s = ledgerPlanV2Repository.findByExamineStatus(4);
        if (ledgerPlanV2s.size() > 0) {
            LedgerPlanV2 ledgerPlanV2;
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < ledgerPlanV2s.size(); i++) {
                ledgerPlanV2 = ledgerPlanV2s.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                entityMap.put(0, i + 1);
                entityMap.put(1, ledgerPlanV2.getLedgerBasicInformation().getBeamNumber());
                entityMap.put(2, ledgerPlanV2.getLedgerBasicInformation().getLine());
                entityMap.put(3, ledgerPlanV2.getLedgerBasicInformation().getWorkPoint());
                entityMap.put(4, ledgerPlanV2.getLedgerBasicInformation().getBeamType());
                entityMap.put(5, ledgerPlanV2.getLedgerBasicInformation().getPierNumberSmall());
                entityMap.put(6, ledgerPlanV2.getLedgerBasicInformation().getPierNumberBig());
                entityMap.put(7, ledgerPlanV2.getLedgerBasicInformation().getType());
                entityMap.put(8, ledgerPlanV2.getLedgerBasicInformation().getPierHeight());
                entityMap.put(9, ledgerPlanV2.getLedgerBasicInformation().getBeamSpan());
                entityMap.put(10, ledgerPlanV2.getLedgerBasicInformation().getRadiusOfCurve());
                entityMap.put(11, ledgerPlanV2.getLedgerBasicInformation().getBeamLength());
                entityMap.put(12, ledgerPlanV2.getUseTime());
                entityMap.put(13, ledgerPlanV2.getNote());
                list.add(entityMap);
            }
            String fileName = "制梁计划-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            try {
                ExcelExport.exportByModel(response, fileName, list, ledgerPlanExcelModelPath, 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据墩号数组生成制梁计划
     *
     * @param ledgerPlanV2VoV2
     */
    @Override
    public void createLedgerPlanByPier(LedgerPlanV2VoV2 ledgerPlanV2VoV2) {
        List<LedgerBasicInformation> ledgerBasicInformationList = ledgerBasicInformationRepository.findByPierNumberSmallInOrPierNumberBigIn(ledgerPlanV2VoV2.getPierList(), ledgerPlanV2VoV2.getPierList());
        LedgerBasicInformation ledgerBasicInformation;
        List<String> pierNumberList = ledgerPlanV2VoV2.getPierList();
        for (int i = 0; i < ledgerBasicInformationList.size(); i++) {
            ledgerBasicInformation = ledgerBasicInformationList.get(i);
            String pierSmall = ledgerBasicInformation.getPierNumberSmall();
            String pierBig = ledgerBasicInformation.getPierNumberBig();
            if (pierNumberList.contains(pierSmall) && pierNumberList.contains(pierBig)) {
                ledgerBasicInformation.setWorkPoint(ledgerPlanV2VoV2.getWorkPoint());
                ledgerBasicInformationRepository.save(ledgerBasicInformation);
                LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findByBeamNumber(ledgerBasicInformation.getBeamNumber());
                if (ledgerPlanV2 != null) {
                    ledgerPlanV2.setUseTime(ledgerPlanV2VoV2.getUseTime());
                    ledgerPlanV2Repository.save(ledgerPlanV2);
                } else {
                    ledgerPlanV2 = new LedgerPlanV2();
                    ledgerPlanV2.setUseTime(ledgerPlanV2VoV2.getUseTime());
                    ledgerPlanV2.setExamineStatus(1);
                    ledgerPlanV2.setProcessExamineId(-1);
                    ledgerPlanV2.setLedgerBasicInformation(ledgerBasicInformation);
                    ledgerPlanV2Repository.save(ledgerPlanV2);
                }
            }
        }
    }

    /**
     * 根据梁号list生成制梁计划
     *
     * @param createLedgerPlanVoList
     */
    @Override
    public void createLedgerPlanByPier2(List<CreateLedgerPlanVo> createLedgerPlanVoList) {
        List<LedgerPlanV2> ledgerPlanV2s = new ArrayList<>();
        CreateLedgerPlanVo createLedgerPlanVo;
        for (int i = 0; i < createLedgerPlanVoList.size(); i++) {
            createLedgerPlanVo = createLedgerPlanVoList.get(i);
            LedgerPlanV2 ledgerPlanV2 = new LedgerPlanV2();
            LedgerBasicInformation ledgerBasicInformation = new LedgerBasicInformation();
            ledgerBasicInformation.setBeamNumber(createLedgerPlanVo.getBeamNumber());
            ledgerBasicInformation.setWorkPoint(createLedgerPlanVo.getWorkPoint());
            ledgerPlanV2.setLedgerBasicInformation(ledgerBasicInformation);
            ledgerPlanV2.setExamineStatus(1);
            ledgerPlanV2.setProcessExamineId(-1);
            ledgerPlanV2.setUseTime(createLedgerPlanVo.getUseTime());
            ledgerPlanV2s.add(ledgerPlanV2);
        }
        ledgerPlanV2Repository.save(ledgerPlanV2s);
    }


    @Override
    public void deleteImg(String beamType, String pierHeight, int picType) {
        relatedDrawingsRepository.deleteByBeamTypeAndPierHeightAndDrawingsType(beamType, pierHeight, picType);
    }

    /**
     * 工序审核查询数据
     *
     * @param id
     * @return
     */
    @Override
    public ProcessAuditQueryVo processAuditQuery(Long id) {
        ProcessAuditQueryVo processAuditQueryVo = new ProcessAuditQueryVo();
        LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findOne(id);
        processAuditQueryVo.setProcessExamineId(ledgerPlanV2.getProcessExamineId());
        processAuditQueryVo.setProcessExamineTime(ledgerPlanV2.getProcessExamineTime());
        //map返回每个还未审核的工序对应id和对应的预计审核时间
        Map<Integer, Date> map = new HashMap<>();
        //list返回可审核的工序id和该填报的字段，输入类型(文本框还是时间选择框)
        List<ProcessEditVo> list = new ArrayList<>();
        //有填报功能的工序id
        Integer[] processEditId = new Integer[]{1, 8, 9, 13, 15, 17, 18, 20, 21};
        List<Integer> processEditIdList = Arrays.asList(processEditId);
        Integer processId = ledgerPlanV2.getProcessExamineId();
        for (int i = 1; i <= 23; i++) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(ledgerPlanV2.getProcessExamineTime().toInstant(), ZoneId.systemDefault());
            if (i <= processId) {
                map.put(i, ledgerPlanV2.getProcessExamineTime());
            } else {
                double hour = 0;
                for (int j = processId + 1; j <= i; j++) {
                    hour += processIntervalTimeHour(j);
                }
                localDateTime = localDateTime.plusMinutes((long) (hour * 60));
                //若预计审核时间小于当前时间，可以填报
                Duration duration = Duration.between(localDateTime, LocalDateTime.now());
                if (duration.toMinutes() > 0 && processEditIdList.contains(i)) {
                    list = processEditVo(i, list);
                }
                map.put(i, Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
            }
        }
        //装填审核意见数据
        ProcessEditVo processEditVo = new ProcessEditVo();
        processEditVo.setLabel("审核意见");
        processEditVo.setInputType(1);
        processEditVo.setCode("advice");
        processEditVo.setPId(0);
        list.add(processEditVo);
        processAuditQueryVo.setMap(map);
        processAuditQueryVo.setList(list);
        return processAuditQueryVo;
    }

    public List<ProcessEditVo> processEditVo(int i, List<ProcessEditVo> list) {
        if (i == 13) {
            for (int j = 1; j <= 3; j++) {
                ProcessEditVo vo = new ProcessEditVo();
                vo.setPId(i);
                switch (j) {
                    case 1:
                        vo.setInputType(2);
                        vo.setLabel("浇筑时间");
                        vo.setCode("pouringTime");
                        break;
                    case 2:
                        vo.setInputType(1);
                        vo.setLabel("坍落度");
                        vo.setCode("slump");
                        break;
                    default:
                        vo.setInputType(1);
                        vo.setLabel("入模温度");
                        vo.setCode("dieTemperature");
                        break;
                }
                list.add(vo);
            }
        } else if (i == 18) {
            for (int j = 1; j <= 2; j++) {
                ProcessEditVo vo = new ProcessEditVo();
                vo.setPId(i);
                if (j == 1) {
                    vo.setInputType(2);
                    vo.setLabel("移梁时间");
                    vo.setCode("beamShiftingTime");
                } else {
                    vo.setInputType(1);
                    vo.setLabel("存梁台座号");
                    vo.setCode("storageBeamNumber");
                }
                list.add(vo);
            }
        } else {
            ProcessEditVo vo = new ProcessEditVo();
            vo.setPId(i);
            switch (i) {
                case 1:
                    vo.setLabel("制梁台座号");
                    vo.setInputType(1);
                    vo.setCode("beamPedestal");
                    break;
                case 8:
                    vo.setLabel("绑扎完成时间");
                    vo.setInputType(2);
                    vo.setCode("steelBarBindingTime");
                    break;
                case 9:
                    vo.setLabel("合模时间");
                    vo.setInputType(2);
                    vo.setCode("modeTime");
                    break;
                case 15:
                    vo.setLabel("拆模时间");
                    vo.setInputType(2);
                    vo.setCode("dieBreakingTime");
                    break;
                case 17:
                    vo.setLabel("初张时间");
                    vo.setInputType(2);
                    vo.setCode("initialTensioningTime");
                    break;
                case 20:
                    vo.setLabel("终张时间");
                    vo.setInputType(2);
                    vo.setCode("endTensioningTime");
                    break;
                case 21:
                    vo.setLabel("压浆时间");
                    vo.setInputType(2);
                    vo.setCode("pulpingTime");
                    break;
                default:
                    break;
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public Map<String, String> ledgerUserType() {
        Map<String, String> map = new HashMap<>();
        String deleteType = "false";
        String addType = "false";
        ResourceVo resourceVo = systemClient.fetchUserType("hql");
        ResourceVo resourceVo1 = systemClient.fetchUserType("createLegerPlan");
        if (resourceVo != null) {
            deleteType = "true";
        }
        if (resourceVo1 != null) {
            addType = "true";
        }
        map.put("isDelete", deleteType);
        map.put("isAdd", addType);
        return map;
    }


    /**
     * 开始制梁
     * 点击开始制梁后要生成对应的制梁计划
     *
     * @param id
     */
    @Override
    public void beginMaking(Long id) {
        LedgerPlanV2 ledgerPlanV2 = ledgerPlanV2Repository.findOne(id);
        ledgerPlanV2.setProcessExamineId(0);
        ledgerPlanV2.setProcessExamineTime(new Date());
        ledgerPlanV2Repository.save(ledgerPlanV2);
        LedgerV2 ledgerV2 = new LedgerV2();
        ledgerV2.setLedgerBasicInformation(ledgerPlanV2.getLedgerBasicInformation());
        ledgerV2Repository.save(ledgerV2);
    }


    /**
     * 计算工序间隔时间
     *
     * @param i
     * @return
     */
    public double processIntervalTimeHour(int i) {
        double hour;
        switch (i) {
            case 1:
                hour = 1.5;
                break;
            case 2:
                hour = 1.5;
                break;
            case 3:
                hour = 3;
                break;
            case 4:
                hour = 0.25;
                break;
            case 5:
                hour = 3;
                break;
            case 6:
                hour = 3;
                break;
            case 7:
                hour = 2;
                break;
            case 8:
                hour = 0;
                break;
            case 9:
                hour = 3;
                break;
            case 10:
                hour = 1;
                break;
            case 11:
                hour = 1;
                break;
            case 12:
                hour = 1;
                break;
            case 13:
                hour = 6;
                break;
            case 14:
                hour = 18;
                break;
            case 15:
                hour = 1;
                break;
            case 16:
                hour = 2;
                break;
            case 17:
                hour = 0.5;
                break;
            case 18:
                hour = 0.5;
                break;
            case 19:
                hour = 336;
                break;
            case 20:
                hour = 0.5;
                break;
            case 21:
                hour = 2;
                break;
            case 22:
                hour = 1104;
                break;
            default:
                hour = 0;
                break;
        }
        return hour;
    }

    public LedgerDemandStatistics dataStatistics(int month, int year, int queryType) {
        QueryHelper helper;
        switch (queryType) {
            case 1:
                helper = new QueryHelper("l.month,l.number",
                        "(select CONCAT('" + year + "-','" + month + "') as month,count(*) as number from wm_ledger_plan2 where MONTH(use_time) = " + month + " and YEAR(use_time)=" + year + ") as l")
                        .useNativeSql(true);
                break;
            case 2:
                helper = new QueryHelper("l.month,l.number",
                        "(select CONCAT('" + year + "-','" + month + "') as month,count(*) as number from wm_ledger_plan2 where MONTH(use_time) <= " + month + " and YEAR(use_time)=" + year + ") as l")
                        .useNativeSql(true);
                break;
            default:
                helper = new QueryHelper("l.month,l.number",

                        "(select CONCAT('" + year + "-','" + month + "') as month,count(*) as number from wm_ledger_plan2 where process_examine_id = 23 and MONTH(process_examine_time) <= " + month + " and YEAR(process_examine_time)=" + year + ") as l")
                        .useNativeSql(true);
                break;
        }
        List<LedgerDemandStatistics> list = commonDAO.findList(helper, LedgerDemandStatistics.class);
        return list.get(0);
    }

}
