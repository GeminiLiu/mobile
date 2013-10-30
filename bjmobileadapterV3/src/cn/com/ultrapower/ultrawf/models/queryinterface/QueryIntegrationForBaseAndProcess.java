package cn.com.ultrapower.ultrawf.models.queryinterface;

/**
 * 为其它模块做查询用的类，主要是查询工单表和处理表的联合查询
 * @author xufaqiu
 *
 */
import java.sql.*;
import java.util.*;

import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.config.ParBaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.process.ParBaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
import cn.com.ultrapower.ultrawf.models.process.ProcessUtil;
import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.ultrawf.share.constants.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.ultrawf.share.queryanalyse.ParBindString;
import cn.com.ultrapower.ultrawf.share.queryanalyse.ParseParmeter;
 
public class QueryIntegrationForBaseAndProcess {

	//工单特有字段Hashtable
	private Hashtable hsOwnerFiled=null;
	
	private int intQueryResultRows=0;
	//返回查询的总行数行数 
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	
	public QueryIntegrationForBaseAndProcess(){}
	
	/**
	 * 等待处理的任务(主办、协办、待阅、待审批)查询(某人的)
	 * @param optType
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 * @throws Exception
	 */
	public List GetListWaitProcess(int optType, String p_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		//设置从活动表中查询
		m_ParBaseModel.setIsArchive(0);		
		ParDealProcess m_ParDealProcess=new ParDealProcess();		
		m_ParDealProcess.setProcessOptionalType(optType);
		m_ParDealProcess.setTasekPersonID(p_UserLoginName);
		//设置从活动表中查询
		m_ParDealProcess.setIsArchive(0);
		//如果是审批
		/* 加了审批配置waitauditing后注释掉的 2007-06-28
		if(optType==Constants.ProcessWaitAuditing||optType==Constants.ProcessMyAuditingAndIsFinished)
		{
			//设置为审批，默认为处理(3为审批)
			m_ParDealProcess.setFlagType("3");
		}
		*/
		
		//设置排序字段
		m_ParBaseModel.setOrderbyFiledNameString("BaseID,BaseSN",1);

		m_Relist=GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);		
		return m_Relist;
	}
	/**
	 * 等待处理的任务(主办、协办、待阅、待审批)查询(某人某工单的)
	 * @param optType
	 * @param p_BaseSchema
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 * @throws Exception
	 */
	public List GetListWaitProcess(int optType,String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		//设置从活动表中查询		m_ParBaseModel.setIsArchive(0);		
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParBaseModel.setBaseSchema(p_BaseSchema);
		m_ParDealProcess.setProcessBaseSchema(p_BaseSchema);
		m_ParDealProcess.setProcessOptionalType(optType);
		m_ParDealProcess.setTasekPersonID(p_UserLoginName);
		//如果是审批
		if(optType==Constants.ProcessWaitAuditing||optType==Constants.ProcessMyAuditingAndIsFinished)
		{
			//设置为审批，默认为处理(3为审批)
			m_ParDealProcess.setFlagType("3");
		}		
		//设置排序字段
		m_ParBaseModel.setOrderbyFiledNameString("BaseID,BaseSN",1);
		
		m_Relist=GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);		
		return m_Relist;
	}
	
	/**
	 * 将查询的Rs记录转换成实体类的List
	 * @param p_ObjRs
	 * @return
	 * @throws Exception
	 */
	private List ConvertRsToList(ResultSet p_ObjRs) throws Exception
	{
		if(p_ObjRs==null) return null;
		String key;
		List list = new ArrayList();
		try{
			while(p_ObjRs.next())
			{
				 IntegrationBaseAndDealProcessModle m_Base=new IntegrationBaseAndDealProcessModle();
				 m_Base.setBaseID(p_ObjRs.getString("BaseID"));
				 m_Base.setBaseTplID(p_ObjRs.getString("BaseTplID"));
				 m_Base.setBaseSchema(p_ObjRs.getString("BaseSchema"));
				 m_Base.setBaseName(p_ObjRs.getString("BaseName"));
				 m_Base.setBaseSN(p_ObjRs.getString("BaseSN"));
				 m_Base.setBaseCreatorFullName(p_ObjRs.getString("BaseCreatorFullName"));
				 m_Base.setBaseCreatorLoginName(p_ObjRs.getString("BaseCreatorLoginName"));
				 m_Base.setBaseCreateDate(p_ObjRs.getLong("BaseCreateDate"));
				 m_Base.setBaseSendDate(p_ObjRs.getLong("BaseSendDate"));
				 m_Base.setBaseFinishDate(p_ObjRs.getLong("BaseFinishDate"));
				 m_Base.setBaseCloseDate(p_ObjRs.getLong("BaseCloseDate"));
				 m_Base.setBaseStatus(p_ObjRs.getString("BaseStatus"));
				// m_Base.setBaseSummary(p_ObjRs.getString("BaseSummary"));
//				 m_Base.setBaseAssigneeS(p_ObjRs.getString("BaseAssigneeS"));
//				 m_Base.setBaseAuditingerS(p_ObjRs.getString("BaseAuditingerS"));
				// m_Base.setBaseItems(p_ObjRs.getString("BaseItems"));
				// m_Base.setBasePriority(p_ObjRs.getString("BasePriority"));
				// m_Base.setBaseIsAllowLogGroup(p_ObjRs.getInt("BaseIsAllowLogGroup"));
				// m_Base.setBaseAcceptOutTime(p_ObjRs.getLong("BaseAcceptOutTime"));
				// m_Base.setBaseDealOutTime(p_ObjRs.getLong("BaseDealOutTime"));
				// m_Base.setBaseDescrption(p_ObjRs.getString("BaseDescrption"));
				  
				 
				// m_Base.setBaseResult(p_ObjRs.getString("BaseResult"));
				 m_Base.setBaseCloseSatisfy(p_ObjRs.getString("BaseCloseSatisfy"));
				 
				 m_Base.setProcessID(p_ObjRs.getString("ProcessID"));
				 m_Base.setStDate(p_ObjRs.getLong("StDate"));
				 m_Base.setBgDate(p_ObjRs.getLong("BgDate"));
				 m_Base.setFlagType(p_ObjRs.getInt("FlagType"));
				 m_Base.setAssginee(p_ObjRs.getString("Assginee"));
				 m_Base.setAssgineeID(p_ObjRs.getString("AssgineeID"));
				 m_Base.setGroup(p_ObjRs.getString("GroupName"));
				 m_Base.setGroupID(p_ObjRs.getString("GroupID"));
				 m_Base.setProcessStatus(p_ObjRs.getString("ProcessStatus"));
				 m_Base.setDealOverTimeDate(p_ObjRs.getLong("DealOverTimeDate"));//处理时限
				// System.out.println(p_ObjRs.getLong("StDate"));
				 m_Base.setFlagActive(p_ObjRs.getInt("FlagActive"));
				 m_Base.setDealer(p_ObjRs.getString("Dealer"));
				 m_Base.setDealerID(p_ObjRs.getString("DealerID"));
				
				 m_Base.setProcessType(p_ObjRs.getString("ProcessType"));
				 //700020018	Desc		描述，From写给to的信息，在该条记录创建时就已经有内容信息了
				 m_Base.setProcessDescinfo(p_ObjRs.getString("ProcessDescinfo"));
				 m_Base.setPhaseNo(p_ObjRs.getString("PhaseNo"));
				 m_Base.setPrevPhaseNo(p_ObjRs.getString("PrevPhaseNo"));
				 
				 m_Base.setCommissioner(p_ObjRs.getString("Commissioner"));
				 m_Base.setCommissionerID(p_ObjRs.getString("CommissionerID"));
				 m_Base.setCloseBaseSamenessGroup(p_ObjRs.getString("CloseBaseSamenessGroup"));
				 m_Base.setCloseBaseSamenessGroupID(p_ObjRs.getString("CloseBaseSamenessGroupID"));
				 m_Base.setIsGroupSnatch(p_ObjRs.getString("IsGroupSnatch"));
				 m_Base.setFlag32IsToTransfer(p_ObjRs.getString("Flag32IsToTransfer"));
				 m_Base.setFlag33IsEndPhase(p_ObjRs.getString("Flag33IsEndPhase"));
				 
				 
				 if(this.hsOwnerFiled!=null)
				 {	
					 String isColb="";
					 ProcessUtil m_ProcessUtil=new ProcessUtil();
					 for(Iterator it=hsOwnerFiled.keySet().iterator();it.hasNext();)
					 {
						 key   =   (String)it.next();
						 //System.out.println("Key: "+key);
						 //hsOwnerFiled.put(key,p_BaseRs.getString(key));
						 //System.out.println("Key: "+p_BaseRs.getString(key));
						 isColb=FormatString.CheckNullString(hsOwnerFiled.get(key));
						 //如果是大字段
						 if(isColb.trim().equals("1"))
						 {
							 Clob m_clob=p_ObjRs.getClob(key);
							 m_Base.SetOwnerFiled(key,m_ProcessUtil.getClobString(m_clob));
						 }
						 else
						 {
							 if(p_ObjRs.getString(key)!=null)
								 m_Base.SetOwnerFiled(key,p_ObjRs.getString(key));
							 else
								 m_Base.SetOwnerFiled(key,"");
						 }
					 }
				 }				 
				 list.add(m_Base);
			}//while(p_ObjRs.next())
			
			p_ObjRs.close();
		}
		catch(Exception ex)
		{
			System.err.println("QueryIntegrationForBaseAndProcess.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
			throw ex;
		}
		
		return list;
	}
	
	/**
	 * 生成工单特有字段的查询sql
	 * @param p_BaseSchema
	 * @param p_TblAliasName
	 * @return
	 */
	private String GetOwnerFiledSqlString(String p_BaseSchema,String p_TblAliasName)
	{
		String strTblName="";
		StringBuffer stringBuffer=new StringBuffer();
		if(p_TblAliasName==null)
			strTblName="";
		if(!strTblName.trim().equals(""))
			strTblName=p_TblAliasName+".";
		if(p_BaseSchema==null)
			p_BaseSchema="";
		
		if(p_BaseSchema.trim().equals(""))
		{
			hsOwnerFiled=new Hashtable();
			hsOwnerFiled.put("BaseItems","");
			hsOwnerFiled.put("BasePriority","");
			hsOwnerFiled.put("BaseSummary","");
			hsOwnerFiled.put("BaseAcceptOutTime","");
			hsOwnerFiled.put("BaseDealOutTime","");
			hsOwnerFiled.put("BaseIsAllowLogGroup","");
			hsOwnerFiled.put("BaseDescrption","");
			//因为工单专业（BaseItems）、紧急程度（BasePriority）、工单主题(BaseSummary)、工单受理时限(BaseAcceptOutTime)
			//、工单处理时限(BaseDealOutTime)、是否允许同组关单(BaseIsAllowLogGroup)、工单描述(BaseDescrption)改成了工单特有字段
			//但这些字段在每类工单都有的，所以在查所有工单时要把这些字段加上。
			//注：在查询某类工单时这些字段是通过下一段程序会读取工单特有字段表循环取出,所以不用像本段程序一样写死.
			stringBuffer.append(","+strTblName+"C700000014 as BaseItems");
			stringBuffer.append(","+strTblName+"C700000015 as BasePriority");
			stringBuffer.append(","+strTblName+"C700000011 as BaseSummary,"+strTblName+"C700000017 as BaseAcceptOutTime,"+strTblName+"C700000018 as BaseDealOutTime");
			stringBuffer.append(","+strTblName+"C700000016 as BaseIsAllowLogGroup,"+strTblName+"C700000019 as BaseDescrption");			
		}
		else
		{	
			hsOwnerFiled=new Hashtable();
			
			BaseOwnFieldInfo m_BaseOwnFieldInfo=new BaseOwnFieldInfo();
			ParBaseOwnFieldInfoModel m_ParBaseOwnFieldInfoModel=new ParBaseOwnFieldInfoModel();
			m_ParBaseOwnFieldInfoModel.SetBaseCategorySchama(p_BaseSchema);
			List m_List=m_BaseOwnFieldInfo.GetList(m_ParBaseOwnFieldInfoModel,0,0);
			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel;
			try{
				if(m_List!=null)
				{
					for(int i=0;i<m_List.size();i++)
					{
						m_BaseOwnFieldInfoModel=(BaseOwnFieldInfoModel)m_List.get(i);
						//hsOwnerFiled.put(m_BaseOwnFieldInfoModel.GetBase_field_DBName(),"");
						hsOwnerFiled.put(m_BaseOwnFieldInfoModel.GetBase_field_DBName(),String.valueOf(m_BaseOwnFieldInfoModel.getVarcharFieldeIsExceed()));
						stringBuffer.append(","+strTblName+"C"+m_BaseOwnFieldInfoModel.GetBase_field_ID()+" as "+m_BaseOwnFieldInfoModel.GetBase_field_DBName());
					}
				}
			}catch(Exception ex)
			{
				System.err.println("Base.GetOwnerFiledSqlString 方法："+ex.getMessage());
				ex.printStackTrace();	
			}
		}//if(p_BaseSchema.trim().equals(""))
		return stringBuffer.toString();
	}	
	
	/**
	 * 生成查询的Sql语句
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @return
	 */
	private String GetSelectSql(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_ParBaseModel==null)
			p_ParBaseModel=new ParBaseModel();
		if(p_ParDealProcess==null)
			p_ParDealProcess=new ParDealProcess();
		
		if(p_ParDealProcess.getProcessOptionalType()==Constants.ProcessWaitDealAndAuditing)
		{
			//待处理和待审批的待办事宜=待处理+待审批
			sqlString.append(" select * from (");
			sqlString.append(GetSelectSqlExt2(p_ParBaseModel,p_ParDealProcess));
			sqlString.append(") ResultTbl ");
		}
		else
		{
			if(p_ParBaseModel.getIsArchive()==0||p_ParBaseModel.getIsArchive()==1)
			{
				p_ParDealProcess.setIsArchive(p_ParBaseModel.getIsArchive());
				sqlString.append(" select * from (");
				sqlString.append(GetSelectSqlExt(p_ParBaseModel,p_ParDealProcess));
				sqlString.append(") ResultTbl ");
			}
			else 
			{
				int isArchive=p_ParBaseModel.getIsArchive();
				
				sqlString.append(" select * from (");
				//生成当前活动信息的sql
				p_ParBaseModel.setIsArchive(0);	
				p_ParDealProcess.setIsArchive(0);
				sqlString.append(GetSelectSqlExt(p_ParBaseModel,p_ParDealProcess));
				sqlString.append(" union all ");
	//			生成当前历史信息的sql
				p_ParBaseModel.setIsArchive(1);
				p_ParDealProcess.setIsArchive(1);			
				sqlString.append(GetSelectSqlExt(p_ParBaseModel,p_ParDealProcess));
				sqlString.append(") ResultTbl ");
				
				p_ParBaseModel.setIsArchive(isArchive);
				p_ParDealProcess.setIsArchive(isArchive);
			}
		}
		return sqlString.toString();
		
	}
	private String GetSelectSqlExt2(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess)
	{
		StringBuffer sqlString = new StringBuffer();
		p_ParDealProcess.setProcessOptionalType(Constants.ProcessDeal);
		sqlString.append(GetSelectSqlExt(p_ParBaseModel,p_ParDealProcess));
		sqlString.append(" union all ");
		p_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitAuditing);	
		sqlString.append(GetSelectSqlExt(p_ParBaseModel,p_ParDealProcess));
		return sqlString.toString();
	}
	/**
	 * 生成查询的Sql语句
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @return
	 */
	private String GetSelectSqlExt(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess)
	{
		
		String strBaseScheam=p_ParBaseModel.getBaseSchema();
		if(strBaseScheam==null)
			strBaseScheam="";
		
		String strProcessTblName;
		String strBaseInfo;
		StringBuffer sqlString = new StringBuffer();
		StringBuffer selectString=new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		//默认为处理		int m_intProcessType=Constants.ProcessDealing;// ProcessWaitAuditing;
		if(p_ParDealProcess!=null)
			m_intProcessType=p_ParDealProcess.getProcessOptionalType();
		
		
		//if(m_intProcessType==Constants.ProcessWaitAuditing||m_intProcessType==Constants.ProcessMyAuditingAndIsFinished)
//		//如果是审批
		if(m_intProcessType==Constants.ProcessWaitAuditing||m_intProcessType==Constants.ProcessMyAuditingAndIsFinished||p_ParDealProcess.getFlagType().trim().equals("3"))
		{	

			if(p_ParBaseModel.getIsArchive()==1)
				strProcessTblName=Constants.TblAuditingProcess_H;
			else
				strProcessTblName=Constants.TblAuditingProcess;
		}
		else
		{
			if(p_ParBaseModel.getIsArchive()==1)
				strProcessTblName=Constants.TblDealProcess_H;
			else 
				strProcessTblName=Constants.TblDealProcess;

		}
		strProcessTblName=m_RemedyDBOp.GetRemedyTableName(strProcessTblName);
		
		//生成Base字的Select
		selectString.append("Select ");
		
		//工单信息表
		if(!strBaseScheam.trim().equals(""))
		{
			String[] SplAry=strBaseScheam.split(":");;
			String key=SplAry[0].trim().toUpperCase();
			if(key.equals("IN")||key.equals("LIKE")||key.equals("NOT")||key.equals("OR"))
				strBaseScheam="";
		}
		if(!strBaseScheam.trim().equals(""))
		{
			//某类工单表			strBaseInfo=m_RemedyDBOp.GetRemedyTableName(strBaseScheam);
			p_ParDealProcess.setProcessBaseSchema(strBaseScheam);
			selectString.append(" BaseInfo.C1 as BaseID ");
		}
		else
		{
			//工单集合表 从UltraProcess:App_Base_Infor中查询  
			strBaseInfo=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseInfor);
			selectString.append(" BaseInfo.C700000000 as BaseID ");
		}

		//selectString.append(" BaseInfo.C1 as BaseID ");
		//selectString.append(" BaseInfo.C700000000 as BaseID ");
		
		selectString.append(",BaseInfo.C700000022 as BaseTplID,BaseInfo.C700000001 as BaseSchema,BaseInfo.C700000002 as BaseName,BaseInfo.C700000003 as BaseSN");
		selectString.append(",BaseInfo.C700000004 as BaseCreatorFullName,BaseInfo.C700000005 as BaseCreatorLoginName,BaseInfo.C700000006 as BaseCreateDate");
		selectString.append(",BaseInfo.C700000007 as BaseSendDate,BaseInfo.C700000008 as BaseFinishDate,BaseInfo.C700000009 as BaseCloseDate");
		selectString.append(",BaseInfo.C700000010 as BaseStatus");
		//sqlString.append(",BaseInfo.C700000011 as BaseSummary,BaseInfo.C700000014 as BaseItems,BaseInfo.C700000015 as BasePriority");
		//sqlString.append(",BaseInfo.C700000016 as BaseIsAllowLogGroup,BaseInfo.C700000017 as BaseAcceptOutTime,BaseInfo.C700000018 as BaseDealOutTime");
		//sqlString.append(",BaseInfo.C700000019 as BaseDescrption" );
		//selectString.append(",BaseInfo.C700000020 as BaseResult");
		selectString.append(",BaseInfo.C700000021 as BaseCloseSatisfy ");
		//工单特有字段
		String strOwnerSql=GetOwnerFiledSqlString(strBaseScheam,"BaseInfo");
		selectString.append(strOwnerSql);
		//sqlString.append(" ,DealProcess.C700020001 as Baseid,C700020002 as BaseSchema ");
		selectString.append(",DealProcess.C1 as ProcessID");
		selectString.append(",DealProcess.C700020043 as ProcessType");
		//700020019	FlagType 取值为：0主办、1协办、2抄送  
		selectString.append(",DealProcess.C700020019 as FlagType");
		//	700020006	AssgineeID	人登陆名 700020005	Assginee	人名，本记录的主人，派单的对象

		selectString.append(",DealProcess.C700020006 as AssgineeID,DealProcess.C700020005 as Assginee");
		//700020007	Group   	组，本记录的主人，派单的对象 700020008	GroupID		组ID
		selectString.append(",DealProcess.C700020007 as GroupName,DealProcess.C700020008 as GroupID");
		//700020009	Dealer		人名，本记录的操作者 700020010	DealerID	人登陆名
		selectString.append(",DealProcess.C700020009 as Dealer,DealProcess.C700020010 as DealerID ");
		//700020011	ProcessStatus	本记录状态描述。
		selectString.append(",DealProcess.C700020011 as ProcessStatus");
		//700020020	FlagActive 是否有效：7未派发，并等待审批，6未派发、未提交审批，5下一步的审批等待，4下一步的处理等待，3提交审批后等待审批完成的等待中，2派发后等待处理完成的等待中，1活动，0已完毕
		selectString.append(",DealProcess.C700020020 as FlagActive");
		//700020018	Desc		描述，From写给to的信息，在该条记录创建时就已经有内容信息了
		selectString.append(",DealProcess.C700020018 as ProcessDescinfo");
		//700020015	StDate		创建/生效时间，表示该记录创建/生效时间
		selectString.append(",DealProcess.C700020015 as StDate");
		//700020016	BgDate		领单时间
		selectString.append(",DealProcess.C700020016 as BgDate");
		
		selectString.append(",DealProcess.C700020003 as PhaseNo");
		selectString.append(",DealProcess.C700020004 as PrevPhaseNo");
		
		selectString.append(" ,DealProcess.C700020047 as Commissioner,DealProcess.C700020048 as CommissionerID");
		selectString.append(" ,DealProcess.C700020050 as CloseBaseSamenessGroup,DealProcess.C700020051 as CloseBaseSamenessGroupID");
		selectString.append(" ,DealProcess.C700020049 as IsGroupSnatch");
		selectString.append(" ,DealProcess.C700020053 as Flag33IsEndPhase ");

		
		//如果是审批环节
		if(m_intProcessType==Constants.ProcessWaitAuditing ||p_ParDealProcess.getFlagType().trim().equals("3"))
		{
			//因为审批环节没有 DealOverTimeDate处理时限的字段 所以用0取代
			selectString.append(",0 as DealOverTimeDate");
			selectString.append(",0 as Flag32IsToTransfer");
		}
		else
		{
			selectString.append(",DealProcess.C700020014 as DealOverTimeDate");
			selectString.append(",DealProcess.C700020052 as Flag32IsToTransfer");
		}		
		
		try{
			
				//sqlString.append(" union all ");
				sqlString.append(selectString.toString());
				sqlString.append(" from "+strBaseInfo+" BaseInfo");
				sqlString.append(" ,"+strProcessTblName+" DealProcess ");
				//BaseInfo.BaseID=DealProcess.BaseID and BaseInfo.BaseSchema=DealProcess.BaseSchema
				//处理环节表：BaseID 700020001 指向主工单处理过程记录的指针
				
				if(!strBaseScheam.equals(""))
					////	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
					sqlString.append(" where DealProcess.C700020001=BaseInfo.C1 ");
				else
					////因为改为从UltraProcess:App_Base_Infor中查询，所以Baseid为700000000
					sqlString.append(" where DealProcess.C700020001=BaseInfo.C700000000 ");
				//ProcessBaseSchema	700020002	指向主工单记录的指针
				sqlString.append("  and BaseInfo.C700000001=DealProcess.C700020002 ");
				if(p_ParBaseModel!=null)
					sqlString.append(p_ParBaseModel.GetWhereSql("BaseInfo"));
				if(p_ParDealProcess!=null)
					sqlString.append(p_ParDealProcess.GetWhereSql("DealProcess"));
			
			
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationForBaseAndProcess.GetBaseSelectSql 方法："+ex.getMessage());
			ex.printStackTrace();				
		}
	
		return(sqlString.toString());
	}

	/**
	 * 根据条件查询数据返回IntegrationBaseAndDealProcessModle类的List
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		Statement stm=null;
		ResultSet m_BaseRs=null;
		List m_Relist=null;
		String strSql;
		if(p_ParBaseModel==null)
			p_ParBaseModel=new ParBaseModel();
		
		strSql=GetSelectSql(p_ParBaseModel,p_ParDealProcess);
		
		GetQueryResultRows(strSql);
		strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_BaseRs);
		}catch(Exception ex)
		{
			
			System.err.println("QueryIntegrationForBaseAndProcess.GetList 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try{
				if(m_BaseRs!=null)
					m_BaseRs.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
			try{
				if(stm!=null)
					stm.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}			
			m_dbConsole.closeConn();
		}
		return m_Relist;
	}
	
	
	public int GetResultCount(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess)
	{
		String strSql=GetSelectSql(p_ParBaseModel,p_ParDealProcess);
		return GetQueryResultRows(strSql);
	}

	/**
	 * 返回查询的记录总数量(用于分页时计算总页数)
	 * @param p_strSql
	 */
	private int GetQueryResultRows(String p_strSql)
	{
		intQueryResultRows=0;
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(p_strSql);
		sqlString.append(" )");
		Statement stm=null;
		ResultSet m_BaseRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		
		try{
			stm=m_dbConsole.GetStatement();
			//System.out.println("sqlString语句："+sqlString.toString());
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			//System.out.println("ok:");
			if(m_BaseRs!=null)
			{
				if(m_BaseRs.next())
				{
					intQueryResultRows=m_BaseRs.getInt("rownums");
				}
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationForBaseAndProcess.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(m_BaseRs!=null)
					m_BaseRs.close();
				}catch(Exception ex){};
			try{
				if(stm!=null)
					stm.close();
			}catch(Exception ex){}
			m_dbConsole.closeConn();
		}
		return intQueryResultRows;
	}		

	
	/**
	 * 绑定变量新加2007-11-15
	 */
	

	private boolean equalsString(String str1,String str2)
	{
		
		boolean result=false;
		str1=FormatString.CheckNullString(str1).toUpperCase();
		str2=FormatString.CheckNullString(str2).toUpperCase();
		try{
			if(str1.equals(str2))
				result=true;
		}catch(Exception ex)
		{
			
		}
		return result;
	}
	
	/**
	 * 
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @param type 0 取工单和process的字段 1只要工单字段 2 只要process的字段
	 * @return
	 */
	private String GetSelectFiled(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int type)
	{
		
		String strBaseScheam=p_ParBaseModel.getBaseSchema();
		if(strBaseScheam==null)
			strBaseScheam="";
		
		StringBuffer selectString=new StringBuffer();
		//默认为处理
		int m_intProcessType=Constants.ProcessDealing;// ProcessWaitAuditing;
		if(p_ParDealProcess!=null)
			m_intProcessType=p_ParDealProcess.getProcessOptionalType();
		
		if(type==0 || type==1)
		{
			if(!strBaseScheam.trim().equals(""))
			{
				//某类工单表
				selectString.append(" base.C1 as BaseID ");
			}
			else
			{
				//工单集合表 从UltraProcess:App_Base_Infor中查询  
				selectString.append(" base.C700000000 as BaseID ");
			}
	
			//selectString.append(" C1 as BaseID ");
			//selectString.append(" C700000000 as BaseID ");
			
			selectString.append(",base.C700000022 as BaseTplID,base.C700000001 as BaseSchema,base.C700000002 as BaseName,base.C700000003 as BaseSN");
			selectString.append(",base.C700000004 as BaseCreatorFullName,base.C700000005 as BaseCreatorLoginName,base.C700000006 as BaseCreateDate");
			selectString.append(",base.C700000007 as BaseSendDate,base.C700000008 as BaseFinishDate,base.C700000009 as BaseCloseDate");
			selectString.append(",base.C700000010 as BaseStatus");
			//sqlString.append(",base.C700000011 as BaseSummary,base.C700000014 as BaseItems,base.C700000015 as BasePriority");
			//sqlString.append(",base.C700000016 as BaseIsAllowLogGroup,base.C700000017 as BaseAcceptOutTime,base.C700000018 as BaseDealOutTime");
			//sqlString.append(",base.C700000019 as BaseDescrption" );
			//selectString.append(",base.C700000020 as BaseResult");
			selectString.append(",base.C700000021 as BaseCloseSatisfy ");
			//工单特有字段
			String strOwnerSql=GetOwnerFiledSqlString(strBaseScheam,"base");
			
			selectString.append(strOwnerSql);
		}//if(type==0 || type==1)
		
		if(type==0 || type==2)
		{
			if(type==0)
				selectString.append(",");
			//sqlString.append(" ,deal.C700020001 as Baseid,C700020002 as BaseSchema ");
			selectString.append("deal.C1 as ProcessID");
			selectString.append(",deal.C700020043 as ProcessType");
			//700020019	FlagType 取值为：0主办、1协办、2抄送  
			selectString.append(",deal.C700020019 as FlagType");
			//	700020006	AssgineeID	人登陆名 700020005	Assginee	人名，本记录的主人，派单的对象
	
			selectString.append(",deal.C700020006 as AssgineeID,deal.C700020005 as Assginee");
			//700020007	Group   	组，本记录的主人，派单的对象 700020008	GroupID		组ID
			selectString.append(",deal.C700020007 as GroupName,deal.C700020008 as GroupID");
			//700020009	Dealer		人名，本记录的操作者 700020010	DealerID	人登陆名
			selectString.append(",deal.C700020009 as Dealer,deal.C700020010 as DealerID ");
			//700020011	ProcessStatus	本记录状态描述。
	
			selectString.append(",deal.C700020011 as ProcessStatus");
			//700020020	FlagActive 是否有效：7未派发，并等待审批，6未派发、未提交审批，5下一步的审批等待，4下一步的处理等待，3提交审批后等待审批完成的等待中，2派发后等待处理完成的等待中，1活动，0已完毕
	
			selectString.append(",deal.C700020020 as FlagActive");
			//700020018	Desc		描述，From写给to的信息，在该条记录创建时就已经有内容信息了
	
			selectString.append(",deal.C700020018 as ProcessDescinfo");
			//700020015	StDate		创建/生效时间，表示该记录创建/生效时间
			selectString.append(",deal.C700020015 as StDate");
			//700020016	BgDate		领单时间
			selectString.append(",deal.C700020016 as BgDate");
			
			selectString.append(",deal.C700020003 as PhaseNo");
			selectString.append(",deal.C700020004 as PrevPhaseNo");
			
			selectString.append(" ,deal.C700020047 as Commissioner,deal.C700020048 as CommissionerID");
			selectString.append(" ,deal.C700020050 as CloseBaseSamenessGroup,deal.C700020051 as CloseBaseSamenessGroupID");
			selectString.append(" ,deal.C700020049 as IsGroupSnatch");
			selectString.append(" ,deal.C700020053 as Flag33IsEndPhase ");
	
			
			//如果是审批环节
	
			if(m_intProcessType==Constants.ProcessWaitAuditing ||p_ParDealProcess.getFlagType().trim().equals("3"))
			{
				//因为审批环节没有 DealOverTimeDate处理时限的字段 所以用0取代
				selectString.append(",0 as DealOverTimeDate");
				selectString.append(",0 as Flag32IsToTransfer");
			}
			else
			{
				selectString.append(",deal.C700020014 as DealOverTimeDate");
				selectString.append(",deal.C700020012 as AssignOverTimeDate");			
				selectString.append(",deal.C700020052 as Flag32IsToTransfer");
			}
		}//if(type==0 || type==2)
		
		return selectString.toString();
	}

	
	
	private  List ConvertRsToListForBind(ResultSet p_ObjRs)
	{
		if(p_ObjRs==null) return null;
		//String key;
		List list = new ArrayList();
		try{

			ResultSetMetaData metaData=p_ObjRs.getMetaData();
			int cols=metaData.getColumnCount();			
			while(p_ObjRs.next())
			{
				
				IntegrationBaseAndDealProcessModle m_Base=new IntegrationBaseAndDealProcessModle();
				String colName;
				for(int col=1;col<=cols;col++)
				{
					colName=metaData.getColumnName(col);
					if(colName.equals(""))
						continue;
					 if(equalsString(colName,"BaseID"))
						 m_Base.setBaseID(p_ObjRs.getString("BaseID"));
					 else if(equalsString(colName,"BaseTplID"))
						 m_Base.setBaseTplID(p_ObjRs.getString("BaseTplID"));
					 else if(equalsString(colName,"BaseSchema"))
						 m_Base.setBaseSchema(p_ObjRs.getString("BaseSchema"));
					 else if(equalsString(colName,"BaseName"))
						 m_Base.setBaseName(p_ObjRs.getString("BaseName"));
					 else if(equalsString(colName,"BaseSN"))
						 m_Base.setBaseSN(p_ObjRs.getString("BaseSN"));
					 else if(equalsString(colName,"BaseCreatorFullName"))
						 m_Base.setBaseCreatorFullName(p_ObjRs.getString("BaseCreatorFullName"));
					 else if(equalsString(colName,"BaseCreatorLoginName"))
						 m_Base.setBaseCreatorLoginName(p_ObjRs.getString("BaseCreatorLoginName"));
					 else if(equalsString(colName,"BaseCreateDate"))
						 m_Base.setBaseCreateDate(p_ObjRs.getLong("BaseCreateDate"));
					 else if(equalsString(colName,"BaseSendDate"))
						 m_Base.setBaseSendDate(p_ObjRs.getLong("BaseSendDate"));
					 else if(equalsString(colName,"BaseFinishDate"))
						 m_Base.setBaseFinishDate(p_ObjRs.getLong("BaseFinishDate"));
					 else if(equalsString(colName,"BaseCloseDate"))
						 m_Base.setBaseCloseDate(p_ObjRs.getLong("BaseCloseDate"));
					 else if(equalsString(colName,"BaseStatus"))
						 m_Base.setBaseStatus(p_ObjRs.getString("BaseStatus"));
					// m_Base.setBaseSummary(p_ObjRs.getString("BaseSummary"));
	//				 m_Base.setBaseAssigneeS(p_ObjRs.getString("BaseAssigneeS"));
	//				 m_Base.setBaseAuditingerS(p_ObjRs.getString("BaseAuditingerS"));
					// m_Base.setBaseItems(p_ObjRs.getString("BaseItems"));
					// m_Base.setBasePriority(p_ObjRs.getString("BasePriority"));
					// m_Base.setBaseIsAllowLogGroup(p_ObjRs.getInt("BaseIsAllowLogGroup"));
					//m_Base.setBaseAcceptOutTime(p_ObjRs.getLong("BaseAcceptOutTime"));
					//m_Base.setBaseDealOutTime(p_ObjRs.getLong("BaseDealOutTime"));
					// m_Base.setBaseDescrption(p_ObjRs.getString("BaseDescrption"));
					  
					 
					// m_Base.setBaseResult(p_ObjRs.getString("BaseResult"));
					 else if(equalsString(colName,"BaseCloseSatisfy"))
						 m_Base.setBaseCloseSatisfy(p_ObjRs.getString("BaseCloseSatisfy"));
					 
					 else if(equalsString(colName,"ProcessID"))
						 m_Base.setProcessID(p_ObjRs.getString("ProcessID"));
					 else if(equalsString(colName,"StDate"))	
						 m_Base.setStDate(p_ObjRs.getLong("StDate"));
					 else if(equalsString(colName,"BgDate"))
						 m_Base.setBgDate(p_ObjRs.getLong("BgDate"));
					 else if(equalsString(colName,"FlagType"))
						 m_Base.setFlagType(p_ObjRs.getInt("FlagType"));
					 else if(equalsString(colName,"Assginee"))
						 m_Base.setAssginee(p_ObjRs.getString("Assginee"));
					 else if(equalsString(colName,"AssgineeID"))
						 m_Base.setAssgineeID(p_ObjRs.getString("AssgineeID"));
					 else if(equalsString(colName,"GroupName"))
						 m_Base.setGroup(p_ObjRs.getString("GroupName"));
					 else if(equalsString(colName,"GroupID"))
						 m_Base.setGroupID(p_ObjRs.getString("GroupID"));
					 else if(equalsString(colName,"ProcessStatus"))
						 m_Base.setProcessStatus(p_ObjRs.getString("ProcessStatus"));
					 else if(equalsString(colName,"DealOverTimeDate"))
						 m_Base.setDealOverTimeDate(p_ObjRs.getLong("DealOverTimeDate"));//处理时限
					// System.out.println(p_ObjRs.getLong("StDate"));
					 else if(equalsString(colName,"FlagActive"))
						 m_Base.setFlagActive(p_ObjRs.getInt("FlagActive"));
					 else if(equalsString(colName,"Dealer"))
						 m_Base.setDealer(p_ObjRs.getString("Dealer"));
					 else if(equalsString(colName,"DealerID"))
						 m_Base.setDealerID(p_ObjRs.getString("DealerID"));
					 else if(equalsString(colName,"ProcessType"))
						 m_Base.setProcessType(p_ObjRs.getString("ProcessType"));
					 //700020018	Desc		描述，From写给to的信息，在该条记录创建时就已经有内容信息了
					 else if(equalsString(colName,"ProcessDescinfo"))
						 m_Base.setProcessDescinfo(p_ObjRs.getString("ProcessDescinfo"));
					 else if(equalsString(colName,"PhaseNo"))
						 m_Base.setPhaseNo(p_ObjRs.getString("PhaseNo"));
					 else if(equalsString(colName,"PrevPhaseNo"))
						 m_Base.setPrevPhaseNo(p_ObjRs.getString("PrevPhaseNo"));
					 else if(equalsString(colName,"Commissioner"))
						 m_Base.setCommissioner(p_ObjRs.getString("Commissioner"));
					 else if(equalsString(colName,"CommissionerID"))
						 m_Base.setCommissionerID(p_ObjRs.getString("CommissionerID"));
					 else if(equalsString(colName,"CloseBaseSamenessGroup"))
						 m_Base.setCloseBaseSamenessGroup(p_ObjRs.getString("CloseBaseSamenessGroup"));
					 else if(equalsString(colName,"CloseBaseSamenessGroupID"))
						 m_Base.setCloseBaseSamenessGroupID(p_ObjRs.getString("CloseBaseSamenessGroupID"));
					 else if(equalsString(colName,"IsGroupSnatch"))
						 m_Base.setIsGroupSnatch(p_ObjRs.getString("IsGroupSnatch"));
					 else if(equalsString(colName,"Flag32IsToTransfer"))
						 m_Base.setFlag32IsToTransfer(p_ObjRs.getString("Flag32IsToTransfer"));
					 else if(equalsString(colName,"Flag33IsEndPhase"))
						 m_Base.setFlag33IsEndPhase(p_ObjRs.getString("Flag33IsEndPhase"));
					 else 
					 {
						 //如果是大字段
						 if(metaData.getColumnType(col)==java.sql.Types.CLOB)
						 {
							 ProcessUtil m_ProcessUtil=new ProcessUtil();
							 Clob m_clob=p_ObjRs.getClob(colName);
							 m_Base.SetOwnerFiled(colName,m_ProcessUtil.getClobString(m_clob));
						 }
						 else
							 m_Base.SetOwnerFiled(colName,FormatString.CheckNullString(p_ObjRs.getString(colName)));
						 
					 }
				}//for(int col=0;col<cols;col++)
					list.add(m_Base);
			}//while(p_ObjRs.next())
			
			p_ObjRs.close();
		}
		catch(Exception ex)
		{
			System.err.println("QueryIntegrationForBaseAndProcess.ConvertRsToListForBind 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		
		return list;		
	}
	
	
	public String getListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{

		
		List m_BaseFiledinfoList=null;
		List m_DealFiledinfoList=null;		
		m_DealFiledinfoList=p_ParDealProcess.getContionFiledInfoList();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strBaseScheam="";
		String strTblbase="";
		
		if(p_ParBaseModel!=null)
		{
			strBaseScheam=p_ParBaseModel.getBaseSchema();
			m_BaseFiledinfoList=p_ParBaseModel.getContionFiledInfoList();
		}
		if(!strBaseScheam.trim().equals(""))
		{
			//某类工单表
			strTblbase=m_RemedyDBOp.GetRemedyTableName(strBaseScheam);
		}
		else
			//工单集合表 从UltraProcess:App_Base_Infor中查询  
			strTblbase=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseInfor);		
		
		
		StringBuffer sqlString = new StringBuffer();
		ParseParmeter parseBaseParmeter=new ParseParmeter(m_BaseFiledinfoList);
		ParseParmeter parseDealParmeter=new ParseParmeter(m_DealFiledinfoList);
		String strDealTblName="";
		String strDealTblName_h="";
		
		String baseSelectFiled=FormatString.CheckNullString(parseBaseParmeter.getSelectFiled());
		String dealSelectFiled=FormatString.CheckNullString(parseDealParmeter.getSelectFiled());
		String whereSql=parseBaseParmeter.getWhereSql();
			
		whereSql+=parseDealParmeter.getWhereSql();
		String selectFiled="";
		if(baseSelectFiled.equals("") && dealSelectFiled.equals(""))
		{
			selectFiled=this.GetSelectFiled(p_ParBaseModel,p_ParDealProcess,0);
		}
		else 
		{
			if(!baseSelectFiled.equals(""))
				selectFiled=baseSelectFiled;
			else
				selectFiled=this.GetSelectFiled(p_ParBaseModel,p_ParDealProcess,1);
			
			if(!dealSelectFiled.equals(""))
				selectFiled+=","+dealSelectFiled;
			else
				selectFiled+=","+this.GetSelectFiled(p_ParBaseModel,p_ParDealProcess,2);
		}
		
		ParBindString m_ParBindString=p_ParDealProcess.getPorcessTypeBindSqlList("deal",p_ParDealProcess.getProcessOptionalType());
		//如果是审批
		if(p_ParDealProcess.getFlagType().trim().equals("3"))
		{
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				//获取历史表名
				if(p_ParBaseModel.getIsArchive()==1)
				{
					strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
				}
				else
					strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
			}
			else
			{
				strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
				strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
			}
		}
		else
		{
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				//获取历史表名
				if(p_ParBaseModel.getIsArchive()==1)
				{
					strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				}
				else
					strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
			}
			else
			{
				strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
			}			
		}//if(p_ParDealProcess.getFlagType().trim().equals("3"))
		
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				if(p_ParBaseModel.getIsArchive()==1 )
					sqlString.append(" from "+strDealTblName_h+" deal, "+strTblbase+" base " );
				else
					sqlString.append(" from "+strDealTblName+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlString.append(whereSql);
				sqlString.append(m_ParBindString.getBingSqlString());
				
			}
			else
			{
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strDealTblName+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlString.append(whereSql);
				sqlString.append(m_ParBindString.getBingSqlString());
				
				sqlString.append(" union all ");
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strDealTblName_h+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlString.append(whereSql);
				sqlString.append(m_ParBindString.getBingSqlString());
			
			}//if(p_ParDealProcess.getIsArchive()==0 || p_ParDealProcess.getIsArchive()==1)	
		
		
		String strSql=sqlString.toString();
		String orderByString=p_ParBaseModel.getOrderbyFiledNameString();
		if(!orderByString.equals(""))
			strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		else
		{
			orderByString=p_ParDealProcess.getOrderbyFiledNameString();
			strSql+=orderByString;
		}
		
		try{
			String[] baseValueList=parseBaseParmeter.getParmeterValue();
			String[] dealValueList=parseDealParmeter.getParmeterValue();
			int baselen=0;
			int deallen=0;
			if(baseValueList!=null)
				baselen=baseValueList.length;
			if(dealValueList!=null)
				deallen=dealValueList.length;
			
			
			for(int i=0;i<baselen;i++)
			{	
				strSql=strSql.replaceFirst("\\?","'"+ baseValueList[i]+"'");
			}
			for(int i=0;i<deallen;i++)
			{	
				strSql=strSql.replaceFirst("\\?","'"+ dealValueList[i]+"'");
			}
			String[] optionType=m_ParBindString.getBindValue();
			if(optionType!=null)
			{
				int optlen=0;
				optlen=optionType.length;
				for(int i=0;i<optlen;i++)
				{	
					strSql=strSql.replaceFirst("\\?","'"+ optionType[i]+"'");
				}				
			}
			
			if(p_ParBaseModel.getIsArchive()!=0 && p_ParBaseModel.getIsArchive()!=1)
			{
				for(int i=0;i<baselen;i++)
				{	
					strSql=strSql.replaceFirst("\\?","'"+ baseValueList[i]+"'");
				}
				for(int i=0;i<deallen;i++)
				{	
					strSql=strSql.replaceFirst("\\?","'"+ dealValueList[i]+"'");
				}
				if(optionType!=null)
				{
					int optlen=0;
					optlen=optionType.length;
					for(int i=0;i<optlen;i++)
					{	
						strSql=strSql.replaceFirst("\\?","'"+ optionType[i]+"'");
					}				
				}			
			}
			

		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationForBaseAndProcess.getListForBind(ParBaseModel,ParDealProcess) 方法"+ex.getMessage());
			System.err.println("strsql:"+strSql);
			ex.printStackTrace();			
			//throw ex;			
		}

		
		return strSql;

	}		
	public List getListForBind(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{

		List m_Relist=null;
		List m_BaseFiledinfoList=null;
		List m_DealFiledinfoList=null;		
		m_DealFiledinfoList=p_ParDealProcess.getContionFiledInfoList();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strBaseScheam="";
		String strTblbase="";
		
		if(p_ParBaseModel!=null)
		{
			strBaseScheam=p_ParBaseModel.getBaseSchema();
			m_BaseFiledinfoList=p_ParBaseModel.getContionFiledInfoList();
		}
		if(!strBaseScheam.trim().equals(""))
		{
			//某类工单表
			strTblbase=m_RemedyDBOp.GetRemedyTableName(strBaseScheam);
		}
		else
			//工单集合表 从UltraProcess:App_Base_Infor中查询  
			strTblbase=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseInfor);		
		
		 
		StringBuffer sqlString = new StringBuffer();
		StringBuffer sqlRowCount = new StringBuffer();
		ParseParmeter parseBaseParmeter=new ParseParmeter(m_BaseFiledinfoList);
		ParseParmeter parseDealParmeter=new ParseParmeter(m_DealFiledinfoList);
		String strDealTblName="";
		String strDealTblName_h="";
		
		String baseSelectFiled=FormatString.CheckNullString(parseBaseParmeter.getSelectFiled());
		String dealSelectFiled=FormatString.CheckNullString(parseDealParmeter.getSelectFiled());
			
		String whereSql=parseBaseParmeter.getWhereSql();
		whereSql+=parseDealParmeter.getWhereSql();
		
		String selectFiled="";
		if(baseSelectFiled.equals("") && dealSelectFiled.equals(""))
		{
			selectFiled=this.GetSelectFiled(p_ParBaseModel,p_ParDealProcess,0);
		}
		else 
		{
			if(!baseSelectFiled.equals(""))
				selectFiled=baseSelectFiled;
			else
				selectFiled=this.GetSelectFiled(p_ParBaseModel,p_ParDealProcess,1);
			
			if(!dealSelectFiled.equals(""))
				selectFiled+=","+dealSelectFiled;
			else
				selectFiled+=","+this.GetSelectFiled(p_ParBaseModel,p_ParDealProcess,2);
		}
		
		ParBindString m_ParBindString=p_ParDealProcess.getPorcessTypeBindSqlList("deal",p_ParDealProcess.getProcessOptionalType());
		//如果是审批
		if(p_ParDealProcess.getFlagType().trim().equals("3"))
		{
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				//获取历史表名
				if(p_ParBaseModel.getIsArchive()==1)
				{
					strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
				}
				else
					strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
			}
			else 
			{
				strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
				strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
			}
		}
		else
		{
			if(p_ParBaseModel.getIsArchive()==0 || p_ParDealProcess.getIsArchive()==1)
			{
				//获取历史表名
				if(p_ParBaseModel.getIsArchive()==1)
				{
					strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				}
				else
					strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
			}
			else
			{
				strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
			}			
		}//if(p_ParDealProcess.getFlagType().trim().equals("3"))
		
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				if(p_ParBaseModel.getIsArchive()==1 )
					sqlString.append(" from "+strDealTblName_h+" deal, "+strTblbase+" base " );
				else
					sqlString.append(" from "+strDealTblName+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlString.append(whereSql);
				sqlString.append(m_ParBindString.getBingSqlString());
				
				
				sqlRowCount.append(" select count(*) rownums ");
				if(p_ParDealProcess.getIsArchive()==1 )
					sqlRowCount.append(" from "+strDealTblName_h+" deal, "+strTblbase+" base " );
				else
					sqlRowCount.append(" from "+strDealTblName+" deal, "+strTblbase+" base " );
				
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlRowCount.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlRowCount.append(whereSql);
				sqlRowCount.append(m_ParBindString.getBingSqlString());
			}
			else
			{
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strDealTblName+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlString.append(whereSql);
				sqlString.append(m_ParBindString.getBingSqlString());
				
				sqlString.append(" union all ");
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strDealTblName_h+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlString.append(whereSql);
				sqlString.append(m_ParBindString.getBingSqlString());
				
				
				//数量查询sql
				sqlRowCount.append(" select sum(rownums) rownums from (");
				sqlRowCount.append(" select count(*) rownums ");
				sqlRowCount.append(" from "+strDealTblName+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlRowCount.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
		 		
				sqlRowCount.append(whereSql);
				sqlRowCount.append(m_ParBindString.getBingSqlString());
				
				sqlRowCount.append(" union all ");
				
				sqlRowCount.append(" select count(*) rownums ");
				sqlRowCount.append(" from "+strDealTblName_h+" deal, "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlRowCount.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				
				sqlRowCount.append(whereSql);
				sqlRowCount.append(m_ParBindString.getBingSqlString());
				sqlRowCount.append(" ) base");
				
				
			}//if(p_ParDealProcess.getIsArchive()==0 || p_ParDealProcess.getIsArchive()==1)	
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=sqlString.toString();
		
		String orderByString=p_ParBaseModel.getOrderbyFiledNameString();
		if(!orderByString.equals(""))
			strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		else
		{
			if(p_ParDealProcess!=null)
			{
				orderByString=p_ParDealProcess.getOrderbyFiledNameString();
				strSql+=orderByString;
			}
		}
		
		if(strSql.equals(""))
			return null;
		//分页
		if(p_PageNumber>0)
			//strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
			strSql=PageControl.bindGetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		//strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		PreparedStatement prestm=null;
		ResultSet m_BaseRs =null;
		try{
			
			int intStartRow=0;//查询条数计算，用于绑定查询条数
			int intRowCount=0;
			if(p_PageNumber>0)
			{

				if(p_PageNumber==1)
					intStartRow=1;
				else if(p_PageNumber>1)
					intStartRow=(p_PageNumber-1)*p_StepRow+1;
				else
					intStartRow=0;
				intRowCount=p_PageNumber*p_StepRow;
			}
			
			String[] baseValueList=parseBaseParmeter.getParmeterValue();
			String[] dealValueList=parseDealParmeter.getParmeterValue();
			int baselen=0;
			int deallen=0;
			if(baseValueList!=null)
				baselen=baseValueList.length;
			if(dealValueList!=null)
				deallen=dealValueList.length;
			
			int bindIndex=0;
			
			prestm=m_dbConsole.getConn().prepareStatement(sqlRowCount.toString());
			for(int i=0;i<baselen;i++)
			{	
				bindIndex++;
				prestm.setString(bindIndex, baseValueList[i] ); //为绑定变量赋值
			}
			//System.out.println("baselen::"+baselen);
			for(int i=0;i<deallen;i++)
			{	bindIndex++;
				prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
			}
			//System.out.println("deallen::"+deallen);
			
			String[] optionType=m_ParBindString.getBindValue();
			if(optionType!=null)
			{
				int optlen=0;
				optlen=optionType.length;
				for(int i=0;i<optlen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, optionType[i] ); //为绑定变量赋值
				}		
				//System.out.println("optlen::"+optlen);
			}
			
			
			if(p_ParBaseModel.getIsArchive()!=0 && p_ParBaseModel.getIsArchive()!=1)
			{
				for(int i=0;i<baselen;i++)
				{	
					bindIndex++;
					prestm.setString(bindIndex, baseValueList[i] ); //为绑定变量赋值
				}
				for(int i=0;i<deallen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
				}			
				optionType=m_ParBindString.getBindValue();
				if(optionType!=null)
				{
					int optlen=0;
					optlen=optionType.length;
					for(int i=0;i<optlen;i++)
					{	bindIndex++;
						prestm.setString(bindIndex, optionType[i] ); //为绑定变量赋值
					}				
				}
			}

			//System.out.println("BaseAndProcess-getListForBind(ParBaseModel ParDealProcess) sqlRowCount:"+sqlRowCount.toString());			
			//System.out.println();
			m_BaseRs=prestm.executeQuery();
			if(m_BaseRs!=null)
			{
				if(m_BaseRs.next())
				{
					intQueryResultRows=m_BaseRs.getInt("rownums");
				}			
			}
			
			if(intQueryResultRows<=0)
				return null;
			
				
			m_BaseRs.close();
			prestm.close();
			
			bindIndex=0;
			prestm=m_dbConsole.getConn().prepareStatement(strSql);
			//System.out.println("BaseAndProcess-getListForBind(ParBaseModel ParDealProcess) strSql:"+strSql);
			for(int i=0;i<baselen;i++)
			{	
				bindIndex++;
				prestm.setString(bindIndex, baseValueList[i] ); //为绑定变量赋值
			}
			for(int i=0;i<deallen;i++)
			{	bindIndex++;
				prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
			}
			
			if(optionType!=null)
			{
				int optlen=0;
				optlen=optionType.length;
				for(int i=0;i<optlen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, optionType[i] ); //为绑定变量赋值
				}				
			}	
				
			if(p_ParBaseModel.getIsArchive()!=0 && p_ParBaseModel.getIsArchive()!=1)
			{
				for(int i=0;i<baselen;i++)
				{	
					bindIndex++;
					prestm.setString(bindIndex, baseValueList[i] ); //为绑定变量赋值
				}
				for(int i=0;i<deallen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
				}	
				if(optionType!=null)
				{
					int optlen=0;
					optlen=optionType.length;
					for(int i=0;i<optlen;i++)
					{	bindIndex++;
						prestm.setString(bindIndex, optionType[i] ); //为绑定变量赋值
					}				
				}				
			}
			
			if(p_PageNumber>0)//绑定查询数量变量
			{
				bindIndex++;
				prestm.setString(bindIndex,String.valueOf(intRowCount));
				bindIndex++;
				prestm.setString(bindIndex,String.valueOf(intStartRow));
			}
			
			m_BaseRs=prestm.executeQuery();
			if(baseSelectFiled.equals("") && dealSelectFiled.equals(""))
				m_Relist=this.ConvertRsToList(m_BaseRs);
			else
				m_Relist=ConvertRsToListForBind(m_BaseRs);	
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationForBaseAndProcess.getListForBind(ParBaseModel,ParDealProcess) 方法"+ex.getMessage());
			System.err.println("strsql:"+strSql);
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try {
				if (m_BaseRs != null)
					m_BaseRs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (prestm != null)
					prestm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return m_Relist;
	}	
	
	
}
