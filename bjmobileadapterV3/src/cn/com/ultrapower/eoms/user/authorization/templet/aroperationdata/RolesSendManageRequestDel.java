package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.log.aroperationdata.ArLog;
import cn.com.ultrapower.eoms.util.user.UserInformation;

	public class RolesSendManageRequestDel extends HttpServlet
	{
	
		public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
	   doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		GetFormTableName gftn 	= new GetFormTableName();
		String tableName 		= "";
		tableName 		= gftn.GetFormName("rolessendmanage");
//		浠巗ession涓幏寰楃敤鎴蜂俊鎭�
		HttpSession session = request.getSession(true);
		
		String tuser = "";
  		if(session.getAttribute("userName")!=null)
    	{
    		tuser = (String)session.getAttribute("userName");
    	}
    	else
    	{
    		UserInformation userInfomation	= (UserInformation)session.getAttribute("userInfo");
			tuser							= userInfomation.getUserLoginName();
    	}
		
		RolesSendManage rolesmanage = new RolesSendManage();
		String DelRequest[]=request.getParameterValues("delrecord");
		for(int i=0;i<DelRequest.length;i++)
		{
			ArLog.logWrite(tuser,"鍒犻櫎瑙掕壊娲惧彂鏉冮檺","琛ㄥ悕锛�"+tableName);
			rolesmanage.rolesSendManageDelete(DelRequest[i]);
		}
		String rolesname = Function.toChineseiso(Function.nullString(request.getParameter("rolesname")));
		String rolesnameUTF = java.net.URLEncoder.encode(rolesname,"utf-8");
		if(request.getParameter("roleid") != null){
			response.sendRedirect("../roles/rolessendmanagelist_rolesmanage.jsp?roleid="+request.getParameter("roleid")+"&rolesname="+rolesnameUTF);
		}else{ 
			response.sendRedirect("../roles/rolessendmanagelist.jsp");
		}
	
	}

}
