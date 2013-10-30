package cn.com.ultrapower.ultrawf.models.design;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.util.FormatInt;
import cn.com.ultrapower.system.util.FormatString;

public class GroupUserInterfaceTmp
{
	public String addGroup(RemedyFormOp RemedyOp, String groupName)
	{
		int maxgid = 2000001;		
		RemedyDBOp rdbo = new RemedyDBOp();
		String gidSql = "select max(C106) as gid from " + rdbo.GetRemedyTableName("Group") + " where C106 >= " + maxgid;
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();

		ResultSet rs = idb.executeResultSet(stat, gidSql);
		try
		{
			if(rs.next())
			{
				int m_tmp = FormatInt.FormatStringToInt(rs.getString("gid"));
				if (m_tmp >= maxgid)
				{
					maxgid = m_tmp + 1;
				}
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}finally
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
		
		if(maxgid < 2000001) maxgid = 2000001;
		List<RemedyFieldInfo> gFieldList = new ArrayList<RemedyFieldInfo>();
		gFieldList.add(new RemedyFieldInfo("105", groupName, 4));
		gFieldList.add(new RemedyFieldInfo("106", maxgid + "", 4));
		gFieldList.add(new RemedyFieldInfo("107", "0", 6));
		gFieldList.add(new RemedyFieldInfo("8", groupName + "", 4));
		RemedyOp.FormDataInsertReturnID("Group", gFieldList);
		return String.valueOf(maxgid);
	}
	
	public void removeGroup(RemedyFormOp RemedyOp, String groupID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String sqlString = "select C1 from " + rdbo.GetRemedyTableName("Group") + " where C106 = " + groupID;
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		String groupC1 = "";
		try
		{
			if(rs.next())
			{
				groupC1 = rs.getString("C1");
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}finally
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
		RemedyOp.FormDataDelete("Group", groupC1);
	}
	
	public List<UserModel> getUserList()
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String sqlString = "select C1 as UserID, C101 as LoginName, C8 as UserName, C640000000 as Groups from " + rdbo.GetRemedyTableName("User");
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		List<UserModel> uList = new ArrayList<UserModel>();
		try
		{
			while(rs.next())
			{
				UserModel uModel = new UserModel();
				uModel.setUserID(rs.getString("UserID"));
				uModel.setLoginName(rs.getString("LoginName"));
				uModel.setUserName(rs.getString("UserName"));
				uModel.setGroups(FormatString.CheckNullString(DataBaseOtherDeal.GetFieldClobValue(rs, "Groups")));
				uList.add(uModel);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}finally
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
		return uList;
	}
	
	public List<UserModel> getUserList(String groupid)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String sqlString = "select C1 as UserID, C101 as LoginName, C8 as UserName, C104 as Groups from " + rdbo.GetRemedyTableName("User") + " where C104 like '%" + groupid + ";%'";
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		List<UserModel> uList = new ArrayList<UserModel>();
		try
		{
			while(rs.next())
			{
				UserModel uModel = new UserModel();
				uModel.setUserID(rs.getString("UserID"));
				uModel.setLoginName(rs.getString("LoginName"));
				uModel.setUserName(rs.getString("UserName"));
				uModel.setGroups(rs.getString("Groups"));
				uList.add(uModel);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}finally
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
		return uList;
	}
	
	public void setUser(UserModel uModel)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		if((";" + uModel.getGroups()).indexOf(";1;") < 0)
		{
			uModel.setGroups("1;" + uModel.getGroups());
		}
		String sqlString = "update " + rdbo.GetRemedyTableName("User") + " set C640000000 = '" + uModel.getGroups() + "' where C101 = '" + uModel.getLoginName() + "'";
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		idb.executeNonQuery(stat, sqlString);
		try {
			if (stat != null)
				stat.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}				
		idb.closeConn();
	}
	
	public UserModel getUser(String loginName)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String sqlString = "select C1 as UserID, C101 as LoginName, C8 as UserName, C640000000 as Groups from " + rdbo.GetRemedyTableName("User") + " where C101 = '" + loginName + "'";
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		UserModel uModel = new UserModel();
		try
		{
			if(rs.next())
			{
				uModel.setUserID(rs.getString("UserID"));
				uModel.setLoginName(rs.getString("LoginName"));
				uModel.setUserName(rs.getString("UserName"));
				uModel.setGroups(FormatString.CheckNullString(DataBaseOtherDeal.GetFieldClobValue(rs, "Groups")));
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}finally
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
		return uModel;
	}
}
