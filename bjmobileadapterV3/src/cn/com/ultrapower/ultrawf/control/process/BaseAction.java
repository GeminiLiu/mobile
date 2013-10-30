package cn.com.ultrapower.ultrawf.control.process;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;		
import java.util.Map;
import java.io.File;

import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.config.ConfigUserCloseBaseGroupModel;
import cn.com.ultrapower.ultrawf.models.config.ParBaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.config.ParConfigUserCloseBaseGroupModel;
import cn.com.ultrapower.ultrawf.models.config.User;
import cn.com.ultrapower.ultrawf.models.config.UserModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcess;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessModel;
import cn.com.ultrapower.ultrawf.models.process.Base;
import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcess;
import cn.com.ultrapower.ultrawf.models.process.DealProcessModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.config.BaseCategory;

import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachment;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseDealObject;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseToDealObject;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Auditing;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_New;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Next;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextBaseCustom;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextCustomHavaMatchRole;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_ToAuditing;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_ToDeal;
import cn.com.ultrapower.ultrawf.control.config.ConfigUserCloseBaseGroupManage;
import cn.com.ultrapower.ultrawf.control.config.ConfigUserCommissionManage;
//import cn.com.ultrapower.ultrawf.control.process.bean.PublicFieldInfo;
import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.eoms.processSheet.NMSAlarmInterface;
import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;
import cn.com.ultrapower.ultrawf.share.Guid;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.OperationLogFile;
public class BaseAction //implements IBaseAction
{
	
	Map Hashtable_BaseAllFields 		= null;

	/**
	 * 工单动作：新建工单函数
	 * @param p_BaseSchema				工单类别的Schema
	 * @param p_OperateUserLoginName	操作工单动作的用户登陆名
	 * @param p_FieldListInfo			工单新建动作的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回工单的BaseID			（C1字段）
	 */
	public String Action_New(String p_OperateUserLoginName, String p_BaseSchema, List p_FieldListInfo, List p_BaseAttachmentList) {				
		//工单信息初始化
		if (this.Init_Open_Base(p_BaseSchema,null,p_OperateUserLoginName,0,"新建")==false)
		{  
			this.Init_Close_Base();	
			return null;	
		}		

		//新建时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Map Hashtable_BaseOwnAllFields = new HashMap(); 
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			//if (FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("草稿;")>-1 && FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("草稿;")>-1)
			//{
				Integer obj_Integer = new Integer(obj_BaseOwnFieldInfoModel.GetBase_field_Type());
				Hashtable_BaseOwnAllFields.put(
						obj_BaseOwnFieldInfoModel.GetBase_field_DBName(), 
						new BaseFieldInfo(
								obj_BaseOwnFieldInfoModel.GetBase_field_DBName(),
								obj_BaseOwnFieldInfoModel.GetBase_field_ID(),
								null,
								obj_Integer.intValue(),
								1,
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep(),
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()));
				
			//}
		}
		int fieldCount=0;
		if(p_FieldListInfo!=null)
			fieldCount=p_FieldListInfo.size();
		for (int i=0;i<fieldCount;i++)
		{
			BaseFieldInfo p_obj_BaseFieldInfo 		= (BaseFieldInfo)p_FieldListInfo.get(i);
			BaseFieldInfo m_obj_BaseOwnFieldInfo 	= (BaseFieldInfo)Hashtable_BaseOwnAllFields.get(p_obj_BaseFieldInfo.getStrFieldName());
			if (m_obj_BaseOwnFieldInfo != null)//传递的显示或编辑字段可以显示或编辑
			{				
				m_obj_BaseOwnFieldInfo.setStrFieldValue(p_obj_BaseFieldInfo.getStrFieldValue());
				Hashtable_BaseAllFields.put(m_obj_BaseOwnFieldInfo.getStrFieldName(),m_obj_BaseOwnFieldInfo);
			}
			else
			{
				Init_BaseWrite_Log("新建",1,p_obj_BaseFieldInfo.getStrFieldName() + "字段在草稿阶段不可填写");
				this.Init_Close_Base();
				return null;
			}
		}
		
		Hashtable_BaseOwnAllFields.clear();
		Hashtable_BaseOwnAllFields = null;
//		附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return null;	
			}				
		}

        //转到后台操作
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,null,p_OperateUserLoginName,0);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{  
			Init_BaseWrite_Log("新建",1,"新建失败！");
		    this.Init_Close_Base();	
			return null;	
		}				
		
		this.Init_Close_Base();
		return str_Return_BaseID;
	}

	/**
	 * 工单动作：分派工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_ToDealDesc					派发表述
	 * @param p_BaseToDealObject			派发对象的List(BaseToDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		    附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_ToDeal(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_ToDealDesc,List p_BaseToDealObject,List p_BaseAttachmentList) {
		if(p_ToDealDesc==null){
			Init_BaseWrite_Log("派发",1,"派发，没有写描述！");
			return false;			
		}
		//工单信息初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"派发")==false)
		{
			Init_BaseWrite_Log("派发",1,"初始化失败！");
			this.Init_Close_Base();	
			return false;	
		}		
		//插入派发记录
		if (this.Init_Push_DealProcess(p_BaseToDealObject)==false)
		{
			Init_BaseWrite_Log("派发",1,"插入处理信息！");
			this.Init_Close_Base();	
			return false;	
		}
		//需要指明派发操作process,故如果用户选择的派发记录的ProcessID不为空,则给tmp_BaseActionBtn_Char赋值为“派发”
		if(!this.Init_BaseActionBtn("派发","tmp_Select_Assigner_ProcessIDS",false)){
			Init_BaseWrite_Log("派发",1,"BaseActiveBtn赋值“派发”失败！");
			this.Init_Close_Base();	
			return false;			
		}
//		附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台操作
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			Init_BaseWrite_Log("派发",1,"派发失败！");
			this.Init_Close_Base();	
			return false;	
		}				
		
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 工单动作：组自动转派工单时通过代理人传递ProcessId和ProcessType转派工单	
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_ToDealDesc					派发表述
	 * @param p_BaseToDealObject			派发对象的List(BaseToDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_ToDeal_FormGivenUser(String p_OperateUserLoginName,
			String p_processId,String p_processType,String p_BaseSchema, String p_BaseID, String p_GroupToDealDesc,List p_BaseToDealObject,List p_BaseAttachmentList)
	{
		if(p_GroupToDealDesc==null){
			Init_BaseWrite_Log("派发",1,"派发，没有写描述！");
			return false;			
		}
		
		//工单信息初始化
    	if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"派发")==false)
		{
			Init_BaseWrite_Log("派发",1,"初始化失败！");
			this.Init_Close_Base();	
			return false;	
		}
    	
		//插入派发记录
		if (this.Init_Push_DealProcess(p_BaseToDealObject)==false)
		{
			Init_BaseWrite_Log("派发",1,"插入处理信息！");
			this.Init_Close_Base();	
			return false;	
		}
		
		//需要指明派发操作process,故如果用户选择的派发记录的ProcessID不为空,则给tmp_BaseActionBtn_Char赋值为“派发”
		if(!this.Init_BaseActionBtn("派发","tmp_Select_Assigner_ProcessIDS",false)){
			Init_BaseWrite_Log("派发",1,"BaseActiveBtn赋值“派发”失败！");
			this.Init_Close_Base();	
			return false;			
		}
//		附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台操作
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			Init_BaseWrite_Log("派发",1,"派发失败！");
			this.Init_Close_Base();	
			return false;	
		}				
		
		this.Init_Close_Base();		
		return true;
	}	
	/**
	 * 工单动作：受理处理工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)

	 * @return 返回是否成功
	 */
	public boolean Action_Start(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID,List p_BaseAttachmentList) {
		//工单信息初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"受理")==false)
		{
			Init_BaseWrite_Log("受理",1,"受理，没有写描述！");
			return false;	
		}		
		//不需要指明操作process,直接给tmp_BaseActionBtn_Char赋值“受理”
		if(!this.Init_BaseActionBtn("受理",null,true)){
			Init_BaseWrite_Log("受理",1,"BaseActiveBtn赋值“受理”失败！");
			return false;			
		}
//		附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台操作
	    String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}				
		
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 工单动作：阶段回复工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealPhaseNoticeDesc			该工单动作操作时的内容	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList	    	附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_PhaseNotice(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealPhaseNoticeDesc,List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_DealPhaseNoticeDesc == null){
			Init_BaseWrite_Log("阶段回复",1,"阶段回复,没有写描述！");
			return false;
		}
//		工单信息初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"阶段回复")==false)
		{
			Init_BaseWrite_Log("阶段回复",1,"初始化失败！");
			return false;	
		}	
		
//		阶段回复时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Map Hashtable_BaseOwnAllFields = new HashMap();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("待处理;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("待处理;")>-1 
					)
					|| 
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("处理中;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("处理中;")>-1 
					)				
				)
			{
				Integer obj_Integer = new Integer(obj_BaseOwnFieldInfoModel.GetBase_field_Type());
				Hashtable_BaseOwnAllFields.put(
						obj_BaseOwnFieldInfoModel.GetBase_field_DBName(), 
						new BaseFieldInfo(
								obj_BaseOwnFieldInfoModel.GetBase_field_DBName(),
								obj_BaseOwnFieldInfoModel.GetBase_field_ID(),
								null,
								obj_Integer.intValue(),
								1,
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep(),
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()));
			}
		}
		int fieldCount=0;
		if(p_FieldListInfo!=null)
			fieldCount=p_FieldListInfo.size();
		for (int i=0;i<fieldCount;i++)
		{
			BaseFieldInfo p_obj_BaseFieldInfo = (BaseFieldInfo)p_FieldListInfo.get(i);
			BaseFieldInfo m_obj_BaseOwnFieldInfo = (BaseFieldInfo)Hashtable_BaseOwnAllFields.get(p_obj_BaseFieldInfo.getStrFieldName());
			if (m_obj_BaseOwnFieldInfo != null)
			{
				m_obj_BaseOwnFieldInfo.setStrFieldValue(p_obj_BaseFieldInfo.getStrFieldValue());
				Hashtable_BaseAllFields.put(m_obj_BaseOwnFieldInfo.getStrFieldName(),m_obj_BaseOwnFieldInfo);
			}
			else
			{ 
				Init_BaseWrite_Log("阶段处理",1,p_obj_BaseFieldInfo.getStrFieldName()+"阶段处理失败，字段在“阶段处理”时不可填写！");
				return false;
			}
		}
		Hashtable_BaseOwnAllFields.clear();
		Hashtable_BaseOwnAllFields = null;
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_OpDeal_Desc")).setStrFieldValue(p_DealPhaseNoticeDesc);
		
		//不需要指明操作process,直接给tmp_BaseActionBtn_Char赋值为“阶段处理”		
		if(!this.Init_BaseActionBtn("阶段处理",null,true)){
			return false;			
		}
//		附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}			
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 工单动作：完成处理工单函数
	 * @param p_OperateUserLoginName        操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealFinishDesc				该工单动作操作时的内容
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Finish(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealFinishDesc,
			List p_FieldListInfo,List p_BaseAttachmentList) {		
		if (p_DealFinishDesc == null){
			Init_BaseWrite_Log("完成",1,"完成,没有写描述！");
			return false;
		}

		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"完成")==false)
		{
			Init_BaseWrite_Log("完成",1,"初始化失败！");
			return false;	
		}	
		
//		完成时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Map Hashtable_BaseOwnAllFields = new HashMap();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("待处理;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("待处理;")>-1 
					)
					|| 
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("处理中;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("处理中;")>-1 
					)				
				)
			{
				Integer obj_Integer = new Integer(obj_BaseOwnFieldInfoModel.GetBase_field_Type());
				Hashtable_BaseOwnAllFields.put(
						obj_BaseOwnFieldInfoModel.GetBase_field_DBName(), 
						new BaseFieldInfo(
								obj_BaseOwnFieldInfoModel.GetBase_field_DBName(),
								obj_BaseOwnFieldInfoModel.GetBase_field_ID(),
								null,
								obj_Integer.intValue(),
								1,
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep(),
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()));
			}
		}
		int fieldCount=0;
		if(p_FieldListInfo!=null)
			fieldCount=p_FieldListInfo.size();
		for (int i=0;i<fieldCount;i++)
		{
			BaseFieldInfo p_obj_BaseFieldInfo = (BaseFieldInfo)p_FieldListInfo.get(i);
			BaseFieldInfo m_obj_BaseOwnFieldInfo = (BaseFieldInfo)Hashtable_BaseOwnAllFields.get(p_obj_BaseFieldInfo.getStrFieldName());
			if (m_obj_BaseOwnFieldInfo != null)
			{
				m_obj_BaseOwnFieldInfo.setStrFieldValue(p_obj_BaseFieldInfo.getStrFieldValue());
				Hashtable_BaseAllFields.put(m_obj_BaseOwnFieldInfo.getStrFieldName(),m_obj_BaseOwnFieldInfo);
			}
			else
			{
				Init_BaseWrite_Log("完成",1,p_obj_BaseFieldInfo.getStrFieldName()+"完成失败，字段在“完成”时不可填写！");
				return false;
			}
		}
		Hashtable_BaseOwnAllFields.clear();
		Hashtable_BaseOwnAllFields = null;
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_OpDeal_Desc")).setStrFieldValue(p_DealFinishDesc);
		
		//不需要指明操作process,直接给tmp_BaseActionBtn_Char赋值为“完成”		
		if(!this.Init_BaseActionBtn("完成",null,true)){
			return false;			
		}
		
		//附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}			
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 描述：确认抄送工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealConfirmDesc				该工单动作操作时的内容
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)

	 * @return 返回是否成功
	 */
	public boolean Action_Confirm(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealConfirmDesc,
			List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_DealConfirmDesc == null){
			Init_BaseWrite_Log("确认抄送",1,"确认抄送，没有写描述！");
			return false;
		}

		//操作工单信息初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"确认")==false)
		{   
			Init_BaseWrite_Log("确认",1,"“确认”初始化失败！");
			return false;	
		}	
		
//		确认时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Map Hashtable_BaseOwnAllFields = new HashMap();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("待处理;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("待处理;")>-1 
					)								
				)
			{
				Integer obj_Integer = new Integer(obj_BaseOwnFieldInfoModel.GetBase_field_Type());
				Hashtable_BaseOwnAllFields.put(
						obj_BaseOwnFieldInfoModel.GetBase_field_DBName(), 
						new BaseFieldInfo(
								obj_BaseOwnFieldInfoModel.GetBase_field_DBName(),
								obj_BaseOwnFieldInfoModel.GetBase_field_ID(),
								null,
								obj_Integer.intValue(),
								1,
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep(),
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()));
			}
		}
		int fieldCount=0;
		if(p_FieldListInfo!=null)
			fieldCount=p_FieldListInfo.size();
		for (int i=0;i<fieldCount;i++)
		{
			BaseFieldInfo p_obj_BaseFieldInfo = (BaseFieldInfo)p_FieldListInfo.get(i);
			BaseFieldInfo m_obj_BaseOwnFieldInfo = (BaseFieldInfo)Hashtable_BaseOwnAllFields.get(p_obj_BaseFieldInfo.getStrFieldName());
			if (m_obj_BaseOwnFieldInfo != null)
			{
				m_obj_BaseOwnFieldInfo.setStrFieldValue(p_obj_BaseFieldInfo.getStrFieldValue());
				Hashtable_BaseAllFields.put(m_obj_BaseOwnFieldInfo.getStrFieldName(),m_obj_BaseOwnFieldInfo);
			}
			else
			{
				Init_BaseWrite_Log("确认抄送",1,p_obj_BaseFieldInfo.getStrFieldName()+"确认抄送失败，“确认抄送”时不可填写！");
				return false;
			}
		}
		Hashtable_BaseOwnAllFields.clear();
		Hashtable_BaseOwnAllFields = null;
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_OpConfirm_Desc")).setStrFieldValue(p_DealConfirmDesc);
	
		//不需要指明操作process,直接给tmp_BaseActionBtn_Char赋值为“派发”
		if(!this.Init_BaseActionBtn("确认",null,true)){
			Init_BaseWrite_Log("确认",1,"BaseActiveBtn赋值“确认”失败！");
			return false;			
		}
		
//		附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台操作
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{					
			return false;	
		}			
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 描述：催办处理工单函数	
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealConfirmDesc				该工单动作操作时的内容	 * @param p_BaseDealObject				崔办对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealHasten(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealHastenDesc,
			List p_BaseDealObject,List p_BaseAttachmentList) {
		if (p_DealHastenDesc == null){
			Init_BaseWrite_Log("催办",1,"催办，没有写描述！");
			return false;
		}	
		//工单信息初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"催办")==false)
		{
			Init_BaseWrite_Log("催办",1,"初始化失败！");
			return false;	
		}		
		
		//初始化催办对象process信息
		if (this.Init_Select_OtherDealProcess(0,p_BaseDealObject)==false)
		{
			return false;	
		}
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_Others_Deal_Desc")).setStrFieldValue(p_DealHastenDesc);
		
		//需要指明催办对象process,故如果用户选择的催办记录不为空,则给tmp_BaseActionBtn_Char赋值为“催办”
		if(!this.Init_BaseActionBtn("催办","tmp_Select_OtherDeal_ProcessIDS",false)){
			return false;			
		}
		
//		附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台操作
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}				
		
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 描述：退回处理工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealTurnDownDesc			该工单动作操作时的内容	 * @param p_BaseDealObject				退回对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealTurnDown(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealTurnDownDesc,
			List p_BaseDealObject,List p_BaseAttachmentList) {
		if (p_DealTurnDownDesc == null){
			Init_BaseWrite_Log("退回",1,"退回，没有写描述！");
			return false;
		}		
		//操作工单信息初始化		
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"退回")==false)
		{
			Init_BaseWrite_Log("退回",1,"初始化失败！");
			return false;	
		}	
		
		//退回对象process初始化
		if (this.Init_Select_OtherDealProcess(1,p_BaseDealObject)==false)
		{
			return false;	
		}
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_Others_Deal_Desc")).setStrFieldValue(p_DealTurnDownDesc);
		
		//需要指明退回对象process,故如果用户选择的退回记录不为空,则给tmp_BaseActionBtn_Char赋值为“退回”
		if(!this.Init_BaseActionBtn("退回","tmp_Select_OtherDeal_ProcessIDS",false)){
			return false;			
		}
		
//		附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}				
		
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 描述：驳回处理工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealTurnUpDesc				该工单动作操作时的内容	 * @param p_BaseDealObject				驳回对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealTurnUp(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealTurnUpDesc,
			List p_BaseDealObject,List p_BaseAttachmentList) {
		
		if (p_DealTurnUpDesc == null){
			Init_BaseWrite_Log("驳回",1,"请插入驳回描述！");
			return false;
		}
		
		//操作工单初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"驳回")==false)
		{   
			Init_BaseWrite_Log("驳回",1,"驳回初始化失败！");
			return false;	
		}		
				
		//驳回对象process初始化
		if (this.Init_Select_OtherDealProcess(2,p_BaseDealObject)==false)
		{
			Init_BaseWrite_Log("驳回",1,"驳回process获得失败！");
			return false;	
		}
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_Others_Deal_Desc")).setStrFieldValue(p_DealTurnUpDesc);
	
		//需要指明驳回对象process,故如果用户选择的驳回记录不为空,则给tmp_BaseActionBtn_Char赋值为“驳回处理”
		if(!this.Init_BaseActionBtn("驳回处理","tmp_Select_OtherDeal_ProcessIDS",false))
		{
			Init_BaseWrite_Log("驳回",1,"BaseActiveBtn赋值“驳回处理”失败！");
			return false;			
		}
		
//		附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			Init_BaseWrite_Log("驳回",1,"驳回失败！");
			return false;	
		}				
		
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 描述：追回处理工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealRecallDesc				该工单动作操作时的内容     * @param _Auditor_rad_AuditorRecallNewOp 追回是否从派
	 * @param p_BaseDealObject				追回对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	
	public boolean Action_DealRecall(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealRecallDesc,
			int P_Others_Deal_rad_DealRecallNewOp,List p_BaseDealObject,List p_BaseAttachmentList) {
		BaseFieldInfo baseFieldInfo  = null;
		if (p_DealRecallDesc == null){
			Init_BaseWrite_Log("追回",1,"追回，没有写描述！");
			return false;
		}		
			 	
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"追回")==false)
		{
			Init_BaseWrite_Log("追回",1,"追回初始化失败！");
			return false;	
		}	
		
		//追回时“追回是否重派”字段根据参数P_Others_Deal_rad_DealRecallNewOp赋值
		//如果当前环节只有一个下一环节,则P_Others_Deal_rad_DealRecallNewOp的值必须为0(是)
        //如果当前环节的所有下一环节都被选中追回,则P_Others_Deal_rad_DealRecallNewOp的值必须为0(是)
		if(P_Others_Deal_rad_DealRecallNewOp!=0&&P_Others_Deal_rad_DealRecallNewOp!=1){
			Init_BaseWrite_Log("追回",1,"“追回是否重派”数据错误！");
			return false;				
		}else{
		    baseFieldInfo = (BaseFieldInfo)Hashtable_BaseAllFields.get("P_Others_Deal_rad_DealRecallNewOp");
	    	baseFieldInfo.setStrFieldValue(P_Others_Deal_rad_DealRecallNewOp+"");
	   	}
		 
		//追回process初始化
		//如果P_Others_Deal_rad_DealRecallNewOp不满足要求则报错
		if (this.Init_Select_OtherDealProcess(3,p_BaseDealObject)==false)
		{
			Init_BaseWrite_Log("追回",1,"追回process获得失败！");
			return false;	
		}
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_Others_Deal_Desc")).setStrFieldValue(p_DealRecallDesc);
		
		//需要指明追回对象process,故如果用户选择的追回记录不为空,则给tmp_BaseActionBtn_Char赋值为“追回处理”
		if(!this.Init_BaseActionBtn("追回处理","tmp_Select_OtherDeal_ProcessIDS",false)){
			Init_BaseWrite_Log("追回",1,"BaseActiveBtn赋值“追回处理”失败！");
			return false;			
		}
		
//		附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//后台操作
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}				
		
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 描述：关闭工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealCloseDesc				该工单动作操作时的内容
	 * @param p_CloseSatisfaction           归档满意度		
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Close(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealCloseDesc,
			String p_CloseSatisfaction,
			List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_DealCloseDesc == null){
			Init_BaseWrite_Log("关闭",1,"关闭，没有写描述！");
			return false;
		}
		//操作工单信息初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"关闭")==false)
		{
			Init_BaseWrite_Log("关闭",1,"初始化失败！");
			return false;	
		}	
		
//		关闭时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Map Hashtable_BaseOwnAllFields = new HashMap();
		int listCount=0;
		if(List_BaseOwnFieldInfoGet!=null)
			listCount=List_BaseOwnFieldInfoGet.size();
		for (int i=0;i<listCount;i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("已完成;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("已完成;")>-1 
					)								
				)
			{
				Integer obj_Integer = new Integer(obj_BaseOwnFieldInfoModel.GetBase_field_Type());
				Hashtable_BaseOwnAllFields.put(
						obj_BaseOwnFieldInfoModel.GetBase_field_DBName(), 
						new BaseFieldInfo(
								obj_BaseOwnFieldInfoModel.GetBase_field_DBName(),
								obj_BaseOwnFieldInfoModel.GetBase_field_ID(),
								null,
								obj_Integer.intValue(),
								1,
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep(),
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()));
			}
		}
		int fieldCount=0;
		if(p_FieldListInfo!=null)
			fieldCount=p_FieldListInfo.size();
		
		for (int i=0;i<fieldCount;i++)
		{
			BaseFieldInfo p_obj_BaseFieldInfo = (BaseFieldInfo)p_FieldListInfo.get(i);
			BaseFieldInfo m_obj_BaseOwnFieldInfo = (BaseFieldInfo)Hashtable_BaseOwnAllFields.get(p_obj_BaseFieldInfo.getStrFieldName());
			if (m_obj_BaseOwnFieldInfo != null)
			{
				m_obj_BaseOwnFieldInfo.setStrFieldValue(p_obj_BaseFieldInfo.getStrFieldValue());
				Hashtable_BaseAllFields.put(m_obj_BaseOwnFieldInfo.getStrFieldName(),m_obj_BaseOwnFieldInfo);
			}
			else
			{
				Init_BaseWrite_Log("关闭",1,p_obj_BaseFieldInfo.getStrFieldName()+"关闭失败，“关闭”时不可填写！");
				return false;
			}
		}
		Hashtable_BaseOwnAllFields.clear();
		Hashtable_BaseOwnAllFields = null;
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_OpClose_Desc")).setStrFieldValue(p_DealCloseDesc);
		 //归档满意度
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_CloseOpSatisfaction")).setStrFieldValue(p_CloseSatisfaction);

		//不需要指明操作对象process,直接给tmp_BaseActionBtn_Char赋值为“关闭”
		if(!this.Init_BaseActionBtn("关闭",null,true)){
			return false;			
		}
		
//		附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//转到后台操作
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}			
		this.Init_Close_Base();		
		return true;
	}

	/**
	 * 描述：作废工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealCancelDesc				该工单动作操作时的内容	 * @param p_BaseAttachmentList		    附件对象的List(BaseAttachment类的对象的数组)

	 * @return 返回是否成功
	 */
	public boolean Action_Cancel(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID, String p_DealCancelDesc,List p_BaseAttachmentList) {
		if (p_DealCancelDesc == null){
			Init_BaseWrite_Log("作废",1,"作废，没有写描述！");
			return false;
		}
		
		//操作工单初始化
		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"作废")==false)
		{
			Init_BaseWrite_Log("作废",1,"“作废”初始化失败！");
			return false;	
		}	
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_OpCancel_Desc")).setStrFieldValue(p_DealCancelDesc);
		
		//不需要指明操作对象process,直接给tmp_BaseActionBtn_Char赋值为“作废”
		if(!this.Init_BaseActionBtn("作废",null,true)){
			Init_BaseWrite_Log("作废",1,"BaseActiveBtn赋值“作废”失败！");
			return false;			
		}
		
