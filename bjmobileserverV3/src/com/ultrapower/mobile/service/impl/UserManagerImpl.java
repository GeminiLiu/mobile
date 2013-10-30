package com.ultrapower.mobile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.biz.ItSysWsFacade;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.mobile.common.constants.Constants;
import com.ultrapower.mobile.common.utils.WSUtil;
import com.ultrapower.mobile.common.utils.XMLParamParser;
import com.ultrapower.mobile.model.UserInfo;
import com.ultrapower.mobile.model.xml.XmlInfo;
import com.ultrapower.mobile.service.UserManagerService;

@Transactional
public class UserManagerImpl implements UserManagerService {
	private IDao<UserInfo> userManagerDao;

	public IDao<UserInfo> getUserManagerDao() {
		return userManagerDao;
	}

	public void setUserManagerDao(IDao<UserInfo> userManagerDao) {
		this.userManagerDao = userManagerDao;
	}

	public boolean saveOrUpdate(UserInfo user) {
		userManagerDao.save(user);
		return true;
	}

	public UserInfo getUser(String loginName, String itSysName) {
		String hql = " from UserInfo where itSysName = ? and userName = ?";
		return userManagerDao.findUnique(hql, itSysName, loginName);
	}

	public String login(String userName, String password, String itSysName) {
		
		XMLParamParser wsParams = XMLParamParser.createInstance();
		wsParams.addAttr(Constants.INF_TYPE, "" ,Constants.INF_TYPE_WS_USERAUTHENTICATION);
		wsParams.addAttr(Constants.USERNAME, "" ,userName);
		wsParams.addAttr(Constants.PASSWORD, "" ,password);
		
		/*
		ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
		String wsReslut;
		try {
			wsReslut = itSysWsFacade.invoke(wsParams.getXmlContent());
			XmlInfo xml = XMLParamParser.convert(wsReslut);
			String issucc = xml.getContent(Constants.ISSUCCESS);
			String userFullName = xml.getContent(Constants.USERFULLNAME);
			if (Boolean.parseBoolean(issucc)) {
				return userFullName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		return userName;
		//return null;
	}
}
