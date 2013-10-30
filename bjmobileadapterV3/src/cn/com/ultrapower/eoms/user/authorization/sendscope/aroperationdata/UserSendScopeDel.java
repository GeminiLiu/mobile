package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage.UserSendScopeSQL;

public class UserSendScopeDel extends HttpServlet
{


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		UserSendScopeSQL usersendsql = new UserSendScopeSQL();
		UserSendScopeOp sendscopeop = new UserSendScopeOp();	
		
		String DelRequest[]=request.getParameterValues("delrecord");
		for(int i=0;i<DelRequest.length;i++)
		{
				
				List idlist = usersendsql.getUserSendScopeID(DelRequest[i]);
				for(Iterator it = idlist.iterator();it.hasNext();)
				{
					String sendid = String.valueOf((Long)it.next());
					boolean bl = sendscopeop.sourceCnfDel(sendid);
					if(bl)
					{
						System.out.println("删除 "+sendid+" 成功");
					}
					else
					{
						System.out.println("删除 "+sendid+" 失败");
					}
				}
		}

		response.sendRedirect("../roles/usersendscope.jsp");
	}

}
