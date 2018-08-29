package top.shanbing.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by shanbing.top on 2018/8/5.
 */
@Component
@EnableAsync
public class MailService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(String from,String toMail,String subject,String text) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(from);
        //收件人
        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        log.info("发送邮件成功【from:{},toMail:{},subject:{},text:{}】{}",from,toMail,subject,text,LocalDateTime.now().toString());
    }
}
