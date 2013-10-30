package cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.OaManaged;

public class OaStartupDao extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{		
		String pwdId0[]    = request.getParameterValues("startupEles");
		String pwdId1      = request.getParameter("result");
		OaManaged      oaManaged    = new OaManaged();
		StringBuffer str = new StringBuffer();
		if(pwdId0!=null){
			for(int i=0;i<pwdId0.length;i++)
			{
				if(i>0){
					str.append(",");
				}
				str.append(pwdId0[i]);
			}
			oaManaged.update0(str.toString());
		}
		if(pwdId1!=null&&!pwdId1.equals("")){
			oaManaged.update1(pwdId1);
		}		
		RequestDispatcher rd = request.getRequestDispatcher("../roles/oaVisited.jsp");
		rd.forward(request, response);
	}

	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request,response);
	}

}
