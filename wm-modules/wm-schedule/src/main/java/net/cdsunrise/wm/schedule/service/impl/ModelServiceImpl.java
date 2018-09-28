package net.cdsunrise.wm.schedule.service.impl;

import net.cdsunrise.wm.schedule.entity.Model;
import net.cdsunrise.wm.schedule.repository.ModelRepository;
import net.cdsunrise.wm.schedule.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelRepository modelRepository;
    @Override
    public List<Model> findByModelStatus(Integer modelStatus) {
        return modelRepository.findByModelStatus(modelStatus);
    }
}
