package top.shanbing.common.exception;


import top.shanbing.domain.enums.ErrorCodeEnum;

/**
 * 自定义业务异常类
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -6773372105984590040L;

	/**
	 * 自定义的错误码
	 */
	private Integer code;

	private ErrorCodeEnum errorCodeEnum;

	public BizException(ErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getMsg());
		this.code = errorCodeEnum.getCode();
		this.errorCodeEnum = errorCodeEnum;
	}

	public BizException(int code, String msg){
		super(msg);
		this.code = code;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public ErrorCodeEnum getErrorCodeEnum() {
		return errorCodeEnum;
	}
}
