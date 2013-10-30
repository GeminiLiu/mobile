package cn.com.ultrapower.eoms.util;
/**
 * 本类定义本系统所工单的超时状态
 * @author zhaoqi
 *
 */
public class TaskStateType {
	
	public static int UNFINISHUNOVER = new Long(10).intValue();//未完成，未超时
	public static int UNFINISHOVER = new Long(20).intValue();//未完成，已超时
	public static int FINISHUNOVER = new Long(30).intValue();//已完成,未超时
	public static int FINISHOVER = new Long(40).intValue();//已完成,已超时
}
