package net.cdsunrise.wm.riskmanagement.repository;

import net.cdsunrise.wm.riskmanagement.entity.TaskReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
public interface TaskReportRepository extends JpaRepository<TaskReport, Long> {
    List<TaskReport> findByTaskId(Long taskId);

    void deleteByTaskId(Long taskId);
}
