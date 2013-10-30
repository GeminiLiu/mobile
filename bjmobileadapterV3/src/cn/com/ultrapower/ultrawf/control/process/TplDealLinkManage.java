package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.TplDealLinkModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class TplDealLinkManage {
	public List<TplDealLinkModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("TplDealLink.BaseOn_Condition",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<TplDealLinkModel>	m_List	= new ArrayList<TplDealLinkModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		TplDealLinkModel m_Model 	= (TplDealLinkModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public TplDealLinkModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("TplDealLink.BaseOn_Condition",p_rDParameter) ;
      	TplDealLinkModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row 	m_Rs			= null;
	  		m_Rs = m_rowSet.get(0);
	  		m_Model 	= (TplDealLinkModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public TplDealLinkModel getOneModel(String p_BaseSchema,String p_BaseID,String p_StartPhase)
	{
      	RDParameter m_rDParameter=new RDParameter();
      	m_rDParameter.addIndirectPar("baseschema",p_BaseSchema,4);
      	m_rDParameter.addIndirectPar("basesid",p_BaseID,4);
      	m_rDParameter.addIndirectPar("startphase",p_StartPhase,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		TplDealLinkModel m_Model 	= new TplDealLinkModel(); 
		m_Model.setLinkID(p_Rs.getString("LinkID"));
		m_Model.setDesc(p_Rs.getString("LinkDesc"));
		m_Model.setEndPhaseNo(p_Rs.getString("EndPhase"));
		m_Model.setEndPoint(p_Rs.getInt("EndPoint"));
		m_Model.setEndPort(p_Rs.getInt("EndPort"));
		m_Model.setLinkFlag00IsAvail(p_Rs.getInt("Flag00IsAvail"));
		m_Model.setFlag21Required(p_Rs.getInt("Flag21Required"));
		m_Model.setLinkBaseID(p_Rs.getString("LinkBaseID"));
		m_Model.setLinkBaseSchema(p_Rs.getString("LinkBaseSchema"));
		m_Model.setLinkFlagDuplicated(p_Rs.getInt("LinkFlagDuplicated"));
		m_Model.setLinkGoLine(p_Rs.getString("LinkGoLine"));
		m_Model.setLinkNum(p_Rs.getInt("LinkNum"));
		m_Model.setLinkPhaseNo(p_Rs.getString("LinkPhaseNo"));
		m_Model.setLinkType(p_Rs.getString("LinkType"));
		m_Model.setLinkVerdictResult(p_Rs.getInt("LinkVerdictResult"));
		m_Model.setStartPhaseNo(p_Rs.getString("StartPhase"));
		m_Model.setStartPoint(p_Rs.getInt("StartPoint"));
		m_Model.setStartPort(p_Rs.getInt("StartPort"));
		return m_Model;
	}
}
