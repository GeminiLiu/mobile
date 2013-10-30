package cn.com.ultrapower.eoms.user.config.groupuser.hibernate.dbmanage;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

/**
 * <p>Description:获得组成员信息列表<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-25
 */
public class GetGroupuserInfoList {
	
	static final Logger logger = (Logger) Logger.getLogger(GetGroupuserInfoList.class);
	
	/**
	 * <p>Description:获得用户列表返回List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-25
	 * @param mgroupId
	 * @return List
	 */
	public List getUserList(String mgroupId)
	{
		try 
		{
			String sql = "from SysGroupUserpo where c620000027 = '"+mgroupId+"'";
			return HibernateDAO.queryClearObject(sql);
		} 
		catch (Exception e) 
		{
			logger.error("[462]GetGroupuserInfoList.getUserList() 获得用户列表返回List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:获得组列表List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-25
	 * @param muserId
	 * @return List
	 */
	public List getGroupList(String muserId)
	{
		try 
		{
			String sql = "from SysGroupUserpo where c620000028 ='"+muserId+"'";
			return HibernateDAO.queryClearObject(sql);
		} 
		catch (Exception e) 
		{
			logger.error("[463]GroupUserFind.find() 获得组列表List失败"+e.getMessage());
			return null;
		}
	}
	
}
