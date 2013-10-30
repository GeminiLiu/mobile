package daiwei.mobile.animal;

/**
 * 函数的返回类型
 * @author changxiaofei
 * @time 2013-3-31 下午12:07:02
 */
public class CommonResult {
	/** 操作类型 */
	private int arg1 = 0;
	/** 操作成功或失败 */
	private boolean ok = false;
	/** 操作结果：字符串 */
	private String str1 = null;
	/** 操作结果：Object */
	private Object obj1 = null;
	/** operateOK==false时，错误代码 */
	private int errorCode = 0;
	/** operateOK==false时，错误提示信息 */
	private String erorrMsg = null;

	public CommonResult() {
	}

	/**
	 * 最简单的操作情景。 结果包含：是否成功；返回结果字符串
	 * @param ok
	 * @param str1
	 */
	public CommonResult(boolean ok, String str1) {
		super();
		this.str1 = str1;
		this.ok = ok;
	}

	/**
	 * 最简单的操作情景。 结果包含：是否成功；返回结果Object
	 * @param ok
	 * @param obj1
	 */
	public CommonResult(boolean ok, Object obj1) {
		super();
		this.obj1 = obj1;
		this.ok = ok;
	}

	/**
	 * @return 操作类型
	 */
	public int getArg1() {
		return arg1;
	}

	/**
	 * @param 操作类型
	 */
	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	/**
	 * @return 操作成功或失败
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param 操作成功或失败
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return 操作结果：字符串
	 */
	public String getStr1() {
		return str1;
	}

	/**
	 * @param 操作结果：字符串
	 */
	public void setStr1(String str1) {
		this.str1 = str1;
	}

	/**
	 * @return 操作结果：Object
	 */
	public Object getObj1() {
		return obj1;
	}

	/**
	 * @param 操作结果：Object
	 */
	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}

	/**
	 * @return operateOK==false时，错误代码
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param operateOK==false时，错误代码
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return operateOK==false时，错误提示信息
	 */
	public String getErorrMsg() {
		return erorrMsg;
	}

	/**
	 * @param operateOK==false时，错误提示信息
	 */
	public void setErorrMsg(String erorrMsg) {
		this.erorrMsg = erorrMsg;
	}
	
	
}
