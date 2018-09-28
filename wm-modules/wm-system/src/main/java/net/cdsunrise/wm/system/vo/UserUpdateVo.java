package net.cdsunrise.wm.system.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/6/26/026
 * Describe:
 */
@Data
public class UserUpdateVo {

    private Long id;

    /**
     * 昵称
     */
    @ApiParam("昵称")
    private String nickName;

    /**
     * 电话号码
     */
    @ApiParam("电话号码")
    private String tel;

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
