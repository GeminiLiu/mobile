package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.ConfigQueryDetailModel;
import cn.com.ultrapower.ultrawf.models.config.ConfigUserCloseBaseGroup;
import cn.com.ultrapower.ultrawf.models.config.ConfigUserCloseBaseGroupModel;
import cn.com.ultrapower.ultrawf.models.config.ParConfigUserCloseBaseGroupModel;
import cn.com.ultrapower.ultrawf.models.config.SysUser;
import cn.com.ultrapower.ultrawf.models.config.SysUserModel;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

import cn.com.ultrapower.ultrawf.share.queryanalyse.ParBindString;
import cn.com.ultrapower.ultrawf.share.queryanalyse.ParFiledInfo;
import cn.com.ultrapower.ultrawf.share.queryanalyse.PariseUntil;

public class ParDealProcess {
	
	
	private int ProcessOptionalType=0;
	
	//任务处理人
	private String TasekPersonID="";
	private String TasekPersonName="";
	private String TasekPersonExtendSql="";
	private Hashtable hsOwnFiled=null;
	
	public int getProcessOptionalType() {
		return ProcessOptionalType;
	}
	public void setProcessOptionalType(int p_ProcessOptionalType) {
		ProcessOptionalType = p_ProcessOptionalType;
	}
	public String getTasekPersonID() {
		return TasekPersonID;
	}
	public void setTasekPersonID(String tasekPersonID) {
		if(tasekPersonID!=null)
			TasekPersonID = tasekPersonID;
		else
			TasekPersonID="";
	}
	public String getTasekPersonName() {
		return TasekPersonName;
	}
	public void setTasekPersonName(String tasekPersonName) {
		TasekPersonName = tasekPersonName;
	}	
	
	
//	属性设置区域--Begin--
	private  String ProcessID="";
	private  String ProcessBaseID="";
	private  String ProcessBaseSchema="";
	private  String PhaseNo="";
	private  String PrevPhaseNo="";
	private  String CreateByUserID="";
	private  String DealerID="";
	
	
	private  String Assginee="";
	private  String AssgineeID="";
	private  String Group="";
	private  String GroupID="";
	private  String Dealer="";

	
	private  String ProcessStatus="";
	private  long AssignOverTimeDate=0;

	private  long AcceptOverTimeDate;
	private  long AcceptOverTimeDateBegin=0;
	private  long AcceptOverTimeDateEnd=0;
	private  long DealOverTimeDate;
	private  long DealOverTimeDateBegin=0;
	private  long DealOverTimeDateEnd=0;
	private  long BgDate=0;
	private  long BgDateBegin=0;
	private  long BgDateEnd=999;
	private  String FlagActive="";
	private  int FlagPredefined=999;
	private  int FlagDuplicated=999;	
	private  long StDate;
	private  long StDateBegin=0;
	private  long StDateEnd=0;
	
	private  long EdDate=0;
	private  long EdDateBegin=0;
	private  long EdDateEnd=0;
	private  String FlagType="";
	
	private  String EndDateString="";
	private  String StDateString="";
	private  String BgDateString="";
	
	/*

	private  long EdDate;
	private  String Desc="";
	


	private  int Flag01Assign=0;
	private  int Flag02Copy=0;
	private  int Flag03Assist=0;
	private  int Flag04Transfer=0;
	private  int Flag05TurnDown=0;
	private  int Flag06TurnUp=0;
	private  int Flag07Recall=0;
	private  int Flag08Cancel=0;
	private  int Flag09Close=0;
	private  int Flag15ToAuditing=0;
	private  int Flag20SideBySide=0;
	private  int Flag22IsSelect=0;
	private  int Flag30AuditingResult=0;
	private  int Flag31IsTransfer=0;
	private  String TransferPhaseNo="";
	private  String ProcessType="";*/
	//是否已存入历史记录表
	private int IsArchive=999;	
	private int IsGroupSnatch=999;
	
	private String ExtendSql="";
public int getIsGroupSnatch() {
		return IsGroupSnatch;
	}
	public void setIsGroupSnatch(int isGroupSnatch) {
		IsGroupSnatch = isGroupSnatch;
	}
	//	本记录的唯一标识，创建是自动形成，无业务含义
	public String getProcessID()
	{
	   return ProcessID;
	}
	public void   setProcessID(String p_ProcessID)
	{
	   ProcessID=p_ProcessID;
	}
//	指向主工单记录的指针
	public String getProcessBaseID()
	{
	   return ProcessBaseID;
	}
	public void   setProcessBaseID(String p_ProcessBaseID)
	{
	   ProcessBaseID=p_ProcessBaseID;
	}
//	指向主工单记录的指针
	public String getProcessBaseSchema()
	{
	   return ProcessBaseSchema;
	}
	public void   setProcessBaseSchema(String p_ProcessBaseSchema)
	{
	   ProcessBaseSchema=p_ProcessBaseSchema;
	}
//	环节号，插入记录时，如果为空，则产生一个随机数，插入该字段。新建记录时该号默认取随机的值；从模版建工单时该号不变；已复制的形式（Copy to New）新建环节时与原环节号相同。


	public String getPhaseNo()
	{
	   return PhaseNo;
	}
	public void   setPhaseNo(String p_PhaseNo)
	{
	   PhaseNo=p_PhaseNo;
	}
//	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。


	public String getPrevPhaseNo()
	{
	   return PrevPhaseNo;
	}
	public void   setPrevPhaseNo(String p_PrevPhaseNo)
	{
	   PrevPhaseNo=p_PrevPhaseNo;
	}
//	选择派单的对象的人登录名，当该条记录的FlagActive不等于6时，清空
	public String getCreateByUserID()
	{
	   return CreateByUserID;
	}
	public void   setCreateByUserID(String p_CreateByUserID)
	{
	   CreateByUserID=p_CreateByUserID;
	}
//	人名，本记录的主人，派单的对象


	public String getAssginee()
	{
	   return Assginee;
	}
	public void   setAssginee(String p_Assginee)
	{
	   Assginee=p_Assginee;
	}
//	人登陆名
	public String getAssgineeID()
	{
	   return AssgineeID;
	}
	public void   setAssgineeID(String p_AssgineeID)
	{
	   AssgineeID=p_AssgineeID;
	}
//	组，本记录的主人，派单的对象
	public String getGroup()
	{
	   return Group;
	}
	public void   setGroup(String p_Group)
	{
	   Group=p_Group;
	}
//	组ID
	public String getGroupID()
	{
	   return GroupID;
	}
	public void   setGroupID(String p_GroupID)
	{
	   GroupID=p_GroupID;
	}
//	人名，本记录的操作者


	public String getDealer()
	{
	   return Dealer;
	}
	public void   setDealer(String p_Dealer)
	{
	   Dealer=p_Dealer;
	}

//	本记录状态描述。


	public String getProcessStatus()
	{
	   return ProcessStatus;
	}
	public void   setProcessStatus(String p_ProcessStatus)
	{
	   ProcessStatus=p_ProcessStatus;
	}
//	派单时限（接到工单后，超过这个时间就不允许往下派单，只能自己处理，可以为空，为空时即为没有时间限制）
	public long getAssignOverTimeDate()
	{
	   return AssignOverTimeDate;
	}
	public void   setAssignOverTimeDate(long p_AssignOverTimeDate)
	{
	   AssignOverTimeDate=p_AssignOverTimeDate;
	}

	
//	人登陆名
	public String getDealerID()
	{
	   return DealerID;
	}
	public void   setDealerID(String p_DealerID)
	{
	   DealerID=p_DealerID;
	}	
//	受理时限
	public long getAcceptOverTimeDate()
	{
	   return AcceptOverTimeDate;
	}
	public void   setAcceptOverTimeDate(long p_AcceptOverTimeDate)
	{
	   AcceptOverTimeDate=p_AcceptOverTimeDate;
	}
//	受理时限开始时间	
	public long getAcceptOverTimeDateBegin()
	{
	   return AcceptOverTimeDateBegin;
	}
	public void   setAcceptOverTimeDateBegin(long p_AcceptOverTimeDateBegin)
	{
		AcceptOverTimeDateBegin=p_AcceptOverTimeDateBegin;
	}
//	受理时限结束时间	
	public long getAcceptOverTimeDateEnd()
	{
	   return AcceptOverTimeDateEnd;
	}
	public void   setAcceptOverTimeDateEnd(long p_AcceptOverTimeDateEnd)
	{
		AcceptOverTimeDateEnd=p_AcceptOverTimeDateEnd;
	}	
	
//	处理时限
	public long getDealOverTimeDate()
	{
	   return DealOverTimeDate;
	}
	public void   setDealOverTimeDate(long p_DealOverTimeDate)
	{
	   DealOverTimeDate=p_DealOverTimeDate;
	}
	
//	处理时限开始时间
	public long getDealOverTimeDateBegin()
	{
	   return DealOverTimeDateBegin;
	}
	public void   setDealOverTimeDateBegin(long p_getDealOverTimeDateBegin)
	{
		DealOverTimeDateBegin=p_getDealOverTimeDateBegin;
	}	
//	处理时限结束时间
	public long getDealOverTimeDateEnd()
	{
	   return DealOverTimeDateEnd;
	}
	public void   setDealOverTimeDateEnd(long p_getDealOverTimeDateEnd)
	{
		DealOverTimeDateEnd=p_getDealOverTimeDateEnd;
	}	
	
//	创建/生效时间，表示该记录创建/生效时间
	public long getStDate()
	{
	   return StDate;
	}
	public void   setStDate(long p_StDate)
	{
	   StDate=p_StDate;
	}
	
	public long getStDateBegin() {
		return StDateBegin;
	}
	public void setStDateBegin(long stDateBegin) {
		StDateBegin = stDateBegin;
	}
	public long getStDateEnd() {
		return StDateEnd;
	}
	public void setStDateEnd(long stDateEnd) {
		StDateEnd = stDateEnd;
	}	
	
	
//	领单时间
	public long getBgDate()
	{
	   return BgDate;
	}
	public void   setBgDate(long p_BgDate)
	{
	   BgDate=p_BgDate;
	}
	
	public long getBgDateBegin()
	{
	   return BgDateBegin;
	}
	public void   setBgDateBegin(long p_BgDateBegin)
	{
	   BgDateBegin=p_BgDateBegin;
	}	
	
	public long getBgDateEnd()
	{
	   return BgDateEnd;
	}
	public void  setBgDateEnd(long p_BgDateEnd)
	{
		BgDateEnd=p_BgDateEnd;
	}		
	

//	是否预定义固定流程。只有在保存为模版、完成处理是判断。1：是；0否，默认为：0
	public int getFlagPredefined()
	{
	   return FlagPredefined;
	}
	public void   setFlagPredefined(int p_FlagPredefined)
	{
	   FlagPredefined=p_FlagPredefined;
	}
//	是否复制出来的记录；标示本条记录是否复制出来的1：是；0否，默认为：0
	public int getFlagDuplicated()
	{
	   return FlagDuplicated;
	}
	public void   setFlagDuplicated(int p_FlagDuplicated)
	{
	   FlagDuplicated=p_FlagDuplicated;
	}	
	
