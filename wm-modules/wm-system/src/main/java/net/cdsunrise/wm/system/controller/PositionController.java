package net.cdsunrise.wm.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.*;
import net.cdsunrise.wm.system.service.PositionService;
import net.cdsunrise.wm.system.vo.PositionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :岗位模块Controller
 */
@Api(value = "岗位")
@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation("新增一个岗位")
    @SavePath
    public void save(PositionVo positionVo) {
        positionService.save(positionVo);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        positionService.delete(id);
    }

    @ApiOperation("查询单个岗位信息")
    @GetPath
    public PositionVo get(Long id) {
        return positionService.findOne(id);
    }

    @ApiOperation("根据部门ID查询岗位")
    @GetMapping(value = "/findByDeptId")
    public List<PositionVo> selectPositionByDept(Long deptId) {
        return positionService.selectPositionByDept(deptId);
    }

    @ApiOperation("查询所有岗位")
    @ListPath
    public List<PositionVo> get() {
        return positionService.findAll();
    }

    @ApiOperation(value = "分页")
    @PagePath
    public Pager<PositionVo> getPager(PageCondition condition) {
        return positionService.getPager(condition);
    }
}
