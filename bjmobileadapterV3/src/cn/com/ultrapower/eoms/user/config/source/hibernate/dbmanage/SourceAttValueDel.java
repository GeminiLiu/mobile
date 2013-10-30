package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Description:封装删除资源属性值信息的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-30
 */
public class SourceAttValueDel extends HttpServlet {
	
	private String belongrow;
	private String sourceid;

	/**
	 * Constructor of the object.
	 */
	public SourceAttValueDel() {
		
		belongrow	= "";
		sourceid	= "";	
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
		
		sourceid	= request.getParameter("sourceid");
		belongrow	= request.getParameter("belongrow");
		
		SourceAttValueOp attValue = new SourceAttValueOp();
		attValue.sourceAttValueDelete(belongrow);
		
		RequestDispatcher rd=request.getRequestDispatcher("../roles/sourceattvalueshow.jsp?sourceid="+sourceid);
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
			throws ServletException, IOException {
		doGet(request,response);
	}

}
