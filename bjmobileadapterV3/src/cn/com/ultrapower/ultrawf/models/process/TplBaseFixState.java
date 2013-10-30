package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;

public class TplBaseFixState
{
	public static String BASESCHEMA = "WF:App_TplBase_FixState";
	public static String BASESCHEMA_ACTIVE = "WF:App_Base_FixState";
	
	/**
	 * TplBaseFixState的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplBaseFixStateModel tbfsModel)
	{
		List p_FieldInfoList = getfieldInfoList(tbfsModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA,p_FieldInfoList);
		return strReturnID;
	}
	
	public void Delete(RemedyFormOp RemedyOp, String tbfsID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tbfsID);
	}
	
	private List getfieldInfoList(TplBaseFixStateModel tbfsModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("650000010", tbfsModel.getBaseStateCode(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000001", tbfsModel.getBaseID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000002", tbfsModel.getBaseCategoryName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000003", tbfsModel.getBaseCategorySchama(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000004", String.valueOf(tbfsModel.getBaseStateIndex()), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000005", tbfsModel.getBaseStateName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000006", tbfsModel.getBaseStateDesc(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000007", tbfsModel.getBaseStateColor(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000008", String.valueOf(tbfsModel.getBaseStateLength()), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000009", String.valueOf(tbfsModel.getBaseStateFlag00IsAvail()), 6));
		return remedyFieldList;
	}
	
	public List getBaseFixStateList(String baseID, String schema, String flowID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C650000001 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA_ACTIVE);
		tmpadd += " and C650000003 = '" + schema + "' ";
		if(flowID.equals(""))
		{
			tmpadd += " and C650000011 is null";
		}
		else
		{
			tmpadd += " and C650000011 = '" + flowID + "'";
		}
		return getList(tablename, tmpadd);
	}
	
	public List getTplBaseFixStateList(String baseID, String schema, String type)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C650000001 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		return getList(tablename, tmpadd);
	}

	private List getList(String tablename, String tmpadd)
	{
		String sqlString = "select C1 as BaseTplStateID"+
		", C650000001 as BaseID"+
		", C650000002 as BaseCategoryName"+
		", C650000003 as BaseCategorySchama"+
		", C650000004 as BaseStateIndex"+
		", C650000005 as BaseStateName"+
		", C650000006 as BaseStateDesc"+
		", C650000007 as BaseStateColor"+
		", C650000008 as BaseStateLength"+
		", C650000009 as BaseStateFlag00IsAvail"+
		", C650000010 as BaseStateCode"+
		" from " + tablename + " where " + tmpadd + " order by C650000004 asc";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List tbfsList = new ArrayList();
		try
		{
			while(rs.next())
			{
				TplBaseFixStateModel tsModel=new TplBaseFixStateModel();
				tsModel.setBaseTplStateID(rs.getString("BaseTplStateID"));
				tsModel.setBaseID(rs.getString("BaseID"));
				tsModel.setBaseCategoryName(rs.getString("BaseCategoryName"));
				tsModel.setBaseCategorySchama(rs.getString("BaseCategorySchama"));
				tsModel.setBaseStateIndex(rs.getInt("BaseStateIndex"));
				tsModel.setBaseStateName(rs.getString("BaseStateName"));
				tsModel.setBaseStateDesc(rs.getString("BaseStateDesc"));
				tsModel.setBaseStateColor(rs.getString("BaseStateColor"));
				tsModel.setBaseStateLength(rs.getInt("BaseStateLength"));
				tsModel.setBaseStateFlag00IsAvail(rs.getInt("BaseStateFlag00IsAvail"));
				tsModel.setBaseStateCode(rs.getString("BaseStateCode"));	
				tbfsList.add(tsModel);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stat != null)
					stat.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			idb.closeConn();
		}
		return tbfsList;
	}
}
