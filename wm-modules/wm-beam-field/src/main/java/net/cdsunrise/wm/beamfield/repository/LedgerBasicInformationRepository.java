package net.cdsunrise.wm.beamfield.repository;


import net.cdsunrise.wm.beamfield.entity.LedgerBasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
public interface LedgerBasicInformationRepository extends JpaRepository<LedgerBasicInformation,String> {

    LedgerBasicInformation findByBeamNumber(String beamNumber);

    List<LedgerBasicInformation> findByPierNumberSmallInOrPierNumberBigIn(List<String> strings,List<String> strings2);

    @Query(value = "select l from LedgerBasicInformation l where pierNumberSmall in ?1 or pierNumberBig in ?1 group by beamNumber")
    List<LedgerBasicInformation> selectByPierNumber(List<String> pierNumber1);
}
