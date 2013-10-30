package cn.com.ultrapower.ultrawf.models.process;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParBaseInforModel {

	private String BaseID="";
	private String BaseSchema="";
	private String BaseSN="";
	private String BaseName="";
	private String BaseCreatorFullName="";
	private String BaseCreatorLoginName="";	
	private String BaseStatus="";
	private String BaseCreateDate;
	private String BaseFinishDate;
	private String BaseCloseDate;
	private String RequestID;
	public String getRequestID() {
		return RequestID;
	}
	public void setRequestID(String requestID) {
		RequestID = requestID;
	}
	public String getBaseCloseDate() {
		return BaseCloseDate;
	}
	public void setBaseCloseDate(String baseCloseDate) {
		BaseCloseDate = baseCloseDate;
	}
	public String getBaseCreateDate() {
		return BaseCreateDate;
	}
	public void setBaseCreateDate(String baseCreateDate) {
		BaseCreateDate = baseCreateDate;
	}
	public String getBaseCreatorFullName() {
		return BaseCreatorFullName;
	}
	public void setBaseCreatorFullName(String baseCreatorFullName) {
		BaseCreatorFullName = baseCreatorFullName;
	}
	public String getBaseCreatorLoginName() {
		return BaseCreatorLoginName;
	}
	public void setBaseCreatorLoginName(String baseCreatorLoginName) {
		BaseCreatorLoginName = baseCreatorLoginName;
	}
	public String getBaseFinishDate() {
		return BaseFinishDate;
	}
	public void setBaseFinishDate(String baseFinishDate) {
		BaseFinishDate = baseFinishDate;
	}
	public String getBaseID() {
		return BaseID;
	}
	public void setBaseID(String baseID) {
		BaseID = baseID;
	}
	public String getBaseName() {
		return BaseName;
	}
	public void setBaseName(String baseName) {
		BaseName = baseName;
	}
	public String getBaseSchema() {
		return BaseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		BaseSchema = baseSchema;
	}
	public String getBaseSN() {
		return BaseSN;
	}
	public void setBaseSN(String baseSN) {
		BaseSN = baseSN;
	}
	public String getBaseStatus() {
		return BaseStatus;
	}
	public void setBaseStatus(String baseStatus) {
		BaseStatus = baseStatus;
	}
	
	
	public String getWhereSql()
	{
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C1",this.getRequestID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000000",this.getBaseID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000001",this.getBaseSchema()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000003",this.getBaseSN()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000002",this.getBaseName()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000004",this.getBaseCreatorFullName()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000005",this.getBaseCreatorLoginName()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000010",this.getBaseStatus()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000006",getBaseCreateDate()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000008",getBaseFinishDate()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700000009",getBaseCloseDate()));
		
		return stringBuffer.toString();
	}
	
	
	
}
