package cn.com.ultrapower.eoms.user.sla.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.sla.hibernate.po.UserSlaConfigPo;

public class UserSlaConfigDao extends HttpServlet {
	
	private String Id;
	private String selectedPeople;
	
	/**
	 * Constructor of the object.
	 */
	public UserSlaConfigDao()
	{
		Id			= "";
		selectedPeople	= "";
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
			throws ServletException, IOException
	{
		
		Id					= request.getParameter("id");			
		//添加组成员信息时,获得人员ID的字符串
		selectedPeople			= request.getParameter("selectedPeople");
		//删除组成员信息时获得checkbox提交过来的组成员ID值
		String UserSlaConfigid[]	= request.getParameterValues("UserSlaConfigid");
		//进行数据库操作
		UserSlaConfig userslaconfig		= new UserSlaConfig();
		UserSlaConfigPo userslaconfigpo = new UserSlaConfigPo();
		//delete the data of table UltraProcess:SysGroupUser by groupUserId
		if(UserSlaConfigid!=null&&!(UserSlaConfigid.equals("")))
		{
			//根据组成员ID删除相应组成员信息
			for(int i = 0;i<UserSlaConfigid.length;i++)
			{
				userslaconfig.deleteUserSlaConfig(UserSlaConfigid[i]);
			}
			response.sendRedirect("UserSlaConfigShow?id="+Id);
		}
		
		//insert the data to the table UltraProcess:SYSGROUPSUSER by groupId from the virtualgrouptree.jsp	
		else if(selectedPeople!=null&&!(selectedPeople.equals("")))
		{
			//组成员信息bean
			
			String temp_string[] = selectedPeople.split(",");
	  		for(int i=0;i<temp_string.length;i++)
	  		{	
	  			userslaconfigpo.setSlaid(Id);
	  			userslaconfigpo.setUserid(temp_string[i]);
	  			
	  			if(userslaconfig.isDuplicate(userslaconfigpo)){
	  				continue;
	  			}else{
	  				userslaconfig.insertUserSlaConfig(userslaconfigpo);
	  			}	  			
	  		}
	  		
	  		response.sendRedirect("../roles/userslaconfig_usertree.jsp?targetflag=yes&id="+Id);
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
			throws ServletException, IOException
	{
		doGet(request,response);
	}

}
