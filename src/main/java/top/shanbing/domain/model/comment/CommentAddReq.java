package top.shanbing.domain.model.comment;

public class CommentAddReq {

    public String siteUrl;
    public String postUrl;
    public String commentName;
    public String commentContacts;
    public String commentContent;

    @Override
    public String toString() {
        return "CommentAddReq{" +
                "siteUrl='" + siteUrl + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", commentName='" + commentName + '\'' +
                ", commentContacts='" + commentContacts + '\'' +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }
}
