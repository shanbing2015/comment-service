package top.shanbing.sdk.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.shanbing.common.SpringApplicationContext;

import javax.annotation.PostConstruct;

/**
 * Created by shanbing.top on 2018/8/5.
 */
@Component
public class MailUtil {
    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private static MailService mailService;
    private static String from;
    private static String toMail;

    @Autowired
    private MailService service;
    @Value("${spring.mail.username}")
    private String fromTemp;
    @Value("${toMail}")
    private String toMailTemp;

    private MailUtil(){}

    @PostConstruct
    private void init(){
        mailService  = service;
        from = fromTemp;
        toMail = toMailTemp;
    }

    /**评论邮件通知*/
    public static void sendCommentNotify(String text){
       try {
           if(SpringApplicationContext.isProd()){
               mailService.sendMail(from,toMail,"【shanbing.top】新的评论通知",text);
           }else{
               logger.info("非正式环境，不进行邮件通知");
           }
       }catch (Exception e){
           logger.error("评论邮件通知错误",e);
       }
    }
}
