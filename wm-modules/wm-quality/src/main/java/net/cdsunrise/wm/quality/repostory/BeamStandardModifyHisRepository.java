package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.BeamStandardModifyHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/***
 * @author gechaoqing
 * 梁墩修改记录数据仓库
 */
public interface BeamStandardModifyHisRepository extends JpaRepository<BeamStandardModifyHis,String> {

    /***
     * 获取修改后还没有审核的记录
     * @param beamCode 梁号
     * @return
     */
    @Query("SELECT bsmf FROM BeamStandardModifyHis bsmf WHERE bsmf.beamCode=?1 AND bsmf.status='NONE'")
    BeamStandardModifyHis findForModifyCheck(String beamCode);
}
