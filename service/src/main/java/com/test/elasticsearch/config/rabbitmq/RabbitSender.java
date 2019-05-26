package com.test.elasticsearch.config.rabbitmq;

import com.test.elasticsearch.elasticsearch.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

/**
 * @author guang
 * @title: RabbitSender
 * @projectName elasticDemo
 * @description: Ribbitmq发送消息
 * @date 2019/5/2621:40
 */
@Slf4j
@Component
public class RabbitSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    
    /**
     * 实现消息发送到RabbitMQ交换器后接收ack回调,如果消息发送确认失败就进行重试
     * @author  GuangWei
     * @param correlationData
     * @param ack
     * @param cause
     * @return  void
     * @exception  
     * @date       2019/5/26 22:04
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功,消息ID:{}", correlationData.getId());
        } else {
            log.info("消息发送失败,消息ID:{}", correlationData.getId());
        }
    }

    /**
     * 实现消息发送到RabbitMQ交换器,但无相应队列与交换器绑定时的回调.
     * @author  GuangWei
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     * @return  void
     * @exception  
     * @date       2019/5/26 22:05
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息发送失败，replyCode:{}, replyText:{}，exchange:{}，routingKey:{}，消息体:{}",replyCode, replyText, exchange, routingKey, new String(message.getBody()));
    }

   /**
    * convertAndSend 异步,消息是否发送成功用ConfirmCallback和ReturnCallback回调函数类确认。
    * 发送MQ消息
    * @author  GuangWei
    * @param exchangeName 交换机
    * @param routingKey key
    * @param message 消息
    * @return  void
    * @exception
    * @date       2019/5/26 22:37
    */
    public void sendMessage(String exchangeName, String routingKey, Object message) {
        // id + 时间戳 全局唯一
        String id = UUID.randomUUID().toString();
        log.info("id:{}", id);
        CorrelationData correlationData = new CorrelationData(id);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, correlationData);
    }

    /**
     * sendMessageAndReturn 当发送消息过后,该方法会一直阻塞在哪里等待返回结果,直到请求超时,配置spring.rabbitmq.template.reply-timeout来配置超时时间。
     * 发送MQ消息并返回结果
     * @author  GuangWei
     * @param exchangeName
     * @param routingKey
     * @param message
     * @return  java.lang.Object
     * @exception
     * @date       2019/5/26 22:58
     */
    public Object sendMessageAndReturn(String exchangeName, String routingKey, Object message) {
        // id + 时间戳 全局唯一
        String id = UUID.randomUUID().toString();
        log.info("id:{}", id);
        CorrelationData correlationData = new CorrelationData(id);
        return rabbitTemplate.convertSendAndReceive(exchangeName, routingKey, message, correlationData);
    }

    /**
     * 发送消息方法调用: 构建自定义对象消息
     * @author  GuangWei
     * @param order
     * @return  void
     * @exception
     * @date       2019/5/26 22:44
     */
    public void sendOrder(Order order) {
        //id + 时间戳 全局唯一
        String id = UUID.randomUUID().toString();
        log.info("sendOrder id: {}", id);
        CorrelationData correlationData = new CorrelationData(id);
        rabbitTemplate.convertAndSend("exchange-1", "springboot.def", order, correlationData);
    }
}
