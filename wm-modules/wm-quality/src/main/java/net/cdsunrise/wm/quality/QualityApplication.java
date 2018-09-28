package net.cdsunrise.wm.quality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"net.cdsunrise.wm"})
/***
 * @author gechaoqing
 * 质量管理APP
 */
public class QualityApplication {
    public static void main(String [] args){
        SpringApplication.run(QualityApplication.class);
    }
}
