package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesModuleRelBean;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.GetRolesModuleRel;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.GetRolesSendManage;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.GetRolesSkillManage;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSendManagepo;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSkillManagepo;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class RolesModuleRelRequestAction extends HttpServlet
{

	
	public void dogGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
    	String RoleModule_RoleID 	= "";
    	String RoleModule_SkillID 	= "";
    	String RoleModule_Type 		= "";
    	String RoleModule_memo 		= "";
    	String RoleModule_memo1 	= "";
    	String RoleModule_memo2 	= "";
    	
    	boolean flag 						= false;
		res.setContentType("text/html");
		PrintWriter out 					= res.getWriter();
		
		RolesModuleRel rolesmodulerel 			= new RolesModuleRel();
		RolesModuleRelBean rolesmodulerelInfo = new RolesModuleRelBean();
		
		RoleModule_RoleID 		= Function.nullString(req.getParameter("modulename"));
		RoleModule_SkillID 		= Function.nullString(req.getParameter("skillaction"));
		RoleModule_Type 		= Function.nullString(req.getParameter("typeid"));
		RoleModule_memo 		= Function.nullString(req.getParameter("rolememo"));
		RoleModule_memo1 		= Function.nullString(req.getParameter("rolememo1")); 
		RoleModule_memo2 		= Function.nullString(req.getParameter("rolememo1")); 
		
		rolesmodulerelInfo.setRoleModule_memo(RoleModule_memo);
		rolesmodulerelInfo.setRoleModule_memo1(RoleModule_memo1);
		rolesmodulerelInfo.setRoleModule_memo2(RoleModule_memo2);
		rolesmodulerelInfo.setRoleModule_RoleID(RoleModule_RoleID);
		rolesmodulerelInfo.setRoleModule_Type(RoleModule_Type);
		
		RolesModuleRelSQL rolesql = new RolesModuleRelSQL();
		List list = rolesql.getUserGroupInfo(RoleModule_RoleID);
		RolesModuleRelSync roleskillsync = new RolesModuleRelSync();
		
		String[] skillvalue = RoleModule_SkillID.split(",");
		for(int i=0;i<skillvalue.length;i++)
		{
			rolesmodulerelInfo.setRoleModule_SkillID(skillvalue[i]);
			boolean bl = GetRolesModuleRel.getBoolValue(RoleModule_RoleID,skillvalue[i],RoleModule_Type);
			if(bl)
			{
				flag = rolesmodulerel.rolesModuleRelInsert(rolesmodulerelInfo);
				//同步.....
				if(RoleModule_Type.equals("0"))
				{
					GetRolesSkillManage getskillinfo = new GetRolesSkillManage();
					RolesSkillManagepo skillpo = getskillinfo.getRolesSkillManage(skillvalue[i]);
					roleskillsync.insertToSkill(list,skillpo);
				}
				else if(RoleModule_Type.equals("1"))
				{
					GetRolesSendManage getsendinfo = new GetRolesSendManage();
					RolesSendManagepo sendpo = getsendinfo.getRolesSendManage(skillvalue[i]);
					roleskillsync.insertToSend(list,sendpo);
				}
			}else
			{
				flag = true;
			}
		}
		if (flag) 
		{
			String contextPath = req.getContextPath();
			res.sendRedirect(contextPath+"/roles/rolesmodulereladd.jsp?targetflag=yes");

		} 
		else 
		{
			out.println("<HTML><HEAD><TITLE>角色管理添加页面</TITLE></HEAD>");
			out.println("<BODY>");
			out.println("falure");
			out.println("</BODY>");
			out.println("</HEAD>");
			out.println("</HTML>");
			out.flush();
		}
		
	}


}
