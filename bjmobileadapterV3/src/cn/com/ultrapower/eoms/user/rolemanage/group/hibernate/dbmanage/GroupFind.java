package cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

/**
 * <p>Description:使用hibernate从数据库中查找字段<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-18
 */
public class GroupFind {
	
	static final Logger logger 		= (Logger) Logger.getLogger(GroupFind.class);
	
	//从配置文件中取表名
	GetFormTableName getFormTableName	= new GetFormTableName();
	String Rgroup						= getFormTableName.GetFormName("RemedyTgroup");
	String sourcemanager				= getFormTableName.GetFormName("RemedyTsourceManager");
	String systemmanage					= getFormTableName.GetFormName("systemmanage");
	String sourceconfig					= getFormTableName.GetFormName("sourceconfig");
	String usertablename				= getFormTableName.GetFormName("RemedyTpeople");
	
	GetUserInfoList getUserInfoList		= new GetUserInfoList();
	//公共的js树
	private StringBuffer treeinfo		= new StringBuffer();
	
	/**
	 * <p>Description:调用GetGroupInfoList类的方法getGroupList()查找所有组信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-18
	 * @return List
	 */
	public List find()
	{
		GetGroupInfoList groupInfoList = new GetGroupInfoList();
		return groupInfoList.getGroupList();
	}
	
	/**
	 * <p>Description:调用GroupFind类的方法getGroupInfo()修改组信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-18
	 * @param id
	 * @return SysGrouppo
	 */
	public SysGrouppo findModify(String id)
	{
		GetGroupInfoList groupInfoList = new GetGroupInfoList();
		return groupInfoList.getGroupInfo(id);
	}
	
