package cn.com.ultrapower.eoms.user.sla.bussiness;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


import cn.com.ultrapower.eoms.user.sla.bean.ActionMailCreateBean;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.MailOperation;



public class MailEditHandle extends HttpServlet 
{
	static final Logger logger = (Logger) Logger.getLogger(MailEditHandle.class);
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}
    
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		
		String actionid                       = req.getParameter("actionid");
		String slaid                          = req.getParameter("slaid");
		String mailid                         = req.getParameter("mailid");
		String mail_sendtouser                = req.getParameter("mail_sendtouser");
		String mail_content                   = req.getParameter("mail_content");
		String mail_sendquery                 = req.getParameter("mail_sendquery");
		String mail_copysendto                = req.getParameter("mail_copysendto"); 
		boolean issuccess                     = false;
		
        try
        {
    		ActionMailCreateBean mailbean         = new ActionMailCreateBean();
    		mailbean.setMail_actionid(actionid);
    		mailbean.setMail_content(mail_content);
    		mailbean.setMail_copysendto(mail_copysendto);
    		mailbean.setMail_sendquery(mail_sendquery);
    		mailbean.setMail_sendtouser(mail_sendtouser);
    		mailbean.setMail_slaid(slaid);
    		MailOperation mailop = new MailOperation();
    		issuccess            = mailop.modidyMail(mailbean,mailid);
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
        	logger.error("[152]MailEditHandle:dopost编辑邮件异常"+e.getMessage());
        }
	}
}
