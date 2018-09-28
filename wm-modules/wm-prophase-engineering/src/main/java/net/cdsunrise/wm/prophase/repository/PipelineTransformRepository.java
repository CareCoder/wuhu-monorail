package net.cdsunrise.wm.prophase.repository;

import net.cdsunrise.wm.prophase.entity.PipelineTransform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
public interface PipelineTransformRepository extends JpaRepository<PipelineTransform,Long> {

    /***
     * 添加关联模型信息
     * @param fid FID
     * @param guid GUID
     */
    @Modifying
    @Query(value = "INSERT INTO wm_pipeline_transform_ref_model(pipeline_transform_id,fid,guid,model_id) VALUES(?1,?2,?3,?4)",nativeQuery = true)
    void insertRefModel(Long id,Long fid,String guid,Long modelId);

    /***
     * 删除关联模型信息
     */
    @Modifying
    @Query(value = "DELETE FROM wm_pipeline_transform_ref_model WHERE pipeline_transform_id=?1",nativeQuery = true)
    void deleteRefModel(Long id);
}
