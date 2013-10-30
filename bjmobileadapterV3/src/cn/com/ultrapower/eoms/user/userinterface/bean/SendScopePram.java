package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SendScopePram {
	
	//用户登录名
	private String userLoginName	= null;
	//工单schema
	private String sourceName		= null;
	//权限值
	/*提交审批 14 14
	 * 派单 15 15  
	 * 抄送 13 13  
	 * 协办 12 12 
	 * 转单 11 11 
	 * 追回审批 9 9 
	 * 关闭 8 8 
	 * 新建 7 7 
	 * 驳回审批 6 6
	 * 追回处理 5 5 
	 * 驳回处理 4 4
	 * 作废 3 3 
	 * 退回工单 2 2
	 * 查看  1 1
	 */
	private String actionvalue	= null;
	//节点父id
	private String nodeParentid	= null;
	//是否是自定义类型0:权限树1：自定义派发树
	private String customerflag	= null;
	//0:派发1：提审
	private String sendscopetype =  null;
	//工单流程：0 自由，1：固定  默认值：0
	private String workflowtype = null;
	private String DeptAndPeople="";
	public String getCustomerflag()
	{
		return customerflag;
	}
	public void setCustomerflag(String customerflag)
	{
		this.customerflag = customerflag;
	}
	public String getWorkflowtype()
	{
		return workflowtype;
	}
	public void setWorkflowtype(String workflowtype)
	{
		this.workflowtype = workflowtype;
	}
	public String getActionvalue()
	{
		return actionvalue;
	}
	public void setActionvalue(String actionvalue)
	{
		this.actionvalue = actionvalue;
	}
	public String getNodeParentid()
	{
		return nodeParentid;
	}
	public void setNodeParentid(String nodeParentid)
	{
		this.nodeParentid = nodeParentid;
	}
	public String getSourceName()
	{
		return sourceName;
	}
	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}
	public String getUserLoginName()
	{
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName)
	{
		this.userLoginName = userLoginName;
	}
	public String getcustomerflag()
	{
		return customerflag;
	}
	public void setcustomerflag(String customerflag)
	{
		this.customerflag = customerflag;
	}
	public String getSendscopetype()
	{
		return sendscopetype;
	}
	public void setSendscopetype(String sendscopetype)
	{
		this.sendscopetype = sendscopetype;
	}
	public String getDeptAndPeople() {
		return DeptAndPeople;
	}
	public void setDeptAndPeople(String deptAndPeople) {
		DeptAndPeople = deptAndPeople;
	}

}