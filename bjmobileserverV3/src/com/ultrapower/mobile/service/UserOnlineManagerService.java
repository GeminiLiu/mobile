package com.ultrapower.mobile.service;

import java.util.List;

import com.ultrapower.mobile.model.UserOnline;


public interface UserOnlineManagerService {
	public boolean saveOrUpdate(UserOnline userOnline);
	
	/**
	 * 获取当前所有在线用户列表
	 * @return 在线用户列表
	 */
	public List<UserOnline> getOnlineUsers();
	
	/**
	 * 获取指定业务系统的在线用户列表
	 * @param itSysName 业务系统标识
	 * @return 指定业务系统的在线用户列表
	 */
	public List<UserOnline> getOnlineUsers(String itSysName);
	
	/**
	 * 获取在线用户信息
	 * @param userName 登录名
	 * @param password 密码
	 * @param itSysName 业务系统标识
	 * @return 在线用户对象
	 */
	public UserOnline getUserOnline(String userName, String password, String itSysName);
	
	/**
	 * 获取在线用户信息
	 * @param userName 登录名
	 * @param itSysName 业务系统标识
	 * @return 在线用户对象
	 */
	public UserOnline getUserOnline(String userName, String itSysName);
}
