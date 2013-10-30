package com.ultrapower.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.mobile.model.xml.ActionModel;
import com.ultrapower.mobile.model.xml.FieldInfo;
import com.ultrapower.mobile.service.JspExtendFunction;
import com.ultrapower.remedy4j.core.RemedySession;

public class JspExtendFunctionImpl implements JspExtendFunction
{

	public void open(String baseSchema, String baseStatus, String baseSn, List<FieldInfo> bizFields, List<FieldInfo> dpLogFields, List<ActionModel> actions)
	{
		if ("WF4:EL_TTM_TTH".equals(baseSchema)) 
		{
			List<ActionModel> tempActions = new ArrayList<ActionModel>(); 
			//移除按钮：内部处理，升级到技术支援
			for (ActionModel actionModel : actions)
			{
				if ("内部处理".equals(actionModel.getActionName()) || "升级到技术支援".equals(actionModel.getActionName()) || "提交审批".equals(actionModel.getActionName()) || "处理驳回".equals(actionModel.getActionName()) || "作废".equals(actionModel.getActionName())|| "作　废".equals(actionModel.getActionName())|| "处理退回".equals(actionModel.getActionName()))
				{
					tempActions.add(actionModel);
				}
				
				if( !"NEXT".equals(actionModel.getActionType()) && ("T2移交处理".equals(actionModel.getActionName())||"T2完成处理".equals(actionModel.getActionName())|| "内部转派".equals(actionModel.getActionName())||"归档".equals(actionModel.getActionName()) || "退回处理".equals(actionModel.getActionName())))
				{
					tempActions.add(actionModel); 
				}
			}
			actions.removeAll(tempActions);
		}
	}

}
