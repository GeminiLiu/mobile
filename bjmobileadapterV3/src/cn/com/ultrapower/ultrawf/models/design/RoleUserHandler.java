package cn.com.ultrapower.ultrawf.models.design;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

public class RoleUserHandler
{
	private static final String roleUserForm = "WF:Workflow_RoleUser";
	private String roleUserFormID;
	
	public RoleUserHandler()
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		roleUserFormID = rdbo.GetRemedyTableName(roleUserForm);
	}
	
	/**
	 * 新增角色用户关系
	 * @param RemedyOp RemedyFormOp对象
	 * @param userName 用户登录名
	 * @param roleID 角色细分ID
	 */
	public void addRoleUser(RemedyFormOp RemedyOp, String userName, String roleID)
	{
		List ruFieldList = new ArrayList();
		ruFieldList.add(new RemedyFieldInfo("650000001", userName, 4));
		ruFieldList.add(new RemedyFieldInfo("650000003", roleID, 4));
		
		RemedyOp.FormDataInsertReturnID(roleUserForm,ruFieldList);
	}
	
	/**
	 * 获取角色细分下的用户，返回该角色细分下的用户的登录名集合
	 * @param roleID 角色细分ID
	 * @return 该角色细分下的用户的登录名集合
	 */
	public List<String> getRoleUsers(String roleID)
	{
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		String sqlString = "select C650000001 as username from " + roleUserFormID + " where C650000003 = '" + roleID + "'";
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		
		List<String> userList = new ArrayList<String>();
		try
		{
			while(rs.next())
			{
				userList.add(rs.getString("username"));
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stat != null)
					stat.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			idb.closeConn();
		}
		
		return userList;
	}
	
	/**
	 * 获取角色细分下的用户，返回该角色细分下的用户的登录名集合
	 * @param roleID 角色细分ID
	 * @return 该角色细分下的用户的登录名集合
	 */
	public List<UserModel> getUsers(String roleID)
	{
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		String sqlString = "select C650000001 as username, C650000002 as fullname from " + roleUserFormID + " where C650000003 = '" + roleID + "'";
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		
		List<UserModel> userList = new ArrayList<UserModel>();
		try
		{
			while(rs.next())
			{
				UserModel model = new UserModel();
				model.setLoginName(rs.getString("username"));
				model.setUserName(rs.getString("fullname"));
				userList.add(model);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stat != null)
					stat.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			idb.closeConn();
		}
		
		return userList;
	}
	
	/**
	 * 删除角色用户关系
	 * @param RemedyOp RemedyFormOp对象
	 * @param roleID 角色细分ID
	 */
	public void removeRoleUser(RemedyFormOp RemedyOp, String roleID)
	{
		String sqlString = "select C1 from " + roleUserFormID + " where C650000003 = '" + roleID + "'";
		executeRemoveRoleUser(RemedyOp, sqlString);
	}
	
	/**
	 * 删除角色用户关系
	 * @param RemedyOp RemedyFormOp对象
	 * @param userName 用户登录名
	 * @param roleID 角色细分ID
	 */
	public void removeRoleUser(RemedyFormOp RemedyOp, String userName, String roleID)
	{
		String sqlString = "select C1 from " + roleUserFormID + " where C650000001 = '" + userName + "' and C650000003 = '" + roleID + "'";
		executeRemoveRoleUser(RemedyOp, sqlString);
	}
	
	private void executeRemoveRoleUser(RemedyFormOp RemedyOp, String sqlString)
	{
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		
		try
		{
			while(rs.next())
			{
				RemedyOp.FormDataDelete(roleUserForm, rs.getString("C1"));
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stat != null)
					stat.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			idb.closeConn();
		}
	}
}
