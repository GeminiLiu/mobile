package cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.GetNewId;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.SysUserBean;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.SysUserInfoAction;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysUserInfo;

public class SysUserInfoSevlet extends HttpServlet {


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
//		String UserInfo_LoginName=Function.nullString(request.getParameter("UserInfo_LoginName"));
		String UserInfo_Gender=Function.nullString(request.getParameter("UserInfo_Gender"));
		String UserInfo_birthday=Function.nullString(request.getParameter("UserInfo_birthday"));
		String UserInfo_native_place=Function.nullString(request.getParameter("UserInfo_native_place"));
		String UserInfo_ID_Card=Function.nullString(request.getParameter("UserInfo_ID_Card"));
		String UserInfo_educational_level=Function.nullString(request.getParameter("UserInfo_educational_level"));
		String UserInfo_alma_mater=Function.nullString(request.getParameter("UserInfo_alma_mater"));
		String UserInfo_current_post=Function.nullString(request.getParameter("UserInfo_current_post"));
		String UserInfo_current_job=Function.nullString(request.getParameter("UserInfo_current_job"));
		
		String UserInfo_room=Function.nullString(request.getParameter("UserInfo_room"));
		String UserInfo_remark=Function.nullString(request.getParameter("UserInfo_remark"));
		String UserInfo_Dep_Name_1And2=Function.nullString(request.getParameter("UserInfo_Dep_Name_1And2"));
		String User_Mobie=Function.nullString(request.getParameter("User_Mobie"));
		String User_Phone=Function.nullString(request.getParameter("User_Phone"));
		String User_Fax=Function.nullString(request.getParameter("User_Fax"));
		String User_Mail=Function.nullString(request.getParameter("User_Mail"));
		String User_Status=Function.nullString(request.getParameter("User_Status"));
		String Memo1=Function.nullString(request.getParameter("Memo1"));
		String Memo2=Function.nullString(request.getParameter("Memo2"));
		String Memo3=Function.nullString(request.getParameter("Memo3"));
		String Memo4=Function.nullString(request.getParameter("Memo4"));
	
		SysUserBean sysUserInfo=new SysUserBean();
//		sysUserInfo.setC680000001(UserInfo_LoginName);
		sysUserInfo.setUserInfo_Gender(UserInfo_Gender);
		sysUserInfo.setUserInfo_birthday(UserInfo_birthday);
		sysUserInfo.setUserInfo_native_place(UserInfo_native_place);
		sysUserInfo.setUserInfo_ID_Card(UserInfo_ID_Card);
		sysUserInfo.setUserInfo_educational_level(UserInfo_educational_level);
		sysUserInfo.setUserInfo_alma_mater(UserInfo_alma_mater);
		sysUserInfo.setUserInfo_current_post(UserInfo_current_post);
		sysUserInfo.setUserInfo_current_job(UserInfo_current_job);
		sysUserInfo.setUserInfo_room(UserInfo_room);
		sysUserInfo.setUserInfo_remark(UserInfo_remark);
		sysUserInfo.setUserInfo_Dep_Name_1And2(UserInfo_Dep_Name_1And2);
		sysUserInfo.setUser_Mobie(User_Mobie);
		sysUserInfo.setUser_Phone(User_Phone);
		sysUserInfo.setUser_Fax(User_Fax);
		sysUserInfo.setUser_Mail(User_Mail);
		sysUserInfo.setUser_Status(User_Status);
		sysUserInfo.setMemo1(Memo1);
		sysUserInfo.setMemo2(Memo2);
		sysUserInfo.setMemo3(Memo3);
		sysUserInfo.setMemo4(Memo4);
		
		
		String username=Function.nullString(request.getParameter("UserInfo_LoginName"));
		sysUserInfo.setUserInfo_LoginName(username);//用户名
		
		String strid="";
		SysUserInfoAction sysUserInfoAction=new SysUserInfoAction();
		try{
			List list =sysUserInfoAction.sysUserInfoQuery(username);
			if (list.size()>0){
				//修改
				Iterator it=list.iterator();
				while(it.hasNext()){
					SysUserInfo ui=(SysUserInfo)it.next();
					strid=ui.getC1();//求出id
				}
				System.out.println("00000000000"+sysUserInfo.getUserInfo_Gender());
				sysUserInfoAction.menuModify(strid,sysUserInfo);
				
			}else{
				sysUserInfoAction.menuInsertArray(sysUserInfo);
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<script language='javascript'>");
			out.println("window.close();");
			out.println("</script>");
		
		}catch(Exception e){
			System.out.print("------");
		}
	}

	

}
