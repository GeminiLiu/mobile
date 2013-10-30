package cn.com.ultrapower.eoms.user.config.filesubscribe.bean;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.dbmanage.HiberanteOperation;
import cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.po.FileSubscribe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileSubscribeEdit extends HttpServlet
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
		Long fileid  = Long.valueOf(req.getParameter("fileid"));
		String[] source = sourceid.split(",");
		if(!sourceid.equals(""))
		{
				HiberanteOperation op   = new HiberanteOperation();
				FileSubscribe   filesub = new FileSubscribe();
				filesub.setFilesmsFlag(isorder);
				filesub.setFilesmsId(fileid);
				filesub.setFilesmsSourceid(sourceid);
				filesub.setFilesmsUserid(userid);
				filesub.setNote(contents);
				boolean issucc = op.updateRecord(filesub);
				if(issucc)
				{
					String Path = req.getContextPath();
					res.sendRedirect(Path+"/roles/filesubscribeindex.jsp");
				}else
				{
					System.out.println("sorry");
				}
    		}
		}
	} 



