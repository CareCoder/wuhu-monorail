package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.BeamPierSupportStandard;
import net.cdsunrise.wm.quality.vo.BeamPierSupportDeviationsVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/***
 * @author gechaoqing
 * 墩临时支撑标准数据仓库
 */
public interface BeamPierSupportStandardRepository extends JpaRepository<BeamPierSupportStandard,Long> {

    /***
     * 更新断面偏差
     * @param deviation 偏差数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("UPDATE BeamPierSupportStandard SET centerDeviationVal=:#{#deviation.centerDeviationVal},heightDeviationVal=:#{#deviation.heightDeviationVal} WHERE id=:#{#deviation.id}")
    void updateDeviations(@Param("deviation") BeamPierSupportDeviationsVo deviation);
}
