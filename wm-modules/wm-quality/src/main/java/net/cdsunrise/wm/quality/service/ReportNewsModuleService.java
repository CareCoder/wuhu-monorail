package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.ReportNewsModule;

import java.util.List;

public interface ReportNewsModuleService {
    List<ReportNewsModule> list();

    void add(ReportNewsModule reportNewsModule);

    void update(ReportNewsModule reportNewsModule);

    List<ReportNewsModule> findByPid(Long pid);
}
