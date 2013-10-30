package cn.com.ultrapower.eoms.user.config.groupuser.aroperationdata;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.dbmanage.GroupUserFind;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.comm.function.*;

/**
 * <p>Description:封装按照权限查找出相应用户的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-23
 */
public class FindPeopleDao extends HttpServlet {
	
	private String groupId;

	/**
	 * Constructor of the object.
	 */
	public FindPeopleDao() {
		
		groupId = "";
		
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
		
		groupId = request.getParameter("id");
		
		response.setContentType("text/html;charset=utf-8");
		if(groupId == null||groupId.equals(""))
		{
			groupId = "";
		}
		
		GroupUserFind peopleInfo	= new GroupUserFind();
		//根据权限ID查找相应用户信息
		List list					= peopleInfo.find(groupId);
		StringBuffer strhtml		= new StringBuffer();
		
		strhtml.append("<tr class='tabletitle'><td width='30%'>用户登录名</td><td width='50%'>用户名</td><td align='center' width='20%'><input type='button' class='button' value='删除' onClick='return confirmdel();'></td></tr>");
		for(Iterator it = list.iterator();it.hasNext();)
		{
			Object[] obj			= (Object[])it.next();
			SysPeoplepo syspeople	= (SysPeoplepo)obj[0];
			String c1				= (String)obj[1];
			strhtml.append("<tr class='tablecontent'><td>"+syspeople.getC630000001()+"</td><td>"+syspeople.getC630000003()+"</td><td align='center'><input type='checkbox' class='checkbox' value='"+c1+"' name='groupUser_Id'></td></tr>");
		}
		
		//获得组类型
		GetGroupInfoList getGroupInfoList = new GetGroupInfoList();
		String groupType = getGroupInfoList.getGroupInfo(groupId).getC630000021();
		GetFormTableName getFormTableName	= new GetFormTableName();
		String virtual	= getFormTableName.GetFormName("virtual");
		//判断是否虚拟组
		System.out.println(virtual);
		String url = "";
		//如果是虚拟组
		if(virtual.indexOf(groupType+";")>=0)
		{
			url = "/roles/groupuser.jsp?id="+groupId+"&flag=yes";
		}
		//其他情况
		else
		{
			url = "/roles/groupuser.jsp?id="+groupId+"&flag=no";
		}
		
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
