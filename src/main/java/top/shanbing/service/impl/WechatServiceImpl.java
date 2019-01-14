package top.shanbing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.shanbing.mq.rabbitmq.RabbitMqMessageSender;
import top.shanbing.mq.rabbitmq.constant.RabbitMqConstant;
import top.shanbing.redis.IRedisManager;
import top.shanbing.redis.RedisKeys;
import top.shanbing.service.WechatService;

import java.io.File;

/**
 * @author shanbing
 * @date 2018/8/17.
 */
@Service
public class WechatServiceImpl implements WechatService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IRedisManager redisManager;

    @Value("${QRCode.imgTime}")
    private Integer imgTime;

    //@Autowired
    private RabbitMqMessageSender mqMessageSender;

    @Override
    public void cacheQRCodePath(String path) {
        if(imgTime == null)
            imgTime = 60;
        redisManager.setex(RedisKeys.WECHAT_QRCODE_PATH,imgTime,path,true);
    }

    @Override
    public String getQRCode(){
        String filePath = this.getCacheQRCodePath();
        if(filePath == null || filePath.trim().equals("") || !new File(filePath).exists()){
            log.info("未获取到有效二维码路径,path:{}",filePath);
            boolean notify = notifyPythonGenerateQRCode();
            log.info("通知python服务生成登录二维码,结果:{}",notify);
            // 返回默认图片
        }
        return filePath;
    }

    @Override
    public void refreshQR() {
        notifyPythonGenerateQRCode();
    }

    @Override
    public String getCacheQRCodePath() {
        return redisManager.get(RedisKeys.WECHAT_QRCODE_PATH,String.class);
    }

    @Override
    public boolean notifyPythonGenerateQRCode() {
        String taskQueueName = RabbitMqConstant.QUEUE_TEST;
        String objectJson = "getQR";
        mqMessageSender.syncSend(taskQueueName,objectJson);
        return true;
    }

    @Override
    public void sendCommentNotify(String notifyText) {
        String taskQueueName = RabbitMqConstant.QUEUE_COMMENT;
        String objectJson = notifyText;
        mqMessageSender.syncSend(taskQueueName,objectJson);
    }

    @Override
    public boolean isLogin() {
        String wechatName = redisManager.get(RedisKeys.WECHAT_LOGIN,String.class);
        boolean wechatLogin = wechatName!=null;
        log.info("微信心跳：{},当前用户：{}",wechatLogin,wechatName);
        if(!wechatLogin){
            String filePath = this.getCacheQRCodePath();
            if(filePath == null || filePath.trim().equals("") || !new File(filePath).exists()){
                boolean notify = notifyPythonGenerateQRCode();
                log.info("通知python服务生成登录二维码,结果:{}",notify);
            }
        }
        return wechatLogin;
    }
}
