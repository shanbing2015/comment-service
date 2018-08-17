package top.shanbing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.shanbing.common.exception.BizException;
import top.shanbing.common.redis.IRedisManager;
import top.shanbing.common.redis.RedisKeys;
import top.shanbing.domain.enums.ErrorCodeEnum;
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

    @Override
    public void cacheQRCodePath(String path) {
        redisManager.setex(RedisKeys.WECHAT_QRCODE_PATH,60,path,true);
    }

    @Override
    public String getQRCode() {
        String filePath = this.getCacheQRCodePath();
        if(filePath == null || filePath.trim().equals("") || !new File(filePath).exists()){
            log.info("未获取到有效二维码路径,path:{}",filePath);
            boolean notify = notifyPythonGenerateQRCode();
            log.info("通知python服务生成登录二维码,结果:{}",notify);
            if(!notify){
                throw new BizException(ErrorCodeEnum.PYTHON_ERROR);
            }
            filePath = this.getCacheQRCodePath();
            if(filePath == null || filePath.trim().equals("") || !new File(filePath).exists()){
                log.error("获取的二维码图片地址错误，path:{}",filePath);
                throw new BizException(ErrorCodeEnum.FILE_ERROR);
            }
        }
        return filePath;
    }

    @Override
    public String getCacheQRCodePath() {
        return redisManager.get(RedisKeys.WECHAT_QRCODE_PATH,String.class);
    }

    @Override
    public boolean notifyPythonGenerateQRCode() {
        return true;
    }
}
