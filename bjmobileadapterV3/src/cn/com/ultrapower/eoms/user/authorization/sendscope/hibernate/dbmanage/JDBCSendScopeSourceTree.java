package cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class JDBCSendScopeSourceTree {

	static final Logger logger = (Logger) Logger.getLogger(JDBCSendScopeSourceTree.class);
	
	/**
	 * 日期 2006-11-28
	 * @author wangyanguang/王彦广 
	 * @param strwhere
	 * @return StringBuffer
	 */
	public StringBuffer show_JDBCTree(String strwhere)
	{
		
		StringBuffer strjs = new  StringBuffer();
		strjs.append("<script language='JavaScript'>d = new dTree('d');d.add(0,-1,'',null,'','main');</script>");
    	strjs.append("<script language='JavaScript'>");
    	strjs.append("try{");
    	strjs.append(JDBC_SourceTree(strwhere));
    	strjs.append("}catch(e){}");
    	strjs.append("</script>");
    	strjs.append("<script>d.closeAll();document.write(d);</script>");
    	return strjs;
	}
	/**
	 * 
	 * 日期 2006-11-28
	 * 
	 * @author wangyanguang/王彦广 
	 * @param strwhere
	 * @return String
	 *
	 */
	public String JDBC_SourceTree(String strwhere)
	{
		StringBuffer treeinfo 	= new StringBuffer();
		String tablename 		= "";
		IDataBase dataBase		= null;
		Statement stm			= null;
		ResultSet rs			= null;
		//从配置文件中取表名
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			tablename					= getTableProperty.GetFormName("sourceconfig");
		}
		catch(Exception e)
		{
			logger.info("JDBCSendScopeSourceTree 类中JDBC_SourceTree(String strwhere)调用GetTableProperty时出现异常！"+e.getMessage());
		}
		System.out.println(tablename);
		try
		{
			if(!String.valueOf(tablename).equals("")&&!String.valueOf(tablename).equals("null"))
			{
				//根据配置文件中的表名和传入的参数确定sql语句
				String sql = "select source_id,source_parentid,source_cnname from "+tablename +" where 1=1 and source_type like '%1;%'";
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql = sql+" and "+strwhere +" order by source_parentid,source_id";
				}
				sql = sql + " order by source_parentid";
				System.out.println(sql);
				
				//实例化一个类型为接口IDataBase类型的工厂类
				dataBase	= DataBaseFactory.createDataBaseClassFromProp();
				//获得数据库查询结果集
				stm		= dataBase.GetStatement();
				rs		= dataBase.executeResultSet(stm,sql);
				
				String groupParentID = "";
	 	    	String groupName     = "";
	 	    	String groupID       = "";
	 	    	String treeflag      = "no";
	 	    	//int i = 0;
				while(rs.next())
				{
					groupParentID  = rs.getString("source_parentid");
		 	    	groupName      = rs.getString("source_cnname");
		 	    	groupID        = rs.getString("source_id");
		 	    	//调用js脚本生成树
		 	    	if(treeflag.equals("no"))
		 	    	{
		 	    		treeinfo.append("d.add(\""+groupID+"\",\""+groupParentID+"\",\"<font onclick=back_time('"+groupName+"','"+groupID+"','"+groupParentID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"\",'1');");
		 	    		treeflag = "yes";
		 	    	}else
		 	    	{
		 	    		treeinfo.append("d.add(\""+groupID+"\",\""+groupParentID+"\",\"<font onclick=back_time('"+groupName+"','"+groupID+"','"+groupParentID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"\",'0');");
		 	    	}
		 	    	
				}
				//关闭连接，释放资源
				rs.close();
				stm.close();
				dataBase.closeConn();
				
				return treeinfo.toString();
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("299 JDBCSendScopeSourceTree 类中JDBC_SourceTree(String strwhere)执行查询时出现异常！"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}

}
