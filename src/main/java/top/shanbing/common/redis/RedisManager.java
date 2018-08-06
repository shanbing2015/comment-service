package top.shanbing.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import redis.clients.util.SafeEncoder;
import top.shanbing.common.stringConvertor.GsonStringConverter;
import top.shanbing.common.stringConvertor.StringConverter;

import java.io.UnsupportedEncodingException;

/**
 * @author shanbing
 * @date 2018/8/4.
 *
 * todo: JedisConnectionFactory 在spring boot 2 中已无自动配置
 */

@Component
@EnableAsync
public class RedisManager implements IRedisManager{

    protected StringConverter converter = new GsonStringConverter();
    private static final String CHARSET = "UTF-8";

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private static final Logger logger = LoggerFactory.getLogger(RedisManager.class);

    @Override
    public <T> void setex(String key, int ttl, T data, boolean sync) {
        //同步写入
        if (sync) {
            setex(key, ttl, data);
        } else {
            asyncSetex(key, ttl, data);
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return convert(get(key), clazz);
    }

    @Override
    public byte[] get(String key) {
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        try {
            //get操作用同一个connection
            return redisConnection.get(SafeEncoder.encode(key));
        } finally {
            redisConnection.close();
        }
    }

    @Override
    public boolean isExist(String key) {
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        try {
            return redisConnection.exists(SafeEncoder.encode(key));
        } finally {
            redisConnection.close();
        }
    }

    @Override
    public Long delete(String key) {
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        try {
            return redisConnection.del(SafeEncoder.encode(key));
        } finally {
            redisConnection.close();
        }
    }

    @Override
    public <T> T convert(byte[] value, Class<T> clazz) {
        if (value != null) {
            //原始类型直接转型返回
            if (clazz.isPrimitive()) {
                String temp;
                try {
                    temp = new String(value, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    logger.error("UnsupportedEncodingException", e);
                    return null;
                }
                if (String.class.isAssignableFrom(clazz)) {
                    return (T) temp;
                } else if (Integer.class.isAssignableFrom(clazz)) {
                    return (T) new Integer(temp);
                } else if (Long.class.isAssignableFrom(clazz)) {
                    return (T) new Long(temp);
                } else if (Boolean.class.isAssignableFrom(clazz)) {
                    return (T) new Boolean(temp);
                } else if (Double.class.isAssignableFrom(clazz)) {
                    return (T) new Double(temp);
                } else if (Float.class.isAssignableFrom(clazz)) {
                    return (T) new Float(temp);
                } else if (Byte.class.isAssignableFrom(clazz)) {
                    return (T) new Byte(temp);
                } else if (Short.class.isAssignableFrom(clazz)) {
                    return (T) new Short(temp);
                } else if (Character.class.isAssignableFrom(clazz)) {
                    return (T) new Character((char) (((value[0] & 0xFF) << 8) | (value[1] & 0xFF)));
                } else {
                    throw new RuntimeException("unsupport type:type=" + clazz.getName() + "value=" + temp);
                }
            } else {
                return converter.fromString(SafeEncoder.encode(value),clazz);
            }
        }
        return null;
    }

    private <T> void setex(String key, int ttl, T data) {
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        try {
            byte[] dataBytes;
            //如果是基本类型，直接处理
            if (!data.getClass().isPrimitive()) {
                dataBytes = converter.toString(data).getBytes(CHARSET);
            }else{
                dataBytes = SafeEncoder.encode(data.toString());
            }
            redisConnection.setEx(key.getBytes(CHARSET), ttl, dataBytes);
        }catch (Exception e){
            logger.error("数据存入redis错误", e);
        }finally {
            redisConnection.close();
        }
    }

    @Async
    <T> void asyncSetex(String key, int ttl, T data) {
        setex(key, ttl, data);
    }

    @Override
    public Long incr(String key,long tt) {
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        try {
            Long count = redisConnection.incr(key.getBytes(CHARSET));
            redisConnection.expire(key.getBytes(CHARSET),tt);
            return count;
        }catch (Exception e){
            logger.error("数据存入redis错误", e);
        }finally {
            redisConnection.close();
        }
        return 0L;
    }
}
