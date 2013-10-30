package cn.com.ultrapower.ultrawf.models.process;

public class BaseAttachmentModel
{
	private String 	attachmentID	;
	private String 	BaseID	;
	private String 	BaseSchema	;
	private String 	PhaseNo	;
	private long 	FlagActive	;
	private String 	upLoadUser	;
	private String 	upLoadUserID	;
	private long 	upLoadTimeDate	;
	private String 	upLoadFileName	;
	private String 	upLoadFileDesc	;
	private String 	ProcessID	;
	private String 	ProcessType	;
	private String 	ProcessLogID	;
	private String	ViewAttAchID;
	public String getAttachmentID() {
		return attachmentID;
	}
	public void setAttachmentID(String attachmentID) {
		this.attachmentID = attachmentID;
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
	public String getPhaseNo() {
		return PhaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		PhaseNo = phaseNo;
	}
	public long getFlagActive() {
		return FlagActive;
	}
	public void setFlagActive(long flagActive) {
		FlagActive = flagActive;
	}
	public String getUpLoadUser() {
		return upLoadUser;
	}
	public void setUpLoadUser(String upLoadUser) {
		this.upLoadUser = upLoadUser;
	}
	public String getUpLoadUserID() {
		return upLoadUserID;
	}
	public void setUpLoadUserID(String upLoadUserID) {
		this.upLoadUserID = upLoadUserID;
	}
	public long getUpLoadTimeDate() {
		return upLoadTimeDate;
	}
	public void setUpLoadTimeDate(long upLoadTimeDate) {
		this.upLoadTimeDate = upLoadTimeDate;
	}
	public String getUpLoadFileName() {
		return upLoadFileName;
	}
	public void setUpLoadFileName(String upLoadFileName) {
		this.upLoadFileName = upLoadFileName;
	}
	public String getUpLoadFileDesc() {
		return upLoadFileDesc;
	}
	public void setUpLoadFileDesc(String upLoadFileDesc) {
		this.upLoadFileDesc = upLoadFileDesc;
	}
	public String getProcessID() {
		return ProcessID;
	}
	public void setProcessID(String processID) {
		ProcessID = processID;
	}
	public String getProcessType() {
		return ProcessType;
	}
	public void setProcessType(String processType) {
		ProcessType = processType;
	}
	public String getProcessLogID() {
		return ProcessLogID;
	}
	public void setProcessLogID(String processLogID) {
		ProcessLogID = processLogID;
	}
	public void setViewAttAchID(String viewAttAchID) {
		ViewAttAchID = viewAttAchID;
	}
	public String getViewAttAchID() {
		return ViewAttAchID;
	}
	public String getRequestID()
	{
		return attachmentID;
	}
	public void setRequestID(String requestID)
	{
		attachmentID = requestID;
	}
	
}
