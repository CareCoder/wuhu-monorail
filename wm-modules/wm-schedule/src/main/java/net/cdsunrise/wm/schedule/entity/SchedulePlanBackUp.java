package net.cdsunrise.wm.schedule.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***
 * @author gechaoqing
 * 进度计划原始数据记录
 */
@Data
@Entity
@Table(name = "wm_schedule_plan_back_up")
public class SchedulePlanBackUp extends BaseEntity {

    @ApiParam("进度任务开始日期，yyyy-MM-dd")
    private Date startDate;
    @ApiParam("进度任务完成日期，yyyy-MM-dd")
    private Date completeDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private SchedulePlan schedulePlan;
    @ApiParam("计划工程量")
    private BigDecimal quantity;

}
