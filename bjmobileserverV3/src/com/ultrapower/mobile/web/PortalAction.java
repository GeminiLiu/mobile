package com.ultrapower.mobile.web;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.mobile.service.SheetQueryService;
import com.ultrapower.mobile.service.UserManagerService;

public class PortalAction extends BaseAction {
	private String userName;
	private String password;
	private String itSysName;
	private UserManagerService userManagerService;
	private SheetQueryService sheetQueryService;
	
	/**
	 * 登陆
	 * @return
	 */
	public String login() {
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(itSysName)) {
			userName = userName.trim();
			password = password.trim();
			itSysName = itSysName.trim();
			String fullName = userManagerService.login(userName.trim(), password.trim(), itSysName.trim());
			if (StringUtils.isNotBlank(fullName)) {
				UserSession userSession = new UserSession(); 
				userSession.setPwd(password);
				userSession.setLoginName(userName);
				userSession.setItSysName(itSysName);
				userSession.setFullName(fullName);
				userSession.setWfTypes(sheetQueryService.getAllWfTypes(itSysName));
				this.getSession().setAttribute("userSession", userSession);
				return "main";
			}
		}
		getRequest().setAttribute("msg", "登陆失败！");
		//return "index";
		return findRedirect("/mobile.callService.jsp");
	}
	
	/**
	 * 退出
	 * @return
	 */
	public String logout() {
		getSession().invalidate();
		return "index";
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public String getItSysName() {
		return itSysName;
	}

	public void setItSysName(String itSysName) {
		this.itSysName = itSysName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public SheetQueryService getSheetQueryService() {
		return sheetQueryService;
	}

	public void setSheetQueryService(SheetQueryService sheetQueryService) {
		this.sheetQueryService = sheetQueryService;
	}
}
