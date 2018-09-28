package net.cdsunrise.wm.system.repository;

import net.cdsunrise.wm.system.entity.ProjectLine;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * @author gechaoqing
 * 线路数据仓库
 */
public interface ProjectLineRepository extends JpaRepository<ProjectLine,Long> {
}
