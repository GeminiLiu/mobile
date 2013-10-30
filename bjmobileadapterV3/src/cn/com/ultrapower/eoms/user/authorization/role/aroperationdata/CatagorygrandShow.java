package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.Catagorygrandpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;

/**
 * <p>Description:封装按照权限查找出相应用户的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-23
 */
public class CatagorygrandShow extends HttpServlet {
	
	private String rolesId;

	/**
	 * Constructor of the object.
	 */
	public CatagorygrandShow() {
		
		rolesId = "";
		
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
		
		rolesId = request.getParameter("id");
		
		response.setContentType("text/html;charset=utf-8");
		if(rolesId == null||rolesId.equals(""))
		{
			rolesId = "";
		}
		
		Catagorygrand catagorygrand	= new Catagorygrand();
		//根据权限ID查找相应用户信息
		List list					= catagorygrand.find(rolesId);
		StringBuffer strhtml		= new StringBuffer();
		
		strhtml.append("<tr class='tabletitle'><td width='30%'>资源名</td><td width='50%'>资源描述</td><td align='center' width='20%'><input type='button' class='button' value='删除' onClick='return confirmdel();'></td></tr>");
		for(Iterator it = list.iterator();it.hasNext();)
		{
			Object[] obj			= (Object[])it.next();
			Catagorygrandpo catagorygrandpo	= (Catagorygrandpo)obj[0];
			Sourceconfig sourceconfig = (Sourceconfig)obj[1];
			strhtml.append("<tr class='tablecontent'><td>"+sourceconfig.getSourceCnname()+"</td><td>"+Function.nullString(catagorygrandpo.getCondition_Desc())+"</td><td align='center'><input type='checkbox' class='checkbox' value='"+catagorygrandpo.getId()+"' name='catagorygrandid'></td></tr>");
		}

		String url = "";

		url = "/roles/catagorygrandlist.jsp?id="+rolesId+"&flag=yes";		
		
		//将用户信息存入request属性中转发到客户端
		request.setAttribute("str", strhtml.toString());
		ServletContext servletContext = getServletContext();
		RequestDispatcher d = servletContext.getRequestDispatcher(url);
		d.forward(request, response);
		
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
