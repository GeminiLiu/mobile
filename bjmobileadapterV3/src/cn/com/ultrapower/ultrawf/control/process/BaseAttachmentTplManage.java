package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BaseAttachmentTplModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class BaseAttachmentTplManage {
	public List<BaseAttachmentTplModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseAttachmentTpl.BaseOn_OneProcessSelect",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<BaseAttachmentTplModel>	m_List	= new ArrayList<BaseAttachmentTplModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		BaseAttachmentTplModel m_Model 	= (BaseAttachmentTplModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public BaseAttachmentTplModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseAttachmentTpl.BaseOn_OneProcessSelect",p_rDParameter) ;
      	BaseAttachmentTplModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row m_Rs	= null;
	  		m_Rs 		= m_rowSet.get(0);
	  		m_Model 	= (BaseAttachmentTplModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public BaseAttachmentTplModel getOneModel(String p_AttachmentTplID)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("attachmenttplid",p_AttachmentTplID,4);
      	return getOneModel(m_rDParameter);      	
	}
	public BaseAttachmentTplModel getOneModel(String p_baseschema,String p_FileIdentifier)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("baseschema",p_baseschema,4);
      	m_rDParameter.addIndirectPar("fileidentifier",p_FileIdentifier,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		BaseAttachmentTplModel m_Model 	= new BaseAttachmentTplModel(); 
		m_Model.setAttachmentTplID(p_Rs.getString("attachmentTplID"));
		m_Model.setBaseName(p_Rs.getString("BaseName"));
		m_Model.setBaseSchema(p_Rs.getString("BaseSchema"));
		m_Model.setFileIdentifier(p_Rs.getString("FileIdentifier"));
		m_Model.setFileName(p_Rs.getString("FileName"));
		m_Model.setFileDesc(p_Rs.getString("FileDesc"));
		
		return m_Model;
	}
	

}
