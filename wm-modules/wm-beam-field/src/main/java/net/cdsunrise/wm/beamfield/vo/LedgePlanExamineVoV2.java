package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * Author: WangRui
 * Time: 2018/6/3/003
 * Describe:
 */
@Data
public class LedgePlanExamineVoV2 {

    @ApiParam("制梁计划id-必填")
    private Long id;

    @ApiParam("是否通过 1:通过,2:不通过,不通过时要编辑用梁时间,3:逻辑删除这条计划 -必填")
    private Integer result;

    @ApiParam("修改后的用梁时间")
    private Date useTime;

    @ApiParam("备注")
    private String note;
}