package com.test.elasticsearch.event;

import com.test.elasticsearch.domain.Order;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.event
 * @ClassName: ContentEvent
 * @Author: guang
 * @Description: 定义事件体（消息）
 * @Date: 2019/10/17 17:57
 * @Version: 1.0
 */
@Data
public class OrderEvent extends ApplicationEvent {

    /**
     * 抽象主题内容（注册用户对象）
     */
    private String name;

    /**
     * 订单
     */
    private Order order;

    public OrderEvent(Object source) {
        super(source);
    }

    /**
     * 重写构造函数
     * @param source 发生事件的对象
     * @param name 操作名称
     * @param name 消息参数
     */
    public OrderEvent(Object source, String name, Order order) {
        super(source);
        this.name = name;
        this.order = order;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}