	/*
//	操作完成时间
	public Date getEdDate()
	{
	   return EdDate;
	}
	public void   setEdDate(Date p_EdDate)
	{
	   EdDate=p_EdDate;
	}
//	描述，From写给to的信息，在该条记录创建时就已经有内容信息了


	public String getDesc()
	{
	   return Desc;
	}
	public void   setDesc(String p_Desc)
	{
	   Desc=p_Desc;
	}

//	是否允许派单：0否；1是，默认为：1
	public int getFlag01Assign()
	{
	   return Flag01Assign;
	}
	public void   setFlag01Assign(int p_Flag01Assign)
	{
	   Flag01Assign=p_Flag01Assign;
	}
//	是否允许抄送：0否；1是，默认为：1
	public int getFlag02Copy()
	{
	   return Flag02Copy;
	}
	public void   setFlag02Copy(int p_Flag02Copy)
	{
	   Flag02Copy=p_Flag02Copy;
	}
//	是否允许协办：0否；1是，默认为：1
	public int getFlag03Assist()
	{
	   return Flag03Assist;
	}
	public void   setFlag03Assist(int p_Flag03Assist)
	{
	   Flag03Assist=p_Flag03Assist;
	}
//	是否允许转单（派单，是否回单给我）：0否；1是，默认为：1
	public int getFlag04Transfer()
	{
	   return Flag04Transfer;
	}
	public void   setFlag04Transfer(int p_Flag04Transfer)
	{
	   Flag04Transfer=p_Flag04Transfer;
	}
//	是否允许退单：0否；1是，默认为：1
	public int getFlag05TurnDown()
	{
	   return Flag05TurnDown;
	}
	public void   setFlag05TurnDown(int p_Flag05TurnDown)
	{
	   Flag05TurnDown=p_Flag05TurnDown;
	}
//	是否允许驳回：0否；1是，默认为：1
	public int getFlag06TurnUp()
	{
	   return Flag06TurnUp;
	}
	public void   setFlag06TurnUp(int p_Flag06TurnUp)
	{
	   Flag06TurnUp=p_Flag06TurnUp;
	}
//	是否允许追单：0否；1是，默认为：1
	public int getFlag07Recall()
	{
	   return Flag07Recall;
	}
	public void   setFlag07Recall(int p_Flag07Recall)
	{
	   Flag07Recall=p_Flag07Recall;
	}
//	是否允许废单：0否；1是，默认为：0
	public int getFlag08Cancel()
	{
	   return Flag08Cancel;
	}
	public void   setFlag08Cancel(int p_Flag08Cancel)
	{
	   Flag08Cancel=p_Flag08Cancel;
	}
//	是否允许关闭：0否；1是，默认为：0
	public int getFlag09Close()
	{
	   return Flag09Close;
	}
	public void   setFlag09Close(int p_Flag09Close)
	{
	   Flag09Close=p_Flag09Close;
	}
//	是否允许提交审批：0否；1是，默认为：1
	public int getFlag15ToAuditing()
	{
	   return Flag15ToAuditing;
	}
	public void   setFlag15ToAuditing(int p_Flag15ToAuditing)
	{
	   Flag15ToAuditing=p_Flag15ToAuditing;
	}
//	是否选择或并行：0并行，1选择，默认为：0
	public int getFlag20SideBySide()
	{
	   return Flag20SideBySide;
	}
	public void   setFlag20SideBySide(int p_Flag20SideBySide)
	{
	   Flag20SideBySide=p_Flag20SideBySide;
	}
//	用户是否选中0没有选中，1选中，默认为：0
	public int getFlag22IsSelect()
	{
	   return Flag22IsSelect;
	}
	public void   setFlag22IsSelect(int p_Flag22IsSelect)
	{
	   Flag22IsSelect=p_Flag22IsSelect;
	}
//	发生审批环后的审批结果：0没有提交审批；1审批通过；2不审批通过，默认为：0
	public int getFlag30AuditingResult()
	{
	   return Flag30AuditingResult;
	}
	public void   setFlag30AuditingResult(int p_Flag30AuditingResult)
	{
	   Flag30AuditingResult=p_Flag30AuditingResult;
	}
//	是否转派过来的工单：0否；1是；默认为0，默认为：0
	public int getFlag31IsTransfer()
	{
	   return Flag31IsTransfer;
	}
	public void   setFlag31IsTransfer(int p_Flag31IsTransfer)
	{
	   Flag31IsTransfer=p_Flag31IsTransfer;
	}
//	发起转派工单动作的环节号，例如：A转派给B，B转派给C，那么B的” TransferPhaseNo”是A的前一环节号，C的” TransferPhaseNo”也是A的前一环节号


	public String getTransferPhaseNo()
	{
	   return TransferPhaseNo;
	}
	public void   setTransferPhaseNo(String p_TransferPhaseNo)
	{
	   TransferPhaseNo=p_TransferPhaseNo;
	}
//	默认为“Deal”


	public String getProcessType()
	{
	   return ProcessType;
	}
	public void   setProcessType(String p_ProcessType)
	{
	   ProcessType=p_ProcessType;
	}*/
	
//	取值为：0主办、1协办、2抄送

	public String getFlagType()
	{
	   return FlagType;
	}
	public void   setFlagType(String p_FlagType)
	{
	   FlagType=p_FlagType;
	}	
	
//	是否有效：7未派发，并等待审批，6未派发、未提交审批，5下一步的审批等待，4下一步的处理等待，3提交审批后等待审批完成的等待中，2派发后等待处理完成的等待中，1活动，0已完毕

	public String getFlagActive()
	{
	   return FlagActive;
	}
	public void   setFlagActive(String p_FlagActive)
	{
	   FlagActive=p_FlagActive;
	}	
	
	public int getIsArchive() {
		return IsArchive;
	}

	public void setIsArchive(int isArchive) {
		IsArchive = isArchive;
	}	
	
	private String Commissioner;//Assginee的代办人名，本记录的主人的代办人
	private String CommissionerID;//Assginee的代办人登陆名
	private String CloseBaseSamenessGroup;//同组关单的组
	private String CloseBaseSamenessGroupID;//同组关单的组ID
	//private String IsGroupSnatch;//工单派发到组时，该组是否是抢单	1：是；0否，默认为：0
	private String Flag32IsToTransfer;//本环节是否已经转交出去：0否；1是；默认为0，默认为：0
	private String Flag33IsEndPhase;//是否最终处理\审批工单的环节：0否；1是；默认为0，默认为：1	
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

	
	
//	属性设置区域--End--		
	
