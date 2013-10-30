package cn.com.ultrapower.eoms.user.sla.hibernate.po;

import java.io.Serializable;

public class UserSlaConfigPo implements Serializable {

	private java.lang.String id;
	private java.lang.String Userid;
	private java.lang.String Slaid;
	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}	

	public java.lang.String getSlaid() {
		return Slaid;
	}

	public void setSlaid(java.lang.String slaid) {
		Slaid = slaid;
	}

	public java.lang.String getUserid() {
		return Userid;
	}

	public void setUserid(java.lang.String userid) {
		Userid = userid;
	}

	public UserSlaConfigPo(){
		
	}
}
