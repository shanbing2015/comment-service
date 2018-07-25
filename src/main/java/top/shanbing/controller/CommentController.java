package top.shanbing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.domain.model.comment.CommentListReq;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.domain.model.result.PageResult;
import top.shanbing.domain.model.result.ResultUtil;
import top.shanbing.service.CommentService;
import top.shanbing.util.CommentUtil;

import java.util.ArrayList;

@RequestMapping("comment/v1")
@RestController
public class CommentController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CommentService commentService;

    @PostMapping(value = "/save", produces = "application/json")
    public Mono<JsonResult> save(@RequestBody CommentAddReq addReq){
        String ip = "";
        String deviceType = "";
        CommentUtil.isIpBlack(ip);
        commentService.save(addReq,ip,deviceType);
        return Mono.just(ResultUtil.success());
    }

    @PostMapping(value ="/list", produces = "application/json")
    public Mono<JsonResult> list(@RequestBody CommentListReq listReq){
        PageResult pageResult = new PageResult();
        pageResult.setList(new ArrayList());
        return Mono.just(ResultUtil.success(pageResult));
    }
}
