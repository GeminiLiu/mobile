package cn.com.ultrapower.eoms.processSheet.subScript;

import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class CchComplainType implements InterStr {

	public String getStrByNo(String No) {
		return ChangeTypeUtil.getStrByNo("WF:Config_EL_TTM_CCH_ComplaintType", "c650000006", "c650000005", No);
	}
		/*RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("WF:Config_EL_TTM_CCH_ComplaintType");
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		String type = "";
		String sqlCycle = "select c650000006 from " + strTblName + " t where t.c650000005 = '" + No +"'";
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
	}*/

}
