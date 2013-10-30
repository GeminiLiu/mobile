package cn.com.ultrapower.eoms.user.comm.bean;

/**
 * @author wangwenzhuo
 * @CreatTime 2006-10-16
 * @将Arfield封装在配置信息

 */
public class ConfigBean {
	
	private String databasetype		= "";
	private String databasedriver	= "";
	private String databaseurl		= "";
	private String databaseuser		= "";
	private String databasepwd		= "";
	private String remedyuser		= "";
	private String remedypwd		= "";
	private String remedyserver		= "";
	private String remedyport		= "";
	
	public String getdatabasetype() {
		return databasetype;
	}

	public void setdatabasetype(String databasetype) {
		this.databasetype = databasetype;
	}

	public String getdatabasedriver() {
		return databasedriver;
	}

	public void setdatabasedriver(String databasedriver) {
		this.databasedriver = databasedriver;
	}

	public String getdatabaseurl() {
		return databaseurl;
	}

	public void setdatabaseurl(String databaseurl) {
		this.databaseurl = databaseurl;
	}

	public String getdatabaseuser() {
		return databaseuser;
	}

	public void setdatabaseuser(String databaseuser) {
		this.databaseuser = databaseuser;
	}
	
	public String getdatabasepwd() {
		return databaseurl;
	}

	public void setdatabasepwd(String databasepwd) {
		this.databasepwd = databasepwd;
	}
	
	public String getremedyuser() {
		return remedyuser;
	}

	public void setremedyuser(String remedyuser) {
		this.remedyuser = remedyuser;
	}
	
	public String getremedypwd() {
		return remedypwd;
	}

	public void setremedypwd(String remedypwd) {
		this.remedypwd = remedypwd;
	}
	
	public String getremedyserver() {
		return remedyserver;
	}

	public void setremedyserver(String remedyserver) {
		this.remedyserver = remedyserver;
	}
	
	public String getremedyport() {
		return remedyport;
	}

	public void setremedyport(String remedyport) {
		this.remedyport = remedyport;
	}
}
