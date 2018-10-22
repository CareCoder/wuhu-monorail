package net.cdsunrise.wm.fileresource.service.impl;

import net.cdsunrise.wm.fileresource.bo.FileResourceBo;
import net.cdsunrise.wm.fileresource.entity.FileResource;
import net.cdsunrise.wm.fileresource.repository.FileResourceRepository;
import net.cdsunrise.wm.fileresource.service.FileResourceService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Service
public class FileResourceResourceServiceImpl implements FileResourceService {
    @Autowired
    private FileResourceRepository fileResourceRepository;
    @Value("${spring.application.name}")
    private String serverName;
    @Value("${fileUrlPrefix}")
    private String fileUrlPrefix;
    @Value("${filePath}")
    private String filePath;

    @Override
    public FileResourceBo get(Long id) {
        FileResource fileResource = fileResourceRepository.findOne(id);
        return convertToBo(fileResource);
    }

    @Override
    public List<FileResourceBo> findAll() {
        List<FileResource> fileResources = fileResourceRepository.findAll();
        return convertToBos(fileResources);
    }

    @Override
    public void upload(FileResource fileResource) {
        fileResource.setCreateTime(new Date());
        fileResource.setModifyTime(new Date());
        fileResourceRepository.save(fileResource);
    }

    @Override
    public FileResourceBo getByUuid(String uuid) {
        FileResource fileResource = fileResourceRepository.getByUuid(uuid);
        return convertToBo(fileResource);
    }

    @Override
    public void deleteByUuid(String uuid) {
        FileResourceBo bo = getByUuid(uuid);
        String fileName = filePath + bo.getFileName();
        File file = new File(fileName);
        FileUtils.deleteQuietly(file);
        fileResourceRepository.deleteByUuid(uuid);
    }

    private List<FileResourceBo> convertToBos(List<FileResource> fileResources) {
        List<FileResourceBo> bos = new ArrayList<>();
        if (fileResources == null || fileResources.isEmpty()) {
            return bos;
        }
        fileResources.forEach(f ->
                bos.add(convertToBo(f))
        );
        return bos;
    }

    private FileResourceBo convertToBo(FileResource fileResource) {
        FileResourceBo bo = new FileResourceBo();
        BeanUtils.copyProperties(fileResource, bo);
        bo.setUrl("/" + serverName + fileUrlPrefix + fileResource.getFileName());
        return bo;
    }
}
