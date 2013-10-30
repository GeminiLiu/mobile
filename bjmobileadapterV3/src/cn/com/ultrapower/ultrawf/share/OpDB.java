package cn.com.ultrapower.ultrawf.share;

import java.sql.Clob;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;

public class OpDB {
	
	public static RowSet getDataSetFromXML(String p_queryName,RDParameter p_rDParameter)
	{
      	ReBuildSQL m_reBuildSQL	= new ReBuildSQL(p_queryName,p_rDParameter);
      	//获得查询SQL
      	String m_sql			= m_reBuildSQL.getReBuildSQL();
      	//查询数据
      	Table 	m_table			= new Table(GetDataBase.createDataBase(),"");
      	RowSet 	m_rowSet		= m_table.executeQuery(m_sql,null,0,0,0);
      	return m_rowSet;
	}
	
	public static RowSet getDataSet(String p_sql)
	{
      	Table 	m_table			= new Table(GetDataBase.createDataBase(),"");
      	RowSet 	m_rowSet		= m_table.executeQuery(p_sql,null,0,0,0);
      	return m_rowSet;
	}
	
	public static String getClobString(Clob p_clob)
	{
		String strValue="";
	
		if (p_clob!=null) 
		{
			try{
			if(p_clob.length()>0)
			{
				strValue=p_clob.getSubString((long)1,(int)p_clob.length());
			}
			}catch(Exception ex)
			{
				strValue="";
			}
		}
		return strValue;
	}	
}
