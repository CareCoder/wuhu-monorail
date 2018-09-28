package net.cdsunrise.wm.hiddentrouble.service.impl;

import net.cdsunrise.wm.base.exception.DataBaseRollBackException;
import net.cdsunrise.wm.base.util.FileUtils;
import net.cdsunrise.wm.hiddentrouble.entity.ImageResource;
import net.cdsunrise.wm.hiddentrouble.repository.ImageResourceRepository;
import net.cdsunrise.wm.hiddentrouble.service.ImageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-23.
 * @descritpion
 */
@Transactional(rollbackFor = {DataBaseRollBackException.class})
@Service
public class ImageResourceServiceImpl implements ImageResourceService {

    @Value("${uploadImagePath}")
    private String uploadImagePath;

    @Autowired
    private ImageResourceRepository imageResourceRepository;

    @Override
    public List<ImageResource> upload(MultipartFile... multipartFiles) {
        List<ImageResource> imageResources = new ArrayList<>();
        //判断是否有文件上传
        if (multipartFiles.length == 0) {
            return null;
        }
        try {
            Map<String, String> fileMap = FileUtils.upload2(uploadImagePath, multipartFiles);
            fileMap.forEach((k, v) -> {
                ImageResource imageResource = new ImageResource();
                imageResource.setNewName(k);
                imageResource.setOriginalName(v);
                imageResources.add(imageResource);
            });
            imageResourceRepository.save(imageResources);
            return imageResources;
        } catch (IOException e) {
            throw new RuntimeException("图片上传失败");
        }

    }
}
