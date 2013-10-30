package cn.com.ultrapower.eoms.user.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据库操作类的接口
 * @author BigMouse
 *
 */
public interface IDataBase
{
    public String getDatabaseType();
	
    /**
     * 取得Connection对象
     * @return Oracle数据库链接对象
     */
    public Connection getConn();
    public Statement GetStatement();    
    /**
     * 关闭Oracle数据库链接
     *
     */
    public void closeConn();
    
    /**
     * 执行SQL语句,并返回影响行数<br>
     * <b>举例:</b><br>
     * int i = oc.ExecuteNonQuery(oc.getConn(),"update set id=1");
     * @param conn:Oracle数据库链接对象,使用OracleConsole对象的getConn()方法得到
     * @param SqlString:对数据库操作的Sql语句
     * @return Sql语句操作Oracle数据库所影响的行数
     */
    public int executeNonQuery(Statement p_stat, String SqlString);
    
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
    public ResultSet executeResultSet(Statement p_stat, String SqlString);
    
    public Object executeScalar(Statement p_stat, String SqlString);
}
