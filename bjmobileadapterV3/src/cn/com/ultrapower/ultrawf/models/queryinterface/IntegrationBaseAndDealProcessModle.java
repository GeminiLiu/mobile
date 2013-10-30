package cn.com.ultrapower.ultrawf.models.queryinterface;

import java.util.Hashtable;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
public class IntegrationBaseAndDealProcessModle {

  
	//工单属性设置区域--Begin--
	private String BaseID;
 
	private String BaseTplID;

	private String BaseSchema;

	private String BaseName;

	private String BaseSN;

	private String BaseCreatorFullName;

	private String BaseCreatorLoginName;

	private long BaseCreateDate;

	private long BaseSendDate;

	private long BaseFinishDate;

	private long BaseCloseDate;

	private String BaseStatus;

	//private String BaseSummary;

	//private String BaseAssigneeS;

	//private String BaseAuditingerS;

	//private String BaseItems;

	//private String BasePriority;

	//private int BaseIsAllowLogGroup;

	//private long BaseAcceptOutTime;

	//private long BaseDealOutTime;

	//private String BaseDescrption;

	private String BaseResult;

	private String BaseCloseSatisfy;

	private String BaseModifyUrl;
	
	private Hashtable hsOwnFiled=new Hashtable();
	//工单特有字段取值，因为每类工单都有自己特有而其它工单没有的字段，为了方便和同一取值采用了该方法
	public void SetOwnerFiled(String p_FiledName,String p_FiledValue)
	{
		hsOwnFiled.put(p_FiledName,p_FiledValue);
	}
	
	public String GetOwnerFiledValue(String p_FiledName)
	{	
		Object obj;
		if(hsOwnFiled!=null)
		{
			obj=hsOwnFiled.get(p_FiledName);
			if(obj!=null)
				return obj.toString();
			else
			{
				p_FiledName=p_FiledName.toUpperCase();
				obj=hsOwnFiled.get(p_FiledName);
				if(obj!=null)
					return obj.toString();
			}
		}
		return "";
	}	
	
	//	本记录的唯一标识，创建是自动形成，无业务含义
	public String getBaseID() {
		return BaseID;
	}

	public void setBaseID(String p_BaseID) {
		BaseID = p_BaseID;
	}

	//	用户选择的固定流程工单模板ID
	public String getBaseTplID() {
		return BaseTplID;
	}

	public void setBaseTplID(String p_BaseTplID) {
		BaseTplID = p_BaseTplID;
	}

	//	指向主工单记录的工单类别的标示（新建模式下默认是工单的Form名）
	public String getBaseSchema() {
		return BaseSchema;
	}

	public void setBaseSchema(String p_BaseSchema) {
		BaseSchema = p_BaseSchema;
	}

	//	工单名称（新建模式下通过工单的Form名到工单类别表中查询）

	public String getBaseName() {
		return BaseName;
	}

	public void setBaseName(String p_BaseName) {
		BaseName = p_BaseName;
	}

	//	工单业务流水号，由（’ID’+’工单类别ID’+’时间’+’天的流水号'）组成（在Submit的Fiter中创建）。

	public String getBaseSN() {
		return BaseSN;
	}

	public void setBaseSN(String p_BaseSN) {
		BaseSN = p_BaseSN;
	}

	//	建单人名（新建模式下打开工单时默认填写）
	public String getBaseCreatorFullName() {
		return BaseCreatorFullName;
	}

	public void setBaseCreatorFullName(String p_BaseCreatorFullName) {
		BaseCreatorFullName = p_BaseCreatorFullName;
	}

	//	建单人登陆名（新建模式下打开工单时默认填写）
	public String getBaseCreatorLoginName() {
		return BaseCreatorLoginName;
	}

	public void setBaseCreatorLoginName(String p_BaseCreatorLoginName) {
		BaseCreatorLoginName = p_BaseCreatorLoginName;
	}

	//	建单时间（新建模式下打开工单时默认填写）
	public long getBaseCreateDate() {
		return BaseCreateDate;
	}

	public void setBaseCreateDate(long p_BaseCreateDate) {
		BaseCreateDate = p_BaseCreateDate;
	}

