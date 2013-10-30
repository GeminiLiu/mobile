package cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage;

import java.sql.SQLException;
import java.sql.Statement;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class OaManaged {
	GetFormTableName getFormTableName = new GetFormTableName();	
	/**
	 * 可以通过oa访问
	 * @param id0
	 */
	public void update0(String id0){
		GetFormTableName tablename	= new GetFormTableName();
		String ip			= tablename.GetFormName("ip");
		IDataBase dataBase = DataBaseFactory.createDataBaseClassFromProp();
		String sql ="update passwordmanage set ipcontrol_oaflag=0,ipcontrol_oaip='"+ ip +"' where pwdid in ("+ id0 + ")";
		Statement stm = dataBase.GetStatement();
		try {
			stm.executeUpdate(sql);
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
	}
	/**
	 * 不可以通过oa访问
	 * @param id0
	 */
	public void update1(String id1){
		GetFormTableName tablename	= new GetFormTableName();
		String ip			= tablename.GetFormName("ip");
		IDataBase dataBase = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm = dataBase.GetStatement();
		String sql ="update passwordmanage set ipcontrol_oaflag=1,ipcontrol_oaip='"+ ip +"' where pwdid in ("+ id1 + ")";
		try {
			stm.executeUpdate(sql);
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
	}
}
