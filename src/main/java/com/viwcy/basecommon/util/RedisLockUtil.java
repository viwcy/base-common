package com.viwcy.basecommon.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * TODO //redis分布式锁
 *
 * <p> Title: RedisLockUtil </p>
 * <p> Description: RedisLockUtil </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Component
public class RedisLockUtil {

    /**
     * 锁释放脚本，原子化操作
     */
    private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    /**
     * 锁释放成功标志
     */
    private static final Long RELEASE_SUCCESS = 1L;
    /**
     * 自旋操作，线程休眠时间，ms
     */
    private static final Long THREAD_SLEEP_TIME = 100L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * TODO 获取锁，带自旋次数，无过期时间
     * <p>
     * lockKey:锁键
     * value:无强制性含义，一般用作当前请求的唯一性，确保不会错解锁
     * tryCount:自旋次数
     */
    public boolean tryLock(String lockKey, String value, int tryCount) {
        boolean absent = false;
        while (!absent && tryCount > 0) {
            try {
                Thread.sleep(RedisLockUtil.THREAD_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            absent = redisTemplate.opsForValue().setIfAbsent(lockKey, value);
            tryCount--;
        }
        return absent;
    }

    /**
     * TODO 获取锁，带自旋次数，设过期时间
     * <p>
     * lockKey:锁键
     * value:无强制性含义，一般用作当前请求的唯一性，确保不会错解锁
     * tryCount:自旋次数
     * expire:锁过期时间
     * timeUnit:时间单位
     */
    public boolean tryLock(String lockKey, String value, int tryCount, long expire, TimeUnit timeUnit) {
        boolean absent = false;
        while (!absent && tryCount > 0) {
            try {
                Thread.sleep(RedisLockUtil.THREAD_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            absent = redisTemplate.opsForValue().setIfAbsent(lockKey, value, expire, timeUnit);
            tryCount--;
        }
        return absent;
    }

    /**
     * TODO 获取锁，无限次自旋，直到锁过期，设过期时间
     * <p>
     * lockKey:锁键
     * value:无强制性含义，一般用作当前请求的唯一性，确保不会错解锁
     * expire:锁过期时间
     * timeUnit:时间单位
     */
    public boolean tryLock(String lockKey, String value, long expire, TimeUnit timeUnit) {
        boolean absent = false;
        while (!absent) {
            try {
                Thread.sleep(RedisLockUtil.THREAD_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            absent = redisTemplate.opsForValue().setIfAbsent(lockKey, value, expire, timeUnit);
        }
        return absent;
    }

    /**
     * TODO 获取锁，无自旋，无过期时间
     * <p>
     * lockKey:锁键
     * value:无强制性含义，一般用作当前请求的唯一性，确保不会错解锁
     */
    public boolean lock(String lockKey, String value) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, value);
    }

    /**
     * TODO 获取锁，无自旋，无过期时间
     * <p>
     * lockKey:锁键
     * value:无强制性含义，一般用作当前请求的唯一性，确保不会错解锁
     * expire:锁过期时间
     * timeUnit:时间单位
     */
    public boolean lock(String lockKey, String value, long expire, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, value, expire, timeUnit);
    }

    /**
     * TODO 释放锁
     * <p>
     * lockKey:锁键
     * value:无强制性含义，一般用作当前请求的唯一性，确保不会错解锁
     */
    public boolean releaseLock(String lockKey, String value) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        redisScript.setScriptText(RedisLockUtil.SCRIPT);
        redisScript.setResultType(Long.class);
        Object execute = redisTemplate.execute(redisScript, Arrays.asList(lockKey), value);
        return RedisLockUtil.RELEASE_SUCCESS.equals(execute);
    }
}
