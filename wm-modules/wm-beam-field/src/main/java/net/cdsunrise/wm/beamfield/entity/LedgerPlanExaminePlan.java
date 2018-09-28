package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: WangRui
 * Date: 2018/5/8
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_leger_plan_examine")
public class LedgerPlanExaminePlan extends BaseEntity {

    /**
     * 制梁计划ID
     */
    @ApiParam("制梁计划ID")
    private Long ledgerPlanId;

    /**
     * 工序编号 1模板打磨 2涂模板漆 3合模 4线型条、梁长及梁宽调整 5入模测量验收 6浇筑等强 7拆模
     */
    @ApiParam("工序编号")
    private Integer processNumber;

    @ApiParam("工序名称")
    private String processName;

    /**
     * 审核状态 0未审核，1已审核
     */
    @ApiParam("审核状态 0未审核，1已审核")
    private Integer examineStatus;

    /**
     * 审核人
     */
    @ApiParam("审核人")
    private String examineEmp;

    /**
     * 预计审核时间
     */
    @ApiParam("预计审核时间")
    private String examineTime;

}
