package cn.com.ultrapower.eoms.user.sla.aroperationdata;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.sla.bean.ActionMailCreateBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
/**
 * @author xuquanxing
 *该类是用于新建邮件动作时对remedy数据库进行插入操作
 */
public class MailOperation
{
	GetFormTableName getformtablename = new GetFormTableName();
	String formname                   = getformtablename.GetFormName("sysslamail");
	String username                   = getformtablename.GetFormName("user");
	String driverurl                  = getformtablename.GetFormName("driverurl");
	int    port                       = Integer.parseInt(getformtablename.GetFormName("serverport"));
	String password                   = getformtablename.GetFormName("password");
    static final Logger logger = (Logger) Logger.getLogger(MailOperation.class);
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param mailcreatebean
	 * @return boolean
	 */
    public boolean insertMail(ActionMailCreateBean mailcreatebean)
    {
		try
		{
	    	boolean isflag=false;
			ArEdit aredit   = new ArEdit(username,password,driverurl,port);
			//ArEdit aredit=new ArEdit("Demo","","192.168.10.201",8888);
			MailFieldAssocation mailfieldassocation=new MailFieldAssocation();
			ArrayList insertresult=mailfieldassocation.associationResult(mailcreatebean);
			isflag=aredit.ArInster(formname,insertresult);
			return isflag;
		}catch(Exception e)
		{
			logger.error("[119]MailOperation:insertMail插入邮件信息时发生异常"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param mailcreatebean
	 * @param id the id of the modified record
	 * @return boolean
	 */
	public boolean modidyMail(ActionMailCreateBean mailcreatebean,String id)
	{
		try
		{
			boolean isflag=false;
			ArEdit aredit   = new ArEdit(username,password,driverurl,port);
			MailFieldAssocation mailfieldassocation=new MailFieldAssocation();
			ArrayList insertresult=mailfieldassocation.associationResult(mailcreatebean);
			isflag=aredit.ArModify(formname,id,insertresult);
			return isflag;
		}catch(Exception e)
		{
			logger.error("[120]MailOperation:modidyMail修改邮件信息时发生异常"+e.getMessage());
			return false;
		}		
	}
	
	//the test method
	public static void main(String[] args)
	{
		MailOperation op=new MailOperation();
		ActionMailCreateBean mailcreatebean =new ActionMailCreateBean();
		mailcreatebean.setMail_slaid("1");
		mailcreatebean.setMail_content("2");
		mailcreatebean.setMail_copysendto("3");
		mailcreatebean.setMail_sendquery("4");
		mailcreatebean.setMail_sendtouser("5");
		mailcreatebean.setMail_actionid("000000000000002");
		op.insertMail(mailcreatebean);//test the insert operation
		//boolean is=op.modidyMail(mailcreatebean,"000000000000005");//test the modify operation
		//System.out.print(is);
	}
}
