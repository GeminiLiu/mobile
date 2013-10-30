package cn.com.ultrapower.ultrawf.models.process;

public class DealProcessModel implements Cloneable
{
	private long AcceptOverTimeDate;
	private long AcceptOverTimeDate_Relative;
	private long AcceptOverTimeDate_tmp;
	private String ActionName;
	private String ActionPageID;
	private String ActionPageName;
	private String Assginee;
	private String AssgineeID;
	private long AssignOverTimeDate;
	private long AssignOverTimeDate_Relative;
	private long AssignOverTimeDate_tmp;
	private String AssigneeCorp;
	private String AssigneeCorpID;
	private String AssigneeDN;
	private String AssigneeDNID;
	private String AssigneeDep;
	private String AssigneeDepID;
	private long BaseOpenDateTime;
	private String BaseStateCode;
	private String BaseStateName;
	private long BgDate;
	private String CloseBaseSamenessGroup;
	private String CloseBaseSamenessGroupID;
	private String Commissioner;
	private String CommissionerID;
	private String CreateByUserID;
	private String CustomActions;
	private long DealOverTimeDate;
	private long DealOverTimeDate_Relative;
	private long DealOverTimeDate_tmp;
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
	private String Flag05TurnDown;
	private String Flag06TurnUp;
	private String Flag07Recall;
	private String Flag08Cancel;
	private String Flag09Close;
	private String Flag15ToAuditing;
	private String Flag16ToAssistAuditing;
	private String Flag20SideBySide;
	private String Flag22IsSelect;
	private String Flag30AuditingResult;
	private String Flag31IsTransfer;
	private String Flag32IsToTransfer;
	private String Flag33IsEndPhase;
	private String Flag34IsEndDuplicated;
	private long Flag36IsCreateBase;
	private long Flag3IsCanCreateBase;
	private long FlagActive;
	private String FlagAssignGroupOrUser;
	private long FlagBegin;
	private long FlagDuplicated;
	private long FlagEnd;
	private String FlagGroupSnatch;
	private long FlagPredefined;
	private long FlagStart;
	private String FlagType;
	private String Group;
	private String GroupID;
	private String IsQualityCheckUp;
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
	private String TransferPhaseNo;
	private String tmp_Config_GroupIsSnatch_IsHave;
	
