package cn.com.ultrapower.ultrawf.control.process.baseaction;

import java.util.Hashtable;

import cn.com.ultrapower.ultrawf.control.config.BaseCategoryManage;
import cn.com.ultrapower.ultrawf.control.process.AuditingProcessManage;
import cn.com.ultrapower.ultrawf.control.process.BaseInforManage;
import cn.com.ultrapower.ultrawf.control.process.DealProcessManage;
import cn.com.ultrapower.ultrawf.control.process.TplDealLinkManage;
import cn.com.ultrapower.ultrawf.control.process.TplDealProcessManage;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.config.User;
import cn.com.ultrapower.ultrawf.models.config.UserModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessModel;
import cn.com.ultrapower.ultrawf.models.process.BaseInforModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessModel;
import cn.com.ultrapower.ultrawf.models.process.TplDealProcessModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.system.util.FormatTime;

public class Init_Base extends BaseProcessObject {
	
	
	protected boolean Init_Open_Base(String p_BaseSchema,String p_BaseID,String p_TplBaseID,String p_ProcessID,String p_ProcessType,String p_strUserLoginName,int p_Operation) {
		try
		{
			this.Hashtable_BaseAllFields = null;
			this.Hashtable_BaseAllFields = new Hashtable<String, BaseFieldInfo>();
			
			//初始化字段信息
			if (this.Init_Open_BaseAllFields(p_BaseSchema,p_BaseID,p_Operation)==false)
			{
				return false;	
			}
			//初始化人员信息		
			if (this.Init_Open_Read_UserInfo(p_strUserLoginName,p_BaseSchema)==false)
			{
				return false;	
			}			
			//初始化工单类别信息		
			if (this.Init_Open_Read_BaseCategoryInfor(p_BaseSchema,p_TplBaseID)==false)
			{
				return false;	
			}
			if (p_Operation == 0)
			{
				//初始化工单新建的信息		
				if (this.Init_Open_Set_BaseInformation() == false)
				{
					return false;	
				}
				//初始化工单新建环节的信息
				if (this.Init_Open_Set_BeginProcessInformation() == false)
				{
					return false;	
				}
			}	
			if (p_Operation == 1)
			{
				//初始化工单主体信息
				if (this.Init_Open_Select_Base() == false)
				{
					return false;
				}	
				//初始化本人的工单环节信息 p_ProcessID,String p_ProcessType
				if (FormatString.CheckNullString(p_ProcessID).equals("") || FormatString.CheckNullString(p_ProcessType).equals(""))
				{
					if (this.Init_Open_Select_Process() == false)
					{
						return false;	
					}	
				}
				else
				{
					setFieldValue("tmp_Pro_ProcessID",p_ProcessID);
					setFieldValue("tmp_Pro_ProcessType",p_ProcessType);
				}
				
				if (!this.Init_Open_Set_Process(getFieldValue("tmp_Pro_ProcessID"),getFieldValue("tmp_Pro_ProcessType")))
				{
					return false;	
				}					
			}			
		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("初始化信息",ex);
			return false;
		}
		return true;
	}	
	
	/**
	 * 描述：得到该工单的所有字段信息的集合
	 * @param p_BaseSchema			该工单的类别名 Form名 
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @return 得到该工单的所有字段信息的集合
	 */
	protected boolean Init_Open_BaseAllFields(String p_BaseSchema,String p_BaseID,int p_Operation) {
		
		Hashtable_BaseAllFields = new Hashtable<String, BaseFieldInfo>();
		if (p_Operation == 1)
		{		
			Hashtable_BaseAllFields.put("BaseID", new BaseFieldInfo("BaseID","1",p_BaseID,4));
			Hashtable_BaseAllFields.put("BaseSchema", new BaseFieldInfo("BaseSchema","700000001",p_BaseSchema,4));
		}
		else
		{
			Hashtable_BaseAllFields.put("BaseSchema", new BaseFieldInfo("BaseSchema","700000001",null,4));
		}
		
      	RDParameter m_BaseField_rDParameter=new RDParameter();
      	m_BaseField_rDParameter.addIndirectPar("BaseFormName",p_BaseSchema,4);
      	m_BaseField_rDParameter.addIndirectPar("BaseOwnFieldsFormName","WF:Config_BaseOwnFieldInfo_Mobile",4);
      	RowSet 	m_BaseField_RowSet 	= OpDB.getDataSetFromXML("BaseField.BaseOn_FormName",m_BaseField_rDParameter);
      	Row 	m_BaseField_Rs		= null;
      	for (int i = 0;i<m_BaseField_RowSet.length();i++) 
		{
      		m_BaseField_Rs = m_BaseField_RowSet.get(i);
      		Hashtable_BaseAllFields.put(
      				m_BaseField_Rs.getString("fieldname"), 
      				new BaseFieldInfo(
      						m_BaseField_Rs.getString("fieldname"),
      						m_BaseField_Rs.getString("fieldid"),
      						null,
      						m_BaseField_Rs.getInt("datatype")
      						)
      				);
		} 
    	return true;
	}
	
	/**
	 * 描述：读用户信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_strUserLoginName			操作工单动作的用户登陆名
	 * @return 返回已经填写的字段信息 
	 */
	protected boolean Init_Open_Read_UserInfo(String p_strUserLoginName,String p_strBaseSchama) {
		
		try
		{
			User user = new User();
			UserModel userModel = null;
			userModel = user.UserIsExist(p_strUserLoginName);
			if(userModel!=null){
				setFieldValue("tmp_UserLoginName",userModel.GetLoginName());
				setFieldValue("tmp_UserLoginName",userModel.GetLoginName());
				setFieldValue("tmp_UserLoginName",userModel.GetLoginName());
				setFieldValue("tmp_UserFullName",userModel.GetFullName());
				setFieldValue("tmp_UserGroupList",userModel.GetGroupList());
				setFieldValue("tmp_UserEmailAdd",userModel.GetEmailAddress());		
				setFieldValue("tmp_UserCloseBaseSamenessGroup",userModel.getCloseBaseSamenessGroup());						
				setFieldValue("tmp_UserCloseBaseSamenessGroupID",userModel.getCloseBaseSamenessGroupID());
				setFieldValue("tmp_UserCorp",userModel.getCorp());
				setFieldValue("tmp_UserCorpID",userModel.getCorpID());
				setFieldValue("tmp_UserDep",userModel.getDep());
				setFieldValue("tmp_UserDepID",userModel.getDepID());
				setFieldValue("tmp_UserDN",userModel.getDN());
				setFieldValue("tmp_UserDNID",userModel.getDNID());
			}
			else
			{
				BaseWrite_Log.writeLog("初始化人员",1,"初始化人员信息失败，该人员不存在！");
				return false;
			}
		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("初始化人员", ex);
			return false;
		}
		return true;
	}
	
