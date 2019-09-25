package test.Jedis;

import com.test.elasticsearch.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

/**
 * @ProjectName: elasticsearch
 * @Package: test.Jedis
 * @ClassName: MessageSubProducer
 * @Author: guang
 * @Description: 发布订阅生产者
 * @Date: 2019/9/24 20:12
 * @Version: 1.0
 */
public class MessageSubProducer extends Thread {

    public static final String CHANNEL_KEY = "channel:1";
    private volatile int count;

    public void putMessage(String message) {
        Jedis jedis = JedisPoolUtils.getJedis();
        // 返回订阅者数量
        Long publish = jedis.publish(CHANNEL_KEY, message);
        System.out.println(Thread.currentThread().getName() + " put message,count=" + count+",subscriberNum="+publish);
        count++;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            putMessage("message" + count);
        }
    }

    public static void main(String[] args) {
        MessageSubProducer producer = new MessageSubProducer();
        Thread t1 = new Thread(producer, "thread1");
        Thread t2 = new Thread(producer, "thread2");
        Thread t3 = new Thread(producer, "thread3");
        Thread t4 = new Thread(producer, "thread4");
        Thread t5 = new Thread(producer, "thread5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
