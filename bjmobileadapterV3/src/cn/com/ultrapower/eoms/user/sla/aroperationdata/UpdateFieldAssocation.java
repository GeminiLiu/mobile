package cn.com.ultrapower.eoms.user.sla.aroperationdata;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.sla.bean.ActionUpdateCreateBean;


/**
 * 日期 2006-10-15
 * @author xuquanxing
 * 该类用于拼接升级动作的插入字符串 
 */
public class UpdateFieldAssocation 
{
    static final Logger logger = (Logger) Logger.getLogger(UpdateFieldAssocation.class);
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param actionupdatecreatebean
	 * @return ArrayList
	 */
	private ArInfo initWorkflowmanage_slaid(ActionUpdateCreateBean actionupdatecreatebean)
	{
        try
        {
    		ArInfo slaid = new ArInfo();
    		slaid.setFieldID("600000031");
    		slaid.setValue(actionupdatecreatebean.getWorkflowmanage_slaid());
    		slaid.setFlag("1");
    		return slaid;
        }catch(Exception e)
        {
        	logger.error("[144]UpdateFieldAssocation:initWorkflowmanage_slaid升级动作slaid异常"+e.getMessage());
        	return null;
        }
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param actionupdatecreatebean
	 * @return ArrayList
	 */
	private ArInfo initWorkflowmanage_sendtouser(ActionUpdateCreateBean actionupdatecreatebean)
	{
        try
        {
    		ArInfo sendtouser = new ArInfo();
    		sendtouser.setFieldID("600000030");
    		sendtouser.setValue(actionupdatecreatebean.getWorkflowmanage_sendtouser());
    		sendtouser.setFlag("1");
    		return sendtouser;
        }catch(Exception e)
        {
        	logger.error("[145]UpdateFieldAssocation:initWorkflowmanage_sendtouser升级动作发送对象异常"+e.getMessage());
        	return null;
        }
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param actionupdatecreatebean
	 * @return ArrayList
	 */
	private ArInfo initWorkflowmanage_sendquery(ActionUpdateCreateBean actionupdatecreatebean)
	{
        try
        {
    		ArInfo sendtouser = new ArInfo();
    		sendtouser.setFieldID("600000029");
    		sendtouser.setValue(actionupdatecreatebean.getWorkflowmanage_sendquery());
    		sendtouser.setFlag("1");
    		return sendtouser;
        }catch(Exception e)
        {
        	logger.error("[146]UpdateFieldAssocation:initWorkflowmanage_sendquery升级动作发送条件异常"+e.getMessage());
        	return null;
        }
	}
	
	/**
	 * ���� 2006-10-17
	 * @author xuquanxing 
	 * @param actionupdatecreatebean
	 * @return ArInfo
	 */
	private ArInfo initWorkflowmanage_actionid(ActionUpdateCreateBean actionupdatecreatebean)
	{
        try
        {
    		ArInfo actionid = new ArInfo();
    		actionid.setFieldID("600000037");
    		actionid.setValue(actionupdatecreatebean.getWorkFlowManage_ActionID());
    		actionid.setFlag("1");
    		return actionid;
        }catch(Exception e)
        {
        	logger.error("[147]UpdateFieldAssocation:initWorkflowmanage_actionid升级动作id异常"+e.getMessage());
        	return null;
        }
	}
	
	/**
	 * 日期 2006-10-15
	 * @author xuquanxing 
	 * @param actionupdatecreatebean
	 * @return ArrayList
	 * outer interface
	 */
	public ArrayList assocationResult(ActionUpdateCreateBean actionupdatecreatebean)
	{
        try
        {
    		ArrayList lastresult             = new ArrayList();
    		ArInfo workflowmanage_slaid      = initWorkflowmanage_slaid(actionupdatecreatebean);
    		ArInfo workflowmanage_sendtouser = initWorkflowmanage_sendtouser(actionupdatecreatebean);
    		ArInfo workflowmanage_sendquery  = initWorkflowmanage_sendquery(actionupdatecreatebean);
    		ArInfo Workflowmanage_actionid   = initWorkflowmanage_actionid( actionupdatecreatebean);
            lastresult.add(workflowmanage_slaid);
            lastresult.add(workflowmanage_sendtouser);
            lastresult.add(workflowmanage_sendquery);
            lastresult.add(Workflowmanage_actionid);
            return lastresult;
        }catch(Exception e)
        {
        	logger.error("[148]UpdateFieldAssocation:assocationResult封装升级动作异常"+e.getMessage());
        	return null;
        }
	}

}
