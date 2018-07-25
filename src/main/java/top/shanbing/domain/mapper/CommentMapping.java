package top.shanbing.domain.mapper;

import top.shanbing.domain.entity.CommentPosts;
import top.shanbing.domain.entity.CommentSites;
import top.shanbing.domain.entity.Comments;
import top.shanbing.domain.model.comment.CommentListRes;

import java.util.List;

public interface CommentMapping {

    CommentSites selectCommentSitesById(int id);
    CommentSites selectCommentSitesBySiteUrl(String siteUrl);

    CommentPosts selectCommentPostsByid(int id);
    CommentPosts selectCommentPostsByPostUrl(String postUrl);

    Integer insertSite(CommentSites site);
    Integer insertPost(CommentPosts posts);
    Integer insertComment(Comments comment);

    List<CommentListRes> selectCommentList(String siteUrl,String postUrl,Integer pageSize,Integer pageNum);
}
