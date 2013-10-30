package cn.com.ultrapower.eoms.user.sla.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.sla.bean.ActionUpdateCreateBean;
import cn.com.ultrapower.eoms.user.sla.bean.SlaDefineBean;


public class UpdateOperation 
{
	GetFormTableName getformtablename = new GetFormTableName();
	String formname                   = getformtablename.GetFormName("sysslaworkflowmanage");
	String username                   = getformtablename.GetFormName("user");
	String driverurl                  = getformtablename.GetFormName("driverurl");
	int    port                       = Integer.parseInt(getformtablename.GetFormName("serverport"));
	String password                   = getformtablename.GetFormName("password");
    static final Logger logger = (Logger) Logger.getLogger(UpdateOperation.class);
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing                                                 
	 * @param updatecreatebean
	 * @return boolean
	 */
	public boolean insertUpdate(ActionUpdateCreateBean updatecreatebean)
	{
		try
		{
			boolean successflag=false;
			ArEdit aredit   = new ArEdit(username,password,driverurl,port);
			UpdateFieldAssocation updatefieldassocation = new UpdateFieldAssocation ();
			ArrayList insertResult=updatefieldassocation.assocationResult(updatecreatebean);
			successflag=aredit.ArInster(formname,insertResult);
			return successflag;
		}catch(Exception e)
		{
			logger.error("[149]UpdateOperation:insertUpdate插入升级动作异常"+e.getMessage());
			return false;
		}
	}
    
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param updatecreatebean
	 * @param id
	 * @return boolean
	 */
	public boolean modifyUpdate(ActionUpdateCreateBean updatecreatebean,String id)
	{
		try
		{
			boolean isSuccessFlag=false;
			ArEdit aredit   = new ArEdit(username,password,driverurl,port);
			UpdateFieldAssocation updatefieldassocation = new UpdateFieldAssocation  ();
			ArrayList insertResult=updatefieldassocation.assocationResult(updatecreatebean);
			isSuccessFlag=aredit.ArModify(formname,id,insertResult);
			return isSuccessFlag;
		}catch(Exception e)
		{
			logger.error("[150]UpdateOperation:modifyUpdate修改升级动作异常"+e.getMessage());
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		UpdateOperation op=new UpdateOperation();
		ActionUpdateCreateBean actionupdatecreatebean=new ActionUpdateCreateBean();
		actionupdatecreatebean.setWorkflowmanage_sendquery("22222222");
		actionupdatecreatebean.setWorkflowmanage_sendtouser("222222222222");
		actionupdatecreatebean.setWorkflowmanage_slaid("22222222222");
		actionupdatecreatebean.setWorkFlowManage_ActionID("2222222");
		//op.insertUpdate(actionupdatecreatebean);//test the insert
		op.modifyUpdate(actionupdatecreatebean,"000000000000003");//test the modify
	}
}
