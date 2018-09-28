package net.cdsunrise.wm.riskmanagement.client;

import net.cdsunrise.wm.riskmanagement.vo.UserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lijun
 * @date 2018-04-24.
 * @descritpion
 */
@FeignClient("wm-system")
@RequestMapping("/user")
public interface UserClient {
    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    UserVo get(@RequestParam("id") Long id);
}
