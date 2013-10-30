package cn.com.ultrapower.eoms.user.sla.bussiness;

	import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.sla.aroperationdata.SlaConfig;

	public class SlaConfigDel extends HttpServlet {


		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException 
		{
			   doPost(request,response);
		}

		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException 
		{
				
			String[] id;
			id = request.getParameterValues("delrecord");
			
			SlaConfig slaconfig = new SlaConfig();
			for(int i=0; i<id.length; i++){
				slaconfig.deleteSlaConfig(id[i]);
			}			
			
			response.sendRedirect("../roles/slaconfiglist.jsp");
		}
	}
