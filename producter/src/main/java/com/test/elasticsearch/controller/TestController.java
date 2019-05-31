package com.test.elasticsearch.controller;

import com.test.elasticsearch.config.rabbitmq.RabbitSender;
import com.test.elasticsearch.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guang
 * @title: TestController
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/5/2223:19
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private RabbitSender rabbitSender;

    @GetMapping("/test")
    public void test(){
        log.info("进入test方法");
        log.debug("进入test方法");
        log.warn("进入test方法");
        log.error("进入test方法");
    }

    /**
     * 测试MQ自定义消息
     */
    @GetMapping("/mq/send/order")
    public void testMqOrder(){
        Order order = new Order();
        order.setId(2936);
        order.setOrderCode("42019030816050432845");
        order.setUserId(745);
        rabbitSender.sendOrder(order);
    }
}
