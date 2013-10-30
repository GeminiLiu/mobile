package cn.com.ultrapower.ultrawf.control.queryinterface;

import java.util.*;

public interface IBaseInfo {

	/**
	 *
	 * 建单人为本值班室的到目前为止工单状态还未完成的故障工单
	 * @param p_personSql
	 * @return 返回BaseModel的List
	 */
	public int getIntQueryResultRows();

	public void setIntQueryResultRows(int intQueryResultRows) ;
	
	public List getIncidentBaseForTeamAndIsNotFinished(String p_personSql,int p_PageNumber,int p_StepRow);
	/**
	 * 建单人为本值班室的到目前为止工单状态还未完成的重大故障工单
	 * @param p_personSql
	 * @return 返回BaseModel的List
	 */
	public List getImportIncidentBaseForTeamAndIsNotFinished(String p_personSql,int p_PageNumber,int p_StepRow);

	/**
	 * 用户所选的日志中，建单人为该班次的人员在该班次时间段内建立的故障工单
	 * @param p_personSql 人员查询sql
	 * @param startDate 开始时间
	 * @param p_endDate 结束时间
	 * @return 返回BaseModel的List
	 */
	public List getIncidentBaseForTeamCreate(String p_personSql,String p_startDate,String p_endDate,int p_PageNumber,int p_StepRow);
	
	/**
	 * 用户所选的日志中，该班次的人员在该班次时间段内所涉及到的所有故障工单
	 * @param p_personSql 人员查询sql
	 * @param p_startDate 开始时间
	 * @param p_endDate 结束时间
	 * @return 返回BaseModel的List
	 */
	public List getIncidentBaseForTeamDealAll(String p_personSql,String p_startDate,String p_endDate,int p_PageNumber,int p_StepRow);
	
	/**
	 * 该班次的人员在该班次时间段内所涉及到的所有工单,如果开始时间或结束时间为空则查所有时间的
	 * @param p_personSql 人员查询sql
	 * @param p_startDate 开始时间
	 * @param p_endDate 结束时间
	 * @return 返回BaseModel的List
	 */
	public List getBaseForTeamDealAll(String p_personSql,String p_startDate,String p_endDate,int p_PageNumber,int p_StepRow);

}
