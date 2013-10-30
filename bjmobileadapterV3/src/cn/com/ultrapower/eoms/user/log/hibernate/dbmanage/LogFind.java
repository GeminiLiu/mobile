package cn.com.ultrapower.eoms.user.log.hibernate.dbmanage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.ConvertTimeToSecond;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.log.hibernate.po.SysLogpo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

/**
 * <p>Description:使用hibernate从数据库中查找字段<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-27
 */
public class LogFind {
	
	static final Logger logger = (Logger) Logger.getLogger(LogFind.class);
	
	/**
	 * <p>Description:从Sourceconfig表中按照模块名称查找相应模块ID<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-27
	 * @param moduleName
	 * @return String
	 */
	public String findModuleId(String moduleName)
	{	
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//		    session.clear();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery("from Sourceconfig where sourceName="+moduleName);
//			-----------shigang modify--------------
			String sql="from Sourceconfig where sourceName="+moduleName;
			List list= HibernateDAO.queryObject(sql);
			
//			List list = query.list();
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				Sourceconfig sourceConfig=(Sourceconfig)it.next();
				String sourceId = String.valueOf(sourceConfig.getSourceId());
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return sourceId;
			}
			return null;
		}
		catch(Exception e)
		{
			logger.error("[454]LogFind.findModuleId() 从Sourceconfig表中按照模块名称查找相应模块ID失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:按照用户登录名查找用户全名<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-27
	 * @param userLoginName
	 * @return String
	 */
	public String findUserFullName(String userLoginName)
	{
		//use the interface of the people information to get the userfullname
		GetUserInfoList userInfoList	= new GetUserInfoList();
		try
		{
			SysPeoplepo peoplepo			= userInfoList.getUserInfoName(userLoginName);
			String userFullName				= peoplepo.getC630000003();
			return userFullName;
		}
		catch(Exception e)
		{
			logger.error("[455]LogFind.findUserFullName() 按照用户登录名查找用户全名失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:获得以秒为单位的系统时间<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-27
	 * @return String
	 */
	public String findCurrentTime()
	{
		//Date date = new Date();
		//String time = date.toString();
		//System.out.println(time);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.SIMPLIFIED_CHINESE);
		String timestr = sdf.format(new Date()); 
	   
		//use the common class: ConvertTimeToSecond
		//String temptime = new Long(ConvertTimeToSecond.timeConvert(timestr)).toString();
		String secondTime = String.valueOf(ConvertTimeToSecond.timeConvert(timestr));
		return secondTime;
	}
	
	public List getUserLogList()
	{
		List list = null;
		String sql = "from SysUserLogpo";
		try
		{
			list= HibernateDAO.queryObject(sql);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return list;
	}
	
	public static void main(String args[])
	{
		LogFind logfind = new LogFind();
	    System.out.println(logfind.findCurrentTime());
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.SIMPLIFIED_CHINESE);
		String timestr = sdf.format(new Date()); 
		System.out.println(timestr);
	}
}
