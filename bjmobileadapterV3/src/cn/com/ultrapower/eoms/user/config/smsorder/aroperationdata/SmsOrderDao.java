package cn.com.ultrapower.eoms.user.config.smsorder.aroperationdata;

import java.io.IOException;
import java.util.List;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.config.smsorder.bean.SmsOrderInfo;
import cn.com.ultrapower.eoms.user.config.smsorder.hibernate.dbmanage.SmsOrderFind;
import cn.com.ultrapower.eoms.user.config.smsorder.hibernate.po.SysSmsOrderpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;

/**
 * <p>Description:封装处理工单短信订阅管理信息的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-18
 */
public class SmsOrderDao extends HttpServlet {
	
	private String smsOrderUserName;
	private String smsOrderFormSchema;
	private String smsOrderStartTime;
	private String smsOrderEndTime;
	
	/**
	 * Constructor of the object.
	 */
	public SmsOrderDao()
	{
		smsOrderUserName	= "";
		smsOrderFormSchema	= "";
		smsOrderStartTime	= "";
		smsOrderEndTime		= "";
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
		
		smsOrderUserName			= request.getParameter("smsOrderUserName");
		smsOrderFormSchema			= request.getParameter("smsOrderFormSchema");
		smsOrderStartTime			= request.getParameter("smsOrderStartTime");
		smsOrderEndTime				= request.getParameter("smsOrderEndTime");
		GetFormTableName	getPram = new GetFormTableName();
		//判断默认是否发短信
		String isallsend			= getPram.GetFormName("smstype");
		SmsOrderInfo smsOrderInfo = new SmsOrderInfo();
		
		if(smsOrderUserName==null||smsOrderUserName.equals(""))
		{
			response.sendRedirect("../roles/smsorderschema.jsp");
		}
		else
		{
			smsOrderInfo.setSmsOrderUserName(smsOrderUserName);
		}
		
		if(smsOrderFormSchema==null||smsOrderFormSchema.equals(""))
		{
			response.sendRedirect("../roles/smsorderschema.jsp");
		}
		else
		{
			smsOrderInfo.setSmsOrderFormSchema(smsOrderFormSchema);
		}
		
		if(smsOrderStartTime==null||smsOrderStartTime.equals(""))
		{
			response.sendRedirect("../roles/smsorderschema.jsp");
		}
		else
		{
	  		smsOrderInfo.setSmsOrderStartTime(Function.tranHourMinuteToSecond(smsOrderStartTime));
		}
		
		if(smsOrderEndTime==null||smsOrderEndTime.equals(""))
		{
			response.sendRedirect("../roles/smsorderschema.jsp");
		}
		else
		{
	  		smsOrderInfo.setSmsOrderEndTime(Function.tranHourMinuteToSecond(smsOrderEndTime));
		}
		
		//该字段为保留字段，暂时置为空
		smsOrderInfo.setSmsOrderUrgent("");
		//页面工单动作全部选为0，全不选为1，其他为2
		smsOrderInfo.setSmsOrderMemo("2");
		
		//first of all,delete all the record of the FormAction whick is the user who has the session
		SmsOrderFind smsOrderFind	= new SmsOrderFind();
		
		//access the list which is one piece of FormSchemas of the user who has the session 
		List list			= smsOrderFind.findActionList(smsOrderUserName,smsOrderFormSchema);
		
		SmsOrder smsOrder	= new SmsOrder();
		
		if(list!=null&&list.size()!=0)
		{
			//默认不发短信
			//firstly,delete the data
			for(Iterator it = list.iterator();it.hasNext();)
			{
				SysSmsOrderpo smsOrderpo	= (SysSmsOrderpo)it.next();
				String c1					= smsOrderpo.getC1();
				smsOrder.deleteSmsOrder(c1);
			}
			//获得checkbox提交过来的已选中的工单短信动作值
			String[] smsOrderAction	= request.getParameterValues("smsorder_action");
			//页面选中工单动作
			if(!String.valueOf(smsOrderAction).equals("")&&!String.valueOf(smsOrderAction).equals("null"))
			{
				System.out.println(String.valueOf(smsOrderAction));
				for(int i = 0;i<smsOrderAction.length;i++)
				{
					if(!String.valueOf(smsOrderAction[i]).equals("")&&!String.valueOf(smsOrderAction[i]).equals("null"))
					{
						smsOrderInfo.setSmsOrderMemo("0");
						smsOrderInfo.setSmsOrderAction(smsOrderAction[i]);
						smsOrder.insertSmsOrder(smsOrderInfo);
					}
				}
			}
			response.sendRedirect("FindSmsOrderActionDao?smsOrderUserName="+smsOrderUserName+"&smsOrderFormSchema="+smsOrderFormSchema);
		}
		//若不存在工单动作信息
		//insert the record of the FormAction
		else
		{
			//默认不发短信
			//获得checkbox提交过来的已选中的工单短信动作值
			String[] smsOrderAction	= request.getParameterValues("smsorder_action");
			//页面选中工单动作
			if(!String.valueOf(smsOrderAction).equals("")&&!String.valueOf(smsOrderAction).equals("null"))
			{
				for(int i = 0;i<smsOrderAction.length;i++)
				{
					if(!String.valueOf(smsOrderAction[i]).equals("")&&!String.valueOf(smsOrderAction[i]).equals("null"))
					{
						smsOrderInfo.setSmsOrderMemo("0");
						smsOrderInfo.setSmsOrderAction(smsOrderAction[i]);
						smsOrder.insertSmsOrder(smsOrderInfo);
					}
				}
			}
			response.sendRedirect("FindSmsOrderActionDao?smsOrderUserName="+smsOrderUserName+"&smsOrderFormSchema="+smsOrderFormSchema);
			//response.sendRedirect("../roles/smsorderaction.jsp?smsOrderUserName="+smsOrderUserName+"&smsOrderFormSchema="+smsOrderFormSchema+"&smsOrderStartTime="+smsOrderStartTime+"&smsOrderEndTime="+smsOrderEndTime);
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
