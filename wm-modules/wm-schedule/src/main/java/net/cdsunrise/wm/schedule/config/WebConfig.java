package net.cdsunrise.wm.schedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/***
 * @author gechaoqing
 * web配置
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("${fileUrl}")
    private String fileUrl;
    @Value("${uploadPath}")
    private String uploadPath;
    @Value("${storageAttachmentPath}")
    private String storageAttachmentPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileUrl+"**").addResourceLocations("file:/"+uploadPath,"file:/"+storageAttachmentPath);
        super.addResourceHandlers(registry);
    }
}
