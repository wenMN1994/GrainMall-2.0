package com.grain.mall.order.service.impl;

import com.grain.mall.order.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grain.common.utils.PageUtils;
import com.grain.common.utils.Query;

import com.grain.mall.order.dao.OrderItemDao;
import com.grain.mall.order.entity.OrderItemEntity;
import com.grain.mall.order.service.OrderItemService;


@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 测试RabbitMQ接收消息
     * queues 声明需要监听的所有队列
     *
     * org.springframework.amqp.core.Message
     * 参数可以参考以下类型：
     *  1、Message message：原生消息详细信息。消息头+消息体
     *  2、T<发送的消息类型>
     *  eg. OrderReturnReasonEntity content
     *  3、Channel channel：当前传输数据的通道
     */
//    @RabbitListener(queues = {"hello-java-queue"})
//    public void receiveMessage(Message message, OrderReturnReasonEntity content, Channel channel){
//
//        byte[] body = message.getBody();
//        // 消息头属性信息
//        MessageProperties messageProperties = message.getMessageProperties();
//        System.out.println("接收到消息"+message+"--->内容："+content);
//    }

}