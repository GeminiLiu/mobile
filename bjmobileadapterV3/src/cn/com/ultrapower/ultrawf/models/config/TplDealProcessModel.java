package cn.com.ultrapower.ultrawf.models.config;

public class TplDealProcessModel {

//	属性设置区域--Begin--
	private  String ProcessID;//
	private  String BaseTplID;//
	private  String BaseTplSchema;//
	private  String PhaseNo;//
	private  String PrevPhaseNo;//
	private  String Assginee;//
	private  String AssgineeID;//
	private  String Group;//
	private  String GroupID;//a
	private  String ProcessStatus;//
	private  long AssignOverTimeDate;//
	private  long AcceptOverTimeDate;//
	private  long DealOverTimeDate;//
	private  String Desc;//
	private  int FlagType;//
	private  int FlagActive;//
	private  int FlagPredefined;//
	private  int FlagDuplicated;//
	private  int Flag01Assign;//
	private  int Flag02Copy;//
	private  int Flag03Assist;//
	private  int Flag04Transfer;//
	private  int Flag05TurnDown;//
	private  int Flag06TurnUp;//
	private  int Flag07Recall;//
	private  int Flag08Cancel;//
	private  int Flag09Close;//
	private  int Flag15ToAuditing;//
	private  int Flag20SideBySide;//
	private  int Flag31IsTransfer;//
	private  String TransferPhaseNo;//
	private  String ProcessType;//
	private  int PosX;//
	private  int PosY;//
	