	/**
	 * 描述：读工单类别信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_strBaseSchama		操作工单类别名（工单的From名）
	 * @return 返回已经填写的字段信息

	 */
	protected boolean Init_Open_Read_BaseCategoryInfor(String p_strBaseSchama,String p_TplBaseID) {
		try
		{
			BaseCategoryManage baseCategory = new BaseCategoryManage();
			BaseCategoryModel baseCategoryModel = null;
			
			baseCategoryModel = baseCategory.getOneModel(p_strBaseSchama);
			if (baseCategoryModel != null)
			{
				setFieldValue("Tmp_BaseCategoryBtnFixIDS",baseCategoryModel.getBaseCategoryBtnFixIDS());
				setFieldValue("Tmp_BaseCategoryBtnFreeIDS",baseCategoryModel.getBaseCategoryBtnFreeIDS());
				setFieldValue("Tmp_BaseCategoryBtnIDS",baseCategoryModel.getBaseCategoryBtnAllIDS());
				setFieldValue("tmp_BaseCategoryClassCode",(new Integer(baseCategoryModel.getBaseCategoryClassCode())).toString());
				setFieldValue("tmp_BaseCategoryClassName",baseCategoryModel.getBaseCategoryClassName());
				setFieldValue("tmp_BaseCategoryCode",baseCategoryModel.getBaseCategoryCode());
				setFieldValue("tmp_BaseCategoryDayLastNo",(new Integer(baseCategoryModel.getBaseCategoryDayLastNo())).toString());
				setFieldValue("tmp_BaseCategoryIsFlow",(new Integer(baseCategoryModel.getBaseCategoryIsFlow())).toString());
				setFieldValue("tmp_BaseCategoryName",baseCategoryModel.getBaseCategoryName());
				setFieldValue("Tmp_BaseCategoryPageHIDS",baseCategoryModel.getBaseCategoryPageHIDS());
				setFieldValue("Tmp_BaseCategoryPageIDS",baseCategoryModel.getBaseCategoryPageIDS());
				setFieldValue("tmp_BaseCategorySchama",baseCategoryModel.getBaseCategorySchama());
				if (baseCategoryModel.getBaseCategoryIsFlow()==1)
				{
					if (FormatString.CheckNullString(p_TplBaseID).equals("") && baseCategoryModel.getBaseCategoryIsDefaultFix()==0)
					{
						BaseWrite_Log.writeLog("初始化工单类别",1,"固定流程没有模板信息！");
						return false;				
					}
					else if (FormatString.CheckNullString(p_TplBaseID).equals("")==false)
					{
						setFieldValue("BaseTplID",p_TplBaseID);
					}
					else
					{
						if (baseCategoryModel.getBaseCategoryIsDefaultFix()==1 )
						{
							setFieldValue("BaseTplID",baseCategoryModel.getBaseCategoryDefaultFixTplBase());
						}
					}
				}
				else
				{
					
				}
			}
			else
			{
				BaseWrite_Log.writeLog("初始化工单类别",1,"初始化工单类别信息失败，该工单类别不存在！");
				return false;				
			}

		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("初始化工单类别", ex);
			return false;
		}
		return true;
	}	
	
	/**
	 * 描述：设置工单基本公用信息的函数，并返回工单上的临时字段信息已经负值的
	 * @return 是否成功
	 */
	protected boolean Init_Open_Set_BaseInformation() {
		setFieldValueFromOtherField("BaseWorkFlowFlag","tmp_BaseCategoryIsFlow");
		setFieldValueFromOtherField("BaseSchema","tmp_BaseCategorySchama");
		setFieldValueFromOtherField("BaseCreatorLoginName","tmp_UserLoginName");
		setFieldValueFromOtherField("BaseCreatorFullName","tmp_UserFullName");
		setFieldValueFromOtherField("BaseCreatorDNID","tmp_UserDNID");
		setFieldValueFromOtherField("BaseCreatorDN","tmp_UserDN");
		setFieldValueFromOtherField("BaseCreatorDepID","tmp_UserDepID");
		setFieldValueFromOtherField("BaseCreatorDep","tmp_UserDep");
		setFieldValueFromOtherField("BaseCreatorCorpID","tmp_UserCorpID");
		setFieldValueFromOtherField("BaseCreatorCorp","tmp_UserCorp");
		setFieldValueFromOtherField("BaseCategoryClassName","tmp_BaseCategoryClassName");
		setFieldValueFromOtherField("BaseCategoryClassCode","tmp_BaseCategoryClassCode");
		setFieldValueFromOtherField("BaseName","tmp_BaseCategoryName");
		setFieldValue("BaseOpenDateTime",(new Long(FormatTime.FormatDateToInt(FormatTime.getCurrentDate()))).toString());
		setFieldValue("BaseIsTrueClose","0");
		setFieldValue("BaseIsArchive","0");
		setFieldValue("BaseFlagSended","0");
		setFieldValue("BaseFlagIsCreateChild","0");
		setFieldValue("BaseFlagFinished","0");
		setFieldValue("BaseFlagCreated","0");
		setFieldValue("BaseFlagCloseed","0");
		setFieldValue("BaseAcceptOutFlag","0");
		setFieldValue("BaseDealOutFlag","0");
		setFieldValue("BaseFieldLogName","WF:App_Base_Field_ModifyLog");
		setFieldValue("BaseDealVerdictName","WF:App_DealVerdict");
		setFieldValue("BaseDealProcessName","WF:App_DealProcess");
		setFieldValue("BaseDealProcessLogName","WF:App_DealProcessLog");
		setFieldValue("BaseDealLinkName","WF:App_DealLink");
		setFieldValue("BaseDealAssistantProcessName","WF:App_DealAssistantProcess");
		setFieldValue("BaseAuditingProcessName","WF:App_AuditingProcess");
		setFieldValue("BaseAuditingProcessLogName","WF:App_AuditingProcessLog");
		setFieldValue("BaseAuditingLinkName","WF:App_AuditingLink");		
		if (getFieldValue("tmp_BaseCategoryIsFlow").equals("0"))
		{
			setFieldValue("BaseStatusCode","10");
			setFieldValue("BaseStatus","草稿");
		}

		
		return true;
	}
	
