package top.shanbing.domain.model;

/**
 * @author shanbing
 * @date 2019/1/11.
 */
public class MailReq {
    public String toMail;
    public String text;

    @Override
    public String toString() {
        return "MailReq{" +
                "toMail='" + toMail + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
