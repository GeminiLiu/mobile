package com.ultrapower.eoms.mobile.util;

import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.authorization.role.aroperationdata.Role;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.menu.aroperationdata.GrandActionConfig;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.dbmanage.GrandActionConfigList;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.po.GrandActionConfigpo;

public class InsertGrandValue {

	/**
	 * 日期 2006-11-16
	 * 
	 * @author wangyanguang/王彦广 
	 * @param args void
	 *
	 */
	public static void main(String[] args) 
	{
		InsertGrandValue insertvalue = new InsertGrandValue();
		insertvalue.Insert2Role("187","1000");
		
	}
	/**
	 * 
	 * 日期 2006-11-25
	 * 
	 * @author wangyanguang/王彦广 
	 * @param parentID
	 * @param childID
	 * @return boolean
	 *
	 */
	public boolean Insert2Role(String parentID,String childID)
	{
		boolean flag = false;
		try
		{
			GetRole getRole = new GetRole();
			List roleList = getRole.getRoleInfo(parentID);
			if(roleList!=null)
			{
				Iterator it = roleList.iterator();
				while(it.hasNext())
				{
					SysSkillpo sysskillpo = (SysSkillpo)it.next();
					RoleBean roleBean = skill2RoleBean(sysskillpo,childID);
					Role role = new Role();
					role.roleInsert(roleBean);
				}
				flag = Insert2ActionGrand(parentID,childID);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public RoleBean skill2RoleBean(SysSkillpo sysskillpo,String childID)
	{
		RoleBean roleBean = new RoleBean();
		roleBean.setSkill_Module(childID);
		roleBean.setSkill_Action(Function.nullString(sysskillpo.getC610000010()));
		roleBean.setSkill_CategoryQueryID(Function.nullString(sysskillpo.getC610000009()));
		roleBean.setSkill_CommissionCloseTime(sysskillpo.getC610000015().longValue());
		roleBean.setSkill_CommissionGID(Function.nullString(sysskillpo.getC610000012()));
		roleBean.setSkill_CommissionUID(Function.nullString(sysskillpo.getC610000014()));
		roleBean.setSkill_GroupID(Function.nullString(sysskillpo.getC610000011()));
		roleBean.setSkill_Status(Function.nullString(sysskillpo.getC610000018()));
		roleBean.setSkill_Type(Function.nullString(sysskillpo.getC610000006()));
		roleBean.setSkill_UserID(Function.nullString(sysskillpo.getC610000007()));
		roleBean.setSkill_WorkFlowType(Function.nullString(sysskillpo.getC610000019()));
		
		return roleBean;
	}
	
	public boolean Insert2ActionGrand(String parentID,String childID)
	{
		GrandActionConfigList grandaction = new GrandActionConfigList();
		List actionList = null;
		try
		{
			actionList = grandaction.getMenuId(parentID);
			if(actionList!=null)
			{
				Iterator it = actionList.iterator();
				while(it.hasNext())
				{
					GrandActionConfigpo grandpo = (GrandActionConfigpo)it.next();
					GrandActionConfigBean grandbean = grandpo2Bean(grandpo,childID);
					GrandActionConfig grandconfig = new GrandActionConfig();
					grandconfig.grandActionConfigInsert(grandbean);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception e");
			return false;
		}
		
		return true;
		//return false;
	}

	public GrandActionConfigBean grandpo2Bean(GrandActionConfigpo grandpo,String childID)
	{
		GrandActionConfigBean grandbean = new GrandActionConfigBean();
		grandbean.setDropDownConf_FieldID(Function.nullString(childID));
		grandbean.setDropDownConf_FieldValue(Function.nullString(grandpo.getC620000033()));
		grandbean.setDropDownConf_NumValue(Function.nullString(grandpo.getC620000034()));
		grandbean.setDropDownConf_OrderBy(Function.nullString(grandpo.getC620000035()));
		
		return grandbean;
	}
}
