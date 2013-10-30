package com.ultrapower.mobile.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "MOB_USER")
public class UserInfo implements java.io.Serializable {

	private String numId;
	private String itSysName;
	private String simId;
	private String userName;
	private String userFullName;
	private String password;

	public UserInfo() {
	}
	
	public UserInfo(String itSysName, String simId,
			String userName, String password) {
		this.itSysName = itSysName;
		this.simId = simId;
		this.userName = userName;
		this.password = password;
	}



	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getNumId() {
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getItSysName() {
		return itSysName;
	}

	public void setItSysName(String itSysName) {
		this.itSysName = itSysName;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
}