package cn.com.ultrapower.ultrawf.control.queryinterface;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.BaseFactory;
import cn.com.ultrapower.ultrawf.control.process.IBase;
import cn.com.ultrapower.ultrawf.models.process.Base;
import cn.com.ultrapower.ultrawf.models.process.ParBaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
import cn.com.ultrapower.ultrawf.models.queryinterface.*;
import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.ultrawf.share.constants.*;

public class QueryIntegrationBaseAndProcess implements IQueryIntegrationBaseAndProcess  
{
	private int m_PageCounts=0;
	
	private int m_ResultRows=0;
	public int getQueryResultRows()
	{
		return m_ResultRows;
	}
	/**
	 * 等待处理：　处理 (等于)=待处理+处理中
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListProcessDeal(String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=null;

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessDeal,p_UserLoginName,p_PageNumber,p_StepRow);
				m_ResultRows=m_QueryIntegration.getQueryResultRows();
				//计算页数
			    CalculatePages(m_ResultRows,p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}
	/**
	 * 等待处理：　处理 (等于)=待处理（未受理）+处理中(已受理)
	 * @param p_BaseSchema
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetListProcessDeal(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=null;

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessDeal,p_BaseSchema,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
				CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;	
	}
	/**
	 * 待处理（未受理）
	 */
	public List GetListWaitDeal(String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessWaitDeal,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}
	
	/**
	 *  待处理（未受理）
	 */
	public List GetListWaitDeal(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessWaitDeal,p_BaseSchema,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
				CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}
	
	/**
	 * 待审批	 */
	public List GetListWaitAuditing(String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessWaitAuditing,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}
	/**
	 * 待审批	 */
	public List GetListWaitAuditing(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessWaitAuditing,p_BaseSchema,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}	
	
	/**
	 * 处理中(已受理)
	 */
	public List GetListDealing(String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessDealing,"",p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}
	/**
	 * 处理中(已受理)
	 */
	public List GetListDealing(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessDealing,p_BaseSchema,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;		
	}
	
	/**
	 * 待阅知
	 */
	public List GetListWaitConfirm(String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessWaitConfirm,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}
	
	/**
	 * 待阅知
	 */	
	public List GetListWaitConfirm(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessWaitConfirm,p_BaseSchema,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}	
	/**
	 * 我建立的工单　
	 */
	public List GetListMyCreate(String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessMyCreate,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}
	
	/**
	 * 我建立的工单　
	 */	
	public List GetListMyCreate(String p_BaseSchema,String p_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_List=new ArrayList();

		try{
			if(!p_UserLoginName.equals(""))
			{
				QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
				m_List=m_QueryIntegration.GetListWaitProcess(Constants.ProcessMyCreate,p_BaseSchema,p_UserLoginName,p_PageNumber,p_StepRow);
				//计算页数
			    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);				
			}
		}catch(Exception ex)
		{
			System.err.println("QueryIntegrationBaseAndProcess.QueryWaitDealForBaseList 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		return m_List;
	}		
	
	
	public List GetList(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		List m_List=new ArrayList();
		QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
		m_List=m_QueryIntegration.GetList(p_ParBaseModel,p_ParDealProcess,p_PageNumber,p_StepRow);
		//计算页数
	    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);		
		return m_List;
	}
	
	/**
	 * 计算总页数	 * @param p_RusultRows
	 * @param p_StepRow
	 */
	private void CalculatePages(int p_RusultRows,int p_StepRow)
	{
		int intPages=0;
		if(p_StepRow>0)
		{
			intPages=p_RusultRows/p_StepRow;
			if(p_RusultRows%p_StepRow>0)
				intPages++;
		}
		SetPageCounts(intPages);
	}
	
	private void SetPageCounts(int p_PageCounts)
	{
		m_PageCounts=p_PageCounts;
	}
	/**
	 * 返回总页数
	 */
	public int GetPageCount()
	{
		return m_PageCounts; 
	}	
	
	
	//某人所有待处理超时的工单	public int GetOverTimeBaseAll(String p_UserLoginName)
	{
		int intRows=0;
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		long lngDate=ServerInfo.GetServerTime();
		if(lngDate>0)
		{
			ParDealProcess m_ParDealProcess=new ParDealProcess();
			//设置从活动表中查询
			m_ParDealProcess.setIsArchive(0);			
			//工单处理时限大于(当前时间+超过时间)
			QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
			//Constants.ProcessDeal处理  处理=待处理(未受理)+处理中(已受理)
			m_ParDealProcess.setProcessOptionalType(Constants.ProcessDeal);				
			m_ParDealProcess.setTasekPersonID(p_UserLoginName);
			m_ParDealProcess.setDealOverTimeDateEnd(lngDate);
			intRows=m_QueryIntegrationForBaseAndProcess.GetResultCount(m_ParBaseModel,m_ParDealProcess);
		}
		return intRows;
	}
	
