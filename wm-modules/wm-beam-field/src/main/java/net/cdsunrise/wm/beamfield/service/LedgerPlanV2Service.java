package net.cdsunrise.wm.beamfield.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.beamfield.entity.LedgerPlanV2;
import net.cdsunrise.wm.beamfield.entity.RelatedDrawings;
import net.cdsunrise.wm.beamfield.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
public interface LedgerPlanV2Service {

    void save(LedgerPlanV2Vo ledgerPlanV2Vo);

    LedgerPlanV2 findOne(Long id);

    Pager<LedgerPlanV2> getPager(PageCondition condition, int type);

    Pager<LedgerPlanV2> selectList(LedgerPlanV2Vo ledgerPlanV2Vo, PageCondition condition, int type);

    void ledgerExamine(LedgePlanExamineVoV2 ledgePlanExamineVoV2);

    Map<Integer, Integer> isHaveSameDrawing(DrawingQueryVo drawingQueryVo);

    String queryDrawing(RelatedDrawings relatedDrawings);

    LedgerPlanAppQueryResult queryDrawingAPP(Long id);

    void ledgerPlanDrawing(LedgerPlanV2DrawingUploadVo ledgerPlanV2DrawingUploadVo);

    void processAudit(Long id, Integer processId, String isPass);

    void processAudit2(ProcessInputVo processInputVo);

    List<LedgerDemandStatistics> demandStatistics(int queryType);

    void ledgerPlanExport(HttpServletResponse response);

    void createLedgerPlanByPier(LedgerPlanV2VoV2 ledgerPlanV2VoV2);

    void createLedgerPlanByPier2(List<CreateLedgerPlanVo> createLedgerPlanVoList);

    void deleteImg(String beamType, String pierHeight, int picType);

    ProcessAuditQueryVo processAuditQuery(Long id);

    Map<String, String> ledgerUserType();

    void beginMaking(Long id);
}
