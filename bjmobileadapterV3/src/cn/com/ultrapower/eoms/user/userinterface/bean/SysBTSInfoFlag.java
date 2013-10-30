package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SysBTSInfoFlag
{
	//基站代号：
	private boolean BTSCode 				= false;
	//基站名称：
	private boolean BTSName 				= false;
	//基站等级：
	private boolean BTSLevel 				= false;
	//小区数：
	private boolean BTSAreaNO 				= false;
	//电源监控
	private boolean BTSIsELectricControl 	= false;
	//状态：
	private boolean BTSState 				= false;
	//所属县区：
	private boolean BTSTown 				= false;
	//网元：
	private boolean BTSNetWorkElement 		= false;
	//配置：
	private boolean BTSConfig 				= false;
	//描述：
	private boolean BTSDescription 			= false;
	
	public boolean isBTSAreaNO() {
		return BTSAreaNO;
	}
	public void setBTSAreaNO(boolean areaNO) {
		BTSAreaNO = areaNO;
	}
	public boolean isBTSCode() {
		return BTSCode;
	}
	public void setBTSCode(boolean code) {
		BTSCode = code;
	}
	public boolean isBTSConfig() {
		return BTSConfig;
	}
	public void setBTSConfig(boolean config) {
		BTSConfig = config;
	}
	public boolean isBTSDescription() {
		return BTSDescription;
	}
	public void setBTSDescription(boolean description) {
		BTSDescription = description;
	}
	public boolean isBTSIsELectricControl() {
		return BTSIsELectricControl;
	}
	public void setBTSIsELectricControl(boolean isELectricControl) {
		BTSIsELectricControl = isELectricControl;
	}
	public boolean isBTSLevel() {
		return BTSLevel;
	}
	public void setBTSLevel(boolean level) {
		BTSLevel = level;
	}
	public boolean isBTSName() {
		return BTSName;
	}
	public void setBTSName(boolean name) {
		BTSName = name;
	}
	public boolean isBTSNetWorkElement() {
		return BTSNetWorkElement;
	}
	public void setBTSNetWorkElement(boolean netWorkElement) {
		BTSNetWorkElement = netWorkElement;
	}
	public boolean isBTSState() {
		return BTSState;
	}
	public void setBTSState(boolean state) {
		BTSState = state;
	}
	public boolean isBTSTown() {
		return BTSTown;
	}
	public void setBTSTown(boolean town) {
		BTSTown = town;
	}
 
}