	//	派单时间
	public long getBaseSendDate() {
		return BaseSendDate;
	}

	public void setBaseSendDate(long p_BaseSendDate) {
		BaseSendDate = p_BaseSendDate;
	}

	//	完成时间
	public long getBaseFinishDate() {
		return BaseFinishDate;
	}

	public void setBaseFinishDate(long p_BaseFinishDate) {
		BaseFinishDate = p_BaseFinishDate;
	}

	//	关闭时间
	public long getBaseCloseDate() {
		return BaseCloseDate;
	}

	public void setBaseCloseDate(long p_BaseCloseDate) {
		BaseCloseDate = p_BaseCloseDate;
	}

	//	状态名（新建模式下打开工单时默认填写为“草稿”）
	public String getBaseStatus() {
		return BaseStatus;
	}

	public void setBaseStatus(String p_BaseStatus) {
		BaseStatus = p_BaseStatus;
	}

	//	主题
	/*public String getBaseSummary() {
		return BaseSummary;
	}

	public void setBaseSummary(String p_BaseSummary) {
		BaseSummary = p_BaseSummary;
	}
*/
	//	当前处理人

//	public String getBaseAssigneeS() {
//		return BaseAssigneeS;
//	}
//
//	public void setBaseAssigneeS(String p_BaseAssigneeS) {
//		BaseAssigneeS = p_BaseAssigneeS;
//	}
//
//	//	当前审批人//
//
//	public String getBaseAuditingerS() {
//		return BaseAuditingerS;
//	}
//
//	public void setBaseAuditingerS(String p_BaseAuditingerS) {
//		BaseAuditingerS = p_BaseAuditingerS;
//	}

	//	专业，类别	/*
	public String getBaseItems() {
		return BaseItems;
	}

	public void setBaseItems(String p_BaseItems) {
		BaseItems = p_BaseItems;
	}

	//	紧急程度

	public String getBasePriority() {
		return BasePriority;
	}

	public void setBasePriority(String p_BasePriority) {
		BasePriority = p_BasePriority;
	}

	//	是否允许同组关单：、0否、1是，默认为0
	public int getBaseIsAllowLogGroup() {
		return BaseIsAllowLogGroup;
	}

	public void setBaseIsAllowLogGroup(int p_BaseIsAllowLogGroup) {
		BaseIsAllowLogGroup = p_BaseIsAllowLogGroup;
	}

	//	工单受理时限
	public long getBaseAcceptOutTime() {
		return BaseAcceptOutTime;
	}

	public void setBaseAcceptOutTime(long p_BaseAcceptOutTime) {
		BaseAcceptOutTime = p_BaseAcceptOutTime;
	}

	//	工单处理时限
	public long getBaseDealOutTime() {
		return BaseDealOutTime;
	}

	public void setBaseDealOutTime(long p_BaseDealOutTime) {
		BaseDealOutTime = p_BaseDealOutTime;
	}

	//	工单描述
	public String getBaseDescrption() {
		return BaseDescrption;
	}

	public void setBaseDescrption(String p_BaseDescrption) {
		BaseDescrption = p_BaseDescrption;
	}
	*/
	//	工单处理记录
	public String getBaseResult() {
		return BaseResult;
	}

	public void setBaseResult(String p_BaseResult) {
		BaseResult = p_BaseResult;
	}

	//	工单关闭的满意度
	public String getBaseCloseSatisfy() {
		return BaseCloseSatisfy;
	}

	public void setBaseCloseSatisfy(String p_BaseCloseSatisfy) {
		BaseCloseSatisfy = p_BaseCloseSatisfy;
	}	
	
