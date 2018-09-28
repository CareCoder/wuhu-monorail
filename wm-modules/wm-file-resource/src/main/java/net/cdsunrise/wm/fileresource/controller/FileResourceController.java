package net.cdsunrise.wm.fileresource.controller;

import net.cdsunrise.wm.fileresource.bo.FileResourceBo;
import net.cdsunrise.wm.fileresource.entity.FileResource;
import net.cdsunrise.wm.fileresource.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@RestController
@RequestMapping("/file-resource")
public class FileResourceController {
    @Autowired
    private FileResourceService fileResourceService;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public void upload() {

    }

    @GetMapping("/get/{id}")
    public FileResourceBo get(@PathVariable("id") Long id) {
        return fileResourceService.get(id);
    }

    @GetMapping("/all-list")
    public List<FileResourceBo> findAll() {
        return fileResourceService.findAll();
    }
}
