package cn.com.ultrapower.eoms.user.sla.bean;

/**
 * @author xuquanxing/徐全星
 * @version1.0
 *该类主要用于封装升级动作的内容的bean
 * */
public class ActionUpdateCreateBean
{
	private String workflowmanage_slaid       = "";//升级动作所属的slaid值
	private String workflowmanage_sendtouser  = "";//升级动作的发送对象
	private String workflowmanage_sendquery   = "";//发送条件
	private String WorkFlowManage_ActionID    = "";	//升级动作所属的动作类型id
	/**
	 * @return Returns the workflowmanage_sendquery.
	 */
	public String getWorkflowmanage_sendquery() 
	{
		return workflowmanage_sendquery;
	}
	/**
	 * @param workflowmanage_sendquery The workflowmanage_sendquery to set.
	 */
	public void setWorkflowmanage_sendquery(String workflowmanage_sendquery) 
	{
		this.workflowmanage_sendquery = workflowmanage_sendquery;
	}
	/**
	 * @return Returns the workflowmanage_sendtouser.
	 */
	public String getWorkflowmanage_sendtouser() 
	{
		return workflowmanage_sendtouser;
	}
	/**
	 * @param workflowmanage_sendtouser The workflowmanage_sendtouser to set.
	 */
	public void setWorkflowmanage_sendtouser(String workflowmanage_sendtouser)
	{
		this.workflowmanage_sendtouser = workflowmanage_sendtouser;
	}
	/**
	 * @return Returns the workflowmanage_slaid.
	 */
	public String getWorkflowmanage_slaid()
	{
		return workflowmanage_slaid;
	}
	/**
	 * @param workflowmanage_slaid The workflowmanage_slaid to set.
	 */
	public void setWorkflowmanage_slaid(String workflowmanage_slaid)
	{
		this.workflowmanage_slaid = workflowmanage_slaid;
	}
	/**
	 * @return Returns the workFlowManage_ActionID.
	 */
	public String getWorkFlowManage_ActionID() 
	{
		return WorkFlowManage_ActionID;
	}
	/**
	 * @param workFlowManage_ActionID The workFlowManage_ActionID to set.
	 */
	public void setWorkFlowManage_ActionID(String workFlowManage_ActionID) 
	{
		WorkFlowManage_ActionID = workFlowManage_ActionID;
	}

}
