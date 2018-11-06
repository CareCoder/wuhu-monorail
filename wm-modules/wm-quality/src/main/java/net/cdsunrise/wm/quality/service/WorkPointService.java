package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.WorkPoint;

import java.util.List;

public interface WorkPointService {
    List<WorkPoint> list();

    WorkPoint add(WorkPoint workPoint);

    List<WorkPoint> query(Long carPointId);

    /**
     * 删除文件夹及文件夹下面的所有文件
     * @param id
     */
    void delete(Long id);

    void update(WorkPoint workPoint);
}
