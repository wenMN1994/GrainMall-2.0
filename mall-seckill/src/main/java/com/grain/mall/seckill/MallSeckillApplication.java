package com.grain.mall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 一、SpringBoot整合Sentinel
 *
 * 1、导入依赖
 *
 * ```xml
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
 *
 * 二、每一个微服务导入以下依赖并配置management.endpoints.web.exposure.include=*
 *
 * ```xml
 * <dependency>
 *     <groupId>org.springframework.boot</groupId>
 *     <artifactId>spring-boot-starter-actuator</artifactId>
 * </dependency>
 * ```
 *
 * 三、自定义sentinel流控返回数据
 *
 * 四、使用Sentinel来保护feign远程调用：熔断
 *
 * 1、调用方的熔断保护：配置feign.sentinel.enabled=true
 *
 * 2、调用方手动指定远程服务的降级策略。远程服务被降级处理，触发我们的熔断回调方法
 *
 * 3、超大流量的时候，必须牺牲一些远程服务。在服务的提供方指定我们的降级策略；提供方是在运行的，但是不运行自己的业务逻辑，返回的是默认的降级数据（限流的数据）
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
