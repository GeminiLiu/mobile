package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class SysSkillConferSync
{

	public void skillconfersync(String roleid,String commissionid,String skillcommissiontype)
	{
		GetRole getrole = new GetRole();
		SysSkillpo skillinfo = getrole.getSkillInfo(roleid);
		RoleBean roleinfo = new RoleBean();
		
		roleinfo.setSkill_Action(Function.nullString(skillinfo.getC610000010()));
		roleinfo.setSkill_CategoryQueryID(Function.nullString(skillinfo.getC610000009()));
		roleinfo.setSkill_CommissionCloseTime(skillinfo.getC610000015().longValue());
		roleinfo.setSkill_GroupID(Function.nullString(skillinfo.getC610000011()));
		roleinfo.setSkill_Module(Function.nullString(skillinfo.getC610000008()));
		roleinfo.setSkill_Status(Function.nullString(skillinfo.getC610000018()));
		roleinfo.setSkill_Type(Function.nullString(skillinfo.getC610000006()));
		roleinfo.setSkill_UserID(Function.nullString(skillinfo.getC610000007()));
		roleinfo.setSkill_WorkFlowType(Function.nullString(skillinfo.getC610000019()));
		
		roleinfo.setSkill_CommissionUID(commissionid);
		roleinfo.setSkill_CommissionGID(skillcommissiontype);
		Role role = new Role();
		boolean bl = role.roleModify(roleid,roleinfo);
		if(bl)
		{
			System.out.println(" 同步技能表代办人成功！");
		}
		else
		{
			System.out.println(" 同步技能表代办人失败");
		}
		
	}

	public static void main(String args[])
	{
		SysSkillConferSync confersync = new SysSkillConferSync();
		confersync.skillconfersync("000000000000061","","");
	}
}
