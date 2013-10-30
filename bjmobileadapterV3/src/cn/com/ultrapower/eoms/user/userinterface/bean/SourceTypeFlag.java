package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SourceTypeFlag
{
	//资源类别：
	private boolean Config_SourceType 	= false;
	//资源名称：
	private boolean Config_SourceName 	= false;
	public boolean isConfig_SourceName() {
		return Config_SourceName;
	}
	public void setConfig_SourceName(boolean config_SourceName) {
		Config_SourceName = config_SourceName;
	}
	public boolean isConfig_SourceType() {
		return Config_SourceType;
	}
	public void setConfig_SourceType(boolean config_SourceType) {
		Config_SourceType = config_SourceType;
	}
	 
	
}