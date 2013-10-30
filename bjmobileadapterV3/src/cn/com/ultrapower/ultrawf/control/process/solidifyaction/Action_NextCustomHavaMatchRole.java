package cn.com.ultrapower.ultrawf.control.process.solidifyaction;

import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.baseaction.BaseWrite_Log;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseDealObject;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.system.util.FormatString;

public class Action_NextCustomHavaMatchRole extends Action_NextBaseCustom {
	
	public boolean do_Action(
			String p_strUserLoginName,
			String p_BaseSchema,
			String p_BaseID,
			String p_ProcessID,
			String p_ProcessType,
			String p_ActionCustom,
			String p_NeedActive_ProPhaseNo,
			BaseFieldInfo p_NeedActive_ProAssgineeIDField,
			BaseFieldInfo p_NeedActive_ProAssgineeField,
			BaseFieldInfo p_NeedActive_ProGroupIDField,
			BaseFieldInfo p_NeedActive_ProGroupField,
			List<BaseFieldInfo> p_FieldListInfo, 
			List<BaseAttachmentInfo> p_BaseAttachmentList)
	{
		set_CustomInformation(p_ActionCustom);
		
		if (!(this.Init_Action(p_BaseSchema, p_BaseID, null,p_ProcessID, p_ProcessType, p_strUserLoginName, 1, m_Action, m_ActionCustom)))
		{
			BaseWrite_Log.writeLog(m_Action,1,m_strError + "；" + m_strActionCustomError);
			baseClose();
			return false;
		}

		//动作的个性化操作开始
		setFieldValue("tmp_BaseActionBtn_Char",m_Action );
		setFieldValue("tmp_BaseActionBtn_Fix_Char", m_ActionCustom);
		BaseDealObject m_BaseDealObject = getProMatchRole(p_NeedActive_ProPhaseNo);
		//设置匹配出的角色
		//wangwumei 未找到匹配的角色数据，返回false
		if(null == m_BaseDealObject){
			
			return false;
		}
		p_NeedActive_ProAssgineeIDField.setStrFieldValue(m_BaseDealObject.getAssgineeID());
		p_NeedActive_ProAssgineeField.setStrFieldValue(m_BaseDealObject.getAssginee());
		p_NeedActive_ProGroupIDField.setStrFieldValue(m_BaseDealObject.getGroupID());
		p_NeedActive_ProGroupField.setStrFieldValue(m_BaseDealObject.getGroup());
		setFieldValue(p_NeedActive_ProAssgineeIDField);
		setFieldValue(p_NeedActive_ProAssgineeField );
		setFieldValue(p_NeedActive_ProGroupIDField );
		setFieldValue(p_NeedActive_ProGroupField );
		//动作的个性化操作结束

		for (int i=0;i<p_FieldListInfo.size();i++)
		{
			this.setFieldValue(p_FieldListInfo.get(i));
		}
		
		//附件	
		if (p_BaseAttachmentList != null && p_BaseAttachmentList.size() > 0)
		{
			if (this.baseAttachmentPush(p_BaseAttachmentList)==false)
			{
				BaseWrite_Log.writeLog(m_Action,1,m_strError + "；" + m_strActionCustomError);
				baseClose();
				return false;	
			}				
		}
				
        //转到后台操作
		printBaseAllFields(this.Hashtable_BaseAllFields);
		
		m_BaseID = basePush(p_BaseSchema,p_BaseID,p_strUserLoginName,1);
		if (FormatString.CheckNullString(m_BaseID).equals(""))
		{  
			BaseWrite_Log.writeLog(m_Action,1,m_strError + "；" + m_strActionCustomError);
			baseClose();	
			return false;	
		}
		BaseWrite_Log.writeLog(m_Action,0,"针对"+ this.getFieldValue("BaseName") +"，操作<" + m_Action + ">的固定流程客户化动作<" + m_ActionCustom + ">成功！");
		baseClose();	
		return true;		
	}
}
