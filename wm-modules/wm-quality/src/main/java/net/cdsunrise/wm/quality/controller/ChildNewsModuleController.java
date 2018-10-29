package net.cdsunrise.wm.quality.controller;

import io.swagger.annotations.Api;
import net.cdsunrise.wm.quality.entity.ChildNewsModule;
import net.cdsunrise.wm.quality.service.ChildNewsModuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("新闻子模块")
@RequestMapping("/childnewsmodule")
public class ChildNewsModuleController {
    @Resource
    private ChildNewsModuleService childNewsModuleService;

    @GetMapping("/list")
    public List<ChildNewsModule> list() {
        return childNewsModuleService.list();
    }

    @PostMapping("/add")
    public void add(ChildNewsModule childNewsModule) {
        childNewsModuleService.add(childNewsModule);
    }

    @PostMapping("/update")
    public void update(ChildNewsModule childNewsModule) {
        childNewsModuleService.update(childNewsModule);
    }
}
