package cn.com.ultrapower.ultrawf.models.process;

public class AuditingProcessModel implements Cloneable
{
	private String ActionName;
	private String ActionPageID;
	private String ActionPageName;
	private String Assginee;
	private String AssgineeID;
	private String AssigneeCorp;
	private String AssigneeCorpID;
	private String AssigneeDN;
	private String AssigneeDNID;
	private String AssigneeDep;
	private String AssigneeDepID;
	private String AssistantAuditingPhaseNo;
	private long AuditingOverTimeDate;
	private long AuditingOverTimeDate_Relative;
	private String AuditingWayIsActive;
	private String AuditingWayNo;
	private String AuditingWayPhaseNo;
	private long BaseOpenDateTime;
	private String BaseStateCode;
	private String BaseStateName;
	private long BgDate;
	private String CloseBaseSamenessGroup;
	private String CloseBaseSamenessGroupID;
	private String Commissioner;
	private String CommissionerID;
	private String CreateByUserID;
	private String Dealer;
	private String DealerCorp;
	private String DealerCorpID;
	private String DealerDN;
	private String DealerDNID;
	private String DealerDep;
	private String DealerDepID;
	private String DealerID;
	private String Desc;
	private long EdDate;
	private String EdProcessAction;
	private String Flag01Assign;
	private String Flag02Copy;
	private String Flag03Assist;
	private String Flag04Transfer;
	private String Flag08Cancel;
	private String Flag15ToAuditing;
	private String Flag16ToAssistAuditing;
	private String Flag16TurnUpAuditing;
	private String Flag17RecallAuditing;
	private String Flag20SideBySide;
	private String Flag22IsSelect;
	private String Flag33IsEndPhase;
	private String Flag34IsEndDuplicated;
	private long Flag36IsCreateBase;
	private long Flag3IsCanCreateBase;
	private long FlagActive;
	private String FlagAssignGroupOrUser;
	private long FlagBegin;
	private long FlagDuplicated;
	private long FlagEnd;
	private long FlagPredefined;
	private long FlagStart;
	private String FlagType;
	private String Group;
	private String GroupID;
	private String IsGroupSnatch;
	private String PhaseNo;
	private String PhaseNoTakeMeActive;
	private long PosX;
	private long PosY;
	private String PrevPhaseNo;
	private String ProcessBaseID;
	private String ProcessBaseSchema;
	private String ProcessFlag00IsAvail;
	private String ProcessID;
	private String ProcessStatus;
	private String ProcessType;
	private String RoleName;
	private String RoleOnlyID;
	private long StDate;
	private String StProcessAction;

