package cn.com.ultrapower.eoms.user.sla.bussiness;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.sla.aroperationdata.MailOperation;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.SlaDefineOp;
/**
 * 日期 2006-12-8
 * @author xuquanxing
 * 删除sla信息
 */
public class SlaDel extends HttpServlet
{
    static final Logger logger = (Logger) Logger.getLogger(SlaDel.class);
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}  
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String [] delrow = req.getParameterValues("delrecord");
		String  rowid    = "";
		SlaDefineOp op   = new SlaDefineOp();
		try
		{
			for(int i =0; i<delrow.length;i++)
			{
				rowid = delrow[i];
				op.deleteSla(rowid);
			}
			String Path = req.getContextPath();
			res.sendRedirect(Path+"/roles/slainfo.jsp");
			
		}catch(Exception e)
		{
		    logger.error("[154]SlaDel:dopost删除sla异常"+e.getMessage());	
		}
	}
}
