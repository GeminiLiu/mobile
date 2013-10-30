package cn.com.ultrapower.eoms.user.sla.bussiness;

import java.io.IOException;
import java.io.PrintWriter;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.SlaDefineOp;
import cn.com.ultrapower.eoms.user.sla.bean.SlaDefineBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.ConvertTimeToSecond;
/**
 * @author xuquanxing
 * 该servlet用于处理sla的修改操作
 *  *
 */
public class SlaEditHandle extends HttpServlet 
{
	static final Logger logger = (Logger) Logger.getLogger(SlaEditHandle.class);
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
		String id                    = "";
		boolean successflag          = false;
	
		System.out.print(id);
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		SlaDefineOp sladefineop = new SlaDefineOp(); 
		HttpSession session     =req.getSession();
		id                           = (String) session.getAttribute("slaid");
		SlaDefineBean sladefinebean = new SlaDefineBean();
		System.out.println(id);
		sla_name = req.getParameter("sla_name");
		if(sla_name==null||sla_name.equals("")){
			sladefinebean.setSla_name("");
		}else{
			sladefinebean.setSla_name(sla_name);
		}
		
		sla_startdatetime = req.getParameter("sla_startdatetime");
		if(sla_startdatetime==null||sla_startdatetime.equals("")){
			sladefinebean.setSla_startdatetime(null);
		}else{
            //�����������͵�ת��
			String temptime = new Long(ConvertTimeToSecond.timeConvert(sla_startdatetime)).toString();
			sladefinebean.setSla_startdatetime(temptime);
		}
		
		sla_enddatetime=req.getParameter("sla_enddatetime");
		if(sla_enddatetime==null||sla_enddatetime.equals("")){
			sladefinebean.setSla_enddatetime(null);
		}else{
			//�����������͵�ת��
			String temptime = new Long(ConvertTimeToSecond.timeConvert(sla_enddatetime)).toString();
			sladefinebean.setSla_enddatetime(temptime);
		}
		
		sla_status=req.getParameter("sla_status");
		if(sla_status==null||sla_status.equals("")){
			sladefinebean.setSla_status("");
		}else{
			sladefinebean.setSla_status(sla_status);
		}
		
		sla_apptable=req.getParameter("sla_apptable");
		if(sla_apptable==null||sla_apptable.equals("")){
			sladefinebean.setSla_apptable("");
		}else{
			sladefinebean.setSla_apptable(sla_apptable);
		}
		
		sla_appcommstartquery=req.getParameter("sla_appcommstartquery");
		if(sla_appcommstartquery==null||sla_appcommstartquery.equals("")){
			sladefinebean.setSla_appcommstartquery("");
		}else{
			sladefinebean.setSla_appcommstartquery(sla_appcommstartquery);
		}
		
		
		sla_appcommendquery=req.getParameter("sla_appcommendquery");
		if(sla_appcommendquery==null||sla_appcommendquery.equals("")){
			sladefinebean.setSla_appcommendquery("");
		}else{
			sladefinebean.setSla_appcommendquery(sla_appcommendquery);
		}
		sla_usercommquery=req.getParameter("sla_usertype");
		if(sla_usercommquery==null||sla_usercommquery.equals("")){
			sladefinebean.setSla_usercommquery("");

		}else{
			sladefinebean.setSla_usercommquery(sla_usercommquery);
		}
		
		    try
		    {
		    	successflag=sladefineop.modifySla(sladefinebean,id);
			    if(successflag)
			    {
			    	String path = req.getContextPath();
	                res.sendRedirect(path+"/roles/slainfo.jsp");

			    
			    }else{
				    out.println("<HTML><HEAD><TITLE>Guestbook</TITLE></HEAD>");
			        out.println("<BODY>");
			        out.println("failure");
			        out.println("</BODY>");
			        out.println("</HEAD>");
			        out.println("</HTML>");
			        out.flush();
			    }
		    }catch(Exception e)
		    {
		    	logger.error("[159]SlaEditHandle:dopost编辑sla异常"+e.getMessage());
		    }
		    
	}
}
