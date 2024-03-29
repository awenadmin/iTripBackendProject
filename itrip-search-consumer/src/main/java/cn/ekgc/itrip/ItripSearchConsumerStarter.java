package cn.ekgc.itrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <b>搜索模块Consumer</b>
 * @author awen
 * @version 3.1.1
 * @since 3.1.1
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ItripSearchConsumerStarter {
    public static void main(String[] args) {
        SpringApplication.run(ItripSearchConsumerStarter.class, args);
    }
}
