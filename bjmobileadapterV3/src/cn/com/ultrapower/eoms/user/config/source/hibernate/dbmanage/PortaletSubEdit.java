package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.config.source.Bean.PortaletSubscibeBean;

public class PortaletSubEdit extends HttpServlet
{

	static final Logger logger = (Logger) Logger.getLogger(PortaletSubAction.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String portaletsourceid   	= request.getParameter("sourceid");
		String portaletorderby 	 	= request.getParameter("portaletorderby");
		String portaletdesc 	 	= request.getParameter("portaletdesc");
		
		try{
			PortaletSubscibeBean portaletinfo = new PortaletSubscibeBean();
		
			PortaletSubOp	  portaletop	= new PortaletSubOp();
			
			portaletinfo.setNote(portaletdesc);
			if(!portaletorderby.equals(""))
			{
				portaletinfo.setPortaletOrderby(new Long(portaletorderby));
			}
			portaletinfo.setPortaletPortalsourceid(new Long(portaletsourceid));
			
			String portaletsourceid2    = request.getParameter("sourceid2");
			String portaletid 	 		= request.getParameter("portaletid");
			portaletinfo.setPortaletId(new Long(portaletid));
			portaletinfo.setPortaletContentsourceid(new Long(portaletsourceid2));
			portaletop.portaletSubModify(portaletinfo);
			logger.info("修改成功！");
			
			String Path = request.getContextPath();
			response.sendRedirect(Path+"/roles/portaletsubedit.jsp?targetflag=yes&id="+portaletid);
			
		}catch(Exception e){
			logger.info("error！383:"+e.toString());
		}
	}

}
