package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.Group;
import cn.com.ultrapower.ultrawf.models.config.ParGroupModel;

public class GroupManage {
	
	public List GetList(int p_PageNumber,int p_StepRow)
	{
		Group m_User=new Group();
		return m_User.GetList(p_PageNumber,p_StepRow);
	}
	public List GetList(ParGroupModel p_ParGroupModel,int p_PageNumber,int p_StepRow)
	{
		Group m_User=new Group();
		return m_User.GetList(p_ParGroupModel,p_PageNumber,p_StepRow);
	}	
	

}
