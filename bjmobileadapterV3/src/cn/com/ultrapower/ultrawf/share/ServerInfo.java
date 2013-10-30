package cn.com.ultrapower.ultrawf.share;

import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class ServerInfo {

	
	/**
	 * 获取数据库服务器时间
	 * @return
	 */
	public static long GetServerTime()
	{
		long longTime=0;
		longTime=System.currentTimeMillis();
		longTime=longTime/1000;
		//Date   today   =   Calendar.getInstance().getTime();
		
	/*	IDataBase m_dbConsole = GetDataBase.createDataBase();
		String m_DataBaseType=m_dbConsole.getDatabaseType();
		if(m_DataBaseType==null)
			m_DataBaseType="";
		//System.out.println("数据库类型："+m_DataBaseType);
		long longTime=0;
		String strSql="";
		if(m_DataBaseType.trim().toUpperCase().equals("ORACLE"))
		{
			strSql=" select sysdate  from dual ";
		}
		Statement stm=null;
		ResultSet m_BaseRs=null;
		try{
			if(!strSql.equals(""))
			{
				stm=m_dbConsole.GetStatement();
				m_BaseRs = m_dbConsole.executeResultSet(stm, strSql);
				if(m_BaseRs.next())
				{
					Date dateTime=m_BaseRs.getTimestamp("sysdate");
					//System.out.print("数据库服务器日期：");
					//System.out.print(dateTime);
					longTime=FormatTime.FormatDateToInt(dateTime);
				}
			}
		}catch(Exception ex)
		{
			System.err.println("ServerInfo.GetServerTime　方法"+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(m_BaseRs!=null)
					m_BaseRs.close();
			}catch(Exception ex)
			{
				System.err.print(ex.getMessage());
			}
			try{
				if(stm!=null)
					stm.close();
			}catch(Exception ex)
			{
				System.err.print(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}*/
		return longTime;
	}
}
