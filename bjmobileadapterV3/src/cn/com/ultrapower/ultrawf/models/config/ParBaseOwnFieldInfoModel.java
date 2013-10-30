package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParBaseOwnFieldInfoModel {

//	属性设置区域--Begin--
	private  String BaseOwnFieldInfoID="";
	private  String BaseCategoryName="";
	private  String BaseCategorySchama="";
	private  String Base_field_ID="";
	private  String Base_field_DBName="";
	private  String Base_field_ShowName="";
	private  String Base_field_Type="";
	private  String Base_field_TypeValue="";
	private  String BaseFree_field_ShowStep="";
	private  String BaseFree_field_EditStep="";
	private  String BaseFix_field_ShowStep="";
	private  String BaseFix_field_EditStep="";
	private  String BaseOwnFieldInfoDesc="";
	
	private  String PrintOrder="";
	
	private  String   EntryMode; //650000014 Entry Mode 0：Display Only 	1：Optional	2：Required
	private  String   VarcharFieldeIsExceed;//650000015 字符型字段是否超过长度。0：否 1：是
	private  String   LogIsWrite;// 在“工单处理对字段进行修改记录表”是否需要记录字段变化	0：否 1：是		
	
	
	public String getPrintOrder() {
		return PrintOrder;
	}
	public void setPrintOrder(String printOrder) {
		PrintOrder = printOrder;
	}	
	
//	1 BaseOwnFieldInfoID 本记录的唯一标识，创建是自动形成，无业务含义
	public String GetBaseOwnFieldInfoID()
	{
	   return BaseOwnFieldInfoID;
	}
	public void   SetBaseOwnFieldInfoID(String p_BaseOwnFieldInfoID)
	{
	   BaseOwnFieldInfoID=p_BaseOwnFieldInfoID;
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
//	650000003 Base_field_ID 工单字段ID
	public String GetBase_field_ID()
	{
	   return Base_field_ID;
	}
	public void   SetBase_field_ID(String p_Base_field_ID)
	{
	   Base_field_ID=p_Base_field_ID;
	}
//	650000004 Base_field_DBName 工单字段数据库名
	public String GetBase_field_DBName()
	{
	   return Base_field_DBName;
	}
	public void   SetBase_field_DBName(String p_Base_field_DBName)
	{
	   Base_field_DBName=p_Base_field_DBName;
	}
//	650000005 Base_field_ShowName 工单字段显示名
	public String GetBase_field_ShowName()
	{
	   return Base_field_ShowName;
	}
	public void   SetBase_field_ShowName(String p_Base_field_ShowName)
	{
	   Base_field_ShowName=p_Base_field_ShowName;
	}
//	650000006 Base_field_Type 工单字段类型（数字、字符串、选择）
	public String GetBase_field_Type()
	{
	   return Base_field_Type;
	}
	public void   SetBase_field_Type(String p_Base_field_Type)
	{
	   Base_field_Type=p_Base_field_Type;
	}
//	650000007 Base_field_TypeValue 工单字段类型值（只有类型为：选择时才有值）
	public String GetBase_field_TypeValue()
	{
	   return Base_field_TypeValue;
	}
	public void   SetBase_field_TypeValue(String p_Base_field_TypeValue)
	{
	   Base_field_TypeValue=p_Base_field_TypeValue;
	}
//	650000008 BaseFree_field_ShowStep 自由流程显示阶段（工单状态，用“;”隔开）
	public String GetBaseFree_field_ShowStep()
	{
	   return BaseFree_field_ShowStep;
	}
	public void   SetBaseFree_field_ShowStep(String p_BaseFree_field_ShowStep)
	{
	   BaseFree_field_ShowStep=p_BaseFree_field_ShowStep;
	}
//	650000009 BaseFree_field_EditStep 自由流程编辑阶段（工单状态，用“;”隔开）
	public String GetBaseFree_field_EditStep()
	{
	   return BaseFree_field_EditStep;
	}
	public void   SetBaseFree_field_EditStep(String p_BaseFree_field_EditStep)
	{
	   BaseFree_field_EditStep=p_BaseFree_field_EditStep;
	}
//	650000010 BaseFix_field_ShowStep 固定流程显示阶段
	public String GetBaseFix_field_ShowStep()
	{
	   return BaseFix_field_ShowStep;
	}
	public void   SetBaseFix_field_ShowStep(String p_BaseFix_field_ShowStep)
	{
	   BaseFix_field_ShowStep=p_BaseFix_field_ShowStep;
	}
//	650000011 BaseFix_field_EditStep 固定流程编辑阶段
	public String GetBaseFix_field_EditStep()
	{
	   return BaseFix_field_EditStep;
	}
	public void   SetBaseFix_field_EditStep(String p_BaseFix_field_EditStep)
	{
	   BaseFix_field_EditStep=p_BaseFix_field_EditStep;
	}
//	650000012 BaseOwnFieldInfoDesc 字段信息描述
	public String GetBaseOwnFieldInfoDesc()
	{
	   return BaseOwnFieldInfoDesc;
	}
	public void   SetBaseOwnFieldInfoDesc(String p_BaseOwnFieldInfoDesc)
	{
	   BaseOwnFieldInfoDesc=p_BaseOwnFieldInfoDesc;
	}
//	属性设置区域--End--
	
	public String GetWhereSql()
	{
		StringBuffer sqlString=new StringBuffer(); 
		
		//1		BaseOwnFieldInfoID		本记录的唯一标识，创建是自动形成，无业务含义
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C1",GetBaseOwnFieldInfoID()));

		//650000001	BaseCategoryName		工单类别名称
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C1",GetBaseCategoryName()));
	
		//650000002	BaseCategorySchama		工单Form名
		if(!BaseCategorySchama.equals(""))
			sqlString.append(" and C650000002='"+BaseCategorySchama+"'");		
		//650000003	Base_field_ID			工单字段ID
		if(!Base_field_ID.equals(""))
			sqlString.append(" and C650000003='"+Base_field_ID+"'");		
		//650000004	Base_field_DBName		工单字段数据库名
		if(!Base_field_DBName.equals(""))
			sqlString.append(" and C650000004='"+Base_field_DBName+"'");		
		//650000005	Base_field_ShowName		工单字段显示名
		if(!Base_field_ShowName.equals(""))
			sqlString.append(" and C650000005 like'%"+Base_field_ShowName+"%'");		
		//650000006	Base_field_Type			工单字段类型（数字、字符串、选择）
		if(!Base_field_Type.equals(""))
			sqlString.append(" and C650000006='"+Base_field_Type+"'");
		//650000007	Base_field_TypeValue		工单字段类型值（只有类型为：选择时才有值）
		if(!Base_field_TypeValue.equals(""))
			sqlString.append(" and C650000007 ='"+Base_field_TypeValue+"'");		
		
		//650000013 打印的顺序 PrintOrder
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C650000013",getPrintOrder()));	
		//650000008	BaseFree_field_ShowStep		自由流程显示阶段（工单状态，用“;”隔开）
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C650000008",GetBaseFree_field_ShowStep()));

		//   EntryMode; 650000014 Entry Mode 0：Display Only 	1：Optional	2：Required
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C650000014",getEntryMode()));	
		//   VarcharFieldeIsExceed;//650000015 字符型字段是否超过长度。0：否 1：是
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C650000015",getVarcharFieldeIsExceed()));
		//  650000016 LogIsWrite;// 在“工单处理对字段进行修改记录表”是否需要记录字段变化	0：否 1：是		
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C650000016",getLogIsWrite()));
		
		
		//650000009	BaseFree_field_EditStep		自由流程编辑阶段（工单状态，用“;”隔开）
		if(!BaseFree_field_EditStep.equals(""))
			sqlString.append(" and C650000009 like'%"+BaseFree_field_EditStep+"%'");		
		//650000010	BaseFix_field_ShowStep		固定流程显示阶段
		if(!BaseFix_field_ShowStep.equals(""))
			sqlString.append(" and C650000010='"+BaseFix_field_ShowStep+"'");		
		//650000011	BaseFix_field_EditStep		固定流程编辑阶段
		if(!BaseFix_field_EditStep.equals(""))
			sqlString.append(" and C650000011='"+BaseFix_field_EditStep+"'");		
		//650000012	BaseOwnFieldInfoDesc		字段信息描述
		if(!BaseOwnFieldInfoDesc.equals(""))
			sqlString.append(" and C650000012 like'%"+BaseOwnFieldInfoDesc+"%'");		
		
			
		
		return sqlString.toString();
	}
	public String getEntryMode() {
		return EntryMode;
	}
	public void setEntryMode(String entryMode) {
		EntryMode = entryMode;
	}
	public String getLogIsWrite() {
		return LogIsWrite;
	}
	public void setLogIsWrite(String logIsWrite) {
		LogIsWrite = logIsWrite;
	}
	public String getVarcharFieldeIsExceed() {
		return VarcharFieldeIsExceed;
	}
	public void setVarcharFieldeIsExceed(String varcharFieldeIsExceed) {
		VarcharFieldeIsExceed = varcharFieldeIsExceed;
	}
	
	//用于排序字段的名称

	private String OrderbyFiledNameString="";
	//排序类型 0升序　否则为降序 

	private int OrderByType=0;
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
						strRe+=" order by "+ strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				//strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return (strRe);
	}	
	
}
