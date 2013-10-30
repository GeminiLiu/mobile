package com.ultrapower.eoms.mobile.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.XMLWriter;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;
import cn.com.ultrapower.eoms.user.config.sourcemanager.bean.SourceManagerBean;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.AgentInterface;
import cn.com.ultrapower.eoms.user.userinterface.AgentUserInfo;
import cn.com.ultrapower.eoms.user.userinterface.IndexShowInterfaceSQL;
import cn.com.ultrapower.eoms.user.userinterface.SkillUserInfoInterface;
import cn.com.ultrapower.eoms.user.userinterface.SourceConfigInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.AgentPram;
import cn.com.ultrapower.eoms.user.userinterface.bean.GroupAndUserInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.RoleInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SendScopePram;
import cn.com.ultrapower.eoms.user.userinterface.bean.SkillGrandParm;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceManagerParm;
import cn.com.ultrapower.eoms.user.userinterface.cm.GetSkillGrandInfoInterface;
import cn.com.ultrapower.eoms.user.userinterface.cm.JDBCGetAuditUser;
//import cn.com.ultrapower.eoms.user.userinterface.cm.UserFolderSource;


public class RoleGrandInterface 
{
	static final Logger logger 	= (Logger) Logger.getLogger(RoleGrandInterface.class);
	

	GetFormTableName getFormTableName	= new GetFormTableName();
	private String sourcemanager				= getFormTableName.GetFormName("RemedyTsourceManager");
	private String grouptable					= getFormTableName.GetFormName("RemedyTgroup");
	private String peopletable					= getFormTableName.GetFormName("RemedyTpeople");
	private String sourceconfig					= getFormTableName.GetFormName("sourceconfig");
	
	private String roleskill				= getFormTableName.GetFormName("RemedyTrole");

	
	private String RemedyTrolesusergrouprel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	private String RemedyTrolesskillmanage	= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String RemedyTgroupuser			= getFormTableName.GetFormName("RemedyTgroupuser");
	
	private String managergrandaction	= getFormTableName.GetFormName("managergrandaction");
	private String grandaction			= getFormTableName.GetFormName("grandaction");
	
	private String systemmanage			= getFormTableName.GetFormName("systemmanage");
	private String muluurl				= getFormTableName.GetFormName("muluurl");
	private String remedyserver			= getFormTableName.GetFormName("driverurl");
	
	private Long 	sourceid					= null;
	private String 	userid						= null;
	private List   	grouplist					= null;
	private List 	rolelist					= null;
	
	/**
	 * 根据参数返回资源树或者是组织结构目录树。
	 * 日期 2006-11-23
	 * 
	 * @author wangyanguang/王彦广 
	 * @param username              用户登陆名。
	 * @param sourceConfigBean		资源Bean信息。
	 * 								source_id:			资源ID。
	 * 								source_parentid：	资源父ID。
	 * 								source_cnname：		资源中文名。
	 * 								source_name：		资源英文名。
	 * 								source_module：		资源所属模块ID。
	 * 								source_orderby：		资源排序值。
	 * 								source_desc：		资源描述。
	 * 								source_type：		资源类别类型（0：目录节点，1：工单，2：环节表3：表资源，4：Menu资源5，字段资源）
	 * 
	 * @param beanList				条件List。
	 * @param showType				返回类型。	0:返回当前节点及其子节点的技能权限和属性同时返回其父节点。 
	 * 											1:仅返回当前节点的下一级的技能权限和属性。
	 * 											2:仅返回当前节点的技能权限和属性。
	 * @param treeType				树的类型。0:为资源树形结构。					1：组织结构目录树。
	 * @return Document
	 */
	public Document getMenuTree(String username, SourceConfigInfoBean sourceConfigBean,List beanList,String showType,String treeType)
	{
		
		SourceRoleGrandInterface sourceRoleGrandInterface = new SourceRoleGrandInterface();
		Document returnDoc = sourceRoleGrandInterface.getMenuTree(username,sourceConfigBean,beanList,showType,treeType);
		return returnDoc;
	}
	/**
	 * 根据参数返回资源树或者是组织结构目录树。
	 * 日期 2006-11-23
	 * 
	 * @author wangyanguang/王彦广 
	 * @param username              用户登陆名。
	 * @param sourceConfigBean		资源Bean信息。
	 * 								source_id:			资源ID。
	 * 								source_parentid：	资源父ID。
	 * 								source_cnname：		资源中文名。
	 * 								source_name：		资源英文名。
	 * 								source_module：		资源所属模块ID。
	 * 								source_orderby：		资源排序值。
	 * 								source_desc：		资源描述。
	 * 								source_type：		资源类别类型（0：目录节点，1：工单，2：环节表3：表资源，4：Menu资源5，字段资源）
	 * 
	 * @param beanList				条件List。
	 * @param showType				返回类型。	0:返回当前节点及其子节点的技能权限和属性同时返回其父节点。 
	 * 											1:仅返回当前节点的下一级的技能权限和属性。
	 * 											2:仅返回当前节点的技能权限和属性。
	 * @param treeType				树的类型。	0:为资源树形结构。					1：组织结构目录树。
	 * @param commissionType		代办人类型。	0:为不显示用户代办人的权限			1：显示用代办人的权限。
	 * @return Document
	 *
	 */
	public Document getMenuTree(String username,SourceConfigInfoBean sourceConfigBean,List beanList,String showType,String treeType,String commissionType)
	{
		Document returnDoc  = null;
		if(commissionType.equals("0"))
		{
			SourceRoleGrandInterface sourceRoleGrandInterface = new SourceRoleGrandInterface();
			returnDoc = sourceRoleGrandInterface.getMenuTree(username,sourceConfigBean,beanList,showType,treeType);
		}
		else if(commissionType.endsWith("1")) 
		{
			TemSourceInterface tmpSourceInter = new TemSourceInterface();
			returnDoc = tmpSourceInter.getMenuTree(username,sourceConfigBean,beanList,showType,treeType);
		}
		return returnDoc;
	}
	
