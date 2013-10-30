package cn.com.ultrapower.eoms.user.config.smsorder.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.config.smsorder.bean.SmsOrderInfo;

/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-18
 */
public class SmsOrderAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(SmsOrderAssociate.class);
	
	/**
	 * <p>Description:工单短信订阅管理信息把字段信息封装到一个bean对象内<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-18
	 * @param ID
	 * @param value
	 * @param flag
	 * @return ArInfo
	 */
	public static ArInfo setObject(String ID,String value,String flag){
    	try
    	{
    		ArInfo arGroupInfo = new ArInfo();
    		arGroupInfo.setFieldID(ID);
    		arGroupInfo.setValue(value);
    		arGroupInfo.setFlag(flag);
    		return arGroupInfo;
    	}
    	catch(Exception e)
    	{
    		logger.error("[469]SmsOrderAssociate.setObject() 工单短信订阅管理信息把字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * <p>Description:工单短信订阅管理信息把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-18
	 * @param smsOrderInfo
	 * @return ArrayList
	 */
	public static ArrayList associateInsert(SmsOrderInfo smsOrderInfo){
		
		String temp_SmsOrderAction		= smsOrderInfo.getSmsOrderAction();
		String temp_SmsOrderUserName	= smsOrderInfo.getSmsOrderUserName();
		String temp_SmsOrderFormSchema	= smsOrderInfo.getSmsOrderFormSchema();
		String temp_SmsOrderUrgent		= smsOrderInfo.getSmsOrderUrgent();
		Long temp_SmsOrderStartTime		= smsOrderInfo.getSmsOrderStartTime();
		Long temp_SmsOrderEndTime		= smsOrderInfo.getSmsOrderEndTime();
		String temp_SmsOrderMemo		= smsOrderInfo.getSmsOrderMemo();
		
		ArInfo SmsOrderAction		= setObject("650000001",temp_SmsOrderAction,"1");
		ArInfo SmsOrderUserName		= setObject("650000002",temp_SmsOrderUserName,"1");
		ArInfo SmsOrderFormSchema	= setObject("650000003",temp_SmsOrderFormSchema,"1");
		ArInfo SmsOrderUrgent		= setObject("650000004",temp_SmsOrderUrgent,"1");
		ArInfo SmsOrderStartTime	= setObject("650000005",String.valueOf(temp_SmsOrderStartTime),"1");
		ArInfo SmsOrderEndTime		= setObject("650000006",String.valueOf(temp_SmsOrderEndTime),"1");
		ArInfo SmsOrderMemo			= setObject("650000007",temp_SmsOrderMemo,"1");
		
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(SmsOrderAction);
			backlist.add(SmsOrderUserName);
			backlist.add(SmsOrderFormSchema);
			backlist.add(SmsOrderUrgent);
			backlist.add(SmsOrderStartTime);
			backlist.add(SmsOrderEndTime);
			backlist.add(SmsOrderMemo);
		
			return backlist;
		}
		catch(Exception e)
		{
			logger.error("[470]SmsOrderAssociate.associateInsert() 工单短信订阅管理信息把字段信息转换成ArrayList对象失败"+e.getMessage());
    		return null;
		}
	} 
	
}
