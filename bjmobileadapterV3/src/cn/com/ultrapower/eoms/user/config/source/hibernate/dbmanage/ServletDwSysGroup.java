package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata.DwSysGroup;

public class ServletDwSysGroup extends HttpServlet {
	
	public ServletDwSysGroup() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean flag 						= false;
		response.setContentType("text/html");
		
		request.setCharacterEncoding("gb2312");
		String division = request.getParameter("division");
		String dept = request.getParameter("dept_id");
		DwSysGroup dwSysGroup = new DwSysGroup();
		if(dwSysGroup.cops(division, dept)){
			if(dwSysGroup.insert(division, dept)){
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath+"/roles/append.jsp?targetflag=yes");
			}
		}else{
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath+"/roles/append.jsp?targetflag=no");
		}
	}
	
	public void init() throws ServletException {
	}

}
