package cn.com.ultrapower.ultrawf.control.process.solidifyaction;

import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.baseaction.Action_Base;
import cn.com.ultrapower.ultrawf.control.process.baseaction.BaseWrite_Log;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseToDealObject;
import cn.com.ultrapower.system.util.FormatString;

public class Action_Auditing extends Action_Base {

	protected String m_strError;
	
	public Action_Auditing()
	{
		this.m_Action = "审批";
		
		this.m_strError = m_Action + "失败！";
	}
	
	/*
	 * @param p_AuditingResult		工单审批结果（0:审批不通过;1:审批通过;2:审批通过，并转审;）
	 */
	public boolean do_Action(
			String p_strUserLoginName,
			String p_BaseSchema,
			String p_BaseID,
			String p_ProcessID,
			String p_ProcessType,
			int p_AuditingResult,
			List<BaseToDealObject> 		p_BaseToAuditingObject,			
			List<BaseFieldInfo> 		p_FieldListInfo, 			
			List<BaseAttachmentInfo> 	p_BaseAttachmentList)
	{
		
		if (!(this.Init_Action(p_BaseSchema, p_BaseID, null,p_ProcessID, p_ProcessType, p_strUserLoginName, 1, m_Action, null)))
		{
			BaseWrite_Log.writeLog(m_Action,1,m_strError);
			baseClose();	
			return false;
		}

		//动作的个性化操作开始
		
		//动作的个性化操作结束

		//设置传入字段的值
		for (int i=0;i<p_FieldListInfo.size();i++)
		{
			this.setFieldValue(p_FieldListInfo.get(i));
		}
		
		//附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.baseAttachmentPush(p_BaseAttachmentList)==false)
			{
				BaseWrite_Log.writeLog(m_Action,1,m_strError);
				baseClose();
				return false;	
			}				
		}
		
		switch(p_AuditingResult) 
		{
			case 0 : 
			{
				setFieldValue("tmp_BaseActionBtn_Char",m_Action );
				setFieldValue("tmp_BaseUser_OpDesc_AuditingResult","审批不通过" );
				break;
			}
			case 1 : 
			{
				setFieldValue("tmp_BaseActionBtn_Char",m_Action );
				setFieldValue("tmp_BaseUser_OpDesc_AuditingResult","审批通过" );
				break;
			}
			case 2 : 
			{
				setFieldValue("tmp_BaseActionBtn_Char","提交审批" );
				setFieldValue("tmp_BaseUser_OpDesc_AuditingResult","审批通过，并转审" );
				if(getFieldValue("tmp_Pro_Flag15ToAuditing").equals("0"))
				{
					BaseWrite_Log.writeLog(m_Action,1,"没有转审权限");
					baseClose();
					return false;
				}
				if(p_BaseToAuditingObject == null)
				{
					BaseWrite_Log.writeLog(m_Action,1,"没有传递转审对象");
					baseClose();
					return false;
				}
				if(p_BaseToAuditingObject.size()<1)
				{
					BaseWrite_Log.writeLog(m_Action,1,"没有传递转审对象");
					baseClose();
					return false;
				}
				
				//插入审批记录
				if (push_WaitingAuditingProcess(p_BaseToAuditingObject)==false)
				{
					BaseWrite_Log.writeLog(m_Action,1,"插入审批记录");
					baseClose();
					return false;	
				}	
				break;
			}
			default:
			{
				BaseWrite_Log.writeLog(m_Action,1,"审批结果传递错误");
				baseClose();
				return false;
			}
		}
		
        //转到后台操作
		printBaseAllFields(this.Hashtable_BaseAllFields);
		
		m_BaseID = basePush(p_BaseSchema,p_BaseID,p_strUserLoginName,1);
		if (FormatString.CheckNullString(m_BaseID).equals(""))
		{  
			BaseWrite_Log.writeLog(m_Action,1,m_strError);
			baseClose();	
			return false;	
		}
		BaseWrite_Log.writeLog(m_Action,0,"针对"+ this.getFieldValue("BaseName") +"，操作<" + m_Action + ">成功！");
		baseClose();	
		return true;		
	}


}
