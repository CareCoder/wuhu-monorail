package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.BeamPierSupportStandardModifyHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/***
 * @author gechaoqing
 * 墩临时支撑修改记录数据仓库
 */
public interface BeamPierSupportStandardModifyHisRepository extends JpaRepository<BeamPierSupportStandardModifyHis,Long> {
    /***
     * 获取修改后还没有审核的记录
     * @param id 唯一标识
     * @return
     */
    @Query("SELECT bsmf FROM BeamPierSupportStandardModifyHis bsmf WHERE bsmf.id=?1 AND bsmf.status='NONE'")
    BeamPierSupportStandardModifyHis findForModifyCheck(Long id);
}
