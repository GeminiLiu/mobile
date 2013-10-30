package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.GetRolesSkillManage;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSkillManagepo;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class RolesSkillManageRequestDel  extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
	   doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		RolesSkillManage roleskillmanage = new RolesSkillManage();
		GetRolesSkillManage rolesskillmanage 	= new GetRolesSkillManage();
		String DelRequest[]=request.getParameterValues("delrecord");
		for(int i=0;i<DelRequest.length;i++)
		{
			String[] str= DelRequest[i].split(",");
			String RoleSkill_Name = str[0];
			String RoleSkill_SourceID = str[1];
			String companyid = str[2];
			
			List list = rolesskillmanage.getRolesSkillManageList(RoleSkill_Name,RoleSkill_SourceID,companyid);
			Iterator roleit = list.iterator();
		  	
			while(roleit.hasNext())
		  	{	
		  		RolesSkillManagepo skillpo 	= (RolesSkillManagepo)roleit.next();
		  		String skillid = skillpo.getC1();
		  		roleskillmanage.rolesskillManageDelete(skillid);
		  	}
		}
		String rolesname = Function.toChineseiso(Function.nullString(request.getParameter("rolesname")));
		String rolesnameUTF = java.net.URLEncoder.encode(rolesname,"utf-8");
		if(request.getParameter("roleid") != null){
			response.sendRedirect("../roles/rolesskillmanagelist_rolesmanage.jsp?roleid="+request.getParameter("roleid")+"&rolesname="+rolesnameUTF);
		}else{ 
			response.sendRedirect("../roles/rolesskillmanagelist.jsp");
		}
			
	}

}
