package cn.com.ultrapower.ultrawf.control.queryinterface;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.*;
import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.ultrawf.share.constants.*;;
public class BaseInfoFacde  implements IBaseInfo{

	/**
	 *
	 * 建单人为本值班室的到目前为止工单状态还未完成的故障工单
	 * @param p_personSql
	 * @return 返回BaseModel的List
	 */
	private int intQueryResultRows=0;
	
	public int getIntQueryResultRows() {
		return intQueryResultRows;
	}
	public void setIntQueryResultRows(int intQueryResultRows) {
		this.intQueryResultRows = intQueryResultRows;
	}
	public List getIncidentBaseForTeamAndIsNotFinished(String p_personSql,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		Base m_Base=new Base();
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseSchema(Constants.TblBaseIncident);		
		m_ParBaseModel.setBaseCreatorLoginName("SQLIN:"+p_personSql);
		//未关闭和未作废的工单都是未完成的工单
		m_ParBaseModel.setBaseStatus("NOT:已完成,已关闭,已作废");
		//ParDealProcess m_ParDealProcess=new ParDealProcess();
		//m_ParDealProcess.setProcessBaseSchema(Constants.TblBaseIncident);
		m_list=m_Base.GetList(m_ParBaseModel,p_PageNumber,p_StepRow);	
		setIntQueryResultRows(m_Base.getQueryResultRows());
		return m_list;
	}
	/**
	 * 建单人为本值班室的到目前为止工单状态还未完成的重大故障工单
	 * @param p_personSql
	 * @return 返回BaseModel的List
	 */
	public List getImportIncidentBaseForTeamAndIsNotFinished(String p_personSql,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		Base m_Base=new Base();
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseSchema(Constants.TblBaseImportIncident);		
		m_ParBaseModel.setBaseCreatorLoginName("SQLIN:"+p_personSql);
		//如果故障工单和重大故障的BaseSchema是一样的则通过故障类型是否是重大故障来区别
		//if(Constants.TblBaseIncident.equals(Constants.TblBaseImportIncident))
		//	m_ParBaseModel.SetOwnerFiled("","");
		//未关闭和未作废的工单都是未完成的工单
		m_ParBaseModel.setBaseStatus("NOT:已完成,已关闭,已作废");
		//ParDealProcess m_ParDealProcess=new ParDealProcess();
		//m_ParDealProcess.setProcessBaseSchema(Constants.TblBaseIncident);
		m_list=m_Base.GetList(m_ParBaseModel,p_PageNumber,p_StepRow);	
		setIntQueryResultRows(m_Base.getQueryResultRows());
		return m_list;
	}

	/**
	 * 用户所选的日志中，建单人为该班次的人员在该班次时间段内建立的故障工单
	 * @param p_personSql 人员查询sql
	 * @param startDate 开始时间
	 * @param p_endDate 结束时间
	 * @return  返回BaseModel的List
	 */
	public List getIncidentBaseForTeamCreate(String p_personSql,String p_startDate,String p_endDate,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		Base m_Base=new Base();
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseSchema(Constants.TblBaseIncident);		
		m_ParBaseModel.setBaseCreatorLoginName("SQLIN:"+p_personSql);
		p_startDate=FormatString.CheckNullString(p_startDate);
		if(!p_startDate.trim().equals(""))
			m_ParBaseModel.setBaseCreateDateBegin(FormatTime.FormatDateStringToInt(p_startDate));

		p_endDate=FormatString.CheckNullString(p_endDate);
		if(!p_endDate.trim().equals(""))
			m_ParBaseModel.setBaseCreateDateEnd(FormatTime.FormatDateStringToInt(p_endDate));

		//ParDealProcess m_ParDealProcess=new ParDealProcess();
		//m_ParDealProcess.setProcessBaseSchema(Constants.TblBaseIncident);
		
		m_list=m_Base.GetList(m_ParBaseModel,p_PageNumber,p_StepRow);		
		setIntQueryResultRows(m_Base.getQueryResultRows());
		return m_list;

	}
	
	/**
	 * 用户所选的日志中，该班次的人员在该班次时间段内所涉及到的所有故障工单
	 * @param p_personSql 人员查询sql
	 * @param p_startDate 开始时间
	 * @param p_endDate 结束时间
	 * @return 返回BaseModel的List
	 */
	public List getIncidentBaseForTeamDealAll(String p_personSql,String p_startDate,String p_endDate,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		Base m_Base=new Base();
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseSchema(Constants.TblBaseIncident);		
		


		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessBaseSchema(Constants.TblBaseIncident);
		
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyDeallAll);
		p_startDate=FormatString.CheckNullString(p_startDate);
		if(!p_startDate.trim().equals(""))
			m_ParDealProcess.setEdDateBegin(FormatTime.FormatDateStringToInt(p_startDate));

