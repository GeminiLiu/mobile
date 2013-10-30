package cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.UserCommisionInfo;
import cn.com.ultrapower.eoms.user.authorization.usercommision.aroperationdata.UserCommision;
import cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.po.SysUserCommisionpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.ShowMenu;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

/**
 * <p>Description:封装业务逻辑<p>
 * @author wangwenzhuo
 * @creattime 2007-3-1
 */
public class UserCommisionFind {
	
	static final Logger logger = (Logger) Logger.getLogger(UserCommisionFind.class);
	
	//从配置文件中取表名
	GetFormTableName getFormTableName	= new GetFormTableName();
	private String sourceconfig			= getFormTableName.GetFormName("sourceconfig");
	private String roleskill			= getFormTableName.GetFormName("RemedyTrole");
	private String rolesSkillManage		= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String rolesUserGroupRel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	//资源权限动作值表
	private String grandactiontable		= getFormTableName.GetFormName("RemedyTgrandaction");
	//组成员表
	private String groupUser			= getFormTableName.GetFormName("RemedyTgroupuser");
	//用户信息表
	private String usertablename		= getFormTableName.GetFormName("RemedyTpeople");
	//组信息表
	private String grouptablename		= getFormTableName.GetFormName("RemedyTgroup");
	//
	private String skillconferstatus	= getFormTableName.GetFormName("skillconferstatus");
	
	private String skillconfer			= getFormTableName.GetFormName("skillconfer");
	
	private String rolemanagetable		= getFormTableName.GetFormName("RemedyTrolesmanage");
	
