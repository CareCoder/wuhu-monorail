package net.cdsunrise.wm.beamfield.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.beamfield.entity.LedgerBasicInformation;
import net.cdsunrise.wm.beamfield.repository.LedgerBasicInformationRepository;
import net.cdsunrise.wm.beamfield.service.LedgerBasicInformationService;
import net.cdsunrise.wm.beamfield.vo.LedgerBasicInformationVo;
import net.cdsunrise.wm.beamfield.vo.LedgerPlanV2VoV2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
@Service
public class LedgerBasicInformationServiceImpl implements LedgerBasicInformationService {

    @Autowired
    private LedgerBasicInformationRepository ledgerBasicInformationRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void save(LedgerBasicInformation ledgerBasicInformation) {
        ledgerBasicInformationRepository.save(ledgerBasicInformation);
    }

    @Override
    public void delete(String beamNumber) {
        ledgerBasicInformationRepository.delete(beamNumber);
    }

    @Override
    public LedgerBasicInformation get(String beamNumber) {
        return ledgerBasicInformationRepository.findOne(beamNumber);
    }

    @Override
    public void SaveBatch(List<LedgerBasicInformation> ledgerBasicInformationList) {
        ledgerBasicInformationRepository.save(ledgerBasicInformationList);
    }

    @Override
    public Pager<LedgerBasicInformation> getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(LedgerBasicInformation.class, "l")
                .setPageCondition(condition)
                .useNativeSql(false);
        Pager<LedgerBasicInformation> pager = commonDAO.findPager(helper);
        return pager;
    }

    @Override
    public Pager<LedgerBasicInformation> selectList(LedgerBasicInformationVo ledgerBasicInformationVo, PageCondition condition) {
        QueryHelper helper = new QueryHelper(LedgerBasicInformation.class, "l")
                .addCondition(StringUtils.isNotBlank(ledgerBasicInformationVo.getBeamNumber()), "beamNumber like ?", "%" + ledgerBasicInformationVo.getBeamNumber() + "%")
                .addCondition(StringUtils.isNotBlank(ledgerBasicInformationVo.getWorkPoint()), "workPoint like ?", "%" + ledgerBasicInformationVo.getWorkPoint() + "%")
                .setPageCondition(condition)
                .useNativeSql(false);
        Pager<LedgerBasicInformation> pager = commonDAO.findPager(helper);
        return pager;
    }

    @Override
    public List<LedgerBasicInformation> selectByPierNumber(LedgerPlanV2VoV2 list) {
        return ledgerBasicInformationRepository.selectByPierNumber(list.getPierList());
    }
}
