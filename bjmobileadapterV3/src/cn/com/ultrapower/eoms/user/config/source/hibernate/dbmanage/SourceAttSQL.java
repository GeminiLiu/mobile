package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class SourceAttSQL
{

	String sourcemanager			= "";
	String systemmanage				= "";
	String sourceconfig				= "";
	String dutyorgnazition			= "";
	String orgnazitionarranger		= "";
	String usertablename			= "";
	String grouptablename			= "";
	String groupusertablename		= "";
	String sysskill					= "";
	String uid						= "";
	public SourceAttSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			sourcemanager		= getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage		= getTableProperty.GetFormName("systemmanage");
			sourceconfig		= getTableProperty.GetFormName("sourceconfig");
			dutyorgnazition		= getTableProperty.GetFormName("dutyorgnazition");
			orgnazitionarranger	= getTableProperty.GetFormName("orgnazitionarranger");
			usertablename		= getTableProperty.GetFormName("RemedyTpeople");
			grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename	= getTableProperty.GetFormName("RemedyTgroupuser");
			sysskill			= getTableProperty.GetFormName("RemedyTsysskill");
		}
		catch(Exception e)
		{
			System.out.print("从配置表中读取数据表名时出现异常！");
		}
	}
	
	//查询出DEMO用户的资源信息。
	public String getDemoParentSourceInfo()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.source_id,a.source_parentid,a.source_cnname,a.source_type from "+ sourceconfig +" a");
		sql.append(" where a.source_parentid='0'");
		System.out.println(sql.toString());
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm	= dataBase.GetStatement();
		ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 	 	  	
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"sourceattinfotree.jsp?sourceid="+sourceid+"\"  funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\" />");
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return sbf.toString();
	}
	
	//根据资源父ID查询下一级节点(Demo)
	public String getDemoSourceInfo(String parentsourceid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.source_id,a.source_parentid,a.source_cnname from "+ sourceconfig +" a");
		sql.append(" where a.source_parentid="+parentsourceid);
		
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm	= dataBase.GetStatement();
		ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"sourceinfotree.jsp?sourceid="+sourceid+"\"  funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\" />");
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return sbf.toString();
	}
	//查询出非DEMO用户的资源信息。
	//关联查询技能表与资源表逻辑：select * from 资源表 and 技能表 where 资源表的ID=技能表中的资源字段ID and 技能表中的权限=1000
	public String getParentSourceInfo(String userid)
	{
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		String fordersource = "";
		sql.append("select a.source_id,a.source_parentid,a.source_cnname from "+ sourceconfig +" a,"+ sysskill +" b");
		sql.append(" where a.source_id = b.C610000008 and b.C610000010='1000' and b.c610000007="+userid+" and not exists");
		sql.append(" (select c.source_id from "+sourceconfig +" c where a.source_parentid=c.source_id and c.source_id = b.C610000008)");

		sql1.append(" select a.source_id,a.source_parentid,a.source_name from "+sourceconfig+" a");
		sql1.append(" where a.source_type like '%4;%' and not exists");
		sql1.append(" (select b.source_id from "+sourceconfig+" b where a.source_parentid=b.source_id and c.source_type like '%4;%')");
	
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm	= dataBase.GetStatement();
		ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
		Statement stm1	= dataBase.GetStatement();
		ResultSet rs1	= dataBase.executeResultSet(stm1,sql1.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"sourceattinfotree.jsp?sourceid="+sourceid+"\"  funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\"/>");
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		
		try
		{
			while(rs1.next())
			{
				String sourceid 		= rs1.getString("source_id");
				String sourceparentid 	= rs1.getString("source_parentid");
				String sourcename		= rs1.getString("source_cnname");
				sbf.append("<tree text=\""+sourcename+"\" src=\"sourceattinfotree.jsp?sourceid="+sourceid+";1\"  funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\"/>");
			}
			rs1.close();
			stm1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs1,stm,dataBase);
		}
		
		
		
		return sbf.toString();
	}

	//根据资源父ID查询下一级节点(非Demo)
	public String getSourceInfo(String parentsourceid,String userid,String type)
	{
		StringBuffer sql = new StringBuffer();

		if(type.equals("1"))
		{
			sql.append("select source_id,source_parentid,source_cnname from "+ sourceconfig +" a");
			sql.append(" where a.source_parentid="+parentsourceid);
		}else
		{
			sql.append("select a.source_id,a.source_parentid,a.source_cnname from "+ sourceconfig +" a,"+ sysskill +" b");
			sql.append(" where a.source_id = b.C610000008 and b.C610000010='1000' and b.c610000007="+userid );
			sql.append(" and a.source_parentid="+parentsourceid);			
		}
		
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm	= dataBase.GetStatement();
		ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 		  	String sourcetype			= rs.getString("source_type");
	 	 	  	if(sourcetype.indexOf("4;")>0)
	 	 	  	{
	 	 	  		sbf.append("<tree text=\""+sourcename+"\" src=\"sourceattinfotree.jsp?sourceid="+sourceid+";1\"  funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\" />");
	 	 	  	}
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"sourceattinfotree.jsp?sourceid="+sourceid+"\"   funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\"/>");
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return sbf.toString();
	}

}
