package cn.com.ultrapower.ultrawf.models.config;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;


public class BaseOwnFieldInfo {

	private int intQueryResultRows=0;
	//返回查询的总行数行数
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	
	/**
	 * 
	 * @param objRs
	 * @return
	 */
	public List GetRsConvertList(ResultSet objRs)
	{	
		List list = new ArrayList();
		try{
			while(objRs.next())
			{
				BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel=new BaseOwnFieldInfoModel();
				m_BaseOwnFieldInfoModel.SetBaseOwnFieldInfoID(objRs.getString("BaseOwnFieldInfoID"));
				m_BaseOwnFieldInfoModel.SetBaseCategoryName(objRs.getString("BaseCategoryName"));
				m_BaseOwnFieldInfoModel.SetBaseCategorySchama(objRs.getString("BaseCategorySchama"));
				m_BaseOwnFieldInfoModel.SetBase_field_ID(objRs.getString("Base_field_ID"));
				m_BaseOwnFieldInfoModel.SetBase_field_DBName(objRs.getString("Base_field_DBName"));
				m_BaseOwnFieldInfoModel.SetBase_field_ShowName(objRs.getString("Base_field_ShowName"));
				m_BaseOwnFieldInfoModel.SetBase_field_Type(objRs.getString("Base_field_Type"));
				m_BaseOwnFieldInfoModel.SetBase_field_TypeValue(objRs.getString("Base_field_TypeValue"));
				m_BaseOwnFieldInfoModel.SetBaseFree_field_ShowStep(objRs.getString("BaseFree_field_ShowStep"));
				m_BaseOwnFieldInfoModel.SetBaseFree_field_EditStep(objRs.getString("BaseFree_field_EditStep"));
				m_BaseOwnFieldInfoModel.SetBaseFix_field_ShowStep(objRs.getString("BaseFix_field_ShowStep"));
				m_BaseOwnFieldInfoModel.SetBaseFix_field_EditStep(objRs.getString("BaseFix_field_EditStep"));
				m_BaseOwnFieldInfoModel.SetBaseOwnFieldInfoDesc(objRs.getString("BaseOwnFieldInfoDesc"));
				m_BaseOwnFieldInfoModel.SetPrintOneLine(objRs.getInt("PrintOneLine"));
				m_BaseOwnFieldInfoModel.SetPrintOrder(objRs.getInt("PrintOrder"));
				
				m_BaseOwnFieldInfoModel.setEntryMode(objRs.getInt("EntryMode"));
				m_BaseOwnFieldInfoModel.setVarcharFieldeIsExceed(objRs.getInt("VarcharFieldeIsExceed"));
				m_BaseOwnFieldInfoModel.setLogIsWrite(objRs.getInt("LogIsWrite"));
				
				list.add(m_BaseOwnFieldInfoModel);
			}
		}catch(Exception ex)
		{
			System.err.println("BaseOwnFieldInfo.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		finally
		{
			try{
				if(objRs!=null)
					objRs.close();
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
		return list;
	}
	/**
	 * 
	 * @return
	 */
	public String GetSelectSql()
	{
		StringBuffer strSelect = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseOwnFieldInfo);	
		strSelect.append(" select ");
		strSelect.append("C1 as BaseOwnFieldInfoID,C650000001 as BaseCategoryName");
		strSelect.append(",C650000002 as BaseCategorySchama,C650000003 as Base_field_ID");
		strSelect.append(",C650000004 as Base_field_DBName,C650000005 as Base_field_ShowName");
		strSelect.append(",C650000006 as Base_field_Type,C650000007 as Base_field_TypeValue");
		strSelect.append(",C650000008 as BaseFree_field_ShowStep,C650000009 as BaseFree_field_EditStep");
		strSelect.append(",C650000010 as BaseFix_field_ShowStep,C650000011 as BaseFix_field_EditStep");
		strSelect.append(",C650000100 as BaseOwnFieldInfoDesc ");
		strSelect.append(",C650000012 as PrintOneLine ");//打印是否占一行		strSelect.append(",C650000013 as PrintOrder ");//打印的顺序 如果顺序中间有间隔，表示该字段的后面需要空一个间隔。		strSelect.append(",C650000014 as EntryMode ");
		strSelect.append(",C650000015 as VarcharFieldeIsExceed ");
		strSelect.append(",650000016 as  LogIsWrite");
		
		strSelect.append(" from "+strTblName+" ");
		return strSelect.toString();
		
	}
	
	/**
	 * 根据打印查询工单特有信息
	 * @param p_Baseschema
	 * @return
	 */
	public List GetListForPrint(String p_Baseschema)
	{
		ParBaseOwnFieldInfoModel m_ParBaseOwnFieldInfoModel=new ParBaseOwnFieldInfoModel();
		m_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_Baseschema);
		//排序为0的字段不打印出来(查询)
		m_ParBaseOwnFieldInfoModel.setPrintOrder("NOT:0");
//		Entry Mode 0：Display Only 	1：Optional	2：Required
		m_ParBaseOwnFieldInfoModel.setEntryMode("NOT:0");
		List m_list = new ArrayList();
		StringBuffer strSelect = new StringBuffer();
		
		strSelect.append(GetSelectSql());
		
		strSelect.append(" where 1=1 ");
		strSelect.append(m_ParBaseOwnFieldInfoModel.GetWhereSql());
		
		//PrintOrder	650000014	打印的顺序 如果顺序中间有间隔，表示该字段的后面需要空一个间隔。		strSelect.append(" order by C650000013");
		String strSql=strSelect.toString();
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet objRs=null;
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSql);
			m_list=GetRsConvertList(objRs);
		}catch(Exception ex)
		{
			System.err.println("BaseOwnFieldInfo.GetList 方法："+ex.getMessage());
			ex.printStackTrace();				
		}	
		finally
		{
			try{
				if(objRs!=null)
					objRs.close();
			}catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}

			try{
				if(stm!=null)
					stm.close();
			}catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return m_list;	
	}
	
