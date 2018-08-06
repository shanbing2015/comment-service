package top.shanbing.domain.enums;

/**
 * 错误码枚举类 用于错误码和错误信息的统一管理，方便维护
 *
 * @author KangKai
 * @date 2017年3月28日
 */
public enum ErrorCodeEnum {
    SUCCESS(0, "success"),
    // >0 业务异常

    BIZ_ERROR(2, "业务异常"),
    PARAM_VALID_ERROR(11, "参数校验错误"),
    SITE_BLACK_ERROR(21, "站点是黑名单"),
    IP_BLACK_ERROR(22, "ip是黑名单"),
    IP_FLOW_RATE(23, "ip限流"),
    API_FLOW_RATE(23, "接口限流"),

    // <0 系统异常
    ERROR(-1, "系统错误"),
    BUSY_ERROR(-2, "系统繁忙"),
    UNKNOWN_ERROR(-11, "未知错误"),


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

    @Override
    public String toString() {
        return "ErrorCodeEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
