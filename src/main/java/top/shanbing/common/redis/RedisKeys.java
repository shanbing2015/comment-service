package top.shanbing.common.redis;

/**
 * @author shanbing
 * @date 2018/8/6.
 * 统一管理redis Key
 */
public interface RedisKeys {

    /**ip限流*/
    String IP_FLOW_RATE = "ip:flow:rete:%s";

    String IP_BLOCK = "ip:block:%s";

    String USER_IDENTIY = "user:identiy:%s";
}