	/**
	 * <p>Description:添加情况下判断同一节点下是否相同组名称<p>
	 * @author wangwezhuo
	 * @creattime 2006-12-1
	 * @param groupName
	 * @param groupParentId
	 * @return boolean
	 */
	public boolean isDuplicate(String groupName,String groupParentId)
	{
		try
		{
			GetGroupInfoList groupInfoList = new GetGroupInfoList();
			List list = groupInfoList.getGroupList(groupParentId);
			for(Iterator it = list.iterator();it.hasNext();)
			{
				SysGrouppo grouppo = (SysGrouppo)it.next();
				if(groupName.equals(grouppo.getC630000018()))
				{
					return true;
				}
				else
				{
					continue;
				}
			}
			return false;
		}
		catch(Exception e)
		{
			logger.error("[421]GroupFind.isDuplicate() 添加判断同一节点下是否相同组名称失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:修改情况下判断同一节点下是否相同组名称<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-1
	 * @param groupId
	 * @param groupName
	 * @param groupParentId
	 * @return boolean
	 */
	public boolean isDuplicate(String groupId,String groupName,String groupParentId)
	{
		try
		{
			GetGroupInfoList groupInfoList = new GetGroupInfoList();
			List list = groupInfoList.getGroupList(groupParentId);
			for(Iterator it = list.iterator();it.hasNext();)
			{
				SysGrouppo grouppo = (SysGrouppo)it.next();
				if(groupName.equals(grouppo.getC630000018()))
				{
					if(!groupId.equals(grouppo.getC1()))
					{
						return true;
					}
					else
					{
						continue;
					}
				}
				else
				{
					continue;
				}
			}
			return false;
		}
		catch(Exception e)
		{
			logger.error("[422]GroupFind.isDuplicate() 修改情况下判断同一节点下是否相同组名称失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:组成员模块生成树,点击为超链接<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-28
	 * @param id
	 * @param tuser
	 * @return String
	 */
	public String nodefind(String id,String tuser)
	{
		StringBuffer sql	= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select C1,C630000018,C630000020,C630000022 from "+Rgroup+" where C630000025 = '0' and C630000020 = '"+id+"'");
				
				
			}
			else
			{
				//如果当前用户为系统管理员
				if(getUserInfoList.isSystemManagerByName(tuser))
				{
					//sql.append("select C1,C630000018,C630000020 from "+Rgroup+" where C630000025 = '0' and C630000020 = '"+id+"'");
					sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+Rgroup+" grouptable,"+sourcemanager+" sourcetable,"+sourceconfig+" sourceconfigtable,"+usertablename+" usertable where grouptable.C630000025 = '0'");
					sql.append(" and (grouptable.C630000026 = sourcetable.C650000003 or grouptable.C1 = sourcetable.C650000003) and sourcetable.C650000007 = usertable.c1 and usertable.c630000001='"+tuser+"' and sourcetable.C650000005 = '3' and grouptable.C630000020='"+id+"'");
					sql.append(" and sourcetable.C650000001 = sourceconfigtable.source_id and sourceconfigtable.source_name = '"+systemmanage+"'" );
				}
				//如果当前用户为值班管理员
				else
				{
					sql.append("select C1,C630000018,C630000020,C630000022 from "+Rgroup+" where C630000025 = '0' and C630000020 = '"+id+"'");
					sql.append(" and C630000021 = '4'");
				}
			}
			
 	    	sql.append(" order by C630000022");
 	    	
			String groupName     = "";
 	    	String groupID       = "";

			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	//xml生成树
	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";2\" action=\"FindPeopleDao?id="+groupID+"\" target=\"mainFrame\" />");
			}	
		}
		catch(Exception e)
		{
			logger.error("[423]GroupFind.nodefind() 组成员模块生成树,点击为超链接失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return treeinfo.toString();
	}
	
	public String nodefind1(String id,String tuser)
	{
		StringBuffer sql	= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select C1,C630000018,C630000020,C630000021,C630000022 from "+Rgroup+" where C630000025 = '0' and C630000020 = '"+id+"' and C630000025=0");
			}
			else
			{
				//如果当前用户为系统管理员
				if(getUserInfoList.isSystemManagerByName(tuser))
				{
					sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022,grouptable.C630000021 from "+Rgroup+" grouptable,"+sourcemanager+" sourcetable,"+sourceconfig+" sourceconfigtable,"+usertablename+" usertable where grouptable.C630000025 = '0'");
					sql.append(" and (grouptable.C630000026 = sourcetable.C650000003 or grouptable.C1 = sourcetable.C650000003) and sourcetable.C650000007 = usertable.c1 and usertable.c630000001='"+tuser+"' and sourcetable.C650000005 = '3' and grouptable.C630000020='"+id+"'");
					sql.append(" and sourcetable.C650000001 = sourceconfigtable.source_id and sourceconfigtable.source_name = '"+systemmanage+"' and grouptable.C630000025=0" );
				}
				//如果当前用户为值班管理员
				else
				{
					sql.append("select C1,C630000021,C630000018,C630000020,C630000022,C630000021 from "+Rgroup+" where C630000025 = '0' and C630000020 = '"+id+"' and C630000025=0");
					sql.append(" and C630000021 = '4'");
				}
			}
			
 	    	sql.append(" order by C630000022");
 	    	
			String groupName     = "";
 	    	String groupID       = "";
 	    	String groupType 	 = "";
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	groupType       = rs.getString("C630000021");
	 	    	//xml生成树
	 	    	if(String.valueOf(groupType).equals("3")){
	 	    		System.out.println(groupType);
	 	    		treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";14\" funpram=\""+groupName+":"+groupID+"\"/>");
	 	    	}else{
	 	    		System.out.println(groupType);
	 	    		treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";14\"/>");
	 	    	}
	 	    	//treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";1\"/>");
			}	
		}
		catch(Exception e)
		{
			logger.error("[423]GroupFind.nodefind() 组成员模块生成树,点击为超链接失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return treeinfo.toString();
	}
	/**
	 * <p>Description:用户模块选择单位ID<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-24
	 * @param id
	 * @param tuser
	 * @return String
	 */
	public String cpnodefind(String id)
	{
		StringBuffer sql	= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+Rgroup+" a where a.C630000025='0' and a.C630000021='2' and a.C630000020 = '"+id+"'");
			sql.append(" order by a.C630000022");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName     = "";
 	    	String groupID       = "";
 	    	String groupDnId	 = "";
 	    	String groupFullName = "";
 	    	
			while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	groupDnId	 	= rs.getString("C630000037");
	 	    	groupFullName 	= rs.getString("C630000019");
	 	    	//xml生成树
	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";0\" funpram=\""+groupName+":"+groupID+":"+groupFullName+":"+groupDnId+"\"/>");
			}		
		}
		catch(Exception e)
		{
			logger.error("[424]GroupFind.cpnodefind() 用户模块选择单位ID失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return treeinfo.toString();
	}
	
	/**
	 * <p>Description:用户模块选择部门ID<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-24
	 * @param strwhere		组类型为3
	 * @param id
	 * @return String
	 */
	public String dpnodefind(String strwhere,String id)
	{
		StringBuffer sql	= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			//根据配置文件中的表名和传入的参数确定sql语句
			sql.append("select C1,C630000018,C630000022 from "+Rgroup+" where C630000025 = '0' and C630000020 = '"+id+"'");
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere+" order by C630000022");
			}
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName     = "";
 	    	String groupID       = "";
 	    	
			while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	//xml生成树
	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";1\" funpram=\""+groupName+":"+groupID+"\"/>");
			}
		}
		catch(Exception e)
		{
			logger.error("[425]GroupFind.dpnodefind() 用户模块部门ID生成树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return treeinfo.toString();
	}
	
	
	/**
	 * <p>Description:根据组类型groupType实现组模块生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-26
	 * @param groupType
	 * @param cpId
	 * @return String
	 */
	public String parentidfind(String groupType,String cpId,String tuser)
	{
		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				return flagfind("",groupType,cpId);
			}
			else
			{
				//如果当前用户为系统管理员
				if(getUserInfoList.isSystemManagerByName(tuser))
				{
					if(groupType.equals("0")||groupType.equals("4")||groupType.equals("5"))
					{
						return flagfind("C630000021<>'2'",groupType,cpId);
					}
					else if(groupType.equals("6"))
					{
						return flagfind("",groupType,cpId);
					}
					else if(groupType.equals("2"))
					{
						return flagfind("C630000021='2'",groupType,cpId);
					}
					else if(groupType.equals("3"))
					{
						return flagfind("C630000021='3'",groupType,cpId);
					}
				}
				//如果当前用户为值班管理员
				else
				{
					return flagfind("C630000021 = '4' and C630000037 like '%"+cpId+";%'",groupType,cpId);
				}
			}
			return null;
		}
		catch(Exception e)
		{
			logger.error("[426]GroupFind.parentidfind() 根据组类型groupType实现组模块生成树失败"+e.getMessage());
			return null;
		}		
	}
	
	/**
	 * <p>Description:协助根据groupType实现当组模块生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-7
	 * @param strwhere
	 * @param id
	 * @return String
	 */
	private String flagfind(String strwhere,String groupType,String id)
	{	
		StringBuffer sql = new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			//根据配置文件中的表名和传入的参数确定sql语句
			sql.append("select C1,C630000018,C630000019,C630000037,C630000022 from "+Rgroup+" where C630000025 = '0' and C630000020 = '"+id+"'");
			
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			sql.append(" order by C630000022");
			System.out.println(sql.toString());
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName     = "";
 	    	String groupID       = "";
 	    	String groupDnId	 = "";
 	    	String groupFullName = "";
 	    	
			while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	groupDnId	 	= rs.getString("C630000037");
	 	    	groupFullName 	= rs.getString("C630000019");
	 	    	
	 	    	//xml生成树
	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"parentidloadtree.jsp?str="+groupID+";"+groupType+"\" funpram=\""+groupName+":"+groupID+":"+groupFullName+":"+groupDnId+"\"/>");
			}
		}
		catch(Exception e)
		{
			logger.error("[427]GroupFind.flagfind() 协助根据groupType实现当组模块生成树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return treeinfo.toString();
	}
	
	/**
	 * <p>Description:修改组信息时拼DnId<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-14
	 * @param id
	 * @return String
	 */
	public String modifyDnId(String id)
	{
		String dnId = "";
		String finalDnId	= "";
		try
		{
			if(String.valueOf(findModify(id).getC630000037()).equals("")||String.valueOf(findModify(id).getC630000037()).equals("null"))
			{
				logger.error("[469]GroupFind.modifyDnId() 获得DnID失败");
				return "null";
			}
			else
			{
				dnId = findModify(id).getC630000037();
			}
		}
		catch(Exception e)
		{
			logger.error("[428]GroupFind.modifyDnId() 获得DnID失败"+e.getMessage());
			return "null";
		}
		
		String temp_dnId[]	= dnId.split(";");
		try
		{
			
			for(int i = 0;i<temp_dnId.length;i++)
			{
				if(temp_dnId[i].equals(id))
				{
					continue;
				}
				else if(!finalDnId.equals(""))
				{
					finalDnId = finalDnId + ";";
				}
				finalDnId = finalDnId + temp_dnId[i];
			}	
			return finalDnId+";";
		}
		catch(Exception e)
		{
			logger.error("[429]GroupFind.modifyDnId() 修改组信息时拼DnId失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:修改组信息时拼DnName<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-16
	 * @param dnName
	 * @return String
	 */
	public String modifyDnName(String dnName)
	{
		String finalDnName		= "";
		String temp_dnName[]	= dnName.split("\\.");
		for(int i = 0;i<temp_dnName.length-1;i++)
		{	
			if(!finalDnName.equals(""))
			{
				finalDnName = finalDnName + ".";
			}
			finalDnName = finalDnName + temp_dnName[i];
		}
		return finalDnName;
	}
	
	/**
	 * <p>Description:显示单位ID名称<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-18
	 * @param companyId
	 * @return String
	 */
	public String modifyCompanyName(String companyId)
	{
		try 
		{
			String sql	= "select distinct b.c630000018 from SysGrouppo b,SysGrouppo a where a.c630000026=b.c1 and a.c630000026 ='"+companyId+"'";
			List list	=  HibernateDAO.queryClearObject(sql);
			for(Iterator it = list.iterator();it.hasNext();)
			{
				String groupName = (String)it.next();
				return groupName;
			}
			return null;
		} 
		catch (Exception e) 
		{
			logger.error("[430]GroupFind.modifyCompanyName() 显示单位ID名称失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * <p>Description:判断一个部门下是否有部门<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-21
	 * @param groupId
	 * @return boolean		若为true,则该部门下有部门
	 */
	public boolean ishaveDepartment(String groupId)
	{
		//获取该节点下的组信息
		GetGroupInfoList groupInfo	= new GetGroupInfoList();
		List list					= groupInfo.getGroupList(groupId);
		try
		{
			if(list.size()>0)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					SysGrouppo grouppo	= (SysGrouppo)it.next();
					String groupType	= grouppo.getC630000021();
					//若该部门下有部门
					if(groupType.equals("3"))
					{
						return true;
					}
					else
					{
						continue;
					}
				}
				//该部门下没有部门
				return false;
			}
			else
			{
				//该部门下没有组
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[508]GroupFind.ishaveDepartment() 判断一个部门下是否有部门失败"+e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * 查询参数中的组名称与数据库中的组名称是否相同。相同返回true,否则返回false.
	 * 日期 2007-3-22
	 * @author wangyanguang
	 * @param groupid		组ID
	 * @param newGroupName	组名称
	 * @return boolean
	 */
	public boolean isSameGroupName(String groupid,String newGroupName)
	{
		SysGrouppo grouppo = findModify(groupid);
		if(grouppo.getC630000018().equals(newGroupName))
		{
			return true;
		}
		else
		{
			return false;
			
		}
	}
	
}