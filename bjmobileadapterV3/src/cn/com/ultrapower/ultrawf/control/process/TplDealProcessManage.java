package cn.com.ultrapower.ultrawf.control.process;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.TplDealProcessModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class TplDealProcessManage {

	
	public List<TplDealProcessModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("TplDealLink.BaseOn_Condition",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<TplDealProcessModel>	m_List	= new ArrayList<TplDealProcessModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		TplDealProcessModel m_Model 	= (TplDealProcessModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public TplDealProcessModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("TplDealProcess.BaseOn_Condition",p_rDParameter) ;
      	TplDealProcessModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row 	m_Rs			= null;
	  		m_Rs = m_rowSet.get(0);
      		m_Model 	= (TplDealProcessModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public TplDealProcessModel getOneModel(String p_BaseSchema,String p_BaseID,String p_PhaseNo)
	{
      	RDParameter m_rDParameter=new RDParameter();
      	m_rDParameter.addIndirectPar("baseschema",p_BaseSchema,4);
      	m_rDParameter.addIndirectPar("basesid",p_BaseID,4);
      	m_rDParameter.addIndirectPar("phaseno",p_PhaseNo,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private Object setModelValue(Row p_Rs)
	{
  		TplDealProcessModel m_Model 	= new TplDealProcessModel(); 
  		m_Model.setAcceptOverTimeDate(p_Rs.getInt("AcceptOverTimeDate"));
  		m_Model.setAcceptOverTimeDate_Relative(p_Rs.getInt("AcceptOverTimeDate_Relative"));
  		m_Model.setAcceptOverTimeDate_tmp(p_Rs.getInt("AcceptOverTimeDate_tmp"));
  		m_Model.setActionName(p_Rs.getString("ActionName"));
  		m_Model.setActionPageID(p_Rs.getString("ActionPageID"));
  		m_Model.setActionPageName(p_Rs.getString("ActionPageName"));
  		m_Model.setAssignee(p_Rs.getString("Assginee"));
  		m_Model.setAssgineeID(p_Rs.getString("AssgineeID"));
  		m_Model.setAssignOverTimeDate(p_Rs.getInt("AssignOverTimeDate"));
  		m_Model.setAssignOverTimeDate_Relative(p_Rs.getInt("AssignOverTimeDate_Relative"));
  		m_Model.setAssignOverTimeDate_tmp(p_Rs.getInt("AssignOverTimeDate_tmp"));
  		m_Model.setAssigneeCorp(p_Rs.getString("AssigneeCorp"));
  		m_Model.setAssigneeCorpID(p_Rs.getString("AssigneeCorpID"));
  		m_Model.setAssigneeDN(p_Rs.getString("AssigneeDN"));
  		m_Model.setAssigneeDNID(p_Rs.getString("AssigneeDNID"));
  		m_Model.setAssigneeDep(p_Rs.getString("AssigneeDep"));
  		m_Model.setAssigneeDepID(p_Rs.getString("AssigneeDepID"));
  		m_Model.setBaseOpenDateTime(p_Rs.getInt("BaseOpenDateTime"));
  		m_Model.setBaseStateCode(p_Rs.getString("BaseStateCode"));
  		m_Model.setBaseStateName(p_Rs.getString("BaseStateName"));
  		m_Model.setBgDate(p_Rs.getInt("BgDate"));
  		m_Model.setCloseBaseSamenessGroup(p_Rs.getString("CloseBaseSamenessGroup"));
  		m_Model.setCloseBaseSamenessGroupID(p_Rs.getString("CloseBaseSamenessGroupID"));
  		m_Model.setCommissioner(p_Rs.getString("Commissioner"));
  		m_Model.setCommissionerID(p_Rs.getString("CommissionerID"));
  		m_Model.setCreateByUserID(p_Rs.getString("CreateByUserID"));
  		m_Model.setCustomActions(p_Rs.getString("CustomActions"));
  		m_Model.setDealOverTimeDate(p_Rs.getInt("DealOverTimeDate"));
  		m_Model.setDealOverTimeDate_Relative(p_Rs.getInt("DealOverTimeDate_Relative"));
  		m_Model.setDealOverTimeDate_tmp(p_Rs.getInt("DealOverTimeDate_tmp"));
  		m_Model.setDealer(p_Rs.getString("Dealer"));
  		m_Model.setDealerCorp(p_Rs.getString("DealerCorp"));
  		m_Model.setDealerCorpID(p_Rs.getString("DealerCorpID"));
  		m_Model.setDealerDN(p_Rs.getString("DealerDN"));
  		m_Model.setDealerDNID(p_Rs.getString("DealerDNID"));
  		m_Model.setDealerDep(p_Rs.getString("DealerDep"));
  		m_Model.setDealerDepID(p_Rs.getString("DealerDepID"));
  		m_Model.setDealerID(p_Rs.getString("DealerID"));
  		m_Model.setDesc(p_Rs.getString("ProDesc"));
  		m_Model.setEdDate(p_Rs.getInt("EdDate"));
  		m_Model.setEdProcessAction(p_Rs.getInt("EdProcessAction"));
  		m_Model.setFlag01Assign(p_Rs.getInt("Flag01Assign"));
  		m_Model.setFlag02Copy(p_Rs.getInt("Flag02Copy"));
  		m_Model.setFlag03Assist(p_Rs.getInt("Flag03Assist"));
  		m_Model.setFlag04Transfer(p_Rs.getInt("Flag04Transfer"));
  		m_Model.setFlag05TurnDown(p_Rs.getInt("Flag05TurnDown"));
  		m_Model.setFlag06TurnUp(p_Rs.getInt("Flag06TurnUp"));
  		m_Model.setFlag07Recall(p_Rs.getInt("Flag07Recall"));
  		m_Model.setFlag08Cancel(p_Rs.getInt("Flag08Cancel"));
  		m_Model.setFlag09Close(p_Rs.getInt("Flag09Close"));
  		m_Model.setFlag15ToAuditing(p_Rs.getInt("Flag15ToAuditing"));
  		m_Model.setFlag16ToAssistAuditing(p_Rs.getInt("Flag16ToAssistAuditing"));
  		m_Model.setFlag20SideBySide(p_Rs.getInt("Flag20SideBySide"));
  		m_Model.setFlag22IsSelect(p_Rs.getInt("Flag22IsSelect"));
  		m_Model.setFlag30AuditingResult(p_Rs.getInt("Flag30AuditingResult"));
  		m_Model.setFlag31IsTransfer(p_Rs.getInt("Flag31IsTransfer"));
  		m_Model.setFlag32IsToTransfer(p_Rs.getInt("Flag32IsToTransfer"));
  		m_Model.setFlag33IsEndPhase(p_Rs.getInt("Flag33IsEndPhase"));
  		m_Model.setFlag34IsEndDuplicated(p_Rs.getInt("Flag34IsEndDuplicated"));
  		m_Model.setFlag36IsCreateBase(p_Rs.getInt("Flag36IsCreateBase"));
  		m_Model.setFlag35IsCanCreateBase(p_Rs.getInt("Flag35IsCanCreateBase"));
  		m_Model.setFlagActive(p_Rs.getInt("FlagActive"));
  		m_Model.setFlagAssignGroupOrUser(p_Rs.getInt("FlagAssignGroupOrUser"));
  		m_Model.setFlagBegin(p_Rs.getInt("FlagBegin"));
  		m_Model.setFlagDuplicated(p_Rs.getInt("FlagDuplicated"));
  		m_Model.setFlagEnd(p_Rs.getInt("FlagEnd"));
  		m_Model.setFlagGroupSnatch(p_Rs.getInt("FlagGroupSnatch"));
  		m_Model.setFlagPredefined(p_Rs.getInt("FlagPredefined"));
  		m_Model.setFlagStart(p_Rs.getInt("FlagStart"));
  		m_Model.setFlagType(p_Rs.getInt("FlagType"));
  		m_Model.setGroup(p_Rs.getString("ProGroup"));
  		m_Model.setGroupID(p_Rs.getString("GroupID"));
  		m_Model.setIsQualityCheckUp(p_Rs.getInt("IsQualityCheckUp"));
  		m_Model.setPhaseNo(p_Rs.getString("PhaseNo"));
  		m_Model.setPhaseNoTakeMeActive(p_Rs.getString("PhaseNoTakeMeActive"));
  		m_Model.setPosX(p_Rs.getInt("PosX"));
  		m_Model.setPosY(p_Rs.getInt("PosY"));
  		m_Model.setPrevPhaseNo(p_Rs.getString("PrevPhaseNo"));
  		m_Model.setProcessBaseID(p_Rs.getString("ProcessBaseID"));
  		m_Model.setProcessBaseSchema(p_Rs.getString("ProcessBaseSchema"));
  		m_Model.setFlag00IsAvail(p_Rs.getInt("Flag00IsAvail"));
  		m_Model.setProcessGoLine(p_Rs.getString("ProcessGoLine"));
  		m_Model.setProcessID(p_Rs.getString("ProcessID"));
  		m_Model.setProcessStatus(p_Rs.getString("ProcessStatus"));
  		m_Model.setProcessType(p_Rs.getString("ProcessType"));
  		m_Model.setRoleName(p_Rs.getString("RoleName"));
  		m_Model.setRoleOnlyID(p_Rs.getString("RoleOnlyID"));
  		m_Model.setStDate(p_Rs.getInt("StDate"));
  		m_Model.setStProcessAction(p_Rs.getInt("StProcessAction"));
  		m_Model.setTransferPhaseNo(p_Rs.getString("TransferPhaseNo"));		
		return m_Model;
		
	}	
}
