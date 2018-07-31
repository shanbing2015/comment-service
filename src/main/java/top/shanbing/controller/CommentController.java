package top.shanbing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.domain.model.comment.CommentListReq;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.domain.model.result.PageResult;
import top.shanbing.domain.model.result.ResultUtil;
import top.shanbing.service.CommentService;
import top.shanbing.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("comment/v1")
@RestController
public class CommentController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CommentService commentService;

    @RequestMapping(value = "/save", produces = "application/json" ,consumes="application/json")
    public Mono<JsonResult> save(@RequestBody CommentAddReq addReq,ServerHttpRequest request) throws UnsupportedEncodingException {
        //response.getHeaders().add("Access-Control-Allow-Origin", "*");
        addReq.postUrl = java.net.URLDecoder.decode(addReq.postUrl,"UTF-8");
        addReq.commentName = HttpUtil.htmlEncode(addReq.commentName);
        addReq.commentContacts = HttpUtil.htmlEncode(addReq.commentContacts);
        addReq.commentContent = HttpUtil.htmlEncode(addReq.commentContent);

        String ip = HttpUtil.getIp(request);
        String deviceType = HttpUtil.deviceType(request);
        log.info("请求IP:"+ip+",设备类型:"+deviceType+"\t[siteUrl:"+addReq.siteUrl+",postUrl:"+addReq.postUrl+",commentContent:"+addReq.commentContent+"]");
        //CommentUtil.isIpBlack(ip);
        commentService.save(addReq,ip,deviceType);
        return Mono.just(ResultUtil.success());
    }

    @PostMapping(value ="/list", produces = "application/json")
    public Mono<JsonResult> list(@RequestBody CommentListReq listReq, ServerHttpRequest request) throws UnsupportedEncodingException{
        listReq.postUrl = java.net.URLDecoder.decode(listReq.postUrl,"UTF-8");
        String ip = HttpUtil.getIp(request);
        String deviceType = HttpUtil.deviceType(request);
        log.info("请求IP:"+ip+",设备类型:"+deviceType+"\t[siteUrl:"+listReq.siteUrl+",postUrl:"+listReq.postUrl+"]");
        //CommentUtil.isIpBlack(ip);

        PageResult pageResult = commentService.getList(listReq);
        return Mono.just(ResultUtil.success(pageResult));
    }
}