	private  long DealOverTimeDate_tmp;
	private  long AssignOverTimeDate_tmp;
	private  long AcceptOverTimeDate_tmp;
	
//	1 ProcessID 
	public String getProcessID()
	{
	   return ProcessID;
	}
	public void   setProcessID(String p_ProcessID)
	{
	   ProcessID=p_ProcessID;
	}
//	700020001 BaseTplID 
	public String getBaseTplID()
	{
	   return BaseTplID;
	}
	public void   setBaseTplID(String p_BaseTplID)
	{
	   BaseTplID=p_BaseTplID;
	}
//	700020002 BaseTplSchema 
	public String getBaseTplSchema()
	{
	   return BaseTplSchema;
	}
	public void   setBaseTplSchema(String p_BaseTplSchema)
	{
	   BaseTplSchema=p_BaseTplSchema;
	}
//	700020003 PhaseNo 
	public String getPhaseNo()
	{
	   return PhaseNo;
	}
	public void   setPhaseNo(String p_PhaseNo)
	{
	   PhaseNo=p_PhaseNo;
	}
//	700020004 PrevPhaseNo 
	public String getPrevPhaseNo()
	{
	   return PrevPhaseNo;
	}
	public void   setPrevPhaseNo(String p_PrevPhaseNo)
	{
	   PrevPhaseNo=p_PrevPhaseNo;
	}
//	700020005 Assginee 
	public String getAssginee()
	{
	   return Assginee;
	}
	public void   setAssginee(String p_Assginee)
	{
	   Assginee=p_Assginee;
	}
//	700020006 AssgineeID 
	public String getAssgineeID()
	{
	   return AssgineeID;
	}
	public void   setAssgineeID(String p_AssgineeID)
	{
	   AssgineeID=p_AssgineeID;
	}
//	700020007 Group 
	public String getGroup()
	{
	   return Group;
	}
	public void   setGroup(String p_Group)
	{
	   Group=p_Group;
	}
//	700020008 GroupID 
	public String getGroupID()
	{
	   return GroupID;
	}
	public void   setGroupID(String p_GroupID)
	{
	   GroupID=p_GroupID;
	}
//	700020011 ProcessStatus 
	public String getProcessStatus()
	{
	   return ProcessStatus;
	}
	public void   setProcessStatus(String p_ProcessStatus)
	{
	   ProcessStatus=p_ProcessStatus;
	}
//	700020012 AssignOverTimeDate 
	public long getAssignOverTimeDate()
	{
	   return AssignOverTimeDate;
	}
	public void   setAssignOverTimeDate(long p_AssignOverTimeDate)
	{
	   AssignOverTimeDate=p_AssignOverTimeDate;
	}
//	700020013 AcceptOverTimeDate 
	public long getAcceptOverTimeDate()
	{
	   return AcceptOverTimeDate;
	}
	public void   setAcceptOverTimeDate(long p_AcceptOverTimeDate)
	{
	   AcceptOverTimeDate=p_AcceptOverTimeDate;
	}
//	700020014 DealOverTimeDate 
	public long getDealOverTimeDate()
	{
	   return DealOverTimeDate;
	}
	public void   setDealOverTimeDate(long p_DealOverTimeDate)
	{
	   DealOverTimeDate=p_DealOverTimeDate;
	}
//	700020018 Desc 
	public String getDesc()
	{
	   return Desc;
	}
	public void   setDesc(String p_Desc)
	{
	   Desc=p_Desc;
	}
//	700020019 FlagType 
	public int getFlagType()
	{
	   return FlagType;
	}
	public void   setFlagType(int p_FlagType)
	{
	   FlagType=p_FlagType;
	}
//	700020020 FlagActive 
	public int getFlagActive()
	{
	   return FlagActive;
	}
	public void   setFlagActive(int p_FlagActive)
	{
	   FlagActive=p_FlagActive;
	}
//	700020021 FlagPredefined 
	public int getFlagPredefined()
	{
	   return FlagPredefined;
	}
	public void   setFlagPredefined(int p_FlagPredefined)
	{
	   FlagPredefined=p_FlagPredefined;
	}
//	700020022 FlagDuplicated 
	public int getFlagDuplicated()
	{
	   return FlagDuplicated;
	}
	public void   setFlagDuplicated(int p_FlagDuplicated)
	{
	   FlagDuplicated=p_FlagDuplicated;
	}
//	700020023 Flag01Assign 
	public int getFlag01Assign()
	{
	   return Flag01Assign;
	}
	public void   setFlag01Assign(int p_Flag01Assign)
	{
	   Flag01Assign=p_Flag01Assign;
	}
//	700020024 Flag02Copy 
	public int getFlag02Copy()
	{
	   return Flag02Copy;
	}
	public void   setFlag02Copy(int p_Flag02Copy)
	{
	   Flag02Copy=p_Flag02Copy;
	}
//	700020025 Flag03Assist 
	public int getFlag03Assist()
	{
	   return Flag03Assist;
	}
	public void   setFlag03Assist(int p_Flag03Assist)
	{
	   Flag03Assist=p_Flag03Assist;
	}
//	700020026 Flag04Transfer 
	public int getFlag04Transfer()
	{
	   return Flag04Transfer;
	}
	public void   setFlag04Transfer(int p_Flag04Transfer)
	{
	   Flag04Transfer=p_Flag04Transfer;
	}
//	700020027 Flag05TurnDown 
	public int getFlag05TurnDown()
	{
	   return Flag05TurnDown;
	}
	public void   setFlag05TurnDown(int p_Flag05TurnDown)
	{
	   Flag05TurnDown=p_Flag05TurnDown;
	}
//	700020028 Flag06TurnUp 
	public int getFlag06TurnUp()
	{
	   return Flag06TurnUp;
	}
	public void   setFlag06TurnUp(int p_Flag06TurnUp)
	{
	   Flag06TurnUp=p_Flag06TurnUp;
	}
//	700020029 Flag07Recall 
	public int getFlag07Recall()
	{
	   return Flag07Recall;
	}
	public void   setFlag07Recall(int p_Flag07Recall)
	{
	   Flag07Recall=p_Flag07Recall;
	}
//	700020030 Flag08Cancel 
	public int getFlag08Cancel()
	{
	   return Flag08Cancel;
	}
	public void   setFlag08Cancel(int p_Flag08Cancel)
	{
	   Flag08Cancel=p_Flag08Cancel;
	}
//	700020031 Flag09Close 
	public int getFlag09Close()
	{
	   return Flag09Close;
	}
	public void   setFlag09Close(int p_Flag09Close)
	{
	   Flag09Close=p_Flag09Close;
	}
//	700020032 Flag15ToAuditing 
	public int getFlag15ToAuditing()
	{
	   return Flag15ToAuditing;
	}
	public void   setFlag15ToAuditing(int p_Flag15ToAuditing)
	{
	   Flag15ToAuditing=p_Flag15ToAuditing;
	}
//	700020033 Flag20SideBySide 
	public int getFlag20SideBySide()
	{
	   return Flag20SideBySide;
	}
	public void   setFlag20SideBySide(int p_Flag20SideBySide)
	{
	   Flag20SideBySide=p_Flag20SideBySide;
	}
//	700020035 Flag31IsTransfer 
	public int getFlag31IsTransfer()
	{
	   return Flag31IsTransfer;
	}
	public void   setFlag31IsTransfer(int p_Flag31IsTransfer)
	{
	   Flag31IsTransfer=p_Flag31IsTransfer;
	}
//	700020036 TransferPhaseNo 
	public String getTransferPhaseNo()
	{
	   return TransferPhaseNo;
	}
	public void   setTransferPhaseNo(String p_TransferPhaseNo)
	{
	   TransferPhaseNo=p_TransferPhaseNo;
	}
//	700020043 ProcessType 
	public String getProcessType()
	{
	   return ProcessType;
	}
	public void   setProcessType(String p_ProcessType)
	{
	   ProcessType=p_ProcessType;
	}
//	700020511 PosX 
	public int getPosX()
	{
	   return PosX;
	}
	public void   setPosX(int p_PosX)
	{
	   PosX=p_PosX;
	}
//	700020512 PosY 
	public int getPosY()
	{
	   return PosY;
	}
	public void   setPosY(int p_PosY)
	{
	   PosY=p_PosY;
	}
//	属性设置区域--End--
	public long getAcceptOverTimeDate_tmp() {
		return AcceptOverTimeDate_tmp;
	}
	public void setAcceptOverTimeDate_tmp(long acceptOverTimeDate_tmp) {
		AcceptOverTimeDate_tmp = acceptOverTimeDate_tmp;
	}
	public long getAssignOverTimeDate_tmp() {
		return AssignOverTimeDate_tmp;
	}
	public void setAssignOverTimeDate_tmp(long assignOverTimeDate_tmp) {
		AssignOverTimeDate_tmp = assignOverTimeDate_tmp;
	}
	public long getDealOverTimeDate_tmp() {
		return DealOverTimeDate_tmp;
	}
	public void setDealOverTimeDate_tmp(long dealOverTimeDate_tmp) {
		DealOverTimeDate_tmp = dealOverTimeDate_tmp;
	}


}