	public String getBaseModifyUrl() {
		String strModifyUrl = Constants.REMEDY_QUERY_URL;
		
		strModifyUrl = Constants.REMEDY_QUERY_URL.replaceAll("<REMEDY_FROM>",this.getBaseSchema());
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.getBaseID());
		setBaseModifyUrl(strModifyUrl);
		return BaseModifyUrl;
	}
	
	public String getBaseModifyUrl(String p_ProcessID,String p_ProcessType) {
		String strModifyUrl = Constants.REMEDY_PROCESS_QUERY_URL;
		//System.out.println(strModifyUrl);
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROM>",this.getBaseSchema());
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.getBaseID());
		strModifyUrl = strModifyUrl.replaceAll("<PROCESSID>",p_ProcessID);
		strModifyUrl = strModifyUrl.replaceAll("<PROCESSTYPE>",p_ProcessType);
		//System.out.println(strModifyUrl);
		setBaseModifyUrl(strModifyUrl);
		return BaseModifyUrl;
	}		

	public void setBaseModifyUrl(String baseModifyUrl) {
		BaseModifyUrl = baseModifyUrl;
	}	
	
	
	//	工单属性设置区域--End--	
	
//	DealProcess属性设置区域--Begin--
	private  String ProcessID="";
	private  int FlagType=999;
	private  String FlagTypeName="";
	private  String Assginee;
	private  String AssgineeID;
	private  String Group="";
	private  String GroupID="";
	private  String Dealer="";
	private  String DealerID="";		
	private  int FlagActive=0;
	private  long StDate=0;
	private  long BgDate=0;
	private  String ProcessStatus="";
	private String ProcessDescinfo="";
	private  String ProcessType="";
	private  String PrevPhaseNo="";
	private  String PhaseNo="";
	
