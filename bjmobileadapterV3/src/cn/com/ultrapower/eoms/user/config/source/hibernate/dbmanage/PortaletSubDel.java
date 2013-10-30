package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class PortaletSubDel extends HttpServlet
{
	static final Logger logger = (Logger) Logger.getLogger(SourceActionConfigDel.class);
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 try{
				String DelRequest[]=request.getParameterValues("DelRequest");
				for(int i=0;i<DelRequest.length;i++){
					PortaletSubOp PortaletSubOp=new PortaletSubOp();
					PortaletSubOp.portaletSubDelete(DelRequest[i]);
				}
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath+"/roles/portaletsublist.jsp");
			 }catch(Exception e){
				   logger.error("316 PortaletSubDel servlet error:"+e.toString());
			}
	}
}
