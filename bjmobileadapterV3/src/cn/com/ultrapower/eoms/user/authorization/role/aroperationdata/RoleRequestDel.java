package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

	import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;

	public class RoleRequestDel extends HttpServlet {


		/**
		 * 
		 */
		private static final long serialVersionUID = -343085757536241478L;

		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException 
		{
			   doPost(request,response);
		}

		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException 
		{
				
			Role role = new Role();
			String DelRequest[]=request.getParameterValues("delrecord");
			for(int i=0;i<DelRequest.length;i++)
			{
				String str = DelRequest[i];
				
				String[] delstr = str.split(",");
				String typeid = delstr[0];
				String roleid = delstr[1];
				
				String moduleid = delstr[2];
				String skillcondition = "";
				if(delstr.length>3)
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
			response.sendRedirect("../roles/roleshow.jsp");
		}
	}
