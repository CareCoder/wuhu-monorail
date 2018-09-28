package net.cdsunrise.wm.document.repository;

import net.cdsunrise.wm.document.entity.FileManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/1/001
 * Describe:
 */
public interface FileManagementRepository extends JpaRepository<FileManagement,Long> {

    List<FileManagement> findByUploadPersonIdAndParentIdOrderByTypeAsc(Long uploadPersonId,Long parentId);

    List<FileManagement> findByUploadPersonIdAndIdOrderByTypeAsc(Long uploadPersonId,Long id);

    FileManagement findByParentId(Long parentId);
}
