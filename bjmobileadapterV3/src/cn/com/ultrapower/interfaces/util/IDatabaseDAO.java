package cn.com.ultrapower.interfaces.util;
/**
 * @function 数据库处理
 * @version 1.0
 * @author 杨洪
 * @since 2009-5-20
 */

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class IDatabaseDAO {
	
	public List executeQuery(String sql){
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		List list=new ArrayList();
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm, sql);
			list=resultSetToList(resultSet);
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				if (resultSet != null){
					resultSet.close();
				}
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null){
					stm.close();
				}
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}		
		return list;
	}
	
	public int executeUpdate(String sql){
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		int i=-1;
		try {
			stm = m_dbConsole.GetStatement();
			i=stm.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}finally{
			try {
				if (i <0){
					m_dbConsole.getConn().rollback();
				}else{
					m_dbConsole.getConn().commit();					
				}
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null){
					stm.close();
				}
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return i;
	}
	
	public static List resultSetToList(ResultSet rs)throws java.sql.SQLException{ 
		if(rs==null){ 
			return null; 
		}
		ResultSetMetaData md =rs.getMetaData(); 
		int columnCount = md.getColumnCount(); 
		List list = new ArrayList(); 
		while(rs.next()){ 
			HashMap rowData = new HashMap(columnCount); 
			for(int i=1;i<=columnCount;i++){ 
				rowData.put(md.getColumnName(i),rs.getObject(i)); 
			} 
			list.add(rowData); 
			System.out.println("list:"+list.toString()); 
		} 
		rs.close();
		return list; 
	} 
	
}
