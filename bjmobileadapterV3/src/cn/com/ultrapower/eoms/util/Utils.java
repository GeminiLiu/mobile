package cn.com.ultrapower.eoms.util;

/**
 * 
 * <p>
 * Description:
 * </p>
 * <p>
 * Created on: 2006-11-14
 * </p>
 * 
 * @author <a href="mailto:yechanglun@ultrapower.com.cn">YeChangLun</a>
 * @version 1.0
 */
public class Utils {

	private static final int ID_LENGTH = 15;

	public static String getLongStringKey(String shortid) {
		StringBuffer sb = new StringBuffer("000000000000000");
		if (shortid != null) {
			int size = shortid.length();
			sb.replace(ID_LENGTH - size, ID_LENGTH, shortid);
		} else {
			return sb.toString();
		}
		return sb.toString();
	}

	public static String getLongStringKey(Long longid) {
		StringBuffer sb = new StringBuffer("000000000000000");
		if (longid != null) {
			String slongid = longid.toString();
			int size = slongid.length();
			sb.replace(ID_LENGTH - size, ID_LENGTH, slongid);
		} else {
			return sb.toString();
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getLongStringKey("25"));
		System.out.println(getLongStringKey("5"));
	}
}
