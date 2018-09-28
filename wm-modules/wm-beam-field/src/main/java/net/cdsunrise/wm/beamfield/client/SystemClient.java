package net.cdsunrise.wm.beamfield.client;

import net.cdsunrise.wm.beamfield.vo.ResourceVo;
import net.cdsunrise.wm.beamfield.vo.UserVo;
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
     */
    @GetMapping("/get")
    UserVo fetchUserNameByUserId(@RequestParam("id") Long id);

    /**
     * 判断用户类型
     * @param code
     * @return
     */
    @GetMapping("/fetchUserType")
    ResourceVo fetchUserType(@RequestParam("code")String code);

}
