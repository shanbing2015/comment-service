package top.shanbing.controller;

import io.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.shanbing.common.exception.BizException;
import top.shanbing.domain.enums.ErrorCodeEnum;
import top.shanbing.service.BashService;
import top.shanbing.util.HttpUtil;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class BashController {
    @Autowired
    BashService bashService;

    @RequestMapping("/home")
    public String index(){
        System.out.println(LocalDateTime.now());
        return "index#"+LocalDateTime.now();
    }

    @RequestMapping("/home2")
    public String index2(ServerHttpResponse response){
        ResponseCookie cookie = ResponseCookie.from("Access-Control-Allow-Origin", "*").build();
        response.addCookie(cookie);
        System.out.println(LocalDateTime.now());
        return "index2#"+LocalDateTime.now();
    }

    @RequestMapping("/test")
    public void test(ServerHttpRequest request,ServerHttpResponse response){
        String ip = HttpUtil.getIp(request);
        System.out.println("访问IP:"+ip);
        throw new IllegalArgumentException("测试异常");
    }

    @RequestMapping("/test2")
    public void test2(){
        throw new BizException(ErrorCodeEnum.PARAM_VALID_ERROR);
    }

    @RequestMapping("/test3")
    public void test3() throws Exception{
        throw new IOException("io测试异常");
    }

    @RequestMapping("/test4")
    public void test4(){
        bashService.test();
    }

    @RequestMapping("/test5")
    public void test5(){
        bashService.test2();
    }

    @RequestMapping("/test6")
    public void test6() throws Exception{
        bashService.test3();
    }
}
