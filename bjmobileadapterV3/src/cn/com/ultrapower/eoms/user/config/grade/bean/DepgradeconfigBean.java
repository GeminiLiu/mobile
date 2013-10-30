package cn.com.ultrapower.eoms.user.config.grade.bean;

public class DepgradeconfigBean {
	long depgradeconfig_id;
	String depgradeconfig_dep;
	double depgradeconfig_gradvalue;
	long depgradeconfig_affectbusiness;
	
	public long getDepgradeconfig_affectbusiness() {
		return depgradeconfig_affectbusiness;
	}
	public void setDepgradeconfig_affectbusiness(long depgradeconfig_affectbusiness) {
		this.depgradeconfig_affectbusiness = depgradeconfig_affectbusiness;
	}
	public String getDepgradeconfig_dep() {
		return depgradeconfig_dep;
	}
	public void setDepgradeconfig_dep(String depgradeconfig_dep) {
		this.depgradeconfig_dep = depgradeconfig_dep;
	}
	public double getDepgradeconfig_gradvalue() {
		return depgradeconfig_gradvalue;
	}
	public void setDepgradeconfig_gradvalue(long depgradeconfig_gradvalue) {
		this.depgradeconfig_gradvalue = depgradeconfig_gradvalue;
	}
	public long getDepgradeconfig_id() {
		return depgradeconfig_id;
	}
	public void setDepgradeconfig_id(long depgradeconfig_id) {
		this.depgradeconfig_id = depgradeconfig_id;
	}
	
}
