/**
 * 
 */
package cn.com.ultrapower.eoms.util;

/**
 * 定义具有审批流程的工作的各环节
 * @author lijupeng
 *
 */
public class ValidateStatus {
	public static int NEW = 10;//新建
	public static int VALIDATING = 20;//审批中
	public static int PASS = 30;//审批通过
	public static int DENY = 40;//审批驳回
	public static int OUT_OF_DATE = 90; //失效
	public static int CHANGEING = 70;   //变更审批中
	public static int CHANGE_DENY = 80; //变更驳回
	public static int PERFORM_PASS = 50;//执行完成
	public static int PERFORM_DENY = 60;//执行未完成
	
	public static String getValidateStatusName ( int _StatusCode ) {
		switch ( _StatusCode ) {
		case 10: return "新建";
		case 20: return "审批中";
		case 30: return "审批通过";
		case 40: return "审批驳回";
		case 70: return "变更审批中";
		case 80: return "变更驳回";
		case 90: return "失效";
		case 50: return "执行完成";
		case 60: return "执行未完成";
		}
		return "未知状态";
	}
}
