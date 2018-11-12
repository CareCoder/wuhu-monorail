package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.base.util.BeanUtilIgnore;
import net.cdsunrise.wm.quality.entity.ReportNewsModule;
import net.cdsunrise.wm.quality.repostory.ReportNewsModuleRepository;
import net.cdsunrise.wm.quality.service.ReportNewsModuleService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ReportNewsModuleServiceImpl implements ReportNewsModuleService {
    @Resource
    private ReportNewsModuleRepository reportNewsModuleRepository;


    @Override
    public List<ReportNewsModule> list() {
        return reportNewsModuleRepository.findAll();
    }

    @Override
    public void add(ReportNewsModule reportNewsModule) {
        Assert.notNull(reportNewsModule.getPid(), "pid is null");
        reportNewsModule.setCreateTime(new Date());
        reportNewsModule.setModifyTime(new Date());
        reportNewsModuleRepository.saveAndFlush(reportNewsModule);
    }

    @Override
    public void update(ReportNewsModule childNewsModule) {
        Assert.notNull(childNewsModule.getId(), "id is null");
        ReportNewsModule one = reportNewsModuleRepository.getOne(childNewsModule.getId());
        BeanUtilIgnore.copyPropertiesIgnoreNull(childNewsModule, one);
        one.setModifyTime(new Date());
        reportNewsModuleRepository.saveAndFlush(one);
    }

    @Override
    public List<ReportNewsModule> findByPid(Long pid) {
        return reportNewsModuleRepository.findByPid(pid);
    }
}
