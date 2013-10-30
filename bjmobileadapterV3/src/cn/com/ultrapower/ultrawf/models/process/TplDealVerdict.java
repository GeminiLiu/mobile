package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.*;

public class TplDealVerdict
{
public static String BASESCHEMA = "WF:App_TplDealVerdict";
public static String BASESCHEMA_ACTIVE = "WF:App_DealVerdict";
	
	/**
	 * TplBaseFixState的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplDealVerdictModel tdvModel)
	{
		List p_FieldInfoList = getfieldInfoList(tdvModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA,p_FieldInfoList);
		return strReturnID;
	}
	
	public void Delete(RemedyFormOp RemedyOp, String tdvID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tdvID);
	}
	
	private List getfieldInfoList(TplDealVerdictModel tdvModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("700020601", tdvModel.getVerdictBaseID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020602", tdvModel.getVerdictBaseSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020603", tdvModel.getBaseTplStateCode(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020604", tdvModel.getBaseTplStateName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020605", tdvModel.getVerdictPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020606", tdvModel.getVerdictCondition(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020607", tdvModel.getVerdictDesc(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020608", String.valueOf(tdvModel.getVerdictFlag00IsAvail()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020609", tdvModel.getVerdictGoLine(),4));
		remedyFieldList.add(new RemedyFieldInfo("700020610", tdvModel.getVerdictType(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020611", String.valueOf(tdvModel.getVerdictFlagDuplicated()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020690", String.valueOf(tdvModel.getVerdictPosX()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020691", String.valueOf(tdvModel.getVerdictPosY()), 2));
		return remedyFieldList;
	}
	
	public List getDealVerdictList(String baseID, String schema, String flowID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020601 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA_ACTIVE);
		tmpadd += " and C700020602 = '" + schema + "' ";
		if(flowID.equals(""))
		{
			tmpadd += " and C700020613 is null";
		}
		else
		{
			tmpadd += " and C700020613 = '" + flowID + "'";
		}
		return getList(tablename, tmpadd);
	}
	
	public List getTplDealVerdictList(String baseID, String schema, String type)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020601 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		return getList(tablename, tmpadd);
	}

	private List getList(String tablename, String tmpadd)
	{
		String sqlString = "select C1 as VerdictID" +
				", C3 as CreateDate"+
				", C700020601 as VerdictBaseID"+
				", C700020602 as VerdictBaseSchema"+
				", C700020603 as BaseTplStateCode"+
				", C700020604 as BaseTplStateName"+
				", C700020605 as VerdictPhaseNo"+
				", C700020606 as VerdictCondition"+
				", C700020607 as VerdictDesc"+
				", C700020608 as VerdictFlag00IsAvail"+
				", C700020610 as VerdictType"+
				", C700020611 as VerdictFlagDuplicated"+
				", C700020690 as VerdictPosX"+
				", C700020691 as VerdictPosY"+
				" from " + tablename + " where " + tmpadd + " order by C700020605 asc, C700020611 asc";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List dvpList = new ArrayList();
		try
		{
			while(rs.next())
			{
				TplDealVerdictModel dvModel=new TplDealVerdictModel();
				dvModel.setVerdictID(rs.getString("VerdictID"));
				dvModel.setCreateDate(rs.getLong("CreateDate"));
				dvModel.setVerdictBaseID(rs.getString("VerdictBaseID"));
				dvModel.setVerdictBaseSchema(rs.getString("VerdictBaseSchema"));
				dvModel.setBaseTplStateCode(rs.getString("BaseTplStateCode"));
				dvModel.setBaseTplStateName(rs.getString("BaseTplStateName"));
				dvModel.setVerdictPhaseNo(rs.getString("VerdictPhaseNo"));
				dvModel.setVerdictCondition(rs.getString("VerdictCondition"));
				dvModel.setVerdictDesc(rs.getString("VerdictDesc"));
				dvModel.setVerdictFlag00IsAvail(rs.getInt("VerdictFlag00IsAvail"));
				dvModel.setVerdictGoLine("");
				dvModel.setVerdictType(rs.getString("VerdictType"));
				dvModel.setVerdictFlagDuplicated(rs.getInt("VerdictFlagDuplicated"));
				dvModel.setVerdictPosX(rs.getInt("VerdictPosX"));
				dvModel.setVerdictPosY(rs.getInt("VerdictPosY"));
				
				if(dvModel.getVerdictFlagDuplicated() == 1)
				{
					for(Iterator it = dvpList.iterator(); it.hasNext();)
					{
						TplDealVerdictModel tdvm = (TplDealVerdictModel)it.next();
						if(tdvm.getVerdictPhaseNo().equals(dvModel.getVerdictPhaseNo()))
						{
							tdvm.setDuplicatedVerdictModel(dvModel);
							break;
						}
					}
				}
				else
				{
					dvpList.add(dvModel);
				}
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
		return dvpList;
	}
}
