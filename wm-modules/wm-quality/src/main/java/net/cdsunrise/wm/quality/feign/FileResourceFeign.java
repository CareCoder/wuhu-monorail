package net.cdsunrise.wm.quality.feign;

import net.cdsunrise.wm.quality.configuration.FromUrlEncodedClientConfiguration;
import net.cdsunrise.wm.quality.vo.FileResourceBo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/***
 * @author gechaoqing
 * 文件模块API
 */
@FeignClient(name = "wm-file-resource" , configuration = FromUrlEncodedClientConfiguration.class)
public interface FileResourceFeign {
    /**
     * 文件上传
     */
    @PostMapping("/file-resource/upload")
    String upload(MultipartFile file);

    @GetMapping("/file-resource/get-uuid/{uuid}")
    FileResourceBo getByUuid(@PathVariable("uuid") String uuid);

    @GetMapping("/file-resource/delete-uuid/{uuid}")
    void deleteByUuid(@PathVariable("uuid") String uuid);

    @GetMapping("/file-resource/download-uuid")
    ResponseEntity<byte[]> downloadByUuid(@RequestParam("uuids") String[] uuids);
}
