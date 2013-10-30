package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.*;

public class TplDealLink
{
	public static String BASESCHEMA = "WF:App_TplDealLink";
	public static String BASESCHEMA_ACTIVE = "WF:App_DealLink";
	
	/**
	 * TplBaseFixState的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplDealLinkModel tdlModel)
	{
		List p_FieldInfoList = getfieldInfoList(tdlModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA,p_FieldInfoList);
		return strReturnID;
	}
	
	public void Delete(RemedyFormOp RemedyOp, String tdlID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tdlID);
	}
	
	private List getfieldInfoList(TplDealLinkModel tdlModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("700020501", tdlModel.getLinkBaseID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020502", tdlModel.getLinkBaseSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020503", tdlModel.getStartPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020504", tdlModel.getEndPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020505", tdlModel.getDesc(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020506", String.valueOf(tdlModel.getLinkFlag00IsAvail()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020507", String.valueOf(tdlModel.getFlag21Required()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020508", String.valueOf(tdlModel.getLinkFlagDuplicated()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020044", tdlModel.getLinkType(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020521", tdlModel.getLinkPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020522", String.valueOf(tdlModel.getStartPort()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020523", String.valueOf(tdlModel.getEndPort()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020524", String.valueOf(tdlModel.getLinkVerdictResult()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020525", tdlModel.getLinkGoLine(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020591", String.valueOf(tdlModel.getStartPoint()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020592", String.valueOf(tdlModel.getEndPoint()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020526", String.valueOf(tdlModel.getLinkNum()), 2));
		return remedyFieldList;
	}
	
	public List getDealLinkList(String baseID, String schema, String flowID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020501 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA_ACTIVE);
		tmpadd += " and C700020502 = '" + schema + "' ";
		if(flowID.equals(""))
		{
			tmpadd += " and C700020527 is null";
		}
		else
		{
			tmpadd += " and C700020527 = '" + flowID + "'";
		}
		return getList(tablename, tmpadd);
	}
	
	public List getTplDealLinkList(String baseID, String schema, String type)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020501 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		return getList(tablename, tmpadd);
	}

	private List getList(String tablename, String tmpadd)
	{
		String sqlString = "select C1 as LinkID"+
		", C700020501 as LinkBaseID"+
		", C700020502 as LinkBaseSchema"+
		", C700020503 as StartPhaseNo"+
		", C700020504 as EndPhaseNo"+
		", C700020505 as LinkDesc"+
		", C700020506 as LinkFlag00IsAvail"+
		", C700020507 as Flag21Required"+
		", C700020508 as LinkFlagDuplicated"+
		", C700020044 as LinkType"+
		", C700020521 as LinkPhaseNo"+
		", C700020522 as StartPort"+
		", C700020523 as EndPort"+
		", C700020524 as LinkVerdictResult"+
		", C700020591 as StartPoint"+
		", C700020592 as EndPoint"+
		", C700020526 as LinkNum"+
		" from " + tablename + " where " + tmpadd + " order by C700020521 asc, C700020508 asc, C1 asc";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List tdlList = new ArrayList();
		try
		{
			while(rs.next())
			{
				TplDealLinkModel dlModel=new TplDealLinkModel();
				dlModel.setLinkID(rs.getString("LinkID"));
				dlModel.setLinkBaseID(rs.getString("LinkBaseID"));
				dlModel.setLinkBaseSchema(rs.getString("LinkBaseSchema"));
				dlModel.setStartPhaseNo(rs.getString("StartPhaseNo"));
				dlModel.setEndPhaseNo(rs.getString("EndPhaseNo"));
				dlModel.setDesc(rs.getString("LinkDesc"));
				dlModel.setLinkFlag00IsAvail(rs.getInt("LinkFlag00IsAvail"));
				dlModel.setFlag21Required(rs.getInt("Flag21Required"));
				dlModel.setLinkFlagDuplicated(rs.getInt("LinkFlagDuplicated"));
				dlModel.setLinkType(rs.getString("LinkType"));
				dlModel.setLinkPhaseNo(rs.getString("LinkPhaseNo"));
				dlModel.setStartPort(rs.getInt("StartPort"));
				dlModel.setEndPort(rs.getInt("EndPort"));
				dlModel.setLinkVerdictResult(rs.getInt("LinkVerdictResult"));
				dlModel.setLinkGoLine("");
				dlModel.setStartPoint(rs.getInt("StartPoint"));
				dlModel.setEndPoint(rs.getInt("EndPoint"));
				dlModel.setLinkNum(rs.getInt("LinkNum"));
				if(dlModel.getLinkFlagDuplicated() == 0)
				{
					tdlList.add(dlModel);
				}
				else
				{
					for(Iterator it = tdlList.iterator(); it.hasNext();)
					{
						TplDealLinkModel tdlm = (TplDealLinkModel)it.next();
						if(tdlm.getLinkPhaseNo().equals(dlModel.getLinkPhaseNo()))
						{
							tdlm.setLinkFlag00IsAvail(dlModel.getLinkFlag00IsAvail());
							break;
						}
					}
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
		return tdlList;
	}
}
