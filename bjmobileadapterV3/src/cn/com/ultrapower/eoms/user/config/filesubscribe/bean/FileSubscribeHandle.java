package cn.com.ultrapower.eoms.user.config.filesubscribe.bean;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.dbmanage.HiberanteOperation;
import cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.po.FileSubscribe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileSubscribeHandle extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	} 
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String  sourceid  = "";
		Long    isorder   = null;
		String  contents  = "";
		String  userid    = "";
		sourceid = req.getParameter("sourceid");
		isorder  = Long.valueOf(req.getParameter("isorder"));
		contents = req.getParameter("notes");
		userid   = req.getParameter("userid");
		boolean issucc	 =false;
		if(!sourceid.equals(""))
		{
			String[] source = sourceid.split(",");
			for(int i=0; i<source.length;i++)
			{
				Long fileid             = Long.valueOf(Function.getNewID("file_subscribe","filesms_id"));
				HiberanteOperation op   = new HiberanteOperation();
				FileSubscribe   filesub = new FileSubscribe();
				filesub.setFilesmsFlag(isorder);
				filesub.setFilesmsId(fileid);
				filesub.setFilesmsSourceid(source[i]);
				filesub.setFilesmsUserid(userid);
				filesub.setNote(contents);
				issucc = op.createRecord(filesub);
			}
			if(issucc)
			{
				String Path = req.getContextPath();
				res.sendRedirect(Path+"/roles/filesmsorder.jsp?flag='yes'");
			}else
			{
				System.out.println("sorry");
			}
		}
	} 
}
