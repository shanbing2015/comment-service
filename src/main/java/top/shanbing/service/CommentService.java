package top.shanbing.service;

import top.shanbing.domain.entity.CommentPosts;
import top.shanbing.domain.entity.CommentSites;
import top.shanbing.domain.entity.Comments;
import top.shanbing.domain.model.comment.CommentAddReq;

public interface CommentService {

    void save(CommentAddReq addReq);

    void saveSite(CommentSites site);
    void savePost(CommentPosts posts);
    void saveComment(Comments comment);
}
