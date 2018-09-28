package net.cdsunrise.wm.system.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.web.annotation.DeletePath;
import net.cdsunrise.wm.base.web.annotation.ListPath;
import net.cdsunrise.wm.base.web.annotation.SavePath;
import net.cdsunrise.wm.system.entity.Role;
import net.cdsunrise.wm.system.service.RoleService;
import net.cdsunrise.wm.system.vo.ResourcesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/25
 * Describe :角色
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("新增角色")
    @SavePath
    public void save(Role role){
        roleService.save(role);
    }

    @ApiOperation("删除角色")
    @DeletePath
    public void delete(Long id){
        roleService.delete(id);
    }

    @ApiOperation("更新角色权限")
    @PostMapping(value = "/updateResource")
    public void updateResource(Long id, @RequestParam("resourceId") List<Long> resourceIds) {
        roleService.updateRoleRight(resourceIds, id);
    }

    @ApiOperation("查看角色权限")
    @GetMapping("/viewRoleResource")
    public List<ResourcesVo> viewRoleResource(Long roleId) {
        return roleService.viewRoleResource(roleId);
    }

    @ApiOperation("查询当前用户创建的所有角色")
    @ListPath
    public List<Role> findAll(){
        return roleService.findAll();
    }
}
