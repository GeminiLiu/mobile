package cn.com.ultrapower.ultrawf.models.process;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;

public class TplCreateBaseDefine
{
	public static String BASESCHEMA = "WF:App_TplBase_CreateBaseDefine";
	
	/**
	 * TplCreateBaseDefine的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplCreateBaseDefineModel tcbdModel)
	{
		List p_FieldInfoList = getfieldInfoList(tcbdModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA, p_FieldInfoList);
		return strReturnID;
	}
	
	public void Delete(RemedyFormOp RemedyOp, String tcbdID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tcbdID);
	}
	
	private List getfieldInfoList(TplCreateBaseDefineModel tcbdModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("700023001", tcbdModel.getCBDBaseID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700023002", tcbdModel.getCBDBaseSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700023007", tcbdModel.getCBDBaseName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700023003", tcbdModel.getCBDProcessPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700023004", String.valueOf(tcbdModel.getCreateBaseAftermathType()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700023005", tcbdModel.getCreateBaseSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700023006", tcbdModel.getCreateBaseName(), 4));
		return remedyFieldList;
	}
	
	public List getTplCreateBaseDefineList(String baseID, String schema, String type)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700023001 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		String sqlString = "select C1 as CreateBaseDefineID"+
		", C700023001 as CBDBaseID"+
		", C700023002 as CBDBaseSchema"+
		", C700023007 as CBDBaseName"+
		", C700023003 as CBDProcessPhaseNo"+
		", C700023004 as CreateBaseAftermathType"+
		", C700023005 as CreateBaseSchema"+
		", C700023006 as CreateBaseName"+
		" from " + tablename + " where " + tmpadd;
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List tcbdList = new ArrayList();
		try
		{
			while(rs.next())
			{
				TplCreateBaseDefineModel tcbdModel=new TplCreateBaseDefineModel();
				tcbdModel.setCreateBaseDefineID(rs.getString("CreateBaseDefineID"));
				tcbdModel.setCBDBaseID(rs.getString("CBDBaseID"));
				tcbdModel.setCBDBaseSchema(rs.getString("CBDBaseSchema"));
				tcbdModel.setCBDBaseName(rs.getString("CBDBaseName"));
				tcbdModel.setCBDProcessPhaseNo(rs.getString("CBDProcessPhaseNo"));
				tcbdModel.setCreateBaseAftermathType(rs.getInt("CreateBaseAftermathType"));
				tcbdModel.setCreateBaseSchema(rs.getString("CreateBaseSchema"));
				tcbdModel.setCreateBaseName(rs.getString("CreateBaseName"));
				tcbdList.add(tcbdModel);
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
		return tcbdList;
	}
}
