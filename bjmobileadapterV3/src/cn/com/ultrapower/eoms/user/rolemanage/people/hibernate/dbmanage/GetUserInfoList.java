package cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata.People;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

/**
 * <p>Description:封装用户信息接口<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-19
 */
public class GetUserInfoList {
	
	static final Logger logger = (Logger) Logger.getLogger(GetUserInfoList.class);
	
	GetFormTableName getFormTableName	= new GetFormTableName();
	//管理者类型为资源管理员
	private String systemmanage			= getFormTableName.GetFormName("systemmanage");
	
	/**
	 * <p>Description:从UltraProcess:SysUser表查找所有用户信息<p>
     * @author wangwenzhuo
     * @creattime 2006-10-19
     * @return List
     */
    public List getUserList()
    {
    	
    	try 
		{
    		String sql = "from SysPeoplepo";
			return HibernateDAO.queryClearObject(sql);
		} 
		catch (Exception e) 
		{
			logger.error("[444]GetUserInfoList.getUserList() 从UltraProcess:SysUser表查找所有用户信息"+e.getMessage());
			return null;
		}
    }
    
    /**
     * <p>Description:通过userID查找用户信息<p> 
     * @author wangwenzhuo
     * @creattime 2006-10-19
     * @param userID
     * @return SysPeoplepo
     */
    public SysPeoplepo getUserInfoID(String userID)
    {
    	try
		{
//    		Session session			= HibernateSessionFactory.currentSession();
//			Transaction tx			= session.beginTransaction();
//			SysPeoplepo sysPeoplepo = (SysPeoplepo)session.load(SysPeoplepo.class,userID);
//			tx.commit();
//			HibernateSessionFactory.closeSession();

////			-----------shigang modify--------------
			SysPeoplepo sysPeoplepo = (SysPeoplepo)HibernateDAO.loadStringValue(SysPeoplepo.class,userID);

			return sysPeoplepo;
		}
		catch(Exception e)
		{
			logger.error("[445]GetUserInfoList.getUserInfoID() 通过userID查找用户信息失败"+e.getMessage());
			return null;
		}
    }
	
