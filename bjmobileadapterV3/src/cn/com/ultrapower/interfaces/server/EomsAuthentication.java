package cn.com.ultrapower.interfaces.server;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import cn.com.ultrapower.eoms.processSheet.XmlSplit;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.eoms.processSheet.Contents;

public class EomsAuthentication extends AlarmBase {
	
	
	
	
	/**
	 * EOMS系统提供该接口服务以供网管系统对EOMS系统用户进行鉴权
	 * @param serSupplier
	 * @param serCaller
	 * @param callerPwd
	 * @param callTime
	 * @param opDetail
	 * @return
	 */
	public String eomsAuthentication(String serSupplier,String serCaller,String callerPwd,String callTime,String opDetail){
		XmlSplit xmlSplit = new XmlSplit();
		List<Map<String,String>> list = xmlSplit.getDatatoListM(opDetail, Contents.NMSPROCESS, Contents.EOMSAUTHENTICATION);
		String userName = list.get(0).get("userName");
		String password = list.get(0).get("userPassword");
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("UltraProcess:SysUser");
		String sql = "select count(c1) from " + strTblName + " t where t.c630000001 ='"+ userName +"' and c630000002 = '" + password +"'";
		Connection conn = null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		try {
			conn = m_dbConsole.getConn();
			stm = m_dbConsole.GetStatement();
			//如果是空值返回０，否则返回符合条件的记录数
			int s = Integer.valueOf(String.valueOf(m_dbConsole.executeScalar(stm, sql)));
			if( s != 0){
				return "密码正确";
			}
		}catch (Exception ex) {
				ex.printStackTrace();
				return "用户名或密码错误";
		}finally{
				try {
					if (stm != null)
						stm.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				m_dbConsole.closeConn();
		}
		return "用户名或密码错误";
	}
}
