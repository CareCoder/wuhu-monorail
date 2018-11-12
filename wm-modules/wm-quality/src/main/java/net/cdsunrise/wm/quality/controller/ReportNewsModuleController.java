package net.cdsunrise.wm.quality.controller;

import io.swagger.annotations.Api;
import net.cdsunrise.wm.quality.entity.ReportNewsModule;
import net.cdsunrise.wm.quality.service.ReportNewsModuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("新闻报告模块")
@RequestMapping("/reportnewsmodule")
public class ReportNewsModuleController {
    @Resource
    private ReportNewsModuleService reportNewsModuleService;

    @GetMapping("/list")
    public List<ReportNewsModule> list() {
        return reportNewsModuleService.list();
    }

    @PostMapping("/add")
    public void add(ReportNewsModule childNewsModule) {
        reportNewsModuleService.add(childNewsModule);
    }

    @PostMapping("/update")
    public void update(ReportNewsModule childNewsModule) {
        reportNewsModuleService.update(childNewsModule);
    }

    @GetMapping("/find")
    public List<ReportNewsModule> find(Long pid) {
        return reportNewsModuleService.findByPid(pid);
    }
}
