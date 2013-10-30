package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.GroupIsSnatchBean;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage.GetSendScope;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class GroupIsSnatchAction extends HttpServlet
{
	public void dogGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{

		String basecategoryname 	= "";
		String basecategoryschama 	= "";
		String group 				= "";
		String groupid 				= "";
		String groupissnatch 		= "";
		String tmpsourcevalue		= "";
		String tmpgroupvalue		= "";
		boolean flag 				= false;
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		GroupIsSnatch groupsnatch 			= new GroupIsSnatch();
		GroupIsSnatchBean groupsnatchbean 	= new GroupIsSnatchBean();
		
		tmpsourcevalue 	= Function.nullString(req.getParameter("sourcecnname"));
		tmpgroupvalue	= Function.nullString(req.getParameter("groupid"));
		groupissnatch 	= Function.nullString(req.getParameter("groupsnatchtype"));
		
		groupsnatchbean.setGroupissnatch(groupissnatch);
		
		String[] tmpsource = tmpsourcevalue.split(",");
		for(int i=0;i<tmpsource.length;i++)
		{
			String[] tmpstr 	= tmpsource[i].split(";");
			basecategoryname 	= tmpstr[0];
			basecategoryschama 	= tmpstr[1];
			groupsnatchbean.setBasecategoryname(basecategoryname);
			groupsnatchbean.setBasecategoryschama(basecategoryschama);
			String[] tmpgroupstr = tmpgroupvalue.split(",");
			for(int j=0;j<tmpgroupstr.length;j++)
			{
				String[] groupstr = tmpgroupstr[j].split(";");
				group 	= groupstr[0];
				groupid = groupstr[1];
				groupsnatchbean.setGroup(group);
				groupsnatchbean.setGroupid(groupid);
				flag = groupsnatch.groupIsSnatchInsert(groupsnatchbean);
			}
		}
		if (flag)
		{
			String contextPath = req.getContextPath();
			res.sendRedirect(contextPath
					+ "/roles/groupissnatchadd.jsp?targetflag=yes");
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
