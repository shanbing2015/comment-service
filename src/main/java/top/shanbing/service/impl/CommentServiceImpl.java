package top.shanbing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.shanbing.common.AppPage;
import top.shanbing.domain.entity.CommentPosts;
import top.shanbing.domain.entity.CommentSites;
import top.shanbing.domain.entity.Comments;
import top.shanbing.domain.mapper.CommentMapper;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.domain.model.comment.CommentListReq;
import top.shanbing.domain.model.comment.CommentListRes;
import top.shanbing.domain.model.comment.ReplyComment;
import top.shanbing.domain.model.result.PageResult;
import top.shanbing.sdk.mail.MailUtil;
import top.shanbing.service.CommentService;
import top.shanbing.util.CommentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(CommentAddReq addReq,String ip,String deviceType) {

        CommentSites site = commentMapper.selectCommentSitesBySiteUrl(addReq.siteUrl);
        if(site == null){
            site = new CommentSites(addReq.siteUrl,1);
            commentMapper.insertSite(site);
        }
        CommentUtil.isSiteBlack(site);

        CommentPosts post = commentMapper.selectCommentPostsByPostUrl(site.getId(),addReq.postUrl);
        if(post == null){
            post = new CommentPosts(site.getId(),addReq.postUrl);
            commentMapper.insertPost(post);
        }

        //todo addReq.parentId 检查父节点是否存在
        //todo beReplyId  检查是否存在

        Comments comment = new Comments(post.getId(),addReq.commentName,addReq.commentContacts,addReq.commentContent,1,ip,deviceType,addReq.parentId,addReq.beReplyId);
        commentMapper.insertComment(comment);

        String notifyText = "评论者:"+comment.getCommentName();
        notifyText += "\n评论内容:"+comment.getCommentContent();
        notifyText += "\n\nIP:"+comment.getIp()+",来源:"+comment.getDeviceType();
        MailUtil.sendCommentNotify(notifyText);
    }

    @Override
    public PageResult<CommentListRes> getList(CommentListReq req) {
        if(req.pageNum == null || req.pageNum <= 0){
            req.pageNum = AppPage.pageNum;
        }
        if(req.pageSize == null || req.pageSize <=0 || req.pageSize >= 50){
            req.pageSize = AppPage.pageSize;
        }

        Integer limit = (req.pageNum -1) * AppPage.pageSize;
        Integer offset = req.pageSize;

        List<Comments> list = commentMapper.selectCommentList(req.siteUrl,req.postUrl,limit ,offset );
        return this.commentListVO(req.pageNum,req.pageSize,list);
    }

    private PageResult<CommentListRes> commentListVO(Integer pageSize,Integer pageNum,List<Comments> list){
        PageResult<CommentListRes> pageResult = new PageResult<>(pageSize,pageNum);
        List<CommentListRes> resList = new ArrayList<>();
        Map<Integer,String> commentNameMap = new HashMap<>();
        list.forEach( comment -> {
            commentNameMap.put(comment.getId(),comment.getCommentName());
            CommentListRes commentListRes = new CommentListRes(comment.getId(),comment.getCommentName(), comment.getCommentContacts(),comment.getCommentContent(), comment.getCreatetime());
            // todo 这里后期可放在一起查询，提升效率
            int parentId = comment.getId();
            List<Comments> beReplyList = commentMapper.selectCommentByParentId(parentId); //该条评论下的评论
            List<ReplyComment> replyComments = new ArrayList<>();
            beReplyList.forEach( byReplyComment ->{
                commentNameMap.put(byReplyComment.getId(),byReplyComment.getCommentName());
                String replyCommentName = commentNameMap.get(byReplyComment.getBeReplyId());
                if(replyCommentName == null)
                    replyCommentName = "未知";
                replyComments.add(new ReplyComment(byReplyComment.getId(),byReplyComment.getCommentName(),replyCommentName, byReplyComment.getCommentContacts(),byReplyComment.getCommentContent(), byReplyComment.getCreatetime()));
            });
            commentListRes.setReplys(replyComments);
            resList.add(commentListRes);
        });
        pageResult.setList(resList);
        return pageResult;
    }
}
