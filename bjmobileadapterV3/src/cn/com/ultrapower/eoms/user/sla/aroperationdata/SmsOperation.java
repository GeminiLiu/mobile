package cn.com.ultrapower.eoms.user.sla.aroperationdata;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.sla.bean.ActionSMSCreateBean;
import cn.com.ultrapower.eoms.user.sla.hibernate.dbmanage.GetSlaInfoList;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
/**
 * 日期 2006-10-15
 * @author xuquanxing/徐全星
 * 该类用于将短信动作内容插入到remedy数据库中
 */
public class SmsOperation
{
    static final Logger logger = (Logger) Logger.getLogger(SmsOperation.class);
	GetFormTableName getformtablename = new GetFormTableName();
	String formname                   = getformtablename.GetFormName("sysslasms");
	String username                   = getformtablename.GetFormName("user");
	String driverurl                  = getformtablename.GetFormName("driverurl");
	int    port                       = Integer.parseInt(getformtablename.GetFormName("serverport"));
	String password                   = getformtablename.GetFormName("password");
	
	/**
	 * 日期 2006-10-15
	 * @author x
uquanxing 
	 * @param smscreatebean
	 * @return boolean
	 */
	public boolean insertSms(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			boolean isflag=false;
			ArEdit aredit   = new ArEdit(username,password,driverurl,port);
			SMSFieldAssocation  smsfieldassocation =new  SMSFieldAssocation ();
			ArrayList inserresult= smsfieldassocation.associationResult(smscreatebean);
			isflag=aredit.ArInster(formname,inserresult);
			return isflag;
		}catch(Exception e)
		{
			logger.error("[142]SmsOperation:insertSms插入短信时异常"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param smscreatebean
	 * @param id
	 * @return boolean
	 */ 
	public boolean modidySms(ActionSMSCreateBean smscreatebean,String id)
	{
		try
		{
			boolean isflag = false;
			ArEdit aredit   = new ArEdit(username,password,driverurl,port);
			SMSFieldAssocation smsfieldassocation=new SMSFieldAssocation();
			ArrayList insertresult= smsfieldassocation.associationResult(smscreatebean);
			isflag=aredit.ArModify(formname,id,insertresult);
			return isflag;
		}catch(Exception e)
		{
			logger.error("[143]SmsOperation:modidySms修改短信时异常"+e.getMessage());
			return false;
		}

	}

	//the test method
	public static void main(String[] args)
	{
		SmsOperation op                   = new SmsOperation();
		ActionSMSCreateBean smscreatebean = new ActionSMSCreateBean ();
		smscreatebean.setSms_content("12");
		smscreatebean.setSms_send("12");
		smscreatebean.setSms_sendendquery("121");
		smscreatebean.setSms_sendnum("sdfs");
		smscreatebean.setSms_sendquery("sdfsd");
		smscreatebean.setSms_sendtouser("sdf");
		smscreatebean.setSms_slaid("123");
		//smscreatebean.setSms_actionid("23");
		//op.insertSms(smscreatebean);//test the insert method
		op.modidySms(smscreatebean,"000000000000001");
	}
	
}
