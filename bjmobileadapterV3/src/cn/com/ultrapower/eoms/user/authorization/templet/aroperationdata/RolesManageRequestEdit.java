package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesManageBean;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class RolesManageRequestEdit extends HttpServlet
{

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		String id 			= "";
		String Role_Name 	= "";
    	String Role_Orderby = "";
    	String Role_Desc 	= "";
    	String Role_memo 	= "";
    	String Role_memo1 	= "";
    	
    	boolean flag 						= false;
		res.setContentType("text/html");
		PrintWriter out 					= res.getWriter();
		
		RolesManage rolesManage 		= new RolesManage();
		RolesManageBean rolesManageInfo = new RolesManageBean();
		
		id				= Function.nullString(req.getParameter("id"));
		Role_Name 		= Function.nullString(req.getParameter("rolename"));
		Role_Orderby 	= Function.nullString(req.getParameter("roleordervalue"));
		Role_Desc 		= Function.nullString(req.getParameter("roledesc"));
		Role_memo 		= Function.nullString(req.getParameter("rolememo"));
		Role_memo1 		= Function.nullString(req.getParameter("rolememo1")); 
		
		rolesManageInfo.setRole_Desc(Role_Desc);
		rolesManageInfo.setRole_memo(Role_memo);
		rolesManageInfo.setRole_memo1(Role_memo1);
		rolesManageInfo.setRole_Name(Role_Name);
		rolesManageInfo.setRole_Orderby(Role_Orderby);
		
		flag = rolesManage.rolesManageModify(id,rolesManageInfo);
		
		if (flag) 
		{

			String contextPath = req.getContextPath();
			res.sendRedirect(contextPath+"/roles/rolesmanagelist.jsp");

		} else 
		{
			out.println("<HTML><HEAD><TITLE>角色管理修改页面</TITLE></HEAD>");
			out.println("<BODY>");
			out.println("falure");
			out.println("</BODY>");
			out.println("</HEAD>");
			out.println("</HTML>");
			out.flush();
		}
	}



}
