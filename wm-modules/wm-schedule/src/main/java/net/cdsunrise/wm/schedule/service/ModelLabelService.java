package net.cdsunrise.wm.schedule.service;

import net.cdsunrise.wm.schedule.entity.ModelLabel;

import java.util.List;
import java.util.Map;

/***
 * @author gechaoqing
 * 模型标签服务
 */
public interface ModelLabelService {
    /***
     * 保存模型标签
     * @param modelLabel 模型标签
     */
    void save(ModelLabel modelLabel);

    /***
     * 获取所有模型标签
     * @return 模型标签列表
     */
    Map<Long,List<ModelLabel>> getAll();

    /***
     * 根据模型ID获取标签列表
     * @param modelId 模型ID
     * @return
     */
    List<ModelLabel> getByModelId(Long modelId);

    /***
     * 删除指定ID列表的模型标签
     * @param idArray
     */
    void delete(Long[] idArray);
}
