package com.test.elasticsearch.config.listener;

import com.test.elasticsearch.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.config.listener
 * @ClassName: WechatListener
 * @Author: guang
 * @Description: 微信监听器
 * @Date: 2019/10/18 13:45
 * @Version: 1.0
 */
@Component
@Slf4j
public class WechatListener {

    @Async
    @EventListener
    public void handle(OrderEvent orderEvent) {
        log.info("微信通知成功，订单号" + orderEvent.getOrder().getOrderCode() + "创建成功！");
    }
}
