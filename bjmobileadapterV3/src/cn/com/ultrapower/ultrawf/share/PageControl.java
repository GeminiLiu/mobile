package cn.com.ultrapower.ultrawf.share;

import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class PageControl {
	
	/**
	 * 根据数据库类型以及查询的Sql生成分页查询的Sql
	 * 
	 * @param strSql查询的Sql语句
	 * @param strDataType数据库的类型
	 * @param intStartRow开始的行数
	 * @param intStepRow要返回的多少行的数量
	 * @author xufaqiu
	 * @date 2006-11-15
	 * @return
	 */
	public static String GetSqlStringForPagination(String strSql, String strDataBaseType,
			int p_PageNumber,int p_StepRow)
	{
		int intStartRow;	
		int intRowCount;
		if(p_PageNumber==1)
			intStartRow=1;
		else if(p_PageNumber>1)
			intStartRow=(p_PageNumber-1)*p_StepRow+1;
		else
			intStartRow=0;
		intRowCount=p_PageNumber*p_StepRow;
		//System.out.println("改之前："+strSql);
		StringBuffer strRe = new StringBuffer();
		if (strDataBaseType.toUpperCase().equals("ORACLE")) {
			// Oracle是通过Rownum限定行数
			strRe.append("select * from ( ");
			strRe.append("  select row_.*, rownum rownum_ from (  ");
			strRe.append(strSql);
			strRe.append(") row_ where rownum <="
					+ Integer.toString(intRowCount));
			strRe.append(") rsresult where rownum_>=" + Integer.toString(intStartRow));
		} else if (strDataBaseType.toUpperCase() == "SQLSERVER") {
			// SqlServer是通过Top来限定行数的
		} else 
		{
			strRe.append(strSql);
		}
		 //System.out.println("改之后："+strRe.toString());
		return strRe.toString();
	}	
	
	
	public static String bindGetSqlStringForPagination(String strSql, String strDataBaseType,
			int p_PageNumber,int p_StepRow)
	{

		//System.out.println("改之前："+strSql);
		StringBuffer strRe = new StringBuffer();
		if (strDataBaseType.toUpperCase().equals("ORACLE")) {
			// Oracle是通过Rownum限定行数
			strRe.append("select * from ( ");
			strRe.append("  select row_.*, rownum rownum_ from (  ");
			strRe.append(strSql);
			strRe.append(") row_ where rownum <=?");
			strRe.append(") rsresult where rownum_>=?");
		} else if (strDataBaseType.toUpperCase() == "SQLSERVER") {
			// SqlServer是通过Top来限定行数的
		} else 
		{
			strRe.append(strSql);
		}
		 //System.out.println("改之后："+strRe.toString());
		return strRe.toString();
	}	
	
	
	/**
	 * 获得总页数	 * @param _allCount
	 * @param _showCount
	 * @return
	 */
	public static int CalculatePages(String _strSql,int _showCount)
	{		
		PageControl pageControl = new PageControl();
		int allCount = pageControl.GetQueryResultRows(_strSql);
		int intPages=0;
		if(_showCount>0)
		{
			intPages=allCount/_showCount;
			if(allCount%_showCount>0)
				intPages++;
		}
		return intPages;
	}
	/**
	 * 获得总记录行数	 * @param _strSql
	 * @return
	 */
	public int GetQueryResultRows(String _strSql)
	{
		int pageNumber = 0;
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(_strSql);
		sqlString.append(" )");
		Statement stm=null;
		ResultSet m_BaseRs =null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_BaseRs.next())
			{
				pageNumber=m_BaseRs.getInt("rownums");				
			}
			m_BaseRs.close();
			stm.close();
		}catch(Exception ex)
		{
			System.err.println("PageControl.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(m_BaseRs!=null)
					m_BaseRs.close();
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
			return pageNumber;
	}	

}
