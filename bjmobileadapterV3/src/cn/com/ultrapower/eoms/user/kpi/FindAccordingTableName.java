package cn.com.ultrapower.eoms.user.kpi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class FindAccordingTableName
{

	/**
	 * 日期 2007-5-16
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	/**
	 * 日期 2007-5-16
	 * @author xuquanxing 
	 * @param tableflag void
	 * 根据传入的数字标识，查询其对应的表
	 */
	public String getTableName(String tableflag)
	{
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		String        accordtable  = "";//对应的表明
		String        sql          = "select t.tablename from kpi_info_config t where t.ownerflag='"+tableflag+"'";
		StringBuffer  buff         = new StringBuffer();
		stm	                       = dataBase.GetStatement();
		ResultSet res              = null;
		res	                       = dataBase.executeResultSet(stm,sql);
		try
		{
			if(res.next())
			{
				accordtable = res.getString("tablename");//取得表明，只需取得一条
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return accordtable;
	}
	
	/**
	 * 日期 2007-5-21
	 * @author xuquanxing 
	 * @param sql
	 * @return List
	 * 返回系统中目前用到的各指标集对应的表名
	 */
	public List getAllTableName(String sql)
	{
		System.out.println("getAllTableName="+sql);
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		String        accordtable  = "";//对应的表明
		String        owenflag     = "";
//		String        sql          = "select t.tablename from kpi_info_config t where t.ownerflag='"+tableflag+"'";
		StringBuffer  buff         = new StringBuffer();
		stm	                       = dataBase.GetStatement();
		ResultSet     res          = null;
		List          list         = new ArrayList();
		res	                       = dataBase.executeResultSet(stm,sql);
		try
		{
			while(res.next())
			{
				accordtable = res.getString("tablename");//取得表明，只需取得一条
				owenflag    = res.getString("ownerflag");//取得数字标识 
				list.add(accordtable+","+owenflag);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return list;
	}

}
