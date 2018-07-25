package top.shanbing.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.shanbing.domain.entity.CommentPosts;
import top.shanbing.domain.entity.CommentSites;
import top.shanbing.domain.entity.Comments;
import top.shanbing.domain.model.comment.CommentListRes;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {

    CommentSites selectCommentSitesBySiteUrl(@Param("siteUrl") String siteUrl);
    CommentPosts selectCommentPostsByPostUrl(@Param("postUrl") String postUrl);

    Integer insertSite(CommentSites site);
    Integer insertPost(CommentPosts posts);
    Integer insertComment(Comments comment);

    List<Comments> selectCommentList(@Param("siteUrl") String siteUrl,@Param("postUrl") String postUrl,@Param("limit") Integer limit ,@Param("offset") Integer offset );
}
