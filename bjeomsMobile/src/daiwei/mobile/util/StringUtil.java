package daiwei.mobile.util;

import java.text.SimpleDateFormat;

/**
 * String相关的工具类
 * @author changxiaofei
 * @time 2013-3-25 下午1:37:45
 */
public class StringUtil {
	private static final String TAG = "StringUtil";
	
	/**
	 * 参数不为null且不为""，返回true；否则返回false
	 * @param strParam
	 * @return
	 */
	public static boolean isNotEmpty(String strParam) {
		if (strParam != null && !"".equals(strParam.trim()) && !"null".equals(strParam.trim().toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 参数为null、 "null"或" "，返回true；否则返回false
	 * @param strParam
	 * @return
	 */
	public static boolean isEmpty(String strParam) {
		if (strParam == null || "".equals(strParam.trim()) || "null".equals(strParam.trim().toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 格式化时间
	 * @param searcherInput
	 * @return
	 */
	public static String StringToLongToString(String searcherInput) {
		if(isEmpty(searcherInput)){
			return "";
		}
		long lgTime = 0;
		Boolean judge = false;
		try {
			Integer.parseInt(searcherInput);
			searcherInput += "000";
			judge = true;
		} catch (NumberFormatException e) {
			judge = false;
		}
		if (judge == true) {
			lgTime = Long.parseLong(searcherInput);
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			searcherInput = sdf.format(lgTime);
		}
		return searcherInput;

	}
	

}
