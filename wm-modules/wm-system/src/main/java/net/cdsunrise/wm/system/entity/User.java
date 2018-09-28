package net.cdsunrise.wm.system.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.util.*;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe : 用戶
 */
@Data
@Entity
@Table(name = "wm_user")
public class User extends BaseEntity {

    /**
     * 用戶名
     */
    @ApiParam(value = "用戶名")
    private String username;

    /**
     * 密码
     */
    @ApiParam(value = "密码")
    private String password;

    /**
     * 昵称
     */
    @ApiParam(value = "昵称")
    private String nickName;

    /**
     * 真实姓名
     */
    @ApiParam(value = "真实姓名")
    private String realName;

    /**
     * 性别
     */
    @ApiParam(value = "性别")
    private String sex;

    /**
     * 角色ID
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * 岗位id
     */
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    /**
     * 用户状态1激活，2注销
     */
    @ApiParam(value = "用户状态")
    private Integer status;

    /**
     * 电话号码
     */
    @ApiParam(value = "电话号码")
    private String tel;

    /**
     * 邮箱
     */
    @ApiParam(value = "邮箱")
    private String email;

    /**
     * 地址
     */
    @ApiParam(value = "地址")
    private String address;

    /**
     * 用户列表权限id
     */
    @ApiParam("用户列表权限id")
    private Long userAuthorId;

    /**
     * 备注
     */
    @ApiParam(value = "备注")
    private String note;

}
