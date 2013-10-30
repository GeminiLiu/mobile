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
@Table(name = "MOB_USERONLINE")
public class UserOnline implements java.io.Serializable {

	private String numId;
	private String itSysName;
	private String simId;
	private String userName;
	private String password;
	private String onlineIpAddress;
	private int port;
	private long loginTime; 
	private long heartBeatTime;
	private int isOnline;//0：表示不在线  1：表示在线


	public UserOnline() {
	}
	
	public UserOnline(String itSysName, String simId,
			String userName, String password, int port) {
		this.itSysName = itSysName;
		this.simId = simId;
		this.userName = userName;
		this.password = password;
		this.port = port;
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

	public String getOnlineIpAddress() {
		return onlineIpAddress;
	}

	public void setOnlineIpAddress(String onlineIpAddress) {
		this.onlineIpAddress = onlineIpAddress;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public long getHeartBeatTime() {
		return heartBeatTime;
	}

	public void setHeartBeatTime(long heartBeatTime) {
		this.heartBeatTime = heartBeatTime;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}