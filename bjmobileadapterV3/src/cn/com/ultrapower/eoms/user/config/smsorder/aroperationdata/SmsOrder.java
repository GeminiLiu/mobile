package cn.com.ultrapower.eoms.user.config.smsorder.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.config.smsorder.bean.SmsOrderInfo;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-18
 */
public class SmsOrder {
	
	static final Logger logger = (Logger) Logger.getLogger(SmsOrder.class);
	
	GetFormTableName tablename = new GetFormTableName();
	String driverurl           = tablename.GetFormName("driverurl");
	String user     		   = tablename.GetFormName("user");
	String password			   = tablename.GetFormName("password");
	int serverport			   = Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName			   = tablename.GetFormName("smsorder");
	
	/**
	 * <p>Description:对工单短信订阅管理信息进行数据添加<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-18
	 * @param smsOrderInfo
	 * @return boolean
	 */
	public boolean insertSmsOrder(SmsOrderInfo smsOrderInfo)
	{
		try
		{
			ArrayList smsOrderInfoInfoValue	= SmsOrderAssociate.associateInsert(smsOrderInfo);
			ArEdit ar						= new ArEdit(user, password, driverurl, serverport);
			return ar.ArInster(TBLName,smsOrderInfoInfoValue);
		}
		catch(Exception e)
		{
			logger.error("[467]SmsOrder.insertSmsOrder() 对工单短信订阅管理信息进行数据添加失败"+e.getMessage());
			return false;
		}		
	}
	
	/**
	 * <p>Description:根据ID对工单短信订阅管理信息进行数据删除<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-20
	 * @param smsOrderId
	 * @return boolean
	 */
	public boolean deleteSmsOrder(String smsOrderId)
	{
		try
		{
			ArEdit ar = new ArEdit(user, password, driverurl, serverport);
	    	return ar.ArDelete(TBLName, smsOrderId);
		}
		catch(Exception e)
		{
			logger.error("[468]SmsOrder.deleteSmsOrder() 根据ID进行数据删除失败"+e.getMessage());
			return false;
		}
    }
	
}
