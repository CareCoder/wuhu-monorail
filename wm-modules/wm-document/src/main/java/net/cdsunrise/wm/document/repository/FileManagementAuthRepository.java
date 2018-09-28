package net.cdsunrise.wm.document.repository;

import net.cdsunrise.wm.document.entity.FileManagementAuth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/2/002
 * Describe:
 */
public interface FileManagementAuthRepository extends JpaRepository<FileManagementAuth,Long> {

    void deleteByFileId(Long fileId);


}
