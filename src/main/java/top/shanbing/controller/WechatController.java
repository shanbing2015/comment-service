package top.shanbing.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author shanbing
 * @date 2018/8/16.
 */

@RequestMapping("wechat")
@Controller
public class WechatController {

    @GetMapping()
    public void home(ServerHttpResponse response){
        //todo 判定是否登录
        //redis获取心跳
            //无心跳，发送获取登录二维码mq消息
            //while(true) 获取二维码图片文件
                //发送文件
        response.getHeaders().add("Content-Type","image/jped");

        byte data[] = null;
        try {
            File filePic = new File("1.JPG");
            System.out.println(filePic.getPath());
            System.out.println(filePic.getAbsolutePath());
            if(filePic.exists()){
                FileInputStream is = new FileInputStream(filePic);
                int i = is.available(); // 得到文件大小
                data = new byte[i];
                is.read(data); // 读数据
                is.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Mono mono = Mono.just(data);

        response.writeWith(mono);
    }
}
