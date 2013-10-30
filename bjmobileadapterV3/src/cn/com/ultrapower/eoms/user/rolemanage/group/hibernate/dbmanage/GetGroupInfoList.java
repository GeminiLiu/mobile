package cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;

/**
 * <p>Description:封装组信息接口<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-19
 */
public class GetGroupInfoList {
	
	static final Logger logger 		= (Logger) Logger.getLogger(GetGroupInfoList.class);
	
	/**
	 * <p>Description:根据组ID查找组信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-19
	 * @param groupID
	 * @return SysGrouppo
	 */
	public SysGrouppo getGroupInfo(String groupID)
	{
		try
		{
//			Session session			= HibernateSessionFactory.currentSession();
//			Transaction tx			= session.beginTransaction();
//			SysGrouppo sysGrouppo	= (SysGrouppo)session.load(SysGrouppo.class,groupID);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return sysGrouppo;
			//-----------shigang----------modify
			SysGrouppo sysGrouppo=(SysGrouppo) HibernateDAO.loadStringValue(SysGrouppo.class,groupID);
			return sysGrouppo;
		}
		catch(Exception e)
		{
			logger.error("[418]GetGroupInfoList.getGroupInfo() 根据组ID查找组信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:从UltraProcess:SYSGROUP表中返回一个List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-19
	 * @return List
	 */
	public List getGroupList()
	{
		try 
		{
//		    Session session = HibernateSessionFactory.currentSession();
//		    session.clear();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("from SysGrouppo");
//			List list		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return list;
			return HibernateDAO.queryObject("from SysGrouppo");
		} 
		catch (Exception e) 
		{
			logger.error("[419]GetGroupInfoList.getGroupList() 从UltraProcess:SYSGROUP表中List失败"+e.getMessage());
			return null;
		}	
	}
	
	/**
	 * <p>Description:根据父组ID查找组信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-1
	 * @param parentId
	 * @return List
	 */
	public List getGroupList(String parentId)
	{
		try
		{
//			Session session	= HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("from SysGrouppo where c630000020 ="+parentId);
//			List list		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return list;
			return HibernateDAO.queryObject("from SysGrouppo where c630000020 ='"+parentId +"' and c630000025='0'");
		}
		catch(Exception e){
			logger.error("[420]GetGroupInfoList.getGroupList() 根据父组ID查找组信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过groupIntId查找组信息</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-18
	 * @param groupIntId
	 * @return SysGrouppo
	 */
	public SysGrouppo findByIntId(String groupIntId)
    {
    	try
    	{
    		String sql	= "from SysGrouppo a where a.c630000030 ='"+groupIntId+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				SysGrouppo sysGrouppo = (SysGrouppo)it.next();
				return sysGrouppo;
			}
			logger.info("[536]GetGroupInfoList.findByIntId() 系统中不存在该组");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[536]GetGroupInfoList.findByIntId() 通过groupIntId查找组信息失败"+e.getMessage());
			return null;
		}
    }  
	
	/**
	 * <p>Description:查找组信息</p>
	 * @author fangqun
	 * @creattime 2008-4-8
	 * @param 
	 * @return String
	 */
	public String getGroupInfo()
	{
		StringBuffer optionvalue   = new StringBuffer();
		try
		{
			List list = getGroupList();
			Iterator it=list.iterator();
			optionvalue.append("<option value=''>请选择</option>");
			
			while(it.hasNext())
			{	
				SysGrouppo sysgrouppo = (SysGrouppo)it.next();
				optionvalue.append("<option value='"+String.valueOf(sysgrouppo.getC1())+"'>"+String.valueOf(sysgrouppo.getC630000018())+"</option>");
			}
			
			return optionvalue.toString();
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
