package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.GetNewId;
import cn.com.ultrapower.eoms.user.config.source.Bean.PortaletSubscibeBean;

public class PortaletSubAction extends HttpServlet
{
	static final Logger logger = (Logger) Logger.getLogger(PortaletSubAction.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		String portaletsourceid = request.getParameter("sourceid");
		String portaletorderby  = request.getParameter("portaletorderby");
		String portaletdesc     = request.getParameter("portaletdesc");

		try
		{
			PortaletSubscibeBean portaletinfo = new PortaletSubscibeBean();

			PortaletSubOp portaletop = new PortaletSubOp();

			portaletinfo.setNote(portaletdesc);
			if (!portaletorderby.equals(""))
			{
				portaletinfo.setPortaletOrderby(new Long(portaletorderby));
			}
			portaletinfo.setPortaletPortalsourceid(new Long(portaletsourceid));

			String portaletsourceid2 = request.getParameter("sourceid2");
			String[] tmpstr = portaletsourceid2.split(",");
			for (int i = 0; i < tmpstr.length; i++)
			{
				GetNewId getNewId = new GetNewId();
				String id = getNewId.getnewid("portalet_subscibe",
						"portalet_id");
				portaletinfo.setPortaletId(new Long(id));
				portaletinfo.setPortaletContentsourceid(new Long(tmpstr[i]));
				portaletop.portaletSubInsert(portaletinfo);
				logger.info("插入成功 ");
			}
			String Path = request.getContextPath();
			response.sendRedirect(Path
					+ "/roles/portaletsubadd.jsp?targetflag=yes");

		} catch (Exception e)
		{
			logger.info(e.toString());
		}
	}

}
