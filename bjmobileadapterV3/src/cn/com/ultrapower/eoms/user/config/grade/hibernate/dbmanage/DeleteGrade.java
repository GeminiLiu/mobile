package cn.com.ultrapower.eoms.user.config.grade.hibernate.dbmanage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.sla.aroperationdata.MailOperation;
import cn.com.ultrapower.eoms.user.config.grade.hibernate.dbmanage.Grade_Point;
/**
 * 日期 2006-12-8
 * @author xuquanxing
 * 删除sla信息
 */
public class DeleteGrade extends HttpServlet
{
    static final Logger logger = (Logger) Logger.getLogger(DeleteGrade.class);
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}  
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String [] delrow = req.getParameterValues("delrecord");
		String  rowid    = "";
		Grade_Point op   = new Grade_Point();
		try
		{
			for(int i =0; i<delrow.length;i++)
			{
				rowid = delrow[i];
				op.delete(rowid);
			}
			String Path = req.getContextPath();
			res.sendRedirect(Path+"/roles/gradePoint.jsp");
			
		}catch(Exception e)
		{
		    logger.error("[154]SlaDel:dopost删除sla异常"+e.getMessage());	
		}
	}
}
