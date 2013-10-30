package cn.com.ultrapower.ultrawf.control.process;

import java.util.Hashtable;
import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.config.ParBaseOwnFieldInfoModel;

public class BaseActionNoInitProcess extends BaseAction{

	public BaseActionNoInitProcess(){}
	

	
	/**
	 * 工单动作：受理处理工单函数

	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)

	 * @return 返回是否成功
	 */
	public boolean Action_Start_NoInitProcess(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType,List p_BaseAttachmentList) {
		//工单信息初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"受理")==false)
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealPhaseNoticeDesc			该工单动作操作时的内容
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList	    	附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_PhaseNotice_NoInitProcess(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealPhaseNoticeDesc,List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_DealPhaseNoticeDesc == null){
			Init_BaseWrite_Log("阶段回复",1,"阶段回复,没有写描述！");
			return false;
		}
//		工单信息初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"阶段回复")==false)
		{
			Init_BaseWrite_Log("阶段回复",1,"初始化失败！");
			return false;	
		}	
		
//		阶段回复时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Hashtable Hashtable_BaseOwnAllFields = new Hashtable();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("待处理;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("待处理;")>-1 
					)
					|| 
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("处理中;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("处理中;")>-1 
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealFinishDesc				该工单动作操作时的内容

	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Finish_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealFinishDesc,
			List p_FieldListInfo,List p_BaseAttachmentList) {		
		if (p_DealFinishDesc == null){
			Init_BaseWrite_Log("完成",1,"完成,没有写描述！");
			return false;
		}

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"完成")==false)
		{
			Init_BaseWrite_Log("完成",1,"初始化失败！");
			return false;	
		}	
		
//		完成时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Hashtable Hashtable_BaseOwnAllFields = new Hashtable();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("待处理;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("待处理;")>-1 
					)
					|| 
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("处理中;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("处理中;")>-1 
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealConfirmDesc				该工单动作操作时的内容

	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)

	 * @return 返回是否成功
	 */
	public boolean Action_Confirm_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType,String p_DealConfirmDesc,
			List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_DealConfirmDesc == null){
			Init_BaseWrite_Log("确认抄送",1,"确认抄送，没有写描述！");
			return false;
		}

		//操作工单信息初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"确认")==false)
		{   
			Init_BaseWrite_Log("确认",1,"“确认”初始化失败！");
			return false;	
		}	
		
//		确认时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Hashtable Hashtable_BaseOwnAllFields = new Hashtable();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("待处理;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("待处理;")>-1 
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealConfirmDesc				该工单动作操作时的内容
	 * @param p_BaseDealObject				崔办对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		    附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealHasten_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealHastenDesc,
			List p_BaseDealObject,List p_BaseAttachmentList) {
		if (p_DealHastenDesc == null){
			Init_BaseWrite_Log("催办",1,"催办，没有写描述！");
			return false;
		}	
		//工单信息初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"催办")==false)
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealTurnDownDesc			该工单动作操作时的内容
	 * @param p_BaseDealObject				退回对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealTurnDown_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealTurnDownDesc,
			List p_BaseDealObject,List p_BaseAttachmentList) {
		if (p_DealTurnDownDesc == null){
			Init_BaseWrite_Log("退回",1,"退回，没有写描述！");
			return false;
		}		
		//操作工单信息初始化		
		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"退回")==false)
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealTurnUpDesc				该工单动作操作时的内容
	 * @param p_BaseDealObject				驳回对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealTurnUp_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealTurnUpDesc,
			List p_BaseDealObject,List p_BaseAttachmentList) {
		
		if (p_DealTurnUpDesc == null){
			Init_BaseWrite_Log("驳回",1,"请插入驳回描述！");
			return false;
		}
		
		//操作工单初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"驳回")==false)
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealRecallDesc				该工单动作操作时的内容
     * @param _Auditor_rad_AuditorRecallNewOp 追回是否从派
	 * @param p_BaseDealObject				追回对象的List(BaseDealObject类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	
	public boolean Action_DealRecall_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealRecallDesc,
			int P_Others_Deal_rad_DealRecallNewOp,List p_BaseDealObject,List p_BaseAttachmentList) {
		BaseFieldInfo baseFieldInfo  = null;
		if (p_DealRecallDesc == null){
			Init_BaseWrite_Log("追回",1,"追回，没有写描述！");
			return false;
		}		
				
		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"追回")==false)
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealCloseDesc				该工单动作操作时的内容
	 * @param p_CloseSatisfaction           归档满意度		
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		    附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Close_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealCloseDesc,
			String p_CloseSatisfaction,
			List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_DealCloseDesc == null){
			Init_BaseWrite_Log("关闭",1,"关闭，没有写描述！");
			return false;
		}
		//操作工单信息初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"关闭")==false)
		{
			Init_BaseWrite_Log("关闭",1,"初始化失败！");
			return false;	
		}	
		
//		关闭时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Hashtable Hashtable_BaseOwnAllFields = new Hashtable();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("已完成;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("已完成;")>-1 
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealCancelDesc				该工单动作操作时的内容
	 * @param p_BaseAttachmentList		    附件对象的List(BaseAttachment类的对象的数组)

	 * @return 返回是否成功
	 */
	public boolean Action_Cancel_NoInitProcess(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_DealCancelDesc,List p_BaseAttachmentList) {
		if (p_DealCancelDesc == null){
			Init_BaseWrite_Log("作废",1,"作废，没有写描述！");
			return false;
		}
		
		//操作工单初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"作废")==false)
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
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_DealPhaseNoticeDesc			该工单动作操作时的内容
	 * @param p_AuditingResult              审批结果
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList	    	附件对象的List(BaseAttachment类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Auditing_NoInitProcess(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID,
			String p_processId,String p_processType, String p_AuditingDesc,String p_AuditingResult,List p_FieldListInfo,List p_BaseAttachmentList) {
		if (p_AuditingDesc == null){
			Init_BaseWrite_Log("审批",1,"审批,没有写描述！");
			return false;
		}
		if (p_AuditingResult == null){
			Init_BaseWrite_Log("审批",1,"审批,没有选择审批结果！");
			return false;
		}
//		工单信息初始化

		if (this.Init_Open_Base_FormGivenUser(p_BaseSchema,p_BaseID,p_processId,p_processType,p_OperateUserLoginName,1,"审批")==false)
		{
			Init_BaseWrite_Log("审批",1,"初始化失败！");
			return false;	
		}	
		
//		审批时显示或编辑的字段初始化
		ParBaseOwnFieldInfoModel obj_ParBaseOwnFieldInfoModel = new ParBaseOwnFieldInfoModel();
		obj_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
		BaseOwnFieldInfo obj_BaseOwnFieldInfo = new BaseOwnFieldInfo();
		List List_BaseOwnFieldInfoGet = obj_BaseOwnFieldInfo.GetList(obj_ParBaseOwnFieldInfoModel,0,0);
		
		Hashtable Hashtable_BaseOwnAllFields = new Hashtable();
		for (int i=0;i<List_BaseOwnFieldInfoGet.size();i++)
		{
			BaseOwnFieldInfoModel obj_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)List_BaseOwnFieldInfoGet.get(i);
			if (
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("待审批;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("待审批;")>-1 
					)
					|| 
					(
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_ShowStep().indexOf("审批中;")>-1 
							&&
							obj_BaseOwnFieldInfoModel.GetBaseFree_field_EditStep().indexOf("审批中;")>-1 
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
}
