package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.ChildNewsModule;
import net.cdsunrise.wm.quality.entity.NewsModule;

import java.util.List;

public interface ChildNewsModuleService {
    List<ChildNewsModule> list();

    void add(ChildNewsModule childNewsModule);

    void update(ChildNewsModule childNewsModule);

    List<ChildNewsModule> findByPid(Long pid);
}
