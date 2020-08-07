package com.grain.mall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/5/21 16:45
 * @description：
 *      使用RabbitMQ
 *          1、引入amqp场景，RabbitAutoConfiguration 就会自动生效
 *          2、给容器中自动配置了
 *              RabbitTemplate、AmqpAdmin、CachingConnectionFactory、RabbitMessagingTemplate
 *              所有属性都是spring.rabbitmq来配置
 *              @ConfigurationProperties(prefix = "spring.rabbitmq")
 *          3、给配置文件中配置spring.rabbitmq属性
 *          4、@EnableRabbit 开启功能
 *          5、监听消息
 *              @RabbitListener 类+方法都可以使用（监听哪些队列）
 *              @RabbitHandler 方法上使用（重载区分不同的消息）
 * @modified By：
 * @version: $
 */
@EnableRabbit
@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication
public class MallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class, args);
    }

}
