package cn.com.ultrapower.ultrawf.models.process;

public class BaseConnectionModel
{
	private String 	BaseID;
	private String 	BaseName;
	private String 	BaseSchema;
	private String 	BaseProcessID;
	private String 	BaseProcessType;
	private String 	BaseProcessPhaseNo;
	private String 	BaseProcessLogID;
	private long 	CreateBaseAftermathType;
		
	private String 	ChildBaseID;
	private String 	ChildBaseName;
	private String 	ChildBaseSchema;
	private String 	ChildBaseSN;
	private String 	ChildBaseState;
	private long 	ChildBaseCreateDate;
	private String 	ChildBaseSummary;
	
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
	public String getBaseProcessID() {
		return BaseProcessID;
	}
	public void setBaseProcessID(String baseProcessID) {
		BaseProcessID = baseProcessID;
	}
	public String getBaseProcessType() {
		return BaseProcessType;
	}
	public void setBaseProcessType(String baseProcessType) {
		BaseProcessType = baseProcessType;
	}
	public String getBaseProcessPhaseNo() {
		return BaseProcessPhaseNo;
	}
	public void setBaseProcessPhaseNo(String baseProcessPhaseNo) {
		BaseProcessPhaseNo = baseProcessPhaseNo;
	}
	public String getBaseProcessLogID() {
		return BaseProcessLogID;
	}
	public void setBaseProcessLogID(String baseProcessLogID) {
		BaseProcessLogID = baseProcessLogID;
	}
	public long getCreateBaseAftermathType() {
		return CreateBaseAftermathType;
	}
	public void setCreateBaseAftermathType(long createBaseAftermathType) {
		CreateBaseAftermathType = createBaseAftermathType;
	}
	public String getChildBaseID() {
		return ChildBaseID;
	}
	public void setChildBaseID(String childBaseID) {
		ChildBaseID = childBaseID;
	}
	public String getChildBaseName() {
		return ChildBaseName;
	}
	public void setChildBaseName(String childBaseName) {
		ChildBaseName = childBaseName;
	}
	public String getChildBaseSchema() {
		return ChildBaseSchema;
	}
	public void setChildBaseSchema(String childBaseSchema) {
		ChildBaseSchema = childBaseSchema;
	}
	public String getChildBaseSN() {
		return ChildBaseSN;
	}
	public void setChildBaseSN(String childBaseSN) {
		ChildBaseSN = childBaseSN;
	}
	public String getChildBaseState() {
		return ChildBaseState;
	}
	public void setChildBaseState(String childBaseState) {
		ChildBaseState = childBaseState;
	}
	public long getChildBaseCreateDate() {
		return ChildBaseCreateDate;
	}
	public void setChildBaseCreateDate(long childBaseCreateDate) {
		ChildBaseCreateDate = childBaseCreateDate;
	}
	public String getChildBaseSummary() {
		return ChildBaseSummary;
	}
	public void setChildBaseSummary(String childBaseSummary) {
		ChildBaseSummary = childBaseSummary;
	}


	
}
