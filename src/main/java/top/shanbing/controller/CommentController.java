package top.shanbing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.shanbing.flowRate.FlowRate;
import top.shanbing.flowRate.FlowRateAction;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.domain.model.comment.CommentListReq;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.domain.model.result.PageResult;
import top.shanbing.domain.model.result.ResultUtil;
import top.shanbing.service.CommentService;
import top.shanbing.util.HttpUtil;

import java.io.UnsupportedEncodingException;

@RequestMapping("comment/v1")
@RestController
public class CommentController extends BashController{
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CommentService commentService;

    @FlowRate(type = FlowRateAction.APIIP_FLOWRATE,count = 1,timeSlot = 3)    //3秒1次限流
    @PostMapping(value = "/save", produces = "application/json" ,consumes="application/json")
    public Mono<JsonResult> save(@RequestBody CommentAddReq addReq,ServerHttpRequest request) throws UnsupportedEncodingException {
        addReq.postUrl = java.net.URLDecoder.decode(addReq.postUrl,"UTF-8");
        addReq.commentName = HttpUtil.htmlEncode(addReq.commentName);
        addReq.commentContacts = HttpUtil.htmlEncode(addReq.commentContacts);
        addReq.commentContent = HttpUtil.htmlEncode(addReq.commentContent);

        String ip = HttpUtil.getIp(request);
        String deviceType = HttpUtil.deviceType(request);
        log.info("请求IP:"+ip+",设备类型:"+deviceType+"\t[siteUrl:"+addReq.siteUrl+",postUrl:"+addReq.postUrl+",commentContent:"+addReq.commentContent+"]");
        commentService.save(addReq,ip,deviceType);
        return Mono.just(ResultUtil.success());
    }

    @FlowRate(type = FlowRateAction.APIIP_FLOWRATE,count = 3,timeSlot = 1)    //1秒3次限流
    @PostMapping(value ="/list", produces = "application/json",consumes="application/json")
    public Mono<JsonResult> list(@RequestBody CommentListReq listReq, ServerHttpRequest request) throws UnsupportedEncodingException{
        listReq.postUrl = java.net.URLDecoder.decode(listReq.postUrl,"UTF-8");
        String ip = HttpUtil.getIp(request);
        String deviceType = HttpUtil.deviceType(request);
        log.info("请求IP:"+ip+",设备类型:"+deviceType+"\t[siteUrl:"+listReq.siteUrl+",postUrl:"+listReq.postUrl+"]");
        PageResult pageResult = commentService.getList(listReq);
        return Mono.just(ResultUtil.success(pageResult));
    }
}
