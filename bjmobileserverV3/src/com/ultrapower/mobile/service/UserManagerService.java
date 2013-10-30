package com.ultrapower.mobile.service;

import com.ultrapower.mobile.model.UserInfo;


public interface UserManagerService {
	public boolean saveOrUpdate(UserInfo user);
	
	/**
	 * 获取用户对象
	 * @param loginName 登录名
	 * @param itSysName 业务系统标识
	 * @return 用户对象
	 */
	public UserInfo getUser(String loginName, String itSysName);
	
	/**
	 * 登陆
	 * @param userName 登录名
	 * @param password 密码
	 * @param itSysName 业务系统标识
	 * @return 成功 或 失败
	 */
	public String login(String userName, String password, String itSysName);

}
