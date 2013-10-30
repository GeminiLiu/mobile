package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.GroupIsSnatchBean;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class GroupIsSnatchEdit extends HttpServlet
{
	public void dogGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{

		String id 					= "";
		String basecategoryname 	= "";
		String basecategoryschama 	= "";
		String group 				= "";
		String groupid 				= "";
		String groupissnatch 		= "";
		boolean flag 				= false;

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		GroupIsSnatch groupsnatch = new GroupIsSnatch();
		GroupIsSnatchBean groupsnatchbean = new GroupIsSnatchBean();
		
		id					= Function.nullString(req.getParameter("id"));
		basecategoryname 	= Function.nullString(req.getParameter("sourcename"));
		basecategoryschama 	= Function.nullString(req.getParameter("sourcecnname"));
		group				= Function.nullString(req.getParameter("groupname"));
		groupid 			= Function.nullString(req.getParameter("groupid"));
		groupissnatch 		= Function.nullString(req.getParameter("groupsnatchtype"));
		
		groupsnatchbean.setBasecategoryname(basecategoryname);
		groupsnatchbean.setBasecategoryschama(basecategoryschama);
		groupsnatchbean.setGroup(group);
		groupsnatchbean.setGroupid(groupid);
		groupsnatchbean.setGroupissnatch(groupissnatch);
		
		flag = groupsnatch.groupIsSnatchModify(id,groupsnatchbean);

		if (flag)
		{
			String contextPath = req.getContextPath();
			res.sendRedirect(contextPath
					+ "/roles/groupissnatchlist.jsp");
		} else
		{
			out.println("<HTML><HEAD><TITLE>组是否抢单</TITLE></HEAD>");
			out.println("<BODY>");
			out.println("falure");
			out.println("</BODY>");
			out.println("</HEAD>");
			out.println("</HTML>");
			out.flush();
		}
	}
}
