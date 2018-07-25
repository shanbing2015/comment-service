package top.shanbing.domain.entity;

import java.util.Date;

public class CommentSites {
    private Integer id;
    private String siteUrl;
    private Integer type = 1;       //站点状态:1、正常；2、加黑单
    private Date createtime = new Date();

    public CommentSites() {
    }

    public CommentSites(String siteUrl, Integer type) {
        this.siteUrl = siteUrl;
        this.type = type;
    }

    @Override
    public String toString() {
        return "CommentSites{" +
                "id=" + id +
                ", siteUrl='" + siteUrl + '\'' +
                ", type=" + type +
                ", createtime=" + createtime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
