package net.cdsunrise.wm.fileresource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("${filePath}")
    private String filePath;

    @Value("${fileUrlPrefix}")
    private String fileUrlPrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileUrlPrefix + "**").addResourceLocations("file:/" + filePath);
        super.addResourceHandlers(registry);
    }
}
