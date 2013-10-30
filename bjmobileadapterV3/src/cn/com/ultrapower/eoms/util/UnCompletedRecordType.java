/**
 * 
 */
package cn.com.ultrapower.eoms.util;

/**
 * @author lijupeng
 *
 */
public class UnCompletedRecordType {

	public static String ACCIDENT = "ACCIDENT";//故障记录
	public static String BASE = "BASE";//基站故障记录
	public static String AFFAIR = "AFFAIR";//事务记录
	public static String COMMON = "COMMON";//通用记录//事务记录
	//public static String COMMON2 = "COMMON2";//事务记录
	public static Long UNCOMPLETE_FLAG = new Long(0);//未完成标志
	public static Long COMPLETE_FLAG = new Long(1);//已完成标志
}
