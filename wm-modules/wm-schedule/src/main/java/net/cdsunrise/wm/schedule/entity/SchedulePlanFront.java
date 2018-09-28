package net.cdsunrise.wm.schedule.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 进度计划前置任务
 */
@Data
@Entity
@Table(name = "wm_schedule_plan_ref_front")
@ApiModel("前置任务")
public class SchedulePlanFront extends BaseEntity{
    @ApiModelProperty("当前任务ID")
    @ApiParam("当前任务ID")
    private Long scheduleId;
    @ApiModelProperty("前置任务ID")
    @ApiParam("前置任务ID")
    private Long scheduleFrontId;
    /***
     * 前置关系
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanFrontRel#FF 完成-完成
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanFrontRel#FS 完成-开始
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanFrontRel#SF 开始-完成
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanFrontRel#SS 开始-开始
     */
    @ApiModelProperty("前置关系")
    @ApiParam("前置关系['FF'-'完成-完成','FS'-'完成-开始','SF'-'开始-完成','SS'-'开始-开始']，下拉框选择")
    private String frontRel;
    /***
     * 前置天数
     */
    @ApiModelProperty("前置间隔天数")
    @ApiParam("前置间隔天数")
    private Integer frontVal;
}
