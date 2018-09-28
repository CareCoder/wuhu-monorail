package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.BeamSectionStandard;
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
 * 梁断面标准数据仓库
 */
public interface BeamSectionStandardRepository extends JpaRepository<BeamSectionStandard,Long> {
    /***
     * 更新断面偏差
     * @param deviation 偏差数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("UPDATE BeamSectionStandard SET topWidthDeviation=:#{#deviation.topWidthDeviation},bottomWidthDeviation=:#{#deviation.bottomWidthDeviation},leftHeightDeviation=:#{#deviation.leftHeightDeviation},rightHeightDeviation=:#{#deviation.rightHeightDeviation} WHERE id=:#{#deviation.id}")
    void updateDeviations(@Param("deviation") BeamSectionDeviationsVo deviation);

}
