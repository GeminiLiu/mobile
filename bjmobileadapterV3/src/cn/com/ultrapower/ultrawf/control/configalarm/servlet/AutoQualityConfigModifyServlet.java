package cn.com.ultrapower.ultrawf.control.configalarm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.util.StringUtil;
import cn.com.ultrapower.ultrawf.control.configalarm.AlarmManager;
import cn.com.ultrapower.ultrawf.control.configalarm.config.AutoQualityConfigManager;
import cn.com.ultrapower.ultrawf.control.configalarm.models.AutoQualityConfigModel;
import cn.com.ultrapower.ultrawf.control.configalarm.models.ConfigAlarmModel;

public class AutoQualityConfigModifyServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long cfgId = StringUtil.javaLong(StringUtil.checkNull(request.getParameter("cfgId")));
		String cfgName = StringUtil.checkNull(request.getParameter("cfgName"));
		String cfgStatus = StringUtil.checkNull(request.getParameter("cfgStatus"));
		String tmpbasename = StringUtil.checkNull(request.getParameter("cfgBaseName"));
		String cfgBaseSchema = "";
		String cfgBaseName = "";
		if(tmpbasename.equals("0")){
			cfgBaseSchema = "WF:EL_TTM_CCH";
			cfgBaseName = "个人投诉工单";
		}else{
			cfgBaseSchema = "WF:EL_TTM_CCH_CMC";
			cfgBaseName = "集团投诉工单";
		}
		String cfgComplainType = StringUtil.checkNull(request.getParameter("cfgComplainType"));
		String cfgBasePriority = StringUtil.checkNull(request.getParameter("cfgBasePriority"));
		String cfgDealOutTime = StringUtil.checkNull(request.getParameter("cfgDealOutTime"));
		String cfgUserLoginName = StringUtil.checkNull(request.getParameter("cfgUserLoginName"));
		String cfgDesc = "";
		AutoQualityConfigModel configModel=new AutoQualityConfigModel();
		configModel.setCfgId(cfgId);
		configModel.setCfgName(cfgName);
		configModel.setCfgStatus(cfgStatus);
		configModel.setCfgBaseSchema(cfgBaseSchema);
		configModel.setCfgBaseName(cfgBaseName);
		configModel.setCfgComplainType(cfgComplainType);
		configModel.setCfgBasePriority(cfgBasePriority);
		configModel.setCfgDealOutTime(cfgDealOutTime);
		configModel.setCfgUserLoginName(cfgUserLoginName);
		configModel.setCfgDesc(cfgDesc);
		AutoQualityConfigManager manager=new AutoQualityConfigManager();
		manager.updateAutoQualityConfig(configModel);
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("<script type='text/javascript'>var pwin = window.parent;" +
				"pwin.opener.location='QualityConfigList.jsp' ;"+
				"pwin.close();" +
				"</script>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}


}
