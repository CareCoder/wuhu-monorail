package net.cdsunrise.wm.prophase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :前期工程
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"net.cdsunrise.wm"})
public class ProphaseApplication {
    public static void main(String [] args){
        SpringApplication.run(ProphaseApplication.class);
    }
}
