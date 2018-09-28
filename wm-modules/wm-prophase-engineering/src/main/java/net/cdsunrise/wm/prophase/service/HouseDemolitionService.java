package net.cdsunrise.wm.prophase.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.prophase.entity.HDStatisticalData;
import net.cdsunrise.wm.prophase.entity.HouseDemolition;
import net.cdsunrise.wm.prophase.entity.HouseDemolitionRefModel;
import net.cdsunrise.wm.prophase.vo.HouseDemolitionSaveVo;

import java.util.HashMap;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
public interface HouseDemolitionService {
    void save(HouseDemolitionSaveVo saveVo);

    void saveBatch(List<HouseDemolition> houseDemolitionList);

    void delete(Long id);

    HouseDemolition findOne(Long id);

    Pager getPager(PageCondition condition);

    List<HouseDemolition> selectList(HouseDemolition houseDemolition);

    HDStatisticalData dataStat();

}
