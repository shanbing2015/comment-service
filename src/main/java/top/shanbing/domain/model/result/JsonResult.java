package top.shanbing.domain.model.result;

import top.shanbing.domain.enums.ErrorCodeEnum;

public class JsonResult<T> {
	private int errcode = ErrorCodeEnum.SUCCESS.getCode();
	private String errmsg = ErrorCodeEnum.SUCCESS.getMsg();
	private T data;
	
	public JsonResult(){
	}
	
	public JsonResult(T data){
		this.data = data;
	}
	
	public JsonResult(String msg, T data){
		this.errmsg = msg;
		this.data = data;
	}

	public JsonResult(int errcode, String msg){
		this.errcode = errcode;
		this.errmsg = msg;
	}
	
	public JsonResult(int result, String msg, T data){
		this.errcode = result;
		this.errmsg = msg;
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult{" +
				"errcode=" + errcode +
				", errmsg='" + errmsg + '\'' +
				", data=" + data +
				'}';
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
