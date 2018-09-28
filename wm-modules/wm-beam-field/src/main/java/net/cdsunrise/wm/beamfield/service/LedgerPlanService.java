package net.cdsunrise.wm.beamfield.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.beamfield.entity.LedgerPlan;
import net.cdsunrise.wm.beamfield.entity.LedgerPlanExaminePlan;
import net.cdsunrise.wm.beamfield.entity.RelatedDrawings;
import net.cdsunrise.wm.beamfield.vo.LedgePlanExamineVo;
import net.cdsunrise.wm.beamfield.vo.LedgerDemandStatistics;
import net.cdsunrise.wm.beamfield.vo.LedgerPlanVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author : WangRui
 * Date : 2018/4/23
 * Describe :
 */
public interface LedgerPlanService {

    void save(LedgerPlan ledgerPlan);

    LedgerPlan findOne(Long id);

    Pager getPager(PageCondition condition);

    List<LedgerPlan> findAll();

    Pager selectList(LedgerPlanVo ledgerPlanVo,PageCondition condition);

    List<LedgerDemandStatistics> demandStatistics(int queryType);

    void beginMake(Long id);

    void examinePass(Long id);

    List<LedgerPlanExaminePlan> examineList(Long id);

    void delete(Long id);

    void ledgerPlanDrawing(LedgerPlanVo ledgerPlanVo);

    Map<Integer,Integer> isHaveSameDrawing(LedgerPlanVo ledgerPlanVo);

    String queryDrawing(RelatedDrawings relatedDrawings);

    String queryDrawingAPP(Long id,Integer processId);

    void processAudit(Long id,Integer processId);

    void ledgerExamine(LedgePlanExamineVo ledgePlanExamineVo);

    void addLedgerPlan(List<LedgerPlan> ledgerPlanList);
}
