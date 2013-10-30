package cn.com.ultrapower.interfaces.server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.eoms.processSheet.XmlSplit;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.share.OperationLogFile;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class AlarmBase {
	/**
	 * 连通测试
	 * 
	 * @return
	 */
	public String isAlive() {
		return "";
	}

	
	public String getUserNameByUserLoginName(String userLoginName){
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("UltraProcess:SysUser");
		String sql = "select t.c630000003 from " + strTblName + " t where t.c630000001 = '" + userLoginName + "'";
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		String userFullName = "";
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sql);
			if(resultSet.next()){
				userFullName = resultSet.getString(1);
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
		return userFullName;
	}
	
	
	/**
	 * 解析opdetai的内容，并且做日志记录
	 * @param serSupplier
	 * @param serCaller
	 * @param callerPwd
	 * @param callTime
	 * @param opDetail
	 * @param whichSheet
	 * @param whichAction
	 * @return
	 */
	protected List<List<BaseFieldInfo>> prepareDate(String serSupplier, String serCaller,
			String callerPwd, String callTime, String opDetail,
			String whichSheet, String whichAction) {
		if(opDetail != null && !"".equals(opDetail)){
		// 公共信息
			OperationLogFile.writeTxt("\r\n服务提供方(serSupplier):" + serSupplier);
			OperationLogFile.writeTxt("\r\n服务调用方(serCaller):" + serCaller);
			OperationLogFile.writeTxt("\r\n服务调用方口令/密码(callerPwd):" + callerPwd);
			OperationLogFile.writeTxt("\r\n服务调用时间(callTime):" + callTime);
			OperationLogFile.writeTxt("\r\n操作信息列表(opDetail):" + opDetail);
			OperationLogFile.writeTxt("\r\n--------------END(建单参数)---------------");
			XmlSplit xmlSplit = new XmlSplit();
			List<List<BaseFieldInfo>> list = xmlSplit.getDatatoList(opDetail, whichSheet, whichAction);
			return list;
		}else{
		// 没有正确的数据或者未成功解析，返回null
			OperationLogFile.writeTxt("\r\n------没有正确的数据或者未成功解析，返回null-----");
			return null;
		}	
	}
	
	protected String getSpcialValue(List<List<BaseFieldInfo>> list , String fieldEnName){
		String va = "";
		if(list.get(0) != null){
			for (int i = 0; i < list.get(0).size() ; i++) {
				if(fieldEnName.equals(list.get(0).get(i).getStrFieldName())){
					va = list.get(0).get(i).getStrFieldValue();
					break;
				}
			}
		}
		return va;
	}
}
