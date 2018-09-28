package net.cdsunrise.wm.riskmanagement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lijun
 * @date 2018-04-24.
 * @descritpion
 */
@ApiModel("用户")
@Data
public class UserVo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("名称")
    private String username;

    private String nickName;

    private String realName;
    @ApiModelProperty("部门ID")
    private Long deptId;
}
