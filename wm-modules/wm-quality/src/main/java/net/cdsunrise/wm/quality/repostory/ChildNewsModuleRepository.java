package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.ChildNewsModule;
import net.cdsunrise.wm.quality.entity.NewsModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildNewsModuleRepository extends JpaRepository<ChildNewsModule, Long> {
}
