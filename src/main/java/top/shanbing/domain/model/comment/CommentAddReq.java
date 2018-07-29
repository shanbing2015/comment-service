package top.shanbing.domain.model.comment;

public class CommentAddReq {

    public String siteUrl;
    public String postUrl;
    public String commentName;
    public String commentContacts;
    public String commentContent;
    public Integer beReplyId;//被回复id
    public Integer parentId;//父节点回复

    @Override
    public String toString() {
        return "CommentAddReq{" +
                "siteUrl='" + siteUrl + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", commentName='" + commentName + '\'' +
                ", commentContacts='" + commentContacts + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", beReplyId='" + beReplyId + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
