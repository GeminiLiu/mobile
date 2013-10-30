package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfo;

public class BaseOwnFieldInfoManage {

	public List GetListForPrint(String p_Baseschema)
	{
		List m_List=null;
		BaseOwnFieldInfo m_BaseOwnFieldInfo=new BaseOwnFieldInfo();
		m_List=m_BaseOwnFieldInfo.GetListForPrint(p_Baseschema);
		return m_List;
	}
}
