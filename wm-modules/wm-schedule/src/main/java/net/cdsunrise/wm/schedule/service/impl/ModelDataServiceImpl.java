package net.cdsunrise.wm.schedule.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.schedule.entity.Model;
import net.cdsunrise.wm.schedule.entity.ModelCategory;
import net.cdsunrise.wm.schedule.entity.ModelData;
import net.cdsunrise.wm.schedule.entity.ModelDataCol;
import net.cdsunrise.wm.schedule.repository.ModelCategoryRepository;
import net.cdsunrise.wm.schedule.repository.ModelDataColRepository;
import net.cdsunrise.wm.schedule.repository.ModelDataRepository;
import net.cdsunrise.wm.schedule.repository.ModelRepository;
import net.cdsunrise.wm.schedule.service.ModelDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author gechaoqing
 * 模型数据服务
 */
@Service
public class ModelDataServiceImpl implements ModelDataService{
    private ModelDataRepository dataRepository;
    private ModelDataColRepository cellRepository;
    private ModelCategoryRepository modelCategoryRepository;
    private ModelRepository modelRepository;
    private CommonDAO commonDAO;
    public ModelDataServiceImpl(ModelDataRepository dataRepository
            ,ModelDataColRepository cellRepository
            ,CommonDAO commonDAO
            ,ModelCategoryRepository modelCategoryRepository
            ,ModelRepository modelRepository){
        this.dataRepository = dataRepository;
        this.cellRepository = cellRepository;
        this.commonDAO = commonDAO;
        this.modelCategoryRepository = modelCategoryRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public void save(Model model) {
        modelRepository.save(model);
    }

    @Override
    public void save(ModelCategory category) {
        modelCategoryRepository.save(category);
    }

    @Override
    public void save(ModelData modelData) {
        dataRepository.save(modelData);
    }

    @Override
    public void save(ModelDataCol extraCell) {
        cellRepository.save(extraCell);
    }

    @Override
    public void deleteModel(Long[] ids) {
        modelRepository.deleteIdIn(ids);
    }

    @Override
    public void deleteModelCategory(Long[] ids) {
        modelCategoryRepository.deleteIdIn(ids);
    }

    @Override
    public void deleteModelData(Long[] ids) {
        dataRepository.deleteIdIn(ids);
    }

    @Override
    public void deleteExtraCell(Long[] ids) {
        cellRepository.deleteIdIn(ids);
    }

    @Override
    public Pager<Model> getModelPage(PageCondition pageCondition) {
        QueryHelper helper = new QueryHelper(Model.class, "l").useNativeSql(false).setPageCondition(pageCondition);
        return commonDAO.findPager(helper);
    }

    @Override
    public List<ModelCategory> getAllModelCategory() {
        return modelCategoryRepository.findAll();
    }

    @Override
    public List<ModelData> getModelDataByModelId(Long modelId) {
        return dataRepository.findByModelId(modelId);
    }

    @Override
    public List<ModelData> getModelDataByModelUniqueId(String uniqueId) {
        return dataRepository.findByUniqueId(uniqueId);
    }

    @Override
    public List<ModelDataCol> getAllExtraCells() {
        return cellRepository.findAll();
    }

    @Override
    public void setCategoryCols(Long categoryId, List<Long> colIds) {
        modelCategoryRepository.deleteRefColByCategoryId(categoryId);
        if(colIds!=null&&!colIds.isEmpty()){
            for(Long id:colIds){
                modelCategoryRepository.insertRefCol(categoryId,id);
            }
        }
    }
}
