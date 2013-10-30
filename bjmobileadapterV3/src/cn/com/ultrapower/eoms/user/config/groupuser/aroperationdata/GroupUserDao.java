package cn.com.ultrapower.eoms.user.config.groupuser.aroperationdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.GroupUserBean;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.SkillAndSendSync;
import cn.com.ultrapower.eoms.user.config.groupuser.bean.GroupUserInfo;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.dbmanage.GetGroupuserInfoList;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.po.SysGroupUserpo;

/**
 * <p>Description:封装组成员信息的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-20
 */
public class GroupUserDao extends HttpServlet {
	
	private String mgroupId;
	private String selectedPeople;
	private String bakuserinfo;
	public GroupUserDao()
	{
		mgroupId		="";
		selectedPeople	= "";
		bakuserinfo		= "";
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
		SkillAndSendSync skillsync = new SkillAndSendSync();
		List returnList 	= new ArrayList();
		mgroupId			= request.getParameter("id");	
		//bakuserinfo			= request.getParameter("bakuserinfo");
		
		//delete all the data of table UltraProcess:SysGroupUser by groupId
		GroupUser groupUser = new GroupUser();
		
		//GetGroupuserInfoList groupUserInfo = new GetGroupuserInfoList();
		//		List list = groupUserInfo.getUserList(mgroupId);
		//		for(Iterator it = list.iterator();it.hasNext();)
		//		{
		//			SysGroupUserpo groupUserPoDel = (SysGroupUserpo)it.next();
		//			String c1 = groupUserPoDel.getC1();
		//			groupUser.deleteGroupUserByGroup(c1,mgroupId);
		//		}
		
		//insert the data to the table UltraProcess:SysGroupUser by groupId from the selectman.jsp
		GroupUserInfo groupUserPoIns = new GroupUserInfo();
		
		selectedPeople	= request.getParameter("selectedPeople");
		if(selectedPeople!=null&&!(selectedPeople.equals("")))
		{
			String temp_string[] = selectedPeople.split(",");
	  		for(int i=0;i<temp_string.length;i++)
	  		{
	  			groupUserPoIns.setMgroupGroupId(mgroupId);
	  			groupUserPoIns.setMgroupUserId(temp_string[i]);
	  			groupUser.insertGroupUser(groupUserPoIns);
		  		//同步人员工单处理同组归档配置
	  			groupUser.insertUserCloseBaseGroup(groupUserPoIns);
	  		}
	  		response.sendRedirect("../roles/selectman.jsp?targetflag=yes&id="+mgroupId);
		}
		else
		{
			response.sendRedirect("../roles/selectman.jsp?targetflag=yes&id="+mgroupId);
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
			throws ServletException, IOException {
		doGet(request,response);
	}

}
