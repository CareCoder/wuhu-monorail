package net.cdsunrise.wm.base.config;

import net.cdsunrise.wm.base.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lijun
 * @date 2018-03-29.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

//    @Value("${goodsImagePath}")
//    private String goodsImagePath;
//
//    @Value("${goodsImageUrl}")
//    private String goodsImageUrl;


    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.tokenHead}")
    private String jwtTokenHead;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private Integer jwtExpiration;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtFilter(jwtHeader, jwtTokenHead, jwtSecret, jwtExpiration)).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(goodsImageUrl + "**").addResourceLocations("file:/" + goodsImagePath);
//        super.addResourceHandlers(registry);
    }
}
