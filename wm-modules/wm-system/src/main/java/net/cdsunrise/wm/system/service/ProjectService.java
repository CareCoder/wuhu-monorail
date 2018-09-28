package net.cdsunrise.wm.system.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.system.entity.ProjectLine;
import net.cdsunrise.wm.system.entity.WorkPoint;

import java.util.List;

/***
 * @author gechaoqing
 * 项目服务
 * 线路、工点数据服务
 */
public interface ProjectService {
    /***
     * 分页查询线路
     * @param condition 查询条件
     * @return 分页数据
     */
    Pager getLinePager(PageCondition condition);

    /***
     * 分页查询工点
     * @param condition 查询条件
     * @return 分页数据
     */
    Pager getWorkPointPager(PageCondition condition);

    /***
     * 保存线路数据
     * @param line 线路数据
     */
    void save(ProjectLine line);

    /***
     * 保存工点数据
     * @param workPoint 工点数据
     */
    void save(WorkPoint workPoint);

    /***
     * 删除线路数据
     * @param lineId 线路ID
     */
    void deleteLine(Long lineId);

    /***
     * 删除工点数据
     * @param workPointId 工点ID
     */
    void deleteWorkPoint(Long workPointId);

    /***
     * 根据线路获取工点集合
     * @param lineId 线路ID
     * @return 工点集合
     */
    List<WorkPoint> getByLine(Long lineId);

    /***
     * 根据线路获取不同类型的工点集合
     * @param lineId 线路ID
     * @param type 工点类型   站/区间
     * @return 工点集合
     */
    List<WorkPoint> getByLineAndType(Long lineId,String type);

    /***
     * 根据线路ID获取线路详情
     * @param id 线路ID
     * @return 线路详情
     */
    ProjectLine getLineById(Long id);

    /***
     * 根据工点ID获取工点信息
     * @param id 工点ID
     * @return 工点信息
     */
    WorkPoint getWorkPointById(Long id);
}
