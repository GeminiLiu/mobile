package cn.com.ultrapower.eoms.user.config.grade.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cn.com.ultrapower.eoms.user.config.grade.bean.GradeBean;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.SysGroup;


public class Grade_Point {
	public ArrayList selects(){
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		ArrayList list = new ArrayList();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = dataBase2.GetStatement();
			String sql = "select fungradeconfig_gradnode from Fungradeconfig";
			rs = dataBase2.executeResultSet(stm,sql);
			while(rs.next()){
				GradeBean gradeBean = new GradeBean();
				gradeBean.setFungradeconfig_gradnode(rs.getString("fungradeconfig_gradnode"));
				list.add(gradeBean);
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
	 * 通过ID查询所有的值
	 * @param id
	 * @return
	 */
	public ArrayList selects(String id){
		ArrayList list = new ArrayList();
		long lid = Long.parseLong(id);
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = dataBase2.GetStatement();
			String sql = "select fungradeconfig_gradnode,fungradeconfig_gradvalue from Fungradeconfig where fungradeconfig_id="+ lid +"";
			rs = dataBase2.executeResultSet(stm,sql);
			while(rs.next()){
				list.add(rs.getString("fungradeconfig_gradnode"));
				list.add(rs.getString("fungradeconfig_gradvalue"));
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
	public boolean update(String id,String gradnode,String gradvalue) {
		long l_id = Long.parseLong(id);
		long l_gradwalue = Long.parseLong(gradvalue);
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		boolean bool = false;
		String sql = "update fungradeconfig set fungradeconfig_gradnode='"+ gradnode +"',fungradeconfig_gradvalue='"+ l_gradwalue +"' where fungradeconfig_id="+ l_id +"";
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
	 * 获得点数之和
	 */
	public int sum(){
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = null;
		ResultSet rs = null;
		int sum=0;
		try {
			stm = dataBase2.GetStatement();
			String sql = "select fungradeconfig_gradvalue from Fungradeconfig";
			rs = dataBase2.executeResultSet(stm,sql);
			while(rs.next()){
				sum = sum + Integer.parseInt(rs.getString("fungradeconfig_gradvalue"));
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
			String sql = "select fungradeconfig_gradnode from Fungradeconfig where fungradeconfig_gradnode='"+ gradnode +"'";
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
	 * 插入值
	 */
	public boolean insert(String id,String gradnode,String gradvalue){
		long lid = Long.parseLong(id);
		long lgradvalue = Long.parseLong(gradvalue);
		boolean bool=false;
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = dataBase2.GetStatement();
			String sql = "insert into fungradeconfig values('"+ lid +"','"+ gradnode +"','"+ lgradvalue +"')";
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
	 * 删除
	 */
	public boolean delete(String id) {
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		boolean bool=false;
		long it = Long.parseLong(id);
		Statement stm = dataBase2.GetStatement();
		String sql = "delete fungradeconfig where fungradeconfig_id="+it;
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
}
