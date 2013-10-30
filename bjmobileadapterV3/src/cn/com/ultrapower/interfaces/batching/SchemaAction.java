package cn.com.ultrapower.interfaces.batching;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseToDealObject;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Close;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextBaseCustom;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextCustomHavaMatchRole;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Start;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_ToDeal;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Confirm;
import cn.com.ultrapower.ultrawf.share.FormatString;

public class SchemaAction {

	
	//派发工单
	public boolean getFixationAssagin(String BaseCreatorLoginName, String BaseSchema,String NextTache,String BaseID,List<BaseFieldInfo> AssingFieldlist,
			String p_ProcessID,String p_ProcessType,String haveRole,String baseTplID)
	{
		boolean istrue_send=false;
		
		//查询是否带角色
		String RoleProcessRoleType="";
		if(haveRole.equals("no")){
			Row row=getQueryRole(BaseSchema,NextTache,BaseID,baseTplID);
			if(row!=null){
				 RoleProcessRoleType=row.getString("RoleProcessRoleType");
			}
		}
		
		BaseFieldInfo assgineeIDField = null;
		BaseFieldInfo assgineeField = null;
		BaseFieldInfo groupIDField = null;
		BaseFieldInfo groupField = null;
		if(RoleProcessRoleType.equals("2")){
			
			if(NextTache.equals("dp_4")){
			assgineeIDField=new BaseFieldInfo("tmp_INC_T1_ToUserID","800045108","",4);
			assgineeField=new BaseFieldInfo("tmp_INC_T1_ToUser","800045107","",4);
			groupIDField=new BaseFieldInfo("tmp_INC_T1_ToGroupID","800045110","",4);
			groupField=new BaseFieldInfo("tmp_INC_T1_ToGroup","800045109","",4);
			}
			
			if(NextTache.equals("dp_6")){
				assgineeIDField=new BaseFieldInfo("tmp_INC_T2_ToUserID","800045206","",4);
				assgineeField=new BaseFieldInfo("tmp_INC_T2_ToUser","800045205","",4);
				groupIDField=new BaseFieldInfo("tmp_INC_T2_ToGroupID","800045208","",4);
				groupField=new BaseFieldInfo("tmp_INC_T2_ToGroup","800045207","",4);
				}
			
			//带角色的固定流程派发
			istrue_send = new Action_NextCustomHavaMatchRole().do_Action(
					BaseCreatorLoginName, 
					BaseSchema, 
					BaseID, 
					p_ProcessID, 
					p_ProcessType, 
					"移交处理", 
					NextTache,
					assgineeIDField,
					assgineeField,
					groupIDField,
					groupField,
					AssingFieldlist, 
					null);
			
		}else{
			//不带角色的固定流程派发
			istrue_send=new Action_NextBaseCustom().do_Action(
					BaseCreatorLoginName, 
					BaseSchema, 
					BaseID, 
					p_ProcessID, 
					p_ProcessType, 
					"移交处理", 
					AssingFieldlist,
					null);
		}
		return istrue_send;
	}
	
