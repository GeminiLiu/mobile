package cn.com.ultrapower.eoms.processSheet.subScript;

import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class ChangeTypeUtil {
	
	/**
	 * 
	 * @param tblName 带WF的BMC表名称
	 * @param searchColumn 查询的列
	 * 
	 * @param condtionColumn 查询的条件列
	 * @param No	条件值
	 * @return
	 */
	public static String getStrByNo(String tblName,String searchColumn,String condtionColumn, String No)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(tblName);
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		String type = "未找到值";
		String sqlCycle = "select "+searchColumn+" from " + strTblName + " t where t." + condtionColumn + "= '" + No +"'";
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sqlCycle);
			if(resultSet.next()) {
				type = FormatString.CheckNullString(resultSet.getString(1)).trim();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
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
		return type;
	}

}
