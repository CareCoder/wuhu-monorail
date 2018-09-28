package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.PierDisStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/***
 * @author gechaoqing
 * 墩的平距和高差数据仓库
 */
public interface PierDisStandardRepository extends JpaRepository<PierDisStandard,Long> {
    /***
     * 更新偏差值
     * @param flatDeviation 平距偏差
     * @param heightDeviation 高差偏差
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("UPDATE PierDisStandard SET flatDeviation=?1,heightDeviation=?2 WHERE id=?3 ")
    void update(Double flatDeviation,Double heightDeviation,Long id);
}
