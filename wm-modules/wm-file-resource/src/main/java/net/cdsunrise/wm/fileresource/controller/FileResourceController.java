package net.cdsunrise.wm.fileresource.controller;

import net.cdsunrise.wm.fileresource.bo.FileResourceBo;
import net.cdsunrise.wm.fileresource.entity.FileResource;
import net.cdsunrise.wm.fileresource.service.FileResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@RestController
@RequestMapping("/file-resource")
public class FileResourceController {
    private static final Logger log = LoggerFactory.getLogger(FileResourceController.class);
    @Autowired
    private FileResourceService fileResourceService;

    @Value("${filePath}")
    private String filePath;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "";
        }
        String uuid = UUID.randomUUID().toString();
        try {
            if (file.getSize() > 0) {
                //获取文件信息和保存
                String fileStr = file.getOriginalFilename();
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                String fileName = uuid + "." + suffix;
                File saveFile = new File(filePath + fileName);
                file.transferTo(saveFile);
                //保存文件信息到数据库
                FileResource fileResource = new FileResource();
                fileResource.setFileName(fileName);
                fileResource.setOriginalName(fileStr);
                fileResource.setSuffix(suffix);
                fileResource.setUuid(uuid);
                fileResourceService.upload(fileResource);
            }
        } catch (Exception e) {
            log.error("upload error ", e);
        }
        return uuid;
    }

    @GetMapping("/get/{id}")
    public FileResourceBo get(@PathVariable("id") Long id) {
        return fileResourceService.get(id);
    }

    @GetMapping("/all-list")
    public List<FileResourceBo> findAll() {
        return fileResourceService.findAll();
    }

    @GetMapping("/get-uuid/{uuid}")
    public FileResourceBo getByUuid(@PathVariable("uuid") String uuid) {
        return fileResourceService.getByUuid(uuid);
    }

    @GetMapping("/delete-uuid/{uuid}")
    public void deleteByUuid(@PathVariable("uuid") String uuid) {
        fileResourceService.deleteByUuid(uuid);
    }
}
