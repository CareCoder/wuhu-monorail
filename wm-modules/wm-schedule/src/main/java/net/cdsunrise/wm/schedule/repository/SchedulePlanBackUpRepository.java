package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.SchedulePlanBackUp;
import org.springframework.data.jpa.repository.JpaRepository;

/****
 * @author gechaoqing
 * 进度计划数据仓库
 */
public interface SchedulePlanBackUpRepository extends JpaRepository<SchedulePlanBackUp,Long> {

}
