package cn.com.ultrapower.interfaces.server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.eoms.processSheet.InterfaceCfg;
import cn.com.ultrapower.eoms.processSheet.NMSAlarmInterface;
import cn.com.ultrapower.eoms.processSheet.XmlSplit;
import cn.com.ultrapower.eoms.processSheet.subScript.ChangeTypeUtil;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_New;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextCustomHavaMatchRole;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.ultrawf.share.OperationLogFile;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class InterSwitchAlarm extends AlarmBase {
	/**
	 * EOMS系统提供该接口服务用于接收需要自动或手工派发故障工单的告警信息
	 * @param serSupplier 服务提供方
	 * @param serCaller 服务调用方
	 * @param callerPwd 口令
	 * @param callTime 服务调用时间
	 * @param opDetail 详细信息
	 * @return
	 */
	public String newAlarm(String serSupplier,String serCaller,String callerPwd,String callTime,String opDetail){
		String sheetNo = "";
		String errList = "";
		String rev = "";
		//新建故障工单
		NMSAlarmInterface nmsAlarmInter = new NMSAlarmInterface(); 
		OperationLogFile.writeTxt("\r\n");
		OperationLogFile.writeTxt("\r\n--网管告警工单建单动作"
				+ FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "--");
		System.out.println(serSupplier + "调用派单服务接口开始");
		OperationLogFile.writeTxt("\r\n-------------BEGIN(建单参数)--------------");
		System.out.println(opDetail);
		OperationLogFile.writeTxt(opDetail);
		
		if (null != opDetail && opDetail.length() > 0) {// 将opDetail解析出来
			List<List<BaseFieldInfo>> list = prepareDate(
					serSupplier, serCaller, callerPwd, callTime,opDetail,Contents.NMSPROCESS,Contents.NEWALARM);
			
			if(list != null){
				try {
					for (int i = 0; i < list.size(); i++) {
						
						List<Map<String,String>> fieldlist = nmsAlarmInter.getBasefieldInfo(Contents.NEWALARM, Contents.ACTION);
						//新建
						Action_New action_New = new Action_New();
						
						//用于区分不同网管系统之间的建单
						List<BaseFieldInfo> l = list.get(i);
						for (int s=0;s<l.size();s++)
						{
							BaseFieldInfo p_FieldInfo = l.get(s);
							if("INC_SendUser".equals(p_FieldInfo.getStrFieldName())){
								if("SJWG".equals(p_FieldInfo.getStrFieldValue())){//数据网管
									list.get(0).add(new BaseFieldInfo(fieldlist.get(4).get(Contents.FIELDBMC) , fieldlist.get(4).get(Contents.FIELDCOLUMN),Contents.CREATORSJWG, 4));
								}else if("CSWG".equals(p_FieldInfo.getStrFieldValue())){//传输网管
									list.get(0).add(new BaseFieldInfo(fieldlist.get(4).get(Contents.FIELDBMC) , fieldlist.get(4).get(Contents.FIELDCOLUMN),Contents.CREATORCSWG, 4));
								}else if("POWER".equals(p_FieldInfo.getStrFieldValue())){//话务网管
									list.get(0).add(new BaseFieldInfo(fieldlist.get(4).get(Contents.FIELDBMC) , fieldlist.get(4).get(Contents.FIELDCOLUMN),Contents.CREATORPOWERWG, 4));
								}else{
									list.get(0).add(new BaseFieldInfo(fieldlist.get(4).get(Contents.FIELDBMC) , fieldlist.get(4).get(Contents.FIELDCOLUMN),getUserNameByUserLoginName(Contents.WGKF) , 4));
								}
								break;
							}
							if(s==l.size()-1){
								list.get(0).add(new BaseFieldInfo(fieldlist.get(4).get(Contents.FIELDBMC) , fieldlist.get(4).get(Contents.FIELDCOLUMN),getUserNameByUserLoginName(Contents.WGKF) , 4));
							}
						}
						
						list.get(0).add(new BaseFieldInfo(fieldlist.get(5).get(Contents.FIELDBMC) , fieldlist.get(5).get(Contents.FIELDCOLUMN), String.valueOf(System.currentTimeMillis()/1000), 7));
						list.get(0).add(new BaseFieldInfo(fieldlist.get(6).get(Contents.FIELDBMC) , fieldlist.get(6).get(Contents.FIELDCOLUMN),Contents.CREATORCOP , 4));
						list.get(0).add(new BaseFieldInfo(fieldlist.get(7).get(Contents.FIELDBMC) , fieldlist.get(7).get(Contents.FIELDCOLUMN), Contents.CREATORDEP, 4));
						boolean isSuccess = action_New.do_Action(Contents.WGKF, nmsAlarmInter.getAllBMCTable() ,null, list.get(i), null);
						String createType = getSpcialValue(list,Contents.CREATETYPE);
						if (createType != null && "1".equals(createType) && isSuccess) {
							return "sheetNo="+action_New.getM_BaseID() + ";errList=";
						}
						String flag = nmsAlarmInter.getMethodMap(Contents.NEWALARM).get(Contents.ISROLE);
						if("1".equals(flag)){
							if (createType != null && "0".equals(createType) && isSuccess) {//新建成功派发工单
								Action_NextCustomHavaMatchRole m_Action_NextCustomHavaMatchRole = new Action_NextCustomHavaMatchRole();
								if(m_Action_NextCustomHavaMatchRole.do_Action(
																			Contents.WGKF,
																			nmsAlarmInter.getAllBMCTable() ,
																			action_New.getM_BaseID(), 
																			null,
																			null, 
																			nmsAlarmInter.getMethodMap(Contents.NEWALARM).get(Contents.CHNAME),
																			nmsAlarmInter.getMethodMap(Contents.NEWALARM).get(Contents.ACTIONID),
																			new BaseFieldInfo(fieldlist.get(0).get(Contents.FIELDBMC) , fieldlist.get(0).get(Contents.FIELDCOLUMN),"", 4),
																			new BaseFieldInfo(fieldlist.get(1).get(Contents.FIELDBMC) , fieldlist.get(1).get(Contents.FIELDCOLUMN),"", 4), 
																			new BaseFieldInfo(fieldlist.get(2).get(Contents.FIELDBMC) , fieldlist.get(2).get(Contents.FIELDCOLUMN), "", 4),
																			new BaseFieldInfo(fieldlist.get(3).get(Contents.FIELDBMC) , fieldlist.get(3).get(Contents.FIELDCOLUMN), "", 4), 
																			list.get(i),
																			null)){
									return "sheetNo="+action_New.getM_BaseID() + ";errList=";
								}else{
									return "error";
								}
							}
						}else{//不需要匹配角色
							if (createType != null && "0".equals(createType) && isSuccess) {//新建成功派发工单
								Action_NextCustomHavaMatchRole m_Action_NextCustomHavaMatchRole = new Action_NextCustomHavaMatchRole();
								if(m_Action_NextCustomHavaMatchRole.do_Action(
																			Contents.WGKF,
																			nmsAlarmInter.getAllBMCTable() ,
																			action_New.getM_BaseID(), 
																			null,
																			null, 
																			nmsAlarmInter.getMethodMap(Contents.NEWALARM).get(Contents.CHNAME),
																			list.get(i),
																			null)){
									return "sheetNo="+action_New.getM_BaseID() + ";errList=";
								}else{
									return "error";
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "error";
	}
	
	
	/**
	 * 告警同步
	 * @param serSupplier 服务提供方
	 * @param serCaller 服务调用方
	 * @param callerPwd 口令
	 * @param callTime 服务调用时间
	 * @param opDetail 详细信息
	 * @return
	 */
	public String syncAlarm(String serSupplier,String serCaller,String callerPwd,String callTime,String opDetail){
		OperationLogFile.writeTxt("\r\n--网管告警状态同步调用开始----");
		InterfaceCfg intcfg = new InterfaceCfg();
		String rev = "";
		List listcfg = intcfg.getElements(Contents.NMSPROCESS, Contents.NMSsyncAlarm);
		
		XmlSplit xmlSplit = new XmlSplit();
		List listOpDetail = xmlSplit.getDatatoList(opDetail, Contents.NMSPROCESS,Contents.NMSsyncAlarm);
		
		NMSAlarmInterface nmsAlarmInter = new NMSAlarmInterface();
		
		String upSql = getSyncSql(nmsAlarmInter.getAllBMCTable(),listcfg,listOpDetail);
		
		Statement stm=null;
		ResultSet resultSet =null;
		String type = "";
		int iii = 0;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try {
			stm = m_dbConsole.GetStatement();
			iii = m_dbConsole.executeNonQuery(stm,upSql);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		
		
		String colAlarm = "";
		for(int i=0;i<listcfg.size();i++)
		{
			Map<String,String> map = (Map)listcfg.get(i);
			if(map.get(Contents.FIELDBMC).equals(Contents.SynalarmId))
			{
				colAlarm = "C"+map.get(Contents.FIELDCOLUMN);
				break;
			}
		}
		String sheetCol ="C"+ nmsAlarmInter.getMethodMap(Contents.NMSsyncAlarm).get(Contents.SynsheetNoColumn);
			
		String sheetNo = ChangeTypeUtil.getStrByNo(nmsAlarmInter.getAllBMCTable(),sheetCol,colAlarm, getSpcialValue(listOpDetail,Contents.SynalarmId));
		
		if(iii>0)
			rev = "sheetNo="+sheetNo+";errList=";
		else
			rev = "sheetNo=;errList=未找到对应的工单";
		
		return rev;
	}
	
	private String getSyncSql(String tblName,List<Map<String,String>> listcfg,List<List<BaseFieldInfo>> listOpDetail)
	{
		String sql = "";
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(tblName);
		sql = "update "+strTblName+ " set ";
		Boolean flag = false;
		for(int i=0;i<listcfg.size();i++)
		{
			Map<String,String> map = listcfg.get(i);
			if(map.get(Contents.FIELDBMC).equals(Contents.SynalarmStatus))
			{
				if(!flag)
				{
					sql += " C"+map.get(Contents.FIELDCOLUMN);
					flag = true;
				}else
				{
					sql += " ,C"+map.get(Contents.FIELDCOLUMN);
				}
				sql += "='";
				for(int j=0;j<listOpDetail.get(0).size();j++)
				{
					BaseFieldInfo baseInfo = (BaseFieldInfo)listOpDetail.get(0).get(j);
					if(baseInfo!=null && baseInfo.getStrFieldName().equals(Contents.SynalarmStatus))
					{
						sql += baseInfo.getStrFieldValue();
					}		
				}
				sql += "'";

			}
			
			if(map.get(Contents.FIELDBMC).equals(Contents.SynclearTime))
			{
				if(!flag)
				{
					sql += " C"+map.get(Contents.FIELDCOLUMN);
					flag = true;
				}else
				{
					sql += " ,C"+map.get(Contents.FIELDCOLUMN);
				}
				sql += "='";
				for(int j=0;j<listOpDetail.get(0).size();j++)
				{
					BaseFieldInfo baseInfo = (BaseFieldInfo)listOpDetail.get(0).get(j);
					if(baseInfo!=null && baseInfo.getStrFieldName().equals(Contents.SynclearTime))
					{
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							sql += sf.parse(baseInfo.getStrFieldValue()).getTime()/1000;
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}					
				}
				sql += "'";

			}
		}
			
		for(int i=0;i<listcfg.size();i++)
		{
			Map<String,String> map = listcfg.get(i);
			if(map.get(Contents.FIELDBMC).toString().equals(Contents.SynalarmId))
			{
				
				sql += " where C"+map.get(Contents.FIELDCOLUMN) + "='";
				
				for(int j=0;j<listOpDetail.get(0).size();j++)
				{
					BaseFieldInfo baseInfo = (BaseFieldInfo)listOpDetail.get(0).get(j);
					if(baseInfo!=null && baseInfo.getStrFieldName().equals(Contents.SynalarmId))
					{
						sql += baseInfo.getStrFieldValue();
					}		
				}
				sql += "'";
			}
		}
		return sql;
	}
}
