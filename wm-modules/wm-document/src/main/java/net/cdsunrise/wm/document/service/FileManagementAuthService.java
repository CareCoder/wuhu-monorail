package net.cdsunrise.wm.document.service;

import net.cdsunrise.wm.document.entity.FileManagementAuth;

import java.util.List;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/2/002
 * Describe:
 */
public interface FileManagementAuthService {

    void deleteByFileId(Long fileId);

    void add(List<FileManagementAuth> list);
}
