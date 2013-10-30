package daiwei.mobile.db;

public class LoginMess {
	private String id;
	private String type;
	private String version;
	private String updatetime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public LoginMess(String id, String type, String version, String updatetime) {
		super();
		this.id = id;
		this.type = type;
		this.version = version;
		this.updatetime = updatetime;
	}

	
	
	
}
