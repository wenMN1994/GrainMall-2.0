package com.grain.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * SpringSession的核心原理（装饰着模式）
 * 1、@EnableRedisHttpSession导入RedisHttpSessionConfiguration配置
 *      1）、给容器中添加一个组件
 *          SessionRepository-->RedisOperationsSessionRepository：redis操作session。session的增删改查封装类
 *      2）、SessionRepositoryFilter-->Filter：session存储过滤器,每个请求过来都要经过filter
 *          创建的时候，自动从容器中获取到SessionRepository；
 *          原始的request，response都被包装成SessionRepositoryRequestWrapper，SessionRepositoryResponseWrapper；
 *          以后获取session。request.getSession();
 *          wrappedRequest.getSession();-->SessionRepository中获取到的
 * @author DragonWen
 */
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MallAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAuthServerApplication.class, args);
    }

}
