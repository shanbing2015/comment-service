package top.shanbing.controller;

import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.domain.model.comment.CommentListReq;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.domain.model.result.PageResult;
import top.shanbing.domain.model.result.ResultUtil;
import top.shanbing.service.CommentService;
import top.shanbing.util.CommentUtil;
import top.shanbing.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@RequestMapping("comment/v1")
@RestController
public class CommentController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CommentService commentService;

    @RequestMapping(value = "/save", produces = "application/json" ,consumes="application/json")
    public Mono<JsonResult> save(@RequestBody CommentAddReq addReq,ServerHttpRequest request) throws UnsupportedEncodingException {

        System.out.println("解码前:"+addReq.postUrl);
        addReq.postUrl = java.net.URLDecoder.decode(addReq.postUrl,"UTF-8");
        System.out.println("解码后:"+addReq.postUrl);
        String ip = HttpUtil.getIp(request);
        log.info(ip+addReq.toString());
        String deviceType = "";
        //CommentUtil.isIpBlack(ip);
        commentService.save(addReq,ip,deviceType);
        return Mono.just(ResultUtil.success());
    }

    @PostMapping(value ="/list", produces = "application/json")
    public Mono<JsonResult> list(@RequestBody CommentListReq listReq,ServerHttpRequest request) throws UnsupportedEncodingException{
        listReq.postUrl = java.net.URLDecoder.decode(listReq.postUrl,"UTF-8");
        String ip = HttpUtil.getIp(request);
        log.info("访问IP:"+ip+listReq.toString());
        String deviceType = "";
        //CommentUtil.isIpBlack(ip);

        PageResult pageResult = commentService.getList(listReq);
        return Mono.just(ResultUtil.success(pageResult));
    }
}
