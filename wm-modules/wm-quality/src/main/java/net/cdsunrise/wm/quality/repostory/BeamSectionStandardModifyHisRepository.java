package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.BeamSectionStandardModifyHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/***
 * @author gechaoqing
 * 梁断面修改历史数据仓库
 */
public interface BeamSectionStandardModifyHisRepository extends JpaRepository<BeamSectionStandardModifyHis,Long> {

    /***
     * 获取修改后还没有审核的记录
     * @param type 类型[PIER 梁端    BEAM 梁间]
     * @param id 唯一标识
     * @return
     */
    @Query("SELECT bsmf FROM BeamSectionStandardModifyHis bsmf WHERE bsmf.id=?1 AND bsmf.type=?2 AND bsmf.status='NONE'")
    BeamSectionStandardModifyHis findForModifyCheck(Long id,String type);
}
