package top.shanbing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.shanbing.domain.model.MailReq;
import top.shanbing.flowRate.FlowRate;
import top.shanbing.flowRate.FlowRateAction;
import top.shanbing.mail.MailUtil;

import java.time.LocalDateTime;

@RestController
public class TestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/home")
    @FlowRate
    public String index(){
        return "API限流#"+LocalDateTime.now();
    }

    @RequestMapping("/home2")
    @FlowRate(type = FlowRateAction.IP_FLOWRATE)
    public String index2(ServerHttpRequest request){
        return "IP限流#"+LocalDateTime.now();
    }

    @RequestMapping("/home3")
    @FlowRate(type = FlowRateAction.APIIP_FLOWRATE)
    public String index3(){
        return "API_IP限流#"+LocalDateTime.now();
    }

    @RequestMapping("/home4")
    @FlowRate( count = 2 ,timeSlot = 1)
    public String index4(){
        return "API限流#"+LocalDateTime.now();
    }

    @FlowRate(type = FlowRateAction.APIIP_FLOWRATE,count = 1,timeSlot = 3)    //3秒1次限流
    @RequestMapping("/bypass/notify")
    public String notifyTest(String toMail,String msg){
        log.info("toMail:{},msg:{}",toMail,msg);
        MailUtil.sendCommentNotify(toMail,"购票订单成功通知","内容:"+msg+"\n\n(请勿非法使用)");
        return "ok\t"+LocalDateTime.now();
    }

}
