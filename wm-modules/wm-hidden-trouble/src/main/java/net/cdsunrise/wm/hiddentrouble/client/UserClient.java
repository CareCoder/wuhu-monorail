package net.cdsunrise.wm.hiddentrouble.client;

import net.cdsunrise.wm.hiddentrouble.bo.UserBo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lijun
 * @date 2018-04-26.
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
    UserBo get(@RequestParam("id") Long id);
}
