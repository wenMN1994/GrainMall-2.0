package com.grain.mall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * SpringBoot整合Sentinel
 *
 * 1、导入依赖
 *
 * ```
 * <dependency>
 *     <groupId>com.alibaba.cloud</groupId>
 *     <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
 * </dependency>
 * ```
 *
 * 2、下载sentinel的控制台
 *
 * 3、配置sentinel控制台地址信息
 *
 * 4、在控制台调整参数【默认所有的流控设置保存在内存中，重启失效】
 * @author DragonWen
 */
@EnableRedisHttpSession
@EnableFeignClients(basePackages = "com.grain.mall.seckill.feign")
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MallSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallSeckillApplication.class, args);
    }

}
