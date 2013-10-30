package cn.com.ultrapower.eoms.user.comm.function;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GetFormTableName {
	public  String url;
	public  String user;
	public  String password;
	public  int serverport;
	static final Logger logger = (Logger) Logger.getLogger(GetFormTableName.class);
	public String GetFormName(String TableName)
	{
		try
		{
			String formname		= "";
			Function.initPram();
			if(ConfigContents.complete)
			{
				formname			= String.valueOf(ConfigContents.mapParam.get(TableName));
			}
			else
			{
				logger.error("获得变量参数失败");
			}
			
			System.out.println("获得表名："+formname+"   "+TableName);
	    	return formname;
		}
		catch(Exception e)
		{
			logger.error("获得变量参数失败");
			return "";
			
		}
	}
	public HashMap GetDataparam(String pram)
	{
		try
		{
			HashMap formname		= new HashMap();
			Function.initPram();
			if(ConfigContents.complete)
			{
				formname			= (HashMap)ConfigContents.mapParam.get(pram);
			}
			else
			{
				logger.error("获得变量参数失败");
			}
	    	return formname;
		}
		catch(Exception e)
		{
			logger.error("获得变量参数失败");
			return null;
			
		}
	}
}