	/**
	 * 
	 * @param p_ParBaseOwnFieldInfoModel
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParBaseOwnFieldInfoModel p_ParBaseOwnFieldInfoModel,int p_PageNumber,int p_StepRow )
	{
		
		List m_list = new ArrayList();
		StringBuffer strSelect = new StringBuffer();
		strSelect.append(GetSelectSql());
		strSelect.append(" where 1=1 ");
		if(p_ParBaseOwnFieldInfoModel==null)
		{	
			p_ParBaseOwnFieldInfoModel=new ParBaseOwnFieldInfoModel();
		}
		//Entry Mode 0：Display Only 	1：Optional	2：Required
		p_ParBaseOwnFieldInfoModel.setEntryMode("NOT:0");
		strSelect.append(p_ParBaseOwnFieldInfoModel.GetWhereSql());
		String orderSql=p_ParBaseOwnFieldInfoModel.getOrderbyFiledNameString();
		if(orderSql.equals(""))
			strSelect.append(" order by C650000013");
		else
			strSelect.append(orderSql);
		String strSql=strSelect.toString();
		GetQueryResultRows(strSql);
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		Statement stm=null;
		ResultSet objRs=null;
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());
			m_list=GetRsConvertList(objRs);
		}catch(Exception ex)
		{
			System.err.println("BaseOwnFieldInfo.GetList 方法："+ex.getMessage());
			ex.printStackTrace();				
		}	
		finally
		{
			try{
				if(objRs!=null)
					objRs.close();
			}catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}

			try{
				if(stm!=null)
					stm.close();
			}catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return m_list;	
	}
	/**
	 *返回查询的记录总数量(用于计算分页时的页数)
	 * @param p_strSql
	 */
	private void GetQueryResultRows(String p_strSql)
	{
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(p_strSql);
		sqlString.append(" )");
		Statement stm=null;
		ResultSet m_BaseRs =null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_BaseRs.next())
			{
				intQueryResultRows=m_BaseRs.getInt("rownums");
			}
		}catch(Exception ex)
		{
			System.out.println("BaseOwnFieldInfo.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{ 
			try{
				if(m_BaseRs!=null)
					m_BaseRs.close();
			}catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}

			try{
				if(stm!=null)
					stm.close();
			}catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}			
			m_dbConsole.closeConn();
		}
	}	
	
}
