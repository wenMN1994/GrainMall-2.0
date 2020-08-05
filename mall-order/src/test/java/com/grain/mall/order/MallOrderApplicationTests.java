package com.grain.mall.order;

import com.grain.mall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/4 22:02
 * @description：
 * @modified By：
 * @version: $
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MallOrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessageTest() {
        OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
        reasonEntity.setId(1L);
        reasonEntity.setName("哈哈");
        reasonEntity.setCreateTime(new Date());
        String msg = "Hello World";
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",reasonEntity, new CorrelationData(UUID.randomUUID().toString()));
        log.info("消息发送完成{}", reasonEntity);
    }

    /**
     * 1、如何创建Exchange、Queues、Binding
     *  使用AmqpAdmin进行创建
     * 2、如何收发消息
     */
    @Test
    public void createExchange() {
        /**
         * DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
         */
        DirectExchange directExchange = new DirectExchange("hello-java-exchange", true, false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功", "hello-java-exchange");
    }

    @Test
    public void createQueue() {
        /**
         * Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         */
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("Queue[{}]创建成功", "hello-java-queue");
    }

    @Test
    public void createBinding() {
        /**
         * Binding(String destination, 【目的地】
         * Binding.DestinationType destinationType, 【目的地类型】
         * String exchange, 【交换机】
         * String routingKey, 【路由键】
         * Map<String, Object> arguments)【自定义参数】
         */
        Binding binding = new Binding("hello-java-queue",Binding.DestinationType.QUEUE,"hello-java-exchange","hello.java",null);
        amqpAdmin.declareBinding(binding);
        log.info("Binding[{}]创建成功", "hello-java-binding");
    }
}
