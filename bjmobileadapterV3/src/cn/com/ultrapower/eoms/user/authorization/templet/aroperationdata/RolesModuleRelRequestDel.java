package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RolesModuleRelRequestDel extends HttpServlet
{
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
	   doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		
		RolesModuleRel rolesmanage = new RolesModuleRel();
		String DelRequest[]=request.getParameterValues("delrecord");
		for(int i=0;i<DelRequest.length;i++)
		{
			rolesmanage.rolesModuleRelDelete(DelRequest[i]);
		}
		
		response.sendRedirect("../roles/rolesmodulerellist.jsp");
	
	}

}
