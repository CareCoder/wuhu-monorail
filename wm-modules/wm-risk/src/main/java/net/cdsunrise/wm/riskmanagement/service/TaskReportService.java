package net.cdsunrise.wm.riskmanagement.service;

import net.cdsunrise.wm.riskmanagement.bo.TaskReportBo;
import net.cdsunrise.wm.riskmanagement.entity.TaskReport;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
public interface TaskReportService {
    void save(TaskReport report);

    TaskReport findOne(Long id);

    List<TaskReportBo> findBoByTaskId(Long taskId);

    void deleteByTaskId(Long taskId);
}