    /**
     * <p>Description:通过userLoginName名查找用户信息<p>
     * @author wangwenzhuo
     * @creattime 2006-10-19
     * @param userLoginName
     * @return SysPeoplepo
     */
    public SysPeoplepo getUserInfoName(String userLoginName)
    {
    	try
    	{
    		String sql	= "from SysPeoplepo a where a.c630000001 ='"+userLoginName+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				SysPeoplepo people = (SysPeoplepo)it.next();
				return people;
			}

			logger.info("[446]GetUserInfoList.getUserInfoName() 系统中不存在该用户");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[446]GetUserInfoList.getUserInfoName() 通过userLoginName名查找用户信息失败"+e.getMessage());
			return null;
		}
    }  
    
    /**
     * <p>Description:组成员模块通过userDpId查找相应用户信息<p>
     * @author wangwenzhuo
     * @creattime 2006-10-24
     * @param userDpId
     * @return List
     */
    public List getUserDp(String userDpId)
    {
    	try
    	{
			SysGrouppo grouppo	= (SysGrouppo)HibernateDAO.loadStringValue(SysGrouppo.class,userDpId);
			
			String flag			= grouppo.getC630000021();
			
			if(flag.equals("3"))
			{
				String sql="from SysPeoplepo a where a.c630000015 ='"+userDpId+"' and a.c630000012='0' and not exists (select b.c620000028 from SysGroupUserpo b where b.c620000027='"+userDpId+"' and b.c620000028 = a.c1) order by a.c630000001,a.c630000017";
				List list= HibernateDAO.queryObject(sql);
				return list;
			}
			else if(flag.equals("2"))
			{
				String sql="from SysPeoplepo a where a.c630000013 ='"+userDpId+"' and a.c630000012='0'  and not exists (select b.c620000028 from SysGroupUserpo b where b.c620000027='"+userDpId+"' and b.c620000028 = a.c1) order by a.c630000001,a.c630000017";
				List list= HibernateDAO.queryObject(sql);
				return list;
			}
			else if(flag.equals("0")||flag.equals("1")||flag.equals("4")||flag.equals("5")||flag.equals("6")||flag.equals("7"))
			{
				//查找该情况下的部门ID或单位ID
				String dpid			= flagIdFind(grouppo.getC630000020());
				//根据c1查找该组的组类型
				SysGrouppo sysGrouppo = getGroupInfo(dpid);
				if(sysGrouppo!=null)
				{
					String group_Type	= sysGrouppo.getC630000021();
					//根据flagIdFind查得的dpid组类型只可能是公司或者部门
					if(group_Type.equals("2"))
					{
						String sql="from SysPeoplepo a where a.c630000013 ='"+dpid+"' and a.c630000012='0'  and not exists (select b.c620000028 from SysGroupUserpo b where b.c620000027='"+userDpId+"' and b.c620000028 = a.c1) order by a.c630000001,a.c630000017";
						List list= HibernateDAO.queryObject(sql);
						return list;
					}
					else
					{
						String sql="from SysPeoplepo a where a.c630000015 ='"+dpid+"' and a.c630000012='0'  and not exists (select b.c620000028 from SysGroupUserpo b where b.c620000027='"+userDpId+"' and b.c620000028 = a.c1) order by a.c630000001,a.c630000017";
						List list= HibernateDAO.queryObject(sql);
						return list;
					}	
				}
				else
				{
					logger.error("此组是顶级组，并且不是部门也不是公司，！！没有组成员");
				}
			}
			
			logger.info("[447]GetUserInfoList.getUserDp() 该组类型不存在");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[447]GetUserInfoList.getUserDp() 组成员模块通过userDpId查找相应用户信息失败"+e.getMessage());
			return null;
		}
    }  
   
    /**
     * <p>Description:查找节点为普通组、权限组、领导组、其他的情况下的部门ID或单位ID,在调用该方法的地方关闭session<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-26
	 * @param id
	 * @return String
	 */
	public String flagIdFind(String id)
	{
		String temp_para = "";
		try
		{
			//wuwenlong xiugai
			SysGrouppo grouppo		= (SysGrouppo)HibernateDAO.loadStringValue(SysGrouppo.class,id);

			if(grouppo!=null)
			{
				String flag				= grouppo.getC630000021();
				String groupParentId	= grouppo.getC630000020();
				String groupId			= grouppo.getC1();
				
				if(!flag.equals("3")&&!flag.equals("2"))
				{
					temp_para = flagIdFind(groupParentId);
					if(!String.valueOf(temp_para).equals("null")&&!String.valueOf(temp_para).equals(""))
					{
						return temp_para;
					}
					else
					{
						logger.error("此组没有上级部门和公司");
						return "0";
					}
				}
				else
				{
					return groupId;
				}
			}
			else
			{
				logger.error("此组没有上级部门和公司");
				return "0";
			}
		}
		catch(Exception e)
		{
			logger.error("[448]GetUserInfoList.flagIdFind() 查找节点为普通组、权限组、其他的情况下的部门ID或单位ID,在调用该方法的地方关闭session失败"+e.getMessage());
			return null;
		}		
	}
	
	/**
	 * <p>Description:根据组ID查找组信息,在调用该方法的地方关闭session<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-15
	 * @param groupID
	 * @return SysGrouppo
	 */
	private SysGrouppo getGroupInfo(String groupID)
	{
		try
		{
			SysGrouppo sysGrouppo	= (SysGrouppo)HibernateDAO.loadStringValue(SysGrouppo.class,groupID);
			return sysGrouppo;
		}
		catch(Exception e)
		{
			logger.error("[509]GetUserInfoList.getGroupInfo() 根据组ID查找组信息,在调用该方法的地方关闭session失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过用户的部门ID查找相应的用户信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-13
	 * @param userDpId
	 * @return List
	 */
	public List getListByUserDp(String userDpId)
	{
		try
    	{
			String sql = "from SysPeoplepo a where a.c630000015 ='"+userDpId+"'  and a.c630000012='0' ";
			return HibernateDAO.queryObject(sql);
		}
		catch(Exception e)
		{
			logger.error("[505]GetUserInfoList.getListByUserDp() 通过用户的部门ID查找相应的用户信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过用户的单位ID查找相应的部门ID为空的用户信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-16
	 * @param userCpId
	 * @return List
	 */
	public List getListByUserCp(String userCpId)
	{
		try
    	{
			String sql = "from SysPeoplepo a where a.c630000013 ='"+userCpId+"' and a.c630000012='0'  and a.c630000015 is null";
			return HibernateDAO.queryObject(sql);
		}
		catch(Exception e)
		{
			logger.error("[514]GetUserInfoList.getListByUserCp() 通过用户的单位ID查找相应的部门ID为空的用户信息失败"+e.getMessage());
			return null;
		}
	}
    
	/**
	 * <p>Description:通过用户登陆名查找该用户是否值班管理员<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-30
	 * @param userLoginName
	 * @return boolean
	 */
	public boolean isDutyArranger(String userIntId)
	{
		String sql = "from DutyArranger a where a.arrangerId = '"+userIntId+"'";
		
		try
		{
			List list = HibernateDAO.queryObject(sql);
			if(list.size()>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[538]GetUserInfoList.isDutyArranger() 通过用户登陆名查找该用户是否管理员失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:通过用户登陆名判断该用户是否系统管理员<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-6
	 * @param userLoginName
	 * @return boolean
	 */
	public boolean isSystemManagerByName(String userLoginName)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.c650000003 from SourceManagerpo a,Sourceconfig b,SysPeoplepo c");
		sql.append(" where a.c650000007 = c.c1 and c.c630000001 = '"+userLoginName+"' and a.c650000005 = '3'");
		sql.append(" and a.c650000001 = b.sourceId and b.sourceName = '"+systemmanage+"'");
		sql.append("  and c.c630000012='0' ");
		try
		{
			List list = HibernateDAO.queryObject(sql.toString());
			if(list.size()>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[539]GetUserInfoList.isSystemManagerByName() 通过用户登陆名判断该用户是否系统管理员失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:通过用户ID判断该用户是否系统管理员<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-6
	 * @param userId
	 * @return boolean
	 */
	public boolean isSystemManagerById(String userId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.c650000003 from SourceManagerpo a,Sourceconfig b");
		sql.append(" where a.c650000007 = '"+userId+"' and a.c650000005 = '3'");
		sql.append(" and a.c650000001 = b.sourceId and b.sourceName = '"+systemmanage+"'");
		
		try
		{
			List list = HibernateDAO.queryObject(sql.toString());
			if(list.size()>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[540]GetUserInfoList.isSystemManagerById() 通过用户ID判断该用户是否系统管理员失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:验证用户信息完整性</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-13
	 * @param userLoginName
	 * @return boolean
	 */
	public boolean isIntegrity(String userLoginName)
	{
		SysPeoplepo sysPeoplepo	= getUserInfoName(userLoginName);
		try
		{
			String userMobie				= Function.nullString(sysPeoplepo.getC630000008());
			String userPhone				= Function.nullString(sysPeoplepo.getC630000009());	
			String userFax					= Function.nullString(sysPeoplepo.getC630000010());
			String userMail					= Function.nullString(sysPeoplepo.getC630000011());
			
			if(userMobie.equals("")||userPhone.equals("")||userFax.equals("")||userMail.equals(""))
			{
				logger.info("[541]GetUserInfoList.isIntegrity() 用户信息不全,禁止登陆");
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(Exception e)
		{
			logger.error("[541]GetUserInfoList.isIntegrity() 验证用户信息完整性失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:通过userIntId查找用户信息</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-18
	 * @param userIntId
	 * @return SysPeoplepo
	 */
	public SysPeoplepo findByIntId(String userIntId)
	{
		try
    	{
    		String sql	= "from SysPeoplepo a where a.c630000029 ='"+userIntId+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				SysPeoplepo people = (SysPeoplepo)it.next();
				return people;
			}
			
			logger.info("[542]GetUserInfoList.findByIntId() 系统中不存在该用户");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[542]GetUserInfoList.findByIntId() 通过userIntId查找用户信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过userloginname查找用户信息</p>
	 * @author lihongbo
	 * @creattime 2010-11-11
	 * @param userloginname
	 * @return SysPeoplepo
	 */
	public SysPeoplepo findByUserLoginName(String userloginname)
	{
		try
    	{
    		String sql	= "from SysPeoplepo a where a.c630000001 ='"+userloginname+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				SysPeoplepo people = (SysPeoplepo)it.next();
				return people;
			}
			
			logger.info("[542]GetUserInfoList.findByIntId() 系统中不存在该用户");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[542]GetUserInfoList.findByIntId() 通过userIntId查找用户信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过userLoginName名查找用户密码信息</p>
	 * @author wangwenzhuo
	 * @creattime 2007-2-28
	 * @param userLoginName
	 * @return String
	 */
	public String findUserPassword(String userLoginName)
	{
		try
		{
			String sql	= "select a.c630000002 from SysPeoplepo a where a.c630000001 ='"+userLoginName+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				String userPassword = (String)it.next();
				return Function.nullString(userPassword);
			}

			logger.info("[547]GetUserInfoList.findUserPassword() 系统中不存在该用户");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[547]GetUserInfoList.findUserPassword() 通过userLoginName名查找用户密码信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过userLoginName名查找用户状态<p>
	 * @author wangwenzhuo
	 * @creattime 2007-5-8
	 * @param userLoginName
	 * @return String
	 */
	public String findUserStatus(String userLoginName)
	{
		try
		{
			String sql	= "select a.c630000012 from SysPeoplepo a where a.c630000001 ='"+userLoginName+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				String userStatus = (String)it.next();
				return Function.nullString(userStatus);
			}

			logger.info("[]GetUserInfoList.findUserStatus() 系统中不存在用户:"+userLoginName);
			return null;
		}
		catch(Exception e)
		{
			logger.error("[]GetUserInfoList.findUserStatus() 通过userLoginName名查找用户状态失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:更改用户状态<p>
	 * @author wangwenzhuo
	 * @creattime 2007-5-8
	 * @param userId
	 * @return boolean
	 */
	public boolean changeUserStatus(String userId,String userStatus)
	{
		SysPeoplepo sysPeoplepo = getUserInfoID(userId);
		if(sysPeoplepo != null)
		{
			//实例化一个用户信息bean
			PeopleInfo peopleInfo = new PeopleInfo();
			peopleInfo.setUserId(Function.nullString(sysPeoplepo.getC1()));
			peopleInfo.setUserFullname(Function.nullString(sysPeoplepo.getC630000003()));				
			peopleInfo.setUserBelongGroupId(Function.nullString(sysPeoplepo.getC630000036()));
			peopleInfo.setUserCpid(Function.nullString(sysPeoplepo.getC630000013()));
			peopleInfo.setUserCptype(Function.nullString(sysPeoplepo.getC630000014()));
			peopleInfo.setUserCreateuser(Function.nullString(sysPeoplepo.getC630000004()));
			peopleInfo.setUserDpid(Function.nullString(sysPeoplepo.getC630000015()));
			peopleInfo.setUserFax(Function.nullString(sysPeoplepo.getC630000010()));
			peopleInfo.setUserIntId(sysPeoplepo.getC630000029().intValue());
			peopleInfo.setUserIsmanager(Function.nullString(sysPeoplepo.getC630000006()));
			peopleInfo.setUserLicensetype(Function.nullString(sysPeoplepo.getC630000016()));
			peopleInfo.setUserLoginname(Function.nullString(sysPeoplepo.getC630000001()));
			peopleInfo.setUserMail(Function.nullString(sysPeoplepo.getC630000011()));
			peopleInfo.setUserMobie(Function.nullString(sysPeoplepo.getC630000008()));
			peopleInfo.setUserOrderby(Function.nullString(sysPeoplepo.getC630000017()));
			peopleInfo.setUserPassword(Function.nullString(sysPeoplepo.getC630000002()));
			peopleInfo.setUserPhone(Function.nullString(sysPeoplepo.getC630000009()));
			peopleInfo.setUserPosition(Function.nullString(sysPeoplepo.getC630000005()));
			peopleInfo.setUserType(Function.nullString(sysPeoplepo.getC630000007()));
			peopleInfo.setUserStatus(Function.nullString(userStatus));
			
			People people = new People();
			return people.modifyPeople(peopleInfo,userId,"managerModify");
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		GetUserInfoList GetUserInfoList = new GetUserInfoList();
		GetUserInfoList.flagIdFind("000000000600010");
	}
	
}