package net.cdsunrise.wm.system.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
@Data
public class PositionVo {

    @ApiParam("主键id")
    private Long id;

    @ApiParam("岗位名称--新增必须")
    private String name;

    @ApiParam("上级岗位ID--新增必须")
    private Long parentId;

    @ApiParam("上级岗位名称")
    private String parentName;

    @ApiParam("部门id--新增必须")
    private Long deptId;

    @ApiParam("部门名称")
    private String deptName;

    @ApiParam("BIM里模型视角定位")
    private String lookAtPos;
}