	//自由派发流程
	public boolean getAssagin(String BaseCreatorLoginName,
							 String BaseSchema,String BaseID,
							 List<BaseFieldInfo> AssingFieldlist,
							 String BaseAssigneeS,
							 String BaseAssigneeSName,
							 String p_ProcessID,
							 String p_ProcessType,
							 long lBaseAcceptOutTime,
							 long lBaseDealOutTime
							 )
	{
		boolean istrue_send=false;
		//增加派单时间
		//AssingFieldlist.add(new BaseFieldInfo("BaseSendDate","700000007",System.currentTimeMillis()/1000+"",7));
		BaseToDealObject baseToDealObject = null;
		String[] assigns = BaseAssigneeS.split(";");
		String[] assignNames = BaseAssigneeSName.split(";");
		List p_BaseToDealObject = new ArrayList();
		for(int i=0;i<assigns.length;i++)
		{
			baseToDealObject = new BaseToDealObject();
			baseToDealObject.setAssginee("");
			baseToDealObject.setAssgineeID("");
			baseToDealObject.setGroup("");
			baseToDealObject.setGroupID("");
			if((assigns[i].split("#"))[0].equals("GroupID")){
				baseToDealObject.setGroup(assignNames[i]);
				baseToDealObject.setGroupID((assigns[i].split("#"))[1]);						
			}else{
				baseToDealObject.setAssginee(assignNames[i]);
				baseToDealObject.setAssgineeID((assigns[i].split("#"))[1]);
			}
			baseToDealObject.setAssignOverTimeDate(new Date());
			//baseToDealObject.setDealOverTimeDate(FormatTime.formatIntToDate(lBaseDealOutTime));
			//baseToDealObject.setAcceptOverTimeDate(FormatTime.formatIntToDate(lBaseAcceptOutTime));
			baseToDealObject.setDealOverTimeDate(null);
			baseToDealObject.setAcceptOverTimeDate(null);
			baseToDealObject.setFlag01Assign(1);
			baseToDealObject.setFlagType(0);
			baseToDealObject.setFlag02Copy(1);
			baseToDealObject.setFlag03Assist(1);
			baseToDealObject.setFlag04Transfer(1);
			baseToDealObject.setFlag05TurnDown(1);
			baseToDealObject.setFlag06TurnUp(1);
			baseToDealObject.setFlag07Recall(1);
			baseToDealObject.setFlag08Cancel(1);
			baseToDealObject.setFlag09Close(1);
			baseToDealObject.setFlag15ToAuditing(1);
			baseToDealObject.setFlag31IsTransfer(0);
		
			p_BaseToDealObject.add(baseToDealObject);
			}
		
			istrue_send=new Action_ToDeal().do_Action(
					BaseCreatorLoginName,
					BaseSchema,
					BaseID,
					p_ProcessID,
					p_ProcessType,
					p_BaseToDealObject,
					AssingFieldlist,
					null);

		return  istrue_send;
	}
	
	//固定完成功能
	public boolean getFixFinish(String m_BaseID,List AssingFieldlist,String p_ProcessID,String p_ProcessType){
		boolean blnFinsih=false;

		Action_Close m_Action_Close = new Action_Close();
		blnFinsih =  m_Action_Close.do_Action(
					"Demo", 
					"WF:EL_TTM_CCH", 
					m_BaseID, 
					p_ProcessID,
					p_ProcessType,
					AssingFieldlist,
					null
					);

		return blnFinsih;
	}
	
	//受理功能
	public boolean getConfirm(String p_strUserLoginName,
							String p_BaseSchema,
							String p_BaseID ,
							List<BaseFieldInfo>  AssingFieldlist,
							String p_ProcessID,
							String p_ProcessType
							){
		return new Action_Start().do_Action(p_strUserLoginName, p_BaseSchema, p_BaseID, p_ProcessID,
				p_ProcessType, null);
	}
	
	//受理功能
	public boolean getFreeConfirm(String p_strUserLoginName,
							String p_BaseSchema,
							String p_BaseID ,
							List<BaseFieldInfo>  AssingFieldlist,
							String p_ProcessID,
							String p_ProcessType
							){
		return new Action_Confirm().do_Action(	p_strUserLoginName, 
												p_BaseSchema, 
												p_BaseID, 
												p_ProcessID,
												p_ProcessType,
												AssingFieldlist,
												null);
	}
	
	
	//归档功能
	public boolean getClsoeSchema(String p_strUserLoginName,
								String p_BaseSchema,
								String p_BaseID ,
								List<BaseFieldInfo>  AssingFieldlist,
								String p_ProcessID,
								String p_ProcessType){
	
		boolean istrue_Finish=false;
		istrue_Finish=new Action_NextBaseCustom().do_Action(	
															p_strUserLoginName,
															p_BaseSchema,
															p_BaseID,
															p_ProcessID,
															p_ProcessType,
															"归档",
															AssingFieldlist,
															null);
		
		return istrue_Finish;
	}
	
