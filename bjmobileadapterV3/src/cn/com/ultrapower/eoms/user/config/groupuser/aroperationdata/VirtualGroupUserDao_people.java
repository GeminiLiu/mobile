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
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.groupuser.bean.GroupUserInfo;

public class VirtualGroupUserDao_people extends HttpServlet {
		
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
	   doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		String[] id = null;
		List returnList 	= new ArrayList();
        GroupUser groupuser = new GroupUser();
        SkillAndSendSync skillsync = new SkillAndSendSync();
		
		String DelRequest[]=request.getParameterValues("groupid");
		String flag = String.valueOf(request.getParameter("flag"));
		String userId = request.getParameter("userId");
		
		if(flag.equals(String.valueOf("1"))){
			
			String groupid = "";
			for(int i=0;i<DelRequest.length;i++)
	  		{
				groupid+="'"+DelRequest[i]+"'";
				if(i+1<DelRequest.length){
					groupid+=",";
				}
	  		}
			
			List listsql = new ArrayList();
			String sql = " select sysgroupuserpo.c1 from SysGroupUserpo sysgroupuserpo where sysgroupuserpo.c620000027 in ("+groupid+") and sysgroupuserpo.c620000028="+userId+"";
			try{
				listsql = HibernateDAO.queryObject(sql);
				if(listsql.size() > 0){
					String userfullname = Function.toChineseiso(request.getParameter("userFullname"));
					userfullname = java.net.URLEncoder.encode(userfullname,"utf-8");
					response.sendRedirect("../roles/groupuseradd_people.jsp?error=001&userId="+request.getParameter("userId")+"&userDpid="+request.getParameter("userDpid")+"&userFullname="+userfullname);
				}else{
					GroupUserInfo groupUserPoIns = new GroupUserInfo();			
			  		for(int i=0;i<DelRequest.length;i++)
			  		{
			  			groupUserPoIns.setMgroupGroupId(DelRequest[i]);
			  			groupUserPoIns.setMgroupUserId(userId);
			  			groupuser.insertGroupUser(groupUserPoIns);
                        //同步人员工单处理同组归档配置
			  			groupuser.insertUserCloseBaseGroup(groupUserPoIns);
			  		}
					//调用接口，将用户所在组的权限自动添加到用户上。
			  		 for(int j=0;j<DelRequest.length;j++)
			  		 {
		  				GroupUserBean groupuserinfo = new GroupUserBean();
		  				groupuserinfo.setGroupid(DelRequest[j]);
		  				groupuserinfo.setUserid(userId);
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
			  		response.sendRedirect("../roles/refreshprarent.jsp");
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}else{
			for(int i=0;i<DelRequest.length;i++)
			{
				id = DelRequest[i].split(",");
				
				try{
					//先同步删除人员工单处理同组归档配置
					groupuser.deleteUserCloseBaseGroupById(id[1]);
					
					groupuser.deleteGroupUserByGroup(id[1], id[0]);                    
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			String userFullname = Function.toChineseiso(Function.nullString(request.getParameter("userFullname")));
			String userFullnameUTF = java.net.URLEncoder.encode(userFullname,"utf-8");
			response.sendRedirect("../roles/groupuser_people.jsp?id=" + request.getParameter("userId") + "&userFullname="
					+ userFullnameUTF + "&userDpid=" + request.getParameter("userDpid"));
		}		
	}
}
