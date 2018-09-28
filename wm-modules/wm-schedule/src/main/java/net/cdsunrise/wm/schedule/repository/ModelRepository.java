package net.cdsunrise.wm.schedule.repository;


import net.cdsunrise.wm.schedule.entity.Model;
import net.cdsunrise.wm.schedule.entity.ModelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/***
 * @author gechaoqing
 * 模型信息仓库
 */
public interface ModelRepository extends JpaRepository<Model,Long> {
    /***
     * 批量删除模型数据
     * @param ids ID列表
     */
    @Modifying
    @Query(value = "DELETE FROM Model WHERE id IN ?1")
    void deleteIdIn(Long[] ids);

    /***
     * 根据模型ID获取模型数据
     * @param uniqueId 模型唯一标识
     * @return 模型
     */
    Model findByUniqueId(String uniqueId);

    /**
     * 根据模型状态获取模型数据
     * @param modelStatus 模型状态
     * @return 模型
     */
    List<Model> findByModelStatus(Integer modelStatus);
}
