package net.cdsunrise.wm.schedule.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.schedule.entity.DelayReason;
import net.cdsunrise.wm.schedule.service.DelayReasonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author gechaoqing
 * 进度延迟原因控制层
 */
@Api("进度延迟因素")
@RestController
@RequestMapping("/delay-reason")
public class DelayReasonController {
    private DelayReasonService delayReasonService;
    public DelayReasonController(DelayReasonService delayReasonService){
        this.delayReasonService = delayReasonService;
    }

    @ApiOperation("根据父级ID获取")
    @GetMapping("/{parentId}/list")
    public List<DelayReason> listByParentId(@PathVariable("parentId") Long parentId){
        return delayReasonService.getByParentId(parentId);
    }

    @ApiOperation("获取根节点因素列表")
    @GetMapping("/list")
    public List<DelayReason> list(){
        return delayReasonService.getRoot();
    }


}
