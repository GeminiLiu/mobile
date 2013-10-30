package cn.com.ultrapower.ultrawf.control.process.baseaction;

import java.util.Hashtable;
import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.BasePerformRoleManage;
//import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseDealObject;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
//import cn.com.ultrapower.ultrawf.control.process.bean.BaseToDealObject;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseDealObject;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseToDealObject;
import cn.com.ultrapower.ultrawf.models.process.BasePerformRoleModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.eoms.util.StringUtil;
import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.system.util.FormatTime;

public abstract class Action_Base  extends BaseProcessObject {

	protected String m_Action;
	protected String m_BaseID;
	protected String m_Pro_ProcessID;
	protected String m_Pro_PhaseNo;
	protected String m_Pro_ProcessType;
	
	public void setM_BaseID(String m_BaseID) {
		this.m_BaseID = m_BaseID;
	}

	public String getM_BaseID() {
		return m_BaseID;
	}

	public String getM_Pro_PhaseNo()
	{
		return m_Pro_PhaseNo;
	}

	public void setM_Pro_PhaseNo(String pro_PhaseNo)
	{
		m_Pro_PhaseNo = pro_PhaseNo;
	}

	public String getM_Pro_ProcessID()
	{
		return m_Pro_ProcessID;
	}

	public void setM_Pro_ProcessID(String pro_ProcessID)
	{
		m_Pro_ProcessID = pro_ProcessID;
	}

	public String getM_Pro_ProcessType()
	{
		return m_Pro_ProcessType;
	}

	public void setM_Pro_ProcessType(String pro_ProcessType)
	{
		m_Pro_ProcessType = pro_ProcessType;
	}

	public boolean Init_Action(
						String p_BaseSchema,
						String p_BaseID,
						String p_TplBaseID,						
						String p_ProcessID,
						String p_ProcessType,
						String p_strUserLoginName,
						int p_Operation,
						String p_Action,
						String p_ActionCustom)
	{
		Init_Base m_Init_Base = new Init_Base();
		if (!(m_Init_Base.Init_Open_Base(p_BaseSchema, p_BaseID,p_TplBaseID,p_ProcessID, p_ProcessType, p_strUserLoginName, p_Operation)))
		{
			BaseWrite_Log.writeActionLog(p_Action,1,"初始化失败！");
			return false;
		}
		this.Hashtable_BaseAllFields = m_Init_Base.Hashtable_BaseAllFields;
		if (!(Action_InitPermission(p_Action,p_ActionCustom, p_Operation)))
		{
			BaseWrite_Log.writeActionLog(p_Action,1,"该用户没有权限操作该动作！");
			return false;
		}
		m_Pro_ProcessID = this.getFieldValue("tmp_Pro_ProcessID");
		m_Pro_PhaseNo = this.getFieldValue("tmp_Pro_PhaseNo");
		m_Pro_ProcessType = this.getFieldValue("tmp_Pro_ProcessType");
		return true;
	}

