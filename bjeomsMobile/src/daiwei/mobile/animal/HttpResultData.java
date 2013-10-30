package daiwei.mobile.animal;

/**
 * http请求函数的返回类型
 * @author changxiaofei
 * @time 2013-4-18 下午8:08:07
 */
public class HttpResultData {
	private String strResult = null;
	private int arg1 = 0;
	private String str1 = null;
	private Object obj = null;
	/**
	 * 返回码StatusCode==200
	 */
	private boolean httpStatusOK = false;
	private boolean parseJsonOK = false;
	private int errorCode = 0;
	private String errorMsg = null;

	public HttpResultData() {
	}

	public HttpResultData(String strResult, boolean httpStatusOK) {
		super();
		this.strResult = strResult;
		this.httpStatusOK = httpStatusOK;
	}

	public String getStrResult() {
		return strResult;
	}

	public void setStrResult(String strResult) {
		this.strResult = strResult;
	}

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isHttpStatusOK() {
		return httpStatusOK;
	}

	public void setHttpStatusOK(boolean httpStatusOK) {
		this.httpStatusOK = httpStatusOK;
	}

	public boolean isParseJsonOK() {
		return parseJsonOK;
	}

	public void setParseJsonOK(boolean parseJsonOK) {
		this.parseJsonOK = parseJsonOK;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
}
