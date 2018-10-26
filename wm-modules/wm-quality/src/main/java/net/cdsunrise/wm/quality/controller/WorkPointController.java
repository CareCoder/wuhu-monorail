package net.cdsunrise.wm.quality.controller;

import io.swagger.annotations.Api;
import net.cdsunrise.wm.quality.entity.WorkPoint;
import net.cdsunrise.wm.quality.service.WorkPointService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/***
 * @author gechaoqing
 * 线性测量
 */
@RestController
@Api("工作站点")
@RequestMapping("/workpoint")
public class WorkPointController {
    @Resource
    private WorkPointService workPointService;

    @GetMapping("/list")
    public List<WorkPoint> list() {
        return workPointService.list();
    }

    @GetMapping("/query/{carpointid}")
    public List<WorkPoint> query(@PathVariable("carpointid") Long carPointId) {
        List<WorkPoint> query = workPointService.query(carPointId);
        return query;
    }

    @PostMapping("/add")
    public void add(WorkPoint workPoint) {
        workPointService.add(workPoint);
    }
}
