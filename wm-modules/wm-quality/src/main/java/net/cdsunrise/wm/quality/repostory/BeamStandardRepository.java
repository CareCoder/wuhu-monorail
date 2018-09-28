package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.BeamStandard;
import net.cdsunrise.wm.quality.vo.BeamDeviationsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/***
 * @author gechaoqing
 * 梁墩标准数据仓库
 */
public interface BeamStandardRepository  extends JpaRepository<BeamStandard,String> {
    /***
     * 更新梁综合参数偏差
     * @param deviation 偏差数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("UPDATE BeamStandard SET lengthDeviation=:#{#deviation.lengthDeviation},heightDeviation=:#{#deviation.heightDeviation} WHERE beamCode=:#{#deviation.beamCode}")
    void updateBeamDeviations(@Param("deviation") BeamDeviationsVo deviation);

    /***
     * 分页查询有更新的数据
     * @param pageable 分页
     * @return
     */
    Page<BeamStandard> findByModifyTimeIsNotNull(Pageable pageable);

}
