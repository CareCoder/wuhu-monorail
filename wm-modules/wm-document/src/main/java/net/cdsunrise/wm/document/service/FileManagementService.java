package net.cdsunrise.wm.document.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.document.entity.FileManagement;
import net.cdsunrise.wm.document.vo.CurrentFolderVo;
import net.cdsunrise.wm.document.vo.FileManagementResultVo;
import net.cdsunrise.wm.document.vo.FileManagementUploadVo;


/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/1/001
 * Describe:
 */
public interface FileManagementService {

    void save(FileManagementUploadVo fileManagementUploadVo);

    void delete(Long id);

    FileManagement getOne(Long id);

    CurrentFolderVo getAll(Long parentFolderId);

    Pager<FileManagementResultVo> shareFilePage(PageCondition condition);
}
