package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.AuditingProcessModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class AuditingProcessManage {
	public List<AuditingProcessModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("AuditingProcess.BaseOn_Condition",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<AuditingProcessModel>	m_List	= new ArrayList<AuditingProcessModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		AuditingProcessModel m_Model 	= (AuditingProcessModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public AuditingProcessModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("AuditingProcess.BaseOn_Condition",p_rDParameter) ;
      	AuditingProcessModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row 	m_Rs			= null;
	  		m_Rs = m_rowSet.get(0);
	  		m_Model 	= (AuditingProcessModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public AuditingProcessModel getOneModel(String p_ProcessID,String p_ProcessType)
	{
      	RDParameter m_rDParameter=new RDParameter();
      	m_rDParameter.addIndirectPar("processid",p_ProcessID,4);
      	m_rDParameter.addIndirectPar("processtype",p_ProcessType,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
		AuditingProcessModel m_Model 	= new AuditingProcessModel(); 
//		m_Model.setAcceptOverTimeDate(p_Rs.getlong("AcceptOverTimeDate"));
//		m_Model.setAcceptOverTimeDate_Relative(p_Rs.getlong("AcceptOverTimeDate_Relative"));
//		m_Model.setAcceptOverTimeDate_tmp(p_Rs.getlong("AcceptOverTimeDate_tmp"));
		m_Model.setActionName(p_Rs.getString("ActionName"));
		m_Model.setActionPageID(p_Rs.getString("ActionPageID"));
		m_Model.setActionPageName(p_Rs.getString("ActionPageName"));
		m_Model.setAssginee(p_Rs.getString("Assginee"));
		m_Model.setAssgineeID(p_Rs.getString("AssgineeID"));
//		m_Model.setAssignOverTimeDate(p_Rs.getlong("AssignOverTimeDate"));
//		m_Model.setAssignOverTimeDate_Relative(p_Rs.getlong("AssignOverTimeDate_Relative"));
//		m_Model.setAssignOverTimeDate_tmp(p_Rs.getlong("AssignOverTimeDate_tmp"));
		m_Model.setAssigneeCorp(p_Rs.getString("AssigneeCorp"));
		m_Model.setAssigneeCorpID(p_Rs.getString("AssigneeCorpID"));
		m_Model.setAssigneeDN(p_Rs.getString("AssigneeDN"));
		m_Model.setAssigneeDNID(p_Rs.getString("AssigneeDNID"));
		m_Model.setAssigneeDep(p_Rs.getString("AssigneeDep"));
		m_Model.setAssigneeDepID(p_Rs.getString("AssigneeDepID"));
		m_Model.setBaseOpenDateTime(p_Rs.getlong("BaseOpenDateTime"));
		m_Model.setBaseStateCode(p_Rs.getString("BaseStateCode"));
		m_Model.setBaseStateName(p_Rs.getString("BaseStateName"));
		m_Model.setBgDate(p_Rs.getlong("BgDate"));
		m_Model.setCloseBaseSamenessGroup(p_Rs.getString("CloseBaseSamenessGroup"));
		m_Model.setCloseBaseSamenessGroupID(p_Rs.getString("CloseBaseSamenessGroupID"));
		m_Model.setCommissioner(p_Rs.getString("Commissioner"));
		m_Model.setCommissionerID(p_Rs.getString("CommissionerID"));
		m_Model.setCreateByUserID(p_Rs.getString("CreateByUserID"));
//		m_Model.setCustomActions(p_Rs.getString("CustomActions"));
//		m_Model.setDealOverTimeDate(p_Rs.getlong("DealOverTimeDate"));
//		m_Model.setDealOverTimeDate_Relative(p_Rs.getlong("DealOverTimeDate_Relative"));
//		m_Model.setDealOverTimeDate_tmp(p_Rs.getlong("DealOverTimeDate_tmp"));
		m_Model.setDealer(p_Rs.getString("Dealer"));
		m_Model.setDealerCorp(p_Rs.getString("DealerCorp"));
		m_Model.setDealerCorpID(p_Rs.getString("DealerCorpID"));
		m_Model.setDealerDN(p_Rs.getString("DealerDN"));
		m_Model.setDealerDNID(p_Rs.getString("DealerDNID"));
		m_Model.setDealerDep(p_Rs.getString("DealerDep"));
		m_Model.setDealerDepID(p_Rs.getString("DealerDepID"));
		m_Model.setDealerID(p_Rs.getString("DealerID"));
		m_Model.setDesc(p_Rs.getString("ProDesc"));
		m_Model.setEdDate(p_Rs.getlong("EdDate"));
		m_Model.setEdProcessAction(p_Rs.getString("EdProcessAction"));
		m_Model.setFlag01Assign(p_Rs.getString("Flag01Assign"));
		m_Model.setFlag02Copy(p_Rs.getString("Flag02Copy"));
		m_Model.setFlag03Assist(p_Rs.getString("Flag03Assist"));
		m_Model.setFlag04Transfer(p_Rs.getString("Flag04Transfer"));
//		m_Model.setFlag05TurnDown(p_Rs.getString("Flag05TurnDown"));
//		m_Model.setFlag06TurnUp(p_Rs.getString("Flag06TurnUp"));
//		m_Model.setFlag07Recall(p_Rs.getString("Flag07Recall"));
		m_Model.setFlag08Cancel(p_Rs.getString("Flag08Cancel"));
//		m_Model.setFlag09Close(p_Rs.getString("Flag09Close"));
		m_Model.setFlag15ToAuditing(p_Rs.getString("Flag15ToAuditing"));
		m_Model.setFlag16ToAssistAuditing(p_Rs.getString("Flag16ToAssistAuditing"));
		m_Model.setFlag20SideBySide(p_Rs.getString("Flag20SideBySide"));
		m_Model.setFlag22IsSelect(p_Rs.getString("Flag22IsSelect"));
//		m_Model.setFlag30AuditingResult(p_Rs.getString("Flag30AuditingResult"));
//		m_Model.setFlag31IsTransfer(p_Rs.getString("Flag31IsTransfer"));
//		m_Model.setFlag32IsToTransfer(p_Rs.getString("Flag32IsToTransfer"));
		m_Model.setFlag33IsEndPhase(p_Rs.getString("Flag33IsEndPhase"));
		m_Model.setFlag34IsEndDuplicated(p_Rs.getString("Flag34IsEndDuplicated"));
		m_Model.setFlag36IsCreateBase(p_Rs.getlong("Flag36IsCreateBase"));
		m_Model.setFlag3IsCanCreateBase(p_Rs.getlong("Flag3IsCanCreateBase"));
		m_Model.setFlagActive(p_Rs.getlong("FlagActive"));
		m_Model.setFlagAssignGroupOrUser(p_Rs.getString("FlagAssignGroupOrUser"));
		m_Model.setFlagBegin(p_Rs.getlong("FlagBegin"));
		m_Model.setFlagDuplicated(p_Rs.getlong("FlagDuplicated"));
		m_Model.setFlagEnd(p_Rs.getlong("FlagEnd"));
		m_Model.setIsGroupSnatch(p_Rs.getString("IsGroupSnatch"));
		m_Model.setFlagPredefined(p_Rs.getlong("FlagPredefined"));
		m_Model.setFlagStart(p_Rs.getlong("FlagStart"));
		m_Model.setFlagType(p_Rs.getString("FlagType"));
		m_Model.setGroup(p_Rs.getString("ProGroup"));
		m_Model.setGroupID(p_Rs.getString("GroupID"));
//		m_Model.setIsQualityCheckUp(p_Rs.getString("IsQualityCheckUp"));
		m_Model.setPhaseNo(p_Rs.getString("PhaseNo"));
		m_Model.setPhaseNoTakeMeActive(p_Rs.getString("PhaseNoTakeMeActive"));
		m_Model.setPosX(p_Rs.getlong("PosX"));
		m_Model.setPosY(p_Rs.getlong("PosY"));
		m_Model.setPrevPhaseNo(p_Rs.getString("PrevPhaseNo"));
		m_Model.setProcessBaseID(p_Rs.getString("ProcessBaseID"));
		m_Model.setProcessBaseSchema(p_Rs.getString("ProcessBaseSchema"));
		m_Model.setProcessFlag00IsAvail(p_Rs.getString("ProcessFlag00IsAvail"));
		m_Model.setProcessID(p_Rs.getString("ProcessID"));
		m_Model.setProcessStatus(p_Rs.getString("ProcessStatus"));
		m_Model.setProcessType(p_Rs.getString("ProcessType"));
		m_Model.setRoleName(p_Rs.getString("RoleName"));
		m_Model.setRoleOnlyID(p_Rs.getString("RoleOnlyID"));
		m_Model.setStDate(p_Rs.getlong("StDate"));
		m_Model.setStProcessAction(p_Rs.getString("StProcessAction"));
//		m_Model.setTransferPhaseNo(p_Rs.getString("TransferPhaseNo"));
//		m_Model.setTmp_Config_GroupIsSnatch_IsHave(p_Rs.getString("tmp_Config_GroupIsSnatch_IsHave"));
		
//		m_Model.setFlag37IsNeedStartInsideFlow(p_Rs.getlong("Flag37IsNeedStartInsideFlow"));
//		m_Model.setStartInsideFlowID(p_Rs.getString("StartInsideFlowID"));
//		m_Model.setStartInsideFlowName(p_Rs.getString("StartInsideFlowName"));
//		m_Model.setFlag38StartInsideFlow(p_Rs.getlong("Flag38StartInsideFlow"));
//		m_Model.setStartInsideFlowsCount(p_Rs.getlong("StartInsideFlowsCount"));
//		m_Model.setStartInsideFlowsNoBackCount(p_Rs.getlong("StartInsideFlowsNoBackCount"));
//		m_Model.setStartInsideFlows(p_Rs.getString("StartInsideFlows"));
//		m_Model.setBaseFlowID(p_Rs.getString("BaseFlowID"));
		m_Model.setAuditingWayIsActive(p_Rs.getString("AuditingWayIsActive"));
		m_Model.setAuditingWayNo(p_Rs.getString("AuditingWayNo"));
		m_Model.setAuditingWayPhaseNo(p_Rs.getString("AuditingWayPhaseNo"));

		return m_Model;
	}
}