	public String getActionName() {
		return ActionName;
	}
	public void setActionName(String actionName) {
		ActionName = actionName;
	}
	public String getActionPageID() {
		return ActionPageID;
	}
	public void setActionPageID(String actionPageID) {
		ActionPageID = actionPageID;
	}
	public String getActionPageName() {
		return ActionPageName;
	}
	public void setActionPageName(String actionPageName) {
		ActionPageName = actionPageName;
	}
	public String getAssginee() {
		return Assginee;
	}
	public void setAssginee(String assginee) {
		Assginee = assginee;
	}
	public String getAssgineeID() {
		return AssgineeID;
	}
	public void setAssgineeID(String assgineeID) {
		AssgineeID = assgineeID;
	}
	public String getAssigneeCorp() {
		return AssigneeCorp;
	}
	public void setAssigneeCorp(String assigneeCorp) {
		AssigneeCorp = assigneeCorp;
	}
	public String getAssigneeCorpID() {
		return AssigneeCorpID;
	}
	public void setAssigneeCorpID(String assigneeCorpID) {
		AssigneeCorpID = assigneeCorpID;
	}
	public String getAssigneeDN() {
		return AssigneeDN;
	}
	public void setAssigneeDN(String assigneeDN) {
		AssigneeDN = assigneeDN;
	}
	public String getAssigneeDNID() {
		return AssigneeDNID;
	}
	public void setAssigneeDNID(String assigneeDNID) {
		AssigneeDNID = assigneeDNID;
	}
	public String getAssigneeDep() {
		return AssigneeDep;
	}
	public void setAssigneeDep(String assigneeDep) {
		AssigneeDep = assigneeDep;
	}
	public String getAssigneeDepID() {
		return AssigneeDepID;
	}
	public void setAssigneeDepID(String assigneeDepID) {
		AssigneeDepID = assigneeDepID;
	}
	public String getAssistantAuditingPhaseNo() {
		return AssistantAuditingPhaseNo;
	}
	public void setAssistantAuditingPhaseNo(String assistantAuditingPhaseNo) {
		AssistantAuditingPhaseNo = assistantAuditingPhaseNo;
	}
	public long getAuditingOverTimeDate() {
		return AuditingOverTimeDate;
	}
	public void setAuditingOverTimeDate(long auditingOverTimeDate) {
		AuditingOverTimeDate = auditingOverTimeDate;
	}
	public long getAuditingOverTimeDate_Relative() {
		return AuditingOverTimeDate_Relative;
	}
	public void setAuditingOverTimeDate_Relative(long auditingOverTimeDate_Relative) {
		AuditingOverTimeDate_Relative = auditingOverTimeDate_Relative;
	}
	public String getAuditingWayIsActive() {
		return AuditingWayIsActive;
	}
	public void setAuditingWayIsActive(String auditingWayIsActive) {
		AuditingWayIsActive = auditingWayIsActive;
	}
	public String getAuditingWayNo() {
		return AuditingWayNo;
	}
	public void setAuditingWayNo(String auditingWayNo) {
		AuditingWayNo = auditingWayNo;
	}
	public String getAuditingWayPhaseNo() {
		return AuditingWayPhaseNo;
	}
	public void setAuditingWayPhaseNo(String auditingWayPhaseNo) {
		AuditingWayPhaseNo = auditingWayPhaseNo;
	}
	public long getBaseOpenDateTime() {
		return BaseOpenDateTime;
	}
	public void setBaseOpenDateTime(long baseOpenDateTime) {
		BaseOpenDateTime = baseOpenDateTime;
	}
	public String getBaseStateCode() {
		return BaseStateCode;
	}
	public void setBaseStateCode(String baseStateCode) {
		BaseStateCode = baseStateCode;
	}
	public String getBaseStateName() {
		return BaseStateName;
	}
	public void setBaseStateName(String baseStateName) {
		BaseStateName = baseStateName;
	}
	public long getBgDate() {
		return BgDate;
	}
	public void setBgDate(long bgDate) {
		BgDate = bgDate;
	}
	public String getCloseBaseSamenessGroup() {
		return CloseBaseSamenessGroup;
	}
	public void setCloseBaseSamenessGroup(String closeBaseSamenessGroup) {
		CloseBaseSamenessGroup = closeBaseSamenessGroup;
	}
	public String getCloseBaseSamenessGroupID() {
		return CloseBaseSamenessGroupID;
	}
	public void setCloseBaseSamenessGroupID(String closeBaseSamenessGroupID) {
		CloseBaseSamenessGroupID = closeBaseSamenessGroupID;
	}
	public String getCommissioner() {
		return Commissioner;
	}
	public void setCommissioner(String commissioner) {
		Commissioner = commissioner;
	}
	public String getCommissionerID() {
		return CommissionerID;
	}
	public void setCommissionerID(String commissionerID) {
		CommissionerID = commissionerID;
	}
	public String getCreateByUserID() {
		return CreateByUserID;
	}
	public void setCreateByUserID(String createByUserID) {
		CreateByUserID = createByUserID;
	}
	public String getDealer() {
		return Dealer;
	}
	public void setDealer(String dealer) {
		Dealer = dealer;
	}
	public String getDealerCorp() {
		return DealerCorp;
	}
	public void setDealerCorp(String dealerCorp) {
		DealerCorp = dealerCorp;
	}
	public String getDealerCorpID() {
		return DealerCorpID;
	}
	public void setDealerCorpID(String dealerCorpID) {
		DealerCorpID = dealerCorpID;
	}
	public String getDealerDN() {
		return DealerDN;
	}
	public void setDealerDN(String dealerDN) {
		DealerDN = dealerDN;
	}
	public String getDealerDNID() {
		return DealerDNID;
	}
	public void setDealerDNID(String dealerDNID) {
		DealerDNID = dealerDNID;
	}
	public String getDealerDep() {
		return DealerDep;
	}
	public void setDealerDep(String dealerDep) {
		DealerDep = dealerDep;
	}
	public String getDealerDepID() {
		return DealerDepID;
	}
	public void setDealerDepID(String dealerDepID) {
		DealerDepID = dealerDepID;
	}
	public String getDealerID() {
		return DealerID;
	}
	public void setDealerID(String dealerID) {
		DealerID = dealerID;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public long getEdDate() {
		return EdDate;
	}
	public void setEdDate(long edDate) {
		EdDate = edDate;
	}
	public String getEdProcessAction() {
		return EdProcessAction;
	}
	public void setEdProcessAction(String edProcessAction) {
		EdProcessAction = edProcessAction;
	}
	public String getFlag01Assign() {
		return Flag01Assign;
	}
	public void setFlag01Assign(String flag01Assign) {
		Flag01Assign = flag01Assign;
	}
	public String getFlag02Copy() {
		return Flag02Copy;
	}
	public void setFlag02Copy(String flag02Copy) {
		Flag02Copy = flag02Copy;
	}
	public String getFlag03Assist() {
		return Flag03Assist;
	}
	public void setFlag03Assist(String flag03Assist) {
		Flag03Assist = flag03Assist;
	}
	public String getFlag04Transfer() {
		return Flag04Transfer;
	}
	public void setFlag04Transfer(String flag04Transfer) {
		Flag04Transfer = flag04Transfer;
	}
	public String getFlag08Cancel() {
		return Flag08Cancel;
	}
	public void setFlag08Cancel(String flag08Cancel) {
		Flag08Cancel = flag08Cancel;
	}
	public String getFlag15ToAuditing() {
		return Flag15ToAuditing;
	}
	public void setFlag15ToAuditing(String flag15ToAuditing) {
		Flag15ToAuditing = flag15ToAuditing;
	}
	public String getFlag16ToAssistAuditing() {
		return Flag16ToAssistAuditing;
	}
	public void setFlag16ToAssistAuditing(String flag16ToAssistAuditing) {
		Flag16ToAssistAuditing = flag16ToAssistAuditing;
	}
	public String getFlag16TurnUpAuditing() {
		return Flag16TurnUpAuditing;
	}
	public void setFlag16TurnUpAuditing(String flag16TurnUpAuditing) {
		Flag16TurnUpAuditing = flag16TurnUpAuditing;
	}
	public String getFlag17RecallAuditing() {
		return Flag17RecallAuditing;
	}
	public void setFlag17RecallAuditing(String flag17RecallAuditing) {
		Flag17RecallAuditing = flag17RecallAuditing;
	}
	public String getFlag20SideBySide() {
		return Flag20SideBySide;
	}
	public void setFlag20SideBySide(String flag20SideBySide) {
		Flag20SideBySide = flag20SideBySide;
	}
	public String getFlag22IsSelect() {
		return Flag22IsSelect;
	}
	public void setFlag22IsSelect(String flag22IsSelect) {
		Flag22IsSelect = flag22IsSelect;
	}
	public String getFlag33IsEndPhase() {
		return Flag33IsEndPhase;
	}
	public void setFlag33IsEndPhase(String flag33IsEndPhase) {
		Flag33IsEndPhase = flag33IsEndPhase;
	}
	public String getFlag34IsEndDuplicated() {
		return Flag34IsEndDuplicated;
	}
	public void setFlag34IsEndDuplicated(String flag34IsEndDuplicated) {
		Flag34IsEndDuplicated = flag34IsEndDuplicated;
	}
	public long getFlag36IsCreateBase() {
		return Flag36IsCreateBase;
	}
	public void setFlag36IsCreateBase(long flag36IsCreateBase) {
		Flag36IsCreateBase = flag36IsCreateBase;
	}
	public long getFlag3IsCanCreateBase() {
		return Flag3IsCanCreateBase;
	}
	public void setFlag3IsCanCreateBase(long flag3IsCanCreateBase) {
		Flag3IsCanCreateBase = flag3IsCanCreateBase;
	}
	public long getFlagActive() {
		return FlagActive;
	}
	public void setFlagActive(long flagActive) {
		FlagActive = flagActive;
	}
	public String getFlagAssignGroupOrUser() {
		return FlagAssignGroupOrUser;
	}
	public void setFlagAssignGroupOrUser(String flagAssignGroupOrUser) {
		FlagAssignGroupOrUser = flagAssignGroupOrUser;
	}
	public long getFlagBegin() {
		return FlagBegin;
	}
	public void setFlagBegin(long flagBegin) {
		FlagBegin = flagBegin;
	}
	public long getFlagDuplicated() {
		return FlagDuplicated;
	}
	public void setFlagDuplicated(long flagDuplicated) {
		FlagDuplicated = flagDuplicated;
	}
	public long getFlagEnd() {
		return FlagEnd;
	}
	public void setFlagEnd(long flagEnd) {
		FlagEnd = flagEnd;
	}
	public long getFlagPredefined() {
		return FlagPredefined;
	}
	public void setFlagPredefined(long flagPredefined) {
		FlagPredefined = flagPredefined;
	}
	public long getFlagStart() {
		return FlagStart;
	}
	public void setFlagStart(long flagStart) {
		FlagStart = flagStart;
	}
	public String getFlagType() {
		return FlagType;
	}
	public void setFlagType(String flagType) {
		FlagType = flagType;
	}
	public String getGroup() {
		return Group;
	}
	public void setGroup(String group) {
		Group = group;
	}
	public String getGroupID() {
		return GroupID;
	}
	public void setGroupID(String groupID) {
		GroupID = groupID;
	}
	public String getIsGroupSnatch() {
		return IsGroupSnatch;
	}
	public void setIsGroupSnatch(String isGroupSnatch) {
		IsGroupSnatch = isGroupSnatch;
	}
	public String getPhaseNo() {
		return PhaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		PhaseNo = phaseNo;
	}
	public String getPhaseNoTakeMeActive() {
		return PhaseNoTakeMeActive;
	}
	public void setPhaseNoTakeMeActive(String phaseNoTakeMeActive) {
		PhaseNoTakeMeActive = phaseNoTakeMeActive;
	}
	public long getPosX() {
		return PosX;
	}
	public void setPosX(long posX) {
		PosX = posX;
	}
	public long getPosY() {
		return PosY;
	}
	public void setPosY(long posY) {
		PosY = posY;
	}
	public String getPrevPhaseNo() {
		return PrevPhaseNo;
	}
	public void setPrevPhaseNo(String prevPhaseNo) {
		PrevPhaseNo = prevPhaseNo;
	}
	public String getProcessBaseID() {
		return ProcessBaseID;
	}
	public void setProcessBaseID(String processBaseID) {
		ProcessBaseID = processBaseID;
	}
	public String getProcessBaseSchema() {
		return ProcessBaseSchema;
	}
	public void setProcessBaseSchema(String processBaseSchema) {
		ProcessBaseSchema = processBaseSchema;
	}
	public String getProcessFlag00IsAvail() {
		return ProcessFlag00IsAvail;
	}
	public void setProcessFlag00IsAvail(String processFlag00IsAvail) {
		ProcessFlag00IsAvail = processFlag00IsAvail;
	}
	public String getProcessID() {
		return ProcessID;
	}
	public void setProcessID(String processID) {
		ProcessID = processID;
	}
	public String getProcessStatus() {
		return ProcessStatus;
	}
	public void setProcessStatus(String processStatus) {
		ProcessStatus = processStatus;
	}
	public String getProcessType() {
		return ProcessType;
	}
	public void setProcessType(String processType) {
		ProcessType = processType;
	}
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	public String getRoleOnlyID() {
		return RoleOnlyID;
	}
	public void setRoleOnlyID(String roleOnlyID) {
		RoleOnlyID = roleOnlyID;
	}
	public long getStDate() {
		return StDate;
	}
	public void setStDate(long stDate) {
		StDate = stDate;
	}
	public String getStProcessAction() {
		return StProcessAction;
	}
	public void setStProcessAction(String stProcessAction) {
		StProcessAction = stProcessAction;
	}

	
	
}
