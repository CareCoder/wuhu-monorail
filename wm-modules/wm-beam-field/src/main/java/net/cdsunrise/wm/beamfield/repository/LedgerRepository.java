package net.cdsunrise.wm.beamfield.repository;

import net.cdsunrise.wm.beamfield.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author lijun
 * @date 2018-04-02.
 */
public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    Ledger findByBeamNumberAndLineAndLineInterval(String beamNumber, String line, String lineInterval);
}
