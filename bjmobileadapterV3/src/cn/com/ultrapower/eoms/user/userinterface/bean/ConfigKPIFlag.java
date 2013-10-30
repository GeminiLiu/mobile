package cn.com.ultrapower.eoms.user.userinterface.bean;

public class ConfigKPIFlag
{
	//KPI指标集:
	private boolean Config_KPICollection 	= false;
	//KPI指标:
	private boolean Config_KPISign 			= false;
	public boolean isConfig_KPICollection() {
		return Config_KPICollection;
	}
	public void setConfig_KPICollection(boolean config_KPICollection) {
		Config_KPICollection = config_KPICollection;
	}
	public boolean isConfig_KPISign() {
		return Config_KPISign;
	}
	public void setConfig_KPISign(boolean config_KPISign) {
		Config_KPISign = config_KPISign;
	}
	 

}