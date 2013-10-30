/**
 * Copyright (c) 2007 神州泰岳服务管理事业部应用组
 * All rights reserved.
 *
 * 文件名称: BaseAction.java
 * 文件标示: BaseAction.java
 * 摘   要:
 * 
 * 当前版本：
 * 作   者:Administrator
 * 完成日期:
 */
package com.ultrapower.eoms.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.com.ultrapower.eoms.util.user.UserInformation;

import com.opensymphony.xwork2.ActionSupport;
import com.ultrapower.eoms.common.support.PageBean;

public abstract class BaseAction extends ActionSupport {
	protected PageBean page = new PageBean();

	public PageBean getPage() {
		return page;
	}
	
	public void setPage(PageBean page) {
		this.page = page;
	}
	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {

		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	public UserInformation getUserInfo() {
		return (UserInformation) getSession().getAttribute("userInfo");
	}
}
