package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BaseOwnFieldInfo;
import cn.com.ultrapower.ultrawf.models.process.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class BaseOwnFieldInfoManage {
	public List<BaseOwnFieldInfoModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseOwnFieldInfo.BaseOn_BaseSchemaSelectBase",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<BaseOwnFieldInfoModel>	m_List	= new ArrayList<BaseOwnFieldInfoModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		BaseOwnFieldInfoModel m_Model 	= (BaseOwnFieldInfoModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public BaseOwnFieldInfoModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseOwnFieldInfo.BaseOn_BaseSchemaSelectBase",p_rDParameter) ;
      	BaseOwnFieldInfoModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row m_Rs	= null;
	  		m_Rs 		= m_rowSet.get(0);
	  		m_Model 	= (BaseOwnFieldInfoModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public BaseOwnFieldInfoModel getOneModel(String p_BaseSchema)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("baseschema",p_BaseSchema,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		BaseOwnFieldInfoModel m_Model 	= new BaseOwnFieldInfoModel(); 

		m_Model.setBaseCategoryName(p_Rs.getString("BaseCategoryName"));
		m_Model.setBaseCategorySchama(p_Rs.getString("BaseCategorySchama"));
		m_Model.setBaseFix_field_EditPhase(p_Rs.getString("BaseFix_field_EditPhase"));
		m_Model.setBaseFix_field_RequiredPhase(p_Rs.getString("BaseFix_field_RequiredPhase"));
		m_Model.setBaseFree_field_EditStep(p_Rs.getString("BaseFree_field_EditStep"));
		m_Model.setBaseFree_field_ShowStep(p_Rs.getString("BaseFree_field_ShowStep"));
		m_Model.setBaseOwnFieldInfoDesc(p_Rs.getString("BaseOwnFieldInfoDesc"));
		m_Model.setBaseOwnFieldInfoID(p_Rs.getString("BaseOwnFieldInfoID"));
		m_Model.setBase_field_DBName(p_Rs.getString("Base_field_DBName"));
		m_Model.setBase_field_ID(p_Rs.getString("Base_field_ID"));
		m_Model.setBase_field_IsRequired(p_Rs.getlong("Base_field_IsRequired"));
		m_Model.setBase_field_Purpose_FlowControl(p_Rs.getString("Base_field_Purpose_FlowControl"));
		m_Model.setBase_field_Purpose_Print(p_Rs.getString("Base_field_Purpose_Print"));
		m_Model.setBase_field_ShowName(p_Rs.getString("Base_field_ShowName"));
		m_Model.setBase_field_Type(p_Rs.getString("Base_field_Type"));
		m_Model.setBase_field_TypeValue(p_Rs.getString("Base_field_TypeValue"));
		m_Model.setEntryMode(p_Rs.getlong("EntryMode"));
		m_Model.setLogIsWrite(p_Rs.getlong("LogIsWrite"));
		m_Model.setPrintOneLine(p_Rs.getlong("PrintOneLine"));
		m_Model.setPrintOrder(p_Rs.getlong("PrintOrder"));
		m_Model.setBase_field_Is_AllPrint(p_Rs.getlong("Base_field_Is_AllPrint"));
		m_Model.setBase_field_WriteAction(p_Rs.getString("Base_field_WriteAction"));
		m_Model.setVarcharFieldeIsExceed(p_Rs.getlong("VarcharFieldeIsExceed"));
		m_Model.setDefVal(p_Rs.getString("Base_field_DefaultValue"));
				
		return m_Model;
	}
	
	public void insretOwnField(List<BaseOwnFieldInfoModel> ownFieldList){
		
		BaseOwnFieldInfo m_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		m_BaseOwnFieldInfo.InsertList(RemedyOp, ownFieldList);
		RemedyOp.RemedyLogout();
	}
}
