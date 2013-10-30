package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SoftWareFalg
{
	//专业：
	private boolean Config_Speciality 	= false;
	//网元：
	private boolean Config_NetElement 	= false;
	//厂家：
	private boolean Config_EquipCompany = false;
	//软件版本1：
	private boolean Config_SoftWare1 	= false;
	//软件版本2：
	private boolean Config_SoftWare2 	= false;
	
	public boolean isConfig_EquipCompany() {
		return Config_EquipCompany;
	}
	public void setConfig_EquipCompany(boolean config_EquipCompany) {
		Config_EquipCompany = config_EquipCompany;
	}
	public boolean isConfig_NetElement() {
		return Config_NetElement;
	}
	public void setConfig_NetElement(boolean config_NetElement) {
		Config_NetElement = config_NetElement;
	}
	public boolean isConfig_SoftWare1() {
		return Config_SoftWare1;
	}
	public void setConfig_SoftWare1(boolean config_SoftWare1) {
		Config_SoftWare1 = config_SoftWare1;
	}
	public boolean isConfig_SoftWare2() {
		return Config_SoftWare2;
	}
	public void setConfig_SoftWare2(boolean config_SoftWare2) {
		Config_SoftWare2 = config_SoftWare2;
	}
	public boolean isConfig_Speciality() {
		return Config_Speciality;
	}
	public void setConfig_Speciality(boolean config_Speciality) {
		Config_Speciality = config_Speciality;
	}
	
	
}