package test.Jedis;

import com.test.elasticsearch.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @ProjectName: elasticsearch
 * @Package: test.Jedis
 * @ClassName: MessageSubConsumer
 * @Author: guang
 * @Description: 发布订阅消费者
 * @Date: 2019/9/24 20:12
 * @Version: 1.0
 */
public class MessageSubConsumer implements Runnable {

    public static final String CHANNEL_KEY = "channel*";//频道

    public static final String EXIT_COMMAND = "exit";//结束程序的消息

    private MyJedisPubSub myJedisPubSub = new MyJedisPubSub();//处理接收消息

    public void consumerMessage() {
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.psubscribe(myJedisPubSub, CHANNEL_KEY);//第一个参数是处理接收消息，第二个参数是订阅的消息频道
    }

    @Override
    public void run() {
        while (true) {
            consumerMessage();
        }
    }

    public static void main(String[] args) {
        MessageConsumer messageConsumer = new MessageConsumer();
        Thread t1 = new Thread(messageConsumer, "thread5");
        Thread t2 = new Thread(messageConsumer, "thread6");
        t1.start();
        t2.start();
    }
}

/**
 * 继承JedisPubSub，重写接收消息的方法
 */
class MyJedisPubSub extends JedisPubSub {
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(Thread.currentThread().getName()+"-接收到消息:pattern="+pattern+",channel=" + channel + ",message=" + message);
        //接收到exit消息后退出
        if (MessageSubConsumer.EXIT_COMMAND.equals(message)) {
            System.exit(0);
        }
    }
}
