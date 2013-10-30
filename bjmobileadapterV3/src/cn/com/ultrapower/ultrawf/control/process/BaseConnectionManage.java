package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BaseConnectionModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class BaseConnectionManage {
	public List<BaseConnectionModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseConnection.BaseOn_OneProcessSelectBaseConnection",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<BaseConnectionModel>	m_List	= new ArrayList<BaseConnectionModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		BaseConnectionModel m_Model 	= (BaseConnectionModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public BaseConnectionModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseConnection.BaseOn_OneProcessSelectBaseConnection",p_rDParameter) ;
      	BaseConnectionModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row m_Rs	= null;
	  		m_Rs 		= m_rowSet.get(0);
	  		m_Model 	= (BaseConnectionModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public BaseConnectionModel getOneModel(String p_ChildBaseSchema,String p_ChildBaseID)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("childbaseschema",p_ChildBaseSchema,4);
      	m_rDParameter.addIndirectPar("childbaseid",p_ChildBaseID,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		BaseConnectionModel m_Model 	= new BaseConnectionModel(); 
		m_Model.setBaseID(p_Rs.getString("BaseID"));
		m_Model.setBaseName(p_Rs.getString("BaseName"));
		m_Model.setBaseSchema(p_Rs.getString("BaseSchema"));
		m_Model.setBaseProcessID(p_Rs.getString("BaseProcessID"));
		m_Model.setBaseProcessType(p_Rs.getString("BaseProcessType"));
		m_Model.setBaseProcessPhaseNo(p_Rs.getString("BaseProcessPhaseNo"));
		m_Model.setBaseProcessLogID(p_Rs.getString("BaseProcessLogID"));
		m_Model.setCreateBaseAftermathType(p_Rs.getlong("CreateBaseAftermathType"));

		m_Model.setChildBaseID(p_Rs.getString("ChildBaseID"));
		m_Model.setChildBaseName(p_Rs.getString("ChildBaseName"));
		m_Model.setChildBaseSchema(p_Rs.getString("ChildBaseSchema"));
		m_Model.setChildBaseSN(p_Rs.getString("ChildBaseSN"));
		m_Model.setChildBaseState(p_Rs.getString("ChildBaseState"));
		m_Model.setChildBaseCreateDate(p_Rs.getlong("ChildBaseCreateDate"));
		m_Model.setChildBaseSummary(p_Rs.getString("ChildBaseSummary"));

		return m_Model;
	}
}
