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
 * @author xuquanxing/徐全星
 *  该类用于处理新建短信动作���൱�½����Ŷ���ʱ����
 */
public class SmsHandle extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(SmsHandle.class);
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
    	doPost(req,res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
    	String sms_slaid        = "";
    	String sms_sendtouser   = "";
    	String sms_content      = "";
    	String sms_sendquery    = "";
    	String sms_sendendquery = "";
    	String sms_send         = "";
    	String sms_sendnum      = "";
    	String sms_actionid     = "";
    	boolean issuccess       = false;
    	
    	res.setContentType("text/html");
    	PrintWriter out         = res.getWriter();
    	sms_slaid               = req.getParameter("slaid");
    	sms_sendtouser          = req.getParameter("SMS_SendToUser");
    	sms_content             = req.getParameter("SMS_Content");
    	sms_sendquery           = req.getParameter("SMS_SendQuery");
    	sms_sendendquery        = req.getParameter("SMS_SendEndQuery");
    	sms_send                = req.getParameter("SMS_Send");
    	sms_sendnum             = req.getParameter("SMS_SendNum");
    	sms_actionid            = req.getParameter("actionid");
    	
    	ActionSMSCreateBean smscreatebean=new ActionSMSCreateBean();
    	smscreatebean.setSms_slaid(sms_slaid);
    	smscreatebean.setSms_sendtouser(sms_sendtouser);
    	smscreatebean.setSms_content(sms_content);
    	smscreatebean.setSms_sendquery(sms_sendquery);
    	smscreatebean.setSms_sendendquery(sms_sendendquery);
    	smscreatebean.setSms_actionid(sms_actionid);

    	if(sms_send.equals("")||sms_send==null)
    	{
    		smscreatebean.setSms_send("20");
    		}
    	else
    	{
    		smscreatebean.setSms_send(sms_send);
    	}
    	
    	if(sms_sendnum.equals("")||sms_sendnum==null)
    	{
    		smscreatebean.setSms_sendnum("10");
    	}
    	else{
    		smscreatebean.setSms_sendnum(sms_sendnum);
    	}

    	SmsOperation op = new SmsOperation();
    	try
    	{
        	issuccess       = op.insertSms(smscreatebean);
        	if(issuccess){
        		String Path = req.getContextPath();
        		res.sendRedirect(Path+"/roles/smsadd.jsp?targetflag=yes");
    		   //  res.sendRedirect("../roles/smsadd.jsp?targetflag=yes");
    		     
    	
        	}
        	else{
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
    		logger.error("[156]SmsHandle:dopost新建短信异常"+e.getMessage());
    	}

    	
    }
}
