package cn.com.ultrapower.ultrawf.models.process;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParBaseAttachmentModel {
	
	private String RequestID="";
	private String BaseID="";
	private String  BaseSchema="";
	private String  UpLoadFileName="";
	private String  UpLoadTimeDate="";
	private String  UpLoadUser="";
	private String  UpLoadUserID="";
	private String  PhaseNo="";
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
	public String getPhaseNo() {
		return PhaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		PhaseNo = phaseNo;
	}
	public String getRequestID() {
		return RequestID;
	}
	public void setRequestID(String requestID) {
		RequestID = requestID;
	}
	public String getUpLoadFileName() {
		return UpLoadFileName;
	}
	public void setUpLoadFileName(String upLoadFileName) {
		UpLoadFileName = upLoadFileName;
	}
	public String getUpLoadTimeDate() {
		return UpLoadTimeDate;
	}
	public void setUpLoadTimeDate(String upLoadTimeDate) {
		UpLoadTimeDate = upLoadTimeDate;
	}
	public String getUpLoadUser() {
		return UpLoadUser;
	}
	public void setUpLoadUser(String upLoadUser) {
		UpLoadUser = upLoadUser;
	}
	public String getUpLoadUserID() {
		return UpLoadUserID;
	}
	public void setUpLoadUserID(String upLoadUserID) {
		UpLoadUserID = upLoadUserID;
	}

	public String getWhereSql()
	{
		StringBuffer stringBuffer=new StringBuffer();
		
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C1",this.getRequestID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C650000001",this.getBaseID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C650000002",this.getBaseSchema()));

		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C650000008",this.getUpLoadFileName()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C650000007",this.getUpLoadTimeDate()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C650000005",this.getUpLoadUser()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C650000006",this.getUpLoadUserID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C650000003",this.getPhaseNo()));
		
		return stringBuffer.toString();
		
	}
	
}
