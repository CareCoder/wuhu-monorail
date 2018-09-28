package net.cdsunrise.wm.prophase.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.prophase.entity.PeripheralRisk;
import net.cdsunrise.wm.prophase.vo.PeripheralRiskSaveVo;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
public interface PeripheralRiskService {
    void save(PeripheralRiskSaveVo peripheralRiskSaveVo);

    void saveBatch(List<PeripheralRisk> peripheralRisk);

    void delete(Long id);

    PeripheralRisk findOne(Long id);

    Pager getPager(PageCondition condition);

    List<PeripheralRisk> selectList(PeripheralRisk peripheralRisk);
}
