package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BaseInforModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class BaseInforManage {
	public List<BaseInforModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseInfor.BaseOn_BaseIDSelectBase",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<BaseInforModel>	m_List	= new ArrayList<BaseInforModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		BaseInforModel m_Model 	= (BaseInforModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public BaseInforModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("BaseInfor.BaseOn_BaseIDSelectBase",p_rDParameter) ;
      	BaseInforModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row m_Rs	= null;
	  		m_Rs 		= m_rowSet.get(0);
	  		m_Model 	= (BaseInforModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public BaseInforModel getOneModel(String p_BaseSchema,String p_BaseID)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("baseschema",p_BaseSchema,4);
      	m_rDParameter.addIndirectPar("baseid",p_BaseID,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		BaseInforModel m_Model 	= new BaseInforModel(); 
		m_Model.setBaseID(p_Rs.getString("BaseID"));
		m_Model.setBaseSchema(p_Rs.getString("BaseSchema"));
		m_Model.setBaseName(p_Rs.getString("BaseName"));
		m_Model.setBaseSN(p_Rs.getString("BaseSN"));
		m_Model.setBaseCreatorFullName(p_Rs.getString("BaseCreatorFullName"));
		m_Model.setBaseCreatorLoginName(p_Rs.getString("BaseCreatorLoginName"));
		m_Model.setBaseCreateDate(p_Rs.getInt("BaseCreateDate"));
		m_Model.setBaseSendDate(p_Rs.getInt("BaseSendDate"));
		m_Model.setBaseFinishDate(p_Rs.getInt("BaseFinishDate"));
		m_Model.setBaseCloseDate(p_Rs.getInt("BaseCloseDate"));
		m_Model.setBaseStatus(p_Rs.getString("BaseStatus"));
		m_Model.setBaseSummary(p_Rs.getString("BaseSummary"));
		m_Model.setBaseItems(p_Rs.getString("BaseItems"));
		m_Model.setBasePriority(p_Rs.getString("BasePriority"));
		m_Model.setBaseIsAllowLogGroup(p_Rs.getString("BaseIsAllowLogGroup"));
		m_Model.setBaseAcceptOutTime(p_Rs.getInt("BaseAcceptOutTime"));
		m_Model.setBaseDealOutTime(p_Rs.getInt("BaseDealOutTime"));
		m_Model.setBaseDescrption(p_Rs.getString("BaseDescrption"));
		m_Model.setBaseResult(p_Rs.getString("BaseResult"));
		m_Model.setBaseCloseSatisfy(p_Rs.getString("BaseCloseSatisfy"));
		m_Model.setBaseTplID(p_Rs.getString("BaseTplID"));
		m_Model.setBaseIsArchive(p_Rs.getInt("BaseIsArchive"));
		m_Model.setBaseAuditingLinkName(p_Rs.getString("BaseAuditingLinkName"));
		m_Model.setBaseAuditingProcessName(p_Rs.getString("BaseAuditingProcessName"));
		m_Model.setBaseAuditingProcessLogName(p_Rs.getString("BaseAuditingProcessLogName"));
		m_Model.setBaseDealLinkName(p_Rs.getString("BaseDealLinkName"));
		m_Model.setBaseDealProcessName(p_Rs.getString("BaseDealProcessName"));
		m_Model.setBaseDealProcessLogName(p_Rs.getString("BaseDealProcessLogName"));
		m_Model.setBaseIsTrueClose(p_Rs.getInt("BaseIsTrueClose"));
		m_Model.setBaseWorkFlowFlag(p_Rs.getInt("BaseWorkFlowFlag"));
		m_Model.setBaseCategoryClassName(p_Rs.getString("BaseCategoryClassName"));
		m_Model.setBaseCategoryClassCode(p_Rs.getInt("BaseCategoryClassCode"));
		m_Model.setBaseFlagCreated(p_Rs.getInt("BaseFlagCreated"));
		m_Model.setBaseFlagSended(p_Rs.getInt("BaseFlagSended"));
		m_Model.setBaseFlagFinished(p_Rs.getInt("BaseFlagFinished"));
		m_Model.setBaseFlagCloseed(p_Rs.getInt("BaseFlagCloseed"));
		m_Model.setBaseStatusCode(p_Rs.getString("BaseStatusCode"));
		m_Model.setBaseAcceptOutFlag(p_Rs.getInt("BaseAcceptOutFlag"));
		m_Model.setBaseDealOutFlag(p_Rs.getInt("BaseDealOutFlag"));
		m_Model.setBaseDealVerdictName(p_Rs.getString("BaseDealVerdictName"));
		m_Model.setBaseDealAssistantProcessName(p_Rs.getString("BaseDealAssistantProcessName"));
		m_Model.setBaseFieldLogName(p_Rs.getString("BaseFieldLogName"));
		m_Model.setBaseFlagIsMotherCreated(p_Rs.getInt("BaseFlagIsMotherCreated"));
		m_Model.setBaseFlowDrawDesc(p_Rs.getString("BaseFlowDrawDesc"));
		m_Model.setBaseFlagIsCreateChild(p_Rs.getInt("BaseFlagIsCreateChild"));
		m_Model.setBaseOpenDateTime(p_Rs.getInt("BaseOpenDateTime"));
		m_Model.setBaseCreatorConnectWay(p_Rs.getString("BaseCreatorConnectWay"));
		m_Model.setBaseCreatorCorp(p_Rs.getString("BaseCreatorCorp"));
		m_Model.setBaseCreatorCorpID(p_Rs.getString("BaseCreatorCorpID"));
		m_Model.setBaseCreatorDep(p_Rs.getString("BaseCreatorDep"));
		m_Model.setBaseCreatorDepID(p_Rs.getString("BaseCreatorDepID"));
		m_Model.setBaseCreatorDN(p_Rs.getString("BaseCreatorDN"));
		m_Model.setBaseCreatorDNID(p_Rs.getString("BaseCreatorDNID"));
		return m_Model;
	}
}
