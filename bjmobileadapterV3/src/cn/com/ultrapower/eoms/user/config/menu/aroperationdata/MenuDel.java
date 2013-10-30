package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class MenuDel extends HttpServlet {

	static final Logger logger = (Logger) Logger.getLogger(MenuDel.class);
		
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			String DelRequest[]=request.getParameterValues("DelRequest");
			for(int i=0;i<DelRequest.length;i++){
				ArMenu schemaedit=new ArMenu();
				schemaedit.menuDelete(DelRequest[i]);
			}
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath+"/roles/sysdropdownconfiglist.jsp");
		}catch(Exception e){
			logger.error("365 MenuDel servlet error:"+e.toString());
		}
	}
}
