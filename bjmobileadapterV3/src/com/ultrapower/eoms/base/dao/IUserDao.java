package com.ultrapower.eoms.base.dao;


import com.ultrapower.eoms.base.model.User;

public interface IUserDao {

	/**
	 * 根据用户登录名称查询用户信息
	 * 
	 * @param username
	 *            用户ID
	 * @return 用户信息对象
	 */
	public User getUser(String username);

}
