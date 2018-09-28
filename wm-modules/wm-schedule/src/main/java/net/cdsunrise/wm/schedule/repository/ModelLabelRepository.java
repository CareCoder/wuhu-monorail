package net.cdsunrise.wm.schedule.repository;


import net.cdsunrise.wm.schedule.entity.ModelLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * @author gechaoqing
 * 模型标签信息仓库
 */
public interface ModelLabelRepository extends JpaRepository<ModelLabel,Long> {
    /***
     * 根据模型ID获取
     * @param modelId 模型ID
     * @return
     */
    List<ModelLabel> findByModelId(Long modelId);
}
