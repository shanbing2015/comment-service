package top.shanbing.service;

import top.shanbing.domain.model.comment.CommentAddReq;

public interface CommentService {

    void save(CommentAddReq addReq,String ip,String deviceType);
}