		p_endDate=FormatString.CheckNullString(p_endDate);
		if(!p_endDate.trim().equals(""))
			m_ParDealProcess.setEdDateEnd(FormatTime.FormatDateStringToInt(p_endDate));
		p_personSql=FormatString.CheckNullString(p_personSql);
		//从处理记录表中查找匹配的人员
		if(!p_personSql.trim().equals(""))
			m_ParDealProcess.setTasekPersonExtendSql(" and C700020404 in ("+p_personSql+")");
		
		m_list=m_Base.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);	
		setIntQueryResultRows(m_Base.getQueryResultRows());
		return m_list;

	}
	
	/**
	 * 该班次的人员在该班次时间段内所涉及到的所有工单,如果开始时间或结束时间为空则查所有时间的
	 * @param p_personSql 人员查询sql
	 * @param p_startDate 开始时间
	 * @param p_endDate 结束时间
	 * @return 返回BaseModel的List
	 */
	public List getBaseForTeamDealAll(String p_personSql,String p_startDate,String p_endDate,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		Base m_Base=new Base();
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		//m_ParBaseModel.setBaseSchema(Constants.TblBaseIncident);		
		


		ParDealProcess m_ParDealProcess=new ParDealProcess();
		//m_ParDealProcess.setProcessBaseSchema(Constants.TblBaseIncident);
		
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyDeallAll);
		p_startDate=FormatString.CheckNullString(p_startDate);
		if(!p_startDate.trim().equals(""))
			m_ParDealProcess.setEdDateBegin(FormatTime.FormatDateStringToInt(p_startDate));

		p_endDate=FormatString.CheckNullString(p_endDate);
		if(!p_endDate.trim().equals(""))
			m_ParDealProcess.setEdDateEnd(FormatTime.FormatDateStringToInt(p_endDate));
		p_personSql=FormatString.CheckNullString(p_personSql);
		//从处理记录表中查找匹配的人员
		if(!p_personSql.trim().equals(""))
			m_ParDealProcess.setTasekPersonExtendSql(" and C700020404 in ("+p_personSql+")");
		
		m_list=m_Base.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);	
		setIntQueryResultRows(m_Base.getQueryResultRows());
		return m_list;
	}
	
	
	/**
	 * 该班次的人员在该班次时间段内所归档(关闭)的所有工单,如果开始时间或结束时间为空则查所有时间的
	 * @param p_personSql 查询人员登录名的sql
	 * @param p_startDate 开始日期
	 * @param p_endDate 结束日期
	 * @param p_PageNumber  第几页
	 * @param p_StepRow 每页行数
	 * @return
	 */
	public List getBaseForTeamArchiveAll(String p_personSql,String p_startDate,String p_endDate,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		Base m_Base=new Base();
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseSchema(Constants.TblBaseIncident);		
		


		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessBaseSchema(Constants.TblBaseIncident);
		
		//m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyDeallAll);
		
		/*
		p_startDate=FormatString.CheckNullString(p_startDate);
		if(!p_startDate.trim().equals(""))
			m_ParDealProcess.setEdDateBegin(FormatTime.FormatDateStringToInt(p_startDate));

		p_endDate=FormatString.CheckNullString(p_endDate);
		if(!p_endDate.trim().equals(""))
			m_ParDealProcess.setEdDateEnd(FormatTime.FormatDateStringToInt(p_endDate));
			*/
		//关闭时间
		p_startDate=FormatString.CheckNullString(p_startDate);
		if(!p_startDate.trim().equals(""))
			m_ParBaseModel.setBaseCloseDateBegin(FormatTime.FormatDateStringToInt(p_startDate));

		p_endDate=FormatString.CheckNullString(p_endDate);
		if(!p_endDate.trim().equals(""))
			m_ParBaseModel.setBaseCloseDateEnd(FormatTime.FormatDateStringToInt(p_endDate));
		
		p_personSql=FormatString.CheckNullString(p_personSql);
		//从处理记录表中查找匹配的人员
		if(!p_personSql.trim().equals(""))
			m_ParDealProcess.setTasekPersonExtendSql(" and C700020404 in ("+p_personSql+")");
		
		m_ParDealProcess.setProcessStatus("已关闭");
		
		m_list=m_Base.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);	
		setIntQueryResultRows(m_Base.getQueryResultRows());
		return m_list;
	}	


}
