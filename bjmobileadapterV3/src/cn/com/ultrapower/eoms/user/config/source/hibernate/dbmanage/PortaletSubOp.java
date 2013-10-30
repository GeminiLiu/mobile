package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.Bean.PortaletSubscibeBean;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.PortaletSubscibe;

public class PortaletSubOp
{

static final Logger logger = (Logger) Logger.getLogger(PortaletSubOp.class);
	
	public synchronized PortaletSubscibe beanValue(PortaletSubscibeBean portaletsubinfo)
	{
		PortaletSubscibe portbean = new PortaletSubscibe();
		portbean.setNote(Function.nullString(portaletsubinfo.getNote()));
		portbean.setPortaletContentsourceid(portaletsubinfo.getPortaletContentsourceid());
		portbean.setPortaletId(portaletsubinfo.getPortaletId());
		String orderby = Function.nullString(String.valueOf(portaletsubinfo.getPortaletOrderby()));
		if(!orderby.equals(""))
		{
			portbean.setPortaletOrderby(new Long(orderby));
		}
		portbean.setPortaletParentid(portaletsubinfo.getPortaletParentid());
		portbean.setPortaletPortalsourceid(portaletsubinfo.getPortaletPortalsourceid());
		portbean.setPortaletUserloginname(Function.nullString(portaletsubinfo.getPortaletUserloginname()));
		return portbean;
	}
	
	public synchronized boolean portaletSubInsert(PortaletSubscibeBean protaletsubinfo)
	{
		try
		{
			HibernateDAO.insert(beanValue(protaletsubinfo));
			return true;
		}
		catch(Exception e)
		{
			logger.error(""+e.getMessage());
			return false;
		}
	}
	
	public synchronized boolean portaletSubModify(PortaletSubscibeBean protaletsubinfo)
	{
		try
		{			
			HibernateDAO.modify(beanValue(protaletsubinfo));
			return true;
		}
		catch(Exception e)
		{
			logger.error(""+e.getMessage());
			return false;
		}
	}
	
	public synchronized boolean portaletSubDelete(String protaletid)
	{
		try
		{
			String Sql="from PortaletSubscibe a where a.portaletId="+protaletid;
		
			if(HibernateDAO.deleteMulObjects1(Sql))
			{
				logger.info("删除"+protaletid+"成功");
				return true;
			}
			else
			{
				logger.info("删除"+protaletid+"失败");
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error(""+e.getMessage());
			return false;
		}
	}
	

}
