package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.schedule.entity.SchedulePlan;
import net.cdsunrise.wm.schedule.entity.SchedulePlanFront;

import java.util.List;

/***
 * @author gechaoqing
 * 保存进度计划任务实体
 */
@Data
public class SchedulePlanSaveVo {
    @ApiParam("进度计划信息")
    private SchedulePlan schedulePlan;
    @ApiParam("前置任务列表")
    private List<SchedulePlanFront> planFrontList;
    @ApiParam("关联模型列表")
    private List<SchedulePlanRefModelVo> modelList;
    @ApiParam("被分配任务的用户ID")
    private Long assignToUserId;
}
