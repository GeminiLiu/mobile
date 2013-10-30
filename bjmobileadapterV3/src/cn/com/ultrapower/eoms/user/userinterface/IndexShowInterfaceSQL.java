package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.IndexTopBean;

public class IndexShowInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(IndexShowInterfaceSQL.class);
	
	public boolean getBoolValue(String username,String sourcename)
	{
		try 
		{
			String sql = "from SysSkillpo skilltable,Sourceconfig sourcetable,SysPeoplepo usertable " +
						"where skilltable.c610000007=usertable.c1 "+
						" and usertable.c630000001='"+username+"'"+
						" and skilltable.c610000008=sourcetable.sourceId  and sourcetable.sourceName='"+sourcename+"'"+
						" and skilltable.c610000018='0'";
			logger.info("sql::"+sql);
			List list = HibernateDAO.queryObject(sql);
			if(list!=null)
			{
				if(list.size()>0)
				{
					return true;
				}else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	
	public boolean getJdbcBoolValue(String username,String sourcename)
	{
		GetFormTableName getTableProperty 	= new GetFormTableName();
		String usertablename 				= getTableProperty.GetFormName("RemedyTpeople");
		String roletablename				= getTableProperty.GetFormName("RemedyTrole");
		String roleskillmanagetable 		= getTableProperty.GetFormName("RemedyTrolesskillmanage");
		String groupusertablename			= getTableProperty.GetFormName("RemedyTgroupuser");
		String usergrouprel					= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
		String rolemanagetable		 		= getTableProperty.GetFormName("RemedyTrolesmanage");
		
		boolean bl = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" select sourcetable.source_id from ");
		sql.append(roletablename +" skilltable,Sourceconfig sourcetable,"+ usertablename +" usertable ");
		sql.append("where skilltable.c610000007=usertable.c1 ");
		sql.append(" and usertable.c630000001='"+username+"'");
		sql.append(" and skilltable.c610000008=sourcetable.source_id ");
		sql.append(" and sourcetable.source_name='"+sourcename+"'");
		sql.append(" and skilltable.c610000018='0'");
		
		sql.append(" union ");
		
	    sql.append(" select sourcetable.source_id ");
	    sql.append(" from Sourceconfig sourcetable,");              
	    sql.append(  usertablename + " usertable,");
	    sql.append(  roleskillmanagetable +" roleskillmanage,");
	    sql.append(  usergrouprel+" rolegroupuserrel,");
	    sql.append(  rolemanagetable + " rolemanagetable,");
	    sql.append(  groupusertablename+" groupuser ");              
	    sql.append(" where usertable.c630000001 = '"+username+"'");     
	    sql.append(" and  groupuser.c620000028=usertable.c1 "); 
	    sql.append(" and sourcetable.source_name='"+sourcename+"'");
		//新加的权限控制
		sql.append(" and (rolegroupuserrel.c660000026 = groupuser.c620000028 or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		
	    sql.append(" and  roleskillmanage.c660000006  = rolegroupuserrel.c660000028 and roleskillmanage.c660000007 = sourcetable.source_id"); 
		
		logger.info("查询用户对资源是否有权限的SQL："+sql.toString());
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			if(rs.next())
			{
				bl = true;
			}
			//关闭数据库连接
			rs.close();
			stm.close();
			dataBase.closeConn();
			
			return bl;
		}
		catch(Exception e)
		{
			return false;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	/**
	 * 根据用户登陆名返回用户有权限的首页导航栏LIST。
	 * 日期 2007-2-5
	 * @author wangyanguang
	 * @param username
	 */
	public List getTopList(String username)
	{
		List returnList = new ArrayList();
		GetFormTableName getTableProperty 	= new GetFormTableName();
		String usertablename 				= getTableProperty.GetFormName("RemedyTpeople");
		String roletablename				= getTableProperty.GetFormName("RemedyTrole");
		String roleskillmanagetable 		= getTableProperty.GetFormName("RemedyTrolesskillmanage");
		String groupusertablename			= getTableProperty.GetFormName("RemedyTgroupuser");
		String usergrouprel					= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
		String remedyserver					= getTableProperty.GetFormName("driverurl");
		String rolemanagetable		 		= getTableProperty.GetFormName("RemedyTrolesmanage");
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct sourcetable.source_id,sourcetable.source_cnname,sourcetable.source_name,sourcetable.source_url,sourcetable.source_orderby from " );
		sql.append(  roletablename +" skilltable,");
		sql.append(" Sourceconfig sourcetable,");
		sql.append(  usertablename +" usertable " );
		sql.append(" where skilltable.c610000007=usertable.c1 ");
		sql.append(" and usertable.c630000001='"+username+"'");
		sql.append(" and skilltable.c610000008=sourcetable.source_id " );
		sql.append(" and sourcetable.source_type like '%6;%'");
		sql.append(" and sourcetable.source_type like '%0;%'");
		sql.append(" and sourcetable.source_isleft = '0'");
		sql.append(" and skilltable.c610000018='0'");

	    sql.append(" union ");
	    sql.append(" select distinct sourcetable.source_id,sourcetable.source_cnname,sourcetable.source_name,");
	    sql.append(" sourcetable.source_url,sourcetable.source_orderby");
	    sql.append(" from Sourceconfig sourcetable,");              
	    sql.append(  usertablename + " usertable,");
	    sql.append(  roleskillmanagetable +" roleskillmanage,");
	    sql.append(  usergrouprel+" rolegroupuserrel,");
	    sql.append(  rolemanagetable + " rolemanagetable,");
	    sql.append(  groupusertablename+" groupuser");              
	    sql.append(" where usertable.c630000001 = '"+username+"'");     
	    sql.append(" and  groupuser.c620000028=usertable.c1 "); 
		//新加的权限控制
		sql.append(" and (rolegroupuserrel.c660000026 = groupuser.c620000028 or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
	    sql.append(" and  roleskillmanage.c660000006  = rolegroupuserrel.c660000028 and roleskillmanage.c660000007 = sourcetable.source_id"); 
	    sql.append(" and  sourcetable.source_type like '%6;%' and  sourcetable.source_type like '%0;%'"); 
	    sql.append(" and sourcetable.source_isleft = '0'");
	    
	    sql.append(" order by source_orderby");
	    logger.info("导航栏SQL:"+sql.toString());
	    System.out.println(sql.toString());

		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
				while(rs.next())
				{
					String source_id    = Function.nullString(rs.getString("source_id"));
		 	    	String sourcecnname		= Function.nullString(rs.getString("source_cnname"));
		 	    	String sourcename		= Function.nullString(rs.getString("source_name"));
		 	    	String attvalue			= Function.nullString(rs.getString("source_url"));
		 	    	attvalue				= attvalue.replaceAll("remedyserver",remedyserver);
		 	    	logger.info("资源ID："+source_id+",资源中文名称："+sourcecnname+",资源英文名称:"+sourcename+",ulr:"+attvalue);
	 	    	
		 	    	if(!source_id.equals("")&&!sourcecnname.equals("")&&!sourcename.equals("")&&!attvalue.equals(""))
		 	    	{
		 	    		IndexTopBean indexbean = new IndexTopBean();
		 	    		indexbean.setSorucecnname(sourcecnname);
		 	    		indexbean.setSourceenname(sourcename);
		 	    		indexbean.setSourceid(source_id);
		 	    		indexbean.setUrl(attvalue);
		 	    		returnList.add(indexbean);
		 	    	}
				}
				//关闭数据库连接
				rs.close();
				stm.close();
				dataBase.closeConn();
				
				return returnList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}

	public static void main(String args[])
	{
		IndexShowInterfaceSQL indexsql = new IndexShowInterfaceSQL();
		System.out.println(indexsql.getJdbcBoolValue("wangyangyi","systemmanage"));
	}
}
