package cn.com.ultrapower.ultrawf.models.flowmap;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.flowmap.*;
import cn.com.ultrapower.system.database.*;

public class AuditingLink extends Link
{
	/**
	 * 获取审批环节的Link集合
	 * 
	 * @param sqlString：sql语句
	 * @throws Exception
	 */
	public List getLinkListInfo(String sqlString) throws Exception
	{
		// 读取FlowMapConfig.xml中的字段信息
		FlowMapConfig fmConfig = new FlowMapConfig();
		Map fieldMap = fmConfig.getFieldMap("ALink");
		
		// 读取所有的字段名，替换sql语句中的*，拼成最后的sql语句
		List fieldList = fmConfig.getFieldList("ALink");
		String[] fieldsName = new String[fieldList.size()];
		StringBuffer sqlParas = new StringBuffer();
		for (int i = 0; i < fieldsName.length; i++)
		{
			fieldsName[i] = ((FieldModel) fieldList.get(i)).getFieldName();
			sqlParas.append(fieldsName[i] + ",");
		}
		sqlParas.deleteCharAt(sqlParas.length() - 1);
		StringBuffer sqlStr = new StringBuffer(sqlString);
		sqlStr.replace(sqlStr.indexOf("*"), sqlStr.indexOf("*") + 1, sqlParas.toString());

		// ******************************
		System.out.println("FlowMap_SQL_SELECT: " + sqlStr);

		// 执行查询语句
		IDataBase dbConsole = GetDataBase.createDataBase();;
		Statement stm = null;
		ResultSet rs = null;
		try
		{
			stm = dbConsole.GetStatement();
			rs = dbConsole.executeResultSet(stm, sqlStr.toString());
		}
		catch (Exception ex)
		{
			throw ex;
		}
		// ******************************
		
		List lInfoList = new ArrayList();
		
		// 读取ResultSet中的数据
		while (rs.next())
		{
			AuditingLinkInfo dlInfo = new AuditingLinkInfo();
			
			// 遍历要查询的字段的数组

			for (int i = 0; i < fieldsName.length; i++)
			{
				// 根据要查询的字段获取字段的Model
				FieldModel fModel = (FieldModel) fieldMap.get(fieldsName[i]);

				// 获取列名
				String colName = fModel.getFieldName();

				// 拼接set方法
				String modelName = "set" + ((FieldModel) fieldMap.get(colName)).getFieldModel().trim();

				// 判断字段的数据类型

				switch (fModel.getFieldType())
				{
					case FieldType.INT:
					{
						// 找到并调用set方法，向字段赋值

						Class[] c = { int.class };
						Method method = AuditingLinkInfo.class.getMethod(modelName, c);
						Object[] paraObj = { new Integer(rs.getInt(colName)) };
						method.invoke(dlInfo, paraObj);
						break;
					}
					case FieldType.STRING:
					{
						// 找到并调用set方法，向字段赋值

						Class[] c = { String.class };
						Method method = AuditingLinkInfo.class.getMethod(modelName, c);
						Object[] paraObj = { rs.getString(colName) };
						method.invoke(dlInfo, paraObj);
						break;
					}
				}
			}
			
			lInfoList.add((LinkInfo)dlInfo);
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
		return lInfoList;
	}
}
