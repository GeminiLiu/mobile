package cn.com.ultrapower.eoms.user.kpi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class KpiEditDeal
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
	 * @param sql
	 * @return boolean
	 * 实现修改操作
	 */
	public boolean kpiEdit(String sql)
	{
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase     dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		= null;
		int           count		= 0;
		boolean       issuc     = false;
		StringBuffer  buff      = new StringBuffer();
		stm	                    = dataBase.GetStatement();
		try
		{
			count	                = dataBase.executeNonQuery(stm,sql);
			if(count>=0)
			{
				issuc = true;
			}else
			{
				issuc = false;
			}
		}catch(Exception e)
		{
			
		}finally
		{
			try
			{
				stm.close();
				dataBase.closeConn();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return issuc;
	}
	
	
	/**
	 * 日期 2007-5-16
	 * @author xuquanxing 
	 * @param sql
	 * @return boolean
	 */
	public boolean kpiDelete(String sql)
	{
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase     dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		= null;
		int           count		= 0;
		boolean       issuc     = false;
		StringBuffer  buff      = new StringBuffer();
		stm	                    = dataBase.GetStatement();
		try
		{
			count	                = dataBase.executeNonQuery(stm,sql);
			if(count>=0)
			{
				issuc = true;
			}else
			{
				issuc = false;
			}
		}catch(Exception e)
		{
			
		}finally
		{
			try
			{
				stm.close();
				dataBase.closeConn();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return issuc;
	}
	

}
