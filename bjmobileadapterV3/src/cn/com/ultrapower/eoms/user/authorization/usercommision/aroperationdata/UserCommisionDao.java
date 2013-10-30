package cn.com.ultrapower.eoms.user.authorization.usercommision.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.UserCommisionInfo;
import cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.dbmanage.UserCommisionFind;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

public class UserCommisionDao extends HttpServlet {
	
	private String skillconfer_Cause;
	private String skillconfer_SourceID;
	private String skillconfer_LoginName;

	/**
	 * Constructor of the object.
	 */
	public UserCommisionDao()
	{
		skillconfer_Cause		= "";
		skillconfer_SourceID	= "";
		skillconfer_LoginName	= "";
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

		skillconfer_Cause				= request.getParameter("confer_Cause");
		skillconfer_SourceID			= request.getParameter("confer_SourceID");
		skillconfer_LoginName			= request.getParameter("confer_LoginName");
		
		UserCommision userCommision			= new UserCommision();
		UserCommisionInfo userCommisionInfo	= new UserCommisionInfo();
		//置入授权原因
		if(skillconfer_Cause == null||skillconfer_Cause.equals(""))
		{
			response.sendRedirect("../roles/commisionshow.jsp");
		}
		else
		{
			userCommisionInfo.setSkillconfer_Cause(skillconfer_Cause);
		}
		//置入资源ID
		if(skillconfer_SourceID == null||skillconfer_SourceID.equals(""))
		{
			response.sendRedirect("../roles/commisionshow.jsp");
		}
		else
		{
			userCommisionInfo.setSkillconfer_SourceID(skillconfer_SourceID);
		}
		//置入当前用户ID
		if(skillconfer_LoginName == null||skillconfer_LoginName.equals(""))
		{
			response.sendRedirect("../roles/commisionshow.jsp");
		}
		else
		{
			userCommisionInfo.setSkillconfer_LoginName(skillconfer_LoginName);
		}
		
		//置入资源英文名
		UserCommisionFind UserCommisionFind = new UserCommisionFind();
		userCommisionInfo.setSkillconfer_SourceEnname(UserCommisionFind.getSourceName(skillconfer_SourceID));
		//置入当前用户ID
		GetUserInfoList getUserInfoList = new GetUserInfoList();
		String skillconfer_UserID		= getUserInfoList.getUserInfoName(skillconfer_LoginName).getC1();
		userCommisionInfo.setSkillconfer_UserID(skillconfer_UserID);
		//置入授权时间
		userCommisionInfo.setSkillconfer_StartTime(System.currentTimeMillis()/1000);
		//置入激活状态
		userCommisionInfo.setSkillconfer_Status("0");
		
		try
		{
			String[] skillconfer_checkBox			= request.getParameterValues("chkbox");
			String[] skillconfer_GrandActionValue	= request.getParameterValues("actionValue");
			if(skillconfer_checkBox.length==0)
			{
				response.sendRedirect("../roles/commisionshow.jsp");
			}
			else if(skillconfer_GrandActionValue.length==0)
			{
				response.sendRedirect("../roles/commisionshow.jsp");
			}
			else
			{
				//按照资源权限动作值数组循环
				for(int x = 0;x<skillconfer_GrandActionValue.length;x++)
				{
					//得到资源权限动作值/名称
					String[] valuesArray1 = skillconfer_GrandActionValue[x].split(";");
					//置入资源权限动作值/名称
					userCommisionInfo.setSkillconfer_GrandActionValue(valuesArray1[0]);
					userCommisionInfo.setSkillconfer_GrandActionName(valuesArray1[1]);
					//按照用户组信息数组循环
					for(int y = 0;y<skillconfer_checkBox.length;y++)
					{
						//得到用户ID,用户登录名,用户所属组ID的数组
						String[] valuesArray2 =  skillconfer_checkBox[y].split(";");
						//置入被授权人用户ID
						userCommisionInfo.setSkillconfer_DealUserID(valuesArray2[0]);
						//置入被授权人登录名
						userCommisionInfo.setSkillconfer_DeanlLoginName(valuesArray2[1]);
						//置入被授权组ID
						userCommisionInfo.setSkillconfer_GroupID(valuesArray2[2]);
						//执行添加操作
						userCommision.insertUserCommision(userCommisionInfo);
					}
				}
			}
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
