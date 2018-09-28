package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.schedule.entity.SchedulePlanFront;

import java.util.List;

/***
 * @author gechaoqing
 * 前置任务保存类
 */
@ApiModel("前置任务保存实体")
@Data
public class SchedulePlanFrontSaveVo {
    @ApiModelProperty("当前任务ID")
    @ApiParam("当前任务ID")
    private Long planId;
    @ApiModelProperty("前置任务列表")
    @ApiParam("前置任务列表")
    private List<SchedulePlanFront> planFrontList;
}
