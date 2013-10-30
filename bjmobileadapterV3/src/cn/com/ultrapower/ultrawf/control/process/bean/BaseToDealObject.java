package cn.com.ultrapower.ultrawf.control.process.bean;

import java.util.Date;

public class BaseToDealObject
{
	private  String AssgineeID;
	private  String Assginee;
	private  String GroupID;
	private  String Group;
	
	private  Date AssignOverTimeDate;
	private  Date AcceptOverTimeDate;
	private  Date DealOverTimeDate;
	private  Date AuditingOverTimeDate;
	
	private  String ProDesc;
	
	/**
	 * 0主办、1协办、2抄送
	 */
	private  int FlagType;//类型0主办、1协办、2抄送、3审批、5会审
	private  int Flag01Assign;// 是否允许派单：0否；1是，默认为：1
	private  int Flag02Copy; //是否允许抄送：0否；1是，默认为：
	private  int Flag03Assist; //是否允许协办：0否；1是，默认为：1
	private  int Flag04Transfer; //是否允许转单（派单，是否回单给我）：0否；1是，默认为：1
	private  int Flag05TurnDown; //是否允许退单：0否；1是，默认为：1
	private  int Flag06TurnUp; //是否允许驳回：0否；1是，默认为：1
	private  int Flag07Recall; //是否允许追单：0否；1是，默认为：1
	private  int Flag08Cancel; //是否允许废单：0否；1是，默认为：0
	private  int Flag09Close; //是否允许关闭：0否；1是，默认为：0
	private  int Flag15ToAuditing; //是否允许提交审批：0否；1是，默认为：1
	private  int Flag16ToAssistAuditing; //是否允许组织会审：0否；1是，默认为：0
	private  int Flag31IsTransfer; //是否转派过来的工单：0否；1是；默认为0，默认为：0

	
	public Date getAcceptOverTimeDate() {
		return AcceptOverTimeDate;
	}
	public void setAcceptOverTimeDate(Date acceptOverTimeDate) {
		AcceptOverTimeDate = acceptOverTimeDate;
	}
	public Date getAssignOverTimeDate() {
		return AssignOverTimeDate;
	}
	public void setAssignOverTimeDate(Date assignOverTimeDate) {
		AssignOverTimeDate = assignOverTimeDate;
	}
	public Date getDealOverTimeDate() {
		return DealOverTimeDate;
	}
	public void setDealOverTimeDate(Date dealOverTimeDate) {
		DealOverTimeDate = dealOverTimeDate;
	}
	public int getFlag01Assign() {
		return Flag01Assign;
	}
	public void setFlag01Assign(int flag01Assign) {
		Flag01Assign = flag01Assign;
	}
	public int getFlag02Copy() {
		return Flag02Copy;
	}
	public void setFlag02Copy(int flag02Copy) {
		Flag02Copy = flag02Copy;
	}
	public int getFlag03Assist() {
		return Flag03Assist;
	}
	public void setFlag03Assist(int flag03Assist) {
		Flag03Assist = flag03Assist;
	}
	public int getFlag04Transfer() {
		return Flag04Transfer;
	}
	public void setFlag04Transfer(int flag04Transfer) {
		Flag04Transfer = flag04Transfer;
	}
	public int getFlag05TurnDown() {
		return Flag05TurnDown;
	}
	public void setFlag05TurnDown(int flag05TurnDown) {
		Flag05TurnDown = flag05TurnDown;
	}
	public int getFlag06TurnUp() {
		return Flag06TurnUp;
	}
	public void setFlag06TurnUp(int flag06TurnUp) {
		Flag06TurnUp = flag06TurnUp;
	}
	public int getFlag07Recall() {
		return Flag07Recall;
	}
	public void setFlag07Recall(int flag07Recall) {
		Flag07Recall = flag07Recall;
	}
	public int getFlag08Cancel() {
		return Flag08Cancel;
	}
	public void setFlag08Cancel(int flag08Cancel) {
		Flag08Cancel = flag08Cancel;
	}
	public int getFlag09Close() {
		return Flag09Close;
	}
	public void setFlag09Close(int flag09Close) {
		Flag09Close = flag09Close;
	}
	public int getFlag15ToAuditing() {
		return Flag15ToAuditing;
	}
	public void setFlag15ToAuditing(int flag15ToAuditing) {
		Flag15ToAuditing = flag15ToAuditing;
	}
	public int getFlagType() {
		return FlagType;
	}
	public void setFlagType(int flagType) {
		FlagType = flagType;
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
	public int getFlag31IsTransfer() {
		return Flag31IsTransfer;
	}
	public void setFlag31IsTransfer(int flag31IsTransfer) {
		Flag31IsTransfer = flag31IsTransfer;
	}
	public String getProDesc() {
		return ProDesc;
	}
	public void setProDesc(String proDesc) {
		ProDesc = proDesc;
	}
	public void setFlag16ToAssistAuditing(int flag16ToAssistAuditing) {
		Flag16ToAssistAuditing = flag16ToAssistAuditing;
	}
	public int getFlag16ToAssistAuditing() {
		return Flag16ToAssistAuditing;
	}
	public void setAuditingOverTimeDate(Date auditingOverTimeDate) {
		AuditingOverTimeDate = auditingOverTimeDate;
	}
	public Date getAuditingOverTimeDate() {
		return AuditingOverTimeDate;
	}


	
}
