package cn.com.ultrapower.eoms.user.sla.bussiness;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.sla.aroperationdata.SlaConfig;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SlaConfigPo;

public class SlaConfigEdit extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(SlaConfigEdit.class);

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{ 
	
		String id;//id
		String SlaType;//超时类型
		String Slasupertime;//工单超时时长
	    String SlaSchema;//工单的schema
		String Slacompany;//工单的单位id
		String Sendobj;//发送对象
		
		id =Function.nullString(req.getParameter("id"));
		SlaType =Function.nullString(req.getParameter("SlaType"));
		Slasupertime =Function.nullString(req.getParameter("Slasupertime"));
		SlaSchema =Function.nullString(req.getParameter("SlaSchema"));
		Slacompany =Function.nullString(req.getParameter("Slacompany"));
		Sendobj =Function.nullString(req.getParameter("Sendobj"));

		SlaConfigPo slaconfigpo = new SlaConfigPo();
				
		slaconfigpo.setId(id);
		slaconfigpo.setSlacompany(Slacompany);
		slaconfigpo.setSlaSchema(SlaSchema);
		slaconfigpo.setSlasupertime(Slasupertime);
		slaconfigpo.setSlaType(SlaType);
		slaconfigpo.setSendobj(Sendobj);
		
		SlaConfig groupgrand = new SlaConfig();
		
		if(groupgrand.isDuplicate(slaconfigpo)){
			res.sendRedirect("../roles/slaconfigedit.jsp?error=001&id="+id);
		}else{
			groupgrand.modifySlaConfig(slaconfigpo);		
			res.sendRedirect("../roles/slaconfigedit.jsp?targetflag=yes");
		}
	}
}

