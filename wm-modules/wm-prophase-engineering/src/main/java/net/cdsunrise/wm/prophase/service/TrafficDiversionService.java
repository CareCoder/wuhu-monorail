package net.cdsunrise.wm.prophase.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.prophase.entity.TrafficDiversion;
import net.cdsunrise.wm.prophase.vo.LocationVo;
import net.cdsunrise.wm.prophase.vo.TrafficDiversionSaveVo;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
public interface TrafficDiversionService {
    void save(TrafficDiversionSaveVo trafficDiversionSaveVo);

    void saveBatch(List<TrafficDiversion> trafficDiversion);

    void delete(Long id);

    TrafficDiversion findOne(Long id);

    Pager getPager(PageCondition condition);

    List<TrafficDiversion> selectList(TrafficDiversion trafficDiversion);

    List<LocationVo> allModelLocation();
}
