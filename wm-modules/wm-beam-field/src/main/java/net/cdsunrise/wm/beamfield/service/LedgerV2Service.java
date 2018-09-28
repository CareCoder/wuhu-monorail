package net.cdsunrise.wm.beamfield.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.beamfield.entity.LedgerV2;

import javax.servlet.http.HttpServletResponse;

/**
 * Author: WangRui
 * Date: 2018/6/8
 * Describe:
 */
public interface LedgerV2Service {

    void save(LedgerV2 ledgerV2);

    LedgerV2 get(Long id);

    Pager<LedgerV2> page(PageCondition condition);

    void export(HttpServletResponse response);
}
