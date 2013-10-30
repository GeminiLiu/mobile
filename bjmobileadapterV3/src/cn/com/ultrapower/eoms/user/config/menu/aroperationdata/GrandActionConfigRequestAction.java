package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.dbmanage.GrandActionConfigList;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.po.GrandActionConfigpo;

public class GrandActionConfigRequestAction extends HttpServlet{

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{

		String fieldID 					= null;
		String fieldValue				= null;
		String numValue					= null;
		String orderby 					= null;
		boolean flag 					= false;
		
		res.setContentType("text/html");
		PrintWriter out 				= res.getWriter();
		
		GrandActionConfig grandactionconfig 	= new GrandActionConfig();
		GrandActionConfigBean formFieldBean 	= new GrandActionConfigBean();
		fieldID 	= Function.nullString(req.getParameter("fieldid"));
		orderby 	= Function.nullString(req.getParameter("orderby"));
		numValue 	= Function.nullString(req.getParameter("numvalue"));
		fieldValue 	= Function.nullString(req.getParameter("fieldvalue"));
		
	
		formFieldBean.setDropDownConf_FieldID(fieldID);
		formFieldBean.setDropDownConf_FieldValue(fieldValue);
		formFieldBean.setDropDownConf_NumValue(numValue);
		formFieldBean.setDropDownConf_OrderBy(orderby);
		List list = GrandActionConfigList.getDupRecord(fieldID);
		if(list!=null)
		{
			boolean bl = false;
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				GrandActionConfigpo grandaction = (GrandActionConfigpo)it.next();
				String value = grandaction.getC620000034();
				if(value.equals(numValue))
				{
					bl = true;
					break;
				}
			}
			if(bl)
			{
				res.sendRedirect("../roles/grandactionconfigadd.jsp?error=001");
			}
			else
			{
				flag = grandactionconfig.grandActionConfigInsert(formFieldBean);
				if (flag) 
				{
					res.sendRedirect("../roles/grandactionconfigadd.jsp?targetflag=yes");
		
				} else 
				{
					out.println("<HTML><HEAD><TITLE>Guestbook</TITLE></HEAD>");
					out.println("<BODY>");
					out.println("falure!");
					out.println("</BODY>");
					out.println("</HEAD>");
					out.println("</HTML>");
					out.flush();
				}
			}
			
		}
		else
		{
			flag = grandactionconfig.grandActionConfigInsert(formFieldBean);
			if (flag) 
			{
				res.sendRedirect("../roles/grandactionconfigadd.jsp?targetflag=yes");
	
			} else 
			{
				out.println("<HTML><HEAD><TITLE>falure</TITLE></HEAD>");
				out.println("<BODY>");
				out.println("falure!");
				out.println("</BODY>");
				out.println("</HEAD>");
				out.println("</HTML>");
				out.flush();
			}
		}
	}
}
