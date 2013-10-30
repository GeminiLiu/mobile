package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class RolesUserGroupRelRequestEdit extends HttpServlet
{

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		String id				= "";
    	String RoleRel_UserID 	= "";
    	String RoleRel_GroupID 	= "";
    	String RoleRel_RoleID 	= "";
    	String RoleRel_OrderBy 	= "";
    	String RoleRel_Desc 	= "";
    	String RoleRel_memo 	= "";
    	String RoleRel_memo1 	= "";
    	
    	boolean flag 									= false;
		res.setContentType("text/html");
		PrintWriter out 								= res.getWriter();
		
		RolesUserGroupRel rolesusergrouprel 			= new RolesUserGroupRel();
		RolesUserGroupRelBean rolesusergrouprelinfo 	= new RolesUserGroupRelBean();
		
		id						= Function.nullString(req.getParameter("id"));
		RoleRel_UserID 			= Function.nullString(req.getParameter("userid"));
		RoleRel_RoleID 			= Function.nullString(req.getParameter("roleid"));
		RoleRel_OrderBy 		= Function.nullString(req.getParameter("orderby"));
		RoleRel_Desc 			= Function.nullString(req.getParameter("desc")); 
		RoleRel_memo 			= Function.nullString(req.getParameter("memo")); 
		RoleRel_memo1 			= Function.nullString(req.getParameter("memo1")); 
		RoleRel_GroupID			= Function.nullString(req.getParameter("groupid"));
		
		
		rolesusergrouprelinfo.setRoleRel_Desc(RoleRel_Desc);
		rolesusergrouprelinfo.setRoleRel_memo(RoleRel_memo);
		rolesusergrouprelinfo.setRoleRel_memo1(RoleRel_memo1);
		rolesusergrouprelinfo.setRoleRel_OrderBy(RoleRel_OrderBy);
		rolesusergrouprelinfo.setRoleRel_RoleID(RoleRel_RoleID);
		rolesusergrouprelinfo.setRoleRel_UserID(RoleRel_UserID);
		rolesusergrouprelinfo.setRoleRel_GroupID(RoleRel_GroupID);
		
		List listsql = new ArrayList();
		String sql = " select rolesusergrouprelpo.c1 from RolesUserGroupRelpo rolesusergrouprelpo" +
				" where rolesusergrouprelpo.c660000028='"+RoleRel_RoleID+"' and (rolesusergrouprelpo.c660000026='" +
				RoleRel_UserID+"' or rolesusergrouprelpo.c660000027='"+RoleRel_GroupID+"')";
		try{
			listsql = HibernateDAO.queryObject(sql.toString());
			if(listsql.size() > 0 && !id.equals((String)listsql.get(0))){
				res.sendRedirect("../roles/rolesusergroupreledit.jsp?error=001");
			}else{
				flag = rolesusergrouprel.rolesUserGroupRelModify(id,rolesusergrouprelinfo);
				if (flag) 
				{

					String contextPath = req.getContextPath();
					res.sendRedirect(contextPath+"/roles/rolesusergrouprellist.jsp");

				} else 
				{
					out.println("<HTML><HEAD><TITLE>璧勬簮鎺堟潈绠＄悊娣诲姞椤甸潰</TITLE></HEAD>");
					out.println("<BODY>");
					out.println("falure");
					out.println("</BODY>");
					out.println("</HEAD>");
					out.println("</HTML>");
					out.flush();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}

}
