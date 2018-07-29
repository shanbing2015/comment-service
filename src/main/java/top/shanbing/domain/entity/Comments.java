package top.shanbing.domain.entity;

import java.util.Date;

public class Comments {
    private Integer id;
    private Integer postId;
    private String commentName;
    private String commentContacts;
    private String commentContent;
    private Date createtime = new Date();
    private Integer type = 1;   //1正常,2删除,3置顶
    private String ip;
    private String deviceType;
    private Integer parentId;
    private Integer beReplyId;

    public Comments() {
    }

    public Comments(Integer postId, String commentName, String commentContacts, String commentContent, Integer type, String ip, String deviceType,Integer parentId,Integer beReplyId) {
        this.postId = postId;
        this.commentName = commentName;
        this.commentContacts = commentContacts;
        this.commentContent = commentContent;
        this.type = type;
        this.ip = ip;
        this.deviceType = deviceType;
        this.beReplyId = beReplyId;
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", postId=" + postId +
                ", commentName='" + commentName + '\'' +
                ", commentContacts='" + commentContacts + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", createtime=" + createtime +
                ", type=" + type +
                ", ip='" + ip + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", parentId='" + parentId + '\'' +
                ", beReplyId='" + beReplyId + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getBeReplyId() {
        return beReplyId;
    }

    public void setBeReplyId(Integer beReplyId) {
        this.beReplyId = beReplyId;
    }
}
