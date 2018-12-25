package com.calabashbrothers.common.redis;

import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/10/23
 * @Time: 12:51
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 写入redis缓存（不设置expire存活时间）
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 写入redis缓存（设置expire存活时间）
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    @Override
    public boolean set(final String key, String value, Long expire) {
        boolean result = false;
        try {
            ValueOperations operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return result;
    }


    /**
     * 读取redis缓存
     *
     * @param key
     * @return
     */
    @Override
    public Object get(final String key) {
        Object result = null;
        try {
            ValueOperations operations = stringRedisTemplate.opsForValue();
            result = operations.get(key);
        } catch (Exception e) {
            log.error("读取redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 判断redis缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(final String key) {
        boolean result = false;
        try {
            result = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     *
     * @param key
     * @return
     */
    @Override
    public boolean remove(final String key) {
        boolean result = false;
        try {
            if (exists(key)) {
                stringRedisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            log.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据keys批量删除对应的value
     *
     * @param keys
     * @return
     */
    @Override
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 存放 类
     *
     * @return boolean
     * @Author YatXuan
     * @Description //TODO
     * @Date 14:42 2018/10/30 0030
     * @Param [key, value]
     **/
    @Override
    public  boolean setClass(String key,  Object value) {
        return set(key, JSONObject.toJSONString(value));
    }

    /**
     * 取出 类
     *
     * @return
     * @Author YatXuan
     * @Description //TODO
     * @Date 14:43 2018/10/30 0030
     * @Param
     **/
    @Override
    public <T> T getClazz(String key, Class<T> clazz) {
        Object o = get(key);
        return JSONObject.parseObject(o.toString(), clazz);
    }

    /**
     * 存放list集合
     *
     * @return boolean
     * @Author YatXuan
     * @Description //TODO
     * @Date 15:10 2018/10/30 0030
     * @Param [key, list]
     **/
    @Override
    public <T> boolean setList(String key, List<T> list) {
        return set(key, JSONObject.toJSONString(list));
    }

    /**
     * 取出list集合
     *
     * @return java.util.List<java.lang.Object>
     * @Author YatXuan
     * @Description //TODO
     * @Date 15:10 2018/10/30 0030
     * @Param [key]
     **/
    @Override
    public List<Object> getList(String key) {
        Object o = get(key);
        return JSONObject.parseArray(o.toString(), Object.class);
    }
}
