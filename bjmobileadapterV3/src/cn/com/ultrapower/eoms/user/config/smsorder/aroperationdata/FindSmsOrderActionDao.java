package cn.com.ultrapower.eoms.user.config.smsorder.aroperationdata;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.config.smsorder.hibernate.dbmanage.SmsOrderFind;
import cn.com.ultrapower.eoms.user.config.smsorder.hibernate.po.SysSmsOrderpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;

/**
 * <p>Description:封装处理工单FORM信息的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-18
 */
public class FindSmsOrderActionDao extends HttpServlet {
	
	private String smsOrderUserName;
	private String smsOrderFormSchema;

	/**
	 * Constructor of the object.
	 */
	public FindSmsOrderActionDao()
	{
		smsOrderUserName	= "";
		smsOrderFormSchema	= "";
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

		smsOrderUserName	= request.getParameter("smsOrderUserName");
		smsOrderFormSchema	= request.getParameter("smsOrderFormSchema");
		GetFormTableName	getPram = new GetFormTableName();
		//判断默认是否发短信
		String isallsend			= getPram.GetFormName("smstype");
		
		//若用户名，即非法登陆，则返回工单FORM信息页
		if(smsOrderUserName == null||smsOrderUserName.equals(""))
		{
			response.sendRedirect("../roles/smsorderschema.jsp");
		}
		else
		{
			//通过用户登陆名和工单schema查找相应工单短信订阅管理信息的List
			SmsOrderFind smsOrderFind	= new SmsOrderFind();
			List list					= smsOrderFind.findActionList(smsOrderUserName,smsOrderFormSchema);
			
			//工单动作起始时间
			String smsOrderStartTime	= "";
			String smsOrderEndTime		= "";
			String isselectflag			= "0";
			//用逗号将当前用户登陆名的某张工单schema的未选中工单动作拼为字符串
			String temp_str				= "";
			String smsOrderActionId		= "";
			
			if(list!=null&&list.size()!=0)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					SysSmsOrderpo smsOrderpo	= (SysSmsOrderpo)it.next();
					//获得工单动作字段信息
					smsOrderActionId		= smsOrderpo.getC650000001();
					smsOrderStartTime		= String.valueOf(Function.nullLong(smsOrderpo.getC650000005()));
					smsOrderEndTime			= String.valueOf(Function.nullLong(smsOrderpo.getC650000006()));
					isselectflag			= String.valueOf(Function.nullString(smsOrderpo.getC650000007()));
		
					if(!temp_str.equals(""))
					{
						temp_str = temp_str + ",";
					}
					temp_str = temp_str + smsOrderActionId;
				
				}
			}
			else
			{
				temp_str = "";
			}
			//已选中工单动作
			String final_str			= "";
			final_str = temp_str;
			//将拼得条件存入request中属性中
			request.setAttribute("actionId", final_str);
			
			ServletContext servletContext = getServletContext();
			RequestDispatcher d = servletContext.getRequestDispatcher("/roles/smsorderaction.jsp?smsOrderUserName="+smsOrderUserName+"&smsOrderFormSchema="+smsOrderFormSchema+"&smsOrderStartTime="+smsOrderStartTime+"&smsOrderEndTime="+smsOrderEndTime);
			d.forward(request, response);
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
