package cn.com.ultrapower.ultrawf.control.process.solidifyaction;

import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.baseaction.Action_Base;
import cn.com.ultrapower.ultrawf.control.process.baseaction.BaseWrite_Log;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseDealObject;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.system.util.FormatString;

public class Action_DealTurnDown extends Action_Base {

	protected String m_strError;
	
	public Action_DealTurnDown()
	{
		this.m_Action = "退回";
		
		this.m_strError = m_Action + "失败！";
	}
	
	public boolean do_Action(
			String p_strUserLoginName,
			String p_BaseSchema,
			String p_BaseID,
			String p_ProcessID,
			String p_ProcessType,
			List<BaseDealObject> 		p_BaseDealObject,
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
		setFieldValue("tmp_BaseActionBtn_Char",m_Action );
		//动作的个性化操作结束
		//设置传入字段的值
		for (int i=0;i<p_FieldListInfo.size();i++)
		{
			this.setFieldValue(p_FieldListInfo.get(i));
		}
		//初始化催办对象process信息
		if (select_OtherDealProcess(1/*退回*/,p_BaseDealObject)==false)
		{
			BaseWrite_Log.writeLog(m_Action,1,m_strError);
			baseClose();
			return false;	
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
