package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class SourceAttRequestDel extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(SourceAttRequestDel.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			String DelRequest[]=request.getParameterValues("DelRequest");
			for(int i=0;i<DelRequest.length;i++){
				SourceAttDataBaseOp sourceConfigOp=new SourceAttDataBaseOp();
				sourceConfigOp.sourceCnfDel(DelRequest[i]);
			}
			
			response.sendRedirect("../roles/sourcecfgattrshow.jsp");
		 }catch(Exception e){
			   logger.error("376 DutyRequestDel servlet error:"+e.toString());
		}
	
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request,response);
	}
}
