package net.cdsunrise.wm.quality.feign;

import net.cdsunrise.wm.quality.vo.UserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/***
 * @author gechaoqing
 * 系统模块API
 */
@FeignClient(name = "wm-system")
public interface SystemFeign {

    /***
     * 获取当前登录用户所在部门ID
     * @return
     */
    @GetMapping("/user/userDeptId")
    Long fetchCurrentUserDeptId();

    /***
     * 根据用户ID获取姓名
     * @return 用户信息
     */
    @GetMapping("/user/userInfo")
    UserVo fetchCurrentUser();
}
