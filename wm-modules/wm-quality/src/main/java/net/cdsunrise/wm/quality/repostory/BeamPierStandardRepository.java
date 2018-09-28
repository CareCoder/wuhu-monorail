package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.BeamPierStandard;
import net.cdsunrise.wm.quality.entity.BeamStandard;
import net.cdsunrise.wm.quality.vo.BeamDeviationsVo;
import net.cdsunrise.wm.quality.vo.BeamSectionDeviationsVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/***
 * @author gechaoqing
 * 墩标准数据仓库
 */
public interface BeamPierStandardRepository extends JpaRepository<BeamPierStandard,Long> {
    /***
     * 获取墩的对立面标准数据
     * @param id 墩ID
     * @param pierCode 墩编号
     * @param beamCode 梁号
     * @return 墩数据
     */
    @Query(value = "SELECT * FROM wm_beam_pier_standard WHERE id<>?1 AND pier_code=?2 AND beam_standard_id LIKE ?3",nativeQuery = true)
    BeamPierStandard findBeamPierInverse(Long id, String pierCode, String beamCode);

    /***
     * 更新断面偏差
     * @param deviation 偏差数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("UPDATE BeamPierStandard SET topWidthDeviation=:#{#deviation.topWidthDeviation},bottomWidthDeviation=:#{#deviation.bottomWidthDeviation},leftHeightDeviation=:#{#deviation.leftHeightDeviation},rightHeightDeviation=:#{#deviation.rightHeightDeviation} WHERE id=:#{#deviation.id}")
    void updateDeviations(@Param("deviation") BeamSectionDeviationsVo deviation);
}
