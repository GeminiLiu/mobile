package cn.com.ultrapower.eoms.user.database;

import java.sql.*;


import com.ultrapower.eoms.common.cfg.Config;
import com.ultrapower.eoms.common.cfg.ConfigKeys;

import cn.com.ultrapower.system.database.ConnectionParam;
/**
 * <b>数据库操作类 V2.000</b><br>
 * 作者:BigMouse(李昊原)<br>
 * 时间:2006年9月18日<br>
 * <b>说明:</b><br>
 * 对Oracle数据库进行操作的类,支持连接池或者直接链接,可以实现:<br>
 * 1.执行SQL语句,并返回影响行数;<br>
 * 2.执行SQL语句,返回ResultSet对象.<br>
 */
class OracleConsole implements IDataBase
{    
    /**
     * 在server.xml中的Oracle数据库链接字符串
     */
    private static boolean 	isUsePool		= true;
	private static String 	DBType 			= "";
    //private static String 	DBString 		= "";
    private static String 	databaseDriver 	= "";
    private static String 	databaseUrl 	= "";
    private static String 	username 		= "";
    private static String 	password 		= "";      
    //Statement stat=null;
    //private  String poolname="orapool";
    //private  int MinConnection=5;
    //private  int MaxConnection=100;
    //private  int TimeoutValue=20000;//
    ConnectionParam conPar;
   
    
    private Connection oraConn = null;
    //private List StatementList=new ArrayList();   

//	protected OracleConsole() {}
//    
//    protected OracleConsole(boolean isPool)
//    {
//    	DBType 			= Constants.DBSMTYPE;
//    	databaseDriver 	= Constants.DBDRIVERNAME;
//    	databaseUrl 	= Constants.DBDRIVERURL;
//    	username 		= Constants.DBUSER;
//    	password 		= Constants.DBPASSWORD;
//        isUsePool = isPool;
//    }
    
	/**
	 * 重载的构造函数,实例化Connection对象
     * @param dbString:在server.xml中的Oracle数据库链接字符串
	 */
	/*protected OracleConsole(String dbString)
	{
		DBString = dbString;
		oraConn = null;
        isUsePool = true;
	}*/
    
    /**
     * 重载的构造函数,实例化Connection对象
     * @param Driver:数据库连接的驱动
     * @param url:数据库服务器地址
     * @param username:用户名
     * @param password:密码
     */
    protected OracleConsole(String DBType, String Driver, String url,
			String UserName, String PassWord) {
		OracleConsole.DBType = DBType;

	}

	public String getDatabaseType(){
		return DBType;
	}
    

	/**
	 * 建立Statement对象
     * @param conn:Oracle 数据库链接对象
     * @return Statement对象
	 */
	public Statement GetStatement()
	{
		Statement	stat =null;
		try
		{	
			if(getConn()!=null)
			{
				stat = oraConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			}
		}
		catch(Exception e)
		{
			if(stat==null)
			{
				//DBPoolManage dbpoolmanage=new DBPoolManage();
				//dbpoolmanage.RemoveAllConn();
				oraConn=null;
				if(getConn()!=null)
				{
					try
					{
						stat = oraConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					}
					catch(Exception e1)
					{
						e1.getMessage();
					}
				}
				//dbpoolmanage=null;
			}
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return stat;
	}
	
	/**
	 * 取得Connection对象
     * @return Oracle数据库链接对象
	 */
	public synchronized Connection getConn() {
		try {
			if (oraConn == null || oraConn.isClosed()) {
				oraConn = DriverManager.getConnection("proxool."
						+ Config.getValue(ConfigKeys.DB_ALIAS));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return oraConn;
	}
	
	/**
	 * 关闭Oracle数据库链接
	 *
	 */
	public synchronized void closeConn() {
		try {
			if (oraConn != null)
				oraConn.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行SQL语句,并返回影响行数<br>
	 * <b>举例:</b><br>
	 * int i = oc.ExecuteNonQuery(oc.getConn(),"update set id=1");
	 * 
	 * @param conn:Oracle数据库链接对象,使用OracleConsole对象的getConn()方法得到
	 * @param SqlString:对数据库操作的Sql语句
	 * @return Sql语句操作Oracle数据库所影响的行数
	 */
	public int executeNonQuery(Statement p_stat, String SqlString)
	{
		int Count = -1;
		try
		{		
			Count = p_stat.executeUpdate(SqlString);
		}
		catch(Exception e)
		{
			Count = -2;
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return Count;
	}
	
	/**
	 * 执行SQL语句,返回ResultSet对象<br>
     * <b>举例:</b><br>
     * ResutltSet rs = oc.ExecuteResultSet(oc.getConn(),"select * from testTable");<br>
     * {对ResultSet的操作...}<br>
     * oc.closeConn();
     * @param conn:Oracle数据库链接对象,使用OracleConsole对象的getConn()方法得到
     * @param SqlString:对数据库操作的Sql语句
     * @return ResultSet对象,Sql语句操作Oracle数据库所查询出的数据集
	 */
	public ResultSet executeResultSet(Statement p_stat, String SqlString)
	{
		try
		{
			return p_stat.executeQuery(SqlString);
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
            e.printStackTrace();
		}
		return null;
	}
	
	public Object executeScalar(Statement p_stat, String SqlString)
	{
		
		try
		{
			ResultSet rs = p_stat.executeQuery(SqlString);
			if(rs.next())
			{
				Object obj = rs.getObject(1);
				rs.close();
				return obj;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}


}
