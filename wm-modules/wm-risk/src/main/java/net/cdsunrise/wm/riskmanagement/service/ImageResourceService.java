package net.cdsunrise.wm.riskmanagement.service;

import net.cdsunrise.wm.riskmanagement.bo.ImageResourceBo;
import net.cdsunrise.wm.riskmanagement.entity.ImageResource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-23.
 * @descritpion
 */
public interface ImageResourceService {
    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    List<ImageResource> upload(MultipartFile... multipartFile);

    /**
     * 根据任务ID 删除图片
     *
     * @param taskId
     */
    void deleteByTaskId(Long taskId);

    /**
     * 根据上报信息删除图片
     *
     * @param reportId
     */
    void deleteByReportId(Long reportId);
}
