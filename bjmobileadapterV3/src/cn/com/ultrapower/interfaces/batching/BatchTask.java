package cn.com.ultrapower.interfaces.batching;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Finish;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextBaseCustom;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.sqlquery.*;
import cn.com.ultrapower.system.table.*;

public class BatchTask extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		boolean istrue_send=false;
		boolean strNotice = true;
		 //获取工单From
		String Action =FormatString.CheckNullString(request.getParameter("NowAction"));
		String ActionFinish=FormatString.CheckNullString(request.getParameter("ActionFinish"));
		String ids=FormatString.CheckNullString(request.getParameter("ids"));
		String status=FormatString.CheckNullString(request.getParameter("status"));
		String BaseSchema=FormatString.CheckNullString(request.getParameter("schema"));
		String BaseCreatorLoginName=FormatString.CheckNullString(request.getParameter("userLoginName"));
		String NextTache="";
		Row row=null;
		System.out.println("Action: "+Action+" || status :"+status+" || schema:"+BaseSchema+" ||BaseCreatorLoginName :"+BaseCreatorLoginName);
		List<BaseFieldInfo> BaseExpFields = new ArrayList<BaseFieldInfo>();
		if(ActionFinish.equals("")){
			ActionFinish="1"; //0自由|| 1固定
		}
		String recordID="";
		String BaseAssigneeSName="";
		String BaseAssigneeS="";
		String GroupID="";
		String GroupName="";
		String AssgineeID="";
		String AssgineeName=""; 
	    String [] stringIDS=ids.split(";");
		String baseSN = "";
		String baseTplID = "";
		String con_creator = "";
		String con_creators = "";
		List<String> cre_alarm = new ArrayList<String>();
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		List<String> undo_baseid = new ArrayList<String>();
		for(int i=0;i<stringIDS.length;i++){
			if(i==stringIDS.length-1){
				con_creator = con_creator+" c1='"+stringIDS[i].substring(0,stringIDS[i].indexOf("-"))+"' ";
			}else{
				con_creator = con_creator+" c1='"+stringIDS[i].substring(0,stringIDS[i].indexOf("-"))+"' or";
			}
		}
		if(!con_creator.equals("")){
			con_creators = " and ("+con_creator+") ";
		}
		//根据BaseID查询建单人登录名
		RDParameter m_p_rDParameter = new RDParameter();
		m_p_rDParameter.addIndirectPar("con_baseid", con_creators, 4);
		String m_cacheid = "SQL_findcreatorbybaseid";
		RowSet m_rowSet = null;
		ROperation m_rOperation = new ROperation(m_cacheid, request,
				response, m_p_rDParameter);
		m_rOperation.isCache = false;//是否缓存数据
		m_rOperation.cacheId = m_cacheid;
		m_rOperation.setCacheMode(2);//数据缓存模式 1: 当前页数据  2： 缓存sql方式
		m_rOperation.isCreateHtml = false;//是否生成html true 为是  false 为否
		m_rOperation.init();//初始化    	
		m_rowSet = m_rOperation.m_RTable.getRowSet();
		
		Row objRows;
  		int rowLengths=0;
  		if(m_rowSet!=null)
  		  rowLengths=m_rowSet.length();
  		for(int rows=0;rows<rowLengths;rows++)
  		{
  			objRows  = m_rowSet.get(rows);
  			cre_alarm.add(objRows.getString("basecreator"));
  			cre_alarm.add(objRows.getString("INC_Alarm_ClearTime"));
  			map.put(objRows.getString("id"), cre_alarm);
  		}
	    
	    //status 1一级处理中|| 2二级处理中 || 3三级处理中 ||  Action 4处理完成待归档
		for(int i=0; i<stringIDS.length;i++){
			String strIDS=stringIDS[i];
			String BaseID=strIDS.substring(0,strIDS.indexOf("-"));
			String ProcessID=strIDS.substring(strIDS.indexOf("-")+1,strIDS.length());
			String p_ProcessType="DEAL";
			System.out.println("BaseID: "+BaseID+"  ProcessID "+ProcessID);
			//c700000008 as BaseFinishDate,c700000009 as BaseCloseDate
			//c700000017 as BaseAcceptOutTime,c700000018  as BaseDealOutTime 
			row=new SchemaAction().getSchemaInfo(BaseSchema, BaseID);
			long lBaseFinishDate=0;
			long lBaseCloseDate=0;
			long  lBaseAcceptOutTime=0;
			long  lBaseDealOutTime=0;
			long  lBaseSendDate=0;
			if(row!=null){
				 lBaseFinishDate=row.getlong("BaseFinishDate"); 
				 lBaseCloseDate=row.getlong("BaseCloseDate");
				 String BaseStatus=row.getString("BaseStatus");
				 lBaseAcceptOutTime=row.getlong("BaseAcceptOutTime");
				 lBaseDealOutTime=row.getlong("BaseDealOutTime");
				 lBaseSendDate=row.getlong("BaseSendDate");
				 baseSN = row.getString("BaseSN");
				 baseTplID =row.getString("BaseTplID");
			}

			if(lBaseFinishDate==-1 || lBaseFinishDate==0){
				BaseExpFields.add(new BaseFieldInfo("BaseFinishDate","c700000008",null,7));
			}
			if(lBaseCloseDate==-1 || lBaseCloseDate==0){
				BaseExpFields.add(new BaseFieldInfo("BaseCloseDate","c700000009",null,7));
			}
			
			if(!Action.equals("2")){
				//处理派发人

			    BaseAssigneeSName=FormatString.CheckNullString(request.getParameter("BaseAssigneeSNameFix"));
				BaseAssigneeS=FormatString.CheckNullString(request.getParameter("BaseAssigneeSFix"));
				String haveRole = "no";
				
				//System.out.println("BaseAssigneeS："+BaseAssigneeS);
				if(Action.equals("0")){
					//解析固定派发人或派发组
					
					String[] assigns = BaseAssigneeS.split(";");
					System.out.println("assigns.length："+assigns.length);
					//if(assigns.length >1){
					if(!BaseAssigneeS.equals("") && !BaseAssigneeS.equals("null")){
						haveRole = "yes";
						
						for(int n =0;n<assigns.length;n++){
							if((assigns[n].split("#"))[0].equals("GroupID")){
								GroupID=(assigns[n].split("#"))[1];	
								GroupName=BaseAssigneeSName.substring(0, BaseAssigneeSName.indexOf(";"));
							}else{
								AssgineeID=(assigns[n].split("#"))[1];
								AssgineeName=BaseAssigneeSName.substring(0, BaseAssigneeSName.indexOf(";"));
							}
						}
					}
					System.out.println("GroupID:"+ GroupID+" || GroupName: "+GroupName  +" ||AssgineeID: "+AssgineeID+" || AssgineeName: "+AssgineeName);
					if(status.equals("1")){
					//if(status.equals("一级处理中")){
						NextTache="dp_4";
						/*	
						是否涉及安全       800030101     INC_T1IsDealWithIOT           Selection        0否|1是
						是否涉及互联互通   800030102     INC_T1IsDealWithSafety       Selection        0否|1是
						是否重大故障       800030106     IsImportantIncident	       Selection        0否|1是
						故障初步处理情况： 800030104     INC_T1PrimaryDealDesc        Character500     
						故障描述：         800030105     INC_FinishDealDesc           Character3500   
						T1移交理由：      800030132     INC_TDealDesc                 Character500  
					    T2处理人：       800045107     tmp_INC_T1_ToUser    Character254   Display only
						T2处理人ID：     800045108     tmp_INC_T1_ToUserID   Character254   Display only
						T2处理组：       800045109     tmp_INC_T1_ToGroup    Character254   Display only
						T2处理组ID：     800045110     tmp_INC_T1_ToGroupID  Character254   Display only
						*/
						String INC_T1IsDealWithIOT=FormatString.CheckNullString(request.getParameter("INC_T1IsDealWithIOT"));
						String INC_T1IsDealWithSafety=FormatString.CheckNullString(request.getParameter("INC_T1IsDealWithSafety"));
						String IsImportantIncident=FormatString.CheckNullString(request.getParameter("IsImportantIncident"));
						String INC_T1PrimaryDealDesc=FormatString.CheckNullString(request.getParameter("INC_T1PrimaryDealDesc"));
						String INC_FinishDealDesc=FormatString.CheckNullString(request.getParameter("INC_FinishDealDesc"));
						String INC_TDealDesc=FormatString.CheckNullString(request.getParameter("INC_TDealDesc"));
						BaseExpFields.add(new BaseFieldInfo("INC_T1IsDealWithIOT","800030101",INC_T1IsDealWithIOT,4));
						BaseExpFields.add(new BaseFieldInfo("INC_T1IsDealWithSafety","800030102",INC_T1IsDealWithSafety,4));
						BaseExpFields.add(new BaseFieldInfo("IsImportantIncident","800030106",IsImportantIncident,4));
						BaseExpFields.add(new BaseFieldInfo("INC_T1PrimaryDealDesc","800030104",INC_T1PrimaryDealDesc,4));
						BaseExpFields.add(new BaseFieldInfo("INC_FinishDealDesc","800030105",INC_FinishDealDesc,4));
						BaseExpFields.add(new BaseFieldInfo("INC_TDealDesc","800030132",INC_TDealDesc,4));
						BaseExpFields.add(new BaseFieldInfo("INC_T1Result","800021001","",4));
						BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200",INC_T1PrimaryDealDesc,4));
						
						
						//固定处理移交T2派发处理人
						if(!BaseAssigneeS.equals("")){
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T1_ToUser","800045107",AssgineeName,4));
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T1_ToUserID","800045108",AssgineeID,4));
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T1_ToGroup","800045109",GroupName,4));
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T1_ToGroupID","800045110",GroupID,4));
						}
						
					}else if(status.equals("2")){
					//}else if(status.equals("二级处理中")){
						NextTache="dp_6";
						/*	
						是否重大故障：   800030107       T2_IsImportantIncident       Selection        0否|1是
						T2移交理由：     800030133       INC_TDealDesc2               Character500
						故障处理情况：   800030202       INC_T2FinishDealDesc         Character500
						T3处理人：       800045205      tmp_INC_T2_ToUser    Character254   Display only
						T3处理人ID：     800045206      tmp_INC_T2_ToUserID   Character254   Display only
						T3处理组：       800045207      tmp_INC_T2_ToGroup    Character254   Display only
						T3处理组ID：     800045208      tmp_INC_T2_ToGroupID  Character254   Display only
						*/
						String T2_IsImportantIncident=FormatString.CheckNullString(request.getParameter("T2_IsImportantIncident"));
						String INC_TDealDesc2=FormatString.CheckNullString(request.getParameter("INC_TDealDesc2"));
						String INC_T2FinishDealDesc=FormatString.CheckNullString(request.getParameter("INC_T2FinishDealDesc"));
						BaseExpFields.add(new BaseFieldInfo("T2_IsImportantIncident","800030107",T2_IsImportantIncident,4));
						BaseExpFields.add(new BaseFieldInfo("INC_TDealDesc2","800030133",INC_TDealDesc2,4));
						BaseExpFields.add(new BaseFieldInfo("INC_T2FinishDealDesc","800030202",INC_T2FinishDealDesc,4));
						BaseExpFields.add(new BaseFieldInfo("INC_T2Result","800021002","",4));
						BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200",INC_TDealDesc2,4));
						
						//固定处理移交T3派发处理人
						if(!BaseAssigneeS.equals("")){
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T2_ToUser","800045205",AssgineeName,4));
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T2_ToUserID","800045206",AssgineeID,4));
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T2_ToGroup","800045207",GroupName,4));
							BaseExpFields.add(new BaseFieldInfo("tmp_INC_T2_ToGroupID","800045208",GroupID,4));
						}
					}
					istrue_send=new SchemaAction().getFixationAssagin(BaseCreatorLoginName, BaseSchema,NextTache, BaseID, BaseExpFields,ProcessID,p_ProcessType,haveRole,baseTplID);
				}//if(Action.equals("0"))
				else if(Action.equals("1")){
				    BaseAssigneeSName=FormatString.CheckNullString(request.getParameter("BaseAssigneeSName"));
					BaseAssigneeS=FormatString.CheckNullString(request.getParameter("BaseAssigneeS"));
					/*自由派发
					是否重大故障：    800030108      oDeal_IsImportantIncident        Selection      0否|1是
					故障处理情况：    800030120      ToDealDesc			  Character500  Optional
					分派理由：        800030121      ToDealResult     
					*/
					String oDeal_IsImportantIncident=FormatString.CheckNullString(request.getParameter("oDeal_IsImportantIncident"));
					String ToDealDesc=FormatString.CheckNullString(request.getParameter("ToDealDesc"));
					String ToDealResult=FormatString.CheckNullString(request.getParameter("ToDealResult"));
					BaseExpFields.add(new BaseFieldInfo("oDeal_IsImportantIncident","800030108",oDeal_IsImportantIncident,4));//原为800030101
					BaseExpFields.add(new BaseFieldInfo("ToDealDesc","800030120",ToDealDesc,4));
					BaseExpFields.add(new BaseFieldInfo("ToDealResult","800030121",ToDealResult,4));
					//BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200",ToDealDesc,4));
					istrue_send=new SchemaAction().getAssagin(	BaseCreatorLoginName, 
																BaseSchema, 
																BaseID, 
																BaseExpFields, 
																BaseAssigneeS,
																BaseAssigneeSName,
																ProcessID,
																p_ProcessType,
																lBaseAcceptOutTime,
					 											lBaseDealOutTime);
				}
				/*
				故障设备厂商：  800020017   INC_EquipmentManufacturer  
				故障设备类型：  800020016   INC_EquipmentType
				故障原因类别：  800030002   INC_ReasonType	
				
				故障原因分类    800030163   INC_CloseINCReason
				最终网络分类：  800030164   INC_CloseNetType
				最终设备厂家：  800030165  INC_CloseEquipment*/
				
				//待归档动作
				else if(Action.equals("4")){
					String BaseCloseSatisfy=FormatString.CheckNullString(request.getParameter("BaseCloseSatisfy"));
					String tmp_BaseUser_P_OpDealNext_Desc=FormatString.CheckNullString(request.getParameter("tmp_BaseUser_P_OpDealNext_Desc"));
					BaseExpFields.add(new BaseFieldInfo("INC_CloseAuditingDealResult","800021003","归档工单",4));
					BaseExpFields.add(new BaseFieldInfo("BaseCloseSatisfy","700000021",BaseCloseSatisfy,4));
					BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200",tmp_BaseUser_P_OpDealNext_Desc,4));
					//如果派单时间为空赋值为null
					if(lBaseSendDate==0 || lBaseSendDate==-1){
						BaseExpFields.add(new BaseFieldInfo("BaseSendDate","c700000007",null,7));
					}
					/*if(row!=null){
						BaseExpFields.add(new BaseFieldInfo("INC_CloseINCReason","800030163",row.getString("INC_ReasonType"),4));
						BaseExpFields.add(new BaseFieldInfo("INC_CloseNetType","800030164",row.getString("BaseItems"),4));
						BaseExpFields.add(new BaseFieldInfo("INC_CloseEquipment","800030165",row.getString("INC_EquipmentManufacturer"),4));
					}*/
					istrue_send=new SchemaAction().getClsoeSchema(BaseCreatorLoginName, BaseSchema, BaseID, BaseExpFields,ProcessID,p_ProcessType);
				}
				//受理动作
				else if(Action.equals("5")){			
					istrue_send=new SchemaAction().getConfirm(BaseCreatorLoginName, BaseSchema, BaseID, BaseExpFields,ProcessID,p_ProcessType);
					istrue_send=true;
				}
				//抄送确认
				else if(Action.equals("6")){
					String tmp_BaseUser_P_OpConfirm_Desc=FormatString.CheckNullString(request.getParameter("tmp_BaseUser_P_OpConfirm_Desc"));
					BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpConfirm_Desc","700038482",tmp_BaseUser_P_OpConfirm_Desc,4));
					
					istrue_send=new SchemaAction().getFreeConfirm(BaseCreatorLoginName, BaseSchema, BaseID, BaseExpFields,ProcessID,p_ProcessType);
					
				}
			}
			else
			{
				String Come_ClearINCTime = "";
				String Come_ClearOPTime = "";
				//当前时间
		        Date current = new Date();
				SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentime = dateFormat.format(current);
				//需求变更加判断
				List<String> cre_alarmtime = map.get(BaseID);  //建单人登录名
				if(cre_alarmtime.get(0).equals("WGKF")){
					if(cre_alarmtime.get(1).equals("")){
						//返回工单号，不做处理
						undo_baseid.add(baseSN);
						strNotice = false;
						continue;
					}else{
						Come_ClearINCTime = cre_alarmtime.get(1);
						Come_ClearOPTime = currentime;
					}
				}else{
					if(cre_alarmtime.get(1).equals("")){
						Come_ClearINCTime = FormatTime.FormatDateStringToInt(currentime)+"";
						Come_ClearOPTime = currentime;
					}else{
						Come_ClearINCTime = cre_alarmtime.get(1);
						Come_ClearOPTime = currentime;
					}
				}
				//0自由完成||1固定完成
				if(ActionFinish.equals("0")){
					/*
					故障原因类别：   800030122    Come_ReasonType                Character254    Optional   
					故障原因细分：   800030123    Come_ReasonSubType             Character254    Optional 
					是否重大故障：   800030109    Come_IsImportantIncident       Selection       Optional 
					处理措施：       800030124    Come_DealGuomodo               Character254    Optional 
					故障消除时间：   800030125    Come_ClearINCTime              Date/Time       Optional 
					业务恢复时间：   800030126    Come_ClearOPTime               Date/Time       Optional 
					影响业务时长：   800030127    Come_TakeTime                  Character254    Optional 
					处理说明：       700038199    tmp_BaseUser_P_OpDeal_Desc     Character3500   Display only

					申请说明：       800080040       INC_ApplyDesc                Character254   Optional
					*/	
					String Come_ReasonType=FormatString.CheckNullString(request.getParameter("Come_ReasomType"));
					String Come_ReasonSubType=FormatString.CheckNullString(request.getParameter("Come_ReasonSubType"));
					String Come_IsImportantIncident=FormatString.CheckNullString(request.getParameter("Come_IsImportantIncident"));
					String Come_DealGuomodo=FormatString.CheckNullString(request.getParameter("Come_DealGuomodo"));
					String Come_TakeTime=FormatString.CheckNullString(request.getParameter("Come_TakeTime"));
					String tmp_BaseUser_P_OpDeal_Desc=FormatString.CheckNullString(request.getParameter("tmp_BaseUser_P_OpDeal_Desc"));
					
					BaseExpFields.add(new BaseFieldInfo("Come_ReasonType","800030122",Come_ReasonType,4));
					BaseExpFields.add(new BaseFieldInfo("Come_ReasonSubType","800030123",Come_ReasonSubType,4));
					BaseExpFields.add(new BaseFieldInfo("Come_IsImportantIncident","800030109",Come_IsImportantIncident,4));
					BaseExpFields.add(new BaseFieldInfo("Come_DealGuomodo","800030124",Come_DealGuomodo,4));
					BaseExpFields.add(new BaseFieldInfo("Come_ClearINCTime","800030125",Come_ClearINCTime,7));
					BaseExpFields.add(new BaseFieldInfo("Come_ClearOPTime","800030126",FormatTime.FormatDateStringToInt(Come_ClearOPTime)+"",7));
					BaseExpFields.add(new BaseFieldInfo("Come_TakeTime","800030127",Come_TakeTime,4));
					BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpDeal_Desc","700038199",tmp_BaseUser_P_OpDeal_Desc,4));

					
					istrue_send=new Action_Finish().do_Action(BaseCreatorLoginName, BaseSchema, BaseID, ProcessID, p_ProcessType, BaseExpFields, null);

				}else{
					/*				
					故障处理结果：   800030001     INC_DealResult                Character254      Optional   
					故障原因类别：   800030002     INC_ReasonType                Character254      Optional  
					故障原因细分：   800030003     INC_ReasonSubType             Character254      Optional				
					是否重大故障     800030110     Inc_IsImportantIncident        Selection        0否|1是
					处理措施：       800030004     INC_DealGuomodo               Character254      Optional
					是否实施变更：   800030005     INC_IsDealNetChange           Character254      Optional
					是否为最终解决方案 800030006   INC_IsFinallyPlan             Selection         0否|1是
					是否申请入案例库   800030007   INC_IsApplyCaseBase           Selection         0否|1是
					故障消除时间：    800030008    INC_ClearINCTime              Date/Time         Optional
					业务恢复时间：    800030009     INC_ClearOPTime              Date/Time         Optional
					影响业务时长：    800030010     INC_TakeTime                 Character254      Optional
				    */
					String INC_DealResult=FormatString.CheckNullString(request.getParameter("INC_DealResult"));
					String INC_ReasonType=FormatString.CheckNullString(request.getParameter("INC_ReasonType"));
					String INC_ReasonSubType=FormatString.CheckNullString(request.getParameter("INC_ReasonSubType"));
					String Inc_IsImportantIncident=FormatString.CheckNullString(request.getParameter("Inc_IsImportantIncident"));
					String INC_DealGuomodo=FormatString.CheckNullString(request.getParameter("INC_DealGuomodo"));
					String INC_IsDealNetChange=FormatString.CheckNullString(request.getParameter("INC_IsDealNetChange"));
					String INC_IsFinallyPlan=FormatString.CheckNullString(request.getParameter("INC_IsFinallyPlan"));
					String INC_IsApplyCaseBase=FormatString.CheckNullString(request.getParameter("INC_IsApplyCaseBase"));
					String INC_TakeTime=FormatString.CheckNullString(request.getParameter("INC_TakeTime"));
					String INC_EndNetType=FormatString.CheckNullString(request.getParameter("tmp_INC_EndNetType"));
					//String INC_EndEquipment=FormatString.CheckNullString(request.getParameter("tmp_INC_EndEquipment"));
					String tmp_INC_FinishDealDesc=FormatString.CheckNullString(request.getParameter("tmp_INC_FinishDealDesc"));
					
					//if(row!=null){
					//	BaseExpFields.add(new BaseFieldInfo("tmp_INC_EndNetType","800030161",row.getString("BaseItems"),4));
						//BaseExpFields.add(new BaseFieldInfo("tmp_INC_EndEquipment","800030162",row.getString("INC_EquipmentManufacturer"),4));
					//}
					
					BaseExpFields.add(new BaseFieldInfo("INC_DealResult","800030001",INC_DealResult,4));
					BaseExpFields.add(new BaseFieldInfo("INC_ReasonType","800030002",INC_ReasonType,4));
					BaseExpFields.add(new BaseFieldInfo("INC_ReasonSubType","800030003",INC_ReasonSubType,4));
					BaseExpFields.add(new BaseFieldInfo("Inc_IsImportantIncident","800030110",Inc_IsImportantIncident,4));
					BaseExpFields.add(new BaseFieldInfo("INC_DealGuomodo","800030004",INC_DealGuomodo,4));
					BaseExpFields.add(new BaseFieldInfo("INC_IsDealNetChange","800030005",INC_IsDealNetChange,4));
					BaseExpFields.add(new BaseFieldInfo("INC_IsFinallyPlan","800030006",INC_IsFinallyPlan,4));
					BaseExpFields.add(new BaseFieldInfo("INC_IsApplyCaseBase","800030007",INC_IsApplyCaseBase,4));
					BaseExpFields.add(new BaseFieldInfo("INC_ClearINCTime","800030008",Come_ClearINCTime,7));
					BaseExpFields.add(new BaseFieldInfo("INC_ClearOPTime","800030009",FormatTime.FormatDateStringToInt(Come_ClearOPTime)+"",7));
					BaseExpFields.add(new BaseFieldInfo("INC_TakeTime","800030010",INC_TakeTime,4));					
					//BaseExpFields.add(new BaseFieldInfo("tmp_INC_EndNetType","800030161",INC_EndNetType,4));
					//BaseExpFields.add(new BaseFieldInfo("tmp_INC_EndEquipment","800030162",INC_EndEquipment,4));
					BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200",tmp_INC_FinishDealDesc,4));
					
					if(status.equals("1")){
						BaseExpFields.add(new BaseFieldInfo("INC_T1Result","800021001","完成处理",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_Phase","800021004","一级处理完成",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_PhaseNO","800021005","dp_2",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_ProcessID","800021006",ProcessID,4));
						BaseExpFields.add(new BaseFieldInfo("BaseFinishDate","700000008",System.currentTimeMillis()/1000+"",7));
					}else if(status.equals("2")){
						BaseExpFields.add(new BaseFieldInfo("INC_T2Result","800021002","完成处理",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_Phase","800021004","二级处理完成",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_PhaseNO","800021005","dp_4",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_ProcessID","800021006",ProcessID,4));
						BaseExpFields.add(new BaseFieldInfo("BaseFinishDate","700000008",System.currentTimeMillis()/1000+"",7));
						
						
					}else{
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_ProcessID","800021004","三级处理完成",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_PhaseNO","800021005","dp_6",4));
						BaseExpFields.add(new BaseFieldInfo("INC_Finish_ProcessID","800021006",ProcessID,4));
						BaseExpFields.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200",tmp_INC_FinishDealDesc,4));//三级处理描述
						BaseExpFields.add(new BaseFieldInfo("BaseFinishDate","700000008",System.currentTimeMillis()/1000+"",7));
					}
					istrue_send=new Action_NextBaseCustom().do_Action(
																		BaseCreatorLoginName, 
																		BaseSchema, 
																		BaseID, 
																		ProcessID, 
																		p_ProcessType,
																		"完成处理", 
																		BaseExpFields,
																		null);
					
				}
			}
			
			
			if(istrue_send==true){
				new SchemaAction().getUpdateOpera( BaseSchema, BaseID);
				//recordID+=baseSN+"&nbsp;&nbsp;&nbsp;处理成功<br>";
			}
			else{
				strNotice = false;
				recordID+=baseSN+"<br>";
			}
			
		}

			System.out.println("recordID: "+recordID);
            String undo_xml = "";
            if(undo_baseid.size()>0){
            	for(int i=0;i<undo_baseid.size();i++){
            		undo_xml = undo_xml +undo_baseid.get(i)+"<br>";
            	}
            }
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			StringBuffer str = new StringBuffer();
			
			str.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			str.append("<HTML> <HEAD><TITLE> 处理结果</TITLE></HEAD> <BODY>");
			if(strNotice==true && undo_baseid.size()==0){
				str.append("<div align=\'center\' style=\'font-size: 16px;font-weight: bold;\'><br> 操作成功！<br><br>");
			}else if(strNotice==true && undo_baseid.size()>0){
				str.append("<div align=\'center\' style=\'font-size: 16px;font-weight: bold;\'><br> 操作成功！有以下话务网管系统建单，告警清除时间为空，不处理!<br><br>");
				str.append(undo_xml);
			}else if(strNotice==false && undo_baseid.size()==0){
				str.append("<div align=\'center\' style=\'font-size: 16px;font-weight: bold;\'><br>操作完成，有以下工单处理失败，工单流水号如下，请联系管理员!<br><br>");
				str.append(recordID);
			}else if(strNotice==false && undo_baseid.size()>0 && !recordID.equals("")){
				str.append("<div align=\'center\' style=\'font-size: 16px;font-weight: bold;\'><br>操作完成，有以下工单处理失败，工单流水号如下，请联系管理员!<br><br>");
				str.append(recordID);
				str.append("有以下话务网管系统建单，告警清除时间为空，不处理!<br><br>");
				str.append(undo_xml);
			}else if(strNotice==false && undo_baseid.size()>0 && recordID.equals("")){
				str.append("<div align=\'center\' style=\'font-size: 16px;font-weight: bold;\'><br>有以下话务网管系统建单，告警清除时间为空，不处理!<br><br>");
				str.append(undo_xml);
			}
			
			str.append(" <P><a href=\'#\' onclick=\' window.opener.location.href=window.opener.location.href;;window.close()\'>关闭窗口</a></div>");
			str.append(" </BODY>  </HTML>");

			out.println(str);
			out.flush();
			out.close();		

	}

}
