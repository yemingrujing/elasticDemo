package test.Jedis;

import com.test.elasticsearch.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: test.Jedis
 * @ClassName: MessageConsumer
 * @Author: guang
 * @Description: 消费者
 * @Date: 2019/9/24 19:52
 * @Version: 1.0
 */
public class MessageConsumer implements Runnable {

    public static final String MESSAGE_KEY = "message:queue";
    Jedis jedis = JedisPoolUtils.getJedis();

    @Override
    public void run() {
        while (true) {
            consumerMessage();
        }
    }

    public void consumerMessage() {
        // 0是timeout,返回的是一个集合，第一个是消息的key，第二个是消息的内容
        List<String> brpop = jedis.brpop(0, MESSAGE_KEY);
        System.out.println(Thread.currentThread().getName() + " consumer message,message=" + brpop);
    }

    public static void main(String[] args) {
        MessageConsumer messageConsumer = new MessageConsumer();
        Thread t1 = new Thread(messageConsumer, "thread8");
        Thread t2 = new Thread(messageConsumer, "thread9");
        t1.start();
        t2.start();
    }
}
