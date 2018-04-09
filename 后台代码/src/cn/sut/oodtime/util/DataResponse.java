package cn.sut.oodtime.util;

public class DataResponse {
	/** 错误代码 */
	protected String code;

	/** 返回信息 */
	protected String message;

	/** 业务数据 */
	protected Object result;
	
	public DataResponse(String message) {
		this("500", message, null);
	}

	public DataResponse(String message, Object result) {
		this("200", message, result);
	}

	public DataResponse(String code,
			String message, Object result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "DataResponse [code=" + code + ", message=" + message + ", result=" + result + "]";
	}
	
	


}
