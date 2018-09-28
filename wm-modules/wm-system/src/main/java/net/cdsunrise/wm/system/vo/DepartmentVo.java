package net.cdsunrise.wm.system.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: WangRui
 * Date: 2018/5/19
 * Describe:
 */
@Data
public class DepartmentVo {

    /**
     * 主键id
     */
    @ApiParam("部门主键id")
    private Long id;

    /**
     * 部门名称
     */
    @ApiParam("部门名称，新增时必须")
    private String name;

    /**
     * 所属单位ID
     */
    @ApiParam("所属单位ID，新增时必须")
    private Long companyId;

    /**
     * 所属单位名称
     */
    @ApiParam("所属单位名称")
    private String companyName;

    @ApiParam("用户列表权限id")
    private Long userAuthorId;
}
