package cn.com.ultrapower.ultrawf.models.design;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class ProcessRoleHandler
{
	private static final String tplBaseForm = "WF:App_TplBase";
	private static final String tplDealProcessForm = "WF:App_TplDealProcess";
	private static final String tplRoleForm = "WF:App_TplBase_PerformRole";
	private static final String tplIncidentManageForm = "WF:Config_EL_TTM_TTH_IncidentManage";
	
	public List<ProcessRoleModel> getProcessRoleList()
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tplBaseFormName = rdbo.GetRemedyTableName(tplBaseForm);
		String tplDealProcessFormName = rdbo.GetRemedyTableName(tplDealProcessForm);
		String tplRoleFormName = rdbo.GetRemedyTableName(tplRoleForm);
		
		String sqlString = "select " +
				"b.C1 " +
				", r.C1 as RoleC1 " +
				", b.C650000003 as BaseSchema " +
				", b.C650000015 as BaseName " +
				", dp.C1 as ProcessID " +
				", dp.C700020018 as ProcessName " +
				", r.C700022002 as RoleProcessRoleType " +
				", r.C700022101 as Assignee " +
				", r.C700022102 as AssigneeID " +
				", r.C700022103 as RoleGroup " +
				", r.C700022104 as GroupID " +
				", r.C700022201 as RoleKey " +
				", r.C700022301 as ContextAssignee_FieldID " +
				", r.C700022302 as ContextAssigneeID_FieldID " +
				", r.C700022303 as ContextGroup_FieldID " +
				", r.C700022404 as ContextGroupID_FieldID " +
				", r.C700022000 as RoleID " +
				", r.C700022001 as RoleName " +
				", r.C700022501 as Condition " +
				"from " + tplBaseFormName + " b, " + tplDealProcessFormName + " dp, " + tplRoleFormName + " r " +
				"where b.C1 = dp.C700020001 " + //BaseID
						"and b.C650000003 = dp.C700020002 " + //Schema
						"and b.C1 = r.C700022004 " +
						"and r.C700022006 = dp.C700020003"; //BaseID
		System.out.println(sqlString);
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		List<ProcessRoleModel> prList = new ArrayList<ProcessRoleModel>();
		try
		{
			while(rs.next())
			{
				ProcessRoleModel prModel = new ProcessRoleModel();
				prModel.setBaseID(rs.getString("C1"));
				prModel.setRoleC1(rs.getString("RoleC1"));
				prModel.setBaseName(rs.getString("BaseName"));
				prModel.setBaseSchema(rs.getString("BaseSchema"));
				prModel.setProcessID(rs.getString("ProcessID"));
				prModel.setProcessName(rs.getString("ProcessName"));
				prModel.setRoleProcessRoleType((new Integer(rs.getString("RoleProcessRoleType"))).intValue());
				prModel.setAssignee(rs.getString("Assignee") == null ? "" : rs.getString("Assignee"));
				prModel.setAssigneeID(rs.getString("AssigneeID") == null ? "" : rs.getString("AssigneeID"));
				prModel.setGroup(rs.getString("RoleGroup") == null ? "" : rs.getString("RoleGroup"));
				prModel.setGroupID(rs.getString("GroupID") == null ? "" : rs.getString("GroupID"));
				prModel.setRoleKey(rs.getString("RoleKey") == null ? "" : rs.getString("RoleKey"));
				prModel.setContextAssignee_FieldID(rs.getString("ContextAssignee_FieldID") == null ? "" : rs.getString("ContextAssignee_FieldID"));
				prModel.setContextAssigneeID_FieldID(rs.getString("ContextAssigneeID_FieldID") == null ? "" : rs.getString("ContextAssigneeID_FieldID"));
				prModel.setContextGroup_FieldID(rs.getString("ContextGroup_FieldID") == null ? "" : rs.getString("ContextGroup_FieldID"));
				prModel.setContextGroupID_FieldID(rs.getString("ContextGroupID_FieldID") == null ? "" : rs.getString("ContextGroupID_FieldID"));
				prModel.setRoleID(rs.getString("RoleID"));
				prModel.setRoleName(rs.getString("RoleName"));
				prModel.setRoleCondition(rs.getString("Condition"));
				prList.add(prModel);
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
		return prList;
	}
	public List<ProcessRoleModel> getProcessRoleList(String baseID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tplBaseFormName = rdbo.GetRemedyTableName(tplBaseForm);
		String tplDealProcessFormName = rdbo.GetRemedyTableName(tplDealProcessForm);
		String tplRoleFormName = rdbo.GetRemedyTableName(tplRoleForm);
		String tplIncidentManageFormName = rdbo.GetRemedyTableName(tplIncidentManageForm);
		
		String sqlString = "select " +
				"r.C1 as RoleC1 " +
				", b.C650000003 as BaseSchema " +
				", b.C650000015 as BaseName " +
				", dp.C1 as ProcessID " +
				", dp.C700020018 as ProcessName " +
				", r.C700022002 as RoleProcessRoleType " +
				", r.C700022101 as Assignee " +
				", r.C700022102 as AssigneeID " +
				", r.C700022103 as RoleGroup " +
				", r.C700022104 as GroupID " +
				", r.C700022201 as RoleKey " +
				", r.C700022301 as ContextAssignee_FieldID " +
				", r.C700022302 as ContextAssigneeID_FieldID " +
				", r.C700022303 as ContextGroup_FieldID " +
				", r.C700022404 as ContextGroupID_FieldID " +
				", r.C700022000 as RoleID " +
				", r.C700022001 as RoleName " +
				", r.C700022501 as Condition " +
				"from " + tplBaseFormName + " b, " + tplDealProcessFormName + " dp, " + tplRoleFormName + " r " +
				"where b.C1 = '" + baseID + "' " +
						"and b.C1 = dp.C700020001 " + //BaseID
						"and b.C650000003 = dp.C700020002 " + //Schema
						"and b.C1 = r.C700022004 " +
						"and r.C700022006 = dp.C700020003" + //BaseID
				" union all select " +
				"r.C1 as RoleC1 " +
				", b.C650000003 as BaseSchema " +
				", b.C650000015 as BaseName " +
				", '' as ProcessID " +
				", '' as ProcessName " +
				", r.C700022002 as RoleProcessRoleType " +
				", r.C700022101 as Assignee " +
				", r.C700022102 as AssigneeID " +
				", r.C800020001 as RoleGroup " +
				", r.C800020002 as GroupID " +
				", r.C700022201 as RoleKey " +
				", r.C700022301 as ContextAssignee_FieldID " +
				", r.C700022302 as ContextAssigneeID_FieldID " +
				", r.C700022303 as ContextGroup_FieldID " +
				", r.C700022404 as ContextGroupID_FieldID " +
				", r.C700022000 as RoleID " +
				", r.C700022001 as RoleName " +
				", r.C700022501 as Condition " +
				"from " + tplBaseFormName + " b, " + tplIncidentManageFormName + " r " +
				"where b.C1 = '" + baseID + "' " +
						"and b.C1 = r.C700022004 " + //BaseID
						"and b.C650000003 = r.C700022005"; //Schema
		System.out.println(sqlString);
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		List<ProcessRoleModel> prList = new ArrayList<ProcessRoleModel>();
		try
		{
			while(rs.next())
			{
				ProcessRoleModel prModel = new ProcessRoleModel();
				prModel.setBaseID(baseID);
				prModel.setRoleC1(rs.getString("RoleC1"));
				prModel.setBaseName(rs.getString("BaseName"));
				prModel.setBaseSchema(rs.getString("BaseSchema"));
				prModel.setProcessID(rs.getString("ProcessID"));
				prModel.setProcessName(rs.getString("ProcessName"));
				prModel.setRoleProcessRoleType((new Integer(rs.getString("RoleProcessRoleType"))).intValue());
				prModel.setAssignee(rs.getString("Assignee") == null ? "" : rs.getString("Assignee"));
				prModel.setAssigneeID(rs.getString("AssigneeID") == null ? "" : rs.getString("AssigneeID"));
				prModel.setGroup(rs.getString("RoleGroup") == null ? "" : rs.getString("RoleGroup"));
				prModel.setGroupID(rs.getString("GroupID") == null ? "" : rs.getString("GroupID"));
				prModel.setRoleKey(rs.getString("RoleKey") == null ? "" : rs.getString("RoleKey"));
				prModel.setContextAssignee_FieldID(rs.getString("ContextAssignee_FieldID") == null ? "" : rs.getString("ContextAssignee_FieldID"));
				prModel.setContextAssigneeID_FieldID(rs.getString("ContextAssigneeID_FieldID") == null ? "" : rs.getString("ContextAssigneeID_FieldID"));
				prModel.setContextGroup_FieldID(rs.getString("ContextGroup_FieldID") == null ? "" : rs.getString("ContextGroup_FieldID"));
				prModel.setContextGroupID_FieldID(rs.getString("ContextGroupID_FieldID") == null ? "" : rs.getString("ContextGroupID_FieldID"));
				prModel.setRoleID(rs.getString("RoleID"));
				prModel.setRoleName(rs.getString("RoleName"));
				prModel.setRoleCondition(rs.getString("Condition"));
				prList.add(prModel);
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
		return prList;
	}
	
	public ProcessRoleModel getProcessRole(String baseID, String roleID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tplBaseFormName = rdbo.GetRemedyTableName(tplBaseForm);
		String tplDealProcessFormName = rdbo.GetRemedyTableName(tplDealProcessForm);
		String tplRoleFormName = rdbo.GetRemedyTableName(tplRoleForm);
		
		String sqlString = "select " +
				"r.C1 as RoleC1 " +
				", b.C650000003 as BaseSchema " +
				", b.C650000015 as BaseName " +
				", dp.C1 as ProcessID " +
				", dp.C700020018 as ProcessName " +
				", r.C700022002 as RoleProcessRoleType " +
				", r.C700022101 as Assignee " +
				", r.C700022102 as AssigneeID " +
				", r.C700022103 as RoleGroup " +
				", r.C700022104 as GroupID " +
				", r.C700022201 as RoleKey " +
				", r.C700022301 as ContextAssignee_FieldID " +
				", r.C700022302 as ContextAssigneeID_FieldID " +
				", r.C700022303 as ContextGroup_FieldID " +
				", r.C700022404 as ContextGroupID_FieldID " +
				", r.C700022000 as RoleID " +
				", r.C700022001 as RoleName " +
				", r.C700022501 as Condition " +
				"from " + tplBaseFormName + " b, " + tplDealProcessFormName + " dp, " + tplRoleFormName + " r " +
				"where b.C1 = '" + baseID + "' " +
						"and b.C1 = dp.C700020001 " + //BaseID
						"and b.C650000003 = dp.C700020002 " + //Schema
						"and b.C1 = r.C700022004 " +
						"and r.C700022006 = dp.C700020003 " +
						"and r.C700022000 = '" + roleID + "'"; //BaseID
		System.out.println(sqlString);
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		ProcessRoleModel prModel = new ProcessRoleModel();
		try
		{
			if(rs.next())
			{
				prModel.setBaseID(baseID);
				prModel.setRoleC1(rs.getString("RoleC1"));
				prModel.setBaseName(rs.getString("BaseName"));
				prModel.setBaseSchema(rs.getString("BaseSchema"));
				prModel.setProcessID(rs.getString("ProcessID"));
				prModel.setProcessName(rs.getString("ProcessName"));
				prModel.setRoleProcessRoleType((new Integer(rs.getString("RoleProcessRoleType"))).intValue());
				prModel.setAssignee(rs.getString("Assignee") == null ? "" : rs.getString("Assignee"));
				prModel.setAssigneeID(rs.getString("AssigneeID") == null ? "" : rs.getString("AssigneeID"));
				prModel.setGroup(rs.getString("RoleGroup") == null ? "" : rs.getString("RoleGroup"));
				prModel.setGroupID(rs.getString("GroupID") == null ? "" : rs.getString("GroupID"));
				prModel.setRoleKey(rs.getString("RoleKey") == null ? "" : rs.getString("RoleKey"));
				prModel.setContextAssignee_FieldID(rs.getString("ContextAssignee_FieldID") == null ? "" : rs.getString("ContextAssignee_FieldID"));
				prModel.setContextAssigneeID_FieldID(rs.getString("ContextAssigneeID_FieldID") == null ? "" : rs.getString("ContextAssigneeID_FieldID"));
				prModel.setContextGroup_FieldID(rs.getString("ContextGroup_FieldID") == null ? "" : rs.getString("ContextGroup_FieldID"));
				prModel.setContextGroupID_FieldID(rs.getString("ContextGroupID_FieldID") == null ? "" : rs.getString("ContextGroupID_FieldID"));
				prModel.setRoleID(rs.getString("RoleID"));
				prModel.setRoleName(rs.getString("RoleName"));
				prModel.setRoleCondition(rs.getString("Condition"));
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
		return prModel;
	}
	
	public void setCondition(ProcessRoleModel prModel, boolean isRebuild)
	{
		List<ChildRoleModel> crList = new ArrayList<ChildRoleModel>();
		if(!prModel.getRoleCondition().equals(""))
		{
			List<List<Object[]>> condList = new ArrayList<List<Object[]>>();
			String[] conds = prModel.getRoleCondition().split("#");
			for(int i = 0; i < conds.length; i++)
			{
				List<Object[]> dList = new ArrayList<Object[]>();
				String[] condfield = conds[i].split("&");
				String fieldID = condfield[0];
				String fieldText = condfield[1];
				String fieldName = condfield[2];
				
				DimensionHandler dHandler = new DimensionHandler();
				DimensionModel dModel = dHandler.getDimension(fieldID, prModel.getBaseSchema());
				
				List<String> dvList = getDimensionValues(dModel);
				for(Iterator<String> it = dvList.iterator(); it.hasNext();)
				{
					Object[] d = new Object[2];
					d[0] = dModel;
					d[1] = it.next();
					dList.add(d);
				}
				condList.add(dList);
			}
			for(Iterator<List<Object[]>> it = condList.iterator(); it.hasNext();)
			{
				List<Object[]> sList = it.next();
				List<ChildRoleModel> tmpList = new ArrayList<ChildRoleModel>();
				List<ChildRoleModel> cList = new ArrayList<ChildRoleModel>();
				for(Iterator<Object[]> it1 = sList.iterator(); it1.hasNext();)
				{
					Object[] s = it1.next();
					ChildRoleModel crModel = new ChildRoleModel();
					DimensionModel dm = new DimensionModel();
					dm.setDFieldID(((DimensionModel)s[0]).getDFieldID());
					dm.setDFieldName(((DimensionModel)s[0]).getDFieldName());
					dm.setDFieldText(((DimensionModel)s[0]).getDFieldText());
					dm.setFieldFormName(((DimensionModel)s[0]).getFieldFormName());
					dm.setFieldFormText(((DimensionModel)s[0]).getFieldFormText());
					dm.setFieldID(((DimensionModel)s[0]).getFieldID());
					dm.setFieldName(((DimensionModel)s[0]).getFieldName());
					dm.setFieldText(((DimensionModel)s[0]).getFieldText());
					dm.setFieldValue(s[1].toString());
					dm.setRequestID(((DimensionModel)s[0]).getRequestID());
					
					dm.setFieldValue(s[1].toString());
					crModel.setParentRoleID(prModel.getRoleID());
					crModel.setParentRoleName(prModel.getRoleName());
					crModel.setBaseID(prModel.getBaseID());
					crModel.setBaseSchema(prModel.getBaseSchema());
					crModel.setBaseName(prModel.getBaseName());
					crModel.getDimensionList().add(dm);
					crModel.setRoleName("[" + s[1].toString() + "]");
					crModel.setCondition(dm.getDFieldText() + "='" + s[1].toString() + "';");
					cList.add(crModel);
				}
				
				if(crList.size() > 0)
				{
					for(Iterator<ChildRoleModel> it1 = crList.iterator(); it1.hasNext();)
					{
						ChildRoleModel tmpModel = it1.next();
						for(Iterator<ChildRoleModel> it2 = cList.iterator(); it2.hasNext();)
						{
							ChildRoleModel nModel = it2.next();
							ChildRoleModel crModel = new ChildRoleModel();
							crModel.setParentRoleID(prModel.getRoleID());
							crModel.setParentRoleName(prModel.getRoleName());
							crModel.setBaseID(prModel.getBaseID());
							crModel.setBaseSchema(prModel.getBaseSchema());
							crModel.setBaseName(prModel.getBaseName());
							crModel.getDimensionList().addAll(tmpModel.getDimensionList());
							crModel.getDimensionList().addAll(nModel.getDimensionList());
							crModel.setRoleName(tmpModel.getRoleName() + nModel.getRoleName());
							crModel.setCondition(tmpModel.getCondition() + nModel.getCondition());
							tmpList.add(crModel);
						}
					}
					crList = tmpList;
				}
				else
				{
					crList = cList;
				}
			}
		}
		ChildRoleHandler crHandler = new ChildRoleHandler();
		crHandler.setMatchConditions(crList, prModel.getBaseID(), prModel.getRoleID(), isRebuild);
	}
	
	public List<String> getDimensionValues(DimensionModel dModel)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String formName = rdbo.GetRemedyTableName(dModel.getFieldFormName());
		String sqlString = "select " + dModel.getFieldID() + " as fvalue from " + formName;
		System.out.println(sqlString);
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		List<String> fList = new ArrayList<String>();
		try
		{
			while(rs.next())
			{
				String fieldValue = rs.getString("fvalue");
				fList.add(fieldValue);
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
		return fList;
	}
}
