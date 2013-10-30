package cn.com.ultrapower.ultrawf.control.design;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.design.*;
import cn.com.ultrapower.ultrawf.models.process.TplDealProcessRoleDefine;
import cn.com.ultrapower.ultrawf.models.process.TplDealProcessRoleDefineModel;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;

public class ProcessRoleManager
{
	public List<ProcessRoleModel> getProcessRoleList()
	{
		ProcessRoleHandler prHandler = new ProcessRoleHandler();
		return prHandler.getProcessRoleList();
	}
	
	public List<ProcessRoleModel> getProcessRoleList(String baseID)
	{
		ProcessRoleHandler prHandler = new ProcessRoleHandler();
		return prHandler.getProcessRoleList(baseID);
	}
	public ProcessRoleModel getProcessRole(String baseID, String roleID)
	{
		ProcessRoleHandler prHandler = new ProcessRoleHandler();
		return prHandler.getProcessRole(baseID, roleID);
	}
	public List<ChildRoleModel> getFormChildRoleList(String baseID)
	{
		ChildRoleHandler crHandler = new ChildRoleHandler();
		return crHandler.getFormChildRoleList(baseID);
	}
	public List<ChildRoleModel> getRoleChildRoleList(String baseID,String roleID)
	{
		ChildRoleHandler crHandler = new ChildRoleHandler();
		return crHandler.getRoleChildRoleList(baseID,roleID);
	}
	
	public void setChildRole(ProcessRoleModel prModel, boolean rebuild)
	{
		ProcessRoleHandler prHandler = new ProcessRoleHandler();
		prHandler.setCondition(prModel, rebuild);
	}

	public void updateProcessRole(TplDealProcessRoleDefineModel tdprdModel)
	{
		TplDealProcessRoleDefine tdprd = new TplDealProcessRoleDefine();
		tdprd.Update(tdprdModel);
	}
	public TplDealProcessRoleDefineModel getProcessRoleDefine(String baseID, String roleID)
	{
		TplDealProcessRoleDefine tdprd = new TplDealProcessRoleDefine();
		return tdprd.getDealProcessRoleDefine(baseID, roleID);
	}
	
	public RowSet getChildRoleRowSet(String baseID, String roleID)
	{
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("baseID", baseID, 4);
		rdp.addIndirectPar("roleID", roleID, 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL("rolequery.ChildRole.ChileRoleTree", rdp, "");
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		RowSet rowSet = table.executeQuery(sql, null, 0, 0, 0);
		return rowSet;
	}
	
	public String getChildRoleTree(RowSet rowSet)
	{
		StringBuffer xmlString = new StringBuffer("<?xml version=\"1.0\"?><tree id=\"0\" select=\"0\">");
		if(rowSet.length() > 0)
		{
			String pRoleName = rowSet.get(0).getString("ParentRoleName");
			xmlString.append("<item id=\"parentRole\" text=\"" + pRoleName + "\" open=\"1\">");
			xmlString.append("<userdata name=\"type\">#</userdata>");
			for(int i = 0; i < rowSet.length(); i++)
			{
				Row row = rowSet.get(i);
				xmlString.append("<item id=\"role_" + row.getString("RoleID") + "\" child=\"1\" text=\"" + row.getString("RoleName") + "\">");
				xmlString.append("<userdata name=\"type\">GroupID</userdata><userdata name=\"typeid\">" + row.getString("RoleID") + "</userdata>");
				xmlString.append("</item>");
			}
			xmlString.append("</item>");
		}
		xmlString.append("</tree>");
		return xmlString.toString();
	}
	
	public String getChildRoleuserTree(String groupID)
	{
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("groupID", groupID, 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL("rolequery.ChildRole.UserTree", rdp, "");
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		RowSet rowSet = table.executeQuery(sql, null, 0, 0, 0);
		StringBuffer xmlString = new StringBuffer("");
		if(rowSet.length() > 0)
		{
			for(int i = 0; i < rowSet.length(); i++)
			{
				Row row = rowSet.get(i);
				xmlString.append("<item id=\"roleuser_" + row.getString("LoginName") + "\" text=\"" + row.getString("FullName") + "\">");
				xmlString.append("<userdata name=\"type\">UserID</userdata><userdata name=\"typeid\">" + row.getString("LoginName") + "</userdata>");
				xmlString.append("</item>");
			}
		}
		return xmlString.toString();
	}
}

