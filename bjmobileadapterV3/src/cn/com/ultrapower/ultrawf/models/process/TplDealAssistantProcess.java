package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;

public class TplDealAssistantProcess
{
public static String BASESCHEMA = "WF:App_TplDealAssistantProcess";
public static String BASESCHEMA_ACTIVE = "WF:App_DealAssistantProcess";
	
	/**
	 * TplBaseFixState的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplDealAssistantProcessModel tdapModel)
	{
		List p_FieldInfoList = getfieldInfoList(tdapModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA,p_FieldInfoList);
		return strReturnID;
	}
	
	public void Delete(RemedyFormOp RemedyOp, String tdapID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tdapID);
	}
	
	private List getfieldInfoList(TplDealAssistantProcessModel tdapModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("700020801", tdapModel.getAssistantProcessBaseID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020802", tdapModel.getAssistantProcessBaseSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020803", tdapModel.getBaseTplStateCode(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020804", tdapModel.getBaseTplStateName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020805", tdapModel.getAssistantProcessPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020806", String.valueOf(tdapModel.getAssistantProcessPurpose()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020807", tdapModel.getAssistantProcessDesc(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020808", String.valueOf(tdapModel.getAssistantProcessFlag00IsAvail()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020809", tdapModel.getAssistantProcessGoLine(),4));
		remedyFieldList.add(new RemedyFieldInfo("700020810", tdapModel.getAssistantProcessType(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020811", String.valueOf(tdapModel.getAssistantFlagDuplicated()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020890", String.valueOf(tdapModel.getAssistantProcessPosX()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020891", String.valueOf(tdapModel.getAssistantProcessPosY()), 2));
		return remedyFieldList;
	}
	
	public List getDealAssistantProcessList(String baseID, String schema, String flowID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020801 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA_ACTIVE);
		tmpadd += " and C700020802 = '" + schema + "' ";
		if(flowID.equals(""))
		{
			tmpadd += " and C700020813 is null";
		}
		else
		{
			tmpadd += " and C700020813 = '" + flowID + "'";
		}
		return getList(tablename, tmpadd);
	}
	
	public List getTplDealAssistantProcessList(String baseID, String schema, String type)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020801 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		return getList(tablename, tmpadd);
	}

	private List getList(String tablename, String tmpadd)
	{
		String sqlString = "select C1 as AssistantProcessID"+
		", C700020801 as AssistantProcessBaseID"+
		", C700020802 as AssistantProcessBaseSchema"+
		", C700020803 as BaseTplStateCode"+
		", C700020804 as BaseTplStateName"+
		", C700020805 as AssistantProcessPhaseNo"+
		", C700020806 as AssistantProcessPurpose"+
		", C700020807 as AssistantProcessDesc"+
		", C700020808 as AssistantProcessFlag00IsAvail"+
		", C700020810 as AssistantProcessType"+
		", C700020811 as AssistantFlagDuplicated"+
		", C700020890 as AssistantProcessPosX"+
		", C700020891 as AssistantProcessPosY"+
		" from " + tablename + " where " + tmpadd + " and C700020811 = 0";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List dapList = new ArrayList();
		try
		{
			while(rs.next())
			{
				TplDealAssistantProcessModel daModel=new TplDealAssistantProcessModel();
				daModel.setAssistantProcessID(rs.getString("AssistantProcessID"));
				daModel.setAssistantProcessBaseID(rs.getString("AssistantProcessBaseID"));
				daModel.setAssistantProcessBaseSchema(rs.getString("AssistantProcessBaseSchema"));
				daModel.setBaseTplStateCode(rs.getString("BaseTplStateCode"));
				daModel.setBaseTplStateName(rs.getString("BaseTplStateName"));
				daModel.setAssistantProcessPhaseNo(rs.getString("AssistantProcessPhaseNo"));
				daModel.setAssistantProcessPurpose(rs.getInt("AssistantProcessPurpose"));
				daModel.setAssistantProcessDesc(rs.getString("AssistantProcessDesc"));
				daModel.setAssistantProcessFlag00IsAvail(rs.getInt("AssistantProcessFlag00IsAvail"));
				daModel.setAssistantProcessGoLine("");
				daModel.setAssistantProcessType(rs.getString("AssistantProcessType"));
				daModel.setAssistantFlagDuplicated(rs.getInt("AssistantFlagDuplicated"));
				daModel.setAssistantProcessPosX(rs.getInt("AssistantProcessPosX"));
				daModel.setAssistantProcessPosY(rs.getInt("AssistantProcessPosY"));
				dapList.add(daModel);
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
		return dapList;
	}
}
