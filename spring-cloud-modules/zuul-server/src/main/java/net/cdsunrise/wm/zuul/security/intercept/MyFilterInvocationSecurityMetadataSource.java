package net.cdsunrise.wm.zuul.security.intercept;

import net.cdsunrise.wm.zuul.client.RoleClient;
import net.cdsunrise.wm.zuul.model.Resource;
import net.cdsunrise.wm.zuul.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-18.
 * @descritpion 数据源加载
 */
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private RoleClient roleClient;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getHttpRequest();
        AntPathRequestMatcher matcher;
        List<Role> roles = roleClient.findAll();
        Collection<ConfigAttribute> configAttributes = new HashSet<>();
        for (Role role : roles) {
            if (!role.getResources().isEmpty()) {
                for (Resource resource : role.getResources()) {
                    matcher = new AntPathRequestMatcher(resource.getUrl(), resource.getMethod());
                    if (matcher.matches(request)) {
                        configAttributes.add((ConfigAttribute) () -> role.getCode());
                        break;
                    }
                }
            }
        }
        if (configAttributes.isEmpty()) {
            //判断URL 是否在资源表中
            //没有匹配到,默认是要登录才能访问
            return SecurityConfig.createList("ROLE_USER");
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}