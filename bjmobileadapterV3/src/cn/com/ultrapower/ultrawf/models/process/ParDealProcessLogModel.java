package cn.com.ultrapower.ultrawf.models.process;

import java.util.*;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;
import cn.com.ultrapower.ultrawf.share.queryanalyse.*;
public class ParDealProcessLogModel {

//	属性设置区域--Begin--
	private  String ProcessLogID="";
	private  String ProcessID="";
	private  String Act="";
	private  String logUser="";
	private  String logUserID="";
	private  long StDate=0;
	private  long StDateBegin=0;
	private  long StDateEnd=0;
	private  String Result="";
	private String ExtendSql="";
	
	private String BaseID="";
	private String BaseSchema="";
	//用于排序字段的名称
	private String OrderbyFiledNameString="";
	//排序类型 0升序　否则为降序
	private int OrderByType=0;	
	
	//是否已存入历史记录表
	private int IsArchive=999;
	
	private int ProcessOptionalType=0;//3为审批 其它的为处理
	
	public int getIsArchive() {
		return IsArchive;
	}
	public void setIsArchive(int isArchive) {
		IsArchive = isArchive;
	}
//	本记录的唯一标识，创建是自动形成，无业务含义
	public String getProcessLogID()
	{
	   return ProcessLogID;
	}
	public void   setProcessLogID(String p_ProcessLogID)
	{
	   ProcessLogID=p_ProcessLogID;
	}
//	指向主工单处理过程记录的指针
	public String getProcessID()
	{
	   return ProcessID;
	}
	public void   setProcessID(String p_ProcessID)
	{
	   ProcessID=p_ProcessID;
	}
//	记录的动作

	public String getAct()
	{
	   return Act;
	}
	public void   setAct(String p_Act)
	{
	   Act=p_Act;
	}
//	记录的用户名字

	public String getlogUser()
	{
	   return logUser;
	}
	public void   setlogUser(String p_logUser)
	{
	   logUser=p_logUser;
	}
//	记录的用户登陆名
	public String getlogUserID()
	{
	   return logUserID;
	}
	public void   setlogUserID(String p_logUserID)
	{
	   logUserID=p_logUserID;
	}
//	创建/生效时间，表示该记录创建/生效时间
	public long getStDate()
	{
	   return StDate;
	}
	public void   setStDate(long p_StDate)
	{
	   StDate=p_StDate;
	}
//	Dealer记录的结果

	public String getResult()
	{
	   return Result;
	}
	public void   setResult(String p_Result)
	{
	   Result=p_Result;
	}
//	属性设置区域--End--	
	
	public String GetWhereSql()
	{
		StringBuffer sqlString=new StringBuffer();
		//1	ProcessLogID		本记录的唯一标识，创建是自动形成，无业务含义
		//if(!ProcessLogID.equals(""))
		//	sqlString.append(" and C1='"+ProcessLogID+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C1",ProcessLogID));
		//700020401	ProcessID	指向主工单处理过程记录的指针
		//if(!ProcessID.equals(""))
		//	sqlString.append(" and C700020401='"+ProcessID+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020401",ProcessID));
		//700020402	Act		记录的动作
		//if(!Act.equals(""))
		//	sqlString.append(" and C700020402='"+Act+"'");	
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020402",Act));
		//700020403	logUser		记录的用户名字
		//if(!logUser.equals(""))
		//	sqlString.append(" and C700020403='"+logUser+"'");	
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020403",logUser));
		//700020404	logUserID	记录的用户登陆名
		//if(!logUserID.equals(""))
		//	sqlString.append(" and C700020404='"+logUserID+"'");	
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020404",logUserID));
		//700020405	StDate		创建/生效时间，表示该记录创建/生效时间
		if(this.getStDate()>0)
			sqlString.append(" and C700020405="+String.valueOf(getStDate())+"");
		if(this.getStDateBegin()>0)
			sqlString.append(" and C700020405>="+String.valueOf(getStDateBegin())+"");
		if(this.getStDateEnd()>0)
			sqlString.append(" and C700020405<="+String.valueOf(getStDateEnd())+"");
		
		//700020406	Result		Dealer记录的结果
		//if(!Result.equals(""))
		//	sqlString.append(" and C700020406='"+Result+"'");		
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020406",Result));
		
		//700020407 ProcessLogBaseID 工单号
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020407",this.getBaseID()));
		//700020408 ProcessLogBaseSchema 工单类别
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020408",this.getBaseSchema() ));		
		
		if(!this.getExtendSql().trim().equals(""))
			sqlString.append(getExtendSql());
		
		return sqlString.toString();
		
	}
	public long getStDateBegin() {
		return StDateBegin;
	}
	public void setStDateBegin(long stDateBegin) {
		StDateBegin = stDateBegin;
	}
	public long getStDateEnd() {
		return StDateEnd;
	}
	public void setStDateEnd(long stDateEnd) {
		StDateEnd = stDateEnd;
	}	
	
