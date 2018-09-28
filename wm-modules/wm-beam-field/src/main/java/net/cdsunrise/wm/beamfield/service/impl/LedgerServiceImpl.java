package net.cdsunrise.wm.beamfield.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.beamfield.entity.Ledger;
import net.cdsunrise.wm.beamfield.repository.LedgerRepository;
import net.cdsunrise.wm.beamfield.service.LedgerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-02.
 */
@Service
public class LedgerServiceImpl implements LedgerService {
    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public Ledger findOne(Long id) {
        return ledgerRepository.findOne(id);
    }

    @Override
    public List<Ledger> findAll() {
        return ledgerRepository.findAll();
    }

    @Override
    public Pager<Ledger> getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(Ledger.class)
                .setPageCondition(condition)
                .useNativeSql(false);
        Pager<Ledger> pager = commonDAO.findPager(helper);
        return pager;

    }

    @Override
    public List<Ledger> selectList(Ledger ledger) {
        QueryHelper helper = new QueryHelper(Ledger.class)
                .addCondition(StringUtils.isNotBlank(ledger.getLine()), "line = ?", ledger.getLine())
                .addCondition(StringUtils.isNotBlank(ledger.getLineInterval()), "lineInterval = ?", ledger.getLineInterval())
                .addCondition(StringUtils.isNotBlank(ledger.getBeamNumber()), "beamNumber = ?", ledger.getBeamNumber())
                .useNativeSql(false);
        List<Ledger> ledgerList = commonDAO.findList(helper);
        return ledgerList;
    }

    @Override
    public List<Ledger> sbaLedgerList() {
        QueryHelper helper = new QueryHelper(Ledger.class)
                .addCondition(true, "presTensGroutBeamStorageNumber <>'回转区' and presTensGroutBeamStorageNumber is not null and isTransport is null")
                .useNativeSql(false);
        List<Ledger> ledgerList = commonDAO.findList(helper);
        return ledgerList;
    }

    @Override
    public List<String> sbaLedgerStorgeNumberArray() {
        List<Ledger> ledgerList = sbaLedgerList();
        List<String> stringList = new ArrayList<>();
        for (Ledger ledger : ledgerList){
            stringList.add(ledger.getPresTensGroutBeamStorageNumber());
        }
        return stringList;
    }

    @Override
    public void save(Ledger ledger) {
        ledgerRepository.save(ledger);
    }

    /**
     * 台账批量导入
     *
     * @param ledgerList
     */
    @Override
    public void saveBatch(List<Ledger> ledgerList) {
        for (Ledger ledger : ledgerList) {
            Ledger exist = ledgerRepository.findByBeamNumberAndLineAndLineInterval(ledger.getBeamNumber(), ledger.getLine(), ledger.getLineInterval());
            if (exist != null) {
                ledger.setId(exist.getId());
            }
        }
        ledgerRepository.save(ledgerList);
    }

    @Override
    public void delete(Long id) {
        ledgerRepository.delete(id);
    }

}
