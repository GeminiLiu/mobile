package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;;
public class SkillAndSendSync
{

	/**
	 * 资源授权同步
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public boolean skillsync(String usergroupid,String roleid)
	{
//		System.out.println("正在进行技能表数据同步.....");
//		List usergroupinfo = getUserGroupInfo(usergroupid);
//		//查询角色与技能关联表得到技能信息Bean.
//		List skillList = getSkillInfo(roleid);
//		for(Iterator it = skillList.iterator();it.hasNext();)
//		{
//			RolesSkillManagepo rolesmanagepo = (RolesSkillManagepo)it.next();
//			//同步记录
//			insertToSkill(usergroupinfo,rolesmanagepo);
//		}
//		System.out.println("技能表中数据同步结束！");
		return false;
	}
	/**
	 * 派发授权同步
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public boolean sendsync(String usergroupid,String roleid)
	{
//		System.out.println("正在进行派发表数据同步......");
//		
//		//根据传入的复选框值将其分离成用户和组写到Bean中，再将其添加到List中。
//		List usergroupinfo = getUserGroupInfo(usergroupid);
//		//查询角色与技能关联表得到派发技能信息Bean.
//		List sendList  = getSendInfo(roleid);
//		for(Iterator it = sendList.iterator();it.hasNext();)
//		{
//			RolesSendManagepo rolesmanagepo = (RolesSendManagepo)it.next();
//			//同步记录
//			insertToSend(usergroupinfo,rolesmanagepo);
//		}
//		System.out.println("派发表中数据同步结束！");
		return true;
	}
	
	/**
	 * 将技能管理表中的记录同步到技能表中。
	 * 日期 2007-1-9
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
			String workflowtype	= Function.nullString(skillmanagepo.getC660000013());
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
					//启用
					roleinfo.setSkill_Status("0");
					roleinfo.setSkill_Type("");
					roleinfo.setSkill_WorkFlowType(workflowtype);
					
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
	 * 日期 2007-1-9
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
					sendscopebean.setManageGroupUser_Type(sendtype);
					sendscopebean.setManagerGroupUser_Source(sourceid);
					
					sendscope.sendScopeInsert(sendscopebean);
				}
			}else
			{
				System.out.println("组与用户Bean中组ID与用户ID有空值！");
			}
		}
	}
	
	
	
	
	/**
	 * 根据组、用户复选框返回值，将组与用户分离后添加到List中。
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public List getUserGroupInfo(String usergroupid)
	{
		List returnList = new ArrayList();
		SyncSQL synsql = new SyncSQL();
		String[] str = usergroupid.split(",");
		
		for (int j = 0; j < str.length; j++) 
		{
			String[] tmpinfo = str[j].split(";");
			if(tmpinfo[1].equals("1"))
			{
				//关联组成员表。
				List list = synsql.getUserID(tmpinfo[0]);
				for(Iterator it = list.iterator();it.hasNext();)
				{
					GroupUserBean groupuserinfo = new GroupUserBean();
					String userid = (String)it.next();
					groupuserinfo.setGroupid(tmpinfo[0]);
					groupuserinfo.setUserid(userid);
					returnList.add(groupuserinfo);
				}
			}
			else
			{
				GroupUserBean groupuserinfo = new GroupUserBean();
				groupuserinfo.setGroupid(tmpinfo[1]);
				groupuserinfo.setUserid(tmpinfo[0]);
				returnList.add(groupuserinfo);
			}
		}
		if(returnList!=null)
		{
			return returnList;
		}
		else
		{
			return null;
		}
	}
	
	
	/**
	 * (组成员添加时，自动添加此成员权限接口)
	 * 根据传入的用户ID与组ID查询出此用户ID和组ID所满足的角色ID
	 * 然后再根据角色ID查询出技能动作，将此动作同步到技能与派发表中。
	 * 日期 2007-1-12
	 * @author wangyanguang
	 * @param userid       用户ID
	 * @param groupid 	   组ID	
	 */
	public boolean groupUserSync(List groupuserinfo)
	{
		boolean flag = false;
//		if(groupuserinfo!=null)
//		{
//			for(Iterator its = groupuserinfo.iterator();its.hasNext();)
//			{
//				GroupUserBean groupuserbean = (GroupUserBean)its.next();
//				String groupid 	= Function.nullString(groupuserbean.getGroupid());
//				if(!groupid.equals(""))
//				{
//					SyncSQL syncsql = new SyncSQL();
//					List roleidlist = syncsql.getRoleid(groupid);
//					for(Iterator it = roleidlist.iterator();it.hasNext();)
//					{
//						String roleid = (String)it.next();
//						flag = skillsync(groupuserinfo,roleid);
//						flag = sendsync(groupuserinfo,roleid);
//					}
//				}
//				else
//				{
//					System.out.println("组ID为空，不能执行同步！");
//				}
//			}
//		}
		return flag;
		
	}
	
	
	/**
	 * 资源授权同步(组成员变时执行)
	 * 日期 2007-1-12
	 * @author wangyanguang
	 */
	public boolean skillsync(List usergroupinfo,String roleid)
	{
//		System.out.println("正在进行技能表数据同步.....");
//		System.out.println("角色ID："+roleid);
//		//查询角色与技能关联表得到技能信息Bean.
//		List skillList = getSkillInfo(roleid);
//		for(Iterator it = skillList.iterator();it.hasNext();)
//		{
//			RolesSkillManagepo rolesmanagepo = (RolesSkillManagepo)it.next();
//			//同步记录
//			insertToSkill(usergroupinfo,rolesmanagepo);
//		}
//		System.out.println("技能表中数据同步结束！");
		return false;
	}
	
