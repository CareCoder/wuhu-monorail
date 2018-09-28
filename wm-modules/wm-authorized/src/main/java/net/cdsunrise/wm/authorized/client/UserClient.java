package net.cdsunrise.wm.authorized.client;

import net.cdsunrise.wm.authorized.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "wm-system")
@RequestMapping("/user")
public interface UserClient {

    @RequestMapping(value = "/findByUsername", method = {RequestMethod.GET})
    User findByUsername(@RequestParam("username") String username);
}
