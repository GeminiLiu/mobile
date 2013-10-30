package cn.com.ultrapower.ultrawf.control.process;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParBaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLogModel;

public interface IBase {

	public List getList(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow );
	
	public List getList(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow );	
	
	public BaseModel getOneForKey(String p_BaseSchema,String p_BaseID);
	
	
	/**
	 * 我处理过的工单(我处理过，不管是否处理完成。包括自己建单(处理)的工单)
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List getProcessMyDeallAll(String strLoginName,ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow );

	/**
	 * 我处理过的工单(我处理过，不管是否处理完成。不包括自己建单(处理)的工单)
	 * @param p_ParBaseModel
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List getProcessMyDeallAllNotIncludeNew(String strLoginName,ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow );

	
	/**
	 * 描述：我审批过的工单(我审批过，并且已完成的。）
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return BaseModel List
	 */
	public List getProcessMyAuditingAndIsFinished(String strLoginName,ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow );
	
	
	//返回查询的页数	public int getPageCount();	
	public int getResultRows();
	
	public String getListForBindSQL(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow );
	public List getListForBind(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow );
	
	public List getBaseListForBind(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow );
	public String getBaseListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow );
	public List getBaseListForBind(ParBaseModel p_ParBaseModel,ParDealProcessLogModel p_ParDealProcesslog,int p_PageNumber,int p_StepRow );
	public String getBaseListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcessLogModel p_ParDealProcesslog,int p_PageNumber,int p_StepRow );
	
	
}
