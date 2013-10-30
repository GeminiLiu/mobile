package cn.com.ultrapower.eoms.user.config.groupuser.aroperationdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.GroupUserBean;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.SkillAndSendSync;
import cn.com.ultrapower.eoms.user.config.groupuser.bean.GroupUserInfo;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.dbmanage.GroupUserFind;

public class VirtualGroupUserDao extends HttpServlet {
	
	private String groupId;
	private String selectedPeople;
	
	/**
	 * Constructor of the object.
	 */
	public VirtualGroupUserDao()
	{
		groupId			= "";
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
		SkillAndSendSync skillsync = new SkillAndSendSync();
		List returnList 	= new ArrayList();
		
		groupId					= request.getParameter("id");			
		//添加组成员信息时,获得人员ID的字符串
		selectedPeople			= request.getParameter("selectedPeople");
		//删除组成员信息时获得checkbox提交过来的组成员ID值
		String groupUserId[]	= request.getParameterValues("groupUser_Id");
		//进行数据库操作
		GroupUser groupUser		= new GroupUser();
		
		//delete the data of table UltraProcess:SysGroupUser by groupUserId
		if(groupUserId!=null&&!(groupUserId.equals("")))
		{
			//根据组成员ID删除相应组成员信息
			for(int i = 0;i<groupUserId.length;i++)
			{
//				先删除同步人员工单处理同组归档配置
				groupUser.deleteUserCloseBaseGroupById(groupUserId[i]);
				groupUser.deleteGroupUserById(groupUserId[i]);                
			}
			response.sendRedirect("FindPeopleDao?id="+groupId);
		}
		
		//insert the data to the table UltraProcess:SYSGROUPSUSER by groupId from the virtualgrouptree.jsp	
		else if(selectedPeople!=null&&!(selectedPeople.equals("")))
		{
			//组成员信息bean
			GroupUserInfo groupUserPoIns = new GroupUserInfo();
			
			String temp_string[] = selectedPeople.split(",");
	  		for(int i=0;i<temp_string.length;i++)
	  		{
	  			groupUserPoIns.setMgroupGroupId(groupId);
	  			groupUserPoIns.setMgroupUserId(temp_string[i]);
	  			groupUser.insertGroupUser(groupUserPoIns);
	  			//同步人员工单处理同组归档配置
	  			groupUser.insertUserCloseBaseGroup(groupUserPoIns);
	  		}
			//调用接口，将用户所在组的权限自动添加到用户上。
	  		 for(int j=0;j<temp_string.length;j++)
	  		 {
  				GroupUserBean groupuserinfo = new GroupUserBean();
  				groupuserinfo.setGroupid(groupId);
  				groupuserinfo.setUserid(temp_string[j]);
  				returnList.add(groupuserinfo);
	  			 
	  		 }
	  		boolean bl = skillsync.groupUserSync(returnList);
	  		if(bl)
	  		{
	  			System.out.println("同步用户与组权限值成功！");
	  		}
	  		else
	  		{
	  			System.out.println("同步用户与组权限值失败！");
	  		}
	  		
	  		response.sendRedirect("../roles/virtualgrouptree.jsp?targetflag=yes&id="+groupId);
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
