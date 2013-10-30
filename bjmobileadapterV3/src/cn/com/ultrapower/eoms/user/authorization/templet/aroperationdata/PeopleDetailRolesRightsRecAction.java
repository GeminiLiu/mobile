package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.util.user.UserInformation;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.ultrawf.control.design.UserManager;

public class PeopleDetailRolesRightsRecAction extends HttpServlet {


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
			   doPost(request,response);
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
		
		String tuser 		= "";
		UserInformation userInfo = (UserInformation) request.getSession().getAttribute("userInfo");
		if(null!=userInfo){
			  tuser = userInfo.getUserLoginName();
		}
		PeopleRolesRightsTraImpl peopleRolesRightsTraImpl = new PeopleRolesRightsTraImpl();
		UserManager usermanager = new UserManager();
		String[] DelRequest=request.getParameterValues("delrecord");
		if(null!=DelRequest){
			for(int i=0;i<DelRequest.length;i++){
				
				List  rlist   = new ArrayList();
				List<String> dellist = new ArrayList();
				List<String> delnew  = new ArrayList();
				List<String> inslist = new ArrayList();
				List<String> delid   = new ArrayList();
				Map<String,List> map = new HashMap<String,List>();
				
				//查找当前选择的记录的用户，对应在表WorkflowRoleUserpo中是否有角色
				rlist = peopleRolesRightsTraImpl.getUserDetailRolesId(DelRequest[i]);
				if(rlist.size()>0){
					usermanager.removeUserGroup(DelRequest[i], rlist);
				}
				//根据用户id，查找在backup表中的角色id,如果有角色，就将记录插入到表WorkflowRoleUserpo中，进行恢复
				delnew = peopleRolesRightsTraImpl.getBackUpDetailRoles(DelRequest[i]);
				if(delnew.size()>0){
					usermanager.addUserGroup(DelRequest[i], delnew);
					delid = peopleRolesRightsTraImpl.getBackUpDetailRolesId(DelRequest[i]);
					if(delid.size()>0){
						peopleRolesRightsTraImpl.deleteNewDetailRoles(delid);
					}
				}
			}
		}
		
		response.sendRedirect("../roles/peopleDetailRoleRightsRecover.jsp");
		
	}

}
