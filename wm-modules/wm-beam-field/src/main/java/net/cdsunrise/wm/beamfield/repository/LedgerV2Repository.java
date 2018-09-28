package net.cdsunrise.wm.beamfield.repository;

import net.cdsunrise.wm.beamfield.entity.LedgerBasicInformation;
import net.cdsunrise.wm.beamfield.entity.LedgerV2;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: WangRui
 * Date: 2018/6/8
 * Describe:
 */
public interface LedgerV2Repository extends JpaRepository<LedgerV2, Long> {

    LedgerV2 findFirstByLedgerBasicInformation(LedgerBasicInformation ledgerBasicInformation);
}
