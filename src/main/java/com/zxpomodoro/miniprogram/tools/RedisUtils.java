package com.zxpomodoro.miniprogram.tools;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    public boolean isHaveKey(final String key) {
        return redisTemplate.opsForValue().get(key) != null;
    }

    // 读取
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 写入
    public boolean set(final String key, String value) {
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 更新
    public boolean getAndSet(final String key, String value) {
        try {
            redisTemplate.opsForValue().getAndSet(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 删除
    public boolean delete(final String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
