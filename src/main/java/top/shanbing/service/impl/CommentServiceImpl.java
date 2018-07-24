package top.shanbing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.shanbing.domain.entity.CommentPosts;
import top.shanbing.domain.entity.CommentSites;
import top.shanbing.domain.entity.Comments;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void save(CommentAddReq addReq) {

        CommentSites site = new CommentSites(addReq.siteUrl,1);
        this.saveSite(site);

        CommentPosts post = new CommentPosts(site.getId(),addReq.postUrl);
        this.savePost(post);

        String ip = "";
        String deviceType = "";
        Comments comment = new Comments(post.getId(),addReq.commentName,addReq.commentContacts,addReq.commentContent,1,ip,deviceType);
        this.saveComment(comment);
    }

    @Override
    public void saveSite(CommentSites site) {
        site.setId(1);
        log.info("保存站点:"+site.toString());
    }

    @Override
    public void savePost(CommentPosts posts) {
        posts.setId(2);
        log.info("保存帖子:"+posts.toString());
    }

    @Override
    public void saveComment(Comments comment) {
        comment.setId(3);
        log.info("保存评论:"+comment.toString());
    }
}
