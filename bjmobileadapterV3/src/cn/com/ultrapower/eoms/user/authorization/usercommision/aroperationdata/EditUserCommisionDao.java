package cn.com.ultrapower.eoms.user.authorization.usercommision.aroperationdata;

import java.io.IOException;
import java.util.List;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.dbmanage.UserCommisionFind;

public class EditUserCommisionDao extends HttpServlet {
	
	private String skillconfer_LoginName;
	private String skillconfer_SourceID;
	private String skillconfer_DeanlLoginName;
	private String skillconfer_Cause;

	/**
	 * Constructor of the object.
	 */
	public EditUserCommisionDao()
	{
		skillconfer_LoginName 		= "";
		skillconfer_SourceID		= "";
		skillconfer_DeanlLoginName	= "";
		skillconfer_Cause			= "";
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
		
		//数据库操作类
		UserCommision userCommision = new UserCommision();
		skillconfer_LoginName 		= request.getParameter("confer_LoginName");
		skillconfer_SourceID		= request.getParameter("confer_SourceID");
		skillconfer_DeanlLoginName	= request.getParameter("confer_DeanlLoginName");
		skillconfer_Cause			= request.getParameter("skillconfer_Cause");
		
		//保留的用户资源权限动作ID
		String[] valuesArray		= request.getParameterValues("valuesArray");
		
		try
		{
			//获得资源权限动作值List
			UserCommisionFind userCommisionFind = new UserCommisionFind();
			//List list = userCommisionFind.getActionValues(skillconfer_LoginName, skillconfer_SourceID, skillconfer_DeanlLoginName);
			if(!String.valueOf(valuesArray).equals("null")&&!String.valueOf(valuesArray).equals(""))
			{
				for(int i = 0;i<valuesArray.length;i++)
				{
					if(!String.valueOf(valuesArray[i]).equals("")&&!String.valueOf(valuesArray[i]).equals("null"))
					{
						//userCommision.deleteUserCommision(userCommisionFind.getSkillconfer_ID(skillconfer_LoginName, skillconfer_SourceID, skillconfer_DeanlLoginName, valuesArray[i]));
						userCommisionFind.getSkillconfer_Bean(skillconfer_LoginName, skillconfer_SourceID, skillconfer_DeanlLoginName, valuesArray[i],skillconfer_Cause);
					}
					else
					{
						continue;
					}
				}
			}
			response.sendRedirect("../roles/commisionshow.jsp");
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
