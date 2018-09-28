package net.cdsunrise.wm.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/***
 * @author gechaoqing
 * 进度管理启动类
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"net.cdsunrise.wm"})
public class ScheduleBootStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleBootStartApplication.class);
    }
}
