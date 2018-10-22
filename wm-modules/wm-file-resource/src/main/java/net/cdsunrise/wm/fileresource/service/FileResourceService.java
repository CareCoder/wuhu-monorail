package net.cdsunrise.wm.fileresource.service;

import net.cdsunrise.wm.fileresource.bo.FileResourceBo;
import net.cdsunrise.wm.fileresource.entity.FileResource;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
public interface FileResourceService {
    FileResourceBo get(Long id);

    List<FileResourceBo> findAll();

    void upload(FileResource fileResource);

    FileResourceBo getByUuid(String uuid);

    void deleteByUuid(String uuid);
}
