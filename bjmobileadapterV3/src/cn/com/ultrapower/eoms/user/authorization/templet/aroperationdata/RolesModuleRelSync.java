package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.authorization.bean.GroupUserBean;
import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.authorization.bean.SendScopeBean;
import cn.com.ultrapower.eoms.user.authorization.role.aroperationdata.Role;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata.SendScope;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage.GetSendScope;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSendManagepo;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSkillManagepo;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class RolesModuleRelSync
{

	/**
	 * 将技能管理表中的记录同步到技能表中。
	 * 日期 2007-2-2
	 * @author wangyanguang
	 */
	public void insertToSkill(List list,RolesSkillManagepo skillmanagepo)
	{
		
		for(Iterator it = list.iterator();it.hasNext();)
		{
			Role role       	= new Role();
			GroupUserBean groupuserbean = (GroupUserBean)it.next();
			String userid 		= Function.nullString(groupuserbean.getUserid());
			String groupid 		= Function.nullString(groupuserbean.getGroupid());
			String sourceid		= Function.nullString(skillmanagepo.getC660000007());
			String skillaction	= Function.nullString(skillmanagepo.getC660000009());
			System.out.println("技能动作列表："+skillaction);
			if(!userid.equals("")&&!groupid.equals(""))
			{
				//判断用户ID、组ID、资源ID在技能表中是否存在，如果存在不同步数据，否则同步。
				boolean flag = GetRole.isExists(userid,groupid,sourceid,skillaction);
				if(!flag)
				{
					System.out.println("用户ID："+userid+",组ID："+groupid+",技能动作ID:"+skillaction+",资源ID："+sourceid);
					RoleBean roleinfo 	= new RoleBean();
					//同步
					roleinfo.setSkill_UserID(userid);
					roleinfo.setSkill_GroupID(groupid);
					roleinfo.setSkill_Module(sourceid);
					roleinfo.setSkill_Action(skillaction);
					roleinfo.setSkill_CategoryQueryID("");
					roleinfo.setSkill_CommissionCloseTime(0);
					roleinfo.setSkill_CommissionGID("");
					roleinfo.setSkill_CommissionUID("");
					roleinfo.setSkill_Status("0");
					roleinfo.setSkill_Type("");
					roleinfo.setSkill_WorkFlowType("2");
					
					role.roleInsert(roleinfo);
				}
			}else
			{
				System.out.println("组与用户Bean中组ID与用户ID有空值！");
			}
		}
	}
	
	/**
	 * 将派发管理表中的记录同步到工单派发表中
	 * 日期 2007-2-2
	 * @author wangyanguang
	 */
	public void insertToSend(List list,RolesSendManagepo sendmanagepo)
	{
		SendScopeBean sendscopebean = new SendScopeBean();
		SendScope		sendscope	= new SendScope();
		
		for(Iterator it = list.iterator();it.hasNext();)
		{
			GroupUserBean groupuserbean = (GroupUserBean)it.next();
			String userid 		= Function.nullString(groupuserbean.getUserid());
			String groupid 		= Function.nullString(groupuserbean.getGroupid());
			String sourceid		= Function.nullString(sendmanagepo.getC660000015());
			String sendgroupid 	= Function.nullString(sendmanagepo.getC660000016());
			String sendtype		= Function.nullString(sendmanagepo.getC660000019());
			if(!userid.equals("")&&!groupid.equals(""))
			{
				//判断用户ID、组ID、资源ID在派发表中是否存在，如果存在不同步数据，否则同步。
				boolean flag = GetSendScope.isExists(userid,groupid,sendgroupid,sourceid,sendtype);
				if(!flag)
				{
					System.out.println("用户ID："+userid+",组ID："+groupid+",派发组ID:"+sendgroupid+",资源ID："+sourceid);
					//同步
					sendscopebean.setManageGroupUser_UserID(userid);
					sendscopebean.setManageGroupUser_GroupID(groupid);
					sendscopebean.setManageGroupUser_Desc(Function.nullString(sendmanagepo.getC660000018()));
					sendscopebean.setManageGroupUser_Memo1("");
					sendscopebean.setManageGroupUser_Memo2("");
					sendscopebean.setManageGroupUser_Memo3("");
					sendscopebean.setManageGroupUser_MType("1");
					sendscopebean.setManageGroupUser_RoleID(sendgroupid);
					sendscopebean.setManageGroupUser_Type("0");
					sendscopebean.setManagerGroupUser_Source(sourceid);
					
					sendscope.sendScopeInsert(sendscopebean);
				}
			}else
			{
				System.out.println("组与用户Bean中组ID与用户ID有空值！");
			}
		}
	}
	
	

}
