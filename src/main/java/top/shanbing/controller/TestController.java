package top.shanbing.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.shanbing.common.Interceptor.FlowRate;
import top.shanbing.common.flowRate.FlowRateType;

import java.time.LocalDateTime;

@RestController
public class TestController {

    @RequestMapping("/home")
    @FlowRate
    public String index(){
        return "API限流#"+LocalDateTime.now();
    }

    @RequestMapping("/home2")
    @FlowRate(type = FlowRateType.IP_FLOWRATE)
    public String index2(ServerHttpRequest request){
        return "IP限流#"+LocalDateTime.now();
    }

    @RequestMapping("/home3")
    @FlowRate(type = FlowRateType.APIIP_FLOWRATE)
    public String index3(){
        return "API_IP限流#"+LocalDateTime.now();
    }

    @RequestMapping("/home4")
    @FlowRate( count = 2 ,timeSlot = 1)
    public String index4(){
        return "API限流#"+LocalDateTime.now();
    }

}