	//某人待处理超时多少分钟的工单
	public int GetOverTimeBaseCountInMinute(String p_UserLoginName,int p_OverMinute)
	{	
		int intRows=0;
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		long lngDate=ServerInfo.GetServerTime();
		if(lngDate>0)
		{
			ParDealProcess m_ParDealProcess=new ParDealProcess();
			//设置从活动表中查询
			m_ParDealProcess.setIsArchive(0);			
			//工单处理时限大于(当前时间+超过时间)
			lngDate=lngDate-p_OverMinute*60;
			QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
			//Constants.ProcessDeal处理   处理=待处理(未受理)+处理中(已受理)
			m_ParDealProcess.setProcessOptionalType(Constants.ProcessDeal);			
			m_ParDealProcess.setTasekPersonID(p_UserLoginName);
			m_ParDealProcess.setDealOverTimeDateEnd(lngDate);
			//m_ParDealProcess.setIsArchive(0);
			intRows=m_QueryIntegrationForBaseAndProcess.GetResultCount(m_ParBaseModel,m_ParDealProcess);
		}
		return intRows;
	}	
	
	//已受理的超时工单
	public int GetOverTimeBaseCountProcessed(String p_UserLoginName)
	{
		//工单处理时限大于(当前时间)　并且领单日期大于0的为　已超时并受理的		int intRows=0;
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		long lngDate=ServerInfo.GetServerTime();
		if(lngDate>0)
		{
			ParDealProcess m_ParDealProcess=new ParDealProcess();
			
			QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
			//设置从活动表中查询
			m_ParDealProcess.setIsArchive(0);
			//Constants.ProcessDealing处理中(已受理)			
			m_ParDealProcess.setProcessOptionalType(Constants.ProcessDealing);
			//当前工单处理人
			m_ParDealProcess.setTasekPersonID(p_UserLoginName);
			//工单处理时限大于(当前时间)
			m_ParDealProcess.setDealOverTimeDateEnd(lngDate);
			//领单日期大于0表示已受理
			m_ParDealProcess.setBgDateBegin(1);
			intRows=m_QueryIntegrationForBaseAndProcess.GetResultCount(m_ParBaseModel,m_ParDealProcess);
		}
		return intRows;		
	}	
	
	//已受理的超时工单
	public int GetOverTimeBaseCountProcessed(String p_UserLoginName,int p_OverMinute)
	{
		//工单处理时限大于(当前时间)　并且领单日期大于0的为　已超时并受理的
		int intRows=0;
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		long lngDate=ServerInfo.GetServerTime();
		lngDate=lngDate-p_OverMinute*60;
		if(lngDate>0)
		{
			ParDealProcess m_ParDealProcess=new ParDealProcess();
			//设置从活动表中查询
			m_ParDealProcess.setIsArchive(0);			
			QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
			//Constants.ProcessDealing处理中(已受理)
			m_ParDealProcess.setProcessOptionalType(Constants.ProcessDealing);	
			//当前工单处理人
			m_ParDealProcess.setTasekPersonID(p_UserLoginName);
			//工单处理时限大于(当前时间)
			m_ParDealProcess.setDealOverTimeDateEnd(lngDate);
			//领单日期大于0表示已受理
			m_ParDealProcess.setBgDateBegin(1);
			intRows=m_QueryIntegrationForBaseAndProcess.GetResultCount(m_ParBaseModel,m_ParDealProcess);
		}
		return intRows;		
	}		
	/**
	 * 返回满足条件数据的行数
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @return
	 */
	public int GetResultCount(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess)
	{
		int intRows=0;
		QueryIntegrationForBaseAndProcess m_QueryIntegrationForBaseAndProcess=new QueryIntegrationForBaseAndProcess();
		intRows=m_QueryIntegrationForBaseAndProcess.GetResultCount(p_ParBaseModel,p_ParDealProcess);
		return intRows;
	}
	
	/*public int getCountExp()
	{
		ParBaseModel p_ParBaseModel=new ParBaseModel();
		ParDealProcess p_ParDealProcess=new ParDealProcess();
		p_ParDealProcess.setTasekPersonID("loginname");
		//		描述：待阅知
		//public static final int ProcessWaitConfirm			= 10;

		//		描述：待处理
		public static final int ProcessWaitDeal				= 20;

		//		描述：处理中
		
		//public static final int ProcessDealing				= 30;
		
		//		描述：待审批
		//public static final int ProcessWaitAuditing			= 40;
		//
		 * 		描述：处理 (等于)=待处理+处理中
		//public static final int ProcessDeal					= 50;
		
	//		描述：我建立的工单
		//public static final int ProcessMyCreate				= 60;		
		p_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitDeal);
		//时间
		//StDate 产生时间
		//BgDate 领单时间
		//EdDate 完成时间

		p_ParDealProcess.setBgDateBegin(11);
		p_ParDealProcess.setBgDateEnd(11);
		
		return GetResultCount(p_ParBaseModel,p_ParDealProcess);
		//list　返回的类型为　IntegrationBaseAndDealProcessModle
	}*/
	
	
	/**
	 * 查询某人某时段内处理过的所有的工单信息和Process信息(已处理完成的)
	 * 查询返回IntegrationBaseAndDealProcessModle，例：如果对同一个工单处理过两次则会返回两
	 * 个IntegrationBaseAndDealProcessModle对象,但工单信息都是一样的。
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return IntegrationBaseAndDealProcessModle List
	 */
	public List GetProcessMyDealAndIsFinished(ParBaseModel p_ParBaseModel,
			ParDealProcess p_ParDealProcess, int p_PageNumber, int p_StepRow)
	{
		if(p_ParBaseModel==null)
			p_ParBaseModel=new ParBaseModel();
		if(p_ParDealProcess==null)
			p_ParDealProcess=new ParDealProcess();
		//Constants.ProcessMyDealAndIsFinished描述：我处理完成的工单(我处理过，并且已处理完成)
		p_ParDealProcess.setProcessOptionalType(Constants.ProcessMyDealAndIsFinished);
		return GetList(p_ParBaseModel,p_ParDealProcess,p_PageNumber,p_StepRow);
	}
	
