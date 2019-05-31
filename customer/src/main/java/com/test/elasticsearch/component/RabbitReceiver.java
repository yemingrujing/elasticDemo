package com.test.elasticsearch.component;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.QueryResults;
import com.rabbitmq.client.Channel;
import com.test.elasticsearch.config.rabbitmq.RabbitmqConf;
import com.test.elasticsearch.domain.Order;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.param.OrderParam;
import com.test.elasticsearch.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author guang
 * @title: RabbitReceiver
 * @projectName elasticDemo
 * @description: Rabbitmq接收消息
 * @date 2019/5/2622:56
 */
@Slf4j
@Component
public class RabbitReceiver {

    @Autowired
    private OrderService orderService;

   /**
    * 消费消息
    * @author  GuangWei
    * @param sendMessage
    * @param channel
    * @param message
    * @return  void
    * @exception
    * @date       2019/5/26 23:22
    */
    @RabbitListener(queues = RabbitmqConf.DIRECT_QUEUE)
    @RabbitHandler
    public void directMessage(@Payload String sendMessage, Channel channel, Message message) throws Exception {
        try {
            log.info("处理MQ消息");
            // prefetchCount限制每个消费者在收到下一个确认回执前一次可以最大接受多少条消息,通过basic.qos方法设置prefetch_count=1,这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message
            channel.basicQos(1);
            log.info("DirectConsumer {} directMessage :" + message);
            OrderParam param = JSON.parseObject(sendMessage, OrderParam.class);
            QueryResults<OrderDTO> list = orderService.query(param);
            log.info("订单查询信息：{}", JSON.toJSONString(list));
            // 确认消息已经消费成功,可以在队列安全删除，这样后面就不会再重发了
            // 否则消息服务器以为这条消息没处理掉，后续还会再发
            // 第二个参数是消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("MQ消息处理异常，消息ID：{}，消息体:{}", message.getMessageProperties().getCorrelationId(), sendMessage, e);
            // 拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    /**
     * 消费消息(自定义)
     * @author  GuangWei
     * @param channel
     * @return  void
     * @exception
     * @date       2019/5/26 23:05
     */
    @RabbitListener(queues = RabbitmqConf.TOPIC_QUEUE)
    @RabbitHandler
    public void onOrderMessage(org.springframework.messaging.Message message, Channel channel) throws IOException {
        Order order = (Order) message.getPayload();
        log.info("消费端order: " + order.getId());
        channel.basicQos(1);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        // 手工ACK, 确认消息已经消费成功
        channel.basicAck(deliveryTag, false);
    }
}
