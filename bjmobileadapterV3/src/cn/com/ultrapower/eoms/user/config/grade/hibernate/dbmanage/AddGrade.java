package cn.com.ultrapower.eoms.user.config.grade.hibernate.dbmanage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.com.ultrapower.eoms.user.config.grade.hibernate.dbmanage.Grade_Point;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class AddGrade extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddGrade() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
			throws ServletException, IOException {
		doPost(request, response);
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
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		String gradnode  = request.getParameter("gradnode");
		String gradvalue = request.getParameter("gradvalue");
		String id = Function.getNewID("fungradeconfig","fungradeconfig_id");//获取ID
		Grade_Point grade_Point = new Grade_Point();
		if(!grade_Point.iterant(gradnode)){
			if(grade_Point.insert(id, gradnode, gradvalue)){
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath+"/roles/newGrade.jsp?targetflag=yes&id="+ id +"");
			}else{
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath+"/roles/newGrade.jsp?targetflag=no&id="+ id +"");
			}
		}else{
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath+"/roles/newGrade.jsp?targetflag=no&id="+ id +"");
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
