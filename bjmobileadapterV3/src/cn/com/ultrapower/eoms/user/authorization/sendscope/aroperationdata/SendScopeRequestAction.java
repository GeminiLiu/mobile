package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.SendScopeBean;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage.GetSendScope;
import cn.com.ultrapower.eoms.user.comm.function.Function;

	public class SendScopeRequestAction extends HttpServlet {

		public void dogGet(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException 
		{
			doPost(req, res);
		}

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException 
		{

			String roleid 						= "";
			String typeid 						= "";
			String sendscopeid					= "";
			String sendscopetype 				= "";
			String sendscopedesc 				= "";
			String sourceid						= "";
			String type							= "";
			String memo1						= "";
			String memo2						= "";
			String memo3						= "";
			
			boolean flag 						= false;
			res.setContentType("text/html");
			PrintWriter out 					= res.getWriter();
			
			SendScope sendscope 			= new SendScope();
			SendScopeBean sendscopeBean 	= new SendScopeBean();
			
			typeid 			= Function.nullString(req.getParameter("typeid"));
			roleid 			= Function.nullString(req.getParameter("roleid"));
			sendscopetype 	= Function.nullString(req.getParameter("sendscopeuserid"));
			sendscopeid 	= Function.nullString(req.getParameter("sendscopegroupid"));
			sendscopedesc 	= Function.nullString(req.getParameter("sendscopedesc"));
			sourceid		= Function.nullString(req.getParameter("sourceid"));
			type			= Function.nullString(req.getParameter("sendscopetype"));
			memo1			= Function.nullString(req.getParameter("memo1"));
			memo2			= Function.nullString(req.getParameter("memo2"));
			memo3			= Function.nullString(req.getParameter("memo3"));
			
			
			sendscopeBean.setManageGroupUser_MType("1");
			sendscopeBean.setManageGroupUser_Desc(sendscopedesc);
			sendscopeBean.setManageGroupUser_Type(type);
			sendscopeBean.setManageGroupUser_Memo1(memo1);
			sendscopeBean.setManageGroupUser_Memo2(memo2);
			sendscopeBean.setManageGroupUser_Memo3(memo3);
			
			sendscopeBean.setManagerGroupUser_Source(sourceid);
			if (roleid == null || roleid.equals(""))
			{
				
			} else 
			{
				String[] userandgroup = roleid.split(",");
				for (int i = 0; i < userandgroup.length; i++) 
				{
					String tmpuser	= "";
					String tmpgroup = "";
					String[] tmpstr = userandgroup[i].split(";");
					if (tmpstr.length > 1) 
					{
						tmpuser = tmpstr[0];
						tmpgroup = tmpstr[1];
						sendscopeBean.setManageGroupUser_UserID(tmpuser);
						sendscopeBean.setManageGroupUser_GroupID(tmpgroup);
					} else {
						sendscopeBean.setManageGroupUser_UserID("");
						sendscopeBean.setManageGroupUser_GroupID("");
					}
					if(sendscopeid == null ||sendscopeid.equals(""))
					{
						sendscopeBean.setManageGroupUser_RoleID("");
					}else
					{
						String[] sendgroup = sendscopeid.split(",");
						for(int j=0;j<sendgroup.length;j++)
						{
							String tmpsendgroup = sendgroup[j];
							boolean bl = GetSendScope.isExists(tmpuser,tmpgroup,tmpsendgroup,sourceid,type);
							if(bl==true)
							{
								flag = true;
								continue;
							}
							sendscopeBean.setManageGroupUser_RoleID(tmpsendgroup);
							flag = sendscope.sendScopeInsert(sendscopeBean);
						}
						
					}
					
				}

			}
			
			
			
			if (flag) 
			{

				String contextPath = req.getContextPath();
				res.sendRedirect(contextPath+"/roles/sendscopeadd.jsp?targetflag=yes");

			} else 
			{
				out.println("<HTML><HEAD><TITLE>Guestbook</TITLE></HEAD>");
				out.println("<BODY>");
				out.println("falure");
				out.println("</BODY>");
				out.println("</HEAD>");
				out.println("</HTML>");
				out.flush();
			}
		}
		
	}

