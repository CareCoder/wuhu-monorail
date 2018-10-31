package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.base.util.BeanUtilIgnore;
import net.cdsunrise.wm.quality.entity.ChildNewsModule;
import net.cdsunrise.wm.quality.repostory.ChildNewsModuleRepository;
import net.cdsunrise.wm.quality.service.ChildNewsModuleService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ChildNewsModuleServiceImpl implements ChildNewsModuleService {
    @Resource
    private ChildNewsModuleRepository childNewsModuleRepository;


    @Override
    public List<ChildNewsModule> list() {
        return childNewsModuleRepository.findAll();
    }

    @Override
    public void add(ChildNewsModule childNewsModule) {
        Assert.notNull(childNewsModule.getPid(), "pid is null");
        childNewsModule.setCreateTime(new Date());
        childNewsModule.setModifyTime(new Date());
        childNewsModuleRepository.saveAndFlush(childNewsModule);
    }

    @Override
    public void update(ChildNewsModule childNewsModule) {
        Assert.notNull(childNewsModule.getId(), "id is null");
        ChildNewsModule one = childNewsModuleRepository.getOne(childNewsModule.getId());
        BeanUtilIgnore.copyPropertiesIgnoreNull(childNewsModule, one);
        one.setModifyTime(new Date());
        childNewsModuleRepository.saveAndFlush(one);
    }

    @Override
    public List<ChildNewsModule> findByPid(Long pid) {
        return childNewsModuleRepository.findByPid(pid);
    }
}
