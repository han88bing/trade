package com.touke.trade.util.lock.impl;





import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import com.touke.trade.util.lock.AbstractDistributedLock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2019/8/6.
 */
@Slf4j
@Component
public class RedisDistributedLock extends AbstractDistributedLock {

    private final static List<String> CONSTANTS = Arrays.asList("1");

    private final static String CONSTANTS_STR = "1";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;



    private static final String UNLOCK_LUA;

    private static final String FUZZY_UNLOCK_LUA;

    /*
     * 通过lua脚本释放锁,来达到释放锁的原子操作
     */
    static {
        UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                "then " +
                "    return redis.call(\"del\",KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end ";
    }


    static{
        FUZZY_UNLOCK_LUA = "local t1=redis.call('KEYS',KEYS[1]) for k,v in pairs(t1) do redis.call('del',v) print(v) end";
    }

    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = setRedis(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                log.info("get redisDistributeLock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            result = setRedis(key, expire);
        }
        return result;
    }

    private boolean setRedis(final String key, final long expire) {
        try {
            String status = redisTemplate.execute((RedisCallback<String>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
//                String uuid = UUID.randomUUID().toString();
//                lockFlag.set(uuid);
//                String uuid = "1";
                String result = null;
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    result = ((JedisCluster) nativeConnection).set(key, CONSTANTS_STR, "NX", "PX", expire);
                }
                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    result = ((Jedis) nativeConnection).set(key, CONSTANTS_STR, "NX", "PX", expire);
                }
                return result;
            });
            return !StringUtils.isEmpty(status);
        } catch (Exception e) {
            log.error("set redisDistributeLock occured an exception", e);
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            final List<String> keys = new ArrayList<>();
            keys.add(key);
//            final List<String> args = new ArrayList<>();
//            args.add(lockFlag.get());
            final List<String> args = CONSTANTS;

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本

            Long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            });

            return result != null && result > 0;
        } catch (Exception e) {
            log.error("release redisDistributeLock occured an exception", e);
        }
//        finally {
//            lockFlag.remove();
//        }
        return false;
    }

    /**
     * 模糊删除key的 ，注意不要删错了
     * @param key
     */
    public void fuzzyReleaseLock(String key) {
        try {
            redisTemplate.execute((RedisCallback<String>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    ((JedisCluster) nativeConnection).eval(FUZZY_UNLOCK_LUA,1,key);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    ((Jedis) nativeConnection).eval(FUZZY_UNLOCK_LUA,1,key);
                }
                return "";
            });
        } catch (Exception e) {
            log.error("release redisDistributeLock occured an exception", e);
        }
    }
}