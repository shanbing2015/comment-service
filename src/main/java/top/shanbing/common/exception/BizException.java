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
	public Integer code;
	public ErrorCodeEnum errorCodeEnum;
	public String errorInfo;

	public BizException(ErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getMsg());
		this.code = errorCodeEnum.getCode();
		this.errorCodeEnum = errorCodeEnum;
	}
	public BizException(ErrorCodeEnum errorCodeEnum,String errorInfo) {
		super(errorCodeEnum.getMsg());
		this.code = errorCodeEnum.getCode();
		this.errorCodeEnum = errorCodeEnum;
		this.errorInfo = errorInfo;
	}

	public BizException(int code, String msg){
		super(msg);
		if(code == 0) code = 1;
		else this.code = code;
	}

	@Override
	public String toString() {
		return "BizException{" +
				"code=" + code +
				", errorCodeEnum=" + errorCodeEnum.toString() +
				", errorInfo='" + errorInfo + '\'' +
				'}';
	}
}
