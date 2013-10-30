package cn.com.ultrapower.eoms.user.inputdata;

public class ValueBean
{
	private String parentid 	= "0";
	private String dnid			= "0";
	private String dnname		= "";
	public String getDnid() {
		return dnid;
	}
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getDnname() {
		return dnname;
	}
	public void setDnname(String dnname) {
		this.dnname = dnname;
	}
}