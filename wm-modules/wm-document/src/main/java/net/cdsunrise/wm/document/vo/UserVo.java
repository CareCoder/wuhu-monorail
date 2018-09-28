package net.cdsunrise.wm.document.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: WangRui
 * Date: 2018/5/11
 * Describe:
 */
@Data
public class UserVo {

    private String username;

    /**
     * 所属部门id   department.id
     */
    @ApiParam("所属部门id")
    private Long deptId;
}
