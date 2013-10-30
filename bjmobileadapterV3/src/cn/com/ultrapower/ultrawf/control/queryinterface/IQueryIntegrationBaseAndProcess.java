package cn.com.ultrapower.ultrawf.control.queryinterface;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.ParBaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;

public  interface IQueryIntegrationBaseAndProcess {
	
	/**
	 * 等待处理：　处理 (等于)=待处理+处理中  	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListProcessDeal(String p_UserLoginName,int p_PageNumber,int p_StepRow);
	/**
	 * 等待处理：　处理 (等于)=待处理（未受理）+处理中(已受理)
	 * @param p_BaseSchema
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListProcessDeal(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow);
	/**
	 * 待处理（未受理）
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListWaitDeal(String p_UserLoginName,int p_PageNumber,int p_StepRow);
	/**
	 * 待处理（未受理）
	 * @param p_BaseSchema
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListWaitDeal(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow);
	/**
	 * 待阅事宜(返回BaseModel类列表)
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListDealing(String p_UserLoginName,int p_PageNumber,int p_StepRow);
	public List GetListDealing(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow);
	
	public List GetListWaitConfirm(String p_UserLoginName,int p_PageNumber,int p_StepRow);
	/**
	 * 
	 * @param p_BaseSchema
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListWaitConfirm(String p_BaseSchema , String p_UserLoginName,int p_PageNumber,int p_StepRow);
	/**
	 * 等待审批事宜(返回BaseModel类列表)
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListWaitAuditing(String p_UserLoginName,int p_PageNumber,int p_StepRow);
	/**
	 * 
	 * @param p_BaseSchema
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListWaitAuditing(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow);
	
	
	public List GetList(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow );
	
	//返回查询的页数

	public int GetPageCount();	
	public int getQueryResultRows();
	
	public List GetListMyCreate(String p_UserLoginName,int p_PageNumber,int p_StepRow);
	public List GetListMyCreate(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow);
	
	
	//某人所有待处理超时的工单
	public int GetOverTimeBaseAll(String p_UserLoginName);
	//某人待处理超时多少分钟的工单
	public int GetOverTimeBaseCountInMinute(String p_UserLoginName,int p_OverMinute);
	//已受理的超时工单
	public int GetOverTimeBaseCountProcessed(String p_UserLoginName);
	//已受理的超时多少分钟工单
	public int GetOverTimeBaseCountProcessed(String p_UserLoginName,int p_OverMinute);
	//返回满足条件数据的行数
	public int GetResultCount(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess);
	

	/**
	 * 查询某人某时段内处理过的所有的工单信息和Process信息(已处理完成的)
	 * 查询返回IntegrationBaseAndDealProcessModle，例：如果对同一个工单处理过两次则会返回两
	 * 个IntegrationBaseAndDealProcessModle对象,但工单信息都是一样的。
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return IntegrationBaseAndDealProcessModle List
	 */
	public List GetProcessMyDealAndIsFinished(ParBaseModel p_ParBaseModel,
			ParDealProcess p_ParDealProcess, int p_PageNumber, int p_StepRow);
	
	
	/**
	 * 查询某人某时段内处理过的所有的工单信息(只查询返回工单信息)
	 * @param p_LoginName
	 * @param p_startDate
	 * @param p_endDate
	 * @return 返回BaseModel List
	 */
	public List GetMyProcessDealBase(String p_LoginName,String p_startDate,String p_endDate);
	/**
	 * 查询某人某时段内审批过的所有的工单信息(只查询返回工单信息)
	 * @param p_LoginName
	 * @param p_startDate
	 * @param p_endDate
	 * @return 返回BaseModel List
	 */
	public List GetMyProcessAuditingBase(String p_LoginName,String p_startDate,String p_endDate);
	/**
	 * 查询某人某时段内派发的所有的工单信息(只查询返回工单信息)
	 * @param p_LoginName
	 * @param p_startDate
	 * @param p_endDate
	 * @return 返回BaseModel List
	 */
	public List GetMyAssignBase(String p_LoginName,String p_startDate,String p_endDate);
	
	
	public List getListForBind(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow );
	public String getListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow );
	
	

}
