package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.DealProcessLog;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLog;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLogModel;

public class ProcessLogFacade implements IProcessLog{
	/**
	 * 根据ProcessID取处理日志列表
	 */
	public List GetDealtList(String p_ProcessID,int p_IsArchive)
	{
		List m_list=new ArrayList();
		DealProcessLog m_DealProcessLog=new DealProcessLog();
		try{
			
			m_list=m_DealProcessLog.GetList(p_ProcessID,0,0,p_IsArchive);
		
		}catch(Exception ex)
		{
			System.err.println("DealProcessLogFacade.GeDealtList:"+ex.getMessage());
			ex.printStackTrace();
		}
		return m_list;
	}	

	public List GetDealtList(String p_Baschema,String p_BaseID,int p_IsArchive)
	{
		List m_list=null;
		DealProcessLog m_DealProcessLog=new DealProcessLog();
		try{
			
			m_list=m_DealProcessLog.GetList(p_Baschema,p_BaseID,p_IsArchive);
		
		}catch(Exception ex)
		{
			System.err.println("DealProcessLogFacade.GetDealtList:"+ex.getMessage());
			ex.printStackTrace();
		}
		return m_list;
	}
	
	/**
	 *  根据ProcessID取审批日志列表	 */
	public List GetAuditingtList(String p_ProcessID,int p_IsArchive)
	{
		List m_list=new ArrayList();
		AuditingProcessLog m_AuditingProcessLog=new AuditingProcessLog();
		try{
			
			m_list=m_AuditingProcessLog.GetList(p_ProcessID,0,0,p_IsArchive);
		
		}catch(Exception ex)
		{
			System.err.println("DealProcessLogFacade.GeAuditingtList:"+ex.getMessage());
			ex.printStackTrace();
		}
		return m_list;		
	}
	/**
	 * 审批信息日志
	 */
	public List GetAuditingtList(String p_Baschema,String p_BaseID,int p_IsArchive)
	{
		List m_list=null;
		AuditingProcessLog m_AuditingProcessLog=new AuditingProcessLog();
		try{
			
			m_list=m_AuditingProcessLog.GetList(p_Baschema,p_BaseID,p_IsArchive);
		
		}catch(Exception ex)
		{
			System.err.println("DealProcessLogFacade.GeAuditingtList:"+ex.getMessage());
			ex.printStackTrace();
		}
		return m_list;
	}	
	
	
	public List getListForBind(ParDealProcessLogModel p_DealProcessLog,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;;
		DealProcessLog m_DealProcessLog=new DealProcessLog();
		try{
			
			m_list=m_DealProcessLog.getListForBind(p_DealProcessLog,0,0);
		}catch(Exception ex)
		{
			System.err.println("DealProcessLogFacade.GeDealtList:"+ex.getMessage());
			ex.printStackTrace();
		}
		return m_list;
	}	
	
}