	protected boolean Init_Open_Set_BeginProcessInformation()
	{
		String m_GUID = this.getGUID("BID_", System.currentTimeMillis());
		setFieldValue("tmp_Pro_DealTime",(new Long(FormatTime.FormatDateToInt(FormatTime.getCurrentDate()))).toString());
		setFieldValue("tmp_Pro_BaseID",m_GUID);
		setFieldValue("tmp_Pro_ProcessID","PID" + m_GUID);		
		
		if (getFieldValue("tmp_BaseCategoryIsFlow").equals("1"))
		{
			setFieldValueFromOtherField("tmp_Pro_BaseSchema","tmp_BaseCategorySchama");		
			setFieldValueFromOtherField("tmp_Ver_VerdictBaseSchema","tmp_BaseCategorySchama");		
			setFieldValueFromOtherField("tmp_Ver_VerdictBaseID","tmp_Pro_BaseID");		
			setFieldValueFromOtherField("tmp_Link_BaseSchema","tmp_BaseCategorySchama");		
			setFieldValueFromOtherField("tmp_Link_BaseID","tmp_Pro_BaseID");		
			setFieldValueFromOtherField("tmp_Ass_AssistantProcessBaseSchema","tmp_BaseCategorySchama");		
			setFieldValueFromOtherField("tmp_Ass_AssistantProcessBaseID","tmp_Pro_BaseID");	
			
			setFieldValue("tmp_Pro_PhaseNo",((new TplDealLinkManage()).getOneModel(getFieldValue("tmp_Link_BaseSchema"), getFieldValue("BaseTplID"), "p_BEGIN")).getEndPhaseNo());		
			
			TplDealProcessModel m_TplDealProcessModel =  (new TplDealProcessManage()).getOneModel(getFieldValue("tmp_Pro_BaseSchema"), getFieldValue("BaseTplID"), getFieldValue("tmp_Pro_PhaseNo"));
			
			setFieldValue("tmp_Pro_AcceptOverTimeDate",(new Long(m_TplDealProcessModel.getAcceptOverTimeDate())).toString());
			setFieldValue("tmp_Pro_AcceptOverTimeDate_Relative",(new Long(m_TplDealProcessModel.getAcceptOverTimeDate_Relative())).toString());
			setFieldValue("tmp_Pro_AcceptOverTimeDate_tmp",(new Long(m_TplDealProcessModel.getAcceptOverTimeDate_tmp())).toString());
			setFieldValue("tmp_Pro_ActionName",m_TplDealProcessModel.getActionName());
			setFieldValue("tmp_Pro_ActionPageID",m_TplDealProcessModel.getActionPageID());
			setFieldValue("tmp_Pro_ActionPageName",m_TplDealProcessModel.getActionPageName());
			setFieldValue("tmp_Pro_Assginee",m_TplDealProcessModel.getAssignee());
			setFieldValue("tmp_Pro_AssgineeID",m_TplDealProcessModel.getAssgineeID());
			setFieldValue("tmp_Pro_AssignOverTimeDate",(new Long(m_TplDealProcessModel.getAssignOverTimeDate())).toString());
			setFieldValue("tmp_Pro_AssignOverTimeDate_Relative",(new Long(m_TplDealProcessModel.getAssignOverTimeDate_Relative())).toString());
			setFieldValue("tmp_Pro_AssignOverTimeDate_tmp",(new Long(m_TplDealProcessModel.getAssignOverTimeDate_tmp())).toString());
			setFieldValue("tmp_Pro_AssigneeCorp",m_TplDealProcessModel.getAssigneeCorp());
			setFieldValue("tmp_Pro_AssigneeCorpID",m_TplDealProcessModel.getAssigneeCorpID());
			setFieldValue("tmp_Pro_AssigneeDN",m_TplDealProcessModel.getAssigneeDN());
			setFieldValue("tmp_Pro_AssigneeDNID",m_TplDealProcessModel.getAssigneeDNID());
			setFieldValue("tmp_Pro_AssigneeDep",m_TplDealProcessModel.getAssigneeDep());
			setFieldValue("tmp_Pro_AssigneeDepID",m_TplDealProcessModel.getAssigneeDepID());
			setFieldValue("tmp_Pro_BaseTplStateID",m_TplDealProcessModel.getBaseStateCode());
			setFieldValue("tmp_Pro_BaseTplStateName",m_TplDealProcessModel.getBaseStateName());
			setFieldValue("tmp_Pro_BgDate",(new Long(m_TplDealProcessModel.getBgDate())).toString());
			setFieldValue("tmp_Pro_CloseBaseSamenessGroup",m_TplDealProcessModel.getCloseBaseSamenessGroup());
			setFieldValue("tmp_Pro_CloseBaseSamenessGroupID",m_TplDealProcessModel.getCloseBaseSamenessGroupID());
			setFieldValue("tmp_Pro_Commissioner",m_TplDealProcessModel.getCommissioner());
			setFieldValue("tmp_Pro_CommissionerID",m_TplDealProcessModel.getCommissionerID());
			setFieldValue("tmp_Pro_CreateByUserID",m_TplDealProcessModel.getCreateByUserID());
			setFieldValue("tmp_Pro_CustomActions",m_TplDealProcessModel.getCustomActions());
			setFieldValue("tmp_Pro_DealOverTimeDate",(new Long(m_TplDealProcessModel.getDealOverTimeDate())).toString());
			setFieldValue("tmp_Pro_DealOverTimeDate_Relative",(new Long(m_TplDealProcessModel.getDealOverTimeDate_Relative())).toString());
			setFieldValue("tmp_Pro_DealOverTimeDate_tmp",(new Long(m_TplDealProcessModel.getDealOverTimeDate_tmp())).toString());
			setFieldValue("tmp_Pro_Dealer",m_TplDealProcessModel.getDealer());
			setFieldValue("tmp_Pro_DealerDNID",m_TplDealProcessModel.getDealerDNID());
			setFieldValue("tmp_Pro_DealerID",m_TplDealProcessModel.getDealerID());
			setFieldValue("tmp_Pro_Desc",m_TplDealProcessModel.getDesc());
			setFieldValue("tmp_Pro_EdDate",(new Long(m_TplDealProcessModel.getEdDate())).toString());
			//setFieldValue("tmp_Pro_EdProcessAction",(new Integer(m_TplDealProcessModel.getEdProcessAction())).toString());
			setFieldValue("tmp_Pro_Flag01Assign",(new Integer(m_TplDealProcessModel.getFlag01Assign())).toString());
			setFieldValue("tmp_Pro_Flag02Copy",(new Integer(m_TplDealProcessModel.getFlag02Copy())).toString());
			setFieldValue("tmp_Pro_Flag03Assist",(new Integer(m_TplDealProcessModel.getFlag03Assist())).toString());
			setFieldValue("tmp_Pro_Flag04Transfer",(new Integer(m_TplDealProcessModel.getFlag04Transfer())).toString());
			setFieldValue("tmp_Pro_Flag05TurnDown",(new Integer(m_TplDealProcessModel.getFlag05TurnDown())).toString());
			setFieldValue("tmp_Pro_Flag06TurnUp",(new Integer(m_TplDealProcessModel.getFlag06TurnUp())).toString());
			setFieldValue("tmp_Pro_Flag07Recall",(new Integer(m_TplDealProcessModel.getFlag07Recall())).toString());
			setFieldValue("tmp_Pro_Flag08Cancel",(new Integer(m_TplDealProcessModel.getFlag08Cancel())).toString());
			setFieldValue("tmp_Pro_Flag09Close",(new Integer(m_TplDealProcessModel.getFlag09Close())).toString());
			setFieldValue("tmp_Pro_Flag15ToAuditing",(new Integer(m_TplDealProcessModel.getFlag15ToAuditing())).toString());
			setFieldValue("tmp_Pro_Flag16ToAssistAuditing",(new Integer(m_TplDealProcessModel.getFlag16ToAssistAuditing())).toString());
			setFieldValue("tmp_Pro_Flag20SideBySide",(new Integer(m_TplDealProcessModel.getFlag20SideBySide())).toString());
			setFieldValue("tmp_Pro_Flag22IsSelect",(new Integer(m_TplDealProcessModel.getFlag22IsSelect())).toString());
			setFieldValue("tmp_Pro_Flag30AuditingResult",(new Integer(m_TplDealProcessModel.getFlag30AuditingResult())).toString());
			setFieldValue("tmp_Pro_Flag31IsTransfer",(new Integer(m_TplDealProcessModel.getFlag31IsTransfer())).toString());
			setFieldValue("tmp_Pro_Flag32IsToTransfer",(new Integer(m_TplDealProcessModel.getFlag32IsToTransfer())).toString());
			setFieldValue("tmp_Pro_Flag33IsEndPhase",(new Integer(m_TplDealProcessModel.getFlag33IsEndPhase())).toString());
			setFieldValue("tmp_Pro_Flag34IsEndDuplicated",(new Integer(m_TplDealProcessModel.getFlag34IsEndDuplicated())).toString());
			setFieldValue("tmp_Pro_Flag36IsCreateBase",(new Integer(m_TplDealProcessModel.getFlag36IsCreateBase())).toString());
			setFieldValue("tmp_Pro_Flag35IsCanCreateBase",(new Integer(m_TplDealProcessModel.getFlag35IsCanCreateBase())).toString());
			setFieldValue("tmp_Pro_FlagActive",(new Integer(m_TplDealProcessModel.getFlagActive())).toString());
			setFieldValue("tmp_Pro_FlagAssignGroupOrUser",(new Integer(m_TplDealProcessModel.getFlagAssignGroupOrUser())).toString());
			setFieldValue("tmp_Pro_FlagBegin",(new Long(m_TplDealProcessModel.getFlagBegin())).toString());
			setFieldValue("tmp_Pro_FlagDuplicated",(new Integer(m_TplDealProcessModel.getFlagDuplicated())).toString());
			setFieldValue("tmp_Pro_FlagEnd",(new Long(m_TplDealProcessModel.getFlagEnd())).toString());
			setFieldValue("tmp_Pro_FlagGroupSnatch",(new Integer(m_TplDealProcessModel.getFlagGroupSnatch())).toString());
			setFieldValue("tmp_Pro_FlagPredefined",(new Integer(m_TplDealProcessModel.getFlagPredefined())).toString());
			setFieldValue("tmp_Pro_FlagStart",(new Long(m_TplDealProcessModel.getFlagStart())).toString());
			setFieldValue("tmp_Pro_FlagType",(new Integer(m_TplDealProcessModel.getFlagType())).toString());
			setFieldValue("tmp_Pro_Group",m_TplDealProcessModel.getGroup());
			setFieldValue("tmp_Pro_GroupID",m_TplDealProcessModel.getGroupID());
			setFieldValue("tmp_Pro_PhaseNo",m_TplDealProcessModel.getPhaseNo());
			setFieldValue("tmp_Pro_PhaseNoTakeMeActive",m_TplDealProcessModel.getPhaseNoTakeMeActive());
			setFieldValue("tmp_Pro_PosX",(new Integer(m_TplDealProcessModel.getPosX())).toString());
			setFieldValue("tmp_Pro_PosY",(new Integer(m_TplDealProcessModel.getPosY())).toString());
			setFieldValue("tmp_Pro_PrevPhaseNo",m_TplDealProcessModel.getPrevPhaseNo());
			setFieldValue("tmp_Pro_ProcessFlag00IsAvail",(new Integer(m_TplDealProcessModel.getFlag00IsAvail())).toString());
			setFieldValue("tmp_Pro_ProcessGoLine",m_TplDealProcessModel.getProcessGoLine());
			setFieldValue("tmp_Pro_Status",m_TplDealProcessModel.getProcessStatus());
			setFieldValue("tmp_Pro_ProcessType",m_TplDealProcessModel.getProcessType());
			setFieldValue("tmp_Pro_RoleName",m_TplDealProcessModel.getRoleName());
			setFieldValue("tmp_Pro_RoleOnlyID",m_TplDealProcessModel.getRoleOnlyID());
			setFieldValue("tmp_Pro_StDate",(new Long(m_TplDealProcessModel.getStDate())).toString());
			setFieldValue("tmp_Pro_StProcessAction",(new Integer(m_TplDealProcessModel.getStProcessAction())).toString());
			setFieldValue("tmp_Pro_TransferPhaseNo",m_TplDealProcessModel.getTransferPhaseNo());
			
			setFieldValue("tmp_Pro_AcceptOverTimeDate_Relative","0");
			setFieldValueFromOtherField("tmp_Pro_Assginee","tmp_UserFullName");
			setFieldValueFromOtherField("tmp_Pro_AssgineeID","tmp_UserLoginName");
			setFieldValue("tmp_Pro_AssignOverTimeDate_Relative","0");
			setFieldValueFromOtherField("tmp_Pro_AssigneeCorp","tmp_UserCorp");
			setFieldValueFromOtherField("tmp_Pro_AssigneeCorpID","tmp_UserCorpID");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDN","tmp_UserDN");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDNID","tmp_UserDNID");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDep","tmp_UserDep");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDepID","tmp_UserDepID");
			setFieldValueFromOtherField("tmp_Pro_BgDate","tmp_Pro_DealTime");
			setFieldValueFromOtherField("tmp_Pro_CloseBaseSamenessGroup","tmp_UserCloseBaseSamenessGroup");
			setFieldValueFromOtherField("tmp_Pro_CloseBaseSamenessGroupID","tmp_UserCloseBaseSamenessGroupID");
			setFieldValueFromOtherField("tmp_Pro_Dealer","tmp_UserFullName");
			setFieldValueFromOtherField("tmp_Pro_DealerCorp","tmp_UserCorp");
			setFieldValueFromOtherField("tmp_Pro_DealerCorpID","tmp_UserCorpID");
			setFieldValueFromOtherField("tmp_Pro_DealerDN","tmp_UserDN");
			setFieldValueFromOtherField("tmp_Pro_DealerDNID","tmp_UserDNID");
			setFieldValueFromOtherField("tmp_Pro_DealerDep","tmp_UserDep");
			setFieldValueFromOtherField("tmp_Pro_DealerDepID","tmp_UserDepID");
			setFieldValueFromOtherField("tmp_Pro_DealerID","tmp_UserLoginName");
			setFieldValue("tmp_Pro_Flag08Cancel","1");
			setFieldValue("tmp_Pro_Flag09Close","1");
			setFieldValue("tmp_Pro_Flag30AuditingResult","0");
			setFieldValue("tmp_Pro_Flag31IsTransfer","0");
			setFieldValue("tmp_Pro_Flag32IsToTransfer","0");
			setFieldValue("tmp_Pro_Flag33IsEndPhase","1");
			setFieldValue("tmp_Pro_Flag34IsEndDuplicated","1");
			setFieldValue("tmp_Pro_FlagActive","1");
			setFieldValue("tmp_Pro_FlagAssignGroupOrUser","0");
			setFieldValueFromOtherField("tmp_Pro_FlagBegin","tmp_Pro_DealTime");
			setFieldValue("tmp_Pro_FlagDuplicated","0");
			setFieldValue("tmp_Pro_FlagPredefined","1");
			setFieldValueFromOtherField("tmp_Pro_FlagStart","tmp_Pro_DealTime");
			setFieldValue("tmp_Pro_FlagType","0");
			setFieldValueFromOtherField("tmp_Pro_BaseSchema","tmp_BaseCategorySchama");
			setFieldValue("tmp_Pro_ProcessFlag00IsAvail","1");
			setFieldValue("tmp_Pro_Status","待处理");
			setFieldValue("tmp_Pro_ProcessType","DEAL");
			setFieldValueFromOtherField("tmp_Pro_StDate","tmp_Pro_DealTime");
			setFieldValue("tmp_Pro_StProcessAction","0");

