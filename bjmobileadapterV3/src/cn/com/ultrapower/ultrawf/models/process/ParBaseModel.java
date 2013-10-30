package cn.com.ultrapower.ultrawf.models.process;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.config.ParBaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;
import cn.com.ultrapower.ultrawf.share.queryanalyse.ParFiledInfo;
import cn.com.ultrapower.ultrawf.share.queryanalyse.PariseUntil;

public class ParBaseModel {

	//	属性设置区域--Begin--
	private String BaseModifyUrl="";
	
	private String BaseID="";

	private String BaseTplID="";

	private String BaseSchema="";

	private String BaseName="";

	private String BaseSN="";

	private String BaseCreatorFullName="";

	private String BaseCreatorLoginName="";

	private long BaseCreateDate=0;
	private long BaseCreateDateBegin=0;
	private long BaseCreateDateEnd=0;

	private long BaseSendDate=0;

	private long BaseFinishDate=0;
	
	private long BaseFinishDateBegin=0;
	private long BaseFinishDateEnd=0;	
 
	private String BaseCloseDateString="";
	private long BaseCloseDate=0;
	private long BaseCloseDateBegin=0;
	private long BaseCloseDateEnd=0;
 
	private String BaseStatus="";

	/*private String BaseSummary="";

	private String BaseItems="";

	private String BasePriority="";

	private int BaseIsAllowLogGroup=999;

	private long BaseAcceptOutTime;

	private long BaseDealOutTime=0;

	private String BaseDescrption="";
	*/
	private String BaseResult="";

	private String BaseCloseSatisfy="";
	//是否已存入历史记录表
	private int IsArchive=999;
	//工单是否已经关闭或作废
	private int BaseIsTrueClose=999;
	
	//用于排序字段的名称
	private String OrderbyFiledNameString="";
	//排序类型 0升序　否则为降序
	private int OrderByType=0;
	private Hashtable hsOwnFiled=null;
	
	private String ExtendSql="";
	
	//工单特有字段取值，因为每类工单都有自己特有而其它工单没有的字段，为了方便和同一取值采用了该方法	public  ParBaseModel()
	{
		//setOwnerFiled("");
	}
	public ParBaseModel(String p_strBaseSchema)
	{
		setOwnerFiled(p_strBaseSchema);
		this.setBaseSchema(p_strBaseSchema);
	}
	
