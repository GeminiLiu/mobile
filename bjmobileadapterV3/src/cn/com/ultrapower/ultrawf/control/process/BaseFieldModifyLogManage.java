package cn.com.ultrapower.ultrawf.control.process;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.process.BaseFieldModifyLog;
import cn.com.ultrapower.ultrawf.models.process.BaseFieldModifyLogModel;
import cn.com.ultrapower.ultrawf.models.process.ParBaseFieldModifyLogModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class BaseFieldModifyLogManage {

	public BaseFieldModifyLogModel getOneForKey(String strField_ModifyLogID ,int p_IsArchive)
	{
		BaseFieldModifyLog m_BaseFieldModifyLog=new BaseFieldModifyLog();
		return m_BaseFieldModifyLog.getOneForKey(strField_ModifyLogID,p_IsArchive);
	}
	/**
	 * 
	 * @param p_ParBaseFieldModifyLogModel
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List getList(ParBaseFieldModifyLogModel p_ParBaseFieldModifyLogModel,int p_PageNumber, int p_StepRow)
	{
		List m_list=null;
		BaseFieldModifyLog m_BaseFieldModifyLog=new BaseFieldModifyLog();
		m_list=m_BaseFieldModifyLog.getList(p_ParBaseFieldModifyLogModel,p_PageNumber,p_StepRow);
		return m_list;
	}
	public Hashtable<String,BaseFieldModifyLogModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseFieldModifyLog.BaseOn_OneProcessSelect",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	Hashtable<String,BaseFieldModifyLogModel> m_Hashtable = new Hashtable<String,BaseFieldModifyLogModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		BaseFieldModifyLogModel m_Model 	= (BaseFieldModifyLogModel) setModelValue(m_Rs);
      		m_Hashtable.put(m_Model.getBase_Field_ModifyLog_FieldDBName(),m_Model);
		}    
		return m_Hashtable;
	}

	public BaseFieldModifyLogModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseFieldModifyLog.BaseOn_OneProcessSelect",p_rDParameter) ;
      	BaseFieldModifyLogModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row m_Rs	= null;
	  		m_Rs 		= m_rowSet.get(0);
	  		m_Model 	= (BaseFieldModifyLogModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public BaseFieldModifyLogModel getOneModel(String p_basefieldmodifylogid)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("basefieldmodifylogid",p_basefieldmodifylogid,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		BaseFieldModifyLogModel m_Model 	= new BaseFieldModifyLogModel(); 
		m_Model.setBase_Field_ModifyLog_ID(p_Rs.getString("ModifyLog_ID"));
		m_Model.setBase_Field_ModifyLog_BaseID(p_Rs.getString("ModifyLog_BaseID"));
		m_Model.setBase_Field_ModifyLog_BaseSchema(p_Rs.getString("ModifyLog_BaseSchema"));
		m_Model.setBase_Field_ModifyLog_PhaseNo(p_Rs.getString("ModifyLog_PhaseNo"));
		m_Model.setBase_Field_ModifyLog_field_ID(p_Rs.getString("ModifyLog_field_ID"));
		m_Model.setBase_Field_ModifyLog_Date(p_Rs.getlong("ModifyLog_Date"));
		m_Model.setBase_Field_ModifyLog_FieldDBName(p_Rs.getString("ModifyLog_FieldDBName"));
		m_Model.setBase_Field_ModifyLog_DealerID(p_Rs.getString("ModifyLog_DealerID"));
		m_Model.setBase_Field_ModifyLog_Dealer(p_Rs.getString("ModifyLog_Dealer"));
		m_Model.setBase_Field_ModifyLog_OldValue(p_Rs.getString("ModifyLog_OldValue"));
		m_Model.setBase_Field_ModifyLog_NewValue(p_Rs.getString("ModifyLog_NewValue"));
		m_Model.setBase_Field_ModifyLog_ProcessType(p_Rs.getString("ModifyLog_ProcessType"));
		m_Model.setBase_Field_ModifyLog_Base_field_ShowName(p_Rs.getString("ModifyLog_Base_field_ShowName"));
		m_Model.setBase_Field_ModifyLog_ProcessID(p_Rs.getString("ModifyLog_ProcessID"));
		m_Model.setBase_Field_ModifyLog_ProcessLogID(p_Rs.getString("ModifyLog_ProcessLogID"));
		m_Model.setBase_Field_ModifyLog_Base_field_Type(p_Rs.getlong("ModifyLog_Base_field_Type"));
		m_Model.setBase_Field_ModifyLog_Base_field_TypeValue(p_Rs.getString("ModifyLog_Base_field_TypeValue"));
		return m_Model;
	}
	
}
