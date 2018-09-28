package net.cdsunrise.wm.document.client;

import net.cdsunrise.wm.document.vo.UserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author: WangRui
 * Date: 2018/5/11
 * Describe:
 */
@FeignClient("wm-system")
@RequestMapping("/user")
public interface SystemClient {

    /***
     * 根据用户ID获取姓名
     * @param id 用户ID
     * @return 用户姓名
     * UserVo 包含需要的字段   是wm-system服务方法/user/get返回的对象所包含的才有效
     */
    @GetMapping("/get")
    UserVo fetchUserDeptIdByUserId(@RequestParam("id") Long id);
}
