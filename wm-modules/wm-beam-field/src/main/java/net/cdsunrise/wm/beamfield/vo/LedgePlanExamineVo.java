package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: WangRui
 * Date: 2018/5/30
 * Describe:
 */
@Data
public class LedgePlanExamineVo {

    @ApiParam("制梁计划id-必填")
    private Long id;

    /**
     * 制梁计划审核
     * 制梁计划状态：
     * 计划生成-待总包方审核状态-1
     * 总包方审核通过-待梁场审核状态-2
     * 总包方审核不通过或选择逻辑删除计划-更改为删除状态-5
     * 梁场审核通过-同意生产状态-4
     * 梁场审核不通过改为总包方待审核状态，梁场给出预计生产时间-3
     * 总包方查看梁场意见同意的话就变更用梁时间为梁场预计的梁出场时间，同意生产状态，-4
     */
    @ApiParam("制梁计划审核状态-必填")
    private Integer planExamineStatus;

    @ApiParam("是否通过 1:通过,2:不通过,3:逻辑删除这条计划 -必填")
    private Integer idPass;

    @ApiParam("梁场预计梁出场时间")
    private String beamCompletionTime;

    @ApiParam("备注")
    private String note;
}
