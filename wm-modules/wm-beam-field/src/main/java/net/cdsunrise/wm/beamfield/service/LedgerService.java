package net.cdsunrise.wm.beamfield.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.beamfield.entity.Ledger;
import net.cdsunrise.wm.beamfield.vo.LedgerDemandStatistics;
import net.cdsunrise.wm.beamfield.vo.LedgerExcelModel;
import net.cdsunrise.wm.beamfield.vo.LedgerQueryVo;
import org.apache.commons.lang.text.StrTokenizer;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-02.
 */
public interface LedgerService {

    void save(Ledger ledger);

    void saveBatch(List<Ledger> ledgerList);

    void delete(Long id);

    Ledger findOne(Long id);

    List<Ledger> findAll();

    Pager getPager(PageCondition condition);

    List<Ledger> selectList(Ledger ledger);

    List<Ledger> sbaLedgerList();

    List<String> sbaLedgerStorgeNumberArray();
}
