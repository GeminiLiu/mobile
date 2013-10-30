package cn.com.ultrapower.eoms.user.sla.aroperationdata;

import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SlaConfigPo;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.UserSlaConfigPo;

/**
 * 
 * @author fangqun
 * @CreatTime 2008-4-17
 */
public class UserSlaConfig {
	
	static final Logger logger = (Logger) Logger.getLogger(UserSlaConfig.class);			
	
	/**
	 * <p>Description:对工单超时配置进行数据添加<p>
	 * @author fangqun
	 * @creattime 2008-4-17
	 * @param UserSlaConfigPo
	 * @return boolean
	 */
	public boolean insertUserSlaConfig(UserSlaConfigPo userslaconfigpo)
	{
		try{
			userslaconfigpo.setId(Function.getNewID("userslaconfig", "id"));//id
			return HibernateDAO.insert(userslaconfigpo);
		}
		catch(Exception e)
		{
			logger.error("[456]UserSlaConfig.insertUserSlaConfig() 对信息进行数据添加失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:对工单超时配置进行数据修改<p>
	 * @author fangqun
	 * @creattime 2008-4-17
	 * @param UserSlaConfigPo
	 * @return boolean
	 */
	public boolean modifyUserSlaConfig(UserSlaConfigPo userslaconfigpo)
	{
		try{
			return HibernateDAO.modify(userslaconfigpo);
		}
		catch(Exception e)
		{
			logger.error("[458]UserSlaConfig.UsermodifySlaConfig() 对信息进行数据修改失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:对工单超时配置进行数据删除<p>
	 * @author fangqun
	 * @param id
	 * @return
	 */
	public boolean deleteUserSlaConfig(String id)
	{
		
		try{
			IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			Statement stmt		= dataBase.GetStatement();
			String sql = " delete from userslaconfig where id="+id+"";
			System.out.println("删除"+sql);
			stmt.execute(sql);
			Function.closeDataBaseSource(null, stmt, dataBase);
			return true;			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			logger.error("[457]UserSlaConfig.deleteUserSlaConfig() 对信息进行数据删除失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据id查询信息,返回List<p>
	 * @author fangqun
	 * @param id
	 * @return
	 */
	
	public List find(String id)
	{
		try
		{
			String sql=" from UserSlaConfigPo userslaconfigpo,SysPeoplepo syspeoplepo where userslaconfigpo.Userid=syspeoplepo.c1 and userslaconfigpo.Slaid="+id;
			List list= HibernateDAO.queryObject(sql);
			return list;
		}
		catch(Exception e)
		{
			logger.error("[458]UserSlaConfig.find() 按照组ID查找相应超时人员信息失败"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 添加或修改时排重
	 * @param UserSlaConfigPo
	 * @return boolean
	 */
	public boolean isDuplicate(UserSlaConfigPo userslaconfigpo)
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("from UserSlaConfigPo userslaconfigpo where");
		  	sql.append(" userslaconfigpo.Slaid='"+userslaconfigpo.getSlaid()+"'");
		  	sql.append(" and userslaconfigpo.Userid='"+userslaconfigpo.getUserid()+"'");
		  	List list = HibernateDAO.queryObject(sql.toString());
		  		  	
		  	if(list.size()>0){
		  		boolean flag = Function.nullString(userslaconfigpo.getId()).equals(((UserSlaConfigPo)list.get(0)).getId());
		  		if(flag){
		  			return false;
		  		}else{
		  			return true;
		  		}
		  	}else{
		  		return false;
		  	}			
		}
		catch(Exception e)
		{
			logger.error("[421]UserSlaConfig.isDuplicate() 排重失败"+e.getMessage());
			return true;
		}
	}
	
}

