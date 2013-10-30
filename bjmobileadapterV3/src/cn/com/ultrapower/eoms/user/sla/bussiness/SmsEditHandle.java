package cn.com.ultrapower.eoms.user.sla.bussiness;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import cn.com.ultrapower.eoms.user.sla.bean.ActionSMSCreateBean;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.SmsOperation;



/**
 * 日期 2006-10-25
 * @author xuquanxing/徐全星
 * 该类用语处理短信动作的编辑修改
 */
public class SmsEditHandle extends HttpServlet 
{
	static final Logger logger = (Logger) Logger.getLogger(SmsEditHandle.class);
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}
    
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String actionid                       = req.getParameter("actionid");
		String slaid                          = req.getParameter("slaid");
		String smsid                         = req.getParameter("sms_id");
		String sms_sendtouser                 = req.getParameter("sms_sendtouser");
		String sms_content                    = req.getParameter("sms_content");
		String sms_sendquery                  = req.getParameter("sms_sendquery");
		String sms_sendendquery               = req.getParameter("sms_sendendquery");
		String sms_send                       = req.getParameter("sms_send");
		String sms_sendnum                    = req.getParameter("sms_sendnum");
		boolean issuccess                     = false;
		System.out.print("smsid  " +smsid);

		ActionSMSCreateBean smsbean         = new ActionSMSCreateBean();
		smsbean.setSms_actionid(actionid);
		smsbean.setSms_content(sms_content);
		smsbean.setSms_send(sms_send);
		smsbean.setSms_sendendquery(sms_sendendquery);
		smsbean.setSms_sendnum(sms_sendnum);
		smsbean.setSms_sendquery(sms_sendquery);
		smsbean.setSms_sendtouser(sms_sendtouser);
		smsbean.setSms_slaid(slaid);
		try
		{
			SmsOperation smsop = new SmsOperation();
			issuccess          = smsop.modidySms(smsbean,smsid);
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			if(issuccess)
			    {
				    String Path = req.getContextPath();
	    		    res.sendRedirect(Path+"/roles/slaedit.jsp?slaid="+slaid+"");
			    
			    }else
			    {
				    out.println("<HTML><HEAD><TITLE>failure</TITLE></HEAD>");
			        out.println("<BODY>");
			        out.println("failure");
			        out.println("</BODY>");
			        out.println("</HEAD>");
			        out.println("</HTML>");
			        out.flush();
			    }
		}catch(Exception e)
		{
			logger.error("[155]SmsEditHandle:dopost编辑短信异常"+e.getMessage());
		}

	}
}
