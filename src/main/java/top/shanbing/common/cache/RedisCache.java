package top.shanbing.common.cache;

import java.lang.annotation.*;

/**
 * redis 缓存
 * @author shanbing
 * @date 2018/8/28.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {

    /** 执行何种操作，默认是先访问 redis **/
    RedisCacheAction action() default RedisCacheAction.REDIS_FIRST;

    String value();
    /**
     *  指示方法的哪些参数用来构造key，及其顺序(编号由0开始)     *
     *   示例
     *       keyArgs = {1,0,2}，表示用方法的第2，第1，第3个参数，按顺序来构造key
     *   默认值的意思是方法的前 n 个参数来构造key，n 最大为10     *
     *   ！！如果构造 key 的参数不多于 10 个且顺序也和方法参数一致，则可以用默认值
     */
    int[] keyArgs() default { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    /** redis 不能保存 null值，就用这个值表示缓存的是 null值 */
    String nullValue() default NULL_VALUE;

    /** 是否要缓存 null 值，默认为true **/
    boolean cacheNull() default true;


    /**
     * 若某个key的value为这个值，表示该key缓存的是null值
     */
    String NULL_VALUE = "_NULL_";

    /** 过期时间，默认250分钟 **/
    int ttl() default DEFAULT_TTL;

    /**
     * 非null值的默认时间，暂定4小时+10分钟，后面根据业务需要调整
     */
    int DEFAULT_TTL = 4 * 60 * 60 + 10 * 60;

    /** 是否以同步的方式操作redis，默认是false **/
    boolean sync() default false;
}
