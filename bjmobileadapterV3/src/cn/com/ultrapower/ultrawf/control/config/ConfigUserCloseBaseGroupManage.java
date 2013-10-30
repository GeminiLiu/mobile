package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.ConfigUserCloseBaseGroup;
import cn.com.ultrapower.ultrawf.models.config.ParConfigUserCloseBaseGroupModel;

public class ConfigUserCloseBaseGroupManage {

	
	public List getList(ParConfigUserCloseBaseGroupModel p_UserCloseBaseGroupModel,int p_PageNumber,int p_StepRow )
	{
		ConfigUserCloseBaseGroup m_ConfigUserCloseBaseGroup=new ConfigUserCloseBaseGroup();
		List m_list=m_ConfigUserCloseBaseGroup.getList(p_UserCloseBaseGroupModel,p_PageNumber,p_StepRow);
		return m_list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
