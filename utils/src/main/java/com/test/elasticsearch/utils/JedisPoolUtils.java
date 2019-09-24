package com.test.elasticsearch.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils
 * @ClassName: JedisPoolUtils
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/9/24 15:59
 * @Version: 1.0
 */
public class JedisPoolUtils {

    private static JedisPool pool;

    static {
        // 加载配置文件
        InputStream in = JedisPoolUtils.class.getClassLoader().getResourceAsStream("test/redis.properties");
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取的池子对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大闲置个数
        poolConfig.setMaxIdle(Integer.parseInt(pro.get("redis.maxIdle").toString()));
        // 最大闲置个数
        poolConfig.setMaxWaitMillis(Integer.parseInt(pro.get("redis.maxWait").toString()));
        // 最小闲置个数
        poolConfig.setMinIdle(Integer.parseInt(pro.get("redis.minIdle").toString()));
        // 最大连接数
        poolConfig.setMaxTotal(Integer.parseInt(pro.get("redis.maxTotal").toString()));
        pool = new JedisPool(poolConfig, pro.getProperty("redis.url"), Integer.parseInt(pro.get("redis.port").toString()), Integer.parseInt(pro.get("redis.timeout").toString()), pro.get("redis.password").toString());
    }

    //获得jedis资源的方法
    public static Jedis getJedis() {
        return pool.getResource();
    }
}
