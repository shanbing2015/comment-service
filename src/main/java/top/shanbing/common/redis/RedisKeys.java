package top.shanbing.common.redis;

/**
 * @author shanbing
 * @date 2018/8/6.
 * 统一管理redis Key
 */
public interface RedisKeys {

    /**ip限流*/
    String IP_FLOW_RATE = "ip:flow:rete:%s";

    /**ip黑名单*/
    String IP_BLOCK = "ip:block:%s";

    /**用户cache*/
    String USER_IDENTIY = "user:identiy:%s";

    /**微信登录二维码图片路径*/
    String WECHAT_QRCODE_PATH = "wechat_qrcode_path";

    String WECHAT_LOGIN = "wechat_login";
}
