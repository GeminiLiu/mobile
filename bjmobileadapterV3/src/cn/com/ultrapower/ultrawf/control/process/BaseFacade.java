package cn.com.ultrapower.ultrawf.control.process;

import java.util.List;
import cn.com.ultrapower.ultrawf.models.process.Base;
import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParBaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLogModel;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
public class BaseFacade implements IBase{

	private int m_PageCounts=0;
	private int m_rowCount=0;
	public List getList(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		List m_List=null;
		Base m_Base=new Base();
		m_List=m_Base.GetList(p_ParBaseModel,p_PageNumber,p_StepRow);
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);
		return m_List;
	}
	
	public List getList(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		List m_List=null;
		Base m_Base=new Base();
		m_List=m_Base.GetList(p_ParBaseModel,p_ParDealProcess,p_PageNumber,p_StepRow);
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);
		return m_List;		
	}
	
	/**
	 * 我处理过的工单(我处理过，不管是否处理完成。包括自己建单(处理)的工单)
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return BaseModel List
	 */
	public List getProcessMyDeallAll(String strLoginName,ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		List m_List=null;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setTasekPersonID(strLoginName);
		m_ParDealProcess.setProcessBaseSchema(p_ParBaseModel.getBaseSchema());
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyDeallAll);
		m_ParDealProcess.setIsArchive(p_ParBaseModel.getIsArchive());
		Base m_Base=new Base();
		m_List=m_Base.GetList(p_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow );
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);		
		return m_List;
	}
	
	/**
	 * 我处理过的工单(我处理过，不管是否处理完成。不包括自己建单(处理)的工单)
	 * @param p_ParBaseModel
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List getProcessMyDeallAllNotIncludeNew(String strLoginName,ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		List m_List=null;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setTasekPersonID(strLoginName);
		m_ParDealProcess.setProcessBaseSchema(p_ParBaseModel.getBaseSchema());
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyDeallAllNotIncludeNew);
		m_ParDealProcess.setIsArchive(p_ParBaseModel.getIsArchive());
		Base m_Base=new Base();
		m_List=m_Base.GetList(p_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow );
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);		
		return m_List;		
	}
	
	
	/**
	 * 描述：我审批过的工单(我审批过，并且以完成的。）
	 * @param p_UserLoginName
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return BaseModel List
	 */
	public List getProcessMyAuditingAndIsFinished(String strLoginName,ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		List m_List=null;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setTasekPersonID(strLoginName);
		m_ParDealProcess.setProcessBaseSchema(p_ParBaseModel.getBaseSchema());
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyAuditingAndIsFinished);
		m_ParDealProcess.setIsArchive(p_ParBaseModel.getIsArchive());
		//设置为审批类型
		m_ParDealProcess.setFlagType("3");
		Base m_Base=new Base();
		m_List=m_Base.GetList(p_ParBaseModel,m_ParDealProcess,p_PageNumber,p_StepRow );
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);		
		return m_List;
	}	
	
	public BaseModel getOneForKey(String p_BaseSchema,String p_BaseID)
	{
		BaseModel m_BaseModel=null;
		if(p_BaseSchema==null)
			p_BaseSchema="";
		if(p_BaseID==null)
			p_BaseID="";
		
		if(!p_BaseSchema.equals("")&&!p_BaseID.equals(""))
		{
			Base m_Base=new Base();
			m_BaseModel=m_Base.GetOneForKey(p_BaseSchema,p_BaseID);
		}
		return m_BaseModel;
	}
	
	/**
	 * 计算总页数
	 * @param p_RusultRows
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
		m_PageCounts=intPages;
	}
	/**
	 * 返回页数
	 */
	public int getPageCount()
	{
		return m_PageCounts;
	}
	
	public int getResultRows()
	{
		return m_rowCount;
	}
	
	
	public String getListForBindSQL(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		Base m_Base=new Base();
		return m_Base.getListForBindSQL(p_ParBaseModel,p_PageNumber,p_StepRow);
	}
	
	public List getListForBind(ParBaseModel p_ParBaseModel,int p_PageNumber,int p_StepRow )
	{
		
		Base m_Base=new Base();
		List m_list=m_Base.getListForBind(p_ParBaseModel,p_PageNumber,p_StepRow);
		
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);			
		return m_list;
	}
	
	public List getBaseListForBind(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		Base m_Base=new Base();
		List m_list=m_Base.getBaseListForBind(p_ParBaseModel,p_ParDealProcess,p_PageNumber,p_StepRow);
		
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);			
		return m_list;		
	}
	
	public String getBaseListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow )
	{
		Base m_Base=new Base();
		
		return m_Base.getBaseListForBindSQL(p_ParBaseModel,p_ParDealProcess,p_PageNumber,p_StepRow);
	}
	
	public List getBaseListForBind(ParBaseModel p_ParBaseModel,ParDealProcessLogModel p_ParDealProcesslog,int p_PageNumber,int p_StepRow )
	{
		Base m_Base=new Base();
		List m_list=m_Base.getBaseListForBind(p_ParBaseModel,p_ParDealProcesslog,p_PageNumber,p_StepRow);
		m_rowCount=m_Base.getQueryResultRows();
		CalculatePages(m_rowCount,p_StepRow);			
		return m_list;
	}
	public String getBaseListForBindSQL(ParBaseModel p_ParBaseModel,ParDealProcessLogModel p_ParDealProcesslog,int p_PageNumber,int p_StepRow )
	{
		Base m_Base=new Base();
		return m_Base.getBaseListForBindSQL(p_ParBaseModel,p_ParDealProcesslog,p_PageNumber,p_StepRow);
	}	
	
	
}
