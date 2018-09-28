package net.cdsunrise.wm.virtualconstruction.repository;

import net.cdsunrise.wm.virtualconstruction.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lijun
 * @date 2018-04-20.
 */
public interface VideoRepository extends JpaRepository<Video, Long> {
}
