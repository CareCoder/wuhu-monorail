package net.cdsunrise.wm.beamfield.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.util.ExcelExport;
import net.cdsunrise.wm.beamfield.entity.LedgerBasicInformation;
import net.cdsunrise.wm.beamfield.entity.LedgerV2;
import net.cdsunrise.wm.beamfield.repository.LedgerV2Repository;
import net.cdsunrise.wm.beamfield.service.LedgerV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: WangRui
 * Date: 2018/6/8
 * Describe:
 */
@Service
public class LedgerV2ServiceImpl implements LedgerV2Service {

    @Autowired
    private LedgerV2Repository ledgerV2Repository;

    @Autowired
    private CommonDAO commonDAO;

    @Value("${tbExcelModelPath2}")
    private String tbExcelModelPath2;

    @Override
    public void save(LedgerV2 ledgerV2) {
        ledgerV2Repository.save(ledgerV2);
    }

    @Override
    public LedgerV2 get(Long id) {
        return ledgerV2Repository.findOne(id);
    }

    @Override
    public Pager<LedgerV2> page(PageCondition condition) {
        QueryHelper helper = new QueryHelper(LedgerV2.class, "l")
                .setPageCondition(condition)
                .useNativeSql(false);
        Pager<LedgerV2> ledgerV2List = commonDAO.findPager(helper);
        return ledgerV2List;
    }

    @Override
    public void export(HttpServletResponse response) {
        List<LedgerV2> ledgerV2s = ledgerV2Repository.findAll();
        LedgerV2 ledgerV2;
        if (ledgerV2s.size() > 0) {
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < ledgerV2s.size(); i++) {
                ledgerV2 = ledgerV2s.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                LedgerBasicInformation bic = ledgerV2.getLedgerBasicInformation();
                entityMap.put(0, bic.getModelComponentUniquelyMarking());
                entityMap.put(1, bic.getBeamNumber());
                entityMap.put(2, bic.getGraphNumber());
                entityMap.put(3, bic.getLine());
                entityMap.put(4, bic.getApplicationScope());
                entityMap.put(5, bic.getLineInterval());
                entityMap.put(6, bic.getPierNumberSmall());
                entityMap.put(7, bic.getPierNumberBig());
                entityMap.put(8, bic.getMileageSmall());
                entityMap.put(9, bic.getMileageBig());
                entityMap.put(10, bic.getBeamType());
                entityMap.put(11, bic.getBeamSpan());
                entityMap.put(12, bic.getBeamLength());
                entityMap.put(13, bic.getRadiusOfCurve());
                entityMap.put(14, bic.getSuperHighSmallMileage());
                entityMap.put(15, bic.getSuperHighBigMileage());
                entityMap.put(16, bic.getSuperHighLength());
                entityMap.put(17, bic.getEPEscapeRoute());
                entityMap.put(18, bic.getEPContactRailChannelNumber());
                entityMap.put(19, bic.getEPBeacon());
                entityMap.put(20, bic.getEPSupportingSteel());
                entityMap.put(21, bic.getEPSupportCastSteelPullingForce());
                entityMap.put(22, bic.getEPSupportSphericalSteel());
                entityMap.put(23, bic.getEPInternalModel());
                entityMap.put(24, bic.getEPPVC());
                entityMap.put(25, bic.getEPSteelPipe());
                entityMap.put(26, bic.getEPFingerPlateSeat());
                entityMap.put(27, bic.getEPSteelStrand());
                entityMap.put(28, bic.getEPCorrugatedPipe50());
                entityMap.put(29, bic.getEPCorrugatedPipe55());
                entityMap.put(30, bic.getEPCorrugatedPipe60());
                entityMap.put(31, bic.getEPCorrugatedPipe70());
                entityMap.put(32, bic.getEPAnchoragePlate3());
                entityMap.put(33, bic.getEPAnchoragePlate4());
                entityMap.put(34, bic.getEPAnchoragePlate5());
                entityMap.put(35, bic.getEPAnchoragePlate6());
                entityMap.put(36, bic.getEPAnchoragePlate7());
                entityMap.put(37, bic.getEPGroutingMaterial());
                entityMap.put(38, bic.getHPB8());
                entityMap.put(39, bic.getHPB10());
                entityMap.put(40, bic.getHPB12());
                entityMap.put(41, bic.getHPB16());
                entityMap.put(42, bic.getHPB18());
                entityMap.put(43, bic.getHPB32());
                entityMap.put(44, bic.getSleeve32());
                entityMap.put(45, bic.getSleeve16());
                entityMap.put(46, bic.getC60());
                entityMap.put(47, ledgerV2.getIsGrant());
                entityMap.put(48, ledgerV2.getBeamPedestal());
                entityMap.put(49, ledgerV2.getSteelBarBindingTime());
                entityMap.put(50, ledgerV2.getModeTime());
                entityMap.put(51, ledgerV2.getPouringTime());
                entityMap.put(52, ledgerV2.getSlump());
                entityMap.put(53, ledgerV2.getDieTemperature());
                entityMap.put(54, ledgerV2.getDieBreakingTime());
                entityMap.put(55, ledgerV2.getInitialTensioningTime());
                entityMap.put(56, ledgerV2.getBeamShiftingTime());
                entityMap.put(57, ledgerV2.getStorageBeamNumber());
                entityMap.put(58, ledgerV2.getEndTensioningTime());
                entityMap.put(59, ledgerV2.getPulpingTime());
                entityMap.put(60,ledgerV2.getIsGrant());
                list.add(entityMap);
            }
            String fileName = "PC轨道梁台帐-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            try {
                ExcelExport.exportByModel(response, fileName, list, tbExcelModelPath2, 4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
