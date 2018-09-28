package net.cdsunrise.wm.beamfield.repository;

import net.cdsunrise.wm.beamfield.entity.LedgerPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/20
 * Describe :
 */
public interface LedgerPlanRepository extends JpaRepository<LedgerPlan, Long> {

    LedgerPlan findByBeamNumberAndLine(String beamNumber, String line);

}
