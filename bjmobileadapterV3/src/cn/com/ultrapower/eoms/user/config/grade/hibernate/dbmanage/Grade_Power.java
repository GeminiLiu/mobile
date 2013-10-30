package cn.com.ultrapower.eoms.user.config.grade.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class Grade_Power {
	/**
	 * 删除
	 */
	public boolean delete(String id) {
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		boolean bool=false;
		long it = Long.parseLong(id);
		Statement stm = dataBase2.GetStatement();
		String sql = "delete depgradeconfig where depgradeconfig_id="+it;
		try {
			stm.executeUpdate(sql);
			bool=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				stm.close();
				dataBase2.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return bool;
	}
	/**
	 * 插入值
	 */
	public boolean insert(String id,String dept,String gradvalue,String affectbusiness){
		long l_id = Long.parseLong(id);
		double l_gradvalue = Double.parseDouble(gradvalue);
		long l_affectbusiness =Long.parseLong(affectbusiness);
		boolean bool=false;
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = dataBase2.GetStatement();
			String sql = "insert into depgradeconfig values('"+ l_id +"','"+ dept +"','"+ l_gradvalue +"','"+ l_affectbusiness +"')";
			stm = dataBase2.GetStatement();
			stm.execute(sql);
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
                rs.close();
				stm.close();
				dataBase2.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return bool;
	}
	/**
	 * 判断有没有重复的
	 */
	public boolean iterant(String gradnode){
		boolean bool=false;
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = dataBase2.GetStatement();
			String sql = "select depgradeconfig_dep from depgradeconfig where depgradeconfig_dep='"+ gradnode +"'";
			rs = dataBase2.executeResultSet(stm,sql);
			if(rs.next()){
				bool=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
                rs.close();                
				stm.close();
				dataBase2.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return bool;
	}
	
	/**
	 * 通过ID查询所有的值
	 */
	public ArrayList selects(String id){
		ArrayList list = new ArrayList();
		long l_id = Long.parseLong(id);
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = dataBase2.GetStatement();
			String sql = "select depgradeconfig_dep,depgradeconfig_gradvalue,depgradeconfig_affectbusiness from depgradeconfig where depgradeconfig_id="+ l_id +"";
			rs = dataBase2.executeResultSet(stm,sql);
			while(rs.next()){
				list.add(rs.getString("depgradeconfig_dep"));
				list.add(rs.getString("depgradeconfig_gradvalue"));
				list.add(rs.getString("depgradeconfig_affectbusiness"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
                rs.close();
				stm.close();
				dataBase2.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 修改
	 */
	public boolean update(String id,String gradvalue,String affectbusiness) {
		long l_id = Long.parseLong(id);
		double l_gradwalue = Double.parseDouble(gradvalue);
		long l_affectbusiness = Long.parseLong(affectbusiness);
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		boolean bool = false;
		String sql = "update depgradeconfig set depgradeconfig_gradvalue='"+ l_gradwalue +"',depgradeconfig_affectbusiness='"+ l_affectbusiness +"' where depgradeconfig_id="+ l_id +"";
		Statement stm = dataBase2.GetStatement();
		try {
			stm.executeUpdate(sql);
			bool = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				stm.close();
				dataBase2.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return bool;
	}
	
	/**
	 * 获得权重值之和
	 */
	public double sum(){
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = null;
		ResultSet rs = null;
		double sum=0;
		try {
			stm = dataBase2.GetStatement();
			String sql = "select depgradeconfig_gradvalue from depgradeconfig";
			rs = dataBase2.executeResultSet(stm,sql);
			while(rs.next()){				
				sum = sum + Double.parseDouble(rs.getString("depgradeconfig_gradvalue"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
                rs.close();
				stm.close();
				dataBase2.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return sum;
	}
}
