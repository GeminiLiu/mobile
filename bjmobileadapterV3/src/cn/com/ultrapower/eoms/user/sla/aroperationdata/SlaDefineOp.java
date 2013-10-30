package cn.com.ultrapower.eoms.user.sla.aroperationdata;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.sla.bean.SlaDefineBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

/**
 * @author xuquanxing/徐全星
 * ��������该类是用于新建sla定义时对remedy数据库进行操作�ж���ݿ����ɾ�Ĳ���
 *
 */
public class SlaDefineOp 
{
    static final Logger logger = (Logger) Logger.getLogger(SlaDefineOp.class);
	GetFormTableName getformtablename = new GetFormTableName();
	String formname                   = getformtablename.GetFormName("syssla");
	String     username               = getformtablename.GetFormName("user");
	String     password               = getformtablename.GetFormName("password");
	String     ipname                 = getformtablename.GetFormName("driverurl");
	int        port                   = Integer.parseInt(getformtablename.GetFormName("serverport"));
	/**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param slaDefineBean
	 * @return boolean
	 * �˷�������对remedy进行插入操作ִ
	 * 	 */
	public String insertSla(SlaDefineBean slaDefineBean)
	{
		try
		{
			String returnvalue                    = "";
			ArEdit aredit   = new ArEdit(username,password,ipname,port);
			SlaFieldAssocation slafieldassocation = new SlaFieldAssocation ();
			ArrayList insertResult=slafieldassocation.assocationResult(slaDefineBean);
			logger.info("starttimeeeee:"+slaDefineBean.getSla_startdatetime());
			logger.info("endtimetime:"+slaDefineBean.getSla_enddatetime());
			returnvalue =aredit.ArInsterR(formname,insertResult);
			return returnvalue;
		}catch(Exception e)
		{
			logger.error("[121]SlaDefineOp:insertSla插入sla时发生异常"+e.getMessage());
			return null;
		}

	}
	
	/**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param slaDefineBean
	 * @param id
	 * @return boolean
	 * �˷�������ִ对remedy进行修改操作ִ
	 */
	public boolean modifySla(SlaDefineBean slaDefineBean,String id)
	{
		try
		{
			boolean isSuccessFlag                 = false;
			ArEdit aredit   = new ArEdit(username,password,ipname,port);
			SlaFieldAssocation slafieldassocation = new SlaFieldAssocation ();
			ArrayList insertResult                = slafieldassocation.assocationResult(slaDefineBean);
			isSuccessFlag=aredit.ArModify(formname,id,insertResult);
			return isSuccessFlag;
		}catch(Exception e)
		{
			logger.error("[122]SlaDefineOp:modifySla修改sla时发生异常"+e.getMessage());
			return false;
		}

	}
	
	public boolean deleteSla(String id)
	{
		try
		{
			boolean isSuccessFlag    = false;
			ArEdit aredit   = new ArEdit(username,password,ipname,port);
			isSuccessFlag=aredit.ArDelete(formname,id);
			return isSuccessFlag;
		}catch(Exception e)
		{
			logger.error("[123]SlaDefineOp:deleteSla删除sla时发生异常"+e.getMessage());
			return false;
		}		
	}
	

	
	public static void  main(String[] args)
	{
    	SlaDefineOp sla=new SlaDefineOp ();
    	SlaDefineBean slaDefineBean=new SlaDefineBean ();
    	slaDefineBean.setSla_name("test");
    	slaDefineBean.setSla_appcommendquery("nihhaoooo");
    	slaDefineBean.setSla_appcommstartquery("nihhaoooo");
    	slaDefineBean.setSla_apptable("dfs");
    	slaDefineBean.setSla_status("nihhaoooo");
    	slaDefineBean.setSla_startdatetime("12234567890111");
    	slaDefineBean.setSla_enddatetime("12234567890111");
    	slaDefineBean.setSla_usercommquery("sdfd");
      	sla.modifySla(slaDefineBean,"000000000000007");
    	System.out.println(sla.insertSla(slaDefineBean));
    }
    
}
