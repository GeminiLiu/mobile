package cn.com.ultrapower.eoms.user.sla.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.sla.bean.ActionMailCreateBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;



/**
 * @author xuquanxing
 * 
 *该类是用于新建邮件动作时拼接其个字段方便插入到remedy
 * */
public class MailFieldAssocation {
    static final Logger logger = (Logger) Logger.getLogger(MailFieldAssocation.class);
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param mailcreatebean
	 * @return ArrayList
	 */
	private ArInfo initMail_slaid(ActionMailCreateBean mailcreatebean){
		try
		{
			ArInfo arinfo = new ArInfo();
			arinfo.setFieldID("600000021");
			arinfo.setValue(mailcreatebean.getMail_slaid());
			arinfo.setFlag("1");
			return arinfo;			
		}catch(Exception e)
		{
			logger.error("[112]MailFieldAssocation:initMail_slaid初始化slaid时发生异常"+e.getMessage());
		    return null;
		}
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param mailcreatebean
	 * @return ArrayList
	 */
	private ArInfo initMail_sendtouser(ActionMailCreateBean mailcreatebean){
		try
		{
			ArInfo arinfo = new ArInfo();
			arinfo.setFieldID("600000025");
			arinfo.setValue(mailcreatebean.getMail_sendtouser());
			arinfo.setFlag("1");
			return arinfo;			
		}catch(Exception e)
		{
			logger.error("[113]MailFieldAssocation:initMail_sendtouser初始化发送对象时发生异常"+e.getMessage());
		    return null;
		}
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param actionmailcreatebean
	 * @return ArrayList
	 */
	private ArInfo initMail_content(ActionMailCreateBean actionmailcreatebean){
		try
		{
			ArInfo arinfo = new ArInfo();
			arinfo.setFieldID("600000022");
			arinfo.setValue(actionmailcreatebean.getMail_content());
			arinfo.setFlag("1");
			return arinfo;
		}catch(Exception e)
		{
			logger.error("[114]MailFieldAssocation:initMail_content初始化邮件内容时发生异常"+e.getMessage());
		    return null;
		}
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param actionmailcreatebean
	 * @return ArrayList
	 */
	private ArInfo initMail_sendquery(ActionMailCreateBean actionmailcreatebean){
		try
		{
			ArInfo arinfo = new ArInfo();
			arinfo.setFieldID("600000024");
			arinfo.setValue(actionmailcreatebean.getMail_sendquery());
			arinfo.setFlag("1");
			return arinfo;
		}catch(Exception e)
		{
			logger.error("[115]MailFieldAssocation:initMail_sendquery初始化邮件发送条件时发生异常"+e.getMessage());
		    return null;
		}
	}
   
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param actionmailcreatebean
	 * @return ArrayList
	 */
	private ArInfo initMail_copysendto(ActionMailCreateBean actionmailcreatebean){
		try
		{
			ArInfo arinfo = new ArInfo();
			arinfo.setFieldID("600000023");
			arinfo.setValue(actionmailcreatebean.getMail_copysendto());
			arinfo.setFlag("1");
			return arinfo;
		}catch(Exception e)
		{
			logger.error("[116]MailFieldAssocation:initMail_copysendto初始化邮件抄送对象时发生异常"+e.getMessage());
		    return null;
		}
	}
	
	private ArInfo initMail_actionid(ActionMailCreateBean actionmailcreatebean){
		try
		{
			ArInfo arinfo = new ArInfo();
			arinfo.setFieldID("600000035");
			arinfo.setValue(actionmailcreatebean.getMail_actionid());
			arinfo.setFlag("1");
			return arinfo;
		}catch(Exception e)
		{
			logger.error("[117]MailFieldAssocation:initMail_actionid初始化邮件id时发生异常"+e.getMessage());
		    return null;
		}

	}
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param mailcreatebean
	 * @return ArrayList
	 * 该类对外的接口
	 * 	 */
	public ArrayList associationResult(ActionMailCreateBean mailcreatebean){
		try
		{
			ArrayList mailresult     = new ArrayList();
			ArInfo slaid             = initMail_slaid(mailcreatebean);
			ArInfo senttouser        = initMail_sendtouser(mailcreatebean);
			ArInfo content           = initMail_content(mailcreatebean);
			ArInfo sendquery         = initMail_sendquery(mailcreatebean);
			ArInfo copysendto        = initMail_copysendto(mailcreatebean);
			ArInfo actionid          = initMail_actionid(mailcreatebean);
			mailresult.add(slaid);
			mailresult.add(senttouser);
			mailresult.add(content);
			mailresult.add(sendquery);
			mailresult.add(copysendto);
			mailresult.add(actionid);
			return mailresult;
		}catch(Exception e)
		{
			logger.error("[118]MailFieldAssocation：associationResult返回封装的mail字段信息时发送异常");
		    return null;
		}
	}
}
