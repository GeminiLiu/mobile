package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.GetNewId;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceActionConfigBean;

public class SourceActionConfigAction extends HttpServlet {
	
	static final Logger logger = (Logger) Logger.getLogger(SourceActionConfigAction.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sourceaction_typeflag 	 = request.getParameter("sourceaction_typeflag");
		String sourceaction_fieldValue   = request.getParameter("sourceaction_fieldValue");
		String sourceaction_numValue     = request.getParameter("sourceaction_numValue");
		String sourceaction_orderBy 	 = request.getParameter("sourceaction_orderBy");
		
		String flagType		 	    	 = request.getParameter("flagType");
		
		try{
			SourceActionConfigBean SourceActionConfigBean = new SourceActionConfigBean();
		
			SourceActionConfigOp	  SourceActionConfigOp	= new SourceActionConfigOp();
			
			SourceActionConfigBean.setSourceaction_typeflag(sourceaction_typeflag);
			SourceActionConfigBean.setSourceaction_fieldValue(sourceaction_fieldValue);
			SourceActionConfigBean.setSourceaction_numValue(sourceaction_numValue);
			SourceActionConfigBean.setSourceaction_orderBy(sourceaction_orderBy);
			
			if (flagType.equals("1"))
			{	
				GetNewId getNewId=new GetNewId();
				String id= getNewId.getnewid("sourceactionconfig","Id");
				
				SourceActionConfigBean.setId(id);
				SourceActionConfigOp.sourceActionCnfInsert(SourceActionConfigBean);
				
				logger.info("插入成功 381:");
				
			}else if (flagType.equals("2"))
			{
				String sourceattcfg_id		 = request.getParameter("Id");
				SourceActionConfigBean.setId(sourceattcfg_id);
				SourceActionConfigOp.sourceActionCnfModify(SourceActionConfigBean);
				logger.info("修改成功！382:");
			}
			String Path = request.getContextPath();
			response.sendRedirect(Path+"/roles/sourceactionconfig.jsp?targetflag=yes");
			
		}catch(Exception e){
			logger.info("error！383:"+e.toString());
		}
	}
}
