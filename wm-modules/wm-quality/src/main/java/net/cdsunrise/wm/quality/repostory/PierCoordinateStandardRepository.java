package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.PierCoordinateStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/***
 * @author gechaoqing
 * 坐标标准数据
 */
public interface PierCoordinateStandardRepository extends JpaRepository<PierCoordinateStandard,Long> {

    /***
     * 保存坐标标准数据
     * @param id ID
     * @param x x
     * @param y y
     * @param z z
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("UPDATE PierCoordinateStandard SET xDeviation=?2,yDeviation=?3,zDeviation=?4 WHERE id=?1")
    void updateDeviation(Long id,Double x,Double y,Double z);
}
