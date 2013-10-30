package cn.com.ultrapower.ultrawf.models.flowmap;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;

/**
 * 环节日志的操作类
 * 
 * @author BigMouse
 */
public class ProcessLog
{
	/**
	 * 获取环节日志集合
	 * 
	 * @param sqlString：sql语句
	 * @return 环节日志集合
	 * @throws Exception
	 */
	public List getProcessLogInfoList(String sqlString) throws Exception
	{
		// ******************************
		System.out.println("FlowMap_SQL_SELECT: " + sqlString);

		// 执行查询语句
		IDataBase dbConsole = GetDataBase.createDataBase();;
		Statement stm = null;
		ResultSet rs = null;
		try
		{
			stm = dbConsole.GetStatement();
			rs = dbConsole.executeResultSet(stm, sqlString);
		}
		catch (Exception ex)
		{
			throw ex;
		}
		// ******************************

		List pLogInfoList = new ArrayList();

		while (rs.next())
		{
			ProcessLogInfo pLogInfo = new ProcessLogInfo();

			pLogInfo.setLogID(rs.getString("C1"));
			pLogInfo.setProcessID(rs.getString("C700020401"));
			pLogInfo.setAct(rs.getString("C700020402"));
			pLogInfo.setLogUser(rs.getString("C700020403"));
			pLogInfo.setLogUserID(rs.getString("C700020404"));
			pLogInfo.setStDate(rs.getInt("C700020405"));
			pLogInfo.setResult(rs.getString("C700020406"));

			pLogInfoList.add(pLogInfo);
		}
		try {
			if (rs != null)
				rs.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		try {
			if (stm != null)
				stm.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}				
		dbConsole.closeConn();
		return pLogInfoList;
	}
}
