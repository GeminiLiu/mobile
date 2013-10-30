package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.ProcessLogModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class ProcessLogManage {
	private String 	m_ProcessType;
	private String 	m_SelectXmlName;
	
	public ProcessLogManage(String p_ProcessType)
	{
		setM_ProcessType(p_ProcessType);
	}
	
	public String getM_ProcessType() {
		return m_ProcessType;
	}

	public void setM_ProcessType(String processType) {
		m_ProcessType = processType;
		if (m_ProcessType.equals("DEAL"))
		{
			m_SelectXmlName = "BaseProcessLog.BaseOn_OneDealProcessSelect";
		}
		else
		{
			m_SelectXmlName = "BaseProcessLog.BaseOn_OneAuditingProcessSelect";
		}		
	}

	public List<ProcessLogModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML(m_SelectXmlName,p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<ProcessLogModel>	m_List	= new ArrayList<ProcessLogModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		ProcessLogModel m_Model 	= (ProcessLogModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public ProcessLogModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
		RowSet 	m_rowSet			= OpDB.getDataSetFromXML(m_SelectXmlName,p_rDParameter) ;
      	ProcessLogModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row m_Rs	= null;
	  		m_Rs 		= m_rowSet.get(0);
	  		m_Model 	= (ProcessLogModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public ProcessLogModel getOneModel(String p_ProcessLogID)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("processid",p_ProcessLogID,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		ProcessLogModel m_Model 	= new ProcessLogModel(); 
		m_Model.setLogUserCorp(p_Rs.getString("logUserCorp"));
		m_Model.setLogUserCorpID(p_Rs.getString("logUserCorpID"));
		m_Model.setLogUserDep(p_Rs.getString("logUserDep"));
		m_Model.setLogUserDepID(p_Rs.getString("logUserDepID"));
		m_Model.setLogUserDN(p_Rs.getString("logUserDN"));
		m_Model.setLogUserDNID(p_Rs.getString("logUserDNID"));
		m_Model.setProcessID(p_Rs.getString("ProcessID"));
		m_Model.setAct(p_Rs.getString("Act"));
		m_Model.setLogUser(p_Rs.getString("logUser"));
		m_Model.setLogUserID(p_Rs.getString("logUserID"));
		m_Model.setStDate(p_Rs.getlong("StDate"));
		m_Model.setResult(p_Rs.getString("Result"));
		m_Model.setProcessLogBaseID(p_Rs.getString("ProcessLogBaseID"));
		m_Model.setProcessLogBaseSchema(p_Rs.getString("ProcessLogBaseSchema"));
		m_Model.setFlagStart(p_Rs.getlong("FlagStart"));



		return m_Model;
	}
}
