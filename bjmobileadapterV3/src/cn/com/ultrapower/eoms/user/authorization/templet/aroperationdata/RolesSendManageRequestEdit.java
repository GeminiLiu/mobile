package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesSendManageBean;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.GetRolesSendManage;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.log.aroperationdata.ArLog;
import cn.com.ultrapower.eoms.util.user.UserInformation;

public class RolesSendManageRequestEdit extends HttpServlet
{

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException 
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		GetFormTableName gftn 	= new GetFormTableName();
		String tableName 		= "";
		tableName 		= gftn.GetFormName("rolessendmanage");
//		浠巗ession涓幏寰楃敤鎴蜂俊鎭�
		HttpSession session = req.getSession(true);
		
		String tuser = "";
  		if(session.getAttribute("userName")!=null)
    	{
    		tuser = (String)session.getAttribute("userName");
    	}
    	else
    	{
    		UserInformation userInfomation	= (UserInformation)session.getAttribute("userInfo");
			tuser							= userInfomation.getUserLoginName();
    	}
		String id				= "";
		String RoleSend_Name 	= "";
		String RoleSend_SouceID = "";
		String RoleSend_Group 	= "";
		String RoleSend_OrderBy = "";
		String RoleSend_Desc 	= "";
		String RoleSend_type 	= "";
		String RoleSend_memo1 	= "";
		
		boolean flag 					= false;
		res.setContentType("text/html");
		PrintWriter out 				= res.getWriter();
		
		RolesSendManage rolessendmanage = new RolesSendManage();
		RolesSendManageBean rolessendmanageinfo = new RolesSendManageBean();
		
		id						= Function.nullString(req.getParameter("id"));
		RoleSend_Name 			= Function.nullString(req.getParameter("sendname"));
		RoleSend_SouceID 		= Function.nullString(req.getParameter("sendsourceid"));
		RoleSend_Group 			= Function.nullString(req.getParameter("sendscopeuserid"));
		RoleSend_OrderBy 		= Function.nullString(req.getParameter("sendorderby"));
		RoleSend_Desc 			= Function.nullString(req.getParameter("senddesc")); 
		RoleSend_type 			= Function.nullString(req.getParameter("sendscopetype")); 
		RoleSend_memo1 			= Function.nullString(req.getParameter("sendmemo1")); 
		
		rolessendmanageinfo.setRoleSend_Desc(RoleSend_Desc);
		rolessendmanageinfo.setRoleSend_SouceID(RoleSend_SouceID);
		rolessendmanageinfo.setRoleSend_Group(RoleSend_Group);
		rolessendmanageinfo.setRoleSend_OrderBy(RoleSend_OrderBy);
		rolessendmanageinfo.setRoleSend_Name(RoleSend_Name);
		rolessendmanageinfo.setRoleSend_memo(RoleSend_type);
		rolessendmanageinfo.setRoleSend_memo1(RoleSend_memo1);
		
		GetRolesSendManage getrolessendmanage = new GetRolesSendManage(); 
		try{
			if(getrolessendmanage.isDuplicate(id, RoleSend_Name, RoleSend_SouceID, RoleSend_Group, RoleSend_type)){
				res.sendRedirect("../roles/rolessendmanageedit.jsp?error=1");				
		    }else{
		    	flag = rolessendmanage.rolesSendManageModify(id,rolessendmanageinfo);
		    }			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		if (flag) 
		{
			ArLog.logWrite(tuser,"淇敼瑙掕壊","淇敼浜�"+tableName);
			String contextPath = req.getContextPath();
			if(req.getParameter("rolesmanage") != null && !req.getParameter("rolesmanage").equals("")){
				res.sendRedirect(contextPath+"/roles/refreshprarent.jsp");
			}else{
				res.sendRedirect(contextPath+"/roles/rolessendmanagelist.jsp");
			}
		} else 
		{
			out.println("<HTML><HEAD><TITLE>娲惧彂鎺堟潈绠＄悊娣诲姞椤甸潰</TITLE></HEAD>");
			out.println("<BODY>");
			out.println("falure");
			out.println("</BODY>");
			out.println("</HEAD>");
			out.println("</HTML>");
			out.flush();
		}
		
	}
}
