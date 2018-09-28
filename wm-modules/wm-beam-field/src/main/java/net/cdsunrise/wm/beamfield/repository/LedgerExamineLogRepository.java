package net.cdsunrise.wm.beamfield.repository;

import net.cdsunrise.wm.beamfield.entity.LedgerExamineLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/15/015
 * Describe:
 */
public interface LedgerExamineLogRepository extends JpaRepository<LedgerExamineLog, Long> {

    LedgerExamineLog findFirstByBeamNumberAndProcessExamineId(String beamNumber, Integer processExamineId);
}
