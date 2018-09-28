package net.cdsunrise.wm.document.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/2/002
 * Describe:
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    //下载文件访问路径
    @Value("${accessPath}")
    private String fileUrl;

    //上传文件路径
    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //用fileUrl路径映射uploadPath
        registry.addResourceHandler(fileUrl + "**").addResourceLocations("file:/" + uploadPath);
        super.addResourceHandlers(registry);
    }
}
