package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class BaseOwnFieldInfoModel {

//	属性设置区域--Begin--
	private  String BaseOwnFieldInfoID;
	private  String BaseCategoryName;
	private  String BaseCategorySchama;
	private  String Base_field_ID;
	private  String Base_field_DBName;
	private  String Base_field_ShowName;
	private  String Base_field_Type;
	private  String Base_field_TypeValue;
	private  String BaseFree_field_ShowStep;
	private  String BaseFree_field_EditStep;
	private  String BaseFix_field_ShowStep;
	private  String BaseFix_field_EditStep;
	private  String BaseOwnFieldInfoDesc;
	private  int 	PrintOneLine=0;
	private  int	PrintOrder=0;
	
	private  int   EntryMode; //650000014 Entry Mode 0：Display Only 	1：Optional	2：Required
	private  int   VarcharFieldeIsExceed=0;//650000015 字符型字段是否超过长度。0：否 1：是
	private  int   LogIsWrite;// 在“工单处理对字段进行修改记录表”是否需要记录字段变化	0：否 1：是	

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
	
	//PrintOneLine	650000012	Selection(Radio Button)	打印是否占一行
	public int GetPrintOneLine()
	{
	   return PrintOneLine;
	}
	public void   SetPrintOneLine(int p_PrintOneLine)
	{
		PrintOneLine=p_PrintOneLine;
	}	
	//PrintOrder	650000013	Interger	打印的顺序 如果顺序中间有间隔，表示该字段的后面需要空一个间隔。
	public int GetPrintOrder()
	{
	   return PrintOrder;
	}
	public void   SetPrintOrder(int p_PrintOrder)
	{
		PrintOrder=p_PrintOrder;
	}
	public int getEntryMode() {
		return EntryMode;
	}
	public void setEntryMode(int entryMode) {
		EntryMode = entryMode;
	}
	public int getLogIsWrite() {
		return LogIsWrite;
	}
	public void setLogIsWrite(int logIsWrite) {
		LogIsWrite = logIsWrite;
	}
	public int getVarcharFieldeIsExceed() {
		return VarcharFieldeIsExceed;
	}
	public void setVarcharFieldeIsExceed(int varcharFieldeIsExceed) {
		VarcharFieldeIsExceed = varcharFieldeIsExceed;
	}
	
//	属性设置区域--End--
	
	private String ModifyUrl="";
	public String getModifyUrl() {
		if(ModifyUrl==null)
			ModifyUrl="";
		if(ModifyUrl.equals(""))
		{
			String strModifyUrl = Constants.REMEDY_QUERY_URL;
			strModifyUrl = Constants.REMEDY_QUERY_URL.replaceAll("<REMEDY_FROM>",Constants.TblBaseOwnFieldInfo);
			strModifyUrl=strModifyUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
			strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
			strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.GetBaseOwnFieldInfoID());
			setModifyUrl(strModifyUrl);
		}
		return ModifyUrl;
	}	
	
	public void setModifyUrl(String baseModifyUrl) {
		ModifyUrl = baseModifyUrl;
	}	
	
}
