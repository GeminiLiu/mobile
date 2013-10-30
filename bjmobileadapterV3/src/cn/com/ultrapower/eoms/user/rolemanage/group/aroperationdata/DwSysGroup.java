package cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata;

import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.SysGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class DwSysGroup {

	Function function = new Function();

	// -------------添加
	IDataBase dataBase = DataBaseFactory.createDataBaseClassFromProp();
	public boolean insert(String division, String dept) {
		boolean bool = false;
		String newStr = function.getNewID("companyrel", "companyrel_id");
		int newId = Integer.parseInt(newStr);
		Statement stm = dataBase.GetStatement();
		// 获得数据库查询结果集
		try {
			stm.executeUpdate("insert into companyrel values(" + newId + ",'" + division + "','" + dept + "')");
			bool=true;
		} catch (SQLException e) {
			System.out.println("获得数据失败");
			e.printStackTrace();
		}
		finally{
			try {
				stm.close();
				dataBase.closeConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bool;
	}

	// ---------------查询
	public ArrayList select() {
		GetFormTableName getTableProperty	= new GetFormTableName();
		String grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
		Statement stm = null;
		ResultSet rs = null;
		SysGroup sysGroup;
		ArrayList list = new ArrayList();
		try {
			IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
			stm = dataBase2.GetStatement();
			rs = dataBase2.executeResultSet(stm, "select c1,C630000018 from "+grouptablename+" where c630000021=2 and c630000027 = 2 and c630000025=0");
			while(rs.next()){
				sysGroup = new SysGroup();
				sysGroup.setId(rs.getString(1));
				sysGroup.setName(rs.getString(2));
				list.add(sysGroup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
                rs.close();
				stm.close();
				dataBase.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return list;
	}
	
	//判断是否有重复重数据
	public boolean cops(String division,String dept) {
		
		boolean bool = false;
		Statement stm = null;
		ResultSet rs = null;
		try {
			IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
			stm = dataBase2.GetStatement();
			rs = dataBase2.executeResultSet(stm, "select companyrel_id from companyrel where companyrel_companyid='"+ division +"' and companyrel_depid='"+ dept +"'");
			if(!rs.next()){
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
                rs.close();
				stm.close();
				dataBase.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return bool;
	}

	// ----------------修改
	public boolean update(String id ,String division,String dept) {
		boolean bool = false;
		String sql = "update companyrel set companyrel_companyid='"+ division +"',companyrel_depid='"+ dept +"' where companyrel_id="+ id +"";
		Statement stm = dataBase.GetStatement();
		try {
			stm.executeUpdate(sql);
			bool = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				stm.close();
				dataBase.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return bool;
	}

	// ------------------删除
	public boolean delete(String id) {
		boolean bool=false;
		System.out.println(id);
		int it = Integer.parseInt(id);
		Statement stm = dataBase.GetStatement();
		String sql = "delete companyrel where companyrel_id="+it;
		try {
			stm.executeUpdate(sql);
			bool=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				stm.close();
				dataBase.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return bool;
	}
	
	public ArrayList selectId(String id){
		System.out.println("aaaa");
		Statement stm = dataBase.GetStatement();
		String sql = "";
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		if(String.valueOf(id).equals("null")){
  			sql = "select companyrel_companyid,companyrel_depid from companyrel";
  		}else{
  			int it = Integer.parseInt(id);
  			sql = "select companyrel_companyid,companyrel_depid from companyrel where companyrel_id="+ id +"";
  		}
		try{
		stm = dataBase2.GetStatement();
		rs = dataBase2.executeResultSet(stm, sql);
		if(rs.next()){
			list.add(rs.getString(1));
			list.add(rs.getString(2));
		}
		}catch(Exception e){
			e.getStackTrace();
		}finally{
			try{
                rs.close();
				stm.close();
				dataBase.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return list;
	}
	
	public ArrayList selectName(String companyrel_companyid,String companyrel_depid){
		GetFormTableName getTableProperty	= new GetFormTableName();
		String grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
		Statement stm = dataBase.GetStatement();
		String sql = "";
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		IDataBase dataBase2 = DataBaseFactory.createDataBaseClassFromProp();
		sql = "select t1.c630000018,t2.c630000018 from "+grouptablename+" t1,"+grouptablename+" t2 where t1.c1='"+ companyrel_companyid +"' and t2.c1='"+ companyrel_depid +"'";
		try{
		stm = dataBase2.GetStatement();
		rs = dataBase2.executeResultSet(stm, sql);
		if(rs.next()){
			list.add(rs.getString(1));
			list.add(rs.getString(2));
		}
		}catch(Exception e){
			e.getStackTrace();
		}finally{
			try{
                rs.close();
				stm.close();
				dataBase.closeConn();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return list;
	}
}
