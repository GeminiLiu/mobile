package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.SendScopeBean;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage.GetSendScope;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.po.ManageGroupUserpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;

	public class SendScopeRequestEdit extends HttpServlet {

		public void dogGet(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException 
		{
			doPost(req, res);
		}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException 
		{
			String id 							= "";
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
			
			id 				= Function.nullString(req.getParameter("id"));
			roleid 			= Function.nullString(req.getParameter("roleid"));
			typeid 			= Function.nullString(req.getParameter("typeid"));
			sendscopetype 	= Function.nullString(req.getParameter("sendscopeuserid"));
			sendscopeid 	= Function.nullString(req.getParameter("sendscopegroupid"));
			sendscopedesc 	= Function.nullString(req.getParameter("sendscopedesc"));
			sourceid		= Function.nullString(req.getParameter("sourceid"));
			type			= Function.nullString(req.getParameter("sendscopetype"));
			memo1			= Function.nullString(req.getParameter("memo1"));
			memo2			= Function.nullString(req.getParameter("memo2"));
			memo3			= Function.nullString(req.getParameter("memo3"));
			
			
			boolean bl = false;
			List list = GetSendScope.getSendScopeValue(roleid,typeid,sendscopeid,sourceid);
			
			if(list!=null)
			{
				Iterator it = list.iterator();
				while(it.hasNext())
				{
					ManageGroupUserpo managepo = (ManageGroupUserpo)it.next();
					String tid = managepo.getC1();
					if(!tid.equals(id))
					{
						bl = true;
						break;
					}
				}
				if(bl)
				{
					res.sendRedirect("../roles/sendscopeedit.jsp?id="+id+"&error=001");
				}
				else
				{
					if (typeid == null || typeid.equals(""))
			        {
						sendscopeBean.setManageGroupUser_UserID("");
						sendscopeBean.setManageGroupUser_GroupID("");
			        } else
					{
			        	
			        	if(roleid==null||roleid.equals(""))
			        	{
			        		sendscopeBean.setManageGroupUser_UserID("");
							sendscopeBean.setManageGroupUser_GroupID(typeid);
			        		
			        	}else
			        	{
			        		sendscopeBean.setManageGroupUser_UserID(roleid);
							sendscopeBean.setManageGroupUser_GroupID(typeid);
				        	
			        	}
					}
					
					sendscopeBean.setManageGroupUser_MType("1");
					sendscopeBean.setManageGroupUser_RoleID(sendscopeid);
					sendscopeBean.setManageGroupUser_Desc(sendscopedesc);
					sendscopeBean.setManagerGroupUser_Source(sourceid);
					sendscopeBean.setManageGroupUser_Type(type);
					sendscopeBean.setManageGroupUser_Memo1(memo1);
					sendscopeBean.setManageGroupUser_Memo2(memo2);
					sendscopeBean.setManageGroupUser_Memo3(memo3);
					
					
					flag = sendscope.sendScopeModify(id,sendscopeBean);
					System.out.println(flag+"flag");
					if (flag) 
					{
//						String contextPath = req.getContextPath();
						res.sendRedirect("../roles/sendscopeshow.jsp");
	
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
			else
			{
				if (typeid == null || typeid.equals(""))
		        {
					sendscopeBean.setManageGroupUser_UserID("");
					sendscopeBean.setManageGroupUser_GroupID("");
		        } else
				{
		        	
		        	if(roleid==null||roleid.equals(""))
		        	{
		        		sendscopeBean.setManageGroupUser_UserID("");
						sendscopeBean.setManageGroupUser_GroupID(typeid);
		        		
		        	}else
		        	{
		        		sendscopeBean.setManageGroupUser_UserID(roleid);
						sendscopeBean.setManageGroupUser_GroupID(typeid);
			        	
		        	}
				}
				
				sendscopeBean.setManageGroupUser_MType("1");
				sendscopeBean.setManageGroupUser_RoleID(sendscopeid);
				sendscopeBean.setManageGroupUser_Desc(sendscopedesc);
				sendscopeBean.setManagerGroupUser_Source(sourceid);
				sendscopeBean.setManageGroupUser_Type(type);
				sendscopeBean.setManageGroupUser_Memo1(memo1);
				sendscopeBean.setManageGroupUser_Memo2(memo2);
				sendscopeBean.setManageGroupUser_Memo3(memo3);
				
				
				flag = sendscope.sendScopeModify(id,sendscopeBean);
				System.out.println(flag+"flag");
				if (flag) 
				{
//					String contextPath = req.getContextPath();
					res.sendRedirect("../roles/sendscopeshow.jsp");

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
		
	}

