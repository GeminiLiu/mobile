package cn.com.ultrapower.eoms.showList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class ShowProcessInfo {
	
	public Map<String,String> getInfobyC1(String schemaName,String[] cols,String c1Value)
	{
		Map<String,String> map = new HashMap();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(schemaName);
		String sql = "select ";
		for(int i=0;i<cols.length;i++)
		{
			if(i>0)
			{
				sql += ",";
			}
			sql += cols[i];
		}
		
		sql += " from "+ strTblName +" where c1 = '"+c1Value+"'";
		
		// 轮询
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		Connection conn = null;
		ResultSetMetaData metadata;
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sql);
			
			if(resultSet.next()) {
				metadata = resultSet.getMetaData();
		        for (int i = 1; i <= metadata.getColumnCount(); i++) {
		          String field_name = metadata.getColumnName(i).toLowerCase();
		          String value = resultSet.getString(metadata.getColumnName(i));
		          value = (value == null) ? "" : value;
		          map.put(field_name, value);
		        }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception ex){
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
		
		return map;
	}

}
