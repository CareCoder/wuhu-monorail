package net.cdsunrise.wm.schedule.service.impl;

import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.schedule.entity.ModelLabel;
import net.cdsunrise.wm.schedule.repository.ModelLabelRepository;
import net.cdsunrise.wm.schedule.service.ModelLabelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author gechaoqing
 * 模型标签服务实现
 */
@Service
public class ModelLabelServiceImpl implements ModelLabelService {
    private ModelLabelRepository modelLabelRepository;

    public ModelLabelServiceImpl(ModelLabelRepository modelLabelRepository){
        this.modelLabelRepository = modelLabelRepository;
    }

    @Override
    public void save(ModelLabel modelLabel) {
        modelLabel.setCreateUserId(UserUtils.getUserId());
        modelLabelRepository.save(modelLabel);
    }

    @Override
    public Map<Long,List<ModelLabel>> getAll() {
        List<ModelLabel> modelLabels = modelLabelRepository.findAll();
        Map<Long,List<ModelLabel>> modelLabelMap = new HashMap<>(10);
        for(ModelLabel modelLabel:modelLabels){
            modelLabelMap.computeIfAbsent(modelLabel.getModelId(),key->new ArrayList<>()).add(modelLabel);
        }
        return modelLabelMap;
    }

    @Override
    public List<ModelLabel> getByModelId(Long modelId) {
        return modelLabelRepository.findByModelId(modelId);
    }

    @Override
    public void delete(Long[] idArray) {
        if (idArray == null || idArray.length == 0) {
            throw new ServiceErrorException("删除列表为空，无法进行删除操作！");
        }
        for (Long id : idArray) {
            ModelLabel modelLabel = modelLabelRepository.findOne(id);
            if(modelLabel!=null){
                modelLabelRepository.delete(modelLabel);
            }
        }
    }
}