	private long 	Flag37IsNeedStartInsideFlow;
	private String 	StartInsideFlowID;
	private String 	StartInsideFlowName;
	private long 	Flag38StartInsideFlow;
	private long 	StartInsideFlowsCount;
	private long 	StartInsideFlowsNoBackCount;
	private String 	StartInsideFlows;
	private String 	BaseFlowID;
	
	
	public long getFlag37IsNeedStartInsideFlow() {
		return Flag37IsNeedStartInsideFlow;
	}
	public void setFlag37IsNeedStartInsideFlow(long flag37IsNeedStartInsideFlow) {
		Flag37IsNeedStartInsideFlow = flag37IsNeedStartInsideFlow;
	}
	public String getStartInsideFlowID() {
		return StartInsideFlowID;
	}
	public void setStartInsideFlowID(String startInsideFlowID) {
		StartInsideFlowID = startInsideFlowID;
	}
	public String getStartInsideFlowName() {
		return StartInsideFlowName;
	}
	public void setStartInsideFlowName(String startInsideFlowName) {
		StartInsideFlowName = startInsideFlowName;
	}
	public long getFlag38StartInsideFlow() {
		return Flag38StartInsideFlow;
	}
	public void setFlag38StartInsideFlow(long flag38StartInsideFlow) {
		Flag38StartInsideFlow = flag38StartInsideFlow;
	}
	public long getStartInsideFlowsCount() {
		return StartInsideFlowsCount;
	}
	public void setStartInsideFlowsCount(long startInsideFlowsCount) {
		StartInsideFlowsCount = startInsideFlowsCount;
	}
	public long getStartInsideFlowsNoBackCount() {
		return StartInsideFlowsNoBackCount;
	}
	public void setStartInsideFlowsNoBackCount(long startInsideFlowsNoBackCount) {
		StartInsideFlowsNoBackCount = startInsideFlowsNoBackCount;
	}
	public String getStartInsideFlows() {
		return StartInsideFlows;
	}
	public void setStartInsideFlows(String startInsideFlows) {
		StartInsideFlows = startInsideFlows;
	}
	public String getBaseFlowID() {
		return BaseFlowID;
	}
	public void setBaseFlowID(String baseFlowID) {
		BaseFlowID = baseFlowID;
	}
	public long getAcceptOverTimeDate() {
		return AcceptOverTimeDate;
	}
	public void setAcceptOverTimeDate(long acceptOverTimeDate) {
		AcceptOverTimeDate = acceptOverTimeDate;
	}
	public long getAcceptOverTimeDate_Relative() {
		return AcceptOverTimeDate_Relative;
	}
	public void setAcceptOverTimeDate_Relative(long acceptOverTimeDate_Relative) {
		AcceptOverTimeDate_Relative = acceptOverTimeDate_Relative;
	}
	public long getAcceptOverTimeDate_tmp() {
		return AcceptOverTimeDate_tmp;
	}
	public void setAcceptOverTimeDate_tmp(long acceptOverTimeDate_tmp) {
		AcceptOverTimeDate_tmp = acceptOverTimeDate_tmp;
	}
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
	public long getAssignOverTimeDate() {
		return AssignOverTimeDate;
	}
	public void setAssignOverTimeDate(long assignOverTimeDate) {
		AssignOverTimeDate = assignOverTimeDate;
	}
	public long getAssignOverTimeDate_Relative() {
		return AssignOverTimeDate_Relative;
	}
	public void setAssignOverTimeDate_Relative(long assignOverTimeDate_Relative) {
		AssignOverTimeDate_Relative = assignOverTimeDate_Relative;
	}
	public long getAssignOverTimeDate_tmp() {
		return AssignOverTimeDate_tmp;
	}
	public void setAssignOverTimeDate_tmp(long assignOverTimeDate_tmp) {
		AssignOverTimeDate_tmp = assignOverTimeDate_tmp;
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
	public String getCustomActions() {
		return CustomActions;
	}
	public void setCustomActions(String customActions) {
		CustomActions = customActions;
	}
	public long getDealOverTimeDate() {
		return DealOverTimeDate;
	}
	public void setDealOverTimeDate(long dealOverTimeDate) {
		DealOverTimeDate = dealOverTimeDate;
	}
	public long getDealOverTimeDate_Relative() {
		return DealOverTimeDate_Relative;
	}
	public void setDealOverTimeDate_Relative(long dealOverTimeDate_Relative) {
		DealOverTimeDate_Relative = dealOverTimeDate_Relative;
	}
	public long getDealOverTimeDate_tmp() {
		return DealOverTimeDate_tmp;
	}
	public void setDealOverTimeDate_tmp(long dealOverTimeDate_tmp) {
		DealOverTimeDate_tmp = dealOverTimeDate_tmp;
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
	public String getFlag05TurnDown() {
		return Flag05TurnDown;
	}
	public void setFlag05TurnDown(String flag05TurnDown) {
		Flag05TurnDown = flag05TurnDown;
	}
	public String getFlag06TurnUp() {
		return Flag06TurnUp;
	}
	public void setFlag06TurnUp(String flag06TurnUp) {
		Flag06TurnUp = flag06TurnUp;
	}
	public String getFlag07Recall() {
		return Flag07Recall;
	}
	public void setFlag07Recall(String flag07Recall) {
		Flag07Recall = flag07Recall;
	}
	public String getFlag08Cancel() {
		return Flag08Cancel;
	}
	public void setFlag08Cancel(String flag08Cancel) {
		Flag08Cancel = flag08Cancel;
	}
	public String getFlag09Close() {
		return Flag09Close;
	}
	public void setFlag09Close(String flag09Close) {
		Flag09Close = flag09Close;
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
	public String getFlag30AuditingResult() {
		return Flag30AuditingResult;
	}
	public void setFlag30AuditingResult(String flag30AuditingResult) {
		Flag30AuditingResult = flag30AuditingResult;
	}
	public String getFlag31IsTransfer() {
		return Flag31IsTransfer;
	}
	public void setFlag31IsTransfer(String flag31IsTransfer) {
		Flag31IsTransfer = flag31IsTransfer;
	}
	public String getFlag32IsToTransfer() {
		return Flag32IsToTransfer;
	}
	public void setFlag32IsToTransfer(String flag32IsToTransfer) {
		Flag32IsToTransfer = flag32IsToTransfer;
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
	public String getFlagGroupSnatch() {
		return FlagGroupSnatch;
	}
	public void setFlagGroupSnatch(String flagGroupSnatch) {
		FlagGroupSnatch = flagGroupSnatch;
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
	public String getIsQualityCheckUp() {
		return IsQualityCheckUp;
	}
	public void setIsQualityCheckUp(String isQualityCheckUp) {
		IsQualityCheckUp = isQualityCheckUp;
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
	public String getTransferPhaseNo() {
		return TransferPhaseNo;
	}
	public void setTransferPhaseNo(String transferPhaseNo) {
		TransferPhaseNo = transferPhaseNo;
	}
	public String getTmp_Config_GroupIsSnatch_IsHave() {
		return tmp_Config_GroupIsSnatch_IsHave;
	}
	public void setTmp_Config_GroupIsSnatch_IsHave(
			String tmp_Config_GroupIsSnatch_IsHave) {
		this.tmp_Config_GroupIsSnatch_IsHave = tmp_Config_GroupIsSnatch_IsHave;
	}
	
	
}
