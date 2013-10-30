package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.comm.function.ConvertTimeToSecond;
public class RoleRequestEdit extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(RoleRequestEdit.class);

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		String str					= "";
		String skilluserid 			= "";
		String skillgroupid 		= "";
		String skillmodule 			= "";
		String skillcategory 		= "";
		String skillaction 			= "";
		String commissiongid 		= "";
		String commissionuid 		= "";
		String commissionclosetime 	= "";
		String skillstatus 			= "";
		String workflowtype 		= "";
		String skilltype 			= "";
		boolean flag 				= false;
		
		res.setContentType("text/html");
		PrintWriter out 			= res.getWriter();
		Role role 					= new Role();
		RoleBean rolebean 			= new RoleBean();
		
		str 				= Function.nullString(req.getParameter("bakvalue"));
		skilltype			= Function.nullString(req.getParameter("skilltype"));
		skillmodule  		= Function.nullString(req.getParameter("skillmoudleid"));
		skillstatus 		= Function.nullString(req.getParameter("skillstatus"));
		workflowtype 		= Function.nullString(req.getParameter("workflowtype"));
		skillgroupid 		= Function.nullString(req.getParameter("skillgroupid"));
		skillcategory 		= Function.nullString(req.getParameter("skillcategory"));
		commissionuid 		= Function.nullString(req.getParameter("skillcommissionid"));
		commissiongid 		= Function.nullString(req.getParameter("skillcommissiontype"));
		commissionclosetime = Function.nullString(req.getParameter("skillcommissionclosetime"));
		
		
		if (skillgroupid == null || skillgroupid.equals(""))
        {
			rolebean.setSkill_UserID("");
			rolebean.setSkill_GroupID("");
        } else
		{
        	skilluserid =Function.nullString(req.getParameter("skilluserid"));
        	if(skilluserid==null||skilluserid.equals("")||skilluserid.equals("null"))
        	{
        		rolebean.setSkill_UserID("");
				rolebean.setSkill_GroupID(skillgroupid);
        	}else
        	{
	        		rolebean.setSkill_UserID(skilluserid);
	        		rolebean.setSkill_GroupID(skillgroupid);
	        	
        	}
		}
		
        	rolebean.setSkill_Module(skillmodule);
        	rolebean.setSkill_CategoryQueryID(skillcategory);
        	rolebean.setSkill_CommissionUID(commissionuid);
        	rolebean.setSkill_CommissionGID(commissiongid);
        	long temptime = new Long(ConvertTimeToSecond.timeConvert(commissionclosetime)).longValue();
        	rolebean.setSkill_CommissionCloseTime(temptime);
        	rolebean.setSkill_Status(skillstatus);
        	rolebean.setSkill_WorkFlowType(workflowtype);
        	rolebean.setSkill_Type(skilltype);
		
			if(str == null || str.equals(""))
			{
			
			}else
			{
				String[] delstr = str.split(",");
				System.out.println("delstr:::::::::::"+delstr.length);
				String roleid = delstr[0];
				String typeid = delstr[1];
				System.out.println("groupid::::"+typeid);
				String skillcondition = "";
				String moduleid = delstr[2];
				if(delstr.length >3)
				{
				 skillcondition = delstr[3];
				}
				GetRole getrole = new GetRole();
				List list = getrole.getRoleActionList(roleid,typeid,moduleid,skillcondition);
				if(list.size()>0)
				{
					Iterator it = list.iterator();
					while(it.hasNext())
					{
						SysSkillpo skillpo = (SysSkillpo)it.next();
						String id = skillpo.getC1();
						role.roleDelete(id);
					}
				}
			}
			try
			{
				String DelRequest[]=req.getParameterValues("delrecord");
				System.out.println("LENGTH:"+DelRequest.length);
				if(DelRequest.length > 0)
				{
					
				
					for(int i=0;i<DelRequest.length;i++){
						skillaction = DelRequest[i];
						if(skillaction == null || skillaction.equals(""))
						{
							rolebean.setSkill_Action("");
						}
						else
						{
							rolebean.setSkill_Action(skillaction);
							flag = role.roleInsert(rolebean);
						}
					}
					if (flag) 
					{
						res.sendRedirect("../roles/roleshow.jsp");
					} else 
					{
						out.println("<HTML><HEAD><TITLE>资源ID修改</TITLE></HEAD>");
						out.println("<BODY>");
						out.println("���ʧ�ܣ�");
						out.println("</BODY>");
						out.println("</HEAD>");
						out.println("</HTML>");
						out.flush();
					}
				}else
				{
					res.sendRedirect("../roles/roleshow.jsp");
				}
			}catch(Exception e)
			{
				res.sendRedirect("../roles/roleshow.jsp");
			}
			
		
	}
	
}
