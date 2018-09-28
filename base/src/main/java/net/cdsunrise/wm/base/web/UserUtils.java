package net.cdsunrise.wm.base.web;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lijun
 * @date 2018-04-12.
 * @descritpion
 */
public class UserUtils {

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
      return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
    }

    /**
     * 获取用户名称
     *
     * @return
     */
    public static String getUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
