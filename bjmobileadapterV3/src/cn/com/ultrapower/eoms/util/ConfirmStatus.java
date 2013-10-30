package cn.com.ultrapower.eoms.util;

import java.util.HashMap;

public class ConfirmStatus {
	public static Long NO_ACTION = new Long(0);//交接尚未开始
	public static Long CHECK_IN_REQUEST = new Long(1);//交班方申请
	public static Long CHECK_OUT_REQUEST = new Long(2);//接班方申请
	public static Long BOTH_AGREE = new Long(3);//双方同意
	public static Long CHECK_OUT_DENY = new Long(4);//接班方拒绝
	public static Long CHECK_IN_DENY = new Long(5);//交班方拒绝
	
	public static String CHECK_IN_SIDE = "check_in";//交班方
	public static String CHECK_OUT_SIDE = "check_out";//接班方
	
	// 交班方
	public static int CHECK_IN = 0x1;

	// 接班方
	public static int CHECK_OUT = 0x2;

	// 既是交班方又是接班方
	public static int CHECK_IN_OUT = 0x3;
	
	public static Long LOG_CLOSED = new Long(3);//日志关闭
	public static Long LOG_OPEN = new Long(1);//日志打开
	
	public static Long LOG_CREATED = new Long(1);//日志已建立
	
	public static Long NO_CONFIRM = new Long(1);//交接班双方无需确认
	public static Long CHECK_IN_CONFIRM = new Long(2);//交班方确认
	public static Long CHECK_OUT_CONFIRM = new Long(3);//接班方确认
	public static Long BOTH_CONFIRM = new Long(4);//交接班双方均可确认
	
	public static Long ONLY_CHIEF_CAN_CONFIRM = new Long(1);//只有主班人可以确认交接班
	public static Long EVERYONE_CAN_CONFIRM = new Long(2);//所有值班人员均可以确认交接班
	
	public static Long TRANSFER_FLAG = new Long(1);//日志记录迁移标志
	
	public static String LOG_SERIAL_IDENTIFIER = "DUTYSERIAL";
	
	private static HashMap confirmStatusSet;
	private static HashMap logStatusSet;
	/**
	 * 获得交接班确认状态
	 * @param confirmStatusId
	 * @return
	 */
	public static String getConfirmStatus(String confirmStatusId){
		String confirmStatus = "";
		try {
			if (confirmStatusId != null && confirmStatusId.trim().length()>0) {
				confirmStatus = (String) getConfirmStatus(new Long(	confirmStatusId));
			}
		} catch (Exception e) {
			Log.logger.error("数据格式转换错误！"+e.getMessage());
		}		
		return confirmStatus;
	}

	/**
	 * 获得交接班确认状态
	 * @param confirmStatusId
	 * @return
	 */
	public static String getConfirmStatus(Long confirmStatusId){
		String confirmStatus = "";
		try {
			//初始化交接确认状态数据
			if (confirmStatus == null || confirmStatus.length()<=0) {
				setConfirmStatus();
			}
			if(confirmStatusId == null ){
				confirmStatusId = ConfirmStatus.NO_ACTION;
			}
			confirmStatus = (String)confirmStatusSet.get(confirmStatusId);
		} catch (Exception e) {
			Log.logger.error("获得交接班确认状态数据时发现错误！"+e.getMessage());
		}		
		return confirmStatus;
	}
	
	/**
	 * 返回交接班确认状态集合
	 * @return　HashMap
	 */
	private static HashMap setConfirmStatus(){
		confirmStatusSet = new HashMap();
		confirmStatusSet.put(NO_ACTION,"交接尚未开始");
		confirmStatusSet.put(CHECK_IN_REQUEST,"交班方申请");
		confirmStatusSet.put(CHECK_OUT_REQUEST,"接班方申请");
		confirmStatusSet.put(BOTH_AGREE,"双方同意");
		confirmStatusSet.put(CHECK_OUT_DENY,"接班方拒绝");
		confirmStatusSet.put(CHECK_IN_DENY,"交班方拒绝");
		return confirmStatusSet;
	}
	/**
	 * 返回日志状态集合
	 * @return
	 */
	private static HashMap setLogStatus(){
		logStatusSet = new HashMap();
		logStatusSet.put(LOG_OPEN,"日志未关闭");
		logStatusSet.put(LOG_CLOSED,"日志已关闭");
		return logStatusSet;
	}
	public static String getLogStatus(Long logStatusId){
		String logStatus = "";
		try {
			if (logStatus == null || logStatus.length() <= 0) {
				setLogStatus();
			}
			logStatus = (String)logStatusSet.get(logStatusId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return logStatus;
	}

}
