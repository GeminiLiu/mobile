package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesSkillManageBean;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.GetRolesSkillManage;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSkillManagepo;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class RolesSkillManageRequestEdit extends HttpServlet
{


	public void dogGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		String RoleSkill_Name 			= "";
		String RoleSkill_SourceID 		= "";
		String RoleSkill_Sourcequery 	= "";
		String RoleSkill_Grand 			= "";
		String RoleSkill_OrderBy 		= "";
		String RoleSkill_Desc 			= "";
		String RoleSkill_memo 			= "";
		String RoleSkill_memo1 			= "";
    	String workflowtype				= "";
    	boolean flag 					= false;
		res.setContentType("text/html");
		PrintWriter out 				= res.getWriter();
		
		RolesSkillManageBean rolesSkillManageInfo 	= new RolesSkillManageBean();
		
		RoleSkill_Name 			= Function.nullString(req.getParameter("skillname"));
		RoleSkill_SourceID 		= Function.nullString(req.getParameter("skillsourceid"));
		RoleSkill_Sourcequery 	= Function.nullString(req.getParameter("skillquery"));
		//RoleSkill_Grand 		= Function.nullString(req.getParameter("skillaction"));
		RoleSkill_OrderBy 		= Function.nullString(req.getParameter("skillorderby")); 
		RoleSkill_Desc 			= Function.nullString(req.getParameter("skilldesc")); 
		RoleSkill_memo 			= Function.nullString(req.getParameter("skillmemo")); 
		RoleSkill_memo1 		= Function.nullString(req.getParameter("skillmemo1")); 
		workflowtype			= Function.nullString(req.getParameter("workflowtype"));
		rolesSkillManageInfo.setRoleSkill_Desc(RoleSkill_Desc);
		rolesSkillManageInfo.setRoleSkill_memo(RoleSkill_memo);
		rolesSkillManageInfo.setRoleSkill_memo1(workflowtype);
		rolesSkillManageInfo.setRoleSkill_Name(RoleSkill_Name);
		rolesSkillManageInfo.setRoleSkill_OrderBy(RoleSkill_OrderBy);
		rolesSkillManageInfo.setRoleSkill_SourceID(RoleSkill_SourceID);
		rolesSkillManageInfo.setRoleSkill_Sourcequery(RoleSkill_Sourcequery);
		
		
		GetRolesSkillManage rolesskillmanage 	= new GetRolesSkillManage();
		RolesSkillManage roleskillmanage = new RolesSkillManage();
		
		List list = rolesskillmanage.getRolesSkillManageList(RoleSkill_Name,RoleSkill_SourceID,RoleSkill_memo);
		Iterator roleit 					= list.iterator();
	  	
		while(roleit.hasNext())
	  	{	
	  		RolesSkillManagepo skillpo 	= (RolesSkillManagepo)roleit.next();
	  		String skillid = skillpo.getC1();
	  		roleskillmanage.rolesskillManageDelete(skillid);
	  	}
	  	String DelRequest[]=req.getParameterValues("delrecord");
		if(DelRequest.length > 0)
		{
			for(int i=0;i<DelRequest.length;i++){
				RoleSkill_Grand = DelRequest[i];
				if(RoleSkill_Grand == null || RoleSkill_Grand.equals(""))
				{
					rolesSkillManageInfo.setRoleSkill_Grand("");
				}
				else
				{
					rolesSkillManageInfo.setRoleSkill_Grand(RoleSkill_Grand);
					flag = roleskillmanage.rolesskillManageInsert(rolesSkillManageInfo);
				}
			}
		}
		if (flag) 
		{
			String contextPath = req.getContextPath();
			if(req.getParameter("rolesmanage") != null && !req.getParameter("rolesmanage").equals("")){
				res.sendRedirect(contextPath+"/roles/refreshprarent.jsp");
			}else{
				res.sendRedirect(contextPath+"/roles/rolesskillmanagelist.jsp");
			}
		} else 
		{
			out.println("<HTML><HEAD><TITLE>璧勬簮鎺堟潈绠＄悊淇敼椤甸潰</TITLE></HEAD>");
			out.println("<BODY>");
			out.println("falure");
			out.println("</BODY>");
			out.println("</HEAD>");
			out.println("</HTML>");
			out.flush();
		}
		
	}

}
