package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

/***
 * @author gechaoqing
 * 用户信息
 */
@Data
public class UserVo {
    private Long id;
    private String realName;
    private Long companyId;
    private Long deptId;
}
