package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.system.util.FormatString;


public class RolesUserGroupRelRequestDel extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
	   doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		RolesUserGroupRel rolesusergrouprel = new RolesUserGroupRel();
		String DelRequest[]=request.getParameterValues("delrecord");
//		String flag = FormatString.CheckNullString(request.getParameter("flag"));
		String tra_login = FormatString.CheckNullString(request.getParameter("tra_loginname"));
		String acc_login = FormatString.CheckNullString(request.getParameter("acc_loginname"));
		String titleflag       = FormatString.CheckNullString(request.getParameter("titflag"));
		
		for(int i=0;i<DelRequest.length;i++)
		{
			rolesusergrouprel.rolesUserGroupRelDelete(DelRequest[i]);
		}
		if(request.getParameter("groupid") != null && request.getParameter("userFullname") == null){
			response.sendRedirect("../roles/rolesusergrouprellist_group.jsp?groupid="+request.getParameter("groupid"));
		}else if(request.getParameter("userFullname") != null){
			String userFullname = Function.toChineseiso(Function.nullString(request.getParameter("userFullname")));
			String userFullnameUTF = java.net.URLEncoder.encode(userFullname,"utf-8");
			response.sendRedirect("../roles/rolesusergrouprellist_people.jsp?userFullname="+userFullnameUTF+"&groupid="+request.getParameter("groupid")
						+"&id="+request.getParameter("id"));
		}else if(!tra_login.equals("") || !acc_login.equals("")){
			response.sendRedirect("../roles/peopleRolesRightsTra_roles.jsp?tra_login="+tra_login+"&acc_login="+acc_login
					+"&titleflag="+titleflag);
		}else if(request.getParameter("roleid") != null){
			String rolesname = Function.toChineseiso(Function.nullString(request.getParameter("rolesname")));
			String rolesnameUTF = java.net.URLEncoder.encode(rolesname,"utf-8");
			response.sendRedirect("../roles/rolesusergrouprellist_rolesmanage.jsp?roleid="+request.getParameter("roleid")+"&rolesname="+rolesnameUTF);
		}else{
			response.sendRedirect("../roles/rolesusergrouprellist.jsp");
		}
			
	}


}
