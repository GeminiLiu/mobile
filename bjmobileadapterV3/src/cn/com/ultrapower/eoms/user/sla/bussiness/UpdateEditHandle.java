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
 * 该类用于升级动作修改的处理
 */
public class UpdateEditHandle extends HttpServlet 
{
	static final Logger logger = (Logger) Logger.getLogger(UpdateEditHandle.class);
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}
    
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String actionid                       = req.getParameter("actionid");
		String slaid                          = req.getParameter("slaid");
		String updateid                       = req.getParameter("updateid");
		String workflowmanage_sendtouser      = req.getParameter("workflowmanage_sendtouser");
		System.out.println(workflowmanage_sendtouser);
		String workflowmanage_sendquery       = req.getParameter("WorkFlowManage_SendQuery");
		boolean issuccess                     = false;
		ActionUpdateCreateBean updatebean     = new ActionUpdateCreateBean();
		updatebean.setWorkFlowManage_ActionID(actionid);
		updatebean.setWorkflowmanage_sendquery(workflowmanage_sendquery);
		updatebean.setWorkflowmanage_sendtouser(workflowmanage_sendtouser);
		updatebean.setWorkflowmanage_slaid(slaid);
		try
		{
			UpdateOperation updateop = new UpdateOperation();
			issuccess          = updateop.modifyUpdate(updatebean,updateid);
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			if(issuccess)
			{
				String Path = req.getContextPath();
				System.out.println("path="+Path);
			    System.out.println("slaid="+slaid);
	    		res.sendRedirect(Path+"/roles/slaedit.jsp?slaid="+slaid+"");
			    
			 }
			else
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
			logger.error("[157]UpdateEditHandle:dopost编辑升级动作异常"+e.getMessage());
		}

	}
}
