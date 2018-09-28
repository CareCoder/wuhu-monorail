package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/***
 * @author gechaoqing
 * 进度关联模型保存类
 */
@ApiModel("进度关联模型保存类")
@Data
public class SchedulePlanRefModelSaveVo {
    @ApiModelProperty("当前任务ID")
    @ApiParam("当前任务ID")
    private Long planId;
    @ApiModelProperty("关联模型列表")
    @ApiParam("关联模型列表")
    private List<SchedulePlanRefModelVo> models;
}
