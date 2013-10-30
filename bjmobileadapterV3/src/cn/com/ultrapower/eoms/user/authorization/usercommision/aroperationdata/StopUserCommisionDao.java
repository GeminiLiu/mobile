package cn.com.ultrapower.eoms.user.authorization.usercommision.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.dbmanage.UserCommisionFind;

public class StopUserCommisionDao extends HttpServlet {
	
	private String confer_CancelCause;

	/**
	 * Constructor of the object.
	 */
	public StopUserCommisionDao()
	{
		confer_CancelCause	= "";
	}

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
		
		confer_CancelCause		= request.getParameter("confer_CancelCause");
		String[] conditions		= request.getParameterValues("conditions");
		
		UserCommisionFind userCommisionFind = new UserCommisionFind();
		//若停用个人授权记录成功
		if(userCommisionFind.stopAllUserCommision(conditions, confer_CancelCause))
		{
			response.sendRedirect("../roles/commisionshow.jsp");
		}
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
			throws ServletException, IOException{
		doGet(request,response);
	}
}
