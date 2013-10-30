package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.ShowMenu;
import cn.com.ultrapower.eoms.user.startup.Server;

public class RoleMenuAssociate 
{
	static final Logger logger = (Logger) Logger.getLogger(RoleMenuAssociate.class);
	public static String getRoleMenu(String fieldname,String tablename) 
	{
		GetFormTableName gftn 	= new GetFormTableName();
		System.out.println( fieldname+"::"+ tablename);
		String tableName 		= "";
		String fieldName 		= "";
		try
		{
			tableName 		= gftn.GetFormName(tablename);
			fieldName 		= gftn.GetFormName(fieldname);
		}
		catch(Exception e)
		{
			logger.info(" 211 RoleMenuAssociate 类中 getRoleMenu 方法中调用GetFormTableName时出现异常！");
		}
		
		ShowMenu sm 			= new ShowMenu();
		
		StringBuffer smbuff 	= new StringBuffer();
		try
		{
			smbuff 					= sm.getMenu(fieldName,tableName);
			if (smbuff.toString()==null||smbuff.toString()=="")
			{
				return "";
			}
			return smbuff.toString();
		}
		catch(Exception e)
		{
			logger.info("210 RoleMenuAssociate 类中 getRoleMenu 方法调用getMenu时出现异常!");
			return null;
		}

	}
	public static String getNoSelectRoleMenu(String fieldname,String tablename) 
	{
		GetFormTableName gftn 	= new GetFormTableName();
		System.out.println( fieldname+"::"+ tablename);
		String tableName 		= "";
		String fieldName 		= "";
		try
		{
			tableName 		= gftn.GetFormName(tablename);
			fieldName 		= gftn.GetFormName(fieldname);
		}
		catch(Exception e)
		{
			logger.info(" 211 RoleMenuAssociate 类中 getRoleMenu 方法中调用GetFormTableName时出现异常！");
		}
		
		ShowMenu sm 			= new ShowMenu();
		
		StringBuffer smbuff 	= new StringBuffer();
		try
		{
			smbuff 					= sm.getNoSelectMenu(fieldName,tableName);
			if (smbuff.toString()==null||smbuff.toString()=="")
			{
				return "";
			}
			return smbuff.toString();
		}
		catch(Exception e)
		{
			logger.info("210 RoleMenuAssociate 类中 getRoleMenu 方法调用getMenu时出现异常!");
			return null;
		}

	}
	/**
	 * @author shigang
	 * @creattime 2006-11-11
	 * @param FieldName
	 * @param TableName
	 * @return StringBuffer
	 * @根据字段名和表名封装出下拉菜单中的checkbox
	 */
	public static String getRoleCheckBox(String fieldname,String tablename) 
	{
		GetFormTableName gftn 	= new GetFormTableName();
		String tableName 		= gftn.GetFormName(tablename);
		String fieldName 		= gftn.GetFormName(fieldname);
		ShowMenu sm 			= new ShowMenu();
		StringBuffer smbuff 	= new StringBuffer();
		try
		{
		smbuff 					= sm.getCheckBox(fieldName,tableName);
		if (smbuff.toString()==null||smbuff.toString()=="")
		{
			return "";
		}
		System.out.println("wuwenlong");
		logger.info("get menu success");
		return smbuff.toString();
		}
		catch(Exception e)
		{
			e.getCause();
			return null;
		}

	}
}
