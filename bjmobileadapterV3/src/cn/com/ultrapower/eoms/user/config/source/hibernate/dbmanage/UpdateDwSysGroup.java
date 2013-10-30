package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata.DwSysGroup;
import cn.com.ultrapower.eoms.user.comm.function.Function;
public class UpdateDwSysGroup extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateDwSysGroup() {
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
		request.setCharacterEncoding("gb2312");
		Function function = new Function();
		String id = request.getParameter("id");		
		String division = request.getParameter("division");
		String dept = request.getParameter("dept_id");
		String str = function.toTrans(dept);
		DwSysGroup dwSysGroup = new DwSysGroup();
		if(dwSysGroup.cops(division, dept)){
			if(dwSysGroup.update(id,division,dept)){
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath+"/roles/sysgroupupdate.jsp?targetflag=yes&companyrel="+ id +"");
			}
			else{
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath+"/roles/sysgroupupdate.jsp?targetflag=no&companyrel="+ id +"");
			}
		}else{
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath+"/roles/sysgroupupdate.jsp?targetflag=no&companyrel="+ id +"");
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
