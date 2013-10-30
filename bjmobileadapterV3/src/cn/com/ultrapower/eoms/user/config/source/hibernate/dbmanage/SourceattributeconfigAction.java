package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.GetNewId;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceattributeconfigBean;
import cn.com.ultrapower.eoms.user.comm.function.Function;
public class SourceattributeconfigAction extends HttpServlet {
	
	static final Logger logger = (Logger) Logger.getLogger(SourceattributeconfigAction.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String sourceattcfg_id		 = request.getParameter("sourceattcfg_id");
		String sourceattcfg_typeflag = request.getParameter("sourceattcfg_typeflag");
		String sourceattcfg_cnname   = request.getParameter("sourceattcfg_cnname");
		String sourceattcfg_enname   = request.getParameter("sourceattcfg_enname");
		String sourceattcfg_type 	 = request.getParameter("sourceattcfg_typeflag");
		String sourceattcfg_orderby  = request.getParameter("sourceattcfg_orderby");
		String sourceattcfg_desc 	 = request.getParameter("sourceattcfg_desc");
		
		String flagType		 	     = request.getParameter("flagType");
		
		try{
			SourceattributeconfigBean sourceattributeconfigBean = new SourceattributeconfigBean();
			SourceAttributeConfigOp	  sourceAttributeConfigOp	= new SourceAttributeConfigOp();
			
			//sourceattributeconfigBean.setSourceattcfg_id(new Long(Function.nuLong(sourceattcfg_id)));
			sourceattributeconfigBean.setSourceattcfg_typeflag(sourceattcfg_typeflag);
			sourceattributeconfigBean.setSourceattcfg_cnname(sourceattcfg_cnname);
			sourceattributeconfigBean.setSourceattcfg_enname(sourceattcfg_enname);
			sourceattributeconfigBean.setSourceattcfg_type(sourceattcfg_type);
			sourceattributeconfigBean.setProurceattcfg_orderby(new Long(Function.nuLong(sourceattcfg_orderby)));
			sourceattributeconfigBean.setSourceattcfg_desc(sourceattcfg_desc);
			
			if (flagType.equals("1"))
			{	
				GetNewId getNewId=new GetNewId();
				long sourceattcfg_id=Long.parseLong(getNewId.getnewid("sourceattributeconfig","sourceattcfg_id"));
				
				sourceattributeconfigBean.setSourceattcfg_id(new Long(sourceattcfg_id));
				sourceAttributeConfigOp.sourceCnfInsert(sourceattributeconfigBean);
				
				logger.info("插入成功 377:::");
				
			}else if (flagType.equals("2"))
			{
				String sourceattcfg_id		 = request.getParameter("sourceattcfg_id");
				sourceattributeconfigBean.setSourceattcfg_id(new Long(Function.nuLong(sourceattcfg_id)));
				
				sourceAttributeConfigOp.sourceCnfModify(sourceattributeconfigBean);
				logger.info("修改成功！378:::");
			}
			String Path = request.getContextPath();
			response.sendRedirect(Path+"/roles/sourceattributeconfig.jsp?targetflag=yes");
			
		}catch(Exception e){
			logger.info("error 379::"+e.toString());
		}
	}
}
