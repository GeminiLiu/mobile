package cn.com.ultrapower.ultrawf.control.configalarm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.ultrawf.control.configalarm.AlarmManager;
import cn.com.ultrapower.ultrawf.control.configalarm.models.ConfigAlarmModel;

public class SaveAlarmFormSelect extends HttpServlet {

	
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

		this.doPost(request, response);
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
		String cfgid=request.getParameter("cfgid");
		String cfgName=request.getParameter("cfgName");
		String cfgStatus=request.getParameter("cfgStatus");
		String cfgBaseItems=request.getParameter("cfgBaseItems");
		String cfgLogicType=request.getParameter("cfgLogicType");
		String cfgResponsElevel=request.getParameter("cfgResponsElevel");
		String cfgCloseOpsatisfaction=request.getParameter("cfgCloseOpsatisfaction");
		String cfgCloseDesc=request.getParameter("cfgCloseDesc");
		ConfigAlarmModel configAlarmModel=new ConfigAlarmModel();
		configAlarmModel.setCfgName(cfgName);
		configAlarmModel.setCfgBaseItems(cfgBaseItems);
		configAlarmModel.setCfgStatus(cfgStatus);
		configAlarmModel.setCfgLogicType(cfgLogicType);
		configAlarmModel.setCfgResponsElevel(cfgResponsElevel);
		configAlarmModel.setCfgCloseDesc(cfgCloseDesc);
		configAlarmModel.setCfgCloseOpsatisfaction(cfgCloseOpsatisfaction);
		configAlarmModel.setCfgid(Integer.parseInt(cfgid));
		AlarmManager manager=new AlarmManager();
		if(cfgid!=null && !"".equals(cfgid)){
			manager.UpdateConfigAlarm(configAlarmModel);
		}
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("<script type='text/javascript'>var pwin = window.parent;" +
				"pwin.opener.location='listFormAlarmQuery.jsp' ;"+
				"pwin.close();" +
				"</script>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}


}
