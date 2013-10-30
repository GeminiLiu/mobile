package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class ConfigUserCommission {

	
	
	
	private  List ConvertRsToList(ResultSet p_ObjRs)  throws Exception
	{
		if(p_ObjRs==null) return null;
		
		List list = new ArrayList();
		try{
			while(p_ObjRs.next())
			{
				ConfigUserCommissionModel m_ConfigUserCommissionModel=new ConfigUserCommissionModel();
			
				m_ConfigUserCommissionModel.setConfigID(p_ObjRs.getString("ConfigID"));
				m_ConfigUserCommissionModel.setBaseCategoryName(p_ObjRs.getString("BaseCategoryName"));
				m_ConfigUserCommissionModel.setBaseCategorySchama(p_ObjRs.getString("BaseCategorySchama"));
				m_ConfigUserCommissionModel.setAssginee(p_ObjRs.getString("Assginee"));
				m_ConfigUserCommissionModel.setAssgineeID(p_ObjRs.getString("AssgineeID"));
				m_ConfigUserCommissionModel.setCommissioner(p_ObjRs.getString("Commissioner"));
				m_ConfigUserCommissionModel.setCommissionerID(p_ObjRs.getString("CommissionerID"));
				m_ConfigUserCommissionModel.setCommissionBeginTime(p_ObjRs.getString("CommissionBeginTime"));
				m_ConfigUserCommissionModel.setCommissionEndTime(p_ObjRs.getString("CommissionEndTime"));
				list.add(m_ConfigUserCommissionModel);
			}
		}catch(Exception ex)
		{
			System.err.println("ConfigUserCloseBaseGroup.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
			throw ex;
		}
		finally
		{
			p_ObjRs.close();
		}
		return list;
	}
	
	private String getSelectSql(ParConfigUserCommissionModel p_ConfigUserCommissionModel)
	{
		StringBuffer stringBuffer=new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblUserCommission);			
		stringBuffer.append(" select ");
		stringBuffer.append("C1 as ConfigID,C650000001 as BaseCategoryName,C650000002 as BaseCategorySchama");
		stringBuffer.append(",C650000003 as Assginee,C650000004 as AssgineeID,C650000005 as Commissioner");
		stringBuffer.append(",C650000006 as CommissionerID,C650000007 as CommissionBeginTime,C650000008 as CommissionEndTime ");
		stringBuffer.append(" from "+strTblName);
		stringBuffer.append(" where 1=1 ");
		if(p_ConfigUserCommissionModel!=null)
			stringBuffer.append(p_ConfigUserCommissionModel.getWhereSql(""));
		return stringBuffer.toString();
	}
	
	
	public List getList(ParConfigUserCommissionModel p_ConfigUserCommissionModel,int p_PageNumber,int p_StepRow )
	{
		
		List m_Relist=null;
		
		//StringBuffer sqlString = new StringBuffer();
		String strSql;
		strSql=this.getSelectSql(p_ConfigUserCommissionModel);
		
		if(strSql.trim().equals(""))
		{	
			return m_Relist;
		}
		PageControl m_PageControl=new PageControl();
		intQueryResultRows=m_PageControl.GetQueryResultRows(strSql);
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		//strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		Statement stm=null;
		ResultSet m_Resultset =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_Resultset = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_Resultset);	
		}catch(Exception ex)
		{
			System.err.println("ConfigUserCommission.GetList 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try{
				if(m_Resultset!=null)
					m_Resultset.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
			try{
				if(stm!=null)
					stm.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
		return m_Relist;
		
	}		
	//返回查询的总行数行数
	private int intQueryResultRows=0;
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
