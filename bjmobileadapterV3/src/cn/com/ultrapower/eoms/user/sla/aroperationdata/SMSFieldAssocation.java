package cn.com.ultrapower.eoms.user.sla.aroperationdata;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.sla.bean.ActionMailCreateBean;
import cn.com.ultrapower.eoms.user.sla.bean.ActionSMSCreateBean;

/**
 * @author xuquanxing
 * 该类是用于新建短信动作时拼接其个字段的�?�，方便插入到remedy中
 */

public class SMSFieldAssocation 
{
    static final Logger logger = (Logger) Logger.getLogger(SMSFieldAssocation.class);
	/**
	 * 日期 2006-10-14
	 * @author xuquanxing 
	 * @param smscreatebean
	 * @return ArrayList
	 */
	private ArInfo initSms_slaid(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArInfo slaid = new ArInfo();
			slaid.setFieldID("600000012");
			slaid.setValue(smscreatebean.getSms_slaid());
			slaid.setFlag("1");
			return slaid;
		}catch(Exception e)
		{
			logger.error("[133]SMSFieldAssocation:initSms_slaid初始化短信所属slaid时发生异常"+e.getMessage());
			return null;
		}
    }
	
	/**
	 * 日期 2006-10-14
	 * @author xuquanxing
	 * @param smscreatebean
	 * @return ArrayList
	 */
	private ArInfo initSms_sendtouser(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArInfo sendtouser = new ArInfo();
			sendtouser.setFieldID("600000013");
			sendtouser.setValue(smscreatebean.getSms_sendtouser());
			sendtouser.setFlag("1");
			return sendtouser;
		}catch(Exception e)
		{
			logger.error("[134]SMSFieldAssocation:initSms_sendtouser初始化短信发送对象时发生异常"+e.getMessage());
			return null;
		}
    }
	
	/**
	 * 日期 2006-10-14
	 * @author xuquanxing 
	 * @param smscreatebean
	 * @return ArrayList
	 */
	
	private ArInfo initSms_content(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArInfo content = new ArInfo();
			content.setFieldID("600000014");
			content.setValue(smscreatebean.getSms_content());
			content.setFlag("1");
			return content;
		}catch(Exception e)
		{
			logger.error("[135]SMSFieldAssocation:initSms_content初始化短信发送内容时发生异常"+e.getMessage());
			return null;
		}
    }
	
	/**
	 * 日期 2006-10-14
	 * @author xuquanxing 
	 * @param smscreatebean
	 * @return ArrayList
	 */
	private ArInfo initSms_sendquery(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArInfo sendquery = new ArInfo();
			sendquery.setFieldID("600000018");
			sendquery.setValue(smscreatebean.getSms_sendquery());
			sendquery.setFlag("1");
			return sendquery;
		}catch(Exception e)
		{
			logger.error("[136]SMSFieldAssocation:initSms_sendquery初始化短信发送条件时发生异常"+e.getMessage());
			return null;
		}
    }
	
	/**
	 * 日期 2006-10-14
	 * @author xuquanxing 
	 * @param smscreatebean
	 * @return ArrayList
	 */
	private ArInfo initSms_sendendquery(ActionSMSCreateBean smscreatebean){
		try
		{
			ArInfo sendendquery = new ArInfo();
			sendendquery.setFieldID("600000015");
			sendendquery.setValue(smscreatebean.getSms_sendendquery());
			sendendquery.setFlag("1");
			return sendendquery;
		}catch(Exception e)
		{
			logger.error("[137]SMSFieldAssocation:initSms_sendendquery初始化短信结束条件时发生异常"+e.getMessage());
			return null;
		}

    }
	
	/**
	 * 日期 2006-10-14
	 * @author xuquanxing 
	 * @param smscreatebean
	 * @return ArrayList
	 */
	private ArInfo initSms_send(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArInfo send = new ArInfo();
			send.setFieldID("600000016");
			send.setValue(smscreatebean.getSms_send());
			send.setFlag("1");
			return send;
		}catch(Exception e)
		{
			logger.error("[138]SMSFieldAssocation:initSms_send初始化短信发送周期时发生异常"+e.getMessage());
			return null;
		}

    }
	
	/**
	 * 日期 2006-10-14
	 * @author xuquanxing 
	 * @param smscreatebean
	 * @return ArrayList
	 */
	private ArInfo initSms_sendnum(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArInfo sendnum = new ArInfo();
			sendnum.setFieldID("600000017");
			sendnum.setValue(smscreatebean.getSms_sendnum());
			sendnum.setFlag("1");
			return sendnum;
		}catch(Exception e)
		{
			logger.error("[139]SMSFieldAssocation:initSms_sendnum初始化短信发送次数时发生异常"+e.getMessage());
			return null;
		}

    }
	
	private ArInfo initSms_actionid(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArInfo arinfo = new ArInfo();
			arinfo.setFieldID("600000036");
			arinfo.setValue(smscreatebean.getSms_actionid());
			arinfo.setFlag("1");
			return arinfo;
		}catch(Exception e)
		{
			logger.error("[140]SMSFieldAssocation:initSms_actionid初始化短信id时发生异常"+e.getMessage());
			return null;
		}

	} 
	
	public  ArrayList associationResult(ActionSMSCreateBean smscreatebean)
	{
		try
		{
			ArrayList lastresult=new ArrayList();
			ArInfo sms_slaid           = initSms_slaid(smscreatebean);
			ArInfo sms_sendtouser      = initSms_sendtouser(smscreatebean);
			ArInfo sms_content         = initSms_content(smscreatebean);
			ArInfo sms_sendquery       = initSms_sendquery( smscreatebean);
			ArInfo sms_sendendquery    = initSms_sendendquery(smscreatebean);
			ArInfo sms_send            = initSms_send(smscreatebean);
			ArInfo sms_sendnum         = initSms_sendnum(smscreatebean);
			ArInfo sms_actionid        = initSms_actionid( smscreatebean);
			lastresult.add(sms_slaid);
			lastresult.add(sms_sendtouser);
			lastresult.add(sms_content);
			lastresult.add( sms_sendquery);
			lastresult.add(sms_sendendquery);
			lastresult.add(sms_send);
			lastresult.add(sms_sendnum);
			lastresult.add(sms_actionid);
			return lastresult;
		}catch(Exception e)
		{
			logger.error("[141]SMSFieldAssocation:associationResult封装短信信息时发生异常"+e.getMessage());
			return null;
		}

	}
	

}
