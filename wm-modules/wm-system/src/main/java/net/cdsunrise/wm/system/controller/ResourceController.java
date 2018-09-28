package net.cdsunrise.wm.system.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Author : WangRui
 * Date : 2018/4/24
 * Describe :
 */
@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /*@ApiOperation("查询所有资源(菜单，事件，授权)")
    @ListPath
    public List<Resource> allResources(){
        return resourceService.findAll();
    }*/

    @ApiOperation("查询所有菜单类型资源")
    @GetMapping("/allMenuResources")
    public List<Map<String, Object>> allMenuResources(){
        return resourceService.allMenuResources();
    }
}
