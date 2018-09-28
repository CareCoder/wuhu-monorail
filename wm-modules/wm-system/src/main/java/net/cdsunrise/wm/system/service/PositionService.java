package net.cdsunrise.wm.system.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.system.entity.Position;
import net.cdsunrise.wm.system.vo.PositionVo;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
public interface PositionService {

    void save(PositionVo positionVo);

    void delete(Long id);

    PositionVo findOne(Long id);

    Pager<PositionVo> getPager(PageCondition condition);

    List<PositionVo> findAll();

    List<PositionVo> selectPositionByDept(Long deptId);
}
