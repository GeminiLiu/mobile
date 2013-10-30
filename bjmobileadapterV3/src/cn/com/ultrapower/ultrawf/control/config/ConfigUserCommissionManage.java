package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.ConfigUserCommission;
import cn.com.ultrapower.ultrawf.models.config.ParConfigUserCommissionModel;

public class ConfigUserCommissionManage {

	
	public List getList(ParConfigUserCommissionModel p_ConfigUserCommissionModel,int p_PageNumber,int p_StepRow )
	{
		ConfigUserCommission m_ConfigUserCommission=new ConfigUserCommission();
		List m_list=m_ConfigUserCommission.getList(p_ConfigUserCommissionModel,p_PageNumber,p_StepRow);
		return m_list;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
