package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BasePerformRoleModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class BasePerformRoleManage {
	public List<BasePerformRoleModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BasePerformRole.BaseOn_Condition",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<BasePerformRoleModel>	m_List	= new ArrayList<BasePerformRoleModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		BasePerformRoleModel m_Model 	= (BasePerformRoleModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public BasePerformRoleModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BasePerformRole.BaseOn_Condition",p_rDParameter) ;
      	BasePerformRoleModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row 	m_Rs			= null;
	  		m_Rs = m_rowSet.get(0);
	  		m_Model 	= (BasePerformRoleModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public BasePerformRoleModel getOneModel(String p_BaseSchema,String p_BaseID,String p_PhaseNo)
	{
      	RDParameter m_rDParameter=new RDParameter();
      	m_rDParameter.addIndirectPar("baseschema",p_BaseSchema,4);
      	m_rDParameter.addIndirectPar("basesid",p_BaseID,4);
      	m_rDParameter.addIndirectPar("phaseno",p_PhaseNo,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		BasePerformRoleModel m_Model 	= new BasePerformRoleModel(); 
		m_Model.setRoleDefineID(p_Rs.getString("RoleDefineID"));
		m_Model.setRoleOnlyID(p_Rs.getString("RoleOnlyID"));
		m_Model.setRoleName(p_Rs.getString("RoleName"));
		m_Model.setRoleProcessRoleType(p_Rs.getlong("RoleProcessRoleType"));
		m_Model.setRoleDesc(p_Rs.getString("RoleDesc"));
		m_Model.setRoleBaseID(p_Rs.getString("RoleBaseID"));
		m_Model.setRoleBaseSchema(p_Rs.getString("RoleBaseSchema"));
		m_Model.setRoleProcessPhaseNo(p_Rs.getString("RoleProcessPhaseNo"));
		m_Model.setAssignee(p_Rs.getString("Assignee"));
		m_Model.setAssigneeID(p_Rs.getString("AssigneeID"));
		m_Model.setGroup(p_Rs.getString("ProGroup"));
		m_Model.setGroupID(p_Rs.getString("GroupID"));
		m_Model.setRoleKey(p_Rs.getString("RoleKey"));
		m_Model.setContextAssignee_FieldID(p_Rs.getString("ContextAssignee_FieldID"));
		m_Model.setContextAssigneeID_FieldID(p_Rs.getString("ContextAssigneeID_FieldID"));
		m_Model.setContextGroup_FieldID(p_Rs.getString("ContextGroup_FieldID"));
		m_Model.setContextGroupID_FieldID(p_Rs.getString("ContextGroupID_FieldID"));
		m_Model.setTopRoleMatchConditionsDesc(p_Rs.getString("TopRoleMatchConditionsDesc"));
		return m_Model;
	}
}
