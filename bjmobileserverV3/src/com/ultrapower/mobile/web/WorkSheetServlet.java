package com.ultrapower.mobile.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.mobile.service.SheetQueryService;

public class WorkSheetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter("userName");
		String itSysCode = req.getParameter("itSysName");
		String simId = req.getParameter("simId");
		SheetQueryService sheetQueryService = (SheetQueryService) WebApplicationManager.getBean("sheetQueryService");
		String queryWaitDeal = sheetQueryService.queryWaitDeal(userName, itSysCode, 1, 50, "");
		resp.getWriter().write(queryWaitDeal);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
