package top.shanbing;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.shanbing.service.WechatService;

/**
 * @author shanbing
 * @date 2018/8/29.
 */
public class RabbitMqTest extends BaseTest{

    @Autowired
    private WechatService wechatService;

    @Test
    public void sendCommentNotifyTest(){
        wechatService.sendCommentNotify("测试内容");
    }

    @Test
    public void notifyPythonGenerateQRCodeTest(){
        wechatService.notifyPythonGenerateQRCode();
    }
}
