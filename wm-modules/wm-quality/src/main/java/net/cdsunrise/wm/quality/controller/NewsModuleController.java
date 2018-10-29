package net.cdsunrise.wm.quality.controller;

import io.swagger.annotations.Api;
import net.cdsunrise.wm.quality.entity.NewsModule;
import net.cdsunrise.wm.quality.service.NewsModuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("新闻模块")
@RequestMapping("/newsmodule")
public class NewsModuleController {
    @Resource
    private NewsModuleService newsModuleService;

    @GetMapping("/list")
    public List<NewsModule> list() {
        return newsModuleService.list();
    }

    @GetMapping("/query")
    public List<NewsModule> query(String info) {
        return newsModuleService.query(info);
    }

}
