package net.cdsunrise.wm.beamfield.repository;

import net.cdsunrise.wm.beamfield.entity.LedgerPlanExaminePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/11
 * Describe:
 */
public interface LedgerPlanExaminePlanRepository extends JpaRepository<LedgerPlanExaminePlan,Long> {

    LedgerPlanExaminePlan findByLedgerPlanIdAndProcessNumber(Long ledgerPlanId,Integer processNumber);

    List<LedgerPlanExaminePlan> findByLedgerPlanId(Long id);
}
