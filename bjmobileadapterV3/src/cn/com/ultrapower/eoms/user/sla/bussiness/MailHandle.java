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

/**
 * @author xuquanxing/徐全星
 *  该类用于处理新建邮件的动作���൱�½����Ŷ���ʱ����
 */
public class MailHandle extends HttpServlet 
{
	static final Logger logger = (Logger) Logger.getLogger(MailHandle.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
    {
    	doPost(req,res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
    {
    	String slaid             = "";
    	String sendtouser        = "";
     	String sendquery         = "";
    	String actionid          = "";
    	String content           = "";
    	String copysendto        = "";
    	boolean issuccess        = false;
    	
    	res.setContentType("text/html");
    	PrintWriter out         = res.getWriter();
    	slaid                   = req.getParameter("slaid");
    	sendquery               = req.getParameter("mail_sendquery"); 
    	actionid                = req.getParameter("actionid");
    	copysendto              = req.getParameter("mail_copysendto");
    	content                 = req.getParameter("mail_content");
    	sendtouser              = req.getParameter("mail_sendtouser");
        try
        {
        	ActionMailCreateBean mailecreatebean=new ActionMailCreateBean();
        	mailecreatebean.setMail_actionid(actionid);
        	mailecreatebean.setMail_content(content);
        	mailecreatebean.setMail_copysendto(copysendto);
        	mailecreatebean.setMail_sendquery(sendquery);
        	mailecreatebean.setMail_sendtouser(sendtouser);
        	mailecreatebean.setMail_slaid(slaid);
            
        	MailOperation op     = new MailOperation();
        	issuccess            = op.insertMail(mailecreatebean);
        	if(issuccess){
    		     //res.sendRedirect("../roles/slaedit.jsp?slaid="+slaid+""); 
    		     String Path = req.getContextPath();
    		     res.sendRedirect(Path+"/roles/mailadd.jsp?targetflag=yes");
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
        	logger.error("[153]MailHandle:dopost新建邮件动作异常"+e.getMessage());
        }
    }
}
