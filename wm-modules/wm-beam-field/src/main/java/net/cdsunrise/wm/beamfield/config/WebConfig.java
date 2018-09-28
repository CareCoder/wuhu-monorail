package net.cdsunrise.wm.beamfield.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Author: WangRui
 * Date: 2018/5/29
 * Describe: web配置
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${accessPath}")
    private String accessPath;

    @Value("${uploadPath}")
    private String uploadPath;

    /**
     * 文件访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(accessPath + "**").addResourceLocations("file:/" + uploadPath);
        super.addResourceHandlers(registry);
    }
}
