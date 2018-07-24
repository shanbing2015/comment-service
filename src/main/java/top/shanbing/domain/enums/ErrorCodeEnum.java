package top.shanbing.domain.enums;

/**
 * 错误码枚举类 用于错误码和错误信息的统一管理，方便维护
 *
 * @author KangKai
 * @date 2017年3月28日
 */
public enum ErrorCodeEnum {
    SUCCESS(0, "success"),
    ERROR(-1, "系统错误"),
    ;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
