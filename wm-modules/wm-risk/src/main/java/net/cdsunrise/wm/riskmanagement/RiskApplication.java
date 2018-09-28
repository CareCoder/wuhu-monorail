package net.cdsunrise.wm.riskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author lijun
 * @date 2018-04-08.
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"net.cdsunrise.wm"})
public class RiskApplication {
    public static void main(String[] args) {
        SpringApplication.run(RiskApplication.class);
    }
}
