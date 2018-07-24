package top.shanbing.domain.entity;

import java.util.Date;

public class CommentPosts {
    private Integer id;
    private Integer siteId;
    private String postUrl;
    private Date createtime;

    public CommentPosts() {
    }

    public CommentPosts(Integer siteId, String postUrl) {
        this.siteId = siteId;
        this.postUrl = postUrl;
    }

    @Override
    public String toString() {
        return "CommentPosts{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", postUrl='" + postUrl + '\'' +
                ", createtime=" + createtime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
