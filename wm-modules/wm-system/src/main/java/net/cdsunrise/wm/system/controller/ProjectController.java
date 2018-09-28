package net.cdsunrise.wm.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.system.entity.ProjectLine;
import net.cdsunrise.wm.system.entity.WorkPoint;
import net.cdsunrise.wm.system.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author gechaoqing
 * 工程项目总览
 */
@Api(value = "项目总览线路/工点")
@RestController
@RequestMapping("/project")
public class ProjectController {
    private ProjectService projectService;
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @ApiOperation("新增/修改保存线路")
    @PostMapping("/line/save")
    public void save(ProjectLine line){
        projectService.save(line);
    }

    @ApiOperation("新增/修改保存工点")
    @PostMapping("/work-point/save")
    public void save(WorkPoint workPoint){
        projectService.save(workPoint);
    }

    @ApiOperation("删除线路")
    @PostMapping("/line/delete")
    public void deleteLine(Long lineId){
        projectService.deleteLine(lineId);
    }

    @ApiOperation("删除工点")
    @PostMapping("/work-point/delete")
    public void deleteWorkPoint(Long workPointId){
        projectService.deleteWorkPoint(workPointId);
    }

    @ApiOperation("工点分页查询")
    @GetMapping("/work-point/page")
    public Pager queryWorkPoint(PageCondition pageCondition){
        return projectService.getWorkPointPager(pageCondition);
    }

    @ApiOperation("线路分页查询")
    @GetMapping("/line/page")
    public Pager queryLine(PageCondition pageCondition){
        return projectService.getLinePager(pageCondition);
    }


    @ApiOperation("根据线路ID获取工点")
    @GetMapping("/work-point/line/{lineId}")
    public List<WorkPoint> queryByLine(@PathVariable("lineId") Long lineId){
        return projectService.getByLine(lineId);
    }

    @ApiOperation("根据线路ID获取不同类型工点,type可取：WORK_POINT=工点/站、WORK_INTERVAL=区间")
    @GetMapping("/work-point/line-type/{lineId}")
    public List<WorkPoint> queryByLineAndType(@PathVariable("lineId") Long lineId,String type){
        return projectService.getByLineAndType(lineId,type);
    }

    @ApiOperation("根据线路ID获取线路详情")
    @GetMapping("/line/{id}")
    public ProjectLine getById(@PathVariable("id") Long id){
        return projectService.getLineById(id);
    }

    @ApiOperation("根据线路ID获取线路详情")
    @GetMapping("/work-point/{id}")
    public WorkPoint getWorkPointById(@PathVariable("id") Long id){
        return projectService.getWorkPointById(id);
    }
}
