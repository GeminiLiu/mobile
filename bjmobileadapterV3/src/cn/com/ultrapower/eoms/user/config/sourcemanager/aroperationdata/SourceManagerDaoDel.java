package cn.com.ultrapower.eoms.user.config.sourcemanager.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class SourceManagerDaoDel extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(SourceManagerDaoDel.class);
	/**
	 * Constructor of the object.
	 */
	public SourceManagerDaoDel() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String type = request.getParameter("type");

		try
		{
			String DelRequest[]=request.getParameterValues("delrecord");
			
			SourceManager sourceManager=new SourceManager();
			boolean trueflag = false;
			String Path       = request.getContextPath();
			for(int i=0;i<DelRequest.length;i++)
			{
				trueflag=sourceManager.sourceManagerDel(DelRequest[i]);
				if(trueflag==false)
				{
					break;
				}
			}
			if(trueflag)
			{
				if("wf".equals(type)){
					response.sendRedirect(Path+"/roles/sourcemanagershow_wf.jsp");
				}else{
					response.sendRedirect(Path+"/roles/sourcemanagershow.jsp");
				}				
			}
			else
			{
				logger.info("cn.com.ultrapower.eoms.user.config.sourcemanager.aroperationdata.sourcemanagerDaoMod 删除管理者数据失败");
				if("wf".equals(type)){
					response.sendRedirect(Path+"/roles/sourcemanagershow_wf.jsp");
				}else{
					response.sendRedirect(Path+"/roles/sourcemanagershow.jsp");
				}
			}
			logger.info("删除管理者数据成功");
		}
		catch(Exception e)
		{
			logger.error("[000] TreeDelDao servlet error:"+e.toString());
			if("wf".equals(type)){
				response.sendRedirect("../roles/sourcemanagershow_wf.jsp");
			}else{
				response.sendRedirect("../roles/sourcemanagershow.jsp");
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
