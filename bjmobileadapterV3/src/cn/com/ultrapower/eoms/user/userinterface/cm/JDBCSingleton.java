package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class JDBCSingleton 
{
	static final Logger logger 	= (Logger) Logger.getLogger(JDBCSingleton.class);

	//private static Connection conn = null;
	//private static Statement stat = null;
	
	 
	public  synchronized static ResultSet getConn(String sql)
	{	
		Statement stat 		= null;
		Connection conn 	= null;
		ResultSet rs  		= null;
		IDataBase dataBase 	= null;
		if(conn==null)
		{
			System.out.println("connection 为空！");
			try
			{
				dataBase 	= DataBaseFactory.createDataBaseClassFromProp();
				conn = dataBase.getConn();
			}
			catch(Exception e)
			{
				logger.info("JDBCSingleton连接数据库时出现异常!");
			}
			finally
			{
				if(dataBase!=null)
				{
					dataBase.closeConn();
				}
			}
			if(stat==null)
			{
				System.out.println("statement 为空！");
				try
				{
					stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					rs = stat.executeQuery(sql);
				}
				catch(Exception e)
				{
					logger.info("查询数据库异常！");
				}
			}
			if(rs==null)
			{
				return null;
			}
			else
			{
				return rs;
			}
		}
		else
		{
			if(stat==null)
			{
				System.out.println("statement为空，Connection 不为空!");
				try
				{
					stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					rs = stat.executeQuery(sql);
				}
				catch(Exception e)
				{
					logger.info("查询数据库异常！");
				}
			}
			else
			{
				try
				{
					System.out.println("statment不为空！");
					rs = stat.executeQuery(sql);
				}
				catch(Exception e)
				{
					logger.info("查询数据库异常！");
				}
			}
			if(rs==null)
			{
				return null;
			}
			else
			{
				return rs;
			}
		}
	}
}
