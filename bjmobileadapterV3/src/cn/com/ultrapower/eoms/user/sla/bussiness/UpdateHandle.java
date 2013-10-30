package cn.com.ultrapower.eoms.user.sla.bussiness;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.sla.bean.ActionUpdateCreateBean;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.UpdateOperation;

/**
 * @author xuquanxing/徐全星
 *  该类用于处理新建升级动作的处理���൱�½����Ŷ���ʱ����
 */
public class UpdateHandle extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(UpdateHandle.class);  
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
    	doPost(req,res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
    	String update_slaid             = "";
    	String update_sendtouser        = "";
     	String update_sendquery         = "";
    	String update_actionid          = "";
    	boolean issuccess               = false;
    	
    	res.setContentType("text/html");
    	PrintWriter out            = res.getWriter();
    	update_slaid               = req.getParameter("slaid");
    	update_sendtouser          = req.getParameter("update_sendtouser");
    	update_sendquery           = req.getParameter("update_sendquery");//发送条件 ���ŷ������ 
    	update_actionid            = req.getParameter("actionid");
    	ActionUpdateCreateBean updatecreatebean=new ActionUpdateCreateBean();
    	updatecreatebean.setWorkFlowManage_ActionID(update_actionid);
    	updatecreatebean.setWorkflowmanage_sendquery(update_sendquery);
    	updatecreatebean.setWorkflowmanage_sendtouser(update_sendtouser);
    	updatecreatebean.setWorkflowmanage_slaid(update_slaid);
    	UpdateOperation op   = new UpdateOperation();
    	try
    	{
        	issuccess            = op.insertUpdate(updatecreatebean);
        	if(issuccess)
        	{
        		String Path = req.getContextPath();
        		res.sendRedirect(Path+"/roles/updateadd.jsp?targetflag=yes");
    		    // res.sendRedirect("../roles/slaedit.jsp?slaid="+update_slaid+"");
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
    		logger.error("[158]UpdateHandle:dopost新建升级动作异常"+e.getMessage());
    	}

    	
    }
}
