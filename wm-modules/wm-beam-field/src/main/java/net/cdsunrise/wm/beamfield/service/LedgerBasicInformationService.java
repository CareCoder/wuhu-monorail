package net.cdsunrise.wm.beamfield.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.beamfield.entity.LedgerBasicInformation;
import net.cdsunrise.wm.beamfield.vo.LedgerBasicInformationVo;
import net.cdsunrise.wm.beamfield.vo.LedgerPlanV2VoV2;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
public interface LedgerBasicInformationService {

    void save(LedgerBasicInformation ledgerBasicInformation);

    void delete(String beamNumber);

    LedgerBasicInformation get(String beamNumber);

    void SaveBatch(List<LedgerBasicInformation> ledgerBasicInformationList);

    Pager<LedgerBasicInformation> getPager(PageCondition condition);

    Pager<LedgerBasicInformation> selectList(LedgerBasicInformationVo ledgerBasicInformationVo, PageCondition condition);

    List<LedgerBasicInformation> selectByPierNumber(LedgerPlanV2VoV2 list);
}
