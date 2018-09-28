package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_ledger_plan2")
public class LedgerPlanV2 extends BaseEntity {



    @ApiParam("梁号")
    @ManyToOne
    @JoinColumn(name = "beam_number")
    private LedgerBasicInformation ledgerBasicInformation;

    @ApiParam("用梁时间")
    private Date useTime;

    /**
     * 制梁计划审核
     * 制梁计划状态：
     * 计划生成-待总包方审核状态-1
     * 总包方审核通过-待梁场审核状态-2
     *
     * 总包方审核不通过或选择逻辑删除计划-更改为删除状态-5
     * 梁场审核通过-同意生产状态-4
     * 梁场审核不通过改为总包方待审核状态，梁场给出预计生产时间-3
     * 总包方查看梁场意见同意的话就变更用梁时间为梁场预计的梁出场时间，同意生产状态，-4
     */
    @ApiParam("审核状态")
    private Integer examineStatus;

    @ApiParam("工序审核步骤id,若为-1则显示开始制梁，0-22显示审核，23显示打印按")
    private Integer processExamineId;

    @ApiParam("工序审核时间")
    private Date processExamineTime;

    @ApiParam("备注")
    private String note;


}
