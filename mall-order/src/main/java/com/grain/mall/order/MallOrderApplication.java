package com.grain.mall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
 *      Seata控制分布式事务
 *          1、每一个微服务先创建undo_log数据库表；
 *          2、安装事务协调器：seata-server：https://github.com/seata/seata/releases
 *          3、整合
 *              1）、导入依赖：spring-cloud-starter-alibaba-seata
 *              2）、解压并启动seata-server
 *                  registry.conf：注册中心配置
 *                  file.conf
 *              3）、所有想要用到分布式事务的微服务使用seata DataSourceProxy代理自己的数据源
 *              4）、每个微服务都要导入registry.conf和file.conf vgroup_mapping.${spring.application.name}-fescar-service-group
 *              5）、启动测试分布式事务
 *              6）、给分布式大事务入口标注@GlobalTransactional
 *              7）、每一个远程的小事务标注@Transactional
 * @modified By：
 * @version: $
 */
@EnableRabbit
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class, args);
    }

}
