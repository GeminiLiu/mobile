package cn.com.ultrapower.ultrawf.control.process;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.AuditingProcess;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;

public class AuditingProcessFacade {

	
	public List GetList(String p_Baseschema,String p_BaseID)
	{
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessBaseSchema(p_Baseschema);
		m_ParDealProcess.setProcessBaseID(p_BaseID);
		return GetList(m_ParDealProcess,0,0);
	}
	
	public List GetList(String p_Baseschema,String p_BaseID,int p_isArchive)
	{
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessBaseSchema(p_Baseschema);
		m_ParDealProcess.setProcessBaseID(p_BaseID);
		m_ParDealProcess.setIsArchive(p_isArchive);
		return GetList(m_ParDealProcess,0,0);
	}
		
	
	public List GetList(ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		AuditingProcess m_AuditingProcess=new AuditingProcess();
		try{
			
			m_list=m_AuditingProcess.GetList(p_ParDealProcess,0,0);
		
		}catch(Exception ex)
		{
			System.err.println("AuditingProcessFacade.GeAuditingtList:"+ex.getMessage());
			ex.printStackTrace();
		}
		
		return m_list;
	}
}
