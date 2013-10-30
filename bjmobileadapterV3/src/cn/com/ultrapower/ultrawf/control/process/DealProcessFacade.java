package cn.com.ultrapower.ultrawf.control.process;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.DealProcess;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
public class DealProcessFacade {
	
	/**
	 * 查询处理Process信息
	 * @param p_Baseschema
	 * @param p_BaseID
	 * @return ProcessModel的List
	 */
	public List GetList(String p_Baseschema,String p_BaseID)
	{
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessBaseSchema(p_Baseschema);
		m_ParDealProcess.setProcessBaseID(p_BaseID);
		return GetList(m_ParDealProcess);
	}
	
	/**
	 * 查询处理Process信息
	 * @param p_Baseschema
	 * @param p_BaseID
	 * @return ProcessModel的List
	 */
	public List GetList(String p_Baseschema,String p_BaseID,int p_isArchive)
	{
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessBaseSchema(p_Baseschema);
		m_ParDealProcess.setProcessBaseID(p_BaseID);
		m_ParDealProcess.setIsArchive(p_isArchive);
		return GetList(m_ParDealProcess);
	}	
	
	/**
	 * 查询处理Process信息
	 * @param p_ParDealProcess
	 * @return ProcessModel的List
	 */
	public List GetList(ParDealProcess p_ParDealProcess)
	{
		DealProcess m_DealProcess=new DealProcess();
		
		return m_DealProcess.GetList(p_ParDealProcess,0,0);
	}
	
	protected void finalize()
	{
		
	}
	
	
	public List getListForBind(ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow)
	{
		DealProcess m_DealProcess=new DealProcess();
		
		return m_DealProcess.getListForBind(p_ParDealProcess,0,0);
	}	
	
	
}
