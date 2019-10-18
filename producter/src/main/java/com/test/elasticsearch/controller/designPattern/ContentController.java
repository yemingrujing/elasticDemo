package com.test.elasticsearch.controller.designPattern;

import com.test.elasticsearch.domain.Order;
import com.test.elasticsearch.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller.designPattern
 * @ClassName: ContentController
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/10/17 19:05
 * @Version: 1.0
 */
@RestController
public class ContentController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/event/order")
    public void sendEvent() {
        applicationContext.publishEvent(new OrderEvent(this, "订单创建", Order.builder().orderCode("120192121321312312").build()));
    }
}
