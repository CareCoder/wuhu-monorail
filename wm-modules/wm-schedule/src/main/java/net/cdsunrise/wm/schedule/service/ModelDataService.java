package net.cdsunrise.wm.schedule.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.schedule.entity.Model;
import net.cdsunrise.wm.schedule.entity.ModelCategory;
import net.cdsunrise.wm.schedule.entity.ModelData;
import net.cdsunrise.wm.schedule.entity.ModelDataCol;

import java.util.List;

/***
 * @author gechaoqing
 * 模型数据服务
 */
public interface ModelDataService {
    /***
     * 保存模型
     * @param model 模型
     */
    void save(Model model);

    /***
     * 保存模型分类
     * @param category 分类
     */
    void save(ModelCategory category);

    /***
     * 保存模型数据
     * @param modelData 模型数据
     */
    void save(ModelData modelData);

    /***
     * 保存模型附加数据字段定义
     * @param extraCell 字段定义信息
     */
    void save(ModelDataCol extraCell);

    /***
     * 删除模型
     * @param ids 模型ID
     */
    void deleteModel(Long[] ids);

    /***
     * 删除模型分类
     * @param ids 模型分类ID
     */
    void deleteModelCategory(Long[] ids);

    /***
     * 删除模型数据
     * @param ids 模型数据ID
     */
    void deleteModelData(Long[] ids);

    /***
     * 删除模型附加数据字段定义
     * @param ids 字段定义ID
     */
    void deleteExtraCell(Long[] ids);

    /***
     * 模型分页查询
     * @param pageCondition 分页查询条件
     * @return 模型数据
     */
    Pager<Model> getModelPage(PageCondition pageCondition);

    /***
     * 获取所有模型分类
     * @return 模型分类列表
     */
    List<ModelCategory> getAllModelCategory();

    /***
     * 根据模型ID获取模型数据
     * @param modelId 模型ID
     * @return 模型数据
     */
    List<ModelData> getModelDataByModelId(Long modelId);

    /***
     * 根据模型唯一标识获取模型数据
     * @param uniqueId 模型唯一标识
     * @return 模型数据列表
     */
    List<ModelData> getModelDataByModelUniqueId(String uniqueId);

    /***
     * 获取所有模型附加数据的字段定义
     * @return 字段定义列表
     */
    List<ModelDataCol> getAllExtraCells();

    /***
     * 设置模型分类关联字段
     * @param categoryId 分类ID
     * @param colIds 列ID
     */
    void setCategoryCols(Long categoryId,List<Long> colIds);
}
