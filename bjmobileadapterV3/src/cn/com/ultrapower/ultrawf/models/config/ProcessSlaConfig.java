package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class ProcessSlaConfig {

	private int intQueryResultRows=0;
	//返回查询的总行数行数

	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}	

	public String getWhereSql(String p_TblAliasName,ParProcessSlaConfigModel parObj)
	{
		if(parObj==null)
			return "";
		StringBuffer sqlWhere= new StringBuffer();
		sqlWhere.append(" where 1=1 ");

		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C1",parObj.getRequsetID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800001",parObj.getFix_BaseName()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800002",parObj.getFix_BaseSchema()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800003",parObj.getFix_From_Group()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800004",parObj.getFix_From_GroupID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800005",parObj.getFix_From_User()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800006",parObj.getFix_From_UserID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800007",parObj.getFix_State()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800008",parObj.getFix_Desc()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800009",parObj.getFix_To_Group()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800010",parObj.getFix_To_GroupID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800011",parObj.getFix_To_User()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800012",parObj.getFix_To_UserID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C802800013",parObj.getFix_BaseOverTime()));

		return sqlWhere.toString();
	}
	
	public String getSelectSql()
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblProcessName="UltraProcess:Config_Sys_Sla_Config";//Constants.TblTplDealLink;
		String strTblname=m_RemedyDBOp.GetRemedyTableName(tblProcessName);
		
		StringBuffer sqlBuffer= new StringBuffer();
		sqlBuffer.append("select C1 as RequsetID,C802800001 as Fix_BaseName,C802800002 as Fix_BaseSchema,C802800003 as Fix_From_Group");
		sqlBuffer.append(",C802800004 as Fix_From_GroupID,C802800005 as Fix_From_User,C802800006 as Fix_From_UserID,C802800007 as Fix_State");
		sqlBuffer.append(",C802800008 as Fix_Desc,C802800009 as Fix_To_Group,C802800010 as Fix_To_GroupID,C802800011 as Fix_To_User");
		sqlBuffer.append(",C802800012 as Fix_To_UserID,C802800013 as Fix_BaseOverTime ");
		
		sqlBuffer.append(" from "+strTblname);

		return sqlBuffer.toString();
		
	}
	
	private  List ConvertRsToList( ResultSet objRs) 
	{
		
		if(objRs==null)
			return null;
		List list=new ArrayList();
		try{
			while (objRs.next())
			{
				ProcessSlaConfigModel processSlaModel=new ProcessSlaConfigModel();
				processSlaModel.setRequsetID(objRs.getString("RequsetID"));
				processSlaModel.setFix_BaseName(objRs.getString("Fix_BaseName"));
				processSlaModel.setFix_BaseSchema(objRs.getString("Fix_BaseSchema"));
				processSlaModel.setFix_From_Group(objRs.getString("Fix_From_Group"));
				processSlaModel.setFix_From_GroupID(objRs.getString("Fix_From_GroupID"));
				processSlaModel.setFix_From_User(objRs.getString("Fix_From_User"));
				processSlaModel.setFix_From_UserID(objRs.getString("Fix_From_UserID"));
				processSlaModel.setFix_State(objRs.getLong("Fix_State"));
				processSlaModel.setFix_Desc(objRs.getString("Fix_Desc"));
				processSlaModel.setFix_To_Group(objRs.getString("Fix_To_Group"));
				processSlaModel.setFix_To_GroupID(objRs.getString("Fix_To_GroupID"));
				processSlaModel.setFix_To_User(objRs.getString("Fix_To_User"));
				processSlaModel.setFix_To_UserID(objRs.getString("Fix_To_UserID"));
				processSlaModel.setFix_BaseOverTime(objRs.getLong("Fix_BaseOverTime"));
				list.add(processSlaModel);
	
			}//while (objRs.next())
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	
	public List getList(ParProcessSlaConfigModel parProcessSlaConfigModel,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=null;
		Statement stm=null;
		ResultSet m_ObjRs =null;
		
		String strSql;
		strSql=this.getSelectSql();
		String sqlWhere=this.getWhereSql("",parProcessSlaConfigModel);
		strSql+=sqlWhere;
		GetQueryResultRows(sqlWhere);
		
		//String strOrder="";
		//if(parProcessSlaConfigModel!=null)
		//	strOrder=parProcessSlaConfigModel.getOrderbyFiledNameString();
		//if(strOrder.trim().equals(""))
		//	strOrder=" order by ProcessID";
		//排序
		//strSql+=strOrder;		
		

		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		try{
			stm=m_dbConsole.GetStatement();
			m_ObjRs = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_ObjRs);	
		}catch(Exception ex)
		{
			System.err.println("ProcessSlaConfig.GetList 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try {
				if (m_ObjRs != null)
					m_ObjRs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
		return m_Relist;
	}
	
	/**
	 * 返回本次查询的总行数，用于计算分页时的总页数

	 * @param p_strSql
	 */
	private void GetQueryResultRows(String p_strWhereSql)
	{
		if(p_strWhereSql==null)
			p_strWhereSql="";
		StringBuffer sqlString = new StringBuffer();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblProcessName="UltraProcess:Config_Sys_Sla_Config";//Constants.TblTplDealLink;
		String strTblname=m_RemedyDBOp.GetRemedyTableName(tblProcessName);

		sqlString.append(" select count(*) rownums from ");
		sqlString.append(strTblname);
		
		sqlString.append(p_strWhereSql);
		//sqlString.append(" ");

		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet m_objRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_objRs.next())
			{
				intQueryResultRows=m_objRs.getInt("rownums");
			}
		}catch(Exception ex)
		{
			System.err.println("ProcessSlaConfig.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try {
				if (m_objRs != null)
					m_objRs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
	}	
}
