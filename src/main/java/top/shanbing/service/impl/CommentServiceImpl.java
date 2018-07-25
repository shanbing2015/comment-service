package top.shanbing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.shanbing.domain.entity.CommentPosts;
import top.shanbing.domain.entity.CommentSites;
import top.shanbing.domain.entity.Comments;
import top.shanbing.domain.mapper.CommentMapping;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.service.CommentService;
import top.shanbing.util.CommentUtil;

@Service
public class CommentServiceImpl implements CommentService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private CommentMapping commentMapping;

    //TODO 事务
    @Override
    public void save(CommentAddReq addReq,String ip,String deviceType) {

        CommentSites site = commentMapping.selectCommentSitesBySiteUrl(addReq.siteUrl);
        if(site == null){
            site = new CommentSites(addReq.siteUrl,1);
            this.saveSite(site);
        }
        CommentUtil.isSiteBlack(site);

        CommentPosts post = commentMapping.selectCommentPostsByPostUrl(addReq.postUrl);
        if(post == null){
            post = new CommentPosts(site.getId(),addReq.postUrl);
            this.savePost(post);
        }

        Comments comment = new Comments(post.getId(),addReq.commentName,addReq.commentContacts,addReq.commentContent,1,ip,deviceType);
        this.saveComment(comment);
    }

    public void saveSite(CommentSites site) {
        site.setId(1);
        log.info("保存站点:"+site.toString());
    }

    public void savePost(CommentPosts posts) {
        posts.setId(2);
        log.info("保存帖子:"+posts.toString());
    }

    public void saveComment(Comments comment) {
        comment.setId(3);
        log.info("保存评论:"+comment.toString());
    }
}
