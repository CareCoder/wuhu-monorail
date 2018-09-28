package net.cdsunrise.wm.riskmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Configuration
public class WebConfig {
    @Value("${imageUrlPrefix}")
    private String imageUrlPrefix;
}
