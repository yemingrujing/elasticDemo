package com.test.elasticsearch.config.redis;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.config.redis
 * @ClassName: RedisService
 * @Author: guang
 * @Description:
 * @Date: 2019/6/13 19:00
 * @Version: 1.0
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 缓存是否开启，默认开启,true开启，false不开启
     */
    private volatile boolean redisSwitch = true;


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        if (redisSwitch) {
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        if (redisSwitch) {
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(final String key, Object value) {
        boolean result = false;
        if (redisSwitch) {
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                return operations.setIfAbsent(key,value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getAndSet(final String key, Object value){
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            return (String) operations.getAndSet(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void expire(final String key, int timeout, TimeUnit unit) {
        if (redisSwitch) {
            redisTemplate.expire(key, Long.valueOf(timeout), unit);
        }
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        if (redisSwitch) {
            for (String key : keys) {
                remove(key);
            }
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        if (redisSwitch) {
            Set<Serializable> keys = redisTemplate.keys(pattern);
            if (keys.size() > 0) {
                redisTemplate.delete(keys);
            }
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (redisSwitch) {
            if (exists(key)) {
                redisTemplate.delete(key);
            }
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        if (redisSwitch) {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Object result = operations.get(key);
            return result;
        }
        return null;
    }



    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        if (redisSwitch) {
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            hash.put(key, hashKey, value);
        }
    }

    /**
     * 哈希 移除
     *
     * @param key
     * @param hashKey
     */
    public void hDel(String key, Object hashKey) {
        if (redisSwitch) {
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            hash.delete(key, hashKey);
        }
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public <T> T hmGet(String key, Object hashKey, Class<T> clazz) {
        if (redisSwitch) {
            try {
                if (null == hashKey || StringUtils.isBlank(key)) {
                    return null;
                }
                String objectJson = (String) redisTemplate.opsForHash().get(key, hashKey);
                if (StringUtils.isBlank(objectJson)) {
                    return null;
                }
                return JSON.parseObject(objectJson.trim(), clazz);
            } catch (Throwable e) {
                log.error("hmGet key:" + key + " fieldkey:" + hashKey + " error:", e);
            }
        }
        return null;
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String hmGetString(String key, Object hashKey) {
        if (redisSwitch) {
            try {
                String objectJson = (String) redisTemplate.opsForHash().get(key, hashKey);
                if (StringUtils.isBlank(objectJson)) {
                    return StringUtils.EMPTY;
                }
                return objectJson.trim();
            } catch (Throwable e) {
                log.error("hmGet key:" + key + " fieldkey:" + hashKey + " error:", e);
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Integer hmGetIntegerValue(String key, Object hashKey) {
        if (redisSwitch) {
            try {
                Integer cacheValue = (Integer) redisTemplate.opsForHash().get(key, hashKey);
                return cacheValue;
            } catch (Throwable e) {
                log.error("hmGetIntegerValue key:" + key + " fieldkey:" + hashKey + " error:", e);
            }
        }
        return null;
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        if (redisSwitch) {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            list.rightPush(k, v);
        }
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        if (redisSwitch) {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            return list.range(k, l, l1);
        }
        return null;
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        if (redisSwitch) {
            SetOperations<String, Object> set = redisTemplate.opsForSet();
            set.add(key, value);
        }
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        if (redisSwitch) {
            SetOperations<String, Object> set = redisTemplate.opsForSet();
            return set.members(key);
        }
        return null;
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        if (redisSwitch) {
            ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
            zset.add(key, value, scoure);
        }
    }
    /**
     * 有序集合删除
     *
     * @param key
     * @param value
     */
    public void zRemove(String key, Object value) {
        if (redisSwitch) {
            ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
            zset.remove(key,value);
        }
    }

    /**
     * 有序集合大小
     *
     * @param key
     */
    public long zSize(String key) {
        if (redisSwitch) {
            return redisTemplate.opsForZSet().size(key);
        }
        return -1;
    }

    /**
     * 有序集合移除
     *
     * @param key
     */
    public void zRemoveRange(String key) {
        if (redisSwitch) {
            redisTemplate.opsForZSet().removeRange(key, 0, 0);
        }
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        if (redisSwitch) {
            ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
            return zset.rangeByScore(key, scoure, scoure1);
        }
        return null;
    }

    public <T> Set<T> reverseRange(String key, int start, int end, Class<T> clazz) {
        if (redisSwitch) {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        }
        return null;
    }

    public <T> Set<T> range(String key, int start, int end, Class<T> clazz) {
        if (redisSwitch) {
            return redisTemplate.opsForZSet().range(key, start, end);
        }
        return null;
    }
    public <T> Set<T> reverseRangeByScore(String key, int start, int end, Class<T> clazz) {
        if (redisSwitch) {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
        }
        return null;
    }

    public <T> Set<T> rangeByScore(String key, int start, int end, Class<T> clazz) {
        if (redisSwitch) {
            return redisTemplate.opsForZSet().rangeByScore(key, start, end);
        }
        return null;
    }



    /**
     * 保存缓存
     *
     * @param redisKey
     * @param map
     * @param timeout
     * @param unit
     */
    public void hsetAll(String redisKey, Map<String, String> map, long timeout, TimeUnit unit) {
        if (redisSwitch) {
            try {
                BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
                boundHashOperations.putAll(map);
                boundHashOperations.expire(timeout, unit);
            } catch (Exception e) {
                log.error("hsetAll key:" + redisKey + " error:", e);
            }
        }
    }

    /**
     * 保存缓存
     *
     * @param redisKey
     * @param map
     */
    public void hsetAllValueLong(String redisKey, Map<String, Long> map) {
        if (redisSwitch) {
            try {
                BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
                boundHashOperations.putAll(map);
            } catch (Exception e) {
                log.error("hsetAllValueLong key:" + redisKey + " error:", e);
            }
        }
    }

    /**
     * 保存缓存
     *
     * @param redisKey
     * @param map
     */
    public void hsetAll(String redisKey, Map<String, String> map) {
        if (redisSwitch) {
            try {
                BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
                boundHashOperations.putAll(map);
            } catch (Exception e) {
                log.error("hsetAll key:" + redisKey + " error:", e);
            }
        }
    }

    /**
     * 获取MAP 对应HSETALL(KEY)
     *
     * @param redisKey INFO
     * @return BASE, EXTENDS
     */
    public Map<String, String> hgetall(String redisKey) {
        if (redisSwitch) {
            try {
                HashOperations<String, String, String> opt = redisTemplate.opsForHash();
                return opt.entries(redisKey);
            } catch (Exception e) {
                log.error("hgetall KEY:" + redisKey + " error:", e);
            }
        }
        return null;
    }

    /**
     * 增加map集合key得value值
     *
     * @param redisKey
     * @param hashKey
     * @param initAddNum
     * @return
     */
    public Long hincrby(final String redisKey, final String hashKey, long initAddNum) {
        if (redisSwitch) {
            try {
                Long after = redisTemplate.opsForHash().increment(redisKey, hashKey, initAddNum);
                return after;
            } catch (Throwable e) {
                log.error("hincrby key:" + redisKey + " fieldkey:" + hashKey + " error:", e);
            }
        }
        return null;
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param redisKey
     * @param hashKey
     * @return
     */
    public boolean hExists(final String redisKey, final String hashKey) {
        if (redisSwitch) {
            return redisTemplate.opsForHash().hasKey(redisKey, hashKey);
        }
        return false;
    }

    public void sAddOne(final String key, final Object value, int expireTime, TimeUnit timeUnit) {
        if (redisSwitch) {
            redisTemplate.opsForSet().add(key, value.toString());
            redisTemplate.expire(key, expireTime, timeUnit);
        }
    }

    public void sDelOne(final String key, final Object value) {
        if (redisSwitch) {
            redisTemplate.opsForSet().remove(key, value.toString());
        }
    }

    public boolean sIsMember(final String key, final Object value) {
        if (ObjectUtil.isNotEmpty(key) && value != null) {
            return this.redisTemplate.boundSetOps(key).isMember(value.toString());
        }
        return false;
    }

    /**
     * 自增redis的value
     *
     * @param redisKey
     * @param value
     * @return 自增后的值
     */
    public Long increaseValue(String redisKey, long value, int timeout, TimeUnit timeUnit) {
        long result = 0L;
        if (redisSwitch) {
            try {
                result = redisTemplate.opsForValue().increment(redisKey, value);
                redisTemplate.expire(redisKey, timeout, timeUnit);
            } catch (Exception e) {
                log.error("increaseValue error:", e);
            }
        }
        return result;
    }

    public Long increaseValue(String redisKey, long value){
        long result = 0L;
        if (redisSwitch) {
            try {
                result = redisTemplate.opsForValue().increment(redisKey, value);
            } catch (Exception e) {
                log.error("increaseValue error:", e);
            }
        }
        return result;
    }

    public Integer incr(String key, Long liveTime) {
        RedisAtomicInteger inviteCounter  = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        Integer increment = inviteCounter.getAndIncrement();
        if ((null == increment || increment.intValue() == 0) && liveTime > 0) {
            // 初始设置过期时间 单位毫秒
            inviteCounter.expire(liveTime, TimeUnit.MILLISECONDS);
        }
        return increment;
    }

    /**
     * 现在到指定时间毫秒数
     * @return
     */
    public static Long getCurrent2TodayEndMillisTime(Date date) {
        return date.getTime() - System.currentTimeMillis();
    }
}
