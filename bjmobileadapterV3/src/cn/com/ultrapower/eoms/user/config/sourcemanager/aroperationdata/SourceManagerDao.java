package cn.com.ultrapower.eoms.user.config.sourcemanager.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.config.sourcemanager.bean.SourceManagerBean;

public class SourceManagerDao extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(SourceManagerDaoDel.class);
	/**
	 * Constructor of the object.
	 */
	public SourceManagerDao() {
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
		String sourcemanager_sourceid		= Function.nullString(String.valueOf(request.getParameter("sourcemanager_sourceid")));
		String sourcemanager_query    		= Function.nullString(String.valueOf(request.getParameter("sourcemanager_query")));
		String sourcemanager_groupid   		= Function.nullString(String.valueOf(request.getParameter("sourcemanager_groupid")));
		String sourcemanager_userid  		= Function.nullString(String.valueOf(request.getParameter("sourcemanager_userid")));
		String sourcemanager_type  	    	= Function.nullString(String.valueOf(request.getParameter("sourcemanager_type")));
		String sourcemanager_sgroupid  	   	= Function.nullString(String.valueOf(request.getParameter("sourcemanager_sgroupid")));
		String sourcemanager_suserid		= Function.nullString(String.valueOf(request.getParameter("sourcemanager_suserid")));
		
		
//		if (sourcemanager_groupid == null || sourcemanager_groupid.equals(""))
//        {
//			sourcemanager_groupid = "";
//			sourcemanager_userid = "";
//        } else
//		{
//        	{
//	        	if(sourcemanager_groupid.equals("1"))
//	        	{
//	        		sourcemanager_groupid = sourcemanager_userid;
//	        		sourcemanager_userid = "";
//	        		
//	        	}
//	        	
//        	}
//		}
//		
//		if (sourcemanager_sgroupid == null || sourcemanager_sgroupid.equals(""))
//        {
//			sourcemanager_sgroupid = "";
//			sourcemanager_suserid = "";
//        } else
//		{
//        	{
//	        	if(sourcemanager_sgroupid.equals("1"))
//	        	{
//	        		sourcemanager_sgroupid = sourcemanager_suserid;
//	        		sourcemanager_suserid = "";
//	        		
//	        	}
//	        	
//        	}
//		}
		SourceManagerBean sourceManagerBean = new SourceManagerBean();
		SourceManager sourceManager=new SourceManager();
		boolean trueflag = false;
		
		String[] sourcemanager_groupids = sourcemanager_groupid.split(",");
		
		if(sourcemanager_groupids != null && sourcemanager_groupids.length > 0){
			
			for(int i=0; i<sourcemanager_groupids.length; i++){
				
				sourceManagerBean.setsourcemanager_sourceid(sourcemanager_sourceid);
				sourceManagerBean.setsourcemanager_query(sourcemanager_query);
				sourceManagerBean.setsourcemanager_groupid(sourcemanager_groupids[i]);
				sourceManagerBean.setsourcemanager_userid("");
				sourceManagerBean.setsourcemanager_type(sourcemanager_type);
				sourceManagerBean.setsourcemanager_sgroupid(sourcemanager_sgroupid);
				sourceManagerBean.setsourcemanager_suserid(sourcemanager_suserid);
				
				if(sourceManager.isDuplicate(sourceManagerBean)){
					//trueflag = false;
				}else{
					trueflag = sourceManager.sourceManagerInsert(sourceManagerBean);
				}				
			}
			
		}
		
		try
		{			
			String Path = request.getContextPath();
			if(trueflag)
			{
				logger.info("cn.com.ultrapower.eoms.user.config.sourcemanager.aroperationdata.sourcemanagerDao 增加成功");
				response.sendRedirect(Path+"/roles/sourcemanageradd.jsp?targetflag=yes");
			}
			else
			{
				logger.info("cn.com.ultrapower.eoms.user.config.sourcemanager.aroperationdata.sourcemanagerDao 增加失败");				
				response.sendRedirect(Path+"/roles/sourcemanageradd.jsp");
			}
			
		}catch(Exception e){
			e.printStackTrace();
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
