package net.cdsunrise.wm.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author lijun
 * @date 2018-04-12.
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"net.cdsunrise.wm"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class);
    }
}
