package net.cdsunrise.wm.fileresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"net.cdsunrise.wm"})
public class FileResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileResourceApplication.class);
    }
}
