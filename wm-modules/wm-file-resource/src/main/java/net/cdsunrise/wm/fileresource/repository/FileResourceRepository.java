package net.cdsunrise.wm.fileresource.repository;

import net.cdsunrise.wm.fileresource.entity.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lijun
 * @date 2018-04-20.
 */
public interface FileResourceRepository extends JpaRepository<FileResource, Long> {
}
