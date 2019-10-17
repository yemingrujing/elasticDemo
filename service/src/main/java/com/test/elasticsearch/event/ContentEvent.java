package com.test.elasticsearch.event;

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
public class ContentEvent extends ApplicationEvent {

    /**
     * 抽象主题内容（注册用户对象）
     */
    private String content;

    public ContentEvent(Object source) {
        super(source);
    }

    /**
     * 重写构造函数
     * @param source 发生事件的对象
     * @param content 注册用户对象
     */
    public ContentEvent(Object source, String content) {
        super(source);
        this.content = content;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}
