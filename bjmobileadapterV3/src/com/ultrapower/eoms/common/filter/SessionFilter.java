package com.ultrapower.eoms.common.filter;

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

/**
 * 用于验证用户session是否超时的过滤器
 * @author 123
 *
 */
public class SessionFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		HttpSession session = request.getSession();
		
		String requestPath = request.getRequestURL().toString();
		String root = request.getContextPath();
		
		if(requestPath.indexOf("login.jsp") == -1 && requestPath.indexOf("loginverify.jsp") == -1 && requestPath.indexOf("loginVerify.jsp") == -1 && requestPath.indexOf("postcontrol.jsp") == -1 && requestPath.indexOf("logout.jsp") == -1) {
			if(session == null || session.getAttribute("userInfo") == null) {
				response.sendRedirect(root + "/common/jsp/index.jsp");	
				return;
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
