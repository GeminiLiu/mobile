package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.*;

public class TplDealProcess
{
	public static String BASESCHEMA = "WF:App_TplDealProcess";
	public static String BASESCHEMA_ACTIVE = "WF:App_DealProcess";
	
	/**
	 * TplBaseFixState的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplDealProcessModel tdpModel)
	{
		List p_FieldInfoList = getfieldInfoList(tdpModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA,p_FieldInfoList);
		return strReturnID;
	}
	
	public void Delete(RemedyFormOp RemedyOp, String tdpID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tdpID);
	}
	
	private List getfieldInfoList(TplDealProcessModel tdpModel)
	{
		List remedyFieldList = new ArrayList();	
		//remedyFieldList.add(new RemedyFieldInfo("1", tdpModel.getProcessID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020075", tdpModel.getProcessGoLine(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020001", tdpModel.getProcessBaseID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020061", tdpModel.getBaseStateCode(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020002", tdpModel.getProcessBaseSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020062", tdpModel.getBaseStateName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020003", tdpModel.getPhaseNo(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020084", tdpModel.getRoleOnlyID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020085", tdpModel.getRoleName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020011", tdpModel.getProcessStatus(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020043", tdpModel.getProcessType(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020018", tdpModel.getDesc(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020083", tdpModel.getActionName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020089", tdpModel.getActionPageName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020090", tdpModel.getActionPageID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020020", String.valueOf(tdpModel.getFlagActive()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020021", String.valueOf(tdpModel.getFlagPredefined()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020022", String.valueOf(tdpModel.getFlagDuplicated()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020511", String.valueOf(tdpModel.getPosX()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020512", String.valueOf(tdpModel.getPosY()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020078", String.valueOf(tdpModel.getAssignOverTimeDate_tmp()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020079", String.valueOf(tdpModel.getAcceptOverTimeDate_tmp()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020080", String.valueOf(tdpModel.getDealOverTimeDate_tmp()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020088", String.valueOf(tdpModel.getFlag35IsCanCreateBase()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020082", String.valueOf(tdpModel.getFlag36IsCreateBase()), 2));
		remedyFieldList.add(new RemedyFieldInfo("700020019", String.valueOf(tdpModel.getFlagType()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020057", String.valueOf(tdpModel.getFlag00IsAvail()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020023", String.valueOf(tdpModel.getFlag01Assign()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020024", String.valueOf(tdpModel.getFlag02Copy()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020025", String.valueOf(tdpModel.getFlag03Assist()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020026", String.valueOf(tdpModel.getFlag04Transfer()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020027", String.valueOf(tdpModel.getFlag05TurnDown()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020028", String.valueOf(tdpModel.getFlag06TurnUp()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020029", String.valueOf(tdpModel.getFlag07Recall()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020030", String.valueOf(tdpModel.getFlag08Cancel()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020031", String.valueOf(tdpModel.getFlag09Close()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020032", String.valueOf(tdpModel.getFlag15ToAuditing()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020091", String.valueOf(tdpModel.getFlag16ToAssistAuditing()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020033", String.valueOf(tdpModel.getFlag20SideBySide()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020046", String.valueOf(tdpModel.getFlag22IsSelect()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020034", String.valueOf(tdpModel.getFlag30AuditingResult()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020035", String.valueOf(tdpModel.getFlag31IsTransfer()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020052", String.valueOf(tdpModel.getFlag32IsToTransfer()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020053", String.valueOf(tdpModel.getFlag33IsEndPhase()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020077", String.valueOf(tdpModel.getFlag34IsEndDuplicated()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020094", String.valueOf(tdpModel.getFlag37IsNeedStartInsideFlow()), 6));
		remedyFieldList.add(new RemedyFieldInfo("700020095", String.valueOf(tdpModel.getStartInsideFlowID()), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020096", String.valueOf(tdpModel.getStartInsideFlowName()), 4));
		remedyFieldList.add(new RemedyFieldInfo("700020092", String.valueOf(tdpModel.getCustomActions()), 4));

		remedyFieldList.add(new RemedyFieldInfo("700020081", tdpModel.getPhaseNoTakeMeActive(), 4));
		return remedyFieldList;
	}
	
	public List getDealProcessList(String baseID, String schema, String flowID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020001 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA_ACTIVE);
		tmpadd += " and C700020002 = '" + schema + "' and C700020021 = 1";
		if(flowID.equals(""))
		{
			tmpadd += " and C700020201 is null";
		}
		else
		{
			tmpadd += " and C700020201 = '" + flowID + "'";
		}
		return getList("MAP", tablename, tmpadd);
	}
	
	public List getTplDealProcessList(String baseID, String schema, String type)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		String tmpadd = " C700020001 = '" + baseID + "'";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		return getList(type, tablename, tmpadd);
	}

	private List getList(String type, String tablename, String tmpadd)
	{
		String sqlString = "select C1 as ProcessID"+
		", C700020075 as ProcessGoLine " + 
		", C700020001 as ProcessBaseID " + 
		", C700020061 as BaseStateCode " + 
		", C700020002 as ProcessBaseSchema " + 
		", C700020062 as BaseStateName " + 
		", C700020003 as PhaseNo " +
		", C700020005 as Assignee " +
		", C700020006 as AssigneeID " +
		", C700020007 as ProcessGroup " +
		", C700020008 as ProcessGroupID " +
		", C700020009 as Dealer " +
		", C700020010 as DealerID " +
		", C700020015 as StDate " +
		", C700020016 as BgDate " +
		", C700020017 as EdDate " + 
		", C700020084 as RoleOnlyID " + 
		", C700020085 as RoleName " + 
		", C700020011 as ProcessStatus " + 
		", C700020043 as ProcessType " + 
		", C700020018 as ProcessDesc " + 
		", C700020083 as ActionName " + 
		", C700020089 as ActionPageName " + 
		", C700020090 as ActionPageID " + 
		", C700020020 as FlagActive " + 
		", C700020021 as FlagPredefined " + 
		", C700020022 as FlagDuplicated " + 
		", C700020511 as PosX " + 
		", C700020512 as PosY " + 
		", C700020078 as AssignOverTimeDate_tmp " + 
		", C700020079 as AcceptOverTimeDate_tmp " + 
		", C700020080 as DealOverTimeDate_tmp " + 
		", C700020088 as Flag35IsCanCreateBase " + 
		", C700020082 as Flag36IsCreateBase " + 
		", C700020019 as FlagType " + 
		", C700020057 as Flag00IsAvail " + 
		", C700020023 as Flag01Assign " + 
		", C700020024 as Flag02Copy " + 
		", C700020025 as Flag03Assist " + 
		", C700020026 as Flag04Transfer " + 
		", C700020027 as Flag05TurnDown " + 
		", C700020028 as Flag06TurnUp " + 
		", C700020029 as Flag07Recall " + 
		", C700020030 as Flag08Cancel " + 
		", C700020031 as Flag09Close " + 
		", C700020032 as Flag15ToAuditing " + 
		", C700020091 as Flag16ToAssistAuditing " + 
		", C700020033 as Flag20SideBySide " + 
		", C700020046 as Flag22IsSelect " + 
		", C700020034 as Flag30AuditingResult " + 
		", C700020035 as Flag31IsTransfer " + 
		", C700020052 as Flag32IsToTransfer " + 
		", C700020053 as Flag33IsEndPhase " + 
		", C700020077 as Flag34IsEndDuplicated " + 
		", C700020094 as Flag37IsNeedStartInsideFlow " + 
		", C700020095 as StartInsideFlowID " + 
		", C700020096 as StartInsideFlowName " + 
		", C700020081 as PhaseNoTakeMeActive " +
		", C700020065 as EdProcessAction " +
		", C700020092 as CustomActions " + 
		", C700020100 as StartInsideFlows" + 
		" from " + tablename + " where " + tmpadd + " order by C700020015 asc, C700020003 asc, C700020022 asc, C700020077 asc, C1 asc";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List tdpList = new ArrayList();
		try
		{
			while(rs.next())
			{
				TplDealProcessModel dpModel=new TplDealProcessModel();
				dpModel.setProcessID(rs.getString("ProcessID"));
				dpModel.setProcessGoLine(rs.getString("ProcessGoLine"));
				dpModel.setProcessBaseID(rs.getString("ProcessBaseID"));
				dpModel.setBaseStateCode(rs.getString("BaseStateCode"));
				dpModel.setProcessBaseSchema(rs.getString("ProcessBaseSchema"));
				dpModel.setBaseStateName(rs.getString("BaseStateName"));
				dpModel.setPhaseNo(rs.getString("PhaseNo"));
				dpModel.setDealer(rs.getString("Dealer"));
				dpModel.setDealerID(rs.getString("DealerID"));
				dpModel.setAssignee(rs.getString("Assignee"));
				dpModel.setAssgineeID(rs.getString("AssigneeID"));
				dpModel.setGroup(rs.getString("ProcessGroup"));
				dpModel.setGroupID(rs.getString("ProcessGroupID"));
				dpModel.setStDate(rs.getLong("StDate"));
				dpModel.setBgDate(rs.getLong("BgDate"));
				dpModel.setEdDate(rs.getLong("EdDate"));
				dpModel.setRoleOnlyID(rs.getString("RoleOnlyID"));
				dpModel.setRoleName(rs.getString("RoleName"));
				dpModel.setProcessStatus(rs.getString("ProcessStatus"));
				dpModel.setProcessType(rs.getString("ProcessType"));
				dpModel.setDesc(rs.getString("ProcessDesc"));
				dpModel.setActionName(rs.getString("ActionName"));
				dpModel.setActionPageName(rs.getString("ActionPageName"));
				dpModel.setActionPageID(rs.getString("ActionPageID"));
				dpModel.setFlagActive(rs.getInt("FlagActive"));
				dpModel.setFlagPredefined(rs.getInt("FlagPredefined"));
				dpModel.setFlagDuplicated(rs.getInt("FlagDuplicated"));
				dpModel.setPosX(rs.getInt("PosX"));
				dpModel.setPosY(rs.getInt("PosY"));
				dpModel.setAssignOverTimeDate_tmp(rs.getLong("AssignOverTimeDate_tmp"));
				dpModel.setAcceptOverTimeDate_tmp(rs.getLong("AcceptOverTimeDate_tmp"));
				dpModel.setDealOverTimeDate_tmp(rs.getLong("DealOverTimeDate_tmp"));
				dpModel.setFlag35IsCanCreateBase(rs.getInt("Flag35IsCanCreateBase"));
				dpModel.setFlag36IsCreateBase(rs.getInt("Flag36IsCreateBase"));
				dpModel.setFlagType(rs.getInt("FlagType"));
				dpModel.setFlag00IsAvail(rs.getInt("Flag00IsAvail"));
				dpModel.setFlag01Assign(rs.getInt("Flag01Assign"));
				dpModel.setFlag02Copy(rs.getInt("Flag02Copy"));
				dpModel.setFlag03Assist(rs.getInt("Flag03Assist"));
				dpModel.setFlag04Transfer(rs.getInt("Flag04Transfer"));
				dpModel.setFlag05TurnDown(rs.getInt("Flag05TurnDown"));
				dpModel.setFlag06TurnUp(rs.getInt("Flag06TurnUp"));
				dpModel.setFlag07Recall(rs.getInt("Flag07Recall"));
				dpModel.setFlag08Cancel(rs.getInt("Flag08Cancel"));
				dpModel.setFlag09Close(rs.getInt("Flag09Close"));
				dpModel.setFlag15ToAuditing(rs.getInt("Flag15ToAuditing"));
				dpModel.setFlag16ToAssistAuditing(rs.getInt("Flag16ToAssistAuditing"));
				dpModel.setFlag20SideBySide(rs.getInt("Flag20SideBySide"));
				dpModel.setFlag22IsSelect(rs.getInt("Flag22IsSelect"));
				dpModel.setFlag30AuditingResult(rs.getInt("Flag30AuditingResult"));
				dpModel.setFlag31IsTransfer(rs.getInt("Flag31IsTransfer"));
				dpModel.setFlag32IsToTransfer(rs.getInt("Flag32IsToTransfer"));
				dpModel.setFlag33IsEndPhase(rs.getInt("Flag33IsEndPhase"));
				dpModel.setFlag34IsEndDuplicated(rs.getInt("Flag34IsEndDuplicated"));
				dpModel.setFlag37IsNeedStartInsideFlow(rs.getInt("Flag37IsNeedStartInsideFlow"));
				dpModel.setStartInsideFlowID(rs.getString("StartInsideFlowID"));
				dpModel.setStartInsideFlowName(rs.getString("StartInsideFlowName"));
				dpModel.setEdProcessAction(rs.getInt("EdProcessAction"));
				dpModel.setPhaseNoTakeMeActive(rs.getString("PhaseNoTakeMeActive"));
				dpModel.setCustomActions(rs.getString("CustomActions"));
				dpModel.setStartInsideFlows(rs.getString("StartInsideFlows"));
				
				if(type.equals("MAP"))
				{
					DealProcessLog dpl = new DealProcessLog();
					dpModel.setLogList(dpl.getDealProcessLogList(dpModel.getProcessID()));
				}
				
				if(dpModel.getFlagDuplicated() == 0 || !type.equals("MAP"))
				{
					dpModel.addDuplicatedProcessModel((TplDealProcessModel)dpModel.clone());
					tdpList.add(dpModel);
				}
				else
				{
					for(Iterator it = tdpList.iterator(); it.hasNext();)
					{
						TplDealProcessModel tdpm = (TplDealProcessModel)it.next();
						if(tdpm.getPhaseNo().equals(dpModel.getPhaseNo()))
						{
							tdpm.addDuplicatedProcessModel(dpModel);
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
		return tdpList;
	}
}
