package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.base.util.BeanUtilIgnore;
import net.cdsunrise.wm.quality.entity.WorkPoint;
import net.cdsunrise.wm.quality.repostory.WorkPointRepository;
import net.cdsunrise.wm.quality.service.WorkPointService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
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
    public WorkPoint add(WorkPoint workPoint) {
        return workPointRepository.saveAndFlush(workPoint);
    }

    @Override
    public List<WorkPoint> query(Long carPointId) {
        return workPointRepository.query(carPointId);
    }

    @Override
    public void delete(Long id) {
        workPointRepository.delete(id);
    }

    @Override
    public void update(WorkPoint workPoint) {
        Assert.notNull(workPoint.getId(), "id is null");
        WorkPoint one = workPointRepository.findOne(workPoint.getId());
        BeanUtilIgnore.copyPropertiesIgnoreNull(workPoint, one);
        one.setModifyTime(new Date());
        workPointRepository.saveAndFlush(one);
    }
}
