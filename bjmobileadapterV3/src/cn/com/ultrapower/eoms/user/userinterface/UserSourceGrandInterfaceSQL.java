package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserSourceGrandPram;

public class UserSourceGrandInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(UserSourceGrandInterfaceSQL.class);
	private String sourcemanager			= "";
	private String systemmanage				= "";
	private String sourceconfig				= "";
	private String dutyorgnazition			= "";
	private String orgnazitionarranger		= "";
	private String usertablename			= "";
	private String grouptablename			= "";
	private String groupusertablename		= "";
	private String sysskill					= "";
	private String uid						= "";
	private String skillaction				= "";
	
	/**
	 * 从配制文件中读取表的配制信息
	 */
	public UserSourceGrandInterfaceSQL()
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
			sysskill			= getTableProperty.GetFormName("RemedyTrole");
			skillaction			= getTableProperty.GetFormName("managergrandaction");
			
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	/**
	 * 日期 2007-2-1
	 * 
	 * @author wangyanguang
	 * @param args void
	 *
	 */
	public boolean getUserGrandSQL(UserSourceGrandPram pramInfo)
	{
		String userLoginName 	= Function.nullString(pramInfo.getUserLoginName());
		String source_id		= Function.nullString(pramInfo.getSource_id());
		String source_parentid	= Function.nullString(pramInfo.getSource_parentid());
		String source_cnname	= Function.nullString(pramInfo.getSource_cnname());
		String source_name		= Function.nullString(pramInfo.getSource_name());
		String source_module	= Function.nullString(pramInfo.getSource_module());
		String source_orderby	= Function.nullString(pramInfo.getSource_orderby());
		String source_desc		= Function.nullString(pramInfo.getSource_desc());
		String source_type		= Function.nullString(pramInfo.getSource_type());
		String source_fieldtype	= Function.nullString(pramInfo.getSource_fieldtype());
		String actionValue		= Function.nullString(pramInfo.getActionValue());
		
		StringBuffer sql = new StringBuffer();
		String fordersource = "";
		sql.append(" select sourcetable.source_id from ");
		sql.append(  sourceconfig +" sourcetable,");
		sql.append(  sysskill +" skilltable,");
		sql.append(  usertablename + " usertable ");
		sql.append(" where  skilltable.c610000007=usertable.c1 ");
		sql.append(" and usertable.c630000001='"+userLoginName+"'");
		sql.append(" and sourcetable.source_id = skilltable.C610000008 ");
		
		if(!source_name.equals(""))
		{
			sql.append(" and sourcetable.source_name='"+source_name+"'");
		}
		if(!source_id.equals(""))
		{
			sql.append(" and sourcetable.source_id='"+source_id+"'");
		}
		if(!source_parentid.equals(""))
		{
			sql.append(" and sourcetable.source_parentid='"+source_parentid+"'");
		}
		if(!source_cnname.equals(""))
		{
			sql.append(" and sourcetable.source_cnname='"+source_cnname+"'");
		}
		if(!source_module.equals(""))
		{
			sql.append(" and sourcetable.source_module='"+source_module+"'");
		}
		if(!source_orderby.equals(""))
		{
			sql.append(" and sourcetable.source_orderby='"+source_orderby+"'");
		}
		if(!source_desc.equals(""))
		{
			sql.append(" and sourcetable.source_desc='"+source_desc+"'");
		}
		if(!source_type.equals(""))
		{
			sql.append(" and sourcetable.source_type='"+source_type+"'");
		}
		if(!source_fieldtype.equals(""))
		{
			sql.append(" and sourcetable.source_fieldtype='"+source_fieldtype+"'");
		}
		if(!actionValue.equals(""))
		{
			String tmpsql[] = actionValue.split(",");
			String sql1 = " and (";
			sql1 = sql1 + "skilltable.c610000010='"+(tmpsql[0])+"'";
			for(int i=1;i<tmpsql.length;i++)
			{
				sql1 = sql1 + " or skilltable.c610000010='"+(tmpsql[i])+"'";
			}
			sql1 = sql1 + ")";
			sql.append(sql1);
		}
		logger.info("sql:"+sql.toString());
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			if(rs.next())
			{
				rs.close();
				stm.close();
				dataBase.closeConn();
				return true;
			}
			else
			{
				rs.close();
				stm.close();
				dataBase.closeConn();
				return false;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		
	}

}
