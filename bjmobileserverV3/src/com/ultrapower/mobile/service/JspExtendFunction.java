package com.ultrapower.mobile.service;

import java.util.List;
import com.ultrapower.mobile.model.xml.ActionModel;
import com.ultrapower.mobile.model.xml.FieldInfo;

public interface JspExtendFunction
{
	public void open(String baseSchema, String baseStatus, String baseSn, List<FieldInfo> bizFields, List<FieldInfo> dpLogFields, List<ActionModel> actions);
}
