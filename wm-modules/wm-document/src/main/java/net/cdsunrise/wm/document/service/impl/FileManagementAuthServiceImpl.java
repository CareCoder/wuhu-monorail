package net.cdsunrise.wm.document.service.impl;

import net.cdsunrise.wm.document.entity.FileManagementAuth;
import net.cdsunrise.wm.document.repository.FileManagementAuthRepository;
import net.cdsunrise.wm.document.service.FileManagementAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/2/002
 * Describe:
 */
@Service
public class FileManagementAuthServiceImpl implements FileManagementAuthService {

    @Autowired
    private FileManagementAuthRepository fileManagementAuthRepository;


    @Override
    public void deleteByFileId(Long fileId) {
        fileManagementAuthRepository.deleteByFileId(fileId);
    }

    @Override
    public void add(List<FileManagementAuth> list) {
        fileManagementAuthRepository.save(list);
    }
}
