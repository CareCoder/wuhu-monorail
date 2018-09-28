package net.cdsunrise.wm.beamfield.service.impl;

import net.cdsunrise.wm.beamfield.entity.LedgerPlanExaminePlan;
import net.cdsunrise.wm.beamfield.repository.LedgerPlanExaminePlanRepository;
import net.cdsunrise.wm.beamfield.service.LedgerPlanExaminePlanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/11
 * Describe:
 */
public class LedgerPlanExaminePlanServiceImpl implements LedgerPlanExaminePlanService {

    @Autowired
    private LedgerPlanExaminePlanRepository ledgerPlanExaminePlanRepository;

    @Override
    public void save(List<LedgerPlanExaminePlan> ledgerPlanExaminePlanList) {
        ledgerPlanExaminePlanRepository.save(ledgerPlanExaminePlanList);
    }
}