	/**
	 * 根据用户名与资源名查询此用户对资源是否有权限。
	 * 日期 2007-1-22
	 * @author wangyanguang
	 * @param username		用户名
	 * @param sourcename	资源名
	 */
	public boolean getIndexBoolValue(String username,String sourcename)
	{
		boolean bl = false;
		
		IndexShowInterfaceSQL indexsql = new IndexShowInterfaceSQL();
		
		bl = indexsql.getJdbcBoolValue(username,sourcename);
		
		return bl;
	}
	
	/**
	 * 根据用户名、资源名，查询用户派发对象，根据用户派发对象与资源ID查询技能表，生成用户、组Document对象。
	 * 日期 2006-11-30
	 * @author wangyanguang/王彦广 
	 * @param argusername			用户名
	 * @param sourcename			资源名
	 */	
	public Document getGroupUserTree(String argusername,String sourcename)
	{
		UserGroupGrandInterface groupgrand = new UserGroupGrandInterface();
		Document doc = groupgrand.getGroupUserDoc(argusername,sourcename);
		return doc;
	}
	/**
	 * 据用户名、资源名和技能授权表Bean信息，查询用户派发对象，根据用户派发对象与资源ID查询技能表，生成用户、组Document对象。
	 * 日期 2006-12-6
	 * @author wangyanguang/王彦广 
	 * @param argusername			用户名
	 * @param sourcename			资源名
	 * @param roleinfobean			技能授权Bean
	 */
	public Document getGroupUserTree(String argusername,String sourcename,RoleInfoBean roleinfobean)
	{
		UserGroupGrandInterface2 groupgrand = new UserGroupGrandInterface2();
		Document doc = groupgrand.getGroupUserDoc(argusername,sourcename,roleinfobean);
		return doc;
	}
	
