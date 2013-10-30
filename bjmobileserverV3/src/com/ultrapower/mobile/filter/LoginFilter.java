package com.ultrapower.mobile.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;


public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;
		
		String requestURI = req.getRequestURI();
		String itSysName = req.getParameter("itSysName");
		String simId = req.getParameter("simId");
		HttpSession session = req.getSession();
		Object sess = session.getAttribute("userSession");
//		/mobileServer/portal/logout.action
//		/mobileServer/sheet/waitingDealSheet.action
//		/mobileServer/sheet/dealedSheet.action
		boolean isValidate = false;
		if (sess == null) {
			if (StringUtils.isNotBlank(itSysName) && StringUtils.isNotBlank(simId)) {
				//安卓客户端登陆
				isValidate = true;
			} else if (requestURI.indexOf("/ws/") > 0 || requestURI.indexOf("login.action") > 0 || requestURI.indexOf("logout.action") > 0 || requestURI.indexOf("login.jsp") > 0) {
				isValidate = true;
			}
		} else {
			isValidate = true;
		}
		isValidate = true;
		if (isValidate) {
			arg2.doFilter(arg0, arg1);
		} else {
			res.sendRedirect(req.getContextPath() +"/login.jsp");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
