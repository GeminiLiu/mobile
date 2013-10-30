package daiwei.mobile.animal;

public class User {	
	public User() {
		super();
	}
	public User(String username, String passwordEncode) {
		super();
		this.username = username;
		this.passwordEncode = passwordEncode;
	}
	private String username;
	private String passwordEncode;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordEncode() {
		return passwordEncode;
	}
	public void setPasswordEncode(String passwordEncode) {
		this.passwordEncode = passwordEncode;
	}
}
