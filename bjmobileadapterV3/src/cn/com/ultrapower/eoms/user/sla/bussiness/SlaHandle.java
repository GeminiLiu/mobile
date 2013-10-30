package cn.com.ultrapower.eoms.user.sla.bussiness;

import java.io.IOException;
import java.io.PrintWriter;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.SlaDefineOp;
import cn.com.ultrapower.eoms.user.sla.bean.SlaDefineBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.ConvertTimeToSecond;
/**
 * @author xuquanxing/徐全星
 *该类处理新建sla
 *
 */
public class SlaHandle extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(SlaHandle.class);
	// Establish the connection for this instance
	public void dogGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		String sla_name              = "";
		String sla_startdatetime     = "";
		String sla_enddatetime       = "";
		String sla_status            = "";
		String sla_apptable          = "";
		String sla_appcommstartquery = "";
		String sla_appcommendquery   = "";
		String sla_usercommquery     = "";
		String successflag           = "";

		
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out = res.getWriter();
		SlaDefineOp sladefineop = new SlaDefineOp(); 
		SlaDefineBean sladefinebean = new SlaDefineBean();
		sla_name = req.getParameter("sla_name");
		if(sla_name==null||sla_name.equals(""))
		{
			sladefinebean.setSla_name("");
		}else
		{
			sladefinebean.setSla_name(sla_name);
		}
		
		sla_startdatetime = req.getParameter("sla_startdatetime");
		if(sla_startdatetime==null||sla_startdatetime.equals(""))
		{
			sladefinebean.setSla_startdatetime(null);
			
		}else
		{

			String temptime = new Long(ConvertTimeToSecond.timeConvert(sla_startdatetime)).toString();
			sladefinebean.setSla_startdatetime(temptime);
		}
		
		sla_enddatetime=req.getParameter("sla_enddatetime");
		if(sla_enddatetime==null||sla_enddatetime.equals(""))
		{
			sladefinebean.setSla_enddatetime(null);
		}else
		{

			String temptime = new Long(ConvertTimeToSecond.timeConvert(sla_enddatetime)).toString();
			sladefinebean.setSla_enddatetime(temptime);
		}
		
		sla_status=req.getParameter("sla_status");
		if(sla_status==null||sla_status.equals(""))
		{
			sladefinebean.setSla_status("");
		}else
		{
			sladefinebean.setSla_status(sla_status);
		}
		
		sla_apptable=req.getParameter("sla_apptable");
		if(sla_apptable==null||sla_apptable.equals(""))
		{
			sladefinebean.setSla_apptable("");
		}else
		{
			sladefinebean.setSla_apptable(sla_apptable);
		}
		
		sla_appcommstartquery=req.getParameter("sla_appcommstartquery");
		if(sla_appcommstartquery==null||sla_appcommstartquery.equals(""))
		{
			sladefinebean.setSla_appcommstartquery("");
		}else
		{
			sladefinebean.setSla_appcommstartquery(sla_appcommstartquery);
		}
		
		
		sla_appcommendquery=req.getParameter("sla_appcommendquery");
		if(sla_appcommendquery==null||sla_appcommendquery.equals(""))
		{
			sladefinebean.setSla_appcommendquery("");
		}else
		{
			sladefinebean.setSla_appcommendquery(sla_appcommendquery);
		}
		
		
		sla_usercommquery=req.getParameter("sla_usertype");
		if(sla_usercommquery==null||sla_usercommquery.equals(""))
		{
			sladefinebean.setSla_usercommquery("");

		}else
		{
			sladefinebean.setSla_usercommquery(sla_usercommquery);
		}

        System.out.println("sla_usercommquery"+sla_usercommquery);
        try
        {
		    successflag=sladefineop.insertSla(sladefinebean);
		    if(successflag==null||successflag.equals(""))//如果返回值不为空时执行下面的操作
		    {
		    	
		    }else
		    {
		    	//res.sendRedirect("../roles/slaadd.jsp?targetflag=yes");
		        res.sendRedirect("../roles/slaedit.jsp?slaid="+successflag+"");
		    }
        }catch(Exception e)
        {
        	logger.error("[160]SlaHandle:dopost新建sla异常"+e.getMessage());
        }

	}

}