	/**
	 * 根据字段ID给属性赋值
	 * @param p_FiledID
	 * @param p_FiledValue
	 */
	public void setFiledVale(String p_FiledID,String p_FiledValue)
	{
		String strFileID=p_FiledID.trim();
		
		
		//	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
		if(strFileID.equals("1"))
			setBaseID(p_FiledValue);
		
		//	700000022	BaseTplID	用户选择的固定流程工单模板ID
		else if(strFileID.equals("700000022"))
			setBaseTplID(p_FiledValue);
		
		//	700000001	BaseSchema 指向主工单记录的工单类别的标示（新建模式下默认是工单的Form名）
		else if(strFileID.equals("700000001"))
			setBaseSchema(p_FiledValue);
		//	700000002	BaseName	工单名称
		else if(strFileID.equals("700000002"))
			setBaseName(p_FiledValue);
		//	700000003	BaseSN	工单业务流水号，由（’ID’+’工单类别ID’+’时间’+’天的流水号'）组成（在Submit的Fiter中创建）
		else if(strFileID.equals("700000003"))
			setBaseSN(p_FiledValue);
		// 700000004	BaseCreatorFullName	建单人名（新建模式下打开工单时默认填写）
		else if(strFileID.equals("700000004"))
			setBaseCreatorFullName(p_FiledValue);
		// 700000005	BaseCreatorLoginName   建单人登陆名（新建模式下打开工单时默认填写）
		else if(strFileID.equals("700000005"))
			setBaseCreatorLoginName(p_FiledValue);
		
		//700000006	BaseCreateDate	建单时间（新建模式下打开工单时默认填写）
		else if(strFileID.equals("700000006"))
		{
			//说明:因为在日期型的属性定义为长整型类型,所以不能将>=或<=操作符号带过来的因此在此作分解
			//以后日期属性的也应该定义为字符型的类型,为了不影响其它模块的调用在此就不再修改为字符型(2007-03-06 xfq)
			String[] SplAry=p_FiledValue.split(":");
			String keyWord="";
			String strValue="";
			if(SplAry.length>=1)
			{
				keyWord=SplAry[0];
				//keyWord=keyWord.trim().toUpperCase();
			}

			if(keyWord.equals(">="))
			{
				strValue=p_FiledValue.replaceAll(">=:","");
				if(!strValue.trim().equals(""))
				{
					
					setBaseCreateDateBegin(FormatTime.FormatDateStringToInt(strValue));
				}
			}
			else if(keyWord.equals("<="))
			{
				
				strValue=p_FiledValue.replaceAll("<=:","");
				
				if(!strValue.trim().equals(""))
				{
					int typeLen=strValue.split(":").length;
					if(typeLen<=2)
						strValue+=" 23:59:00";
					setBaseCreateDateEnd(FormatTime.FormatDateStringToInt(strValue));
				}
			}
			else
			{
				strValue=p_FiledValue.replaceAll(keyWord+":","");
				if(!strValue.trim().equals(""))
					setBaseCreateDate(FormatTime.FormatDateStringToInt(strValue));
			}			
		}
		//700000008	BaseFinishDate		完成时间
		else if(strFileID.equals("700000008"))
		{
			//setBaseFinishDate(Long.parseLong(p_FiledValue));
			String[] SplAry=p_FiledValue.split(":");
			String keyWord="";
			String strValue="";
			if(SplAry.length>=1)
			{
				keyWord=SplAry[0];
				keyWord=keyWord.trim().toUpperCase();
			}

	
			if(keyWord.equals(">="))
			{
				strValue=p_FiledValue.replaceAll(">=:","");
				if(!strValue.trim().equals(""))
					setBaseFinishDateBegin(FormatTime.FormatDateStringToInt(strValue));
			}
			else if(keyWord.equals("<="))
			{
				
				strValue=p_FiledValue.replaceAll("<=:","");
				if(!strValue.trim().equals(""))
				{	
					int typeLen=strValue.split(":").length;
					if(typeLen<=2)
						strValue+=" 23:59:00";
					setBaseFinishDateEnd(FormatTime.FormatDateStringToInt(strValue));
				}
			}
			else
			{
				setBaseFinishDate(FormatTime.FormatDateStringToInt(strValue));
			}			
		}
		//700000009	BaseCloseDate		关闭时间
		else if(strFileID.equals("700000009"))
		{
			String[] SplAry=p_FiledValue.split(":");
			String keyWord="";
			String strValue="";
			if(SplAry.length>=1)
			{
				keyWord=SplAry[0];
				keyWord=keyWord.trim().toUpperCase();
			}

	
			if(keyWord.equals(">="))
			{
				strValue=p_FiledValue.replaceAll(">=:","");
				if(!strValue.trim().equals(""))
					setBaseCloseDateBegin(FormatTime.FormatDateStringToInt(strValue));
			}
			else if(keyWord.equals("<="))
			{
				strValue=p_FiledValue.replaceAll("<=:","");
				if(!strValue.trim().equals(""))
				{
					int typeLen=strValue.split(":").length;
					if(typeLen<=2)
						strValue+=" 23:59:00";					
					setBaseCloseDateEnd(FormatTime.FormatDateStringToInt(strValue));
				}
			}
			else
			{
				setBaseCloseDate(FormatTime.FormatDateStringToInt(strValue));
			}

		}
		//700000010	BaseStatus		状态名（新建模式下打开工单时默认填写为“草稿”）
		else if(strFileID.equals("700000010"))
			setBaseStatus(p_FiledValue);
		else if(strFileID.equals("700000020"))
			setBaseResult(p_FiledValue);
		
		//700000021	BaseCloseSatisfy	工单关闭的满意度
		else if(strFileID.equals("700000021"))
			setBaseCloseSatisfy(p_FiledValue);
		//sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700000023",this.getIsArchive()));
		//查询是否历史的工单
		else if(strFileID.equals("700000023"))
		{
			String[] SplAry=p_FiledValue.split(":");
			String strValue="";
			if(SplAry.length>1)
			{
				strValue=SplAry[1];
			}
			else
				strValue=p_FiledValue;
			if(strValue.equals(""))
				strValue="999";//999为默认值，表示未进行赋值操作在组织查询sql时不作为查询条件
			setIsArchive(Integer.parseInt(strValue));
		}
		//BaseIsTrueClose	700000030	Interger	工单是否已经关闭或作废 0否　1是
		else if(strFileID.equals("700000030"))
		{
			String[] SplAry=p_FiledValue.split(":");
			String strValue="";
			if(SplAry.length>1)
			{
				strValue=SplAry[1];
			}
			else
				strValue=p_FiledValue;
			if(strValue.equals(""))
				strValue="999";//999为默认值，表示未进行赋值操作在组织查询sql时不作为查询条件
			setBaseIsTrueClose(Integer.parseInt(strValue));			
		}
		else
		{
			SetOwnerFiled("C"+strFileID,p_FiledValue);
		}
	}
	
