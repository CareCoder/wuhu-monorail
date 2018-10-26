package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.quality.entity.WorkPoint;
import net.cdsunrise.wm.quality.repostory.WorkPointRepository;
import net.cdsunrise.wm.quality.service.WorkPointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkPointServiceImpl implements WorkPointService {
    @Resource
    private WorkPointRepository workPointRepository;


    @Override
    public List<WorkPoint> list() {
        return workPointRepository.findAll();
    }

    @Override
    public void add(WorkPoint workPoint) {
        workPointRepository.save(workPoint);
    }

    @Override
    public List<WorkPoint> query(Long carPointId) {
        return workPointRepository.query(carPointId);
    }
}
