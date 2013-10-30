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

public class TplDealLink {

	private int intQueryResultRows=0;
	//返回查询的总行数行数

	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}	

	public String getWhereSql(ParTplDealLinkModel parTplDealLinkModel)
	{
		StringBuffer sqlWhere= new StringBuffer();
		
		//private  String ProcessID;//
		//sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C1",parTplDealLinkModel.getProcessID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020044",parTplDealLinkModel.getLinkType()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020501",parTplDealLinkModel.getBaseTplID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020502",parTplDealLinkModel.getBaseTplSchema()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020503",parTplDealLinkModel.getStartPhase()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020504",parTplDealLinkModel.getEndPhase()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020505",parTplDealLinkModel.getDesc()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020506",parTplDealLinkModel.getFlag00IsAvail()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020507",parTplDealLinkModel.getFlag21Required()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020510",parTplDealLinkModel.getLinkNo()));
		return sqlWhere.toString();
	}
	
	public String getSelectSql(ParTplDealLinkModel parTplDealLinkModel)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblProcessName=Constants.TblTplDealLink;
		String strTblname=m_RemedyDBOp.GetRemedyTableName(tblProcessName);
		
		StringBuffer sqlBuffer= new StringBuffer();
		sqlBuffer.append("select C1 as LinkID,C700020044 as LinkType,C700020501 as BaseTplID");
		sqlBuffer.append(",C700020502 as BaseTplSchema,C700020503 as StartPhase,C700020504 as EndPhase");
		sqlBuffer.append(",C700020505 as DisDesc,C700020506 as Flag00IsAvail,C700020507 as Flag21Required");
		sqlBuffer.append(",C700020510 as LinkNo ");

		sqlBuffer.append(" from "+strTblname);
		sqlBuffer.append(" where 1=1 ");
		if(parTplDealLinkModel!=null)
			sqlBuffer.append(getWhereSql(parTplDealLinkModel));
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
				TplDealLinkModel tplDealLinkModels=new TplDealLinkModel();
				tplDealLinkModels.setLinkID(objRs.getString("LinkID"));
				tplDealLinkModels.setLinkType(objRs.getString("LinkType"));
				tplDealLinkModels.setBaseTplID(objRs.getString("BaseTplID"));
				tplDealLinkModels.setBaseTplSchema(objRs.getString("BaseTplSchema"));
				tplDealLinkModels.setStartPhase(objRs.getString("StartPhase"));
				tplDealLinkModels.setEndPhase(objRs.getString("EndPhase"));
				tplDealLinkModels.setDesc(objRs.getString("DisDesc"));
				tplDealLinkModels.setFlag00IsAvail(objRs.getInt("Flag00IsAvail"));
				tplDealLinkModels.setFlag21Required(objRs.getInt("Flag21Required"));
				tplDealLinkModels.setLinkNo(objRs.getString("LinkNo"));
				list.add(tplDealLinkModels);
	
			}//while (objRs.next())
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	
	public List getList(ParTplDealLinkModel parTplDealLinkModel,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=null;
		Statement stm=null;
		ResultSet m_ObjRs =null;
		
		String strSql;
		strSql=this.getSelectSql(parTplDealLinkModel);
		GetQueryResultRows(strSql);
		
		String strOrder="";
		if(parTplDealLinkModel!=null)
			strOrder=parTplDealLinkModel.getOrderbyFiledNameString();
		//if(strOrder.trim().equals(""))
		//	strOrder=" order by ProcessID";
		//排序
		strSql+=strOrder;		
		
		
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
			System.err.println("TplDealLink.GetList 方法"+ex.getMessage());
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
	private void GetQueryResultRows(String p_strSql)
	{
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(p_strSql);
		sqlString.append(" )");
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_BaseRs.next())
			{
				intQueryResultRows=m_BaseRs.getInt("rownums");
			}
		}catch(Exception ex)
		{
			System.err.println("TplDealLink.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try {
				if (m_BaseRs != null)
					m_BaseRs.close();
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