	/**
	 * 
	 * @param p_FiledID 字段名称 数据库中的字段 如:C7000001
	 * @param p_FiledValue 值
	 */
	public void SetOwnerFiled(String p_FiledID,String p_FiledValue)
	{
		
		if(hsOwnFiled==null)
		{
			//hsOwnFiled=new Hashtable();
			setOwnerFiled(this.getBaseSchema());
			
		}
		if(hsOwnFiled.get(p_FiledID)!=null)
		{
			p_FiledID=p_FiledID.toUpperCase();
			hsOwnFiled.put(p_FiledID,p_FiledValue);
		}
	}
	
	public String GetOwnerFiledValue(String p_FiledID)
	{
		if(p_FiledID!=null)
			p_FiledID=p_FiledID.toUpperCase();
		if(hsOwnFiled!=null)
			if(hsOwnFiled.get(p_FiledID)!=null)
				return hsOwnFiled.get(p_FiledID).toString();
			else
				return "";
		else
			return "";
	}	

	//	本记录的唯一标识，创建是自动形成，无业务含义
	public String getBaseID() {
		return BaseID;
	}

	public void setBaseID(String p_BaseID) {
		BaseID = p_BaseID;
	}

	//	用户选择的固定流程工单模板ID
	public String getBaseTplID() {
		return BaseTplID;
	}

	public void setBaseTplID(String p_BaseTplID) {
		BaseTplID = p_BaseTplID;
	}

	//	指向主工单记录的工单类别的标示（新建模式下默认是工单的Form名）
	public String getBaseSchema() {
		return BaseSchema;
	}

	public void setBaseSchema(String p_BaseSchema) {
		if(p_BaseSchema!=null)
			BaseSchema = p_BaseSchema;
		else
			BaseSchema="";
	}

	//	工单名称（新建模式下通过工单的Form名到工单类别表中查询）


	public String getBaseName() {
		return BaseName;
	}

	public void setBaseName(String p_BaseName) {
		BaseName = p_BaseName;
	}

	//	工单业务流水号，由（’ID’+’工单类别ID’+’时间’+’天的流水号'）组成（在Submit的Fiter中创建）。


	public String getBaseSN() {
		return BaseSN;
	}

	public void setBaseSN(String p_BaseSN) {
		BaseSN = p_BaseSN;
	}

	//	建单人名（新建模式下打开工单时默认填写）
	public String getBaseCreatorFullName() {
		return BaseCreatorFullName;
	}

	public void setBaseCreatorFullName(String p_BaseCreatorFullName) {
		BaseCreatorFullName = p_BaseCreatorFullName;
	}

	//	建单人登陆名（新建模式下打开工单时默认填写）
	public String getBaseCreatorLoginName() {
		return BaseCreatorLoginName;
	}

	public void setBaseCreatorLoginName(String p_BaseCreatorLoginName) {
		BaseCreatorLoginName = p_BaseCreatorLoginName;
	}

