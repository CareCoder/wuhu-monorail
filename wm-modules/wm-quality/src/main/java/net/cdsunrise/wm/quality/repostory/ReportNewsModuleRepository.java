package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.ReportNewsModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportNewsModuleRepository extends JpaRepository<ReportNewsModule, Long> {
    List<ReportNewsModule> findByPid(Long pid);
}
