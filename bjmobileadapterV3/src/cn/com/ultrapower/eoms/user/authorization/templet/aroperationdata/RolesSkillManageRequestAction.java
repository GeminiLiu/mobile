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
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class RolesSkillManageRequestAction  extends HttpServlet
{


	
	public void dogGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		boolean bool = false;
		String RoleSkill_Name 			= "";
		String RoleSkill_SourceID 		= "";
		String RoleSkill_Sourcequery 	= "";
		String RoleSkill_Grand 			= "";
		String RoleSkill_OrderBy 		= "";
		String RoleSkill_Desc 			= "";
		String RoleSkill_memo 			= "";
		String RoleSkill_memo1 			= "";
		String workflowtype 			= "";
		
    	boolean flag 					= false;
		res.setContentType("text/html");
		PrintWriter out 				= res.getWriter();
		
		RolesSkillManage rolesskillManage 			= new RolesSkillManage();
		RolesSkillManageBean rolesSkillManageInfo 	= new RolesSkillManageBean();
		
		RoleSkill_Name 			= Function.nullString(req.getParameter("skillname"));
		RoleSkill_SourceID 		= Function.nullString(req.getParameter("skillsourceid"));
		RoleSkill_Sourcequery 	= Function.nullString(req.getParameter("skillquery"));
		RoleSkill_Grand 		= Function.nullString(req.getParameter("skillaction"));
		RoleSkill_OrderBy 		= Function.nullString(req.getParameter("skillorderby")); 
		RoleSkill_Desc 			= Function.nullString(req.getParameter("skilldesc")); 
		RoleSkill_memo 			= Function.nullString(req.getParameter("skillmemo")); 
		RoleSkill_memo1 		= Function.nullString(req.getParameter("skillmemo1")); 
		workflowtype 			= Function.nullString(req.getParameter("workflowtype"));
		
		String rolesname = Function.nullString(req.getParameter("rolesname"));
		String rolesnameUTF = java.net.URLEncoder.encode(rolesname,"utf-8");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select rolesSkillManagepo.c1 from RolesSkillManagepo rolesSkillManagepo where rolesSkillManagepo.c660000006='"+ RoleSkill_Name +"'");
		sql.append(" and rolesSkillManagepo.c660000007='"+ RoleSkill_SourceID +"'");
		List list = null;
		try{
			list = HibernateDAO.queryObject(sql.toString());
			Iterator it = list.iterator();
			if(it.hasNext()){
				bool = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(!bool){
			rolesSkillManageInfo.setRoleSkill_Desc(RoleSkill_Desc);		
			rolesSkillManageInfo.setRoleSkill_memo(RoleSkill_memo);
			rolesSkillManageInfo.setRoleSkill_memo1(workflowtype);
			rolesSkillManageInfo.setRoleSkill_Name(RoleSkill_Name);
			rolesSkillManageInfo.setRoleSkill_OrderBy(RoleSkill_OrderBy);
			rolesSkillManageInfo.setRoleSkill_SourceID(RoleSkill_SourceID);
			rolesSkillManageInfo.setRoleSkill_Sourcequery(RoleSkill_Sourcequery);
			
			System.out.println("鎶�鑳藉姩浣滐細"+RoleSkill_Grand);
			String[] str = RoleSkill_Grand.split(",");
			
			for (int j = 0; j < str.length; j++) 
			{			
				rolesSkillManageInfo.setRoleSkill_Grand(str[j]);
				flag = rolesskillManage.rolesskillManageInsert(rolesSkillManageInfo);
			}
			
			if (flag) 
			{	
				String contextPath = req.getContextPath();
				res.sendRedirect(contextPath+"/roles/rolesskillmanageadd.jsp?targetflag=yes");
	
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
		}else{
			String contextPath = req.getContextPath();
			if(!rolesname.equals("")){
				res.sendRedirect(contextPath+"/roles/rolesskillmanageadd_rolesmanage.jsp?targetflag=no&roleid='"+RoleSkill_Name+"'&rolesname="+rolesnameUTF);
			}else{
				res.sendRedirect(contextPath+"/roles/rolesskillmanageadd.jsp?targetflag=no");
			}			
		}
		
	}
}
