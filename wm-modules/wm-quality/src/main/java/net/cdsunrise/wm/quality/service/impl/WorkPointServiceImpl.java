package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.base.util.BeanUtilIgnore;
import net.cdsunrise.wm.quality.entity.Teamwork;
import net.cdsunrise.wm.quality.entity.WorkPoint;
import net.cdsunrise.wm.quality.repostory.WorkPointRepository;
import net.cdsunrise.wm.quality.service.TeamworkService;
import net.cdsunrise.wm.quality.service.WorkPointService;
import net.cdsunrise.wm.quality.vo.TeamworkVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkPointServiceImpl implements WorkPointService {
    @Resource
    private WorkPointRepository workPointRepository;
    @Resource
    private TeamworkService teamworkService;


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
        Teamwork teamwork = new Teamwork();
        teamwork.setWorkPointId(id);
        List<TeamworkVo> teamworkVos = teamworkService.query(teamwork);
        Long[] ids = teamworkVos.stream().map(TeamworkVo::getId).toArray(Long[]::new);
        teamworkService.delete(ids);
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
