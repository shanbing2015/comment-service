package top.shanbing.domain.model.comment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentListRes {
    private Integer commentId;
    private String commentName;
    private String commentContacts;
    private String commentContent;
    private String commentDate;

    public CommentListRes() {
    }

    public CommentListRes(Integer commentId, String commentName, String commentContacts, String commentContent, Date commentDate) {
        this.commentId = commentId;
        this.commentName = commentName;
        this.commentContacts = commentContacts;
        this.commentContent = commentContent;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.commentDate = sdf.format(commentDate);
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentContacts() {
        return commentContacts;
    }

    public void setCommentContacts(String commentContacts) {
        this.commentContacts = commentContacts;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
