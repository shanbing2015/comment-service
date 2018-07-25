package top.shanbing.service;

import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.domain.model.comment.CommentListReq;
import top.shanbing.domain.model.comment.CommentListRes;
import top.shanbing.domain.model.result.PageResult;

public interface CommentService {

    void save(CommentAddReq addReq,String ip,String deviceType);

    PageResult<CommentListRes> getList(CommentListReq req);
}
