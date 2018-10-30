package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.WorkPoint;

import java.util.List;

public interface WorkPointService {
    List<WorkPoint> list();

    void add(WorkPoint workPoint);

    List<WorkPoint> query(Long carPointId);

    void delete(Long id);

    void update(WorkPoint workPoint);
}
