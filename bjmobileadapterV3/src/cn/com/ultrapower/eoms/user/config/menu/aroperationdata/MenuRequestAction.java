package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.config.menu.aroperationdata.ArMenu;
import cn.com.ultrapower.eoms.user.config.menu.bean.MenuBean;
public class MenuRequestAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	static final Logger logger = (Logger) Logger.getLogger(MenuRequestAction.class);
	
	public MenuRequestAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String  dropDownConfID 			= request.getParameter("dropDownConfID");
		String  dropDownConfFieldID	    = request.getParameter("dropDownConf_FieldID");
		String  dropDownConf_TableName  = request.getParameter("DropDownConf_TableName");
		String  dropDownConfFieldValue  = request.getParameter("dropDownConf_FieldValue");
		String  dropDownConfNumValue  	= request.getParameter("dropDownConfNumValue");
		String  dropDownConfOrderBy		= request.getParameter("dropDownConfOrderBy");
		
		String  flagType  	   		 	= request.getParameter("flagType");
		
		MenuBean menuBean= new MenuBean();
		try{
			if(dropDownConfID == null||dropDownConfID.equals("")){
				menuBean.setDropDownConf_ID("");
			}else{
				menuBean.setDropDownConf_ID(dropDownConfID);
			}
			if(dropDownConfFieldID == null||dropDownConfFieldID.equals("")){
				menuBean.setDropDownConf_FieldID("");
			}else{
				menuBean.setDropDownConf_FieldID(dropDownConfFieldID);
			}
			if(dropDownConfFieldValue == null||dropDownConfFieldValue.equals("")){
				menuBean.setDropDownConf_FieldValue("");
			}else{
				menuBean.setDropDownConf_FieldValue(dropDownConfFieldValue);
			}
			if(dropDownConfNumValue == null||dropDownConfNumValue.equals("")){
				menuBean.setDropDownConf_NumValue("");
			}else{
				menuBean.setDropDownConf_NumValue(dropDownConfNumValue);
			}
			
			if(dropDownConfOrderBy == null||dropDownConfOrderBy.equals("")){
				menuBean.setDropDownConf_OrderBy("");
			}else{
				menuBean.setDropDownConf_OrderBy(dropDownConfOrderBy);
			}
			
			if(dropDownConf_TableName == null||dropDownConf_TableName.equals("")){
				menuBean.setDropDownConf_TableName("");
			}else{
				menuBean.setDropDownConf_TableName(dropDownConf_TableName);
			}
			
			ArMenu arMenu=new ArMenu();
			if (flagType.equals("1"))
			{
				arMenu.menuInsertArray(menuBean);
			}else if (flagType.equals("2"))
			{	
				arMenu.menuModify(dropDownConfID,menuBean);
			}
		}catch(Exception e){
			logger.error("364 MenuRequestAction servlet error:"+e.toString());
		}
		try
		{
			String Path = request.getContextPath();
			response.sendRedirect(Path+"/roles/sysdropdownconfig.jsp?targetflag=yes");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
