package cn.com.ultrapower.ultrawf.models.design;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.*;

public class ChildRoleHandler
{
	private static final String childRoleForm = "WF:App_WorkFlowChildRole";
	private static final String tplMatchConditionForm = "WF:App_WorkFlowMatchConditions";
	
	public List<ChildRoleModel> getFormChildRoleList(String baseID)
	{
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("orderby", "ParentRoleID asc", 7);
		rdp.addIndirectPar("baseid", baseID, 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL("query.design.ChildRole",rdp, "");
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		RowSet rowSet = table.executeQuery(sql,null,0,0,0);
		List<ChildRoleModel> crList = new ArrayList<ChildRoleModel>();
		for(int i = 0; i < rowSet.length(); i++)
		{
			Row rs = rowSet.get(i);
			ChildRoleModel crModel = new ChildRoleModel();
			crModel.setRoleID(rs.getString("RoleID"));
			crModel.setRoleC1(rs.getString("RoleC1"));
			crModel.setRoleName(rs.getString("RoleName"));
			crModel.setParentRoleID(rs.getString("ParentRoleID"));
			crModel.setParentRoleName(rs.getString("ParentRoleName"));
			crModel.setCondition(rs.getString("RoleCondition"));
			crModel.setChildRoleManager(rs.getString("ChildRoleManager"));
			crModel.setChildRoleManagerID(rs.getString("ChildRoleManagerID"));
			crList.add(crModel);
		}
		return crList;
	}
	public List<ChildRoleModel> getRoleChildRoleList(String baseID,String roleID)
	{
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("orderby", "ParentRoleID asc", 7);
		rdp.addIndirectPar("baseid", baseID, 4);
		rdp.addIndirectPar("roleid", roleID, 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL("query.design.ChildRole",rdp, "");
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		RowSet rowSet = table.executeQuery(sql,null,0,0,0);
		List<ChildRoleModel> crList = new ArrayList<ChildRoleModel>();
		for(int i = 0; i < rowSet.length(); i++)
		{
			Row rs = rowSet.get(i);
			ChildRoleModel crModel = new ChildRoleModel();
			crModel.setRoleID(rs.getString("RoleID"));
			crModel.setRoleC1(rs.getString("RoleC1"));
			crModel.setRoleName(rs.getString("RoleName"));
			crModel.setParentRoleID(rs.getString("ParentRoleID"));
			crModel.setParentRoleName(rs.getString("ParentRoleName"));
			crModel.setCondition(rs.getString("RoleCondition"));
			crModel.setChildRoleManager(rs.getString("ChildRoleManager"));
			crModel.setChildRoleManagerID(rs.getString("ChildRoleManagerID"));			
			crList.add(crModel);
		}
		return crList;
	}
	
	public List<String> setMatchConditions(List<ChildRoleModel> crList, String baseID, String parentRoleID, boolean isRebuild)
	{
		List<String> groupList = new ArrayList<String>();
		if(crList == null) { return groupList; }
		if(isRebuild)
		{
			deleteMatchConditions(parentRoleID);
		}
		else
		{
			List<ChildRoleModel> crOldList = getRoleChildRoleList(baseID, parentRoleID);
			for(ChildRoleModel crOldModel : crOldList)
			{
				for(ChildRoleModel crModel : crList)
				{
					if(crModel.getCondition().equals(crOldModel.getCondition()))
					{
						crList.remove(crModel);
						break;
					}
				}
			}
		}
		
		
		if(crList.size() > 0)
		{
			RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
					Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
					Constants.REMEDY_DEMOPASSWORD);
			RemedyOp.RemedyLogin();
			for(Iterator<ChildRoleModel> it = crList.iterator(); it.hasNext();)
			{
				ChildRoleModel crModel = it.next();
				crModel.setRoleName(crModel.getRoleName() + crModel.getParentRoleName());
				
				GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
				String roleID = guit.addGroup(RemedyOp, crModel.getRoleName());
				groupList.add(roleID);
				
				List crFieldList = new ArrayList();
				crFieldList.add(new RemedyFieldInfo("700024007", crModel.getBaseSchema(), 4));
				crFieldList.add(new RemedyFieldInfo("700024008", crModel.getBaseName(), 4));
				crFieldList.add(new RemedyFieldInfo("700024003", crModel.getParentRoleID(), 4));
				crFieldList.add(new RemedyFieldInfo("700024004", crModel.getParentRoleName(), 4));
				crFieldList.add(new RemedyFieldInfo("700024001", roleID, 4));
				crFieldList.add(new RemedyFieldInfo("700024002", crModel.getRoleName(), 4));
				crFieldList.add(new RemedyFieldInfo("700024005", crModel.getDimensionList().size() + "", 2));
				crFieldList.add(new RemedyFieldInfo("700024006", crModel.getCondition(), 4));
				crFieldList.add(new RemedyFieldInfo("700024010", crModel.getChildRoleManager(), 4));
				crFieldList.add(new RemedyFieldInfo("700024011", crModel.getChildRoleManagerID(), 4));
				RemedyOp.FormDataInsertReturnID(childRoleForm,crFieldList);
				
				for(Iterator<DimensionModel> it1 = crModel.getDimensionList().iterator(); it1.hasNext();)
				{
					DimensionModel dModel = it1.next();
					List mcFieldList = new ArrayList();
					mcFieldList.add(new RemedyFieldInfo("700024101", roleID, 4));
					mcFieldList.add(new RemedyFieldInfo("700024102", crModel.getRoleName(), 4));
					mcFieldList.add(new RemedyFieldInfo("700024103", crModel.getParentRoleID(), 4));
					mcFieldList.add(new RemedyFieldInfo("700024104", crModel.getParentRoleName(), 4));
					mcFieldList.add(new RemedyFieldInfo("700024107", dModel.getDFieldID(), 4));
					mcFieldList.add(new RemedyFieldInfo("700024106", dModel.getDFieldName(), 4));
					mcFieldList.add(new RemedyFieldInfo("700024105", dModel.getDFieldText(), 4));
					mcFieldList.add(new RemedyFieldInfo("700024108", dModel.getFieldValue(), 4));				
					RemedyOp.FormDataInsertReturnID(tplMatchConditionForm,mcFieldList);
				}
			}
			RemedyOp.RemedyLogout();
		}
		return groupList;
	}
	
	public boolean deleteChildRole(String childRoleID)
	{
		
		RemedyDBOp rdbo = new RemedyDBOp();
		String childRoleFormName = rdbo.GetRemedyTableName(childRoleForm);
		String tplMCFormName = rdbo.GetRemedyTableName(tplMatchConditionForm);
		
		String sqlString1 = "select C1, C700024001 from " + childRoleFormName + " where C700024001 = '" + childRoleID + "'";
		String sqlString2 = "select C1 from " + tplMCFormName + " where C700024101 = '" + childRoleID + "'";
		
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs1 = idb.executeResultSet(stat, sqlString1);
		ResultSet rs2 = null;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		try
		{
			GroupUserInterfaceTmp guft = new GroupUserInterfaceTmp();
			RoleUserHandler ruHandler = new RoleUserHandler();
			while(rs1.next())
			{
				String childRoleGroupID = rs1.getString("C700024001");
				guft.removeGroup(RemedyOp, childRoleGroupID);
				ruHandler.removeRoleUser(RemedyOp, childRoleGroupID);
				RemedyOp.FormDataDelete(childRoleForm, rs1.getString("C1"));
			}

			rs2 = idb.executeResultSet(stat, sqlString2);
			while(rs2.next())
			{
				RemedyOp.FormDataDelete(tplMatchConditionForm, rs2.getString("C1"));
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs1 != null)
					rs1.close();
				if (rs2 != null)
						rs2.close();
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
		RemedyOp.RemedyLogout();
		return true;
	}
	
	public boolean deleteMatchConditions(String roleID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String childRoleFormName = rdbo.GetRemedyTableName(childRoleForm);
		String tplMCFormName = rdbo.GetRemedyTableName(tplMatchConditionForm);
		
		String sqlString1 = "select C1, C700024001 from " + childRoleFormName + " where C700024003 = '" + roleID + "'";
		String sqlString2 = "select C1 from " + tplMCFormName + " where C700024103 = '" + roleID + "'";
		System.out.println(sqlString1);
		System.out.println(sqlString2);
		
		IDataBase idb1 = GetDataBase.createDataBase();
		Statement stat1 = idb1.GetStatement();
		IDataBase idb2 = GetDataBase.createDataBase();
		Statement stat2 = idb2.GetStatement();
		IDataBase idb3 = GetDataBase.createDataBase();
		Statement stat3 = idb3.GetStatement();

		ResultSet rs1 = idb1.executeResultSet(stat1, sqlString1);
		ResultSet rs2 = null;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		RoleUserHandler ruHandler = new RoleUserHandler();
		try
		{
			GroupUserInterfaceTmp guft = new GroupUserInterfaceTmp();
			
			while(rs1.next())
			{
				String tmpC1 = rs1.getString("C1");
				System.out.println(tmpC1);
				String groupID = rs1.getString("C700024001");
				guft.removeGroup(RemedyOp, groupID);
				String userForm = rdbo.GetRemedyTableName("User");
				String sqlStrUser = "update " + userForm + " t set t.c640000000 = replace(t.c640000000, '" + groupID + ";', '') where t.c640000000 like '%" + groupID + ";%'";
				System.out.println(sqlStrUser);
				
				idb3.executeNonQuery(stat3, sqlStrUser);
				RemedyOp.FormDataDelete(childRoleForm, tmpC1);
				
				ruHandler.removeRoleUser(RemedyOp, groupID);
			}
			
			rs2 = idb2.executeResultSet(stat2, sqlString2);
			while(rs2.next())
			{
				RemedyOp.FormDataDelete(tplMatchConditionForm, rs2.getString("C1"));
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs1 != null)
					rs1.close();
				if (rs2 != null)
						rs2.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stat1 != null)
					stat1.close();
				if (stat2 != null)
					stat2.close();
				if (stat3 != null)
					stat3.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			idb1.closeConn();
			idb2.closeConn();
			idb3.closeConn();
		}
		RemedyOp.RemedyLogout();
		return true;
	}
}
