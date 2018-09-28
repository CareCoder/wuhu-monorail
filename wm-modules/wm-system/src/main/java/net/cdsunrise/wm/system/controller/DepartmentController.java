package net.cdsunrise.wm.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.*;
import net.cdsunrise.wm.system.entity.Department;
import net.cdsunrise.wm.system.service.DepartmentService;
import net.cdsunrise.wm.system.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :部门模块Controller
 */
@Api(value = "部门")
@RestController
@RequestMapping("/dept")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("新增一个部门")
    @SavePath
    public void save(DepartmentVo departmentVo) {
        departmentService.save(departmentVo);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        departmentService.delete(id);
    }

    @ApiOperation("查询单个部门信息")
    @GetPath
    public DepartmentVo get(Long id) {
        return departmentService.findOne(id);
    }


    @ApiOperation("查询所有部门")
    @ListPath
    public List<DepartmentVo> list() {
        return departmentService.findAll();
    }

    @ApiOperation("查询所有部门【树形结构】")
    @GetMapping("/all/tree")
    public List<Department> getTreeAll() {
        List<Department> list = departmentService.allDepartmentTree();
        return list;
    }

    @ApiOperation("查询父Id下的所有部门【树形结构】")
    @GetMapping("/allByParentId/tree")
    public List<Department> getTreeAllByParentId(Long id) {
        List<Department> list = departmentService.findByParentId(id);
        return list;
    }

    @ApiOperation("根据参建单位ID查询部门")
    @GetMapping("/findByCID")
    public List<DepartmentVo> findByCID(Long id) {
        return departmentService.findByCID(id);
    }

    @ApiOperation(value = "分页")
    @PagePath
    public Pager<DepartmentVo> getPager(PageCondition condition) {
        return departmentService.getPager(condition);
    }

    @ApiOperation("根据登录用户的部门获取该部门下的所有下属部门id")
    @GetMapping("/subordinateDepartment")
    public List<Long> subordinateDepartment() {
        return departmentService.subordinateDepartment();
    }

    @ApiOperation("根据部门id获取该部门下的所有下属部门id")
    @GetMapping("/subordinateDepartmentByDeptId/{id}")
    public List<Long> subordinateDepartmentByDeptId(@PathVariable("id") Long id) {
        return departmentService.subordinateDepartmentByDeptId(id);
    }

    @ApiOperation("根据登录用户的部门获取该部门下的所有下属部门id-树型结构")
    @GetMapping("/childrenDepartment")
    public List<Department> childrenDepartment() {
        List<Department> list = departmentService.childrenDepartment();
        System.out.println("eeee");
        return list;
    }

    @ApiOperation("根据管理一部管理二部id获取该部门的下属部门id")
    @GetMapping("/selectDepartmentByGLId")
    public List<Department> selectDepartmentByGLId() {
        return departmentService.selectDepartmentByGLId();
    }

}