			setFieldValueFromOtherField("BaseStatusCode","tmp_Pro_BaseTplStateID");
			setFieldValueFromOtherField("BaseStatus","tmp_Pro_BaseTplStateName");

		}
		else
		{
			setFieldValue("tmp_Pro_PhaseNo","PNO_" + m_GUID);			
			setFieldValue("tmp_Pro_AcceptOverTimeDate_Relative","0");
			setFieldValueFromOtherField("tmp_Pro_Assginee","tmp_UserFullName");
			setFieldValueFromOtherField("tmp_Pro_AssgineeID","tmp_UserLoginName");
			setFieldValue("tmp_Pro_AssignOverTimeDate_Relative","0");
			setFieldValueFromOtherField("tmp_Pro_AssigneeCorp","tmp_UserCorp");
			setFieldValueFromOtherField("tmp_Pro_AssigneeCorpID","tmp_UserCorpID");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDN","tmp_UserDN");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDNID","tmp_UserDNID");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDep","tmp_UserDep");
			setFieldValueFromOtherField("tmp_Pro_AssigneeDepID","tmp_UserDepID");
			setFieldValueFromOtherField("tmp_Pro_BgDate","tmp_Pro_DealTime");
			setFieldValueFromOtherField("tmp_Pro_CloseBaseSamenessGroup","tmp_UserCloseBaseSamenessGroup");
			setFieldValueFromOtherField("tmp_Pro_CloseBaseSamenessGroupID","tmp_UserCloseBaseSamenessGroupID");
			setFieldValue("tmp_Pro_DealOverTimeDate_Relative","0");
			setFieldValueFromOtherField("tmp_Pro_Dealer","tmp_UserFullName");
			setFieldValueFromOtherField("tmp_Pro_DealerCorp","tmp_UserCorp");
			setFieldValueFromOtherField("tmp_Pro_DealerCorpID","tmp_UserCorpID");
			setFieldValueFromOtherField("tmp_Pro_DealerDN","tmp_UserDN");
			setFieldValueFromOtherField("tmp_Pro_DealerDNID","tmp_UserDNID");
			setFieldValueFromOtherField("tmp_Pro_DealerDep","tmp_UserDep");
			setFieldValueFromOtherField("tmp_Pro_DealerDepID","tmp_UserDepID");
			setFieldValueFromOtherField("tmp_Pro_DealerID","tmp_UserLoginName");
			setFieldValue("tmp_Pro_Desc","新建工单；");
			setFieldValue("tmp_Pro_Flag01Assign","1");
			setFieldValue("tmp_Pro_Flag02Copy","1");
			setFieldValue("tmp_Pro_Flag03Assist","1");
			setFieldValue("tmp_Pro_Flag04Transfer","0");
			setFieldValue("tmp_Pro_Flag05TurnDown","1");
			setFieldValue("tmp_Pro_Flag06TurnUp","1");
			setFieldValue("tmp_Pro_Flag07Recall","1");
			setFieldValue("tmp_Pro_Flag08Cancel","1");
			setFieldValue("tmp_Pro_Flag09Close","1");
			setFieldValue("tmp_Pro_Flag15ToAuditing","1");
			setFieldValue("tmp_Pro_Flag16ToAssistAuditing","0");
			setFieldValue("tmp_Pro_Flag30AuditingResult","0");
			setFieldValue("tmp_Pro_Flag31IsTransfer","0");
			setFieldValue("tmp_Pro_Flag32IsToTransfer","0");
			setFieldValue("tmp_Pro_Flag33IsEndPhase","1");
			setFieldValue("tmp_Pro_Flag34IsEndDuplicated","1");
			setFieldValue("tmp_Pro_Flag36IsCreateBase","0");
			setFieldValue("tmp_Pro_Flag35IsCanCreateBase","0");
			setFieldValue("tmp_Pro_FlagActive","1");
			setFieldValue("tmp_Pro_FlagAssignGroupOrUser","0");
			setFieldValueFromOtherField("tmp_Pro_FlagBegin","tmp_Pro_DealTime");
			setFieldValue("tmp_Pro_FlagDuplicated","0");
			setFieldValue("tmp_Pro_FlagEnd","0");
			setFieldValue("tmp_Pro_FlagGroupSnatch","0");
			setFieldValue("tmp_Pro_FlagPredefined","0");
			setFieldValueFromOtherField("tmp_Pro_FlagStart","tmp_Pro_DealTime");
			setFieldValue("tmp_Pro_FlagType","0");
			setFieldValue("tmp_Pro_PrevPhaseNo","BEGIN");
			setFieldValueFromOtherField("tmp_Pro_BaseID","tmp_Pro_BaseID");
			setFieldValueFromOtherField("tmp_Pro_BaseSchema","tmp_BaseCategorySchama");
			setFieldValue("tmp_Pro_ProcessFlag00IsAvail","1");
			setFieldValue("tmp_Pro_Status","待处理");
			setFieldValue("tmp_Pro_ProcessType","DEAL");
			setFieldValueFromOtherField("tmp_Pro_StDate","tmp_Pro_DealTime");
			setFieldValue("tmp_Pro_StProcessAction","0");
		}	
		return true;
	}
	
	/**
	 * 初始化工单主体信息
	 * @return
	 */
	protected boolean Init_Open_Select_Base() {
		BaseInforManage m_BaseManage = new BaseInforManage();
		BaseInforModel m_BaseModel = null;
		try{
			m_BaseModel = m_BaseManage.getOneModel(getFieldValue("BaseSchema"),getFieldValue("BaseID"));
			
			setFieldValue("BaseID",m_BaseModel.getBaseID());
			setFieldValue("BaseSchema",m_BaseModel.getBaseSchema());
			setFieldValue("BaseName",m_BaseModel.getBaseName());
			setFieldValue("BaseSN",m_BaseModel.getBaseSN());
			setFieldValue("BaseCreatorFullName",m_BaseModel.getBaseCreatorFullName());
			setFieldValue("BaseCreatorLoginName",m_BaseModel.getBaseCreatorLoginName());
			setFieldValue("BaseCreateDate",(new Long(m_BaseModel.getBaseCreateDate())).toString());
			setFieldValue("BaseSendDate",(new Long(m_BaseModel.getBaseSendDate())).toString());
			setFieldValue("BaseFinishDate",(new Long(m_BaseModel.getBaseFinishDate())).toString());
			setFieldValue("BaseCloseDate",(new Long(m_BaseModel.getBaseCloseDate())).toString());
			setFieldValue("BaseStatus",m_BaseModel.getBaseStatus());
//			setFieldValue("BaseSummary",m_BaseModel.getBaseSummary());
//			setFieldValue("BaseItems",m_BaseModel.getBaseItems());
//			setFieldValue("BasePriority",m_BaseModel.getBasePriority());
//			setFieldValue("BaseIsAllowLogGroup",m_BaseModel.getBaseIsAllowLogGroup());
//			setFieldValue("BaseAcceptOutTime",(new Long(m_BaseModel.getBaseAcceptOutTime())).toString());
//			setFieldValue("BaseDealOutTime",(new Long(m_BaseModel.getBaseDealOutTime())).toString());
//			setFieldValue("BaseDescrption",m_BaseModel.getBaseDescrption());
			setFieldValue("BaseResult",m_BaseModel.getBaseResult());
//			setFieldValue("BaseCloseSatisfy",m_BaseModel.getBaseCloseSatisfy());
			setFieldValue("BaseTplID",m_BaseModel.getBaseTplID());
			setFieldValue("BaseIsArchive",(new Integer(m_BaseModel.getBaseIsArchive())).toString());
			setFieldValue("BaseAuditingLinkName",m_BaseModel.getBaseAuditingLinkName());
			setFieldValue("BaseAuditingProcessName",m_BaseModel.getBaseAuditingProcessName());
			setFieldValue("BaseAuditingProcessLogName",m_BaseModel.getBaseAuditingProcessLogName());
			setFieldValue("BaseDealLinkName",m_BaseModel.getBaseDealLinkName());
			setFieldValue("BaseDealProcessName",m_BaseModel.getBaseDealProcessName());
			setFieldValue("BaseDealProcessLogName",m_BaseModel.getBaseDealProcessLogName());
			setFieldValue("BaseIsTrueClose",(new Integer(m_BaseModel.getBaseIsTrueClose())).toString());
			setFieldValue("BaseWorkFlowFlag",(new Integer(m_BaseModel.getBaseWorkFlowFlag())).toString());
			setFieldValue("BaseCategoryClassName",m_BaseModel.getBaseCategoryClassName());
			setFieldValue("BaseCategoryClassCode",(new Integer(m_BaseModel.getBaseCategoryClassCode())).toString());
			setFieldValue("BaseFlagCreated",(new Long(m_BaseModel.getBaseFlagCreated())).toString());
			setFieldValue("BaseFlagSended",(new Long(m_BaseModel.getBaseFlagSended())).toString());
			setFieldValue("BaseFlagFinished",(new Long(m_BaseModel.getBaseFlagFinished())).toString());
			setFieldValue("BaseFlagCloseed",(new Long(m_BaseModel.getBaseFlagCloseed())).toString());
			setFieldValue("BaseStatusCode",m_BaseModel.getBaseStatusCode());
			setFieldValue("BaseAcceptOutFlag",(new Long(m_BaseModel.getBaseAcceptOutFlag())).toString());
			setFieldValue("BaseDealOutFlag",(new Long(m_BaseModel.getBaseDealOutFlag())).toString());
			setFieldValue("BaseDealVerdictName",m_BaseModel.getBaseDealVerdictName());
			setFieldValue("BaseDealAssistantProcessName",m_BaseModel.getBaseDealAssistantProcessName());
			setFieldValue("BaseFieldLogName",m_BaseModel.getBaseFieldLogName());
			setFieldValue("BaseFlagIsMotherCreated",(new Integer(m_BaseModel.getBaseFlagIsMotherCreated())).toString());
			setFieldValue("BaseFlowDrawDesc",m_BaseModel.getBaseFlowDrawDesc());
			setFieldValue("BaseFlagIsCreateChild",(new Integer(m_BaseModel.getBaseFlagIsCreateChild())).toString());
			setFieldValue("BaseOpenDateTime",(new Integer(m_BaseModel.getBaseOpenDateTime())).toString());
			setFieldValue("BaseCreatorConnectWay",m_BaseModel.getBaseCreatorConnectWay());
			setFieldValue("BaseCreatorCorp",m_BaseModel.getBaseCreatorCorp());
			setFieldValue("BaseCreatorCorpID",m_BaseModel.getBaseCreatorCorpID());
			setFieldValue("BaseCreatorDep",m_BaseModel.getBaseCreatorDep());
			setFieldValue("BaseCreatorDepID",m_BaseModel.getBaseCreatorDepID());
			setFieldValue("BaseCreatorDN",m_BaseModel.getBaseCreatorDN());
			setFieldValue("BaseCreatorDNID",m_BaseModel.getBaseCreatorDNID());
		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("初始化工单基础数据", ex);
			return false;
		}
		return true;
		
	}	
	
	/**
	 * 描述：根据本人员信息读工单环节ID和类型的函数
	 * @return 返回是否成功
	 */
	protected boolean Init_Open_Select_Process() {
		try
		{ 
			if (FormatString.CheckNullString(getFieldValue("tmp_Pro_ProcessID")).equals(""))
			{
		  		String m_ProcessString = selectWaitProcess();
				if (m_ProcessString.equals("")==false)
				{
		  			String[] m_ProcessArray	= m_ProcessString.split("&");
					setFieldValue("tmp_Pro_ProcessID", m_ProcessArray[0]);
					setFieldValue("tmp_Pro_ProcessType", m_ProcessArray[1]);
					return true;
				}
				System.out.println("根据本人员信息读工单环节（1），没有读到！");
			}

		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("根据本人信息读工单环节信息的ID", ex);
			return false;
		}
		BaseWrite_Log.writeLog("根据本人信息读工单环节信息的ID",1,"根据本人信息读工单环节信息的ID错误");
		return false;

	}

	/**
	 * 描述：根据工单环节关键字,读工单环节信息并进行设置的函数
	 * @param p_ProcessID				环节ID
	 * @param p_ProcessType				环节类型
	 * @return 是否成功
	 */
	protected boolean Init_Open_Set_Process(String p_ProcessID,String p_ProcessType)
	{
		try
		{	
			if (
					(
							p_ProcessID != null && !p_ProcessID.equals("")
							&& 
							p_ProcessType != null && !p_ProcessType.equals("")
					)
					&& 
					(
							p_ProcessType.equals("DEAL") || p_ProcessType.equals("AUDITING")
					)
				)
			{
				if (p_ProcessType.equals("DEAL"))
				{
					DealProcessManage		m_DealProcessManage		= new DealProcessManage();
					DealProcessModel 		m_DealProcessModel		= m_DealProcessManage.getOneModel(p_ProcessID, p_ProcessType);

//					(
//						select 'setFieldValue("' || t.fieldname || '",m_BaseModel.get' ||  f1.fieldname || '());',t.fieldname as fromname,f1.fieldname as toname, t.* from 
//						(
//							select f.fieldname ,f.datatype,replace(replace(alset.assignshort,'102\1\@\18\WF:App_DealProcess\1\',''),'\4\1\1\1\99\700038041\2\3\','') as dd,alset.*
//							from workflow.actlink_set alset,field f
//							where alset.actlinkid = '864' and alset.actionindex=0 and f.schemaid = 118/*--WF:App_Base_bak*/ and (f.fieldid=alset.fieldid)
//						) t,field f1
//							where f1.schemaid  = 114	--WF:App_DealProcess
//							and (f1.fieldid = t.dd) and t.datatype in (4,6)
//					)
//					union all
//					(
//						select 'setFieldValue("' || t.fieldname || '",(new Long(m_BaseModel.get' ||  f1.fieldname || '())).toString());',t.fieldname as fromname,f1.fieldname as toname, t.* from 
//						(
//							select f.fieldname ,f.datatype,replace(replace(alset.assignshort,'102\1\@\18\WF:App_DealProcess\1\',''),'\4\1\1\1\99\700038041\2\3\','') as dd,alset.*
//							from workflow.actlink_set alset,field f
//							where alset.actlinkid = '864' and alset.actionindex=0 and f.schemaid = 118/*--WF:App_Base_bak*/ and (f.fieldid=alset.fieldid)
//						) t,field f1
//							where f1.schemaid  = 114	--WF:App_DealProcess
//							and (f1.fieldid = t.dd) and t.datatype not in (4,6)
//					)
//					order by fromname
					
					setFieldValue("tmp_Pro_AcceptOverTimeDate",(new Long(m_DealProcessModel.getAcceptOverTimeDate())).toString());
					setFieldValue("tmp_Pro_AcceptOverTimeDate_Relative",(new Long(m_DealProcessModel.getAcceptOverTimeDate_Relative())).toString());
					setFieldValue("tmp_Pro_AcceptOverTimeDate_tmp",(new Long(m_DealProcessModel.getAcceptOverTimeDate_tmp())).toString());
					setFieldValue("tmp_Pro_ActionName",m_DealProcessModel.getActionName());
					setFieldValue("tmp_Pro_ActionPageID",m_DealProcessModel.getActionPageID());
					setFieldValue("tmp_Pro_ActionPageName",m_DealProcessModel.getActionPageName());
					setFieldValue("tmp_Pro_Assginee",m_DealProcessModel.getAssginee());
					setFieldValue("tmp_Pro_AssgineeID",m_DealProcessModel.getAssgineeID());
					setFieldValue("tmp_Pro_AssignOverTimeDate",(new Long(m_DealProcessModel.getAssignOverTimeDate())).toString());
					setFieldValue("tmp_Pro_AssignOverTimeDate_Relative",(new Long(m_DealProcessModel.getAssignOverTimeDate_Relative())).toString());
					setFieldValue("tmp_Pro_AssignOverTimeDate_tmp",(new Long(m_DealProcessModel.getAssignOverTimeDate_tmp())).toString());
					setFieldValue("tmp_Pro_AssigneeCorp",m_DealProcessModel.getAssigneeCorp());
					setFieldValue("tmp_Pro_AssigneeCorpID",m_DealProcessModel.getAssigneeCorpID());
					setFieldValue("tmp_Pro_AssigneeDN",m_DealProcessModel.getAssigneeDN());
					setFieldValue("tmp_Pro_AssigneeDNID",m_DealProcessModel.getAssigneeDNID());
					setFieldValue("tmp_Pro_AssigneeDep",m_DealProcessModel.getAssigneeDep());
					setFieldValue("tmp_Pro_AssigneeDepID",m_DealProcessModel.getAssigneeDepID());
					setFieldValue("tmp_Pro_BaseID",m_DealProcessModel.getProcessBaseID());
					setFieldValue("tmp_Pro_BaseSchema",m_DealProcessModel.getProcessBaseSchema());
					setFieldValue("tmp_Pro_BaseTplStateID",m_DealProcessModel.getBaseStateCode());
					setFieldValue("tmp_Pro_BaseTplStateName",m_DealProcessModel.getBaseStateName());
					setFieldValue("tmp_Pro_BgDate",(new Long(m_DealProcessModel.getBgDate())).toString());
					setFieldValue("tmp_Pro_CloseBaseSamenessGroup",m_DealProcessModel.getCloseBaseSamenessGroup());
					setFieldValue("tmp_Pro_CloseBaseSamenessGroupID",m_DealProcessModel.getCloseBaseSamenessGroupID());
					setFieldValue("tmp_Pro_Commissioner",m_DealProcessModel.getCommissioner());
					setFieldValue("tmp_Pro_CommissionerID",m_DealProcessModel.getCommissionerID());
					setFieldValue("tmp_Pro_CreateByUserID",m_DealProcessModel.getCreateByUserID());
					setFieldValue("tmp_Pro_CustomActions",m_DealProcessModel.getCustomActions());
					setFieldValue("tmp_Pro_DealOverTimeDate",(new Long(m_DealProcessModel.getDealOverTimeDate())).toString());
					setFieldValue("tmp_Pro_DealOverTimeDate_Relative",(new Long(m_DealProcessModel.getDealOverTimeDate_Relative())).toString());
					setFieldValue("tmp_Pro_DealOverTimeDate_tmp",(new Long(m_DealProcessModel.getDealOverTimeDate_tmp())).toString());
					setFieldValue("tmp_Pro_Dealer",m_DealProcessModel.getDealer());
					setFieldValue("tmp_Pro_DealerCorp",m_DealProcessModel.getDealerCorp());
					setFieldValue("tmp_Pro_DealerCorpID",m_DealProcessModel.getDealerCorpID());
					setFieldValue("tmp_Pro_DealerDN",m_DealProcessModel.getDealerDN());
					setFieldValue("tmp_Pro_DealerDNID",m_DealProcessModel.getDealerDNID());
					setFieldValue("tmp_Pro_DealerDep",m_DealProcessModel.getDealerDep());
					setFieldValue("tmp_Pro_DealerDepID",m_DealProcessModel.getDealerDepID());
					setFieldValue("tmp_Pro_DealerID",m_DealProcessModel.getDealerID());
					setFieldValue("tmp_Pro_Desc",m_DealProcessModel.getDesc());
					setFieldValue("tmp_Pro_EdDate",(new Long(m_DealProcessModel.getEdDate())).toString());
					setFieldValue("tmp_Pro_EdProcessAction",m_DealProcessModel.getEdProcessAction());
					setFieldValue("tmp_Pro_Flag01Assign",m_DealProcessModel.getFlag01Assign());
					setFieldValue("tmp_Pro_Flag02Copy",m_DealProcessModel.getFlag02Copy());
					setFieldValue("tmp_Pro_Flag03Assist",m_DealProcessModel.getFlag03Assist());
					setFieldValue("tmp_Pro_Flag04Transfer",m_DealProcessModel.getFlag04Transfer());
					setFieldValue("tmp_Pro_Flag05TurnDown",m_DealProcessModel.getFlag05TurnDown());
					setFieldValue("tmp_Pro_Flag06TurnUp",m_DealProcessModel.getFlag06TurnUp());
					setFieldValue("tmp_Pro_Flag07Recall",m_DealProcessModel.getFlag07Recall());
					setFieldValue("tmp_Pro_Flag08Cancel",m_DealProcessModel.getFlag08Cancel());
					setFieldValue("tmp_Pro_Flag09Close",m_DealProcessModel.getFlag09Close());
					setFieldValue("tmp_Pro_Flag15ToAuditing",m_DealProcessModel.getFlag15ToAuditing());
					setFieldValue("tmp_Pro_Flag16ToAssistAuditing",m_DealProcessModel.getFlag16ToAssistAuditing());
					setFieldValue("tmp_Pro_Flag20SideBySide",m_DealProcessModel.getFlag20SideBySide());
					setFieldValue("tmp_Pro_Flag22IsSelect",m_DealProcessModel.getFlag22IsSelect());
					setFieldValue("tmp_Pro_Flag30AuditingResult",m_DealProcessModel.getFlag30AuditingResult());
					setFieldValue("tmp_Pro_Flag31IsTransfer",m_DealProcessModel.getFlag31IsTransfer());
					setFieldValue("tmp_Pro_Flag32IsToTransfer",m_DealProcessModel.getFlag32IsToTransfer());
					setFieldValue("tmp_Pro_Flag33IsEndPhase",m_DealProcessModel.getFlag33IsEndPhase());
					setFieldValue("tmp_Pro_Flag34IsEndDuplicated",m_DealProcessModel.getFlag34IsEndDuplicated());
					setFieldValue("tmp_Pro_Flag35IsCanCreateBase",(new Long(m_DealProcessModel.getFlag3IsCanCreateBase())).toString());
					setFieldValue("tmp_Pro_Flag36IsCreateBase",(new Long(m_DealProcessModel.getFlag36IsCreateBase())).toString());
					setFieldValue("tmp_Pro_FlagActive",(new Long(m_DealProcessModel.getFlagActive())).toString());
					setFieldValue("tmp_Pro_FlagAssignGroupOrUser",m_DealProcessModel.getFlagAssignGroupOrUser());
					setFieldValue("tmp_Pro_FlagBegin",(new Long(m_DealProcessModel.getFlagBegin())).toString());
					setFieldValue("tmp_Pro_FlagDuplicated",(new Long(m_DealProcessModel.getFlagDuplicated())).toString());
					setFieldValue("tmp_Pro_FlagEnd",(new Long(m_DealProcessModel.getFlagEnd())).toString());
					setFieldValue("tmp_Pro_FlagPredefined",(new Long(m_DealProcessModel.getFlagPredefined())).toString());
					setFieldValue("tmp_Pro_FlagStart",(new Long(m_DealProcessModel.getFlagStart())).toString());
					setFieldValue("tmp_Pro_FlagType",m_DealProcessModel.getFlagType());
					setFieldValue("tmp_Pro_Group",m_DealProcessModel.getGroup());
					setFieldValue("tmp_Pro_GroupID",m_DealProcessModel.getGroupID());
					setFieldValue("tmp_Pro_FlagGroupSnatch",m_DealProcessModel.getFlagGroupSnatch());
					setFieldValue("tmp_Pro_PhaseNo",m_DealProcessModel.getPhaseNo());
					setFieldValue("tmp_Pro_PhaseNoTakeMeActive",m_DealProcessModel.getPhaseNoTakeMeActive());
					setFieldValue("tmp_Pro_PosX",(new Long(m_DealProcessModel.getPosX())).toString());
					setFieldValue("tmp_Pro_PosY",(new Long(m_DealProcessModel.getPosY())).toString());
					setFieldValue("tmp_Pro_PrevPhaseNo",m_DealProcessModel.getPrevPhaseNo());
					setFieldValue("tmp_Pro_ProcessFlag00IsAvail",m_DealProcessModel.getProcessFlag00IsAvail());
					setFieldValue("tmp_Pro_ProcessType",m_DealProcessModel.getProcessType());
					setFieldValue("tmp_Pro_RoleName",m_DealProcessModel.getRoleName());
					setFieldValue("tmp_Pro_RoleOnlyID",m_DealProcessModel.getRoleOnlyID());
					setFieldValue("tmp_Pro_StDate",(new Long(m_DealProcessModel.getStDate())).toString());
					setFieldValue("tmp_Pro_StProcessAction",m_DealProcessModel.getStProcessAction());
					setFieldValue("tmp_Pro_Status",m_DealProcessModel.getProcessStatus());
					setFieldValue("tmp_Pro_TransferPhaseNo",m_DealProcessModel.getTransferPhaseNo());
					
					setFieldValue("tmp_Pro_Flag37IsNeedStartInsideFlow",(new Long(m_DealProcessModel.getFlag37IsNeedStartInsideFlow())).toString());
					setFieldValue("tmp_Pro_StartInsideFlowID",m_DealProcessModel.getStartInsideFlowID());
					setFieldValue("tmp_Pro_StartInsideFlowName",m_DealProcessModel.getStartInsideFlowName());
					setFieldValue("tmp_Pro_Flag38StartInsideFlow",(new Long(m_DealProcessModel.getFlag38StartInsideFlow())).toString());
					setFieldValue("tmp_Pro_StartInsideFlowsCount",(new Long(m_DealProcessModel.getStartInsideFlowsCount())).toString());
					setFieldValue("tmp_Pro_StartInsideFlowsNoBackCount",(new Long(m_DealProcessModel.getStartInsideFlowsNoBackCount())).toString());
					setFieldValue("tmp_Pro_StartInsideFlows",m_DealProcessModel.getStartInsideFlows());
					setFieldValue("tmp_Pro_BaseFlowID",m_DealProcessModel.getBaseFlowID());
					
				}
				else
				{
					AuditingProcessManage		m_AuditingProcessManage		= new AuditingProcessManage();
					AuditingProcessModel 		m_AuditingProcessModel		= m_AuditingProcessManage.getOneModel(p_ProcessID, p_ProcessType);
					
//					(
//					  select 'setFieldValue("' || t.fieldname || '",m_BaseModel.get' ||  f1.fieldname || '());',t.fieldname as fromname,f1.fieldname as toname
//					  from 
//						(
//							select f.fieldname ,f.datatype,replace(replace(alset.assignshort,'102\1\@\22\WF:App_AuditingProcess\1\',''),'\4\1\1\1\99\700038041\2\3\','') as dd,alset.*
//							from workflow.actlink_set alset,field f
//							where alset.actlinkid = '865' and alset.actionindex=0 and f.schemaid = 118 and (f.fieldid=alset.fieldid)
//						) t,field f1
//							where f1.schemaid  = 125
//							and (f1.fieldid = t.dd) and t.datatype in (4,6)
//					)
//					union all
//					(
//					  select 'setFieldValue("' || t.fieldname || '",(new Long(m_BaseModel.get' ||  f1.fieldname || '())).toString());',t.fieldname as fromname,f1.fieldname as toname 
//					  from 
//						(
//							select f.fieldname ,f.datatype,alset.assignshort,replace(replace(alset.assignshort,'102\1\@\22\WF:App_AuditingProcess\1\',''),'\4\1\1\1\99\700038041\2\3\','') as dd,alset.*
//							from workflow.actlink_set alset,field f
//							where alset.actlinkid = '865' and alset.actionindex=0 and f.schemaid = 118 and (f.fieldid=alset.fieldid)
//						) t,field f1
//							where f1.schemaid  = 125
//							and (f1.fieldid = t.dd) and t.datatype not in (4,6)
//					)
//					order by fromname	

					setFieldValue("BaseOpenDateTime",(new Long(m_AuditingProcessModel.getBaseOpenDateTime())).toString());
					setFieldValue("tmp_Pro_ActionName",m_AuditingProcessModel.getActionName());
					setFieldValue("tmp_Pro_ActionPageID",m_AuditingProcessModel.getActionPageID());
					setFieldValue("tmp_Pro_ActionPageName",m_AuditingProcessModel.getActionPageName());
					setFieldValue("tmp_Pro_Assginee",m_AuditingProcessModel.getAssginee());
					setFieldValue("tmp_Pro_AssgineeID",m_AuditingProcessModel.getAssgineeID());
					setFieldValue("tmp_Pro_AssigneeCorp",m_AuditingProcessModel.getAssigneeCorp());
					setFieldValue("tmp_Pro_AssigneeCorpID",m_AuditingProcessModel.getAssigneeCorpID());
					setFieldValue("tmp_Pro_AssigneeDN",m_AuditingProcessModel.getAssigneeDN());
					setFieldValue("tmp_Pro_AssigneeDNID",m_AuditingProcessModel.getAssigneeDNID());
					setFieldValue("tmp_Pro_AssigneeDep",m_AuditingProcessModel.getAssigneeDep());
					setFieldValue("tmp_Pro_AssigneeDepID",m_AuditingProcessModel.getAssigneeDepID());
					setFieldValue("tmp_Pro_AssistantAuditingPhaseNo",m_AuditingProcessModel.getAssistantAuditingPhaseNo());
					setFieldValue("tmp_Pro_AuditingOverTimeDate",(new Long(m_AuditingProcessModel.getAuditingOverTimeDate())).toString());
					setFieldValue("tmp_Pro_AuditingOverTimeDate_Relative",(new Long(m_AuditingProcessModel.getAuditingOverTimeDate_Relative())).toString());
					setFieldValue("tmp_Pro_AuditingWayIsActive",m_AuditingProcessModel.getAuditingWayIsActive());
					setFieldValue("tmp_Pro_AuditingWayNo",m_AuditingProcessModel.getAuditingWayNo());
					setFieldValue("tmp_Pro_AuditingWayPhaseNo",m_AuditingProcessModel.getAuditingWayPhaseNo());
					setFieldValue("tmp_Pro_BaseID",m_AuditingProcessModel.getProcessBaseID());
					setFieldValue("tmp_Pro_BaseSchema",m_AuditingProcessModel.getProcessBaseSchema());
					setFieldValue("tmp_Pro_BaseTplStateID",m_AuditingProcessModel.getBaseStateCode());
					setFieldValue("tmp_Pro_BaseTplStateName",m_AuditingProcessModel.getBaseStateName());
					setFieldValue("tmp_Pro_BgDate",(new Long(m_AuditingProcessModel.getBgDate())).toString());
					setFieldValue("tmp_Pro_CloseBaseSamenessGroup",m_AuditingProcessModel.getCloseBaseSamenessGroup());
					setFieldValue("tmp_Pro_CloseBaseSamenessGroupID",m_AuditingProcessModel.getCloseBaseSamenessGroupID());
					setFieldValue("tmp_Pro_Commissioner",m_AuditingProcessModel.getCommissioner());
					setFieldValue("tmp_Pro_CommissionerID",m_AuditingProcessModel.getCommissionerID());
					setFieldValue("tmp_Pro_CreateByUserID",m_AuditingProcessModel.getCreateByUserID());
					setFieldValue("tmp_Pro_Dealer",m_AuditingProcessModel.getDealer());
					setFieldValue("tmp_Pro_DealerCorp",m_AuditingProcessModel.getDealerCorp());
					setFieldValue("tmp_Pro_DealerCorpID",m_AuditingProcessModel.getDealerCorpID());
					setFieldValue("tmp_Pro_DealerDN",m_AuditingProcessModel.getDealerDN());
					setFieldValue("tmp_Pro_DealerDNID",m_AuditingProcessModel.getDealerDNID());
					setFieldValue("tmp_Pro_DealerDep",m_AuditingProcessModel.getDealerDep());
					setFieldValue("tmp_Pro_DealerDepID",m_AuditingProcessModel.getDealerDepID());
					setFieldValue("tmp_Pro_DealerID",m_AuditingProcessModel.getDealerID());
					setFieldValue("tmp_Pro_Desc",m_AuditingProcessModel.getDesc());
					setFieldValue("tmp_Pro_EdDate",(new Long(m_AuditingProcessModel.getEdDate())).toString());
					setFieldValue("tmp_Pro_EdProcessAction",m_AuditingProcessModel.getEdProcessAction());
					setFieldValue("tmp_Pro_Flag01Assign",m_AuditingProcessModel.getFlag01Assign());
					setFieldValue("tmp_Pro_Flag02Copy",m_AuditingProcessModel.getFlag02Copy());
					setFieldValue("tmp_Pro_Flag03Assist",m_AuditingProcessModel.getFlag03Assist());
					setFieldValue("tmp_Pro_Flag04Transfer",m_AuditingProcessModel.getFlag04Transfer());
					setFieldValue("tmp_Pro_Flag08Cancel",m_AuditingProcessModel.getFlag08Cancel());
					setFieldValue("tmp_Pro_Flag15ToAuditing",m_AuditingProcessModel.getFlag15ToAuditing());
					setFieldValue("tmp_Pro_Flag16ToAssistAuditing",m_AuditingProcessModel.getFlag16ToAssistAuditing());
					setFieldValue("tmp_Pro_Flag16TurnUpAuditing",m_AuditingProcessModel.getFlag16TurnUpAuditing());
					setFieldValue("tmp_Pro_Flag17RecallAuditing",m_AuditingProcessModel.getFlag17RecallAuditing());
					setFieldValue("tmp_Pro_Flag20SideBySide",m_AuditingProcessModel.getFlag20SideBySide());
					setFieldValue("tmp_Pro_Flag22IsSelect",m_AuditingProcessModel.getFlag22IsSelect());
					setFieldValue("tmp_Pro_Flag33IsEndPhase",m_AuditingProcessModel.getFlag33IsEndPhase());
					setFieldValue("tmp_Pro_Flag34IsEndDuplicated",m_AuditingProcessModel.getFlag34IsEndDuplicated());
					setFieldValue("tmp_Pro_Flag35IsCanCreateBase",(new Long(m_AuditingProcessModel.getFlag3IsCanCreateBase())).toString());
					setFieldValue("tmp_Pro_Flag36IsCreateBase",(new Long(m_AuditingProcessModel.getFlag36IsCreateBase())).toString());
					setFieldValue("tmp_Pro_FlagActive",(new Long(m_AuditingProcessModel.getFlagActive())).toString());
					setFieldValue("tmp_Pro_FlagAssignGroupOrUser",m_AuditingProcessModel.getFlagAssignGroupOrUser());
					setFieldValue("tmp_Pro_FlagBegin",(new Long(m_AuditingProcessModel.getFlagBegin())).toString());
					setFieldValue("tmp_Pro_FlagDuplicated",(new Long(m_AuditingProcessModel.getFlagDuplicated())).toString());
					setFieldValue("tmp_Pro_FlagEnd",(new Long(m_AuditingProcessModel.getFlagEnd())).toString());
					setFieldValue("tmp_Pro_FlagPredefined",(new Long(m_AuditingProcessModel.getFlagPredefined())).toString());
					setFieldValue("tmp_Pro_FlagStart",(new Long(m_AuditingProcessModel.getFlagStart())).toString());
					setFieldValue("tmp_Pro_FlagType",m_AuditingProcessModel.getFlagType());
					setFieldValue("tmp_Pro_Group",m_AuditingProcessModel.getGroup());
					setFieldValue("tmp_Pro_GroupID",m_AuditingProcessModel.getGroupID());
					setFieldValue("tmp_Pro_FlagGroupSnatch",m_AuditingProcessModel.getIsGroupSnatch());
					setFieldValue("tmp_Pro_PhaseNo",m_AuditingProcessModel.getPhaseNo());
					setFieldValue("tmp_Pro_PhaseNoTakeMeActive",m_AuditingProcessModel.getPhaseNoTakeMeActive());
					setFieldValue("tmp_Pro_PosX",(new Long(m_AuditingProcessModel.getPosX())).toString());
					setFieldValue("tmp_Pro_PosY",(new Long(m_AuditingProcessModel.getPosY())).toString());
					setFieldValue("tmp_Pro_PrevPhaseNo",m_AuditingProcessModel.getPrevPhaseNo());
					setFieldValue("tmp_Pro_ProcessFlag00IsAvail",m_AuditingProcessModel.getProcessFlag00IsAvail());
					setFieldValue("tmp_Pro_ProcessType",m_AuditingProcessModel.getProcessType());
					setFieldValue("tmp_Pro_RoleName",m_AuditingProcessModel.getRoleName());
					setFieldValue("tmp_Pro_RoleOnlyID",m_AuditingProcessModel.getRoleOnlyID());
					setFieldValue("tmp_Pro_StDate",(new Long(m_AuditingProcessModel.getStDate())).toString());
					setFieldValue("tmp_Pro_StProcessAction",m_AuditingProcessModel.getStProcessAction());
					setFieldValue("tmp_Pro_Status",m_AuditingProcessModel.getProcessStatus());					
				}
			}
			else
			{
				BaseWrite_Log.writeLog("根据工单环节关键字,读工单环节信息",1,"根据工单环节关键字,读工单环节信息失败，参数错误！");
				return false;			
			}
			}catch(Exception ex)
			{
				BaseWrite_Log.writeExceptionLog("根据工单环节关键字,读工单环节信息",ex);
				return false;
			}		
			return true;
		
	}

}
