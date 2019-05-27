package com.test.elasticsearch.config.rabbitmq;

import com.google.common.collect.Maps;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author guang
 * @title: RabbitmqConf
 * @projectName elasticDemo
 * @description: rabbitmq配置类
 * @date 2019/5/2622:18
 */
@Configuration
public class RabbitmqConf {

    /**
     * 消息交换机的名字
     * */
    public static final String DIRECT_EXCHANGE = "DirectExchange";
    public static final String TOPIC_EXCHANGE = "TopicExchange";
    public static final String FANOUT_EXCHANGE ="FanoutExchange" ;
    public static final String HEADERS_EXCHANGE ="HeadersExchange" ;

    /**
     * 队列的名字
     * */
    public static final String DIRECT_QUEUE = "DirectQueue";
    public static final String TOPIC_QUEUE = "TopicQueue";
    public static final String FANOUT_QUEUE = "FanoutQueue";
    public static final String HEADERS_QUEUE = "HeadersQueue";

    /**
     * key
     * */
    public static final String DIRECT_KEY = "DirectKey";
    public static final String TOPIC_KEY = "Topic.#";

    /**
     * 1.队列名字
     * 2.durable="Boolean.TRUE" 是否持久化 rabbitmq重启的时候不需要创建新的队列
     * 3.auto-delete    表示消息队列没有在使用时将被自动删除 默认是Boolean.FALSE
     * 4.exclusive      表示该消息队列是否只在当前connection生效,默认是Boolean.FALSE
     * @author  GuangWei
     * @param
     * @return  org.springframework.amqp.core.Queue
     * @exception
     * @date       2019/5/26 22:21
     */
    @Bean
    public Queue dirctQueue() {
        return new Queue(DIRECT_QUEUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE);
    }
    @Bean
    public Queue topicQueue() {
        return new Queue(TOPIC_QUEUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE);
    }
    @Bean
    public Queue fanoutQueue() {
        return new Queue(FANOUT_QUEUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE);
    }
    @Bean
    public Queue headersQueue() {
        return new Queue(HEADERS_QUEUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE);
    }

    /**
     * 1.交换机名字
     * 2.durable="Boolean.TRUE" 是否持久化 rabbitmq重启的时候不需要创建新的交换机
     * 3.autoDelete    当所有消费客户端连接断开后，是否自动删除队列
     * @author  GuangWei
     * @param
     * @return  org.springframework.amqp.core.DirectExchange
     * @exception
     * @date       2019/5/26 22:50
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE,Boolean.TRUE,Boolean.FALSE);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE,Boolean.TRUE,Boolean.FALSE);
    }
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE,Boolean.TRUE,Boolean.FALSE);
    }
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE,Boolean.TRUE,Boolean.FALSE);
    }

    // 精准匹配
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(dirctQueue()).to(directExchange()).with(DIRECT_KEY);
    }
    // 泛型匹配
    @Bean
    public Binding bindingTopic() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(TOPIC_KEY);
    }
    // 广播
    @Bean
    public Binding bindingFanout() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
    // 通过消息headers的键值对匹配
    @Bean
    public Binding headersBinding(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("headers1","value1");
        map.put("headers2","value2");
        // 做绑定的时候有两种匹配方式供选择。x-match (all/any),意思就是键值对中所有的项都要匹配与只要有一个匹配就可以。
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }

    /**
     * 定义消息转换实例  转化成 JSON 传输  传输实体就可以不用实现序列化
     * @author  GuangWei
     * @param
     * @return  org.springframework.amqp.support.converter.MessageConverter
     * @exception
     * @date       2019/5/26 22:21
     */
    @Bean
    public MessageConverter integrationEventMessageConverter() {
        return  new Jackson2JsonMessageConverter();
    }
}
