package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;


/***
 * @author gechaoqing
 * 查询进度计划进行审核
 */
@Data
@ApiModel("进度审核查询条件")
public class SchedulePlanConfirmAuditQueryVo {
    @ApiParam("计划类型[年-YEAR,月-MONTH]")
    private String category;
    @ApiParam("部门ID")
    private Long departId;
    @ApiParam("计划创建日期[YYYY-MM]")
    private String createDate;
    @ApiParam("审核状态[待审核->NONE,通过->OK,不通过->NOT_OK]")
    private String status;
}