	/**
	 * 根据工单form，环节号，工单form查询角色
	 * @param 
	 * @return
	 */
	public Row getQueryRole(String p_BaseSchema,String p_PhaseNo,String p_BaseID,String p_BaseTplID ){
		Row row=null;
		IDataBase database=GetDataBase.createDataBase();
		RemedyDBOp remedyDBOp=new RemedyDBOp();
		String BaseSchema="WF:App_TplBase_PerformRole";
		String tblName=remedyDBOp.GetRemedyTableName(BaseSchema);
		Table table=new Table(database,tblName);
		try{
			 String fields=" c700022002 as RoleProcessRoleType," +
			 			   " c700022301 as ContextAssignee_FieldID," +
			 			   " c700022302 as ContextAssigneeID_FieldID," +
			 			   " c700022303 as ContextGroup_FieldID," +
			 			   " c700022404 as ContextGroupID_FieldID ";
			 String relate="";
			 String conditions="c700022005=? and c700022006=? and c700022004=? ";
			 Object[] values={p_BaseSchema,p_PhaseNo,p_BaseTplID};
			 String extendby="";
			 int curpage=0;
			 int pageSize=0;
			 int isCount=0;
			 RowSet rowSet=table.getRows( fields,  relate,  conditions  ,values,  extendby,  curpage,  pageSize, isCount);
			 int rows=0;
			 if(rowSet!=null)
				rows=rowSet.length();
			 for(int i=0;i<rows;i++){
				 row=rowSet.get(i);
			 }

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return row;
	}

	/*
	查询故障原因
	WF:Config_EL_TTM_TTH_FaultReason
	编    号： 	No       	650000003   Character254
	代    码： 	Code     	650000005   Character254
	故障原因细分：  detail		650000002   Character254   
	全    称： 	value    	650000006   Character254
	故障原因分类：	category   	650000001   Character254

	WF:Config_EL_TTM_TTH_FaultSubReason
	WF:Config_EL_TTM_TTH_FaultReason
	*/
	public RowSet getFaultReasonInfo(){

		String p_Schema="WF:Config_EL_TTM_TTH_FaultReason";
		IDataBase iDataBase=GetDataBase.createDataBase();
		Table table=new Table(iDataBase,"");
		RemedyDBOp RemedyDBOp=new RemedyDBOp();
		String tblSchema=RemedyDBOp.GetRemedyTableName(p_Schema);
		String sql="select c650000003 as  No, c650000005 as Code, c650000002 as detail,c650000006 as value,c650000001 as category ";
		sql+=" from "+tblSchema;
		sql+=" order by c650000006";
		//sql+=" where c650042014= "+p_NetType;
		Object[] values=null;
		RowSet rowSet=table.executeQuery(sql, values, 0, 0, 2);	
		return rowSet;
	}
	
	public RowSet getSubFaultReasonInfo(){

		String p_Schema="WF:Config_EL_TTM_TTH_FaultReason";
		IDataBase iDataBase=GetDataBase.createDataBase();
		Table table=new Table(iDataBase,"");
		RemedyDBOp RemedyDBOp=new RemedyDBOp();
		String tblSchema=RemedyDBOp.GetRemedyTableName(p_Schema);
		String sql="select distinct c650000002 as detail";
		sql+=" from "+tblSchema;
		sql+=" order by c650000002";
		//sql+=" where c650042014= "+p_NetType;
		Object[] values=null;
		RowSet rowSet=table.executeQuery(sql, values, 0, 0, 2);	
		return rowSet;
	}
	
	
	/*
	查询网络分类
	WF:Config_EL_00_NetType
	编号       650000003  No       Integer
	代    码： 650000005  Code     char254
	一级类别： 650000001  Item1    char254
	二级类别： 650000002  Item2    char254
	三级类别： 650000004  Item3    char254
	全    称： 650000006  value    char254
	*/
	public RowSet getNetType(){
		String p_Schema="WF:Config_EL_00_NetType";
		IDataBase iDataBase=GetDataBase.createDataBase();
		Table table=new Table(iDataBase,"");
		RemedyDBOp RemedyDBOp=new RemedyDBOp();
		String tblSchema=RemedyDBOp.GetRemedyTableName(p_Schema);
		String sql="select c650000003 as  No, c650000005 as Code,c650000001 as Item1, c650000002 as Item2,c650000004 as Item3,c650000006 as value ";
		sql+=" from "+tblSchema;
		sql+=" order by c650000006";
		//sql+=" where c650042014= "+p_NetType;
		Object[] values=null;
		RowSet rowSet=table.executeQuery(sql, values, 0, 0, 2);	
		return rowSet;
	}
	
	/*
	WF:Config_EL_00_EquipmentFactory
	编号       650000003      No           Integer
	所属网络分类： 650042011  NetCategory  char254
	设备厂家：   650000001    name         char254
	代    码：   650000002    code         char254

	*/
	public RowSet getEquipmentFactory(){
		String p_Schema="WF:Config_EL_00_EquipmentFactory";
		IDataBase iDataBase=GetDataBase.createDataBase();
		Table table=new Table(iDataBase,"");
		RemedyDBOp RemedyDBOp=new RemedyDBOp();
		String tblSchema=RemedyDBOp.GetRemedyTableName(p_Schema);
		String sql="select c650000003 as  No, c650000001 as NetName,c650042011 as NetCategory, c650000002 as code ";
		sql+=" from "+tblSchema;
		sql+=" order by c650042011";
		//sql+=" where c650042014= "+p_NetType;
		Object[] values=null;
		RowSet rowSet=table.executeQuery(sql, values, 0, 0, 2);	
		return rowSet;
	}
	
	/**
	 *查询工单信息
	 * @param p_Schema
	 * @param BaseID
	 * @return
	 */
	public Row getSchemaInfo(String p_Schema,String BaseID){
		Row row=null;
		try{
			/*
			故障设备厂商：  800020017   INC_EquipmentManufacturer  
			故障设备类型：  800020016   INC_EquipmentType
			故障原因类别：  800030002   INC_ReasonType	*/
			IDataBase iDataBase=GetDataBase.createDataBase();
			Table table=new Table(iDataBase,"");
			RemedyDBOp RemedyDBOp=new RemedyDBOp();
			String tblSchema=RemedyDBOp.GetRemedyTableName(p_Schema);
			String sql="select c800020017 as  INC_EquipmentManufacturer, c800020016 as INC_EquipmentType,c800030002 as INC_ReasonType,c700000014 as BaseItems,c700000003 as BaseSN,c700000022 as BaseTplID, " +
					" c700000007 as BaseSendDate , c700000008 as BaseFinishDate,c700000009 as BaseCloseDate,c700000010 as BaseStatus,c700000017 as BaseAcceptOutTime,c700000018  as BaseDealOutTime ";
			sql+=" from "+tblSchema;
			sql+=" where  c1='"+BaseID+"'";
			Object[] values=null;
			RowSet rowSet=table.executeQuery(sql, values, 0, 0, 2);	
			
			for(int i=0;i<rowSet.length();i++){
				row=rowSet.get(i);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return row;
	}
	
	/**
	 *查询工单信息
	 * @param p_Schema
	 * @param BaseID
	 * @return
	 */
	public RowSet getDealProcessInfo(String p_Schema,String p_BaseID){
		RowSet rowSet=null;
		try{
			IDataBase iDataBase=GetDataBase.createDataBase();
			Table table=new Table(iDataBase,"");
			RemedyDBOp RemedyDBOp=new RemedyDBOp();
			String tblSchema=RemedyDBOp.GetRemedyTableName(p_Schema);
			String tblDealProcess=RemedyDBOp.GetRemedyTableName("WF:App_DealProcess");
			String sql=" select tth.c700000017 as BaseAcceptOutTime,tth.c700000018  as BaseDealOutTime,tth.c1 as BaseID,deal.c700020013 as AssignOverTimeDate,deal.c700020014 as DealOverTimeDate,deal.c1 as ProcessID ";
			sql+=" from "+tblDealProcess+" deal, "+tblSchema+" tth ";
			sql+=" where tth.c1 = '"+p_BaseID+"' and deal.c700020002 = '"+p_Schema+"' and tth.c1 = deal.c700020001 and (deal.c700020020 = '0' or deal.c700020020 = '1') ";
			Object[] values=null;
			rowSet=table.executeQuery(sql, values, 0, 0, 2);	
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return rowSet;
	}
	
	/**
	 * 更新工单时间
	 * @param p_Schema
	 * @param BaseID
	 * @return
	 */
	public String getUpdateDealProcessInfo(String p_Schema,String p_BaseID,Row newRow,String ProcessIDs,String Updatefield){
		String result="";
		PreparedStatement pstmt=null;
		IDataBase iDataBase=GetDataBase.createDataBase();
		try {
			Table table=new Table(iDataBase,"");
			RemedyDBOp RemedyDBOp=new RemedyDBOp();
			String tblDealProcess=RemedyDBOp.GetRemedyTableName("WF:App_DealProcess");
			String sql="Update "+tblDealProcess+
			       //" Set c700020013="+newRow.getString("c700020013")+",c700020068="+newRow.getString("c700020068")+
			        " Set "+Updatefield+
			        " where c700020002='"+p_Schema+"' and c700020001='"+p_BaseID+"' and c1 in ("+ProcessIDs+")";
			pstmt= iDataBase.getConn().prepareStatement(sql);
			//执行SQL后影响的行数
			int affectableRow = 0; 
			affectableRow = pstmt.executeUpdate();
			System.out.println("更新行数 :"+affectableRow +" ProcessIDs  : "+ProcessIDs);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try
			{
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			iDataBase.closeConn();
		}
	 return result;
	}
	
	/**
	 * 更新Dealprocess时间操作
	 * @param p_Schema
	 * @param p_BaseID
	 * @return
	 */
	public String getUpdateOpera(String p_Schema,String p_BaseID){
		String result="";
		try{
			p_Schema=FormatString.CheckNullString(p_Schema);
			p_BaseID=FormatString.CheckNullString(p_BaseID);
			RowSet rowSet=getDealProcessInfo(p_Schema,p_BaseID);
			if(rowSet==null){ return ""; }
			Row rowInfo=null;
			String AcceptOutTimeProcessID="";
			String OutTimeProcessID="";
			int rsNum=0;
			rsNum=rowSet.length();
			for(int i=0; i<rsNum;i++){
				rowInfo=rowSet.get(i);
				//System.out.println(rowInfo.getlong("AssignOverTimeDate") +"      ||  "+ rowInfo.getlong("DealOverTimeDate") +"||" +rowInfo.getString("ProcessID"));
				if(rowInfo.getlong("AssignOverTimeDate")==0 || rowInfo.getlong("AssignOverTimeDate")==-1){
					if(AcceptOutTimeProcessID.equals("")){
						AcceptOutTimeProcessID=rowInfo.getString("ProcessID");
					}else{
							AcceptOutTimeProcessID+=","+rowInfo.getString("ProcessID");
					}
				}
				if(rowInfo.getlong("DealOverTimeDate")==0 || rowInfo.getlong("DealOverTimeDate")==-1){
					if(OutTimeProcessID.equals("")){
						OutTimeProcessID=rowInfo.getString("ProcessID");
					}else{
						OutTimeProcessID+=","+rowInfo.getString("ProcessID");
					}
				}
			}
			if(!AcceptOutTimeProcessID.equals("") || !OutTimeProcessID.equals("")){
				long BaseAcceptOutTime=rowInfo.getlong("BaseAcceptOutTime");
				long BaseDealOutTime=rowInfo.getlong("BaseDealOutTime");	
				String Updatefield="";
				/*	
				 * 
				工单受理时限 c700000017 as BaseAcceptOutTime,
				工单处理时限 c700000018  as BaseDealOutTime
				--------------
				受理时限：   700020013   AcceptOverTimeDate  		Date/Time  Optional
				受理时限_秒：700020068   AcceptOverTimeDate_Relative    Integer    Optional
				受理时段：   700020079   AcceptOverTimeDate_tmp         Integer    Optional
				处理时限：   700020014   DealOverTimeDate    		Date/Time  Optional
				处理时限_秒：700020069   DealOverTimeDate_Relative      Integer    Optional
				处理时段：   700020080   DealOverTimeDate_tmp           Integer    Optional*/
				if(!AcceptOutTimeProcessID.equals("")){
					Updatefield=" c700020013="+BaseAcceptOutTime+",c700020068="+BaseAcceptOutTime;
					getUpdateDealProcessInfo(p_Schema,p_BaseID,null,AcceptOutTimeProcessID,Updatefield);
				}
				if(!OutTimeProcessID.equals("")){
					Updatefield=" c700020014="+BaseDealOutTime+",c700020069="+BaseDealOutTime;
					getUpdateDealProcessInfo(p_Schema,p_BaseID,null,OutTimeProcessID,Updatefield);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
}
