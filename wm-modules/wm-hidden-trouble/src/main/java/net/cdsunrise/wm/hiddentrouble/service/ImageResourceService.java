package net.cdsunrise.wm.hiddentrouble.service;

import net.cdsunrise.wm.hiddentrouble.entity.ImageResource;
import org.springframework.web.multipart.MultipartFile;

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
}
