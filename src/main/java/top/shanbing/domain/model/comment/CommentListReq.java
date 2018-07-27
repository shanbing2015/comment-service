package top.shanbing.domain.model.comment;

public class CommentListReq {
    public String siteUrl;
    public String postUrl;
    public Integer pageSize;
    public Integer pageNum;

    @Override
    public String toString() {
        return "CommentListReq{" +
                "siteUrl='" + siteUrl + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                '}';
    }
}
