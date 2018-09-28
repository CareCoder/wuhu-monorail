package net.cdsunrise.wm.beamfield;

/**
 * @author lijun
 * @date 2018-04-02.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"net.cdsunrise.wm"})
public class BeamFieldApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeamFieldApplication.class);
    }
}
