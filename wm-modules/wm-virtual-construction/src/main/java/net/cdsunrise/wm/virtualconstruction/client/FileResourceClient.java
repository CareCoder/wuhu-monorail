package net.cdsunrise.wm.virtualconstruction.client;

import net.cdsunrise.wm.virtualconstruction.bo.FileResourceBo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@FeignClient(name = "wm-file-resource")
@RequestMapping("/file-resource")
public interface FileResourceClient {

    @RequestMapping(value = "/get/{id}", method = {RequestMethod.GET})
    FileResourceBo get(@RequestParam("id") @PathVariable("id") Long id);
}
