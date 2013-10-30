package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BaseAttachmentModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class BaseAttachmentManage {
	public List<BaseAttachmentModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseAttachment.BaseOn_OneProcessSelect",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<BaseAttachmentModel>	m_List	= new ArrayList<BaseAttachmentModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		BaseAttachmentModel m_Model 	= (BaseAttachmentModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public BaseAttachmentModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseAttachment.BaseOn_OneProcessSelect",p_rDParameter) ;
      	BaseAttachmentModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row m_Rs	= null;
	  		m_Rs 		= m_rowSet.get(0);
	  		m_Model 	= (BaseAttachmentModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public BaseAttachmentModel getOneModel(String p_AttachmentID)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("attachmentid",p_AttachmentID,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		BaseAttachmentModel m_Model 	= new BaseAttachmentModel(); 
		m_Model.setAttachmentID(p_Rs.getString("attachmentID"));
		m_Model.setBaseID(p_Rs.getString("BaseID"));
		m_Model.setBaseSchema(p_Rs.getString("BaseSchema"));
		m_Model.setPhaseNo(p_Rs.getString("PhaseNo"));
		m_Model.setFlagActive(p_Rs.getlong("FlagActive"));
		m_Model.setUpLoadUser(p_Rs.getString("upLoadUser"));
		m_Model.setUpLoadUserID(p_Rs.getString("upLoadUserID"));
		m_Model.setUpLoadTimeDate(p_Rs.getlong("upLoadTimeDate"));
		m_Model.setUpLoadFileName(p_Rs.getString("upLoadFileName"));
		m_Model.setUpLoadFileDesc(p_Rs.getString("upLoadFileDesc"));
		m_Model.setProcessID(p_Rs.getString("ProcessID"));
		m_Model.setProcessType(p_Rs.getString("ProcessType"));
		m_Model.setProcessLogID(p_Rs.getString("ProcessLogID"));
		m_Model.setViewAttAchID(p_Rs.getString("ViewAttAchID"));


		return m_Model;
	}
	
	public String baseAttachmentPush(
			String p_BaseID,
			String p_BaseSchema,
			String p_ProcessID,
			String p_ProcessType,
			String p_PhaseNo,
			String p_ProcessLogID,
			String p_FlagActive,
			String p_upLoadUser,
			String p_upLoadUserID,
			String p_upLoadTimeDate,
			String p_upLoadFileName,
			String p_upLoadFileDesc,
			String p_upLoadFileContent,
			String p_ViewAttAchID)
	{

		try{				
			List<RemedyFieldInfo> m_List = new ArrayList<RemedyFieldInfo>();
			m_List.add(new RemedyFieldInfo("650000001",p_BaseID,4));//BaseID
			m_List.add(new RemedyFieldInfo("650000002",p_BaseSchema,4));//BaseSchema
			m_List.add(new RemedyFieldInfo("650000011",p_ProcessID,4));//ProcessID
			m_List.add(new RemedyFieldInfo("650000012",p_ProcessType,4));//ProcessType
			m_List.add(new RemedyFieldInfo("650000003",p_PhaseNo,4));//PhaseNo
			m_List.add(new RemedyFieldInfo("650000013",p_ProcessLogID,4));//ProcessLogID
			m_List.add(new RemedyFieldInfo("650000004",p_FlagActive,2));//FlagActive
			m_List.add(new RemedyFieldInfo("650000005",p_upLoadUser,4));//upLoadUser
			m_List.add(new RemedyFieldInfo("650000006",p_upLoadUserID,4));//upLoadUserID
			m_List.add(new RemedyFieldInfo("650000007",p_upLoadTimeDate,7));//upLoadTimeDate
			m_List.add(new RemedyFieldInfo("650000008",p_upLoadFileName,4));//upLoadFileName
			m_List.add(new RemedyFieldInfo("650000009",p_upLoadFileDesc,4));//upLoadFileDesc
			m_List.add(new RemedyFieldInfo("650000010",p_upLoadFileContent,11));//upLoadFileContent
			m_List.add(new RemedyFieldInfo("650000014",p_ViewAttAchID,4));//ViewAttAchID
			


			RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
					Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
					Constants.REMEDY_DEMOPASSWORD);
			RemedyOp.RemedyLogin();
			String strReturnID=RemedyOp.FormDataInsertReturnID(Constants.TblBaseAttachment,m_List);
			RemedyOp.RemedyLogout();

			m_List.clear();
			m_List = null;

			return strReturnID;
		}
		catch(Exception ex)
		{	
			return null;
		}
	}	
}
