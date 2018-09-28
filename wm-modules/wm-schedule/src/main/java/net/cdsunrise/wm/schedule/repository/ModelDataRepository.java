package net.cdsunrise.wm.schedule.repository;


import net.cdsunrise.wm.schedule.entity.ModelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/***
 * @author gechaoqing
 * 模型数据信息仓库
 */
public interface ModelDataRepository extends JpaRepository<ModelData,Long> {
    /***
     * 批量删除模型数据
     * @param ids ID列表
     */
    @Modifying
    @Query(value = "DELETE FROM ModelData WHERE id IN ?1")
    void deleteIdIn(Long[] ids);

    /***
     * 根据模型ID获取模型数据
     * @param modelId 模型ID
     * @return 模型数据列表
     */
    List<ModelData> findByModelId(Long modelId);

    /***
     * 根据模型唯一标识获取模型数据
     * @param uniqueId 唯一标识
     * @return 模型数据列表
     */
    @Query(value = "SELECT * FROM wm_model_data md WHERE md.model_id=(SELECT m.id FROM wm_model m WHERE m.unique_id=?1 )",nativeQuery = true)
    List<ModelData> findByUniqueId(String uniqueId);
}
