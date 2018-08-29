package top.shanbing.redis.cache;

/**
 * @author shanbing
 * @date 2018/8/28.
 */
public enum RedisCacheAction {
    /**
     * 先从redis获取，未命中再去业务层获取
     */
    REDIS_FIRST,

    /**
     * 穿透redis到业务层获取，适用于刷新redis的场景
     */
    STAB_REDIS
}
