package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.ConfigGroupIsSnatch;
import cn.com.ultrapower.ultrawf.models.config.ConfigGroupIsSnatchModel;
import cn.com.ultrapower.ultrawf.models.config.ParConfigGroupIsSnatchModel;

public class ConfigGroupIsSnatchManage {
	
	public List getList(ParConfigGroupIsSnatchModel p_ParConfigGroupIsSnatchModel,int p_PageNumber,int p_StepRow )
	{
		ConfigGroupIsSnatch m_ConfigGroupIsSnatch=new ConfigGroupIsSnatch();
		List m_List=m_ConfigGroupIsSnatch.getList(p_ParConfigGroupIsSnatchModel,p_PageNumber,p_StepRow);
		return m_List;
	}
	
	
	public ConfigGroupIsSnatchModel GetOneForKey(String p_Configid) 
	{
		ConfigGroupIsSnatchModel m_ConfigGroupIsSnatchModel=null;
		ConfigGroupIsSnatch m_ConfigGroupIsSnatch=new ConfigGroupIsSnatch();
		m_ConfigGroupIsSnatchModel=m_ConfigGroupIsSnatch.GetOneForKey(p_Configid);
		return m_ConfigGroupIsSnatchModel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
