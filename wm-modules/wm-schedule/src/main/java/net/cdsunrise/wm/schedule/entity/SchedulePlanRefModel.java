package net.cdsunrise.wm.schedule.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 进度计划关联模型数据
 */
@Data
@Entity
@Table(name = "wm_schedule_plan_ref_model")
public class SchedulePlanRefModel extends BaseEntity{
    @ApiParam("进度计划ID")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_plan_id")
    @JsonBackReference
    private SchedulePlan plan;
    @ApiParam("关联模型ID")
    private Long modelId;
    @ApiParam("模型GUID")
    private String guid;
    @ApiParam("模型FID")
    private Long fid;
    @ApiParam("墩号")
    private String pierCode;
    @ApiParam("模型工程量")
    private BigDecimal quantity;
    @ApiParam("工程量名称")
    private String name;
    @ApiParam("计量单位")
    private String unit;
    @ApiParam("编号")
    private String code;
    @ApiParam("模型状态")
    private String status;

    @Override
    public String toString() {
        return "SchedulePlanRefModel{" +
                "plan=" + (plan!=null?plan.getId():null) +
                ", modelId=" + modelId +
                ", guid='" + guid + '\'' +
                ", fid=" + fid +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
