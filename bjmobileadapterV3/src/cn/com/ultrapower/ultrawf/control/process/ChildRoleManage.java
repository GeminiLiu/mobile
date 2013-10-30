package cn.com.ultrapower.ultrawf.control.process;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.ChildRoleModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;

public class ChildRoleManage {
	public List<ChildRoleModel> getList(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("rolequery.ChildRole.BaseOn_ConnectionSelect",p_rDParameter) ;
      	Row 	m_Rs				= null;
      	List<ChildRoleModel>	m_List	= new ArrayList<ChildRoleModel>();
      	for (int i = 0;i<m_rowSet.length();i++) 
		{
      		m_Rs = m_rowSet.get(i);
      		ChildRoleModel m_Model 	= (ChildRoleModel) setModelValue(m_Rs);
      		m_List.add(m_Model);
		}    
		return m_List;
	}

	public ChildRoleModel getOneModel(RDParameter p_rDParameter)
	{
		p_rDParameter.addIndirectPar("is_notnull","1",2);
      	RowSet 	m_rowSet			= OpDB.getDataSetFromXML("rolequery.ChildRole.BaseOn_ConnectionSelect",p_rDParameter) ;
      	ChildRoleModel m_Model	= null;
      	if (m_rowSet.length()>0)
      	{
      		Row 	m_Rs			= null;
	  		m_Rs = m_rowSet.get(0);
	  		m_Model 	= (ChildRoleModel) setModelValue(m_Rs);
      	}
		return m_Model;
	}
	
	public ChildRoleModel getOneModel(String p_ChildRoleID)
	{
      	RDParameter m_rDParameter=new RDParameter();
      	m_rDParameter.addIndirectPar("childroleid",p_ChildRoleID,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	public void updateChildRole(ChildRoleModel crModel)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName("WF:App_WorkFlowChildRole");
		String sqlString = "update " + tablename + " set" +
				"  C700024008 = '" + crModel.getCBDBaseName() + "'" +
				", C700024007 = '" + crModel.getCBDBaseSchema() + "'" +
				", C700024001 = '" + crModel.getChildRoleID() + "'" +
				", C700024002 = '" + crModel.getChildRoleName() + "'" +
				", C700024005 = '" + crModel.getChlidMatchDegree() + "'" +
				", C700024003 = '" + crModel.getTopRoleID() + "'" +
				", C700024004 = '" + crModel.getTopRoleName() + "'" +
				", C700024010 = '" + crModel.getChildRoleManager() + "'" +
				", C700024011 = '" + crModel.getChildRoleManagerID() + "'" +
				" where C1 = '" + crModel.getRequestID() + "'";
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
	
	private Object setModelValue(Row p_Rs)
	{
		ChildRoleModel m_Model 	= new ChildRoleModel(); 

		m_Model.setCBDBaseName(p_Rs.getString("CBDBaseName"));
		m_Model.setCBDBaseSchema(p_Rs.getString("CBDBaseSchema"));
		m_Model.setChildRoleDesc(p_Rs.getString("ChildRoleDesc"));
		m_Model.setChildRoleID(p_Rs.getString("ChildRoleID"));
		m_Model.setChildRoleName(p_Rs.getString("ChildRoleName"));
		m_Model.setChlidMatchDegree(p_Rs.getInt("ChlidMatchDegree"));
		m_Model.setRequestID(p_Rs.getString("RequestID"));
		m_Model.setTopRoleID(p_Rs.getString("TopRoleID"));
		m_Model.setTopRoleName(p_Rs.getString("TopRoleName"));
		m_Model.setChildRoleManager(p_Rs.getString("ChildRoleManager"));
		m_Model.setChildRoleManagerID(p_Rs.getString("ChildRoleManagerID"));

		return m_Model;
	}
}
