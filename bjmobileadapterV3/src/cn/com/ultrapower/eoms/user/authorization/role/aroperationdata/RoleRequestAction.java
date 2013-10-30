package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.comm.function.ConvertTimeToSecond;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class RoleRequestAction extends HttpServlet {

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
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
		PrintWriter out 	= res.getWriter();
		Role role 			= new Role();
		RoleBean rolebean 	= new RoleBean();

		skilltype 			= Function.nullString(req.getParameter("skilltype"));
		skillaction 		= Function.nullString(req.getParameter("skillaction"));
		skillstatus 		= Function.nullString(req.getParameter("skillstatus"));
		skillmodule 		= Function.nullString(req.getParameter("skillmoudleid"));
		workflowtype 		= Function.nullString(req.getParameter("workflowtype"));
		skillgroupid 		= Function.nullString(req.getParameter("skillgroupid"));
		skillcategory 		= Function.nullString(req.getParameter("skillcategory"));
		commissionuid 		= Function.nullString(req.getParameter("skillcommissionid"));
		commissiongid 		= Function.nullString(req.getParameter("skillcommissiontype"));
		commissionclosetime = Function.nullString(req.getParameter("skillcommissionclosetime"));

		rolebean.setSkill_Module(skillmodule);
		rolebean.setSkill_CategoryQueryID(skillcategory);
		rolebean.setSkill_CommissionUID(commissionuid);
		rolebean.setSkill_CommissionGID(commissiongid);
		long temptime = new Long(ConvertTimeToSecond.timeConvert(commissionclosetime)).longValue();
		rolebean.setSkill_CommissionCloseTime(temptime);
		rolebean.setSkill_Status(skillstatus);
		rolebean.setSkill_WorkFlowType(workflowtype);
		rolebean.setSkill_Type(skilltype);

		skilluserid = Function.nullString(req.getParameter("skilluserid"));
		if (skilluserid == null || skilluserid.equals("")) 
		{
			
		} else 
		{
			String[] userandgroup = skilluserid.split(",");
			for (int i = 0; i < userandgroup.length; i++) 
			{
				String[] tmpstr = userandgroup[i].split(";");
				if (tmpstr.length > 1) {
					String tmpuser = tmpstr[0];
					String tmpgroup = tmpstr[1];
					boolean bl = GetRole.isExists(tmpuser,tmpgroup,skillmodule);
					if(bl==true)
					{
						flag = true;
						continue;
						
					}
					rolebean.setSkill_UserID(tmpuser);
					rolebean.setSkill_GroupID(tmpgroup);
					
					
					
					
				} else {
					rolebean.setSkill_UserID("");
					rolebean.setSkill_GroupID("");
				}

				// 判断动作，如果动作为空，动作不写入数据库，否则循环写入动作。
				if (skillaction == null || skillaction.equals("")) {
					rolebean.setSkill_Action("");
					flag = role.roleInsert(rolebean);

				} else {
					String[] str = skillaction.split(",");
					for (int j = 0; j < str.length; j++) {
						rolebean.setSkill_Action(str[j]);
						flag = role.roleInsert(rolebean);

					}

				}

			}

		}
		
		if (flag) 
		{
			res.sendRedirect("../roles/roleadd.jsp?targetflag=yes");
		} else 
		{
			out.println("<HTML><HEAD><TITLE>资源ID修改</TITLE></HEAD>");
			out.println("<BODY>");
			out.println("falure");
			out.println("</BODY>");
			out.println("</HEAD>");
			out.println("</HTML>");
			out.flush();
		}

	}

}