	//	建单时间（新建模式下打开工单时默认填写）
	public long getBaseCreateDate() {
		return BaseCreateDate;
	}
	public void setBaseCreateDate(long p_BaseCreateDate) {
		BaseCreateDate = p_BaseCreateDate;
	}
	
	public void setBaseCreateDateBegin(long p_BaseCreateDateBegin) {
		BaseCreateDateBegin = p_BaseCreateDateBegin;
	}
	
	public long getBaseCreateDateBegin() {
		return BaseCreateDateBegin;
	}

	public void setBaseCreateDateEnd(long p_BaseCreateDateEnd)
	{
		BaseCreateDateEnd = p_BaseCreateDateEnd;
	}
	public long getBaseCreateDateEnd() {
		return BaseCreateDateEnd;
	}
	

	//	派单时间
	public long getBaseSendDate() {
		return BaseSendDate;
	}

	public void setBaseSendDate(long p_BaseSendDate) {
		BaseSendDate = p_BaseSendDate;
	}
	
	//	完成时间
	public long getBaseFinishDate() {
		return BaseFinishDate;
	}
	public void setBaseFinishDate(long p_BaseFinishDate) {
		BaseFinishDate = p_BaseFinishDate;
	}

	//	关闭时间
	public long getBaseCloseDate() {
		return BaseCloseDate;
	}

	public void setBaseCloseDate(long p_BaseCloseDate) {
		BaseCloseDate = p_BaseCloseDate;
	}

	//	关闭时间(因为要加运算符所以加了这个方法)
	public String getBaseCloseDateString() {
		return BaseCloseDateString;
	}

	public void setBaseCloseDateString(String p_strBaseCloseDate) {
		BaseCloseDateString = p_strBaseCloseDate;
	}	
	
	//	状态名（新建模式下打开工单时默认填写为“草稿”）
	public String getBaseStatus() {
		return BaseStatus;
	}

	public void setBaseStatus(String p_BaseStatus) {
		BaseStatus = p_BaseStatus;
	}
	/*
	//	主题
	public String getBaseSummary() {
		return BaseSummary;
	}

	public void setBaseSummary(String p_BaseSummary) {
		BaseSummary = p_BaseSummary;
	}

	//	专业，类别


	public String getBaseItems() {
		return BaseItems;
	}

	public void setBaseItems(String p_BaseItems) {
		BaseItems = p_BaseItems;
	}

	//	紧急程度


	public String getBasePriority() {
		return BasePriority;
	}

	public void setBasePriority(String p_BasePriority) {
		BasePriority = p_BasePriority;
	}

	//	是否允许同组关单：、0否、1是，默认为0
	public int getBaseIsAllowLogGroup() {
		return BaseIsAllowLogGroup;
	}

	public void setBaseIsAllowLogGroup(int p_BaseIsAllowLogGroup) {
		BaseIsAllowLogGroup = p_BaseIsAllowLogGroup;
	}

	//	工单受理时限
	public long getBaseAcceptOutTime() {
		return BaseAcceptOutTime;
	}

	public void setBaseAcceptOutTime(long p_BaseAcceptOutTime) {
		BaseAcceptOutTime = p_BaseAcceptOutTime;
	}

	//	工单处理时限
	public long getBaseDealOutTime() {
		return BaseDealOutTime;
	}

	public void setBaseDealOutTime(long p_BaseDealOutTime) {
		BaseDealOutTime = p_BaseDealOutTime;
	}

	//	工单描述
	public String getBaseDescrption() {
		return BaseDescrption;
	}

	public void setBaseDescrption(String p_BaseDescrption) {
		BaseDescrption = p_BaseDescrption;
	}
	*/
	//	工单处理记录
	public String getBaseResult() {
		return BaseResult;
	}

	public void setBaseResult(String p_BaseResult) {
		BaseResult = p_BaseResult;
	}

	//	工单关闭的满意度
	public String getBaseCloseSatisfy() {
		return BaseCloseSatisfy;
	}

