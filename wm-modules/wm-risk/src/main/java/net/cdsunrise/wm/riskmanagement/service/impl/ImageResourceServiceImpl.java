package net.cdsunrise.wm.riskmanagement.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.exception.DataBaseRollBackException;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.util.FileUtils;
import net.cdsunrise.wm.riskmanagement.entity.ImageResource;
import net.cdsunrise.wm.riskmanagement.repository.ImageResourceRepository;
import net.cdsunrise.wm.riskmanagement.service.ImageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Autowired
    private CommonDAO commonDAO;

    @Override
    public List<ImageResource> upload(MultipartFile... multipartFiles) {
        List<ImageResource> imageResources = new ArrayList<>();
        //判断是否有文件上传
        if (multipartFiles == null || multipartFiles.length == 0) {
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
            throw new RuntimeException("图片上传出现");
        }
    }

    @Override
    public void deleteByTaskId(Long taskId) {
        QueryHelper helper = new QueryHelper("i.id, i.original_name originalName, new_name newName", "wm_image_resource i")
                .addJoin("join wm_inspection_screen_image si on si.image_id=i.id and si.task_id=?", taskId);
        List<ImageResource> imageResources = commonDAO.findList(helper, ImageResource.class);
        delete(imageResources);
    }

    @Override
    public void deleteByReportId(Long reportId) {
        QueryHelper helper = new QueryHelper("i.id, i.original_name originalName, new_name newName", "wm_image_resource i")
                .addJoin("join wm_report_image ri on ri.image_id=i.id and ri.report_id=?", reportId);
        List<ImageResource> imageResources = commonDAO.findList(helper, ImageResource.class);
        delete(imageResources);
    }

    private void delete(List<ImageResource> imageResources) {
        for (ImageResource image : imageResources) {
            try {
                Files.deleteIfExists(Paths.get(uploadImagePath + image.getNewName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageResourceRepository.delete(image);
        }
    }
}
