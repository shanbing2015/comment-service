package top.shanbing.common.redis;

/**
 * @author shanbing
 * @date 2018/8/4.
 */
public interface IRedisManager {
    /**
     * * 将对象缓存到redis
     * * @param key key
     * * @param ttl 过期时间
     * * @param data 要缓存的对象
     * * @param sync 是否同步处理，true=同步处理,false=非同步处理(异步,注意不要修改对象data)
     */
    <T> void setex(String key, int ttl, T data, boolean sync);

    /**从redis里取出缓存的bean或原始类型*/
    <T> T get(String key, Class <T> clazz);

    /** 将从 reids 获取的二进制数据反序列化成java 对象*/
    <T> T convert(byte[] value, Class <T> clazz);

    /**取出 redis 里缓存的二进制数据*/
    byte[] get(String key);


    /**判断redis中是否存在*/
    boolean isExist(String key);

    /**删除redis*/
    Long delete(String key);

    Long incr(String key,Long tt);

    Long pttl(String key);
}