	/**
	 * 获取where语句
	 * @param TblName
	 * @return
	 */	
	public String GetWhereSql(String p_TblAliasName)
	{
		String strTblPrefix=p_TblAliasName;
		StringBuffer sqlString = new StringBuffer();
		if(!strTblPrefix.equals(""))
			strTblPrefix=strTblPrefix+".";
		
		/**
		 * 注意：GetPorcessTypeSql必须放在最前面，因为在有的操作状态查询会改变相应的属性值
		 * 例如：Constants.ProcessMyDeallAll://我处理过的工单(我处理过，不管是否处理完成)
		 */
		sqlString.append(GetPorcessTypeSql(p_TblAliasName,getProcessOptionalType()));
		
		//C1	ProcessID		本记录的唯一标识，创建是自动形成，无业务含义
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C1",getProcessID()));
		//700020001	ProcessBaseID			指向主工单记录的指针
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020001",getProcessBaseID()));
		//700020002	ProcessBaseSchema	指向主工单记录的指针
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020002",getProcessBaseSchema()));
		
		//700020003	PhaseNo			环节号，插入记录时，如果为空，则产生一个随机数，插入该字段。新建记录时该号默认取随机的值；从模版建工单时该号不变；已复制的形式（Copy to New）新建环节时与原环节号相同。
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020003",getPhaseNo()));
		//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020004",getPrevPhaseNo()));
		//700020045	CreateByUserID		选择派单的对象的人登录名，当该条记录的FlagActive不等于6时，清空
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020045",getCreateByUserID()));
		
		//700020010 DealerID
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020010",getDealerID()));
		//700020009	Dealer
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020009",getDealer()));

		//700020007	Group		组，本记录的主人，派单的对象
		//700020008	GroupID		组ID
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020007",getGroup()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020008",getGroupID()));
		
		
		//700020005	Assginee		人名，本记录的主人，派单的对象
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020005",getAssginee()));
		//700020006	AssgineeID		人登陆名
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020006",getAssgineeID()));
		
		//700020011	ProcessStatus				本记录状态描述。
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020011",getProcessStatus()));
		//700020012	AssignOverTimeDate	派单时限（接到工单后，超过这个时间就不允许往下派单，只能自己处理，可以为空，为空时即为没有时间限制）
		//700020012只有Dealprocess中，AuditingProcess中没有
		sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020012",getAssignOverTimeDate()));
		
		//700020015	StDate		DATE					创建/生效时间，表示该记录创建/生效时间
		sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020015",getStDate()));
		
		if(getStDateBegin()>0)
			sqlString.append(" and "+strTblPrefix+"C700020015>="+String.valueOf(getStDateBegin()));
		//创建时限(范围)结束时间
		if(getStDateEnd()>0)
			sqlString.append(" and "+strTblPrefix+"C700020015<="+String.valueOf(getStDateEnd()));	
		
		
		//700020013	AcceptOverTimeDate		受理时限
		sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020013",getAcceptOverTimeDate()));
		//受理时限(范围)开始时间
		if(getAcceptOverTimeDateBegin()>0)
			sqlString.append(" and "+strTblPrefix+"C700020013>="+String.valueOf(getAcceptOverTimeDateBegin()));
		//受理时限(范围)结束时间
		if(getAcceptOverTimeDateEnd()>0)
			sqlString.append(" and "+strTblPrefix+"C700020013<="+String.valueOf(getAcceptOverTimeDateEnd()));	
	
		//700020014	DealOverTimeDate		处理时限
		if(getDealOverTimeDate()>0)
			sqlString.append(" and "+strTblPrefix+"C700020014="+String.valueOf(getDealOverTimeDate()));		
		//处理时限(范围)开始时间
		if(getDealOverTimeDateBegin()>0)
			sqlString.append(" and "+strTblPrefix+"C700020014>="+String.valueOf(getDealOverTimeDateBegin()));
		//处理时限(范围)结束时间
		if(getDealOverTimeDateEnd()>0)
			sqlString.append(" and "+strTblPrefix+"C700020014<="+String.valueOf(getDealOverTimeDateEnd()));
		
		//700020016	BgDate		领单时间
		sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020016",getBgDate()));
		//领单时间(范围)开始时间
		if(getBgDateBegin()>0)
			sqlString.append(" and "+strTblPrefix+"C700020016>="+String.valueOf(getBgDateBegin()));			
		//领单时间(范围)结束时间
		if(BgDateEnd>0 && BgDateEnd!=999)
			sqlString.append(" and "+strTblPrefix+"C700020016<="+String.valueOf(getBgDateEnd()));	
		else if(BgDateEnd<=0)
			sqlString.append(" and ("+strTblPrefix+"C700020016=0 or "+strTblPrefix+"C700020016 is null)" );
		//700020017	EdDate		操作完成时间
		sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020017",getEdDate()));
		//领单时间(范围)开始时间
		if(getEdDateBegin()>0)
			sqlString.append(" and "+strTblPrefix+"C700020017>="+String.valueOf(getEdDateBegin()));
		//领单时间(范围)结束时间
		if(getEdDateEnd()>0)
			sqlString.append(" and "+strTblPrefix+"C700020017<="+String.valueOf(getEdDateEnd()));			
		//700020020 FlagActive 是否有效：7未派发，并等待审批，6未派发、未提交审批，5下一步的审批等待，4下一步的处理等待，3提交审批后等待审批完成的等待中，2派发后等待处理完成的等待中，1活动，0已完毕
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020020",getFlagActive()));
		
		//700020021	FlagPredefined		是否预定义固定流程。只有在保存为模版、完成处理是判断。1：是；0否，默认为：0
		sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700020021",getFlagPredefined()));
		//700020022	FlagDuplicated		是否复制出来的记录；标示本条记录是否复制出来的1：是；0否，默认为：0
		sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700020022",getFlagDuplicated()));
		//700020019	FlagType  0主办、1协办、2抄送
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019",getFlagType()));
		//IsGroupSnatch	700020049 工单派发到组时，该组是否是抢单		1：是；0否，默认为：0
		sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700020049",getIsGroupSnatch()));
		//System.out.println( sqlString.toString());
		
		//700020047 Commissioner的代办人名，本记录的主人的代办人
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020047",getCommissioner()));
		//700020048 CommissionerID Assginee的代办人登陆名
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020048",getCommissionerID()));
		//700020050 CloseBaseSamenessGroup 同组关单的组
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020050",getCloseBaseSamenessGroup()));
		//700020051 CloseBaseSamenessGroupID 同组关单的组ID
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020051",getCloseBaseSamenessGroupID()));
		//700020052 Flag32IsToTransfer 本环节是否已经转交出去：0否；1是；默认为0，默认为：0
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020052",getFlag32IsToTransfer()));
		//700020053 Flag33IsEndPhase 是否最终处理\审批工单的环节：0否；1是；默认为0，默认为：1			
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020053",getFlag33IsEndPhase()));		
		if(!this.getExtendSql().trim().equals(""))
			sqlString.append(getExtendSql());	
		
		if(hsOwnFiled!=null)
		{
			String key;
			for(Iterator it=hsOwnFiled.keySet().iterator();it.hasNext();)
			{
				key   =   (String)it.next();
				sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,key,getFiledValue(key)));
				
			}
		}
		
		//查询参数配置类的查询条件
		if(m_ConfigQueryDetailList!=null)
		{
			int listCount=m_ConfigQueryDetailList.size();
			String strValue;
			String strType;
			String logic;
			String strClassName;
			for(int i=0;i<listCount;i++)
			{
				ConfigQueryDetailModel m_ConfigQueryDetailModel=(ConfigQueryDetailModel)m_ConfigQueryDetailList.get(i);
				
				strValue=FormatString.CheckNullString(m_ConfigQueryDetailModel.getEvaluate()); // FormatString.CheckNullString(request.getParameter(m_ConfigQueryDetailModel.getFieldname()));
				strType=FormatString.CheckNullString(m_ConfigQueryDetailModel.getFieldtype()).trim();
				strClassName=FormatString.CheckNullString(m_ConfigQueryDetailModel.getParclassname()).trim().toUpperCase();
				//m_ConfigQueryDetailModel.setEvaluate(strValue);	
				if(!strValue.equals("")&&strClassName.equals("PARDEALPROCESS"))
				{
					logic=m_ConfigQueryDetailModel.getLogicexp();			
					if(!logic.trim().equals(""))
						logic+=":";
					//如果是日期
					if(strType.equals("5"))
					{
						if(logic.equals("<=:"))
						{
							if(strValue.split(":").length<2)
								strValue+=" 23:59:59";
								
						}
						strValue=String.valueOf(FormatTime.FormatDateStringToInt(strValue));
						
					}					
					sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C"+m_ConfigQueryDetailModel.getFieldid(),logic+strValue));
				}//if(!strValue.equals(""))
			}//for(int i=0;i<listCount;i++)
			
		}//if(m_ConfigQueryDetailList!=null)
		
		
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020015",getStDateString()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020016",getBgDateString()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020017",getEndDateString()));
		return sqlString.toString();
	}	
	
	/**
	 * 
	 * @param p_FiledID 字段名称 数据库中的字段 如:C7000001
	 * @param p_FiledValue 值
	 */
	public void setFiledValue(String p_FiledID,String p_FiledValue)
	{
		if(hsOwnFiled==null)
		{
			hsOwnFiled=new Hashtable();
		}
		//if(hsOwnFiled.get(p_FiledID)!=null)
		 p_FiledID=p_FiledID.toUpperCase();
	
		hsOwnFiled.put(p_FiledID,p_FiledValue);
			
	}
	
	public String getFiledValue(String p_FiledID)
	{
		if(p_FiledID!=null)
			p_FiledID=p_FiledID.toUpperCase();
		if(hsOwnFiled!=null)
			if(hsOwnFiled.get(p_FiledID)!=null)
				return hsOwnFiled.get(p_FiledID).toString();
			else
				return "";
		else
			return "";
	}		
	
	private String getUserCommissionCondition(String p_TblAliasName,String p_strLoginName,String p_BaeSchemas)
	{
		StringBuffer stringBuffer=new StringBuffer();
		String strCommissionerIDFiledid="C700020048";//Assginee的代办人登陆名字段id
		String strCloseBaseSchemaFiledid="C700020002";//工单Schema
		
		return stringBuffer.toString();
	}
	
	/**
	 * 生成同组关单设置
	 * @param p_TblAliasName
	 * @param p_strLoginName
	 * @param p_BaeSchemas
	 * @return
	 */
	private String getGroupUserCloseBaseGroup(String p_TblAliasName,String p_strLoginName,String p_BaeSchemas)
	{
		if(p_TblAliasName==null)
			p_TblAliasName="";
		if(!p_TblAliasName.trim().equals(""))
			p_TblAliasName+=".";
		String strCloseGroupFiledid="C700020051";//关单组字段id
		String strCloseBaseSchemaFiledid="C700020002";//关单Schema
		
		StringBuffer stringBuffer=new StringBuffer();
		ConfigUserCloseBaseGroup m_ConfigUserCloseBaseGroup=new ConfigUserCloseBaseGroup();
		ParConfigUserCloseBaseGroupModel  m_ParCloseBaseGroupModel=new ParConfigUserCloseBaseGroupModel();
		m_ParCloseBaseGroupModel.setAssgineeID(p_strLoginName);
		m_ParCloseBaseGroupModel.setBaseCategorySchama(p_BaeSchemas);
		//查询关单组
		List m_CloseGroupList=m_ConfigUserCloseBaseGroup.getList(m_ParCloseBaseGroupModel,0,0);
		int row=0;
		if(m_CloseGroupList!=null)
			row=m_CloseGroupList.size();
		String strGroupID;
		String strBaseSchema;
		int isAddOr=0;
		for(int i=0;i<row;i++)
		{
			ConfigUserCloseBaseGroupModel m_CloseGroupModel=(ConfigUserCloseBaseGroupModel)m_CloseGroupList.get(i);
			strGroupID=m_CloseGroupModel.getCloseBaseGroupID();
			strBaseSchema=m_CloseGroupModel.getBaseCategorySchama();
			if(!strGroupID.trim().equals(""))
			{
				if(!strBaseSchema.equals(""))
				{
					if(isAddOr==1)
						stringBuffer.append(" or (");
					else
						stringBuffer.append(" (");
					stringBuffer.append(p_TblAliasName+strCloseGroupFiledid+"='"+strGroupID+"'");
					stringBuffer.append(" and "+p_TblAliasName+strCloseBaseSchemaFiledid+"='"+strBaseSchema+"'");
					stringBuffer.append(" )");
				}
				else
				{
					if(isAddOr==1)
						stringBuffer.append(" or ");						
					stringBuffer.append(p_TblAliasName+strCloseGroupFiledid+"='"+strGroupID+"'");
				}
				isAddOr=1;
			}//if(!strGroupID.trim().equals(""))
			
		}//for(int i=0;i<row;i++)
		String reString=stringBuffer.toString();
		if(!reString.equals(""))
			reString="("+reString+")";
		return reString;
	}
	
	
	
	/**
	 * 根据用户权限组记录集生成查询用户组数据的sql语句条件
	 * @param tblFiled 查询的表的字段
	 * @param p_strGroup 
	 * @return
	 */
	private  String getGroupCondition(String tblFiled,String p_strLoginName)
	{
		if(p_strLoginName==null || p_strLoginName.trim().equals(""))
			return "";
		String strRe;
		String strGroupIDs="";
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblUser);
		//查询用户组
		String strSql="select C104 groupid from "+strTblName+" where C101='"+p_strLoginName+"'";
		//System.out.println("ParDealProcess.GetPorcessTypeSql SQL语句："+strSql);
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet m_GroupRs = null;
		try{
			 stm=m_dbConsole.GetStatement();
			 m_GroupRs = m_dbConsole.executeResultSet(stm, strSql);
			if(m_GroupRs.next())
			{
				//700020008	GroupID
				strGroupIDs=m_GroupRs.getString("groupid");
				if(strGroupIDs==null)
					strGroupIDs="";
			}
		}catch(Exception ex)
		{
			System.err.println("ParDealProcess.GetPorcessTypeSql 方法："+ex.getMessage());
			ex.printStackTrace();				
		}
		finally
		{
			
			try {
				if (m_GroupRs != null)
					m_GroupRs.close();

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			
			try {
				if (stm != null)
					stm.close();

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}			
			m_dbConsole.closeConn();
		}
		StringBuffer sqlString=new StringBuffer();
		sqlString.append(" 1>2 ");
		//以英文分号为分割
		String[] strAry =strGroupIDs.split(";");
		try{
			for(int index=0;index<strAry.length;index++)
			{
				sqlString.append(" or ");
				sqlString.append(tblFiled);
				sqlString.append("='");
				sqlString.append(strAry[index]);
				sqlString.append("' ");
			}
		}catch(Exception ex)
		{
			System.err.println("ParDealProcess.getGroupCondition 方法："+ex.getMessage());
			ex.printStackTrace();				
		}
		strRe=sqlString.toString();
		
		strRe="("+strRe+")";
		return sqlString.toString();
	}	
	
	/**
	 * 
	 * @param p_ProcessType
	 * @return
	 */
	private String GetPorcessTypeSql(String p_TblAliasName,int p_ProcessType)
	{
		String strTemp="";
		String m_TblAliasName=p_TblAliasName;
		if(m_TblAliasName==null)
			m_TblAliasName="";
		if(!m_TblAliasName.trim().equals(""))
			m_TblAliasName+=".";
		String str_TaskPresonID="";
		//任务处理人
		str_TaskPresonID=getTasekPersonID();
		
		StringBuffer sqlString = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		switch(p_ProcessType)
		{
		case Constants.ProcessWaitConfirm://查询抄送（copy）待处理任务
			//700020019 FlagType 取值为：0主办、1协办、2抄送、3审批、4质检、20、复核;  700020020	FlagActive 1活动;
			//700020043:ProcessType DEAL处理 AUDITING审批
			if(Constants.WaitConfirmAction.trim().equals(""))
			{
				sqlString.append(" and "+m_TblAliasName+"C700020019='2'");
			}
			else
			{
				String strConfirmAciton=UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019","OR:"+Constants.WaitConfirmAction);
				if(!strConfirmAciton.trim().equals(""))
					sqlString.append(strConfirmAciton);
			}
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			if(!str_TaskPresonID.trim().equals(""))
			{
				//C700020006:AssgineeID 700020010: DealerID  
				//700020048 CommissionerID  Assginee的代办人登陆名 改字段是后加的(2007-03-09)
				sqlString.append(" and ( "  );
				sqlString.append("(("+m_TblAliasName+"C700020006='"+str_TaskPresonID+"' or "+m_TblAliasName+"C700020048='"+str_TaskPresonID+"' ) and "+m_TblAliasName+"C700020010 is null) ");
				sqlString.append(" or ("+m_TblAliasName+"C700020010='"+str_TaskPresonID+"') ");
				//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
				//取的本组信息
				strTemp=getGroupCondition(""+m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))
					//700020016	BgDate　领单时间
					sqlString.append(" or ( "+m_TblAliasName+"C700020016 is null and  ("+strTemp+")) ");
				sqlString.append(") "  );
			}
			break;
		case Constants.ProcessWaitDeal://查询待处理(主办和协办)的任务 从处理环节表(T74 UltraProcess:App_DealProcess)中查找
			//条件：分派给我的任务，并且我未受理(受单时间为nll表示未受理)
			//700020019 FlagType 取值为：0主办、1协办、2抄送、3审批、4质检、20、复核;  700020020	FlagActive 1活动;
			//700020043:ProcessType DEAL处理 AUDITING审批			
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			if(Constants.WaitDealAction.trim().equals(""))
			{
				sqlString.append(" and (");
				sqlString.append(m_TblAliasName+"C700020019='0' or "+m_TblAliasName+"C700020019='1'");
				sqlString.append("  or "+m_TblAliasName+"C700020019='4'");
				sqlString.append("      ) ");
			}
			else
			{
				String strAction=UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019","OR:"+Constants.WaitDealAction);
				if(!strAction.trim().equals(""))
				{
					sqlString.append(strAction);
				}
				
			}
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyWaitDeal));			
			break;
		case Constants.ProcessDealing://查询处理中(主办和协办)的任务 从处理环节表(T74 UltraProcess:App_DealProcess)中查找
			
			//条件：分派给我的任务，并且我已受理的任务(受单时间不为nll表示已受理)
			//700020019 FlagType 取值为：0主办、1协办、2抄送、3审批、4质检、20、复核;  
			// 700020020	FlagActive 1活动;
			//700020043:ProcessType DEAL处理 AUDITING审批		
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			if(Constants.WaitDealAction.trim().equals(""))
			{
				sqlString.append(" and ("+m_TblAliasName+"C700020019='0' or "+m_TblAliasName+"C700020019='1'");
				sqlString.append("  or "+m_TblAliasName+"C700020019='4'");
				sqlString.append("      ) ");
				
			}
			else
			{
				String strAction=UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019","OR:"+Constants.WaitDealAction);
				if(!strAction.trim().equals(""))
				{
					sqlString.append(strAction);
				}				
			}
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyDealing));
			
			
			break;
		case Constants.ProcessWaitAuditing://查询等待审批的任务 从审批环节表(T81 UltraProcess:App_AuditingProcess)中查找 
			//条件：
			//700020019 FlagType 取值为：3审批;  700020020	FlagActive 1活动;
			//700020043:ProcessType  AUDITING审批
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' ");
			if(Constants.WaitAuditing.trim().equals(""))
			{
				sqlString.append(" and "+m_TblAliasName+"C700020019='3' ");
			}else
			{
				String strAction=UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019","OR:"+Constants.WaitAuditing);
				if(!strAction.trim().equals(""))
				{
					sqlString.append(strAction);
				}				
			}
			sqlString.append(" and "+m_TblAliasName+"C700020043='AUDITING' ");
			//C700020006:AssgineeID 700020010: DealerID  
			if(!str_TaskPresonID.trim().equals(""))
			{
				sqlString.append(" and ( "  );
				sqlString.append("(("+m_TblAliasName+"C700020006='"+str_TaskPresonID+"' or "+m_TblAliasName+"C700020048='"+str_TaskPresonID+"' ) and "+m_TblAliasName+"C700020010 is null) ");
				sqlString.append(" or ("+m_TblAliasName+"C700020010='"+str_TaskPresonID+"') ");
				
				strTemp=getGroupCondition(""+m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))				
					//700020016	BgDate　领单时间
					sqlString.append(" or ("+m_TblAliasName+"C700020016 is null and ("+strTemp+")) ");
				sqlString.append(") "  );
			}
			break;
			
		case Constants.ProcessMyCreate://查询某人所有我建立的工单信息的条件(从处理环节,不包含复制品) 
			//700020006 AssgineeID;
			//700020004	PrevPhaseNo 前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
			//700020022	FlagDuplicated	是否复制出来的记录；标示本条记录是否复制出来的1：是；0否，默认为：0
			if(!str_TaskPresonID.trim().equals(""))
			{
				sqlString.append(" and "+m_TblAliasName+"C700020006='"+str_TaskPresonID+"' ");
			}
			sqlString.append(" and "+m_TblAliasName+"C700020004='BEGIN' and "+m_TblAliasName+"C700020022='0' ");
			break;
		
		case Constants.ProcessMyWaitDeal://我所有待处理的所有工单(未受理)
			//700020004	PrevPhaseNo 前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
			//sqlString.append(" and "+m_TblAliasName+"C700020004<>'BEGIN' ");
			//sqlString.append("and "+m_TblAliasName+"C700020004!='BEGIN' ");			
			//sqlString.append(" and ( "  );
			// 700020016 BgDate 领单时间 700020017	EdDate	操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020016 is null and C700020017 is null ");		
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			//C700020006:AssgineeID 700020010: DealerID  
			////700020048 CommissionerID  Assginee的代办人登陆名 改字段是后加的(2007-03-09)
			if(!str_TaskPresonID.trim().equals(""))
			{
				sqlString.append(" and((("+m_TblAliasName+"C700020006='"+str_TaskPresonID+"' or "+m_TblAliasName+"C700020048='"+str_TaskPresonID+"' ) and "+m_TblAliasName+"C700020010 is null) ");
				sqlString.append(" or ("+m_TblAliasName+"C700020010='"+str_TaskPresonID+"') ");
				
				//同组关单(2007-03-09)
				//700000016 BaseIsAllowLogGroup 是否允许同组关单：、0否、1是
				//700020051 CloseBaseSamenessGroupID 同组关单的组ID
				strTemp=getGroupUserCloseBaseGroup(p_TblAliasName,str_TaskPresonID,this.getProcessBaseSchema());
				if(!strTemp.equals(""))
				{
					sqlString.append(" or ");
					sqlString.append(strTemp);
				}
				//本组处理的工单
				strTemp=getGroupCondition(m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))					
					//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
					sqlString.append(" or ("+m_TblAliasName+"C700020016 is null and ("+strTemp+")) ");
				sqlString.append(") "  );	
			}
			break;
		case Constants.ProcessMyDealing://我所有处理中的所有工单(已受理)
			//700020004	PrevPhaseNo 前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
			
			//sqlString.append("and "+m_TblAliasName+"C700020004!='BEGIN' ");
			//C700020006:AssgineeID 700020010: DealerID  
			//sqlString.append(" and ( "  );
			// 700020016 BgDate 领单时间;	700020017	EdDate	操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020016>0  and C700020017 is null ");
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			//700020048 CommissionerID  Assginee的代办人登陆名 改字段是后加的(2007-03-09)			
			sqlString.append(" and  ((("+m_TblAliasName+"C700020006='"+str_TaskPresonID+"' or "+m_TblAliasName+"C700020048='"+str_TaskPresonID+"' ) and "+m_TblAliasName+"C700020010 is null) ");
			sqlString.append(" or ("+m_TblAliasName+"C700020010='"+str_TaskPresonID+"') ");
			
			
			
			//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
			//IsGroupSnatch	700020049 工单派发到组时，该组是否是抢单	1：是；0否，默认为：0
			//如果不是同组抢单则可以处理本组其它人正在处理的工单
			if(!str_TaskPresonID.trim().equals(""))
			{
				strTemp=getGroupCondition(""+m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))					
					sqlString.append(" or ( "+m_TblAliasName+"C700020049=0 and ("+strTemp+")) ");
				//else //有人信息没有组则不查同组抢单问题
				//	sqlString.append(" or ( "+m_TblAliasName+"C700020049=0) ");
			}
			else
				sqlString.append(" or ( "+m_TblAliasName+"C700020049=0) ");
			
			sqlString.append(") "  );
			
			break;
		case Constants.ProcessMyWaitDeallAll://我所有的待处理(待处理(未受理)+处理中(已受理))的工单(不管是什么处理类型)
			sqlString.append(" and (( 1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyWaitDeal));
			sqlString.append(" ) or (1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyDealing));
			sqlString.append(" ))");
			break;
		case Constants.ProcessMyDeallAll://我处理过的工单(我处理过，不管是否处理完成)
			/**
			 * 处理逻辑
			 * 1: 从Process日志中查询出我处理过的ProcessID
			 * 2：根据ProcessID从处理环节表中查询出相应Baseid和BaseSchema
			 * 3：根据Baseid和BaseSchema查询出工单信息(在Base类中完成)
			 */
			
			sqlString.append(" and (");
			ParDealProcessLogModel m_ParDealProcessLogModel=new ParDealProcessLogModel();
			String strLogTblName=""; 
			m_ParDealProcessLogModel.setlogUserID(this.getTasekPersonID());
			m_ParDealProcessLogModel.setExtendSql(this.getTasekPersonExtendSql());
			if(this.getEdDate()>0)
			{
				m_ParDealProcessLogModel.setStDate(getEdDate());
				this.setEdDate(0);
			}
			if(this.getEdDateBegin()>0)
			{
				m_ParDealProcessLogModel.setStDateBegin(getEdDateBegin());
				setEdDateBegin(0);
			}
			if(this.getEdDateEnd()>0)
			{
				m_ParDealProcessLogModel.setStDateEnd(getEdDateEnd());
				setEdDateEnd(0);
			}
			
			if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				m_ParDealProcessLogModel.setIsArchive(getIsArchive());
				if(this.getIsArchive()==1)
					strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
				else if(this.getIsArchive()==0)
					strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
				sqlString.append(" exists ( ");
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					String dpTblName="";
					if(getIsArchive()==1)
						dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
					else
						dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				sqlString.append(m_ParDealProcessLogModel.GetWhereSql());
				sqlString.append(" ) ");
			}
			else//if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				
				String dpTblName="";
				sqlString.append(" exists ( ");
				
				//查询当前活动表中的信息
				m_ParDealProcessLogModel.setIsArchive(0);
				strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);				
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				sqlString.append(m_ParDealProcessLogModel.GetWhereSql());
				
				sqlString.append(" union all ");
				
				//查询存入历史数据表中的信息
				m_ParDealProcessLogModel.setIsArchive(1);
				strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);				
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				
				sqlString.append(m_ParDealProcessLogModel.GetWhereSql());	
				
				sqlString.append(" ) ");				
				
			}//if(this.getIsArchive()==0||this.getIsArchive()==1)
			
			sqlString.append(")");
			break;
			
		case Constants.ProcessMyGroupDeallAll://我处理过的工单(我处理过，不管是否处理完成)
			
			/**
			 * 处理逻辑
			 * 1: 从Process日志中查询出我处理过的ProcessID
			 * 2：根据ProcessID从处理环节表中查询出相应Baseid和BaseSchema
			 * 3：根据Baseid和BaseSchema查询出工单信息(在Base类中完成)
			 */
			sqlString.append(" and (");
			ParDealProcessLogModel m_ParDealProcessLogModel2=new ParDealProcessLogModel();
			String strLogTblName2=""; 
			//m_ParDealProcessLogModel2.setlogUserID(this.getTasekPersonID());
			if(this.getEdDate()>0)
			{
				m_ParDealProcessLogModel2.setStDate(getEdDate());
				this.setEdDate(0);
			}
			if(this.getEdDateBegin()>0)
			{
				m_ParDealProcessLogModel2.setStDateBegin(getEdDateBegin());
				setEdDateBegin(0);
			}
			if(this.getEdDateEnd()>0)
			{
				m_ParDealProcessLogModel2.setStDateEnd(getEdDateEnd());
				setEdDateEnd(0);
			}
			
			if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				m_ParDealProcessLogModel2.setIsArchive(getIsArchive());
				if(this.getIsArchive()==1)
					strLogTblName2=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
				else if(this.getIsArchive()==0)
					strLogTblName2=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
				sqlString.append(" exists ( ");
				sqlString.append("select 1 from "+strLogTblName2+" DealLog  where 1=1 " );
				
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				String dpTblName="";
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					
					if(getIsArchive()==1)
						dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
					else
						dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				sqlString.append(m_ParDealProcessLogModel2.GetWhereSql());
				
				String personID=FormatString.CheckNullString(getTasekPersonID());
				if(!personID.trim().equals(""))
				{
					String tblSysGroupUser=m_RemedyDBOp.GetRemedyTableName("UltraProcess:SysGroupUser");
					String tblSysUser=m_RemedyDBOp.GetRemedyTableName(Constants.TblSysUser);
					
					SysUser m_SysUser=new SysUser();
					SysUserModel m_SysUserModel=m_SysUser.getOneForKey(this.getTasekPersonID());
					String strGroupids="";
					if(m_SysUserModel!=null)
					{
						String strGroupid=FormatString.CheckNullString(m_SysUserModel.getUser_BelongGroupID());
						String[] strAry=strGroupid.split(";");
						 strGroupids="";
						for(int i=0;i<strAry.length;i++)
						{
							if(!strAry[i].trim().equals(""))
							{
								if(strGroupids.trim().equals(""))
									strGroupids="'"+strAry[i]+"'";
								else
									strGroupids+=",'"+strAry[i]+"'";
							}
						}
					}
					
					if(strGroupids.trim().equals(""))
						strGroupids="'zzzzz'";
					//查询本组的所有人
					//700020404	logUserID	记录的用户登陆名
					sqlString.append(" and C700020404 in(select C630000001 from "+tblSysUser+" where C1 in(select C620000028 from "+tblSysGroupUser+" where C620000027 IN("+strGroupids+") ) ) )");
					//sqlString.append(" ) ");
				}
			}
			else//if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				
				String dpTblName="";
				sqlString.append(" exists ( ");
				
				//查询当前活动表中的信息

				m_ParDealProcessLogModel2.setIsArchive(0);
				strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);				
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				sqlString.append(m_ParDealProcessLogModel2.GetWhereSql());
				
				sqlString.append(" union all ");
				
				//查询存入历史数据表中的信息

				m_ParDealProcessLogModel2.setIsArchive(1);
				strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);				
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				
				sqlString.append(m_ParDealProcessLogModel2.GetWhereSql());	
				
				sqlString.append(" ) ");				
				
			}//if(this.getIsArchive()==0||this.getIsArchive()==1)
			
			sqlString.append(")");			
			
			break;			
			
		case Constants.ProcessMyDeallAllNotIncludeNew:
			//描述：我处理过的工单(我处理过，不管是否处理完成。包括不包括自己建单(处理)的工单)
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyDeallAll));
			//将新建工单排除
			sqlString.append(" and not exists ( ");
			String dpTblName="";
			if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				if(getIsArchive()==1)
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				else
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				if(m_TblAliasName.trim().equals(""))
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+dpTblName+".C1 " );
				else
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+m_TblAliasName+"C1 " );
				//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
				sqlString.append(" and C700020004='BEGIN'");
				//700020010	DealerID 人登陆名
				sqlString.append(" and C700020010='"+getTasekPersonID()+"'");
				
			}
			else
			{
				dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				if(m_TblAliasName.trim().equals(""))
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+dpTblName+".C1 " );
				else
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+m_TblAliasName+"C1 " );
				//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
				sqlString.append(" and C700020004='BEGIN'");
				//700020010	DealerID 人登陆名
				sqlString.append(" and C700020010='"+getTasekPersonID()+"'");
				
				sqlString.append(" union all ");
				
				dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				if(m_TblAliasName.trim().equals(""))
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+dpTblName+".C1 " );
				else
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+m_TblAliasName+"C1 " );
				//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
				sqlString.append(" and C700020004='BEGIN'");	
				//700020010	DealerID 人登陆名
				sqlString.append(" and C700020010='"+getTasekPersonID()+"'");				
				
			}
			sqlString.append(") ");
			
			break;
		case Constants.ProcessMyDealAndIsFinished://描述：我处理完成的工单(我处理并且已处理完成)
			/**
			 * 逻辑：
			 */
			sqlString.append(" and ( ");
			//700020010: DealerID  
			sqlString.append(m_TblAliasName+"C700020010='"+str_TaskPresonID+"' ");
			//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
			//700020017	EdDate 操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020017 is not null ");
			sqlString.append(") ");			
			break;
		case Constants.ProcessMyAuditingAndIsFinished://描述：我审批过的工单(我审批过，并且已完成的。）
			sqlString.append(" and ( ");
			//700020010: DealerID  
			sqlString.append(m_TblAliasName+"C700020010='"+str_TaskPresonID+"' ");
			//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
			//700020017	EdDate 操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020017 is not null ");
			sqlString.append(") ");			
			break;
			
		case Constants.ProcessMyAssign://描述：我(已)派发的工单
			sqlString.append(" and  ");
			//700020010: DealerID  
			sqlString.append(m_TblAliasName+"C700020010='"+str_TaskPresonID+"' ");
			//700020011	ProcessStatus 记录状态描述
			sqlString.append(" and "+m_TblAliasName+"C700020011='派发' ");

			break;
		case Constants.ProcessDeal://处理=待处理(未受理)+处理中(已受理)
			sqlString.append(" and ((");
			sqlString.append(" 1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessWaitDeal));
			sqlString.append(")");
			sqlString.append(" or (");
			sqlString.append(" 1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessDealing));
			sqlString.append("))");
			break;
		default:				
			break;			
		}
		return sqlString.toString();
	}	
	
	public ParDealProcess()
	{
		
	}
	public long getEdDate() {
		return EdDate;
	}
	public void setEdDate(long edDate) {
		EdDate = edDate;
	}
	public long getEdDateBegin() {
		return EdDateBegin;
	}
	public void setEdDateBegin(long edDateBegin) {
		EdDateBegin = edDateBegin;
	}
	public long getEdDateEnd() {
		return EdDateEnd;
	}
	public void setEdDateEnd(long edDateEnd) {
		EdDateEnd = edDateEnd;
	}
	
	//返回排序字符串

	public String getOrderbyFiledNameString() 
	{
		String strRe="";
		if(!OrderbyFiledNameString.trim().equals(""))
		{
			//如果升序
			if(OrderByType==0)
				strRe=" order by "+OrderbyFiledNameString;
			else
			{
				String[] strAry =OrderbyFiledNameString.split(",");
				
				for(int index=0;index<strAry.length;index++)
				{	if(strRe.trim().equals(""))
						strRe+= strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return (strRe);
	}
	
	
	//用于排序字段的名称
	private String OrderbyFiledNameString="";
	//排序类型 0升序　否则为降序
	private int OrderByType=0;
	/**
	 * 设置排序字段
	 * @param p_strOrderByFiledNameString
	 * @param p_intOrderByType 0 升序 否则为降序
	 */
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString,int p_intOrderByType) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=p_intOrderByType;
	}	
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=0;
	}	
	
	/**
	 * 查询扩展得sql语句
	 * @return
	 */
	public String getExtendSql() {
		if(ExtendSql==null)
			ExtendSql="";
		return ExtendSql;
	}
	public void setExtendSql(String extendSql) {
		ExtendSql = extendSql;
	}
	
	/**
	 * 对于查询我处理过的工单，因为是根据处理日志表来查询的
	 * 所以查询人员信息时用setTasekPersonExtendSql来传递查询sql来区分
	 * @return
	 */
	public String getTasekPersonExtendSql() {
		if(TasekPersonExtendSql==null)
			TasekPersonExtendSql="";
		return TasekPersonExtendSql;
	}
	public void setTasekPersonExtendSql(String tasekPersonExtendSql) {
		TasekPersonExtendSql = tasekPersonExtendSql;
	}	
	
	private List m_ConfigQueryDetailList=null;
	
	public void setConfigQueryDetail(List p_ConfigQueryDetailList)
	{
		m_ConfigQueryDetailList=p_ConfigQueryDetailList;
	}
	public String getBgDateString() {
		return BgDateString;
	}
	public void setBgDateString(String bgDateString) {
		BgDateString = bgDateString;
	}
	public String getEndDateString() {
		return EndDateString;
	}
	public void setEndDateString(String endDateString) {
		EndDateString = endDateString;
	}
	public String getStDateString() {
		return StDateString;
	}
	public void setStDateString(String stDateString) {
		StDateString = stDateString;
	}
	
	
	/***为变量绑定而加***/
	private List FiledInfoList;
	
	public List getFiledInfoList() {
		
		List FiledInfoList=setFiledList();
		return FiledInfoList;
	}
	public void setFiledInfoList(List filedInfoList) {
		FiledInfoList = filedInfoList;
	}	
	
	public List getContionFiledInfoList() {
		List reList=new ArrayList();
		int listCount=0;
		if(FiledInfoList!=null)
			listCount=FiledInfoList.size();
		for(int i=0;i<listCount;i++)
		{
			reList.add(FiledInfoList.get(i));
		}
		
		listCount=0;
		List m_FiledList=setFiledList();
		if(m_FiledList!=null)
			listCount=m_FiledList.size();
		for(int i=0;i<listCount;i++)
		{
			reList.add(m_FiledList.get(i));
		}
				
		return reList;
	}	
	private List setFiledList()
	{
		
		List m_FiledInfoList=new ArrayList();
		String tblAlias="deal";
		if(!tblAlias.equals(""))
			tblAlias+=".";

		ParFiledInfo m_ParFiledInfo;
		PariseUntil m_PariseUntil=new PariseUntil();
		//C1	ProcessID		本记录的唯一标识，创建是自动形成，无业务含义
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C1",getProcessID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);	
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C1",getProcessID()));
		//700020001	ProcessBaseID			指向主工单记录的指针
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020001",getProcessBaseID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020001",getProcessBaseID()));
		//700020002	ProcessBaseSchema	指向主工单记录的指针
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020002",getProcessBaseSchema());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020002",getProcessBaseSchema()));
		//700020003	PhaseNo			环节号，插入记录时，如果为空，则产生一个随机数，插入该字段。新建记录时该号默认取随机的值；从模版建工单时该号不变；已复制的形式（Copy to New）新建环节时与原环节号相同。
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020003",getPhaseNo());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);	
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020003",getPhaseNo()));
		//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020004",getPrevPhaseNo());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);	
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020004",getPrevPhaseNo()));
		//700020045	CreateByUserID		选择派单的对象的人登录名，当该条记录的FlagActive不等于6时，清空
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020045",getCreateByUserID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020045",getCreateByUserID()));
		
		//700020010 DealerID
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020010",getDealerID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);		
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020010",getDealerID()));
		//700020009	Dealer
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020009",getDealer());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020009",getDealer()));

		//700020007	Group		组，本记录的主人，派单的对象
		//700020008	GroupID		组ID
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020007",getGroup());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020007",getGroup()));
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020008",getGroupID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);		
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020008",getGroupID()));
		 
		
		//700020005	Assginee		人名，本记录的主人，派单的对象
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020005",getAssginee());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020005",getAssginee()));
		//700020006	AssgineeID		人登陆名
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020006",getAssgineeID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);		
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020006",getAssgineeID()));
		
		//700020011	ProcessStatus				本记录状态描述。
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020011",getProcessStatus());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020011",getProcessStatus()));
		//700020012	AssignOverTimeDate	派单时限（接到工单后，超过这个时间就不允许往下派单，只能自己处理，可以为空，为空时即为没有时间限制）
		//700020012只有Dealprocess中，AuditingProcess中没有
		if(getAssignOverTimeDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020012",String.valueOf(getAssignOverTimeDate()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);
		}
		//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020012",getAssignOverTimeDate()));
		
		//700020013	AcceptOverTimeDate		受理时限
		if(getAcceptOverTimeDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020013",String.valueOf(getAcceptOverTimeDate()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);
		}
		//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020013",getAcceptOverTimeDate()));
		//受理时限(范围)开始时间

		if(getAcceptOverTimeDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020013",">=:"+getAcceptOverTimeDateBegin());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
		}
			//sqlString.append(" and "+strTblPrefix+"C700020013>="+String.valueOf(getAcceptOverTimeDateBegin()));
		//受理时限(范围)结束时间
		if(getAcceptOverTimeDateEnd()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020013","<=:"+getAcceptOverTimeDateEnd());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and "+strTblPrefix+"C700020013<="+String.valueOf(getAcceptOverTimeDateEnd()));	
		}
		//700020014	DealOverTimeDate		处理时限
		if(getDealOverTimeDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020014",String.valueOf(getDealOverTimeDate()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and "+strTblPrefix+"C700020014="+String.valueOf(getDealOverTimeDate()));
		}
		//处理时限(范围)开始时间

		if(getDealOverTimeDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020014",">=:"+getAcceptOverTimeDateBegin());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and "+strTblPrefix+"C700020014>="+String.valueOf(getDealOverTimeDateBegin()));
		}
		//处理时限(范围)结束时间
		if(getDealOverTimeDateEnd()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020014","<=:"+getDealOverTimeDateEnd());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			
			//sqlString.append(" and "+strTblPrefix+"C700020014<="+String.valueOf(getDealOverTimeDateEnd()));
		}
		//700020016	BgDate		领单时间
		//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020016",getBgDate()));
		if(getBgDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020016",String.valueOf(getBgDate()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);		
		}
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020016",getBgDateString()));
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020016",getBgDateString());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);		
		//领单时间(范围)开始时间

		if(getBgDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020016",">=:"+getBgDateBegin());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and "+strTblPrefix+"C700020016>="+String.valueOf(getBgDateBegin()));
			
		}
		//领单时间(范围)结束时间
		if(getBgDateEnd()>0 && getBgDateEnd()!=999)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020016","<=:"+getBgDateEnd());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and "+strTblPrefix+"C700020016<="+String.valueOf(getBgDateEnd()));
		}
		else if(BgDateEnd<=0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020016","OR:0,NULL");
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and ("+strTblPrefix+"C700020016=0 or "+strTblPrefix+"C700020016 is null)" );
		}
		
		//700020017	EdDate		操作完成时间
		if(getEdDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020017",String.valueOf(getEdDate()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);
		}
		//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700020017",getEdDate()));
		//领单时间(范围)开始时间

		if(getEdDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020017",">=:"+getEdDateBegin());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and "+strTblPrefix+"C700020017>="+String.valueOf(getEdDateBegin()));
		}
		//领单时间(范围)结束时间
		if(getEdDateEnd()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020017","<=:"+getEdDateEnd());
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);				
			//sqlString.append(" and "+strTblPrefix+"C700020017<="+String.valueOf(getEdDateEnd()));
		}
		//700020020 FlagActive 是否有效：7未派发，并等待审批，6未派发、未提交审批，5下一步的审批等待，4下一步的处理等待，3提交审批后等待审批完成的等待中，2派发后等待处理完成的等待中，1活动，0已完毕
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020020",getFlagActive());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020020",getFlagActive()));
		
		
		//700020021	FlagPredefined		是否预定义固定流程。只有在保存为模版、完成处理是判断。1：是；0否，默认为：0
		if(getIsGroupSnatch()!=999)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020021",String.valueOf(getFlagPredefined()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);		
			//sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700020021",getFlagPredefined()));
		}
		//700020022	FlagDuplicated		是否复制出来的记录；标示本条记录是否复制出来的1：是；0否，默认为：0
		if(getFlagDuplicated()!=999)
		{		
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020022",String.valueOf(getFlagDuplicated()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);		
			//sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700020022",getFlagDuplicated()));
		}
		//700020019	FlagType  0主办、1协办、2抄送
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020019",String.valueOf(getFlagType()));
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019",getFlagType()));
		//IsGroupSnatch	700020049 工单派发到组时，该组是否是抢单		1：是；0否，默认为：0
		if(getIsGroupSnatch()!=999)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020049",String.valueOf(getIsGroupSnatch()));
			if(m_ParFiledInfo!=null)
				m_FiledInfoList.add(m_ParFiledInfo);
		}
		//sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700020049",getIsGroupSnatch()));
		//System.out.println( sqlString.toString());
		
		//700020047 Commissioner的代办人名，本记录的主人的代办人
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020047",getCommissioner());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020047",getCommissioner()));
		//700020048 CommissionerID Assginee的代办人登陆名
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020048",getCommissionerID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);	
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020048",getCommissionerID()));
		//700020050 CloseBaseSamenessGroup 同组关单的组
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020050",getCloseBaseSamenessGroup());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);		
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020050",getCloseBaseSamenessGroup()));
		//700020051 CloseBaseSamenessGroupID 同组关单的组ID
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020051",getCloseBaseSamenessGroupID());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);		
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020051",getCloseBaseSamenessGroupID()));
		//700020052 Flag32IsToTransfer 本环节是否已经转交出去：0否；1是；默认为0，默认为：0
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020052",getFlag32IsToTransfer());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020052",getFlag32IsToTransfer()));
		//700020053 Flag33IsEndPhase 是否最终处理\审批工单的环节：0否；1是；默认为0，默认为：1			
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020053",getFlag33IsEndPhase());
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);		
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020053",getFlag33IsEndPhase()));
		if(m_ParFiledInfo!=null)
			m_FiledInfoList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C801000101",getProcessNode_DealType()));
		
		
		//查询参数配置类的查询条件
		if(m_ConfigQueryDetailList!=null)
		{
			int listCount=m_ConfigQueryDetailList.size();
			String strValue;
			String strType;
			String logic;
			String strClassName;
			for(int i=0;i<listCount;i++)
			{
				ConfigQueryDetailModel m_ConfigQueryDetailModel=(ConfigQueryDetailModel)m_ConfigQueryDetailList.get(i);
				
				strValue=FormatString.CheckNullString(m_ConfigQueryDetailModel.getEvaluate()); // FormatString.CheckNullString(request.getParameter(m_ConfigQueryDetailModel.getFieldname()));
				strType=FormatString.CheckNullString(m_ConfigQueryDetailModel.getFieldtype()).trim();
				strClassName=FormatString.CheckNullString(m_ConfigQueryDetailModel.getParclassname()).trim().toUpperCase();
				//m_ConfigQueryDetailModel.setEvaluate(strValue);	
				if(!strValue.equals("")&&strClassName.equals("PARDEALPROCESS"))
				{
					logic=m_ConfigQueryDetailModel.getLogicexp();			
					if(!logic.trim().equals(""))
						logic+=":";
					//如果是日期

					if(strType.equals("5"))
					{
						strValue=String.valueOf(FormatTime.FormatDateStringToInt(strValue));
					}
					m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C"+m_ConfigQueryDetailModel.getFieldid(),strValue);
					if(m_ParFiledInfo!=null)
						m_FiledInfoList.add(m_ParFiledInfo);
				}//if(!strValue.equals(""))
			}//for(int i=0;i<listCount;i++)
			
		}//if(m_ConfigQueryDetailList!=null)		
		
		
		return m_FiledInfoList;
		
	}
	 
	
	Hashtable hashValue=null;
	int alueindex=0;
	public ParBindString getPorcessTypeBindSqlList(String p_TblAliasName,int p_ProcessType)
	{
		hashValue=new Hashtable();
		alueindex=0;
		String[] valueAry;
		ParBindString m_ParBindString=new ParBindString();
		String strSql=GetPorcessTypeSqlList(p_TblAliasName,p_ProcessType);
		m_ParBindString.setBingSqlString(strSql);
		int valCols=0;
		if(hashValue!=null)
			valCols=hashValue.size();
		
		if(valCols<=0)
			return m_ParBindString;
		valueAry=new String[valCols];
		for(int i=1;i<=valCols;i++)
		{
			valueAry[i-1]=FormatString.FormatObjectToString(hashValue.get(String.valueOf(i)));
		}
		m_ParBindString.setBindValue(valueAry);
		return m_ParBindString;
	}
	

	private  String[] getGroupIdValue(String p_strLoginName)
	{
		if(p_strLoginName==null || p_strLoginName.trim().equals(""))
			return null;
		String strGroupIDs="";
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblUser);
		//查询用户组

		String strSql="select C104 groupid from "+strTblName+" where C101='"+p_strLoginName+"'";
		//System.out.println("ParDealProcess.GetPorcessTypeSql SQL语句："+strSql);
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet m_GroupRs = null;
		try{
			 stm=m_dbConsole.GetStatement();
			 m_GroupRs = m_dbConsole.executeResultSet(stm, strSql);
			if(m_GroupRs.next())
			{
				//700020008	GroupID
				strGroupIDs=m_GroupRs.getString("groupid");
				if(strGroupIDs==null)
					strGroupIDs="";
			}
		}catch(Exception ex)
		{
			System.err.println("ParDealProcess.GetPorcessTypeSql 方法："+ex.getMessage());
			ex.printStackTrace();				
		}
		finally
		{
			try {
				if (m_GroupRs != null)
					m_GroupRs.close();

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			
			try {
				if (stm != null)
					stm.close();

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}			
			m_dbConsole.closeConn();
		}
		String[] strAry =strGroupIDs.split(";");
		
		return strAry;
	}		
	
	
	private String GetPorcessTypeSqlList(String p_TblAliasName,int p_ProcessType)
	{
		String strTemp;
		String[] strTempAry;
		String m_TblAliasName=p_TblAliasName;
		if(m_TblAliasName==null)
			m_TblAliasName="";
		if(!m_TblAliasName.trim().equals(""))
			m_TblAliasName+=".";
		String str_TaskPresonID="";
		//任务处理人

		str_TaskPresonID=getTasekPersonID();
		
		StringBuffer sqlString = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		switch(p_ProcessType)
		{
		case Constants.ProcessWaitConfirm://查询抄送（copy）待处理任务
			//700020019 FlagType 取值为：0主办、1协办、2抄送、3审批、4质检、20、复核;  700020020	FlagActive 1活动;
			//700020043:ProcessType DEAL处理 AUDITING审批
			if(Constants.WaitConfirmAction.trim().equals(""))
			{
				sqlString.append(" and "+m_TblAliasName+"C700020019='2'");
			}
			else
			{
				String strConfirmAciton=UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019","OR:"+Constants.WaitConfirmAction);
				if(!strConfirmAciton.trim().equals(""))
					sqlString.append(strConfirmAciton);
			}
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			if(!str_TaskPresonID.trim().equals(""))
			{
				//C700020006:AssgineeID 700020010: DealerID  
				//700020048 CommissionerID  Assginee的代办人登陆名 改字段是后加的(2007-03-09)
				sqlString.append(" and ( "  );
				sqlString.append("(("+m_TblAliasName+"C700020006=? or "+m_TblAliasName+"C700020048=? ) and "+m_TblAliasName+"C700020010 is null) ");
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
				sqlString.append(" or ("+m_TblAliasName+"C700020010=?) ");
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);				
				//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
				// 700020008 GroupID
				//取用户的本组ID信息
				strTempAry=getGroupIdValue(str_TaskPresonID);
				int aryLen=0;
				if(strTempAry!=null)
					aryLen=strTempAry.length;
				strTemp="";
				for(int i=0;i<aryLen;i++)
				{
					String groupid=FormatString.CheckNullString(strTempAry[i]);
					if(!groupid.equals(""))
					{
						if(strTemp.equals(""))
							strTemp=m_TblAliasName+"C700020008=?";
						else
							strTemp+=" or "+m_TblAliasName+"C700020008=?";
						
						alueindex++;
						hashValue.put(String.valueOf(alueindex),groupid);							
					}
				}//for(int i=0;i<aryLen;i++)
				if(!strTemp.equals(""))
					//700020016	BgDate　领单时间
					sqlString.append(" or ( "+m_TblAliasName+"C700020016 is null and  ("+strTemp+")) ");
				else
					sqlString.append(" or "+m_TblAliasName+"C700020016 is null ");
				sqlString.append(") "  );
				
				/*
				strTemp=getGroupCondition(""+m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))				
					//700020016	BgDate　领单时间
					sqlString.append(" or ("+m_TblAliasName+"C700020016 is null and ("+strTemp+")) ");
				
				sqlString.append(") "  );
				*/				
				
			}
			break;
		case Constants.ProcessWaitDeal://查询待处理(主办和协办)的任务 从处理环节表(T74 UltraProcess:App_DealProcess)中查找

			//条件：分派给我的任务，并且我未受理(受单时间为nll表示未受理)
			//700020019 FlagType 取值为：0主办、1协办、2抄送、3审批、4质检、20、复核;  700020020	FlagActive 1活动;
			//700020043:ProcessType DEAL处理 AUDITING审批			
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			if(Constants.WaitDealAction.trim().equals(""))
			{
				sqlString.append(" and (");
				sqlString.append(m_TblAliasName+"C700020019='0' or "+m_TblAliasName+"C700020019='1'");
				sqlString.append("  or "+m_TblAliasName+"C700020019='4'");
				sqlString.append("      ) ");
			}
			else
			{
				String strAction=UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019","OR:"+Constants.WaitDealAction);
				if(!strAction.trim().equals(""))
				{
					sqlString.append(strAction);
				}
				
			}
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyWaitDeal));			
			break;
		case Constants.ProcessDealing://查询处理中(主办和协办)的任务 从处理环节表(T74 UltraProcess:App_DealProcess)中查找

			
			//条件：分派给我的任务，并且我已受理的任务(受单时间不为nll表示已受理)
			//700020019 FlagType 取值为：0主办、1协办、2抄送、3审批、4质检、20、复核;  
			// 700020020	FlagActive 1活动;
			//700020043:ProcessType DEAL处理 AUDITING审批		
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			if(Constants.WaitDealAction.trim().equals(""))
			{
				sqlString.append(" and ("+m_TblAliasName+"C700020019='0' or "+m_TblAliasName+"C700020019='1'");
				sqlString.append("  or "+m_TblAliasName+"C700020019='4'");
				sqlString.append("      ) ");
				
			}
			else
			{
				String strAction=UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700020019","OR:"+Constants.WaitDealAction);
				if(!strAction.trim().equals(""))
				{
					sqlString.append(strAction);
				}				
			}
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyDealing));
			
			
			break;
		case Constants.ProcessWaitAuditing://查询等待审批的任务 从审批环节表(T81 UltraProcess:App_AuditingProcess)中查找

			//条件：

			//700020019 FlagType 取值为：3审批;  700020020	FlagActive 1活动;
			//700020043:ProcessType  AUDITING审批				
			sqlString.append(" and "+m_TblAliasName+"C700020019='3'  and "+m_TblAliasName+"C700020020='1'");
			sqlString.append(" and "+m_TblAliasName+"C700020043='AUDITING' ");
			//C700020006:AssgineeID 700020010: DealerID  
			if(!str_TaskPresonID.trim().equals(""))
			{
				sqlString.append(" and ( "  );

				sqlString.append("(("+m_TblAliasName+"C700020006=? or "+m_TblAliasName+"C700020048=? ) and "+m_TblAliasName+"C700020010 is null) ");
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
				sqlString.append(" or ("+m_TblAliasName+"C700020010=?) ");
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);			
				
				//取用户的本组ID信息
				strTempAry=getGroupIdValue(str_TaskPresonID);
				int aryLen=0;
				if(strTempAry!=null)
					aryLen=strTempAry.length;
				strTemp="";
				for(int i=0;i<aryLen;i++)
				{
					String groupid=FormatString.CheckNullString(strTempAry[i]);
					if(!groupid.equals(""))
					{
						if(strTemp.equals(""))
							strTemp=m_TblAliasName+"C700020008=?";
						else
							strTemp+=" or "+m_TblAliasName+"C700020008=?";
						
						alueindex++;
						hashValue.put(String.valueOf(alueindex),groupid);							
					}
				}//for(int i=0;i<aryLen;i++)
				
				if(!strTemp.equals(""))
					//700020016	BgDate　领单时间
					sqlString.append(" or ( "+m_TblAliasName+"C700020016 is null and  ("+strTemp+")) ");
				else
					sqlString.append(" or "+m_TblAliasName+"C700020016 is null ");
				sqlString.append(") " );
				/*
				strTemp=getGroupCondition(""+m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))				
					//700020016	BgDate　领单时间
					sqlString.append(" or ("+m_TblAliasName+"C700020016 is null and ("+strTemp+")) ");
				
				sqlString.append(") "  );
				*/
			}
			break;
			
		case Constants.ProcessMyCreate://查询某人所有我建立的工单信息的条件(从处理环节,不包含复制品) 
			//700020006 AssgineeID;
			//700020004	PrevPhaseNo 前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。

			//700020022	FlagDuplicated	是否复制出来的记录；标示本条记录是否复制出来的1：是；0否，默认为：0
			if(!str_TaskPresonID.trim().equals(""))
			{
				sqlString.append(" and "+m_TblAliasName+"C700020006=? ");
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
			}
			sqlString.append(" and "+m_TblAliasName+"C700020004='BEGIN' and "+m_TblAliasName+"C700020022='0' ");
			break;
		
		case Constants.ProcessMyWaitDeal://我所有待处理的所有工单(未受理)
			//700020004	PrevPhaseNo 前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。

			//sqlString.append(" and "+m_TblAliasName+"C700020004<>'BEGIN' ");
			//sqlString.append("and "+m_TblAliasName+"C700020004!='BEGIN' ");			
			//sqlString.append(" and ( "  );
			// 700020016 BgDate 领单时间 700020017	EdDate	操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020016 is null and C700020017 is null ");		
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			//C700020006:AssgineeID 700020010: DealerID  
			////700020048 CommissionerID  Assginee的代办人登陆名 改字段是后加的(2007-03-09)
			if(!str_TaskPresonID.trim().equals(""))
			{
				sqlString.append(" and((("+m_TblAliasName+"C700020006=? or "+m_TblAliasName+"C700020048=? ) and "+m_TblAliasName+"C700020010 is null) ");
				sqlString.append(" or ("+m_TblAliasName+"C700020010=?) ");
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
				alueindex++;
				hashValue.put(String.valueOf(alueindex),str_TaskPresonID);				
				//同组关单(2007-03-09)
				//700000016 BaseIsAllowLogGroup 是否允许同组关单：、0否、1是

				//700020051 CloseBaseSamenessGroupID 同组关单的组ID
				strTemp=getGroupUserCloseBaseGroup(p_TblAliasName,str_TaskPresonID,this.getProcessBaseSchema());
				if(!strTemp.equals(""))
				{
					sqlString.append(" or ");
					sqlString.append(strTemp);
				}

				//取用户的本组ID信息
				strTempAry=getGroupIdValue(str_TaskPresonID);
				int aryLen=0;
				if(strTempAry!=null)
					aryLen=strTempAry.length;
				strTemp="";
				for(int i=0;i<aryLen;i++)
				{
					String groupid=FormatString.CheckNullString(strTempAry[i]);
					if(!groupid.equals(""))
					{
						if(strTemp.equals(""))
							strTemp=m_TblAliasName+"C700020008=?";
						else
							strTemp+=" or "+m_TblAliasName+"C700020008=?";
						
						alueindex++;
						hashValue.put(String.valueOf(alueindex),groupid);							
					}
				}//for(int i=0;i<aryLen;i++)
				
				if(!strTemp.equals(""))
					//700020016	BgDate　领单时间
					sqlString.append(" or ( "+m_TblAliasName+"C700020016 is null and  ("+strTemp+")) ");
				else
					sqlString.append(" or "+m_TblAliasName+"C700020016 is null ");
				sqlString.append(") " );
				
				/*
				strTemp=getGroupCondition(m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))					
					//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
					sqlString.append(" or ("+m_TblAliasName+"C700020016 is null and ("+strTemp+")) ");
				sqlString.append(") "  );	
				*/
			}
			break;
		case Constants.ProcessMyDealing://我所有处理中的所有工单(已受理)
			//700020004	PrevPhaseNo 前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。

			
			//sqlString.append("and "+m_TblAliasName+"C700020004!='BEGIN' ");
			//C700020006:AssgineeID 700020010: DealerID  
			//sqlString.append(" and ( "  );
			// 700020016 BgDate 领单时间;	700020017	EdDate	操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020016>0  and C700020017 is null ");
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			//700020048 CommissionerID  Assginee的代办人登陆名 改字段是后加的(2007-03-09)			
			sqlString.append(" and  ((("+m_TblAliasName+"C700020006=? or "+m_TblAliasName+"C700020048=? ) and "+m_TblAliasName+"C700020010 is null) ");
			sqlString.append(" or ("+m_TblAliasName+"C700020010=?) ");
			
			alueindex++;
			hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
			alueindex++;
			hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
			alueindex++;
			hashValue.put(String.valueOf(alueindex),str_TaskPresonID);			
			
			//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
			//IsGroupSnatch	700020049 工单派发到组时，该组是否是抢单	1：是；0否，默认为：0
			//如果不是同组抢单则可以处理本组其它人正在处理的工单

			if(!str_TaskPresonID.trim().equals(""))
			{
				
				//取用户的本组ID信息
				strTempAry=getGroupIdValue(str_TaskPresonID);
				int aryLen=0;
				if(strTempAry!=null)
					aryLen=strTempAry.length;
				strTemp="";
				for(int i=0;i<aryLen;i++)
				{
					String groupid=FormatString.CheckNullString(strTempAry[i]);
					if(!groupid.equals(""))
					{
						if(strTemp.equals(""))
							strTemp=m_TblAliasName+"C700020008=?";
						else
							strTemp+=" or "+m_TblAliasName+"C700020008=?";
						
						alueindex++;
						hashValue.put(String.valueOf(alueindex),groupid);							
					}
				}//for(int i=0;i<aryLen;i++)
				
				if(!strTemp.equals(""))
					sqlString.append(" or ( "+m_TblAliasName+"C700020049=0 and  ("+strTemp+")) ");
				//else//有人信息没有组信息则不查同组抢单问题
				//	sqlString.append(" or "+m_TblAliasName+"C700020049=0 ");
					
				/*
				strTemp=getGroupCondition(""+m_TblAliasName+"C700020008",str_TaskPresonID);
				if(!strTemp.equals(""))					
					sqlString.append(" or ( "+m_TblAliasName+"C700020049=0 and ("+strTemp+")) ");
					
				//else //有人信息没有组则不查同组抢单问题
				//	sqlString.append(" or ( "+m_TblAliasName+"C700020049=0) ");
				 * 
				 */
			}
			else
				sqlString.append(" or ( "+m_TblAliasName+"C700020049=0) ");
			
			sqlString.append(") "  );
			
			break;
		case Constants.ProcessMyWaitDeallAll://我所有的待处理(待处理(未受理)+处理中(已受理))的工单(不管是什么处理类型)
			/*
			sqlString.append(" and (( 1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyWaitDeal));
			sqlString.append(" ) or (1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyDealing));
			sqlString.append(" ))");
			*/

			
			//sqlString.append("and "+m_TblAliasName+"C700020004!='BEGIN' ");
			//C700020006:AssgineeID 700020010: DealerID  
			//sqlString.append(" and ( "  );
			// 700020016 BgDate 领单时间;	700020017	EdDate	操作完成时间
			//sqlString.append(" and "+m_TblAliasName+"C700020016>0  and C700020017 is null ");
			sqlString.append(" and C700020017 is null ");
			sqlString.append(" and "+m_TblAliasName+"C700020020='1' and "+m_TblAliasName+"C700020043='DEAL' ");
			//700020048 CommissionerID  Assginee的代办人登陆名 改字段是后加的(2007-03-09)			
			sqlString.append(" and  ((("+m_TblAliasName+"C700020006=? or "+m_TblAliasName+"C700020048=? ) and "+m_TblAliasName+"C700020010 is null) ");
			sqlString.append(" or ("+m_TblAliasName+"C700020010=?) ");
			 
			alueindex++;
			hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
			alueindex++;
			hashValue.put(String.valueOf(alueindex),str_TaskPresonID);
			alueindex++;
			hashValue.put(String.valueOf(alueindex),str_TaskPresonID);			
			
			//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
			//IsGroupSnatch	700020049 工单派发到组时，该组是否是抢单	1：是；0否，默认为：0
			//如果不是同组抢单则可以处理本组其它人正在处理的工单
			/*
			 * 2007-11-21注释该段代码是因为 usd的逻辑为抢单，但目前数据为非抢单了(即C700020049=0 )
			 * 所以注释掉改代码以使逻辑为抢单
			 */
			if(!str_TaskPresonID.trim().equals(""))
			{
				
				//取用户的本组ID信息
				strTempAry=getGroupIdValue(str_TaskPresonID);
				int aryLen=0;
				if(strTempAry!=null)
					aryLen=strTempAry.length;
				strTemp="";
				for(int i=0;i<aryLen;i++)
				{
					String groupid=FormatString.CheckNullString(strTempAry[i]);
					if(!groupid.equals(""))
					{
						if(strTemp.equals(""))
							strTemp=m_TblAliasName+"C700020008=?";
						else
							strTemp+=" or "+m_TblAliasName+"C700020008=?";
						
						alueindex++;
						hashValue.put(String.valueOf(alueindex),groupid);							
					}
				}//for(int i=0;i<aryLen;i++)
				
				if(!strTemp.equals(""))
					sqlString.append(" or ( "+m_TblAliasName+"C700020049=0 and  ("+strTemp+")) ");
			}
			else
				sqlString.append(" or ( "+m_TblAliasName+"C700020049=0) ");
			
			
			sqlString.append(") "  );
			
			
			
			break;
		case Constants.ProcessMyDeallAll://我处理过的工单(我处理过，不管是否处理完成)
			/*
			 * 该参数已作废 建议不要用 建议用 Base类中的
			 * public List getBaseListForBind(ParBaseModel p_ParBaseModel
			 * 	,ParDealProcessLogModel p_ParDealProcesslog,int p_PageNumber,int p_StepRow )
			 * 方法
			 * 
			 */
			/**
			 * 处理逻辑
			 * 1: 从Process日志中查询出我处理过的ProcessID
			 * 2：根据ProcessID从处理环节表中查询出相应Baseid和BaseSchema
			 * 3：根据Baseid和BaseSchema查询出工单信息(在Base类中完成)
			 */
			
			sqlString.append(" and (");
			ParDealProcessLogModel m_ParDealProcessLogModel=new ParDealProcessLogModel();
			String strLogTblName=""; 
			m_ParDealProcessLogModel.setlogUserID(this.getTasekPersonID());
			if(this.getEdDate()>0)
			{
				m_ParDealProcessLogModel.setStDate(getEdDate());
				this.setEdDate(0);
			}
			if(this.getEdDateBegin()>0)
			{
				m_ParDealProcessLogModel.setStDateBegin(getEdDateBegin());
				setEdDateBegin(0);
			}
			if(this.getEdDateEnd()>0)
			{
				m_ParDealProcessLogModel.setStDateEnd(getEdDateEnd());
				setEdDateEnd(0);
			}
			
			if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				m_ParDealProcessLogModel.setIsArchive(getIsArchive());
				if(this.getIsArchive()==1)
					strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
				else if(this.getIsArchive()==0)
					strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
				sqlString.append(" exists ( ");
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					String dpTblName="";
					if(getIsArchive()==1)
						dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
					else
						dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				sqlString.append(m_ParDealProcessLogModel.GetWhereSql());
				sqlString.append(" ) ");
			}
			else//if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				
				String dpTblName="";
				sqlString.append(" exists ( ");
				
				//查询当前活动表中的信息

				m_ParDealProcessLogModel.setIsArchive(0);
				strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);				
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				sqlString.append(m_ParDealProcessLogModel.GetWhereSql());
				
				sqlString.append(" union ");
				
				//查询存入历史数据表中的信息

				m_ParDealProcessLogModel.setIsArchive(1);
				strLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);				
				sqlString.append("select 1 from "+strLogTblName+" DealLog  where 1=1 " );
				//日志表：ProcessID 700020401 指向主工单处理过程记录的指针
				if(!m_TblAliasName.trim().equals(""))
					sqlString.append(" and DealLog.C700020401="+m_TblAliasName+"C1 ");
				else
				{
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
					sqlString.append(" and DealLog.C700020401="+dpTblName+".C1 ");
				}//if(!p_TblAliasName.trim().equals(""))
				
				sqlString.append(m_ParDealProcessLogModel.GetWhereSql());	
				
				sqlString.append(" ) ");				
				
			}//if(this.getIsArchive()==0||this.getIsArchive()==1)
			
			sqlString.append(")");
			break;
		case Constants.ProcessMyDeallAllNotIncludeNew:
			//描述：我处理过的工单(我处理过，不管是否处理完成。包括不包括自己建单(处理)的工单)
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessMyDeallAll));
			//将新建工单排除

			sqlString.append(" and not exists ( ");
			String dpTblName="";
			if(this.getIsArchive()==0||this.getIsArchive()==1)
			{
				if(getIsArchive()==1)
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				else
					dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				if(m_TblAliasName.trim().equals(""))
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+dpTblName+".C1 " );
				else
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+m_TblAliasName+"C1 " );
				//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。

				sqlString.append(" and C700020004='BEGIN'");
				//700020010	DealerID 人登陆名
				sqlString.append(" and C700020010='"+getTasekPersonID()+"'");
				
			}
			else
			{
				dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				if(m_TblAliasName.trim().equals(""))
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+dpTblName+".C1 " );
				else
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+m_TblAliasName+"C1 " );
				//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。

				sqlString.append(" and C700020004='BEGIN'");
				//700020010	DealerID 人登陆名
				sqlString.append(" and C700020010='"+getTasekPersonID()+"'");
				
				sqlString.append(" union ");
				
				dpTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				if(m_TblAliasName.trim().equals(""))
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+dpTblName+".C1 " );
				else
					sqlString.append("select 1 from "+dpTblName+" DProcess where DProcess.C1="+m_TblAliasName+"C1 " );
				//700020004	PrevPhaseNo	前一环节号，在流转表中表示前一环节。在工单新建时，最开始的那条记录的前一环节号为：“BEGIN”；从模版建工单时为空。

				sqlString.append(" and C700020004='BEGIN'");	
				//700020010	DealerID 人登陆名
				sqlString.append(" and C700020010='"+getTasekPersonID()+"'");				
				
			}
			sqlString.append(") ");
			
			break;
		case Constants.ProcessMyDealAndIsFinished://描述：我处理完成的工单(我处理并且已处理完成)
			/**
			 * 逻辑：

			 */
			sqlString.append(" and ( ");
			//700020010: DealerID  
			sqlString.append(m_TblAliasName+"C700020010='"+str_TaskPresonID+"' ");
			//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
			//700020017	EdDate 操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020017 is not null ");
			sqlString.append(") ");			
			break;
		case Constants.ProcessMyAuditingAndIsFinished://描述：我审批过的工单(我审批过，并且已完成的。）
			sqlString.append(" and ( ");
			//700020010: DealerID  
			sqlString.append(m_TblAliasName+"C700020010='"+str_TaskPresonID+"' ");
			//getGroupCondition("C700020008",m_GroupRs.getString("groupid"));
			//700020017	EdDate 操作完成时间
			sqlString.append(" and "+m_TblAliasName+"C700020017 is not null ");
			sqlString.append(") ");			
			break;
			
		case Constants.ProcessMyAssign://描述：我(已)派发的工单

			sqlString.append(" and  ");
			//700020010: DealerID  
			sqlString.append(m_TblAliasName+"C700020010='"+str_TaskPresonID+"' ");
			//700020011	ProcessStatus 记录状态描述

			sqlString.append(" and "+m_TblAliasName+"C700020011='派发' ");

			break;
		case Constants.ProcessDeal://处理=待处理(未受理)+处理中(已受理)
			sqlString.append(" and ((");
			sqlString.append(" 1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessWaitDeal));
			sqlString.append(")");
			sqlString.append(" or (");
			sqlString.append(" 1=1 ");
			sqlString.append(GetPorcessTypeSql(p_TblAliasName,Constants.ProcessDealing));
			sqlString.append("))");
			break;
		default:				
			break;			
		}
		return sqlString.toString();
	}		
	
	

}