	/**
	 * 查询某人某时段内处理过的所有的工单信息
	 * @param p_LoginName
	 * @param p_startDate 开始时间　格式: YYYY-MM-DD HH:MM:SS
	 * @param p_endDate   结束时间　格式: YYYY-MM-DD HH:MM:SS
	 * @return
	 */
	public List GetMyProcessDealBase(String p_LoginName,String p_startDate,String p_endDate)
	{
		List m_List=null;
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();

		
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setTasekPersonID(p_LoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyDeallAll);
		m_ParDealProcess.setIsArchive(m_ParBaseModel.getIsArchive());
		m_ParDealProcess.setEdDateBegin(FormatTime.FormatDateStringToInt(p_startDate));
		m_ParDealProcess.setEdDateEnd(FormatTime.FormatDateStringToInt(p_endDate));
		//m_ParDealProcess.setProcessBaseSchema(m_ParBaseModel.getBaseSchema());
		
		Base m_Base=new Base();
		m_List=m_Base.GetList(m_ParBaseModel,m_ParDealProcess,0,0 );
		m_PageCounts=m_Base.getQueryResultRows();
		//CalculatePages(m_PageCounts,0);		
		return m_List;
	}
	/**
	 * 查询某人某时段内审批过的所有的工单信息
	 * @param p_LoginName
	 * @param p_startDate
	 * @param p_endDate
	 * @return
	 */
	public List GetMyProcessAuditingBase(String p_LoginName,String p_startDate,String p_endDate)
	{
		List m_List=null;
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();

		
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setTasekPersonID(p_LoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyAuditingAndIsFinished);
		m_ParDealProcess.setIsArchive(m_ParBaseModel.getIsArchive());
		m_ParDealProcess.setEdDateBegin(FormatTime.FormatDateStringToInt(p_startDate));
		m_ParDealProcess.setEdDateEnd(FormatTime.FormatDateStringToInt(p_endDate));
		//m_ParDealProcess.setProcessBaseSchema(m_ParBaseModel.getBaseSchema());
		
		Base m_Base=new Base();
		m_List=m_Base.GetList(m_ParBaseModel,m_ParDealProcess,0,0 );
		m_PageCounts=m_Base.getQueryResultRows();
		//CalculatePages(m_PageCounts,0);		
		return m_List;
	}
	/**
	 * 查询某人某时段内派发的所有的工单信息
	 * @param p_LoginName
	 * @param p_startDate
	 * @param p_endDate
	 * @return
	 */
	public List GetMyAssignBase(String p_LoginName,String p_startDate,String p_endDate)
	{
		List m_List=null;
		
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setTasekPersonID(p_LoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyAssign);
		m_ParDealProcess.setIsArchive(m_ParBaseModel.getIsArchive());
		m_ParDealProcess.setEdDateBegin(FormatTime.FormatDateStringToInt(p_startDate));
		m_ParDealProcess.setEdDateEnd(FormatTime.FormatDateStringToInt(p_endDate));
		//m_ParDealProcess.setProcessBaseSchema(m_ParBaseModel.getBaseSchema());
		
		Base m_Base=new Base();
		m_List=m_Base.GetList(m_ParBaseModel,m_ParDealProcess,0,0 );
		m_PageCounts=m_Base.getQueryResultRows();
		//CalculatePages(m_PageCounts,0);		
		return m_List;
	}
	
	
	public List getListForBind(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		List m_List=null;
		QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
		m_List=m_QueryIntegration.getListForBind(p_ParBaseModel,p_ParDealProcess,p_PageNumber,p_StepRow);
		//计算页数
	    CalculatePages(m_QueryIntegration.getQueryResultRows(),p_StepRow);		
		return m_List;
	}
	public String getListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		QueryIntegrationForBaseAndProcess m_QueryIntegration=new QueryIntegrationForBaseAndProcess();
		return m_QueryIntegration.getListForBindSQL(p_ParBaseModel,p_ParDealProcess,p_PageNumber,p_StepRow);

	}	
	
	
	
}
