package cn.com.ultrapower.ultrawf.models.process;

import java.util.*;
import java.sql.*;

import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.config.ParBaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.queryinterface.*;
import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.ultrawf.share.constants.*;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseFieldInfo;
  
import cn.com.ultrapower.ultrawf.share.queryanalyse.*;
/**
 * 基础工单信息处理
 * @author  徐发球
 * @Date	2006-11-10
 */
public class Base {

	private int intQueryResultRows=0;
	//返回查询的总行数行数

	//工单特有字段Hashtable
	private Hashtable hsOwnerFiled=null;
	
	public BaseInforModel getBaseModel(String baseID, String baseSchema, int isArchive)
	{
		BaseInforModel bModel = new BaseInforModel();
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(baseSchema);
		String sqlString = "select C1 as BaseID" +
				", C700000001 as BaseSchema" +
				", C700000002 as BaseName" +
				", C700000003 as BaseSN" +
				", C700000022 as BaseTplID" +
				" from " + tablename + " where C1 = '" + baseID + "'";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		try
		{
			if(rs.next())
			{
				bModel.setBaseID(rs.getString("BaseID"));
				bModel.setBaseSchema(rs.getString("BaseSchema"));
				bModel.setBaseName(rs.getString("BaseName"));
				bModel.setBaseSN(rs.getString("BaseSN"));
				bModel.setBaseTplID(rs.getString("BaseTplID"));
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
		return bModel;
	}
	
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	/**
	 * 根据工单ID实例化一个工单类的对象
	 */
	public Base() {}

	public BaseModel GetOneForKey(String p_BaseSchema,String p_BaseID)
	{
		return GetOneForKey(p_BaseSchema,p_BaseID,999);
	}
	/**
	 * 根据关键字查询工单信息
	 * @author 
	 * @Date
	 * @param str_BaseID
	 * @param str_BaseSchema
	 */
	public BaseModel GetOneForKey(String p_BaseSchema,String p_BaseID,int p_isArchive) 
	{
		String key;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		BaseModel m_BaseModel=null;
		Statement stm=null;
		ResultSet objRs =null;
		try {
			ParBaseModel p_ParBaseModel=new ParBaseModel();
			p_ParBaseModel.setBaseSchema(p_BaseSchema);
			p_ParBaseModel.setBaseID(p_BaseID);
			
			String strSql=this.GetSelectSql(p_ParBaseModel);
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, strSql);

			if (objRs!=null && objRs.next()) {
				m_BaseModel=new BaseModel();
				m_BaseModel.setBaseID(objRs.getString("BaseID"));
				m_BaseModel.setBaseTplID(objRs.getString("BaseTplID"));
				m_BaseModel.setBaseSchema(objRs.getString("BaseSchema"));
				m_BaseModel.setBaseName(objRs.getString("BaseName"));
				m_BaseModel.setBaseSN(objRs.getString("BaseSN"));
				m_BaseModel.setBaseCreatorFullName(objRs.getString("BaseCreatorFullName"));
				m_BaseModel.setBaseCreatorLoginName(objRs.getString("BaseCreatorLoginName"));
				m_BaseModel.setBaseCreateDate(objRs.getLong("BaseCreateDate"));
				m_BaseModel.setBaseSendDate(objRs.getLong("BaseSendDate"));
				m_BaseModel.setBaseFinishDate(objRs.getLong("BaseFinishDate"));
				m_BaseModel.setBaseCloseDate(objRs.getLong("BaseCloseDate"));
				m_BaseModel.setBaseStatus(objRs.getString("BaseStatus"));
				//m_BaseModel.setBaseSummary(objRs.getString("BaseSummary"));
				//m_BaseModel.setBaseAssigneeS(objRs.getString("BaseAssigneeS"));
				//m_BaseModel.setBaseAuditingerS(objRs.getString("BaseAuditingerS"));
				//m_BaseModel.setBaseItems(objRs.getString("BaseItems"));
				//m_BaseModel.setBasePriority(objRs.getString("BasePriority"));
				//m_BaseModel.setBaseIsAllowLogGroup(objRs.getInt("BaseIsAllowLogGroup"));
				//m_BaseModel.setBaseAcceptOutTime(objRs.getLong("BaseAcceptOutTime"));
				//m_BaseModel.setBaseDealOutTime(objRs.getLong("BaseDealOutTime"));
				//m_BaseModel.setBaseDescrption(objRs.getString("BaseDescrption"));
				
				//m_BaseModel.setBaseResult(objRs.getString("BaseResult"));
				m_BaseModel.setBaseCloseSatisfy(objRs.getString("BaseCloseSatisfy"));
				
				m_BaseModel.setIsArchive(objRs.getInt("BaseIsArchive"));
				m_BaseModel.setBaseAuditinglLinkName(objRs.getString("BaseAuditinglLinkName"));
				m_BaseModel.setBaseAuditingProcessName(objRs.getString("BaseAuditingProcessName"));
				m_BaseModel.setBaseAuditingProcessLogName(objRs.getString("BaseAuditingProcessLogName"));
				m_BaseModel.setBaseDealLinkName(objRs.getString("BaseDealLinkName"));
				m_BaseModel.setBaseDealProcessName(objRs.getString("BaseDealProcessName"));
				m_BaseModel.setBaseDealProcessLogName(objRs.getString("BaseDealProcessLogName"));
				
				 if(this.hsOwnerFiled!=null)
				 {
					 String isColb="";
					 ProcessUtil m_ProcessUtil=new ProcessUtil();					 
					 for(Iterator it=hsOwnerFiled.keySet().iterator();it.hasNext();)
					 {
						 key   =   (String)it.next();
						 isColb=FormatString.CheckNullString(hsOwnerFiled.get(key));
						 //如果是大字段
						 if(isColb.trim().equals("1"))
						 {
							 Clob m_clob=objRs.getClob(key);
							 m_BaseModel.SetOwnerFiled(key,m_ProcessUtil.getClobString(m_clob));
						 }
						 else
							 m_BaseModel.SetOwnerFiled(key,objRs.getString(key));
						 
					 }
				 }				
			}//if(objRs.next())
			//return getBaseDealOutTime().toString();
			
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
		finally {
			try {
				if (objRs != null)
					objRs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}		
		return m_BaseModel;
	}
	/**
	 * 根据记录集返回BaseModel类的List
	 * @param p_BaseRs
	 * @return
	 * @throws Exception
	 */
	private  List ConvertRsToList(ResultSet p_BaseRs)  throws Exception
	{
		if(p_BaseRs==null) return null;
		String key;
		List list = new ArrayList();
		//ResultSetMetaData   rsmd=p_BaseRs.getMetaData();
		//System.out.println(rsmd.getColumnClassName(1));
		//System.out.println(rsmd.getColumnType(1));	
		//System.out.println(java.sql.Types.CLOB);
		//System.out.println(java.sql.Types.VARCHAR);
		
		
		try{
			while(p_BaseRs.next())
			{
				  
				
				 BaseModel m_Base=new BaseModel();
				 
				 m_Base.setBaseID(p_BaseRs.getString("BaseID"));
				 m_Base.setBaseTplID(p_BaseRs.getString("BaseTplID"));
				 m_Base.setBaseSchema(p_BaseRs.getString("BaseSchema"));
				 m_Base.setBaseName(p_BaseRs.getString("BaseName"));
				 m_Base.setBaseSN(p_BaseRs.getString("BaseSN"));
				 m_Base.setBaseCreatorFullName(p_BaseRs.getString("BaseCreatorFullName"));
				 m_Base.setBaseCreatorLoginName(p_BaseRs.getString("BaseCreatorLoginName"));
				 m_Base.setBaseCreateDate(p_BaseRs.getLong("BaseCreateDate"));
				 m_Base.setBaseSendDate(p_BaseRs.getLong("BaseSendDate"));
				 
				 m_Base.setBaseFinishDate(p_BaseRs.getLong("BaseFinishDate"));
				 m_Base.setBaseCloseDate(p_BaseRs.getLong("BaseCloseDate"));
				 m_Base.setBaseStatus(p_BaseRs.getString("BaseStatus"));
				 //m_Base.setBaseSummary(p_BaseRs.getString("BaseSummary"));
				 //m_Base.setBaseAssigneeS(p_BaseRs.getString("BaseAssigneeS"));
				// m_Base.setBaseAuditingerS(p_BaseRs.getString("BaseAuditingerS"));
				// m_Base.setBaseItems(p_BaseRs.getString("BaseItems"));
				 //m_Base.setBasePriority(p_BaseRs.getString("BasePriority"));
				// m_Base.setBaseIsAllowLogGroup(p_BaseRs.getInt("BaseIsAllowLogGroup"));
				// m_Base.setBaseAcceptOutTime(p_BaseRs.getLong("BaseAcceptOutTime"));
				// m_Base.setBaseDealOutTime(p_BaseRs.getLong("BaseDealOutTime"));
				// m_Base.setBaseDescrption(p_BaseRs.getString("BaseDescrption"));
				 //m_Base.setBaseResult(p_BaseRs.getString("BaseResult"));
				 m_Base.setBaseCloseSatisfy(p_BaseRs.getString("BaseCloseSatisfy"));
				 m_Base.setIsArchive(p_BaseRs.getInt("BaseIsArchive"));
				 m_Base.setBaseAuditinglLinkName(p_BaseRs.getString("BaseAuditinglLinkName"));
				 m_Base.setBaseAuditingProcessName(p_BaseRs.getString("BaseAuditingProcessName"));
				 m_Base.setBaseAuditingProcessLogName(p_BaseRs.getString("BaseAuditingProcessLogName"));
				 m_Base.setBaseDealLinkName(p_BaseRs.getString("BaseDealLinkName"));
				 m_Base.setBaseDealProcessName(p_BaseRs.getString("BaseDealProcessName"));
				 m_Base.setBaseDealProcessLogName(p_BaseRs.getString("BaseDealProcessLogName"));				
				 
				 if(this.hsOwnerFiled!=null)
				 {
					 String isColb="";
					 ProcessUtil m_ProcessUtil=new ProcessUtil();
					 for(Iterator it=hsOwnerFiled.keySet().iterator();it.hasNext();)
					 {
						 key   =   (String)it.next();
						 isColb=FormatString.CheckNullString(hsOwnerFiled.get(key));
						 //如果是大字段
						 if(isColb.trim().equals("1"))
						 {
							 Clob m_clob=p_BaseRs.getClob(key);
							 m_Base.SetOwnerFiled(key,m_ProcessUtil.getClobString(m_clob));
						 }
						 else
							 m_Base.SetOwnerFiled(key,p_BaseRs.getString(key));
						 //System.out.println("Key: "+key);
						 //hsOwnerFiled.put(key,p_BaseRs.getString(key));
						 //System.out.println("Key: "+p_BaseRs.getString(key));
						 
						
					 }
				 }

				 list.add(m_Base);
			}//while(p_BaseRs.next())
			
		}catch(Exception ex)
		{
			System.err.println("Base.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
			throw ex;
		}
		finally
		{
			p_BaseRs.close();
		}
		
		return list;
	}
	
	public int getColIndex(ResultSetMetaData p_rsmd,String p_ColName)
	{
		int colIndex=0;
		try{
			for(int col=1;col<=p_rsmd.getColumnCount();col++)
			{
				if(p_rsmd.getColumnName(col).toUpperCase().equals(p_ColName.toUpperCase()))
				{
					colIndex=col;
					break;
				}
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			colIndex=0;
		}
		return colIndex;
	}
	
	/**
	 * 工单特有字段的字段查询

	 * 工单特有字段是每工单自己独有的字段，其它工单没有的字段。

	 * @param p_BaseSchema
	 * @return
	 */
	private String GetOwnerFiledSqlString(String p_BaseSchema)
	{
		StringBuffer stringBuffer=new StringBuffer();
		if(p_BaseSchema==null)
		{
			p_BaseSchema="";
		}
	
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
			stringBuffer.append(",C700000014 as BaseItems");
			stringBuffer.append(",C700000015 as BasePriority");
			stringBuffer.append(",C700000011 as BaseSummary,C700000017 as BaseAcceptOutTime,C700000018 as BaseDealOutTime");
			stringBuffer.append(",C700000016 as BaseIsAllowLogGroup,C700000019 as BaseDescrption");
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
						hsOwnerFiled.put(m_BaseOwnFieldInfoModel.GetBase_field_DBName(),String.valueOf(m_BaseOwnFieldInfoModel.getVarcharFieldeIsExceed()));
						stringBuffer.append(",C"+m_BaseOwnFieldInfoModel.GetBase_field_ID()+" as "+m_BaseOwnFieldInfoModel.GetBase_field_DBName());
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
	private String GetSelectSql(ParBaseModel p_ParBaseModel)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_ParBaseModel==null)
			p_ParBaseModel=new ParBaseModel();
		
		if(p_ParBaseModel.getIsArchive()==0||p_ParBaseModel.getIsArchive()==1)
		{
			sqlString.append(" select * from (");
			sqlString.append(GetSelectSqlExt(p_ParBaseModel));
			sqlString.append(") Base where 1=1 ");
		}
		else 
		{
			int isArchive=p_ParBaseModel.getIsArchive();
			sqlString.append(" select * from (");
			//生成当前活动信息的sql
			p_ParBaseModel.setIsArchive(0);			
			sqlString.append(GetSelectSqlExt(p_ParBaseModel));
			sqlString.append(" union all ");
			//生成当前历史信息的sql
			p_ParBaseModel.setIsArchive(1);
			sqlString.append(GetSelectSqlExt(p_ParBaseModel));
			sqlString.append(") Base where 1=1 ");
			p_ParBaseModel.setIsArchive(isArchive);
			
		}
		
		return sqlString.toString();
		
	}	
	
	/**
	 * 返回查询工单信息的SQL
	 * @return
	 */
	private  String GetSelectSqlExt(ParBaseModel p_ParBaseModel)
	{
		String strBaseScheam=p_ParBaseModel.getBaseSchema();
		StringBuffer sqlString = new StringBuffer();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		
		String strBaseInfo="";
		
		
		if(!strBaseScheam.trim().equals(""))
		{
			String[] SplAry=strBaseScheam.split(":");;
			String key=SplAry[0].trim().toUpperCase();
			//判断是否是用于只做查询条件

			if(key.equals("IN")||key.equals("LIKE")||key.equals("NOT")||key.equals("OR"))
				strBaseScheam="";
		}		
		if(!strBaseScheam.trim().equals(""))
		{
			//某类工单表
			strBaseInfo=m_RemedyDBOp.GetRemedyTableName(strBaseScheam);
		}
		else
			//工单集合表 从UltraProcess:App_Base_Infor中查询  
			strBaseInfo=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseInfor);
			
		
		String strOwnerSql=GetOwnerFiledSqlString(strBaseScheam);
		//System.out.println("ownerSql:"+strOwnerSql);
		
		try{
				//查询sql
				sqlString.append(" select ");
				
				//
				if(!strBaseScheam.trim().equals(""))
					sqlString.append(" C1 as BaseID ");
				else
					sqlString.append(" C700000000 as BaseID ");
				sqlString.append(",C700000022 as BaseTplID,C700000001 as BaseSchema,C700000002 as BaseName,C700000003 as BaseSN");
				sqlString.append(",C700000004 as BaseCreatorFullName,C700000005 as BaseCreatorLoginName,C700000006 as BaseCreateDate");
				sqlString.append(",C700000007 as BaseSendDate,C700000008 as BaseFinishDate,C700000009 as BaseCloseDate");
				sqlString.append(",C700000010 as BaseStatus");
				//sqlString.append(",C700000014 as BaseItems");
				//sqlString.append(",C700000015 as BasePriority");
				//sqlString.append(",C700000011 as BaseSummary,C700000017 as BaseAcceptOutTime,C700000018 as BaseDealOutTime");
				//sqlString.append(",C700000016 as BaseIsAllowLogGroup,C700000019 as BaseDescrption");
				
				
				//sqlString.append(",C700000020 as BaseResult ");
				sqlString.append(",C700000021 as BaseCloseSatisfy ");
				//BaseIsArchive	700000023 工单是否已经存档	0：表示不是（默认）	1：表示是
				sqlString.append(",C700000023 as BaseIsArchive ");
				sqlString.append(",C700000024 as BaseAuditinglLinkName,C700000025 as BaseAuditingProcessName,C700000026 as BaseAuditingProcessLogName ");
				sqlString.append(",C700000027 as BaseDealLinkName,C700000028 as BaseDealProcessName,C700000029 as BaseDealProcessLogName ");
				sqlString.append(strOwnerSql);
				// T90 UltraProcess:App_Base 工单信息表 T74 UltraProcess:App_DealProcess 处理环节
				
				sqlString.append(" from "+strBaseInfo + " Base ");
				sqlString.append(" where 1=1 ");
				if(p_ParBaseModel!=null)
					sqlString.append(p_ParBaseModel.GetWhereSql(""));

		}catch(Exception ex)
		{
			
			System.err.println("Base.GetBaseSelectSql 方法："+ex.getMessage());
			ex.printStackTrace();				
		}

		return(sqlString.toString());
	}
	
	/**
	 * 静态的得到某个工单信息列表方法，返回一个List（Base对象的数组）
	 * @param str_BaseSchema
	 * @return
	 */
	public  List GetList(String str_BaseSchema,int p_PageNumber,int p_StepRow)
	{
		
		List m_list;
		ParBaseModel p_ParBaseModel=new ParBaseModel();
		p_ParBaseModel.setBaseSchema(str_BaseSchema);
		m_list=GetList(p_ParBaseModel,p_PageNumber,p_StepRow);		
		return m_list;
	}
	
	/**
	 * 静态的得到某类工单所有待阅环节列表方法，返回一个List（Base对象的数组）
	 * @param str_BaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListWaitConfirm(String str_UserLoginName,int p_PageNumber, int p_StepRow) throws Exception
	{
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		List m_Relist=new ArrayList();
		
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitConfirm);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	
	/**
	 * 静态的得到某类工单所有待阅环节列表方法，返回一个List（Base对象的数组）
	 * @param str_BaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListWaitConfirm(String str_BaseSchema , String str_UserLoginName,int p_PageNumber, int p_StepRow) throws Exception
	{
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		List m_Relist=new ArrayList();
		
		m_ParBaseModel.setBaseSchema(str_BaseSchema);
		
		m_ParDealProcess.setProcessBaseSchema(str_BaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitConfirm);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
		
	}
	
	/**
	 * 静态的得到所有工单所有待办(主办、协办)环节列表方法，返回一个List（Base对象的数组）
	 * 查询某人等待处理(主办、协办)的所有工单
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListWaitDeal(String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		List m_Relist=new ArrayList();
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitDeal);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 静态的得到某类工单所有待办(主办、协办)环节列表方法，返回一个List（Base对象的数组）
	 * 查询某人等待处理(主办、协办)的所有工单
	 * @param str_BaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public List GetListWaitDeal(String str_BaseSchema,
			String str_UserLoginName, int p_PageNumber, int p_StepRow)
	{
		List m_Relist=new ArrayList();
	
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParBaseModel.setBaseSchema(str_BaseSchema);
		
		m_ParDealProcess.setProcessBaseSchema(str_BaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitDeal);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;		
	}
	/**
	 * 静态的得到所有工单所有处理中环节列表方法，返回一个List（Base对象的数组）
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListDealing(String str_UserLoginName,int p_PageNumber,int p_StepRow) 
	{
		List m_Relist=new ArrayList();
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessDealing);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	
	/**
	 * 静态的得到某类工单所有处理中环节列表方法，返回一个List（Base对象的数组）
	 * @param str_BaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public List GetListDealing(String str_BaseSchema, String str_UserLoginName,
			int p_PageNumber, int p_StepRow)
	{
		List m_Relist=new ArrayList();
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParBaseModel.setBaseSchema(str_BaseSchema);
		m_ParDealProcess.setProcessBaseSchema(str_BaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessDealing);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 静态的得到所有工单所有我建立的环节（不包含复制品）列表方法，返回一个List（Base对象的数组）
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListMyCreate(String str_UserLoginName,int p_PageNumber,int p_StepRow) 
	{
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseCreatorLoginName(str_UserLoginName);
		return this.GetList(m_ParBaseModel,p_PageNumber,p_StepRow);
	}
	
	/**
	 * 静态的得到某类工单所有我建立的环节（不包含复制品）列表方法，返回一个List（Base对象的数组）
	 * @param str_BaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public List GetListMyCreate(String str_BaseSchema,
			String str_UserLoginName, int p_PageNumber, int p_StepRow)
	{
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseCreatorLoginName(str_UserLoginName);
		m_ParBaseModel.setBaseSchema(str_BaseSchema);
		return this.GetList(m_ParBaseModel,p_PageNumber,p_StepRow);
	}
	
	/**
	 * 静态的得到所有工单所有待审批环节列表方法，返回一个List（Base对象的数组）
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListWaitAuditing(String str_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=new ArrayList();
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();

		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitAuditing);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 静态的得到某类工单所有待办环节列表方法，返回一个List（Base对象的数组）
	 * @param str_BaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public List GetListWaitAuditing(String str_BaseSchema,
			String str_UserLoginName, int p_PageNumber, int p_StepRow)
	{
		List m_Relist=new ArrayList();
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParBaseModel.setBaseSchema(str_BaseSchema);
		
		m_ParDealProcess.setProcessBaseSchema(str_BaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitAuditing);
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		m_Relist=m_QueryIntegrationForBaseAndProcess.GetList(m_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 根据条件查询数据返回BaseModel类的List
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		
		List m_Relist=null;
		
		//StringBuffer sqlString = new StringBuffer();
		String strSql;
		strSql=this.GetSelectSql(p_ParBaseModel);
		
		if(strSql.trim().equals(""))
		{	
			return m_Relist;
		}
		//System.out.println("Base.GetList SQL语句："+strSql);
		GetQueryResultRows(strSql);
		strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		//strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		Statement stm=null;
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_BaseRs);	
		}catch(Exception ex)
		{
			System.err.println("Base.GetList 方法"+ex.getMessage());
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
	
	
	/**
	 * 根据条件查询数据返回BaseModel类的List
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		
		List m_Relist=null;
		if(p_ParBaseModel==null)
			p_ParBaseModel=new ParBaseModel();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		StringBuffer sqlString = new StringBuffer();
		String strSql;
		String strTblName="";
		if(p_ParDealProcess!=null)
		{
			if(p_ParBaseModel.getIsArchive()==0||p_ParBaseModel.getIsArchive()==1)
			{
				//p_ParDealProcess.setIsArchive(p_ParBaseModel.getIsArchive());
				//获取Base查询Sql
				sqlString.append(GetSelectSqlExt(p_ParBaseModel));
				//如果是审批

				if(p_ParDealProcess.getFlagType().trim().equals("3"))
				{	
					
					if(p_ParBaseModel.getIsArchive()==1)
						//获取历史表名
						strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
					else if(p_ParBaseModel.getIsArchive()==0)
						//获取活动表名
						strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);					
				}else
				{
					if(p_ParBaseModel.getIsArchive()==1)
						strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
					else if(p_ParBaseModel.getIsArchive()==0)
						strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				}
				/**
				 * 处理逻辑
				 * 1：从处理环节表中查询出相应Baseid和BaseSchema
				 * 2：根据Baseid和BaseSchema查询出工单信息(在Base类中完成)
				 */
				sqlString.append(" and exists ( ");
				sqlString.append("select 1 from "+strTblName+" DealProcess  " );
				//处理环节表：BaseID 700020001 指向主工单处理过程记录的指针
				// ProcessBaseSchema	700020002	指向主工单记录的指针
				if(!p_ParBaseModel.getBaseSchema().trim().equals(""))
				{
					////	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
					sqlString.append(" where DealProcess.C700020001=Base.C1 ");
					p_ParDealProcess.setProcessBaseSchema(p_ParBaseModel.getBaseSchema());
				}
				else
					////因为改为从UltraProcess:App_Base_Infor中查询，所以Baseid为700000000
					sqlString.append(" where DealProcess.C700020001=Base.C700000000 ");
				
				sqlString.append(" and DealProcess.C700020002=Base.C700000001 ");
				
				sqlString.append(p_ParDealProcess.GetWhereSql("DealProcess"));
				sqlString.append(" ) ");
			}
			else//if(p_ParBaseModel.getIsArchive()==0||p_ParBaseModel.getIsArchive()==1)
			{
				int isArchive=p_ParBaseModel.getIsArchive();
				
				//查询当前活动表中的信息

				p_ParBaseModel.setIsArchive(0);
				p_ParDealProcess.setIsArchive(0);				
				sqlString.append(GetSelectSqlExt(p_ParBaseModel));
				sqlString.append(" and exists ( ");
				//if(p_ParDealProcess!=null)
				int m_intProcessType=p_ParDealProcess.getProcessOptionalType();				
				//如果是审批				
				//if(p_ParDealProcess.getFlagType().trim().equals("3"))
				if(m_intProcessType==Constants.ProcessWaitAuditing||m_intProcessType==Constants.ProcessMyAuditingAndIsFinished||p_ParDealProcess.getFlagType().trim().equals("3"))
					strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
				else
					strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				sqlString.append("select 1 from "+strTblName+" DealProcess " );
				
				//处理环节表：BaseID 700020001 指向主工单处理过程记录的指针
				// ProcessBaseSchema	700020002	指向主工单记录的指针
				if(!p_ParBaseModel.getBaseSchema().trim().equals(""))
				{
					////	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
					sqlString.append(" where DealProcess.C700020001=Base.C1 ");
					p_ParDealProcess.setProcessBaseSchema(p_ParBaseModel.getBaseSchema());
				}
				else
					////因为改为从UltraProcess:App_Base_Infor中查询，所以Baseid为700000000
					sqlString.append(" where DealProcess.C700020001=Base.C700000000 ");
				
				sqlString.append(" and DealProcess.C700020002=Base.C700000001 ");
				sqlString.append(p_ParDealProcess.GetWhereSql("DealProcess"));
				sqlString.append(" ) ");
				
				sqlString.append(" union all ");
				//查询存入历史数据表中的信息

				p_ParBaseModel.setIsArchive(1);
				p_ParDealProcess.setIsArchive(1);
				sqlString.append(GetSelectSqlExt(p_ParBaseModel));
				sqlString.append(" and exists ( ");				
				//如果是审批

				if(p_ParDealProcess.getFlagType().trim().equals("3"))
					strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
				else
					strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
				sqlString.append("select 1 from "+strTblName+" DealProcess " );
				//处理环节表：BaseID 700020001 指向主工单处理过程记录的指针
				// ProcessBaseSchema	700020002	指向主工单记录的指针
				if(!p_ParBaseModel.getBaseSchema().trim().equals(""))
				{
					////	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
					sqlString.append(" where DealProcess.C700020001=Base.C1 ");
					p_ParDealProcess.setProcessBaseSchema(p_ParBaseModel.getBaseSchema());
				}
				else
					////因为改为从UltraProcess:App_Base_Infor中查询，所以Baseid为700000000
					sqlString.append(" where DealProcess.C700020001=Base.C700000000 ");
				
				sqlString.append(" and DealProcess.C700020002=Base.C700000001 ");
				
				sqlString.append(p_ParDealProcess.GetWhereSql("DealProcess"));	
				
				sqlString.append(" ) ");
				
				p_ParDealProcess.setIsArchive(isArchive);
				
			}//if(p_ParBaseModel.getIsArchive()==0||p_ParBaseModel.getIsArchive()==1)
			
		}
		else
		{
			sqlString.append(GetSelectSql(p_ParBaseModel));
		}//if(p_ParDealProcess!=null)
		
		strSql=sqlString.toString();
		if(strSql.trim().equals(""))
		{	
			return null;
		}
		//System.out.println("Base.GetList SQL语句："+strSql);
		GetQueryResultRows(strSql);
		//加排序条件

		sqlString.append(p_ParBaseModel.getOrderbyFiledNameString());
		strSql=sqlString.toString();
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		
		Statement stm=null;
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_BaseRs);	
		}catch(Exception ex)
		{
			System.err.println("Base.GetList 方法"+ex.getMessage());
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
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return m_Relist;
		
	}	
	

	/**
	 *返回查询的记录总数量(用于计算分页时的页数)
	 * @param p_strSql
	 */
	private void GetQueryResultRows(String p_strSql)
	{
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(p_strSql);
		//System.out.println("Base.GetQueryResultRows SQL语句："+p_strSql);
		sqlString.append(" )");
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_BaseRs.next())
			{
				intQueryResultRows=m_BaseRs.getInt("rownums");
			}
		}catch(Exception ex)
		{
			
			System.err.println("Base.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
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
			
	}
	
	/**
	 * 超时多少分钟的工单
	 * @param p_UserLoginName
	 * @param p_Minute
	 * @return
	 */
	public int GetOverTimeBaseCountInMinute(String p_UserLoginName,int p_OverMinute)
	{
		int intRowCount=0;
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("select count(*) from ");
		
		return intRowCount;
	}
	public int GetOverTimeBaseCountInMinute(String p_BaseSchema,String p_UserLoginName,int p_OverMinute)
	{
		int intRowCount=0;		
		return intRowCount;		
	}
	
	/**
	 * 
	 * @param p_hashTable
	 * @param p_BaseSchema
	 * @return
	 */
	public String Insert(String p_BaseSchema,Map p_hashTable)
	{
		List m_List=new ArrayList();
		String   key;	
		BaseFieldInfo baseFieldInfo = null;
		for(Iterator it=p_hashTable.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)it.next();
			RemedyFieldInfo m_RemedyFieldInfo = null;			
			baseFieldInfo = (BaseFieldInfo)p_hashTable.get(key);
	        if(baseFieldInfo!=null){
	        	m_RemedyFieldInfo=new RemedyFieldInfo();
	        	m_RemedyFieldInfo.setIntFieldType(baseFieldInfo.getIntFieldType());
	          	m_RemedyFieldInfo.setStrFieldID(baseFieldInfo.getStrFieldID());
	        	if (baseFieldInfo.getStrFieldValue() != null && baseFieldInfo.getStrFieldValue().equals("null")==false)
	        	{
		          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	  
	        	}
	        	else
	        	{
	        		m_RemedyFieldInfo.setStrFieldValue(null);	  
	        	}
	          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	      	  
	          	m_List.add(m_RemedyFieldInfo);	        	
	        }		
	    }
	  
		String strReturnID=Insert(p_BaseSchema,m_List);
		return strReturnID;
	}
	
	/**
	 * 
	 * @param p_FieldInfoList　修改的
	 * @param p_BaseSchema 工单类别
	 * @return
	 */
	public String Insert(String p_BaseSchema,List p_FieldInfoList)
	{
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strReturnID=RemedyOp.FormDataInsertReturnID(p_BaseSchema,p_FieldInfoList);
		RemedyOp.RemedyLogout();
		return strReturnID;
	}
	
	
	public boolean Update(String p_BaseSchema,String strModifyEntryId,Map p_hashTable)
	{
		boolean blnRe=true;
		List m_List=new ArrayList();
		String   key;
		BaseFieldInfo baseFieldInfo = null;
		
		for(Iterator it=p_hashTable.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)it.next();
			RemedyFieldInfo m_RemedyFieldInfo = null;			
			baseFieldInfo = (BaseFieldInfo)p_hashTable.get(key);
	        if(baseFieldInfo!=null  && baseFieldInfo.getStrFieldID()!="1"){
	        	m_RemedyFieldInfo=new RemedyFieldInfo();
	        	m_RemedyFieldInfo.setIntFieldType(baseFieldInfo.getIntFieldType());
	          	m_RemedyFieldInfo.setStrFieldID(baseFieldInfo.getStrFieldID());
	          	if (baseFieldInfo.getStrFieldValue() != null && baseFieldInfo.getStrFieldValue().equals("null")==false)
	        	{
		          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	  
	        	}
	        	else
	        	{
	        		m_RemedyFieldInfo.setStrFieldValue(null);	  
	        	}
	          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	      	  
	          	m_List.add(m_RemedyFieldInfo);	        	
	        }
	    }
	  if(m_List.size()>0)
		  blnRe=Update(p_BaseSchema,strModifyEntryId,m_List);
		return blnRe;
	}	
	
	/**
	 * 保存工单信息
	 * @param p_BaseSchema
	 * @param strModifyEntryId
	 * @param p_FieldInfoList
	 * @return
	 */
	public boolean Update(String p_BaseSchema,String strModifyEntryId,List p_FieldInfoList)
	{
		boolean blnRe=true;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		blnRe=RemedyOp.FormDataModify(p_BaseSchema,strModifyEntryId,p_FieldInfoList);

		RemedyOp.RemedyLogout();
		return blnRe;
	}

	/**
	 * 更新C2字段信息
	 * @param p_BaseSchema
	 * @param p_BaseID
	 * @param p_LoginName
	 * @return
	 */
	public boolean UpdateC2(String p_BaseSchema,String p_BaseID, String p_LoginName,int p_Operation)
	{
		boolean blnRe=true;
	
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblName=m_RemedyDBOp.GetRemedyTableName(p_BaseSchema);
		String strSql;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		try{
			stm=m_dbConsole.GetStatement();
			if(p_Operation==0)
			{
				//更新DealProcess的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseCategory);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				//650000002	BaseCategorySchama
				strSql+=" where C650000002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);
				m_dbConsole.closeConn();
			}
			else
			{
				//更新工单C2信息
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'"+" where C1='"+p_BaseID+"'";
				m_dbConsole.executeNonQuery(stm,strSql);
				
				//更新DealProcess的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020001='"+p_BaseID+"' and C700020002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
				//更新DealLink的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealLink);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020501='"+p_BaseID+"' and C700020502='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
				//更新AuditingProcess的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020001='"+p_BaseID+"' and C700020002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);	
				
				//更新AuditingLink的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingLink);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020501='"+p_BaseID+"' and C700020502='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);			
				
				//更新BaseAttachment（附件表）的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseAttachment);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C650000001='"+p_BaseID+"' and C650000002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);			
				
				//更新TblAppBaseInfor（工单信息表）的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppBaseInfor);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700000000='"+p_BaseID+"' and C700000001='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
			}
		}catch(Exception ex)
		{
			System.err.println("Base.UpdateC2 方法："+ex.getMessage());
			ex.printStackTrace();			
		}
		finally
		{
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
		return blnRe;
	}
	
	
	public boolean delete(ParBaseModel p_ParBaseModel)
	{
		List m_list=this.GetList(p_ParBaseModel,0,0);
		int rowCount=0;
		if(m_list!=null)
			rowCount=m_list.size();
		if(rowCount<=0)
			return false;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strFormName=p_ParBaseModel.getBaseSchema();
		
		try{
			

			BaseModel m_BaseModel;
			for(int row=0;row<rowCount;row++)
			{
				m_BaseModel=(BaseModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_BaseModel.getBaseID());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		finally{
			
			RemedyOp.RemedyLogout();
		}
		return true;
	}
	
	
	
	/**
	 * 根据处理日志查询各种动作的工单

	 * @param p_ParBaseModel
	 * @param p_ParDealProcessLogModel
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List getProcessDealBaseList(ParBaseModel p_ParBaseModel,ParDealProcessLogModel p_ParDealProcessLogModel,int p_PageNumber,int p_StepRow)
	{
		List baseList=null;
		String baseSchema=FormatString.CheckNullString(p_ParBaseModel.getBaseSchema());
		if(p_ParDealProcessLogModel==null)
			p_ParDealProcessLogModel=new ParDealProcessLogModel();
		p_ParDealProcessLogModel.setBaseSchema(baseSchema);
		
		StringBuffer sqlString=new StringBuffer();
		sqlString.append(GetSelectSqlExt(p_ParBaseModel));
		sqlString.append( " and exists( ");
		sqlString.append(" select 1 from (");
		DealProcessLog m_DealProcessLog=new DealProcessLog();
		String deallogSql=m_DealProcessLog.getSelectSql(p_ParDealProcessLogModel);
		sqlString.append(deallogSql);
		if(baseSchema.equals(""))
			sqlString.append(" ) processlog where processlog.ProcessLogBaseID=Base.C700000000 and processlog.ProcessLogBaseSchema=Base.C700000001");
		else
			sqlString.append(" ) processlog where processlog.ProcessLogBaseID=Base.C1 and processlog.ProcessLogBaseSchema=Base.C700000001");
		sqlString.append( " )");
		
		System.out.println(sqlString.toString());
		
		GetQueryResultRows(sqlString.toString());
		//加排序条件


		sqlString.append(p_ParBaseModel.getOrderbyFiledNameString());
		String strSql=sqlString.toString();
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		
		Statement stm=null;
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, strSql);
			baseList=ConvertRsToList(m_BaseRs);	
		}catch(Exception ex)
		{
			System.err.println("Base.getProcessDealBaseList 方法"+ex.getMessage());
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
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		
		return baseList;
	}
	
	/**
	 * 根据审批日志查询各种动作的工单

	 * @param p_ParBaseModel
	 * @param p_ParDealProcessLogModel
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List getProcessAuditingBaseList(ParBaseModel p_ParBaseModel,ParAuditingProcessLogModel p_ParAuditingProcessLogModel,int p_PageNumber,int p_StepRow)
	{
		List baseList=null;
		String baseSchema=FormatString.CheckNullString(p_ParBaseModel.getBaseSchema());
		if(p_ParAuditingProcessLogModel==null)
			p_ParAuditingProcessLogModel=new ParAuditingProcessLogModel();
		p_ParAuditingProcessLogModel.setBaseSchema(baseSchema);
		
		StringBuffer sqlString=new StringBuffer();
		sqlString.append(GetSelectSqlExt(p_ParBaseModel));
		sqlString.append( " and exists( ");
		sqlString.append(" select 1 from (");
		AuditingProcessLog m_AuditingProcessLog=new AuditingProcessLog();
		String deallogSql=m_AuditingProcessLog.getSelectSql(p_ParAuditingProcessLogModel);
		sqlString.append(deallogSql);
		if(baseSchema.equals(""))
			sqlString.append(" ) processlog where processlog.ProcessLogBaseID=Base.C700000000 and processlog.ProcessLogBaseSchema=Base.C700000001 ");
		else
			sqlString.append(" ) processlog where processlog.ProcessLogBaseID=Base.C1 and processlog.ProcessLogBaseSchema=Base.C700000001 ");
		sqlString.append( " )");
		
		System.out.println(sqlString.toString());
		
		GetQueryResultRows(sqlString.toString());
		//加排序条件


		sqlString.append(p_ParBaseModel.getOrderbyFiledNameString());
		String strSql=sqlString.toString();
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		
		Statement stm=null;
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, strSql);
			baseList=ConvertRsToList(m_BaseRs);	
		}catch(Exception ex)
		{
			System.err.println("Base.getProcessDealBaseList 方法"+ex.getMessage());
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
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		
		return baseList;
	}	
	
	
/*** 为绑定变量加的****/	
	
	
	private String getSelectFiled(String strBaseScheam)
	{
		StringBuffer sqlString=new StringBuffer();
		
		if (!strBaseScheam.trim().equals(""))
			sqlString.append(" C1 as BaseID ");
		else
			sqlString.append(" C700000000 as BaseID ");
		sqlString.append(",C700000022 as BaseTplID,C700000001 as BaseSchema,C700000002 as BaseName,C700000003 as BaseSN");
		sqlString.append(",C700000004 as BaseCreatorFullName,C700000005 as BaseCreatorLoginName,C700000006 as BaseCreateDate");
		sqlString.append(",C700000007 as BaseSendDate,C700000008 as BaseFinishDate,C700000009 as BaseCloseDate");
		sqlString.append(",C700000010 as BaseStatus");
		// sqlString.append(",C700000014 as BaseItems");
		// sqlString.append(",C700000015 as BasePriority");
		// sqlString.append(",C700000011 as BaseSummary,C700000017 as
		// BaseAcceptOutTime,C700000018 as BaseDealOutTime");
		// sqlString.append(",C700000016 as BaseIsAllowLogGroup,C700000019
		// as BaseDescrption");
		
		sqlString.append(",C700000020 as BaseResult ");
		sqlString.append(",C700000021 as BaseCloseSatisfy ");
		// BaseIsArchive 700000023 工单是否已经存档 0：表示不是（默认） 1：表示是
		sqlString.append(",C700000023 as BaseIsArchive ");
		sqlString.append(",C700000024 as BaseAuditinglLinkName,C700000025 as BaseAuditingProcessName,C700000026 as BaseAuditingProcessLogName ");
		sqlString.append(",C700000027 as BaseDealLinkName,C700000028 as BaseDealProcessName,C700000029 as BaseDealProcessLogName ");
		//工单特有字段
		String strOwnerSql = GetOwnerFiledSqlString(strBaseScheam);
		sqlString.append(strOwnerSql);		
		
		return sqlString.toString();
		
	}
	
	private  List ConvertRsToListForBind(ResultSet p_BaseRs)  
	{
		if(p_BaseRs==null) return null;
		
		List list = new ArrayList();
		
		//System.out.println(rsmd.getColumnClassName(1));
		//System.out.println(rsmd.getColumnType(1));	
		//System.out.println(java.sql.Types.CLOB);
		//System.out.println(java.sql.Types.VARCHAR);
		
		String colName;
		try{
			//ResultSetMetaData   rsmd=p_BaseRs.getMetaData();
			ResultSetMetaData metaData=p_BaseRs.getMetaData();
			int cols=metaData.getColumnCount();
			//metaData.getco
			//PariseUntil m_PariseUntil=new PariseUntil();

			while(p_BaseRs.next())
			{
				  
				
				 BaseModel m_Base=new BaseModel();
				 
				 for(int col=1;col<=cols;col++)
				 {
					 colName=metaData.getColumnName(col);
					 //System.out.println("colName:"+colName);
					 if(FormatString.compareEqualsString(colName,"BaseID"))
						 m_Base.setBaseID(p_BaseRs.getString("BaseID"));
					 else if(FormatString.compareEqualsString(colName,"BaseTplID"))
						 m_Base.setBaseTplID(p_BaseRs.getString("BaseTplID"));
					 else if(FormatString.compareEqualsString(colName,"BaseSchema"))
						 m_Base.setBaseSchema(p_BaseRs.getString("BaseSchema"));
					 else if(FormatString.compareEqualsString(colName,"BaseName"))
						 m_Base.setBaseName(p_BaseRs.getString("BaseName"));
					 else if(FormatString.compareEqualsString(colName,"BaseSN"))
						 m_Base.setBaseSN(p_BaseRs.getString("BaseSN"));
					 else if(FormatString.compareEqualsString(colName,"BaseCreatorFullName"))
						 m_Base.setBaseCreatorFullName(p_BaseRs.getString("BaseCreatorFullName"));
					 else if(FormatString.compareEqualsString(colName,"BaseCreatorLoginName"))
						 m_Base.setBaseCreatorLoginName(p_BaseRs.getString("BaseCreatorLoginName"));
					 else if(FormatString.compareEqualsString(colName,"BaseCreateDate"))
						 m_Base.setBaseCreateDate(p_BaseRs.getLong("BaseCreateDate"));
					 else if(FormatString.compareEqualsString(colName,"BaseSendDate"))
						 m_Base.setBaseSendDate(p_BaseRs.getLong("BaseSendDate"));
					 else if(FormatString.compareEqualsString(colName,"BaseFinishDate"))
						 m_Base.setBaseFinishDate(p_BaseRs.getLong("BaseFinishDate"));
					 else if(FormatString.compareEqualsString(colName,"BaseCloseDate"))
						 m_Base.setBaseCloseDate(p_BaseRs.getLong("BaseCloseDate"));
					 else if(FormatString.compareEqualsString(colName,"BaseStatus"))
						 m_Base.setBaseStatus(p_BaseRs.getString("BaseStatus"));
					 //m_Base.setBaseSummary(p_BaseRs.getString("BaseSummary"));
					 //m_Base.setBaseAssigneeS(p_BaseRs.getString("BaseAssigneeS"));
					// m_Base.setBaseAuditingerS(p_BaseRs.getString("BaseAuditingerS"));
					// m_Base.setBaseItems(p_BaseRs.getString("BaseItems"));
					 //m_Base.setBasePriority(p_BaseRs.getString("BasePriority"));
					// m_Base.setBaseIsAllowLogGroup(p_BaseRs.getInt("BaseIsAllowLogGroup"));
					// m_Base.setBaseAcceptOutTime(p_BaseRs.getLong("BaseAcceptOutTime"));
					// m_Base.setBaseDealOutTime(p_BaseRs.getLong("BaseDealOutTime"));
					// m_Base.setBaseDescrption(p_BaseRs.getString("BaseDescrption"));
					 //m_Base.setBaseResult(p_BaseRs.getString("BaseResult"));
					 else if(FormatString.compareEqualsString(colName,"BaseCloseSatisfy"))
						 m_Base.setBaseCloseSatisfy(p_BaseRs.getString("BaseCloseSatisfy"));
					 else if(FormatString.compareEqualsString(colName,"BaseIsArchive"))
						 m_Base.setIsArchive(p_BaseRs.getInt("BaseIsArchive"));
					 else if(FormatString.compareEqualsString(colName,"BaseAuditinglLinkName"))
						 m_Base.setBaseAuditinglLinkName(p_BaseRs.getString("BaseAuditinglLinkName"));
					 else if(FormatString.compareEqualsString(colName,"BaseAuditingProcessName"))
						 m_Base.setBaseAuditingProcessName(p_BaseRs.getString("BaseAuditingProcessName"));
					 else if(FormatString.compareEqualsString(colName,"BaseAuditingProcessLogName"))
						 m_Base.setBaseAuditingProcessLogName(p_BaseRs.getString("BaseAuditingProcessLogName"));
					 else if(FormatString.compareEqualsString(colName,"BaseDealLinkName"))
						 m_Base.setBaseDealLinkName(p_BaseRs.getString("BaseDealLinkName"));
					 else if(FormatString.compareEqualsString(colName,"BaseDealProcessName"))
						 m_Base.setBaseDealProcessName(p_BaseRs.getString("BaseDealProcessName"));
					 else if(FormatString.compareEqualsString(colName,"BaseDealProcessLogName"))
						 m_Base.setBaseDealProcessLogName(p_BaseRs.getString("BaseDealProcessLogName"));				
					 
					// metaData.getColumnType()
					 
					 else 
					 {
							 //如果是大字段
							 if(metaData.getColumnType(col)==java.sql.Types.CLOB)
							 {
								 ProcessUtil m_ProcessUtil=new ProcessUtil();
								 Clob m_clob=p_BaseRs.getClob(colName);
								 m_Base.SetOwnerFiled(colName,m_ProcessUtil.getClobString(m_clob));
							 }
							 else
								 m_Base.SetOwnerFiled(colName,p_BaseRs.getString(colName));
							  //System.out.println("Key: "+colName+p_BaseRs.getString(colName));
							 //hsOwnerFiled.put(key,p_BaseRs.getString(key));
							 //System.out.println("Key: "+p_BaseRs.getString(key));
							
					 }
					 
				 }
				 list.add(m_Base);
			}//while(p_BaseRs.next())
			
		}catch(Exception ex)
		{
			System.err.println("BaseForBind.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		
		return list;
	}		
	
	
	public String getListForBindSQL(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		String strBaseScheam="";
		String tblName="";
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		List m_FiledinfoList=null;
		if(p_ParBaseModel!=null)
		{
			strBaseScheam=p_ParBaseModel.getBaseSchema();
			m_FiledinfoList=p_ParBaseModel.getContionFiledInfoList();
			
		}
		
		if(!strBaseScheam.trim().equals(""))
		{
			//某类工单表
			tblName=m_RemedyDBOp.GetRemedyTableName(strBaseScheam);
		}
		else
			//工单集合表 从UltraProcess:App_Base_Infor中查询  
			tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseInfor);
		
		ParseParmeter parseParmeter=new ParseParmeter(m_FiledinfoList);
		String[] m_FiledValeAry=parseParmeter.getParmeterValue();
		
		String basewhereSql=parseParmeter.getWhereSql();
		basewhereSql=FormatString.CheckNullString(basewhereSql);
		String baseFiled=parseParmeter.getSelectFiled();
		baseFiled=FormatString.CheckNullString(baseFiled);
		
		String selectFiled=baseFiled;		
		if(selectFiled.equals(""))
		{
			selectFiled=this.getSelectFiled(strBaseScheam);
		}
		
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("select ");
		sqlString.append(selectFiled);
		sqlString.append(" from "+tblName+" base");
		sqlString.append(" where 1=1 ");
		sqlString.append(parseParmeter.getWhereSql());
		if(p_ParBaseModel!=null)
		{	
			sqlString.append(p_ParBaseModel.getExtendSql());
			sqlString.append(p_ParBaseModel.getOrderbyFiledNameString());
		}		
		String strSql=sqlString.toString();

		int arLen = 0;
		if (m_FiledValeAry != null)
			arLen = m_FiledValeAry.length;
		for (int i = 0; i < arLen; i++) {
			strSql=strSql.replaceFirst("\\?","'"+m_FiledValeAry[i]+"'");
			// prestm.setString(i+1, m_FiledValeAry[i] ); //为绑定变量赋值
		}

		return strSql;
	}		
	
	public List getListForBind(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		List m_Relist=null;
		String strBaseScheam="";
		String tblName="";
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		List m_FiledinfoList=null;
		
		if(p_ParBaseModel!=null)
		{
			strBaseScheam=p_ParBaseModel.getBaseSchema();
			m_FiledinfoList=p_ParBaseModel.getContionFiledInfoList();
			
		}
		if(!strBaseScheam.trim().equals(""))
		{
			//某类工单表
			tblName=m_RemedyDBOp.GetRemedyTableName(strBaseScheam);
		}
		else
			//工单集合表 从UltraProcess:App_Base_Infor中查询  
			tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseInfor);
		
		ParseParmeter parseParmeter=new ParseParmeter(m_FiledinfoList);
		String[] m_FiledValeAry=parseParmeter.getParmeterValue();
		
		String basewhereSql=parseParmeter.getWhereSql();
		basewhereSql=FormatString.CheckNullString(basewhereSql);
		
		if(p_ParBaseModel!=null)
			basewhereSql+=p_ParBaseModel.getExtendSql();		
		
		String baseFiled=parseParmeter.getSelectFiled();
		baseFiled=FormatString.CheckNullString(baseFiled);
		
		String whereSql=basewhereSql;
		String selectFiled=baseFiled;		
		if(selectFiled.equals(""))
		{
			selectFiled=this.getSelectFiled(strBaseScheam);
		}
		
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("select count(*) rownums ");
		//sqlString.append(parseParmeter.getSelectFiled());
		sqlString.append(" from "+tblName+" base ");
		sqlString.append(" where 1=1 ");
		sqlString.append(whereSql);
		
		String strSqlRowCount=sqlString.toString();
		//GetQueryResultRows2(strSqlRowCount);
		
		sqlString = new StringBuffer();
		sqlString.append("select ");
		sqlString.append(selectFiled);
		sqlString.append(" from "+tblName+" base ");
		sqlString.append(" where 1=1 ");
		sqlString.append(parseParmeter.getWhereSql());	
		if(p_ParBaseModel!=null)
		{	
			sqlString.append(p_ParBaseModel.getExtendSql());
			sqlString.append(p_ParBaseModel.getOrderbyFiledNameString());
		}
		String strSql=sqlString.toString();
		
		
		//GetQueryResultRows(strSql);
		//strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.bindGetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		//strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		PreparedStatement prestm=null;
		ResultSet m_BaseRs =null;
		
		try{
			int listCount=0;
			
			prestm=m_dbConsole.getConn().prepareStatement(strSqlRowCount);
			if(m_FiledValeAry!=null)
				listCount=m_FiledValeAry.length;
			for(int i=0;i<listCount;i++)
			{	
				prestm.setString(i+1, m_FiledValeAry[i] ); //为绑定变量赋值
			}
			
				
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
			if(p_PageNumber<0||p_StepRow<0)
				return null;
			
			m_BaseRs.close();
			prestm.close();
			
			//System.err.println("Base.getListForBind(p_ParBaseModel) 方法strSql:"+strSql);
			prestm=m_dbConsole.getConn().prepareStatement(strSql);
			int i=0;
			for(i=0;i<listCount;i++)
			{	
				prestm.setString(i+1, m_FiledValeAry[i] ); //为绑定变量赋值
			}
			
			//绑定查询条数变量
			if(p_PageNumber>0)
			{
				int intStartRow;	
				int intRowCount;
				if(p_PageNumber==1)
					intStartRow=1;
				else if(p_PageNumber>1)
					intStartRow=(p_PageNumber-1)*p_StepRow+1;
				else
					intStartRow=0;
				intRowCount=p_PageNumber*p_StepRow;
				
				prestm.setString(i+1,String.valueOf(intRowCount));
				prestm.setString(i+2,String.valueOf(intStartRow));
			}
			m_BaseRs=prestm.executeQuery();
			if(baseFiled.equals(""))
				m_Relist=ConvertRsToList(m_BaseRs);
			else
				m_Relist=ConvertRsToListForBind(m_BaseRs);
		}catch(Exception ex)
		{
			System.err.println("Base.GetList 方法"+ex.getMessage());
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
	
	
	public String getBaseListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcessLogModel p_ParDealProcesslog,int p_PageNumber,int p_StepRow )
	{

		List m_BaseFiledinfoList=null;
		List m_DeallogFiledinfoList=null;		
		if(p_ParDealProcesslog==null)
		{
			return getListForBindSQL(p_ParBaseModel,p_PageNumber,p_StepRow);
		}
		else
		{
			m_DeallogFiledinfoList=p_ParDealProcesslog.getContionFiledInfoList();
			int listCount=0;
			if(m_DeallogFiledinfoList!=null)
				listCount=m_DeallogFiledinfoList.size();
			if(listCount<=0)
				return getListForBindSQL(p_ParBaseModel,p_PageNumber,p_StepRow);
			
		}
		
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
		ParseParmeter parseDeallogParmeter=new ParseParmeter(m_DeallogFiledinfoList);
		String dealLogwhereSql=parseDeallogParmeter.getWhereSql();
		dealLogwhereSql=FormatString.CheckNullString(dealLogwhereSql);
		//如果Dealprocess没有查询条件
		if(dealLogwhereSql.equals(""))
			return getListForBindSQL(p_ParBaseModel,p_PageNumber,p_StepRow);
		
		ParseParmeter parseBaseParmeter=new ParseParmeter(m_BaseFiledinfoList);
		
		String strDealLogTblName="";
		String strDealLogTblName_h="";
		
		String baseFiled=parseBaseParmeter.getSelectFiled();
		
		String basewhereSql=parseBaseParmeter.getWhereSql();
		basewhereSql=FormatString.CheckNullString(basewhereSql);
		
		//String whereSql=basewhereSql+dealLogwhereSql;
		
		String selectFiled=baseFiled;
		if(selectFiled.equals(""))
			selectFiled=this.getSelectFiled(strBaseScheam);
		//如果是审批

			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				//获取历史表名
				if(p_ParBaseModel.getIsArchive()==1)
				{
					strDealLogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
				}
				else
					strDealLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
			}
			else
			{
				strDealLogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
				strDealLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
			}			
		
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				if(p_ParBaseModel.getIsArchive()==1)
					sqlString.append(" and exists (select 1 from "+strDealLogTblName_h+" dealLog");
				else
					sqlString.append(" and exists (select 1 from "+strDealLogTblName+" dealLog");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlString.append(" where base.C1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				sqlString.append(dealLogwhereSql);
				sqlString.append(" )");
				 

			}
			else
			{
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealLogTblName+" dealLog ");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlString.append(" where base.c1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");				sqlString.append(dealLogwhereSql);
				sqlString.append(" )");
				
				sqlString.append(" union all ");
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealLogTblName_h+" dealLog ");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlString.append(" where base.c1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				sqlString.append(dealLogwhereSql);
				sqlString.append(" )");
				
			}//if(p_ParDealProcess.getIsArchive()==0 || p_ParDealProcess.getIsArchive()==1)	
		
			String[] baseValueList=parseBaseParmeter.getParmeterValue();
			String[] dealValueList=parseDeallogParmeter.getParmeterValue();
			int baselen=0;
			int dealLoglen=0;
			if(baseValueList!=null)
				baselen=baseValueList.length;
			if(dealValueList!=null)
				dealLoglen=dealValueList.length;
		
			String strSql=sqlString.toString();
			
			for(int i=0;i<baselen;i++)
			{	
				strSql=strSql.replaceFirst("\\?","'"+baseValueList[i]+"'");
			}
			for(int i=0;i<dealLoglen;i++)
			{	
				strSql=strSql.replaceFirst("\\?","'"+dealValueList[i]+"'");
			}
			if(p_ParBaseModel.getIsArchive()!=0 && p_ParBaseModel.getIsArchive()!=1)
			{
				for(int i=0;i<baselen;i++)
				{	
					strSql=strSql.replaceFirst("\\?","'"+baseValueList[i]+"'");
				}
				for(int i=0;i<dealLoglen;i++)
				{	
					strSql=strSql.replaceFirst("\\?","'"+dealValueList[i]+"'");
				}				
			}
			String orderByString=p_ParBaseModel.getOrderbyFiledNameString();
			if(!orderByString.equals(""))
				strSql+=p_ParBaseModel.getOrderbyFiledNameString();
			else
			{
				if(p_ParDealProcesslog!=null)
				{
					orderByString=p_ParDealProcesslog.getOrderbyFiledNameString();
					strSql+=p_ParBaseModel.getOrderbyFiledNameString();
				}
			}	
			
		return strSql;

	}		
	
	public List getBaseListForBind(ParBaseModel p_ParBaseModel,ParDealProcessLogModel p_ParDealProcesslog,int p_PageNumber,int p_StepRow )
	{
		List m_Relist=null;
		List m_BaseFiledinfoList=null;
		List m_DeallogFiledinfoList=null;		
		if(p_ParDealProcesslog==null)
		{
			return getListForBind(p_ParBaseModel,p_PageNumber,p_StepRow);
		}
		else
		{
			m_DeallogFiledinfoList=p_ParDealProcesslog.getContionFiledInfoList();
			int listCount=0;
			if(m_DeallogFiledinfoList!=null)
				listCount=m_DeallogFiledinfoList.size();
			if(listCount<=0)
				return getListForBind(p_ParBaseModel,p_PageNumber,p_StepRow);
			
		}
		
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
		ParseParmeter parseDeallogParmeter=new ParseParmeter(m_DeallogFiledinfoList);
		String dealLogwhereSql=parseDeallogParmeter.getWhereSql();
		dealLogwhereSql=FormatString.CheckNullString(dealLogwhereSql);
		if(p_ParDealProcesslog!=null)
			dealLogwhereSql+=p_ParDealProcesslog.getExtendSql();
		//如果Dealprocess没有查询条件
		if(dealLogwhereSql.equals(""))
			return getListForBind(p_ParBaseModel,p_PageNumber,p_StepRow);
		
		ParseParmeter parseBaseParmeter=new ParseParmeter(m_BaseFiledinfoList);
		
		String strDealLogTblName="";
		String strDealLogTblName_h="";
		
		String baseFiled=parseBaseParmeter.getSelectFiled();
		
		String basewhereSql=parseBaseParmeter.getWhereSql();
		basewhereSql=FormatString.CheckNullString(basewhereSql);
		if(p_ParBaseModel!=null)
			basewhereSql+=p_ParBaseModel.getExtendSql();
		//String whereSql=basewhereSql+dealLogwhereSql;
		
		String selectFiled=baseFiled;
		if(selectFiled.equals(""))
			selectFiled=this.getSelectFiled(strBaseScheam);
		
		int optType=0;
		if(p_ParDealProcesslog!=null)
			optType=p_ParDealProcesslog.getProcessOptionalType();
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				//获取历史表名
				if(p_ParBaseModel.getIsArchive()==1)
				{
//					如果是审批
					if(optType==3)
						strDealLogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog_H);
					else	
						strDealLogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
				}
				else
				{
					if(optType==3)
						strDealLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog);
					else
						strDealLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
				}
			}
			else
			{
				if(optType==3)
				{
					strDealLogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog_H);
					strDealLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog);					
				}
				else
				{
					strDealLogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
					strDealLogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
				}
			}			
		
			if(p_ParBaseModel.getIsArchive()==0 || p_ParBaseModel.getIsArchive()==1)
			{
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				if(p_ParBaseModel.getIsArchive()==1)
					sqlString.append(" and exists (select 1 from "+strDealLogTblName_h+" dealLog");
				else
					sqlString.append(" and exists (select 1 from "+strDealLogTblName+" dealLog");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlString.append(" where base.C1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");	
				
				sqlString.append(dealLogwhereSql);
				sqlString.append(" )");
				
				
				sqlRowCount.append(" select ");
				sqlRowCount.append(" count(*) rownums ");
				sqlRowCount.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlRowCount.append(" where 1=1 ");
				sqlRowCount.append(basewhereSql);
				
				if(p_ParDealProcesslog.getIsArchive()==1)
					sqlRowCount.append(" and exists (select 1 from "+strDealLogTblName_h+" dealLog");
				else
					sqlRowCount.append(" and exists (select 1 from "+strDealLogTblName+" dealLog");

				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlRowCount.append(" where base.C1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");	
				sqlRowCount.append(dealLogwhereSql);
				sqlRowCount.append(" )");				
				 

			}
			else
			{
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealLogTblName+" dealLog");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlString.append(" where base.C1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");	
				sqlString.append(dealLogwhereSql);
				sqlString.append(" )");

				
				sqlString.append(" union all ");
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealLogTblName_h+" dealLog");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlString.append(" where base.C1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");	
				sqlString.append(dealLogwhereSql);
				sqlString.append(" )");

				
				//数量查询sql
				sqlRowCount.append(" select sum(rownums) rownums from (");
				sqlRowCount.append(" select ");
				sqlRowCount.append(" count(*) rownums ");
				sqlRowCount.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlRowCount.append(" where 1=1 ");
				sqlRowCount.append(basewhereSql);
				sqlRowCount.append(" and exists (select 1 from "+strDealLogTblName+" dealLog");
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlRowCount.append(" where base.C1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");	
				sqlRowCount.append(dealLogwhereSql);
				sqlRowCount.append(" )");

				
				sqlRowCount.append(" union all ");
				
				sqlRowCount.append(" select ");
				sqlRowCount.append(" count(*) rownums ");
				sqlRowCount.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlRowCount.append(" where 1=1 ");
				sqlRowCount.append(basewhereSql);
				sqlRowCount.append(" and exists (select 1 from "+strDealLogTblName_h+" dealLog");
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");
				else
					sqlRowCount.append(" where base.C1=dealLog.C700020407 and base.C700000001=dealLog.C700020408 ");	
				sqlRowCount.append(dealLogwhereSql);
				sqlRowCount.append(" )");
				
				sqlRowCount.append(" ) base");
				
			}//if(p_ParDealProcess.getIsArchive()==0 || p_ParDealProcess.getIsArchive()==1)	
		
			

			
			
			IDataBase m_dbConsole = GetDataBase.createDataBase();
			
			String strSql=sqlString.toString();
			if(strSql.equals(""))
				return null;
			
			String orderByString=p_ParBaseModel.getOrderbyFiledNameString();
			if(!orderByString.equals(""))
				strSql+=p_ParBaseModel.getOrderbyFiledNameString();
			else
			{
				if(p_ParDealProcesslog!=null)
				{
					orderByString=p_ParDealProcesslog.getOrderbyFiledNameString();
					strSql+=p_ParBaseModel.getOrderbyFiledNameString();
				}
			}						
			
			//分页
			if(p_PageNumber>0)
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
				String[] dealLogValueList=parseDeallogParmeter.getParmeterValue();
				int baselen=0;
				int deallen=0;
				if(baseValueList!=null)
					baselen=baseValueList.length;
				if(dealLogValueList!=null)
					deallen=dealLogValueList.length;
				
				int bindIndex=0;
				 
				//System.out.println("Base.getBaseListForBind sqlRowCount:"+sqlRowCount.toString());				
				prestm=m_dbConsole.getConn().prepareStatement(sqlRowCount.toString());
				
				for(int i=0;i<baselen;i++)
				{	
					bindIndex++;
					prestm.setString(bindIndex, baseValueList[i] ); //为绑定变量赋值
				}			
				for(int i=0;i<deallen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, dealLogValueList[i] ); //为绑定变量赋值
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
						prestm.setString(bindIndex, dealLogValueList[i] ); //为绑定变量赋值
					}				

				}

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
				if(p_PageNumber<0||p_StepRow<0)
					return null;
				m_BaseRs.close();
				prestm.close();
				prestm=m_dbConsole.getConn().prepareStatement(strSql);
				//System.out.println("Base.getBaseListForBind strSql:"+strSql);
				bindIndex=0;
				
				for(int i=0;i<baselen;i++)
				{	
					bindIndex++;
					prestm.setString(bindIndex, baseValueList[i] ); //为绑定变量赋值
				}
				for(int i=0;i<deallen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, dealLogValueList[i] ); //为绑定变量赋值
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
						prestm.setString(bindIndex, dealLogValueList[i] ); //为绑定变量赋值
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
				if(baseFiled.equals(""))
					m_Relist=this.ConvertRsToList(m_BaseRs);
				else
					m_Relist=ConvertRsToListForBind(m_BaseRs);	
			}catch(Exception ex)
			{
				
				System.err.println("Base.getBaseListForBind 方法"+ex.getMessage());
				//System.err.println("strSql:"+strSql);
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
	
	public String getBaseListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{

		List m_BaseFiledinfoList=null;
		List m_DealFiledinfoList=null;		
		if(p_ParDealProcess==null)
		{
			return getListForBindSQL(p_ParBaseModel,p_PageNumber,p_StepRow);
		}
		else
		{
			m_DealFiledinfoList=p_ParDealProcess.getContionFiledInfoList();
			int listCount=0;
			if(m_DealFiledinfoList!=null)
				listCount=m_DealFiledinfoList.size();
			if(listCount<=0)
				return getListForBindSQL(p_ParBaseModel,p_PageNumber,p_StepRow);
			
		}
		
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
		ParseParmeter parseDealParmeter=new ParseParmeter(m_DealFiledinfoList);
		String dealwhereSql=parseDealParmeter.getWhereSql();
		dealwhereSql=FormatString.CheckNullString(dealwhereSql);
		dealwhereSql+=p_ParDealProcess.getExtendSql();
		//如果Dealprocess没有查询条件
		if(dealwhereSql.equals(""))
			return getListForBindSQL(p_ParBaseModel,p_PageNumber,p_StepRow);
		
		ParseParmeter parseBaseParmeter=new ParseParmeter(m_BaseFiledinfoList);
		
		String strDealTblName="";
		String strDealTblName_h="";
		
		String baseFiled=parseBaseParmeter.getSelectFiled();
		
		String basewhereSql=parseBaseParmeter.getWhereSql();
		basewhereSql=FormatString.CheckNullString(basewhereSql);
		if(p_ParBaseModel!=null)
			basewhereSql+=p_ParBaseModel.getExtendSql();
		//String whereSql=basewhereSql+dealwhereSql;
		
		String selectFiled=baseFiled;
		if(selectFiled.equals(""))
			selectFiled=this.getSelectFiled(strBaseScheam);
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
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				if(p_ParBaseModel.getIsArchive()==1)
					sqlString.append(" and exists (select 1 from "+strDealTblName_h+" deal");
				else
					sqlString.append(" and exists (select 1 from "+strDealTblName+" deal");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlString.append(dealwhereSql);
				sqlString.append(" )");
				 

			}
			else
			{
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealTblName+" deal");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlString.append(dealwhereSql);
				sqlString.append(" )");
				
				sqlString.append(" union all ");
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealTblName_h+" deal");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlString.append(dealwhereSql);
				sqlString.append(" )");
				
			}//if(p_ParDealProcess.getIsArchive()==0 || p_ParDealProcess.getIsArchive()==1)	
		
			String[] baseValueList=parseBaseParmeter.getParmeterValue();
			String[] dealValueList=parseDealParmeter.getParmeterValue();
			int baselen=0;
			int deallen=0;
			if(baseValueList!=null)
				baselen=baseValueList.length;
			if(dealValueList!=null)
				deallen=dealValueList.length;
		
			String strSql=sqlString.toString();
			
			for(int i=0;i<baselen;i++)
			{	
				strSql=strSql.replaceFirst("\\?","'"+baseValueList[i]+"'");
			}
			for(int i=0;i<deallen;i++)
			{	
				strSql=strSql.replaceFirst("\\?","'"+dealValueList[i]+"'");
			}
			if(p_ParBaseModel.getIsArchive()!=0 && p_ParBaseModel.getIsArchive()!=1)
			{
				for(int i=0;i<baselen;i++)
				{	
					strSql=strSql.replaceFirst("\\?","'"+baseValueList[i]+"'");
				}
				for(int i=0;i<deallen;i++)
				{	
					strSql=strSql.replaceFirst("\\?","'"+dealValueList[i]+"'");
				}				
			}
			String orderByString=p_ParBaseModel.getOrderbyFiledNameString();
			if(!orderByString.equals(""))
				strSql+=p_ParBaseModel.getOrderbyFiledNameString();
			else
			{
				if(p_ParDealProcess!=null)
				{
					orderByString=p_ParDealProcess.getOrderbyFiledNameString();
					strSql+=p_ParBaseModel.getOrderbyFiledNameString();
				}
			}
		return strSql;

	}	
	
	public List getBaseListForBind(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{

		List m_Relist=null;
		List m_BaseFiledinfoList=null;
		List m_DealFiledinfoList=null;		
		if(p_ParDealProcess==null)
		{
			return getListForBind(p_ParBaseModel,p_PageNumber,p_StepRow);
		}
		else
		{
			m_DealFiledinfoList=p_ParDealProcess.getContionFiledInfoList();
			int listCount=0;
			if(m_DealFiledinfoList!=null)
				listCount=m_DealFiledinfoList.size();
			if(listCount<=0)
				return getListForBind(p_ParBaseModel,p_PageNumber,p_StepRow);
			
		}
		
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
		ParseParmeter parseDealParmeter=new ParseParmeter(m_DealFiledinfoList);
		String dealwhereSql=parseDealParmeter.getWhereSql();
		dealwhereSql=FormatString.CheckNullString(dealwhereSql);
		if(p_ParDealProcess!=null)
			dealwhereSql+=p_ParDealProcess.getExtendSql();
		//如果Dealprocess没有查询条件
		if(dealwhereSql.equals(""))
			return getListForBind(p_ParBaseModel,p_PageNumber,p_StepRow);
		
		ParseParmeter parseBaseParmeter=new ParseParmeter(m_BaseFiledinfoList);
		
		String strDealTblName="";
		String strDealTblName_h="";
		
		String baseFiled=parseBaseParmeter.getSelectFiled();
		
		String basewhereSql=parseBaseParmeter.getWhereSql();
		basewhereSql=FormatString.CheckNullString(basewhereSql);
		if(p_ParBaseModel!=null)
			basewhereSql+=p_ParBaseModel.getExtendSql();		
		//String whereSql=basewhereSql+dealwhereSql;
		
		String selectFiled=baseFiled;
		if(selectFiled.equals(""))
			selectFiled=this.getSelectFiled(strBaseScheam);
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
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				if(p_ParBaseModel.getIsArchive()==1)
					sqlString.append(" and exists (select 1 from "+strDealTblName_h+" deal");
				else
					sqlString.append(" and exists (select 1 from "+strDealTblName+" deal");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlString.append(dealwhereSql);
				sqlString.append(" )");
				 
				sqlRowCount.append(" select ");
				sqlRowCount.append(" count(*) rownums ");
				sqlRowCount.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				sqlRowCount.append(" where 1=1 ");
				sqlRowCount.append(basewhereSql);
				if(p_ParDealProcess.getIsArchive()==1)
					sqlRowCount.append(" and exists (select 1 from "+strDealTblName_h+" deal");
				else
					sqlRowCount.append(" and exists (select 1 from "+strDealTblName+" deal");
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlRowCount.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlRowCount.append(dealwhereSql);
				sqlRowCount.append(" )");
				

			}
			else
			{
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealTblName+" deal");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlString.append(dealwhereSql);
				sqlString.append(" )");

				
				sqlString.append(" union all ");
				
				sqlString.append(" select ");
				sqlString.append(selectFiled);
				sqlString.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlString.append(" where 1=1 ");
				sqlString.append(basewhereSql);
				sqlString.append(" and exists (select 1 from "+strDealTblName_h+" deal");
				if(strBaseScheam.equals(""))
					sqlString.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlString.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlString.append(dealwhereSql);
				sqlString.append(" )");

				
				//数量查询sql
				sqlRowCount.append(" select sum(rownums) rownums from (");
				sqlRowCount.append(" select ");
				sqlRowCount.append(" count(*) rownums ");
				sqlRowCount.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlRowCount.append(" where 1=1 ");
				sqlRowCount.append(basewhereSql);
				sqlRowCount.append(" and exists (select 1 from "+strDealTblName+" deal");
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlRowCount.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlRowCount.append(dealwhereSql);
				sqlRowCount.append(" )");

				
				sqlRowCount.append(" union all ");
				
				sqlRowCount.append(" select ");
				sqlRowCount.append(" count(*) rownums ");
				sqlRowCount.append(" from "+strTblbase+" base " );
				//工单表： 700000001  BaseSchema
				//process表： 700020002 ProcessBaseSchema 700020001 ProcessBaseID
				
				sqlRowCount.append(" where 1=1 ");
				sqlRowCount.append(basewhereSql);
				sqlRowCount.append(" and exists (select 1 from "+strDealTblName_h+" deal");
				if(strBaseScheam.equals(""))
					sqlRowCount.append(" where base.C700000000=deal.c700020001 and base.C700000001=deal.C700020002 ");
				else
					sqlRowCount.append(" where base.c1=deal.c700020001 and base.C700000001=deal.C700020002 ");
				sqlRowCount.append(dealwhereSql);
				sqlRowCount.append(" )");
				
				sqlRowCount.append(" ) base");
				
			}//if(p_ParDealProcess.getIsArchive()==0 || p_ParDealProcess.getIsArchive()==1)	
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=sqlString.toString();
		
		if(strSql.equals(""))
			return null;
		String orderByString=p_ParBaseModel.getOrderbyFiledNameString();
		if(!orderByString.equals(""))
			strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		else
		{
			if(p_ParDealProcess!=null)
			{
				orderByString=p_ParDealProcess.getOrderbyFiledNameString();
				strSql+=p_ParBaseModel.getOrderbyFiledNameString();
			}
		} 
		
		//分页
		if(p_PageNumber>0)
		{
			strSql=PageControl.bindGetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		}
		//strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		//System.out.println("Base-getBaseListForBind(ParBaseModel,ParDealProcess) strSql:"+strSql);
		//绑定查询条数变量
		int intStartRow=0;	
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
		PreparedStatement prestm=null;
		ResultSet m_BaseRs =null;
		try{
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
			for(int i=0;i<deallen;i++)
			{	bindIndex++;
				prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
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

			}
		
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
			if(p_PageNumber<0||p_StepRow<0)
				return null;
			
			m_BaseRs.close();
			prestm.close();
			prestm=m_dbConsole.getConn().prepareStatement(strSql);
			bindIndex=0;
			
			for(int i=0;i<baselen;i++)
			{	
				bindIndex++;
				prestm.setString(bindIndex, baseValueList[i] ); //为绑定变量赋值
			}
			for(int i=0;i<deallen;i++)
			{	bindIndex++;
				prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
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
			}
			if(p_PageNumber>0)
			{
				//查询条数限制的变量绑定
				bindIndex++;
				prestm.setString(bindIndex,String.valueOf(intRowCount));
				bindIndex++;
				prestm.setString(bindIndex,String.valueOf(intStartRow));
			}			
			m_BaseRs=prestm.executeQuery();
			if(baseFiled.equals(""))
				m_Relist=this.ConvertRsToList(m_BaseRs);
			else
				m_Relist=ConvertRsToListForBind(m_BaseRs);	
		}catch(Exception ex)
		{
			
			System.err.println("BaseForBind.GetList 方法"+ex.getMessage());
			System.err.println("strSql:"+strSql);
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
				if(prestm!=null)
					prestm.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
		return m_Relist;

	}	
	
	
	
}
