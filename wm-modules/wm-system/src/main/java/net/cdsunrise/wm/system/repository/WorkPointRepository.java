package net.cdsunrise.wm.system.repository;

import net.cdsunrise.wm.system.entity.WorkPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * @author gechaoqing
 * 工点数据仓库
 */
public interface WorkPointRepository extends JpaRepository<WorkPoint,Long> {
    /**
     * 根据线路ID获取该线路下所有工点/区间
     * @param lineId 线路ID
     * @return 工点/区间列表
     */
    List<WorkPoint> findByLineId(Long lineId);

    /***
     * 根据线路ID和工点类型获取数据列表
     * @param lineId 线路ID
     * @param type 工点类型
     * @return 工点数据列表
     */
    List<WorkPoint> findByLineIdAndType(Long lineId,String type);

    /***
     * 根据线路ID删除线路下所有工点/区间数据
     * @param lineId 线路ID
     */
    void deleteByLineId(Long lineId);
}
