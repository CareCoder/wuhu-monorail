package net.cdsunrise.wm.base.web;

import lombok.Data;

/***
 * @author gechaoqing
 * 当前登录用户信息
 */
@Data
public class User {
    private Long id=3L;
    private Long positionId=3L;
    private boolean isManager;
    /***
     * 部门ID
     */
    private Long departId=2L;
}
