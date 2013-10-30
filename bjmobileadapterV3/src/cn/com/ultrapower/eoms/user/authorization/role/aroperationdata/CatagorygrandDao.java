package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.Catagorygrandpo;

public class CatagorygrandDao extends HttpServlet {
	
	private String rolesId;
	private String selectedSource;
	
	/**
	 * Constructor of the object.
	 */
	public CatagorygrandDao()
	{
		rolesId			= "";
		selectedSource	= "";
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
			throws ServletException, IOException
	{
		
		rolesId					= request.getParameter("id");			
		//添加组成员信息时,获得人员ID的字符串
		selectedSource			= request.getParameter("selectedSource");
		//删除组成员信息时获得checkbox提交过来的组成员ID值
		String catagorygrandid[]	= request.getParameterValues("catagorygrandid");
		//进行数据库操作
		Catagorygrand catagorygrand		= new Catagorygrand();
		Catagorygrandpo catagorygrandpo = new Catagorygrandpo();
		//delete the data of table UltraProcess:SysGroupUser by groupUserId
		if(catagorygrandid!=null&&!(catagorygrandid.equals("")))
		{
			//根据组成员ID删除相应组成员信息
			for(int i = 0;i<catagorygrandid.length;i++)
			{
				catagorygrand.deleteCatagorygrand(catagorygrandid[i]);
			}
			response.sendRedirect("CatagorygrandShow?id="+rolesId);
		}
		
		//insert the data to the table UltraProcess:SYSGROUPSUSER by groupId from the virtualgrouptree.jsp	
		else if(selectedSource!=null&&!(selectedSource.equals("")))
		{
			//组成员信息bean
			
			String temp_string[] = selectedSource.split(",");
	  		for(int i=0;i<temp_string.length;i++)
	  		{	
	  			catagorygrandpo.setCondition_RoleID(rolesId);
	  			catagorygrandpo.setCondition_sourceid(temp_string[i]);
	  			
	  			if(catagorygrand.isDuplicate(catagorygrandpo)){
	  				continue;
	  			}else{
	  				catagorygrand.insertCatagorygrand(catagorygrandpo);
	  			}	  			
	  		}
	  		
	  		response.sendRedirect("../roles/catagorygrand_sourcetree.jsp?targetflag=yes&id="+rolesId);
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
			throws ServletException, IOException
	{
		doGet(request,response);
	}

}