	protected boolean Action_InitPermission(String p_Action,String p_ActionCustom,int p_Operation)
	{
		String m_str_ActionError_memo 	= "（"+p_Action+"）动作控制失败！不能"+p_Action+"！";
		//
		if (p_Action.equals("新建"))
		{
			return true;			
		}	
		
		//会审：权限判断
		//( 'tmp_Pro_FlagType' = "5") AND ( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_ProcessType' =  "AUDITING" )
		if (p_Action.equals("会审"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagType").equals("5") && getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("AUDITING")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//审批：权限判断
		//( 'tmp_Pro_FlagType' = "3") AND ( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_ProcessType' =  "AUDITING" )
		if (p_Action.equals("审批"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagType").equals("3") && getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("AUDITING")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//审批驳回：权限判断
		//( 'tmp_Pro_FlagType' = "3") AND ( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag16TurnUpAuditing' = "1") AND ( 'tmp_Pro_AuditingWayIsActive' = "1") AND ( 'tmp_Pro_ProcessType' =  "AUDITING" )
		if (p_Action.equals("审批驳回"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagType").equals("3") && getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("AUDITING")
					&& 
					getFieldValue("tmp_Pro_Flag16TurnUpAuditing").equals("1") && getFieldValue("tmp_Pro_AuditingWayIsActive").equals("1")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//作废：权限判断
		//( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag08Cancel' = "1")
		if (p_Action.equals("作废"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_Flag08Cancel").equals("1")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//关闭：权限判断
		//( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag09Close' = "1") AND ( 'tmp_Pro_ProcessType' =  "DEAL" ) AND (( 'BaseStatus' =  "已完成" ) OR ( 'BaseStatus' =  "审批通过" ) OR ( 'BaseStatus' =  "草稿" ))
		if (p_Action.equals("归档"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
					&& 
					getFieldValue("tmp_Pro_Flag09Close").equals("1") 
					&& 
					(
						getFieldValue("BaseStatus").equals("已完成")
						||
						getFieldValue("BaseStatus").equals("审批通过")
						||
						getFieldValue("BaseStatus").equals("草稿")
					)
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//确认：权限判断
		//( 'tmp_Pro_FlagType' = "2") AND ( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("确认"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagType").equals("2") && getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//建其他流程：权限判断
		//( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag35IsCanCreateBase' = 1)
		if (p_Action.equals("建其他流程"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_Flag35IsCanCreateBase").equals("1")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//固定追回：权限判断
		//( 'tmp_Pro_FlagPredefined' = 1) AND ( 'tmp_Pro_FlagActive' = 0) AND ( 'tmp_Pro_Flag07Recall' = "1") AND ( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("固定追回"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagPredefined").equals("1") && getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_Flag07Recall").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//固定驳回：权限判断
		//( 'tmp_Pro_FlagPredefined' = 1) AND ( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag06TurnUp' = "1") AND ( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("固定驳回"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagPredefined").equals("1") && getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_Flag06TurnUp").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//催办：权限判断
		//( 'BaseStatusCode' !=  "10" ) AND ( 'tmp_Pro_ProcessType' =  "DEAL" ) AND ( 'tmp_Pro_FlagActive' != 1)
		if (p_Action.equals("催办"))
		{
			if (						
					!(getFieldValue("BaseStatusCode").equals("10")) && getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//自由追回：权限判断
		//	( 'tmp_Pro_FlagPredefined' = 0) AND ( 'BaseStatusCode' !=  "10" ) 
		//	AND 
		//	(
		//		( 'tmp_Pro_FlagType' = "0") OR ( 'tmp_Pro_FlagType' = "1") OR ( 'tmp_Pro_FlagType' = "2")
		//	) 
		//	AND 
		//	( 'tmp_Pro_FlagActive' = 2) 
		//	AND 
		//	( 'tmp_Pro_Flag07Recall' = "1") 
		//	AND 
		//	( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("追回处理"))
		{
			if (						
					!(getFieldValue("BaseStatusCode").equals("10")) && getFieldValue("tmp_Pro_FlagPredefined").equals("0") 
					&& 
					(
						getFieldValue("tmp_Pro_FlagType").equals("0") || getFieldValue("tmp_Pro_FlagType").equals("1") || getFieldValue("tmp_Pro_FlagType").equals("2") 
					)
					&&
					getFieldValue("tmp_Pro_FlagActive").equals("2") && getFieldValue("tmp_Pro_Flag07Recall").equals("1") 
					&& 
					getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//自由退回：权限判断
		//		( 'tmp_Pro_FlagPredefined' = 0) AND ( 'BaseStatusCode' !=  "10" ) 
		//		AND 
		//		(
		//			( 'tmp_Pro_FlagType' = "0") OR ( 'tmp_Pro_FlagType' = "1") OR ( 'tmp_Pro_FlagType' = "2")
		//		) 
		//		AND 
		//		(
		//			( 'tmp_Pro_FlagActive' = 1) OR ( 'tmp_Pro_FlagActive' = 2)
		//		) 
		//		AND 
		//		( 'tmp_Pro_Flag05TurnDown' = "1") 
		//		AND 
		//		( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("退回"))
		{
			if (						
					!(getFieldValue("BaseStatusCode").equals("10")) && getFieldValue("tmp_Pro_FlagPredefined").equals("0") 
					&& 
					(
						getFieldValue("tmp_Pro_FlagType").equals("0") || getFieldValue("tmp_Pro_FlagType").equals("1") || getFieldValue("tmp_Pro_FlagType").equals("2") 
					)
					&&
					(	
						getFieldValue("tmp_Pro_FlagActive").equals("1") || getFieldValue("tmp_Pro_FlagActive").equals("2")
					)
					&& 
					getFieldValue("tmp_Pro_Flag05TurnDown").equals("1") 
					&& 
					getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//自由驳回：权限判断
		//		( 'tmp_Pro_FlagPredefined' = 0) AND ( 'tmp_Pro_PrevPhaseNo' !=  "BEGIN" ) 
		//		AND 
		//		(
		//			( 'tmp_Pro_FlagType' = "0") OR ( 'tmp_Pro_FlagType' = "1") OR ( 'tmp_Pro_FlagType' = "2")
		//		) 
		//		AND 
		//		( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag06TurnUp' = "1") 
		//		AND 
		//		( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("驳回处理"))
		{
			if (						
					!(getFieldValue("tmp_Pro_PrevPhaseNo").equals("BEGIN")) && getFieldValue("tmp_Pro_FlagPredefined").equals("0") 
					&& 
					(
						getFieldValue("tmp_Pro_FlagType").equals("0") || getFieldValue("tmp_Pro_FlagType").equals("1") || getFieldValue("tmp_Pro_FlagType").equals("2") 
					)
					&&
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_Flag06TurnUp").equals("1") 
					&& 
					getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//保存草稿：权限判断
		//		( 'tmp_Pro_FlagActive' = 1) 
		//		AND 
		//		(
		//			(
		//				( 'BaseWorkFlowFlag' = 0) AND ( 'tmp_Pro_PrevPhaseNo' =  "BEGIN" ) 
		//				AND 
		//				( 'BaseSendDate' =  $NULL$ ) AND ( 'BaseStatus' =  "草稿" )
		//			) 
		//			OR 
		//			(
		//				( 'BaseWorkFlowFlag' = 1) AND ( 'tmp_Pro_StProcessAction' = "新建")
		//			)
		//		)
		if (p_Action.equals("保存草稿"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1")
					&& 
					(
						(
							getFieldValue("BaseWorkFlowFlag").equals("0") && getFieldValue("tmp_Pro_PrevPhaseNo").equals("BEGIN") 
							&&
							getFieldValue("BaseStatus").equals("草稿") && FormatString.CheckNullString(getFieldValue("BaseSendDate")).equals("")
						)
						||
						(
							getFieldValue("BaseWorkFlowFlag").equals("1") && getFieldValue("tmp_Pro_StProcessAction").equals("新建")
						)
					)
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}		
		
		//受理：权限判断
		//		( 'tmp_Pro_FlagActive' = 1) 
		//		AND 
		//		(
		//			( 'tmp_Pro_FlagType' = "1") OR ( 'tmp_Pro_FlagType' = "0")
		//		) 
		//		AND 
		//		( 'tmp_Pro_ProcessType' =  "DEAL" ) AND ( 'tmp_Pro_FlagBegin' = 0) 
		//		AND 
		//		(
		//			(
		//				( 'tmp_Pro_FlagPredefined' = 0) AND ( 'tmp_Pro_PrevPhaseNo' !=  "BEGIN" )
		//			) 
		//			OR 
		//			(
		//				( 'tmp_Pro_FlagPredefined' = 1) AND ( 'tmp_Pro_StProcessAction' != "新建")
		//			)
		//		)
		if (p_Action.equals("受理"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL") && getFieldValue("tmp_Pro_FlagBegin").equals("0")
					&& 
					(
						getFieldValue("tmp_Pro_FlagType").equals("1") || getFieldValue("tmp_Pro_FlagType").equals("0")
					)
					&&
					(
						(
							getFieldValue("tmp_Pro_FlagPredefined").equals("0") && !(getFieldValue("tmp_Pro_PrevPhaseNo").equals("BEGIN"))
						)
						||
						(
							getFieldValue("tmp_Pro_FlagPredefined").equals("1") && !(getFieldValue("tmp_Pro_StProcessAction").equals("新建"))
						)
					)
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//阶段处理：权限判断
		//		( 'tmp_Pro_FlagActive' = 1) 
		//		AND 
		//		(
		//			( 'tmp_Pro_FlagType' = "1") OR ( 'tmp_Pro_FlagType' = "0")
		//		) 
		//		AND 
		//		( 'tmp_Pro_ProcessType' =  "DEAL" ) 
		//		AND 
		//		(
		//			(
		//				( 'tmp_Pro_FlagPredefined' = 0) AND ( 'tmp_Pro_PrevPhaseNo' !=  "BEGIN" )
		//			) 
		//			OR 
		//			(
		//				( 'tmp_Pro_FlagPredefined' = 1) AND ( 'tmp_Pro_StProcessAction' != "新建")
		//			)
		//		)
		if (p_Action.equals("阶段处理"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
					&& 
					(
						getFieldValue("tmp_Pro_FlagType").equals("1") || getFieldValue("tmp_Pro_FlagType").equals("0")
					)
					&&
					(
						(
							getFieldValue("tmp_Pro_FlagPredefined").equals("0") && !(getFieldValue("tmp_Pro_PrevPhaseNo").equals("BEGIN"))
						)
						||
						(
							getFieldValue("tmp_Pro_FlagPredefined").equals("1") && !(getFieldValue("tmp_Pro_StProcessAction").equals("新建"))
						)
					)
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}

		//完成：权限判断
		//		( 'tmp_Pro_FlagActive' = 1)  AND ( 'tmp_Pro_ProcessType' =  "DEAL" )
		//		AND 
		//		(
		//			( 'tmp_Pro_FlagType' = "1") OR ( 'tmp_Pro_FlagType' = "0")
		//		) 
		//		AND 
		//		( 'tmp_Pro_FlagPredefined' = 0) AND ( 'tmp_Pro_PrevPhaseNo' !=  "BEGIN" )
		if (p_Action.equals("完成"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
					&& 
					(
						getFieldValue("tmp_Pro_FlagType").equals("1") || getFieldValue("tmp_Pro_FlagType").equals("0")
					)
					&&
					getFieldValue("tmp_Pro_FlagPredefined").equals("0") 
					&&
					!(getFieldValue("tmp_Pro_ProcessType").equals("BEGIN"))
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}
		
		//组织会审：权限判断
		//		( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag16ToAssistAuditing' = "1")
		if (p_Action.equals("组织会审"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_Flag16ToAssistAuditing").equals("1")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}
		
		//提交审批：权限判断
		//		( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_Flag15ToAuditing' = "1") AND ( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("提交审批"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_Flag15ToAuditing").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}
		
		//派发：权限判断
		//		( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_ProcessType' =  "DEAL" )
		//		AND 
		//		(
		//			( 'tmp_Pro_Flag01Assign' = "1") OR ( 'tmp_Pro_Flag04Transfer' = "1") OR ( 'tmp_Pro_Flag03Assist' = "1") OR ( 'tmp_Pro_Flag02Copy' = "1")
		//		) 
		if (p_Action.equals("派发"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
					&&
					(
						getFieldValue("tmp_Pro_Flag01Assign").equals("1") || getFieldValue("tmp_Pro_Flag02Copy").equals("1") 
						|| 
						getFieldValue("tmp_Pro_Flag03Assist").equals("1") || getFieldValue("tmp_Pro_Flag04Transfer").equals("1")
					)
				)
			{
				return true;
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}
		
		//下一步\客户化下一步：权限判断
		//		( 'tmp_Pro_FlagActive' = 1) AND ( 'tmp_Pro_FlagPredefined' = 1) AND ( 'tmp_Pro_ProcessType' =  "DEAL" )
		if (p_Action.equals("下一步"))
		{
			if (						
					getFieldValue("tmp_Pro_FlagActive").equals("1") && getFieldValue("tmp_Pro_FlagPredefined").equals("1") && getFieldValue("tmp_Pro_ProcessType").equals("DEAL")
				)
			{
				if (p_ActionCustom!=null&&FormatString.CheckNullString(p_ActionCustom.equals("下一步")).equals(""))
				{
					return true;
				}
				else
				{
					try
					{
						return true;
						//客户化动作的按钮放在字段：tmp_Pro_CustomActions
//						String m_ArrayCustomAction[] = getFieldValue("tmp_Pro_CustomActions").split(";");
//						for (int i = 0;i<m_ArrayCustomAction.length;i++)
//						{
//							String m_OneCustomAction[] = m_ArrayCustomAction[i].split("=");
//							if (m_OneCustomAction[0].equals(p_ActionCustom))
//							{
//								return true;
//							}
//							else
//							{
//								BaseWrite_Log.writeActionLog(p_ActionCustom,1,m_str_ActionError_memo);
//								return false;		
//							}
//						}
					}
					catch(Exception ex)
					{
						BaseWrite_Log.writeActionExceptionLog(p_ActionCustom,ex);
						return false;
					}
				}
			}
			BaseWrite_Log.writeActionLog(p_Action,1,m_str_ActionError_memo);
			return false;			
		}
		//最终返回结果--------------------
		return false;
	}
	
	/**
	 * @param p_NeedActive_ProPhaseNo 需要激活的环节
	 */
	public BaseDealObject getProMatchRole(String p_NeedActive_ProPhaseNo)
	{
		try
		{
			if (FormatString.CheckNullString(p_NeedActive_ProPhaseNo).equals(""))
			{
				return null;
			}
			String m_BaseSchema = getFieldValue("tmp_Pro_BaseSchema");
			String m_BaseID 	= getFieldValue("tmp_Pro_BaseID");
			
			BasePerformRoleModel m_BasePerformRoleModel = (new BasePerformRoleManage()).getOneModel(m_BaseSchema, m_BaseID, p_NeedActive_ProPhaseNo);
			this.setFieldValue("tmp_Role_TopRoleMatchConditionsDesc", m_BasePerformRoleModel.getTopRoleMatchConditionsDesc());
			this.setFieldValue("tmp_Role_RoleOnlyID", m_BasePerformRoleModel.getRoleOnlyID());
			
			//700000014&网络分类&BaseItems#700000014&网络分类&BaseItems#
			String m_Role_OldTopRoleMatchConditionsDesc = m_BasePerformRoleModel.getTopRoleMatchConditionsDesc();
			String m_Role_NewTopRoleMatchConditionsDesc = "";
			String m_Role_TopRoleMatchConditionsList[] = m_Role_OldTopRoleMatchConditionsDesc.split("#");
			for (int i=0;i<m_Role_TopRoleMatchConditionsList.length;i++)
			{
				String m_Role_OldOneMatchCondition = m_Role_TopRoleMatchConditionsList[i];
				String m_Role_OneMatchConditionList[] = m_Role_OldOneMatchCondition.split("&");
				String m_FileID 	= m_Role_OneMatchConditionList[0];
				String m_FileName 	= m_Role_OneMatchConditionList[2];
				String m_Role_NewOneMatchCondition = "";
				if (Hashtable_BaseAllFields.containsKey(m_FileName))
				{
					m_Role_NewOneMatchCondition = m_Role_OldOneMatchCondition + "&" + getFieldValue(m_FileName);
				}
				else
				{
			    	RemedyDBOp remedyDBOp=new RemedyDBOp();
					String tblName=remedyDBOp.GetRemedyTableName(m_BaseSchema);
					String m_tmp_Sql = "select C" + m_FileID + " as " + m_FileName + " from " + tblName + " where C1 ='"+m_BaseID+"' and C700000001='"+m_BaseSchema+"'";
					RowSet 	m_rowSet		= OpDB.getDataSet(m_tmp_Sql);
					Row 	m_Rs = m_rowSet.get(0);
			      	setFieldValue(new BaseFieldInfo(m_FileName,m_FileID,m_Rs.getString(m_FileName),4));
			      	m_Role_NewOneMatchCondition = m_Role_OldOneMatchCondition + "&" + m_Rs.getString(m_FileName);
				}
				m_Role_NewTopRoleMatchConditionsDesc = m_Role_NewTopRoleMatchConditionsDesc + m_Role_NewOneMatchCondition + "#";
			}
			
			// SELECT BaseOnCondGetChildRole('$tmp_Pro_RoleType3_All_In$','$tmp_Role_RoleOnlyID$'),NEXTSELECTTEMP FROM BASENEXTSELECTTEMP '||chr(38)||'
			m_Role_NewTopRoleMatchConditionsDesc = m_Role_NewTopRoleMatchConditionsDesc.replaceAll("&", "'||chr(38)||'");
			String m_sql = "SELECT BaseOnCondGetChildRole('"+m_Role_NewTopRoleMatchConditionsDesc+"','"+m_BasePerformRoleModel.getRoleOnlyID()+"') as SelectReturn,NEXTSELECTTEMP FROM BASENEXTSELECTTEMP";
	      	RowSet 	m_rowSet		= OpDB.getDataSet(m_sql);
	      	String	m_SelectReturn 	= "";
	      	if (m_rowSet.length()>0)
	      	{
	      		Row 	m_Rs = m_rowSet.get(0);
	      		m_SelectReturn = m_Rs.getString("SelectReturn");//组名称&组ID
	      	}
	      	
	      	//wangwumei 判断是否查找到了角色数据
	      	if(!m_SelectReturn.equals("&"))
	      	{
		      	BaseDealObject m_BaseDealObject = new BaseDealObject();
		      	String m_SelectReturnList[] = m_SelectReturn.split("&");
		      	m_BaseDealObject.setGroup(m_SelectReturnList[0]);
		      	m_BaseDealObject.setGroupID(m_SelectReturnList[1]);
		      	return m_BaseDealObject;
		    }
		    else{
		      		return null;
	      	}
		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("获得匹配角色",ex);
			return null;
		}		

	}
	
	/**
	 * 描述：插入处理记录

	 * @param p_BaseToDealObject		派发对象的List(BaseToDealObject类的对象的数组)
	 * @return 是否成功
	 */
	public boolean push_WaitingDealProcess(List<BaseToDealObject> p_BaseToDealObject) {

		String tmp_Select_Assigner_ProcessIDS 	= "";
		String tmp_Select_Assister_ProcessIDS 	= "";
		String tmp_Select_Copyer_ProcessIDS 	= "";
		
		try
		{
			if(p_BaseToDealObject==null || p_BaseToDealObject.size()==0) 
			{
				BaseWrite_Log.writeLog("插入处理记录",1,"没有传递派发或审批对象");
				return false;
			}
			for (int i = 0;i<p_BaseToDealObject.size();i++)
			{

				BaseToDealObject m_tmp_BaseToDealObject = p_BaseToDealObject.get(i);
				
				Hashtable<String,PublicFieldInfo> Hashtable_ToDealProcessAllFields = new Hashtable<String,PublicFieldInfo>();
				
				Hashtable_ToDealProcessAllFields.put("ProcessBaseID", new PublicFieldInfo("ProcessBaseID","700020001",getFieldValue("tmp_Pro_BaseID"),4));
				Hashtable_ToDealProcessAllFields.put("ProcessBaseSchema", new PublicFieldInfo("ProcessBaseSchema","700020002",getFieldValue("tmp_Pro_BaseSchema"),4));
				Hashtable_ToDealProcessAllFields.put("PhaseNo", new PublicFieldInfo("PhaseNo","700020003",getGUID("BID",4),4));
				Hashtable_ToDealProcessAllFields.put("PrevPhaseNo", new PublicFieldInfo("PrevPhaseNo","700020004",getFieldValue("tmp_Pro_PhaseNo"),4));
				Hashtable_ToDealProcessAllFields.put("CreateByUserID", new PublicFieldInfo("CreateByUserID","700020045",getFieldValue("tmp_UserLoginName"),4));
				Hashtable_ToDealProcessAllFields.put("Assginee", new PublicFieldInfo("Assginee","700020005",m_tmp_BaseToDealObject.getAssginee(),4));
				Hashtable_ToDealProcessAllFields.put("AssgineeID", new PublicFieldInfo("AssgineeID","700020006",m_tmp_BaseToDealObject.getAssgineeID(),4));
				Hashtable_ToDealProcessAllFields.put("Group", new PublicFieldInfo("Group","700020007",m_tmp_BaseToDealObject.getGroup(),4));
				Hashtable_ToDealProcessAllFields.put("GroupID", new PublicFieldInfo("GroupID","700020008",m_tmp_BaseToDealObject.getGroupID(),4));
				Hashtable_ToDealProcessAllFields.put("AssignOverTimeDate", new PublicFieldInfo("AssignOverTimeDate","700020012",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAssignOverTimeDate()))).toString(),7));
				Hashtable_ToDealProcessAllFields.put("AcceptOverTimeDate", new PublicFieldInfo("AcceptOverTimeDate","700020013",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAcceptOverTimeDate()))).toString(),7));
				Hashtable_ToDealProcessAllFields.put("DealOverTimeDate", new PublicFieldInfo("DealOverTimeDate","700020014",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getDealOverTimeDate()))).toString(),7));
				//Hashtable_ToDealProcessAllFields.put("AuditingOverTimeDate", new PublicFieldInfo("AuditingOverTimeDate","700020037",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAuditingOverTimeDate()))).toString(),7));
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
				
				Hashtable_ToDealProcessAllFields.put("Flag16ToAssistAuditing", new PublicFieldInfo("Flag16ToAssistAuditing","700020091",(new Integer (m_tmp_BaseToDealObject.getFlag16ToAssistAuditing())).toString(),6));	
				
				String str_tmp_Desc = "";
				if (FormatString.CheckNullString((m_tmp_BaseToDealObject.getProDesc())).equals("")==false)
				{
					str_tmp_Desc = "：" + m_tmp_BaseToDealObject.getProDesc();
				}				
				if (m_tmp_BaseToDealObject.getFlagType()==0 && getFieldValue("tmp_Pro_ProcessType").equals("DEAL"))
				//派发
				{
					if (m_tmp_BaseToDealObject.getFlag31IsTransfer()==1)
					//转交过来的
					{
						String str_Desc = getFieldValue("tmp_UserFullName") + "转发" + StringUtil.checkNull(m_tmp_BaseToDealObject.getGroup()) + StringUtil.checkNull(m_tmp_BaseToDealObject.getAssginee()) + str_tmp_Desc + "；";
						Hashtable_ToDealProcessAllFields.put("ProDesc", new PublicFieldInfo("ProDesc","700020018",str_Desc,4));

						if (
								((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer")).getStrFieldValue() == null 
								|| 
								((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer")).getStrFieldValue().equals("0") 
								|| 
								((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_Flag31IsTransfer")).getStrFieldValue().equals("")
							)
						//本环节不是转交过来的环节
						{
							Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",getFieldValue("tmp_Pro_PrevPhaseNo"),4));
						}
						else
						//本环节是转交过来的环节
						{
							Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",getFieldValue("tmp_Pro_TransferPhaseNo"),4));
						}
					}
					else
						//非转交过来的
					{
						String str_Desc = getFieldValue("tmp_UserFullName") + "派发" + StringUtil.checkNull(m_tmp_BaseToDealObject.getGroup()) + StringUtil.checkNull(m_tmp_BaseToDealObject.getAssginee()) + str_tmp_Desc + "；";
						Hashtable_ToDealProcessAllFields.put("ProDesc", new PublicFieldInfo("ProDesc","700020018",str_Desc,4));
						Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",null,4));
					}
					//调用Process的插入函数				
					//跟新tmp_Select_Assigner_ProcessIDS，加上该插入的ProcessID
					tmp_Select_Assigner_ProcessIDS = tmp_Select_Assigner_ProcessIDS + remedyEntryInsert(Constants.TblDealProcess,Hashtable_ToDealProcessAllFields) + ";";
				}
				if (m_tmp_BaseToDealObject.getFlagType()==1 && getFieldValue("tmp_Pro_ProcessType").equals("DEAL"))
					//协办
				{
					String str_Desc = getFieldValue("tmp_UserFullName") + "要求";	
					str_Desc = str_Desc + StringUtil.checkNull(m_tmp_BaseToDealObject.getGroup()) + StringUtil.checkNull(m_tmp_BaseToDealObject.getAssginee()) + "协办" + str_tmp_Desc + "；";
					Hashtable_ToDealProcessAllFields.put("ProDesc", new PublicFieldInfo("ProDesc","700020018",str_Desc,4));
					Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",null,4));
					//调用Process的插入函数				
					//跟新tmp_Select_Assister_ProcessIDS，加上该插入的ProcessID
					tmp_Select_Assister_ProcessIDS = tmp_Select_Assister_ProcessIDS + remedyEntryInsert(Constants.TblDealProcess,Hashtable_ToDealProcessAllFields) + ";";
				}
				if (m_tmp_BaseToDealObject.getFlagType()==2 && getFieldValue("tmp_Pro_ProcessType").equals("DEAL"))
					//抄送

				{
					String str_Desc = getFieldValue("tmp_UserFullName") + "抄送" + StringUtil.checkNull(m_tmp_BaseToDealObject.getGroup()) + StringUtil.checkNull(m_tmp_BaseToDealObject.getAssginee()) + str_tmp_Desc + "；";
					Hashtable_ToDealProcessAllFields.put("ProDesc", new PublicFieldInfo("ProDesc","700020018",str_Desc,4));
					Hashtable_ToDealProcessAllFields.put("TransferPhaseNo", new PublicFieldInfo("TransferPhaseNo","700020036",null,4));
					//调用Process的插入函数				
					//跟新tmp_Select_Copyer_ProcessIDS，加上该插入的ProcessID
					tmp_Select_Copyer_ProcessIDS = tmp_Select_Copyer_ProcessIDS + remedyEntryInsert(Constants.TblDealProcess,Hashtable_ToDealProcessAllFields) + ";";
				}
				
				setFieldValue("tmp_Select_Assigner_ProcessIDS",tmp_Select_Assigner_ProcessIDS);
				setFieldValue("tmp_Select_Assister_ProcessIDS",tmp_Select_Assister_ProcessIDS);
				setFieldValue("tmp_Select_Copyer_ProcessIDS",tmp_Select_Copyer_ProcessIDS);
		
				Hashtable_ToDealProcessAllFields.clear();
				Hashtable_ToDealProcessAllFields = null;
			}
		}
		catch(Exception ex)
		{ex.printStackTrace();
			BaseWrite_Log.writeExceptionLog("插入处理记录",ex);
			return false;
		}		
		
		return true;
	}
	
	/**
	 * 描述：插入审批记录
	 * @param p_BaseToAuditingObject		审批对象的List(BaseToDealObject类的对象的数组)
	 * @return 是否成功
	 */
	public boolean push_WaitingAuditingProcess(List<BaseToDealObject> p_BaseToAuditingObject) {

		String tmp_Select_Auditinger_ProcessIDS 			= "";
		String tmp_Select_AssistantAuditinger_ProcessIDS 	= "";
		
		try
		{
			if(p_BaseToAuditingObject==null || p_BaseToAuditingObject.size()==0)
			{
				BaseWrite_Log.writeLog("插入审批记录",1,"没有传递派发或审批对象");
				return false;
			}
			for (int i = 0;i<p_BaseToAuditingObject.size();i++)
			{

				BaseToDealObject m_tmp_BaseToDealObject = (BaseToDealObject)p_BaseToAuditingObject.get(i);
				
				Hashtable<String,PublicFieldInfo> Hashtable_ToDealProcessAllFields = new Hashtable<String,PublicFieldInfo>();
				
				Hashtable_ToDealProcessAllFields.put("ProcessBaseID", new PublicFieldInfo("ProcessBaseID","700020001",getFieldValue("tmp_Pro_BaseID"),4));
				Hashtable_ToDealProcessAllFields.put("ProcessBaseSchema", new PublicFieldInfo("ProcessBaseSchema","700020002",getFieldValue("tmp_Pro_BaseSchema"),4));
				Hashtable_ToDealProcessAllFields.put("PhaseNo", new PublicFieldInfo("PhaseNo","700020003",getGUID("BID",4),4));
				Hashtable_ToDealProcessAllFields.put("PrevPhaseNo", new PublicFieldInfo("PrevPhaseNo","700020004",getFieldValue("tmp_Pro_PhaseNo"),4));
				Hashtable_ToDealProcessAllFields.put("CreateByUserID", new PublicFieldInfo("CreateByUserID","700020045",getFieldValue("tmp_UserLoginName"),4));
				Hashtable_ToDealProcessAllFields.put("Assginee", new PublicFieldInfo("Assginee","700020005",m_tmp_BaseToDealObject.getAssginee(),4));
				Hashtable_ToDealProcessAllFields.put("AssgineeID", new PublicFieldInfo("AssgineeID","700020006",m_tmp_BaseToDealObject.getAssgineeID(),4));
				Hashtable_ToDealProcessAllFields.put("Group", new PublicFieldInfo("Group","700020007",m_tmp_BaseToDealObject.getGroup(),4));
				Hashtable_ToDealProcessAllFields.put("GroupID", new PublicFieldInfo("GroupID","700020008",m_tmp_BaseToDealObject.getGroupID(),4));
				//Hashtable_ToDealProcessAllFields.put("AssignOverTimeDate", new PublicFieldInfo("AssignOverTimeDate","700020012",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAssignOverTimeDate()))).toString(),7));
				//Hashtable_ToDealProcessAllFields.put("AcceptOverTimeDate", new PublicFieldInfo("AcceptOverTimeDate","700020013",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAcceptOverTimeDate()))).toString(),7));
				//Hashtable_ToDealProcessAllFields.put("DealOverTimeDate", new PublicFieldInfo("DealOverTimeDate","700020014",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getDealOverTimeDate()))).toString(),7));
				Hashtable_ToDealProcessAllFields.put("AuditingOverTimeDate", new PublicFieldInfo("AuditingOverTimeDate","700020037",(new Long(FormatTime.FormatDateToInt(m_tmp_BaseToDealObject.getAuditingOverTimeDate()))).toString(),7));
				Hashtable_ToDealProcessAllFields.put("StDate", new PublicFieldInfo("StDate","700020015",null,7));
				Hashtable_ToDealProcessAllFields.put("FlagType", new PublicFieldInfo("FlagType","700020019",(new Integer (m_tmp_BaseToDealObject.getFlagType())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("FlagActive", new PublicFieldInfo("FlagActive","700020020","6",2));
				Hashtable_ToDealProcessAllFields.put("FlagPredefined", new PublicFieldInfo("FlagPredefined","700020021","0",2));
				Hashtable_ToDealProcessAllFields.put("FlagDuplicated", new PublicFieldInfo("FlagDuplicated","700020022","0",2));

				Hashtable_ToDealProcessAllFields.put("Flag01Assign", new PublicFieldInfo("Flag01Assign","700020023",(new Integer (m_tmp_BaseToDealObject.getFlag01Assign())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag02Copy", new PublicFieldInfo("Flag02Copy","700020024",(new Integer (m_tmp_BaseToDealObject.getFlag02Copy())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag03Assist", new PublicFieldInfo("Flag03Assist","700020025",(new Integer (m_tmp_BaseToDealObject.getFlag03Assist())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag04Transfer", new PublicFieldInfo("Flag04Transfer","700020026",(new Integer (m_tmp_BaseToDealObject.getFlag04Transfer())).toString(),6));
//				Hashtable_ToDealProcessAllFields.put("Flag05TurnDown", new PublicFieldInfo("Flag05TurnDown","700020027",(new Integer (m_tmp_BaseToDealObject.getFlag05TurnDown())).toString(),6));
//				Hashtable_ToDealProcessAllFields.put("Flag06TurnUp", new PublicFieldInfo("Flag06TurnUp","700020028",(new Integer (m_tmp_BaseToDealObject.getFlag06TurnUp())).toString(),6));
//				Hashtable_ToDealProcessAllFields.put("Flag07Recall", new PublicFieldInfo("Flag07Recall","700020029",(new Integer (m_tmp_BaseToDealObject.getFlag07Recall())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag08Cancel", new PublicFieldInfo("Flag08Cancel","700020030",(new Integer (m_tmp_BaseToDealObject.getFlag08Cancel())).toString(),6));
//				Hashtable_ToDealProcessAllFields.put("Flag09Close", new PublicFieldInfo("Flag09Close","700020031",(new Integer (m_tmp_BaseToDealObject.getFlag09Close())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag15ToAuditing", new PublicFieldInfo("Flag15ToAuditing","700020032",(new Integer (m_tmp_BaseToDealObject.getFlag15ToAuditing())).toString(),6));
				Hashtable_ToDealProcessAllFields.put("Flag20SideBySide", new PublicFieldInfo("Flag20SideBySide","700020033","0",6));
				Hashtable_ToDealProcessAllFields.put("Flag33IsEndPhase", new PublicFieldInfo("AuditingWayIsActive","700020041","2",6));
				Hashtable_ToDealProcessAllFields.put("Flag22IsSelect", new PublicFieldInfo("Flag22IsSelect","700020046","0",6));
//				Hashtable_ToDealProcessAllFields.put("Flag31IsTransfer", new PublicFieldInfo("Flag31IsTransfer","700020035",(new Integer (m_tmp_BaseToDealObject.getFlag31IsTransfer())).toString(),6));		
//				Hashtable_ToDealProcessAllFields.put("Flag32IsToTransfer", new PublicFieldInfo("Flag32IsToTransfer","700020052","0",6));
				Hashtable_ToDealProcessAllFields.put("Flag33IsEndPhase", new PublicFieldInfo("Flag33IsEndPhase","700020053","1",6));	
				
				Hashtable_ToDealProcessAllFields.put("Flag16ToAssistAuditing", new PublicFieldInfo("Flag16ToAssistAuditing","700020091",(new Integer (m_tmp_BaseToDealObject.getFlag16ToAssistAuditing())).toString(),6));	
				
				String str_tmp_Desc = "";
				if (FormatString.CheckNullString((m_tmp_BaseToDealObject.getProDesc())).equals("")==false)
				{
					str_tmp_Desc = "：" + m_tmp_BaseToDealObject.getProDesc();
				}
				if (m_tmp_BaseToDealObject.getFlagType()==3)
				{
					String str_Desc = getFieldValue("tmp_UserFullName") + "提交审批给" + StringUtil.checkNull(m_tmp_BaseToDealObject.getGroup()) + StringUtil.checkNull(m_tmp_BaseToDealObject.getAssginee()) + str_tmp_Desc + "；";
					Hashtable_ToDealProcessAllFields.put("ProDesc", new PublicFieldInfo("ProDesc","700020018",str_Desc,4));
	
					tmp_Select_Auditinger_ProcessIDS = tmp_Select_Auditinger_ProcessIDS + remedyEntryInsert(Constants.TblAuditingProcess,Hashtable_ToDealProcessAllFields) + ";";
				}
				else if (m_tmp_BaseToDealObject.getFlagType()==5)
				{
					String str_Desc = getFieldValue("tmp_UserFullName") + "提交会审给" + StringUtil.checkNull(m_tmp_BaseToDealObject.getGroup()) + StringUtil.checkNull(m_tmp_BaseToDealObject.getAssginee()) + str_tmp_Desc + "；";
					Hashtable_ToDealProcessAllFields.put("ProDesc", new PublicFieldInfo("ProDesc","700020018",str_Desc,4));
	
					tmp_Select_AssistantAuditinger_ProcessIDS = tmp_Select_AssistantAuditinger_ProcessIDS + remedyEntryInsert(Constants.TblAuditingProcess,Hashtable_ToDealProcessAllFields) + ";";
				}
				setFieldValue("tmp_Select_Auditinger_ProcessIDS",tmp_Select_Auditinger_ProcessIDS);
				setFieldValue("tmp_Select_AssistantAuditinger_ProcessIDS",tmp_Select_AssistantAuditinger_ProcessIDS);
		
				Hashtable_ToDealProcessAllFields.clear();
				Hashtable_ToDealProcessAllFields = null;
			}
		}
		catch(Exception ex)
		{ex.printStackTrace();
			BaseWrite_Log.writeExceptionLog("插入审批记录",ex);
			return false;
		}		
		
		return true;
	}
	
	/**
	 * 描述：根据其他，设置工单特有信息的函数，并返回工单上的临时字段信息已经负值的
	 * @param p_FieldListInfo		操作需要的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_ActionType			操作动作类型；0：催办；1：退回；2：驳回；3：追回；
	 * @param p_BaseDealObject		操作的对象
	 * @return 返回已经填写的字段信息
	 */
	public boolean select_OtherDealProcess(int p_ActionType,List<BaseDealObject> p_BaseDealObject) {
		if (p_BaseDealObject.size() < 1)
		{
			BaseWrite_Log.writeLog("作其他操作时，查找操作的环节信息记录",1,"作其他操作时，查找操作的环节信息记录失败，参数传递失败，没有传递操作对象！");
			return false;
		}
		String str_GroupsID 	= "";
		String str_AssgineesID 	= "";
		if(p_BaseDealObject==null || p_BaseDealObject.size()==0)
		{
			BaseWrite_Log.writeLog("作其他操作时，查找操作的环节信息记录",1,"没有传递对象");
			return false;
		}		
		for (int i = 0 ; i < p_BaseDealObject.size(); i++)
		{
			BaseDealObject m_tmp_BaseDealObject = p_BaseDealObject.get(i);
			if (m_tmp_BaseDealObject.getAssgineeID() != null && m_tmp_BaseDealObject.getAssgineeID().equals("") == false)
			{
				str_AssgineesID = str_AssgineesID + m_tmp_BaseDealObject.getAssgineeID() + ";";
			}
			else
			{
				str_GroupsID = str_GroupsID + m_tmp_BaseDealObject.getGroupID() + ";";
			}
		}
		try
		{
			int 	int_Flag = 0;
			StringBuffer m_str_SelectSql = new StringBuffer();
			m_str_SelectSql.append(" AND C700020020 != 6 ");
			switch (p_ActionType)
			{
				case 0://0：催办；
				{
					/*( 'FlagActive' = 1)*/
					m_str_SelectSql.append(" AND C700020020 = 1 ");
					int_Flag = 1;
					break;
				}
				case 1://1：退回；
				{
					/*
					(
						( $tmp_Pro_FlagActive$ = 1) OR ( $tmp_Pro_FlagActive$ = 2)
					) 
					AND 
					( 'PrevPhaseNo' = $tmp_Pro_PhaseNo$) AND ( 'FlagActive' = 0) AND ( 'ProcessStatus' !=  "被退回" ) 
					AND 
					(
						NOT ( $tmp_Select_OtherDeal_ProcessIDS$ LIKE ("%"  + 'ProcessID' +  ";%" ))
					)
					*/
					m_str_SelectSql.append(" AND ("+getFieldValue("tmp_Pro_FlagActive")+"=1 OR "+getFieldValue("tmp_Pro_FlagActive")+" =2) ");
					m_str_SelectSql.append(" AND C700020004 = '"+getFieldValue("tmp_Pro_PhaseNo")+"' ");
					m_str_SelectSql.append(" AND C700020020 = 0 ");
					m_str_SelectSql.append(" AND C700020011 != '被退回' ");
					if (getFieldValue("tmp_Select_OtherDeal_ProcessIDS") != null && getFieldValue("tmp_Select_OtherDeal_ProcessIDS").toString().equals("null") == false && getFieldValue("tmp_Select_OtherDeal_ProcessIDS").toString().equals("") == false)
					{
						m_str_SelectSql.append(" AND ("+getFieldValue("tmp_Select_OtherDeal_ProcessIDS")+" NOT LIKE '%' || C1 || ';%' )");
					}
					int_Flag = 1;
					break;
				}
				case 2://2：驳回；
				{
					/*
					( $tmp_Pro_FlagActive$ = 1) 
					AND 
					(
						(
							( 'PhaseNo' = $tmp_Pro_PrevPhaseNo$) 
							AND 
							(
								( 'FlagActive' = 2) OR ( 'FlagActive' = 0)
							)
						) 
						OR 
						(
							( 'PhaseNo' = $tmp_Pro_PhaseNo$) AND ( 'FlagActive' = 1) AND ( 'ProcessStatus' =  "处理中" )
						)
					) 
					AND 
					(
						NOT ( $tmp_Select_OtherDeal_ProcessIDS$ LIKE ("%"  + 'ProcessID' +  ";%" ))
					)
					*/
					m_str_SelectSql.append(" AND ("+getFieldValue("tmp_Pro_FlagActive")+"=1) ");
					m_str_SelectSql.append(" AND ( ");
					m_str_SelectSql.append(" 		(C700020003 = '"+getFieldValue("tmp_Pro_PrevPhaseNo")+"' AND (C700020020 = 2 OR C700020020 = 0)) ");
					m_str_SelectSql.append(" 		OR ");
					m_str_SelectSql.append(" 		(C700020003 = '"+getFieldValue("tmp_Pro_PhaseNo")+"' AND C700020020 = 1 AND C700020011 = '处理中') ");
					m_str_SelectSql.append("     ) ");
					if (getFieldValue("tmp_Select_OtherDeal_ProcessIDS") != null && getFieldValue("tmp_Select_OtherDeal_ProcessIDS").toString().equals("null") == false && getFieldValue("tmp_Select_OtherDeal_ProcessIDS").toString().equals("") == false)
					{
						m_str_SelectSql.append(" AND ("+getFieldValue("tmp_Select_OtherDeal_ProcessIDS")+" NOT LIKE '%' || C1 || ';%' )");
					}	
					int_Flag = 1;
					break;
				}
				case 3://3：追回；
				{
					/*( 'FlagActive' = 1) AND ( 'PrevPhaseNo' = $tmp_Pro_PhaseNo$)*/
					m_str_SelectSql.append(" AND C700020020 = 1 ");
					m_str_SelectSql.append(" AND C700020004 = '"+getFieldValue("tmp_Pro_PhaseNo")+"' ");
					int_Flag = 1;
					break;
				}
				default:
				{
					BaseWrite_Log.writeLog("作其他操作时，查找操作的环节信息记录",1,"作其他操作时，查找操作的环节信息记录失败，参数传递失败！");
					return false;	
				}
			}
			if (int_Flag == 0)
			{
				BaseWrite_Log.writeLog("作其他操作时，查找操作的环节信息记录",1,"作其他操作时，查找操作的环节信息记录失败，查找失败，没有该操作对象！");
				return false;								
			}
			else
			{
				RDParameter m_ProRDParameter 	= new RDParameter();
				m_ProRDParameter.addIndirectPar("expansionsql",m_str_SelectSql.toString(),4);
				String m_ReturnProcessType 	= "DEAL";
				List<Object> m_ProcessModelList 	= selectMoreProcess(m_ProRDParameter,m_ReturnProcessType);
				StringBuffer m_ReturnProcessIDs = new StringBuffer();
				for (int i=0;i<m_ProcessModelList.size();i++)
				{
					DealProcessModel m_DealProcessModel = (DealProcessModel)m_ProcessModelList.get(i);
					m_ReturnProcessIDs.append(m_DealProcessModel.getProcessID() + ";");
				}
				setFieldValue("tmp_Select_OtherDeal_ProcessIDS",m_ReturnProcessIDs.toString());
				return true;
			}
		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("作其他操作时，查找操作的环节信息记录",ex);
			return false;		
		}
	}
}
