package top.shanbing.service;

/**
 * @author shanbing
 * @date 2018/8/17.
 */
public interface WechatService {

    /**是否登录*/
    boolean isLogin();

    /**将二维码路径缓存redis*/
    void cacheQRCodePath(String path);

    /**获取二维码文件路径*/
    String getQRCode();

    /**刷新二维码*/
    void refreshQR();

    /**获取redis缓存二维码*/
    String getCacheQRCodePath();

    /**通知python服务生成微信登录二维码*/
    boolean notifyPythonGenerateQRCode();
}
