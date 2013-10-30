package cn.com.ultrapower.ultrawf.models.process;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;

public class TplDealProcessRoleDefine
{
public static String BASESCHEMA = "WF:App_TplBase_PerformRole";
public static String BASESCHEMA_ACTIVE = "WF:App_Base_PerformRole";
	
	/**
	 * TplDealProcessRoleDefine的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplDealProcessRoleDefineModel tdprdModel)
	{
		List p_FieldInfoList = getfieldInfoList(tdprdModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA, p_FieldInfoList);
		return strReturnID;
	}
	
	public void Update(TplDealProcessRoleDefineModel tdprdModel)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		String sqlString = "update " + tablename + " set " +
				"C700022000 = '" + tdprdModel.getRoleOnlyID() + "' " +
				", C700022001 = '" + tdprdModel.getRoleName() + "' " +
				", C700022002 = '" + tdprdModel.getRoleProcessRoleType() + "' " +
				", C700022003 = '" + tdprdModel.getRoleDesc() + "' " +
				", C700022004 = '" + tdprdModel.getRoleBaseID() + "' " +
				", C700022005 = '" + tdprdModel.getRoleBaseSchema() + "' " +
				", C700022006 = '" + tdprdModel.getRoleProcessPhaseNo() + "' " +
				", C700022101 = '" + tdprdModel.getAssignee() + "' " +
				", C700022102 = '" + tdprdModel.getAssigneeID() + "' " +
				", C700022103 = '" + tdprdModel.getGroup() + "' " +
				", C700022104 = '" + tdprdModel.getGroupID() + "' " +
				", C700022201 = '" + tdprdModel.getRoleKey() + "' " +
				", C700022301 = '" + tdprdModel.getContextAssignee_FieldID() + "' " +
				", C700022302 = '" + tdprdModel.getContextAssigneeID_FieldID() + "' " +
				", C700022303 = '" + tdprdModel.getContextGroup_FieldID() + "' " +
				", C700022404 = '" + tdprdModel.getContextGroupID_FieldID() + "' " +
				", C700022501 = '" + tdprdModel.getTopRoleMatchConditionsDesc() + "' " +
				"where C1 = '" + tdprdModel.getRoleDefineID() + "'";
		System.out.println(sqlString);
		IDataBase idb = GetDataBase.createDataBase();;
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
	
	public void Delete(RemedyFormOp RemedyOp, String tdprdID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tdprdID);
	}
	
	private List getfieldInfoList(TplDealProcessRoleDefineModel tdprdModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("700022000", tdprdModel.getRoleOnlyID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022001", tdprdModel.getRoleName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022002", String.valueOf(tdprdModel.getRoleProcessRoleType()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700022003", tdprdModel.getRoleDesc(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022004", tdprdModel.getRoleBaseID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022005", tdprdModel.getRoleBaseSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022006", tdprdModel.getRoleProcessPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022101", tdprdModel.getAssignee(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022102", tdprdModel.getAssigneeID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022103", tdprdModel.getGroup(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022104", tdprdModel.getGroupID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022201", tdprdModel.getRoleKey(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022301", tdprdModel.getContextAssignee_FieldID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022302", tdprdModel.getContextAssigneeID_FieldID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022303", tdprdModel.getContextGroup_FieldID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022404", tdprdModel.getContextGroupID_FieldID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700022501", tdprdModel.getTopRoleMatchConditionsDesc(), 4));

		return remedyFieldList;
	}
	
	public TplDealProcessRoleDefineModel getDealProcessRoleDefine(String baseID, String roleID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		String sqlString = "select C1 as RoleDefineID"+
		", C700022000 as RoleOnlyID"+
		", C700022001 as RoleName"+
		", C700022002 as RoleProcessRoleType"+
		", C700022003 as RoleDesc"+
		", C700022004 as RoleBaseID"+
		", C700022005 as RoleBaseSchema"+
		", C700022006 as RoleProcessPhaseNo"+
		", C700022101 as Assignee"+
		", C700022102 as AssigneeID"+
		", C700022103 as RoleGroup"+
		", C700022104 as GroupID"+
		", C700022201 as RoleKey"+
		", C700022301 as ContextAssignee_FieldID"+
		", C700022302 as ContextAssigneeID_FieldID"+
		", C700022303 as ContextGroup_FieldID"+
		", C700022404 as ContextGroupID_FieldID"+
		", C700022501 as TopRoleMatchConditionsDesc"+
		" from " + tablename + " where C700022004 = '" + baseID + "' and C700022000 = '" + roleID + "'";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		TplDealProcessRoleDefineModel tdprdModel = new TplDealProcessRoleDefineModel();
		try
		{
			if(rs.next())
			{
				tdprdModel.setRoleDefineID(rs.getString("RoleDefineID"));
				tdprdModel.setRoleOnlyID(rs.getString("RoleOnlyID") == null ? "" : rs.getString("RoleOnlyID"));
				tdprdModel.setRoleName(rs.getString("RoleName"));
				tdprdModel.setRoleProcessRoleType((new Integer(rs.getString("RoleProcessRoleType"))).intValue());
				tdprdModel.setRoleDesc(rs.getString("RoleDesc") == null ? "" : rs.getString("RoleDesc"));
				tdprdModel.setRoleBaseID(rs.getString("RoleBaseID") == null ? "" : rs.getString("RoleBaseID"));
				tdprdModel.setRoleBaseSchema(rs.getString("RoleBaseSchema") == null ? "" : rs.getString("RoleBaseSchema"));
				tdprdModel.setRoleProcessPhaseNo(rs.getString("RoleProcessPhaseNo") == null ? "" : rs.getString("RoleProcessPhaseNo"));
				tdprdModel.setAssignee(rs.getString("Assignee") == null ? "" : rs.getString("Assignee"));
				tdprdModel.setAssigneeID(rs.getString("AssigneeID") == null ? "" : rs.getString("AssigneeID"));
				tdprdModel.setGroup(rs.getString("RoleGroup") == null ? "" : rs.getString("RoleGroup"));
				tdprdModel.setGroupID(rs.getString("GroupID") == null ? "" : rs.getString("GroupID"));
				tdprdModel.setRoleKey(rs.getString("RoleKey") == null ? "" : rs.getString("RoleKey"));
				tdprdModel.setContextAssignee_FieldID(rs.getString("ContextAssignee_FieldID") == null ? "" : rs.getString("ContextAssignee_FieldID"));
				tdprdModel.setContextAssigneeID_FieldID(rs.getString("ContextAssigneeID_FieldID") == null ? "" : rs.getString("ContextAssigneeID_FieldID"));
				tdprdModel.setContextGroup_FieldID(rs.getString("ContextGroup_FieldID") == null ? "" : rs.getString("ContextGroup_FieldID"));
				tdprdModel.setContextGroupID_FieldID(rs.getString("ContextGroupID_FieldID") == null ? "" : rs.getString("ContextGroupID_FieldID"));
				tdprdModel.setTopRoleMatchConditionsDesc(rs.getString("TopRoleMatchConditionsDesc") == null ? "" : rs.getString("TopRoleMatchConditionsDesc"));
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
		return tdprdModel;
	}
	
	public List getDealProcessRoleDefineList(String baseID, String schema, String flowID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700022004 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA_ACTIVE);
		tmpadd += " and C700022005 = '" + schema + "' ";
		if(flowID.equals(""))
		{
			tmpadd += " and C700022007 is null";
		}
		else
		{
			tmpadd += " and C700022007 = '" + flowID + "'";
		}
		return getList(tablename, tmpadd);
	}
	
	public List getTplDealProcessRoleDefineList(String baseID, String schema, String type)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700022004 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		return getList(tablename, tmpadd);
	}

	private List getList(String tablename, String tmpadd)
	{
		String sqlString = "select C1 as RoleDefineID"+
		", C700022000 as RoleOnlyID"+
		", C700022001 as RoleName"+
		", C700022002 as RoleProcessRoleType"+
		", C700022003 as RoleDesc"+
		", C700022004 as RoleBaseID"+
		", C700022005 as RoleBaseSchema"+
		", C700022006 as RoleProcessPhaseNo"+
		", C700022101 as Assignee"+
		", C700022102 as AssigneeID"+
		", C700022103 as RoleGroup"+
		", C700022104 as GroupID"+
		", C700022201 as RoleKey"+
		", C700022301 as ContextAssignee_FieldID"+
		", C700022302 as ContextAssigneeID_FieldID"+
		", C700022303 as ContextGroup_FieldID"+
		", C700022404 as ContextGroupID_FieldID"+
		", C700022501 as TopRoleMatchConditionsDesc"+
		" from " + tablename + " where " + tmpadd;
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List tdprdList = new ArrayList();
		try
		{
			while(rs.next())
			{
				TplDealProcessRoleDefineModel tdprdModel=new TplDealProcessRoleDefineModel();
				tdprdModel.setRoleDefineID(rs.getString("RoleDefineID"));
				tdprdModel.setRoleOnlyID(rs.getString("RoleOnlyID") == null ? "" : rs.getString("RoleOnlyID"));
				tdprdModel.setRoleName(rs.getString("RoleName"));
				tdprdModel.setRoleProcessRoleType((new Integer(rs.getString("RoleProcessRoleType"))).intValue());
				tdprdModel.setRoleDesc(rs.getString("RoleDesc") == null ? "" : rs.getString("RoleDesc"));
				tdprdModel.setRoleBaseID(rs.getString("RoleBaseID") == null ? "" : rs.getString("RoleBaseID"));
				tdprdModel.setRoleBaseSchema(rs.getString("RoleBaseSchema") == null ? "" : rs.getString("RoleBaseSchema"));
				tdprdModel.setRoleProcessPhaseNo(rs.getString("RoleProcessPhaseNo") == null ? "" : rs.getString("RoleProcessPhaseNo"));
				tdprdModel.setAssignee(rs.getString("Assignee") == null ? "" : rs.getString("Assignee"));
				tdprdModel.setAssigneeID(rs.getString("AssigneeID") == null ? "" : rs.getString("AssigneeID"));
				tdprdModel.setGroup(rs.getString("RoleGroup") == null ? "" : rs.getString("RoleGroup"));
				tdprdModel.setGroupID(rs.getString("GroupID") == null ? "" : rs.getString("GroupID"));
				tdprdModel.setRoleKey(rs.getString("RoleKey") == null ? "" : rs.getString("RoleKey"));
				tdprdModel.setContextAssignee_FieldID(rs.getString("ContextAssignee_FieldID") == null ? "" : rs.getString("ContextAssignee_FieldID"));
				tdprdModel.setContextAssigneeID_FieldID(rs.getString("ContextAssigneeID_FieldID") == null ? "" : rs.getString("ContextAssigneeID_FieldID"));
				tdprdModel.setContextGroup_FieldID(rs.getString("ContextGroup_FieldID") == null ? "" : rs.getString("ContextGroup_FieldID"));
				tdprdModel.setContextGroupID_FieldID(rs.getString("ContextGroupID_FieldID") == null ? "" : rs.getString("ContextGroupID_FieldID"));
				tdprdModel.setTopRoleMatchConditionsDesc(rs.getString("TopRoleMatchConditionsDesc") == null ? "" : rs.getString("TopRoleMatchConditionsDesc"));
				tdprdList.add(tdprdModel);
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
		return tdprdList;
	}
}
