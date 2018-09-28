package net.cdsunrise.wm.zuul.client;

import net.cdsunrise.wm.zuul.model.Role;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "wm-system")
@RequestMapping("/role")
public interface RoleClient {

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    List<Role> findAll();
}