	//返回排序字符串

	public String getOrderbyFiledNameString() 
	{
		String strRe="";
		if(!OrderbyFiledNameString.trim().equals(""))
		{
			//如果升序
			if(OrderByType==0)
				strRe=" order by "+OrderbyFiledNameString;
			else
			{
				String[] strAry =OrderbyFiledNameString.split(",");
				
				for(int index=0;index<strAry.length;index++)
				{	if(strRe.trim().equals(""))
						strRe+= strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return (strRe);
	}
	/**
	 * 设置排序字段
	 * @param p_strOrderByFiledNameString
	 * @param p_intOrderByType 0 升序 否则为降序
	 */
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString,int p_intOrderByType) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=p_intOrderByType;
	}	
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=0;
	}
	public String getExtendSql() {
		return ExtendSql;
	}
	public void setExtendSql(String extendSql) {
		ExtendSql = extendSql;
	}
	public String getBaseID() {
		return BaseID;
	}
	public void setBaseID(String baseID) {
		BaseID = baseID;
	}
	public String getBaseSchema() {
		return BaseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		BaseSchema = baseSchema;
	}	
	
	public int getProcessOptionalType() { 
		return ProcessOptionalType;
	}
	public void setProcessOptionalType(int processOptionalType) {
		ProcessOptionalType = processOptionalType;
	}	
	
	
	public List getContionFiledInfoList() {
		
		List FiledInfoList=setFiledList();
		return FiledInfoList;
	}
	
	private List setFiledList()
	{
		
		List m_FiledList=new ArrayList();
		ParFiledInfo m_ParFiledInfo;
		String tblAlias="deallog";
		if(!tblAlias.equals(""))
			tblAlias+=".";
		PariseUntil m_PariseUntil=new PariseUntil();
		
		//1	ProcessLogID		本记录的唯一标识，创建是自动形成，无业务含义
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C1",ProcessLogID);
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);
		/*
		if(!ProcessLogID.equals(""))
			sqlString.append(" and C1='"+ProcessLogID+"'");
			*/
		//700020401	ProcessID	指向主工单处理过程记录的指针
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020401",ProcessID);
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);		
		//if(!ProcessID.equals(""))
		//	sqlString.append(" and C700020401='"+ProcessID+"'");
		//700020402	Act		记录的动作
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020402",Act);
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);

		//700020403	logUser		记录的用户名字
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"700020403",logUser);
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);
	
		//700020404	logUserID	记录的用户登陆名
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020404",logUserID);
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);		
		
		//700020405	StDate		创建/生效时间，表示该记录创建/生效时间
		if(this.getStDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020405",String.valueOf(getStDate()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);			
		}
		if(this.getStDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020405",">="+String.valueOf(getStDateBegin()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);			
		//	sqlString.append(" and C700020405>="+String.valueOf(getStDateBegin())+"");
		}
		if(this.getStDateEnd()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020405","<="+String.valueOf(getStDateBegin()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);			
			//sqlString.append(" and C700020405<="+String.valueOf(getStDateEnd())+"");
		}
		
		//700020406	Result		Dealer记录的结果
		if(!Result.equals(""))
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020406",Result);
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);			
			//sqlString.append(" and C700020406='"+Result+"'");		
		}
		
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020407",this.getBaseID());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700020408",this.getBaseSchema());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);		
		
		m_PariseUntil=null;
		return m_FiledList;
	}	
	
	
}
