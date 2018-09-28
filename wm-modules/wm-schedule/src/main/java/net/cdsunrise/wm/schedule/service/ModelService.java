package net.cdsunrise.wm.schedule.service;

import net.cdsunrise.wm.schedule.entity.Model;

import java.util.List;

public interface ModelService {
    public List<Model> findByModelStatus(Integer modelStatus);
}