	public void setBaseCloseSatisfy(String p_BaseCloseSatisfy) {
		BaseCloseSatisfy = p_BaseCloseSatisfy;
	}	
	//	属性设置区域--End--

	public String getBaseModifyUrl() {
		String strModifyUrl = Constants.REMEDY_QUERY_URL;
		strModifyUrl = Constants.REMEDY_QUERY_URL.replaceAll("<REMEDY_FROM>",this.getBaseSchema());
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.getBaseID());
		setBaseModifyUrl(strModifyUrl);
		return BaseModifyUrl;
	}

	public void setBaseModifyUrl(String baseModifyUrl) {
		BaseModifyUrl = baseModifyUrl;
	}
	
	/**
	 * 将条件以Like表达式的方式返回，以供按条件查询时使用(目前该方法还未实现，为查询作预留用)
	 * @param p_TblAliasName
	 * @return
	 */
	public String GetWhereSqlForLikeExpression(String p_TblAliasName)
	{
		StringBuffer sqlString = new StringBuffer();
		//String strTblPrefix=p_TblAliasName;
		return sqlString.toString();
	}
	/**
	 * 获取where语句
	 * @param TblName
	 * @return
	 */	
	public String GetWhereSql(String p_TblAliasName)
	{	

		StringBuffer sqlString = new StringBuffer();
		String strTblPrefix=p_TblAliasName;		
		if(!strTblPrefix.equals(""))
			strTblPrefix=strTblPrefix+".";
		
		try{
			if(!getBaseSchema().equals(""))
				//	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
				sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C1",getBaseID()));
			else
				//因为改为从UltraProcess:App_Base_Infor中查询，所以Baseid为700000000
				sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000000",getBaseID()));
			//	700000022	BaseTplID	用户选择的固定流程工单模板ID
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000022",getBaseTplID()));
	
			//	700000001	BaseSchema 指向主工单记录的工单类别的标示（新建模式下默认是工单的Form名）
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000001",getBaseSchema()));
			//	700000002	BaseName	工单名称	
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000002",getBaseName()));
			//	700000003	BaseSN	工单业务流水号，由（’ID’+’工单类别ID’+’时间’+’天的流水号'）组成（在Submit的Fiter中创建）
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000003",getBaseSN()));
			// 700000004	BaseCreatorFullName	建单人名（新建模式下打开工单时默认填写）
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000004",getBaseCreatorFullName()));
			// 700000005	BaseCreatorLoginName   建单人登陆名（新建模式下打开工单时默认填写）
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000005",getBaseCreatorLoginName()));
			
			//700000006	BaseCreateDate	建单时间（新建模式下打开工单时默认填写）
			sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700000006",getBaseCreateDate()));
			//建单时间(范围)开始日期
			if(getBaseCreateDateBegin()>0)
			{
				sqlString.append("  AND "+strTblPrefix+"C700000006>="+String.valueOf(getBaseCreateDateBegin()));
			}
			//建单时间(范围)结束日期
			if(getBaseCreateDateEnd()>0)
			{
				sqlString.append("  AND "+strTblPrefix+"C700000006<="+String.valueOf(getBaseCreateDateEnd()));
			}
			//700000008	BaseFinishDate		完成时间
			sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700000008",getBaseFinishDate()));
			//完成时间(范围)开始日期
			if(getBaseFinishDateBegin()>0)
			{
				sqlString.append("  AND "+strTblPrefix+"C700000008>="+String.valueOf(getBaseFinishDateBegin()));
			}
			//完成时间(范围)结束日期
			if(getBaseFinishDateEnd()>0)
			{
				sqlString.append("  AND "+strTblPrefix+"C700000008<="+String.valueOf(getBaseFinishDateEnd()));
			}	
			
			//700000009	BaseCloseDate		关闭时间
			sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700000009",getBaseCloseDate()));
			
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000009",getBaseCloseDateString()));
			
			//建单时间(范围)开始日期			if(getBaseCloseDateBegin()>0)
			{
				sqlString.append("  AND "+strTblPrefix+"C700000009>="+String.valueOf(getBaseCloseDateBegin()));
			}
			//建单时间(范围)结束日期
			if(getBaseCloseDateEnd()>0)
			{
				sqlString.append("  AND "+strTblPrefix+"C700000009<="+String.valueOf(getBaseCloseDateEnd()));
			}
			//700000010	BaseStatus		状态名（新建模式下打开工单时默认填写为“草稿”）
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000010",getBaseStatus()));
			//700000011	BaseSummary			主题
			//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000011",getBaseSummary()));
			//700000014	BaseItems			专业，类别
			//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000014",getBaseItems()));
			//700000015	BasePriority			紧急程度
			//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000015",getBasePriority()));
			//700000016	BaseIsAllowLogGroup	是否允许同组关单：、0否、1是，默认为0
			//sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700000016",getBaseIsAllowLogGroup()));

			//700000017	BaseAcceptOutTime		工单受理时限
			//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700000017",getBaseAcceptOutTime()));
			//700000018	BaseDealOutTime			工单处理时限			
			//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700000018",getBaseDealOutTime()));
			//700000019	BaseDescrption		工单描述
			//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000019",getBaseDescrption()));
			//700000020	BaseResult		工单处理记录
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000020",getBaseResult()));
			//700000021	BaseCloseSatisfy	工单关闭的满意度
			sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000021",getBaseCloseSatisfy()));
			//sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700000023",this.getIsArchive()));
			//查询是否历史的工单
			if(getIsArchive()==0)
			{
				sqlString.append(" and ("+strTblPrefix+"C700000023=0 or "+strTblPrefix+"C700000023 is null)");
			}
			else
				sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700000023",this.getIsArchive()));
			//BaseIsTrueClose	700000030	Interger	工单是否已经关闭或作废 0否　1是
			sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700000030",this.getBaseIsTrueClose()));
			String key;
			if(hsOwnFiled!=null)
			{
				for(Iterator it=hsOwnFiled.keySet().iterator();it.hasNext();)
				{
					key   =   (String)it.next();
					sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,key,GetOwnerFiledValue(key)));
					
				}
			}
			
			if(!this.getExtendSql().trim().equals(""))
				sqlString.append(getExtendSql());
				
		}catch(Exception ex)
		{
			System.err.println("ParBaseModel.getProcessSqlForWhere 方法："+ex.getMessage());
			ex.printStackTrace();			
		}
		//System.out.println("Where: "+sqlString.toString());
		return sqlString.toString();
	}

	public int getIsArchive() {
		return IsArchive;
	}

	public void setIsArchive(int isArchive) {
		IsArchive = isArchive;
	}

	public long getBaseCloseDateBegin() {
		return BaseCloseDateBegin;
	}

	public void setBaseCloseDateBegin(long baseCloseDateBegin) {
		BaseCloseDateBegin = baseCloseDateBegin;
	}

	public long getBaseCloseDateEnd() {
		return BaseCloseDateEnd;
	}

	public void setBaseCloseDateEnd(long baseCloseDateEnd) {
		BaseCloseDateEnd = baseCloseDateEnd;
	}
	
	
	/**
	 * 初始化工单特有字段
	 * @param p_BaseSchema
	 * @return
	 */
	private void setOwnerFiled(String p_BaseSchema)
	{
		hsOwnFiled=new Hashtable();
		if(p_BaseSchema.trim().equals(""))
		{
			//因为工单专业（BaseItems）、紧急程度（BasePriority）、工单主题(BaseSummary)、工单受理时限(BaseAcceptOutTime)
			//、工单处理时限(BaseDealOutTime)、是否允许同组关单(BaseIsAllowLogGroup)、工单描述(BaseDescrption)改成了工单特有字段
			//但这些字段在每类工单都有的，所以在查所有工单时要把这些字段加上。
			//注：在查询某类工单时这些字段是通过下一段程序会读取工单特有字段表循环取出,所以不用像本段程序一样写死.			
			hsOwnFiled.put("C700000014","");// C700000014 as BaseItems
			hsOwnFiled.put("C700000015","");// C700000015 as BasePriority
			hsOwnFiled.put("C700000011","");//C700000011 as BaseSummary
			hsOwnFiled.put("C700000017","");//C700000017 as BaseAcceptOutTime
			hsOwnFiled.put("C700000018","");//C700000018 as BaseDealOutTime
			hsOwnFiled.put("C700000016","");//C700000016 as BaseIsAllowLogGroup
			hsOwnFiled.put("C700000019","");//C700000019 as BaseDescrption		

		}
		else
		{
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
						hsOwnFiled.put("C"+m_BaseOwnFieldInfoModel.GetBase_field_ID(),"");
					}
				}
			}catch(Exception ex)
			{
				System.err.println("Base.GetOwnerFiledSqlString 方法："+ex.getMessage());
				ex.printStackTrace();	
			}
		}//if(p_BaseSchema.trim().equals(""))
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
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString,int p_intOrderByType) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=p_intOrderByType;
	}	
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=0;
	}
	public int getBaseIsTrueClose() {
		return BaseIsTrueClose;
	}
	public void setBaseIsTrueClose(int baseIsTrueClose) {
		BaseIsTrueClose = baseIsTrueClose;
	}
	public long getBaseFinishDateBegin() {
		return BaseFinishDateBegin;
	}
	public void setBaseFinishDateBegin(long baseFinishDateBegin) {
		BaseFinishDateBegin = baseFinishDateBegin;
	}
	public long getBaseFinishDateEnd() {
		return BaseFinishDateEnd;
	}
	public void setBaseFinishDateEnd(long baseFinishDateEnd) {
		BaseFinishDateEnd = baseFinishDateEnd;
	}
	/**
	 * 查询扩展得sql语句
	 * @return
	 */	
	public String getExtendSql() {
		if(ExtendSql==null)
			ExtendSql="";
		return ExtendSql;
	}
	public void setExtendSql(String extendSql) {
		ExtendSql = extendSql;
	}
		
	
	
	/**为变量绑定而加*/
	
	private List FiledInfoList;
	
	public List getFiledInfoList() {
		
		List FiledInfoList=setFiledList();
		return FiledInfoList;
	}
	public void setFiledInfoList(List filedInfoList) {
		FiledInfoList = filedInfoList;
	}		
	public List getContionFiledInfoList() {
		List reList=new ArrayList();
		int listCount=0;
		if(FiledInfoList!=null)
			listCount=FiledInfoList.size();
		for(int i=0;i<listCount;i++)
		{
			reList.add(FiledInfoList.get(i));
		}
		listCount=0;
		List m_FiledList=setFiledList();
		if(m_FiledList!=null)
			listCount=m_FiledList.size();
		for(int i=0;i<listCount;i++)
		{
			reList.add(m_FiledList.get(i));
		}
		return reList;
	}
	
	private List setFiledList()
	{
		List m_FiledList=new ArrayList();
		ParFiledInfo m_ParFiledInfo;
		String tblAlias="base";
		if(!tblAlias.equals(""))
			tblAlias+=".";
		PariseUntil m_PariseUntil=new PariseUntil();
		if(!getBaseSchema().equals(""))
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C1",getBaseID());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);
		}
		else
		{
			//因为改为从UltraProcess:App_Base_Infor中查询，所以Baseid为700000000
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000000",getBaseID());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);			
		}
		//	700000022	BaseTplID	用户选择的固定流程工单模板ID
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000022",getBaseTplID());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);	
		//	700000001	BaseSchema 指向主工单记录的工单类别的标示（新建模式下默认是工单的Form名）
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000001",getBaseSchema());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//	700000002	BaseName	工单名称	
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000002",getBaseName());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//	700000003	BaseSN	工单业务流水号，由（’ID’+’工单类别ID’+’时间’+’天的流水号'）组成（在Submit的Fiter中创建）
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000003",getBaseSN());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		// 700000004	BaseCreatorFullName	建单人名（新建模式下打开工单时默认填写）
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000004",getBaseCreatorFullName());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		// 700000005	BaseCreatorLoginName   建单人登陆名（新建模式下打开工单时默认填写）
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000005",getBaseCreatorLoginName());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//700000006	BaseCreateDate	建单时间（新建模式下打开工单时默认填写）
		if(getBaseCreateDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000006",String.valueOf(getBaseCreateDate()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);
		}
		//建单时间(范围)开始日期
		if(getBaseCreateDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000006",">=:"+getBaseCreateDateBegin());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);				
			//sqlString.append("  AND "+strTblPrefix+"C700000006>="+String.valueOf(getBaseCreateDateBegin()));
		}
		//建单时间(范围)结束日期
		if(getBaseCreateDateEnd()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000006","<=:"+getBaseCreateDateEnd());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);				
			//sqlString.append("  AND "+strTblPrefix+"C700000006<="+String.valueOf(getBaseCreateDateEnd()));
		}
		//700000008	BaseFinishDate		完成时间
		if(getBaseFinishDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000008",String.valueOf(getBaseFinishDate()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);
		}
		//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700000008",getBaseFinishDate()));
		//完成时间(范围)开始日期
		if(getBaseFinishDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000008",">=:"+getBaseFinishDateBegin());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);				
		}
		//完成时间(范围)结束日期
		if(getBaseFinishDateEnd()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000008","<=:"+getBaseFinishDateEnd());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);				
		}	
		//700000009	BaseCloseDate		关闭时间
		if(getBaseCloseDate()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000009",String.valueOf(getBaseCloseDate()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);
		} 
		//sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C700000009",getBaseCloseDate()));
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000009",getBaseCloseDateString());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000009",getBaseCloseDateString()));
		if(getBaseCloseDateBegin()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000009",">=:"+getBaseCloseDateBegin());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);				
			//sqlString.append("  AND "+strTblPrefix+"C700000009>="+String.valueOf(getBaseCloseDateBegin()));
		}
		//建单时间(范围)结束日期
		if(getBaseCloseDateEnd()>0)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000009","<=:"+getBaseCloseDateEnd());
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);				
			//sqlString.append("  AND "+strTblPrefix+"C700000009<="+String.valueOf(getBaseCloseDateEnd()));
		}
		//700000010	BaseStatus		状态名（新建模式下打开工单时默认填写为“草稿”）
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000010",getBaseStatus());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//700000020	BaseResult		工单处理记录
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000020",getBaseResult());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000020",getBaseResult()));
		//700000021	BaseCloseSatisfy	工单关闭的满意度
		m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000021",getBaseCloseSatisfy());
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000021",getBaseCloseSatisfy()));
		//sqlString.append(UnionConditionForSql.getIntFiedSql(p_TblAliasName,"C700000023",this.getIsArchive()));
		//查询是否历史的工单
		if(getIsArchive()==0||getIsArchive()==1)
		{
			//sqlString.append(" and ("+strTblPrefix+"C700000023=0 or "+strTblPrefix+"C700000023 is null)");
			//sqlString.append(" and ("+strTblPrefix+"C700000023=0"+") ");
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000023",String.valueOf(getIsArchive()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);				
		}
		//BaseIsTrueClose	700000030	Interger	工单是否已经关闭或作废 0否　1是
		if(getBaseIsTrueClose()!=999)
		{
			m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+"C700000030",String.valueOf(this.getBaseIsTrueClose()));
			if(m_ParFiledInfo!=null)
				m_FiledList.add(m_ParFiledInfo);
		}
		if(m_ParFiledInfo!=null)
			m_FiledList.add(m_ParFiledInfo);			
		//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C700000030",getBaseIsTrueCloseString()));
		String key;
		if(hsOwnFiled!=null)
		{
			for(Iterator it=hsOwnFiled.keySet().iterator();it.hasNext();)
			{
				key   =   (String)it.next();
				//System.out.println("key:"+key+":"+FormatString.CheckNullString(GetOwnerFiledValue(key)));
				m_ParFiledInfo=m_PariseUntil.setQueryFieldInfo(tblAlias+key,GetOwnerFiledValue(key));
				if(m_ParFiledInfo!=null)
					m_FiledList.add(m_ParFiledInfo);					
				//sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,key,GetOwnerFiledValue(key)));
			}
		}
		return m_FiledList;
	}	
	
}