	/**
	 * 派发授权同步(组成员改变时执行)
	 * 日期 2007-1-12
	 * @author wangyanguang
	 */
	public boolean sendsync(List usergroupinfo,String roleid)
	{
//		System.out.println("正在进行派发表数据同步......");
//		System.out.println("角色ID："+roleid);
//		//查询角色与技能关联表得到派发技能信息Bean.
//		List sendList  = getSendInfo(roleid);
//		for(Iterator it = sendList.iterator();it.hasNext();)
//		{
//			RolesSendManagepo rolesmanagepo = (RolesSendManagepo)it.next();
//			//同步记录
//			insertToSend(usergroupinfo,rolesmanagepo);
//		}
//		System.out.println("派发表中数据同步结束！");
		return false;
	}
	/**
	 * 根据角色ID查询出所有资源授权管理表中的记录
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public List getSkillInfo(String roleid)
	{
		StringBuffer sql =  new StringBuffer();
		sql.append(" from RolesSkillManagepo skillmanage ");
		sql.append(" where skillmanage.c660000006='"+roleid+"'");
		System.out.println(sql.toString());
		try 
		{
			List list 	= HibernateDAO.queryObject(sql.toString());
			return list;
		} 
		catch (Exception e) 
		{
			System.out.println("750 SkillAndSendSync 类中 " +
					"getSkillInfo(String roleid) 方法执行查询时出现异常！");
			return null;
		}
	}
	
	/**
	 * 根据角色ID查询出所有派发授权管理一中的记录。
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public List getSendInfo(String roleid)
	{
		StringBuffer sql =  new StringBuffer();
		
		sql.append(" from RolesSendManagepo sendmanage ");
		sql.append(" where sendmanage.c660000014='"+roleid+"'");
		System.out.println("查询派发管理中的ＳＱＬ："+sql.toString());
		try 
		{
			List list 	= HibernateDAO.queryObject(sql.toString());
			return list;
		} 
		catch (Exception e) 
		{
			System.out.println("750 SkillAndSendSync 类中 " +
					"getSendInfo(String roleid) 方法执行查询时出现异常！");
			return null;
		}
	
	}
	public static void main(String args[])
	{
		SkillAndSendSync synco = new SkillAndSendSync();
		GroupUserBean groupuserbean = new GroupUserBean();
		groupuserbean.setGroupid("000000000600001");
		groupuserbean.setUserid("000000000000044");
		List list = new ArrayList();
		list.add(groupuserbean);
		synco.groupUserSync(list);
	}
}