	/**
	 * <p>Description:根据当前用户登录名和资源ID显示个人授权模块添加页右侧div<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-2
	 * @param Skillconfer_LoginName
	 * @param Skillconfer_SourceID
	 * @return String
	 */
	public String showCheckBox(String Skillconfer_LoginName,String Skillconfer_SourceID)
	{
		//获得HashMap
		HashMap hashMap = getPurviewActionValue(Skillconfer_LoginName,Skillconfer_SourceID);
		//获得CheckBox
		String strhtml	= getCheckBox(hashMap);
		return strhtml;
	}
	
	
	/**
	 * <p>Description:根据当前用户登录名和资源ID获得资源权限动作名称/资源权限动作值的HashMap<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param Skillconfer_LoginName
	 * @param Skillconfer_SourceID
	 * @return HashMap
	 */
	private HashMap getPurviewActionValue(String Skillconfer_LoginName,String Skillconfer_SourceID)
	{
		StringBuffer sql = new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		//资源权限动作值的HashMap
		HashMap hashMap		= new HashMap();
		
		try
		{
			sql.append("select distinct c.C620000033 x,c.C620000034 y from "+roleskill+" a,"+usertablename+" b,"+grandactiontable+" c");
			sql.append(" where a.C610000008 = '"+Skillconfer_SourceID+"' and a.C610000007 = b.C1");
			sql.append(" and b.C630000001 = '"+Skillconfer_LoginName+"'");
			sql.append(" and c.C620000034 = a.C610000010 and c.C620000032 = '"+Skillconfer_SourceID+"'");
			sql.append(" union");
			sql.append(" select distinct e.C620000033 x,e.C620000034 y from "+rolesSkillManage+" a,"+rolesUserGroupRel+" b,"+usertablename+" c,"+groupUser+" d,"+grandactiontable+" e,");
			sql.append( rolemanagetable + " rolemanagetable");
			sql.append(" where c.C630000001 = '"+Skillconfer_LoginName+"' and d.C620000028 = c.C1 and d.C620000027 = b.C660000027");
			sql.append(" and b.C660000028 = a.C660000006 and a.C660000007 = '"+Skillconfer_SourceID+"'");
			sql.append(" and e.C620000034 = a.C660000009 and e.C620000032 = '"+Skillconfer_SourceID+"'");
			sql.append(" and rolemanagetable.c1=b.c660000028");
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			String actionName	= "";
			String actionValue	= "";
			
			while(rs.next())
			{
				actionName	= rs.getString("x");
				actionValue	= rs.getString("y");
				hashMap.put(actionValue, actionName);
			}
		}
		catch(Exception e)
		{
			logger.error("[554]UserCommisionFind.getPurviewActionValue() 根据当前用户登录名和资源ID获得资源权限动作名称/资源权限动作值的HashMap失败"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
		return hashMap;
	}
	
	/**
	 * <p>Description:根据HashMap显示个人授权模块添加页右侧div<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-2
	 * @param hashMap
	 * @return String
	 */
	private String getCheckBox(HashMap hashMap)
	{
		StringBuffer str = new StringBuffer();
		try
		{
			int i=0;
			for(Iterator it = hashMap.keySet().iterator();it.hasNext();)
			{
				String actionValue	= (String)it.next();
				String actionName	= (String)hashMap.get(actionValue);
				i=i+1;
				if(i%2==0)
			 	{
					str.append("<tr class='tablecontent' onMouseOver='FocusRow(this)' onMouseOut='BlurRow(this)'><td>"+actionName+"</td><td><input type = 'checkbox' name = 'actionValue' value = '"+actionValue+";"+actionName+"'></td></tr>\n");
			 	}
				else
				{
					str.append("<tr class='tablecontent_cross' onMouseOver='FocusRow(this)' onMouseOut='BlurRow(this)'><td>"+actionName+"</td><td><input type = 'checkbox' name = 'actionValue' value = '"+actionValue+";"+actionName+"'></td></tr>\n");
				}
			}
			return str.toString();
		}
		catch(Exception e)
		{
			logger.error("[555]UserCommisionFind.getCheckBox() 根据HashMap显示个人授权模块添加页右侧div失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:个人授权模块显示资源树顶级节点<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param tuser
	 * @return String
	 */
	public String getSourceTree(String tuser)
	{	
		//公共的资源生成树
		StringBuffer strjs	= new StringBuffer();
		//公共的查询sql
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
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig+" a where a.source_parentid = '0'");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList		= new GetUserInfoList();
		  		String tuserId = getUserInfoList.getUserInfoName(tuser).getC1();
		  		
		  		sql.append("select distinct source_id,source_parentid,source_cnname,source_orderby from");
				
		  		sql.append(" (select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig +" a,"+roleskill+" b");
				sql.append(" where b.C610000007 = '"+tuserId+"' and a.source_id = b.C610000008 and b.C610000018 = '0'");
				sql.append(" union");
				sql.append(" select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig+" a,"+rolesSkillManage+" b,"+rolesUserGroupRel+" c,"+groupUser+" d,");
//				sql.append(" where d.C620000028 = '"+tuserId+"' and d.C620000027 = c.C660000027 and c.C660000028 = b.C660000006");
				sql.append( rolemanagetable + " rolemanagetable");
				sql.append(" where d.C620000028 = '"+tuserId+"' and c.C660000028 = b.C660000006");
				sql.append(" and ((c.c660000026 = d.c620000028 and");		           
				sql.append(" c.c660000027 = d.c620000027) or");		       
				sql.append(" (c.c660000026 is null and c.c660000027 = d.c620000027))");
				sql.append(" and rolemanagetable.c1=c.c660000028");
				sql.append(" and b.C660000007 = a.source_id) source1");
				sql.append(" where 1=1 and not exists (");
				sql.append(" select distinct a.source_id from "+sourceconfig +" a,"+roleskill+" b");
				sql.append(" where b.C610000007 = '"+tuserId+"' and a.source_id = b.C610000008 and b.C610000018 = '0'");
				sql.append(" and a.source_id = source1.source_parentid)");
				sql.append(" and not exists(");
				sql.append(" select distinct a.source_id from "+sourceconfig+" a,"+rolesSkillManage+" b,"+rolesUserGroupRel+" c,"+groupUser+" d,");
				sql.append(  rolemanagetable + " rolemanagetable");
				sql.append(" where d.C620000028 = '"+tuserId+"' and c.C660000028 = b.C660000006");
				//新加的权限控制(modify by wangyanguang)
				sql.append(" and ((c.c660000026 = d.c620000028 and");		           
				sql.append(" c.c660000027 = d.c620000027) or");		       
				sql.append(" (c.c660000026 is null and c.c660000027 = d.c620000027))");
				sql.append(" and rolemanagetable.c1=c.c660000028");
				sql.append(" and b.C660000007 = a.source_id ");
				sql.append(" and a.source_id = source1.source_parentid)");
			}
			sql.append(" order by source_orderby,source_parentid,source_id");
			
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");
			
 	    	String sourceName     = "";
 	    	String sourceID       = "";

 	    	stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
 	    	
			while(rs.next())
			{
	 	    	sourceName      = rs.getString("source_cnname");
	 	    	sourceID        = rs.getString("source_id");
	 	    	//生成树顶层节点(加链接)
	 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+sourceName+"\",\"grouploadtree.jsp?str="+sourceID+";12\",\"commisionadd.jsp?sourceid="+sourceID+"\",\"\",\"\",\"\",\"mainFrame\"));");
			}
			
			strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
		}
		catch(Exception e)
		{
			logger.error("[556]GetSourceTree.showAttValueTree() 个人授权模块显示资源树顶级节点失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return strjs.toString();
	}
	
	/**
	 * <p>Description:根据当前用户登录名,资源ID,被授权用户登录名获得资源权限动作名称/资源权限动作值的List<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_LoginName
	 * @param skillconfer_SourceID
	 * @param skillconfer_DeanlLoginName
	 * @return List
	 */
	private List getPurviewActionValue(String skillconfer_LoginName,String skillconfer_SourceID,String skillconfer_DeanlLoginName)
	{	
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select a.c610000034,a.c610000035 from SysUserCommisionpo a");
			sql.append(" where a.c610000032 = '"+skillconfer_LoginName+"' and a.c610000031 = '"+skillconfer_SourceID+"'");
			sql.append(" and a.c610000033 = '"+skillconfer_DeanlLoginName+"'");
			sql.append(" and a.c610000026 = '0'");
		
			return HibernateDAO.queryClearObject(sql.toString());
		}
		catch(Exception e)
		{
			logger.error("[557]UserCommisionFind.getPurviewActionValue() 根据当前用户登录名,资源ID,被授权用户登录名获得资源权限动作名称/资源权限动作值的List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据当前用户登录名,资源ID,被授权用户登录名获得资源权限动作名称/资源权限动作值的获得已停止状态授权的List<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_LoginName
	 * @param skillconfer_SourceID
	 * @param skillconfer_DeanlLoginName
	 * @return List
	 */
	public List getCloseActionValue(String skillconfer_LoginName,String skillconfer_SourceID,String skillconfer_DeanlLoginName)
	{	
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select syspeople1.c630000003,source.sourceCnname,syspeople2.c630000003,commison.c610000035,commison.c610000022,commison.c610000023 ");
			sql.append(" from SysUserCommisionpo commison,Sourceconfig source,SysPeoplepo syspeople1,SysPeoplepo syspeople2");
			sql.append(" where commison.c610000032 = '"+skillconfer_LoginName+"' and commison.c610000031 = '"+skillconfer_SourceID+"'");
			sql.append(" and commison.c610000033 = '"+skillconfer_DeanlLoginName+"'");
			sql.append(" and commison.c610000031 =source.sourceId ");
			sql.append(" and commison.c610000026 = '1'");
			sql.append(" and syspeople1.c1 =commison.c610000025 ");//授权人id
			sql.append(" and syspeople2.c1 =commison.c610000027 ");//被授权人id
			sql.append(" and syspeople1.c630000012='0'");
			sql.append(" and syspeople2.c630000012='0'");
			sql.append(" order by syspeople1.c630000003,source.sourceCnname,syspeople2.c630000003");
			
		
			return HibernateDAO.queryClearObject(sql.toString());
		}
		catch(Exception e)
		{
			logger.error("[557]UserCommisionFind.getCloseActionValue() 根据当前用户登录名,资源ID,被授权用户登录名获得资源权限动作名称/资源权限动作值的List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据当前用户登录名,资源ID,被授权用户登录名获得资源权限动作值的List<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_LoginName
	 * @param skillconfer_SourceID
	 * @param skillconfer_DeanlLoginName
	 * @return List
	 */
	public List getActionValues(String skillconfer_LoginName,String skillconfer_SourceID,String skillconfer_DeanlLoginName)
	{	
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select a.c610000034 from SysUserCommisionpo a");
			sql.append(" where a.c610000032 = '"+skillconfer_LoginName+"' and a.c610000031 = '"+skillconfer_SourceID+"'");
			sql.append(" and a.c610000033 = '"+skillconfer_DeanlLoginName+"'");
		
			return HibernateDAO.queryClearObject(sql.toString());
		}
		catch(Exception e)
		{
			logger.error("[558]UserCommisionFind.getActionValues() 根据当前用户登录名,资源ID,被授权用户登录名获得资源权限动作值的List失败"+e.getMessage());
			return null;
		}
	} 
	
	/**
	 * <p>Description:获得个人授权信息C1<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_LoginName
	 * @param skillconfer_SourceID
	 * @param skillconfer_DeanlLoginName
	 * @param skillconfer_GrandActionValue
	 * @return String
	 */
	public String getSkillconfer_ID(String skillconfer_LoginName,String skillconfer_SourceID,String skillconfer_DeanlLoginName,String skillconfer_GrandActionValue)
	{	
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select a.c1 from SysUserCommisionpo a");
			sql.append(" where a.c610000032 = '"+skillconfer_LoginName+"' and a.c610000031 = '"+skillconfer_SourceID+"'");
			sql.append(" and a.c610000033 = '"+skillconfer_DeanlLoginName+"'");
			sql.append(" and a.c610000034 = '"+skillconfer_GrandActionValue+"'");
		
			List list = HibernateDAO.queryClearObject(sql.toString());
			if(list.size()>0)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					String skillconfer_ID = (String)it.next();
					return skillconfer_ID;
				}
				logger.info("[559]UserCommisionFind.getSkillconfer_ID() 系统中不存在该记录");
				return null;
			}
			else
			{
				logger.info("[559]UserCommisionFind.getSkillconfer_ID() 系统中不存在该记录");
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("[559]UserCommisionFind.getSkillconfer_ID() 获得个人授权信息C1失败"+e.getMessage());
			return null;
		}
	} 
	
	/**
	 * <p>Description:获得个人授权信息C1<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_LoginName
	 * @param skillconfer_SourceID
	 * @param skillconfer_DeanlLoginName
	 * @param skillconfer_GrandActionValue
	 * @return String
	 */
	public boolean getSkillconfer_Bean(String skillconfer_LoginName,String skillconfer_SourceID,String skillconfer_DeanlLoginName,String skillconfer_GrandActionValue,String confer_CancelCause)
	{	
		try
		{
			//实例化数据库操作类
			UserCommision userCommision = new UserCommision();
			
			StringBuffer sql = new StringBuffer();
			sql.append("from SysUserCommisionpo a");
			sql.append(" where a.c610000032 = '"+skillconfer_LoginName+"' and a.c610000031 = '"+skillconfer_SourceID+"'");
			sql.append(" and a.c610000033 = '"+skillconfer_DeanlLoginName+"'");
			sql.append(" and a.c610000034 = '"+skillconfer_GrandActionValue+"'");
			sql.append(" and a.c610000026 = '0'");
			System.out.println(sql.toString()+"打印SQL语句");
			List list = HibernateDAO.queryClearObject(sql.toString());
			if(list.size()>0)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					SysUserCommisionpo sysUserCommisionpo	= (SysUserCommisionpo)it.next();
					//实例化个人授权信息bean
					UserCommisionInfo userCommisionInfo		= new UserCommisionInfo();
					
					userCommisionInfo.setSkillconfer_ID(Function.nullString(sysUserCommisionpo.getC1()));
					userCommisionInfo.setSkillconfer_Cause(Function.nullString(sysUserCommisionpo.getC610000020()));
					userCommisionInfo.setSkillconfer_CancelCause(Function.nullString(confer_CancelCause));
					userCommisionInfo.setSkillconfer_StartTime(Function.nullLong(sysUserCommisionpo.getC610000022()).longValue());
					userCommisionInfo.setSkillconfer_EndTime(System.currentTimeMillis()/1000);
					userCommisionInfo.setSkillconfer_SkillID(Function.nullString(sysUserCommisionpo.getC610000024()));
					userCommisionInfo.setSkillconfer_UserID(Function.nullString(sysUserCommisionpo.getC610000025()));
					userCommisionInfo.setSkillconfer_Status("1");
					userCommisionInfo.setSkillconfer_DealUserID(Function.nullString(sysUserCommisionpo.getC610000027()));
					userCommisionInfo.setSkillconfer_memo(Function.nullString(sysUserCommisionpo.getC610000028()));
					userCommisionInfo.setSkillconfer_GroupID(Function.nullString(sysUserCommisionpo.getC610000029()));
					userCommisionInfo.setSkillconfer_SourceEnname(Function.nullString(sysUserCommisionpo.getC610000030()));
					userCommisionInfo.setSkillconfer_SourceID(Function.nullString(sysUserCommisionpo.getC610000031()));
					userCommisionInfo.setSkillconfer_LoginName(Function.nullString(sysUserCommisionpo.getC610000032()));
					userCommisionInfo.setSkillconfer_DeanlLoginName(Function.nullString(sysUserCommisionpo.getC610000033()));
					userCommisionInfo.setSkillconfer_GrandActionValue(Function.nullString(sysUserCommisionpo.getC610000034()));
					userCommisionInfo.setSkillconfer_GrandActionName(Function.nullString(sysUserCommisionpo.getC610000035()));
					
					userCommision.modifyUserCommision(userCommisionInfo,Function.nullString(sysUserCommisionpo.getC1()));
				}
				return true;
			}
			else
			{
				logger.error("[559]UserCommisionFind.getSkillconfer_ID() 系统中不存在该记录");
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[559]UserCommisionFind.getSkillconfer_ID() 获得个人授权信息C1失败"+e.getMessage());
			return false;
		}
	} 
	/**
	 * <p>Description:根据当前用户登录名,资源ID,被授权用户登录名获得修改个人授权信息记录<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_LoginName
	 * @param skillconfer_SourceID
	 * @param skillconfer_DeanlLoginName
	 * @return List
	 */
	public List showEditValues(String skillconfer_LoginName,String skillconfer_SourceID,String skillconfer_DeanlLoginName)
	{
		try
		{
			//若关联表相关记录被删除或不存在
			if(Function.nullString(skillconfer_SourceID).equals(""))
			{
				logger.info("[560]UserCommisionFind.showEditValues() 关联资源表没有该记录");
				return null;
			}
			else if(Function.nullString(skillconfer_DeanlLoginName).equals(""))
			{
				logger.info("[560]UserCommisionFind.showEditValues() 关联用户信息表没有该记录");
				return null;
			}
			else if(Function.nullString(skillconfer_DeanlLoginName).equals(""))
			{
				logger.info("[560]UserCommisionFind.showEditValues() 关联用户信息表没有该记录");
				return null;
			}
			//执行查询
			else
			{
				StringBuffer sql = new StringBuffer();
				sql.append("select b.c630000003,a.sourceCnname,c.c630000003 from Sourceconfig a,SysPeoplepo b,SysPeoplepo c");
				sql.append(" where a.sourceId = '"+skillconfer_SourceID+"' and b.c630000001 = '"+skillconfer_LoginName+"'");
				sql.append(" and c.c630000001 = '"+skillconfer_DeanlLoginName+"'");
				sql.append(" and b.c630000012='0' and c.c630000012='0'");
				return HibernateDAO.queryClearObject(sql.toString());
			}
		}
		catch(Exception e)
		{
			logger.error("[560]UserCommisionFind.showEditValues() 根据当前用户登录名,资源ID,被授权用户登录名获得修改个人授权信息记录失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据传过来的List和checkBoxName实现每6个checkbox换行效果<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param list
	 * @param checkBoxName
	 * @return String
	 */
	private String getCheckBox(List list,String checkBoxName)
	{
		StringBuffer strhtml = new StringBuffer();
		
		try
		{
			Iterator it	= list.iterator();
			int i 		= 1;
			while(it.hasNext())
			{	
				Object[] obj = (Object[])it.next();
				String actionValue	= (String)obj[0];
				String actionName	= (String)obj[1];
				
				if((i%6)==1&&list.size()>=i)
				{
					strhtml.append("<tr class='tablecontent'><td colspan='3' align='left'>");
				}
				if((i%6)==0)
				{
					strhtml.append("<input type='checkbox' class='checkbox' value='"+actionValue+"' name='"+checkBoxName+"' checked>"+actionName+"</td></tr>\n");
				}
				else
				{
					strhtml.append("<input type='checkbox' class='checkbox' value='"+actionValue+"' name='"+checkBoxName+"' checked>"+actionName+" \n");
				}
				i++;
			}	
			if(list.size()>0&&list.size()%6!=0)
			{
				strhtml.append("</td></tr>");
			}
			return strhtml.toString();
		}
		catch(Exception e)
		{
			logger.error("[561]UserCommisionFind.getCheckBox() 根据传过来的List和checkBoxName实现每6个checkbox换行效果失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据当前用户登录名,资源ID,被授权用户登录名,checkBoxName获得资源权限动作值/名称<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_LoginName
	 * @param skillconfer_SourceID
	 * @param skillconfer_DeanlLoginName
	 * @param checkBoxName
	 * @return String
	 */
	public String showCheckBox(String skillconfer_LoginName,String skillconfer_SourceID,String skillconfer_DeanlLoginName,String checkBoxName)
	{
		//获得List
		List list = getPurviewActionValue(skillconfer_LoginName,skillconfer_SourceID,skillconfer_DeanlLoginName);
		//获得单元格
		String strhtml	= getCheckBox(list,checkBoxName);
		return strhtml;
	}
	
	/**
	 * <p>Description:个人授权模块动态加载资源树子节点<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param strwhere
	 * @param id
	 * @param tuser
	 * @return String
	 */
	public String loadSourceTree(String strwhere,String id,String tuser)
	{
		//公共的资源生成树
		StringBuffer strjs	= new StringBuffer();
		//公共的查询sql
		StringBuffer sql	= new StringBuffer();
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			//当前用户不为Demo时的C1
			GetUserInfoList getUserInfoList		= new GetUserInfoList();
	  		String tuserId = getUserInfoList.getUserInfoName(tuser).getC1();
	  		if(tuser.toLowerCase().equals("demo"))
			{
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig+" a where a.source_parentid = '"+id+"'");
			}
	  		else
	  		{
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig +" a,"+roleskill+" b");
				sql.append(" where b.C610000007 = '"+tuserId+"' and a.source_id = b.C610000008");
				sql.append(" and b.C610000018 = '0' and a.source_parentid = '"+id+"'");
				sql.append(" union");
				sql.append(" select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig +" a,"+rolesSkillManage+" b,"+rolesUserGroupRel+" c,"+groupUser+" d,");
				sql.append( rolemanagetable + " rolemanagetable");
				//sql.append(" where d.C620000028 = '"+tuserId+"' and d.C620000027 = c.C660000027 and c.C660000028 = b.C660000006 and a.source_id = b.C660000007");
				//新加的权限控制(modify by wangyanguang)
				sql.append(" where d.C620000028 = '"+tuserId+"' and c.C660000028 = b.C660000006 and a.source_id = b.C660000007");
				sql.append(" and ((c.c660000026 = d.c620000028 and");		           
				sql.append(" c.c660000027 = d.c620000027) or");		       
				sql.append(" (c.c660000026 is null and c.c660000027 = d.c620000027))");
				sql.append(" and rolemanagetable.c1=c.c660000028");
				sql.append(" and a.source_parentid = '"+id+"'");
	  		}
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			sql.append(" order by source_orderby,source_parentid,source_id");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String sourceName     = "";
 	    	String sourceID       = "";
 	    	
			while(rs.next())
			{
				sourceName      = rs.getString("source_cnname");
				sourceID        = rs.getString("source_id");
	 	    	//xml生成树
	 	    	strjs.append("<tree text=\""+sourceName+"\" src=\"grouploadtree.jsp?str="+sourceID+";12\" action=\"commisionadd.jsp?sourceid="+sourceID+"\" target=\"mainFrame\" />");
			}
		}
		catch(Exception e)
		{
			logger.error("[562]GetSourceTree.loadSourceTree() 个人授权模块动态加载资源树子节点失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
		return strjs.toString();
	}
	
	/**
	 * <p>Description:个人授权管理模块根据用户登陆名查找该用户所处公司所有组信息顶层<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param strwhere
	 * @param tuser
	 * @return String
	 */
	public String getFirstGroupFloorTree(String strwhere,String tuser)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		
		try
		{	
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '0'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
			}
			else
			{	
				sql.append("select distinct a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a,"+usertablename+" b");
				sql.append(" where b.C630000001 = '"+tuser+"' and b.C630000013 = a.C1");
				sql.append(" and a.C630000025='0' ");
				
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
			}
				
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());

			treeinfo.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			treeinfo.append("try{");
			
 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	
	 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";13\",\"\",\"\",\"\",\"\",\"\",\"\"));");
			}
 	    	treeinfo.append("}catch(e){}");
 	    	treeinfo.append("</script>");
 	    	treeinfo.append("<script>document.write(tree);</script>");
		}
		catch(Exception e)
		{
			logger.error("[563]UserCommisionFind.getFirstGroupFloorTree() 个人授权管理模块根据用户登陆名查找该用户所处公司所有组信息顶层失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:个人授权管理模块显示该用户所处公司所有组信息子节点<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param strwhere
	 * @param tuser
	 * @param id
	 * @return String
	 */
	public String loadGroupTree(String strwhere,String id)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		
		try
		{	
			sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '"+id+"'");
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			sql.append(" order by a.C630000022");
			System.out.println(sql.toString());
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	
	 	    	//treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";13\" funpram=\""+groupName+":null:"+groupID+"\"/>");
	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";13\"/>");
			}
		}
		catch(Exception e)
		{
			logger.error("[564]UserCommisionFind.loadGroupTree() 个人授权管理模块显示该用户所处公司所有组信息子节点失败"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:个人授权模块生成用户信息树<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-2
	 * @param strwhere
	 * @param group_id
	 * @return String
	 */
	public String getUserTree(String strwhere,String group_id)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			GetGroupInfoList getGroupInfoList	= new GetGroupInfoList();
//			String groupType					= getGroupInfoList.getGroupInfo(group_id).getC630000021();
			String groupType					= "";
			//如果当前组类型为公司
			if(groupType.equals("2"))
			{
				sql.append("select a.C1 userId,a.C630000001 userLoginName,a.C630000003 userName,a.C630000017");
				sql.append(" from "+usertablename+" a where a.C630000012 = '0'");
				sql.append(" and a.C630000013 = '"+group_id+"' and a.C630000015 is null");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000017");
			}
			//如果当前组类型为部门
			else if(groupType.equals("3"))
			{
				sql.append("select a.C1 userId,a.C630000001 userLoginName,a.C630000003 userName,a.C630000017");
				sql.append(" from "+usertablename+" a where a.C630000012 = '0'");
				sql.append(" and a.C630000015 = '"+group_id+"'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000017");
			}else
			{
				sql.append("select usertable.C1 userId,usertable.C630000001 userLoginName,usertable.C630000003 userName,usertable.C630000017");
				sql.append(" from "+usertablename+" usertable,"+groupUser+" grouptable where usertable.C630000012 = '0'");
				sql.append(" and grouptable.c620000027 = '"+group_id+"'");
				sql.append(" and grouptable.c620000028 = usertable.c1");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by usertable.C630000017");
			}
			
			System.out.println("生成本公司上的所有用户SQL："+sql.toString());
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			String userId			= "";
			String userLoginName	= "";
			String userName			= "";

			while(rs.next())
			{
				userId			= rs.getString("userId");
				userLoginName	= rs.getString("userLoginName");
				userName		= rs.getString("userName");
				//没有下层节点
				treeinfo.append("<tree text=\""+userName+"\"   schkbox=\""+userId+";"+userLoginName+";"+group_id+"\"/>");
			}
		}
		catch(Exception e)
		{
			logger.error("[565]UserCommisionFind.getUserTree() 个人授权模块生成用户信息树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:根据用户登录名,资源ID,被授权用户登录名获得激活状态的个人授权纪录<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param loginName
	 * @param sourceID
	 * @param deanlLoginName
	 * @return List
	 */
	public List getActivationUserCommision(String loginName,String sourceID,String deanlLoginName)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("from SysUserCommisionpo a where a.c610000032 = '"+loginName+"'");
		sql.append(" and a.c610000031 = '"+sourceID+"'");
		sql.append(" and a.c610000033 = '"+deanlLoginName+"'");
		sql.append(" and a.c610000026 = '0'");
		
		try
		{
			List list	= HibernateDAO.queryObject(sql.toString());
			return list;
		}
		catch(Exception e)
		{
			logger.error("[567]UserCommisionFind.getActivationUserCommision() 根据用户登录名,资源ID,被授权用户登录名获得个人授权纪录的List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据用户登录名,资源ID,被授权用户登录名获得停用状态的个人授权纪录<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param loginName
	 * @param sourceID
	 * @param deanlLoginName
	 * @return List
	 */
	public List getStopUserCommision(String loginName,String sourceID,String deanlLoginName)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("from SysUserCommisionpo a where a.c610000032 = '"+loginName+"'");
		sql.append(" and a.c610000031 = '"+sourceID+"'");
		sql.append(" and a.c610000033 = '"+deanlLoginName+"'");
		sql.append(" and a.c610000026 = '1'");
		
		try
		{
			List list	= HibernateDAO.queryObject(sql.toString());
			return list;
		}
		catch(Exception e)
		{
			logger.error("[568]UserCommisionFind.getStopUserCommision() 根据用户登录名,资源ID,被授权用户登录名获得停用状态的个人授权纪录失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据已知长度的数组和取消授权原因停用个人授权<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param conditions			用分号分割的字符串
	 * @param confer_CancelCause	取消个人授权原因
	 * @return boolean
	 */
	public boolean stopSomeUserCommision(String conditions,String confer_CancelCause)
	{
		//实例化数据库操作类
		UserCommision userCommision = new UserCommision();
		//拆解用分号分割的数组,其参数为用户登录名,资源ID,被授权用户登录名(已知数组长度)
		String[] manyCondition = conditions.split(";");
		String loginName		= manyCondition[0];
		String sourceID			= manyCondition[1];
		String deanlLoginName	= manyCondition[2];
		try
		{
			//获得个人授权信息List
			List list = getActivationUserCommision(loginName,sourceID,deanlLoginName);
			for(Iterator it = list.iterator();it.hasNext();)
			{
				SysUserCommisionpo sysUserCommisionpo	= (SysUserCommisionpo)it.next();
				//实例化个人授权信息bean
				UserCommisionInfo userCommisionInfo		= new UserCommisionInfo();
				
				userCommisionInfo.setSkillconfer_ID(Function.nullString(sysUserCommisionpo.getC1()));
				userCommisionInfo.setSkillconfer_Cause(Function.nullString(sysUserCommisionpo.getC610000020()));
				userCommisionInfo.setSkillconfer_CancelCause(Function.nullString(confer_CancelCause));
				userCommisionInfo.setSkillconfer_StartTime(Function.nullLong(sysUserCommisionpo.getC610000022()).longValue());
				userCommisionInfo.setSkillconfer_EndTime(System.currentTimeMillis()/1000);
				userCommisionInfo.setSkillconfer_SkillID(Function.nullString(sysUserCommisionpo.getC610000024()));
				userCommisionInfo.setSkillconfer_UserID(Function.nullString(sysUserCommisionpo.getC610000025()));
				userCommisionInfo.setSkillconfer_Status("1");
				userCommisionInfo.setSkillconfer_DealUserID(Function.nullString(sysUserCommisionpo.getC610000027()));
				userCommisionInfo.setSkillconfer_memo(Function.nullString(sysUserCommisionpo.getC610000028()));
				userCommisionInfo.setSkillconfer_GroupID(Function.nullString(sysUserCommisionpo.getC610000029()));
				userCommisionInfo.setSkillconfer_SourceEnname(Function.nullString(sysUserCommisionpo.getC610000030()));
				userCommisionInfo.setSkillconfer_SourceID(Function.nullString(sysUserCommisionpo.getC610000031()));
				userCommisionInfo.setSkillconfer_LoginName(Function.nullString(sysUserCommisionpo.getC610000032()));
				userCommisionInfo.setSkillconfer_DeanlLoginName(Function.nullString(sysUserCommisionpo.getC610000033()));
				userCommisionInfo.setSkillconfer_GrandActionValue(Function.nullString(sysUserCommisionpo.getC610000034()));
				userCommisionInfo.setSkillconfer_GrandActionName(Function.nullString(sysUserCommisionpo.getC610000035()));
				
				userCommision.modifyUserCommision(userCommisionInfo,Function.nullString(sysUserCommisionpo.getC1()));
			}
			return true;
		}
		catch(Exception e)
		{
			logger.error("[569]UserCommisionFind.stopSomeUserCommision() 根据已知长度的数组和取消授权原因停用个人授权失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:停用个人授权记录<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param conditions			用逗号和分号分割的字符串
	 * @param confer_CancelCause	取消个人授权原因
	 * @return boolean
	 */
	public boolean stopAllUserCommision(String[] conditions,String confer_CancelCause)
	{
		try
		{
			//未知长度数组
			for(int i = 0;i<conditions.length;i++)
			{
				//若停止一组个人授权信息成功
				if(stopSomeUserCommision(conditions[i],confer_CancelCause))
				{
					continue;
				}
				else
				{
					logger.info("[570]UserCommisionFind.stopAllUserCommision() 停止一组以分号分割的个人授权记录失败");
					break;
				}
			}
			return true;
		}
		catch(Exception e)
		{
			logger.error("[570]UserCommisionFind.stopAllUserCommision() 停用个人授权记录失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据资源ID查找资源英文名<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-2
	 * @param sourceId
	 * @return String
	 */
	public String getSourceName(String sourceId)
	{
		try
		{
			String sql = "select a.sourceName from Sourceconfig a where a.sourceId='"+sourceId+"'";
			List list	= HibernateDAO.queryObject(sql);
			if(list.size()>0)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					String sourceName = (String)it.next();
					return sourceName;
				}
				return null;
			}
			else
			{
				logger.info("[571]UserCommisionFind.getSourceName() 系统中不存在该资源");
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("[571]UserCommisionFind.getSourceName() 根据资源ID查找资源英文名失败"+e.getMessage());
			return null;
		}
	}
	public StringBuffer getStatuslist()
	{
		StringBuffer selectMenu   = new StringBuffer();
		ShowMenu showMenu = new ShowMenu();
		try
		{
			selectMenu	= showMenu.getMenu(skillconferstatus,skillconfer);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			logger.error("获得个人授权状态下拉框异常！");
		}
		if(selectMenu!=null)
		{
			return selectMenu;
		}
		else
		{
			return null;
		}
		
	}
	public static void main(String[] args)
	{
		UserCommisionFind UserCommisionFind = new UserCommisionFind();
		//UserCommisionFind.getPurviewActionValue("Demo", "1");
		System.out.println(UserCommisionFind.getSourceName("1"));
	}

}
