package top.shanbing.mail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import top.shanbing.BaseTest;

/**
 * Created by shanbing.top on 2018/8/5.
 */
public class MailTest extends BaseTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void sendMailTest(){
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom("comment@shanbing.top");
        //收件人
        message.setTo("799114324@qq.com");
        message.setSubject("邮件标题");
        message.setText("邮件内容");
        javaMailSender.send(message);
    }
}
