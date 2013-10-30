package cn.com.ultrapower.ultrawf.models.design;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

/**
 * 一些模板需要的工单的数据提取类
 * @版本 V0.1
 * @Build 0001
 * @作者 BigMouse
 * @说明
 */
public class UtilDataHandler
{
	/**
	 * 获取工单的数据提取类集合
	 * @param formName Form名
	 * @param valueField value字段
	 * @param textField text字段
	 * @return 工单的数据提取类集合
	 */
	public List getSelectionModel(String formName, String valueField, String textField)
	{
		RemedyDBOp r = new RemedyDBOp();
		String tableName = r.GetRemedyTableName(formName);
		String sqlString = "select " + valueField + ", " + textField + " from " + tableName;
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		List sList = new ArrayList();
		try
		{
			while(rs.next())
			{
				int type = rs.getMetaData().getColumnType(1);
				SelectionModel sModel = null;
				if(type == Types.VARCHAR)
				{
					sModel = new SelectionModel(rs.getString(valueField), rs.getString(textField));
				}
				else if(type == Types.NUMERIC)
				{
					sModel = new SelectionModel(String.valueOf(rs.getInt(valueField)), rs.getString(textField));
				}
				sList.add(sModel);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		try
		{
			rs.close();
			stat.close();
			idb.closeConn();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		return sList;
	}
}
