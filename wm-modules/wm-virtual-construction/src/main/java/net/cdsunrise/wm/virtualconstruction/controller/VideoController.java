package net.cdsunrise.wm.virtualconstruction.controller;

import net.cdsunrise.wm.base.web.annotation.ListPath;
import net.cdsunrise.wm.virtualconstruction.bo.VideoBo;
import net.cdsunrise.wm.virtualconstruction.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ListPath
    public List<VideoBo> getList() {
        return videoService.getList();
    }

    @GetMapping("/get/{id}")
    public VideoBo get(@PathVariable("id") Long id) {
        return videoService.get(id);
    }
}
