package com.test.elasticsearch.config.listener;

import com.test.elasticsearch.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.config.listener
 * @ClassName: ContentListener
 * @Author: guang
 * @Description: 定义事件监听(接收者)  短信监听器
 * @Date: 2019/10/17 19:03
 * @Version: 1.0
 */
@Component
@Slf4j
public class SmsListener {

    @Async
    @EventListener
    public void handler(OrderEvent orderEvent) {
        log.info("短信发送成功，订单号" + orderEvent.getOrder().getOrderCode() + "创建成功！");
    }
}