public String getPhaseNo() {
		return PhaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		PhaseNo = phaseNo;
	}

	public String getPrevPhaseNo() {
		return PrevPhaseNo;
	}

	public void setPrevPhaseNo(String prevPhaseNo) {
		PrevPhaseNo = prevPhaseNo;
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
	
	//	取值为：0主办、1协办、2抄送
	public void setFlagType(int p_FlagType)
	{
	   FlagType=p_FlagType;
	}		
	public int getFlagType()
	{
	   return FlagType;
	}	
	public String getFlagTypeName()
	{
	   switch(FlagType)
	   {
	   case 0:
		   FlagTypeName="主办";
		   break;
	   case 1:
		   FlagTypeName="协办";
		   break;
	   case 2:
		   FlagTypeName="抄送";
		   break;
	   case 3:
		   FlagTypeName="审批";
		   break;		   
	   case 4:
		   FlagTypeName="质检";
		   break;
	   default:
		   FlagTypeName="";
		   break;
	   }
	   return FlagTypeName;
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
//	人登陆名
	public String getDealerID()
	{
	   return DealerID;
	}
	public void   setDealerID(String p_DealerID)
	{
	   DealerID=p_DealerID;
	}	
	
	//是否有效：7未派发，并等待审批，6未派发、未提交审批，5下一步的审批等待
	//，4下一步的处理等待，3提交审批后等待审批完成的等待中，2派发后等待处理完成的等待中，1活动，0已完毕
	public int getFlagActive()
	{
	   return FlagActive;
	}
	public void   setFlagActive(int p_FlagActive)
	{
		FlagActive=p_FlagActive;
	}	
	//700020015	StDate		创建/生效时间，表示该记录创建/生效时间
	public long getStDate()
	{
	   return StDate;
	}
	public void   setStDate(long p_stdate)
	{
		StDate=p_stdate;
	}		
		
	
	public long getBgDate()
	{
	   return BgDate;
	}
	public void   setBgDate(long p_FlagActive)
	{
		BgDate=p_FlagActive;
	}		
	
	//700020014	DealOverTimeDate		处理时限(只有处理环节有，审批环节是没有的)
	private long DealOverTimeDate=0;
	public long getDealOverTimeDate()
	{
	   return DealOverTimeDate;
	}
	public void   setDealOverTimeDate(long p_DealOverTimeDate)
	{
		DealOverTimeDate=p_DealOverTimeDate;
	}		
	
	public String getProcessStatus()
	{
	   return ProcessStatus;
	}
	public void   setProcessStatus(String p_ProcessStatus)
	{
		ProcessStatus=p_ProcessStatus;	
	}
	
	public String getProcessDescinfo()
	{
	   return ProcessDescinfo;
	}
	public void   setProcessDescinfo(String p_ProcessDescinfo)
	{
		if(p_ProcessDescinfo!=null)
			ProcessDescinfo=p_ProcessDescinfo;	
	}	
	
	
	//是否受理
	public String getIsAccept()
	{
		if(getBgDate()>0)
			return "是";
		else
			return "否";
		
	}
	
	private String Commissioner;//Assginee的代办人名，本记录的主人的代办人
	private String CommissionerID;//Assginee的代办人登陆名
	private String CloseBaseSamenessGroup;//同组关单的组
	private String CloseBaseSamenessGroupID;//同组关单的组ID
	private String IsGroupSnatch;//工单派发到组时，该组是否是抢单	1：是；0否，默认为：0
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
	public String getIsGroupSnatch() {
		return IsGroupSnatch;
	}
	public void setIsGroupSnatch(String isGroupSnatch) {
		IsGroupSnatch = isGroupSnatch;
	}	
	
	
	
//	DealProcess属性设置区域--End--	
	
	
	private String  ProcessOwner;
	//处理组/人
	public String ProcessOwner()
	{
	   ProcessOwner="";
	   if(Assginee!=null && !Assginee.equals(""))
	   {
			   ProcessOwner=Assginee;
	   }
	   else if(Group!=null && !Group.equals(""))
	   {
		   ProcessOwner=Group;
	   }
	   return ProcessOwner;
	}

	//	默认为“Deal”
	public String getProcessType()
	{
	   return ProcessType;
	}
	public void   setProcessType(String p_ProcessType)
	{
	   ProcessType=p_ProcessType;
	}	
	
	//处理超时类型
	//查询Process处理超时情况
	public int getProcessOverTimeType(long longServerDateTime )
	{
		int m_Type=0;
		
		if(getDealOverTimeDate()<=0)
			return m_Type;
		
		//long longServerDate=ServerInfo.GetServerTime();
		//System.out.print("服务器时间：");
		//System.out.println(longServerDate);
		if(longServerDateTime>0)
		{
			
//			超时工单
			if(longServerDateTime>this.getDealOverTimeDate())
			{
				//已完成已超时
				if(this.getFlagActive()==0)
					m_Type=Constants.OverTimeTypeFinishedOvered;
				//未完成已超时
				else if(this.getFlagActive()==1)
					m_Type=Constants.OverTimeTypeNoFinishOvered;
				else
					m_Type=Constants.OverTimeTypeOvered;
			}
			else
			{
				//完成未超时工单
				if(this.getFlagActive()==0)
					m_Type=Constants.OverTimeTypeFinishedNoOver;
				//未完成未超时
				else if(this.getFlagActive()==1)
					m_Type=Constants.OverTimeTypeNoFinishNoOver;	
				else
					m_Type=Constants.OverTimeTypeNoOver;
			}
			
		}
		return m_Type;
	}
	
	//查询工单超时情况
	public int getBaseOverTimeType(long longServerDateTime)
	{
		int m_Type=0;
		//取服务器当前时间
	
		if(longServerDateTime<=0)
			return m_Type;
		if(GetOwnerFiledValue("BaseDealOutTime")==null)
			return m_Type;
		if(GetOwnerFiledValue("BaseDealOutTime").trim().equals(""))
			return m_Type;
		
		long longDealOverDate=Long.parseLong(GetOwnerFiledValue("BaseDealOutTime"));
		if(longDealOverDate==0)
			return m_Type;
		//如果未完成
		if(this.getBaseFinishDate()<=0)
		{
			//超时工单
			if(longServerDateTime>longDealOverDate)
			{
				//未完成已超时
				m_Type=Constants.OverTimeTypeNoFinishOvered;
			}
			else if(longServerDateTime<longDealOverDate)
			{
				//未完成未超时
				m_Type=Constants.OverTimeTypeNoFinishNoOver;
			}
			
		}
		else
		{
			//超时工单
			if(getBaseFinishDate()>longDealOverDate)
			{
				//已完成已超时
				m_Type=Constants.OverTimeTypeFinishedOvered;
			}
			else if(getBaseFinishDate()<longDealOverDate)
			{
				//已完成未超时
				m_Type=Constants.OverTimeTypeFinishedNoOver;
			}			
			
		}
		return m_Type;
	}
	
}
