package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.GirderPier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * @author gechaoqing
 * 梁墩的数据仓库
 */
public interface GirderPierRepository extends JpaRepository<GirderPier,Long> {
    /***
     * 根据梁号获取梁端数据
     * @param girderCode 梁号
     * @return 梁端数据
     */
    List<GirderPier> findByGirderCode(String girderCode);

    /***
     * 获取梁墩对应的
     * @param id 墩ID
     * @param pierCode 墩编号
     * @return 墩数据
     */
    GirderPier findByIdNotAndPierCode(Long id,String pierCode);
}
