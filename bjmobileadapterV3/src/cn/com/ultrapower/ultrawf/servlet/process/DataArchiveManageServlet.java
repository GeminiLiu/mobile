package cn.com.ultrapower.ultrawf.servlet.process;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.control.process.DataArchiveManage;

public class DataArchiveManageServlet
    extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    System.out.println("--------------------------------------------------------");			
		System.out.println("--流程引擎启动表级分离后台程序（开始）");	    
		if (Constants.BaseArchiveRunMode.equals("0"))
		{	
			DataArchiveManage objMain = new DataArchiveManage();
			objMain.DataArchiveManageStart();			
			System.out.println("  --已启动");	    
		}
		else
		{
			System.out.println("  --未启动，参数设置不在Web应用中启动");
		}
		System.out.println("--流程引擎启动表级分离后台程序（结束）");	   		
	    System.out.println("--------------------------------------------------------");		
	
	}
}
