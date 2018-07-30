package top.shanbing.domain.model.comment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReplyComment {
    private Integer commentId;
    private String commentName;
    private String replyCommentName;
    private String commentContacts;
    private String commentContent;
    private String commentDate;

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

    public String getReplyCommentName() {
        return replyCommentName;
    }

    public void setReplyCommentName(String replyCommentName) {
        this.replyCommentName = replyCommentName;
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

    public ReplyComment(Integer commentId, String commentName,String replyCommentName, String commentContacts, String commentContent, Date commentDate) {
        this.commentId = commentId;
        this.commentName = commentName;
        this.replyCommentName = replyCommentName;
        this.commentContacts = commentContacts;
        this.commentContent = commentContent;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.commentDate = sdf.format(commentDate);
    }

}
