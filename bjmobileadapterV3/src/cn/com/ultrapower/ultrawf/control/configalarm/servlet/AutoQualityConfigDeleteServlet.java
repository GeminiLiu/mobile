package cn.com.ultrapower.ultrawf.control.configalarm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.ultrawf.control.configalarm.config.AutoQualityConfigManager;

public class AutoQualityConfigDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids=request.getParameter("delids");
		if(ids!=null && !"".equals(ids)){
			AutoQualityConfigManager manager=new AutoQualityConfigManager();
			manager.deleteAutoQualityConfig(ids);
		}
		response.sendRedirect("QualityConfigList.jsp");
	}
	
}
