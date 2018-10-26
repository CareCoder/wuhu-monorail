package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.WorkPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkPointRepository extends JpaRepository<WorkPoint, Long> {

    @Query(value = "select wp from WorkPoint wp where wp.carPointId = ?1")
    List<WorkPoint> query(Long carPointId);
}
