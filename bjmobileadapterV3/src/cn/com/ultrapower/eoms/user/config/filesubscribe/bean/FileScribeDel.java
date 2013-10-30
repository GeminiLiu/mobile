package cn.com.ultrapower.eoms.user.config.filesubscribe.bean;
import java.io.IOException;
import cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.dbmanage.HiberanteOperation;
import cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.po.FileSubscribe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 日期 2006-12-4
 * @author xuquanxing
 * 删除值班室订阅记录
 */
public class FileScribeDel extends HttpServlet {
    static final Logger logger = (Logger) Logger.getLogger(FileScribeDel.class);
	/**
	 * 
	 */
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}   
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String[] delrow            = req.getParameterValues("delrecord");
		HiberanteOperation op      = new HiberanteOperation();
		Long subscribeid           = null;
		FileSubscribe duty         = new FileSubscribe();
		System.out.println("delrow.length="+delrow.length);
		try
		{
			for(int i=0;i<delrow.length;i++)
			{
				String str         = delrow[i];
				subscribeid        = Long.valueOf(str);
				duty.setFilesmsId(subscribeid);
				op.delRecord(duty);
			}			
		}catch(Exception e)
		{
			//logger.error("[151]ActionDel:dopost删除动作异常"+e.getMessage());
		}
		String Path = req.getContextPath();
		res.sendRedirect(Path+"/roles/filesubscribeindex.jsp");
	}
	
}