//		附件
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台操作
	    String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}				
		this.Init_Close_Base();		
		return true;
	}

	
	/**
	 * 工单动作：审批工单函数

	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealPhaseNoticeDesc			该工单动作操作时的内容
	 * @param p_AuditingResult              审批结果
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList	    	附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Auditing(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_AuditingDesc,String p_AuditingResult,List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_AuditingDesc == null){
			Init_BaseWrite_Log("审批",1,"审批,没有写描述！");
			return false;
		}
		if (p_AuditingResult == null){
			Init_BaseWrite_Log("审批",1,"审批,没有选择审批结果！");
			return false;
		}
//		工单信息初始化

		if (this.Init_Open_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1,"审批")==false)
		{
			Init_BaseWrite_Log("审批",1,"初始化失败！");
			return false;	
		}	
		
//		审批时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Map Hashtable_BaseOwnAllFields = new HashMap();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("待审批;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("待审批;")>-1 
					)
					|| 
					(
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep()).indexOf("审批中;")>-1 
							&&
							FormatString.CheckNullString(obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()).indexOf("审批中;")>-1 
					)				
				)
			{
				Integer obj_Integer = new Integer(obj_BaseOwnFieldInfoModel.GetBase_field_Type());
				Hashtable_BaseOwnAllFields.put(
						obj_BaseOwnFieldInfoModel.GetBase_field_DBName(), 
						new BaseFieldInfo(
								obj_BaseOwnFieldInfoModel.GetBase_field_DBName(),
								obj_BaseOwnFieldInfoModel.GetBase_field_ID(),
								null,
								obj_Integer.intValue(),
								1,
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep(),
								obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep()));
			}
		}

		for (int i=0;i<p_FieldListInfo.size();i++)
		{
			BaseFieldInfo p_obj_BaseFieldInfo = (BaseFieldInfo)p_FieldListInfo.get(i);
			BaseFieldInfo m_obj_BaseOwnFieldInfo = (BaseFieldInfo)Hashtable_BaseOwnAllFields.get(p_obj_BaseFieldInfo.getStrFieldName());
			if (m_obj_BaseOwnFieldInfo != null)
			{
				m_obj_BaseOwnFieldInfo.setStrFieldValue(p_obj_BaseFieldInfo.getStrFieldValue());
				Hashtable_BaseAllFields.put(m_obj_BaseOwnFieldInfo.getStrFieldName(),m_obj_BaseOwnFieldInfo);
			}
			else
			{ 
				Init_BaseWrite_Log("审批",1,p_obj_BaseFieldInfo.getStrFieldName()+"审批失败，字段在“审批”时不可填写！");
				return false;
			}
		}
		Hashtable_BaseOwnAllFields.clear();
		Hashtable_BaseOwnAllFields = null;
		
		//描述信息
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_P_OpAuditing_Desc")).setStrFieldValue(p_AuditingDesc);
			
		//审批结果		
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_OpDesc_AuditingResult")).setStrFieldValue(p_AuditingResult);
		((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_OpDesc_AuditingResult_Tmp")).setStrFieldValue(p_AuditingResult);
		
		//不需要指明操作process,直接给Tmp_BaseActionBtn_Char赋值为“审批”		
		if(!this.Init_BaseActionBtn("审批",null,true)){
			return false;			
		}
//		附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.Init_Push_BaseAttachment(p_BaseAttachmentList)==false)
			{
				this.Init_Close_Base();	
				return false;	
			}				
		}
		
		//跳转后台
		String str_Return_BaseID = this.Init_Puth_Base(p_BaseSchema,p_BaseID,p_OperateUserLoginName,1);
		if (str_Return_BaseID==null || str_Return_BaseID.equals(""))
		{
			return false;	
		}			
		this.Init_Close_Base();		
		return true;
	}
	
	
	/**
	 * 描述：操作工单的初始化
	 * @param p_BaseSchema			工单类型
	 * @param p_BaseID				工单ID
	 * @param p_strUserLoginName	操作工单动作的用户登陆名
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @param p_Action				操作类型：新建\派发\受理\阶段回复\完成\确认\催办\追回\退回\驳回\作废\关闭\
	 *                                      审批\
	 * @return 返回已经填写的字段信息
	 */
	private boolean Init_Open_Base(String p_BaseSchema,String p_BaseID,String p_strUserLoginName,int p_Operation,String p_Action) {
		try
		{
			this.Hashtable_BaseAllFields = null;
			this.Hashtable_BaseAllFields = new HashMap();
			
	//		初始化字段信息
			if (this.Init_Open_BaseAllFields(p_BaseSchema,p_BaseID,p_Operation)==false)
			{
				return false;	
			}
	//		初始化人员信息		
			if (this.Init_Open_Read_UserInfo(p_strUserLoginName,p_BaseSchema)==false)
			{
				return false;	
			}
	//		初始化工单类别信息		
			if (this.Init_Open_Read_BaseCategoryInfor(p_BaseSchema)==false)
			{
				return false;	
			}
				
			if (p_Operation == 0)
			{
	//			初始化工单新建的信息		
				if (this.Init_Open_Set_BasePublicInfor() == false)
				{
					return false;	
				}
	//			初始化工单新建环节的信息
				if (this.Init_Open_Set_BeginProcessInfor() == false)
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
				
	//			初始化本人的工单环节信息
				if (this.Init_Open_Select_Process() == false)
				{
					OperationLogFile.writelnTxt( "初始化本人的工单环节信息失败,动作'"+p_Action+"'");
					return false;	
				}	
	//			根据环节关键字设置环节信息					
				BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessID = null;
				m_BaseFieldInfo_tmp_Pro_ProcessID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessID");	
				BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessType = null;
				m_BaseFieldInfo_tmp_Pro_ProcessType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType");	
				if (!this.Init_Open_Set_Process(m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue(),m_BaseFieldInfo_tmp_Pro_ProcessType.getStrFieldValue()))
				{
					return false;	
				}	
			}

	//		p_Action（操作类型：新建\派发\受理\阶段回复\完成\确认\催办\追回\退回\驳回\作废\关闭）
	//		新建		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer)
	//		派发		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagActive = 1 && Flag01Assign = 1
	//		受理		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && FlagActive = 1 && Status = 待处理 && 当前环节 = 处理环节
	//		阶段回复	(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办) && FlagActive = 1 && FlagPredefined = 0 && 当前环节 = 处理环节
	//		完成		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办) && FlagActive = 1 && FlagPredefined = 0 && 当前环节 = 处理环节
	//		确认		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagType = 抄送 && FlagActive = 1 && FlagPredefined = 0 && 当前环节 = 处理环节
	//		催办		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer)
	//		追回		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && ((FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && ((FlagActive = 2 && FlagPredefined = 0) || (FlagActive = 0 && FlagPredefined = 1)) && Flag07Recall = 1 && 当前环节 = 处理环节
	//		退回		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && FlagActive = 1 && Flag05TurnDown = 1 && 当前环节 = 处理环节
	//		驳回		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && FlagActive = 1 && Flag06TurnUp = 1 && 当前环节 = 处理环节
	//		作废		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagActive = 1 && Flag08Cancel = 1
	//		关闭		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagActive = 1 && Flag09Close = 1 && 当前环节 = 处理环节
			
			BaseFieldInfo m_BaseFieldInfo_tmp_UserLoginName = null;
			m_BaseFieldInfo_tmp_UserLoginName = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName");
//			String str_tmp_UserLoginName = m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue();
			
			BaseFieldInfo m_BaseFieldInfo_tmp_UserGroupList = null;
			m_BaseFieldInfo_tmp_UserGroupList = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserGroupList");
//			String str_tmp_UserGroupList = m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AssgineeID = null;
			m_BaseFieldInfo_tmp_Pro_AssgineeID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AssgineeID");
			String str_tmp_Pro_AssgineeID = m_BaseFieldInfo_tmp_Pro_AssgineeID.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_GroupID = null;
			m_BaseFieldInfo_tmp_Pro_GroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_GroupID");
			String str_tmp_Pro_GroupID = m_BaseFieldInfo_tmp_Pro_GroupID.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_DealerID = null;
			m_BaseFieldInfo_tmp_Pro_DealerID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_DealerID");
			String str_tmp_Pro_DealerID = m_BaseFieldInfo_tmp_Pro_DealerID.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagActive = null;
			m_BaseFieldInfo_tmp_Pro_FlagActive = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagActive");
			String str_tmp_Pro_FlagActive = m_BaseFieldInfo_tmp_Pro_FlagActive.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag01Assign = null;
			m_BaseFieldInfo_tmp_Pro_Flag01Assign = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag01Assign");
			String str_tmp_Pro_Flag01Assign = m_BaseFieldInfo_tmp_Pro_Flag01Assign.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagType = null;
			m_BaseFieldInfo_tmp_Pro_FlagType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagType");
			String str_tmp_Pro_FlagType = m_BaseFieldInfo_tmp_Pro_FlagType.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Status = null;
			m_BaseFieldInfo_tmp_Pro_Status = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Status");
			String str_tmp_Pro_Status = m_BaseFieldInfo_tmp_Pro_Status.getStrFieldValue();
			
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BgDate = null;
			m_BaseFieldInfo_tmp_Pro_BgDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BgDate");
			String atr_temp_Pro_BgDate = m_BaseFieldInfo_tmp_Pro_BgDate.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessType = null;
			m_BaseFieldInfo_tmp_Pro_ProcessType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType");
			String str_tmp_Pro_ProcessType = m_BaseFieldInfo_tmp_Pro_ProcessType.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = null;
			m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo");
			String str_tmp_Pro_PrevPhaseNo = m_BaseFieldInfo_tmp_Pro_PrevPhaseNo.getStrFieldValue();	
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagPredefined = null;
			m_BaseFieldInfo_tmp_Pro_FlagPredefined = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagPredefined");
			String str_tmp_Pro_FlagPredefined = m_BaseFieldInfo_tmp_Pro_FlagPredefined.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag07Recall = null;
			m_BaseFieldInfo_tmp_Pro_Flag07Recall = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag07Recall");
			String str_tmp_Pro_Flag07Recall = m_BaseFieldInfo_tmp_Pro_Flag07Recall.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = null;
			m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag05TurnDown");
			String str_tmp_Pro_Flag05TurnDown = m_BaseFieldInfo_tmp_Pro_Flag05TurnDown.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = null;
			m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag06TurnUp");
			String str_tmp_Pro_Flag06TurnUp = m_BaseFieldInfo_tmp_Pro_Flag06TurnUp.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag08Cancel = null;
			m_BaseFieldInfo_tmp_Pro_Flag08Cancel = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag08Cancel");
			String str_tmp_Pro_Flag08Cancel = m_BaseFieldInfo_tmp_Pro_Flag08Cancel.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag09Close = null;
			m_BaseFieldInfo_tmp_Pro_Flag09Close = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag09Close");
			String str_tmp_Pro_Flag09Close = m_BaseFieldInfo_tmp_Pro_Flag09Close.getStrFieldValue();			
			
			BaseFieldInfo m_BaseFieldInfo_BaseStatus = null;
			m_BaseFieldInfo_BaseStatus = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseStatus");
			String str_BaseStatus = FormatString.CheckNullString(m_BaseFieldInfo_BaseStatus.getStrFieldValue());				
			String str_ProcessID="";
			if(Hashtable_BaseAllFields.get("tmp_Pro_ProcessID")!=null)
				str_ProcessID=((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessID")).getStrFieldValue();
			str_ProcessID=FormatString.CheckNullString(str_ProcessID);
			if (p_Action.equals("新建"))
			{
				return true;			
			}	
			
			if (p_Action.equals("派发"))
			{
				if (						
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag01Assign.equals("1") 
					)
				{
					return true;
				}
				String msg="（派发）动作控制失败！不能派发！ProcessID:"+str_ProcessID;
				msg+=" str_tmp_Pro_FlagActive(1): "+str_tmp_Pro_FlagActive;
				msg+=" str_tmp_Pro_Flag01Assign(1): "+str_tmp_Pro_Flag01Assign;
				this.Init_BaseWrite_Log("（派发）动作控制！",1, msg);
				return false;			
			}	
			if (p_Action.equals("受理"))
			{
				if (		
						!str_tmp_Pro_PrevPhaseNo.equals("BEGIN") 
						&& 					
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1")
						)
						&& 
						str_tmp_Pro_FlagActive.equals("1") 
					    && 
					    (atr_temp_Pro_BgDate.equals("0"))
						&& 
						str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				String msg="（受理）动作控制失败！不能受理！ProcessID:"+str_ProcessID;
				msg+=" str_tmp_Pro_PrevPhaseNo(!=BEGIN):"+str_tmp_Pro_PrevPhaseNo;
				msg+=" and (str_tmp_Pro_FlagType(0): "+str_tmp_Pro_FlagType;
				msg+=" or str_tmp_Pro_FlagType(1): "+str_tmp_Pro_FlagType;
				msg+=" ) and str_tmp_Pro_FlagActive(1): "+str_tmp_Pro_FlagActive;
				msg+="  and atr_temp_Pro_BgDate(0): "+atr_temp_Pro_BgDate;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;
				
				this.Init_BaseWrite_Log("（受理）动作控制！",1,msg);
				return false;			
			}	
			if (p_Action.equals("阶段回复"))
			{
				if (
						!str_tmp_Pro_PrevPhaseNo.equals("BEGIN") 						
						&& 
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") 
						)
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				String msg="(阶段回复)动作控制失败！不能阶段回复！ProcessID:"+str_ProcessID;
				msg+=" str_tmp_Pro_PrevPhaseNo(!=BEGIN):"+str_tmp_Pro_PrevPhaseNo;
				msg+=" and (str_tmp_Pro_FlagType(0): "+str_tmp_Pro_FlagType;
				msg+=" or str_tmp_Pro_FlagType(1): "+str_tmp_Pro_FlagType;
				msg+=" ) and str_tmp_Pro_FlagActive(1): "+str_tmp_Pro_FlagActive;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;				
				this.Init_BaseWrite_Log("（阶段回复）动作控制！",1,msg);
				return false;			
			}	
			if (p_Action.equals("完成"))
				{
				if (
						!str_tmp_Pro_PrevPhaseNo.equals("BEGIN") 						
						&& 
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") 
						)
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_FlagPredefined.equals("0") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				String msg="（完成）动作控制失败！不能完成！ProcessID:"+str_ProcessID;
				msg+=" str_tmp_Pro_PrevPhaseNo(!=BEGIN):"+str_tmp_Pro_PrevPhaseNo;
				msg+=" and (str_tmp_Pro_FlagType(0): "+str_tmp_Pro_FlagType;
				msg+=" or str_tmp_Pro_FlagType(1): "+str_tmp_Pro_FlagType;
				msg+=" ) and str_tmp_Pro_FlagActive(1): "+str_tmp_Pro_FlagActive;
				msg+=" ) and str_tmp_Pro_FlagPredefined(0): "+str_tmp_Pro_FlagPredefined;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;					
				this.Init_BaseWrite_Log("（完成）动作控制！",1,msg);
				return false;			
			}			
			if (p_Action.equals("确认"))	
			{
				if (						
						str_tmp_Pro_FlagType.equals("2") 
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				String msg="（确认）动作控制失败！不能确认！ProcessID:"+str_ProcessID;
				msg+="  and str_tmp_Pro_FlagType(2): "+str_tmp_Pro_FlagType;
				msg+="  and str_tmp_Pro_FlagActive(1): "+str_tmp_Pro_FlagActive;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;				
				this.Init_BaseWrite_Log("（确认）动作控制！",1,msg);
				return false;			
			}	
			if (p_Action.equals("催办"))
			{
				if (
						 str_tmp_Pro_ProcessType.equals("DEAL") 
						 &&
						 !str_BaseStatus.equals("草稿")			
					)
				{
					return true;
				}
				String msg="（催办）动作控制失败！不能催办！ProcessID:"+str_ProcessID;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;
				msg+="  and str_BaseStatus(草稿):"+str_BaseStatus;
				this.Init_BaseWrite_Log("（催办）动作控制！",1,msg);
				return false;			
			}		
			if (p_Action.equals("追回"))	
			{				
				if (				
						!str_BaseStatus.equals("草稿")	
						&&
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") || str_tmp_Pro_FlagType.equals("2") 
						)
						&& 
						(
							(
								str_tmp_Pro_FlagActive.equals("2") && str_tmp_Pro_FlagPredefined.equals("0") 
							) 
							|| 
							(
								str_tmp_Pro_FlagActive.equals("0") && str_tmp_Pro_FlagPredefined.equals("1") 
							)					
						)
						&& 
						str_tmp_Pro_Flag07Recall.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				String msg="（追回）动作控制失败！不能追回！ProcessID:"+str_ProcessID;
				msg+=" str_BaseStatus(!=草稿):"+str_BaseStatus;
				msg+=" and str_tmp_Pro_FlagType(0 or 1 or 2):"+str_tmp_Pro_FlagType;
				msg+=" and((str_tmp_Pro_FlagActive(2):"+str_tmp_Pro_FlagActive;
				msg+=" or  str_tmp_Pro_FlagPredefined(0):"+str_tmp_Pro_FlagPredefined;
				msg+=" ) or (str_tmp_Pro_FlagActive(0):"+str_tmp_Pro_FlagActive;
				msg+=" or str_tmp_Pro_FlagPredefined(1):"+str_tmp_Pro_FlagPredefined;
				msg+=" )) and str_tmp_Pro_Flag07Recall(1):"+str_tmp_Pro_Flag07Recall;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;								
				this.Init_BaseWrite_Log("（追回）动作控制！",1,"");
				return false;			
			}
			if (p_Action.equals("退回"))
			{
				if (   
					   !str_BaseStatus.equals("草稿")	
						&&
					   (
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") || str_tmp_Pro_FlagType.equals("2") 
						) 
						&& 
						(str_tmp_Pro_FlagActive.equals("1")||str_tmp_Pro_FlagActive.equals("2")) && str_tmp_Pro_Flag05TurnDown.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				String msg="（退回）动作控制失败！不能退回！ProcessID:"+str_ProcessID;
				msg+=" str_BaseStatus(!=草稿):"+str_BaseStatus;
				msg+=" and str_tmp_Pro_FlagType(0 or 1 or 2):"+str_tmp_Pro_FlagType;
				msg+=" and str_tmp_Pro_FlagActive(1 or 2):"+str_tmp_Pro_FlagActive;
				msg+=" and str_tmp_Pro_Flag05TurnDown(1):"+str_tmp_Pro_Flag05TurnDown;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;				
				this.Init_BaseWrite_Log("（退回）动作控制！",1,msg);
				return false;			
			}
			if (p_Action.equals("驳回"))
				{
				if (	
						!str_BaseStatus.equals("草稿")	
						&&					
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") || str_tmp_Pro_FlagType.equals("2") 
						) 
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag06TurnUp.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				
				String msg="（驳回）动作控制失败！不能驳回！ProcessID:"+str_ProcessID;
				msg+=" str_BaseStatus(!=草稿):"+str_BaseStatus;
				msg+=" and str_tmp_Pro_FlagType(0 or 1 or 2):"+str_tmp_Pro_FlagType;
				msg+=" and str_tmp_Pro_FlagActive(1):"+str_tmp_Pro_FlagActive;
				msg+=" and str_tmp_Pro_Flag06TurnUp(1):"+str_tmp_Pro_Flag06TurnUp;
				msg+="  and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;				
				
				this.Init_BaseWrite_Log("（驳回）动作控制！",1,msg);
				return false;			
			}
			if (p_Action.equals("作废"))	
			{
				if (
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag08Cancel.equals("1")
					)
				{
					return true;
				}
				String msg="（作废）动作控制失败！不能作废！ProcessID:"+str_ProcessID;
				msg+=" and str_tmp_Pro_FlagActive(1):"+str_tmp_Pro_FlagActive;
				msg+=" and str_tmp_Pro_Flag08Cancel(1):"+str_tmp_Pro_Flag08Cancel;
				
				this.Init_BaseWrite_Log("（作废）动作控制！",1,msg);
				return false;			
			}
			if (p_Action.equals("关闭"))
	    	{
				if (
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag09Close.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				String msg="（关闭）动作控制失败！不能关闭！ProcessID:"+str_ProcessID;
				msg+=" and str_tmp_Pro_FlagActive(1):"+str_tmp_Pro_FlagActive;
				msg+=" and str_tmp_Pro_Flag09Close(1):"+str_tmp_Pro_Flag09Close;
				msg+=" and str_tmp_Pro_ProcessType(DEAL):"+str_tmp_Pro_ProcessType;				
				this.Init_BaseWrite_Log("（关闭）动作控制！",1,msg);
				return false;			
			}
			
			if (p_Action.equals("审批"))
	    	{
				if (
						str_tmp_Pro_FlagType.equals("3")
						&&
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_ProcessType.equals("AUDITING") 
					)
				{
					return true;
				}
				String msg="（审批）动作控制失败！不能审批！ProcessID:"+str_ProcessID;
				msg+=" and str_tmp_Pro_FlagType(3):"+str_tmp_Pro_FlagType;
				msg+=" and str_tmp_Pro_FlagActive(1):"+str_tmp_Pro_FlagActive;
				msg+=" and str_tmp_Pro_ProcessType(AUDITING):"+str_tmp_Pro_ProcessType;					
				this.Init_BaseWrite_Log("（审批）动作控制！",1,msg);
				return false;			
			}
		}catch(Exception ex)
		{
			ex.printStackTrace(); 
			this.Init_BaseWrite_Log("初始化信息",1,"初始化信息失败，异常！"+ex.getMessage());
			return false;
		}
		return false;	
	}	
	

	/**
	 * 描述：代理人传递ProcessId和ProcessType时操作工单的初始化

	 * @param p_BaseSchema			工单类型
	 * @param p_BaseID				工单ID
	 * @param p_processId	        当前环节信息
	 * @param p_processType	        当前环节类型
	 * @param p_strUserLoginName	操作工单动作的用户登陆名
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @param p_Action				操作类型：新建\派发\受理\阶段回复\完成\确认\催办\追回\退回\驳回\作废\关闭

	 * @return 返回已经填写的字段信息

	 */
	public boolean Init_Open_Base_FormGivenUser(String p_BaseSchema,String p_BaseID,String p_ProcessID,String p_ProcessType,String p_strUserLoginName,int p_Operation,String p_Action) {
		try
		{
			this.Hashtable_BaseAllFields = null;
			this.Hashtable_BaseAllFields = new HashMap();
			
	//		初始化字段信息

			if (this.Init_Open_BaseAllFields(p_BaseSchema,p_BaseID,p_Operation)==false)
			{
				return false;	
			}
	//		初始化人员信息		
			if (this.Init_Open_Read_UserInfo(p_strUserLoginName,p_BaseSchema)==false)
			{
				return false;	
			}
	//		初始化工单类别信息		
			if (this.Init_Open_Read_BaseCategoryInfor(p_BaseSchema)==false)
			{
				return false;	
			}
				
			if (p_Operation == 0)
			{
	//			初始化工单新建的信息		
				if (this.Init_Open_Set_BasePublicInfor() == false)
				{
					return false;	
				}
	//			初始化工单新建环节的信息
				if (this.Init_Open_Set_BeginProcessInfor() == false)
				{
					return false;	
				}
			}
			if (p_Operation == 1)
			{			
//				初始化工单主体信息
				if (this.Init_Open_Select_Base() == false)
				{
					return false;
				}	
				((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessID")).setStrFieldValue(p_ProcessID);
				((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType")).setStrFieldValue(p_ProcessType);
					
	//			根据环节关键字设置环节信息
				if (!this.Init_Open_Set_Process(p_ProcessID,p_ProcessType))
				{
					return false;	
				}	
			}

	//		p_Action（操作类型：新建\派发\受理\阶段回复\完成\确认\催办\追回\退回\驳回\作废\关闭）

	//		新建		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer)
	//		派发		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagActive = 1 && Flag01Assign = 1
	//		受理		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && FlagActive = 1 && Status = 待处理 && 当前环节 = 处理环节
	//		阶段回复	(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办) && FlagActive = 1 && FlagPredefined = 0 && 当前环节 = 处理环节
	//		完成		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办) && FlagActive = 1 && FlagPredefined = 0 && 当前环节 = 处理环节
	//		确认		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagType = 抄送 && FlagActive = 1 && FlagPredefined = 0 && 当前环节 = 处理环节
	//		催办		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer)
	//		追回		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && ((FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && ((FlagActive = 2 && FlagPredefined = 0) || (FlagActive = 0 && FlagPredefined = 1)) && Flag07Recall = 1 && 当前环节 = 处理环节
	//		退回		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && FlagActive = 1 && Flag05TurnDown = 1 && 当前环节 = 处理环节
	//		驳回		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && (FlagType = 主办 || FlagType = 协办 || FlagType = 抄送) && FlagActive = 1 && Flag06TurnUp = 1 && 当前环节 = 处理环节
	//		作废		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagActive = 1 && Flag08Cancel = 1
	//		关闭		(当前用户属于Group || 当前用户 = Assginee || 当前用户 = Dealer) && FlagActive = 1 && Flag09Close = 1 && 当前环节 = 处理环节
			
			BaseFieldInfo m_BaseFieldInfo_tmp_UserLoginName = null;
			m_BaseFieldInfo_tmp_UserLoginName = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName");
			String str_tmp_UserLoginName = m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue();
			
			BaseFieldInfo m_BaseFieldInfo_tmp_UserGroupList = null;
			m_BaseFieldInfo_tmp_UserGroupList = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserGroupList");
			String str_tmp_UserGroupList = m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AssgineeID = null;
			m_BaseFieldInfo_tmp_Pro_AssgineeID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AssgineeID");
			String str_tmp_Pro_AssgineeID = m_BaseFieldInfo_tmp_Pro_AssgineeID.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_GroupID = null;
			m_BaseFieldInfo_tmp_Pro_GroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_GroupID");
			String str_tmp_Pro_GroupID = m_BaseFieldInfo_tmp_Pro_GroupID.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_DealerID = null;
			m_BaseFieldInfo_tmp_Pro_DealerID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_DealerID");
			String str_tmp_Pro_DealerID = m_BaseFieldInfo_tmp_Pro_DealerID.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagActive = null;
			m_BaseFieldInfo_tmp_Pro_FlagActive = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagActive");
			String str_tmp_Pro_FlagActive = m_BaseFieldInfo_tmp_Pro_FlagActive.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag01Assign = null;
			m_BaseFieldInfo_tmp_Pro_Flag01Assign = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag01Assign");
			String str_tmp_Pro_Flag01Assign = m_BaseFieldInfo_tmp_Pro_Flag01Assign.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagType = null;
			m_BaseFieldInfo_tmp_Pro_FlagType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagType");
			String str_tmp_Pro_FlagType = m_BaseFieldInfo_tmp_Pro_FlagType.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Status = null;
			m_BaseFieldInfo_tmp_Pro_Status = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Status");
			String str_tmp_Pro_Status = m_BaseFieldInfo_tmp_Pro_Status.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessType = null;
			m_BaseFieldInfo_tmp_Pro_ProcessType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType");
			String str_tmp_Pro_ProcessType = m_BaseFieldInfo_tmp_Pro_ProcessType.getStrFieldValue();
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = null;
			m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo");
			String str_tmp_Pro_PrevPhaseNo = m_BaseFieldInfo_tmp_Pro_PrevPhaseNo.getStrFieldValue();	
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagPredefined = null;
			m_BaseFieldInfo_tmp_Pro_FlagPredefined = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagPredefined");
			String str_tmp_Pro_FlagPredefined = m_BaseFieldInfo_tmp_Pro_FlagPredefined.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag07Recall = null;
			m_BaseFieldInfo_tmp_Pro_Flag07Recall = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag07Recall");
			String str_tmp_Pro_Flag07Recall = m_BaseFieldInfo_tmp_Pro_Flag07Recall.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = null;
			m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag05TurnDown");
			String str_tmp_Pro_Flag05TurnDown = m_BaseFieldInfo_tmp_Pro_Flag05TurnDown.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = null;
			m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag06TurnUp");
			String str_tmp_Pro_Flag06TurnUp = m_BaseFieldInfo_tmp_Pro_Flag06TurnUp.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag08Cancel = null;
			m_BaseFieldInfo_tmp_Pro_Flag08Cancel = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag08Cancel");
			String str_tmp_Pro_Flag08Cancel = m_BaseFieldInfo_tmp_Pro_Flag08Cancel.getStrFieldValue();			
	
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag09Close = null;
			m_BaseFieldInfo_tmp_Pro_Flag09Close = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag09Close");
			String str_tmp_Pro_Flag09Close = m_BaseFieldInfo_tmp_Pro_Flag09Close.getStrFieldValue();			
			
			BaseFieldInfo m_BaseFieldInfo_BaseStatus = null;
			m_BaseFieldInfo_BaseStatus = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseStatus");
			String str_BaseStatus = m_BaseFieldInfo_BaseStatus.getStrFieldValue();	
			
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BgDate = null;
			m_BaseFieldInfo_tmp_Pro_BgDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BgDate");
			String atr_temp_Pro_BgDate = m_BaseFieldInfo_tmp_Pro_BgDate.getStrFieldValue();
			
			if (p_Action.equals("新建"))
			{
				return true;			
			}	
			
			if (p_Action.equals("派发"))
			{
				if (						
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag01Assign.equals("1") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（派发）动作控制！",1,"（派发）动作控制失败！不能派发！");
				return false;			
			}	
			if (p_Action.equals("受理"))
			{
				if (		
						!str_tmp_Pro_PrevPhaseNo.equals("BEGIN") 
						&& 					
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1")
						)
						&& 
						str_tmp_Pro_FlagActive.equals("1") 
					    && 
					    (atr_temp_Pro_BgDate.equals("0"))
						&& 
						str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（受理）动作控制！",1,"（受理）动作控制失败！不能受理！");
				return false;			
			}	
			if (p_Action.equals("阶段回复"))
			{
				if (
						!str_tmp_Pro_PrevPhaseNo.equals("BEGIN") 						
						&& 
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") 
						)
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（阶段回复）动作控制！",1,"（阶段回复）动作控制失败！不能阶段回复！");
				return false;			
			}	
			if (p_Action.equals("完成"))
				{
				if (
						!str_tmp_Pro_PrevPhaseNo.equals("BEGIN") 						
						&& 
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") 
						)
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_FlagPredefined.equals("0") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（完成）动作控制！",1,"（完成）动作控制失败！不能完成！");
				return false;			
			}			
			if (p_Action.equals("确认"))	
			{
				if (						
						str_tmp_Pro_FlagType.equals("2") 
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（确认）动作控制！",1,"（确认）动作控制失败！不能确认！");
				return false;			
			}	
			if (p_Action.equals("催办"))
			{
				if (
						 str_tmp_Pro_ProcessType.equals("DEAL") 
						 &&
						 !str_BaseStatus.equals("草稿")			
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（催办）动作控制！",1,"（催办）动作控制失败！不能催办！");
				return false;			
			}		
			if (p_Action.equals("追回"))	
			{				
				if (				
						!str_BaseStatus.equals("草稿")	
						&&
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") || str_tmp_Pro_FlagType.equals("2") 
						)
						&& 
						(
							(
								str_tmp_Pro_FlagActive.equals("2") && str_tmp_Pro_FlagPredefined.equals("0") 
							) 
							|| 
							(
								str_tmp_Pro_FlagActive.equals("0") && str_tmp_Pro_FlagPredefined.equals("1") 
							)					
						)
						&& 
						str_tmp_Pro_Flag07Recall.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（追回）动作控制！",1,"（追回）动作控制失败！不能追回！");
				return false;			
			}
			if (p_Action.equals("退回"))
			{
				if (   
					   !str_BaseStatus.equals("草稿")	
						&&
					   (
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") || str_tmp_Pro_FlagType.equals("2") 
						) 
						&& 
						(str_tmp_Pro_FlagActive.equals("1")||str_tmp_Pro_FlagActive.equals("2")) && str_tmp_Pro_Flag05TurnDown.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（退回）动作控制！",1,"（退回）动作控制失败！不能退回！");
				return false;			
			}
			if (p_Action.equals("驳回"))
				{
				if (	
						!str_BaseStatus.equals("草稿")	
						&&					
						(
							str_tmp_Pro_FlagType.equals("0") || str_tmp_Pro_FlagType.equals("1") || str_tmp_Pro_FlagType.equals("2") 
						) 
						&& 
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag06TurnUp.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（驳回）动作控制！",1,"（驳回）动作控制失败！不能驳回！");
				return false;			
			}
			if (p_Action.equals("作废"))	
			{
				if (
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag08Cancel.equals("1")
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（作废）动作控制！",1,"（作废）动作控制失败！不能作废！");
				return false;			
			}
			if (p_Action.equals("关闭"))
	    	{
				if (
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_Flag09Close.equals("1") && str_tmp_Pro_ProcessType.equals("DEAL") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（关闭）动作控制！",1,"（关闭）动作控制失败！不能关闭！");
				return false;			
			}
			
			if (p_Action.equals("审批"))
	    	{
				if (
						str_tmp_Pro_FlagType.equals("3")
						&&
						str_tmp_Pro_FlagActive.equals("1") && str_tmp_Pro_ProcessType.equals("AUDITING") 
					)
				{
					return true;
				}
				this.Init_BaseWrite_Log("（审批）动作控制！",1,"（审批）动作控制失败！不能审批！");
				return false;			
			}
		}catch(Exception ex)
		{
			ex.printStackTrace(); 
			this.Init_BaseWrite_Log("初始化信息",1,"初始化信息失败，异常！"+ex.getMessage());
			return false;
		}
		return false;	
	}	
	
	/**
	 * 描述：操作工单的结束
	 * @return 是否成功
	 */
	protected void Init_Close_Base() {
		this.Hashtable_BaseAllFields.clear();
		this.Hashtable_BaseAllFields = null;
	}	
		
	/**
	 * 描述：操作工单的初始化
	 * @param p_BaseSchema			工单类型
	 * @param p_BaseID				工单ID
	 * @param p_strUserLoginName	操作工单动作的用户登陆名
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @return 返回工单ID
	 */
	protected String Init_Puth_Base(String p_BaseSchema,String p_BaseID,String p_strUserLoginName,int p_Operation) {
		try
		{
			Hashtable_BaseAllFields.remove("BaseID");
			Base m_Base = new Base();
			Init_ActionBaseUpdata(p_strUserLoginName,p_BaseSchema,p_BaseID,p_Operation);
			if (p_Operation == 0)
			{	
				return m_Base.Insert(p_BaseSchema,Hashtable_BaseAllFields);
			}
			else
			{
				if (m_Base.Update(p_BaseSchema,p_BaseID,Hashtable_BaseAllFields)==true)
				{
					return p_BaseID;
				}
				return null;
			}
		}catch(Exception ex)
		{ 
			ex.printStackTrace();
			this.Init_BaseWrite_Log("PUTH工单",1,"PUTH工单信息失败，异常！"+ex.getMessage());
			return null;
		}		
	}	
	
	/**
	 * 描述：加入工单附加函数
	 * @param p_BaseAttachmentList			附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	protected boolean Init_Push_BaseAttachment(List p_BaseAttachmentList){

		try{		
			cn.com.ultrapower.ultrawf.models.process.BaseAttachment m_BaseAttachment = new cn.com.ultrapower.ultrawf.models.process.BaseAttachment();
			for (int i=0;i<p_BaseAttachmentList.size();i++)
			{
				String str_Attachment_Path = ((BaseAttachment)p_BaseAttachmentList.get(i)).getStrAttachmentPath();
				
				Map Hashtable_AddBaseAttachAllFields = new HashMap();
				
				int intCharIndexOf = str_Attachment_Path.lastIndexOf(File.separator);
				if(intCharIndexOf>0)
					intCharIndexOf++;
				
				String str_Attachment_Name = str_Attachment_Path.substring(intCharIndexOf);
				
				Hashtable_AddBaseAttachAllFields.put("BaseID", new PublicFieldInfo("BaseID","650000001",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseID")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("BaseSchema", new PublicFieldInfo("BaseSchema","650000002",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseSchema")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("PhaseNo", new PublicFieldInfo("PhaseNo","650000003",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("FlagActive", new PublicFieldInfo("FlagActive","650000004","1",2));
				Hashtable_AddBaseAttachAllFields.put("upLoadUser", new PublicFieldInfo("upLoadUser","650000005",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("upLoadUserID", new PublicFieldInfo("upLoadUserID","650000006",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("upLoadTimeDate", new PublicFieldInfo("upLoadTimeDate","650000007",Init_Get_TIMESTAMP().toString(),7));
				Hashtable_AddBaseAttachAllFields.put("upLoadFileName", new PublicFieldInfo("upLoadFileName","650000008",str_Attachment_Name,4));
				Hashtable_AddBaseAttachAllFields.put("upLoadFileDesc", new PublicFieldInfo("upLoadFileDesc","650000009","接口来的！",4));
				Hashtable_AddBaseAttachAllFields.put("upLoadFileContent", new PublicFieldInfo("upLoadFileContent","650000010",str_Attachment_Path,11));

				m_BaseAttachment.Insert(Hashtable_AddBaseAttachAllFields);
	
				Hashtable_AddBaseAttachAllFields.clear();
				Hashtable_AddBaseAttachAllFields = null;
			}
			return true;
		}
		catch(Exception ex)
		{
			this.Init_BaseWrite_Log("加工单附件",1,"加工单附件失败，异常！"+ex.getMessage());			
			return false;
		}
	}
	
	/**
	 * 描述：读用户信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_strUserLoginName			操作工单动作的用户登陆名
	 * @return 返回已经填写的字段信息 
	 */
	private boolean Init_Open_Read_UserInfo(String p_strUserLoginName,String p_strBaseSchama) {
		
		try
		{
			User user = new User();
			UserModel userModel = null; 
			userModel = user.UserIsExist(p_strUserLoginName);
	
			if(userModel!=null){
				BaseFieldInfo m_BaseFieldInfo_UserLoginName = null;
				m_BaseFieldInfo_UserLoginName = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName");
				//m_BaseFieldInfo_UserLoginName.setStrFieldValue(userModel.GetLoginName());
				
				BaseFieldInfo m_BaseFieldInfo_UserFullName = null;
				m_BaseFieldInfo_UserFullName = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserFullName");
				//m_BaseFieldInfo_UserFullName.setStrFieldValue(userModel.GetFullName()); 
	
				BaseFieldInfo m_BaseFieldInfo_UserGroupList = null;
				m_BaseFieldInfo_UserGroupList = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserGroupList");
//				m_BaseFieldInfo_UserGroupList.setStrFieldValue(userModel.GetGroupList());
	
				BaseFieldInfo m_BaseFieldInfo_UserEmailAdd = null;
				m_BaseFieldInfo_UserEmailAdd = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserEmailAdd");
				//m_BaseFieldInfo_UserEmailAdd.setStrFieldValue(userModel.GetEmailAddress());

				ConfigUserCloseBaseGroupManage m_ConfigUserCloseBaseGroupManage = new ConfigUserCloseBaseGroupManage();
				ParConfigUserCloseBaseGroupModel m_ParConfigUserCloseBaseGroupModel = new ParConfigUserCloseBaseGroupModel();
				m_ParConfigUserCloseBaseGroupModel.setAssgineeID(p_strUserLoginName);
				m_ParConfigUserCloseBaseGroupModel.setBaseCategorySchama(p_strBaseSchama);
				List m_ConfigUserCloseBaseGroupModelList = m_ConfigUserCloseBaseGroupManage.getList(m_ParConfigUserCloseBaseGroupModel,0,0);
				if (m_ConfigUserCloseBaseGroupModelList.size()>0)
				{
					ConfigUserCloseBaseGroupModel m_ConfigUserCloseBaseGroupModel = (ConfigUserCloseBaseGroupModel)m_ConfigUserCloseBaseGroupModelList.get(0);
					
					BaseFieldInfo m_BaseFieldInfo_tmp_UserCloseBaseSamenessGroup = null;
					m_BaseFieldInfo_tmp_UserCloseBaseSamenessGroup = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserCloseBaseSamenessGroup");
					//m_BaseFieldInfo_tmp_UserCloseBaseSamenessGroup.setStrFieldValue(m_ConfigUserCloseBaseGroupModel.getCloseBaseGroup());						
					
					BaseFieldInfo m_BaseFieldInfo_tmp_UserCloseBaseSamenessGroupID = null;
					m_BaseFieldInfo_tmp_UserCloseBaseSamenessGroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserCloseBaseSamenessGroupID");
//					m_BaseFieldInfo_tmp_UserCloseBaseSamenessGroupID.setStrFieldValue(m_ConfigUserCloseBaseGroupModel.getCloseBaseGroupID());
				
				}
				
			}
			else
			{
				this.Init_BaseWrite_Log("初始化人员",1,"初始化人员信息失败，该人员不存在！");
				return false;
			}
		}catch(Exception ex)
		{ex.printStackTrace();
			this.Init_BaseWrite_Log("初始化人员",1,"初始化人员信息失败，异常！"+ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 描述：读工单类别信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_strBaseSchama		操作工单类别名（工单的From名）
	 * @return 返回已经填写的字段信息
	 */
	private boolean Init_Open_Read_BaseCategoryInfor(String p_strBaseSchama) {
		try
		{
			BaseCategory baseCategory = new BaseCategory();
			BaseCategoryModel baseCategoryModel = null;
			
			baseCategoryModel = baseCategory.getOneModel(p_strBaseSchama);
			if (baseCategoryModel != null)
			{
				BaseFieldInfo m_BaseFieldInfo_BaseCategoryCode = null;
				m_BaseFieldInfo_BaseCategoryCode = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategoryCode");
				m_BaseFieldInfo_BaseCategoryCode.setStrFieldValue(baseCategoryModel.GetBaseCategoryCode());
	
				BaseFieldInfo m_BaseFieldInfo_BaseCategoryName = null;
				m_BaseFieldInfo_BaseCategoryName = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategoryName");
				m_BaseFieldInfo_BaseCategoryName.setStrFieldValue(baseCategoryModel.GetBaseCategoryName());
				
				BaseFieldInfo m_BaseFieldInfo_BaseCategoryIsFlow = null;
				m_BaseFieldInfo_BaseCategoryIsFlow = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategoryIsFlow");
				Integer m_obj_BaseCategoryIsFlow = new Integer(baseCategoryModel.GetBaseCategoryIsFlow());
				m_BaseFieldInfo_BaseCategoryIsFlow.setStrFieldValue(m_obj_BaseCategoryIsFlow.toString());
		
				BaseFieldInfo m_BaseFieldInfo_BaseCategoryDayLastNo = null;
				m_BaseFieldInfo_BaseCategoryDayLastNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategoryDayLastNo");
				Integer m_obj_BaseCategoryDayLastNo = new Integer(baseCategoryModel.GetBaseCategoryDayLastNo());
				m_BaseFieldInfo_BaseCategoryDayLastNo.setStrFieldValue(m_obj_BaseCategoryDayLastNo.toString());
				
				BaseFieldInfo m_BaseFieldInfo_BaseCategorySchama = null;
				m_BaseFieldInfo_BaseCategorySchama = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategorySchama");
				m_BaseFieldInfo_BaseCategorySchama.setStrFieldValue(baseCategoryModel.GetBaseCategorySchama());
			}
			else
			{
				this.Init_BaseWrite_Log("初始化工单类别",1,"初始化工单类别信息失败，该工单类别不存在！");
				return false;				
			}

		}catch(Exception ex)
		{ex.printStackTrace();
			this.Init_BaseWrite_Log("初始化工单类别",1,"初始化工单类别信息失败，异常！"+ex.getMessage());
			return false;
		}
		
		return true;
	}

	/**
	 * 描述：设置工单基本公用信息的函数，并返回工单上的临时字段信息已经负值的
	 * @return 是否成功
	 */
	private boolean Init_Open_Set_BasePublicInfor() {
		//状　　态：BaseStatus 				= 草稿
		//工单类别：BaseSchema 				= tmp_BaseCategorySchama
		//工 单 名：BaseName 					= tmp_BaseCategoryName
		//建单人登陆名：BaseCreatorLoginName 	= tmp_UserLoginName
		//建单人姓名：BaseCreatorFullName 		= tmp_UserFullName
		
		BaseFieldInfo m_BaseFieldInfo_BaseStatus = null;
		m_BaseFieldInfo_BaseStatus = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseStatus");
		m_BaseFieldInfo_BaseStatus.setStrFieldValue("草稿");
	
		BaseFieldInfo m_BaseFieldInfo_BaseSchema = null;
		m_BaseFieldInfo_BaseSchema = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseSchema");
		m_BaseFieldInfo_BaseSchema.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategorySchama")).getStrFieldValue());
	
		BaseFieldInfo m_BaseFieldInfo_BaseName = null;
		m_BaseFieldInfo_BaseName = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseName");
		m_BaseFieldInfo_BaseName.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategoryName")).getStrFieldValue());

		BaseFieldInfo m_BaseFieldInfo_BaseCreatorLoginName = null;
		m_BaseFieldInfo_BaseCreatorLoginName = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseCreatorLoginName");
//		m_BaseFieldInfo_BaseCreatorLoginName.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName")).getStrFieldValue());

		BaseFieldInfo m_BaseFieldInfo_BaseCreatorFullName = null;
		m_BaseFieldInfo_BaseCreatorFullName = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseCreatorFullName");
//		m_BaseFieldInfo_BaseCreatorFullName.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue());
		
		return true;
	}

	/**
	 * 暂时没用，预留
	 * 描述：设置工单特有信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_FieldListInfo		操作需要的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @return 返回已经填写的字段信息
	 */
	private List Init_Open_Set_BaseOwnInfor(List p_FieldListInfo) {
		
		return null;
	}

	/**
	 * 描述：新建工单时，设置工单建单环节信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_FieldListInfo		操作需要的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @return 返回已经填写的字段信息
	 */
	private boolean Init_Open_Set_BeginProcessInfor() {
		//当前环节的环节ID：			tmp_Pro_ProcessID 				= $PROCESS$ @@: Application-Generate-GUID
		//当前环节的类型：				tmp_Pro_ProcessType				= "DEAL"
		//当前环节的工单类型：			tmp_Pro_BaseSchema				= tmp_BaseCategorySchama
		//当前环节的工单BaseID：		tmp_Pro_BaseID					= $PROCESS$ @@: Application-Generate-GUID
		//当前环节的前一环节号：			tmp_Pro_PrevPhaseNo 			= "BEGIN"
		//当前环节的环节号：			tmp_Pro_PhaseNo 				= $PROCESS$ @@: Application-Generate-GUID
		//当前环节的派发人登陆名：		tmp_Pro_AssgineeID				= tmp_UserLoginName
		//当前环节的派发人名：			tmp_Pro_Assginee				= tmp_UserFullName		
		//当前环节的执行人登陆名：		tmp_Pro_DealerID				= tmp_UserLoginName
		//当前环节的执行人名：			tmp_Pro_Dealer					= tmp_UserFullName
		//当前环节的状态：				tmp_Pro_Status 					= "待处理"
		//当前环节的环节描述：			tmp_Pro_Desc					= "新建工单"
		//当前环节的开始时间：			tmp_Pro_StDate 					= $TIMESTAMP$
		//当前环节的受理时间：			tmp_Pro_BgDate					= $TIMESTAMP$
		//当前环节的类型：				tmp_Pro_FlagType 				= 0
		//当前环节是否预定义：			tmp_Pro_FlagPredefined 			= 0			
		//当前环节是否复制品：			tmp_Pro_FlagDuplicated 			= 0			
		//当前环节的Active：			tmp_Pro_FlagActive 				= 1		
		//当前环节是否转交给来的环节：	tmp_Pro_Flag31IsTransfer		= 0			
		//当前环节是否允许驳回审批：		tmp_Pro_Flag16TurnUpAuditing	= 0			
		//当前环节是否允许提交审批：		tmp_Pro_Flag15ToAuditing		= 1			
		//当前环节是否允许关闭工单：		tmp_Pro_Flag09Close				= 1			
		//当前环节是否允许作废工单：		tmp_Pro_Flag08Cancel			= 1			
		//当前环节是否允许追回工单：		tmp_Pro_Flag07Recall			= 1			
		//当前环节是否允许驳回工单：		tmp_Pro_Flag06TurnUp			= 1			
		//当前环节是否允许退回工单：		tmp_Pro_Flag05TurnDown			= 1			
		//当前环节是否允许转派工单：		tmp_Pro_Flag04Transfer			= 1			
		//当前环节是否允许协办工单：		tmp_Pro_Flag03Assist			= 1			
		//当前环节是否允许抄送工单：		tmp_Pro_Flag02Copy				= 1			
		//当前环节是否允许派发工单：		tmp_Pro_Flag01Assign			= 1			

		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessID = null;
		m_BaseFieldInfo_tmp_Pro_ProcessID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessID");
		m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(Init_Get_GUID("PID",1));
	
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessType = null;
		m_BaseFieldInfo_tmp_Pro_ProcessType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType");
		m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue("DEAL");

		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BaseSchema = null;
		m_BaseFieldInfo_tmp_Pro_BaseSchema = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseSchema");
		m_BaseFieldInfo_tmp_Pro_BaseSchema.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseCategorySchama")).getStrFieldValue());
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BaseID = null;
		m_BaseFieldInfo_tmp_Pro_BaseID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseID");
		m_BaseFieldInfo_tmp_Pro_BaseID.setStrFieldValue(Init_Get_GUID("BID",2));

		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = null;
		m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo");
		m_BaseFieldInfo_tmp_Pro_PrevPhaseNo.setStrFieldValue("BEGIN");
	
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PhaseNo = null;
		m_BaseFieldInfo_tmp_Pro_PhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo");
		m_BaseFieldInfo_tmp_Pro_PhaseNo.setStrFieldValue(Init_Get_GUID("PNo",3));
	
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AssgineeID = null;
		m_BaseFieldInfo_tmp_Pro_AssgineeID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AssgineeID");
//		m_BaseFieldInfo_tmp_Pro_AssgineeID.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName")).getStrFieldValue());
	
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Assginee = null;
		m_BaseFieldInfo_tmp_Pro_Assginee = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Assginee");
//		m_BaseFieldInfo_tmp_Pro_Assginee.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue());
	
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_DealerID = null;
		m_BaseFieldInfo_tmp_Pro_DealerID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_DealerID");
//		m_BaseFieldInfo_tmp_Pro_DealerID.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName")).getStrFieldValue());
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Dealer = null;
		m_BaseFieldInfo_tmp_Pro_Dealer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Dealer");
//		m_BaseFieldInfo_tmp_Pro_Dealer.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue());

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Status = null;
		m_BaseFieldInfo_tmp_Pro_Status = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Status");
		m_BaseFieldInfo_tmp_Pro_Status.setStrFieldValue("待处理");


		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Desc = null;
		m_BaseFieldInfo_tmp_Pro_Desc = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Desc");
		m_BaseFieldInfo_tmp_Pro_Desc.setStrFieldValue("新建工单");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_StDate = null;
		m_BaseFieldInfo_tmp_Pro_StDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_StDate");
		m_BaseFieldInfo_tmp_Pro_StDate.setStrFieldValue(Init_Get_TIMESTAMP().toString());

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BgDate = null;
		m_BaseFieldInfo_tmp_Pro_BgDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BgDate");
		m_BaseFieldInfo_tmp_Pro_BgDate.setStrFieldValue(Init_Get_TIMESTAMP().toString());


		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagType = null;
		m_BaseFieldInfo_tmp_Pro_FlagType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagType");
		m_BaseFieldInfo_tmp_Pro_FlagType.setStrFieldValue("0");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagPredefined = null;
		m_BaseFieldInfo_tmp_Pro_FlagPredefined = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagPredefined");
		m_BaseFieldInfo_tmp_Pro_FlagPredefined.setStrFieldValue("0");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagDuplicated = null;
		m_BaseFieldInfo_tmp_Pro_FlagDuplicated = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagDuplicated");
		m_BaseFieldInfo_tmp_Pro_FlagDuplicated.setStrFieldValue("0");

	
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagActive = null;
		m_BaseFieldInfo_tmp_Pro_FlagActive = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagActive");
		m_BaseFieldInfo_tmp_Pro_FlagActive.setStrFieldValue("1");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag31IsTransfer = null;
		m_BaseFieldInfo_tmp_Pro_Flag31IsTransfer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer");
		m_BaseFieldInfo_tmp_Pro_Flag31IsTransfer.setStrFieldValue("0");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag16TurnUpAuditing = null;
		m_BaseFieldInfo_tmp_Pro_Flag16TurnUpAuditing = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag16TurnUpAuditing");
		m_BaseFieldInfo_tmp_Pro_Flag16TurnUpAuditing.setStrFieldValue("0");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing = null;
		m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag15ToAuditing");
		m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing.setStrFieldValue("1");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag09Close = null;
		m_BaseFieldInfo_tmp_Pro_Flag09Close = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag09Close");
		m_BaseFieldInfo_tmp_Pro_Flag09Close.setStrFieldValue("1");

		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag08Cancel = null;
		m_BaseFieldInfo_tmp_Pro_Flag08Cancel = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag08Cancel");
		m_BaseFieldInfo_tmp_Pro_Flag08Cancel.setStrFieldValue("1");
	
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag07Recall = null;
		m_BaseFieldInfo_tmp_Pro_Flag07Recall = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag07Recall");
		m_BaseFieldInfo_tmp_Pro_Flag07Recall.setStrFieldValue("1");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = null;
		m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag06TurnUp");
		m_BaseFieldInfo_tmp_Pro_Flag06TurnUp.setStrFieldValue("1");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = null;
		m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag05TurnDown");
		m_BaseFieldInfo_tmp_Pro_Flag05TurnDown.setStrFieldValue("1");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag04Transfer = null;
		m_BaseFieldInfo_tmp_Pro_Flag04Transfer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag04Transfer");
		m_BaseFieldInfo_tmp_Pro_Flag04Transfer.setStrFieldValue("1");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag03Assist = null;
		m_BaseFieldInfo_tmp_Pro_Flag03Assist = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag03Assist");
		m_BaseFieldInfo_tmp_Pro_Flag03Assist.setStrFieldValue("1");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag02Copy = null;
		m_BaseFieldInfo_tmp_Pro_Flag02Copy = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag02Copy");
		m_BaseFieldInfo_tmp_Pro_Flag02Copy.setStrFieldValue("1");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag01Assign = null;
		m_BaseFieldInfo_tmp_Pro_Flag01Assign = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag01Assign");
		m_BaseFieldInfo_tmp_Pro_Flag01Assign.setStrFieldValue("1");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag32IsToTransfer = null;
		m_BaseFieldInfo_tmp_Pro_Flag32IsToTransfer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag32IsToTransfer");
		m_BaseFieldInfo_tmp_Pro_Flag32IsToTransfer.setStrFieldValue("0");
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_tmp_Pro_Flag33IsEndPhase = null;
		m_BaseFieldInfo_tmp_Pro_tmp_Pro_Flag33IsEndPhase = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag33IsEndPhase");
		m_BaseFieldInfo_tmp_Pro_tmp_Pro_Flag33IsEndPhase.setStrFieldValue("1");

		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup = null;
		m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CloseBaseSamenessGroup");
//		m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserCloseBaseSamenessGroup")).getStrFieldValue());

		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID = null;
		m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CloseBaseSamenessGroupID");
//		m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID.setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserCloseBaseSamenessGroupID")).getStrFieldValue());		
		
		return true;
	}

	/**
	 * 初始化工单主体信息
	 * @return
	 */
	private boolean Init_Open_Select_Base() {
		Base base = new Base();
		BaseModel baseModel = null;
		try{
			BaseFieldInfo m_BaseFieldInfo_BaseID = null;
			m_BaseFieldInfo_BaseID = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseID");
	
			BaseFieldInfo m_BaseFieldInfo_BaseSchema = null;
			m_BaseFieldInfo_BaseSchema = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseSchema");
	
			baseModel = base.GetOneForKey(m_BaseFieldInfo_BaseSchema.getStrFieldValue(),m_BaseFieldInfo_BaseID.getStrFieldValue(),Constants.IsNotArchive);
			
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseName")).setStrFieldValue(baseModel.getBaseName());
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseSN")).setStrFieldValue(baseModel.getBaseSN());
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseCreatorFullName")).setStrFieldValue(baseModel.getBaseCreatorFullName());
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseCreatorLoginName")).setStrFieldValue(baseModel.getBaseCreatorLoginName());
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseCreateDate")).setStrFieldValue(baseModel.getBaseCreateDate()+"");
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseSendDate")).setStrFieldValue(baseModel.getBaseSendDate()+"");
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseFinishDate")).setStrFieldValue(baseModel.getBaseFinishDate()+"");
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseCloseDate")).setStrFieldValue(baseModel.getBaseCloseDate()+"");
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseStatus")).setStrFieldValue(baseModel.getBaseStatus());
	   		((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseResult")).setStrFieldValue(baseModel.getBaseResult());
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseCloseSatisfy")).setStrFieldValue(baseModel.getBaseCloseSatisfy());
			((BaseFieldInfo)Hashtable_BaseAllFields.get("BaseTplID")).setStrFieldValue(baseModel.getBaseTplID());

			return true;
		}catch(Exception e){
			
			e.printStackTrace();	
			return false;
		}
		
	}
	
	
	/**
	 * 描述：根据本人员信息读工单环节ID和类型的函数
	 * @return 返回是否成功
	 */
	private boolean Init_Open_Select_Process() {
		try
		{
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessID = null;
			m_BaseFieldInfo_tmp_Pro_ProcessID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessID");
		
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessType = null;
			m_BaseFieldInfo_tmp_Pro_ProcessType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType");

			BaseFieldInfo m_BaseFieldInfo_BaseID = null;
			m_BaseFieldInfo_BaseID = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseID");
	
			BaseFieldInfo m_BaseFieldInfo_BaseSchema = null;
			m_BaseFieldInfo_BaseSchema = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseSchema");
	
			BaseFieldInfo m_BaseFieldInfo_tmp_UserLoginName = null;
			m_BaseFieldInfo_tmp_UserLoginName = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName");
	
			BaseFieldInfo m_BaseFieldInfo_tmp_UserGroupList = null;
			m_BaseFieldInfo_tmp_UserGroupList = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserGroupList");
			
			//执行顺序：1匹配Assignee != NULL && Dealer = 登录用户&& FlagActive = 1 && 当前环节 = 审批环节
			
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ( 'AssigneeID !=NULL')
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "AUDITING" )
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess		= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setFlagActive("1");
				m_ParDealProcess.setAssgineeID("NOTNULL");
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>=1)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
	
			//执行顺序：2匹配Assignee = NULL && Dealer = 登录用户 && IsGroupSnatch = 1 && FlagActive = 1 && 当前环节 = 审批环节 
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( 'AssigneeID == NULL')
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'IsGroupSnatch' = 1 ) 
				// AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "AUDITING" )
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess		= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setIsGroupSnatch(1);
				m_ParDealProcess.setFlagActive("1");
				
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>=1)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
	
			//执行顺序：3匹配LoginUser属于Group && Dealer != NULL && IsGroupSnatch = 0 && FlagActive = 1 && 当前环节 = 审批环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( $tmp_UserGroupList$ LIKE (( "%;"  + 'GroupID' +  ";%" )
				// AND ( 'DealerID' !=  $NULL$ ) AND ( 'IsGroupSnatch' = 0) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "AUDITING" )) 
				
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NOTNULL");
				m_ParDealProcess.setIsGroupSnatch(0);
				m_ParDealProcess.setFlagActive("1");
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>=1)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
	
			//执行顺序：4匹配Assignee = 登录用户 && Dealer = NULL && FlagActive = 1 && 当前环节 = 审批环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ( 'AssigneeID = $tmp_UserLoginName$') AND ( 'DealerID' ==  $NULL$ )
				// AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "AUDITING" )) 
				
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setDealerID("NULL");			
				m_ParDealProcess.setFlagActive("1");
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>=1)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
            //执行顺序：5匹配LoginUser属于Group  && Dealer = NULL && FlagActive = 1 && 当前环节 = 审批环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( $tmp_UserGroupList$ LIKE (( "%;"  + 'GroupID' +  ";%" )
				// AND ( 'DealerID' =  $NULL$ ) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "AUDITING" )) 
				
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NULL");			
				m_ParDealProcess.setFlagActive("1");
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>=1)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
            //执行顺序：6匹配Assignee != NULL && Dealer = 登录用户&& FlagActive = 1 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( 'AssigneeID != NULL')
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NOTNULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setFlagActive("1");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}	
			
			 //执行顺序：7匹配Assignee = NULL && Dealer = 登录用户 && IsGroupSnatch = 1 && FlagActive = 1 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( 'AssigneeID = NULL')
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'IsGroupSnatch' = 1) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setIsGroupSnatch(1);
				m_ParDealProcess.setFlagActive("1");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}	
			
			
			 //执行顺序：8匹配LoginUser属于Group && Dealer != NULL && IsGroupSnatch = 0 && FlagActive = 1 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( '$tmp_UserGroupList$ LIKE ('%GroupID%'))
				// AND ( 'DealerID' != NULL) AND ( 'IsGroupSnatch' = 0) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NOTNULL");
				m_ParDealProcess.setIsGroupSnatch(0);
				m_ParDealProcess.setFlagActive("1");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
			//执行顺序：9	匹配Assignee = 登录用户 && Dealer = NULL && FlagActive = 1 && 当前环节 = 处理环节
			//	新的逻辑：匹配(Assignee = 登录用户 || Commissioner =登录用户) && Dealer = NULL && FlagActive = 1 && 当前环节 = 处理环节
			//	原有逻辑：匹配Assignee = 登录用户 && Dealer = NULL && FlagActive = 1 && 当前环节 = 处理环节

			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( 'AssigneeId' = '$tmp_UserLoginName$' )
				// AND ( 'DealerID' = NULL) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				//----------------------------------------------------
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());		
				m_ParDealProcess.setDealerID("NULL");
				m_ParDealProcess.setFlagActive("1");
				//----------------------------------------------------
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
			 //执行顺序：10匹配LoginUser属于Group  && Dealer = NULL && FlagActive = 1 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( '$tmp_UserGroupList$ LIKE ('%GroupID%'))
				// AND ( 'DealerID' = NULL) AND ( 'FlagActive' = 1) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NULL");
				m_ParDealProcess.setFlagActive("1");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
			//执行顺序：11匹配LoginUser的同组关单的组=CloseBaseSamenessGroup && FlagActive = 1 && 该工单是同组关单 && 当前环节 = 处理环节
			
			
			
			//执行顺序：11匹配Assignee != NULL && Dealer = 登录用户&& FlagActive = 3 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( 'AssigneeId' != 'NULL' )
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'FlagActive' = 3) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NOTNULL");				
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setFlagActive("3");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
			
			 //执行顺序：12匹配Assignee = NULL && Dealer = 登录用户 && IsGroupSnatch = 1 && FlagActive = 3 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( 'AssigneeId' = 'NULL' )
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ('IsGroupSnatch'=1) AND( 'FlagActive' = 3) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NULL");				
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setIsGroupSnatch(1);
				m_ParDealProcess.setFlagActive("3");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
						
			
			 //执行顺序：13匹配LoginUser属于Group && Dealer != NULL && IsGroupSnatch = 0 && FlagActive = 3 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( '$tmp_UserGroupList$ LIKE ('%GroupID%'))
				// AND ( 'DealerID' != NULL) AND ('IsGroupSnatch'=0) AND ( 'FlagActive' = 3) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NOTNULL");
				m_ParDealProcess.setIsGroupSnatch(0);
				m_ParDealProcess.setFlagActive("3");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}			
			
			//执行顺序：14匹配Assignee != NULL && Dealer = 登录用户&& FlagActive = 2 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ( 'AssigneeID' != NULL)
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'FlagActive' = 2) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess		m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssginee("NOTNULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setFlagActive("2");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}		

	       //执行顺序：15匹配Assignee = NULL && Dealer = 登录用户 && IsGroupSnatch = 1 && FlagActive = 2 && 当前环节 = 处理环节 
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
                // AND ( 'AssigneeID' = NULL)
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'IsGroupSnatch' =  1 ) AND ( 'FlagActive' = 2) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess 	m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setIsGroupSnatch(1);
				m_ParDealProcess.setFlagActive("2");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}		
	
			//执行顺序：16匹配LoginUser属于Group && Dealer != NULL && IsGroupSnatch = 0 && FlagActive = 2 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ( $tmp_UserGroupList$ LIKE ( "%;"  + 'GroupID' +  ";%" ))
				// AND ( 'DealerID' !=  $NULL$ ) AND ( 'IsGroupSnatch' = 0) AND ( 'FlagActive' = 2) AND ( 'ProcessType' =  "DEAL" ) 
				
				DealProcess 	m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NOTNULL");
				m_ParDealProcess.setIsGroupSnatch(0);
				m_ParDealProcess.setFlagActive("2");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}			
	
			//执行顺序：17匹配Assignee != NULL && Dealer = 登录用户&& FlagActive = 0 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ('AssigneeID' != NULL)
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'FlagActive' = 0) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess 	m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NOTNULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setFlagActive("0");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}	
	
			//执行顺序：18匹配Assignee = NULL && Dealer = 登录用户 && IsGroupSnatch = 1 && FlagActive = 0 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ( 'AssigneeID' = NULL)
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'IsGroupSnatch' = 1) AND ( 'FlagActive' = 0) AND ( 'ProcessType' =  "DEAL" )
				
				DealProcess 	m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setIsGroupSnatch(1);
				m_ParDealProcess.setFlagActive("0");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}	
			
			//执行顺序：19匹配LoginUser属于Group && Dealer != NULL && IsGroupSnatch = 0 && FlagActive = 0 && 当前环节 = 处理环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				//( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ( $tmp_UserGroupList$ LIKE ( "%;"  + 'GroupID' +  ";%" ))
				// AND ( 'DealerID' !=  $NULL$ ) AND ( 'IsGroupSnatch' = 0) AND ('FlagActive' = 0) AND ( 'ProcessType' =  "DEAL" ) 
				
				DealProcess 	m_DealProcess		= new DealProcess();
				ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NOTNULL");
				m_ParDealProcess.setIsGroupSnatch(0);
				m_ParDealProcess.setFlagActive("0");
				
				List DealProcessList			= m_DealProcess.GetList(m_ParDealProcess,0,0);
				
				if (DealProcessList != null && DealProcessList.size()>0)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)DealProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_DealProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_DealProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}	
	
			//执行顺序：20匹配Assignee != NULL && Dealer = 登录用户&& FlagActive = 0 && 当前环节 = 审批环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				//AND ( 'AssigneeID' != NULL)
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'FlagActive' = 0) AND ( 'ProcessType' =  "AUDITING" )
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess		= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NOTNULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setFlagActive("0");
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>0)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
			//执行顺序：21匹配Assignee = NULL && Dealer = 登录用户 && IsGroupSnatch = 1 && FlagActive = 0 && 当前环节 = 审批环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				// AND ( 'AssigneeID' = NULL)
				// AND ( 'DealerID' = $tmp_UserLoginName$) AND ( 'IsGroupSnatch' = 1) AND ( 'FlagActive' = 0) AND ( 'ProcessType' =  "AUDITING" )
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess		= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				m_ParDealProcess.setAssgineeID("NULL");
				m_ParDealProcess.setDealerID(m_BaseFieldInfo_tmp_UserLoginName.getStrFieldValue());
				m_ParDealProcess.setIsGroupSnatch(1);
				m_ParDealProcess.setFlagActive("0");
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>0)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
			
			//执行顺序：22匹配LoginUser属于Group && Dealer != NULL && IsGroupSnatch = 0 && FlagActive = 0 && 当前环节 = 审批环节
			if (m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue() == null || m_BaseFieldInfo_tmp_Pro_ProcessID.getStrFieldValue().equals(""))
			{
				// ( 'ProcessBaseID' = $BaseID$) AND ( 'ProcessBaseSchema' = $BaseSchema$) 
				//AND ( $tmp_UserGroupList$ LIKE ( "%;"  + 'GroupID' +  ";%" ))
				// AND ( 'DealerID' != NULL) AND ( 'IsGroupSnatch' = 0) AND ( 'FlagActive' = 0) AND ( 'ProcessType' =  "AUDITING" )
				
				AuditingProcess m_AuditingProcess	= new AuditingProcess();
				ParDealProcess 	m_ParDealProcess		= new ParDealProcess();
				
				m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
				m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
				//m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+";"+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setGroupID("LIKEFILD:'"+";"+m_BaseFieldInfo_tmp_UserGroupList.getStrFieldValue()+"' like '%;'||DB_FIELD_NAME||';%'");
				m_ParDealProcess.setDealerID("NOTNULL");
				m_ParDealProcess.setIsGroupSnatch(0);
				m_ParDealProcess.setFlagActive("0");
				
				List AuditingProcessList			= m_AuditingProcess.GetList(m_ParDealProcess,0,0);
				
				if (AuditingProcessList != null && AuditingProcessList.size()>0)
				{
					AuditingProcessModel m_AuditingProcessModel = (AuditingProcessModel)AuditingProcessList.get(0);
					m_BaseFieldInfo_tmp_Pro_ProcessID.setStrFieldValue(m_AuditingProcessModel.getProcessID());
					m_BaseFieldInfo_tmp_Pro_ProcessType.setStrFieldValue(m_AuditingProcessModel.getProcessType());
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessID",m_BaseFieldInfo_tmp_Pro_ProcessID);		
					Hashtable_BaseAllFields.put("tmp_Pro_ProcessType",m_BaseFieldInfo_tmp_Pro_ProcessType);		
					return true;
				}
			}
		}catch(Exception ex)
		{ex.printStackTrace();
			this.Init_BaseWrite_Log("根据本人信息读工单环节信息的ID",1,"根据本人信息读工单环节信息的ID失败，异常！"+ex.getMessage());
			return false;
		}
		
		return false;

	}

	/**
	 * 描述：根据工单环节关键字,读工单环节信息并进行设置的函数
	 * @param p_ProcessID				环节ID
	 * @param p_ProcessType				环节类型
	 * @return 是否成功
	 */
	private boolean Init_Open_Set_Process(String p_ProcessID,String p_ProcessType) {
	
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
					DealProcess 			m_DealProcess			= new DealProcess();
					DealProcessModel 		m_DealProcessModel		= m_DealProcess.GetOneForKey(p_ProcessID,Constants.IsNotArchive);
					//当前环节的环节ID：			tmp_Pro_ProcessID 			= ProcessID
					//当前环节的类型：				tmp_Pro_ProcessType			= ProcessType
					//当前环节的工单类型：			tmp_Pro_BaseSchema			= ProcessBaseSchema
					//当前环节的工单BaseID：		tmp_Pro_BaseID				= ProcessBaseID
					//当前环节的前一环节号：			tmp_Pro_PrevPhaseNo 		= PrevPhaseNo
					//当前环节的前一环节号：			tmp_Pro_TransferPhaseNo		= TransferPhaseNo
					//当前环节的环节号：			tmp_Pro_PhaseNo 			= PhaseNo
					//当前环节的派发人登陆名：		tmp_Pro_AssgineeID			= AssgineeID
					//当前环节的派发人名：			tmp_Pro_Assginee			= Assginee		
					//当前环节的派发组登陆名：		tmp_Pro_GroupID				= GroupID
					//当前环节的派发组名：			tmp_Pro_Group				= Group		
					//当前环节的执行人登陆名：		tmp_Pro_DealerID			= DealerID
					//当前环节的执行人名：			tmp_Pro_Dealer				= Dealer
					//当前环节的状态：				tmp_Pro_Status 				= ProcessStatus
					//当前环节的环节描述：			tmp_Pro_Desc				= Desc
					//当前环节的开始时间：			tmp_Pro_StDate 				= StDate
					//当前环节的受理时间：			tmp_Pro_BgDate				= BgDate
					//当前环节的结束时间：			tmp_Pro_EdDate				= EdDate
					//当前环节的派发时限：			tmp_Pro_AssignOverTimeDate	= AssignOverTimeDate
					//当前环节的受理时限：			tmp_Pro_AcceptOverTimeDate	= AcceptOverTimeDate
					//当前环节的处理时限：			tmp_Pro_DealOverTimeDate	= DealOverTimeDate
					//当前环节的类型：				tmp_Pro_FlagType 			= FlagType
					//当前环节是否预定义：			tmp_Pro_FlagPredefined 		= FlagPredefined
					//当前环节是否复制品：			tmp_Pro_FlagDuplicated 		= FlagDuplicated
					//当前环节的Active：			tmp_Pro_FlagActive 			= FlagActive		
					//当前环节是否转交给来的环节：	tmp_Pro_Flag31IsTransfer	= Flag31IsTransfer		
					//当前环节是否允许驳回审批：		tmp_Pro_Flag16TurnUpAuditing= Flag16TurnUpAuditing			
					//当前环节是否允许提交审批：		tmp_Pro_Flag15ToAuditing	= Flag15ToAuditing			
					//当前环节是否允许关闭工单：		tmp_Pro_Flag09Close			= Flag09Close			
					//当前环节是否允许作废工单：		tmp_Pro_Flag08Cancel		= Flag08Cancel			
					//当前环节是否允许追回工单：		tmp_Pro_Flag07Recall		= Flag07Recall		
					//当前环节是否允许驳回工单：		tmp_Pro_Flag06TurnUp		= Flag06TurnUp			
					//当前环节是否允许退回工单：		tmp_Pro_Flag05TurnDown		= Flag05TurnDown			
					//当前环节是否允许转派工单：		tmp_Pro_Flag04Transfer		= Flag04Transfer	
					//当前环节是否允许协办工单：		tmp_Pro_Flag03Assist		= Flag03Assist			
					//当前环节是否允许抄送工单：		tmp_Pro_Flag02Copy			= Flag02Copy			
					//当前环节是否允许派发工单：		tmp_Pro_Flag01Assign		= Flag01Assign			
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BaseSchema = null;
					m_BaseFieldInfo_tmp_Pro_BaseSchema = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseSchema");
					m_BaseFieldInfo_tmp_Pro_BaseSchema.setStrFieldValue(m_DealProcessModel.getProcessBaseSchema());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BaseID = null;
					m_BaseFieldInfo_tmp_Pro_BaseID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseID");
					m_BaseFieldInfo_tmp_Pro_BaseID.setStrFieldValue(m_DealProcessModel.getProcessBaseID());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = null;
					m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo");
					m_BaseFieldInfo_tmp_Pro_PrevPhaseNo.setStrFieldValue(m_DealProcessModel.getPrevPhaseNo());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_TransferPhaseNo = null;
					m_BaseFieldInfo_tmp_Pro_TransferPhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_TransferPhaseNo");
					m_BaseFieldInfo_tmp_Pro_TransferPhaseNo.setStrFieldValue(m_DealProcessModel.getTransferPhaseNo());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PhaseNo = null;
					m_BaseFieldInfo_tmp_Pro_PhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo");
					m_BaseFieldInfo_tmp_Pro_PhaseNo.setStrFieldValue(m_DealProcessModel.getPhaseNo());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AssgineeID = null;
					m_BaseFieldInfo_tmp_Pro_AssgineeID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AssgineeID");
					m_BaseFieldInfo_tmp_Pro_AssgineeID.setStrFieldValue(m_DealProcessModel.getAssgineeID());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Assginee = null;
					m_BaseFieldInfo_tmp_Pro_Assginee = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Assginee");
					m_BaseFieldInfo_tmp_Pro_Assginee.setStrFieldValue(m_DealProcessModel.getAssginee());
				
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_GroupID = null;
					m_BaseFieldInfo_tmp_Pro_GroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_GroupID");
					m_BaseFieldInfo_tmp_Pro_GroupID.setStrFieldValue(m_DealProcessModel.getGroupID());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Group = null;
					m_BaseFieldInfo_tmp_Pro_Group = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Group");
					m_BaseFieldInfo_tmp_Pro_Group.setStrFieldValue(m_DealProcessModel.getGroup());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_DealerID = null;
					m_BaseFieldInfo_tmp_Pro_DealerID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_DealerID");
					m_BaseFieldInfo_tmp_Pro_DealerID.setStrFieldValue(m_DealProcessModel.getDealerID());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Dealer = null;
					m_BaseFieldInfo_tmp_Pro_Dealer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Dealer");
					m_BaseFieldInfo_tmp_Pro_Dealer.setStrFieldValue(m_DealProcessModel.getDealer());
				
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Status = null;
					m_BaseFieldInfo_tmp_Pro_Status = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Status");
					m_BaseFieldInfo_tmp_Pro_Status.setStrFieldValue(m_DealProcessModel.getProcessStatus());
				
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Desc = null;
					m_BaseFieldInfo_tmp_Pro_Desc = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Desc");
					m_BaseFieldInfo_tmp_Pro_Desc.setStrFieldValue(m_DealProcessModel.getDesc());
				
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_StDate = null;
					m_BaseFieldInfo_tmp_Pro_StDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_StDate");
					Long m_tmp_Pro_StDate_Long = new Long(m_DealProcessModel.getStDate());
					m_BaseFieldInfo_tmp_Pro_StDate.setStrFieldValue(m_tmp_Pro_StDate_Long.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BgDate = null;
					m_BaseFieldInfo_tmp_Pro_BgDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BgDate");
					Long m_tmp_Pro_BgDate_Long = new Long(m_DealProcessModel.getBgDate());
					m_BaseFieldInfo_tmp_Pro_BgDate.setStrFieldValue(m_tmp_Pro_BgDate_Long.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_EdDate = null;
					m_BaseFieldInfo_tmp_Pro_EdDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_EdDate");
					Long m_tmp_Pro_EdDate_Long = new Long(m_DealProcessModel.getEdDate());
					m_BaseFieldInfo_tmp_Pro_EdDate.setStrFieldValue(m_tmp_Pro_EdDate_Long.toString());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AssignOverTimeDate = null;
					m_BaseFieldInfo_tmp_Pro_AssignOverTimeDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AssignOverTimeDate");
					Long m_tmp_Pro_AssignOverTimeDate_Long = new Long(m_DealProcessModel.getAssignOverTimeDate());
					m_BaseFieldInfo_tmp_Pro_AssignOverTimeDate.setStrFieldValue(m_tmp_Pro_AssignOverTimeDate_Long.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AcceptOverTimeDate = null;
					m_BaseFieldInfo_tmp_Pro_AcceptOverTimeDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AcceptOverTimeDate");
					Long m_tmp_Pro_AcceptOverTimeDate_Long = new Long(m_DealProcessModel.getAcceptOverTimeDate());
					m_BaseFieldInfo_tmp_Pro_AcceptOverTimeDate.setStrFieldValue(m_tmp_Pro_AcceptOverTimeDate_Long.toString());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_DealOverTimeDate = null;
					m_BaseFieldInfo_tmp_Pro_DealOverTimeDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_DealOverTimeDate");
					Long m_tmp_Pro_DealOverTimeDate_Long = new Long(m_DealProcessModel.getDealOverTimeDate());
					m_BaseFieldInfo_tmp_Pro_DealOverTimeDate.setStrFieldValue(m_tmp_Pro_DealOverTimeDate_Long.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagType = null;
					m_BaseFieldInfo_tmp_Pro_FlagType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagType");
					Integer m_tmp_Pro_FlagType_int = new Integer(m_DealProcessModel.getFlagType());
					m_BaseFieldInfo_tmp_Pro_FlagType.setStrFieldValue(m_tmp_Pro_FlagType_int.toString());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagPredefined = null;
					m_BaseFieldInfo_tmp_Pro_FlagPredefined = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagPredefined");
					Integer m_tmp_Pro_FlagPredefined_int = Integer.valueOf(String.valueOf(m_DealProcessModel.getFlagPredefined()));
					m_BaseFieldInfo_tmp_Pro_FlagPredefined.setStrFieldValue(m_tmp_Pro_FlagPredefined_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagDuplicated = null;
					m_BaseFieldInfo_tmp_Pro_FlagDuplicated = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagDuplicated");
					Integer m_tmp_Pro_FlagDuplicated_int = Integer.valueOf(String.valueOf(m_DealProcessModel.getFlagDuplicated()));
					m_BaseFieldInfo_tmp_Pro_FlagDuplicated.setStrFieldValue(m_tmp_Pro_FlagDuplicated_int.toString());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagActive = null;
					m_BaseFieldInfo_tmp_Pro_FlagActive = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagActive");
					Integer m_tmp_Pro_FlagActive_int = Integer.valueOf(String.valueOf(m_DealProcessModel.getFlagActive()));
					m_BaseFieldInfo_tmp_Pro_FlagActive.setStrFieldValue(m_tmp_Pro_FlagActive_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag31IsTransfer = null;
					m_BaseFieldInfo_tmp_Pro_Flag31IsTransfer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer");
					Integer m_tmp_Pro_Flag31IsTransfer_int = new Integer(m_DealProcessModel.getFlag31IsTransfer());
					m_BaseFieldInfo_tmp_Pro_Flag31IsTransfer.setStrFieldValue(m_tmp_Pro_Flag31IsTransfer_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing = null;
					m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag15ToAuditing");
					Integer m_tmp_Pro_Flag15ToAuditing_int = new Integer(m_DealProcessModel.getFlag15ToAuditing());
					m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing.setStrFieldValue(m_tmp_Pro_Flag15ToAuditing_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag09Close = null;
					m_BaseFieldInfo_tmp_Pro_Flag09Close = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag09Close");
					Integer m_tmp_Pro_Flag09Close_int = new Integer(m_DealProcessModel.getFlag09Close());
					m_BaseFieldInfo_tmp_Pro_Flag09Close.setStrFieldValue(m_tmp_Pro_Flag09Close_int.toString());
				
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag08Cancel = null;
					m_BaseFieldInfo_tmp_Pro_Flag08Cancel = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag08Cancel");
					Integer m_tmp_Pro_Flag08Cancel_int = new Integer(m_DealProcessModel.getFlag08Cancel());
					m_BaseFieldInfo_tmp_Pro_Flag08Cancel.setStrFieldValue(m_tmp_Pro_Flag08Cancel_int.toString());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag07Recall = null;
					m_BaseFieldInfo_tmp_Pro_Flag07Recall = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag07Recall");
					Integer m_tmp_Pro_Flag07Recall_int = new Integer(m_DealProcessModel.getFlag07Recall());
					m_BaseFieldInfo_tmp_Pro_Flag07Recall.setStrFieldValue(m_tmp_Pro_Flag07Recall_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = null;
					m_BaseFieldInfo_tmp_Pro_Flag06TurnUp = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag06TurnUp");
					Integer m_tmp_Pro_Flag06TurnUp_int = new Integer(m_DealProcessModel.getFlag06TurnUp());
					m_BaseFieldInfo_tmp_Pro_Flag06TurnUp.setStrFieldValue(m_tmp_Pro_Flag06TurnUp_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = null;
					m_BaseFieldInfo_tmp_Pro_Flag05TurnDown = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag05TurnDown");
					Integer m_tmp_Pro_Flag05TurnDown_int = new Integer(m_DealProcessModel.getFlag05TurnDown());
					m_BaseFieldInfo_tmp_Pro_Flag05TurnDown.setStrFieldValue(m_tmp_Pro_Flag05TurnDown_int.toString());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag04Transfer = null;
					m_BaseFieldInfo_tmp_Pro_Flag04Transfer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag04Transfer");
					Integer m_tmp_Pro_Flag04Transfer_int = new Integer(m_DealProcessModel.getFlag04Transfer());
					m_BaseFieldInfo_tmp_Pro_Flag04Transfer.setStrFieldValue(m_tmp_Pro_Flag04Transfer_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag03Assist = null;
					m_BaseFieldInfo_tmp_Pro_Flag03Assist = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag03Assist");
					Integer m_tmp_Pro_Flag03Assist_int = new Integer(m_DealProcessModel.getFlag03Assist());
					m_BaseFieldInfo_tmp_Pro_Flag03Assist.setStrFieldValue(m_tmp_Pro_Flag03Assist_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag02Copy = null;
					m_BaseFieldInfo_tmp_Pro_Flag02Copy = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag02Copy");
					Integer m_tmp_Pro_Flag02Copy_int = new Integer(m_DealProcessModel.getFlag02Copy());
					m_BaseFieldInfo_tmp_Pro_Flag02Copy.setStrFieldValue(m_tmp_Pro_Flag02Copy_int.toString());
				
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag01Assign = null;
					m_BaseFieldInfo_tmp_Pro_Flag01Assign = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag01Assign");
					Integer m_tmp_Pro_Flag01Assign_int = new Integer(m_DealProcessModel.getFlag01Assign());
					m_BaseFieldInfo_tmp_Pro_Flag01Assign.setStrFieldValue(m_tmp_Pro_Flag01Assign_int.toString());
					
					//------------------------------------
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Commissioner = null;
					m_BaseFieldInfo_tmp_Pro_Commissioner = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Commissioner");
					m_BaseFieldInfo_tmp_Pro_Commissioner.setStrFieldValue(m_DealProcessModel.getCommissioner());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CommissionerID = null;
					m_BaseFieldInfo_tmp_Pro_CommissionerID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CommissionerID");
					m_BaseFieldInfo_tmp_Pro_CommissionerID.setStrFieldValue(m_DealProcessModel.getCommissionerID());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup = null;
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CloseBaseSamenessGroup");
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup.setStrFieldValue(m_DealProcessModel.getCloseBaseSamenessGroup());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID = null;
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CloseBaseSamenessGroupID");
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID.setStrFieldValue(m_DealProcessModel.getCloseBaseSamenessGroupID());

					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_IsGroupSnatch = null;
					m_BaseFieldInfo_tmp_Pro_IsGroupSnatch = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_IsGroupSnatch");
					//Integer m_tmp_Pro_IsGroupSnatch_int = new Integer(m_DealProcessModel.getIsGroupSnatch());
					//m_BaseFieldInfo_tmp_Pro_IsGroupSnatch.setStrFieldValue(m_tmp_Pro_IsGroupSnatch_int.toString());

					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag32IsToTransfer = null;
					m_BaseFieldInfo_tmp_Pro_Flag32IsToTransfer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag32IsToTransfer");
					Integer m_tmp_Pro_Flag32IsToTransfer_int = new Integer(m_DealProcessModel.getFlag32IsToTransfer());
					m_BaseFieldInfo_tmp_Pro_Flag32IsToTransfer.setStrFieldValue(m_tmp_Pro_Flag32IsToTransfer_int.toString());

					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag33IsEndPhase = null;
					m_BaseFieldInfo_tmp_Pro_Flag33IsEndPhase = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag33IsEndPhase");
					Integer m_tmp_Pro_Flag33IsEndPhase_int = new Integer(m_DealProcessModel.getFlag33IsEndPhase());
					m_BaseFieldInfo_tmp_Pro_Flag33IsEndPhase.setStrFieldValue(m_tmp_Pro_Flag33IsEndPhase_int.toString());
					
					
				}
				else
				{
					AuditingProcess 		m_AuditingProcess		= new AuditingProcess();
					AuditingProcessModel	m_AuditingProcessModel 	= m_AuditingProcess.GetOneForKey(p_ProcessID,Constants.IsNotArchive);

					//当前环节的提交审批时限：				tmp_Pro_AuditingOverTimeDate		= AuditingOverTimeDate
					//当前环节是否允许追回审批：			tmp_Pro_Flag17RecallAuditing		= Flag17RecallAuditing			
					//当前环节是否允许驳回审批：			tmp_Pro_Flag16TurnUpAuditing		= Flag16TurnUpAuditing			
					//当前环节的审批环的提交审批环节号：	tmp_Pro_AuditingWayPhaseNo		= AuditingWayPhaseNo	
					//当前环节的审批环的环号：				tmp_Pro_AuditingWayNo			= AuditingWayNo	
					//当前环节的审批环的是否活动：			tmp_Pro_AuditingWayIsActive		= AuditingWayIsActive

					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BaseSchema = null;
					m_BaseFieldInfo_tmp_Pro_BaseSchema = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseSchema");
					m_BaseFieldInfo_tmp_Pro_BaseSchema.setStrFieldValue(m_AuditingProcessModel.getProcessBaseSchema());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BaseID = null;
					m_BaseFieldInfo_tmp_Pro_BaseID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseID");
					m_BaseFieldInfo_tmp_Pro_BaseID.setStrFieldValue(m_AuditingProcessModel.getProcessBaseID());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = null;
					m_BaseFieldInfo_tmp_Pro_PrevPhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo");
					m_BaseFieldInfo_tmp_Pro_PrevPhaseNo.setStrFieldValue(m_AuditingProcessModel.getPrevPhaseNo());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_PhaseNo = null;
					m_BaseFieldInfo_tmp_Pro_PhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo");
					m_BaseFieldInfo_tmp_Pro_PhaseNo.setStrFieldValue(m_AuditingProcessModel.getPhaseNo());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AssgineeID = null;
					m_BaseFieldInfo_tmp_Pro_AssgineeID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AssgineeID");
					m_BaseFieldInfo_tmp_Pro_AssgineeID.setStrFieldValue(m_AuditingProcessModel.getAssgineeID());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Assginee = null;
					m_BaseFieldInfo_tmp_Pro_Assginee = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Assginee");
					m_BaseFieldInfo_tmp_Pro_Assginee.setStrFieldValue(m_AuditingProcessModel.getAssginee());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_GroupID = null;
					m_BaseFieldInfo_tmp_Pro_GroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_GroupID");
					m_BaseFieldInfo_tmp_Pro_GroupID.setStrFieldValue(m_AuditingProcessModel.getGroupID());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Group = null;
					m_BaseFieldInfo_tmp_Pro_Group = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Group");
					m_BaseFieldInfo_tmp_Pro_Group.setStrFieldValue(m_AuditingProcessModel.getGroup());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_DealerID = null;
					m_BaseFieldInfo_tmp_Pro_DealerID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_DealerID");
					m_BaseFieldInfo_tmp_Pro_DealerID.setStrFieldValue(m_AuditingProcessModel.getDealerID());
			
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Dealer = null;
					m_BaseFieldInfo_tmp_Pro_Dealer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Dealer");
					m_BaseFieldInfo_tmp_Pro_Dealer.setStrFieldValue(m_AuditingProcessModel.getDealer());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Status = null;
					m_BaseFieldInfo_tmp_Pro_Status = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Status");
					m_BaseFieldInfo_tmp_Pro_Status.setStrFieldValue(m_AuditingProcessModel.getProcessStatus());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Desc = null;
					m_BaseFieldInfo_tmp_Pro_Desc = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Desc");
					m_BaseFieldInfo_tmp_Pro_Desc.setStrFieldValue(m_AuditingProcessModel.getDesc());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_StDate = null;
					m_BaseFieldInfo_tmp_Pro_StDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_StDate");
					Long m_tmp_Pro_StDate_Long = new Long(m_AuditingProcessModel.getStDate());
					m_BaseFieldInfo_tmp_Pro_StDate.setStrFieldValue(m_tmp_Pro_StDate_Long.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_BgDate = null;
					m_BaseFieldInfo_tmp_Pro_BgDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BgDate");
					Long m_tmp_Pro_BgDate_Long = new Long(m_AuditingProcessModel.getBgDate());
					m_BaseFieldInfo_tmp_Pro_BgDate.setStrFieldValue(m_tmp_Pro_BgDate_Long.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_EdDate = null;
					m_BaseFieldInfo_tmp_Pro_EdDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_EdDate");
					Long m_tmp_Pro_EdDate_Long = new Long(m_AuditingProcessModel.getEdDate());
					m_BaseFieldInfo_tmp_Pro_EdDate.setStrFieldValue(m_tmp_Pro_EdDate_Long.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagType = null;
					m_BaseFieldInfo_tmp_Pro_FlagType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagType");
					Integer m_tmp_Pro_FlagType_int = new Integer(m_AuditingProcessModel.getFlagType());
					m_BaseFieldInfo_tmp_Pro_FlagType.setStrFieldValue(m_tmp_Pro_FlagType_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagPredefined = null;
					m_BaseFieldInfo_tmp_Pro_FlagPredefined = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagPredefined");
					Integer m_tmp_Pro_FlagPredefined_int = Integer.valueOf(String.valueOf(m_AuditingProcessModel.getFlagPredefined()));
					m_BaseFieldInfo_tmp_Pro_FlagPredefined.setStrFieldValue(m_tmp_Pro_FlagPredefined_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagDuplicated = null;
					m_BaseFieldInfo_tmp_Pro_FlagDuplicated = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagDuplicated");
					Integer m_tmp_Pro_FlagDuplicated_int = Integer.valueOf(String.valueOf(m_AuditingProcessModel.getFlagDuplicated()));
					m_BaseFieldInfo_tmp_Pro_FlagDuplicated.setStrFieldValue(m_tmp_Pro_FlagDuplicated_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_FlagActive = null;
					m_BaseFieldInfo_tmp_Pro_FlagActive = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_FlagActive");
					Integer m_tmp_Pro_FlagActive_int = Integer.valueOf(String.valueOf(m_AuditingProcessModel.getFlagActive()));
					m_BaseFieldInfo_tmp_Pro_FlagActive.setStrFieldValue(m_tmp_Pro_FlagActive_int.toString());
	
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing = null;
					m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag15ToAuditing");
					Integer m_tmp_Pro_Flag15ToAuditing_int = new Integer(m_AuditingProcessModel.getFlag15ToAuditing());
					m_BaseFieldInfo_tmp_Pro_Flag15ToAuditing.setStrFieldValue(m_tmp_Pro_Flag15ToAuditing_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag08Cancel = null;
					m_BaseFieldInfo_tmp_Pro_Flag08Cancel = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag08Cancel");
					Integer m_tmp_Pro_Flag08Cancel_int = new Integer(m_AuditingProcessModel.getFlag08Cancel());
					m_BaseFieldInfo_tmp_Pro_Flag08Cancel.setStrFieldValue(m_tmp_Pro_Flag08Cancel_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag04Transfer = null;
					m_BaseFieldInfo_tmp_Pro_Flag04Transfer = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag04Transfer");
					Integer m_tmp_Pro_Flag04Transfer_int = new Integer(m_AuditingProcessModel.getFlag04Transfer());
					m_BaseFieldInfo_tmp_Pro_Flag04Transfer.setStrFieldValue(m_tmp_Pro_Flag04Transfer_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag03Assist = null;
					m_BaseFieldInfo_tmp_Pro_Flag03Assist = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag03Assist");
					Integer m_tmp_Pro_Flag03Assist_int = new Integer(m_AuditingProcessModel.getFlag03Assist());
					m_BaseFieldInfo_tmp_Pro_Flag03Assist.setStrFieldValue(m_tmp_Pro_Flag03Assist_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag02Copy = null;
					m_BaseFieldInfo_tmp_Pro_Flag02Copy = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag02Copy");
					Integer m_tmp_Pro_Flag02Copy_int = new Integer(m_AuditingProcessModel.getFlag02Copy());
					m_BaseFieldInfo_tmp_Pro_Flag02Copy.setStrFieldValue(m_tmp_Pro_Flag02Copy_int.toString());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag01Assign = null;
					m_BaseFieldInfo_tmp_Pro_Flag01Assign = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag01Assign");
					Integer m_tmp_Pro_Flag01Assign_int = new Integer(m_AuditingProcessModel.getFlag01Assign());
					m_BaseFieldInfo_tmp_Pro_Flag01Assign.setStrFieldValue(m_tmp_Pro_Flag01Assign_int.toString());
					
					//---------------------------------------------------------------------------------------------------
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AuditingOverTimeDate = null;
					m_BaseFieldInfo_tmp_Pro_AuditingOverTimeDate = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AuditingOverTimeDate");
					Long m_tmp_Pro_AuditingOverTimeDate_Long = new Long(m_AuditingProcessModel.getAuditingOverTimeDate());
					m_BaseFieldInfo_tmp_Pro_AuditingOverTimeDate.setStrFieldValue(m_tmp_Pro_AuditingOverTimeDate_Long.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag17RecallAuditing = null;
					m_BaseFieldInfo_tmp_Pro_Flag17RecallAuditing = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag17RecallAuditing");
					Integer m_tmp_Pro_Flag17RecallAuditing_int = new Integer(m_AuditingProcessModel.getFlag17RecallAuditing());
					m_BaseFieldInfo_tmp_Pro_Flag17RecallAuditing.setStrFieldValue(m_tmp_Pro_Flag17RecallAuditing_int.toString());
	
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag16TurnUpAuditing = null;
					m_BaseFieldInfo_tmp_Pro_Flag16TurnUpAuditing = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag16TurnUpAuditing");
					Integer m_tmp_Pro_Flag16TurnUpAuditing_int = new Integer(m_AuditingProcessModel.getFlag16TurnUpAuditing());
					m_BaseFieldInfo_tmp_Pro_Flag16TurnUpAuditing.setStrFieldValue(m_tmp_Pro_Flag16TurnUpAuditing_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AuditingWayIsActive = null;
					m_BaseFieldInfo_tmp_Pro_AuditingWayIsActive = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AuditingWayIsActive");
					Integer m_tmp_Pro_AuditingWayIsActive_int = new Integer(m_AuditingProcessModel.getAuditingWayIsActive());
					m_BaseFieldInfo_tmp_Pro_AuditingWayIsActive.setStrFieldValue(m_tmp_Pro_AuditingWayIsActive_int.toString());
		
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AuditingWayPhaseNo = null;
					m_BaseFieldInfo_tmp_Pro_AuditingWayPhaseNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AuditingWayPhaseNo");
					m_BaseFieldInfo_tmp_Pro_AuditingWayPhaseNo.setStrFieldValue(m_AuditingProcessModel.getAuditingWayPhaseNo());
				
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_AuditingWayNo = null;
					m_BaseFieldInfo_tmp_Pro_AuditingWayNo = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_AuditingWayNo");
					m_BaseFieldInfo_tmp_Pro_AuditingWayNo.setStrFieldValue(m_AuditingProcessModel.getAuditingWayNo());
					
					
					//--------------------------------------------
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Commissioner = null;
					m_BaseFieldInfo_tmp_Pro_Commissioner = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Commissioner");
					m_BaseFieldInfo_tmp_Pro_Commissioner.setStrFieldValue(m_AuditingProcessModel.getCommissioner());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CommissionerID = null;
					m_BaseFieldInfo_tmp_Pro_CommissionerID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CommissionerID");
					m_BaseFieldInfo_tmp_Pro_CommissionerID.setStrFieldValue(m_AuditingProcessModel.getCommissionerID());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup = null;
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CloseBaseSamenessGroup");
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroup.setStrFieldValue(m_AuditingProcessModel.getCloseBaseSamenessGroup());
					
					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID = null;
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_CloseBaseSamenessGroupID");
					m_BaseFieldInfo_tmp_Pro_CloseBaseSamenessGroupID.setStrFieldValue(m_AuditingProcessModel.getCloseBaseSamenessGroupID());

					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_IsGroupSnatch = null;
					m_BaseFieldInfo_tmp_Pro_IsGroupSnatch = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_IsGroupSnatch");
					Integer m_tmp_Pro_IsGroupSnatch_int = new Integer(m_AuditingProcessModel.getIsGroupSnatch());
					m_BaseFieldInfo_tmp_Pro_IsGroupSnatch.setStrFieldValue(m_tmp_Pro_IsGroupSnatch_int.toString());

					BaseFieldInfo m_BaseFieldInfo_tmp_Pro_Flag33IsEndPhase = null;
					m_BaseFieldInfo_tmp_Pro_Flag33IsEndPhase = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag33IsEndPhase");
					Integer m_tmp_Pro_Flag33IsEndPhase_int = new Integer(m_AuditingProcessModel.getFlag33IsEndPhase());
					m_BaseFieldInfo_tmp_Pro_Flag33IsEndPhase.setStrFieldValue(m_tmp_Pro_Flag33IsEndPhase_int.toString());
					
				}
			}
			else
			{
				this.Init_BaseWrite_Log("根据工单环节关键字,读工单环节信息",1,"根据工单环节关键字,读工单环节信息失败，参数错误！");
				return false;			
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			this.Init_BaseWrite_Log("根据工单环节关键字,读工单环节信息",1,"根据工单环节关键字,读工单环节信息失败，异常！"+ex.getMessage());
			return false;
		}		
		return true;
	}
	/**
	 * 描述：插入处理记录
	 * @param p_BaseToDealObject		派发对象的List(BaseToDealObject类的对象的数组)
	 * @return 是否成功
	 */
	protected boolean Init_Push_DealProcess(List p_BaseToDealObject) {
		
		BaseFieldInfo m_BaseFieldInfo_tmp_BaseUser_OpDesc_ToDeal = null;
		m_BaseFieldInfo_tmp_BaseUser_OpDesc_ToDeal = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseUser_OpDesc_ToDeal");
		String m_str_tmp_BaseUser_OpDesc_ToDeal = m_BaseFieldInfo_tmp_BaseUser_OpDesc_ToDeal.getStrFieldValue();
		
		BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessType = null;
		m_BaseFieldInfo_tmp_Pro_ProcessType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType");
		String m_str_tmp_Pro_ProcessType = m_BaseFieldInfo_tmp_Pro_ProcessType.getStrFieldValue();
		
		String tmp_Select_Assigner_ProcessIDS 	= "";
		String tmp_Select_Assister_ProcessIDS 	= "";
		String tmp_Select_Copyer_ProcessIDS 	= "";
		try
		{
			for (int i = 0;i<p_BaseToDealObject.size();i++)
			{

				BaseToDealObject m_tmp_BaseToDealObject = (BaseToDealObject)p_BaseToDealObject.get(i);
				
				Map Hashtable_ToDealProcessAllFields = new HashMap();
				
				Hashtable_ToDealProcessAllFields.put("ProcessBaseID", new PublicFieldInfo("ProcessBaseID","700020001",((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_Pro_BaseID")).getStrFieldValue(),4));
				Hashtable_ToDealProcessAllFields.put("ProcessBaseSchema", new PublicFieldInfo("ProcessBaseSchema","700020002",((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_Pro_BaseSchema")).getStrFieldValue(),4));
				Hashtable_ToDealProcessAllFields.put("PhaseNo", new PublicFieldInfo("PhaseNo","700020003",Init_Get_GUID("BID",4),4));
				Hashtable_ToDealProcessAllFields.put("PrevPhaseNo", new PublicFieldInfo("PrevPhaseNo","700020004",((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo")).getStrFieldValue(),4));
				Hashtable_ToDealProcessAllFields.put("CreateByUserID", new PublicFieldInfo("CreateByUserID","700020045",((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_UserLoginName")).getStrFieldValue(),4));
				Hashtable_ToDealProcessAllFields.put("Assginee", new PublicFieldInfo("Assginee","700020005",m_tmp_BaseToDealObject.getAssginee(),4));
				Hashtable_ToDealProcessAllFields.put("AssgineeID", new PublicFieldInfo("AssgineeID","700020006",m_tmp_BaseToDealObject.getAssgineeID(),4));
				Hashtable_ToDealProcessAllFields.put("Group", new PublicFieldInfo("Group","700020007",m_tmp_BaseToDealObject.getGroup(),4));
				Hashtable_ToDealProcessAllFields.put("GroupID", new PublicFieldInfo("GroupID","700020008",m_tmp_BaseToDealObject.getGroupID(),4));
				Hashtable_ToDealProcessAllFields.put("ProcessStatus", new PublicFieldInfo("ProcessStatus","700020011","待处理",4));
				Hashtable_ToDealProcessAllFields.put("AssignOverTimeDate", new PublicFieldInfo("AssignOverTimeDate","700020012",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAssignOverTimeDate()))).toString(),7));
				Hashtable_ToDealProcessAllFields.put("AcceptOverTimeDate", new PublicFieldInfo("AcceptOverTimeDate","700020013",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAcceptOverTimeDate()))).toString(),7));
				Hashtable_ToDealProcessAllFields.put("DealOverTimeDate", new PublicFieldInfo("DealOverTimeDate","700020014",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getDealOverTimeDate()))).toString(),7));
				Hashtable_ToDealProcessAllFields.put("StDate", new PublicFieldInfo("StDate","700020015",null,7));
				Hashtable_ToDealProcessAllFields.put("FlagType", new PublicFieldInfo("FlagType","700020019",(new Integer (m_tmp_BaseToDealObject.getFlagType())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("FlagActive", new PublicFieldInfo("FlagActive","700020020","6",2));
				Hashtable_ToDealProcessAllFields.put("FlagPredefined", new PublicFieldInfo("FlagPredefined","700020021","0",2));
				Hashtable_ToDealProcessAllFields.put("FlagDuplicated", new PublicFieldInfo("FlagDuplicated","700020022","0",2));

				Hashtable_ToDealProcessAllFields.put("Flag01Assign", new PublicFieldInfo("Flag01Assign","700020023",(new Integer (m_tmp_BaseToDealObject.getFlag01Assign())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag02Copy", new PublicFieldInfo("Flag02Copy","700020024",(new Integer (m_tmp_BaseToDealObject.getFlag02Copy())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag03Assist", new PublicFieldInfo("Flag03Assist","700020025",(new Integer (m_tmp_BaseToDealObject.getFlag03Assist())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag04Transfer", new PublicFieldInfo("Flag04Transfer","700020026",(new Integer (m_tmp_BaseToDealObject.getFlag04Transfer())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag05TurnDown", new PublicFieldInfo("Flag05TurnDown","700020027",(new Integer (m_tmp_BaseToDealObject.getFlag05TurnDown())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag06TurnUp", new PublicFieldInfo("Flag06TurnUp","700020028",(new Integer (m_tmp_BaseToDealObject.getFlag06TurnUp())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag07Recall", new PublicFieldInfo("Flag07Recall","700020029",(new Integer (m_tmp_BaseToDealObject.getFlag07Recall())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag08Cancel", new PublicFieldInfo("Flag08Cancel","700020030",(new Integer (m_tmp_BaseToDealObject.getFlag08Cancel())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag09Close", new PublicFieldInfo("Flag09Close","700020031",(new Integer (m_tmp_BaseToDealObject.getFlag09Close())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag15ToAuditing", new PublicFieldInfo("Flag15ToAuditing","700020032",(new Integer (m_tmp_BaseToDealObject.getFlag15ToAuditing())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag20SideBySide", new PublicFieldInfo("Flag20SideBySide","700020033","0",6));
				Hashtable_ToDealProcessAllFields.put("Flag22IsSelect", new PublicFieldInfo("Flag22IsSelect","700020046","0",6));
				Hashtable_ToDealProcessAllFields.put("Flag31IsTransfer", new PublicFieldInfo("Flag31IsTransfer","700020035",(new Integer (m_tmp_BaseToDealObject.getFlag31IsTransfer())).toString(),6));		
				Hashtable_ToDealProcessAllFields.put("Flag32IsToTransfer", new PublicFieldInfo("Flag32IsToTransfer","700020052","0",6));
				Hashtable_ToDealProcessAllFields.put("Flag33IsEndPhase", new PublicFieldInfo("Flag33IsEndPhase","700020053","1",6));		
				
				if (m_tmp_BaseToDealObject.getFlagType()==0 && m_str_tmp_Pro_ProcessType.equals("DEAL"))
					//派发
				{
					if (m_tmp_BaseToDealObject.getFlag31IsTransfer()==1)
						//转交过来的
					{
						String str_tmp_Desc = "";
						if (m_str_tmp_BaseUser_OpDesc_ToDeal != null && m_str_tmp_BaseUser_OpDesc_ToDeal.equals("")==false)
						{
							str_tmp_Desc = "：" + m_str_tmp_BaseUser_OpDesc_ToDeal;
						}
						String str_Desc = ((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue() + "转发";	
						str_Desc = str_Desc + m_tmp_BaseToDealObject.getGroup() + m_tmp_BaseToDealObject.getAssginee() + str_tmp_Desc + "；";
						Hashtable_ToDealProcessAllFields.put("Desc", new PublicFieldInfo("Desc","700020018",str_Desc,4));

						if (
								((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer")).getStrFieldValue() == null 
								|| 
								((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer")).getStrFieldValue().equals("0") 
								|| 
								((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer")).getStrFieldValue().equals("")
							)
							//本环节不是转交过来的环节
						{
							Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo")).getStrFieldValue(),4));
						}
						else
							//本环节是转交过来的环节
						{
							Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_Pro_TransferPhaseNo")).getStrFieldValue(),4));
						}
					}
					else
						//非转交过来的
					{
						String str_tmp_Desc = "";
						if (m_str_tmp_BaseUser_OpDesc_ToDeal != null && m_str_tmp_BaseUser_OpDesc_ToDeal.equals("")==false)
						{
							str_tmp_Desc = "：" + m_str_tmp_BaseUser_OpDesc_ToDeal;
						}
						String str_Desc = ((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue() + "派发";	
						str_Desc = str_Desc + m_tmp_BaseToDealObject.getGroup() + m_tmp_BaseToDealObject.getAssginee() + str_tmp_Desc + "；";
						Hashtable_ToDealProcessAllFields.put("Desc", new PublicFieldInfo("Desc","700020018",str_Desc,4));
						Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",null,4));
					}
					//调用Process的插入函数				
					//跟新tmp_Select_Assigner_ProcessIDS，加上该插入的ProcessID
		
					DealProcess m_DealProcess = new DealProcess();
					tmp_Select_Assigner_ProcessIDS = tmp_Select_Assigner_ProcessIDS + m_DealProcess.Insert(Hashtable_ToDealProcessAllFields,0) + ";";
				}
				if (m_tmp_BaseToDealObject.getFlagType()==1 && m_str_tmp_Pro_ProcessType.equals("DEAL"))
					//协办
				{
					String str_tmp_Desc = "";
					if (m_str_tmp_BaseUser_OpDesc_ToDeal != null && m_str_tmp_BaseUser_OpDesc_ToDeal.equals("")==false)
					{
						str_tmp_Desc = "：" + m_str_tmp_BaseUser_OpDesc_ToDeal;
					}
					String str_Desc = ((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue() + "要求";	
					str_Desc = str_Desc + m_tmp_BaseToDealObject.getGroup() + m_tmp_BaseToDealObject.getAssginee() + "协办" + str_tmp_Desc + "；";
					Hashtable_ToDealProcessAllFields.put("Desc", new PublicFieldInfo("Desc","700020018",str_Desc,4));
					Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",null,4));
					//调用Process的插入函数				
					//跟新tmp_Select_Assister_ProcessIDS，加上该插入的ProcessID
			
					DealProcess m_DealProcess = new DealProcess();
					tmp_Select_Assister_ProcessIDS = tmp_Select_Assister_ProcessIDS + m_DealProcess.Insert(Hashtable_ToDealProcessAllFields,0) + ";";
				}
				if (m_tmp_BaseToDealObject.getFlagType()==2 && m_str_tmp_Pro_ProcessType.equals("DEAL"))
					//抄送
				{
					String str_tmp_Desc = "";
					if (m_str_tmp_BaseUser_OpDesc_ToDeal != null && m_str_tmp_BaseUser_OpDesc_ToDeal.equals("")==false)
					{
						str_tmp_Desc = "：" + m_str_tmp_BaseUser_OpDesc_ToDeal;
					}
					String str_Desc = ((BaseFieldInfo) Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue() + "抄送";	
					str_Desc = str_Desc + m_tmp_BaseToDealObject.getGroup() + m_tmp_BaseToDealObject.getAssginee() + str_tmp_Desc + "；";
					Hashtable_ToDealProcessAllFields.put("Desc", new PublicFieldInfo("Desc","700020018",str_Desc,4));
					Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",null,4));
					//调用Process的插入函数				
					//跟新tmp_Select_Copyer_ProcessIDS，加上该插入的ProcessID
				
					DealProcess m_DealProcess = new DealProcess();
					tmp_Select_Copyer_ProcessIDS = tmp_Select_Copyer_ProcessIDS + m_DealProcess.Insert(Hashtable_ToDealProcessAllFields,0) + ";";
				}
				
				((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Select_Assigner_ProcessIDS")).setStrFieldValue(tmp_Select_Assigner_ProcessIDS);
				((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Select_Assister_ProcessIDS")).setStrFieldValue(tmp_Select_Assister_ProcessIDS);
				((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Select_Copyer_ProcessIDS")).setStrFieldValue(tmp_Select_Copyer_ProcessIDS);
		
				Hashtable_ToDealProcessAllFields.clear();
				Hashtable_ToDealProcessAllFields = null;
			}
		}
		catch(Exception ex)
		{ex.printStackTrace();
			this.Init_BaseWrite_Log("插入处理记录",1,"插入处理记录失败，异常！"+ex.getMessage());
			return false;
		}		
		
		return true;
	}

	/**
	 * 描述：根据其他，设置工单特有信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_FieldListInfo		操作需要的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_ActionType			操作动作类型；0：催办；1：退单；2：驳回；3：追回；
	 * @param p_BaseDealObject		操作的对象
	 * @return 返回已经填写的字段信息
	 */
	protected boolean Init_Select_OtherDealProcess(int p_ActionType,List p_BaseDealObject) {
		if (p_BaseDealObject.size() < 1)
		{
			this.Init_BaseWrite_Log("作其他操作时，查找操作的环节信息记录",1,"作其他操作时，查找操作的环节信息记录失败，参数传递失败，没有传递操作对象！");
			return false;
		}
		String str_GroupsID = "";
		String str_AssgineesID = "";
		for (int i = 0 ; i < p_BaseDealObject.size() ; i++)
		{
			BaseDealObject m_tmp_BaseDealObject = (BaseDealObject)p_BaseDealObject.get(i);
			if (m_tmp_BaseDealObject.getAssgineeID() != null && m_tmp_BaseDealObject.getAssgineeID().equals("") == false)
			{
				str_AssgineesID = str_AssgineesID + m_tmp_BaseDealObject.getAssgineeID() + ";";
			}
			else
			{
				str_GroupsID = str_GroupsID + m_tmp_BaseDealObject.getGroupID() + ";";
			}
		}
//		查询条件
//		( $BaseID$ = 'ProcessBaseID') AND ( $BaseSchema$ = 'ProcessBaseSchema') AND ( 'FlagActive' != 6) 
//		AND 
//		(
//			(
//				($P_Others_Deal_SelectOpType$="退单") AND ($tmp_Pro_FlagActive$=1) 
//				AND ('PrevPhaseNo'=$tmp_Pro_PhaseNo$) AND ('FlagActive'=0) AND ('FlagDuplicated' = 0)
//			)
//			OR 
//			(
//				($P_Others_Deal_SelectOpType$="驳回") AND ($tmp_Pro_FlagActive$=1)
//				AND
//				(
//					(
//						('PhaseNo'=$tmp_Pro_PrevPhaseNo$)
//						AND 
//						(
//							('FlagActive'=2) OR ('FlagActive'=0)
//						)
//					)
//					OR
//					(
//						('PhaseNo'=$tmp_Pro_PhaseNo$) AND ('FlagActive'=1) AND ('ProcessStatus' = "处理中")
//		
//					)
//				)
//			)
//			OR 
//			(
//				($P_Others_Deal_SelectOpType$="催办") AND ('FlagActive'=1)
//			)
//			OR 
//			(
//				($P_Others_Deal_SelectOpType$="追回") AND ('FlagActive'=1) AND ('PrevPhaseNo'=$tmp_Pro_PhaseNo$)
//			)
//		)
		if(p_ActionType >3 || p_ActionType < 0)
		{
			this.Init_BaseWrite_Log("作其他操作时，查找操作的环节信息记录",1,"作其他操作时，查找操作的环节信息记录失败，参数传递失败！");
			return false;			
		}
		try
		{
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessID = null;
			m_BaseFieldInfo_tmp_Pro_ProcessID = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessID");
			
			BaseFieldInfo m_BaseFieldInfo_tmp_Pro_ProcessType = null;
			m_BaseFieldInfo_tmp_Pro_ProcessType = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType");
	
			BaseFieldInfo m_BaseFieldInfo_BaseID = null;
			m_BaseFieldInfo_BaseID = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseID");
	
			BaseFieldInfo m_BaseFieldInfo_BaseSchema = null;
			m_BaseFieldInfo_BaseSchema = (BaseFieldInfo)Hashtable_BaseAllFields.get("BaseSchema");
	
			BaseFieldInfo m_BaseFieldInfo_tmp_UserLoginName = null;
			m_BaseFieldInfo_tmp_UserLoginName = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName");
	
			BaseFieldInfo m_BaseFieldInfo_tmp_UserGroupList = null;
			m_BaseFieldInfo_tmp_UserGroupList = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserGroupList");
			
//			"tmp_Select_OtherDeal_ProcessIDS"
			DealProcess		m_DealProcess		= new DealProcess();
			ParDealProcess 	m_ParDealProcess	= new ParDealProcess();
			String str_tmp_Select_OtherDeal_ProcessIDS = "";
			List DealProcessList = null;
			int int_Flag = 0;
			switch (p_ActionType)
			{
				case 0://0：催办；('FlagActive'=1)
					m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
					m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
					m_ParDealProcess.setFlagActive("1");	
					DealProcessList	= m_DealProcess.GetList(m_ParDealProcess,0,0);	
					for (int i=0;i<DealProcessList.size();i++)
					{
						DealProcessModel m_DealProcessModel = (DealProcessModel) DealProcessList.get(i);
						if (
								str_AssgineesID.indexOf(m_DealProcessModel.getAssgineeID() + ";") > -1 
								|| 
								str_GroupsID.indexOf(m_DealProcessModel.getGroupID() + ";") > -1 	
							)
						{
							int_Flag = 1;
							str_tmp_Select_OtherDeal_ProcessIDS = str_tmp_Select_OtherDeal_ProcessIDS + m_DealProcessModel.getProcessID() + ";";
						}
					}
					DealProcessList = null;
					break;
				case 1://1：退单；
//					('PrevPhaseNo'=$tmp_Pro_PhaseNo$) AND ('FlagActive'=0) AND ('FlagDuplicated' = 0)
					m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
					m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
					m_ParDealProcess.setPrevPhaseNo(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo")).getStrFieldValue());	
					m_ParDealProcess.setFlagActive("0");	
					m_ParDealProcess.setFlagDuplicated(0);
					DealProcessList	= m_DealProcess.GetList(m_ParDealProcess,0,0);
					String tempAssgine = "";
					for (int i=0;i<DealProcessList.size();i++)
					{
						DealProcessModel m_DealProcessModel = (DealProcessModel) DealProcessList.get(i);
						if (
								(
									str_AssgineesID.indexOf(m_DealProcessModel.getAssgineeID() + ";") > -1 
									|| 
									str_GroupsID.indexOf(m_DealProcessModel.getGroupID() + ";") > -1 	
								)
								&&str_AssgineesID.indexOf(tempAssgine)==0&&str_GroupsID.indexOf(tempAssgine)==0
							)
						{
							tempAssgine = tempAssgine + m_DealProcessModel.getAssgineeID() + m_DealProcessModel.getGroupID();
							int_Flag = 1;
							str_tmp_Select_OtherDeal_ProcessIDS = str_tmp_Select_OtherDeal_ProcessIDS + m_DealProcessModel.getProcessID() + ";";
						}
					}
					DealProcessList = null;
					break;
				case 2://2：驳回；
//					(
//						(
//							('PhaseNo'=$tmp_Pro_PrevPhaseNo$)
//							AND 
//							(
//								('FlagActive'=2) OR ('FlagActive'=0)
//							)
//						)
//						OR
//						(
//							('PhaseNo'=$tmp_Pro_PhaseNo$) AND ('FlagActive'=1) AND ('ProcessStatus' = "处理中")
//			
//						)
//					)
					m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
					m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
					m_ParDealProcess.setPhaseNo(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo")).getStrFieldValue());	
					m_ParDealProcess.setFlagActive("2");	
					DealProcessList	= m_DealProcess.GetList(m_ParDealProcess,0,0);
					
					for (int i=0;i<DealProcessList.size();i++)
					{
						DealProcessModel m_DealProcessModel = (DealProcessModel) DealProcessList.get(i);
						if (
								str_AssgineesID.indexOf(m_DealProcessModel.getAssgineeID() + ";") > -1 
								|| 
								str_GroupsID.indexOf(m_DealProcessModel.getGroupID() + ";") > -1 	
							)
						{
							int_Flag = 1;
							str_tmp_Select_OtherDeal_ProcessIDS = str_tmp_Select_OtherDeal_ProcessIDS + m_DealProcessModel.getProcessID() + ";";
						}
					}
					DealProcessList = null;
					m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
					m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
					m_ParDealProcess.setPhaseNo(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PrevPhaseNo")).getStrFieldValue());	
					m_ParDealProcess.setFlagActive("0");	
					DealProcessList	= m_DealProcess.GetList(m_ParDealProcess,0,0);
					for (int i=0;i<DealProcessList.size();i++)
					{
						DealProcessModel m_DealProcessModel = (DealProcessModel) DealProcessList.get(i);
						if (
								str_AssgineesID.indexOf(m_DealProcessModel.getAssgineeID() + ";") > -1 
								|| 
								str_GroupsID.indexOf(m_DealProcessModel.getGroupID() + ";") > -1 	
							)
						{
							int_Flag = 1;
							str_tmp_Select_OtherDeal_ProcessIDS = str_tmp_Select_OtherDeal_ProcessIDS + m_DealProcessModel.getProcessID() + ";";
						}
					}
					DealProcessList = null;
					m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
					m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
					m_ParDealProcess.setPhaseNo(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo")).getStrFieldValue());	
					m_ParDealProcess.setFlagActive("1");	
					m_ParDealProcess.setProcessStatus("处理中");	
					DealProcessList	= m_DealProcess.GetList(m_ParDealProcess,0,0);
					for (int i=0;i<DealProcessList.size();i++)
					{
						DealProcessModel m_DealProcessModel = (DealProcessModel) DealProcessList.get(i);
						if (
								str_AssgineesID.indexOf(m_DealProcessModel.getAssgineeID() + ";") > -1 
								|| 
								str_GroupsID.indexOf(m_DealProcessModel.getGroupID() + ";") > -1 	
							)
						{
							int_Flag = 1;
							str_tmp_Select_OtherDeal_ProcessIDS = str_tmp_Select_OtherDeal_ProcessIDS + m_DealProcessModel.getProcessID() + ";";
						}
					}
					DealProcessList = null;					
					break;
				case 3://3：追回；('FlagActive'=1) AND ('PrevPhaseNo'=$tmp_Pro_PhaseNo$)
					m_ParDealProcess.setProcessBaseID(m_BaseFieldInfo_BaseID.getStrFieldValue());
					m_ParDealProcess.setProcessBaseSchema(m_BaseFieldInfo_BaseSchema.getStrFieldValue());
					m_ParDealProcess.setPrevPhaseNo(((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_PhaseNo")).getStrFieldValue());	
					m_ParDealProcess.setFlagActive("1");	
					DealProcessList	= m_DealProcess.GetList(m_ParDealProcess,0,0);
					int int_Flag_AssginProcessS = 0;
					int int_Flag_SelectAssginProcessS = 0;
					
					for (int i=0;i<DealProcessList.size();i++)
					{
						DealProcessModel m_DealProcessModel = (DealProcessModel) DealProcessList.get(i);
						if (Integer.valueOf(m_DealProcessModel.getFlagType()) == 0)//主办
						{
							int_Flag_AssginProcessS = int_Flag_AssginProcessS + 1;
						}						
						if (
								str_AssgineesID.indexOf(m_DealProcessModel.getAssgineeID() + ";") > -1 
								|| 
								str_GroupsID.indexOf(m_DealProcessModel.getGroupID() + ";") > -1 		
							)
						{
							int_Flag = 1;
							str_tmp_Select_OtherDeal_ProcessIDS = str_tmp_Select_OtherDeal_ProcessIDS + m_DealProcessModel.getProcessID() + ";";
							if (Integer.valueOf(m_DealProcessModel.getFlagType()) == 0)//主办
							{
								int_Flag_SelectAssginProcessS = int_Flag_SelectAssginProcessS + 1;
							}
						}
					}	
					if(
							int_Flag_SelectAssginProcessS == int_Flag_AssginProcessS
							&& 
							!((BaseFieldInfo)Hashtable_BaseAllFields.get("P_Others_Deal_rad_DealRecallNewOp")).getStrFieldValue().equals("0")
					   )
					{
						this.Init_BaseWrite_Log("作其他操作时，追回操作",1,"作其他操作时，“追回是否重派”的选项必须为是！");
						return false;						
					}
					break;
			}
			if (int_Flag == 0)
			{
				this.Init_BaseWrite_Log("作其他操作时，查找操作的环节信息记录",1,"作其他操作时，查找操作的环节信息记录失败，查找失败，没有该操作对象！");
				return false;								
			}
			else
			{
				((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Select_OtherDeal_ProcessIDS")).setStrFieldValue(str_tmp_Select_OtherDeal_ProcessIDS);
			}
			return true;			
		}
		catch(Exception ex)
		{ex.printStackTrace();
			this.Init_BaseWrite_Log("作其他操作时，查找操作的环节信息记录",1,"作其他操作时，查找操作的环节信息记录失败，异常！"+ex.getMessage());
			return false;		
		}
	}

	/**
	 * 描述：修改与操作有关的信息表的C2字段C2字段
	 * @param p_strUserLoginName	登陆名 
	 * @param p_BaseSchema			工单类型
	 * @param p_BaseID				工单ID
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @return 是否成功
	 */
	public boolean Init_ActionBaseUpdata(String p_strUserLoginName,String p_BaseSchema, String p_BaseID,int p_Operation) {
		//需要修改的Form有: 
			//		p_BaseSchema：							当前工单
			//		UltraProcess:App_DealProcess：			当前工单的处理记录
			//		UltraProcess:App_DealLink：				当前工单的处理记录流转
			//		UltraProcess:App_AuditingProcess：		当前工单的审批记录
			//		UltraProcess:App_AuditingLink：			当前工单的审批记录流转
			//		WF:EL_UVS_TSKAttachment：		当前工单的附件记录
			//		WF:EL_UVS_TSK_Infor：			当前工单的所有工单记录
			//		UltraProcess:Config_BaseCategory：		当前工单的工单类别
		Base m_Base = new Base();
		return m_Base.UpdateC2(p_BaseSchema,p_BaseID,p_strUserLoginName,p_Operation);
	}
	
	/**
	 * 描述：得到该工单的所有字段信息的 Hashtable 集合
	 * @param p_BaseSchema			该工单的类别名 Form名 
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @return 得到该工单的所有字段信息的 Hashtable 集合
	 */
	private boolean Init_Open_BaseAllFields(String p_BaseSchema,String p_BaseID,int p_Operation) {
		
		Hashtable_BaseAllFields = new HashMap();
		if (p_Operation == 1)
		{		
			Hashtable_BaseAllFields.put("BaseID", new BaseFieldInfo("BaseID","1",p_BaseID,4));
			Hashtable_BaseAllFields.put("BaseSchema", new BaseFieldInfo("BaseSchema","700000001",p_BaseSchema,4));
		}
		else
		{
			Hashtable_BaseAllFields.put("BaseSchema", new BaseFieldInfo("BaseSchema","700000001",null,4));
		}
		
//		select 
//			'Hashtable_BaseAllFields.put("' || t.fieldname || '", new BaseFieldInfo("' || t.fieldname || '","' || t.fieldid || '",null,' || t.datatype || '));' 
//			as dd  
//		from field t 
//		where 
//				t.schemaid=74 and t.datatype<20 and t.fieldid>20 
//				and t.fieldid not in (select BaseOwnFields.C650000003 from T107 BaseOwnFields) and t.fieldid <> '700000001'
//		order by t.fieldid
		
		Hashtable_BaseAllFields.put("tmp_BaseTplID", new BaseFieldInfo("tmp_BaseTplID","650000000",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplName", new BaseFieldInfo("tmp_BaseTplName","650000001",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplVersion", new BaseFieldInfo("tmp_BaseTplVersion","650000002",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplSchema", new BaseFieldInfo("tmp_BaseTplSchema","650000003",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplIsActive", new BaseFieldInfo("tmp_BaseTplIsActive","650000004",null,6));
		Hashtable_BaseAllFields.put("tmp_BaseTplStartDate", new BaseFieldInfo("tmp_BaseTplStartDate","650000005",null,7));
		Hashtable_BaseAllFields.put("tmp_BaseTplEndDate", new BaseFieldInfo("tmp_BaseTplEndDate","650000006",null,7));
		Hashtable_BaseAllFields.put("tmp_BaseTplAuthor", new BaseFieldInfo("tmp_BaseTplAuthor","650000007",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplAuthorID", new BaseFieldInfo("tmp_BaseTplAuthorID","650000008",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplCreateDate", new BaseFieldInfo("tmp_BaseTplCreateDate","650000009",null,7));
		Hashtable_BaseAllFields.put("tmp_BaseTplModifier", new BaseFieldInfo("tmp_BaseTplModifier","650000010",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplModifierID", new BaseFieldInfo("tmp_BaseTplModifierID","650000011",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseTplModifyDate", new BaseFieldInfo("tmp_BaseTplModifyDate","650000012",null,7));
		Hashtable_BaseAllFields.put("tmp_BaseTplDesc", new BaseFieldInfo("tmp_BaseTplDesc","650000013",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseSchema_SOURCE_ID", new BaseFieldInfo("tmp_BaseSchema_SOURCE_ID","650042003",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseSchema_SOURCE_GrandAction_ID", new BaseFieldInfo("tmp_BaseSchema_SOURCE_GrandAction_ID","650042004",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseSchema_SOURCE_GrandAction_IsHave", new BaseFieldInfo("tmp_BaseSchema_SOURCE_GrandAction_IsHave","650042005",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseSchema_SOURCE_UserID", new BaseFieldInfo("tmp_BaseSchema_SOURCE_UserID","650042006",null,4));
		
		
		Hashtable_BaseAllFields.put("BaseName", new BaseFieldInfo("BaseName","700000002",null,4));
		Hashtable_BaseAllFields.put("BaseSN", new BaseFieldInfo("BaseSN","700000003",null,4));
		Hashtable_BaseAllFields.put("BaseCreatorFullName", new BaseFieldInfo("BaseCreatorFullName","700000004",null,4));
		Hashtable_BaseAllFields.put("BaseCreatorLoginName", new BaseFieldInfo("BaseCreatorLoginName","700000005",null,4));
		Hashtable_BaseAllFields.put("BaseCreateDate", new BaseFieldInfo("BaseCreateDate","700000006",null,7));
		Hashtable_BaseAllFields.put("BaseSendDate", new BaseFieldInfo("BaseSendDate","700000007",null,7));
		Hashtable_BaseAllFields.put("BaseFinishDate", new BaseFieldInfo("BaseFinishDate","700000008",null,7));
		Hashtable_BaseAllFields.put("BaseCloseDate", new BaseFieldInfo("BaseCloseDate","700000009",null,7));
		Hashtable_BaseAllFields.put("BaseStatus", new BaseFieldInfo("BaseStatus","700000010",null,4));
		Hashtable_BaseAllFields.put("BaseAssigneeS", new BaseFieldInfo("BaseAssigneeS","700000012",null,4));
		Hashtable_BaseAllFields.put("BaseAuditingerS", new BaseFieldInfo("BaseAuditingerS","700000013",null,4));
		Hashtable_BaseAllFields.put("BaseResult", new BaseFieldInfo("BaseResult","700000020",null,4));
		Hashtable_BaseAllFields.put("BaseCloseSatisfy", new BaseFieldInfo("BaseCloseSatisfy","700000021",null,4));
		Hashtable_BaseAllFields.put("BaseTplID", new BaseFieldInfo("BaseTplID","700000022",null,4));
		
		Hashtable_BaseAllFields.put("tmp_OpDesc_ToAuditing", new BaseFieldInfo("tmp_OpDesc_ToAuditing","700005002",null,4));
		Hashtable_BaseAllFields.put("tmp_OpDesc_ToDeal", new BaseFieldInfo("tmp_OpDesc_ToDeal","700005003",null,4));
		Hashtable_BaseAllFields.put("tmp_IsHaveAssign", new BaseFieldInfo("tmp_IsHaveAssign","700005004",null,4));
		Hashtable_BaseAllFields.put("tmp_IsTransfer", new BaseFieldInfo("tmp_IsTransfer","700005005",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_BaseID", new BaseFieldInfo("tmp_Pro_BaseID","700020001",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_BaseSchema", new BaseFieldInfo("tmp_Pro_BaseSchema","700020002",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_PhaseNo", new BaseFieldInfo("tmp_Pro_PhaseNo","700020003",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_PrevPhaseNo", new BaseFieldInfo("tmp_Pro_PrevPhaseNo","700020004",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_Assginee", new BaseFieldInfo("tmp_Pro_Assginee","700020005",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_AssgineeID", new BaseFieldInfo("tmp_Pro_AssgineeID","700020006",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_Group", new BaseFieldInfo("tmp_Pro_Group","700020007",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_GroupID", new BaseFieldInfo("tmp_Pro_GroupID","700020008",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_Dealer", new BaseFieldInfo("tmp_Pro_Dealer","700020009",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_DealerID", new BaseFieldInfo("tmp_Pro_DealerID","700020010",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_Status", new BaseFieldInfo("tmp_Pro_Status","700020011",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_AssignOverTimeDate", new BaseFieldInfo("tmp_Pro_AssignOverTimeDate","700020012",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_AcceptOverTimeDate", new BaseFieldInfo("tmp_Pro_AcceptOverTimeDate","700020013",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_DealOverTimeDate", new BaseFieldInfo("tmp_Pro_DealOverTimeDate","700020014",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_StDate", new BaseFieldInfo("tmp_Pro_StDate","700020015",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_BgDate", new BaseFieldInfo("tmp_Pro_BgDate","700020016",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_EdDate", new BaseFieldInfo("tmp_Pro_EdDate","700020017",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_Desc", new BaseFieldInfo("tmp_Pro_Desc","700020018",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_FlagType", new BaseFieldInfo("tmp_Pro_FlagType","700020019",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_FlagActive", new BaseFieldInfo("tmp_Pro_FlagActive","700020020",null,2));
		Hashtable_BaseAllFields.put("tmp_Pro_FlagPredefined", new BaseFieldInfo("tmp_Pro_FlagPredefined","700020021",null,2));
		Hashtable_BaseAllFields.put("tmp_Pro_FlagDuplicated", new BaseFieldInfo("tmp_Pro_FlagDuplicated","700020022",null,2));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag01Assign", new BaseFieldInfo("tmp_Pro_Flag01Assign","700020023",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag02Copy", new BaseFieldInfo("tmp_Pro_Flag02Copy","700020024",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag03Assist", new BaseFieldInfo("tmp_Pro_Flag03Assist","700020025",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag04Transfer", new BaseFieldInfo("tmp_Pro_Flag04Transfer","700020026",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag05TurnDown", new BaseFieldInfo("tmp_Pro_Flag05TurnDown","700020027",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag06TurnUp", new BaseFieldInfo("tmp_Pro_Flag06TurnUp","700020028",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag07Recall", new BaseFieldInfo("tmp_Pro_Flag07Recall","700020029",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag08Cancel", new BaseFieldInfo("tmp_Pro_Flag08Cancel","700020030",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag09Close", new BaseFieldInfo("tmp_Pro_Flag09Close","700020031",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag15ToAuditing", new BaseFieldInfo("tmp_Pro_Flag15ToAuditing","700020032",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag20SideBySide", new BaseFieldInfo("tmp_Pro_Flag20SideBySide","700020033",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag30AuditingResult", new BaseFieldInfo("tmp_Pro_Flag30AuditingResult","700020034",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag31IsTransfer", new BaseFieldInfo("tmp_Pro_Flag31IsTransfer","700020035",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_TransferPhaseNo", new BaseFieldInfo("tmp_Pro_TransferPhaseNo","700020036",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_AuditingOverTimeDate", new BaseFieldInfo("tmp_Pro_AuditingOverTimeDate","700020037",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag16TurnUpAuditing", new BaseFieldInfo("tmp_Pro_Flag16TurnUpAuditing","700020038",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag17RecallAuditing", new BaseFieldInfo("tmp_Pro_Flag17RecallAuditing","700020039",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_AuditingWayPhaseNo", new BaseFieldInfo("tmp_Pro_AuditingWayPhaseNo","700020040",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_AuditingWayIsActive", new BaseFieldInfo("tmp_Pro_AuditingWayIsActive","700020041",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_AuditingWayNo", new BaseFieldInfo("tmp_Pro_AuditingWayNo","700020042",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_ProcessType", new BaseFieldInfo("tmp_Pro_ProcessType","700020043",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_LinkType", new BaseFieldInfo("tmp_Link_LinkType","700020044",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_CreateByUserID", new BaseFieldInfo("tmp_Pro_CreateByUserID","700020045",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag22IsSelect", new BaseFieldInfo("tmp_Pro_Flag22IsSelect","700020046",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Commissioner", new BaseFieldInfo("tmp_Pro_Commissioner","700020047",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_CommissionerID", new BaseFieldInfo("tmp_Pro_CommissionerID","700020048",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_IsGroupSnatch", new BaseFieldInfo("tmp_Pro_IsGroupSnatch","700020049",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_CloseBaseSamenessGroup", new BaseFieldInfo("tmp_Pro_CloseBaseSamenessGroup","700020050",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_CloseBaseSamenessGroupID", new BaseFieldInfo("tmp_Pro_CloseBaseSamenessGroupID","700020051",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag32IsToTransfer", new BaseFieldInfo("tmp_Pro_Flag32IsToTransfer","700020052",null,6));
		Hashtable_BaseAllFields.put("tmp_Pro_Flag33IsEndPhase", new BaseFieldInfo("tmp_Pro_Flag33IsEndPhase","700020053",null,6));
		Hashtable_BaseAllFields.put("tmp_ProcessLog_Act", new BaseFieldInfo("tmp_ProcessLog_Act","700020402",null,4));
		Hashtable_BaseAllFields.put("tmp_ProcessLog_StDate", new BaseFieldInfo("tmp_ProcessLog_StDate","700020405",null,7));
		Hashtable_BaseAllFields.put("tmp_ProcessLog_Result", new BaseFieldInfo("tmp_ProcessLog_Result","700020406",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_LinkID", new BaseFieldInfo("tmp_Link_LinkID","700020500",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_BaseID", new BaseFieldInfo("tmp_Link_BaseID","700020501",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_BaseSchema", new BaseFieldInfo("tmp_Link_BaseSchema","700020502",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_StartPhase", new BaseFieldInfo("tmp_Link_StartPhase","700020503",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_EndPhase", new BaseFieldInfo("tmp_Link_EndPhase","700020504",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_Desc", new BaseFieldInfo("tmp_Link_Desc","700020505",null,4));
		Hashtable_BaseAllFields.put("tmp_Link_Flag00IsAvail", new BaseFieldInfo("tmp_Link_Flag00IsAvail","700020506",null,6));
		Hashtable_BaseAllFields.put("tmp_Link_Flag21Required ", new BaseFieldInfo("tmp_Link_Flag21Required ","700020507",null,6));
		Hashtable_BaseAllFields.put("tmp_Link_AuditingWayIsActive", new BaseFieldInfo("tmp_Link_AuditingWayIsActive","700020508",null,6));
		Hashtable_BaseAllFields.put("tmp_Link_AuditingWayNo", new BaseFieldInfo("tmp_Link_AuditingWayNo","700020509",null,4));
		Hashtable_BaseAllFields.put("P_Others_Deal_SelectOpType", new BaseFieldInfo("P_Others_Deal_SelectOpType","700030027",null,6));
		Hashtable_BaseAllFields.put("P_Others_Deal_rad_DealRecallNewOp", new BaseFieldInfo("P_Others_Deal_rad_DealRecallNewOp","700030028",null,6));
		Hashtable_BaseAllFields.put("P_Others_Auditor_rad_AuditorRecallNewOp", new BaseFieldInfo("P_Others_Auditor_rad_AuditorRecallNewOp","700030029",null,6));
		Hashtable_BaseAllFields.put("P_Others_Auditor_OtherSelectOpType", new BaseFieldInfo("P_Others_Auditor_OtherSelectOpType","700030032",null,6));
		Hashtable_BaseAllFields.put("tmp_P_ToDeal_Selected_GroupName", new BaseFieldInfo("tmp_P_ToDeal_Selected_GroupName","700030048",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToDeal_Selected_UserFullName", new BaseFieldInfo("tmp_P_ToDeal_Selected_UserFullName","700030049",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToDeal_Selected_UserLoginName", new BaseFieldInfo("tmp_P_ToDeal_Selected_UserLoginName","700030050",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToDeal_Selected_GroupID", new BaseFieldInfo("tmp_P_ToDeal_Selected_GroupID","700030051",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToAuditor_Selected_GroupName", new BaseFieldInfo("tmp_P_ToAuditor_Selected_GroupName","700030052",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToAuditor_Selected_GroupID", new BaseFieldInfo("tmp_P_ToAuditor_Selected_GroupID","700030053",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToAuditor_Selected_UserFullName", new BaseFieldInfo("tmp_P_ToAuditor_Selected_UserFullName","700030054",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToAuditor_Selected_UserLoginName", new BaseFieldInfo("tmp_P_ToAuditor_Selected_UserLoginName","700030055",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToDeal_Selected_WebID", new BaseFieldInfo("tmp_P_ToDeal_Selected_WebID","700030083",null,4));
		Hashtable_BaseAllFields.put("tmp_P_To_Selected_Web_Tmp_Int0", new BaseFieldInfo("tmp_P_To_Selected_Web_Tmp_Int0","700030084",null,2));
		Hashtable_BaseAllFields.put("tmp_P_To_Selected_Web_Tmp_Int1", new BaseFieldInfo("tmp_P_To_Selected_Web_Tmp_Int1","700030085",null,2));
		Hashtable_BaseAllFields.put("tmp_P_To_Selected_WebID_ONE", new BaseFieldInfo("tmp_P_To_Selected_WebID_ONE","700030086",null,4));
		Hashtable_BaseAllFields.put("tmp_P_To_Selected_Name", new BaseFieldInfo("tmp_P_To_Selected_Name","700030087",null,4));
		Hashtable_BaseAllFields.put("tmp_P_To_Selected_ID", new BaseFieldInfo("tmp_P_To_Selected_ID","700030088",null,4));
		Hashtable_BaseAllFields.put("tmp_P_To_Selected_WebID_Tmp", new BaseFieldInfo("tmp_P_To_Selected_WebID_Tmp","700030089",null,4));
		Hashtable_BaseAllFields.put("tmp_P_ToAuditing_Selected_WebID", new BaseFieldInfo("tmp_P_ToAuditing_Selected_WebID","700030097",null,4));
		Hashtable_BaseAllFields.put("tmp_TurnUp_PrewAssign", new BaseFieldInfo("tmp_TurnUp_PrewAssign","700034001",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_ProcessID_Change", new BaseFieldInfo("tmp_Pro_ProcessID_Change","700034002",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_ProcessType_Change", new BaseFieldInfo("tmp_Pro_ProcessType_Change","700034003",null,4));
		Hashtable_BaseAllFields.put("tmp_IsNextAuditingPhaseNo", new BaseFieldInfo("tmp_IsNextAuditingPhaseNo","700034004",null,4));
		Hashtable_BaseAllFields.put("tmp_IsLive_AuditingWayPhaseNo", new BaseFieldInfo("tmp_IsLive_AuditingWayPhaseNo","700034005",null,4));
		Hashtable_BaseAllFields.put("tmp_IsLive_AuditingWayNotPassNo", new BaseFieldInfo("tmp_IsLive_AuditingWayNotPassNo","700034006",null,4));
		Hashtable_BaseAllFields.put("tmp_IsPrewPhaseNoBegin", new BaseFieldInfo("tmp_IsPrewPhaseNoBegin","700034007",null,4));
		Hashtable_BaseAllFields.put("tmp_IsDealFlagActive7", new BaseFieldInfo("tmp_IsDealFlagActive7","700034008",null,4));
		Hashtable_BaseAllFields.put("tmp_TurnUp_FlagActive02", new BaseFieldInfo("tmp_TurnUp_FlagActive02","700034009",null,4));
		Hashtable_BaseAllFields.put("tmp_Notice_ActionContent_Assign", new BaseFieldInfo("tmp_Notice_ActionContent_Assign","700034010",null,4));
		Hashtable_BaseAllFields.put("tmp_TurnUp_PrewAuditor", new BaseFieldInfo("tmp_TurnUp_PrewAuditor","700034011",null,4));
		Hashtable_BaseAllFields.put("tmp_IsDealTransferPhase", new BaseFieldInfo("tmp_IsDealTransferPhase","700034012",null,4));
		Hashtable_BaseAllFields.put("tmp_TurnUp_PrewDealOrAuditing", new BaseFieldInfo("tmp_TurnUp_PrewDealOrAuditing","700034013",null,4));
		Hashtable_BaseAllFields.put("tmp_IsNextAuditingPhaseNo2", new BaseFieldInfo("tmp_IsNextAuditingPhaseNo2","700034014",null,4));
		Hashtable_BaseAllFields.put("tmp_HastenProcess_Pro_Desc", new BaseFieldInfo("tmp_HastenProcess_Pro_Desc","700034015",null,4));
		Hashtable_BaseAllFields.put("tmp_TurnUp_FlagActive", new BaseFieldInfo("tmp_TurnUp_FlagActive","700034016",null,4));
		Hashtable_BaseAllFields.put("tmp_TurnUpProcess_Pro_Desc", new BaseFieldInfo("tmp_TurnUpProcess_Pro_Desc","700034017",null,4));
		Hashtable_BaseAllFields.put("tmp_RecallProcess_Pro_Desc", new BaseFieldInfo("tmp_RecallProcess_Pro_Desc","700034018",null,4));
		Hashtable_BaseAllFields.put("tmp_attach", new BaseFieldInfo("tmp_attach","700037014",null,11));
		Hashtable_BaseAllFields.put("tmp_UpFileName", new BaseFieldInfo("tmp_UpFileName","700037015",null,4));
		Hashtable_BaseAllFields.put("tmp_SaveFiled", new BaseFieldInfo("tmp_SaveFiled","700037016",null,4));
//		Hashtable_BaseAllFields.put("tmp_UserLoginName", new BaseFieldInfo("tmp_UserLoginName","700038001",null,4));
//		Hashtable_BaseAllFields.put("tmp_UserFullName", new BaseFieldInfo("tmp_UserFullName","700038002",null,4));
//		Hashtable_BaseAllFields.put("tmp_UserGroupList", new BaseFieldInfo("tmp_UserGroupList","700038003",null,4));
//		Hashtable_BaseAllFields.put("tmp_UserEmailAdd", new BaseFieldInfo("tmp_UserEmailAdd","700038004",null,4));
//		Hashtable_BaseAllFields.put("tmp_UserCloseBaseSamenessGroup", new BaseFieldInfo("tmp_UserCloseBaseSamenessGroup","700038005",null,4));
//		Hashtable_BaseAllFields.put("tmp_UserCloseBaseSamenessGroupID", new BaseFieldInfo("tmp_UserCloseBaseSamenessGroupID","700038006",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseCategorySchama", new BaseFieldInfo("tmp_BaseCategorySchama","700038021",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseCategoryName", new BaseFieldInfo("tmp_BaseCategoryName","700038022",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseCategoryCode", new BaseFieldInfo("tmp_BaseCategoryCode","700038023",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseCategoryIsFlow", new BaseFieldInfo("tmp_BaseCategoryIsFlow","700038024",null,6));
		Hashtable_BaseAllFields.put("tmp_BaseCategoryDayLastNo", new BaseFieldInfo("tmp_BaseCategoryDayLastNo","700038025",null,2));
		Hashtable_BaseAllFields.put("Tmp_BaseCategoryBtnIDS", new BaseFieldInfo("Tmp_BaseCategoryBtnIDS","700038026",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_ProcessID", new BaseFieldInfo("tmp_Pro_ProcessID","700038041",null,4));
		Hashtable_BaseAllFields.put("tmp_ExecuteSqlTabel", new BaseFieldInfo("tmp_ExecuteSqlTabel","700038091",null,4));
		Hashtable_BaseAllFields.put("tmp_ExecuteSql", new BaseFieldInfo("tmp_ExecuteSql","700038092",null,4));
		Hashtable_BaseAllFields.put("tmp_ExecuteSqlReturn", new BaseFieldInfo("tmp_ExecuteSqlReturn","700038093",null,4));
		Hashtable_BaseAllFields.put("tmp_ExecuteSqlTabelTop", new BaseFieldInfo("tmp_ExecuteSqlTabelTop","700038094",null,4));
		Hashtable_BaseAllFields.put("tmp_Select_Assigner_ProcessIDS", new BaseFieldInfo("tmp_Select_Assigner_ProcessIDS","700038101",null,4));
		Hashtable_BaseAllFields.put("tmp_Select_Assister_ProcessIDS", new BaseFieldInfo("tmp_Select_Assister_ProcessIDS","700038102",null,4));
		Hashtable_BaseAllFields.put("tmp_Select_Copyer_ProcessIDS", new BaseFieldInfo("tmp_Select_Copyer_ProcessIDS","700038103",null,4));
		Hashtable_BaseAllFields.put("tmp_Auditing_Select_Assigner_ProcessIDS", new BaseFieldInfo("tmp_Auditing_Select_Assigner_ProcessIDS","700038111",null,4));
		Hashtable_BaseAllFields.put("tmp_Auditing_Select_Assister_ProcessIDS", new BaseFieldInfo("tmp_Auditing_Select_Assister_ProcessIDS","700038112",null,4));
		Hashtable_BaseAllFields.put("tmp_Auditing_Select_Copyer_ProcessIDS", new BaseFieldInfo("tmp_Auditing_Select_Copyer_ProcessIDS","700038113",null,4));
		Hashtable_BaseAllFields.put("tmp_Select_Auditinger_ProcessIDS", new BaseFieldInfo("tmp_Select_Auditinger_ProcessIDS","700038121",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseActionBtn_Char", new BaseFieldInfo("tmp_BaseActionBtn_Char","700038131",null,4));
		Hashtable_BaseAllFields.put("tmp_WaitPro_PrevPhaseNo", new BaseFieldInfo("tmp_WaitPro_PrevPhaseNo","700038141",null,4));
		Hashtable_BaseAllFields.put("tmp_WaitPro_Status", new BaseFieldInfo("tmp_WaitPro_Status","700038142",null,4));
		Hashtable_BaseAllFields.put("tmp_WaitPro_StDate", new BaseFieldInfo("tmp_WaitPro_StDate","700038143",null,7));
		Hashtable_BaseAllFields.put("tmp_WaitPro_Desc", new BaseFieldInfo("tmp_WaitPro_Desc","700038144",null,4));
		Hashtable_BaseAllFields.put("tmp_WaitPro_FlagActive", new BaseFieldInfo("tmp_WaitPro_FlagActive","700038145",null,2));
		Hashtable_BaseAllFields.put("tmp_WaitPro_SelectCondition", new BaseFieldInfo("tmp_WaitPro_SelectCondition","700038146",null,4));
		Hashtable_BaseAllFields.put("tmp_WaitPro_AuditingWayPhaseNo", new BaseFieldInfo("tmp_WaitPro_AuditingWayPhaseNo","700038147",null,4));
		Hashtable_BaseAllFields.put("tmp_WaitPro_AuditingWayIsActive", new BaseFieldInfo("tmp_WaitPro_AuditingWayIsActive","700038148",null,6));
		Hashtable_BaseAllFields.put("tmp_WaitPro_AuditingWayNo", new BaseFieldInfo("tmp_WaitPro_AuditingWayNo","700038149",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_P_OpDeal_Desc", new BaseFieldInfo("tmp_BaseUser_P_OpDeal_Desc","700038199",null,4));
//		Hashtable_BaseAllFields.put("tmp_BaseUser_OpDesc_ToDeal", new BaseFieldInfo("tmp_BaseUser_OpDesc_ToDeal","700038221",null,4));
//		Hashtable_BaseAllFields.put("tmp_BaseUser_OpDesc_ToAuditor", new BaseFieldInfo("tmp_BaseUser_OpDesc_ToAuditor","700038222",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_ProcessID_Tmp", new BaseFieldInfo("tmp_Pro_ProcessID_Tmp","700038321",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_Status_Tmp", new BaseFieldInfo("tmp_Pro_Status_Tmp","700038322",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_StDate_Tmp", new BaseFieldInfo("tmp_Pro_StDate_Tmp","700038323",null,7));
		Hashtable_BaseAllFields.put("tmp_Pro_FlagActive_Tmp", new BaseFieldInfo("tmp_Pro_FlagActive_Tmp","700038324",null,2));
		Hashtable_BaseAllFields.put("tmp_Pro_Desc_Tmp", new BaseFieldInfo("tmp_Pro_Desc_Tmp","700038325",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_ProcessType_Tmp", new BaseFieldInfo("tmp_Pro_ProcessType_Tmp","700038326",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_P_Others_Deal_Desc", new BaseFieldInfo("tmp_BaseUser_P_Others_Deal_Desc","700038341",null,4));
		Hashtable_BaseAllFields.put("tmp_Select_OtherDeal_ProcessIDS", new BaseFieldInfo("tmp_Select_OtherDeal_ProcessIDS","700038371",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_P_Others_Auditing_Desc", new BaseFieldInfo("tmp_BaseUser_P_Others_Auditing_Desc","700038431",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_CloseOpSatisfaction", new BaseFieldInfo("tmp_BaseUser_CloseOpSatisfaction","700038471",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_P_OpClose_Desc", new BaseFieldInfo("tmp_BaseUser_P_OpClose_Desc","700038472",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_P_OpCancel_Desc", new BaseFieldInfo("tmp_BaseUser_P_OpCancel_Desc","700038481",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_P_OpConfirm_Desc", new BaseFieldInfo("tmp_BaseUser_P_OpConfirm_Desc","700038482",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_OpDesc_AuditingResult", new BaseFieldInfo("tmp_BaseUser_OpDesc_AuditingResult","700038501",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_P_OpAuditing_Desc", new BaseFieldInfo("tmp_BaseUser_P_OpAuditing_Desc","700038502",null,4));
		Hashtable_BaseAllFields.put("tmp_BaseUser_OpDesc_AuditingResult_Tmp", new BaseFieldInfo("tmp_BaseUser_OpDesc_AuditingResult_Tmp","700038511",null,4));
//		Hashtable_BaseAllFields.put("tmp_Auditor_Exist", new BaseFieldInfo("tmp_Auditor_Exist","700038801",null,4));
//		Hashtable_BaseAllFields.put("tmp_Is_Slove_Finish", new BaseFieldInfo("tmp_Is_Slove_Finish","700038802",null,4));
//		Hashtable_BaseAllFields.put("tmp_Is_Next_Finish", new BaseFieldInfo("tmp_Is_Next_Finish","700038809",null,4));
		Hashtable_BaseAllFields.put("tmp_Is_Another_Deal", new BaseFieldInfo("tmp_Is_Another_Deal","700038811",null,4));
		Hashtable_BaseAllFields.put("tmp_Previous_Desc_Storage", new BaseFieldInfo("tmp_Previous_Desc_Storage","700038812",null,4));
		Hashtable_BaseAllFields.put("tmp_ForDeleteData", new BaseFieldInfo("tmp_ForDeleteData","700038815",null,4));
		Hashtable_BaseAllFields.put("tmp_Next_Tache_NO", new BaseFieldInfo("tmp_Next_Tache_NO","700038820",null,2));
		Hashtable_BaseAllFields.put("tmp_Web_Url", new BaseFieldInfo("tmp_Web_Url","700038821",null,4));
		Hashtable_BaseAllFields.put("tmp_Pro_PrevPhaseNo_Finish", new BaseFieldInfo("tmp_Pro_PrevPhaseNo_Finish","700038822",null,4));
		Hashtable_BaseAllFields.put("tmp_Is_Next_OP_Deal", new BaseFieldInfo("tmp_Is_Next_OP_Deal","700038823",null,4));
		Hashtable_BaseAllFields.put("tmp_Is_Next_OP_Aduiting", new BaseFieldInfo("tmp_Is_Next_OP_Aduiting","700038824",null,4));
		Hashtable_BaseAllFields.put("tmp_Next_Change_Deal", new BaseFieldInfo("tmp_Next_Change_Deal","700038825",null,4));
		Hashtable_BaseAllFields.put("tmp_Next_Change_Aduiting", new BaseFieldInfo("tmp_Next_Change_Aduiting","700038826",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Deal", new BaseFieldInfo("tmp_RE_Deal","700038827",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Aduiting", new BaseFieldInfo("tmp_RE_Aduiting","700038828",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Dis_Deal", new BaseFieldInfo("tmp_RE_Dis_Deal","700038829",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Dis_Aduiting", new BaseFieldInfo("tmp_RE_Dis_Aduiting","700038830",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Dis_Deal_tmp", new BaseFieldInfo("tmp_RE_Dis_Deal_tmp","700038831",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Dis_Aduiting_tmp", new BaseFieldInfo("tmp_RE_Dis_Aduiting_tmp","700038832",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Deal_Tache", new BaseFieldInfo("tmp_RE_Deal_Tache","700038833",null,4));
//		Hashtable_BaseAllFields.put("tmp_RE_Aduiting_Tache", new BaseFieldInfo("tmp_RE_Aduiting_Tache","700038834",null,4));
		Hashtable_BaseAllFields.put("tmp_Deal_Length", new BaseFieldInfo("tmp_Deal_Length","700038835",null,4));
		Hashtable_BaseAllFields.put("tmp_Aduiting_Length", new BaseFieldInfo("tmp_Aduiting_Length","700038836",null,4));
		Hashtable_BaseAllFields.put("tmp_Notice_BaseAction", new BaseFieldInfo("tmp_Notice_BaseAction","700050006",null,4));
		Hashtable_BaseAllFields.put("tmp_Notice_ActionContent", new BaseFieldInfo("tmp_Notice_ActionContent","700050008",null,4));
		Hashtable_BaseAllFields.put("tmp_Notice_NoticeGroup", new BaseFieldInfo("tmp_Notice_NoticeGroup","700050011",null,4));
		Hashtable_BaseAllFields.put("tmp_Notice_NoticeGroupID", new BaseFieldInfo("tmp_Notice_NoticeGroupID","700050012",null,4));
		Hashtable_BaseAllFields.put("tmp_Notice_NoticeUser", new BaseFieldInfo("tmp_Notice_NoticeUser","700050013",null,4));
		Hashtable_BaseAllFields.put("tmp_Notice_NoticeUserID", new BaseFieldInfo("tmp_Notice_NoticeUserID","700050014",null,4));

		
    	return true;
	}	

	/**
	 * 描述：的到GUID
	 */
	private String Init_Get_GUID(String p_Guid_Top,int p_intSeed) {
		Thread Thread_currentThread = Thread.currentThread();
		Guid m_obj_Guid = new Guid(p_intSeed);
		Integer m_Intege_random 		= new Integer(m_obj_Guid.random(100000));
		Long 	m_Intege_currentTime 	= new Long(System.currentTimeMillis());
		String m_str_Guid_tmp_Pro_BaseID	= p_Guid_Top + "-" + m_Intege_currentTime.toString() + "-" + Thread_currentThread.getName() + "-" + m_Intege_random.toString();
		return m_str_Guid_tmp_Pro_BaseID;
	}

	protected boolean Init_BaseActionBtn(String _active,String _strBaseFieldDBName,boolean _flag){
		String str_tmp_Select_ProcessIDS = null;
		if (_strBaseFieldDBName != null)
		{
			BaseFieldInfo m_BaseFieldInfo_tmp_Select_ProcessIDS = null;
			m_BaseFieldInfo_tmp_Select_ProcessIDS = (BaseFieldInfo)Hashtable_BaseAllFields.get(_strBaseFieldDBName);
			str_tmp_Select_ProcessIDS = m_BaseFieldInfo_tmp_Select_ProcessIDS.getStrFieldValue();
		}
		if ((str_tmp_Select_ProcessIDS != null && !str_tmp_Select_ProcessIDS.equals("null") && !str_tmp_Select_ProcessIDS.equals(""))||_flag)
		{
			BaseFieldInfo m_BaseFieldInfo_tmp_BaseActionBtn_Char = null;
			m_BaseFieldInfo_tmp_BaseActionBtn_Char = (BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_BaseActionBtn_Char");
			m_BaseFieldInfo_tmp_BaseActionBtn_Char.setStrFieldValue(_active);	
		    return true;
		}
		else
		{
			Init_BaseWrite_Log(_active,1,_active+"失败，没有传递"+_active+"对象！");
			this.Init_Close_Base();	
			return false;	
		}		
	}
	
	/**
	 * 描述：得到当前时间
	 */
	private Long Init_Get_TIMESTAMP() {
		Long m_obj_Long_TIMESTAMP = new Long(System.currentTimeMillis()/1000);
		return m_obj_Long_TIMESTAMP;
	}
	
	
	/**
	 * 描述：记录日志
	 */
	protected void Init_BaseWrite_Log(String p_BaseAction,int p_Flag,String p_Memo) {
		System.out.println("-----------------------------------------");
		System.out.println("工单动作：" + p_BaseAction);
		String str_p_Flag = "";
		if (p_Flag == 1)
		{
			str_p_Flag = "错误！";
			String errInfo="\r\n工单动作:"+p_BaseAction +" 发生错误"+"\r\n信息描述：" + p_Memo;
			OperationLogFile.writeTxt(errInfo);
			
		}
		else
		{
			str_p_Flag = "成功！";
		}
		System.out.println("信息表示：" + str_p_Flag);
		System.out.println("信息描述：" + p_Memo);
		
	}
	public static void newAction(){
		//fanying 告警接口建单测试
		List<BaseFieldInfo> BaseNewFields = new ArrayList<BaseFieldInfo>();
		BaseFieldInfo baseFieldInfo_baseSummary = new BaseFieldInfo("BaseSummary","700000011",FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss")+" 接口建单 测试自由动作提交",4);
		BaseNewFields.add(baseFieldInfo_baseSummary);
		BaseFieldInfo baseFieldInfo_baseId = new BaseFieldInfo("BaseID","1","000000000021311",4);
		BaseNewFields.add(baseFieldInfo_baseId);
		BaseFieldInfo m_BaseItems = new BaseFieldInfo("BaseItems","700000014","专业",4); 
		BaseNewFields.add(m_BaseItems);
		BaseFieldInfo m_BasePriority = new BaseFieldInfo("INC_Province","800020021","宁夏",4); 
		BaseNewFields.add(m_BasePriority);
		BaseFieldInfo m_INC_City = new BaseFieldInfo("INC_City","800020022","地市",4); 
		BaseNewFields.add(m_INC_City);
		BaseFieldInfo m_BaseTaskType = new BaseFieldInfo("AlarmLevel","800020027","1",4); 
		BaseNewFields.add(m_BaseTaskType);
		BaseFieldInfo m_BaseDescrption = new BaseFieldInfo("INC_ResponseLevel","800020013","1",4); 
		BaseNewFields.add(m_BaseDescrption);
		
		BaseFieldInfo m_INC_HappenTime = new BaseFieldInfo("INC_HappenTime","800020020","1111111111",7); 
		BaseNewFields.add(m_INC_HappenTime);
		BaseFieldInfo m_INC_EquipmentManufacturer = new BaseFieldInfo("INC_EquipmentManufacturer","800020017","厂商",4); 
		BaseNewFields.add(m_INC_EquipmentManufacturer);
		BaseFieldInfo m_INC_AssignType = new BaseFieldInfo("INC_AssignType","800020015","接口1",4); 
		BaseNewFields.add(m_INC_AssignType);
		BaseFieldInfo m_INC_FindQuomodo = new BaseFieldInfo("INC_FindQuomodo","800020012","接口1",4); 
		BaseNewFields.add(m_INC_FindQuomodo);
		BaseFieldInfo m_Is_HaveDeal = new BaseFieldInfo("Is_HaveDeal","800020045","否",4); 
		BaseNewFields.add(m_Is_HaveDeal);
		BaseFieldInfo m_INC_IsEffectOP = new BaseFieldInfo("INC_IsEffectOP","800020023","否",4); 
		BaseNewFields.add(m_INC_IsEffectOP);
		
//		BaseNewFields.add(new BaseFieldInfo("tmp_INC_T1_ToUser","800045107","Demo",4));
//		BaseNewFields.add(new BaseFieldInfo("tmp_INC_T1_ToUserID","800045108","Demo",4));
//		BaseNewFields.add(new BaseFieldInfo("tmp_INC_T1_ToGroup","800045109","",4));
//		BaseNewFields.add(new BaseFieldInfo("tmp_INC_T1_ToGroupID","800045110","",4));
		
		NMSAlarmInterface nmsAlarmInter = new NMSAlarmInterface(); 
		//List<Map<String,String>> fieldlist = nmsAlarmInter.getBasefieldInfo(Contents.NEWALARM, Contents.ACTION);
		//新建
		Action_New action_New = new Action_New(); 
		boolean isSuccess = action_New.do_Action("ymtest", "WF:EL_TTM_TTH" ,null, BaseNewFields, null);
		//派发
		List p_BaseToDealObject = new ArrayList();
		BaseToDealObject baseToDealObject = new BaseToDealObject();
		baseToDealObject.setAssginee("Demo");
		baseToDealObject.setAssgineeID("Demo");
		baseToDealObject.setGroup("");
		baseToDealObject.setGroupID("");
		baseToDealObject.setAssignOverTimeDate(FormatTime.FormatStringToDate("2013-07-25 11:00:17"));
		baseToDealObject.setDealOverTimeDate(FormatTime.FormatStringToDate("2013-07-25 11:00:17"));
		baseToDealObject.setAcceptOverTimeDate(FormatTime.FormatStringToDate("2013-07-25 11:00:17"));
		baseToDealObject.setFlag01Assign(1);
		baseToDealObject.setFlagType(0);		
		baseToDealObject.setFlag02Copy(1);
		baseToDealObject.setFlag03Assist(1);
		baseToDealObject.setFlag04Transfer(1);
		baseToDealObject.setFlag05TurnDown(1);
		baseToDealObject.setFlag06TurnUp(1);
		baseToDealObject.setFlag07Recall(1); 
		baseToDealObject.setFlag08Cancel(1);
		baseToDealObject.setFlag09Close(1);
		baseToDealObject.setFlag15ToAuditing(1);
		baseToDealObject.setFlag31IsTransfer(0);		
		
		p_BaseToDealObject.add(baseToDealObject);			
		//Action_Auditing action = new Action_Auditing();
		//flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, 2, p_BaseToDealObject, bizFields, null);
		//Action_ToAuditing action = new Action_ToAuditing();
		//action.do_Action("Demo", "WF:EL_TTM_TTH", "000000000021323", "000000000161963", null, p_BaseToDealObject, BaseNewFields, null);
//		Action_Next action_Next = new Action_Next();
//		Action_NextBaseCustom action_Next_Custom = new Action_NextBaseCustom();
//		action_Next_Custom.do_Action(
//				"Demo", 
//				"WF:EL_TTM_TTH", 
//				"000000000021319", 
//				null, 
//				null, 
//				"移交处理",
//				BaseNewFields, 
//				null);
		
		//boolean flag = action_Next.do_Action("Demo", "WF:EL_TTM_TTH", "000000000021318", "", "", BaseNewFields, new ArrayList());
		//.do_Action("Demo", "WF:EL_TTM_TTH", "000000000021313", "", "", p_BaseToDealObject, BaseNewFields, new ArrayList());
		//System.out.println("flag:"+flag);
		//BaseActionOp.Action_ToDeal("Demo","WF:EL_UVS_TSK","000000000021311","派发描述",p_BaseToDealObject,new ArrayList());
		//end
	}
/*	public static void newAction() {
		// TODO Auto-generated method stub
		ConstantsManager m_ConstantsManager = new ConstantsManager(Constants.filePath
				);
		m_ConstantsManager.getConstantInstance();	
		
		BaseAction BaseActionOp = new BaseAction();
		//BaseActionOp.getInstance();
		
		List BaseNewFields = new ArrayList();
		
		//if(BaseActionOp.Action_Start("liuliwei","WF:EL_UVS_TSK","000000000000020",new ArrayList()))
		
		if(BaseActionOp.Action_Finish("Demo","WF:EL_UVS_TSK","000000000000020","完成描述",new ArrayList(),new ArrayList()))
		{
			System.out.println("完成");
		}
		
		BaseFieldInfo m_BaseSummary = new BaseFieldInfo("BaseSummary","700000011","接口测试转派",4); 
		BaseNewFields.add(m_BaseSummary);
		BaseFieldInfo m_BaseItems = new BaseFieldInfo("BaseItems","700000014","专业",4); 
		BaseNewFields.add(m_BaseItems);
		BaseFieldInfo m_BasePriority = new BaseFieldInfo("BasePriority","700000015","一般",4); 
		BaseNewFields.add(m_BasePriority);
		BaseFieldInfo m_BaseAcceptOutTime = new BaseFieldInfo("BaseAcceptOutTime","700000017","1111111111",7); 
		BaseNewFields.add(m_BaseAcceptOutTime);		
		BaseFieldInfo m_BaseDealOutTime = new BaseFieldInfo("BaseDealOutTime","700000018","1111111111",7); 
		BaseNewFields.add(m_BaseDealOutTime);
		BaseFieldInfo m_BaseIsAllowLogGroup = new BaseFieldInfo("BaseIsAllowLogGroup","700000016","1",6); 
		BaseNewFields.add(m_BaseIsAllowLogGroup);
		BaseFieldInfo m_BaseDescrption = new BaseFieldInfo("BaseDescrption","700000019","哈哈哈！！！！！",4); 
		BaseNewFields.add(m_BaseDescrption);
		BaseFieldInfo m_BaseTaskType = new BaseFieldInfo("BaseTaskType","800020001","其他其他其他",4); 
		BaseNewFields.add(m_BaseTaskType);

	   String BaseId = BaseActionOp.Action_New("Demo","WF:EL_UVS_TSK",BaseNewFields,new ArrayList());
	   if (BaseId==null)
		{
			System.out.println("No:4505 新建失败！");
		}
		else
		  {  
		 	//分派
			System.out.println("新建成功！");
			
			List p_BaseToDealObject = new ArrayList();
			BaseToDealObject baseToDealObject = new BaseToDealObject();
			baseToDealObject.setAssginee("黄维");
			baseToDealObject.setAssgineeID("Demo");
			baseToDealObject.setGroup("");
			baseToDealObject.setGroupID("");
			baseToDealObject.setAssignOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
			baseToDealObject.setDealOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
			baseToDealObject.setAcceptOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
			baseToDealObject.setFlag01Assign(1);
			baseToDealObject.setFlagType(0);		
			baseToDealObject.setFlag02Copy(1);
			baseToDealObject.setFlag03Assist(1);
			baseToDealObject.setFlag04Transfer(1);
			baseToDealObject.setFlag05TurnDown(1);
			baseToDealObject.setFlag06TurnUp(1);
			baseToDealObject.setFlag07Recall(1);
			baseToDealObject.setFlag08Cancel(1);
			baseToDealObject.setFlag09Close(1);
			baseToDealObject.setFlag15ToAuditing(1);
			baseToDealObject.setFlag31IsTransfer(0);		
			
			p_BaseToDealObject.add(baseToDealObject);			
			
			baseToDealObject = new BaseToDealObject();
			baseToDealObject.setAssginee("李庄");
			baseToDealObject.setAssgineeID("Demo");
			baseToDealObject.setGroup("");
			baseToDealObject.setGroupID("");
			baseToDealObject.setAssignOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
			baseToDealObject.setDealOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
			baseToDealObject.setAcceptOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
			baseToDealObject.setFlag01Assign(0);
			baseToDealObject.setFlagType(0);		
			baseToDealObject.setFlag02Copy(1);
			baseToDealObject.setFlag03Assist(0);
			baseToDealObject.setFlag04Transfer(0);
			baseToDealObject.setFlag05TurnDown(0);
			baseToDealObject.setFlag06TurnUp(0);
			baseToDealObject.setFlag07Recall(0);
			baseToDealObject.setFlag08Cancel(0);
			baseToDealObject.setFlag09Close(0);
			baseToDealObject.setFlag15ToAuditing(0);
			baseToDealObject.setFlag31IsTransfer(0);		
			
			p_BaseToDealObject.add(baseToDealObject);
			
			if(BaseActionOp.Action_ToDeal("Demo","WF:EL_UVS_TSK",BaseId,"派发描述",p_BaseToDealObject,new ArrayList()))
			
			{	
				System.out.println("派发成功!\n");
                //确认抄送

				if(BaseActionOp.Action_Confirm("Demo","WF:EL_UVS_TSK",BaseId,"确认抄送描述",new ArrayList(),new ArrayList())){
					System.out.println("确认抄送成功！\n");						
				}else{
					System.out.println("确认抄送失败！\n");
				}
				
				//驳回							
				List p_BaseDealObject = new ArrayList();
				BaseDealObject baseDealObject = new BaseDealObject();
				baseDealObject.setAssginee("系统管理员");
				baseDealObject.setAssgineeID("Demo");
				baseDealObject.setGroup("");
				baseDealObject.setGroupID("");
			
				p_BaseDealObject.add(baseDealObject);
				
				if(BaseActionOp.Action_DealTurnUp("huangwei","WF:EL_UVS_TSK",BaseId,"驳回描述",p_BaseDealObject,new ArrayList())){
					System.out.println("驳回成功!\n");
					
					if (BaseActionOp.Action_ToDeal("Demo","WF:EL_UVS_TSK",BaseId,"派发描述",p_BaseToDealObject,new ArrayList()))
					{			
						System.out.println("派发成功!\n");
						
						//催办
						List p_BaseDealObjectcb = new ArrayList();
						baseDealObject = new BaseDealObject();
						baseDealObject.setAssginee("黄维");
						baseDealObject.setAssgineeID("Demo");
						baseDealObject.setGroup("");
						baseDealObject.setGroupID("");
					
						p_BaseDealObjectcb.add(baseDealObject);
						if(BaseActionOp.Action_DealHasten("Demo","WF:EL_UVS_TSK",BaseId,"催办描述",p_BaseDealObjectcb,new ArrayList())){
							System.out.println("催办成功！\n");						
						}else{
							System.out.println("催办失败！\n");
						}				
						//受理,阶段回复,完成
						
						if(BaseActionOp.Action_Start("huangwei","WF:EL_UVS_TSK",BaseId,new ArrayList()))
						{
							System.out.println("受理成功！\n");					
							
							if(BaseActionOp.Action_PhaseNotice("huangwei","WF:EL_UVS_TSK",BaseId,"阶段回复描述",new ArrayList(),new ArrayList())){
								System.out.println("阶段回复成功！\n");	
								if(BaseActionOp.Action_Finish("huangwei","WF:EL_UVS_TSK",BaseId,"完成描述",new ArrayList(),new ArrayList())){
									System.out.println("完成成功！\n");			
									//退回
									
									List p_BaseDealObjectth = new ArrayList();
									baseDealObject = new BaseDealObject();
									baseDealObject.setAssginee("黄维");
									baseDealObject.setAssgineeID("Demo");
									baseDealObject.setGroup("");
									baseDealObject.setGroupID("");
								
									p_BaseDealObjectth.add(baseDealObject);
									if(BaseActionOp.Action_DealTurnDown("Demo","WF:EL_UVS_TSK",BaseId,"退回描述",p_BaseDealObjectth,new ArrayList())){
										System.out.println("退回成功！\n");	
										if(BaseActionOp.Action_Finish("huangwei","WF:EL_UVS_TSK",BaseId,"完成描述1",new ArrayList(),new ArrayList())
												){
											System.out.println("完成成功！\n");	
											if(BaseActionOp.Action_Close("Demo","WF:EL_UVS_TSK",BaseId,"关闭描述",BaseId, new ArrayList(), p_BaseDealObjectth)){
												System.out.println("关闭成功！\n");													
											}else{
												System.out.println("关闭失败！\n");
											}
											if(BaseActionOp.Action_Cancel("Demo","WF:EL_UVS_TSK",BaseId,"作废描述",new ArrayList())){
												System.out.println("作废成功！\n");													
											}else{
												System.out.println("作废失败！\n"); 
											}
										}else{
											System.out.println("完成失败！\n");
										}
									}else{
										System.out.println("退回失败！\n");	 							
									}							
								}else{
									System.out.println("完成失败！\n");
								}
							}else{
								System.out.println("阶段回复失败！\n");							
							}
						}else{
							System.out.println("受理失败！\n");					
						}				
					}else
					{
						System.out.println("派发失败！\n");
					}
				}else{
					System.out.println("驳回失败成功!\n");
				}
			}else
			{
				System.out.println("派发失败！\n");
			}
			
			if (BaseActionOp.Action_ToDeal("Demo","WF:EL_UVS_TSK",BaseId,"派发描述",p_BaseToDealObject,new ArrayList()))
			{	
				System.out.println("派发成功!\n");
				//追回
				List p_BaseDealObject = new ArrayList();
				BaseDealObject baseDealObject = new BaseDealObject();
				baseDealObject.setAssginee("黄维");
				baseDealObject.setAssgineeID("Demo");
				baseDealObject.setGroup("");
				baseDealObject.setGroupID("");
			
				p_BaseDealObject.add(baseDealObject);
				
				baseDealObject = new BaseDealObject();
				baseDealObject.setAssginee("李庄");
				baseDealObject.setAssgineeID("Demo");
				baseDealObject.setGroup("");
				baseDealObject.setGroupID("");
			
				p_BaseDealObject.add(baseDealObject);
				if(BaseActionOp.Action_DealRecall("Demo","WF:EL_UVS_TSK",BaseId,"追回描述",0,p_BaseDealObject,new ArrayList())){
					System.out.println("追回成功!\n");
				}else{
					System.out.println("追回失败!\n");
				}
			}
			else
			{
				System.out.println("派发失败！\n");
			}	
			
			if (BaseActionOp.Action_ToDeal("Demo","WF:EL_UVS_TSK",BaseId,"派发描述",p_BaseToDealObject,new ArrayList()))
				{	
					System.out.println("派发成功!\n");
				    
				}
				else
				{
					System.out.println("派发失败！\n");
				}		
	
	   } 
	    List p_BaseToDealObject = new ArrayList();
	    BaseToDealObject baseToDealObject = new BaseToDealObject();
		baseToDealObject.setAssginee("李庄");
		baseToDealObject.setAssgineeID("Demo");
		baseToDealObject.setGroup("");
		baseToDealObject.setGroupID("");
		baseToDealObject.setAssignOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
		baseToDealObject.setDealOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
		baseToDealObject.setAcceptOverTimeDate(FormatTime.FormatStringToDate("2006-11-25 11:00:17"));
		baseToDealObject.setFlag01Assign(1);
		baseToDealObject.setFlagType(0);		
		baseToDealObject.setFlag02Copy(1);
		baseToDealObject.setFlag03Assist(1);
		baseToDealObject.setFlag04Transfer(1);
		baseToDealObject.setFlag05TurnDown(1);
		baseToDealObject.setFlag06TurnUp(1);
		baseToDealObject.setFlag07Recall(1);
		baseToDealObject.setFlag08Cancel(1);
		baseToDealObject.setFlag09Close(1);
		baseToDealObject.setFlag15ToAuditing(1);
		baseToDealObject.setFlag31IsTransfer(0);			
		p_BaseToDealObject.add(baseToDealObject);
		
			   if(BaseActionOp.Action_ToDeal_FormGivenUser("Demo","000000000004097","DEAL","WF:EL_UVS_TSK","000000000001164","转派描述",p_BaseToDealObject,new ArrayList())) 
		{	
			System.out.println("转派成功!\n");
		    
		}
		else
		{
			System.out.println("转派失败！\n");
		}			
     }*/
 	

	
	public void ddd(Hashtable Hashtable_BaseAllFields)
	{
		String   key;	
		for(Iterator it=Hashtable_BaseAllFields.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)   it.next();
	        BaseFieldInfo m_BaseFieldInfo = (BaseFieldInfo)Hashtable_BaseAllFields.get(key);
	        System.out.println(key + " : "+m_BaseFieldInfo.getStrFieldName() + " = " + m_BaseFieldInfo.getStrFieldValue());
		}
	}
}
