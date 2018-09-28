package net.cdsunrise.wm.prophase.repository;

import net.cdsunrise.wm.prophase.entity.PeripheralRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
public interface PeripheralRiskRepository extends JpaRepository<PeripheralRisk,Long> {

    /***
     * 添加关联模型信息
     * @param fid FID
     * @param guid GUID
     */
    @Modifying
    @Query(value = "INSERT INTO wm_peripheral_risk_ref_model(peripheral_risk_id,fid,guid,model_id) VALUES(?1,?2,?3,?4)",nativeQuery = true)
    void insertRefModel(Long id,Long fid,String guid,Long modelId);

    /***
     * 删除关联模型信息
     */
    @Modifying
    @Query(value = "DELETE FROM wm_peripheral_risk_ref_model WHERE peripheral_risk_id=?1",nativeQuery = true)
    void deleteRefModel(Long id);
}
