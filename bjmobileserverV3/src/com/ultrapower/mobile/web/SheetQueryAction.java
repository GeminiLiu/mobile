package com.ultrapower.mobile.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.mobile.common.constants.Constants;
import com.ultrapower.mobile.common.utils.XMLParamParser;
import com.ultrapower.mobile.model.WfType;
import com.ultrapower.mobile.model.WorkSheet;
import com.ultrapower.mobile.model.xml.XmlInfo;
import com.ultrapower.mobile.service.SheetQueryService;
import com.ultrapower.mobile.service.impl.BizFacadeImpl;

public class SheetQueryAction extends BaseAction {
	
	private SheetQueryService sheetQueryService;
	
	private List<WorkSheet> workSheets;
	
	private String userName;
	private String password;
	private String itSysName;
	private String simId;
	private String baseSchema = "";
	private List<WfType> wfTypes;
	
	public String waitingDealSheet() {
		UserSession userSession = getUserSession1();
		String loginName = userSession.getLoginName();
		String sesItSysName = userSession.getItSysName();
		wfTypes = userSession.getWfTypes();
		itSysName = sesItSysName;
		workSheets = sheetQueryService.queryWaitDeal(loginName, sesItSysName, baseSchema);
		return findForward("waitingList");
	}
	
	public String dealedSheet() {
		UserSession userSession = getUserSession1();
		String loginName = userSession.getLoginName();
		String sesItSysName = userSession.getItSysName();
		wfTypes = userSession.getWfTypes();
		itSysName = sesItSysName;
		workSheets = sheetQueryService.dealedSheet(loginName, sesItSysName, baseSchema);
		return findForward("dealedList");
	}
	
	public SheetQueryService getSheetQueryService() {
		return sheetQueryService;
	}

	private UserSession getUserSession1() {
		UserSession userSession = super.getUserSession();
		if (userSession == null) {
			if (this.login()) {
				userSession = new UserSession(); 
				userSession.setPwd(password);
				userSession.setLoginName(userName);
				userSession.setItSysName(itSysName);
				userSession.setWfTypes(sheetQueryService.getAllWfTypes(itSysName));
				this.getSession().setAttribute("userSession", userSession);
			} else {
				throw new RuntimeException("登陆失败！！");
			}
		} else {
			String pwd = userSession.getPwd();
			String loginName = userSession.getLoginName();
			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(loginName) && !loginName.equals(userName)) {
				if (this.login()) {
					userSession = new UserSession(); 
					userSession.setPwd(password);
					userSession.setLoginName(userName);
					userSession.setItSysName(itSysName);
					userSession.setWfTypes(sheetQueryService.getAllWfTypes(itSysName));
					this.getSession().setAttribute("userSession", userSession);
				} else {
					throw new RuntimeException("登陆失败！！");
				}
			}
		}
		return userSession;
	}
	
	private boolean login() {
		if (StringUtils.isBlank(userName) && StringUtils.isBlank(password)) {
			throw new RuntimeException("请指定用户名、密码！！");
		}
		BizFacadeImpl facade = (BizFacadeImpl) WebApplicationManager.getBean("bizFacade");
		String res = facade.login(userName, password, simId, itSysName, "", 0);
		try {
			XmlInfo convert = XMLParamParser.convert(res);
			String issucc = convert.getContent(Constants.ISSUCCESS);
			return Boolean.parseBoolean(issucc);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setSheetQueryService(SheetQueryService sheetQueryService) {
		this.sheetQueryService = sheetQueryService;
	}

	public List<WorkSheet> getWorkSheets() {
		return workSheets;
	}

	public void setWorkSheets(List<WorkSheet> workSheets) {
		this.workSheets = workSheets;
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

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getItSysName() {
		return itSysName;
	}

	public void setItSysName(String itSysName) {
		this.itSysName = itSysName;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public List<WfType> getWfTypes() {
		return wfTypes;
	}

	public void setWfTypes(List<WfType> wfTypes) {
		this.wfTypes = wfTypes;
	}

}

