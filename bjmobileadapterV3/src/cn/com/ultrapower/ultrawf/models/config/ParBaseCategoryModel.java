package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParBaseCategoryModel {
	
//	属性设置区域--Begin--
	private  String BaseCategoryID="";
	private  String BaseCategoryName="";
	private  String BaseCategorySchama="";
	private  String BaseCategoryCode="";
	private  int BaseCategoryIsFlow=999;
	private  int BaseCategoryState=999;
	private  String BaseCategoryDesc="";
	
	private String OrderbyFiledNameString="";
	private int OrderByType=0;
//	1 BaseCategoryID 本记录的唯一标识，创建是自动形成，无业务含义
	public String GetBaseCategoryID()
	{
	   return BaseCategoryID;
	}
	public void   SetBaseCategoryID(String p_BaseCategoryID)
	{
	   BaseCategoryID=p_BaseCategoryID;
	}
	
//	650000001 BaseCategoryName 工单类别名称
	public String GetBaseCategoryName()
	{
	   return BaseCategoryName;
	}
	public void   SetBaseCategoryName(String p_BaseCategoryName)
	{
	   BaseCategoryName=p_BaseCategoryName;
	}
//	650000002 BaseCategorySchama 工单Form名


	public String GetBaseCategorySchama()
	{
	   return BaseCategorySchama;
	}
	public void   SetBaseCategorySchama(String p_BaseCategorySchama)
	{
	   BaseCategorySchama=p_BaseCategorySchama;
	}
//	650000003 BaseCategoryCode 工单代码，根据用户需求制定工单的代码
	public String GetBaseCategoryCode()
	{
	   return BaseCategoryCode;
	}
	public void   SetBaseCategoryCode(String p_BaseCategoryCode)
	{
	   BaseCategoryCode=p_BaseCategoryCode;
	}
//	650000004 BaseCategoryIsFlow 是否固定流程状态（0）否，（1）是
	public int GetBaseCategoryIsFlow()
	{
	   return BaseCategoryIsFlow;
	}
	public void   SetBaseCategoryIsFlow(int p_BaseCategoryIsFlow)
	{
	   BaseCategoryIsFlow=p_BaseCategoryIsFlow;
	}
//	650000005 BaseCategoryState 状态（0）停用，（1）可用


	public int GetBaseCategoryState()
	{
	   return BaseCategoryState;
	}
	public void   SetBaseCategoryState(int p_BaseCategoryState)
	{
	   BaseCategoryState=p_BaseCategoryState;
	}

//	650000007 BaseCategoryDesc 描述
	public String GetBaseCategoryDesc()
	{
	   return BaseCategoryDesc;
	}
	public void   SetBaseCategoryDesc(String p_BaseCategoryDesc)
	{
	   BaseCategoryDesc=p_BaseCategoryDesc;
	}
//	属性设置区域--End--	

	public String GetWhereSql()
	{
		StringBuffer sqlString=new StringBuffer();
		//1	BaseCategoryID				本记录的唯一标识，创建是自动形成，无业务含义
		if(!BaseCategoryID.equals(""))
			sqlString.append(" and C1='"+BaseCategoryID+"'");
		//650000001	BaseCategoryName		工单类别名称
		if(!BaseCategoryName.equals(""))
			sqlString.append(" and C650000001='"+BaseCategoryName+"'");		
		//650000002	BaseCategorySchama		工单Form名		sqlString.append(UnionConditionForSql.getStringFiledSql("","C650000002",GetBaseCategorySchama()));
		//650000003	BaseCategoryCode		工单代码，根据用户需求制定工单的代码
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C650000003",GetBaseCategoryCode()));
		
		//650000004	BaseCategoryIsFlow		是否固定流程状态（0）否，（1）是
		if(BaseCategoryIsFlow!=999)
			sqlString.append(" and C650000004='"+Integer.toString(BaseCategoryIsFlow)+"'");			
		//650000005	BaseCategoryState		状态（0）停用，（1）可用
		if(BaseCategoryState!=999)
			sqlString.append(" and C650000005='"+Integer.toString(BaseCategoryState)+"'");		
		//650000007	BaseCategoryDesc		描述	
		if(!BaseCategoryDesc.equals(""))
			sqlString.append(" and C650000007='"+BaseCategoryDesc+"'");			
		return sqlString.toString();
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
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString,int p_intOrderByType) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=p_intOrderByType;
	}	
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=0;
	}	
}
