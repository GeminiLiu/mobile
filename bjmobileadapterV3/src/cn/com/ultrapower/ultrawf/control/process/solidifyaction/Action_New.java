package cn.com.ultrapower.ultrawf.control.process.solidifyaction;

import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.baseaction.Action_Base;
import cn.com.ultrapower.ultrawf.control.process.baseaction.BaseWrite_Log;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.system.util.FormatString;

public class Action_New extends Action_Base {
	
	protected String m_strError;

	public Action_New()
	{
		this.m_Action 	= "新建";
		this.m_strError = m_Action + "失败！";
	}
	
	public boolean do_Action(String p_strUserLoginName,String p_BaseSchema,String p_TplBaseID, List<BaseFieldInfo> p_FieldListInfo, List<BaseAttachmentInfo> p_BaseAttachmentList)
	{
		
		if (!(this.Init_Action(p_BaseSchema, null,p_TplBaseID, null, null, p_strUserLoginName, 0, m_Action, null)))
		{
			BaseWrite_Log.writeLog(m_Action,1,m_strError);
			baseClose();
			return false;
		}
		
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

        //转到后台操作
		printBaseAllFields(this.Hashtable_BaseAllFields);
		
		m_BaseID = basePush(p_BaseSchema,null,p_strUserLoginName,0);
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
