package net.cdsunrise.wm.beamfield.repository;

import net.cdsunrise.wm.beamfield.entity.LedgerPlanV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
public interface LedgerPlanV2Repository extends JpaRepository<LedgerPlanV2,Long> {

    @Query(value = "select * from wm_ledger_plan2 where beam_number =?1",nativeQuery = true)
    LedgerPlanV2 findByBeamNumber(String beamNumber);

    List<LedgerPlanV2> findByExamineStatus(Integer status);
}
