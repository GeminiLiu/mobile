package cn.com.ultrapower.ultrawf.control.configalarm.models;

public class ConfigAlarmModel {

	  private int cfgid;                  
	  private String cfgName;                
	  private String cfgStatus;             
	  private String cfgBaseItems;           
	  private String cfgLogicType;           
	  private String cfgResponsElevel;       
	  private String cfgCloseOpsatisfaction; 
	  private String cfgCloseDesc;
	  
	public String getCfgBaseItems() {
		return cfgBaseItems;
	}
	public void setCfgBaseItems(String cfgBaseItems) {
		this.cfgBaseItems = cfgBaseItems;
	}
	public String getCfgCloseDesc() {
		return cfgCloseDesc;
	}
	public void setCfgCloseDesc(String cfgCloseDesc) {
		this.cfgCloseDesc = cfgCloseDesc;
	}
	public String getCfgCloseOpsatisfaction() {
		return cfgCloseOpsatisfaction;
	}
	public void setCfgCloseOpsatisfaction(String cfgCloseOpsatisfaction) {
		this.cfgCloseOpsatisfaction = cfgCloseOpsatisfaction;
	}
	public int getCfgid() {
		return cfgid;
	}
	public void setCfgid(int cfgid) {
		this.cfgid = cfgid;
	}
	public String getCfgLogicType() {
		return cfgLogicType;
	}
	public void setCfgLogicType(String cfgLogicType) {
		this.cfgLogicType = cfgLogicType;
	}
	public String getCfgName() {
		return cfgName;
	}
	public void setCfgName(String cfgName) {
		this.cfgName = cfgName;
	}
	public String getCfgResponsElevel() {
		return cfgResponsElevel;
	}
	public void setCfgResponsElevel(String cfgResponsElevel) {
		this.cfgResponsElevel = cfgResponsElevel;
	}
	public String getCfgStatus() {
		return cfgStatus;
	}
	public void setCfgStatus(String cfgStatus) {
		this.cfgStatus = cfgStatus;
	}
	
}
