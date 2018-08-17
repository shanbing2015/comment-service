package top.shanbing.controller;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.domain.model.result.ResultUtil;
import top.shanbing.service.WechatService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shanbing
 * @date 2018/8/16.
 */

@RequestMapping("/wechat/")
@Controller
public class WechatController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatService wechatService;

    @GetMapping("isLogin")
    @ResponseBody
    public Mono<JsonResult> isLogin(){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isLogin",0);
        resultMap.put("msg","微信未登录");
        return Mono.just(ResultUtil.success(resultMap));
    }

    @GetMapping("QRCode")
    public Mono<Void> getQRCode(ServerHttpResponse response){
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().add("Content-Type","image/jpeg");
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);
        String fileQRCodePath = wechatService.getQRCode();
        log.info("QRCode图片路径:{}",fileQRCodePath);
        File file = new File(fileQRCodePath);
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }

    @GetMapping("QRCode/download")
    public Mono<Void> downloadQRCode(ServerHttpResponse response){
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=QRCode.JPG");
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);
        String cacheQRCodePath = wechatService.getCacheQRCodePath();
        log.info("QRCode图片路径:{}",cacheQRCodePath);
        File file = new File(cacheQRCodePath);
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }

    @PostMapping(value = "QRCode/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Mono<JsonResult> requestBodyFlux(@RequestPart("file") FilePart filePart) throws IOException {
        System.out.println("上传文件名:"+filePart.filename());
        Path tempFile = Files.createTempFile("", filePart.filename());

        //NOTE 方法一
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
        DataBufferUtils.write(filePart.content(), channel, 0).doOnComplete(() ->  System.out.println("finish")).subscribe();
        //NOTE 方法二
        //filePart.transferTo(tempFile.toFile());

        String filePath = tempFile.toString();
        log.info("QRCode文件保存成功,path:{}",filePath);
        wechatService.cacheQRCodePath(filePath);
        log.info("QRCode文件地址缓存成功");
        return Mono.just(ResultUtil.success("上传成功"));
    }

    @GetMapping()
    public void wechat(ServerHttpResponse response){

    }
}
