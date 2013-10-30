package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SysBTSInfo
{
	//基站代号：
	private String BTSCode 					= "";
	//基站名称：
	private String BTSName 					= "";
	//基站等级：
	private String BTSLevel 				= "";
	//小区数：
	private String BTSAreaNO 				= "";
	//电源监控
	private String BTSIsELectricControl 	= "";
	//状态：
	private String BTSState 				= "";
	//所属县区：
	private String BTSTown 					= "";
	//网元：
	private String BTSNetWorkElement 		= "";
	//配置：
	private String BTSConfig 				= "";
	//描述：
	private String BTSDescription 			= "";
	public String getBTSAreaNO() {
		return BTSAreaNO;
	}
	public void setBTSAreaNO(String areaNO) {
		BTSAreaNO = areaNO;
	}
	public String getBTSCode() {
		return BTSCode;
	}
	public void setBTSCode(String code) {
		BTSCode = code;
	}
	public String getBTSConfig() {
		return BTSConfig;
	}
	public void setBTSConfig(String config) {
		BTSConfig = config;
	}
	public String getBTSDescription() {
		return BTSDescription;
	}
	public void setBTSDescription(String description) {
		BTSDescription = description;
	}
	public String getBTSIsELectricControl() {
		return BTSIsELectricControl;
	}
	public void setBTSIsELectricControl(String isELectricControl) {
		BTSIsELectricControl = isELectricControl;
	}
	public String getBTSLevel() {
		return BTSLevel;
	}
	public void setBTSLevel(String level) {
		BTSLevel = level;
	}
	public String getBTSName() {
		return BTSName;
	}
	public void setBTSName(String name) {
		BTSName = name;
	}
	public String getBTSNetWorkElement() {
		return BTSNetWorkElement;
	}
	public void setBTSNetWorkElement(String netWorkElement) {
		BTSNetWorkElement = netWorkElement;
	}
	public String getBTSState() {
		return BTSState;
	}
	public void setBTSState(String state) {
		BTSState = state;
	}
	public String getBTSTown() {
		return BTSTown;
	}
	public void setBTSTown(String town) {
		BTSTown = town;
	}
}