	/**
	 * 根据参数返回用户所能派发组、用户信息。
	 * 日期 2007-1-15
	 * @author wangyanguang
	 * @param sendscopepram
	 */
	public List getGroupUserTree(SendScopePram sendscopepram)
	{
		List returnList = null;
		SendScopeInterface sendscopeinterface = new SendScopeInterface();
		returnList = sendscopeinterface.getElementInfo(sendscopepram);
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
	 * 日期 2006-11-25
	 * @author wangyanguang/王彦广 
	 */
	public boolean insertRoleValue (String parentID,String childID)
	{
		InsertGrandValue insertGrandValue = new InsertGrandValue();
		boolean flag = insertGrandValue.Insert2Role(parentID,childID);
		if(flag == true)
		{
			System.out.println("Insert Successful!");
		}
		else
		{
			System.out.println("Insert failure");
		}
		return flag;
	}
	/**
	 * 代理人信息接口，根据用户名查询出此用户的代理人信息。
	 * 日期 2006-11-25
	 * @author wangyanguang/王彦广 
	 * @param username				用户名
	 */
	public List getAgentUser(String username)
	{
		AgentUserInfo agentinfo = new AgentUserInfo();
		List returnList = agentinfo.getUserName(username);
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
	 * 根据技能信息Bean查询技能表与用户信息表，取得满足条件的用户和技能组合的Bean信息集合.
	 * 日期 2006-12-12
	 * @author wangyanguang/王彦广 
	 */
	public List getSkillInfo(RoleInfoBean roleInfoBean)
	{
		List returnList = null;
		SkillUserInfoInterface skillinterface = new SkillUserInfoInterface();
		returnList = skillinterface.getSkillInfo(roleInfoBean);
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
	 * 根据参数返回代办人信息。
	 * 日期 2007-3-5
	 * @author wangyanguang
	 * @param agentpram
	 * @return List
	 */
	public List getAgentList(AgentPram agentpram)
	{
		AgentInterface agentinterface = new AgentInterface();
		List list = agentinterface.getCommissionInfo(agentpram);
		return list;
	}
	
	/**
	 * 根据参数返回用户对资源的权限。
	 * 日期 2007-3-6
	 * @author wangyanguang
	 * @param skillparm
	 * @return List
	 */
	public List getGrand(SkillGrandParm skillparm)
	{
		GetSkillGrandInfoInterface getskillgrand = new GetSkillGrandInfoInterface();
		List list = getskillgrand.getSkillGrand(skillparm);
		return list;
	}
	
	/**
	 * <p>Description:获得管理者(该管理者所管理的组)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-22
	 * @param sourceManagerBean
	 * @return List
	 */
	public List getManager(SourceManagerBean sourceManagerBean)
	{
		StringBuffer sql = new StringBuffer();
		IDataBase dataBase = null;
		Statement stm = null;
		ResultSet rs = null;
		try
		{
			String sourcemanager_suserid	= sourceManagerBean.getsourcemanager_suserid();
			String sourcemanager_type		= sourceManagerBean.getsourcemanager_type();
			String sourcemanager_sourceid	= sourceManagerBean.getsourcemanager_sourceid();
			
			//拼not in子查询的sql，要求其查询结果集为单个字段"a.C1"
	  		StringBuffer sql2 = new StringBuffer();
			sql2.append("select distinct a.C1 from "+grouptable+" a,"+sourcemanager+" b");
			sql2.append(" where a.C1 = b.C650000003 and b.C650000007 = '"+sourcemanager_suserid+"' and not exists");
			sql2.append(" (select distinct c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+sourcemanager_suserid+"' and a.C630000020 = c.C650000003)");
			//查得not in结果集
			String strNotIn	= getNotInStr(sql2.toString());
			
			if(sourcemanager_sourceid.equals("virtualgroup"))
			{
				sql.append("select b.C1,b.C630000018,b.C630000020 from "+sourcemanager+" a,"+grouptable+" b,"+sourceconfig+" c where c.source_name = 'virtualgroup' and a.C650000001 = c.source_id");
			}
			else
			{
				sql.append("select b.C1,b.C630000018,b.C630000020 from "+sourcemanager+" a,"+grouptable+" b where a.C650000001 ='"+sourcemanager_sourceid+"'");
			}
			
			if(sourcemanager_suserid!=null)
			{
				sql.append(" and a.C650000007='"+sourcemanager_suserid+"'");
			}
			if(sourcemanager_type!=null)
			{
				sql.append(" and a.C650000005='"+sourcemanager_type+"'");
			}
			//最终的sql
			sql.append(" and a.C650000003 = b.C1");
			
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sql.toString());
			
			String groupId			= "";
			String groupName		= "";
			String groupParentId	= "";
			
			//作为返回参数的List变量
			List finalList			= new ArrayList();
			//顶级节点组成的以逗号分隔的字符串数组
			String[] temp_strNotIn	= strNotIn.split(",");
			
			while(rs.next())
			{
				groupId			= rs.getString("C1");
				groupName		= rs.getString("C630000018");
				groupParentId	= rs.getString("C630000020");
				
				GroupAndUserInfo beanInfo = new GroupAndUserInfo();
				beanInfo.setGroupId(groupId);
				beanInfo.setGroupName(groupName);
				
				//顶级节点的父组ID置为0
				for(int i = 0;i<temp_strNotIn.length;i++)
				{
					
					if(temp_strNotIn[i].equals(groupId))
					{
						beanInfo.setGroupParentid("0");
						break;
					}
					else
					{
						beanInfo.setGroupParentid(groupParentId);
					}
				}
				finalList.add(beanInfo);
			}
			//关闭连接
			rs.close();
			stm.close();
			dataBase.closeConn();

			return finalList;	
		}
		catch(Exception e)
		{
			logger.error("[498]RoleGrandInterface.getManager() 获得管理者(该管理者所管理的组)失败"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	/**
	 * <p>Description:获得管理者(该人是否该组的管理者)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-29
	 * @param sourceManagerBean
	 * @param departmentId
	 * @return boolean
	 */
	public boolean getManager(SourceManagerBean sourceManagerBean,String departmentId)
	{
		StringBuffer sql = new StringBuffer();
		IDataBase dataBase = null;
		Statement stm = null;
		ResultSet rs = null;
		
		try
		{
			String sourcemanager_suserid	= sourceManagerBean.getsourcemanager_suserid();
			String sourcemanager_type		= sourceManagerBean.getsourcemanager_type();
			String sourcemanager_sourceid	= sourceManagerBean.getsourcemanager_sourceid();
			
			sql.append("select b.C1,b.C630000018,b.C630000020 from "+sourcemanager+" a,"+grouptable+" b where a.C650000001 ='"+sourcemanager_sourceid+"'");
			
			if(sourcemanager_suserid!=null)
			{
				sql.append(" and a.C650000007='"+sourcemanager_suserid+"'");
			}
			if(sourcemanager_type!=null)
			{
				sql.append(" and a.C650000005='"+sourcemanager_type+"'");
			}
			//最终的sql
			sql.append(" and a.C650000003 = b.C1");
			
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sql.toString());
			
			String groupId			= "";
			
			while(rs.next())
			{
				groupId			= rs.getString("C1");
				if(groupId.equals(departmentId))
				{
					return true;
				}
				else
				{
					continue;
				}
			}
			//关闭连接
			rs.close();
			stm.close();
			dataBase.closeConn();

			return false;	
		}
		catch(Exception e)
		{
			logger.error("[]RoleGrandInterface.getManager() 获得管理者(该人是否该组的管理者)失败"+e.getMessage());
			return false;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	/**
	 * <p>Description:获得管理者(该组的管理者)失败<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-29
	 * @param sourceManagerBean
	 * @param isGroupManager
	 * @return List
	 */
	public List getManager(SourceManagerBean sourceManagerBean,boolean isGroupManager)
	{
		StringBuffer sql = new StringBuffer();
		IDataBase dataBase = null;
		Statement stm = null;
		ResultSet rs = null;
		
		try
		{
			String sourcemanager_groupid	= sourceManagerBean.getsourcemanager_groupid();
			String sourcemanager_type		= sourceManagerBean.getsourcemanager_type();
			String sourcemanager_sourceid	= sourceManagerBean.getsourcemanager_sourceid();
			String sourcemanager_userid		= sourceManagerBean.getsourcemanager_userid();
			
			sql.append("select distinct b.C1,b.C630000003 from "+sourcemanager+" a,"+peopletable+" b where a.C650000001 ='"+sourcemanager_sourceid+"'");
			
			if(sourcemanager_groupid!=null)
			{
				sql.append(" and a.C650000003='"+sourcemanager_groupid+"'");
			}
			if(!String.valueOf(sourcemanager_userid).equals("")&&!String.valueOf(sourcemanager_userid).equals("null"))
			{
				sql.append(" and a.C650000004='"+sourcemanager_userid+"'");
			}
			else
			{
				sql.append(" and a.C650000004 is null");
			}
			if(sourcemanager_type!=null)
			{
				sql.append(" and a.C650000005='"+sourcemanager_type+"'");
			}
			//最终的sql
			sql.append(" and a.C650000007 = b.C1");
			
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sql.toString());
			
			String userID			= "";
			String userFullName		= "";
			
			//作为返回参数的List变量
			List finalList			= new ArrayList();
			
			while(rs.next())
			{
				userID			= rs.getString("C1");
				userFullName	= rs.getString("C630000003");
				
				GroupAndUserInfo beanInfo = new GroupAndUserInfo();
				beanInfo.setUserId(userID);
				beanInfo.setUserFullname(userFullName);
				
				finalList.add(beanInfo);
			}
			//关闭连接
			rs.close();
			stm.close();
			dataBase.closeConn();

			return finalList;	
		}
		catch(Exception e)
		{
			logger.error("[]RoleGrandInterface.getManager() 获得管理者(该组的管理者)失败"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	/**
	 * <p>Description:根据条件拼得以","分隔的not in结果集<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-7
	 * @param sql			要求查得的rs结果集只有C1字段
	 * @return String
	 */
	private String getNotInStr(String sql)
	{
		//拼字符串的变量
		StringBuffer str	= new StringBuffer();
		IDataBase dataBase = null;
		Statement stm = null;
		ResultSet rs = null;
		try
		{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			//获得数据库查询结果集
			stm		= dataBase.GetStatement();
			rs		= dataBase.executeResultSet(stm,sql);
			while(rs.next())
			{
				String C1	= rs.getString("C1");
				if(!str.toString().equals(""))
				{
					str.append(",");
				}
				str.append(C1);
			}
			//关闭数据库连接
			rs.close();
			stm.close();
			dataBase.closeConn();
			
			return str.toString();
		}
		catch(Exception e)
		{
			logger.error("[513]RoleGrandInterface.getNotInStr() 根据条件拼得以,分隔的not in结果集失败"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}

	/**
	 * <p>Description:获得该用户的审批人接口</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-16
	 * @param sourceManagerParm
	 * @return List
	 */
	public List getAuditUser(SourceManagerParm sourceManagerParm)
	{
		JDBCGetAuditUser jdbcGetAuditUser	= new JDBCGetAuditUser();
		return jdbcGetAuditUser.getUserOfAudit(sourceManagerParm);	
	}
	/**
	 * 
	 * date 2007-2-27
	 * author shigang
	 * @param args
	 * @return List
	 */

	public List GetFolderSource(String username,SourceConfigBean sourceConfigBean,List valuelist){
		UserFolderSource userFolderSource=new UserFolderSource();
		return userFolderSource.GetFolderSource(username,sourceConfigBean,valuelist);
	}	
	
	public List getSkillGrand(SkillGrandParm skillgrandparm)
	{
		GetSkillGrandInfoInterface getSkillGrand	= new GetSkillGrandInfoInterface();
		try
		{
			List returnvaluelist =getSkillGrand.getSkillGrand(skillgrandparm);
			if(!String.valueOf(returnvaluelist).equals("")&&!String.valueOf(returnvaluelist).equals("null"))
			{
				return returnvaluelist;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage()+"获得权限值接口异常");
			return null;
		}
	}

	public static void main(String[] args)
	{
		
		RoleGrandInterface srgi = new RoleGrandInterface();
		try 
		{
//			SourceConfigInfoBean sourceconfigBean = new SourceConfigInfoBean();
//			sourceconfigBean.setSource_name(module);
//			RoleGrandInterface srgi = new RoleGrandInterface();
//			Document doc = null;
//			try{
//				doc = srgi.getMenuTree(userLoginName,sourceconfigBean,null,"0","0","1");
//				
			SourceConfigInfoBean sourceConfigBean = new SourceConfigInfoBean();
			sourceConfigBean.setSource_name("dutymanage");
			//sourceConfigBean.setSource_module("51");
//			sourceConfigBean.setSource_parentid("178");
			//sourceConfigBean.setSource_module("0");
			//sourceConfigBean.setSource_parentid(new Long(178));
			long start = System.currentTimeMillis();
			//Document doc = srgi.getMenuTree("wangxuelei", sourceConfigBean,null,"0", "0");
			Document doc = srgi.getMenuTree("wangxuelei",sourceConfigBean,null,"0","0","1");
			System.out.println("此次查询用时"+(System.currentTimeMillis()-start)/1000+"."+((System.currentTimeMillis()-start)%1000)+"秒！");
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File("E:/grouplist.xml")));
			writer.write(doc);
			writer.close();
		} 
		catch (Exception e1) 
		{
			System.out.println("Excepiton");
			e1.getMessage();
		}
	}

}
