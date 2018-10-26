package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.Teamwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarAndWorkPointRepository extends JpaRepository<Teamwork, Long> {

}
