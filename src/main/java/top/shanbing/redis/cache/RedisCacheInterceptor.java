package top.shanbing.redis.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.shanbing.redis.IRedisManager;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * redis cache aop拦截器
 * @author shanbing
 * @date 2018/8/28.
 */
@Component
@Aspect
public class RedisCacheInterceptor {
    protected static Logger logger = LoggerFactory.getLogger(RedisCacheInterceptor.class);


    @Autowired
    private IRedisManager redisManager;

    @Around("@annotation(redisCache)")
    public Object doAround(ProceedingJoinPoint pjp, RedisCache redisCache) throws Throwable {
        //是否要直接穿透redis，到db获取
        boolean stab = (redisCache.action() == RedisCacheAction.STAB_REDIS);

        //根据参数构建redisKey
        String redisKey = buildRedisKey(pjp, redisCache);

        //获取返回值的类型
        Class <?> returnType = getReturnType(pjp);

        if (!stab) {
            //不穿透，从redis中获取数据
            Object resultFromRedis = getDataFromRedis(redisKey, redisCache, returnType);
            //如果redis中获取的数据不为null，直接返回，不需要走业务方法
            if (resultFromRedis != null) {
                //判断是否空值，空值返回null
                if (resultFromRedis.toString().equals(RedisCache.NULL_VALUE)) {
                    return null;
                }else{
                    return resultFromRedis;
                }
            }

        }
        //穿透，从业务层代码（db）中获取数据，并缓存起来，便于下次命中
        return getDataFromDbAndDoCache(pjp, redisCache, redisKey);
    }

    /**从redis中获取已缓存的数据*/
    private Object getDataFromRedis(String redisKey, RedisCache redisCache, Class <?> returnType) {
        //获取redis中缓存的数据，如果需要穿透redis到db获取，则赋值为null
        byte[] value = redisManager.get(redisKey);
        //先判断redis中该key缓存的值是不是"null字符串"，是则返回null
        byte[] nullValue;
        nullValue = redisCache.nullValue().getBytes();
        if (value != null && redisCache.cacheNull() && Arrays.equals(value, nullValue)) {
            return redisCache.NULL_VALUE;
        } else {
            //返回经过类型转换的对象
            return redisManager.convert(value, returnType);
        }
    }

    /**穿透到业务层获代码获取数据，并缓存起来*/
    private Object getDataFromDbAndDoCache(ProceedingJoinPoint pjp, RedisCache redis, String redisKey) throws Throwable {
        //穿透，从业务方法获取数据
        Object resultFromDb = pjp.proceed();
        //缓存业务方法的数据，便于下次命中,这里可以异步存入，可以提升性能，暂时不做
        cacheDataToRedis(resultFromDb, redisKey, redis);
        return resultFromDb;
    }

    /**缓存数据到redis*/
    private void cacheDataToRedis(Object resultFromDb, String redisKey, RedisCache redis) {
        //数据若不为null，直接缓存
        if (resultFromDb != null) {
            redisManager.setex(redisKey, redis.ttl(), resultFromDb, redis.sync());
        } else if (redis.cacheNull()) {
            //若数据为空，缓存一个代表null的字符串到redis
            redisManager.setex(redisKey, redis.ttl(), RedisCache.NULL_VALUE, redis.sync());
        }
    }



    /** 构建redisKey*/
    private String buildRedisKey(ProceedingJoinPoint pjp, RedisCache redisCache) {
        //获取构建redis key的参数
        Object[] keyArgs = getKeyArgs(pjp.getArgs(), redisCache.keyArgs());
        //构建redis key
        String redisKey = keyArgs == null ? redisCache.value() : String.format(redisCache.value(), keyArgs);
        //若参数为空，输出警告
        if (keyArgs == null || keyArgs.length == 0 || keyArgs[0] == null) {
            logger.warn("key args is empty,key=" + redisKey);
        }
        return redisKey;
    }

    /**
     * 获得方法的返回值类型
     *
     * @param pjp
     * @return
     */
    private Class <?> getReturnType(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Class <?> returnType = method.getReturnType();
        return returnType;
    }

    /**
     * 获取构造redis的key的参数数组
     *
     * @param args    参数数组
     * @param keyArgs 参数的位置数组
     * @return
     */
    private Object[] getKeyArgs(Object[] args, int[] keyArgs) {
        Object[] redisKeyArgs;
        int len = keyArgs.length;
        if (len == 0) {
            return null;
        } else {
            len = min(len, args.length);
            redisKeyArgs = new Object[len];
            int i = 0;
            for (int n : keyArgs) {
                redisKeyArgs[i++] = args[n];
                if (i >= len) {
                    break;
                }
            }
            return redisKeyArgs;
        }
    }

    private int min(int i, int j) {
        return i < j ? i : j;
    }
}
