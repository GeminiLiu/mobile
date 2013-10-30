package cn.com.ultrapower.ultrawf.control.configalarm.models;

public class AutoQualityConfigModel {

	private long    cfgId;                  
	private String cfgName;                
	private String cfgStatus;             
	private String cfgBaseSchema;           
	private String cfgBaseName;           
	private String cfgComplainType;       
	private String cfgBasePriority; 
	private String cfgDealOutTime;
	private String cfgUserLoginName;
	private String cfgDesc;
	  
	public long getCfgId() {
		return cfgId;
	}
	public void setCfgId(long cfgId) {
		this.cfgId = cfgId;
	}
	public String getCfgName() {
		return cfgName;
	}
	public void setCfgName(String cfgName) {
		this.cfgName = cfgName;
	}
	public String getCfgStatus() {
		return cfgStatus;
	}
	public void setCfgStatus(String cfgStatus) {
		this.cfgStatus = cfgStatus;
	}
	public String getCfgBaseSchema() {
		return cfgBaseSchema;
	}
	public void setCfgBaseSchema(String cfgBaseSchema) {
		this.cfgBaseSchema = cfgBaseSchema;
	}
	public String getCfgBaseName() {
		return cfgBaseName;
	}
	public void setCfgBaseName(String cfgBaseName) {
		this.cfgBaseName = cfgBaseName;
	}
	public String getCfgComplainType() {
		return cfgComplainType;
	}
	public void setCfgComplainType(String cfgComplainType) {
		this.cfgComplainType = cfgComplainType;
	}
	public String getCfgBasePriority() {
		return cfgBasePriority;
	}
	public void setCfgBasePriority(String cfgBasePriority) {
		this.cfgBasePriority = cfgBasePriority;
	}
	public String getCfgDealOutTime() {
		return cfgDealOutTime;
	}
	public void setCfgDealOutTime(String cfgDealOutTime) {
		this.cfgDealOutTime = cfgDealOutTime;
	}
	public String getCfgDesc() {
		return cfgDesc;
	}
	public void setCfgDesc(String cfgDesc) {
		this.cfgDesc = cfgDesc;
	}
	public String getCfgUserLoginName() {
		return cfgUserLoginName;
	}
	public void setCfgUserLoginName(String cfgUserLoginName) {
		this.cfgUserLoginName = cfgUserLoginName;
	}
}
