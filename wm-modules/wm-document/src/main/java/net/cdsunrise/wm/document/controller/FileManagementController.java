package net.cdsunrise.wm.document.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.DeletePath;
import net.cdsunrise.wm.base.web.annotation.GetPath;
import net.cdsunrise.wm.document.entity.FileManagement;
import net.cdsunrise.wm.document.service.FileManagementService;
import net.cdsunrise.wm.document.vo.CurrentFolderVo;
import net.cdsunrise.wm.document.vo.FileManagementResultVo;
import net.cdsunrise.wm.document.vo.FileManagementUploadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/1/001
 * Describe:
 */
@RestController
@RequestMapping("/doc")
public class FileManagementController {

    @Autowired
    private FileManagementService fileManagementService;

    @ApiOperation("上传文件/创建文件夹")
    @PostMapping("/upload")
    public void add(HttpServletRequest request, FileManagementUploadVo fileManagementUploadVo) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        fileManagementService.save(fileManagementUploadVo);
    }

    @ApiOperation("删除文件或文件夹")
    @DeletePath
    public void delete(Long id) {
        fileManagementService.delete(id);
    }

    @ApiOperation("获取单条")
    @GetPath
    public FileManagement getOne(Long id) {
        return fileManagementService.getOne(id);
    }

    @ApiOperation("获取全部--提交当前文件夹的id")
    @GetMapping("/getAll")
    public CurrentFolderVo getAll(Long parentFolderId) {
        return fileManagementService.getAll(parentFolderId);
    }

    @ApiOperation("分享资料页面文件列表查询--分页")
    @GetMapping("/shareFilePage")
    public Pager<FileManagementResultVo> shareFilePage(PageCondition condition){
        return fileManagementService.shareFilePage(condition);
    }

}
