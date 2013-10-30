package cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata.Group;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.GroupInfo;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata.People;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

public class GroupSynchronization {
	
	static final Logger logger 	= (Logger) Logger.getLogger(GroupSynchronization.class);

	/**
	 * <p>Description:修改组信息时同步其子节点下所有相应信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-12
	 * @param groupId		当前需要修改的组的ID(可以根据ID查找修改前的组信息)
	 * @param groupInfo		当前组的修改后的信息
	 * @return boolean
	 */
	public boolean modifySynchronization(String groupId,GroupInfo groupInfo)
	{
		try 
		{
			//查得当前节点的子节点的组信息
			String sql="select b,a.c630000037,a.c630000019,a.c630000021,a.c630000020 from SysGrouppo b,SysGrouppo a where a.c1 = '"+groupId+"' and b.c630000037 like '%'||a.c630000037||'%'";

			List list= HibernateDAO.queryObject(sql);

			//根据传来的bean得到修改后的组信息
			String newDnId				= groupInfo.getGroupDnId();
			String newGroupFullName		= groupInfo.getGroupFullname();
			String newGroupParentId		= groupInfo.getGroupParentid();
			String newGroupCompanyId	= groupInfo.getGroupCompanyid();
			String newGroupType			= groupInfo.getGroupType();
			//不包含当前组
			if(list.size()>1)
			{
				//实例化组信息数据库操作类
				Group group	= new Group();
				//该组下的人的单位ID
				String userCPID	= modifyUserCompanyId(newGroupCompanyId,groupId,newGroupType);
				//该组下的人的部门ID
				String userDPID = modifyUserDepartmentId(newGroupParentId,groupId,newGroupType);
				
				for(Iterator it = list.iterator();it.hasNext();)
				{
					Object[] obj			= (Object[])it.next();
					SysGrouppo grouppo		= (SysGrouppo)obj[0];
					String oldDnId			= (String)obj[1];
					String oldGroupFullName	= (String)obj[2];
					String oldGroupType		= (String)obj[3];
					String oldParentId		= (String)obj[4];
					
					//如果是当前节点
					if(grouppo.getC1().equals(groupId))
					{
						//同步该组下的所有人
						modifyUserInfo(groupId,userCPID,userDPID);
						continue;
					}
					//如果当前组的子节点,则进行同步操作
					else
					{
						//子节点的bean
						GroupInfo subNodeGroupInfo = new GroupInfo();
						
						subNodeGroupInfo.setGroupName(Function.nullString(grouppo.getC630000018()));
						subNodeGroupInfo.setGroupOrderBy(Function.nullString(grouppo.getC630000022()));
						subNodeGroupInfo.setGroupPhone(Function.nullString(grouppo.getC630000023()));
						subNodeGroupInfo.setGroupFax(Function.nullString(grouppo.getC630000024()));
						subNodeGroupInfo.setGroupStatus(Function.nullString(grouppo.getC630000025()));
						subNodeGroupInfo.setGroupDesc(Function.nullString(grouppo.getC630000028()));
						subNodeGroupInfo.setGroupIntId(Function.nullLong(grouppo.getC630000030()).intValue());
						subNodeGroupInfo.setGroupCompanytype(Function.nullString(grouppo.getC630000027()));
						subNodeGroupInfo.setGroupType(Function.nullString(grouppo.getC630000021()));
						subNodeGroupInfo.setGroupParentid(Function.nullString(grouppo.getC630000020()));
						
						//如果父节点组类型为部门且该部门下没有部门,可升级为公司或降级为普通组,值班组,其他或平迁,则其下边的子节点
						if(oldGroupType.equals("3"))
						{
							//若当前父组ID没有改变
							if(oldParentId.equals(newGroupParentId))
							{	
								subNodeGroupInfo.setGroupCompanyid(userCPID);
								//subNodeGroupInfo.setGroupFullname(grouppo.getC630000019());
								subNodeGroupInfo.setGroupFullname(grouppo.getC630000019().replaceFirst(oldGroupFullName,newGroupFullName));
								subNodeGroupInfo.setGroupDnId(grouppo.getC630000037());
							}
							//若当前父组ID改变
							else
							{
								subNodeGroupInfo.setGroupCompanyid(userCPID);
								//设置新的groupFullName
								subNodeGroupInfo.setGroupFullname(grouppo.getC630000019().replaceFirst(oldGroupFullName,newGroupFullName));
								//设置新的DnId
								subNodeGroupInfo.setGroupDnId(grouppo.getC630000037().replaceFirst(oldDnId,newDnId));	
							}
						}
						//如果当前组类型为剩下的情况,则其下边的子节点
						else
						{
							//如果当前组的父组ID不变
							if(oldParentId.equals(newGroupParentId))
							{		
								subNodeGroupInfo.setGroupCompanyid(grouppo.getC630000026());
								//	subNodeGroupInfo.setGroupFullname(grouppo.getC630000019());
								subNodeGroupInfo.setGroupFullname(grouppo.getC630000019().replaceFirst(oldGroupFullName,newGroupFullName));
								subNodeGroupInfo.setGroupDnId(grouppo.getC630000037());
							}
							//如果当前组的父组ID改变
							else
							{
								subNodeGroupInfo.setGroupCompanyid(newGroupCompanyId);
								//设置新的groupFullName
								subNodeGroupInfo.setGroupFullname(grouppo.getC630000019().replaceFirst(oldGroupFullName,newGroupFullName));
								//设置新的DnId
								subNodeGroupInfo.setGroupDnId(grouppo.getC630000037().replaceFirst(oldDnId,newDnId));
							}
						}
						
						//同步子节点相应组信息
						group.modifyGroup(subNodeGroupInfo,grouppo.getC1());
					}
				}
			}
			else
			{
				logger.info("[503]GroupSynchronization.modifyListDnId() 无需要更新的组信息");
			}
			
			return true;
		} 
		catch (Exception e) 
		{
			logger.error("[503]GroupSynchronization.modifyListDnId() 修改组信息时同步其子节点下所有相应信息失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:修改组信息时同步子节点相应用户信息的单位ID<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-16
	 * @param newcompanyId		修改后的组的单位ID
	 * @param groupId			当前要修改的组的ID
	 * @param newgroupType		修改后的组的组类型
	 * @return String
	 */
	private String modifyUserCompanyId(String newcompanyId,String groupId,String newgroupType)
	{
		try
		{
			//需要同步的用户的单位ID
			String userCPID	= "";
			//修改后的组的组类型为单位,则该组下的人的单位ID为该组的组ID
			if(newgroupType.equals("2"))
			{
				userCPID = groupId;
			}
			else
			{
				userCPID = newcompanyId;
			}
			return userCPID;
		}
		catch(Exception e)
		{
			logger.error("[507]GroupSynchronization.modifyUserCompanyId() 修改组信息时同步相应用户信息的单位ID失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:修改组信息时同步子节点相应用户信息的部门ID<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-20
	 * @param newgroupParentId		修改后的组的父组ID
	 * @param groupId				当前要修改的组的ID
	 * @param newgroupType			修改后的组的组类型
	 * @return
	 */
	private String modifyUserDepartmentId(String newgroupParentId,String groupId,String newgroupType)
	{
		GetGroupInfoList groupInfoList = new GetGroupInfoList();
		try
		{
			//用户的部门ID
			String userDPID		= "";
			//如果修改后的组的组类型为部门
			if(newgroupType.equals("3"))
			{
				userDPID = groupId;
			}
			//如果修改后的组的组类型为公司
			else if(newgroupType.equals("2"))
			{
				userDPID = "";
			}
			//如果修改后的组的组类型为普通组,值班组
			else if(!newgroupType.equals("1")&&!newgroupType.equals("2")&&!newgroupType.equals("3"))
			{
				//获取该组的父节点的组类型
				String group_type = groupInfoList.getGroupInfo(newgroupParentId).getC630000021();
				//若该组的父节点为公司
				if(group_type.equals("2"))
				{
					userDPID = "";
				}
				//若该组的父节点为部门
				else if(group_type.equals("3"))
				{
					userDPID = newgroupParentId;
				}
				//若该组的父节点为普通组或者值班组
				else if(!group_type.equals("1")&&!group_type.equals("2")&&!group_type.equals("3"))
				{
					GetUserInfoList getUserInfoList = new GetUserInfoList();
					//递归取得组类型为公司或部门的ID
					String iteratorID = getUserInfoList.flagIdFind(groupId);
					if(groupInfoList.getGroupInfo(iteratorID).getC630000021().equals("2"))
					{
						userDPID = "";
					}
					else
					{
						userDPID = iteratorID;
					}
				}
			}
			
			return userDPID;
			
		}
		catch(Exception e)
		{
			logger.error("[506]GroupSynchronization.findParentNodeInfo() 修改组信息时同步相应用户信息的部门ID失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:修改组信息时同步相应用户信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-13
	 * @param groupId		当前需要修改的组的ID(可以根据ID查找修改前的组信息)		
	 * @param userDPID		该组下的人的单位ID		
	 * @param userCPID		该组下的人的部门ID
	 * @return boolean
	 */
	private boolean modifyUserInfo(String groupId,String userCPID,String userDPID)
	{
		try
		{
			GetUserInfoList userInfoList	= new GetUserInfoList();
			//部门ID为Null相应公司下的人
			List userCpIdList				= userInfoList.getListByUserCp(groupId);
			//相应部门下的人
			List userDpIdList				= userInfoList.getListByUserDp(groupId);
			
			if(userCpIdList.size()>0||userDpIdList.size()>0)
			{
				//实例化用户信息数据库操作类
				People people				= new People();
				
				if(userCpIdList.size()>0)
				{
					for(Iterator it = userCpIdList.iterator();it.hasNext();)
					{
						SysPeoplepo peoplepo	= (SysPeoplepo)it.next();
						//bean的信息
						PeopleInfo peopleInfo	= new PeopleInfo();
						
						peopleInfo.setUserLoginname(Function.nullString(peoplepo.getC630000001()));
						peopleInfo.setUserPassword(Function.nullString(peoplepo.getC630000002()));
						peopleInfo.setUserFullname(Function.nullString(peoplepo.getC630000003()));
						peopleInfo.setUserCreateuser(Function.nullString(peoplepo.getC630000004()));
						peopleInfo.setUserPosition(Function.nullString(peoplepo.getC630000005()));
						peopleInfo.setUserIsmanager(Function.nullString(peoplepo.getC630000006()));
						peopleInfo.setUserType(Function.nullString(peoplepo.getC630000007()));
						peopleInfo.setUserMobie(Function.nullString(peoplepo.getC630000008()));
						peopleInfo.setUserPhone(Function.nullString(peoplepo.getC630000009()));
						peopleInfo.setUserFax(Function.nullString(peoplepo.getC630000010()));
						peopleInfo.setUserMail(Function.nullString(peoplepo.getC630000011()));
						peopleInfo.setUserStatus(Function.nullString(peoplepo.getC630000012()));
						peopleInfo.setUserCptype(Function.nullString(peoplepo.getC630000014()));
						peopleInfo.setUserLicensetype(Function.nullString(peoplepo.getC630000016()));
						peopleInfo.setUserOrderby(Function.nullString(peoplepo.getC630000017()));
						peopleInfo.setUserIntId(peoplepo.getC630000029().intValue());
						peopleInfo.setUserBelongGroupId(Function.nullString(peoplepo.getC630000036()));
						peopleInfo.setUserCpid(userCPID);
						peopleInfo.setUserDpid(userDPID);
						
						//修改该用户
						people.modifyPeople(peopleInfo,peoplepo.getC1(),"managerModify");
					}
				}
				if(userDpIdList.size()>0)
				{
					for(Iterator it = userDpIdList.iterator();it.hasNext();)
					{
						SysPeoplepo peoplepo	= (SysPeoplepo)it.next();
						//bean的信息
						PeopleInfo peopleInfo	= new PeopleInfo();
						
						peopleInfo.setUserLoginname(Function.nullString(peoplepo.getC630000001()));
						peopleInfo.setUserPassword(Function.nullString(peoplepo.getC630000002()));
						peopleInfo.setUserFullname(Function.nullString(peoplepo.getC630000003()));
						peopleInfo.setUserCreateuser(Function.nullString(peoplepo.getC630000004()));
						peopleInfo.setUserPosition(Function.nullString(peoplepo.getC630000005()));
						peopleInfo.setUserIsmanager(Function.nullString(peoplepo.getC630000006()));
						peopleInfo.setUserType(Function.nullString(peoplepo.getC630000007()));
						peopleInfo.setUserMobie(Function.nullString(peoplepo.getC630000008()));
						peopleInfo.setUserPhone(Function.nullString(peoplepo.getC630000009()));
						peopleInfo.setUserFax(Function.nullString(peoplepo.getC630000010()));
						peopleInfo.setUserMail(Function.nullString(peoplepo.getC630000011()));
						peopleInfo.setUserStatus(Function.nullString(peoplepo.getC630000012()));
						peopleInfo.setUserCptype(Function.nullString(peoplepo.getC630000014()));
						peopleInfo.setUserLicensetype(Function.nullString(peoplepo.getC630000016()));
						peopleInfo.setUserOrderby(Function.nullString(peoplepo.getC630000017()));
						peopleInfo.setUserIntId(peoplepo.getC630000029().intValue());
						peopleInfo.setUserBelongGroupId(Function.nullString(peoplepo.getC630000036()));
						peopleInfo.setUserCpid(userCPID);
						peopleInfo.setUserDpid(userDPID);
						
						//修改该用户
						people.modifyPeople(peopleInfo,peoplepo.getC1(),"managerModify");
					}
				}
			}
			else
			{
				logger.error("[508]GroupSynchronization.modifyUserInfo() 无相应用户信息需要同步");
			}

			return true;
		}
		catch(Exception e)
		{
			logger.error("[508]GroupSynchronization.modifySynchronization() 修改组信息时同步相应用户信息失败"+e.getMessage());
			return false;
		}
	}
	
	public static void main(String[] args)
	{
//		GroupFind groupFind = new GroupFind();
//		groupFind.cpnodefind("000000000600003","Demo");
		GroupSynchronization groupSynchronization = new GroupSynchronization();
		Group group			= new Group();
		
//		GroupInfo subNodeGroupInfo	= new GroupInfo();
//		subNodeGroupInfo.setGroupName("组一");
//		subNodeGroupInfo.setGroupFullname("集团公司.省公司.市公司一.公司一部门二.组一");
//		subNodeGroupInfo.setGroupParentid("000000000600007");
//		subNodeGroupInfo.setGroupType("0");
//		subNodeGroupInfo.setGroupOrderBy("1");
//		subNodeGroupInfo.setGroupPhone("1111111");
//		subNodeGroupInfo.setGroupFax("1111111");
//		subNodeGroupInfo.setGroupStatus("0");
//		subNodeGroupInfo.setGroupCompanyid("000000000600004");
//		subNodeGroupInfo.setGroupCompanytype("3");
//		subNodeGroupInfo.setGroupDesc("1");
//		subNodeGroupInfo.setGroupIntId(600009);
//		subNodeGroupInfo.setGroupDnId("0;000000000600002;000000000600003;000000000600004;000000000600007;000000000600009;");
//		
//		groupSynchronization.modifySynchronization("000000000600009",subNodeGroupInfo);
//		group.modifyGroup(subNodeGroupInfo,"000000000600009");
		
		GroupInfo subNodeGroupInfo	= new GroupInfo();
		subNodeGroupInfo.setGroupName("公司一部门二");
		subNodeGroupInfo.setGroupFullname("集团公司.省公司.省公司部门一.公司一部门二");
		subNodeGroupInfo.setGroupParentid("000000000600008");
		subNodeGroupInfo.setGroupType("3");
		subNodeGroupInfo.setGroupOrderBy("1");
		subNodeGroupInfo.setGroupPhone("1111111");
		subNodeGroupInfo.setGroupFax("1111111");
		subNodeGroupInfo.setGroupStatus("0");
		subNodeGroupInfo.setGroupCompanyid("000000000600003");
		subNodeGroupInfo.setGroupCompanytype("1");
		subNodeGroupInfo.setGroupDesc("1");
		subNodeGroupInfo.setGroupIntId(600007);
		subNodeGroupInfo.setGroupDnId("0;000000000600002;000000000600003;000000000600008;000000000600007;");
		
		groupSynchronization.modifySynchronization("000000000600007",subNodeGroupInfo);
		group.modifyGroup(subNodeGroupInfo,"000000000600007");
	}

}
