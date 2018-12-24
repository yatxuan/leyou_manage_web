package com.calabashbrothers.common.redis;

import java.util.List;

public interface RedisService {

    /**
     * set存数据 （不设置expire存活时间）
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(final String key, String value);

    /**
     * 写入redis缓存（设置expire存活时间）
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    boolean set(final String key, String value, Long expire);

    /**
     * get获取数据
     *
     * @param key
     * @return
     */
    Object get(final String key);

    /**
     * 判断redis缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    boolean exists(final String key);

    /**
     * 移除数据
     *
     * @param key
     * @return
     */
    boolean remove(final String key);

    /**
     * redis根据keys批量删除对应的value
     *
     * @param keys
     * @return
     */
    void remove(final String... keys);

    /**
     * 存放 类
     *
     * @author Yat-Xuan
     * @DESCRIPTION:
     * @params: [key, value]
     * @return: boolean
     * @Date: 2018/10/31 0031 19:31
     * @Modified By:
     */
    boolean setClass(String key, Object value);

    /**  */
    <T> T getClazz(String key, Class<T> clazz);

    /**
     * <p>Description: 存放list集合 </p>
     * @author Yat-Xuan
     * @params: [key, list]
     * @return: boolean
     * @Date: 2018/10/31 0031 19:48
     * @Modified By:
    */
    <T> boolean setList(String key, List<T> list);

    /**
     * @author Yat-Xuan
     * @DESCRIPTION: 描述
     * @params: [key]
     * @return: java.util.List<T>
     * @Date: 2018/10/31 0031 19:45
     * @Modified By:
    */
    <T> List<T> getList(String key);
}
