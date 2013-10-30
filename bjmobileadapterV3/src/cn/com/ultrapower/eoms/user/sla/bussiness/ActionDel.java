package cn.com.ultrapower.eoms.user.sla.bussiness;

import java.io.IOException;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.ActionOp;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.MailOperation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 日期 2006-12-4
 * @author xuquanxing
 * 删除短信,邮件,升级动作时调用该类
 */
public class ActionDel extends HttpServlet {
    static final Logger logger = (Logger) Logger.getLogger(ActionDel.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}   
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String[] delrow = req.getParameterValues("delrecord");
		ActionOp op     = new ActionOp();
		String slaid    = "";
		try
		{
			for(int i=0;i<delrow.length;i++)
			{
				String str         = delrow[i];
				String[] delrecord = str.split(",");
				String tablename   = delrecord[0];
				String rowid       = delrecord[1];
				       slaid       = delrecord[2];
				op.delRow(tablename,rowid);
				
			}			
		}catch(Exception e)
		{
			logger.error("[151]ActionDel:dopost删除动作异常"+e.getMessage());
		}
		String Path = req.getContextPath();
		res.sendRedirect(Path+"/roles/slaedit.jsp?slaid="+slaid+"");
	}
	
}
