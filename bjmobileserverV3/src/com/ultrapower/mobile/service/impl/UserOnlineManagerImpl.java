package com.ultrapower.mobile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.mobile.common.enums.UserOnlineEnum;
import com.ultrapower.mobile.model.UserOnline;
import com.ultrapower.mobile.service.UserOnlineManagerService;

@Transactional
public class UserOnlineManagerImpl implements UserOnlineManagerService {
	private IDao<UserOnline> userOnlineManagerDao;
	
	public IDao<UserOnline> getUserOnlineManagerDao() {
		return userOnlineManagerDao;
	}

	public void setUserOnlineManagerDao(IDao<UserOnline> userOnlineManagerDao) {
		this.userOnlineManagerDao = userOnlineManagerDao;
	}

	public boolean saveOrUpdate(UserOnline userOnline) {
		userOnlineManagerDao.saveOrUpdate(userOnline);
		return true;
	}

	public List<UserOnline> getOnlineUsers() {
		String hql = "from UserOnline u where u.isOnline = " + UserOnlineEnum.ONLINE.getVal();
		return userOnlineManagerDao.find(hql, null);
	}

	public UserOnline getUserOnline(String userName, String password, String itSysName) {
		String hql = "from UserOnline u where u.userName=? and password=? and itSysName=? and isOnline=?";
		return userOnlineManagerDao.findUnique(hql, userName, password, itSysName, UserOnlineEnum.ONLINE.getVal());
	}

	public UserOnline getUserOnline(String userName, String itSysName) {
		String hql = "from UserOnline u where u.userName=?  and itSysName=? and isOnline=?";
		return userOnlineManagerDao.findUnique(hql, userName, itSysName, UserOnlineEnum.ONLINE.getVal());
	}
	
	public List<UserOnline> getOnlineUsers(String itSysName) {
		String hql = "from UserOnline u where u.itSysName='" + itSysName + "' and u.isOnline = " + UserOnlineEnum.ONLINE.getVal();
		return userOnlineManagerDao.find(hql, null);
	}
}
