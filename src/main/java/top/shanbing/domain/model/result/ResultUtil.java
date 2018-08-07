package top.shanbing.domain.model.result;


import top.shanbing.domain.enums.ErrorCodeEnum;

import java.util.Date;

/**
 * 通用返回结果工具类
 */
public final class ResultUtil {

	public static JsonResult success() {
		return success(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMsg(),null);
	}

	public static JsonResult success(Object data) {
		return success(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMsg(),data);
	}

	public static JsonResult<?> success(int errcode, String errmsg) {
		return success(errcode,errmsg,null);
	}

	public static JsonResult<?> success(int errcode, String errmsg, Object data) {
		JsonResult<Object> result = new JsonResult<Object>();
		result.setErrcode(errcode);
		result.setErrmsg(errmsg);
		result.setData(data);
		return result;
	}

	public static JsonResult<?> error() {
		return success(ErrorCodeEnum.ERROR.getCode(),ErrorCodeEnum.ERROR.getMsg());
	}

	public static JsonResult<?> error(Object data) {
		return success(ErrorCodeEnum.ERROR.getCode(),ErrorCodeEnum.ERROR.getMsg(),data);
	}
	public static JsonResult<?> error(ErrorCodeEnum errorCodeEnum){
		return error(errorCodeEnum,null);
	}
	public static JsonResult<?> error(ErrorCodeEnum errorCodeEnum,String errorInfo){
		return success(errorCodeEnum.getCode(),errorCodeEnum.getMsg()+(errorInfo!=null?":"+errorInfo:""));
	}

	public static JsonResult error(Integer errcode, String errmsg) {
		return success(errcode,errmsg);
	}

	public static JsonResult errorWithData(Integer errcode, String errmsg,String data) {
		return success(errcode,errmsg,data);
	}



}
