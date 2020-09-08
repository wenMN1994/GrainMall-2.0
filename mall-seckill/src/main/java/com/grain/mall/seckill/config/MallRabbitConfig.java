package com.grain.mall.seckill.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/5 11:30
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class MallRabbitConfig {

    //@Autowired // 注解出现循环依赖问题
    RabbitTemplate rabbitTemplate;

    // TODO 循环依赖
    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate();
        return rabbitTemplate;
    }

    /**
     * 使用JSON序列化机制，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate
     * 1、服务收到消息就回调
     *      1）、spring.rabbitmq.publisher-confirms=true
     *      2）、设置确认回调ConfirmCallback
     * 2、消息正确抵达队列进行回调
     *      1）、# 开启发送端消息抵达队列的确认
     *           spring.rabbitmq.publisher-returns=true
     *           # 只要抵达队列，以异步的方式优先回调我们这个returnConfirm
     *           spring.rabbitmq.template.mandatory=true
     *      2)、ReturnCallback
     * 3、消费端确认（保证每个消息被正确消费，此时才可以broker删除这个消息）
     *      1）、默认是自动确认，只有消息接收到，客户端会自动确认，服务端就会移除这个消息
     *          问题：我们收到很多消息，自动回复给服务器ack，只有一个消息处理成功，宕机了。发生消息丢失
     *          解决思路：消费者手动确认，只要我们没有明确告诉MQ，消息被接收。没有ack，消息就一直是Unacked状态。
     *              即使Consumer宕机。消息不会丢失，会重新变为Ready。下次有新的Consumer进来就重新发给他
     *      2）、如何签收：
     *          channel.basicAck(deliveryTag,false);签收：业务成功完成就应该签收
     *          channel.basicNack(deliveryTag,false);拒签：业务失败，拒签
     */
    //@PostConstruct // MallRabbitConfig对象创建完成以后，执行这个方法
    public void initRabbitTemplate(){
        // 设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 当前消息的唯一关联数据（这个是消息的唯一id）
             * @param ack 消息是否成功收到
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                // 服务器收到了
            }
        });

        // 设置消息抵达队列的确认回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param message 投递失败的消息详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange 当时这个消息发给哪个交换机
             * @param routingKey 当时这个消息用哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                // 报错了。修改数据库当前的状态->错误
            }
        });
    }
}
