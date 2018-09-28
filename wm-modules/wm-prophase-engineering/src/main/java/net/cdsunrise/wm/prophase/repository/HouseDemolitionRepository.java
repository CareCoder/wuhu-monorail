package net.cdsunrise.wm.prophase.repository;

import net.cdsunrise.wm.prophase.entity.HouseDemolition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
public interface HouseDemolitionRepository extends JpaRepository<HouseDemolition,Long> {

    /***
     * 添加关联模型信息
     * @param fid FID
     * @param guid GUID
     */
    @Modifying
    @Query(value = "INSERT INTO wm_house_demolition_ref_model(house_dl_id,fid,guid,model_id) VALUES(?1,?2,?3,?4)",nativeQuery = true)
    void insertRefModel(Long id,Long fid,String guid,Long modelId);

    /***
     * 删除关联模型信息
     */
    @Modifying
    @Query(value = "DELETE FROM wm_house_demolition_ref_model WHERE house_dl_id=?1",nativeQuery = true)
    void deleteRefModel(Long id);

}
