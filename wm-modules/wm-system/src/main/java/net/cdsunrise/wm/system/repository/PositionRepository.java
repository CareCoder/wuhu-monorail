package net.cdsunrise.wm.system.repository;

import net.cdsunrise.wm.system.entity.Department;
import net.cdsunrise.wm.system.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
public interface PositionRepository extends JpaRepository<Position,Long> {

    List<Position> findByDepartment(Department department);
}
