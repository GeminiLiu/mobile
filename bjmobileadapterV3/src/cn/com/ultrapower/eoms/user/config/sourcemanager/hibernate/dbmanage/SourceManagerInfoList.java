package cn.com.ultrapower.eoms.user.config.sourcemanager.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.dbmanage.MenuFindInfoList;
import cn.com.ultrapower.eoms.user.config.sourcemanager.hibernate.po.SourceManagerpo;

public class SourceManagerInfoList 
{
	static final Logger logger = (Logger) Logger.getLogger(MenuFindInfoList.class);
	
	GetFormTableName getFormTableName	= new GetFormTableName();
	//资源配置表
	private String sourceconfig			= getFormTableName.GetFormName("sourceconfig");
	//管理者类型为资源管理者
	private String systemmanage			= getFormTableName.GetFormName("systemmanage");
	
	public SourceManagerInfoList()
	{
		
	}
	
	public List getSourceInfoList()
	{
		try
		{
			 String 		sql	 		 = "from SourceManagerpo";
			 HibernateDAO   session 	= new HibernateDAO(); 
			 List 			list	    = session.queryObject(sql);
			
			 return list;
		}
		catch(Exception e)
		{
			logger.error("999 SourceManagerInfoList.getSourceInfoList error:"+e.toString());
			return null;
		}
	}
	public List getSourceInfoRow(String sql,int pagenum,int pagesize)
	{
		try
		{
			 HibernateDAO   session 	= new HibernateDAO(); 
			 List 			list	    = session.queryObject(sql);
			
			 return list;
		}
		catch(Exception e)
		{
			logger.error("999 SourceManagerInfoList.getSourceInfoList error:"+e.toString());
			return null;
		}
	}
	public SourceManagerpo getSourceOneInfo(String id)
	{
		try
		{
			 String 		sql	 		= "from SourceManagerpo where c1='"+id+"'";
			 HibernateDAO   session1 	= new HibernateDAO(); 
			 List 			list	    = session1.queryObject(sql);
			 SourceManagerpo v1			=new SourceManagerpo();
	   	     if(list!=null)
	   	     {
		   	    Iterator 	it 								= list.iterator();
			    if(it.hasNext())
			    {
			        v1 	= (SourceManagerpo) it.next();

			    }
		     }
		   	 logger.info("get SourceManagerInfoList");
			 return v1;
		}
		catch(Exception e)
		{
			logger.error("999 SourceManagerInfoList.getSourceInfoList error:"+e.toString());
			return null;
		}
	}
	
	/**
	 * <p>Description:获得组管理者List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-29
	 * @param groupId
	 * @return List
	 */
	public List getSourceManager(String groupId)
	{
		try
		{
			String sql	= "select a from SysPeoplepo a,SourceManagerpo b where a.c1 = b.c650000007 and b.c650000003 = '"+groupId+"' and b.c650000004 is null";
			sql			= sql + " and not exists (select b.c620000028 from SysGroupUserpo b where b.c620000027 = '"+groupId+"' and b.c620000028 = a.c1) order by a.c630000017,a.c630000001";
			List list	= HibernateDAO.queryObject(sql);
			return list;
		}
		catch(Exception e)
		{
			logger.error("[494]SourceManagerInfoList.getSourceManager() 获得组管理者List失败"+e.getMessage());
			return null;
		}
	}
	
	public String getSystemMangerSourceId()
	{
		try
		{
			String sql = "select a.sourceId from Sourceconfig a where a.sourceName = '"+systemmanage+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it = list.iterator();it.hasNext();)
			{
				Long sourceId = (Long)it.next();
				return String.valueOf(sourceId);
			}
			
			logger.info("[]SourceManagerInfoList.getSystemMangerSourceId() 没有此记录");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[]SourceManagerInfoList.getSystemMangerSourceId() 获得系统管理ID失败"+e.getMessage());
			return null;
		}
	}

}
