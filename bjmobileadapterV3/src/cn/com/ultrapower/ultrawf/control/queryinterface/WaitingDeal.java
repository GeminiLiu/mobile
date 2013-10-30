package cn.com.ultrapower.ultrawf.control.queryinterface;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;

public class WaitingDeal
{
	public RowSet getWaitingDealProcess(String userName, String groupID, String roleID, int count)
	{
		String orderby = "BaseDealOutTime";
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("orderby", orderby + " asc", 7);
		rdp.addIndirectPar("AssgineeID", userName, 4);
		rdp.addIndirectPar("GroupID", groupID, 4);
		rdp.addIndirectPar("RoleID", roleID, 4);
		rdp.addIndirectPar("type", "user", 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL("query.MyWaitingDeal.user", rdp, "");
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		RowSet rowSet = table.executeQuery(sql, null, 1, count, 0);
		return rowSet;
	}
}
