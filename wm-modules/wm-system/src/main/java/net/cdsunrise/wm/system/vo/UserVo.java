package net.cdsunrise.wm.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author : WangRui
 * Date : 2018/4/24
 * Describe :
 */
@Data
@ApiModel("用戶Vo")
public class UserVo {

    private Long id;

    /**
     * 用戶名
     */
    @ApiModelProperty("用户名")
    @ApiParam("用户名")
    private String username;

    /**
     * 昵称
     */
    @ApiParam("昵称")
    private String nickName;

    /**
     * 真实姓名
     */
    @ApiParam("真实姓名")
    private String realName;

    /**
     * 性别
     */
    @ApiParam("性别")
    private String sex;

    /**
     * 角色ID
     */
    @ApiParam("角色ID")
    private Long roleId;

    /**
     * 角色名
     */
    @ApiParam("角色名")
    private String roleName;

    /**
     * 所属部门id   department.id
     */
    @ApiParam("所属部门id")
    private Long deptId;

    /**
     * 所属部门名称   department.name
     */
    @ApiParam("所属部门名称")
    private String deptName;

    /**
     * 岗位id
     */
    @ApiParam("岗位id  必需")
    private Long positionId;

    /**
     * 岗位名称
     */
    @ApiParam("岗位名称")
    private String positionName;

    /**
     * 用户状态1激活，2注销
     */
    @ApiParam(value = "用户状态")
    private String status;

    /**
     * 电话号码
     */
    @ApiParam("电话号码")
    private String tel;

    /**
     * 邮箱
     */
    @ApiParam("邮箱")
    private String email;

    /**
     * 地址
     */
    @ApiParam("地址")
    private String address;

    /**
     * 备注
     */
    @ApiParam("备注")
    private String note;
}
